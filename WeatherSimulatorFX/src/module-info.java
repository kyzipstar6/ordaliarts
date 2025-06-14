module WeatherSimulatorFX {
	requires javafx.controls;
	requires javafx.graphics;
	requires java.desktop;
	requires org.jfree.jfreechart;
	
	opens application to javafx.graphics, javafx.fxml;
}
