package ui;

import java.awt.EventQueue;
import java.util.Arrays;

import javax.swing.JFrame;

import leapPack.Leap;
import audio.AudioManager;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.JSplitPane;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class MainWindow {

	static JPanel panel_1;
	
	static Color [] inactiveColors = { new Color(98, 177, 255), new Color(255, 132, 123), new Color(99, 209, 111)};
	static Color [] activeColors = { new Color(24, 141, 255), new Color(222, 121, 91), new Color(20, 209, 39)};
	
	private JFrame frame;
	String fontFamily = "Segoe UI";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
					AudioManager.loadConfig();

					Object[] instrNames = AudioManager.getInstrumentNames().toArray();
					Leap.initLeap(Arrays.copyOf(instrNames, instrNames.length, String[].class));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
		
//		Leap.leapHit.addPropertyChangeListener(new MyPropertyChangeListener());
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				Leap.disconnect();
			}
		});
		frame.setBounds(100, 100, 720, 480);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new GridLayout(0, 1, 0, 0));
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setOpaque(true);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setBackground(new Color(90, 90, 90));
		frame.getContentPane().add(splitPane);
		
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		splitPane.setRightComponent(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		panel_1 = new JPanel();
		panel_1.setOpaque(false);
		panel.add(panel_1);
		panel_1.setLayout(new GridLayout(1, 3, 12, 4));
		
		JLabel lblNewLabel_1 = new JLabel("Kick");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setOpaque(true);
		panel_1.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Snare");
		lblNewLabel_2.setForeground(Color.WHITE);
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setHorizontalTextPosition(SwingConstants.LEADING);
		lblNewLabel_2.setOpaque(true);
		panel_1.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Hi Hat");
		lblNewLabel_3.setForeground(Color.WHITE);
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setOpaque(true);
		panel_1.add(lblNewLabel_3);
		panel.setBorder(new EmptyBorder(10, 10, 10, 10) );
		
		JLabel lblNewLabel = new JLabel("Instruments");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBorder(new EmptyBorder(10, 10, 10, 10) );
		splitPane.setLeftComponent(lblNewLabel);
		
		// change all fonts
		changeFont(panel_1, new Font(fontFamily, Font.PLAIN, 20));
		changeFont(lblNewLabel, new Font(fontFamily, Font.PLAIN, 16));
		
		// color the main instrument panel
		initColors();
	}
	
	public void initColors(){
		for (int i = 0; i < panel_1.getComponentCount(); ++i){
			panel_1.getComponent(i).setBackground(inactiveColors[i % inactiveColors.length]);
		}
	}
	
	public static void changeFont ( Component component, Font font )
	{
	    component.setFont ( font );
	    if ( component instanceof Container )
	    {
	        for ( Component child : ( ( Container ) component ).getComponents () )
	        {
	            changeFont ( child, font );
	        }
	    }
	}
	
    public static class MyPropertyChangeListener implements PropertyChangeListener {
	    // This method is called every time the property value is changed
	    public void propertyChange(PropertyChangeEvent evt) {
	    	String propName = evt.getPropertyName();
	    	if (propName == "hit"){
	    		// hit is either true or false new value. oldValue contains instrument property
	    		if ((boolean)evt.getNewValue() == false){
	    			panel_1.getComponent((int)evt.getOldValue()).
	    				setBackground(inactiveColors[(int)evt.getOldValue() % inactiveColors.length]);
	    		}
	    		else if ((boolean)evt.getNewValue() == true){
	    			panel_1.getComponent((int)evt.getOldValue()).
	    			setBackground(activeColors[(int)evt.getOldValue() % activeColors.length]);
	    		}
	    	}
	    	
	    	
	    	System.out.println("Name = " + evt.getPropertyName());
	    	System.out.println("Old Value = " + evt.getOldValue());
	    	System.out.println("New Value = " + evt.getNewValue());
	    	System.out.println("**********************************");
	    }
	}
}
