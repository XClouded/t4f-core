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

import java.awt.Dimension;
import java.awt.Font;
import java.io.OutputStream;

import javax.swing.JTextArea;

public class JTextAreaOutputStream extends JTextArea {
    private static final long serialVersionUID = 1L;

    private OutputStream outputStream = new TextAreaOutputStream();

    public JTextAreaOutputStream() {
        this("", 12, 20);
    }

    public JTextAreaOutputStream(String text) {
        this(text, 12, 20);
    }

    public JTextAreaOutputStream(int rows, int columns) {
        this("", rows, columns);
    }

    public JTextAreaOutputStream(String text, int rows, int columns) {
        super(text, rows, columns);
        this.setEditable(false);
        this.setFont(new Font("Monospaced", Font.PLAIN, 12));
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public Dimension getMinimumSize() {
        return new Dimension(72, 200);
    }

    public Dimension getPreferredSize() {
        return new Dimension(60 * 12, getLineCount() * 12);
    }

    class TextAreaOutputStream extends OutputStream {

        public void write(int b) {
            // recall that the int should really just be a byte
            b &= 0x000000FF;
            // must convert byte to a char in order to append it
            char c = (char) b;
            append(String.valueOf(c));
        }

        public void write(byte[] b, int offset, int length) {
            append(new String(b, offset, length));
        }

    }

}
