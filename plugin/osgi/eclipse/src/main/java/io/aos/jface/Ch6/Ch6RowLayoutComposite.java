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
package io.aos.jface.Ch6;

import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.layout.*;

public class Ch6RowLayoutComposite extends Composite {

    public Ch6RowLayoutComposite(Composite parent) {
        super(parent, SWT.NONE);

        RowLayout layout = new RowLayout(SWT.HORIZONTAL);
        setLayout(layout);
        for (int i = 0; i < 16; ++i) {
            Button button = new Button(this, SWT.NONE);
            button.setText("Sample Text");
            button.setLayoutData(new RowData(200 + 5 * i, 20 + i));
        }
    }
}
