package ui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import audio.AudioManager;

public class MainUI implements KeyListener {

	final String[] instrumentNames;
	
	public MainUI(String[] instrNames){
		JFrame frame = new JFrame("Leap Drums");
		frame.setSize(640, 480);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.addKeyListener(this);
		
		instrumentNames = instrNames;

	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		if (arg0.getKeyCode() == KeyEvent.VK_NUMPAD0){
			try{
				AudioManager.play(instrumentNames[0]);
			}
			catch (Exception e){}
		}
		else if (arg0.getKeyCode() == KeyEvent.VK_NUMPAD1){
			try{
				AudioManager.play(instrumentNames[1]);
			}
			catch (Exception e){}
		}
		else if (arg0.getKeyCode() == KeyEvent.VK_NUMPAD2){
			try{
				AudioManager.play(instrumentNames[2]);
			}
			catch (Exception e){}
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

}
