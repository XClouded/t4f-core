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
package io.aos.jface.sample.spl02;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;

public class JFaceSample02 extends ApplicationWindow {

	public JFaceSample02(Shell parentShell) {
		super(parentShell);
	}

	public static void main(String... args) {
		Display display = new Display();
		Shell aShell = new Shell(display);
		JFaceSample02 application = new JFaceSample02(aShell);
		application.createComponents();
		application.open();
		application.setStatus("sdqds");
	}

	public void createComponents() {
		addMenuBar();
		addStatusLine();
		addToolBar(SWT.FLAT | SWT.WRAP);
		setBlockOnOpen(true);
	};

	protected MenuManager createMenuManager() {
		MenuManager menuManager = new MenuManager();
		menuManager.add(createHelpMenu());
		return menuManager;
	}

	private MenuManager createHelpMenu() {
		MenuManager menu = new MenuManager("Help", "Id01");
		menu.add(new GroupMarker("About"));
		menu.add(createAboutAction());
		return menu;
	}

	private Action createAboutAction() {
		return new Action() {
			public String getText() {
				return "Essai";
			}

			public void run() {
				String[] tab = { IDialogConstants.OK_LABEL };
				MessageDialog dialog = new MessageDialog(getShell(),
						"Essai JFace", null,
						"Ceci est une fen�tre d''information",
						MessageDialog.INFORMATION, tab, 0);
				dialog.open();
			}
		};
	}

	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText("Essai JFace");
	}

	protected Point getInitialSize() {
		return new Point(600, 400);
	}

	protected Point getInitialLocation() {
		return new Point(600, 400);
	}

	protected void handleShellCloseEvent() {
		String[] tab = { IDialogConstants.OK_LABEL };
		MessageDialog dialog = new MessageDialog(getShell(), "Fin de l'essai",
				null, "Nous allons quitter l'application",
				MessageDialog.INFORMATION, tab, 0);
		dialog.open();
		setReturnCode(CANCEL);
		close();
	}

	protected ToolBarManager createToolBarManager(int style) {
		return new ToolBarManager(style);
	}

	protected Control createContents(Composite parent) {
		Table aComposite = new Table(parent, SWT.BORDER);
		return aComposite;
	}

}
