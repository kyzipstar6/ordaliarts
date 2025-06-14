package application;
	

import javafx.application.Application;
import javafx.application.Platform;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import launchconfigurations.LaunchConfigurations;
import math.CustomRandomInt;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;


public class Dashboard extends Application {

    static ArrayList<Double> records;
    static double temp;
    static double rain;
    private static LocalDateTime time;
    private static int hour;
    private static double lochour;
    private static double locday;
    static double hum;
    static double twosecspd;
    static double instaspd;
    static double eingabe;
    static double twominspd;
    static double fiveminspd;
    static long acceleration = 1;
    static boolean dataFromWModels = false;
    static boolean dataFromWind = false;
    private static int windowheight =280;
    static Image image ;
  

    public static void main(String[] args) {
        launch(args);
    	
    }

    @Override
    public void start(Stage primaryStage) {
    	Platform.runLater(()->Rain.model());
    	Platform.runLater(()->CentralClock.launchClock());
    	Platform.runLater(()->PressureSunradiation.commerceModel());
    	Platform.runLater(()->GeneralControls.labelupdate());
    	Platform.runLater(()->Atmosphere.Atmosphere());
    	Platform.runLater(()->{
			try {
				launchOp();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
    	
        primaryStage.setTitle(stagename +" conditions");
        
        StackPane weather = labelsBox();
        FlowPane pane = new FlowPane(weather);
        Pane root = new Pane();
        Scene scene = new Scene(root, 700,700);
        
        Image backgroundImage = null;
		try {
			backgroundImage = new Image(new FileInputStream("Stages\\simulationimage\\"+ stagename + "\\day\\indoor\\1.png"));
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ImageView backgrcontainer = new ImageView(backgroundImage);
		
		backgrcontainer.setFitHeight(580);
		backgrcontainer.setFitWidth(580);
        BackgroundImage background = new BackgroundImage(
        		backgrcontainer.getImage(),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT
                //new BackgroundSize(20, 20, false, false, true, true)
        );
        root.setBackground(new Background(background));
       
        MenuBar menuBar = menuBar();
        //HBox timestamp = timestamp();
        
        Button play = new Button("▶");
        play.setOnAction(e-> runStage(primaryStage));
        play.setStyle("-fx-opacity: 0.5;");
        play.setFont(new Font("Arial",36));
        play.setTextFill(Color.LIGHTGREEN);
        Button accx5 = new Button("▶▶");
        accx5.setStyle("-fx-opacity: 0.5;");
        accx5.setFont(new Font("Arial",36));
        accx5.setTextFill(Color.LIGHTGREEN);
        accx5.setOnAction(e-> tempmodels.timeaccelerator= 5);
        Button accx10 = new Button("▶▶▶");
        accx10.setOnAction(e-> tempmodels.timeaccelerator= 10);
        accx10.setStyle("-fx-opacity: 0.5;");
        accx10.setFont(new Font("Arial",36));
        accx10.setTextFill(Color.LIGHTGREEN);
        
        HBox accelerators = new HBox(2);
        accelerators.getChildren().addAll(accx5, accx10);
        accelerators.setLayoutX(500);
        accelerators.setLayoutY(100);
        Label timestamp = new Label(CentralClock.time);
        
        timestamp.setLayoutX(190);
        timestamp.setLayoutY(520);
        Platform.runLater(()->conc.scheduleAtFixedRate(() -> Platform.runLater(()->timestamp.setText(CentralClock.time)),0,3000, TimeUnit.MILLISECONDS));
        weather.setLayoutX(60);
        weather.setLayoutY(420);
        play.setLayoutX(620);
        play.setLayoutY(590);
        
        menuBar.setLayoutY(0);
        
        conditionants(weather, play);

        root.getChildren().add(play);root.getChildren().add(accelerators);root.getChildren().add(timestamp);
        root.getChildren().add(menuBar);
        root.getChildren().add(weather);
   
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(e->System.exit(0));
    }

    private MenuBar menuBar() {
        MenuBar menuBar = new MenuBar();
        menuBar.setStyle("-fx-background-color: rgb(80,120,10);");
        
      
        Menu fileMenu = new Menu("File");
        MenuItem startWriting = new MenuItem("Start data writing");
        MenuItem startUpdate = new MenuItem("Start data update");
        MenuItem launchWindPanel = new MenuItem("Launch Windpanel");
        MenuItem setBounds = new MenuItem("Set bounds");
        
        
        startWriting.setOnAction(e ->{ GeneralControls.startWriting(); infoPane("Data being written now", 5000);});
        startUpdate.setOnAction(e -> GeneralControls.labelupdate());
        setBounds.setOnAction(e -> RandomWindModels.boundSetter(RandomWindModels.UPPER_BOUND,RandomWindModels.LOWER_BOUND, 
                RandomWindModels.W_MINmax,RandomWindModels.W_MINmin,RandomWindModels.W_MAXmax,RandomWindModels.W_MAXmin,
                RandomWindModels.DURATION, RandomWindModels.MINIMALDIFBOUNDS));
        
        fileMenu.getItems().addAll(startWriting, startUpdate, launchWindPanel, setBounds);
        
        
        Menu plotsMenu = new Menu("Plots");
        MenuItem liveWindPlot = new MenuItem("Launch live wind Plot");
        MenuItem liveWindPlotJF = new MenuItem("Launch live wind Plot JFreeChart");
        MenuItem liveTempPlot = new MenuItem("Launch live temperature Plot");
        MenuItem liveTempPlotJF = new MenuItem("Launch live temperature Plot JFreeChart");
        liveWindPlot.setOnAction(e -> Platform.runLater(() -> threadwrap(() -> launchLiveWindPlot())));
        liveTempPlot.setOnAction(e ->Platform.runLater(() ->  threadwrap(() -> launchLiveTempPlot())));
        liveWindPlotJF.setOnAction(e -> threadwrap(() -> launchLiveWindPlotJF()));
        liveTempPlotJF.setOnAction(e -> threadwrap(() -> launchLiveTempPlotJF()));
        
        plotsMenu.getItems().addAll( liveWindPlot, liveWindPlotJF, liveTempPlot, liveTempPlotJF);
       
        Menu windMenu = new Menu("Random Wind Models");
        MenuItem modelOne = new MenuItem("Random based, wind Model One");
        MenuItem modelOnePrompt = new MenuItem("Less random based, wind Model Two");
        MenuItem modelTwo = new MenuItem("Random based, wind Model Two");
        MenuItem modelThree = new MenuItem("Random based, wind Model Three");
        MenuItem setBoundsWind = new MenuItem("Set bounds");
        
        modelOne.setOnAction(e -> Platform.runLater(()->menubar.launchModelOneFX()));
        modelOnePrompt.setOnAction(e -> promptforWindModelOne());
        modelTwo.setOnAction(e -> menubar.launchModelTwoFX());
        modelThree.setOnAction(e -> menubar.launchModelThreeFX());
        setBoundsWind.setOnAction(e -> menubar.argsFrame(null,null));
        
        windMenu.getItems().addAll(modelOne, modelOnePrompt, modelTwo, modelThree, setBoundsWind);
        
        
        Menu tempMenu = new Menu("Temperature");
        MenuItem TempModelTwo = new MenuItem("Temperature Model One");
        MenuItem tempModelTwo = new MenuItem("Temperature Model Two");
        TempModelTwo.setOnAction(e -> runTmpModelOne());
        tempModelTwo.setOnAction(e -> runTmpModelTwo());
        tempMenu.getItems().addAll(TempModelTwo,tempModelTwo);
        
       
        Menu configMenu = new Menu("Launch Configurations");
        MenuItem lowChangeWarm = new MenuItem("T Model One Low Change Warm");
        MenuItem lowChangeHot = new MenuItem("T Model One Low Change Hot");
        MenuItem lowChangeCold = new MenuItem("T Model One Low Change Cold");
        MenuItem twodesert = new MenuItem("T Model Two Desert");
        MenuItem twonormal = new MenuItem("T Model Two Normal");
        MenuItem twowet = new MenuItem("T Model Two Wet");
        
        lowChangeWarm.setOnAction(e -> LaunchConfigurations.ModelOnelowChangeWarm());
        lowChangeHot.setOnAction(e -> LaunchConfigurations.ModelOnelowChangeHot());
        lowChangeCold.setOnAction(e -> LaunchConfigurations.ModelOnelowChangeCold());
        twodesert.setOnAction(e -> LaunchConfigurations.ModelTwoDesert());
        twonormal.setOnAction(e -> LaunchConfigurations.ModelTwoNormal());
        twowet.setOnAction(e -> LaunchConfigurations.ModelTwoWet());
        
        configMenu.getItems().addAll(lowChangeWarm, lowChangeHot, lowChangeCold, twodesert, twonormal, twowet);
        
        menuBar.getMenus().addAll(fileMenu, windMenu, tempMenu, plotsMenu, configMenu);
        
        return menuBar;
    }


    private StackPane labelsBox() {
    	VBox tempBox = new VBox(5);
        VBox windBox = new VBox(5);            

        Label [] labels = {new Label(),new Label(),new Label(),new Label(),new Label()};
        for(Label label : labels) {label.setFont(Font.font("Calibri", 12));//tmp
        label.setTextFill(Color.BLACK);};
        Label [] wndlabels = {new Label(),new Label(),new Label(),};
        for(Label label : wndlabels) {label.setTextFill(Color.BLACK);
        label.setFont(Font.font("Calibri", 12));};
        
        
        AtomicReference<Double> atohum = new AtomicReference<>();
        AtomicReference<Double> atorain = new AtomicReference<>();
        AtomicReference<Double> atotemp = new AtomicReference<>();
        ScheduledExecutorService updater = Executors.newScheduledThreadPool(1);
        updater.scheduleAtFixedRate(() -> {
        	if(tempmodels.tempmodelind==1)atotemp.set(tempmodels.temp1);
        	if(tempmodels.tempmodelind==2)atotemp.set(TempModelTwo.temp1);
            atotemp.get();
            atorain.set(Rain.rain);
        	atorain.get();
            rain = atorain.get();
            temp = atotemp.get();
            if(tempmodels.tempmodelind==1)atohum.set(tempmodels.hum1);
        	if(tempmodels.tempmodelind==2)atohum.set(TempModelTwo.hum1);
            atohum.get();
            hum = atohum.get();
            Platform.runLater(() ->{ labels[0].setText(String.format("%.2f °C", temp));
            labels[1].setText(String.format("%.1f %%", hum));
            labels[2].setText(String.format("%.1f mm", rain));
            labels[3].setText(String.format("%.1f mb", PressureSunradiation.pressure));
            labels[4].setText(String.format("%.1f W/m2", PressureSunradiation.solrad));
            });
        }, 0, 2000, TimeUnit.MILLISECONDS);
        AtomicReference<Double> instantspeed = new AtomicReference<>();
        
        AtomicReference<Double> twominavgspd = new AtomicReference<>();
        AtomicReference<Double> fiveminavgspd = new AtomicReference<>();
        
        // Quick updates (500ms)
        ScheduledExecutorService quicklabel = Executors.newScheduledThreadPool(1);
        quicklabel.scheduleAtFixedRate(() -> {
            try {
                if (windliveplot.wind == 1) {
                    instantspeed.set(RandomWindModels.quickwind);
                   
                    instaspd = instantspeed.get();
                    
                } else if (windliveplot.wind != 1) {
                    instantspeed.set(Wind.measures);
                  
                    instaspd = instantspeed.get();
               
                }
               
                Platform.runLater(() -> {if (!stagename.equals(stages[0]))wndlabels[0].setText(String.format("%.1f km/h", instaspd));
                else wndlabels[0].setText(String.format("%.1f km/h", instaspd));});
            } catch (Exception e) {
                Platform.runLater(() -> infoPane(e.getMessage(), 10000));
            }
        }, 0, 500, TimeUnit.MILLISECONDS);
        ScheduledExecutorService clock = Executors.newScheduledThreadPool(1);
        clock.scheduleAtFixedRate(() -> {
            try {
              time = LocalDateTime.now();
                threadwrap(()-> {hour = time.getHour();
                image = imageSetter(); });
                
            } catch (Exception e) {
                Platform.runLater(() -> infoPane(e.getMessage(), 10000));
            }
        }, 0, 1, TimeUnit.HOURS);
        
        ScheduledExecutorService tempupdater = Executors.newScheduledThreadPool(1);
        tempupdater.scheduleAtFixedRate(() -> {
            twominavgspd.set(GeneralControls.twominavg);
            twominavgspd.get();
            twominspd = twominavgspd.get();
            
            fiveminavgspd.set(GeneralControls.fiveminavg);
            fiveminavgspd.get();
            fiveminspd = fiveminavgspd.get();
           
            Platform.runLater(() ->  {if (!stagename.equals(stages[0]))wndlabels[1].setText(String.format("%.1f km/h",GeneralControls.twominavg));
            else wndlabels[1].setText(String.format("%.1f km/h", twominspd));
        if (!stagename.equals(stages[0]))wndlabels[2].setText(String.format("%.1f km/h", GeneralControls.fiveminavg));
        else wndlabels[2].setText(String.format("%.1f km/h", fiveminspd));});
        }, 0, 2000, TimeUnit.MILLISECONDS);
        
        tempBox.getChildren().addAll( labels);
        windBox.getChildren().addAll( wndlabels);
        HBox weatherlb = new HBox(10);
        weatherlb.getChildren().addAll(tempBox, windBox);
        StackPane weather = new StackPane(weatherlb);
       
        return weather;
        
    }
    
    static String [] stages = {"Hilltop","Monsoonic Beach","Oceanic Land"
			,"Dry Desert", "Cold Steepes"};
	static String stagename = "Simulated";
	private Stage runStage(Stage stage) {
		TabPane pane = new TabPane();
		
		Scene scene = new Scene(pane, 590,600);
		for(int i = 0; i<stages.length; i++) {
		
		Tab centb = new Tab(stages[i]);
		
		Pane centbcont = new Pane();
		TextArea textCont = new TextArea();
		List <Label> labels = new ArrayList<>();
		VBox text = new VBox(5);
		Image backgroundImage = null;
		try {
			backgroundImage = new Image(new FileInputStream("Stages\\background\\"+ stages[i] +".png"));
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ImageView backgrcontainer = new ImageView(backgroundImage);
		
		backgrcontainer.setFitHeight(580);
		backgrcontainer.setFitWidth(580);
        BackgroundImage background = new BackgroundImage(
        		backgrcontainer.getImage(),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                new BackgroundPosition(null, 0,false, null, 0, false),
                new BackgroundSize(20, 20, false, false, true, true)
        );
       
        centbcont.setBackground(new Background(background));
		try {
			
			BufferedReader reader = new BufferedReader(new FileReader("Stages\\" +stages[i] +".txt"), 1);
			List <String>lines = reader.lines().toList();			
			for(int j = 0; j<lines.size(); j++) {
				labels.add(new Label(lines.get(j)));
				labels.get(j).setTextFill(Color.WHITE);
				
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Button launch = new Button("Launch");
		//launch.setStyle("-fx-background-color:");
		String css = Dashboard.class.getResource("style.css").toExternalForm();
		centbcont.getChildren().add(launch);
		launch.setLayoutX(470);
		launch.getStyleClass().add("launch");
		launch.setOnAction(e -> {Platform.runLater(()->{stagename = centb.getText(); System.out.println(stagename); start(stage);});});
		launch.setLayoutY(550);
        
		text.getChildren().addAll(labels);
		//textCont.setText(text);
		centbcont.getChildren().add(text);
		pane.getTabs().add(centb);
		scene.getStylesheets().add(css);
		centb.setContent(centbcont);};
		
	
		stage.setOnCloseRequest(e->System.exit(0));
		stage.setScene(scene);
		stage.show();
		return stage;
		
	}
    
    private static Image imageSetter() {
    	String prairedaysunnyday = "loops/real/prairie/day/sunny/";
    	String hilltopdsd = "loops/real/hilltop/day/scatteredclouds/";
    	String hilltopnsd = "loops/real/hilltop/night/clear/";
    	String desertdsa = "loops/real/desert/afternoon/";
    	String [] prairedsdimgs = {"20250520_0355_Vast Tranquil Sky_loop_01jvnpekg8ez59w4ea46t20edz.gif"};
    	String [] hilltopnsdimgs = {"1.gif","2.gif","3.gif","4.gif"};
    	String [] hilltopdsdimgs = {"1.gif","2.gif","3.gif"};
    	String [] desertdsaimgs = {"1.gif","2.gif"};
    
    	//Desert day
    	if(TempModelTwo.desertindex == 3)image = new Image("file:"+ desertdsa +
    			desertdsaimgs[CustomRandomInt.customRndInt(desertdsaimgs)]);
    	//Hilltop day
    	if(RandomWindModels.UPPER_BOUND/ RandomWindModels.LOWER_BOUND < 2
    			&& !(TempModelTwo.desertindex < 2)) {windowheight= 480;image = new Image("file:"+ hilltopdsd +
    			hilltopdsdimgs[CustomRandomInt.customRndInt(hilltopdsdimgs)]);}
    	//Praire day
    	if(RandomWindModels.UPPER_BOUND/ RandomWindModels.LOWER_BOUND > 2
    			&& !(TempModelTwo.desertindex < 2))image = new Image("file:"+ prairedaysunnyday +
    					prairedsdimgs[CustomRandomInt.customRndInt(prairedsdimgs)]);
    	//default hilltop
    	else {windowheight= 480; ;if(hour > 7 && hour <20)image = new Image("file:"+ hilltopdsd +
    			hilltopdsdimgs[CustomRandomInt.customRndInt(hilltopdsdimgs)]); 
    	if(hour > 20 || hour <8)image = new Image("file:"+ hilltopnsd +
    			hilltopnsdimgs[CustomRandomInt.customRndInt(hilltopnsdimgs)]);
    	}
    	
    	return image;
    }
    private static HBox timestamp() {
    	AtomicReference<Integer> hourref = new AtomicReference<>();
    	AtomicReference<Integer> dayref = new AtomicReference<>();
    	
    	Label [] labels = {new Label(),new Label(),new Label(),};
    	HBox timestamp = new HBox(5);
    	ScheduledExecutorService timeupdater = Executors.newScheduledThreadPool(1);
        timeupdater.scheduleAtFixedRate(() -> {if(tempmodels.tempmodelind==1){hourref.set(CentralClock.hour); lochour= hourref.get(); labels[0].setText(tempmodels.day + " day");
    	labels[1].setText(" "+lochour);
    	labels[2].setText(":" + tempmodels.minute);}
    	if(tempmodels.tempmodelind==2) {dayref.set(TempModelTwo.hour); locday= dayref.get();hourref.set(TempModelTwo.hour); lochour=hourref.get();labels [0].setText(locday + " day");
    	labels[1].setText(" "+lochour);
    	labels[2].setText(":" + TempModelTwo.minute);}},0, 5, TimeUnit.SECONDS);
    	timestamp.getChildren().addAll(labels);
    	return timestamp;
    }
    ScheduledExecutorService conc = Executors.newScheduledThreadPool(7);
    private void conditionants(StackPane weather, Button play){
    	//Once called for labelUpdate
    	conc.scheduleAtFixedRate(() -> {
	        GeneralControls.labelupdate();
	 },0, 50, TimeUnit.DAYS);
    	conc.scheduleAtFixedRate(() -> {
    	if( stagename.equals(stages[0])){  weather.setLayoutX(40);
        weather.setLayoutY(420);
        play.setLayoutX(620);
        play.setLayoutY(590);weather.setRotate(1);
        stg0Run();
        if( stagename.equals(stages[1])){ weather.setLayoutX(270);
        weather.setLayoutY(340);
        play.setLayoutX(620);
        play.setLayoutY(590);
        stg1Run();
        };
        if( stagename.equals(stages[2])){weather.setLayoutX(470);
        weather.setLayoutY(280);
        play.setLayoutX(620);
        play.setLayoutY(640);
        stg2Run();
        };
        if( stagename.equals(stages[3])){weather.setLayoutX(280);
        weather.setLayoutY(360);
        play.setLayoutX(620);
        play.setLayoutY(640);
        };
        if( stagename.equals(stages[4])){weather.setLayoutX(40);
        weather.setLayoutY(260);
        play.setLayoutX(620);
        play.setLayoutY(640);windliveplot.wind = 2;
        stg4Run();}
        if (stagename.equals("Simulated")){weather.setLayoutX(60);
        weather.setLayoutY(420);
        play.setLayoutX(620);
        play.setLayoutY(590);}
    	}
    	},0, 50, TimeUnit.DAYS);
    }

	public static void schedulerWrap(Runnable runnable, long interval) {
    	ScheduledExecutorService updater= Executors.newScheduledThreadPool(1);
    	updater.scheduleAtFixedRate(runnable, 0, interval, TimeUnit.MILLISECONDS);
    	}
    public static void schedulerWraptwo(Runnable runnable, long interval) {
    	ScheduledExecutorService updater= Executors.newScheduledThreadPool(1);
    	updater.scheduleAtFixedRate(runnable, 0, interval, TimeUnit.MILLISECONDS);
    	}
    	private static Thread threadwrap(Runnable runnable) {
    		Thread thread = new Thread(runnable
    				);
    		thread.start();
    		return thread;
    	}
    	 private double promptforModelOne() {
    	        TextInputDialog dialog = new TextInputDialog();
    	        dialog.setTitle("Temperature Input");
    	        dialog.setHeaderText("Starting temperature for temperature model");
    	        dialog.setContentText("Please enter temperature:");
    	        
    	        return Double.parseDouble(dialog.showAndWait().orElse("0"));
    	    }
    	private void promptforWindModelOne() {
            TextInputDialog dialog1 = new TextInputDialog();
            dialog1.setTitle("Wind Model Input");
            dialog1.setHeaderText("How would you like to set the increment of temperature in integers?");
            dialog1.setContentText("Enter increment:");
            double input = Double.parseDouble(dialog1.showAndWait().orElse("1"));
            
            TextInputDialog dialog2 = new TextInputDialog();
            dialog2.setTitle("Time Input");
            dialog2.setHeaderText("How long in minutes should be such increment taken as parameter?");
            dialog2.setContentText("Enter minutes:");
            long input2 = Long.parseLong(dialog2.showAndWait().orElse("5"));
            
            Wind.commerceWindModelOne(input, input2);
            try {
				infoPane("Running Wind Model One now", 5000);
			} catch (Exception e) {
		
				e.printStackTrace();
			}
        }
    	private void launchLiveTempPlot() {
    		windliveplot.wind = 3;
    		Platform.runLater(()->windliveplot.liveWindPlot(new Stage()));
    	}
    	private  void launchLiveWindPlot() {
    		if(Wind.measures != 0)windliveplot.wind = 2;
    		else windliveplot.wind = 1;
    		Platform.runLater(()->windliveplot.liveWindPlot(new Stage()));
    	}
    	private  void launchLiveTempPlotJF() {
    		windliveplot.wind = 3;
    		JFreeChartLiveWind.liveWindPlot();
    	}
    	private void launchLiveWindPlotJF() {
    		if(Wind.measures != 0)windliveplot.wind = 2;
    		else windliveplot.wind =1;
    		JFreeChartLiveWind.liveWindPlot();
    	}
    	private void runTmpModelOne() {
    		//tempmodels.modelOneAsHour(promptforModelOne());
    	}
    	private void runTmpModelTwo() {
    		int desertindex = 2; 
    		if(TempModelTwo.desertindex <1)  TempModelTwo.modelTwo(promptforModelOne(), desertindex);
    		else TempModelTwo.modelTwo(promptforModelOne(), TempModelTwo.desertindex);
    	}

    static void infoPane(String message, long duration) {
     
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
        
        alert.show();
        
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.schedule(() -> Platform.runLater(() -> alert.close()), duration, TimeUnit.MILLISECONDS);
    }
    private static double refAction(AtomicReference<Double> ref, double number) {
		ref.set(number);
		ref.get();
		double retnumber = ref.get();
		return retnumber;
	}
    FileWriter data; ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    void launchOp() throws FileNotFoundException{
    	 Random random =CentralClock.rd ;
   	 try {
			BufferedReader rd = new BufferedReader(new FileReader("log\\launch.info"));
			 String text = rd.readLine();
			 rd.close();
			 if(text!=null) {
				 BufferedReader rd2 = new BufferedReader(new FileReader("log\\data.csv"));
				String [] vl = rd2.readLine().split(";");
				rd2.close();
				temp = Double.parseDouble(vl[0]);
				hum = Double.parseDouble(vl[1]);
				Wind.measures = Double.parseDouble(vl[2]);
				RandomWindModels.quickwind =  Double.parseDouble(vl[2]);
				PressureSunradiation.pressure = Double.parseDouble(vl[3]);
				Rain.rain  = Double.parseDouble(vl[4]);
				 PressureSunradiation.solrad = Double.parseDouble(vl[5]);
				 FileWriter writer = new FileWriter("log\\launch.info",false);
					writer.write("");
					writer.close();
			 if (text.equals(stages[0]) ) scheduler.scheduleAtFixedRate(() -> { stagename= text;stg0Run();}, 0, 25, TimeUnit.DAYS);
			 if (text.equals(stages[1]) ) scheduler.scheduleAtFixedRate(() -> { stagename= text;stg1Run();}, 0, 25, TimeUnit.DAYS);
			 if (text.equals(stages[2]) ) scheduler.scheduleAtFixedRate(() -> { stagename= text;stg2Run();}, 0, 25, TimeUnit.DAYS);
			 if (text.equals(stages[3]) ) scheduler.scheduleAtFixedRate(() -> { stagename= text;stg3Run();}, 0, 25, TimeUnit.DAYS);}
			 else stagename = stages[random.nextInt(4)];
			
			
			    
		} catch (FileNotFoundException e) {
		
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}}
   	private void stg4Run() {
		windliveplot.wind = 2;tempmodels.upperbound = 52; tempmodels.lowerbound= -20;
		tempmodels.hum1= 20;
		conc.scheduleAtFixedRate(() -> {
		tempmodels.modelOneDay(18);},0, 50, TimeUnit.DAYS); 
		tempmodels.hum1= 30;
		conc.scheduleAtFixedRate(() -> {Wind.commerceWindModelOne(1, 5);},0, 50, TimeUnit.DAYS); 
		
	
	}

	private void stg3Run() {
		windliveplot.wind = 2;tempmodels.upperbound = 52; tempmodels.lowerbound= -20;
		tempmodels.hum1= 20;
		conc.scheduleAtFixedRate(() -> {
		tempmodels.modelOneDay(18);},0, 50, TimeUnit.DAYS); tempmodels.hum1= 30;Wind.commerceWindModelOne(1, 5);
		
	}

	private void stg2Run() {
		
		        conc.scheduleAtFixedRate(() -> {
		        tempmodels.modelOneDay(18); 
		        Wind.commerceWindModelOne(2, 20);
		        },0, 50, TimeUnit.DAYS);
	}

	private void stg1Run() {
		RandomWindModels.UPPER_BOUND = 15;TempModelTwo.upperbound = 44; TempModelTwo.lowerbound= 6; RandomWindModels.BOUNDSDURATION =4555;
        RandomWindModels.W_MAXmax=120;	
        RandomWindModels.W_MINmin=0;	RandomWindModels.LOWER_BOUND=3;
        conc.scheduleAtFixedRate(() -> { TempModelTwo.modelTwo(27, 1);},0, 50, TimeUnit.DAYS);
        conc.scheduleAtFixedRate(() -> {RandomWindModels.launchVersionOne(0.99, 0.99,  10); },0, 50, TimeUnit.DAYS);
    	
       
	}

	private void stg0Run() {
		RandomWindModels.UPPER_BOUND = 23;RandomWindModels.LOWER_BOUND=5;TempModelTwo.upperbound = 35; TempModelTwo.lowerbound= -20; RandomWindModels.BOUNDSDURATION =4555;
		RandomWindModels.W_MINmin = 1;
       	conc.scheduleAtFixedRate(() -> {
    	   RandomWindModels.launchVersionOne(0.997, 0.997,  6); 
       	},0, 50, TimeUnit.DAYS);
       
       	conc.scheduleAtFixedRate(() -> {
        TempModelTwo.modelTwo(17, 1);
       	},0, 50, TimeUnit.DAYS);
	}
}