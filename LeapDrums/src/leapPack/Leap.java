package leapPack;

/******************************************************************************\
* Copyright (C) 2012-2013 Leap Motion, Inc. All rights reserved.               *
* Leap Motion proprietary and confidential. Not for distribution.              *
* Use subject to the terms of the Leap Motion SDK Agreement available at       *
* https://developer.leapmotion.com/sdk_agreement, or another agreement         *
* between Leap Motion and you, your company or other organization.             *
\******************************************************************************/

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.lang.Math;

import audio.AudioManager;

import com.leapmotion.leap.*;

public class Leap {
	public static LeapHit leapHit;
	public static LeapListener listener;
    private static Controller controller;
	
    public static void initLeap(String[] instrNames) {
        // Create a sample listener and controller
    	listener = new LeapListener(instrNames);
    	leapHit = listener.leapHit;
        controller = new Controller();
        // Have the sample listener receive events from the controller
        controller.addListener(listener);       
    }
    
    public static void disconnect(){
    	 // Remove the sample listener when done
        controller.removeListener(listener);
    }
}
