package application;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import java.awt.Font;
import java.awt.Color;

public class Wind {
	public static void main(String args[]){
		
		factorSetter(); ;randomFactorBasedWind();
	
	}
	private static final ScheduledExecutorService conc = Executors.newScheduledThreadPool(7);
	public static void commerceWindModelOne(double increase, long increasereference) {
		GeneralControls.writingindex=2;
		windliveplot.wind =2; 
		 conc.scheduleAtFixedRate(() -> {
		GeneralControls.labelupdate();
		 },0, 50, TimeUnit.DAYS);
		 conc.scheduleAtFixedRate(() -> {
		factorSetter(); 
		 },0, 50, TimeUnit.DAYS);
		 conc.scheduleAtFixedRate(() -> {
		randomFactorBasedWind();},0, 50, TimeUnit.DAYS);
		 conc.scheduleAtFixedRate(() -> {
		 tempdiferenceWind(increase, increasereference);},0, 50, TimeUnit.DAYS);
	}
	static double measures;
	private static double measuresrec;
    private static AtomicReference<Double> atowndtmp = new AtomicReference<Double>();
    private static double tmp;
    private static double tmpfilter;
    private static double tmpfilter2;
    private static double fctr;
   
    
    private static AtomicReference<Double> atofactor = new AtomicReference<Double>();
    private static double factorrec;
    
    private static Thread thread;
    private static Thread thread2;
    
    private final static ScheduledExecutorService windincreaser = Executors.newScheduledThreadPool(1);
    private final static ScheduledExecutorService winddecreaser = Executors.newScheduledThreadPool(1);
    private final static ScheduledExecutorService tempgetter = Executors.newScheduledThreadPool(3);
    private final static ScheduledExecutorService tempfiltersetter = Executors.newScheduledThreadPool(1);
    final static ScheduledExecutorService windfiltersetter = Executors.newScheduledThreadPool(2);
    
    static void tempdiferenceWind(double tmpdif, long increasereferenceminutes) {
    	Dashboard.dataFromWModels = true;
    	windliveplot.wind =2;
    	tempgetter.scheduleAtFixedRate(() -> {
    		if(tempmodels.tempmodelind==1) {atowndtmp.set(tempmodels.temp1); tmp = atowndtmp.get();}
    		else{atowndtmp.set(TempModelTwo.temp1); tmp = atowndtmp.get();}
    	windliveplot.wind = 2;}, increasereferenceminutes, increasereferenceminutes/tempmodels.timeaccelerator, TimeUnit.SECONDS);
    	tmpfilter2 = tmp;tmpfilter = tmp;
//    	tempgetter.scheduleAtFixedRate(() -> {if(Math.abs(Math.round(tmpfilter2-tmpfilter)) > tmpdif) infoPane("From the new one, incr wind due to high T oscilation tmpfilter "+ tmpfilter + ", tmpfilter2: " + tmpfilter2);
//    	if(Math.abs(Math.round(tmpfilter2-tmpfilter)) < tmpdif) infoPane("From the new one, decreased wind due to low T oscilation tmpfilter "+ tmpfilter + ", tmpfilter2: " + tmpfilter2);}, increasereferenceminutes, 1, TimeUnit.MINUTES);
    	
    	tempfiltersetter.scheduleAtFixedRate(() -> {
    	if(tempmodels.tempmodelind==1) {tmpfilter2 = tmpfilter ;atowndtmp.set(tempmodels.temp1); tmpfilter = atowndtmp.get();}
    	else {atowndtmp.set(TempModelTwo.temp1); tmp = atowndtmp.get();}
    	}, increasereferenceminutes, increasereferenceminutes/tempmodels.timeaccelerator, TimeUnit.SECONDS);
    		
    	windincreaser.scheduleAtFixedRate(() -> {if(Math.abs(Math.round(tmpfilter2-tmpfilter)) > tmpdif&& measures>=0)measures = measures +0.1705;},0, 20/tempmodels.timeaccelerator, TimeUnit.MILLISECONDS);
    	winddecreaser.scheduleAtFixedRate(() -> {if(Math.abs(Math.round(tmpfilter2-tmpfilter)) < tmpdif&& measures>=0)measures = measures -0.1705;},0, 20/tempmodels.timeaccelerator, TimeUnit.MILLISECONDS);
    }
	static void randomFactorBasedWind() {
		windliveplot.wind =2;
		Dashboard.dataFromWModels = true;
		//random experience produced only if the method factorSetter() is been called
		tempgetter.scheduleAtFixedRate(() -> {atofactor.set(fctr); factorrec = atofactor.get();}, 0, 1000/tempmodels.timeaccelerator, TimeUnit.MILLISECONDS);
		thread = new Thread(() ->{
		windincreaser.scheduleAtFixedRate(() -> {if(Math.round(factorrec)==1 && measures >= 0
				 && measures>=0) {measures = measuresrec; measures = measures +0.001; measuresrec = measures;}},0, 60/tempmodels.timeaccelerator, TimeUnit.MILLISECONDS);
		});
		thread2 = new Thread(() ->{
			winddecreaser.scheduleAtFixedRate(() -> {if(Math.round(factorrec)==0&& measures >= 0
					 && measures>=0) { measures = measuresrec; measures = measures -0.001; measuresrec = measures;}},0,60/tempmodels.timeaccelerator, TimeUnit.MILLISECONDS);
			});
		thread2.start();	
		thread.start();
	
	}
	
	
		
	private static void factorSetter() {
		Dashboard.dataFromWModels = true;
		//must be called in a separate Thread with randomBasedWind
		ScheduledExecutorService factorsetter = Executors.newScheduledThreadPool(1);
		factorsetter.scheduleAtFixedRate(() -> {fctr = Math.random();
		if(measures < 0) measures = 0; }, 0, 1000/tempmodels.timeaccelerator, TimeUnit.MILLISECONDS);
		
		
	}
	
	
}
