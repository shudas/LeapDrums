package ui;

import java.io.File;

import javax.swing.JFrame;

import audio.*;

public final class UserInterface {
	
	Audio[] samples;
	
	public UserInterface(){
		JFrame frame = new JFrame();
        frame.setSize(300,300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
        LoadSamples();
	}
	
	public void LoadSamples(){
		// For now, loading all samples by hard coding
		File dir = new File("..\\LeapDrums\\samples");
		for (File child : dir.listFiles()) {
			System.out.println(child.getName());
		}
	}
}
