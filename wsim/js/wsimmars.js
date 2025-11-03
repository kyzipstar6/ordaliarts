let trend =0;

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
      { label: 'Temperature', 
        data: [22,22.3,21.5], 
        borderColor: 'rgba(2, 1, 186, 1)', 
        tension: 0.25 ,
        
      }
      
    ]
  },
  options: {
    scales: {
      x: { title: { display: true, text: 'Time' } },
      y: { title: { display: true, text: 'Temperature' }}
    },
    plugins: { legend: { position: 'bottom' } }
  }
}) ;
let score = 0;
const tempT = ['Keep the temperature within 20°C and 26°C for a day'];
const humT = ['Keep the humidity within 60% and 80% for 6 hours'];
const windT = ['Do not let the wind blow over 50 km/h today'];


let inc1 =0.1; let inc2=0.005; let inc3= inc1*50; let inc4 =inc2*50;   
    inc1 = inc1*2; inc2 = inc2*2;  
let ran = Math.random();let ran2 = Math.random();let ran3 = Math.random();let ran4 = Math.random();let ran5 = Math.random();  
        let hum = 11; let temp = -30;  
        let hour = Math.ceil(ran*24);  
        let minute = Math.ceil(ran2*60);  
        let day = Math.ceil(ran3*31);  
        let month = Math.ceil(ran4*12);  
        let year = Math.ceil(ran5*4500);  let chd=0;
    function main() {  
		
        setInterval(() => {  
               
            document.getElementById("out-temp").innerText = `Temperature: ${temp.toFixed(1)} °C`;  
            document.getElementById("out-hum").innerText = `Humidity: ${hum.toFixed(1)} %`;  
          if(minute<10)  document.getElementById("hour").innerText = `Hour: ${hour}:0${minute}`;  
	       if(minute>9)  document.getElementById("hour").innerText = `Hour: ${hour}:${minute}`; 
            document.getElementById("day").innerText = `Date: ${day}/${month}/${year} `;  
            document.getElementById("out-wspd").innerText = `Wind speed: ${wspd.toFixed(1)} m/s`;  
            
            document.getElementById("out-pres").innerText = `Pressure: ${pressure.toFixed(1)} hPa`; 
            pillTmp.innerText = `${temp.toFixed(1)} °C`;  
            pillHum.innerText = `${hum.toFixed(1)} %`;
             try{ pillWspd.innerText = `${dirInput.innerText} ° ${wspd.toFixed(1)} km/h`;} catch{}
            pillPres.innerText = `${pressure.toFixed(1)} hPa`; 
             }, 1000);  
	const daysInMonth = [31,28,31,30,31,30,31,31,30,31,30,31];  let tacc = 1;
        setInterval(() => {  
            minute = minute +3*tacc;  
            if(minute >= 60){minute = 0; hour = hour +1;}  
            if(hour >= 25){hour =0; day = day + 1;}

if (day > daysInMonth[month - 1]) {
day = 1;
month++;
}
if(month >= 13){month = 1; year = year+1;}
if (hum>100) hum =89;

}, 20000);   
    
        
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
           
            if (hour >9 && hour<14){  
            temp +=0.3*tacc;
            hum -= 0.2*tacc;}  
            if (hour >7 && hour<9 || hour >14 && hour<16){  
            temp +=0.6*tacc;
            hum += 0.3*tacc;}    
            if (hour >16 && hour<22){  
            temp +=0.25*tacc;
            hum -= 0.05*tacc;}    
            if (hour >0 && hour<7 || hour >22){  
            temp -=0.45*tacc;
            hum += 0.15*tacc;}
            
			
			if(trend ==-1&&chd==0) temp-=0.6*tacc; 
			if(trend ==1&&chd==0) temp+=0.6*tacc;
			if(trend==0) temp +=0;
			
			if(trend ==-1&&chd==1) hum-=0.6*tacc; 
			if(trend ==1&&chd==1) hum+=0.6*tacc;
			if(trend==0&&chd==1) hum +=0;
        }, 60000);  
           
    }  let acumulator =0;
    let wspm = 1 + (-0.5 + Math.random()); let wspd = 7; let wspdd=7;
    let wspM =87; 
