let inc1 =0.007; let inc2=0.01;
    let ran = Math.random();let ran2 = Math.random();let ran3 = Math.random();let ran4 = Math.random();let ran5 = Math.random();
            let hum = 77; let temp = 20;
            let hour = Math.ceil(ran*24);
            let minute = Math.ceil(ran2*60);
            let day = Math.ceil(ran3*31);
            let month = Math.ceil(ran4*12);
            let year = Math.ceil(ran5*4500);
        function main() {
           setInterval(() => {
                 
                document.getElementById("tmp").innerText = `Temperature: ${temp.toFixed(2)} °C`;
                document.getElementById("hum").innerText = `Humidity: ${hum.toFixed(2)} %`;
                document.getElementById("hour").innerText = `Hour: ${hour}:${minute}`;
                document.getElementById("day").innerText = `Date: ${day}/${month}/${year}`;
                document.getElementById("wind").innerText = `Wind speed: ${quickwind.toFixed(2)} km/h`;
                document.getElementById("pres").innerText = `Pressure: ${pressure.toFixed(2)} hPa`;
            }, 1000);
            setInterval(() => {
                minute = minute +1;
                if(minute >= 60){minute = 0; hour = hour +1;}
                if(hour >= 24){hour =0; day = day + 1;}
                if(day >= 31){day = 1; month = month+1;}
                if(month >= 13){month = 1; year = year+1;}
            }, 60000); 
            
            tempM(); 
            wind();
            presssure(); 
        }
        function tempM(){
            
            if (hour > 10 && hour<18){ if(month ==6 ){temp = 27;}if (month==7 ){ temp=29;} if(month ==5){temp =24;} if( month==8){ temp=28;}if(month ==4 || month==9){ temp=25.5;}
            if(month ==3 || month==10){ temp=13;}if( month==11){ temp=5;}if(month ==1 || month==12){ temp=-2;} if (month==2){temp= -1;}}
            if (hour < 10 || hour>18){  if(month ==6 ){temp = 22;}if (month==7 ){ temp=24;} if(month ==5){temp =19;} if( month==8){ temp=22;}if(month ==4 || month==9){ temp=19.5;}
            if(month ==3 || month==10){ temp=5;}if( month==11){ temp=-2;}if(month ==1 || month==12){ temp=-8;} if (month==2){temp= -7;}}
            if (year > -1 && year <1000){temp = temp-2;}if (year > 1000 && year <1300){temp = temp-4;} if (year > 1700 && year <1300){temp = temp-2;}  if (year > 1600 && year <1700){temp = temp-3;}
            if (year > 1700 && year <1750){temp = temp-6;}if (year > 1750 && year <1920){temp = temp-5;}if (year > 1920 && year <1950){temp = temp-4;}if (year > 1950 && year <1980){temp = temp-3;}
            if (year > 1980 && year <2000){temp = temp-2;}if (year > 2040 && year <2100){temp = temp+2;}if (year > 2100 && year <2200){temp = temp+4;}if (year > 2200 && year <2300){temp = temp+6;}
            if (year > 2300 && year <2400){temp = temp+7;}if (year > 2400 && year <2600){temp = temp+6;}if (year > 2600 && year <3100){temp = temp+5;}
            if (year > 3100 && year <4000){temp = temp+3;}if (year > 4000 && year <4500){temp = temp-1;}
            setInterval(() => {
                inc1 = inc1 *(1+ (-0.5 + Math.random()));
                inc2 = inc2 *(1+ (-0.5 + Math.random()));
                if (hour >9 && hour<14){
                temp = (((Math.round((temp +inc1)*10))))/10;
                hum = ((Math.round(((hum -inc1*2)*10))))/10;}
                if (hour >7 && hour<9 || hour >14 && hour<16){
                temp = (((Math.round((temp +inc2)*10))))/10;
                hum = ((Math.round(((hum -inc2*2)*10))))/10;}
                if (hour >16 && hour<22){
                temp = (((Math.round((temp -inc2)*10))))/10;
                hum = ((Math.round(((hum +inc2*2)*10))))/10;}
                if (hour >0 && hour<7 || hour >22){
                temp = (((Math.round((temp -inc1/2)*10))))/10;
                hum = ((Math.round(((hum -inc1*2)*10))))/10;}
            }, 1000);
             
        }
        let wspeed1=0;let wspeed2=0; let quickwind =0; let filter=0; let fiterrecord=0;
        let W_MAXmax= 122;let W_MAXmin= 4;let W_MINmax= 45;let W_MINmin= 0;
        let UPPER_BOUND = 25;let LOWER_BOUND = 7;
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
          if(month >4 && month >10){UPPER_BOUND = UPPER_BOUND/2; LOWER_BOUND = LOWER_BOUND/2;}
          
            setInterval(() => {
            wspeed1  = (Math.random()*200);
			wspeed2  = (Math.random()*200);
if(month >4 && month >10 && hour>12 && hour<20){UPPER_BOUND = UPPER_BOUND*1.6; LOWER_BOUND = LOWER_BOUND*1.6;}
      if(month >4 && month >10 &&( hour<12 || hour<20)){UPPER_BOUND = UPPER_BOUND/1.6; LOWER_BOUND = LOWER_BOUND/1.6;}
				
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
			
			
			inc1 = inc1 *(1+ (-0.5 + Math.random()));
                inc2 = inc2 *(1+ (-0.5 + Math.random()));
                if (hour >9 && hour<14){
                pressure = (temp +inc1);
               }
                if (hour >7 && hour<9 || hour >14 && hour<16){
                pressure = pressure +inc2
                }
                if (hour >16 && hour<22){
                pressure = pressure -inc2/2;
                }
                if (hour >-1 && hour<7 || hour >22){
                pressure = pressure -inc1;
                }
		},444);
		setInterval(() => {
			if(quickwind>30) {pressure = pressure - 0.01;}
		},222*100,);
		
		
	}
    let title = "";
