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
package io.aos.charset;

import io.aos.pipe.bio.BioPipe;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ProgressMonitorInputStream;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class CharsetedFileViewerUiMain extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;

    private JFileChooser chooser = new JFileChooser();
    private JWritableTextArea theView = new JWritableTextArea();
    private TextModePanel mp = new TextModePanel();

    public static void main(String... args) throws ClassNotFoundException, InstantiationException,
            IllegalAccessException, UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        CharsetedFileViewerUiMain viewer = new CharsetedFileViewerUiMain();
        viewer.init();
        viewer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        viewer.setVisible(true);
    }

    public CharsetedFileViewerUiMain() {
        super("FileViewer");
    }

    public void init() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        chooser.setApproveButtonText("View File");
        chooser.setApproveButtonMnemonic('V');
        chooser.addActionListener(this);

        this.getContentPane().add(BorderLayout.EAST, chooser);
        JScrollPane sp = new JScrollPane(theView);
        sp.setPreferredSize(new Dimension(640, 400));
        this.getContentPane().add(BorderLayout.SOUTH, sp);
        this.getContentPane().add(BorderLayout.WEST, mp);
        this.pack();

        // Center on display
        Dimension display = getToolkit().getScreenSize();
        Dimension bounds = this.getSize();
        int x = (display.width - bounds.width) / 2;
        int y = (display.height - bounds.height) / 2;
        if (x < 0) {
            x = 10;
        }
        if (y < 0) {
            y = 15;
        }
        this.setLocation(x, y);

    }

    public void actionPerformed(ActionEvent evt) {

        if (evt.getActionCommand().equals(JFileChooser.APPROVE_SELECTION)) {
            File f = chooser.getSelectedFile();
            if (f != null) {
                theView.reset();
                try {
                    InputStream inputStream = new FileInputStream(f);
                    // This program was really slow until I buffered the stream.
                    inputStream = new BufferedInputStream(inputStream);
                    inputStream = new ProgressMonitorInputStream(this, "Reading...", inputStream);
                    if (! mp.isText()) {
                        BioPipe.transport(inputStream, theView.getWriter(), mp.getMode(), mp.isBigEndian(),
                                mp.isDeflated(), mp.isGZipped(), mp.getPassword());
                    }
                    else {
                        BioPipe.transport(inputStream, theView.getWriter(), mp.getEncoding(), null, mp.isDeflated(),
                                mp.isGZipped(), mp.getPassword());
                    }
                }
                catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "I/O Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        else if (evt.getActionCommand().equals(JFileChooser.CANCEL_SELECTION)) {
            this.setVisible(false);
            this.dispose();
            // This is a single window application
            System.exit(0);
        }

    }

    private class JWritableTextArea extends JTextArea {
        private static final long serialVersionUID = 1L;

        private Writer writer = new BufferedWriter(new TextAreaWriter());

        public JWritableTextArea() {
            this("", 0, 0);
        }

        public JWritableTextArea(String text, int rows, int columns) {
            super(text, rows, columns);
            setFont(new Font("Monospaced", Font.PLAIN, 12));
            setEditable(false);
        }

        public Writer getWriter() {
            return writer;
        }

        public void reset() {
            this.setText("");
            writer = new BufferedWriter(new TextAreaWriter());
        }

        private class TextAreaWriter extends Writer {
            private boolean closed = false;

            public void close() {
                closed = true;
            }

            public void write(char[] text, int offset, int length) throws IOException {
                if (closed)
                    throw new IOException("Write to closed stream");
                JWritableTextArea.this.append(new String(text, offset, length));
            }

            public void flush() {
            }
        }

    }

    private class TextModePanel extends JPanel {
        private static final long serialVersionUID = 1L;

        JCheckBox bigEndian = new JCheckBox("Big Endian", true);
        JCheckBox deflated = new JCheckBox("Deflated", false);
        JCheckBox gzipped = new JCheckBox("GZipped", false);

        ButtonGroup dataTypes = new ButtonGroup();
        JRadioButton asciiRadio = new JRadioButton("Text");
        JRadioButton decimalRadio = new JRadioButton("Decimal");
        JRadioButton hexRadio = new JRadioButton("Hexadecimal");
        JRadioButton shortRadio = new JRadioButton("Short");
        JRadioButton intRadio = new JRadioButton("Int");
        JRadioButton longRadio = new JRadioButton("Long");
        JRadioButton floatRadio = new JRadioButton("Float");
        JRadioButton doubleRadio = new JRadioButton("Double");

        JTextField password = new JPasswordField();
        JList encodings = new JList();

        public TextModePanel() {

            Map charsets = Charset.availableCharsets();
            encodings.setListData(charsets.keySet().toArray());

            this.setLayout(new GridLayout(1, 2));

            JPanel left = new JPanel();
            JScrollPane right = new JScrollPane(encodings);
            left.setLayout(new GridLayout(13, 1));
            left.add(bigEndian);
            left.add(deflated);
            left.add(gzipped);

            left.add(asciiRadio);
            asciiRadio.setSelected(true);
            left.add(decimalRadio);
            left.add(hexRadio);
            left.add(shortRadio);
            left.add(intRadio);
            left.add(longRadio);
            left.add(floatRadio);
            left.add(doubleRadio);

            dataTypes.add(asciiRadio);
            dataTypes.add(decimalRadio);
            dataTypes.add(hexRadio);
            dataTypes.add(shortRadio);
            dataTypes.add(intRadio);
            dataTypes.add(longRadio);
            dataTypes.add(floatRadio);
            dataTypes.add(doubleRadio);

            left.add(password);
            this.add(left);
            this.add(right);
        }

        public boolean isBigEndian() {
            return bigEndian.isSelected();
        }

        public boolean isDeflated() {
            return deflated.isSelected();
        }

        public boolean isGZipped() {
            return gzipped.isSelected();
        }

        public boolean isText() {
            if (this.getMode() == BioPipe.ASC)
                return true;
            return false;
        }

        public String getEncoding() {
            return (String) encodings.getSelectedValue();
        }

        public int getMode() {

            if (asciiRadio.isSelected())
                return BioPipe.ASC;
            else if (decimalRadio.isSelected())
                return BioPipe.DEC;
            else if (hexRadio.isSelected())
                return BioPipe.HEX;
            else if (shortRadio.isSelected())
                return BioPipe.SHORT;
            else if (intRadio.isSelected())
                return BioPipe.INT;
            else if (longRadio.isSelected())
                return BioPipe.LONG;
            else if (floatRadio.isSelected())
                return BioPipe.FLOAT;
            else if (doubleRadio.isSelected())
                return BioPipe.DOUBLE;
            else
                return BioPipe.ASC;

        }

        public String getPassword() {
            return password.getText();
        }

    }

}
