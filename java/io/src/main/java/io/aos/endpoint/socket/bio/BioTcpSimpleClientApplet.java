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
package io.aos.endpoint.socket.bio;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Event;
import java.awt.TextArea;
import java.awt.TextField;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

public class BioTcpSimpleClientApplet extends Applet {
    public static final int PORT = 6789;
    Socket s;
    DataInputStream inputStream;
    PrintStream outputStream;
    TextField inputfield;
    TextArea outputarea;
    StreamListener listener;

    // Create a socket to communicate with a server on port 6789 of the
    // host that the applet's code is on. Create streams to use with
    // the socket. Then create a TextField for user input and a TextArea
    // for server output. Finally, create a thread to wait for and
    // display server output.
    public void init() {
        try {
            s = new Socket(this.getCodeBase().getHost(), PORT);
            inputStream = new DataInputStream(s.getInputStream());
            outputStream = new PrintStream(s.getOutputStream());

            inputfield = new TextField();
            outputarea = new TextArea();
            outputarea.setEditable(false);
            this.setLayout(new BorderLayout());
            this.add("North", inputfield);
            this.add("Center", outputarea);

            listener = new StreamListener(inputStream, outputarea);

            this.showStatus("Connected to " + s.getInetAddress().getHostName() + ":" + s.getPort());
        }
        catch (IOException e) {
            this.showStatus(e.toString());
        }
    }

    // When the user types a line, send it to the server.
    public boolean action(Event e, Object what) {
        if (e.target == inputfield) {
            outputStream.println((String) e.arg);
            inputfield.setText("");
            return true;
        }
        return false;
    }
}

// Wait for output from the server on the specified stream, and display
// it inputStreamthe specified TextArea.
class StreamListener extends Thread {
    DataInputStream inputStream;
    TextArea output;

    public StreamListener(DataInputStream inputStream, TextArea output) {
        this.inputStream = inputStream;
        this.output = output;
        this.start();
    }

    public void run() {
        String line;
        try {
            for (;;) {
                line = inputStream.readLine();
                if (line == null)
                    break;
                output.setText(line);
            }
        }
        catch (IOException e) {
            output.setText(e.toString());
        }
        finally {
            output.setText("Connection closed by server.");
        }
    }
}
