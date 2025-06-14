package application;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.css.CssMetaData;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.Tab;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import java.time.LocalDateTime;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.time.LocalDateTime;
public class ChartsTabPane {
	
public XYChart.Series<Number, Number> plotData(double value) {
	XYChart.Series<Number, Number> dataSeries = new XYChart.Series<>();
       NumberAxis xSeries = new NumberAxis();
       NumberAxis ySeries = new NumberAxis();
       LineChart<Number, Number> chart= new LineChart(xSeries, ySeries);
       dataSeries.getData().add(new XYChart.Data<CentralClock.minute, value>);
	return dataSeries;   
}
public Stage chartTabPane() {
	Collection<? extends Data<Number, Number>> [] values = {tempmodels.temprec, TempModelTwo.temprec};
	Stage stg = new Stage();
	TabPane pane = new TabPane();
	List<Tab> ltab = new ArrayList<>();
	for (int i = 0; i <5; i++) {
		Tab tab = new Tab();
		ltab.add(tab);
		tab.setContent(plotData());
	}
	pane.getTabs().addAll(ltab);
}
}


