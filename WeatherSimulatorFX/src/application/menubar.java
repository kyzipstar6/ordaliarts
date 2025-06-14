package application;
import java.awt.Color;
import java.awt.Font;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import application.RandomWindModels.*;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javafx.stage.Stage;

public class menubar extends JFrame {
	static Thread thread;
	static Thread thread2;
	
	private static double args1 =RandomWindModels.STABILIZED_FILTER_RATIO;
	private static double args2=RandomWindModels.RATIO_WSPEEDS;
//	private static double args1 ;
//	private static double args2;
//	
//	
	private static AtomicReference<Double> wmaxmax = new AtomicReference<>();
	private static AtomicReference<Double> wmaxmin = new AtomicReference<>();
	private static AtomicReference<Double> wminmax = new AtomicReference<>();
	private static AtomicReference<Double> wminmin = new AtomicReference<>();
	private static AtomicReference<Double> upbound = new AtomicReference<>();
	private static AtomicReference<Double> lowbound = new AtomicReference<>();
	private static AtomicReference<Double> ratio = new AtomicReference<>();
	private static AtomicReference<Double> ran = new AtomicReference<>();
	private static AtomicReference<Double> stab = new AtomicReference<>();
	private static AtomicReference<Double> fil = new AtomicReference<>();
	private static AtomicReference<Long> frec = new AtomicReference<>();
	private static AtomicReference<Long> dur = new AtomicReference<>();
	
	private static double dwmaxmax;
	private static double dwmaxmin;
	private static double dwminmax;
	private static double dwminmin;
	private static double dupbound;
	private static double dlowbound;
	private static double dratio;
	private static double dran;
	private static double dstab;
	private static double dfil;
	private static long dfrec;
	private static long ddur;
	
	private static JLabel [] labels = {new JLabel("Set Max Max wind"), new JLabel("Set Max Min wind"),new JLabel("Set Min Max wind"),
			new JLabel("Set Min Min wind "),new JLabel("Set Upper Bound") ,new JLabel("Set Lower Bound"),new JLabel("Set Filter difference"), new JLabel("Set Deviation difference"),
			new JLabel("Set frequence"), new JLabel("Set bounds duration")};
	private static JTextArea [] textareas = {new JTextArea(Double.toString(RandomWindModels.W_MAXmax)),new JTextArea(dbtostr(RandomWindModels.W_MAXmin)),new JTextArea(dbtostr(RandomWindModels.W_MINmax)),
			new JTextArea(dbtostr(RandomWindModels.W_MINmin)),new JTextArea(dbtostr(RandomWindModels.UPPER_BOUND)),new JTextArea(dbtostr(RandomWindModels.LOWER_BOUND))
			,new JTextArea(dbtostr(args1)),
			new JTextArea(dbtostr(args2)),new JTextArea(dbtostr(RandomWindModels.DURATION)),new JTextArea(dbtostr(RandomWindModels.BOUNDSDURATION))};
	private static Label [] labelsfx = {new Label("Set Max Max wind"), new Label("Set Max Min wind"),new Label("Set Min Max wind"),
			new Label("Set Min Min wind "),new Label("Set Upper Bound") ,new Label("Set Lower Bound"),new Label("Set Filter difference"), new Label("Set Deviation difference"),
			new Label("Set frequence"), new Label("Set bounds duration")};
	private static TextArea [] textareasfx = {new TextArea(Double.toString(RandomWindModels.W_MAXmax).replaceAll(".0", "")),new TextArea(dbtostr(RandomWindModels.W_MAXmin)),new TextArea(dbtostr(RandomWindModels.W_MINmax)),
			new TextArea(dbtostr(RandomWindModels.W_MINmin)),new TextArea(dbtostr(RandomWindModels.UPPER_BOUND)),new TextArea(dbtostr(RandomWindModels.LOWER_BOUND))
			,new TextArea(dbtostr(args1)),
			new TextArea(dbtostr(args2)),new TextArea(dbtostr(RandomWindModels.DURATION)),new TextArea(dbtostr(RandomWindModels.BOUNDSDURATION))};
	private static double [] arguments = {RandomWindModels.W_MAXmax,RandomWindModels.W_MAXmin, RandomWindModels.W_MINmax,RandomWindModels.W_MINmin,RandomWindModels.UPPER_BOUND,RandomWindModels.LOWER_BOUND,
			args1, args2};
	private static long [] longargs = {RandomWindModels.DURATION, RandomWindModels.BOUNDSDURATION};
	
	static int argsnumber;
	
