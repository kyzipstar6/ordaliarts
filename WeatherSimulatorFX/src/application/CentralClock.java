package application;
import java.util.*;

public class CentralClock {
	static Random rd =new Random();
	static int day = rd.nextInt(30);
	static int month =rd.nextInt(12);
	static int hour = rd.nextInt(23);
	static int minute =rd.nextInt(59);
	static int rise= 7; static int set=19;
	static String time= day + "." + month + " " + hour + ":"+ minute;;
	
	static void launchClock() {
		Dashboard.schedulerWrap(()->{minute = minute +1;
		if(minute > 60)minute=0;
		time= day + "." + month + " " + hour + ":"+ minute;;},60000);
		Dashboard.schedulerWrap(()->{hour = hour +1;
		if( hour>24)hour=0;},60000*60);
		Dashboard.schedulerWrap(()->{day = day +1;
		if(day > 30)day=1;},60000*60*24);
		Dashboard.schedulerWrap(()->{month = month +1;
		if(12 >=month&& month >= 11) { rise = 8; set= 17;}
		if(10 >=month&& month >= 9) { rise = 7; set= 19;}
		if(8 ==month) { rise = 6; set= 21;}
		if(7==month&& month >= 6) { rise = 6; set= 22;}},60000*60*24);
			}
}
