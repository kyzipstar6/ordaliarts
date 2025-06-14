package application;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicBorders;

import application.RandomWindModels.*;
import javafx.stage.Stage;



public class GeneralControls extends JFrame {
	
	private static JFrame frame;
	static JLabel label;
	static JLabel label2;
	private static JLabel label3;
	private static JLabel label4;
	static String labeltext = "";
	static String labeltext2= "";
	static String labeltext3= "";
	static String labeltext4= "";
	static double twominavg =0;
	static double fiveminavg=0;
	static String FILEID= "nan";
	private static JTextField recordsList;
	private static JPanel mainpanel;
	private static JPanel labels;
	private static JPanel lablegend;
	private static JPanel buttons;
	static ArrayList <Double> records =null;
	public static int writingindex;
	static JPanel chartPanel;
	
	
	public static void main (String args[]) {
		
		createSwings("Wind Panel", new JFrame(), true);
		RandomWindModels.launchVersionOne(1.2, 1.4,2);
		labelupdate();
		RandomWindModels.boundSetter(RandomWindModels.UPPER_BOUND,RandomWindModels.LOWER_BOUND, RandomWindModels.W_MINmax,
				RandomWindModels.W_MINmin,RandomWindModels.W_MAXmax,RandomWindModels.W_MAXmin,
				RandomWindModels.DURATION, RandomWindModels.MINIMALDIFBOUNDS);
		startWriting();
		//Dashboard.frame();
		
}
	
