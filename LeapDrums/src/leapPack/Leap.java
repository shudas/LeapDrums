package leapPack;

/******************************************************************************\
* Copyright (C) 2012-2013 Leap Motion, Inc. All rights reserved.               *
* Leap Motion proprietary and confidential. Not for distribution.              *
* Use subject to the terms of the Leap Motion SDK Agreement available at       *
* https://developer.leapmotion.com/sdk_agreement, or another agreement         *
* between Leap Motion and you, your company or other organization.             *
\******************************************************************************/

import java.io.IOException;
import java.lang.Math;

import audio.AudioManager;

import com.leapmotion.leap.*;

class SampleListener extends Listener {
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
	
	public SampleListener(String[] instrNames){
		instrumentNames = instrNames;
	}
	
    public void onInit(Controller controller) {
        System.out.println("Initialized");
    }

    public void onConnect(Controller controller) {
        System.out.println("Connected");
        controller.enableGesture(Gesture.Type.TYPE_SCREEN_TAP);
        controller.enableGesture(Gesture.Type.TYPE_KEY_TAP);
    }

    public void onDisconnect(Controller controller) {
        //Note: not dispatched when running in a debugger.
        System.out.println("Disconnected");
    }

    public void onExit(Controller controller) {
        System.out.println("Exited");
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
            	}
            	if (prevrInstr != instrToPlay){
            		rplayReady = true;
            	}
            	if (rightReady){
            		if (rplayReady){
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
            	}
            	if (prevlInstr != instrToPlay){
            		lplayReady = true;
            	}
            	if (leftReady){
            		if (lplayReady){
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

public class Leap {
	private static SampleListener listener;
    private static Controller controller;
	
    public static void initLeap(String[] instrNames) {
        // Create a sample listener and controller
    	listener = new SampleListener(instrNames);
        controller = new Controller();
        // Have the sample listener receive events from the controller
        controller.addListener(listener);       
    }
    
    public static void disconnect(){
    	 // Remove the sample listener when done
        controller.removeListener(listener);
    }
}
