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

const dirInput = document.getElementById('dirInput');
const pressureInput  = document.getElementById('pressureInput');

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
  setInterval(()={>upChart();}, 2000);
}
mkChart();
upChartAs();
