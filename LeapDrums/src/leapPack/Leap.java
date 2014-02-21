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
import com.leapmotion.leap.Gesture.State;

class SampleListener extends Listener {
	final String[] instrumentNames;
	
	float minrPitch = 10000;
	float minlPitch = 10000;
	float maxrPitch = 10000;
	float maxlPitch = 10000;
	String prevrInstr, prevlInstr;
	double threshold = -20;
	double vertThresh = 300;
	boolean rightReady = false;
	boolean rplayReady = false;
	boolean leftReady = false;
	boolean lplayReady = false;
	
	public SampleListener(String[] instrNames){
		instrumentNames = instrNames;
	}
	
    public void onInit(Controller controller) {
        System.out.println("Initialized");
    }

    public void onConnect(Controller controller) {
        System.out.println("Connected");
        controller.enableGesture(Gesture.Type.TYPE_SWIPE);
        controller.enableGesture(Gesture.Type.TYPE_CIRCLE);
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
            Hand lHand = frame.hands().leftmost();
            // Consider the left hand to be null if there is only one hand
            if (frame.hands().count() <= 1){
            	lHand = new Hand();
            }

            if (rHand.isValid()){
            	Vector currPos = rHand.palmPosition();
            	float pitch = rHand.direction().pitch();
            	
            	if (pitch < minrPitch || minrPitch == 10000){
            		minrPitch = pitch;
            	}
            	if (pitch > maxrPitch || maxrPitch == 10000){
            		maxrPitch = pitch;
            	}
            	if (pitch > minrPitch + 0.2617){	// 10 deg: 0.1745
            		rplayReady = true;
            		System.out.println("Got set rplay");
            	}
            	if (pitch < maxrPitch - 0.2617){
            		rightReady = true;
            		minrPitch = 10000;
            	}
            	if (rightReady){
            		if (rplayReady){
            			int instrToPlay = 0;
            			if (currPos.getX() > 150){
            				instrToPlay = 2;
            			}
            			else if (Math.abs(currPos.getX()) <= 150){
            				instrToPlay = 1;
            			}
            			
            			AudioManager.play(instrumentNames[instrToPlay]);
                		rplayReady = false;
            		}
            		rightReady = false;
            		maxrPitch = 10000;
            	}
//            	System.out.print(rHand.direction().yaw() + ", ");
//            	System.out.print(rHand.palmNormal().roll() + ", ");
//            	System.out.print('\t');            	
//            	if (!rightReady){
//	        		if (dir.pitch() > prevrPitch){
//	        			rightReady = false;
//	        			rplayReady = true;
//	        		}
//	        		else if (currPos.getY() < vertThresh - 50){
//	        			rightReady = true;
//	        		}
//            	}
//            	else{
//            		if (rplayReady){
//            			int instrToPlay = 0;
//            			if (currPos.getX() > 150){
//            				instrToPlay = 2;
//            			}
//            			else if (Math.abs(currPos.getX()) <= 150){
//            				instrToPlay = 1;
//            			}
//            			System.out.println("Got r play");
//            			AudioManager.play(instrumentNames[instrToPlay]);
//                		rplayReady = false;
//            		}
//            		rightReady = false;
//            	}
            }
            if (lHand.isValid()){
            	Vector currPos = lHand.palmPosition();
            	float pitch = lHand.direction().pitch();
            	
            	if (pitch < minlPitch || minlPitch == 10000){
            		minlPitch = pitch;
            	}
            	if (pitch > maxlPitch || maxlPitch == 10000){
            		maxlPitch = pitch;
            	}
            	if (pitch > minlPitch + 0.2617){	// 10 deg: 0.1745
            		lplayReady = true;
            		System.out.println("Got set rplay");
            	}
            	if (pitch < maxlPitch - 0.2617){
            		leftReady = true;
            		minlPitch = 10000;
            	}
            	if (leftReady){
            		if (lplayReady){
            			int instrToPlay = 0;
            			if (currPos.getX() > 150){
            				instrToPlay = 2;
            			}
            			else if (Math.abs(currPos.getX()) <= 150){
            				instrToPlay = 1;
            			}
            			
            			AudioManager.play(instrumentNames[instrToPlay]);
                		lplayReady = false;
            		}
            		leftReady = false;
            		maxlPitch = 10000;
            	}
            	
//            	Vector currPos = lHand.palmPosition();
//            	if (!leftReady){
//	        		if (currPos.getY() > vertThresh){
//	        			leftReady = false;
//	        			lplayReady = true;
//	        		}
//	        		else{
//	        			leftReady = true;
//	        		}
//            	}
//            	else{
//            		if (lplayReady){
//            			int instrToPlay = 0;
//            			if (currPos.getX() > 150){
//            				instrToPlay = 2;
//            			}
//            			else if (Math.abs(currPos.getX()) <= 150){
//            				instrToPlay = 1;
//            			}
//            			System.out.println("Got l play");
//            			AudioManager.play(instrumentNames[instrToPlay]);
//                		lplayReady = false;
//            		}
//            		leftReady = false;
//            	}
            }
            
        }
    }
    
    private void recognize(Gesture gesture, long frameNum){
    	
    }
}

public class Leap {
    public static void initLeap(String[] instrNames) {
        // Create a sample listener and controller
        SampleListener listener = new SampleListener(instrNames);
        Controller controller = new Controller();

        // Have the sample listener receive events from the controller
        controller.addListener(listener);

        // Keep this process running until Enter is pressed
        System.out.println("Press Enter to quit...");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Remove the sample listener when done
        controller.removeListener(listener);
    }
}
