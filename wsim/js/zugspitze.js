let trend =0; let rain = 0;

const canvas = document.getElementById('varChart');
if (!canvas) {
  console.warn('varChart canvas not found');
}


function updatePills(text){
    pillTrend.innerText= `${text}`;
}
const pill = id => document.getElementById(id);
const pillTmp   = pill('pillTmp');
const pillHum   = pill('pillHum');
const pillPres = pill('pillPres');
const pillWspd    = pill('pillWspd');
const pillRain    = pill('pillRain');
const pillTrend = pill('pillTrend');
const dirInput = document.getElementById('Input');
const pressureInput  = document.getElementById('pressureInput');
const varCtx = canvas.getContext('2d');

const varChart = new Chart(varCtx, {
  type: 'line',
  data: {
    labels: [],
    datasets: [
      { label: 'Temperatur', 
        data: [], 
        borderColor: 'rgba(123, 38, 2, 1)', 
        tension: 0.25 ,
        
      }
      
    ]
  },
  options: {
    scales: {
      x: { title: { display: true, text: 'Time' } },
      y: { title: { display: true, text: 'Temperatur' }}
    },
    plugins: { legend: { position: 'bottom' } }
  }
}) ;
let score = 0;
const tempT = ['Keep the Temperatur within 20°C and 26°C for a day'];
const humT = ['Keep the humidity within 60% and 80% for 6 hours'];
const windT = ['Do not let the wind bniedrig over 50 km/h today'];


let inc1 =0.04; let inc2=0.02; let inc3= inc1*50; let inc4 =inc2*50;   
    inc1 = inc1*2; inc2 = inc2*2;  
