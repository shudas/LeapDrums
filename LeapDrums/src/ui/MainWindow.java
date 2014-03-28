package ui;

import java.awt.EventQueue;
import java.util.Arrays;

import javax.swing.JFrame;

import leapPack.Leap;
import audio.AudioManager;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainWindow {

	private JFrame frame;

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
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	

	
}
