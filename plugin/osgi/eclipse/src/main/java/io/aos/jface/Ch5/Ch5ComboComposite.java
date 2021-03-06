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
package io.aos.jface.Ch5;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

public class Ch5ComboComposite extends Composite {

    public Ch5ComboComposite(Composite parent) {
        super(parent, SWT.NONE);
        buildControls();
    }

    protected void buildControls() {
        setLayout(new RowLayout());

        int[] comboStyles = { SWT.SIMPLE, 
                              SWT.DROP_DOWN, 
                              SWT.READ_ONLY };

        for (int idxComboStyle = 0;
            idxComboStyle < comboStyles.length;
            ++idxComboStyle) {
            Combo combo = new Combo(this, 
                                    comboStyles[idxComboStyle]);
            combo.add("Option #1");
            combo.add("Option #2");
            combo.add("Option #3");
        }
    }
}