let ran = Math.random();let ran2 = Math.random();let ran3 = Math.random();let ran4 = Math.random();let ran5 = Math.random();  
        let hum = 77*(0.5+ (Math.random()-0.5)); let temp = 20;  
        let hour = Math.ceil(ran*24);  
        let minute = Math.ceil(ran2*60);  
        let day = Math.ceil(ran3*31);  
        let month = Math.ceil(ran4*12);  
        let year = Math.ceil(ran5*4500);  let chd=0;
    function main() {  
		
        if (hour > 10 && hour<18){ if(month ==6 || month==7 ){ temp=26;} if(month ==5 || month==8){ temp=24;}if(month ==4 || month==9){ temp=21;}  
        if(month ==3 || month==10){ temp=14.5;}if(month ==2 || month==11){ temp=9.5;}if(month ==1 || month==12){ temp=7;}}  
        if (hour < 10 || hour>18){  if(month ==6 || month==7 ){temp=19;}if(month ==5 || month==8){ temp=15;} if(month ==4 || month==9){ temp=12.4;}  
        if(month ==3 || month==10){ temp=8.2;}if(month ==2 || month==11){ temp=2.3;}if(month ==1 || month==12){ temp=-0.1;}}  
        if (year > -1 && year <1000){temp = temp-2;}if (year > 1000 && year <1300){temp = temp-4;} if (year > 1300 && year <1700) { temp = temp - 2; }  if (year > 1600 && year <1700){temp = temp-3;}  
        if (year > 1700 && year <1750){temp = temp-6;}if (year > 1750 && year <1920){temp = temp-5;}if (year > 1920 && year <1950){temp = temp-4;}if (year > 1950 && year <1980){temp = temp-3;}  
        if (year > 1980 && year <2000){temp = temp-2;}if (year > 2040 && year <2100){temp = temp+2;}if (year > 2100 && year <2200){temp = temp+4;}if (year > 2200 && year <2300){temp = temp+6;}  
        if (year > 2300 && year <2400){temp = temp+7;}if (year > 2400 && year <2600){temp = temp+6;}if (year > 2600 && year <3100){temp = temp+5;}  
        if (year > 3100 && year <4000){temp = temp+3;}if (year > 4000 && year <4500){temp = temp-1;}  
       setInterval(() => {  
               
            document.getElementById("tmpv").innerText = `${temp.toFixed(1)}`;  
            document.getElementById("humv").innerText = `${hum.toFixed(1)}`;  
          if(minute<10)  document.getElementById("hour").innerText = `Hour: ${hour}:0${minute}`;  
	       if(minute>9)  document.getElementById("hour").innerText = `Hour: ${hour}:${minute}`; 
            document.getElementById("day").innerText = `Date: ${day}/${month}/${year} `;  
            document.getElementById("windv").innerText = `${wspd.toFixed(1)}`;  
            
           if(minute>9)  document.getElementById("presv").innerText = `${pressure.toFixed(1)}`; 
            pillTmp.innerText = `${temp.toFixed(1)} °C`;  
            pillHum.innerText = `${hum.toFixed(1)} %`;
             try{ pillWspd.innerText = `${dirInput.innerText} ° ${wspd.toFixed(1)} km/h`;} catch{}
            pillPres.innerText = `${pressure.toFixed(1)} hPa`; 
		   if (hour >9 && hour<14){  
            temp +=inc1*tacc;
            hum -= 0.1*tacc;}  
            if (hour >7 && hour<9 || hour >14 && hour<16){  
            temp +=inc2*tacc;
            hum += 0.2*tacc;}    
            if (hour >16 && hour<22){  
            temp +=inc*tacc;
            hum -= 0.05*tacc;}    
            if (hour >0 && hour<7 || hour >22){  
            temp -=inc*tacc;
            hum += 0.05*tacc;}
            
			
			if(trend ==-1&&chd==0) temp-=0.2*tacc; 
			if(trend ==1&&chd==0) temp+=0.2*tacc;
			if(trend==0) temp +=0;
			
			if(trend ==-1&&chd==1) hum-=0.2*tacc; 
			if(trend ==1&&chd==1) hum+=0.2*tacc;
			if(trend==0&&chd==1) hum +=0;
             }, 1000);  
	const daysInMonth = [31,28,31,30,31,30,31,31,30,31,30,31];  let tacc = 1;
        setInterval(() => {  
            minute = minute +6*tacc;  
            if(minute >= 60){minute = 0; hour = hour +1;}  
            if(hour >= 24){hour =0; day = day + 1;}

if (day > daysInMonth[month - 1]) {
day = 1;
month++;
}
if(month >= 13){month = 1; year = year+1;}
if (hum>100) hum =89;

}, 5000);   
    
        
    }  
    function tempM(){  
          
        if (hour > 10 && hour<18){ if(month ==1 || month==2){ temp=25;} if(month ==5 || month==8){ temp=34;}if(month ==4 || month==9){ temp=33;}  
        if(month ==3 || month==10){ temp=29.7;}if(month ==2 || month==11){ temp=27.5;}if(month ==1 || month==12){ temp=26.2;}}  
        if (hour < 10 || hour>18){  if(month ==6 || month==7 ){temp=29.4;}if(month ==5 || month==8){ temp=28.5;} if(month ==4 || month==9){ temp=24.3;}  
        if(month ==3 || month==10){ temp=22.1;}if(month ==2 || month==11){ temp=20.4;}if(month ==1 || month==12){ temp=18.7;}}  
        if (year > -1 && year <1000){temp = temp-2;}if (year > 1000 && year <1300){temp = temp-4;} if (year > 1300 && year <1700) { temp = temp - 2; }  if (year > 1600 && year <1700){temp = temp-3;}  
        if (year > 1700 && year <1750){temp = temp-6;}if (year > 1750 && year <1920){temp = temp-5;}if (year > 1920 && year <1950){temp = temp-4;}if (year > 1950 && year <1980){temp = temp-3;}  
        if (year > 1980 && year <2000){temp = temp-2;}if (year > 2040 && year <2100){temp = temp+2;}if (year > 2100 && year <2200){temp = temp+4;}if (year > 2200 && year <2300){temp = temp+6;}  
        if (year > 2300 && year <2400){temp = temp+7;}if (year > 2400 && year <2600){temp = temp+6;}if (year > 2600 && year <3100){temp = temp+5;}  
        if (year > 3100 && year <4000){temp = temp+3;}if (year > 4000 && year <4500){temp = temp-1;}  
        setInterval(() => {  
           
            
        }, 60000);  
           
    }  let acumulator =0;
    let wspm = 1 + (-0.5 + Math.random()); let wspd = 45; let wspdd=9;
    let wspM =87; 