	static JFrame argsFrame(Box parametersBox, JButton button) {
		Dashboard.dataFromWModels = true;
		JFrame frame = new JFrame();
		Box argsbox = argsBox(button);
		frame.getContentPane().add(argsbox);
		frame.pack();
		frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		frame.setVisible(true);
		return frame;
	}
	static Box argsBox(JButton button) {
		Box textareasbox = Box.createVerticalBox();
		for(int i =0; i< textareas.length; i++) {
			textareasbox.add(labels[i]);
			textareasbox.add(textareas[i]);
		}
		
		textareasbox.add(button);
		return textareasbox;
	}
		static JButton basicButton() {
			JButton button	= new JButton("Launch");
			button.addActionListener(e->{
			for(int i =0; i< arguments.length; i++) {
				arguments[i] = Double.parseDouble(textareas[i].getText());
				dwmaxmax = refAction(wmaxmax, arguments[0]);
				RandomWindModels.W_MAXmax = dwmaxmax;
				dwmaxmin = refAction(wmaxmin, arguments[1]);
				RandomWindModels.W_MAXmin = dwmaxmin;
				dwminmax = refAction(wminmax, arguments[2]);
				RandomWindModels.W_MINmax = dwminmax;
				dwminmin = refAction(wminmin, arguments[3]);
				RandomWindModels.W_MINmin = dwminmin;
				dupbound = refAction(upbound, arguments[4]);
				RandomWindModels.UPPER_BOUND = dupbound;
				dlowbound = refAction(lowbound, arguments[5]);
				RandomWindModels.LOWER_BOUND = dlowbound;
				if(args1== RandomWindModels.FILTER_RATIO)dfil =refAction(fil, arguments[6]);
				if(args1== RandomWindModels.RATIO_WSPEEDS)dratio = refAction(ratio, arguments[6]);
				if(args1== RandomWindModels.STABILIZED_FILTER_RATIO)dstab = refAction(stab, arguments[6]);
				
				if(args2== RandomWindModels.STABILIZED_FILTER_RATIO)dstab = refAction(stab, arguments[7]);
				if(args2== RandomWindModels.DIFERENCE_RANDOMS)dran = refAction(ran, arguments[7]);
				RandomWindModels.STABILIZED_FILTER_RATIO= dstab;
				RandomWindModels.DIFERENCE_RANDOMS = dran;
				RandomWindModels.RATIO_WSPEEDS = dratio;
			}
			for(int i =0; i< longargs.length; i++) {
				longargs[i] = Long.parseLong(textareas[textareas.length-1].getText().replaceAll(".0", ""));
				dfrec = (long) refActionLong(frec, longargs[0]);
				RandomWindModels.DURATION = dfrec;
				
				ddur = (long) refActionLong(dur, longargs[1]);
				RandomWindModels.BOUNDSDURATION = ddur;
				}
							
		});
			return button;
		
		
	}
		static Button basicButtonfx(Stage stage) {
			Button button	= new Button("Launch");
			button.setOnAction(e->{
			for(int i =0; i< arguments.length; i++) {
				arguments[i] = Double.parseDouble(textareas[i].getText());
				dwmaxmax = refAction(wmaxmax, arguments[0]);
				RandomWindModels.W_MAXmax = dwmaxmax;
				dwmaxmin = refAction(wmaxmin, arguments[1]);
				RandomWindModels.W_MAXmin = dwmaxmin;
				dwminmax = refAction(wminmax, arguments[2]);
				RandomWindModels.W_MINmax = dwminmax;
				dwminmin = refAction(wminmin, arguments[3]);
				RandomWindModels.W_MINmin = dwminmin;
				dupbound = refAction(upbound, arguments[4]);
				RandomWindModels.UPPER_BOUND = dupbound;
				dlowbound = refAction(lowbound, arguments[5]);
				RandomWindModels.LOWER_BOUND = dlowbound;
				if(args1== RandomWindModels.FILTER_RATIO)dfil =refAction(fil, arguments[6]);
				if(args1== RandomWindModels.RATIO_WSPEEDS)dratio = refAction(ratio, arguments[6]);
				if(args1== RandomWindModels.STABILIZED_FILTER_RATIO)dstab = refAction(stab, arguments[6]);
				
				if(args2== RandomWindModels.STABILIZED_FILTER_RATIO)dstab = refAction(stab, arguments[7]);
				if(args2== RandomWindModels.DIFERENCE_RANDOMS)dran = refAction(ran, arguments[7]);
				RandomWindModels.STABILIZED_FILTER_RATIO= dstab;
				RandomWindModels.DIFERENCE_RANDOMS = dran;
				RandomWindModels.RATIO_WSPEEDS = dratio;
				
			}
			for(int i =0; i< longargs.length; i++) {
				longargs[i] = Long.parseLong(textareas[textareas.length-1].getText().replaceAll(".0", ""));
				dfrec = (long) refActionLong(frec, longargs[0]);
				RandomWindModels.DURATION = dfrec;
				
				ddur = (long) refActionLong(dur, longargs[1]);
				RandomWindModels.BOUNDSDURATION = ddur;
				}
			stage.getWindows().clear();
		});
			return button;
		
		
	}
		 static Stage dialog(Button button) {
	    	Stage stage= new Stage();

	    	DialogPane content= new DialogPane();
	    	FlowPane inner = new FlowPane();
	    	VBox labels = new VBox(25);
	    	VBox areas = new VBox(5);
	    	HBox lblsareas = new HBox(5);
	    	lblsareas.getChildren().addAll(labels,areas);
	    	
	    	for(int i =0; i< textareas.length; i++) {
				labels.getChildren().addAll(labelsfx[i]);
				textareasfx[i].setPrefSize(64, 12);
				labelsfx[i].setFont(new javafx.scene.text.Font("Verdana",14));
				areas.getChildren().addAll(textareasfx[i]);
			}
	    	areas.getChildren().add(button);
	    	inner.getChildren().addAll(lblsareas);
	    	content.getChildren().addAll(inner);
	    	Scene scene = new Scene(content, 340,490);
	    	stage.setScene(scene);
	    	stage.setOnCloseRequest(e->stage.close());
	    	stage.show();
	    	
	    	return stage;
	    }	
		
