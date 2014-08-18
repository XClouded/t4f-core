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
package io.aos.swt.colorchooser;

import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.graphics.*;

public class SwtColorChooserSample01 {

	public static void main(String... args) {

		final Display display = new Display();
		final Shell shell = new Shell(display);

		shell.setLayout(new GridLayout());
		shell.setSize(300, 300);

		Button btnOuvrir = new Button(shell, SWT.PUSH);

		btnOuvrir.setText("Couleur");
		btnOuvrir.addListener(SWT.Selection, new Listener() {

			public void handleEvent(Event e) {
				Color couleurDeFond = shell.getBackground();

				ColorDialog colorDialog = new ColorDialog(shell);
				colorDialog.setRGB(couleurDeFond.getRGB());
				RGB couleur = colorDialog.open();

				if (couleur != null) {
					if (couleurDeFond != null)
						couleurDeFond.dispose();
					couleurDeFond = new Color(display, couleur);
					shell.setBackground(couleurDeFond);
				}

			}
		});

		shell.getBackground().dispose();
		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

}
