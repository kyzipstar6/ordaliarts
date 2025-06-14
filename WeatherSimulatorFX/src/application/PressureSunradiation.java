package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PressureSunradiation {
	static double maxhour=24; static int maxminute;
	static int hour =CentralClock.hour; static int minute =CentralClock.minute;
	static double pressure= 1010;static  double defpres= 1023;
	static double solrad= 0;static  double defmaxsolrad= 1323;
	static List <Double> presrec = new ArrayList<>();static List <Double> radrec = new ArrayList<>();
	static  ScheduledExecutorService pres = Executors.newScheduledThreadPool(3);
	static ScheduledExecutorService rad = Executors.newScheduledThreadPool(3);
	static long upinterval ;
	public static void commerceModel() {
		pres.scheduleAtFixedRate(()->{ presModel();},0,52, TimeUnit.DAYS);
		pres.scheduleAtFixedRate(()->{radModel();},0,52, TimeUnit.DAYS);
	}
	public static void  main (String args[]) {
		commerceModel();
	}
	static double db;
	static Random rd = CentralClock.rd; static double pinc= 0.001;
	static void presModel() {
		pressure = defpres;
		
		pres.scheduleAtFixedRate(()->{
			if (db<2)db = rd.nextDouble();
			if(hour<= maxhour/2 && hour >= maxhour/4 )pressure = pressure+0.0001*db;
			else pressure = pressure-pinc*db;
			
		},0,222,TimeUnit.MILLISECONDS);
		pres.scheduleAtFixedRate(()->{
			if(Wind.measures>50 || RandomWindModels.quickwind>50) {pressure = pressure - 0.01;}
		},0,222*100,TimeUnit.MILLISECONDS);
		pres.scheduleAtFixedRate(()->{
			presrec.add(pressure);
			radrec.add(solrad);
		},0,1000*60,TimeUnit.MILLISECONDS);
		
	}
	static double factorcorrector = (1/maxhour)*24; static double sinc= 0.2;
	//Based on a 24-hour day
	static void radModel() {
		rad.scheduleAtFixedRate(()->{
			if(0> solrad)solrad = 0;
			
			if(CentralClock.hour<= 16 && CentralClock.hour >= maxhour/4 && Atmosphere.clouds>0&& Atmosphere.clouds<3)solrad = solrad + sinc ;
			if(CentralClock.hour<= 16 && CentralClock.hour >= maxhour/4 &&Atmosphere.clouds>3 && Atmosphere.clouds<5)solrad = solrad + sinc/2 ;
			if(CentralClock.hour<= 16 && CentralClock.hour >= maxhour/4 && Atmosphere.clouds>4)solrad = solrad - sinc/3 ;
			if(CentralClock.hour<= 16 && CentralClock.hour >= maxhour/4 && Atmosphere.clouds<=1)solrad = solrad + sinc ;
			else solrad = solrad-sinc;
			
		},0,2,TimeUnit.SECONDS);
			
			rad.scheduleAtFixedRate(()->{
				if(defmaxsolrad<= solrad)solrad = defmaxsolrad;
				
			},0,5,TimeUnit.SECONDS);
			
			//scheduler(()->pressure = pressure-0.04,5000,decreaser);
			
		
	}
	static void scheduler(Runnable runnable, long interval, ScheduledExecutorService updater) {
    	updater= Executors.newScheduledThreadPool(1);
    	updater.scheduleAtFixedRate(runnable, 0, interval, TimeUnit.MILLISECONDS);
    	}
	
	
}
