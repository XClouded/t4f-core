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
package io.aos.concurrent.spl0.cs08.example4;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

public class CharacterTracker extends JPanel {
    private CharCounter counter;
    private AbstractTableModel model;

    private static String[] colName = { "Character", "Num Correct", "Num Incorrect" };
    private static int[] charAt = {
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
            'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z' };

    private class CharacterTrackerModel extends AbstractTableModel {
        public int getColumnCount() { return 3; }
        public int getRowCount() { return charAt.length; };
        public String getColumnName(int col) { return colName[col]; }
        public Object getValueAt(int row, int col) {
            switch(col) {
                case 0: return Character.toString((char)charAt[row]);
        	case 1: return Integer.toString(counter.getCorrectNum(charAt[row]));
        	case 2: return Integer.toString(counter.getIncorrectNum(charAt[row]));
        	default: throw new IllegalArgumentException("Too many cols");
            }
        }
        public Class getColumnClass(int c) { return String.class; }
    }

    public CharacterTracker(CharCounter cc) {
        counter = cc;
        model = new CharacterTrackerModel();
        counter.addModel(model);
        setLayout(new BorderLayout());
        JScrollPane jsp = new JScrollPane(new JTable(model));
        add(jsp, BorderLayout.CENTER);
    }
}
