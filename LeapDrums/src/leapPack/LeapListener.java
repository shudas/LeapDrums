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

import ui.MainWindow;
import ui.MainWindow.MyPropertyChangeListener;

public class LeapListener extends Listener {
	
	boolean rset = false;
	Frame prevFrame;
	public LeapHit leapHit;
	
	final String[] instrumentNames;
	
	float minrPitch = 10000;
	float minlPitch = 10000;
	float maxrPitch = 10000;
	float maxlPitch = 10000;
	boolean rightReady = false;
	boolean rplayReady = false;
	boolean leftReady = false;
	boolean lplayReady = false;
	int prevrInstr = -1;
	int prevlInstr = -1;
	long prevrtime = 0;
	long prevltime = 0;
	boolean lefthit = false;
	boolean righthit = false;
	
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
        
        // turn off button color after a bit if hit. frame timestamp is in microsec
        if (leapHit.getlHit() && frame.timestamp() - prevltime > 100000) {
        	leapHit.setlHit(false);
        }
        if (leapHit.getrHit() && frame.timestamp() - prevrtime > 100000) {
        	leapHit.setrHit(false);
        }
        
        if (frame.hands().isEmpty()) {
        	// dont show where the hands are because there are no hands!
        	MainWindow.ChangeBackground(-1, false);
        }
        else {
            // Get the first hand
            Hand rHand = frame.hands().rightmost();
            Hand lHand = frame.hands().leftmost();
            // Consider the left hand to be invalid if there is only one hand
            if (frame.hands().count() <= 1){
            	lHand = new Hand();
            }

            if (rHand.isValid()) {
            	try {
//            		System.out.println(rHand.rotationAngle(prevFrame, Vector.xAxis()) * 180 / Math.PI);
            	} catch (Exception e) { }
            	
//            	System.out.print("," + rHand.palmVelocity().getY());
//            	System.out.println("," + rHand.palmVelocity().getZ());
            	
            	
            	Vector currPos = rHand.palmPosition();
            	int instrToPlay = 0;
            	// DONT USE ABSOLUTE VALUES
    			if (currPos.getX() > 130){
    				instrToPlay = 2;
    			}
    			else if (Math.abs(currPos.getX()) <= 130){
    				instrToPlay = 1;
    			}
    			
    			// show where hand is
    			MainWindow.ChangeBackground(instrToPlay, true);
    			if (prevrInstr != instrToPlay) {
    				MainWindow.ChangeBackground(prevrInstr, false);
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
            	}
            	if (prevrInstr != instrToPlay){
            		rplayReady = true;
            	}
            	if (rightReady){
            		if (rplayReady){
//            			System.out.println("Yo");
            			leapHit.setrInstrument(instrToPlay);
            			leapHit.setrHit(true);
            			this.prevrtime = frame.timestamp();
            			prevrInstr = instrToPlay;
            			AudioManager.play(instrumentNames[instrToPlay]);
                		rplayReady = false;
            		}
            		rightReady = false;
            		maxrPitch = 10000;
            	}
            	
            	prevrInstr = instrToPlay;
            	
//            	try {
//	            	if (rHand.rotationAngle(prevFrame, Vector.xAxis()) > 0.0087266 && rset) {
//	            		prevrInstr = instrToPlay;
//	            		AudioManager.play(instrumentNames[instrToPlay]);
//	            		rset = false;
//	            	}
//	            	else if (rHand.rotationAngle(prevFrame, Vector.xAxis()) <= 0) {
//	            		rset = true;
//	            	}
//            	} catch (Exception e) {}
            	
            }
            if (lHand.isValid()){
            	Vector currPos = lHand.palmPosition();
            	int instrToPlay = 0;
    			if (currPos.getX() > 130){
    				instrToPlay = 2;
    			}
    			else if (Math.abs(currPos.getX()) <= 130){
    				instrToPlay = 1;
    			}
    			
    			// show where hand is
    			MainWindow.ChangeBackground(instrToPlay, true);
    			if (prevlInstr != instrToPlay) {
    				MainWindow.ChangeBackground(prevlInstr, false);
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
            	}
            	if (prevlInstr != instrToPlay){
            		lplayReady = true;
            	}
            	if (leftReady){
            		if (lplayReady){
            			leapHit.setlInstrument(instrToPlay);
            			leapHit.setlHit(true);
            			this.prevltime = frame.timestamp();
            			prevlInstr = instrToPlay;
            			AudioManager.play(instrumentNames[instrToPlay]);
                		lplayReady = false;
            		}
            		leftReady = false;
            		maxlPitch = 10000;
            	}
            	
            	prevlInstr = instrToPlay;
            }
            
            prevFrame = frame;
            
        }
    }

}
