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
package io.aos.concurrent.applet;

import java.applet.Applet;
import java.awt.Dimension;
import java.awt.TextArea;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class AppletThreadLister extends Applet {
    private static final long serialVersionUID = 1210849161900083331L;
    private TextArea textarea;
    
    public void init() {
        textarea = new TextArea(20, 60);
        this.add(textarea);
        Dimension prefsize = textarea.preferredSize();
        this.resize(prefsize.width, prefsize.height);
    }
    
    // Do the listing.  Note the cool use of ByteArrayOutputStream.
    public void start() {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(os);
        ThreadLister.listAllThreads(ps);
        textarea.setText(os.toString());
    }
}
