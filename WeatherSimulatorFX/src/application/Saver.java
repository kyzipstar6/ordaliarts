package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

class SavedData{
	static double temp;
	static double hum;
	static double wspeed;
	static double wgust;
}

public class Saver {

	public static void main(String[] args) {
		save();
		//load();
		SavedData.temp = 15;
		System.out.println("Log tmp is: " +SavedData.temp);
		save();
	}
	public static void createFileforFirstTime() {
		FileWriter saver;
		try {
			saver = new FileWriter("savegame.txt", false);
			saver.append(String.join(";", Double.toString(SavedData.temp ), Double.toString(SavedData.hum ),
			Double.toString(SavedData.wspeed ),Double.toString(SavedData.wgust )));
			saver.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static void save() {
		FileWriter saver;
		try {
			saver = new FileWriter("savegame.json", false);
			saver.append(String.join(";", Double.toString(SavedData.temp ), Double.toString(SavedData.hum ),
			Double.toString(SavedData.wspeed ),Double.toString(SavedData.wgust )));
			saver.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static void load() {
		BufferedReader loader;
		File file = new File("savegame.json");
		System.out.println(file.compareTo(new File("")));
		try {
			loader = new BufferedReader(new FileReader("savegame.json"),2);
			String [] parts = loader.readLine().split(";");
			SavedData.temp = Double.parseDouble(parts [0]);
			SavedData.hum = Double.parseDouble(parts [1]);
			SavedData.wspeed = Double.parseDouble(parts [2]);
			SavedData.wgust = Double.parseDouble(parts [3]);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
