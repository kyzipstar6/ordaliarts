package launchconfigurations;

import application.tempmodels;
import application.windliveplot;
import application.TempModelTwo;
import  application.Wind;

public class LaunchConfigurations {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public static void ModelOnelowChangeWarm() {
		windliveplot.wind = 2;
		tempmodels.modelOneAsHour(18); Wind.commerceWindModelOne(1, 5);
	}
	public static void ModelOnelowChangeHot() {
		windliveplot.wind = 2;
		tempmodels.modelOneAsHour(27); Wind.commerceWindModelOne(1, 5);
	}
	public static void ModelOnelowChangeCold() {
		windliveplot.wind = 2;
		tempmodels.modelOneAsHour(10); Wind.commerceWindModelOne(1, 5);
	}
	public static void ModelTwoDesert() {
		windliveplot.wind = 2;
		TempModelTwo.desertindex= 3;
		TempModelTwo.modelTwo((18*(0.7+Math.random())), 3); Wind.commerceWindModelOne(1, 5);
	}
	public static void ModelTwoNormal() {
		TempModelTwo.desertindex= 2;
		windliveplot.wind = 2;
		TempModelTwo.modelTwo((18*(0.7+Math.random())), 2); Wind.commerceWindModelOne(1, 5);
	}
	public static void ModelTwoWet() {
		TempModelTwo.desertindex= 1;
		windliveplot.wind = 2;
		TempModelTwo.modelTwo((18*(0.7+Math.random())), 1); Wind.commerceWindModelOne(1, 5);
	}
}
