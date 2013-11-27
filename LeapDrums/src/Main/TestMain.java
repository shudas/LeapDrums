package Main;

import javax.swing.JFrame;
import ui.*;
import audio.Audio;

public class TestMain {

	public static void main(String[] args) {
		// Open frame so that program doesnt close immediately after running
		UserInterface ui = new UserInterface();
		
		Audio audio1 = new Audio("snare01.wav");
		audio1.play();
	}

}
