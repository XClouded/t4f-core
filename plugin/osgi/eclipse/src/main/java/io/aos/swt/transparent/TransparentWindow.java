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
package io.aos.swt.transparent;

//import org.eclipse.swt.internal.win32.OS;

public class TransparentWindow {
/*
	Display display = null;

	Shell shell = new Shell(SWT.CLOSE | SWT.MODELESS);
	Label l = new Label(shell, SWT.NONE);
	private RGB transparentRGB;

	public static void main(String... args) {
		new TransparentWindow();
	}

	public TransparentWindow() {
		this.display = shell.getDisplay();
		System.load("C:\\WINDOWS\\system32\\OSEx.dll");
		OS.SetWindowLong(shell.handle, OS.GWL_EXSTYLE, 0x80000);
		transparentRGB = shell.getBackground().getRGB();

// @todo how does this work ?
// args: windowHandle, rgb.red,rgg.green,rgb.blue, alpha ,dw flags
//		OSEx.SetLayeredWindowAttributes(this.shell.handle, transparentRGB.red,
//				transparentRGB.green, transparentRGB.blue, 255, 1);

		l.setText("a transparent label");
		l.setSize(100, 20);

		this.shell.addPaintListener(new PaintListener() {

			public void paintControl(PaintEvent event) {
				Image imgTest = new Image(Display.getDefault(), "test.png");
				event.gc.drawImage(imgTest, 0, 0);
			}
		}

		);

		this.shell.setSize(256, 256);
		this.shell.open();
		this.shell.update();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}
*/
}
