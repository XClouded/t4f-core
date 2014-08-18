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

public class Ch6GridLayoutAllOptionsComposite extends Composite {

	public Ch6GridLayoutAllOptionsComposite(Composite parent) {
		super(parent, SWT.NONE);

		int[] fillStyles = {SWT.NONE, GridData.FILL_HORIZONTAL, GridData.FILL_VERTICAL, GridData.FILL_BOTH
		};
    
    int [] alignStyles = {SWT.NONE,
                          GridData.HORIZONTAL_ALIGN_BEGINNING, GridData.HORIZONTAL_ALIGN_END,
                          GridData.HORIZONTAL_ALIGN_CENTER, GridData.HORIZONTAL_ALIGN_FILL,
                          GridData.VERTICAL_ALIGN_BEGINNING, GridData.VERTICAL_ALIGN_END,
                          GridData.VERTICAL_ALIGN_CENTER, GridData.VERTICAL_ALIGN_FILL
    };
    
    GridLayout layout = new GridLayout(fillStyles.length, false);
		setLayout(layout);
    int count=0;
		for (int i = 0; i < alignStyles.length; ++i) {
      for(int j = 0; j < fillStyles.length; ++j) {
  			Button button = new Button(this, SWT.NONE);
  			button.setText("Cell " + count++);
        button.setLayoutData(new GridData(fillStyles[j] | alignStyles[i]));
      }
		}
	}
}
