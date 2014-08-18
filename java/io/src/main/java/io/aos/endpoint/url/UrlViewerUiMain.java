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
package io.aos.endpoint.url;

import java.awt.BorderLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class UrlViewerUiMain extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;

    private JTextField urlTextField = new JTextField();
    private JButton loadButton = new JButton("Load");
    private JStreamedTextArea streamedTextArea = new JStreamedTextArea(60, 72);

    public static void main(String... args) {
        final UrlViewerUiMain me = new UrlViewerUiMain();
        // To avoid deadlock don't show frames on the mainputStreamthread
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                me.show();
            }
        });
    }

    public UrlViewerUiMain() {
        super("URL Viewer");
        this.getContentPane().add(BorderLayout.NORTH, urlTextField);
        JScrollPane pane = new JScrollPane(streamedTextArea);
        this.getContentPane().add(BorderLayout.CENTER, pane);
        Panel south = new Panel();
        south.add(loadButton);
        this.getContentPane().add(BorderLayout.SOUTH, south);
        urlTextField.addActionListener(this);
        loadButton.addActionListener(this);
        this.setLocation(50, 50);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
    }

    public void actionPerformed(ActionEvent event) {
        try {
            URL u = new URL(urlTextField.getText());
            InputStream inputStream = u.openStream();
            OutputStream outputStream = streamedTextArea.getOutputStream();
            streamedTextArea.setText("");
            for (int c = inputStream.read(); c != -1; c = inputStream.read()) {
                outputStream.write(c);
            }
            inputStream.close();
        }
        catch (IOException ex) {
            streamedTextArea.setText("Invalid URL: " + ex.getMessage());
        }
    }

    public class JStreamedTextArea extends JTextArea {
        private static final long serialVersionUID = 1L;

        private OutputStream theOutput = new TextAreaOutputStream();

        public JStreamedTextArea() {
            this("", 0, 0);
        }

        public JStreamedTextArea(String text) {
            this(text, 0, 0);
        }

        public JStreamedTextArea(int rows, int columns) {
            this("", rows, columns);
        }

        public JStreamedTextArea(String text, int rows, int columns) {
            super(text, rows, columns);
            setEditable(false);
        }

        public OutputStream getOutputStream() {
            return theOutput;
        }

        private class TextAreaOutputStream extends OutputStream {

            private boolean closed = false;

            public void write(int b) throws IOException {
                checkOpen();
                // recall that the int should really just be a byte
                b &= 0x000000FF;
                // must convert byte to a char inputStreamorder to append it
                char c = (char) b;
                append(String.valueOf(c));
            }

            private void checkOpen() throws IOException {
                if (closed)
                    throw new IOException("Write to closed stream");
            }

            public void write(byte[] data, int offset, int length) throws IOException {
                checkOpen();
                append(new String(data, offset, length));
            }

            public void close() {
                this.closed = true;
            }

        }

    }

}
