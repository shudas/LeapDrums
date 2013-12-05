package Main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.UIManager;

import ui.*;
import audio.Audio;

public class TestMain {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
            // Set cross-platform Java L&F (also called "Metal")
	        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
	    }
		catch( Exception e){
			throw new RuntimeException("Could not set look and feel");
		}
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainUI window = new mainUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