let vbl= 0;
    let pressure = 1023 *(1+ (-0.5+ Math.random()));  

    function windrain(){  

        setInterval(() => {  
      wspm = 1 + (-0.5 + Math.random())/wspdd;
          
		 wspm = 1 + (-0.5 + Math.random())/wspdd;
          if (trend == 1&&chd==2) wspm = 1 + (-0.65 + Math.random())/wspdd;
          if (trend == -1&&chd==2) wspm = 1 + (-0.35 + Math.random())/wspdd;
          if (trend == 0) wspm = 1 + (-0.5 + Math.random())/wspdd;
          if (wspd<0.1) wspd = 5;
			if (wspd>wspM)wspm = 1 + (-0.4 + Math.random())/wspdd;
          wspd*=wspm;
          if(wspd < 40 && wspd>10 && temp<24 && pressure<1010 && hum>85)  rain+=0.1;
          if(hour>22&& minute>50) rain =0;
          if (hour >0 && hour<7 || hour >22) wspM =65;
          if (hour >7 && hour<12) wspM= 60;
          if (hour >12 && hour<18) wspM =57;
          if (hour >18 && hour<22) wspM =52;
           }, 1000);   
    }  
    
    function presssure() {  
	if (pressure<977){pressure = 977;}  
	if (pressure>1029){pressure = 1029;}  
	setInterval(() => {  
		if (pressure<977){pressure = 977;}  
	if (pressure>1029){pressure = 1029;}  
		  
		if (hour >9 && hour<14){  
            pressure = pressure +0.1*tacc;;  
           }  
            if (hour >7 && hour<9 || hour >14 && hour<16){  
            pressure = pressure +0.04*tacc;  
            }  
            if (hour >16 && hour<22){  
            pressure = pressure -0.05*tacc;  
            }  
            if (hour >-1 && hour<7 || hour >22){  
            pressure = pressure -0.1*tacc;  
            }  
            if(trend ==-1&&chd==3) pressure -=0.2*tacc;
            if(trend ==1&&chd==3) pressure +=0.2*tacc;
    if (acumulator>36000*6) acumulator = 0;
	},444);  
	
	 mkChart();  upChartAs();
}  
let title = "";
function accTime(){tacc *=2; alert('Successfully accelerated time to x'+tacc);}
function decTime(){if(tacc>0.5)tacc /=2; alert('Successfully deccelerated time to x'+tacc);}
function getText(){
if (document.getElementById("tmpi").value !=""){ temp = parseFloat(document.getElementById("tmpi").value);}
if (document.getElementById("humi").value!=""){hum = parseFloat(document.getElementById("humi").value);}
if (document.getElementById("presi").value!=""){pressure = parseFloat(document.getElementById("presi").value);}
if (document.getElementById("windi").value!=""){wspd = parseFloat(document.getElementById("windi").value);}
if (document.getElementById("houri").value!=""){hour = parseFloat(document.getElementById("houri").value);}
if (document.getElementById("moni").value!=""){month = parseFloat(document.getElementById("moni").value);}
if (document.getElementById("yei").value!=""){year = parseFloat(document.getElementById("yei").value);}
if (document.getElementById("titi").value!=""){
title = document.getElementById("titi").value;
}
 
  
}
function update(){let aci = acumulator/36000;
    if(hour>19 || hour<7) {  
      temp-=1*aci; hum+=8*aci; pressure += 0.87*aci;}
    if(hour<20 || hour>7) { 
      temp+=1*aci; hum-=8*aci; pressure -= 0.87*aci;}
    
  }
function rise(){ trend = 1;  updatePills('rise'); }
function steady(){ trend = 0;  updatePills('steady'); }
function fall(){ trend=-1; updatePills('fall');} 

let counter = 1;
function mkChart(){
  if(!varChart){
    varChart.data.labels.length = 0;                 // clear safely
    varChart.data.datasets[0].data.push(temp);
    varChart.update();
  }
}

 let chdmem= chd;
function upChart(){
  if(varChart){
  varChart.data.labels.push(counter);     
  
    if(chd==0)varChart.data.datasets[0].data.push(temp);
    if(chd==1)varChart.data.datasets[0].data.push(hum);
    if(chd==2)varChart.data.datasets[0].data.push(wspd);
    if(chd==3)varChart.data.datasets[0].data.push(pressure);
    
   varChart.update('none');
	  if(chdmem!=chd){
    varChart.data.labels.length = 0;               
    varChart.data.datasets[0].data.length = 0;counter=0;
  }            
	  chdmem=chd;
    
  counter +=1;
  }
}
const chlab = varChart.data.datasets[0].label;
const ylab = varChart.options.scales.y.text;
function upn(text){
    chlab.innerText =`${text}`;
    ylab.innerText =`${text}`;
}
function stmp(){chd =0; upn('Temperatur');}function shum(){chd =1;upn('Humidity');}function swspd(){chd =2;upn('Wind Speed');
			 if (trend == 1) {UPPER_BOUND+=5;niedrigER_BOUND+=5;} if (trend == -1) {UPPER_BOUND-=5;niedrigER_BOUND-=5;}
}function spres(){chd =3;upn('Pressure');}

