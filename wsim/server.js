// server.js — Express backend for Stripe Checkout + license email
// -------------------------------------------------------------
// What this does
// 1) POST /api/create-checkout-session — creates a Stripe Checkout Session
// 2) POST /api/stripe/webhook — listens for checkout.session.completed, then
//    generates a license key (idempotently), stores it, and emails it to the buyer
//
// Quick start
//   npm init -y
//   npm i express stripe body-parser cors nodemailer better-sqlite3 dotenv
//   node server.js
//
// Required environment variables (see .env.example below in comments):
//   STRIPE_SECRET_KEY
//   STRIPE_WEBHOOK_SECRET
//   PRICE_ID
//   BASE_URL (e.g., https://yourdomain.com or http://localhost:3000)
//   SMTP_HOST, SMTP_PORT, SMTP_USER, SMTP_PASS, SMTP_FROM
//   DOWNLOAD_URL (direct or gated link to your ~3GB installer)
//   LICENSE_SECRET (random string used to sign license checksums)

require('dotenv').config();
const express = require('express');
const bodyParser = require('body-parser');
const cors = require('cors');
const Stripe = require('stripe');
const crypto = require('crypto');
const nodemailer = require('nodemailer');
const Database = require('better-sqlite3');

// ---- Config ----
const {
  STRIPE_SECRET_KEY,
  STRIPE_WEBHOOK_SECRET,
  PRICE_ID,
  BASE_URL = 'http://localhost:3000',
  SMTP_HOST,
  SMTP_PORT,
  SMTP_USER,
  SMTP_PASS,
  SMTP_FROM,
  DOWNLOAD_URL,
  LICENSE_SECRET = 'change-me',
} = process.env;

if (!STRIPE_SECRET_KEY || !STRIPE_WEBHOOK_SECRET || !PRICE_ID) {
  console.error('Missing required Stripe env vars. Check STRIPE_SECRET_KEY, STRIPE_WEBHOOK_SECRET, PRICE_ID');
  process.exit(1);
}
if (!SMTP_HOST || !SMTP_PORT || !SMTP_USER || !SMTP_PASS || !SMTP_FROM) {
  console.error('Missing SMTP env vars. Check SMTP_HOST, SMTP_PORT, SMTP_USER, SMTP_PASS, SMTP_FROM');
  process.exit(1);
}
if (!DOWNLOAD_URL) {
  console.error('Missing DOWNLOAD_URL env var (link to your 3GB installer)');
  process.exit(1);
}

const stripe = new Stripe(STRIPE_SECRET_KEY, { apiVersion: '2024-06-20' });

// ---- DB setup (SQLite) ----
const db = new Database('licenses.db');
db.exec(`
  CREATE TABLE IF NOT EXISTS licenses (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    session_id TEXT UNIQUE,
    email TEXT NOT NULL,
    name TEXT,
    license_key TEXT NOT NULL,
    created_at TEXT NOT NULL DEFAULT (datetime('now'))
  );
`);

const insertLicense = db.prepare(`
  INSERT INTO licenses (session_id, email, name, license_key) VALUES (?, ?, ?, ?)
`);
const getLicenseBySession = db.prepare(`SELECT * FROM licenses WHERE session_id = ?`);

// ---- Email transport ----
const transporter = nodemailer.createTransport({
  host: SMTP_HOST,
  port: Number(SMTP_PORT),
  secure: Number(SMTP_PORT) === 465, // true for 465, false for others
  auth: { user: SMTP_USER, pass: SMTP_PASS },
});

async function sendLicenseEmail({ to, name, licenseKey }) {
  const subject = 'Your Weather Simulator License Key';
  const html = `
    <p>Hi${name ? ' ' + escapeHtml(name) : ''},</p>
    <p>Thank you for your purchase! Here is your license key:</p>
    <pre style="font-size:1.25rem; font-weight:700; padding:12px 16px; background:#f4f4f4; border-radius:8px;">${licenseKey}</pre>
    <p>You can download the installer (≈ 3 GB) using this link:</p>
    <p><a href="${DOWNLOAD_URL}">${DOWNLOAD_URL}</a></p>
    <p><strong>Keep this email safe.</strong> If you need help, just reply to this message.</p>
    <p>— Weather Simulator Team</p>
  `;

  await transporter.sendMail({ from: SMTP_FROM, to, subject, html });
}

