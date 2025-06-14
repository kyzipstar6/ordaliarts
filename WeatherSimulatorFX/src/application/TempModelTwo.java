package application;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import javafx.scene.chart.XYChart.Data;

//mean to be an improved version of modelOne, with desert and other climates version

public class TempModelTwo {
	static double starttemp;
	static double starthum = 20;
	static double randomsettemp;
	static double randomsethum;
	static double temp1 = 15.9;
	static double temp2 = 14.7;
	static double hum1 = 60;
	static List<Double>  temprec = new ArrayList<>();
	static double hum2 = 80;
	static double temp3 = 14.7;static double upperbound = 40;static double lowerbound = -20;
	static double secondrecord;
	static double secondhumrecord;
	private static int modelstarted=0;
	static ScheduledExecutorService modelScheduler = Executors.newScheduledThreadPool(1);
	static ScheduledExecutorService hourChecker = Executors.newScheduledThreadPool(2);
	static ScheduledExecutorService temp = Executors.newScheduledThreadPool(2);
	static Future<?> currentModelTask = null;
	static boolean isDayRunning = false;
	
	private static LocalDateTime time = LocalDateTime.now();
	static int hour = 1;static int minute = 1;static int day = 1;static int month = 1;private static int year = 1;
	public static int desertindex;
	//=3 desert, ==0 jungle or oceanic 
	
	public static void main(String[] args) {
		modelTwo(12,1);

	}
	static double tinc = 0.001; static double hinc = 0.001;
	public static void modelTwo(double StartingTemperature, int desert_index) {
		GeneralControls.writingindex= 3;
		tempmodels.tempmodelind=2;
		temp = Executors.newScheduledThreadPool(4);
		desertindex = desert_index;
		starttemp = StartingTemperature;
		randomsettemp = starttemp;
		if(desertindex == 3)randomsethum = starthum*(0.7 +Math.random());
		if(desertindex == 2)randomsethum = (starthum+20)*(0.7 +Math.random());
		if(desertindex < 2)randomsethum = (starthum+30)*(0.7 +Math.random());
		if(randomsethum > 90 && desertindex == 2 ) randomsethum = 70;
		//Works OK
		temp1= randomsettemp;
		hum1 = randomsethum;
		
		
		
		temp.scheduleAtFixedRate(() -> {
			if (windliveplot.wind ==1) {
				//No rain day
			if((RandomWindModels.quickwind<20 || Wind.measures<20 )&& CentralClock.hour >CentralClock.rise 
					&& CentralClock.hour<CentralClock.set && Rain.rained ==0 &&Atmosphere.clouds<3) {temp1 = temp1 +(tinc/1.3);hum1 = hum1- 0.0005;}
			if((RandomWindModels.quickwind<20 || Wind.measures<20 )&& CentralClock.hour >CentralClock.rise 
					&& CentralClock.hour<CentralClock.set && Rain.rained ==0&&Atmosphere.clouds==3) {temp1 = temp1 +(tinc/2);hum1 = hum1- 0.0003;}
			if((RandomWindModels.quickwind<20 || Wind.measures<20 )&&  CentralClock.hour >CentralClock.rise 
					&& CentralClock.hour<CentralClock.set  && Rain.rained ==0&&Atmosphere.clouds>3) {temp1 = temp1 +(tinc/4);hum1 = hum1- 0.00005;}
			if((RandomWindModels.quickwind<20 || Wind.measures<20 ) && CentralClock.hour >CentralClock.rise 
					&& CentralClock.hour<CentralClock.set  && Rain.rained ==1&&Atmosphere.clouds==5) {temp1 = temp1 -(tinc/2);hum1 = hum1+ hinc/2;}
			
			//No rain night
			if((RandomWindModels.quickwind<20  || Wind.measures<20)&& CentralClock.hour >CentralClock.set && 
					CentralClock.hour<CentralClock.rise && Rain.rained ==0) {temp1 = temp1 -0.001;hum1 = hum1+ 0.0005;}
			if((RandomWindModels.quickwind<20 || Wind.measures<20)&& CentralClock.hour >CentralClock.set && 
					CentralClock.hour<CentralClock.rise  && Rain.rained ==0) {temp1 = temp1 -0.0005;hum1 = hum1+ 0.0003;}
			if((RandomWindModels.quickwind<20 || Wind.measures<20)&& CentralClock.hour >CentralClock.set && 
					CentralClock.hour<CentralClock.rise  && Rain.rained ==0) {temp1 = temp1 -0.0005;hum1 = hum1+ 0.0001;}
			}
			
				
					
		},0, 1000, TimeUnit.MILLISECONDS);
		temp.scheduleAtFixedRate(() -> {	
			double random = Math.random();
			if (windliveplot.wind ==1) {
			if(Wind.measures<30& random<0.03) {temp1 = temp1 + (-0.0015+ random); hum1 = hum1- (-0.0005+random);}
			if(Wind.measures>30) {temp1 = temp1 -0.0005; hum1 = hum1+ 0.0025;}
			if(Wind.measures > 60) {temp1 = temp1 -0.006; 
			hum1 = hum1+ 0.01;}
			}
			else {
				if(Wind.measures<30& random<0.03) {temp1 = temp1 + (-0.0015+ random); hum1 = hum1- (-0.0005+random);}
				if(Wind.measures>30) {temp1 = temp1 -0.0005; hum1 = hum1+ 0.0025;}
				if(Wind.measures > 60) {temp1 = temp1 -0.006; 
				hum1 = hum1+ 0.01;}
			}
		},0, 1000, TimeUnit.MILLISECONDS);
		temp.scheduleAtFixedRate(() -> {
			if (windliveplot.wind ==1) {//Works OK
			double random = Math.random();
			if( random<0.1)temp1 = temp1 -(-0.0065+ random);	
			if(RandomWindModels.quickwind > 60) {temp1 = temp1 -0.005; 
			hum1 = hum1+ 0.02;}
			if(hum1 >100) hum1 =90;
			if(hum1<0) hum1= 1;
			}
		},0, 10000, TimeUnit.MILLISECONDS);
		
		
		modelstarted = 1;
		ScheduledExecutorService startempsetter =Executors.newScheduledThreadPool(1);//Works OK
		startempsetter.scheduleAtFixedRate(() -> {
			temp3 = temp1;
			randomsettemp = temp3 *(0.07 +Math.random());},0,10, TimeUnit.SECONDS);
			if(randomsettemp/temp1<1.05 && randomsettemp/temp1>0.95) { temp1 = randomsettemp;
			
			hour = LocalDateTime.now().getHour();minute = LocalDateTime.now().getMinute();day = LocalDateTime.now().getDayOfYear();month = LocalDateTime.now().getMonthValue();
			hourChecker.scheduleAtFixedRate(() -> {
		        if(temp1>upperbound) temp1= upperbound - 0.2;
		        temprec.add(temp1);

		    }, 0, 60000/tempmodels.timeaccelerator, TimeUnit.MILLISECONDS);
			hourChecker.scheduleAtFixedRate(() -> {
		        
		        hour = hour +1;
		    
		    }, 0, 60/tempmodels.timeaccelerator, TimeUnit.MINUTES);
			hourChecker.scheduleAtFixedRate(() -> {
		        
		        day = day +1;
		        if(day > 31) day = 1;
		    }, 0, 24/tempmodels.timeaccelerator, TimeUnit.HOURS);
		}
	}
	

}