function upChartAs(){
  setInterval(()=>upChart(), 2000);
}
const wals = ["Es is allzu windig, Machen Sie das Wind ruhiger wehen bitte.","Sie haben vor, alle Gebäude fliegen lassen.", "Es ist Zeit, das Wind aufhalten lassen, oder?"];
const thals = ["Es ist zu heiß, wenn sie machen die Temperatur nicht niedriger, alle Tieren werden aussterben.", "Es ist zu heiß, wenn sie machen die Temperatur nicht niedriger, all die Fauna wird sterben.",
    "Es ist zu heiß, wenn Sie machen nicht die Temperatur niedriger, Einwohner werden irgendwo wegziehen."];
const tlals = ["Es ist zu kalt, wenn Sie machen nicht die Temperatur niedriger, all die Fauna wird sterben.", "Es ist zu kalt, wenn Sie machen nicht die Temperatur niedriger, all die Fauna wird sterben.", 
    "Es ist zu kalt, wenn Sie machen nicht die Temperatur niedriger, Einwohner werden irgendwo wegziehen."];
const hlals = ["Die Wirbeltiere wird sich abtrocknen wenn sie lassen nicht die Luftfeuchtigkeit steigen.","Es ist so trocken, dass eine Abnahme des Flusses wahrnembar ist.", "Die Wirbeltiere wird sich abtrocknen wenn sie lassen nicht die Luftfeuchtigkeit steigen.", 
    "Wer hat es so trocken werden lassen?", "Die Einwohnern können nicht aufhoren, Wasser zu trinken."];
const phals= ["Das Luftdruck ist zu hoch, Sie sollen es niedriger macheb.","Das Luftdruck ist zu hoch, Sie sollen es  niedriger.","Das Luftdruck ist zu hoch, Sie sollen es  niedriger."]
const plals= ["Das Luftdruck ist zu niedrig, Sie sollen es niedriger machen.","Das Luftdruck ist zu niedrig, Sie sollen es höher machen.",
    "Das Luftdruck ist zu niedrig, Sie sollen es höher machen."]

function wfoals(){
    let ran = (Math.random()*3).toFixed(0);
    setInterval(()=>{
            if(temp>40) alert(thals[ran]);
            if(temp<-30) alert(tlals[ran]);
            if(wspd>105) alert(wals[ran]);
            if(hum<5) alert(hlals[ran]);
            if(pressure>1045) alert(phals[ran]);
            if(pressure<950) alert(plals[ran]);
    }, 180000);
}

// Format helpers
const fmt = {
  temp: v => `${Number(v).toFixed(1)}`,
  hum:  v => `${Math.round(Number(v))}`,
  wind: v => `${Number(v).toFixed(1)}`,
  pres: v => `${Math.round(Number(v))}`
};

// Updates the 4 “metric tiles” using existing IDs (tmp, hum, wind, pres)
function updateCurrentConditions({ tempC, humidity, windKmh, pressure }) {
  setMetric('tmp',  fmt.temp(tempC));
  setMetric('hum',  fmt.hum(humidity));
  setMetric('wind', fmt.wind(windKmh));
  setMetric('pres', fmt.pres(pressure));
  setThermalTheme(tempC); // flip colors for hot/cold
}

// minimal DOM writer that respects your existing IDs
function setMetric(id, valueStr) {
  const el = document.getElementById(id);
  if (!el) return;
  const valueNode = el.querySelector('.value');
  if (valueNode) valueNode.textContent = valueStr;
}

// Flip the page theme depending on Temperatur (°C)
function setThermalTheme(tempC) {
  const hotThreshold = 28; // tweak to taste
  const coldThreshold = 12;
  const b = document.body;
  b.classList.remove('theme-hot','theme-cold');
  if (typeof tempC === 'number' && !Number.isNaN(tempC)) {
    if (tempC >= hotThreshold) b.classList.add('theme-hot');
    else if (tempC <= coldThreshold) b.classList.add('theme-cold');
  }
}

// Optional: call this whenever your sim ticks or user hits "Set"/"Update"
function syncFromInputs() {
  const get = id => parseFloat((document.getElementById(id)?.value ?? ''));
  updateCurrentConditions({
    tempC:   get('tmpi'),
    humidity:get('humi'),
    windKmh: get('windi'),
    pressure:get('presi'),
  });

  // keep your header pills in sync too, if you like
  const setText = (id,val)=>{ const n=document.getElementById(id); if(n) n.textContent = val; };
  setText('pillTmp',  fmt.temp(get('tmpi')) + ' °C');
  setText('pillHum',  fmt.hum(get('humi')) + ' %');
  setText('pillWspd', fmt.wind(get('windi')) + ' km/h');
  setText('pillPres', fmt.pres(get('presi')) + ' hPa');
}

// Wire up to your existing buttons without changing their IDs
document.getElementById('set')?.addEventListener('click', syncFromInputs);
document.getElementById('update')?.addEventListener('click', syncFromInputs);