function escapeHtml(s = '') {
  return s.replace(/[&<>"]/g, (c) => ({ '&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;' }[c]));
}

// ---- License key generation ----
function generateLicenseKey(email) {
  // Format: WS-XXXXXX-XXXXXX-XXXXXX-CHK
  const core = crypto.randomBytes(12).toString('base64url').toUpperCase().replace(/[^A-Z0-9]/g, '').slice(0, 18);
  const parts = core.match(/.{1,6}/g); // 3 groups of 6
  const payload = `${email}|${parts.join('-')}`;
  const chk = crypto.createHmac('sha256', LICENSE_SECRET).update(payload).digest('base64url').toUpperCase().slice(0, 6);
  return `WS-${parts.join('-')}-${chk}`;
}

// ---- Express app ----
const app = express();
app.use(cors());
app.use(express.json());

// Create Checkout Session
app.post('/api/create-checkout-session', async (req, res) => {
  try {
    const { priceId = PRICE_ID, customerEmail, customerName, clientReferenceId, metadata = {} } = req.body || {};

    if (!customerEmail) return res.status(400).json({ error: 'Missing customerEmail' });

    const session = await stripe.checkout.sessions.create({
      mode: 'payment',
      payment_method_types: ['card'],
      line_items: [{ price: priceId, quantity: 1 }],
      customer_email: customerEmail,
      client_reference_id: clientReferenceId || undefined,
      metadata,
      success_url: `${BASE_URL}/success.html?session_id={CHECKOUT_SESSION_ID}`,
      cancel_url: `${BASE_URL}/buy.html?canceled=1`,
      allow_promotion_codes: true,
    });

    res.json({ sessionId: session.id });
  } catch (err) {
    console.error('create-checkout-session error:', err);
    res.status(500).send(err.message || 'Internal error');
  }
});

// Stripe webhook needs the raw body to verify the signature
app.post('/api/stripe/webhook', bodyParser.raw({ type: 'application/json' }), async (req, res) => {
  let event;
  const sig = req.headers['stripe-signature'];
  try {
    event = stripe.webhooks.constructEvent(req.body, sig, STRIPE_WEBHOOK_SECRET);
  } catch (err) {
    console.error('Webhook signature verification failed:', err.message);
    return res.status(400).send(`Webhook Error: ${err.message}`);
  }

  if (event.type === 'checkout.session.completed') {
    const session = event.data.object;
    const sessionId = session.id;
    const email = session.customer_email;
    const name = session.customer_details && session.customer_details.name ? session.customer_details.name : null;

    if (!email) {
      console.warn('checkout.session.completed with no email');
      return res.json({ received: true });
    }

    try {
      // Idempotency: if we've already issued a license for this session, skip
      const existing = getLicenseBySession.get(sessionId);
      if (existing) {
        console.log('License already issued for session', sessionId);
      } else {
        const licenseKey = generateLicenseKey(email);
        insertLicense.run(sessionId, email, name, licenseKey);
        await sendLicenseEmail({ to: email, name, licenseKey });
        console.log('Issued license to', email);
      }
    } catch (err) {
      console.error('Error issuing license:', err);
      // Let Stripe retry the webhook if we fail
      return res.status(500).send('Temporary failure, please retry');
    }
  }

  res.json({ received: true });
});

// (Optional) Basic success page (serve your static /success.html with your web server)
app.get('/api/license/by-session/:id', (req, res) => {
  const row = getLicenseBySession.get(req.params.id);
  if (!row) return res.status(404).json({ error: 'Not found' });
  res.json({ email: row.email, name: row.name, licenseKey: row.license_key, createdAt: row.created_at });
});

const PORT = process.env.PORT || 3000;
app.listen(PORT, () => console.log(`Server listening on http://localhost:${PORT}`));

