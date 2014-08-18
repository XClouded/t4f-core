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
package io.aos.concurrent.spl0.cs07.example3;

import java.lang.reflect.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class FeedbackFrame extends JFrame implements Runnable {

    private SwingTypeTester stt;
    private Thread t;
    private JLabel label;
    private int state;

    static String[] stateMessages = {
        "Connecting to server...",
        "Logging into server...",
        "Waiting for data...",
        "Complete"
    };

    public FeedbackFrame(SwingTypeTester stt) {
        this.stt = stt;
        setupFrame();
        t = new Thread(this);
        t.start();
        pack();
        show();
    }

    private void setupFrame() {
        label = new JLabel();
        label.setPreferredSize(new Dimension(200, 200));
        Container c = getContentPane();
        JButton stopButton = new JButton("Stop");
        stopButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                error();
            }
        });
        c.add(label, BorderLayout.NORTH);
        c.add(stopButton, BorderLayout.SOUTH);
    }

    private void setText(final String s) {
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                    label.setText(s);
	        }
            });
        } catch (InterruptedException ie) {
            error();
        } catch (InvocationTargetException ite) {
            error();
        }
    }

    private void error() {
        t.interrupt();
        if (SwingUtilities.isEventDispatchThread())
            closeDown();
        else SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                closeDown();
           }
        });
    }

    private void closeDown() {
        stt.setupCancelled();
        hide();
        dispose();
    }

    public void run() {
        // Simulate connecting to server
        for (int i = 0; i < stateMessages.length; i++) {
            setText(stateMessages[i]);
            try {
                Thread.sleep(5 * 1000);
	    } catch (InterruptedException ie) {}
	    if (Thread.currentThread().isInterrupted())
	        return;
	}
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
              	stt.setupDone();
                hide();
                dispose();
	    }
        });
    }
}
