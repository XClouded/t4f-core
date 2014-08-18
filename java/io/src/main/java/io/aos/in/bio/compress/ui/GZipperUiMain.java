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
package io.aos.in.bio.compress.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.zip.GZIPOutputStream;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class GZipperUiMain {
    public final static String GZIP_SUFFIX = ".gz";

    public static void main(String... args) throws InterruptedException, InvocationTargetException,
            ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {

        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        SwingUtilities.invokeAndWait(new Runnable() {

            public void run() {

                JFrame parent = new JFrame();
                JFileChooser fc = new JFileChooser();
                fc.setDialogTitle("Please choose a file to gzip: ");
                fc.setApproveButtonMnemonic('g');

                while (true) {
                    int result = fc.showDialog(parent, "Select a file, then press this button to gzip it");
                    if (result == JFileChooser.APPROVE_OPTION) {
                        try {
                            File f = fc.getSelectedFile();
                            if (f == null) {
                                JOptionPane.showMessageDialog(parent, "Can only gzip files, not directories");
                            }
                            else {
                                InputStream inputStream = new FileInputStream(f);
                                FileOutputStream fout = new FileOutputStream(f.getAbsolutePath() + GZIP_SUFFIX);
                                OutputStream gzout = new GZIPOutputStream(fout);
                                for (int c = inputStream.read(); c != -1; c = inputStream.read()) {
                                    gzout.write(c);
                                }
                                // These next two should be inputStreama finally
                                // block; but the multiple
                                // nested try-catch blocks just got way too
                                // complicated for a
                                // simple example
                                inputStream.close();
                                gzout.close();
                            }
                        }
                        catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                    else {
                        System.exit(0);
                    }
                }
            }

        });

    }

}
