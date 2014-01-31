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

import com.leapmotion.leap.*;
import com.leapmotion.leap.Gesture.State;

class SampleListener extends Listener {
	private Vector prevVector = new Vector();
	double prevrRoll = 10000;
	double prevlRoll = 10000;
	double threshold = -20;
	double vertThresh = 300;
	boolean rightReady = false;
	boolean rplayReady = false;
	boolean leftReady = false;
	boolean lplayReady = false;
	
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
            Hand lHand = new Hand();
            if (frame.hands().count() > 1){
            	lHand = frame.hands().leftmost();
            }

            if (rHand.isValid()){
            	Vector currPos = rHand.palmPosition();
            	if (!rightReady){
	        		if (currPos.getY() > vertThresh){
	        			rightReady = false;
	        			rplayReady = true;
	        		}
	        		else{
	        			rightReady = true;
	        		}
            	}
            	else{
            		if (rplayReady){
            			System.out.println("play r sound");
                		rplayReady = false;
            		}
            		rightReady = false;
            	}
            }
            if (lHand.isValid()){
            	Vector currPos = lHand.palmPosition();
            	if (!leftReady){
	        		if (currPos.getY() > vertThresh){
	        			leftReady = false;
	        			lplayReady = true;
	        		}
	        		else{
	        			leftReady = true;
	        		}
            	}
            	else{
            		if (lplayReady){
            			System.out.println("play l sound");
                		lplayReady = false;
            		}
            		leftReady = false;
            	}
            }
        }

//        if (!frame.hands().isEmpty() || !gestures.isEmpty()) {
//            System.out.println();
//        }
    }
    
    private void recognize(Gesture gesture, long frameNum){
    	switch (gesture.type()) {
	    	case TYPE_SWIPE:
	            SwipeGesture swipe = new SwipeGesture(gesture);
	            Vector dir = swipe.direction();
	            if (frameNum % 2 == 0){
	            	if (dir.getY() > 0 && prevVector.getY() < 0){
		            	System.out.print("Got here, ");
		            }
		            prevVector = dir;
	            }
	            
//	            System.out.println("Swipe id: " + swipe.id()
//	                       + ", " + swipe.state()
//	                       + ", position: " + swipe.position()
//	                       + ", direction: " + swipe.direction()
//	                       + ", speed: " + swipe.speed());
	            break;
	        case TYPE_KEY_TAP:
//	            KeyTapGesture keyTap = new KeyTapGesture(gesture);
//	            System.out.println("Key Tap id: " + keyTap.id()
//	                       + ", " + keyTap.state()
//	                       + ", position: " + keyTap.position()
//	                       + ", direction: " + keyTap.direction());
	            break;
	        default:
	            System.out.println("Unknown gesture type.");
	            break;
    	}
    	return;
    }
}

public class Leap {
    public static void initLeap() {
        // Create a sample listener and controller
        SampleListener listener = new SampleListener();
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
