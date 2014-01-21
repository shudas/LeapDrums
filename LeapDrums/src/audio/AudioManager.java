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
	
	// Indicates that the config file has been loaded
	private static boolean configLoaded = false;
	
	// Maps the instruments in config file to Lists of AudioClips
	private static HashMap<String, Instrument> clipMap = new HashMap<String, Instrument>();
	
	public AudioManager(){
		loadConfig();
		for (Instrument list : clipMap.values()){
			for (AudioClip clip : list.getClips()){
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
        	// Indicate that the config file has been loaded.
        	configLoaded = true;
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
		   // Read from the samples folder and store the AudioClips
		   readSamples(line);
		}
		br.close();
	}
	
	/*
	 * Stores each sample given the beginning string in that sample into clipMap
	 * Also stores the max number of samples for each startString
	 */
	private static void readSamples(String startString){
		ArrayList<AudioClip> clips = new ArrayList<AudioClip>();
		// Search in samples directory
		File dir = new File(".\\samples");
    	boolean found = false;
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
    	
    	clipMap.put(startString, new Instrument(clips));
	}
	
	/*
	 * Returns the different instrument types as indicated in config.txt
	 */
	public static final ArrayList<String> getInstruments(){
		if (!configLoaded){
			throw new RuntimeException("Config file has not been loaded yet");
		}
		return (ArrayList<String>) clipMap.keySet();
	}
	
	/*
	 * Contains data for each instrument type: The different samples stored in a list,
	 * the maximum number of samples for an instrument, and the current instrument that
	 * is being played
	 */
	private static class Instrument {
		/*
		 * The AudioClips corresponding to one string/instrument
		 */
		private ArrayList<AudioClip> clips;
		
		/*
		 * The maximum number of samples (should be equal to size of clips)
		 */
		private int maxSamples = 0;
		
		/*
		 * The sample in clips that should be played next.
		 */
		private int currPlayingSample = 0;
		
		public Instrument(ArrayList<AudioClip> audioClips){
			clips = audioClips;
			maxSamples = audioClips.size();
		}
		
		/*
		 * Returns the clips in this instrument
		 */
		public ArrayList<AudioClip> getClips(){
			return clips;
		}
		
		/*
		 * Returns the maximum number of samples
		 */
		public int getMaxSamples(){
			return maxSamples;
		}
		
		/*
		 * Returns the sample in clips that should be played next.
		 */
		public int getCurrentPlayingSample(){
			return currPlayingSample;
		}
		
		/*
		 * Plays the correct clip for this instrument
		 */
		public void play(){
			clips.get(currPlayingSample++).play();
			// Reset the currPlayingSample to 0 if it exceeds max - 1
			if (currPlayingSample == maxSamples){
				currPlayingSample = 0;
			}
		}
		
	}
}
