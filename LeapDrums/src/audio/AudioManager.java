/*
 * Manages all of the Audio clips including, playing the right wav file when
 * a sound is played repeatedly. It also loads the files from the samples folder
 * 
 */

package audio;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public final class AudioManager {
	
	// Maps the instruments in config file to Lists of AudioClips
	private static HashMap<String, ArrayList<AudioClip>> clipMap = new HashMap<String, ArrayList<AudioClip>>();
	
	public AudioManager(){
		loadConfig();
		for (ArrayList<AudioClip> list : clipMap.values()){
			for (AudioClip clip : list){
				System.out.println(clip.getName());
			}
		}
	}
	
	/*
	 * Loads the clips by reading the config file
	 */
	private static void loadConfig(){ 
        try {
        	boolean configFound = false;
        	// Read the file using relative paths. Search LeapDrums dir (root dir)
        	File dir = new File(".");
        	for (File child : dir.listFiles()) {
        		if (child.getName().equalsIgnoreCase("config.txt")){
        			configFound = true;
        			readConfig(child);
        			break;
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
	
	/*
	 * Reads the config.txt files and calls readSamples for any instrument names listed in config.txt
	 */
	private static void readConfig(File configFile) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(configFile));
		String line;
		while ((line = br.readLine()) != null) {
		   line = line.trim();
		   // Ignore empty strings and lines that start with *
		   if (line.isEmpty()){
			   continue;
		   }
		   if (line.charAt(0) == '*'){
			   continue;
		   }
		   readSamples(line);
		}
		br.close();
	}
	
	/*
	 * Reads each sample given the beginning string in that sample
	 */
	private static void readSamples(String startString){
		ArrayList<AudioClip> clips = new ArrayList<AudioClip>();
		// Search in samples directory
		File dir = new File(".\\samples");
    	boolean found = false;
    	File soundFile = new File(".");
    	if (dir.isDirectory()){
    		// Go through samples and store all files of instrument corresponding to
    		// startString in the clips ArrayList
        	for (File child : dir.listFiles()) {
        		if (child.getName().startsWith(startString)){
        			found = true;
        			clips.add(new AudioClip(child));
        		}
    		}
    	}
    	
    	if (!found){
    		String err = "No samples were found for instrument: " + startString +
    				"\nPlease make sure the config file contains the correct instrument names";
    		throw new RuntimeException(err);
    	}
    	
    	clipMap.put(startString, clips);
	}
}
