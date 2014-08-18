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
package io.aos.filesystem.ui.fileviewer;

import io.aos.pipe.bio.BioPipe;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ProgressMonitorInputStream;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class FileViewerUiMain extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;

    private JFileChooser chooser = new JFileChooser();
    private JTextAreaOutputStream jtextAreaOutputStream = new JTextAreaOutputStream();
    private ModeJPanel mp = new ModeJPanel();

    public static void main(String... args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        FileViewerUiMain viewer = new FileViewerUiMain();
        viewer.init();
        // This is a single window application
        viewer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        viewer.setVisible(true);
    }

    public void init() {
        chooser.setApproveButtonText("View File");
        chooser.setApproveButtonMnemonic('V');
        chooser.addActionListener(this);

        this.getContentPane().add(BorderLayout.CENTER, chooser);
        JScrollPane sp = new JScrollPane(jtextAreaOutputStream);
        sp.setPreferredSize(new Dimension(640, 400));
        this.getContentPane().add(BorderLayout.SOUTH, sp);
        this.getContentPane().add(BorderLayout.WEST, mp);
        this.pack();

        // Center on display.
        Dimension display = getToolkit().getScreenSize();
        Dimension bounds = this.getSize();

        int x = (display.width - bounds.width) / 2;
        int y = (display.height - bounds.height) / 2;
        if (x < 0)
            x = 10;
        if (y < 0)
            y = 15;
        this.setLocation(x, y);
    }

    public void actionPerformed(ActionEvent evt) {
        if (evt.getActionCommand().equals(JFileChooser.APPROVE_SELECTION)) {
            File f = chooser.getSelectedFile();
            if (f != null) {
                // theView.reset();
                try {
                    InputStream inputStream = new FileInputStream(f);
                    inputStream = new ProgressMonitorInputStream(this, "Reading...", inputStream);
                    OutputStream outputStream = jtextAreaOutputStream.getOutputStream();
                    BioPipe.transport(inputStream, outputStream, mp.getMode(), mp.isBigEndian(), mp.isDeflated(), mp.isGZipped(), mp.getPassword());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "I/O Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else if (evt.getActionCommand().equals(JFileChooser.CANCEL_SELECTION)) {
            this.setVisible(false);
            this.dispose();
        }
    }
}
