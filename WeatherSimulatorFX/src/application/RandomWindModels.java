package application;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import java.util.concurrent.atomic.AtomicReference;

import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class RandomWindModels {
	static double wspeed1;
	static double wspeed2;
	static double filter;
	static double filterrecord;
	static double quickwind;
	static double twominavg;
	static double fiveminavg;
	static double W_MAXmax= 75;
	static double W_MAXmin= 45;
	static double W_MINmax= 25;
	static double W_MINmin= 10;
	static double twosecrec;
	static double finalselec;
	static double FILTER_RATIO = 2;
	static double DIFERENCE_RANDOMS = 10;
	static double STABILIZED_FILTER_RATIO = 0.9985;
	static double BOUNDS_RATIO = 1.6;
	static double RATIO_WSPEEDS = 0.9985;
	static Thread thread;
	static Thread thread2;
	static Thread thread3;
	static double UPPER_BOUND = 60;
	static double LOWER_BOUND = 20;
	static double MINIMALDIFBOUNDS = 7;
	static long DURATION = 1000;
	static long BOUNDSDURATION = 6000;
	static long QUICK_DURATION = 200;
	private static AtomicReference<Double> upperBound;
	private static AtomicReference<Double> lowerBound;
	
	public static void main(String args[]) {
		launchVersionTwo(10, 5, 2);
		GeneralControls.createSwings("", new JFrame(), true);
		GeneralControls.labelupdate();
	}
	
	static void launchVersionOne(double StabilizedFilterRatio, double RatioRandomWspeed,  long duration) {
		//More sophisticated, allows for game uptrend and downtrend. It is meant to be as constant as specified by the ratios
		//Stabilized filter ratio is the allowed increase or decrease per time unit, whereas the RatioRandowmspeed is the maximum 
		//instant divergence between the two randoms proposed. 
		GeneralControls.writingindex=1;
		windliveplot.wind=1;
		 DURATION = duration;
		 Dashboard.dataFromWModels = true;
		 thread = new Thread( () -> {
			 RandomWindModels.boundSetter(RandomWindModels.UPPER_BOUND, RandomWindModels.LOWER_BOUND, RandomWindModels.W_MINmax, RandomWindModels.W_MINmin, RandomWindModels.W_MAXmax, RandomWindModels.W_MINmax,
						RandomWindModels.BOUNDSDURATION, RandomWindModels.MINIMALDIFBOUNDS);
		//CONVERSE_RATIO_WSPEEDS = ConverseRatioWSpeed;
		upperBound = new AtomicReference<Double>();
		
		lowerBound = new AtomicReference<Double>();
		Dashboard.schedulerWrap(() ->{
		upperBound.set(RandomWindModels.UPPER_BOUND);
		lowerBound.set(RandomWindModels.LOWER_BOUND);},5000);
		STABILIZED_FILTER_RATIO= StabilizedFilterRatio;
		RATIO_WSPEEDS = RatioRandomWspeed;
		
		ScheduledExecutorService updater= Executors.newScheduledThreadPool(1);
		try {
			updater.scheduleAtFixedRate(() -> {
				wspeed1= (Math.random()*200);
				wspeed2= (Math.random()*200);
				
				
				try {
					
					//wspeed1/filterrecord >=  (Math.random()*StabilizedFilterRatio)/0.6
				if( wspeed1 < UPPER_BOUND && wspeed1 > LOWER_BOUND 
						&& wspeed2 < UPPER_BOUND && wspeed2 > LOWER_BOUND && (wspeed1/wspeed2<= RATIO_WSPEEDS)
						)
				 filter= wspeed2;
				filterrecord = filter;
				if(wspeed1 < UPPER_BOUND && wspeed1 > LOWER_BOUND  && wspeed2 < UPPER_BOUND && wspeed2> LOWER_BOUND && wspeed1/filter <= STABILIZED_FILTER_RATIO
						)quickwind = wspeed1;
						
				
				//label2.setText(Double.toString(wspeed1));
			} catch (Exception e) {
				// TODO Auto-generated catch block1
				e.printStackTrace();
				infoPane(e.getMessage(), 10000);
			}}, 0, DURATION, TimeUnit.MILLISECONDS);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 });
		 try {
				thread.start();
			} catch (Exception e) {
				e.printStackTrace();
			}
	
	}
	public static void launchVersionTwo(double FilterRatio, double DifferenceRandoms, long duration) {
		//First launch, inherently gusty, was recently edited removing some unnecessary arguments
		GeneralControls.writingindex=1;
		windliveplot.wind=1;
		FILTER_RATIO= FilterRatio;
		 DIFERENCE_RANDOMS = DifferenceRandoms;
		 DURATION = duration;
		 
		 Dashboard.dataFromWModels = true;
		thread = new Thread( () -> {
			RandomWindModels.boundSetter(RandomWindModels.UPPER_BOUND, RandomWindModels.LOWER_BOUND, RandomWindModels.W_MINmax, RandomWindModels.W_MINmin, RandomWindModels.W_MAXmax, RandomWindModels.W_MINmax,
					RandomWindModels.BOUNDSDURATION, RandomWindModels.MINIMALDIFBOUNDS);
		ScheduledExecutorService updater= Executors.newScheduledThreadPool(1);
		try {
			updater.scheduleAtFixedRate(() -> {
				wspeed1  = (Math.random()*200);
				wspeed2  = (Math.random()*200);
				
				try {
				if(wspeed1 < UPPER_BOUND && wspeed1 > LOWER_BOUND && (wspeed2 - wspeed1<= DIFERENCE_RANDOMS)) filter= wspeed2;
				
				if(wspeed1 < UPPER_BOUND && wspeed1 > LOWER_BOUND && (wspeed2 - wspeed1<= DIFERENCE_RANDOMS) && filter - wspeed1 < FILTER_RATIO 
						) filterrecord = wspeed1;
				if(wspeed1 < UPPER_BOUND && wspeed1 > LOWER_BOUND && (wspeed2 - wspeed1<= DIFERENCE_RANDOMS) && filter - wspeed1 < FILTER_RATIO 
						&& filterrecord - wspeed1 < FILTER_RATIO) quickwind = wspeed1; 
						
				
				} catch (Exception e) {
				// TODO Auto-generated catch block1
					infoPane(e.getMessage(), 10000);
			}}, 0, DURATION, TimeUnit.MILLISECONDS);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			
		}
		});
		try {
			thread.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	static void launchVersionThree(double StabilizedFilterRatio, double RatioRandomWspeed,  long duration) {
		//Intended for gust winds
		GeneralControls.writingindex=1;
		windliveplot.wind=1;
		STABILIZED_FILTER_RATIO= StabilizedFilterRatio;
		RATIO_WSPEEDS= RatioRandomWspeed;
		DURATION = duration;
		
		ScheduledExecutorService updater= Executors.newScheduledThreadPool(1);
		List<Double>  measures = new ArrayList<Double>();
		thread = new Thread( () -> {
			RandomWindModels.boundSetter(RandomWindModels.UPPER_BOUND, RandomWindModels.LOWER_BOUND, RandomWindModels.W_MINmax, RandomWindModels.W_MINmin, RandomWindModels.W_MAXmax, RandomWindModels.W_MINmax,
					RandomWindModels.BOUNDSDURATION, RandomWindModels.MINIMALDIFBOUNDS);
		updater.scheduleAtFixedRate(() -> {
			wspeed1  = (Math.random()*200);
			wspeed2  = (Math.random()*200);
			
			try {
			if(wspeed1/filterrecord >=  (Math.random()*StabilizedFilterRatio)/0.6  && wspeed1 < UPPER_BOUND && wspeed1 > LOWER_BOUND && (wspeed1/wspeed2<= RATIO_WSPEEDS)
					&& wspeed2/wspeed1 >= 1/RATIO_WSPEEDS ) filter= wspeed1;
			filterrecord  = filter;
			if(wspeed1 < UPPER_BOUND && wspeed1 > LOWER_BOUND  && wspeed1/filter <= STABILIZED_FILTER_RATIO && wspeed1/filter >= 1/STABILIZED_FILTER_RATIO
				) if (measures.get(measures.size()) - wspeed1 < 0.8) if (measures.get(measures.size()) - wspeed1 > 0.8) filterrecord = wspeed1;  measures.add(wspeed1);
					if (wspeed1 < UPPER_BOUND && wspeed1 > LOWER_BOUND  && wspeed1/filter <= STABILIZED_FILTER_RATIO && wspeed1/filter >= 1/STABILIZED_FILTER_RATIO
							&&  measures.get(measures.size()) - measures.get(measures.size()-1) < 0.8)if (measures.get(measures.size()-1) - wspeed1 < 0.8) if (measures.get(measures.size()-1) - wspeed1 > 0.8)  ;
					if (wspeed1 < UPPER_BOUND && wspeed1 > LOWER_BOUND  && wspeed1/filter <= STABILIZED_FILTER_RATIO && wspeed1/filter >= 1/STABILIZED_FILTER_RATIO
							&&  measures.get(measures.size()-1) - measures.get(measures.size()-2) < 0.8)if (measures.get(measures.size()-2) - wspeed1 < 0.8) if (measures.get(measures.size()-2) - wspeed1 > 0.8);
					if( wspeed1 < UPPER_BOUND && wspeed1 > LOWER_BOUND  && wspeed1/filter <= STABILIZED_FILTER_RATIO && wspeed1/filter >= 1/STABILIZED_FILTER_RATIO
							&& measures.get(measures.size()-2) - measures.get(measures.size()-3) < 0.8)if (measures.get(measures.size()-3) - wspeed1 < 0.8) if (measures.get(measures.size()-3) - wspeed1 > 0.8) ;
					if( wspeed1 < UPPER_BOUND && wspeed1 > LOWER_BOUND  && wspeed1/filter <= STABILIZED_FILTER_RATIO && wspeed1/filter >= 1/STABILIZED_FILTER_RATIO
									&& measures.get(measures.size()-3) - measures.get(measures.size()-4) < 0.8)if (measures.get(measures.size()-4) - wspeed1 < 0.8) if (measures.get(measures.size()-4) - wspeed1 > 0.8);
									if( wspeed1 < UPPER_BOUND && wspeed1 > LOWER_BOUND  && wspeed1/filter <= STABILIZED_FILTER_RATIO && wspeed1/filter >= 1/STABILIZED_FILTER_RATIO
											&& measures.get(measures.size()-4) - measures.get(measures.size()-5) < 0.8) ;quickwind= filterrecord;
											double labelonetext = quickwind*100; labelonetext = Math.round(labelonetext); quickwind= labelonetext/100;
											
		} catch (Exception e) {
			// TODO Auto-generated catch block1
			
		}}, 0, DURATION, TimeUnit.MILLISECONDS);
		});
		try {
			thread.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	static void launchModelFour() {
		GeneralControls.writingindex=1;
		windliveplot.wind=1;
		ScheduledExecutorService updater= Executors.newScheduledThreadPool(1);
		thread = new Thread( () -> {
			RandomWindModels.boundSetter(RandomWindModels.UPPER_BOUND, RandomWindModels.LOWER_BOUND, RandomWindModels.W_MINmax, RandomWindModels.W_MINmin, RandomWindModels.W_MAXmax, RandomWindModels.W_MINmax,
					RandomWindModels.BOUNDSDURATION, RandomWindModels.MINIMALDIFBOUNDS);
		updater.scheduleAtFixedRate(() -> {
			try {
				
				if(wspeed1>LOWER_BOUND ) if( wspeed1<UPPER_BOUND) wspeed1  = 30/ (Math.random()*5);quickwind=wspeed1;
			
			} catch (Exception e) {
			// TODO Auto-generated catch block1
			e.printStackTrace();
		}}, 0, 100, TimeUnit.MILLISECONDS);
		updater.scheduleAtFixedRate(() -> {
			try {
				
				if(wspeed1>10 ) if( wspeed1<60) wspeed1=quickwind;quickwind = quickwind-2; 
				
			} catch (Exception e) {
			// TODO Auto-generated catch block1
			
		}}, 0, 100, TimeUnit.MILLISECONDS);});
		try {
			thread.start();
		} catch (Exception e) {
			infoPane(e.getMessage(), 10000);
		}
	}
	//Standardup and down determine which are the upper limits of the desired period, 
	//LowMax and Low Min determine which are the lowest gust and instant speeds posible
	//UpMax and UpMin determine which are the lowest gust and instant speeds posible
	//Bounds Ratio is the difference between The Standards UP and down expressed in dividig standardup per standarddown
	static void boundSetter(double standardup, double standardown, double lowmax, double lowmin, double upwmax, double upwmin, double boundsratio, double minimaldifbounds) {
		UPPER_BOUND =standardup; W_MAXmax = upwmax;  W_MINmax = upwmin; BOUNDS_RATIO = boundsratio;
		LOWER_BOUND = standardown; W_MINmax = lowmax; W_MINmin = lowmin; MINIMALDIFBOUNDS = minimaldifbounds;
//		upwmax = WMAXnax;
//		upwmnin = WMinmin;
		 long delay = 0;
		ScheduledExecutorService updater= Executors.newSingleThreadScheduledExecutor();
		updater.scheduleAtFixedRate(() -> {
				wspeed1  = (Math.random()*200);
				wspeed2  = (Math.random()*200);
				
			if (wspeed1 < W_MAXmax && wspeed1 > W_MAXmin && UPPER_BOUND/wspeed1 <= BOUNDS_RATIO&& wspeed1 > LOWER_BOUND 
					&& (wspeed1 - UPPER_BOUND <= MINIMALDIFBOUNDS) ) { UPPER_BOUND = wspeed1; }
			if (wspeed1 < W_MAXmax && wspeed1 > W_MAXmin && wspeed1/UPPER_BOUND <= BOUNDS_RATIO&& wspeed1 > LOWER_BOUND 
					&& (wspeed1 + UPPER_BOUND <= MINIMALDIFBOUNDS) ) { UPPER_BOUND = wspeed1; }
					//Dashboard.infoPane("Succesfully updated higher limit!: "+ UPPER_BOUND, 12000);}
			if (wspeed2 < W_MINmax && wspeed2 > W_MINmin &&  (LOWER_BOUND/wspeed2 <= BOUNDS_RATIO)&& UPPER_BOUND > wspeed2 
					&& (wspeed2 - LOWER_BOUND <= MINIMALDIFBOUNDS)) {LOWER_BOUND = wspeed2; }
					if (wspeed2 < W_MINmax && wspeed2 > W_MINmin &&  (wspeed2/LOWER_BOUND <= BOUNDS_RATIO)&& UPPER_BOUND > wspeed2 
							&& (wspeed2 + LOWER_BOUND <= MINIMALDIFBOUNDS)) {LOWER_BOUND = wspeed2; 
			//Dashboard.infoPane("Succesfully updated lower limit!: "+ LOWER_BOUND, 12000);};

			}},delay,  BOUNDSDURATION, TimeUnit.MILLISECONDS);
		
	}
	private static void updatetwosecrec() {

		ScheduledExecutorService updater= Executors.newScheduledThreadPool(1);
		updater.scheduleAtFixedRate(() -> {
			try {
				
				twosecrec= quickwind;
		} catch (Exception e) {
			// TODO Auto-generated catch block1
			e.printStackTrace();
		}}, 0, 2000, TimeUnit.MILLISECONDS);
		try {
			thread.start();
		} catch (Exception e) {
			infoPane(e.getMessage(), 10000);
		}
	}
	static void stopThread() {
		RandomWindModels.thread = null;
	}
	private static void infoPane(String message, long duration) {
		Dashboard.dataFromWModels = true;
		JFrame frame = new JFrame();
		 Wind.windfiltersetter.schedule(()-> {frame.dispose();}, duration, TimeUnit.MILLISECONDS);
		JOptionPane.showMessageDialog(frame, message, "Information", JOptionPane.ERROR_MESSAGE, null);
	}
}

