package Main;

import java.util.Arrays;

import audio.*;
import leapPack.Leap;

public class TestMain {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		AudioManager.loadConfig();
		
		Object[] instrNames = AudioManager.getInstrumentNames().toArray();
		Leap.initLeap(Arrays.copyOf(instrNames, instrNames.length, String[].class));
		
	}

}
