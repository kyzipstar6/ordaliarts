package application;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;



import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.Random;

public class JFreeChartLiveWind {
	private static final int MAX_DATA_POINTS = 7200;
    private static final Random RANDOM = new Random();
    private static final ScheduledExecutorService sizeremover = Executors.newScheduledThreadPool(1);
    public static double wind = 1;
    
    private static String title="N/A";
    private static String unities="N/A";
    private static String tag = "N/A";

    private static TimeSeries windSpeedSeries= new TimeSeries("Five Minutes average");
    private static TimeSeries windSpeedSeries3 = new TimeSeries("Two minutes average");
    private static TimeSeries windSpeedSeries2 = new TimeSeries("N/A");
    

    private static double instant2;
    
    
    
    private static Timer dataUpdateTimer;

    // Method to create and initialize the live wind speed chart
    public static JPanel createLiveWindChart(String chartTitle) {
        // Create the TimeSeriesCollection (dataset)
    	
    	if(wind != 3) {title = "Live Wind Chart";unities = "Windspeed (km/h)"; tag ="Instant wind speed";}
        if(wind== 3) {title = "Live Temperature Chart";unities = "Temperature (°C)";tag ="Instant temperature";}
    	
        windSpeedSeries2.setDescription(tag);
        
    	TimeSeriesCollection dataset = new TimeSeriesCollection();
    	if(wind != 3)dataset.addSeries(windSpeedSeries);
        dataset.addSeries(windSpeedSeries2);
        if(wind != 3)dataset.addSeries(windSpeedSeries3);
        
        
       
        // Create the chart using JFreeChart
        JFreeChart windChart = ChartFactory.createTimeSeriesChart(
                chartTitle,          // Chart title
                "Time (Seconds)",    // X-axis label
                "Windspeed (km/h)", // Y-axis label
                dataset,             // Data
                true,               // Legend
                true,                // Tooltips
                false                // URLs
        );

        // Create the chart panel (to embed the chart into Swing components)
        ChartPanel chartPanel = new ChartPanel(windChart);
        chartPanel.setPreferredSize(new Dimension(800, 600));
        

        // Start updating the data dynamically
        startDataUpdates();

        return chartPanel;
    }

    // Method to start updating the wind speed data at regular intervals
    private static void startDataUpdates() {
        // Initialize the data update timer
        dataUpdateTimer = new Timer();
        dataUpdateTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                // Generate new wind speed data and update the series
                try {
                	if (wind == 1){
					instant2 = RandomWindModels.quickwind;}
                	if (wind == 2){
    					instant2 = Wind.measures;}
                	if (wind == 3){
    					instant2 = tempmodels.roundtemp1;}
               
					//double twosecs = RandomWindModels.twosecrec;
					double twominavg = Dashboard.twominspd;
					double fiveminavg = Dashboard.fiveminspd;
					
					SwingUtilities.invokeLater(() -> {
					    // Add the new data point
						if(instant2< RandomWindModels.UPPER_BOUND && instant2> RandomWindModels.LOWER_BOUND)windSpeedSeries2.addOrUpdate(new Second(), instant2);
						 if(twominavg< RandomWindModels.UPPER_BOUND && twominavg> RandomWindModels.LOWER_BOUND
								 &&wind != 3)windSpeedSeries3.addOrUpdate(new Second(), twominavg);
						if(fiveminavg< RandomWindModels.UPPER_BOUND && fiveminavg> RandomWindModels.LOWER_BOUND
								&&wind != 3)windSpeedSeries.addOrUpdate(new Second(), fiveminavg);

					    // Remove excess data points to avoid exceeding MAX_DATA_POINTS
						sizeremover.scheduleAtFixedRate(() ->{
					    if (windSpeedSeries2.getItemCount() > MAX_DATA_POINTS) {
					        windSpeedSeries.delete(0, 1);
					        windSpeedSeries2.delete(0, 1);
					        windSpeedSeries3.delete(0, 1);
					    }}, 0, 2, TimeUnit.SECONDS);
					});
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        }, 0, 200); // Update every 1 second
    }

    // Method to stop the dynamic data updates to avoid memory leaks
    public static void stopDataUpdates() {
        if (dataUpdateTimer != null) {
            dataUpdateTimer.cancel();
        }
    }

    // Method to simulate random wind speed data (for testing)
    private static double generateRandomWindSpeed() {
        return 10 + RANDOM.nextDouble() * 20; // Generate random wind speed between 10 and 30 km/h
    }

    // Main method to test the chart in a JFrame
    public static void liveWindPlot() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame(title);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            // Add the live wind chart to the frame
            JPanel chartPanel = createLiveWindChart(title);
            frame.getContentPane().add(chartPanel, BorderLayout.CENTER);

            frame.pack();
            frame.setVisible(true);
            frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        });
    }
}
