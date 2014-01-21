package audio;

import java.io.File;
import java.io.IOException;
import java.net.*;
import java.nio.file.*;

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
        clip.setFramePosition(0);  // Must always rewind!
        clip.start();
    }
    
    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    
    public void stop(){
        clip.stop();
    }
    
    public String getName(){
    	return filename;
    }
}
