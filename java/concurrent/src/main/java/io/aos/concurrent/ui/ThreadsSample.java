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
package io.aos.concurrent.ui;

import javax.swing.*;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ThreadsSample extends JFrame implements ActionListener, Runnable {
    
    // Constructor
    ThreadsSample() {
        // Create a frame with a button and a text field
        GridLayout gl = new GridLayout(2, 1);
        this.getContentPane().setLayout(gl);
        JButton myButton = new JButton("Kill Time");
        myButton.addActionListener(this);
        this.getContentPane().add(myButton);
        this.getContentPane().add(new JTextField());
    }
    
    public void actionPerformed(ActionEvent e) {
        // Create a thread and execute the kill-time-code
        // without blocking the window
        Thread worker = new Thread(this);
        worker.start(); // this calls the method run()
    }
    
    public void run() {
        // Just  kill some time to show that
        // window controls are NOT locked
        for (int i = 0; i < 30000; i++) {
            this.setTitle("i=" + i);
        }
    }
    
    public static void main(String... args) {
        
        ThreadsSample myWindow = new ThreadsSample();
        // Ensure that the window can be closed
        // by pressing a little cross in the corner
        myWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        // Set the frame's size and make it visible
        myWindow.setBounds(0, 0, 150, 100);
        myWindow.setVisible(true);
    }
}
