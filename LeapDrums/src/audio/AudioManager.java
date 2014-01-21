/*
 * Manages all of the Audio clips including, playing the right wav file when
 * a sound is played repeatedly. It also loads the files from the samples folder
 * 
 */

package audio;

import java.io.File;

public final class AudioManager {
	
	public AudioManager(){
		loadConfig();
	}
	
	private static void loadConfig(){ 
        try {
        	boolean configFound = false;
        	// Read the file using relative path
        	File dir = new File("..\\LeapDrums");
        	for (File child : dir.listFiles()) {
        		if (child.getName().equalsIgnoreCase("config.txt")){
        			configFound = true;
        		}
    		}
        	
        	// No config file was found
        	if (!configFound){
        		throw new RuntimeException("No config.txt file found in the root LeapDrums folder");
        	}
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
	}
	
	private static void readConfig(){
		
	}
}