	static String dbtostr (double number) {
		String stringnumber = Double.toString(number).replaceAll(".0", "");
		return stringnumber;
	}
	static double refAction(AtomicReference<Double> ref, double number) {
		ref.set(number);
		ref.get();
		double retnumber = ref.get();
		return retnumber;
	}
	static double refActionLong(AtomicReference<Long> ref, long number) {
		ref.set(number);
		ref.get();
		double retnumber = ref.get();
		return retnumber;
	}
		
		private static void standardLaunch() {
			Platform.runLater(()->{
			textareas[8].setText(""+args2);
			textareas[8].setText(""+args2);
			GeneralControls.labelupdate();
			});
		}
		private static Thread threadwrap(Runnable runnable) {
			Thread thread = new Thread(runnable
					);
			return thread;
		}
		public static void main(String[] args) {
			launchModelOne();
		}
		static JFrame launchModelOne() {
			args1= RandomWindModels.STABILIZED_FILTER_RATIO;
			args2= RandomWindModels.RATIO_WSPEEDS;
			windliveplot.wind = 1;
			standardLaunch();
			JButton button = basicButton();
			button.addActionListener(e -> RandomWindModels.launchVersionOne(args1, args2, RandomWindModels.DURATION));
			Box box = argsBox(button);
			JFrame frame = argsFrame(box, button);
			
			
			return frame;
		}
		static Stage launchModelOneFX() {
			args1= RandomWindModels.STABILIZED_FILTER_RATIO;
			args2= RandomWindModels.RATIO_WSPEEDS;
			windliveplot.wind = 1;
			standardLaunch();
			Stage stage = new Stage();
			Button button = basicButtonfx(stage);
			button.setOnAction(e -> RandomWindModels.launchVersionOne(args1, args2, RandomWindModels.DURATION));
			dialog(button);
			
			return stage;
		}
		static JFrame launchModelTwo() {
			args1 = RandomWindModels.FILTER_RATIO;
			args2 = RandomWindModels.DIFERENCE_RANDOMS;
			standardLaunch();
			windliveplot.wind = 1;
			JButton button = basicButton();
			button.addActionListener(e -> RandomWindModels.launchVersionTwo(args1, args2, RandomWindModels.DURATION));
			Box box = argsBox(button);
			JFrame frame = argsFrame(box, button);
			
			return frame;
		}
		static Stage launchModelTwoFX() {
			args1= RandomWindModels.STABILIZED_FILTER_RATIO;
			args2= RandomWindModels.RATIO_WSPEEDS;
			windliveplot.wind = 1;
			standardLaunch();
			Stage stage = new Stage();
			Button button = basicButtonfx(stage);
			button.setOnAction(e -> RandomWindModels.launchVersionOne(args1, args2, RandomWindModels.DURATION));
			dialog(button);
			
			return stage;
		}
		static JFrame launchModelThree() {
			args1 = RandomWindModels.STABILIZED_FILTER_RATIO;
			args2= RandomWindModels.RATIO_WSPEEDS;
			standardLaunch();
			windliveplot.wind = 1;
			JButton button = basicButton();
			button.addActionListener(e -> RandomWindModels.launchVersionThree(args1, args2, RandomWindModels.DURATION));
			Box box = argsBox(button);
			JFrame frame = argsFrame(box, button);
			
			return frame;
		}
		static Stage launchModelThreeFX() {
			args1= RandomWindModels.STABILIZED_FILTER_RATIO;
			args2= RandomWindModels.RATIO_WSPEEDS;
			windliveplot.wind = 1;
			standardLaunch();
			Stage stage = new Stage();
			Button button = basicButtonfx(stage);
			button.setOnAction(e -> RandomWindModels.launchVersionOne(args1, args2, RandomWindModels.DURATION));
			dialog(button);
			
			return stage;
		}
	static JFrame setBounds() {
		windliveplot.wind = 1;
		JButton button = basicButton();
		button.addActionListener(e -> RandomWindModels.launchVersionThree(args1, args2, RandomWindModels.DURATION));
		Box box = argsBox(button);
		JFrame frame = argsFrame(box, button);
		return frame;
	}
}
