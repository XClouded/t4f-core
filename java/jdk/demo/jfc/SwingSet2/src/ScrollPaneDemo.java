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
 * @(#)ScrollPaneDemo.java	1.11 10/03/23
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
 * @(#)ScrollPaneDemo.java	1.11 10/03/23
 */


import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
import javax.swing.border.*;
import javax.swing.colorchooser.*;
import javax.swing.filechooser.*;
import javax.accessibility.*;

import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.util.*;
import java.io.*;
import java.applet.*;
import java.net.*;

/**
 * Scroll Pane Demo
 *
 * @version 1.11 03/23/10
 * @author Jeff Dinkins
 */
public class ScrollPaneDemo extends DemoModule {

    /**
     * main method allows us to run as a standalone demo.
     */
    public static void main(String[] args) {
	ScrollPaneDemo demo = new ScrollPaneDemo(null);
	demo.mainImpl();
    }

    /**
     * ScrollPaneDemo Constructor
     */
    public ScrollPaneDemo(SwingSet2 swingset) {
	super(swingset, "ScrollPaneDemo", "toolbar/JScrollPane.gif");

	ImageIcon crayons = createImageIcon("scrollpane/crayons.jpg",  getString("ScrollPaneDemo.crayons"));
	getDemoPanel().add(new ImageScroller(this, crayons), BorderLayout.CENTER);
    }


    /**
     * ScrollPane class that demonstrates how to set the various column and row headers
     * and corners.
     */
    class ImageScroller extends JScrollPane {
	public ImageScroller(ScrollPaneDemo demo, Icon icon) {
	    super();

	    // Panel to hold the icon image
	    JPanel p = new JPanel(new BorderLayout());
	    p.add(new JLabel(icon), BorderLayout.CENTER);
	    getViewport().add(p);

	    // Create and add a column header to the scrollpane
	    JLabel colHeader = new JLabel(
		demo.createImageIcon("scrollpane/colheader.jpg", getString("ScrollPaneDemo.colheader")));
	    setColumnHeaderView(colHeader);

	    // Create and add a row header to the scrollpane
	    JLabel rowHeader = new JLabel(
		demo.createImageIcon("scrollpane/rowheader.jpg", getString("ScrollPaneDemo.rowheader")));
	    setRowHeaderView(rowHeader);

	    // Create and add the upper left corner
	    JLabel cornerUL = new JLabel(
		demo.createImageIcon("scrollpane/upperleft.jpg", getString("ScrollPaneDemo.upperleft")));
	    setCorner(UPPER_LEFT_CORNER, cornerUL);

	    // Create and add the upper right corner
	    JLabel cornerUR = new JLabel(
		demo.createImageIcon("scrollpane/upperright.jpg", getString("ScrollPaneDemo.upperright")));
	    setCorner(UPPER_RIGHT_CORNER, cornerUR);

	    // Create and add the lower left corner
	    JLabel cornerLL = new JLabel(
		demo.createImageIcon("scrollpane/lowerleft.jpg", getString("ScrollPaneDemo.lowerleft")));
	    setCorner(LOWER_LEFT_CORNER, cornerLL);

	    JScrollBar vsb = getVerticalScrollBar();
	    JScrollBar hsb = getHorizontalScrollBar();

	    vsb.setValue(icon.getIconHeight());
	    hsb.setValue(icon.getIconWidth()/10);
	}
    }

}

