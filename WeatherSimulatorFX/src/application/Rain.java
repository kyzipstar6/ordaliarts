package application;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class Rain {

	static double rain;
	private static List <Double>rainrec; static double difference;
	private static double wnd;
	private static double hum;
	static int clouds;
	private static int cloudsf;
	private static AtomicReference<Double> humref;
	private static int hour;
	public static boolean isRaining;
	static ScheduledExecutorService cloudsScheduler = Executors.newScheduledThreadPool(2);
	
	
	static double rinc=0.005; static int rained =0; static double rainmem;
	static void model() {
		cloudsScheduler = Executors.newScheduledThreadPool(1);
		cloudsScheduler.scheduleAtFixedRate(() -> {
			if(Atmosphere.clmem>= 5 && Dashboard.stagename.equals(Dashboard.stages[0])&& Atmosphere.clouds >=4) rain = rain + rinc*4;
			if(Atmosphere.clmem== 5 && Dashboard.stagename.equals(Dashboard.stages[1])&& Atmosphere.clouds >=4) rain = rain + rinc*2;
			if(Atmosphere.clmem== 5 && Dashboard.stagename.equals(Dashboard.stages[2])||
					Dashboard.stagename.equals(Dashboard.stages[3])&& Atmosphere.clouds >=4) rain = rain + rinc;
			if(Atmosphere.clmem== 5 && Dashboard.stagename.equals(Dashboard.stages[1])&& Atmosphere.clouds >=4) rain = rain + rinc*2;
			
		}, 0, (long) (2*1000/Dashboard.acceleration), TimeUnit.MILLISECONDS);
		cloudsScheduler.scheduleAtFixedRate(() -> {

			if(rain%rainmem ==0) {rained = 0;}
			else rained = 1;
			System.out.println("rainmem: " + rainmem + " rained: "+ rained + " rain: "+ rain + "test "+rain%rainmem );
		}, 0, 60*60/Dashboard.acceleration, TimeUnit.MILLISECONDS);
		cloudsScheduler.scheduleAtFixedRate(() -> {
			rainmem = rain;
			
		}, 0, 60*100/Dashboard.acceleration, TimeUnit.MILLISECONDS);
		cloudsScheduler.scheduleAtFixedRate(() -> {
			rainrec.add(rain);
			
		}, 0, 60*1000/Dashboard.acceleration, TimeUnit.MILLISECONDS);
	}
	public static void main(String[] args) {
		model();
	}

}
