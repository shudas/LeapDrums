package audio;

import java.io.File;
import java.io.IOException;
import java.net.*;
import java.nio.file.*;

import javax.sound.sampled.*;

public class Audio {
    private Clip clip;
    public Audio(String fileName) {
    	// Open through relative path. Searches 
        try {
        	File dir = new File("..\\LeapDrums\\samples");
        	boolean found = false;
        	for (File child : dir.listFiles()) {
        		System.out.println(child.getName() + ":" + fileName);
        		if (fileName == child.getName()){
        			found = true;
        			System.out.println("YAY!");
        		}
        		System.out.println(child.getName());
    		}
        	
            String relPath = "samples\\" + fileName;
            
            URL soundURL = this.getClass().getResource(relPath);
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(soundURL);
            // load the sound into memory (a Clip)
            clip = AudioSystem.getClip();
            clip.open(inputStream);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        

    // play, stop, loop the sound clip
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
    }
