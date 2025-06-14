package application;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import javafx.scene.chart.XYChart.Data;

public class tempmodels {
	static double starttemp;
	static double starthum = 40;
	static double randomsettemp;
	static double randomsethum;
	static double temp1 = 15.9;
	static double temp2 = 14.7;
	static double hum1 = 60;
	static double hum2 = 80;
	static double temp3 = 14.7;static double upperbound = 40;static double lowerbound = -20;
	static double secondrecord;
	static List<Double> temprec = new ArrayList<>();
	static double roundtemp1;
	static double roundtemp2;
	static double roundhum1;
	static double roundhum2;
	static int timeaccelerator = 1;
	private static int modelstarted=0;
	static ScheduledExecutorService modelScheduler = Executors.newScheduledThreadPool(1);
	static ScheduledExecutorService hourChecker = Executors.newScheduledThreadPool(3);
	static Future<?> currentModelTask = null;
	static boolean isDayRunning = false;
	private static AtomicReference<Double> wndref; 
	private static LocalDateTime time = LocalDateTime.now();
	 static int minute = 1;
	static ScheduledExecutorService day = Executors.newScheduledThreadPool(3); static Month month;private static int year = 1;
	private static double atovalue;
	public static int tempmodelind = 1;
	
	public static void main(String[] args) {
	

	}
	static void modelOneDay(double StartingTemperature) {
		
		tempmodelind = 1; 
		
		if(StartingTemperature == 0.0) StartingTemperature = starttemp;
		if(modelstarted != 1) starttemp = StartingTemperature;
		if(modelstarted == 1) starttemp= temp1; starthum= hum1;
		
		
		if(modelstarted != 1)randomsettemp = starttemp*(0.7 +Math.random());
		if(modelstarted != 1)randomsethum = starthum*(0.7 +Math.random());
		if(randomsethum > 90&& modelstarted != 1) randomsethum = 90;
		//Works OK
		if(modelstarted != 1)temp1= randomsettemp;
		if(modelstarted != 1)hum1 = randomsethum;
		Dashboard.schedulerWrap(() ->{wndref.set(RandomWindModels.quickwind);atovalue = wndref.get();}, 5);
		
		
		day =Executors.newScheduledThreadPool(3);
		day.scheduleAtFixedRate(() -> {		
			
			if(atovalue<20&& Atmosphere.clouds>2&&Atmosphere.clouds<4&&CentralClock.hour >7 && CentralClock.hour<19) {temp1 = temp1 +0.0008;hum1 = hum1- 0.001;}
			if(atovalue>20&& Atmosphere.clouds>2&&Atmosphere.clouds<4&&CentralClock.hour >7 && CentralClock.hour<19) {temp1 = temp1 +0.0005;hum1 = hum1- 0.005;}
			if(Atmosphere.clouds>4&&CentralClock.hour >7 && CentralClock.hour<19) {temp1 = temp1 +0.0002;hum1 = hum1- 0.005;}
			if(Atmosphere.clouds<2&&CentralClock.hour >7 && CentralClock.hour<19) {temp1 = temp1 +0.001;hum1 = hum1- 0.006;}
			
		
			
		},0, 1000, TimeUnit.MILLISECONDS);
		day.scheduleAtFixedRate(() -> {	
			double random = Math.random();
			if(atovalue<30&& random<0.03) {temp1 = temp1 + (-0.0015+ random); hum1 = hum1- (-0.0005+random);}
			if(atovalue>30) {temp1 = temp1 -0.0005; hum1 = hum1+ 0.0025;}
			if(atovalue > 60) {temp1 = temp1 -0.006; 
			hum1 = hum1+ 0.01;}
		},0, 1000, TimeUnit.MILLISECONDS);
		day.scheduleAtFixedRate(() -> {	//Works OK
			double random = Math.random();
			if( random<0.1)temp1 = temp1 -(-0.0065+ random);	hum1 = hum1- 0.012;
			if(atovalue > 60) {temp1 = temp1 -0.005; 
			hum1 = hum1+ 0.02;}
			if(hum1 >100) hum1 =90;
			if(hum1<0) hum1= 1;
		},0, 10000, TimeUnit.MILLISECONDS);
		
		
	
		modelstarted = 1;
		ScheduledExecutorService startempsetter =Executors.newScheduledThreadPool(1);//Works OK
		startempsetter.scheduleAtFixedRate(() -> {
			temp3 = temp1;
			randomsettemp = temp3 *(0.07 +Math.random());
			if(randomsettemp/temp1<1.05 && randomsettemp/temp1>0.95) { temp1 = randomsettemp;
			}
		}, 0, 10000,TimeUnit.MILLISECONDS);
	}
	static void modelOneNight(double StartingTemperature) {
		wndref = new AtomicReference<Double>();
		
		
		if(StartingTemperature == 0.0) StartingTemperature = starttemp;
		if(modelstarted != 1) starttemp = StartingTemperature;
		if(modelstarted == 1) starttemp= temp1; starthum= hum1;
		
		
		if(modelstarted != 1)randomsettemp = starttemp*(0.7 +Math.random());
		if(modelstarted != 1)randomsethum = starthum*(0.7 +Math.random());
		if(randomsethum > 90&& modelstarted != 1) randomsethum = 90;
		//Works OK
		if(modelstarted != 1)temp1= randomsettemp;
		if(modelstarted != 1)hum1 = randomsethum;
		if(randomsethum > 90) randomsethum = 90;
		//Works OK
		if(modelstarted != 1)temp1= randomsettemp;
		if(modelstarted != 1)hum1 = randomsethum;
		Dashboard.schedulerWrap(() ->{wndref.set(RandomWindModels.quickwind);atovalue = wndref.get();}, 5);
		
		
		ScheduledExecutorService night =Executors.newScheduledThreadPool(3);
		night.scheduleAtFixedRate(() -> {			
			if( atovalue<18) {temp1 = temp1 -0.001;hum1 = hum1+ 0.0015;}
			if(atovalue>20) {temp1 = temp1 -0.0007;hum1 = hum1+ 0.0005;}
			
		},0, 1000, TimeUnit.MILLISECONDS);
		night.scheduleAtFixedRate(() -> {			
			double random = Math.random();
			if(atovalue<30&& random<0.03) {temp1 = temp1 - (0.0015- random); hum1 = hum1+ (0.0005-random);}
			if(atovalue>30) {temp1 = temp1 +0.0005; hum1 = hum1- 0.0025;}
			if(atovalue > 60) {temp1 = temp1 +0.006; 
			hum1 = hum1+ 0.01;}
			if(hum1 >100) hum1 =90;
			if(hum1<0) hum1= 1;
		},0, 30000, TimeUnit.MILLISECONDS);
		
		
	
		
		ScheduledExecutorService startempsetter =Executors.newScheduledThreadPool(1);//Works OK
		startempsetter.scheduleAtFixedRate(() -> {
			temp3 = temp1;
			randomsettemp = temp3 *(0.07 +Math.random());
			if(randomsettemp/temp1<1.05 && randomsettemp/temp1>0.95) { temp1 = randomsettemp;
			}
		}, 0, 10000,TimeUnit.MILLISECONDS);
		
		hourChecker.scheduleAtFixedRate(() -> {
	        temprec.add(temp1);
	        minute = minute +1;
	        if(minute >= 60) minute =0;	        
	        if(CentralClock.hour > 24) CentralClock.hour = 0;
	        if(temp1>upperbound) temp1= upperbound - 0.2;
	        
	    }, 0, 60000/timeaccelerator, TimeUnit.MILLISECONDS);
		hourChecker.scheduleAtFixedRate(() -> {
	        
	        CentralClock.hour = CentralClock.hour +1;
	    
	    }, 0, 60/timeaccelerator, TimeUnit.MINUTES);
		
	}
	
	
	private static void infoPane(String message) {
		JOptionPane.showMessageDialog(new JFrame(), message, "Information", JOptionPane.INFORMATION_MESSAGE, null);
	}
}