function getText(){
    if (document.getElementById("tmpi").value !=""){ temp = parseFloat(document.getElementById("tmpi").value.toFixed);}
    if (document.getElementById("humi").value!=""){hum = parseFloat(document.getElementById("humi").value);}
    if (document.getElementById("presi").value!=""){pressure = parseFloat(document.getElementById("presi").value);}
    if (document.getElementById("windi").value!=""){quickwind = parseFloat(document.getElementById("windi").value);}
    if (document.getElementById("houri").value!=""){hour = parseFloat(document.getElementById("houri").value);}
    if (document.getElementById("moni").value!=""){month = parseFloat(document.getElementById("moni").value);}
    if (document.getElementById("yei").value!=""){year = parseFloat(document.getElementById("yei").value);}
    if (document.getElementById("titi").value!=""){
        title = document.getElementById("titi").value;
        document.getElementById("cucnh").innerText= ` ${title} `;
    }
}
const tempCtx = document.getElementById('tempChart').getContext('2d');
const tempChart = new Chart(tempCtx, {
  type: 'line',
  data: {
    labels: [],
    datasets: [{
      label: 'Temperature °C',
      data: [],
      borderColor: 'rgb(255, 99, 132)',
      tension: 0.3
    }]
  },
  options: {
    scales: {
      x: { title: { display: true, text: 'Time' }},
      y: { title: { display: true, text: '°C' }}
    }
  }
});

let time = 0;
function updateData() {
  const temp = (temp).toFixed(1);
  const hum = (hum).toFixed(1);
  const wind = (quickwind).toFixed(1);
  const pres = (pres).toFixed(1);

  document.getElementById("tmp").innerText = `Temperature: ${temp} °C`;
  document.getElementById("hum").innerText = `Humidity: ${hum} %`;
  document.getElementById("wind").innerText = `Wind Speed: ${wind} km/h`;
  document.getElementById("pres").innerText = `Pressure: ${pres} hPa`;

  // Add to chart
  tempChart.data.labels.push(`${time}s`);
  tempChart.data.datasets[0].data.push(temp);
  tempChart.update();
  time += 5;
}

setInterval(updateData, 5000);
        