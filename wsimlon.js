 let inc1 =0.01; let inc2=0.005; let inc3= inc1*50; let inc4 =inc2*50;   
    inc1 = inc1*2; inc2 = inc2*2;  
let ran = Math.random();let ran2 = Math.random();let ran3 = Math.random();let ran4 = Math.random();let ran5 = Math.random();  
        let hum = 77*(0.5+ (Math.random()-0.5)); let temp = 20;  
        let hour = Math.ceil(ran*24);  
        let minute = Math.ceil(ran2*60);  
        let day = Math.ceil(ran3*31);  
        let month = Math.ceil(ran4*12);  
        let year = Math.ceil(ran5*4500);  
    function main() {  
       setInterval(() => {  
               
            document.getElementById("tmp").innerText = `Temperature: ${temp} °C`;  
            document.getElementById("hum").innerText = `Humidity: ${hum} %`;  
            document.getElementById("hour").innerText = `Hour: ${hour}:${minute}`;  
            document.getElementById("day").innerText = `Date: ${day}/${month}/${year}`;  
            document.getElementById("wind").innerText = `Wind speed: ${(Math.round(quickwind*10))/10} km/h`;  
            document.getElementById("pres").innerText = `Pressure: ${((pressure*10))/10} hPa`;  
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
          
        tempM();   
        wind();  
        presssure();   
    }  
    function tempM(){  
          
        if (hour > 10 && hour<18){ if(month ==1 || month==2){ temp=7;} if(month ==5 || month==8){ temp=18;}if(month ==4 || month==9){ temp=14;}  
        if(month ==3 || month==10){ temp=15;}if(month ==2 || month==11){ temp=10;}if(month ==1 || month==12){ temp=6;}}  
        if (hour < 10 || hour>18){  if(month ==6 || month==7 ){temp=14;}if(month ==5 || month==8){ temp=12;} if(month ==4 || month==9){ temp=9;}  
        if(month ==3 || month==10){ temp=5;}if(month ==2 || month==11){ temp=3;}if(month ==1 || month==12){ temp=2;}}  
        if (year > -1 && year <1000){temp = temp-2;}if (year > 1000 && year <1300){temp = temp-4;} if (year > 1300 && year <1700) { temp = temp - 2; }  if (year > 1600 && year <1700){temp = temp-3;}  
        if (year > 1700 && year <1750){temp = temp-6;}if (year > 1750 && year <1920){temp = temp-5;}if (year > 1920 && year <1950){temp = temp-4;}if (year > 1950 && year <1980){temp = temp-3;}  
        if (year > 1980 && year <2000){temp = temp-2;}if (year > 2040 && year <2100){temp = temp+2;}if (year > 2100 && year <2200){temp = temp+4;}if (year > 2200 && year <2300){temp = temp+6;}  
        if (year > 2300 && year <2400){temp = temp+7;}if (year > 2400 && year <2600){temp = temp+6;}if (year > 2600 && year <3100){temp = temp+5;}  
        if (year > 3100 && year <4000){temp = temp+3;}if (year > 4000 && year <4500){temp = temp-1;}  
        setInterval(() => {  
            inc1 = inc1/10 *(1+ (-0.5 + Math.random()));  
            inc2 = inc2/10 *(1+ (-0.5 + Math.random()));  
            if (hour >9 && hour<14){  
            temp = (((Math.round((temp +inc1)*10))))/10;  
            hum = (((Math.round((hum -inc1*20)*10))))/10;}  
            if (hour >7 && hour<9 || hour >14 && hour<16){  
            temp = (((Math.round((temp +inc2)*10))))/10;  
            hum = ((Math.round(((hum -inc2*20)*10))))/10;}  
            if (hour >16 && hour<22){  
            temp = (((Math.round((temp -inc2/2)*10))))/10;  
            hum = (((Math.round((hum +inc2*20)*10))))/10;}  
            if (hour >0 && hour<7 || hour >22){  
            temp = (((Math.round((temp -inc1)*10))))/10;  
            hum = (((Math.round((hum -inc1*20)*10))))/10;}  
            
        }, 100);  
           
    }  
    let wspeed1=0;let wspeed2=0; let quickwind =0; let filter=0; let fiterrecord=0;  
    let W_MAXmax= 122;let W_MAXmin= 4;let W_MINmax= 35;let W_MINmin= 0;  
    let UPPER_BOUND = 17;let LOWER_BOUND = 3;  
    function wind(){  
        boundSetter();  
        setInterval(() => {  
            wspeed1= (Math.random()*200);  
			wspeed2= (Math.random()*200);  
		  
				  
			if( wspeed1 < UPPER_BOUND && wspeed1 > LOWER_BOUND   
					&& wspeed2 < UPPER_BOUND && wspeed2 > LOWER_BOUND && (wspeed1/wspeed2<= 0.985)  
					)  
			 filter= wspeed2;  
			filterrecord = filter;  
			if(wspeed1 < UPPER_BOUND && wspeed1 > LOWER_BOUND  && wspeed2 < UPPER_BOUND && wspeed2> LOWER_BOUND && wspeed1/filter <= 0.985  
					) {quickwind = wspeed1;}  
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
    let pressure = 1023 *(1+ (-0.5+ Math.random()));  
    function presssure() {  
	if (pressure<997){pressure = 997;}  
	if (pressure>1029){pressure = 1029;}  
	setInterval(() => {  
		  
		  
		inc3 = inc3 *(1+ (-0.5 + Math.random())*3);  
            inc4 = inc4 *(1+ (-0.5 + Math.random())*3);  
            if (hour >9 && hour<14){  
            pressure = ((((Math.round(pressure +inc3)*10))))/10;  
           }  
            if (hour >7 && hour<9 || hour >14 && hour<16){  
            pressure = ((((Math.round(pressure +inc4)*10))))/10;  
            }  
            if (hour >16 && hour<22){  
            pressure = ((((Math.round(pressure -inc4/2)*10))))/10;  
            }  
            if (hour >-1 && hour<7 || hour >22){  
            pressure = ((((Math.round(pressure -inc3)*10))))/10;  
            }  
	},444);  
	setInterval(() => {  
		if(quickwind>30) {pressure = pressure - 0.01;}  
	},222*100);  
	  
	  
}  
let title = "";

function getText(){
if (document.getElementById("tmpi").value !=""){ temp = parseFloat(document.getElementById("tmpi").value);}
if (document.getElementById("humi").value!=""){hum = parseFloat(document.getElementById("humi").value);}
if (document.getElementById("presi").value!=""){pressure = parseFloat(document.getElementById("presi").value);}
if (document.getElementById("windi").value!=""){quickwind = parseFloat(document.getElementById("windi").value);}
if (document.getElementById("houri").value!=""){hour = parseFloat(document.getElementById("houri").value);}
if (document.getElementById("moni").value!=""){month = parseFloat(document.getElementById("moni").value);}
if (document.getElementById("yei").value!=""){year = parseFloat(document.getElementById("yei").value);}
if (document.getElementById("titi").value!=""){
title = document.getElementById("titi").value;
}
}
