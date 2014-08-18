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
package io.aos.swt.draw2d;

import org.eclipse.draw2d.Button;
import org.eclipse.draw2d.CheckBox;
import org.eclipse.draw2d.Clickable;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Ellipse;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FlowLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.Shape;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class SwtDraw2DSample01 {

	public static void main(String... args) {

		Shell shell = new Shell();
		shell.open();
		shell.setText("Draw2d");
		LightweightSystem lws = new LightweightSystem(shell);
		IFigure panel = new Figure();
		panel.setLayoutManager(new FlowLayout());
		lws.setContents(panel);

		Clickable button = new Button("Click me");
		Clickable checkbox = new CheckBox("Check box");

		Shape ellipse = new Ellipse();
		ellipse.setBackgroundColor(ColorConstants.yellow);

		Shape rectangle = new RectangleFigure();
		rectangle.setBackgroundColor(ColorConstants.lightBlue);

		panel.add(button);
		panel.add(checkbox);
		panel.add(ellipse);
		panel.add(rectangle);

		Display display = Display.getDefault();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}
}
