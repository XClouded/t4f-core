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

import java.awt.GridLayout;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class ModeJPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    
    private JCheckBox bigEndian = new JCheckBox("Big Endian", true);
    private JCheckBox deflated = new JCheckBox("Deflated", false);
    private JCheckBox gzipped = new JCheckBox("GZipped", false);

    private ButtonGroup dataTypes = new ButtonGroup();
    private JRadioButton asciiRadio = new JRadioButton("ASCII");
    private JRadioButton decimalRadio = new JRadioButton("Decimal");
    private JRadioButton hexRadio = new JRadioButton("Hexadecimal");
    private JRadioButton shortRadio = new JRadioButton("Short");
    private JRadioButton intRadio = new JRadioButton("Int");
    private JRadioButton longRadio = new JRadioButton("Long");
    private JRadioButton floatRadio = new JRadioButton("Float");
    private JRadioButton doubleRadio = new JRadioButton("Double");

    private JTextField password = new JPasswordField();

    public ModeJPanel() {

        this.setLayout(new GridLayout(13, 1));
        this.add(bigEndian);
        this.add(deflated);
        this.add(gzipped);

        this.add(asciiRadio);
        asciiRadio.setSelected(true);
        this.add(decimalRadio);
        this.add(hexRadio);
        this.add(shortRadio);
        this.add(intRadio);
        this.add(longRadio);
        this.add(floatRadio);
        this.add(doubleRadio);

        dataTypes.add(asciiRadio);
        dataTypes.add(decimalRadio);
        dataTypes.add(hexRadio);
        dataTypes.add(shortRadio);
        dataTypes.add(intRadio);
        dataTypes.add(longRadio);
        dataTypes.add(floatRadio);
        dataTypes.add(doubleRadio);

        this.add(password);
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
