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
package io.aos.endpoint.file.bio;

import java.awt.Button;
import java.awt.Event;
import java.awt.Font;
import java.awt.Frame;
import java.awt.TextArea;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class BioViewerTest extends Frame {
    private static final long serialVersionUID = 1L;

    private Button close;

    static public void main(String... args) throws IOException {
        args = new String[] { "pom.xml" };
        try {
            Frame f = new BioViewerTest(args[0]);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public BioViewerTest(String filename) throws IOException {
        super("FileViewer: " + filename);
        File f = new File(filename);
        int size = (int) f.length();
        int bytes_read = 0;
        FileInputStream inputStream = new FileInputStream(f);
        byte[] data = new byte[size];
        while (bytes_read < size)
            bytes_read += inputStream.read(data, bytes_read, size - bytes_read);

        TextArea textarea = new TextArea(new String(data, 0), 24, 80);
        textarea.setFont(new Font("Helvetica", Font.PLAIN, 12));
        textarea.setEditable(false);
        this.add("Center", textarea);

        close = new Button("Close");
        this.add("South", close);
        this.pack();
        this.show();
    }

    // Handle the close button by popping this window down
    public boolean action(Event e, Object what) {
        if (e.target == close) {
            this.hide();
            this.dispose();
            return true;
        }
        return false;
    }
}
