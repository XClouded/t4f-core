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
package io.aos.swt.spl;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

public class MyShell {

	public static void main(String... args) {

		// Display and Shell.
		Display myDisplay = new Display();
		final Shell myShell = new Shell(myDisplay);
		myShell.setText("My Shell");
		myShell.setBounds(120, 120, 520, 520);
		
		// Layout.
//		myShell.setLayout(new FillLayout(SWT.VERTICAL));
//		FillLayout fillLayout = new FillLayout(SWT.VERTICAL);
//	    fillLayout.marginHeight = 5;
//	    fillLayout.marginWidth = 5;
//	    fillLayout.spacing = 1;
//		RowLayout myLayout = new RowLayout();
//		myLayout.spacing = 15;
//		myLayout.marginTop = 15;
//		myLayout.marginRight = 15;
//		myLayout.marginLeft = 15;
//		myLayout.marginBottom = 15;
		GridLayout myLayout = new GridLayout();
		myLayout.numColumns = 2;
		myShell.setLayout(myLayout);
		Button myButton;
		for (int i = 1; i <= 10; i++) {
	         myButton = new Button(myShell, SWT.PUSH);
	         myButton.setText("" + i);
	    }

		 // Menu.
		Menu myBar = new Menu(myShell, SWT.BAR);
		myShell.setMenuBar(myBar);
		MenuItem fileMenuItem = new MenuItem(myBar, SWT.CASCADE);
		fileMenuItem.setText("&This is my Menu");
		Menu subMenuItem = new Menu(myShell, SWT.DROP_DOWN);
		fileMenuItem.setMenu(subMenuItem);
		MenuItem selectMenuItem = new MenuItem(subMenuItem, SWT.NULL);
		selectMenuItem.setText("&Hello\tCtrl+S");
		selectMenuItem.setAccelerator(SWT.CTRL + 'S');
		selectMenuItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				System.out.println("Hello Selected!: " + event.time);
			}
		});
		MenuItem thisIsSeperator = new MenuItem(subMenuItem, SWT.SEPARATOR);
		MenuItem exitMenuItem = new MenuItem(subMenuItem, SWT.NULL);
		exitMenuItem.setText("&Bye");
		exitMenuItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				myShell.dispose();
			}
		});

	    // Button.
//		Composite composite = new Composite(myShell,SWT.BORDER);
		final Button myButton2 = new Button(myShell, SWT.PUSH);
		SelectionAdapter adapter = new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				myButton2.setText("You clicked me!");
			}
		};
		myButton2.addSelectionListener(adapter);
		myButton2.setText("Click");

		// Label
		final Label label = new Label(myShell, SWT.CENTER);
		label.setText("Hello World");
		Color red = new Color(myDisplay, 255, 0, 0);
		label.setForeground(red);

		// Combo.
		final Combo myCombo = new Combo(myShell, SWT.READ_ONLY);
		myCombo.setItems(new String[] { "option1", "option2", "option3",
				"option4", "option5" });
		myCombo.setText("option5");
		myCombo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				System.out.println("you selected me: " + myCombo.getText());
			}
		});
		
		// Text.
	    final Text text = new Text(myShell, SWT.MULTI);
	     
	    // Table.
		final Table myTable = new Table(myShell, SWT.SINGLE | SWT.FULL_SELECTION);
		TableColumn col1 = new TableColumn(myTable, SWT.NULL);
		col1.setText("First Column");
		col1.pack();
		TableColumn col2 = new TableColumn(myTable, SWT.NULL);
		col2.setText("Second Column");
		col2.pack();
		TableItem tableItem1 = new TableItem(myTable, SWT.NULL);
		tableItem1.setText(new String[] { "A1", "A2" });
		TableItem tableItem2 = new TableItem(myTable, SWT.NULL);
		tableItem2.setText(new String[] { "B1", "B2" });
		
		// Tree.
		final Tree tree = new Tree(myShell, SWT.SINGLE);
		for (int i = 1; i < 4; i++) {
			TreeItem parent1 = new TreeItem(tree, 0);
			parent1.setText("Paren1 - " + i);
			for (int j = 1; j < 4; j++) {
				TreeItem parent2 = new TreeItem(parent1, 0);
				parent2.setText("Parent2 - " + j);
				for (int k = 1; k < 4; k++) {
					TreeItem child = new TreeItem(parent2, 0);
					child.setText("Child - " + k);
				}
			}
		}
		
		// GridData
		GridData gridData = new GridData();
		gridData.horizontalSpan = 2;
		Label myLabel = new Label(myShell, SWT.LEFT);
		myLabel.setText("Please enter your age and birthdate");
		myLabel.setLayoutData(gridData);

		myLabel = new Label(myShell, SWT.LEFT);
		myLabel.setText("Age:");

		Text myText = new Text(myShell, SWT.SINGLE | SWT.BORDER);
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		myText.setLayoutData(gridData);

		myLabel = new Label(myShell, SWT.LEFT);
		myLabel.setText("BirthDate");
		myText = new Text(myShell, SWT.SINGLE | SWT.BORDER);
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		myText.setLayoutData(gridData);

		// Show it!
		myShell.open();
		while (!myShell.isDisposed()) {
			if (!myDisplay.readAndDispatch())
				myDisplay.sleep();
		}
		myDisplay.dispose();

	}
	
}
