package leapPack;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import audio.AudioManager;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Gesture;
import com.leapmotion.leap.Hand;
import com.leapmotion.leap.Listener;
import com.leapmotion.leap.Tool;
import com.leapmotion.leap.Vector;
import ui.MainWindow.MyPropertyChangeListener;

public class LeapListener extends Listener {
	
	public LeapHit leapHit;
	
	final String[] instrumentNames;
	
	float minrPitch = 10000;
	float minlPitch = 10000;
	float maxrPitch = 10000;
	float maxlPitch = 10000;
	double threshold = -20;
	double vertThresh = 300;
	boolean rightReady = false;
	boolean rplayReady = false;
	boolean leftReady = false;
	boolean lplayReady = false;
	int prevrInstr = 0;
	int prevlInstr = 0;
	long prevrtime = 0;
	long prevltime = 0;
	
	public LeapListener(String[] instrNames){
		instrumentNames = instrNames;
	}
	
    public void onInit(Controller controller) {
        System.err.println("Leap Motion Initialized");
    }

    public void onConnect(Controller controller) {
        System.err.println("Leap Motion Connected");
        controller.enableGesture(Gesture.Type.TYPE_SCREEN_TAP);
        controller.enableGesture(Gesture.Type.TYPE_KEY_TAP);
        
        // start event stuff
        leapHit = new LeapHit();
        leapHit.addPropertyChangeListener(new MyPropertyChangeListener());
    }
    
//    public static class MyPropertyChangeListener implements PropertyChangeListener {
//	    // This method is called every time the property value is changed
//	    public void propertyChange(PropertyChangeEvent evt) {
//	    	System.out.println("Name = " + evt.getPropertyName());
//	    	System.out.println("Old Value = " + evt.getOldValue());
//	    	System.out.println("New Value = " + evt.getNewValue());
//	    	System.out.println("**********************************");
//	    }
//	}

    public void onDisconnect(Controller controller) {
        //Note: not dispatched when running in a debugger.
        System.err.println("Leap Motion Disconnected");
    }

    public void onExit(Controller controller) {
        System.err.println("Leap Motion Exited");
    }

    public void onFrame(Controller controller) {
        // Get the most recent frame and report some basic information
        Frame frame = controller.frame();
        
        if (!frame.hands().isEmpty()) {
            // Get the first hand
            Hand rHand = frame.hands().rightmost();
            Tool rTool = frame.tools().rightmost();
            Hand lHand = frame.hands().leftmost();
            Tool lTool = frame.tools().leftmost();
            // Consider the left hand to be invalid if there is only one hand
            if (frame.hands().count() <= 1){
            	lHand = new Hand();
            	lTool = new Tool();
            }
            

            if (rHand.isValid() || rTool.isValid()){
            	Vector currPos = rHand.palmPosition();
            	int instrToPlay = 0;
    			if (currPos.getX() > 130){
    				instrToPlay = 2;
    			}
    			else if (Math.abs(currPos.getX()) <= 130){
    				instrToPlay = 1;
    			}
            	
            	float pitch = rHand.direction().pitch();
//            	if (!rTool.isFinger()){
//            		currPos = rTool.tipPosition();
//            		pitch = rTool.direction().pitch();
//            	}
            	
            	if (pitch < minrPitch || minrPitch == 10000){
            		minrPitch = pitch;
            	}
            	if (pitch > maxrPitch || maxrPitch == 10000){
            		maxrPitch = pitch;
            	}
            	if (pitch > minrPitch + 0.13089){	// 10 deg: 0.1745
            		rplayReady = true;
            	}
            	if (pitch < maxrPitch - 0.13089){
            		rightReady = true;
            		minrPitch = 10000;
            		leapHit.setHit(false);
            	}
            	if (prevrInstr != instrToPlay){
            		rplayReady = true;
            	}
            	if (rightReady){
            		if (rplayReady){
            			leapHit.setInstrument(instrToPlay);
            			leapHit.setHit(true);
            			prevrInstr = instrToPlay;
            			AudioManager.play(instrumentNames[instrToPlay]);
                		rplayReady = false;
            		}
            		rightReady = false;
            		maxrPitch = 10000;
            	}
            	
            }
            if (lHand.isValid() || lTool.isValid()){
            	Vector currPos = lHand.palmPosition();
            	int instrToPlay = 0;
    			if (currPos.getX() > 130){
    				instrToPlay = 2;
    			}
    			else if (Math.abs(currPos.getX()) <= 130){
    				instrToPlay = 1;
    			}
            	float pitch = lHand.direction().pitch();
//            	if (!lTool.isFinger()){
//            		currPos = lTool.tipPosition();
//            		pitch = lTool.direction().pitch();
//            	}
            	
            	if (pitch < minlPitch || minlPitch == 10000){
            		minlPitch = pitch;
            	}
            	if (pitch > maxlPitch || maxlPitch == 10000){
            		maxlPitch = pitch;
            	}
            	if (pitch > minlPitch + 0.13089){	// 10 deg: 0.1745
            		lplayReady = true;
            	}
            	if (pitch < maxlPitch - 0.13089){
            		leftReady = true;
            		minlPitch = 10000;
            		leapHit.setHit(false);
            	}
            	if (prevlInstr != instrToPlay){
            		lplayReady = true;
            	}
            	if (leftReady){
            		if (lplayReady){
            			leapHit.setInstrument(instrToPlay);
            			leapHit.setHit(true);
            			prevlInstr = instrToPlay;
            			AudioManager.play(instrumentNames[instrToPlay]);
                		lplayReady = false;
            		}
            		leftReady = false;
            		maxlPitch = 10000;
            	}
            	
            }
            
        }
    }

}
