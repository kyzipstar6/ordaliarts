package application;

import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.atomic.AtomicReference;
import java.awt.BorderLayout;


import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.Box;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicBorders;

public class ControlsFrame extends JFrame {
	static double wspeed1;
	static double wspeed2;
	static double filter;
	static double filterrecord;
	static double wspeed;
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(()-> {
		ControlsFrame(new JFrame());});
		
	}
		static JFrame ControlsFrame(JFrame frame) {
			
		try {
			frame = new JFrame("Wind Simulator");
			Box mainbox = Box.createHorizontalBox();
			Box txtareasbox = Box.createVerticalBox();
			Box btnsbox = Box.createVerticalBox();
			//Panel with each textarea and label for setting up the controls of the simulator
			
			
			
			String [] parameters = {"W_MAXmax", "W_MAXmin","W_MINmax", "W_MINmin",  "FILTER_RATIO", "DIFERENCE_RANDOMS","STABILIZED_FILTER_RATIO","RATIO_WSPEEDS",
					"BOUNDS_RATIO","UPPER_BOUND", "LOWER_BOUND", "Duration", "Quick Duration", "File Name"};
			String [] explanations = {" (the maximal wind gust achieveable)", " (the minimum maximal wind gust achieveable)",
					" (the minimal maximal instant wind speed over a period achieveable)", " (the minimal instant wind speed over a period achieveable)",  
					" (In the second model, an obscure index taken from the substraction of " + "\n"
					+ "the random speed over a filter of a previous wind speed.)", 
					"(Model two: Again a substraction of the two generated windspeeds (operator wsp1 - wsp2<=)"," an obscure index IN 3rd Model"," an obscure index IN 3rd Model",
					"","", "", "", " interval of updates of the quickest label", ""};
			JTextArea [] textareas = {new JTextArea(),new JTextArea(),new JTextArea(),new JTextArea(),new JTextArea(),new JTextArea(),new JTextArea(),new JTextArea(),new JTextArea(),new JTextArea()
					,new JTextArea(),new JTextArea(),new JTextArea(),new JTextArea()}; 
			JLabel [] txtlabels = {new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel(),new JLabel()
					,new JLabel(),new JLabel(),new JLabel(),new JLabel()};
			JButton [] btns = {new JButton(),new JButton(),new JButton(),new JButton(),new JButton(),new JButton(),new JButton(),new JButton(),
					new JButton(),new JButton(),new JButton(),new JButton(),new JButton(),new JButton()};System.out.println("Buttons Length" +btns.length);System.out.println("TextAreas Length" + textareas.length);
			JList<String> txtlab = new JList<String>(parameters);
			;System.out.println("Buttons Length" +btns.length);System.out.println("TextAreas Length" + textareas.length);
			;System.out.println("Text labels Length" +txtlabels.length);System.out.println("Parameters Length" + parameters.length);
			Box labels = Box.createVerticalBox();
			
			txtlab.setBackground(new Color(42,32,42,26));
			double [] arguments = {RandomWindModels.W_MAXmax,RandomWindModels.W_MAXmin, RandomWindModels.W_MINmax,RandomWindModels.W_MINmin, RandomWindModels.FILTER_RATIO, RandomWindModels.DIFERENCE_RANDOMS,
					RandomWindModels.STABILIZED_FILTER_RATIO,RandomWindModels.BOUNDS_RATIO,RandomWindModels.RATIO_WSPEEDS,RandomWindModels.UPPER_BOUND,RandomWindModels.LOWER_BOUND,};
			long [] durationargs = {RandomWindModels.DURATION, RandomWindModels.QUICK_DURATION};
			GeneralControls.FILEID = "";
			JMenuItem [] menuitems = {new JMenuItem("Wind Model One"), new JMenuItem("Wind Model Two"), new JMenuItem("Wind Model Three"), new JMenuItem("Wind Model Four"),
					 new JMenuItem("Set bounds")};
			
			//MenuListeners
			ActionListener [] actionlisteners = {e ->{RandomWindModels.stopThread(); RandomWindModels.launchVersionOne(RandomWindModels.STABILIZED_FILTER_RATIO, RandomWindModels.RATIO_WSPEEDS, RandomWindModels.DURATION); standardLaunch();},
					e->{RandomWindModels.stopThread();RandomWindModels.launchVersionTwo(RandomWindModels.FILTER_RATIO, RandomWindModels.DIFERENCE_RANDOMS, RandomWindModels.DURATION); standardLaunch();}, 
					e->{RandomWindModels.stopThread();RandomWindModels.launchVersionThree(RandomWindModels.STABILIZED_FILTER_RATIO, RandomWindModels.RATIO_WSPEEDS, RandomWindModels.DURATION); standardLaunch();},
					e->{RandomWindModels.stopThread();RandomWindModels.launchModelFour();}, e->{ RandomWindModels.boundSetter(RandomWindModels.UPPER_BOUND,RandomWindModels.LOWER_BOUND, RandomWindModels.W_MINmax,RandomWindModels.W_MINmin,RandomWindModels.W_MAXmax,RandomWindModels.W_MAXmin,RandomWindModels.DURATION,RandomWindModels.BOUNDSDURATION); standardLaunch();}};
			;System.out.println("DoubleArguments Length " +arguments.length);
			for(int i = 0; i<menuitems.length; i++) {
				menuitems[i].addActionListener(actionlisteners[i]);}	
			JMenu popup = new JMenu("Model Selection");
			
			for(int i = 0; i<arguments.length; i++) {
				int finali = i;
				textareas[i].setText(Double.toString(arguments[i]));
				btns[i].addActionListener(e -> arguments[finali] = Double.parseDouble(textareas[finali].getText()));
				AtomicReference<Double> reference = new AtomicReference<Double>(arguments[i]);
				reference.get();
				reference.set(arguments[i]);}
				
			for(int i =btns.length-3; i<btns.length-1; i++) {
				int finali = i-9;
				btns[i].addActionListener(e -> durationargs[finali] = Long.parseLong(textareas[finali].getText()));}
			try {
				btns[btns.length].addActionListener(e -> textareas[btns.length].getText());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			for(int i = 0; i<menuitems.length; i++) {
				popup.add(menuitems[i]);}
			
			JMenuBar menubar = new JMenuBar();
			menubar.add(popup);
			
			
			for(int i = 0; i<textareas.length; i++) {
				textareas[i].setBorder(new BasicBorders.MarginBorder());
				
			txtareasbox.add(textareas[i]);}
			for(int i = 0; i<btns.length; i++) {
				btnsbox.add(btns[i]);}
			for(int i = 0; i<parameters.length; i++) {
				txtlabels[i].setText(parameters[i] + explanations[i]); 
				txtlabels[i].setSize(40,10);
				labels.add(txtlabels[i]);
			}
			for(int i = 0; i<parameters.length; i++) {
				btns[i].setText("Set " +parameters[i]);
				btns[i].setSize(textareas[i].getSize());}
			
			mainbox.add(labels);
			Box.createHorizontalStrut(10);
			mainbox.add(txtareasbox);
			Box.createHorizontalGlue();
			mainbox.add(btnsbox);
			Box.createHorizontalGlue();
			
			//Double creation from the label texts
			
			
			//temporaryMessage("Double Atomic upperbound is:" + upperbound);
			
			frame.add(menubar,BorderLayout.NORTH);
			frame.getContentPane().add(mainbox);
			frame.setSize(1400,890);
			frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			frame.setResizable(true);
			
			frame.pack();
			frame.setVisible(true);
			return frame;
		
			} catch (HeadlessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
		private static double getDoublefromLabelsandSet(String [] array, JTextArea [] textarea, int textareaindex) {
				AtomicReference<String> reference = new AtomicReference<String>();
				reference.set(textarea[textareaindex].getText());
				reference.get();
				double doublevalue = Double.parseDouble(reference.get());
				return doublevalue;
			
		}
		private static void standardLaunch() {
			GeneralControls.createSwings("Wind Simulator", new JFrame(), false); 
			RandomWindModels.boundSetter(RandomWindModels.UPPER_BOUND,RandomWindModels.LOWER_BOUND, RandomWindModels.W_MINmax,RandomWindModels.W_MINmin,RandomWindModels.W_MAXmax,RandomWindModels.W_MAXmin,RandomWindModels.DURATION ,RandomWindModels.BOUNDSDURATION);
			GeneralControls.startWriting();
			GeneralControls.labelupdate();
			
		}
		static void temporaryMessage(String message) {
			JOptionPane.showMessageDialog(new JFrame(), message);
			
		}
}
