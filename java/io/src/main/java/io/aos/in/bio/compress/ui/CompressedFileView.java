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

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.filechooser.FileView;

public class CompressedFileView extends FileView {
    private ImageIcon zipIcon = new ImageIcon("images/zipIcon.gif");
    private ImageIcon gzipIcon = new ImageIcon("images/gzipIcon.gif");
    private ImageIcon deflateIcon = new ImageIcon("images/deflateIcon.gif");

    public String getName(File f) {
        return f.getName();
    }

    public String getTypeDescription(File f) {
        if (f.getName().endsWith(".zip"))
            return "Zip archive";
        if (f.getName().endsWith(".gz"))
            return "Gzipped file";
        if (f.getName().endsWith(".dfl"))
            return "Deflated file";
        return null;
    }

    public Icon getIcon(File f) {
        if (f.getName().endsWith(".zip"))
            return zipIcon;
        if (f.getName().endsWith(".gz"))
            return gzipIcon;
        if (f.getName().endsWith(".dfl"))
            return deflateIcon;
        return null;
    }

    public String getDescription(File f) {
        return null;
    }

    public Boolean isTraversable(File f) {
        return null;
    }
}
