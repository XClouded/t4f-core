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
 * @(#)ClickArea.java	1.17 10/03/23
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
 * @(#)ClickArea.java	1.17 10/03/23
 */

import java.awt.Graphics;

/**
 * An click feedback ImageArea class.
 * This class extends the basic ImageArea Class to show the locations
 * of clicks in the image in the status message area.  This utility
 * ImageArea class is useful when setting up ImageMaps.
 *
 * @author 	Jim Graham
 * @version 	1.17, 03/23/10
 */
class ClickArea extends ImageMapArea {
    /** The X location of the last mouse press. */
    int startx;
    /** The Y location of the last mouse press. */
    int starty;
    /** A boolean to indicate whether we are currently being dragged. */
    boolean dragging;

    static String ptstr(int x, int y) {
	return "("+x+", "+y+")";
    }

    /**
     * When the user presses the mouse button, start showing coordinate
     * feedback in the status message line.
     */
    public boolean press(int x, int y) {
	showStatus("Clicked at "+ptstr(x, y));
	startx = x;
	starty = y;
	dragging = true;
	return false;
    }

    /**
     * Update the coordinate feedback every time the user moves the mouse
     * while he has the button pressed.
     */
    public boolean drag(int x, int y) {
	showStatus("Rectangle from "+ptstr(startx, starty)
		   +" to "+ptstr(x, y)
		   +" is "+(x-startx)+"x"+(y-starty));
	return false;
    }

    /**
     * Update the coordinate feedback one last time when the user releases
     * the mouse button.
     */
    public boolean lift(int x, int y) {
	dragging = false;
	return drag(x, y);
    }

    /**
     * This utility method returns the status string this area wants to
     * put into the status bar.  If this area is currently animating
     * a message, then that message takes precedence over any other area
     * that a higher stacked area may want to display, otherwise the
     * message from the higher stacked area takes precedence.
     */
    public String getStatus(String prevmsg) {
	if (dragging) {
	    return (status != null) ? status : prevmsg;
	} else {
	    return (prevmsg == null) ? status : prevmsg;
	}
    }
}

