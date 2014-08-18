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
package io.aos.jface.Ch3;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Main {

public static void main (String [] args) {
	
	final Display display = new Display ();
	Shell shell = new Shell(display);
	shell.setText("Radio Buttons");
	shell.pack();

	Button[] radios = new Button[3];
	
	radios[0] = new Button(shell, SWT.RADIO);
	radios[0].setSelection(true);
	radios[0].setText("Choice 1");
	radios[0].setBounds(10,5,75,30);

	radios[1] = new Button(shell, SWT.RADIO);
	radios[1].setText("Choice 2");
	radios[1].setBounds(10,30,75,30);

	radios[2] = new Button(shell, SWT.RADIO);
	radios[2].setText("Choice 3");
	radios[2].setBounds(10,55,75,30);
	
	shell.open();
	shell.pack();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
} 

