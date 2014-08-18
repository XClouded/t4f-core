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

import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.CoolBar;
import org.eclipse.swt.widgets.CoolItem;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

public class CoolBarTest extends ApplicationWindow {
	public CoolBarTest() {
		super(null);
	}

	protected Control createContents(Composite parent) {
		// --- Create the window title. ---

		getShell().setText("CoolBar Test");

		String asCoolItemSection[] = { "File", "Formatting", "Search" };
		CoolBar composite = new CoolBar(parent, SWT.NONE);
		for (int idxCoolItem = 0; idxCoolItem < 3; ++idxCoolItem) {
			CoolItem item = new CoolItem(composite, SWT.NONE);
			ToolBar tb = new ToolBar(composite, SWT.FLAT);
			for (int idxItem = 0; idxItem < 3; ++idxItem) {
				ToolItem ti = new ToolItem( tb, SWT.NONE );
				ti.setText( asCoolItemSection[ idxCoolItem ] + " Item #" + idxItem );
			}
			Point p = tb.computeSize( SWT.DEFAULT, SWT.DEFAULT );
			tb.setSize( p );
			Point p2 = item.computeSize( p.x, p.y);
			item.setControl(tb);
			item.setSize( p2);
		}
		return composite;
	}

	public static void main(String... args) {
		// --- Display CoolBarTest until the window is closed. ---

		CoolBarTest app = new CoolBarTest();
		app.setBlockOnOpen(true);
		app.open();
		Display.getCurrent().dispose();
	}
}
