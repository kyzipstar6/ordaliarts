let trend =0; let rain = 0;let pressure = 1023 *(1+ (-0.5+ Math.random()));   let temp = 5;  
let tacc=1; let mintemp= temp; let maxtemp= temp; let maxspd=0; let dir=180; let dirl ="N";
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
      { label: 'Температура (°C)', 
        data: [], 
        borderColor: 'rgba(123, 38, 2, 1)', 
        tension: 0.25 ,
        
      }
      
    ]
  },
  options: {
    scales: {
      x: { title: { display: true, text: 'Time' } },
      y: { title: { display: true, text: 'Температура' }}
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
        let hum = 77*(0.5+ (Math.random()-0.5));
        let hour = Math.ceil(ran*24);  
        let minute = Math.ceil(ran2*60);  
        let day = Math.ceil(ran3*31);  
        let month = Math.ceil(ran4*12);  
        let year = Math.ceil(ran5*4500);  let chd=0; let srise =7; let sset=19;
    function main() {  
		
        if (hour > 10 && hour<18){ if(month ==6 || month==7 ){ temp=16.6;} if(month ==5 || month==8){ temp=14;}if(month ==4 || month==9){ temp=12.1;}  
        if(month ==3 || month==10){ temp=4.5;}if(month ==2 || month==11){ temp=-0.8;}if(month ==1 || month==12){ temp=-2.1;}}  
        if (hour < 10 || hour>18){  if(month ==6 || month==7 ){temp=11.1;}if(month ==5 || month==8){ temp=9.6;} if(month ==4 || month==9){ temp=4.4;}  
        if(month ==3 || month==10){ temp=1.2;}if(month ==2 || month==11){ temp=-6.3;}if(month ==1 || month==12){ temp=-8.1;}}  
        if (year > -1 && year <1000){temp = temp-2;}if (year > 1000 && year <1300){temp = temp-4;} if (year > 1300 && year <1700) { temp = temp - 2; }  if (year > 1600 && year <1700){temp = temp-3;}  
        if (year > 1700 && year <1750){temp = temp-6;}if (year > 1750 && year <1920){temp = temp-5;}if (year > 1920 && year <1950){temp = temp-4;}if (year > 1950 && year <1980){temp = temp-3;}  
        if (year > 1980 && year <2000){temp = temp-2;}if (year > 2040 && year <2100){temp = temp+2;}if (year > 2100 && year <2200){temp = temp+4;}if (year > 2200 && year <2300){temp = temp+6;}  
        if (year > 2300 && year <2400){temp = temp+7;}if (year > 2400 && year <2600){temp = temp+6;}if (year > 2600 && year <3100){temp = temp+5;}  
        if (year > 3100 && year <4000){temp = temp+3;}if (year > 4000 && year <4500){temp = temp-1;}  
        mintemp = temp; maxtemp = temp;
		inc1=(month<4 ||month>10) ? 0.02 : 0.04;
		inc2= inc1/2;
		hum = (hour>20)?70:50; hum = (hour<9)?84:(hour>9&&hour<14)?72: hum;
		hum =(month>5&&month<9) ? 45 : hum;
       setInterval(() => {  
               srise = 7 - (month-6)/6*2; sset = 19 + (month-6)/6*2;
            document.getElementById("tmpv").innerText = `${temp.toFixed(1)}`;  
            document.getElementById("humv").innerText = `${hum.toFixed(1)}`;  
          if(minute<10)  document.getElementById("hour").innerText = `Часы: ${hour}:0${minute}`;  
	       if(minute>9)  document.getElementById("hour").innerText = `Часы: ${hour}:${minute}`; 
            document.getElementById("day").innerText = `Дата: ${day}/${month}/${year} `;  
            document.getElementById("windv").innerText = `${dirl}  ${wspd.toFixed(1)}`;  
            
           if(minute>9)  document.getElementById("presv").innerText = `${pressure.toFixed(1)}`; 
           
		   if (hour >9 && hour<14){  
            temp +=inc1*tacc;
            hum -= 0.1*tacc;}  
            if (hour >srise && hour<9 || hour >11 && hour<16){  
            temp +=inc1*tacc;
            hum -= 0.2*tacc;}    
            if (hour >16 && hour<sset){  
            temp +=inc2*tacc;
            hum -= 0.05*tacc;}  
            if (hour >sset-2 && hour<22){  
            temp -=inc2*tacc;
            hum += 0.1*tacc;}  
            if (hour >0 && hour<srise || hour >22){  
            temp -=inc1*tacc;
            hum += 0.2*tacc;}
            temp*=1 + (-0.5 + Math.random())/20;
			 hum*=1 + (-0.5 + Math.random())/40
			if(trend ==-1&&chd==0) temp-=0.2*tacc; 
			if(trend ==1&&chd==0) temp+=0.2*tacc;
			if(trend==0) temp +=0;
			
			if(trend ==-1&&chd==1) hum-=0.2*tacc; 
			if(trend ==1&&chd==1) hum+=0.2*tacc;
			if(trend==0&&chd==1) hum +=0;
      mintemp = (temp<mintemp) ? temp : (hour==23 && minute>45) ? temp : mintemp;
      maxtemp = (temp>maxtemp) ? temp : (hour==23 && minute>45) ? temp : maxtemp;
		   document.getElementById("presv").innerText= `${pressure.toFixed(1)}`;
		   document.getElementById("rainv").innerText = `${rain.toFixed(1)}`;
       document.getElementById("h-tmpv").innerText = `${maxtemp.toFixed(1)}`;
       document.getElementById("l-tmpv").innerText = `${mintemp.toFixed(1)}`;
        document.getElementById("h-wspdv").innerText = `${maxspd.toFixed(1)}`;
		    pillTmp.innerText = `${temp.toFixed(1)} °C`;  
            pillHum.innerText = `${hum.toFixed(1)} %`;
             try{ pillWspd.innerText = `${dir.toFixed(0)} ° ${wspd.toFixed(1)} km/h`;} catch{}
            pillPres.innerText = `${pressure.toFixed(1)} hPa`; 
            pillRain.innerText = `${rain.toFixed(1)} mm`;
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
if (hum>100) hum =89

}, 5000);   
    
        
    }  
    function tempM(){  
          
        if (hour > 10 && hour<18){ if(month==2){ temp=-13.4;} if(month ==5){ temp=11.8;}if(month ==4 ){temp=-1.2;}if ( month==9){ temp=9.6;}  
        if ( month==6){ temp=19.6;} if ( month==7){ temp=22.6;} if ( month==8){ temp=20.5;}    if(month==10){ temp=2.5;}if(month==3){ temp=-5.5;}if(month==11){ temp=-4.5;}if(month ==1 || month==12){ temp=-12.2;}}  
        if (hour < 10 || hour>18){ if(month==2){ temp=-18.4;} if(month ==5){ temp=7.4;}if(month ==4 ){temp=-6;}if ( month==9){ temp=4.4;}  
        if ( month==6){ temp=13.1;} if ( month==7){ temp=17.9;} if ( month==8){ temp=15.9;}    if(month==10){ temp=-2.2;}if(month==3){ temp=-9.1;}if(month==11){ temp=-9.5;}if(month ==1 || month==12){ temp=-15.7;}}  
        if (year > -1 && year <1000){temp = temp-2;}if (year > 1000 && year <1300){temp = temp-4;} if (year > 1300 && year <1700) { temp = temp - 2; }  if (year > 1600 && year <1700){temp = temp-3;}  
        if (year > 1700 && year <1750){temp = temp-6;}if (year > 1750 && year <1920){temp = temp-5;}if (year > 1920 && year <1950){temp = temp-4;}if (year > 1950 && year <1980){temp = temp-3;}  
        if (year > 1980 && year <2000){temp = temp-2;}if (year > 2040 && year <2100){temp = temp+2;}if (year > 2100 && year <2200){temp = temp+4;}if (year > 2200 && year <2300){temp = temp+6;}  
        if (year > 2300 && year <2400){temp = temp+7;}if (year > 2400 && year <2600){temp = temp+6;}if (year > 2600 && year <3100){temp = temp+5;}  
        if (year > 3100 && year <4000){temp = temp+3;}if (year > 4000 && year <4500){temp = temp-1;}  
        setInterval(() => {  
           
            
        }, 60000);  
           
    }  let acumulator =0;
    let wspm = 1 + (-0.5 + Math.random()); let wspd = 25; let wspdd=8;
    let wspM =107; 
let vbl= 0;
    
    function windrain(){  
      maxspd = wspd;
      if (hour<7||hour >19){
		  wspd= (month==1 || month==12)? 54 : (month==2) ? 61 : (month ==3) ? 64 : (month==4) ? 40 : 25;
	  }
        setInterval(() => {  
      wspm = 1 + (-0.5 + Math.random())/wspdd;
                dir*= 1 + (-0.5 + Math.random())/30;
                dir = (dir<0.6) ? 359  : dir>359.6 ? 1 : dir;
                dir = (dir<360 && dir>269 && month<10 && month>4) ? dir*( 1 + (-0.6 + Math.random())/30) : 
                (dir<112 && dir>0 && month<10 && month>4) ? dir*( 1 + (-0.4 + Math.random())/40) 
                :(dir<269 && dir>190 && (month>9 || month<5)) ? dir*( 1 + (-0.4 + Math.random())/30): 
                (dir<190 && dir>105 && (month>9 || month<5)) ? dir*( 1 + (-0.6 + Math.random())/30):dir;

               dirl = (dir>337.5 || dir<22.5) ? "N" : (dir>22.5 && dir<67.5) ? "NE" : (dir>67.5 && dir<112.5) ? "E" : (dir>112.5 && dir<157.5) ? "SE" : 
                (dir>157.5 && dir<202.5) ? "S" : (dir>202.5 && dir<247.5) ? "SW" : (dir>247.5 && dir<292.5) ? "W" : "NW";
		 wspm = 1 + (-0.5 + Math.random())/wspdd;
          if (trend == 1&&chd==2) wspm = 1 + (-0.65 + Math.random())/wspdd;
          if (trend == -1&&chd==2) wspm = 1 + (-0.35 + Math.random())/wspdd;
          if (trend == 0) wspm = 1 + (-0.5 + Math.random())/wspdd;
          if (wspd<0.1) wspd = 5;
			if (wspd>wspM)wspm = 1 + (-0.7 + Math.random())/wspdd;
          wspd*=wspm;
          if(wspd < 20 && wspd>10 && temp<24 && pressure<1010 && hum>85)  rain+=0.12;
          if(hour>22&& minute>50) rain =0;
          if (hour >0 && hour<7 || hour >22) wspM =15;
          if (hour >7 && hour<12) wspM= 40;
          if (hour >12 && hour<18) wspM =52;
          if (hour >18 && hour<22) wspM =22;
          maxspd = (wspd>maxspd) ? wspd : (hour==23 && minute>45) ? wspd : maxspd;
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
function rise(){ trend = 1;  updatePills('рост'); }
function steady(){ trend = 0;  updatePills('устойчивый'); }
function fall(){ trend=-1; updatePills('падать');} 

let counter = 1;
function mkChart(){
  if(!varChart){
    varChart.data.labels.length = 0;                 
    varChart.data.datasets[0].data.push(temp);
    varChart.update();
  }
}

 let chdmem= chd;
function upChart(){
  if(varChart){
        if(minute<10)  varChart.data.labels.push(`${hour}:0${minute}`);  
         if(minute>9)  varChart.data.labels.push(`${hour}:${minute}`);  
     
  
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
        data: Array.isArray(ds.data) ? ds.data.slice() : ds.data,
        borderWidth: ds.borderWidth,
        borderColor: ds.borderColor,
        backgroundColor: ds.backgroundColor,
        tension: ds.tension,
        fill: ds.fill
      }))
    };
  }

  return {
    savedAt: new Date().toISOString(),

    display: {
      Temperatur: tempSpan ? tempSpan.textContent : null,
      humidity: humSpan ? humSpan.textContent : null,
      wind: windSpan ? windSpan.textContent : null,
      pressure: presSpan ? presSpan.textContent : null
    },

    time: {
      hourText: hourP ? hourP.textContent : null,
      dateText: dayP ? dayP.textContent : null
    },

    inputs: {
      tmpi: tmpInput ? tmpInput.value : null,
      humi: humInput ? humInput.value : null,
      presi: presInput ? presInput.value : null,
      windi: windInput ? windInput.value : null,
      houri: hourInput ? hourInput.value : null,
      moni: monInput ? monInput.value : null,
      yei: yeInput ? yeInput.value : null,
      title: titleInput ? titleInput.value : null
    },

    pills: {
      temp: pillTmp ? pillTmp.textContent : null,
      hum: pillHum ? pillHum.textContent : null,
      pres: pillPres ? pillPres.textContent : null,
      wind: pillWspd ? pillWspd.textContent : null,
      rain: pillRain ? pillRain.textContent : null,
      trend: pillTrend ? pillTrend.textContent : null
    },

    chart: chartSnapshot
  };
}

// Save one snapshot for the current user
function wsimSaveCurrentUserSnapshot() {
  const current = wsimGetCurrentUser();
  if (!current || !current.username) {
    return { ok: false, message: "You need to be logged in to save." };
  }

  const username = current.username;
  const allSnapshots = wsimLoadUserSnapshots();
  if (!allSnapshots[username]) {
    allSnapshots[username] = [];
  }

  const snapshot = wsimBuildCurrentSnapshot();
  allSnapshots[username].push(snapshot);

  wsimSaveUserSnapshots(allSnapshots);
  return { ok: true, message: "Weather + chart data saved for user." };
}

// Optional helper: get all snapshots for current user
function wsimGetCurrentUserSnapshots() {
  const current = wsimGetCurrentUser();
  if (!current || !current.username) return [];
  const allSnapshots = wsimLoadUserSnapshots();
  return allSnapshots[current.username] || [];
}

// ======================================
// WIRE EVERYTHING TO THE NEW HTML BUTTONS
// ======================================

document.addEventListener("DOMContentLoaded", function () {
  const usernameInput = document.getElementById("auth-username");
  const passwordInput = document.getElementById("auth-password");
  const btnRegister = document.getElementById("btn-register");
  const btnLogin = document.getElementById("btn-login");
  const btnLogout = document.getElementById("btn-logout");
  const btnSaveSession = document.getElementById("btn-save-session");
  const statusSpan = document.getElementById("auth-status");
  const userPanel = document.getElementById("user-panel");
  const currentUserSpan = document.getElementById("current-username");

  function updateAuthUI() {
    const current = wsimGetCurrentUser();
    if (current && current.username) {
      if (userPanel) userPanel.style.display = "flex";
      if (currentUserSpan) currentUserSpan.textContent = current.username;
    } else {
      if (userPanel) userPanel.style.display = "none";
      if (currentUserSpan) currentUserSpan.textContent = "";
    }
  }

  function setStatus(msg) {
    if (statusSpan) statusSpan.textContent = msg || "";
  }

  if (btnRegister) {
    btnRegister.addEventListener("click", function () {
      const u = (usernameInput && usernameInput.value.trim()) || "";
      const p = (passwordInput && passwordInput.value) || "";
      const res = wsimRegisterUser(u, p);
      setStatus(res.message);
    });
  }

  if (btnLogin) {
    btnLogin.addEventListener("click", function () {
      const u = (usernameInput && usernameInput.value.trim()) || "";
      const p = (passwordInput && passwordInput.value) || "";
      const res = wsimLoginUser(u, p);
      setStatus(res.message);
      updateAuthUI();
    });
  }

  if (btnLogout) {
    btnLogout.addEventListener("click", function () {
      const res = wsimLogoutUser();
      setStatus(res.message);
      updateAuthUI();
    });
  }

  if (btnSaveSession) {
    btnSaveSession.addEventListener("click", function () {
      const res = wsimSaveCurrentUserSnapshot();
      setStatus(res.message);
    });
  }

  // Initial UI state
  updateAuthUI();
});
tempM();  
windrain();  
presssure();   
