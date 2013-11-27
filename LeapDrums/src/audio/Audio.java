package audio;

import java.io.File;
import java.io.IOException;
import java.net.*;
import java.nio.file.*;

import javax.sound.sampled.*;

public class Audio {
    private Clip clip;
    public Audio(String fileName) {
        // specify the sound to play
        // (assuming the sound can be played by the audio system)
        // from a wave File
        try {
            String absPath = new File("").getAbsolutePath() + "\\samples\\" + fileName;
            System.out.println(absPath);
            AudioInputStream sound = (AudioInputStream) this.getClass().getResourceAsStream(absPath);
            // load the sound into memory (a Clip)
            clip = AudioSystem.getClip();
            clip.open(sound);
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
