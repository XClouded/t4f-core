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
package io.aos.filesystem.ui.chooser;

import io.aos.pipe.bio.BytePipe;

import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class TextPreviewChooserUiMain extends JTextArea implements PropertyChangeListener {
    private static final long serialVersionUID = 1L;

    private File selectedFile = null;
    private String preview = "";
    private int previewLength = 250;

    public static void main(String... args) {
    
        JFileChooser fc = new JFileChooser();
        fc.setAccessory(new TextPreviewChooserUiMain(fc));
        int result = fc.showOpenDialog(new JFrame());
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                File f = fc.getSelectedFile();
                if (f != null) {
                    FileInputStream fis = new FileInputStream(f);
                    BytePipe.transport(fis, System.out);
                    fis.close();
                }
            }
            catch (IOException e) {
                System.err.println(e);
            }
    
        }
    
        System.exit(0);
    
    }

    public TextPreviewChooserUiMain(JFileChooser fc) {
        super(10, 20);
        this.setEditable(false);
        this.setPreferredSize(new Dimension(150, 150));
        this.setLineWrap(true);
        fc.addPropertyChangeListener(this);
    }

    private void loadText() {

        if (selectedFile != null) {
            try {
                FileInputStream fis = new FileInputStream(selectedFile);
                byte[] data = new byte[previewLength];
                int bytesRead = 0;
                for (int i = 0; i < previewLength; i++) {
                    int b = fis.read();
                    if (b == -1)
                        break;
                    bytesRead++;
                    data[i] = (byte) b;
                }
                preview = new String(data, 0, bytesRead);
                fis.close();
            }
            catch (IOException e) {
                // File preview is not an essential operation so
                // we'll simply ignore the exception and return
            }
        }

    }

    public void propertyChange(PropertyChangeEvent e) {

        if (e.getPropertyName().equals(JFileChooser.SELECTED_FILE_CHANGED_PROPERTY)) {
            selectedFile = (File) e.getNewValue();
            if (isShowing()) {
                loadText();
                this.setText(preview);
            }
        }

    }

}
