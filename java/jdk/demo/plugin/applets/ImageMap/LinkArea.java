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
 * @(#)LinkArea.java	1.17 10/03/23
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
 * @(#)LinkArea.java	1.17 10/03/23
 */

import java.awt.Graphics;
import java.net.URL;
import java.net.MalformedURLException;

/**
 * The classic "Fetch a URL" ImageArea class.
 * This class extends the basic ImageArea Class to fetch a URL when
 * the user clicks in the area.
 *
 * @author 	Jim Graham
 * @version 	1.17, 03/23/10
 */
class LinkArea extends ImageMapArea {
    /** The URL to be fetched when the user clicks on this area. */
    URL anchor;

    /**
     * The argument string is the URL to be fetched.
     */
    public void handleArg(String arg) {
	try {
	    anchor = new URL(parent.getDocumentBase(), arg);
	} catch (MalformedURLException e) {
	    anchor = null;
	}
    }

    /**
     * The isTerminal method indicates whether events should propagate
     * to the areas underlying this one.
     */
    public boolean isTerminal() {
	return true;
    }

    /**
     * The status message area is updated to show the destination URL.
     */
    public void enter() {
	showStatus((anchor != null)
		   ? "Go To " + anchor.toExternalForm()
		   : null);
    }

    /**
     * The status message area is updated to show the destination URL.
     */
    public void exit() {
	showStatus(null);
    }

    /**
     * The new URL is fetched when the user releases the mouse button
     * only if they are still in the area.
     */
    public boolean lift(int x, int y) {
	if (inside(x, y) && anchor != null) {
	    showDocument(anchor);
	}
	return true;
    }
}
