let inc1 =0.02; let inc2=0.05; let inc3= inc1*5; let inc4 =inc2*5;   
    inc1 = inc1*2; inc2 = inc2*2;  let trend =0;

const canvas = document.getElementById('varChart');
if (!canvas) {
  console.warn('varChart canvas not found');
}
const varCtx = canvas ? canvas.getContext('2d') : null;

const varChart = varCtx ? new Chart(varCtx, {
  type: 'line',
  data: {
    labels: [],
    datasets: [
      { label: 'Temperature', data: [], borderColor: 'rgba(17,24,39,1)', tension: 0.25 },

    ]
  },
  options: {
    animation: true,
    parsing: false,
    normalized: true,
    scales: {
      x: { title: { display: true, text: 'Time' } },
      y: { title: { display: true, text: 'Temperature' }, beginAtZero: false}
    },
    plugins: { legend: { position: 'bottom' } }
  }
}) : null;


//const pill = id => document.getElementById(id);
//const pillTotal   = pill('pillTotal');
//const pillMales   = pill('pillMales');
//const pillFemales = pill('pillFemales');
//const pillYear    = pill('pillYear');
//const pillMode    = pill('pillMode');

const dirInput = document.getElementById('dirInput');
const pressureInput  = document.getElementById('pressureInput');



let temp = 20;  
        let hour = 4;  
        let minute = 54;  
        let day = 8;  
        let month = 6;  
        let year = 2026;  
    function main() {  
       setInterval(() => {  
               
            document.getElementById("tmp").innerText = `Temperature: ${temp.toFixed(1)} °C`;  
            document.getElementById("hum").innerText = `Humidity: ${hum.toFixed(1)} %`;  
          if(minute<10)  document.getElementById("hour").innerText = `Hour: ${hour}:0${minute}`;  
	       if(minute>9)  document.getElementById("hour").innerText = `Hour: ${hour}:${minute}`; 
            document.getElementById("day").innerText = `Date: ${day}/${month}/${year}`;  
            document.getElementById("wind").innerText = `Wind speed: ${wspd.toFixed(1)} km/h`;  
            document.getElementById("pres").innerText = `Pressure: ${pressure.toFixed(1)} hPa`;  
             }, 1000);  
	const daysInMonth = [31,28,31,30,31,30,31,31,30,31,30,31];  
        setInterval(() => {  
            minute = minute +3;  
            if(minute >= 60){minute = 0; hour = hour +1;}  
            if(hour >= 24){hour =0; day = day + 1;}

if (day > daysInMonth[month - 1]) {
day = 1;
month++;
}
if(month >= 13){month = 1; year = year+1;}
if (hum>100) hum =89;

}, 20000);   
          
        
    }  
    function tempM(){  
          
        if (hour > 10 && hour<18){ if(month ==1 || month==2){ temp=11;} if(month ==5 || month==8){ temp=2;}if(month ==4 || month==9){ temp=5;}  
        if(month ==3 || month==10){ temp=7;}if(month ==2 || month==11){ temp=9;}if(month ==1 || month==12){ temp=11;}}  
        if (hour < 10 || hour>18){  if(month ==6 || month==7 ){temp=-4;}if(month ==5 || month==8){ temp=-5;} if(month ==4 || month==9){ temp=-3;}  
        if(month ==3 || month==10){ temp=-1;}if(month ==2 || month==11){ temp=4;}if(month ==1 || month==12){ temp=7;}}  
        if (year > -1 && year <1000){temp = temp-2;}if (year > 1000 && year <1300){temp = temp-4;} if (year > 1300 && year <1700) { temp = temp - 2; }  if (year > 1600 && year <1700){temp = temp-3;}  
        if (year > 1700 && year <1750){temp = temp-6;}if (year > 1750 && year <1920){temp = temp-5;}if (year > 1920 && year <1950){temp = temp-4;}if (year > 1950 && year <1980){temp = temp-3;}  
        if (year > 1980 && year <2000){temp = temp-2;}if (year > 2040 && year <2100){temp = temp+2;}if (year > 2100 && year <2200){temp = temp+4;}if (year > 2200 && year <2300){temp = temp+6;}  
        if (year > 2300 && year <2400){temp = temp+7;}if (year > 2400 && year <2600){temp = temp+6;}if (year > 2600 && year <3100){temp = temp+5;}  
        if (year > 3100 && year <4000){temp = temp+3;}if (year > 4000 && year <4500){temp = temp-1;}  
        setInterval(() => {  
           
            if (hour >9 && hour<14){  
            temp +=0.1;
            hum -= 0.1;}  
            if (hour >7 && hour<9 || hour >14 && hour<16){  
            temp +=0.2;
            hum += 0.2;}    
            if (hour >16 && hour<22){  
            temp +=0.05;
            hum -= 0.05;}    
            if (hour >0 && hour<7 || hour >22){  
            temp -=0.05;
            hum += 0.05;}
            if(temp>20)temp = 19; if(temp<-30)temp = -27;
			if(trend ==-1) temp-=0.2; 
			if(trend ==1) temp+=0.2; 
        }, 60000);  
           
    }  let acumulator =0;
    let wspm = 1 + (-0.5 + Math.random()); let wspd = 34; let wspd = 7;
    let wspm =87;
    function wind(){  

        setInterval(() => {  
      wspm = 1 + (-0.5 + Math.random())/wspd;
          if (wspd>wspm)wspm = 1 + (-0.4 + Math.random())/wspd;
          if (wspd<0.1) wspd = 5;
          wspd*=wspm;
          if (hour >0 && hour<7 || hour >22) wspm =65;
          if (hour >7 && hour<12) wspm= 75;
          if (hour >12 && hour<18) wspm =87;
          if (hour >18 && hour<22) wspm =72;
           }, 1000);   
    }  }
    let pressure = 1023 *(1+ (-0.5+ Math.random()));  
    function presssure() {  
	if (pressure<977){pressure = 977;}  
	if (pressure>1029){pressure = 1029;}  
	setInterval(() => {  
		if (pressure<977){pressure = 977;}  
	if (pressure>1029){pressure = 1029;}  
		  
		if (hour >9 && hour<14){  
            pressure = pressure +0.1;;  
           }  
            if (hour >7 && hour<9 || hour >14 && hour<16){  
            pressure = pressure +0.04;  
            }  
            if (hour >16 && hour<22){  
            pressure = pressure -0.05;  
            }  
            if (hour >-1 && hour<7 || hour >22){  
            pressure = pressure -0.1;  
            }  
    if (acumulator>36000*6) acumulator = 0;
	},444);  
	
	  
}  
let title = "";

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
function rise(){ trend = 1;  updatePills('grow'); }
function steady(){ trend = 0;  updatePills('steady'); }
function fall(){ trend=-1; } 

function mkChart(){
  if(!varChart){
    varChart.data.labels.length = 0;                 // clear safely
    varChart.data.datasets[0].data.length = 0;
    varChart.update();
  }
}
let counter = 1;
function upChart(){
  if(varChart){
  varChart.data.labels.push(counter);                 // clear safely
    varChart.data.datasets[0].data.push(temp);
    varChart.update();
  counter +=1;}
}
function upChartAs(){
  setInterval(()=>upChart(), 2000);
}
mkChart();
upChartAs();
tempM();   
wind();  
 presssure();   