// If your sim loop updates values programmatically, just call:
// updateCurrentConditions({ tempC: t, humidity: h, windKmh: w, pressure: p });

// ===========================================
// SIMPLE FRONT-END LOGIN + PER-USER SNAPSHOTS
// ===========================================

// LocalStorage keys
const WSIM_USERS_KEY = "wsim_users_v1";
const WSIM_CURRENT_USER_KEY = "wsim_current_user_v1";
const WSIM_USER_SNAPSHOTS_KEY = "wsim_user_snapshots_v1";

// If your chart instance is global varChart created elsewhere, we will use window.varChart.
// If not, you can manually call wsimSetChartInstance(chart) after creating it.
let wsimChartRef = null;
function wsimSetChartInstance(chart) {
  wsimChartRef = chart;
}

// ---- helpers for localStorage ----
function wsimLoadUsers() {
  const raw = localStorage.getItem(WSIM_USERS_KEY);
  return raw ? JSON.parse(raw) : {};
}

function wsimSaveUsers(users) {
  localStorage.setItem(WSIM_USERS_KEY, JSON.stringify(users));
}

function wsimGetCurrentUser() {
  const raw = localStorage.getItem(WSIM_CURRENT_USER_KEY);
  return raw ? JSON.parse(raw) : null;
}

function wsimSetCurrentUser(username) {
  localStorage.setItem(WSIM_CURRENT_USER_KEY, JSON.stringify({ username }));
}

function wsimClearCurrentUser() {
  localStorage.removeItem(WSIM_CURRENT_USER_KEY);
}

function wsimLoadUserSnapshots() {
  const raw = localStorage.getItem(WSIM_USER_SNAPSHOTS_KEY);
  return raw ? JSON.parse(raw) : {};
}

function wsimSaveUserSnapshots(data) {
  localStorage.setItem(WSIM_USER_SNAPSHOTS_KEY, JSON.stringify(data));
}

// ==================================
// REGISTER / LOGIN / LOGOUT METHODS
// ==================================

function wsimRegisterUser(username, password) {
  const users = wsimLoadUsers();

  if (!username || !password) {
    return { ok: false, message: "Please enter username and password." };
  }

  if (users[username]) {
    return { ok: false, message: "Username already exists." };
  }

  // Demo only: store raw password in localStorage.
  // For a real app, do this on a server with hashing etc.
  users[username] = { password: password };
  wsimSaveUsers(users);

  return { ok: true, message: "Registered successfully. You can now log in." };
}

function wsimLoginUser(username, password) {
  const users = wsimLoadUsers();
  const user = users[username];

  if (!user || user.password !== password) {
    return { ok: false, message: "Invalid username or password." };
  }

  wsimSetCurrentUser(username);
  return { ok: true, message: "Login successful." };
}

function wsimLogoutUser() {
  wsimClearCurrentUser();
  return { ok: true, message: "Logged out." };
}

// ==================================
// SNAPSHOT OF WEATHER + TIME + CHART
// ==================================

function wsimBuildCurrentSnapshot() {
  // Main "displayed" weather variables
  const tempSpan = document.getElementById("tmpv");
  const humSpan = document.getElementById("humv");
  const windSpan = document.getElementById("windv");
  const presSpan = document.querySelector("#pres .value");

  // Time display
  const hourP = document.getElementById("hour");
  const dayP = document.getElementById("day");

  // Input values (settings panel)
  const tmpInput = document.getElementById("tmpi");
  const humInput = document.getElementById("humi");
  const presInput = document.getElementById("presi");
  const windInput = document.getElementById("windi");
  const hourInput = document.getElementById("houri");
  const monInput = document.getElementById("moni");
  const yeInput = document.getElementById("yei");
  const titleInput = document.getElementById("titi");

  // Pills summary
  const pillTmp = document.getElementById("pillTmp");
  const pillHum = document.getElementById("pillHum");
  const pillPres = document.getElementById("pillPres");
  const pillWspd = document.getElementById("pillWspd");
  const pillRain = document.getElementById("pillRain");
  const pillTrend = document.getElementById("pillTrend");

  // Decide which chart to capture: prefer explicit ref, fallback to window.varChart
  const chartObj = wsimChartRef || window.varChart || null;

  let chartSnapshot = null;
  if (chartObj && chartObj.data) {
    chartSnapshot = {
      labels: Array.isArray(chartObj.data.labels)
        ? chartObj.data.labels.slice()
        : chartObj.data.labels,
      datasets: chartObj.data.datasets.map(ds => ({
        label: ds.label,
        data: Array.isArray(ds.data) ? ds.data.slice() : ds
