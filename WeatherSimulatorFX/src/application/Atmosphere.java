package application;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Atmosphere {
	static int clouds; static int cl1;static int cl2;static int incl;static Random ran = new Random();
	static ScheduledExecutorService clsetter = Executors.newScheduledThreadPool(1);
public static void Atmosphere() {
	if(!Dashboard.stagename.equals(Dashboard.stages[2])||!Dashboard.stagename.equals(Dashboard.stages[3])) incl = ran.nextInt(2,5);
	else incl = ran.nextInt(0,3);
	randomClouds();
}
static Random rdfac = new Random();static int rd; static int clmem = 3;
private static void randomClouds() {
	
	clsetter = Executors.newScheduledThreadPool(1);
	clsetter.scheduleAtFixedRate(() ->{
		if(!Dashboard.stagename.equals(Dashboard.stages[2])||!Dashboard.stagename.equals(Dashboard.stages[3])) { cl1 = ran.nextInt(1,6); cl2 = ran.nextInt(4,6);}
		else {cl1 = ran.nextInt(4); cl2 = ran.nextInt(1,5);}
		rd = rdfac.nextInt(3);
		if(rd<2) clouds = cl2;
		else clouds =cl1;
		System.out.println("Clouds " + clouds + ", cloudsmem: " + clmem+ " " + Dashboard.stagename + rd);
	}, 0, 60000, TimeUnit.MILLISECONDS);
	clsetter.scheduleAtFixedRate(() ->{
		clmem = clouds;
	}, 0, 120000, TimeUnit.MILLISECONDS);
	clsetter.scheduleAtFixedRate(() ->{
		if(!Dashboard.stagename.equals(Dashboard.stages[0])||!Dashboard.stagename.equals(Dashboard.stages[1]) ) clouds = ran.nextInt(6);
		 
	}, 0, 180000, TimeUnit.MILLISECONDS);
}
public static void main(String args[]) {
	Atmosphere();
}
}
