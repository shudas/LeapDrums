package ui;

import java.awt.EventQueue;
import java.util.Arrays;

import javax.swing.JFrame;

import leapPack.Leap;
import audio.AudioManager;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import net.miginfocom.swing.MigLayout;

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

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class MainWindow {

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
		
		JPanel panel_1 = new JPanel();
		panel_1.setOpaque(false);
		panel.add(panel_1);
		panel_1.setLayout(new GridLayout(1, 3, 12, 4));
		
		JLabel lblNewLabel_1 = new JLabel("Kick");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setOpaque(true);
		lblNewLabel_1.setBackground(new Color(98, 177, 255));
		panel_1.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Snare");
		lblNewLabel_2.setForeground(Color.WHITE);
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setHorizontalTextPosition(SwingConstants.LEADING);
		lblNewLabel_2.setOpaque(true);
		lblNewLabel_2.setBackground(new Color(255, 132, 123));
		panel_1.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Hi Hat");
		lblNewLabel_3.setForeground(Color.WHITE);
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setOpaque(true);
		lblNewLabel_3.setBackground(new Color(99, 209, 111));
		panel_1.add(lblNewLabel_3);
		panel.setBorder(new EmptyBorder(10, 10, 10, 10) );
		
		JLabel lblNewLabel = new JLabel("Instruments");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBorder(new EmptyBorder(10, 10, 10, 10) );
		splitPane.setLeftComponent(lblNewLabel);
		
		// change all fonts
		changeFont(panel_1, new Font(fontFamily, Font.PLAIN, 20));
		changeFont(lblNewLabel, new Font(fontFamily, Font.PLAIN, 16));
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
}
