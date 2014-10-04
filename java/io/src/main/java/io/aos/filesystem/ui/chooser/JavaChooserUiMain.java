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

import io.aos.filesystem.filter.JavaFileFilter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class JavaChooserUiMain {

    public static void main(String... args) throws InterruptedException, InvocationTargetException {

        SwingUtilities.invokeAndWait(new Runnable() {

            public void run() {
            
                JFileChooser fc = new JFileChooser();
                fc.addChoosableFileFilter(new JavaFileFilter());
                int result = fc.showOpenDialog(new JFrame());
                if (result == JFileChooser.APPROVE_OPTION) {
                    try {
                        File f = fc.getSelectedFile();
                        if (f != null) {
                            InputStream inputStream = new FileInputStream(f);
                            for (int c = inputStream.read(); c != -1; c = inputStream.read()) {
                                System.out.write(c);
                            }
                            inputStream.close();
                        }
                    }
                    catch (IOException ex) {
                        System.err.println(ex);
                    }
                }
                System.exit(0);

            }
    
        });

    }

}
