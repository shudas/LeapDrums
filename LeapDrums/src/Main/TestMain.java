package Main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.UIManager;

import audio.*;

public class TestMain {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame("FrameDemo");
		frame.setSize(200, 200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		AudioManager.loadConfig();
		for (String i : AudioManager.getInstrumentNames()){
			AudioManager.play(i);
		}
	}

}
