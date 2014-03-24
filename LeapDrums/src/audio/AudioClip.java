package audio;

import java.io.File;

import javax.sound.sampled.*;

public class AudioClip {
	
	private final String filename;
    private Clip clip;

    public AudioClip(File file) {
    	filename = file.getName();
        try{
        	AudioInputStream inputStream = AudioSystem.getAudioInputStream(file);
            // load the sound into memory (a Clip)
            clip = AudioSystem.getClip();
            clip.open(inputStream);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    
    public void play(){
    	try {
	        clip.setFramePosition(0);  // Must always rewind!
	        clip.start();
    	}
    	catch (Exception e) {
    		throw new RuntimeException("Could not play clip");
    	}
    }
    
    public void stop(){
        clip.stop();
    }
    
    public String getFilename(){
    	return filename;
    }
}
