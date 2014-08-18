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
package io.aos.crypto.awt;

import io.aos.crypto.util.*;

import java.awt.*;
import java.awt.event.*;

public class SeederDialog extends Dialog implements ActionListener, KeyListener {
    private ProgressBar mProgressBar;
    private Seeder mSeeder;

    public SeederDialog(Frame parent, int seedBytes) {
        super(parent, "Seeder Dialog", true);
        setupWindow(seedBytes);
    }

    public byte[] getSeed() {
        return mSeeder.getSeed();
    }

    public void actionPerformed(ActionEvent ae) {
        dispose();
    }

    public void keyPressed(KeyEvent ke) {
    }

    public void keyReleased(KeyEvent ke) {
    }

    public void keyTyped(KeyEvent ke) {
        mProgressBar.setLevel(mSeeder.getCurrentBitIndex());
    }

    protected void setupWindow(int seedBytes) {
        setFont(new Font("TimesRoman", Font.PLAIN, 12));
        setLayout(new GridLayout(4, 1));
        Label t1 = new Label("Please type some keys");
        Label t2 = new Label("to initialize the random");
        Label t3 = new Label("number generator.");
        add(t1);
        add(t2);
        add(t3);
        mProgressBar = new ProgressBar();
        Panel p = new Panel();
        p.add(mProgressBar);
        add(p);

        setSize(200, 200);
        setLocation(100, 100);
        pack();

        mSeeder = new Seeder(seedBytes);
        mProgressBar.setMaximum(mSeeder.getBitLength());
        mSeeder.addActionListener(this);

        t1.addKeyListener(mSeeder);
        t1.addKeyListener(this);
        t1.requestFocus();
    }
}
