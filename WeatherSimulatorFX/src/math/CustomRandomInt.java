package math;

import java.util.Random;

public class CustomRandomInt {
	public static int customRndInt (String [] array) {
		Random rnd = new Random();	
		int prod = rnd.nextInt(array.length);
		return prod;
	}
}
