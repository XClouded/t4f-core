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

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class SimpleFileChooser {

    public static void main(String... args) throws IOException {
        File f = getFile();
        FileInputStream fin = new FileInputStream(f);
        BytePipe.transport(fin, System.out);
    }

    public static File getFile() throws IOException {

        // Dummy Frame, never shown
        Frame parent = new Frame();
        FileDialog fd = new FileDialog(parent, "Please choose a file:", FileDialog.LOAD);
        fd.setVisible(true);

        String dir = fd.getDirectory();
        String file = fd.getFile();

        parent.dispose();
        fd.dispose();

        // User cancelled the dialog?
        if (dir == null || file == null) {
            return null;
        }

        return new File(dir, file);

    }

}