let vbl= 0;let wspeed1=0;let wspeed2=0; let quickwind =0; let filter=0; let fiterrecord=0;  
    let W_MAXmax= 122;let W_MAXmin= 4;let W_MINmax= 35;let W_MINmin= 0;  
    let UPPER_BOUND = 37;let LOWER_BOUND = 13;  
    function wind(){  
        
        setInterval(() => {  
            wspeed1= (Math.random()*200);  
			wspeed2= (Math.random()*200);  
		  
				wspm = 1 + (-0.5 + Math.random())/wspdd;
			wspd*=wspm;
      UPPER_BOUND*=wspm;
      LOWER_BOUND*=wspm;
          if (trend == 1&&chd==2) {wspm = 1 + (-0.35 + Math.random())/wspdd;}
          if (trend == -1&&chd==2) wspm = 1 + (-0.65 + Math.random())/wspdd;
          if (trend == 0) wspm = 1 + (-0.5 + Math.random())/wspdd;
           
			if( wspeed1 < UPPER_BOUND && wspeed1 > LOWER_BOUND   
					&& wspeed2 < UPPER_BOUND && wspeed2 > LOWER_BOUND && (wspeed1/wspeed2<= 0.985)  
					)  
			 filter= wspeed2;  
			filterrecord = filter;  
			if(wspeed1 < UPPER_BOUND && wspeed1 > LOWER_BOUND  && wspeed2 < UPPER_BOUND && wspeed2> LOWER_BOUND && wspeed1/filter <= 0.985  
					) {wspd = wspeed1;}  
	     }, 100);   
    }  
    function boundSetter(){  
        setInterval(() => {  
        wspeed1  = (Math.random()*200);  
		wspeed2  = (Math.random()*200);  
			  
		if (wspeed1 < W_MAXmax && wspeed1 > W_MAXmin && UPPER_BOUND/wspeed1 <= 1.3&& wspeed1 > LOWER_BOUND   
				&& (wspeed1 - UPPER_BOUND <= 7) ) { UPPER_BOUND = wspeed1; }  
		if (wspeed1 < W_MAXmax && wspeed1 > W_MAXmin && wspeed1/UPPER_BOUND <= 1.3&& wspeed1 > LOWER_BOUND   
				&& (wspeed1 + UPPER_BOUND <= 7) ) { UPPER_BOUND = wspeed1; }  
		if (wspeed2 < W_MINmax && wspeed2 > W_MINmin &&  (LOWER_BOUND/wspeed2 <= 1.3)&& UPPER_BOUND > wspeed2   
				&& (wspeed2 - LOWER_BOUND <= 7)) {LOWER_BOUND = wspeed2; }  
				if (wspeed2 < W_MINmax && wspeed2 > W_MINmin &&  (wspeed2/LOWER_BOUND <= 1.3)&& UPPER_BOUND > wspeed2   
						&& (wspeed2 + LOWER_BOUND <= 7)) {LOWER_BOUND = wspeed2;   
           }}, 4200);   
    }  
    
    let pressure = 8;  
    function presssure() {  
	if (pressure<0){pressure = 8;}  
	if (pressure>29){pressure = 29;}  
	setInterval(() => {  
		if (pressure<0){pressure = 8;}  
	if (pressure>29){pressure = 29;}  
		  
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
function stmp(){chd =0; upn('Temperature');}function shum(){chd =1;upn('Humidity');}function swspd(){chd =2;upn('Wind Speed');
			 if (trend == 1) {UPPER_BOUND+=5;LOWER_BOUND+=5;} if (trend == -1) {UPPER_BOUND-=5;LOWER_BOUND-=5;}
}function spres(){chd =3;upn('Pressure');}

function upChartAs(){
  setInterval(()=>upChart(), 2000);
}
const wals = ["It is to windy, you should make wind slower.","You are about to let buildings go flying.", "It is time to let wind cease, isn't it?"];
const thals = ["It is too hot, if you don't make temperature lower, fauna will die.", "It is too hot, if you don't make temperature lower, fauna will die.",
    "It is too hot, if you don't make temperature lower, habitants will go elsewhere."];
const tlals = ["It is too cold, if you don't make temperature higher, fauna will die.", "It is too cold, if you don't make temperature higher, fauna will die.", 
    "It is too cold, if you don't make temperature higher, habitants will go elsewhere."];
const hlals = ["The vegetation will dry out if you don't make humidity higher.", "The vegetation will dry out if you don't make humidity higher.", 
    "Who has let humidity fall so low?"];
const phals= ["The pressure is too high, you should make it lower.","The pressure is too high, you should make it lower.","The pressure is too high, you should make it lower."]
const plals= ["The pressure is too low, you should make it higher.","The pressure is too low, you should make it higher.",
    "The pressure is too low, you should make it higher."]

function wfoals(){
    let ran = (Math.random()*3).toFixed(0);
    setInterval(()=>{
            if(temp>25) alert(thals[ran]);
            if(temp<-120) alert(tlals[ran]);
            if(wspd>35) alert(wals[ran]);
            
    }, 180000);
}

// Format helpers
const fmt = {
  temp: v => `${Number(v).toFixed(1)}`,
  hum:  v => `${Math.round(Number(v))}`,
  wind: v => `${Number(v).toFixed(1)}`,
  pres: v => `${Math.round(Number(v))}`
};



  // keep your header pills in sync too, if you like
  const setText = (id,val)=>{ const n=document.getElementById(id); if(n) n.textContent = val; };
  setText('pillTmp',  fmt.temp(get('tmpi')) + ' °C');
  setText('pillHum',  fmt.hum(get('humi')) + ' %');
  setText('pillWspd', fmt.wind(get('windi')) + ' km/h');
  setText('pillPres', fmt.pres(get('presi')) + ' hPa');
}

// Wire up to your existing buttons without changing their IDsdocument.getElementById('update')?.addEventListener('click', syncFromInputs);

// If your sim loop updates values programmatically, just call:
// updateCurrentConditions({ tempC: t, humidity: h, windKmh: w, pressure: p });
tempM();  boundSetter();
wind();  
presssure();   

wfoals();
