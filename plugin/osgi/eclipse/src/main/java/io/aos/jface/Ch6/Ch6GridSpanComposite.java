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

public class Ch6GridSpanComposite extends Composite {

	public Ch6GridSpanComposite(Composite parent) {
		super(parent, SWT.NONE);

    GridLayout layout = new GridLayout(3, false);
		setLayout(layout);
    
    Button b = new Button(this, SWT.NONE);
    b.setText("Button 1");
    
    b = new Button(this, SWT.NONE);
    b.setText("Button 2");
    b.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
    
    b = new Button(this, SWT.NONE);
    b.setText("Button 3");
    
    Text t = new Text(this, SWT.MULTI);
    GridData data = new GridData(GridData.FILL_BOTH);
    data.horizontalSpan = 2;
    data.verticalSpan = 2;
    t.setLayoutData(data);
    
    b = new Button(this, SWT.NONE);
    b.setText("Button 4");
    b.setLayoutData(new GridData(GridData.FILL_VERTICAL));
    
    b = new Button(this, SWT.NONE);
    b.setText("Button 5");
    b.setLayoutData(new GridData(GridData.FILL_VERTICAL));
    
	}
}
