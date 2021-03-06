/****************************************************************
 * Licensed to the AOS Community (AOS) under one or more        *
 * contributor license agreements.  See the NOTICE file         *
 * distributed with this work for additional information        *
 * regarding copyright ownership.  The AOS licenses this file   *
 * to you under the Apache License, Version 2.0 (the            *
 * "License"); you may not use this file except in compliance   *
 * with the License.  You may obtain a copy of the License at   *
 *                                                              *
 *   http://www.apache.org/licenses/LICENSE-2.0                 *
 *                                                              *
 * Unless required by applicable law or agreed to in writing,   *
 * software distributed under the License is distributed on an  *
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY       *
 * KIND, either express or implied.  See the License for the    *
 * specific language governing permissions and limitations      *
 * under the License.                                           *
 ****************************************************************/
/*
 * @(#)DelayedSoundArea.java	1.18 10/03/23
 * 
 * Copyright (c) 2006, Oracle and/or its affiliates. All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * -Redistribution of source code must retain the above copyright notice, this
 *  list of conditions and the following disclaimer.
 * 
 * -Redistribution in binary form must reproduce the above copyright notice, 
 *  this list of conditions and the following disclaimer in the documentation
 *  and/or other materials provided with the distribution.
 * 
 * Neither the name of Oracle or the names of contributors may 
 * be used to endorse or promote products derived from this software without 
 * specific prior written permission.
 * 
 * This software is provided "AS IS," without a warranty of any kind. ALL 
 * EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING
 * ANY IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE
 * OR NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN MICROSYSTEMS, INC. ("SUN")
 * AND ITS LICENSORS SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE
 * AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES. IN NO EVENT WILL SUN OR ITS LICENSORS BE LIABLE FOR ANY LOST 
 * REVENUE, PROFIT OR DATA, OR FOR DIRECT, INDIRECT, SPECIAL, CONSEQUENTIAL, 
 * INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER CAUSED AND REGARDLESS OF THE THEORY 
 * OF LIABILITY, ARISING OUT OF THE USE OF OR INABILITY TO USE THIS SOFTWARE, 
 * EVEN IF SUN HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 * 
 * You acknowledge that this software is not designed, licensed or intended
 * for use in the design, construction, operation or maintenance of any
 * nuclear facility.
 */

/*
 * @(#)DelayedSoundArea.java	1.18 10/03/23
 */

import java.awt.Graphics;
import java.applet.AudioClip;
import java.net.URL;
import java.net.MalformedURLException;
import java.util.StringTokenizer;

/**
 * This ImageArea Class will play a sound each time the user enters the 
 * area. It is different from SoundArea in that it accepts a delay (in 
 * tenths of a second) before it plays the sound. If the mouse leaves
 * the area before the time delay, the sound is not played.
 *
 * This allows you to have one piece of audio when the button is "hit"
 * via SoundArea and another if the user stays on the button.
 *
 * @author 	Chuck McManis
 * @version 	1.18, 03/23/10
 */
class DelayedSoundArea extends ImageMapArea {
    /** The URL of the sound to be played. */
    URL 	sound;
    AudioClip	soundData;
    boolean 	hasPlayed; 
    int	    	delay;
    int		countDown;

    /**
     * The argument is the URL of the sound to be played.
     * This method also sets this type of area to be non-terminal.
     */
    public void handleArg(String arg) {
	Thread soundLoader;
	StringTokenizer st = new StringTokenizer(arg, ", ");
	
	delay = Integer.parseInt(st.nextToken());
	try {
	    sound = new URL(parent.getDocumentBase(), st.nextToken());
	} catch (MalformedURLException e) {
	    sound = null;
	}
    }

    public void getMedia() {
	if (sound != null) {
	    soundData = parent.getAudioClip(sound);
	}
	if (soundData == null) {
	    System.out.println("DelayedSoundArea: Unable to load data "+sound);
	}
    }

    /**
     * The highlight method plays the sound in addition to the usual
     * graphical highlight feedback.
     */
    public void enter() {
	hasPlayed = false;
	countDown = delay;
	parent.startAnimation();
    }

    /**
     * This method is called every animation cycle if there are any
     * active animating areas.
     * @return true if this area requires further animation notifications
     */
    public boolean animate() {
	if (entered && ! hasPlayed) {
	    if (countDown > 0) {
		countDown--;
		return true;
	    }
	    hasPlayed = true;
	    if (soundData != null) {
	        soundData.play();
	    }
	}
	return false;
    }
}