	static JFrame createSwings(String title, JFrame frame2, boolean visible) {
		SwingUtilities.invokeLater(()->{
		frame = new JFrame(title + " Conditions");
		frame.setSize(1000,600);
		frame.setResizable(true);
		frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		frame.setBackground(new Color(68,86,33));
		
		
		Box verticalBox = Box.createVerticalBox();
		
		
		JMenu popup = new JMenu();
		popup.setText("Settings");
		popup.addActionListener(e -> ControlsFrame.ControlsFrame(new JFrame()));
		popup.setBackground(new Color(85,103,85));
		JMenuBar menubar = new JMenuBar();
		menubar.add(popup);
		menubar.setBackground(new Color(30,54,30));
		// Measured simulations
		label =new JLabel("N/A km/h");
		label.setBorder(new BasicBorders.MarginBorder());
		label2 =new JLabel("N/A km/h");
		label3 =new JLabel("N/A km/h");
		label4 =new JLabel("N/A km/h");
		label.setFont(new Font("Calibri", 22, 30));
		label2.setFont(new Font("Calibri", 22, 30));
		label3.setFont(new Font("Calibri", 22, 30));
		label4.setFont(new Font("Calibri", 22, 30));
		labels = new JPanel();
		labels.add(label);
        labels.add(label2);
        labels.add(label3);
        labels.add(label4);
        labels.setBackground(new Color(48,100,52));
		
		
		JTextField set = new JTextField();	
		set.setText("Type your selection here");
		JButton settings = new JButton("Settings");

		//Data Descriptors
		lablegend = new JPanel();
		lablegend.setBackground(new Color(11,92,12));
		JLabel legend1 =new JLabel();
		JLabel legend2 =new JLabel();
		JLabel legend3 =new JLabel();
		JLabel legend4 =new JLabel();
		legend1.setFont(new Font("Calibri", 22, 30));
		legend2.setFont(new Font("Calibri", 22, 30));
		legend3.setFont(new Font("Calibri", 22, 30));
		legend4.setFont(new Font("Calibri", 22, 30));
		legend1.setText("Instant");
		legend2.setText("   2 secs");
		legend3.setText("   2 min Avg");
		legend4.setText("   5 min Avg");
		legend2.setBackground(new Color(44,60,102));
		lablegend.add(legend1);
		lablegend.add(legend2);
		lablegend.add(legend3);
		lablegend.add(legend4);
		 	
		buttons = new JPanel();
		JButton launchinstant = new JButton("Launch Plot");

		launchinstant.addActionListener(e -> {
		windliveplot.liveWindPlot(new Stage());});
		settings.addActionListener(e -> {
			ControlsFrame.ControlsFrame(new JFrame());});
		
		buttons.add(set);		
		buttons.add(launchinstant);
		buttons.add(settings);
		
		verticalBox.add(lablegend);
		verticalBox.add(Box.createHorizontalStrut(10));
		verticalBox.add(labels);
		verticalBox.add(Box.createVerticalGlue());
		verticalBox.add(buttons);
	
		frame.getContentPane().add(verticalBox,BorderLayout.CENTER);
		frame.getContentPane().add(menubar, BorderLayout.NORTH);		
		frame.pack();
		frame.setVisible(visible);
		});
		
		return frame;
	}
	static void startWriting() {
		
		LocalDateTime time = LocalDateTime.now();
		String timestring = time.format(DateTimeFormatter.ofPattern("ddMMHHmm"));
        
		RandomWindModels.thread = new Thread( () -> {
			String  newfiletitle = "runhistory\\" +timestring ;
			File newfile = new File(newfiletitle);
			if(!newfile.exists())newfile.mkdirs();
				
		ScheduledExecutorService updater= Executors.newScheduledThreadPool(1);
		updater.scheduleAtFixedRate(() -> {
			
		try  (FileWriter writer = new FileWriter(newfile + ".csv", true)) {
			if( writingindex==1){ writer.append(String.join(";",
                    LocalDateTime.now().toString(),Double.toString(tempmodels.temp1), Double.toString(tempmodels.roundhum1),Double.toString(RandomWindModels.quickwind), Double.toString(twominavg), Double.toString(fiveminavg)));
            writer.append("\n");}
			if( writingindex==2){writer.append(String.join(";",
                    LocalDateTime.now().toString(),Double.toString(tempmodels.temp1), Double.toString(tempmodels.hum1),Double.toString(Wind.measures)));writer.append("\n");
			
			}
			if( writingindex==3){writer.append(String.join(";",
                    LocalDateTime.now().toString(),Double.toString(TempModelTwo.temp1), Double.toString(TempModelTwo.hum1),Double.toString(Wind.measures)));writer.append("\n");
			
			}
        } catch (IOException e) {
            e.printStackTrace();
        }
		//Dashboard.infoPane("Data being written now at " +newfile.getAbsolutePath() , 5000);
		}, 0, 2000, TimeUnit.MILLISECONDS);
		
		});
		try {
			RandomWindModels.thread.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	static void labelupdate() {
		try {
			records = new ArrayList<Double>();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ScheduledExecutorService updater= Executors.newScheduledThreadPool(1);
		updater.scheduleAtFixedRate(() -> {
		try {
			if(windliveplot.wind == 1) {records.add(RandomWindModels.quickwind);	}
				if(windliveplot.wind == 2) { records.add(Wind.measures); }
		} catch (Exception e) {
			// TODO Auto-generated catch block1
			infoPane(e.getMessage(), 10000);
		}}, 0, 2000, TimeUnit.MILLISECONDS);
		
		

		ScheduledExecutorService updater3= Executors.newScheduledThreadPool(1);		
		updater3.scheduleAtFixedRate(() -> {
			
			
			try {
				if(RandomWindModels.quickwind< RandomWindModels.UPPER_BOUND && RandomWindModels.quickwind> RandomWindModels.LOWER_BOUND
						&& records.size()>60) {
					 twominavg = avg(records, 60);}
				else twominavg = avg(records, 60);
				if(RandomWindModels.quickwind< RandomWindModels.UPPER_BOUND && RandomWindModels.quickwind> RandomWindModels.LOWER_BOUND
						&& records.size()>150) {
					fiveminavg = avg(records, 150);}
				else fiveminavg = avg(records, 60);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}, 0,1700, TimeUnit.MILLISECONDS);
	}
		
	
	static double avg(ArrayList<Double> list, int count) {
		//Should not work as expected remove thread
		double sum = 0;
		
		
        if (list.size() < count) {
            throw new IllegalArgumentException("List does not contain enough elements to calculate the average.");
        }

       
        for (int i = list.size() - count; i < list.size(); i++) {
            sum += list.get(i);
        
        }
        // Return the average
        return  sum / count;
        
    }
	static void stopThread() {
		RandomWindModels.thread = null;
	}
	private static void infoPane(String message, long duration) {
		Dashboard.dataFromWModels = true;
		JFrame frame = new JFrame();
		 Wind.windfiltersetter.schedule(()-> {frame.dispose();}, duration, TimeUnit.MILLISECONDS);
		JOptionPane.showMessageDialog(frame, message, "Information", JOptionPane.ERROR_MESSAGE, null);
	}
}
