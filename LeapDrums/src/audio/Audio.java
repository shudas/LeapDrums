package audio;

import java.io.File;
import java.io.IOException;
import java.net.*;

import javax.sound.sampled.*;

public class Audio {
    private Clip clip;
    public Audio(String fileName) {
        // specify the sound to play
        // (assuming the sound can be played by the audio system)
        // from a wave File
        try {
            AudioInputStream sound = (AudioInputStream) this.getClass().getResourceAsStream(fileName);
            // load the sound into memory (a Clip)
            clip = AudioSystem.getClip();
            clip.open(sound);
        }
        catch (java.net.MalformedURLException e) {
            e.printStackTrace();
            throw new RuntimeException("Sound: Malformed URL: " + e);
        }
//        catch (UnsupportedAudioFileException e) {
//            e.printStackTrace();
//            throw new RuntimeException("Sound: Unsupported Audio File: " + e);
//        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Sound: Input/Output Error: " + e);
        }
        catch (LineUnavailableException e) {
            e.printStackTrace();
            throw new RuntimeException("Sound: Line Unavailable Exception Error: " + e);
        }
        catch (Exception e){
        	e.printStackTrace();
            throw new RuntimeException("Sound: file not found: " + fileName);
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
