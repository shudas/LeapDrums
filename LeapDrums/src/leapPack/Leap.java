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

        GestureList gestures = frame.gestures();
        for (int i = 0; i < gestures.count(); i++) {
            Gesture gesture = gestures.get(i);
            recognize(gesture);
        }

        if (!frame.hands().isEmpty() || !gestures.isEmpty()) {
            System.out.println();
        }
    }
    
    private void recognize(Gesture gesture){
    	switch (gesture.type()) {
	    	case TYPE_SWIPE:
	            SwipeGesture swipe = new SwipeGesture(gesture);
	            System.out.println("Swipe id: " + swipe.id()
	                       + ", " + swipe.state()
	                       + ", position: " + swipe.position()
	                       + ", direction: " + swipe.direction()
	                       + ", speed: " + swipe.speed());
	            break;
	        case TYPE_KEY_TAP:
	            KeyTapGesture keyTap = new KeyTapGesture(gesture);
	            System.out.println("Key Tap id: " + keyTap.id()
	                       + ", " + keyTap.state()
	                       + ", position: " + keyTap.position()
	                       + ", direction: " + keyTap.direction());
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
