package audio;

import javax.swing.JFrame;

public class TestMain {

	public static void main(String[] args) {
		// Open frame so that program doesnt close immediately after running
		JFrame frame = new JFrame();
        frame.setSize(300,300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
		
		Audio audio1 = new Audio("snare01.wav");
	}

}
