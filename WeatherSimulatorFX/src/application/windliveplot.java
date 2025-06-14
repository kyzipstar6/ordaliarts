package application;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Background;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public abstract class windliveplot extends Application {
    private static final int MAX_DATA_POINTS = 7200;
    public static double wind = 1;
    
    private static String title = "N/A";
    private static String yAxisLabel = "N/A";
    private static String seriesName = "N/A";

   
    private static double instant2;
 
    static String threadmessage;
    
    private static AtomicLong timeCounter = new AtomicLong(0);
    

    public static void liveWindPlot(Stage chartStage) {
    	  Platform.runLater(() -> {
    	
    	XYChart.Series<Number, Number> instantSeries = new XYChart.Series<>();
    	XYChart.Series<Number, Number> twoMinSeries = new XYChart.Series<>();
    	XYChart.Series<Number, Number> fiveMinSeries = new XYChart.Series<>();
    	NumberAxis xAxis;
    	NumberAxis yAxis;
    	LineChart<Number, Number> lineChart; 
    	
    	AnimationTimer updateTimer = null;
    	
    	xAxis = new NumberAxis();
        yAxis = new NumberAxis();
        lineChart = new LineChart<>(xAxis, yAxis);
        double win=wind;
      
        	
            // Set chart properties based on what we're plotting
            if (win!= 3) {
            	title = "Live Wind Chart";
                yAxisLabel = "Windspeed (km/h)";
                seriesName = "Instant wind speed";
            } else {
                title = "Live Temperature Chart";
                yAxisLabel = "Temperature (°C)";
                seriesName = "Instant temperature";
            }
          
            // Create axes
            
            xAxis.setLabel("Time (Seconds)");
            yAxis.setLabel(yAxisLabel);
            //xAxis.getCssMetaData()M
            threadmessage = yAxis.getTickUnit() +"";
            if (win != 3)yAxis.setLowerBound(RandomWindModels.LOWER_BOUND);
          
            // Create chart
            
            lineChart.setTitle(title);
            lineChart.setAnimated(false); // Disable animation for better performance
            lineChart.setCreateSymbols(false); // Don't show symbols for better performance with many points
            if (win != 3)lineChart.setStyle("-fx-background-color:  rgb(21,125,35, 0.4);");
            if (win == 3)lineChart.setStyle("-fx-background-color:  rgb(122,15,10, 0.4);");
            lineChart.applyCss();
            // Set up series
            instantSeries.setName(seriesName);
            if (win != 3) {
                fiveMinSeries.setName("Five Minutes average");
                twoMinSeries.setName("Two minutes average");
            }
            
            // Add series to chart
            lineChart.getData().add(instantSeries);
            if (win != 3) {
                lineChart.getData().add(fiveMinSeries);
                lineChart.getData().add(twoMinSeries);
            }
            
            // Create stage and scene
            
            chartStage.setTitle(title);
            Scene scene = new Scene(lineChart, 800, 600);
            chartStage.setScene(scene);
            
            // Start data updates
            startDataUpdates(instantSeries,twoMinSeries,  fiveMinSeries);
            
            
            // Set up data remover
            ScheduledExecutorService dataRemover = Executors.newScheduledThreadPool(1);
            dataRemover.scheduleAtFixedRate(() -> {
                Platform.runLater(() -> {
                    if (instantSeries.getData().size() > MAX_DATA_POINTS) {
                        instantSeries.getData().removeFirst();
                        fiveMinSeries.getData().removeFirst();
                        twoMinSeries.getData().removeFirst();
                        if (win != 3) {
                        	instantSeries.getData().removeFirst();
                            fiveMinSeries.getData().removeFirst();
                            twoMinSeries.getData().removeFirst();
                         
                        }
                        
                    }
                    xAxis.setUpperBound(xAxis.getUpperBound()-1);
                });
            }, 0, 2, TimeUnit.SECONDS);
            
            // Show the stage
            chartStage.show();
            chartStage.setOnCloseRequest(e-> {stopDataUpdates(lineChart, instantSeries,twoMinSeries,  fiveMinSeries, updateTimer,
            		chartStage); lineChart.getData().clear(); chartStage.close();
            xAxis.setUpperBound(0); yAxis.setUpperBound(instant2);});
            // Handle window close
            
        });
    }

    private static void startDataUpdates(XYChart.Series<Number, Number> instantSeries,
    		XYChart.Series<Number, Number> twoMinAvgSeries, XYChart.Series<Number, Number> fiveminAvgSeries) {
    	 AnimationTimer updateTimer;
    	 
        
         updateTimer = new AnimationTimer() {
            private long lastUpdate = 0;
            
            @Override
            public void handle(long now) {
                // Update every 200ms (5 times per second)
                if (now - lastUpdate >= 200_000_000) {
                    lastUpdate = now;
                    
                    try {
                    	double win=wind;
                    	double plotvalue = 0;
                    	
                        if (win == 1){
        					plotvalue = RandomWindModels.quickwind;}
                        	if (win == 2){
                        		plotvalue = Wind.measures;}
                        	if (win == 3){
            					if(tempmodels.temp1!=15.9)plotvalue = tempmodels.temp1;
            					else plotvalue = TempModelTwo.temp1;}
                        		
                        double twominavg = Dashboard.twominspd;
                        double fiveminavg = Dashboard.fiveminspd;
                        
                        long currentTime = timeCounter.getAndIncrement();
                        
                        // Add new data points
                        if (plotvalue < RandomWindModels.UPPER_BOUND && 
                        		plotvalue > RandomWindModels.LOWER_BOUND) {
                            instantSeries.getData().add(
                                new XYChart.Data<>(currentTime, plotvalue));
                        }
                        
                        if (win != 3) {
                            if (win == 1&&twominavg < RandomWindModels.UPPER_BOUND && 
                                twominavg > RandomWindModels.LOWER_BOUND) {
                                twoMinAvgSeries.getData().add(
                                    new XYChart.Data<>(currentTime, twominavg));
                            }
                            else {
                            	twoMinAvgSeries.getData().add(
                                        new XYChart.Data<>(currentTime, twominavg));
                            }
                            
                            if (win == 1&&fiveminavg < RandomWindModels.UPPER_BOUND && 
                                fiveminavg > RandomWindModels.LOWER_BOUND) {
                                fiveminAvgSeries.getData().add(
                                    new XYChart.Data<>(currentTime, fiveminavg));
                            }
                            else {
                            	fiveminAvgSeries.getData().add(
                                        new XYChart.Data<>(currentTime, fiveminavg));
                            }
                            
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        updateTimer.start();
    }

    public static void stopDataUpdates(LineChart<Number, Number> lineChart,XYChart.Series<Number, Number> instantSeries,
    		XYChart.Series<Number, Number> twoMinSeries, XYChart.Series<Number, Number> fiveMinSeries, AnimationTimer updateTimer,
    		Stage chartStage) {
        if (updateTimer != null) {
            updateTimer.stop();
        }
  
        // Clear series data
        Platform.runLater(() -> {
            instantSeries.getData().clear();
            fiveMinSeries.getData().clear();
            twoMinSeries.getData().clear();
            lineChart.getData().clear();
            timeCounter.lazySet(0);
        });
        
        if (chartStage != null) {
            Platform.runLater(chartStage::close);
        }
    }
}
