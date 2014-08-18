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
package io.aos.swt.simpletexteditor;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class SwtSimpleTextEditor {
    private Shell sShell;
    private Text textArea;
    private Button button;
    private Button button1;
    private Button button2;
/*
    private void createSShell() {

        sShell = new org.eclipse.swt.widgets.Shell();
        org.eclipse.swt.layout.GridLayout gridLayout2 = new GridLayout();
        org.eclipse.swt.layout.GridData gridData3 = new org.eclipse.swt.layout.GridData();
        org.eclipse.swt.layout.GridData gridData4 = new org.eclipse.swt.layout.GridData();
        org.eclipse.swt.layout.GridData gridData5 = new org.eclipse.swt.layout.GridData();
        org.eclipse.swt.layout.GridData gridData6 = new org.eclipse.swt.layout.GridData();

        textArea = new Text(sShell, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.BORDER);

        button = new Button(sShell, SWT.NONE);
        button1 = new Button(sShell, SWT.NONE);
        button2 = new Button(sShell, SWT.NONE);

        sShell.setText("title");
        sShell.setLayout(gridLayout2);

        gridLayout2.numColumns = 3;
        gridLayout2.makeColumnsEqualWidth = false;
        gridData3.horizontalSpan = 3;
        gridData3.horizontalAlignment = org.eclipse.swt.layout.GridData.FILL;
        gridData3.verticalAlignment = org.eclipse.swt.layout.GridData.FILL;
        gridData3.grabExcessHorizontalSpace = true;
        gridData3.grabExcessVerticalSpace = true;

        textArea.setLayoutData(gridData3);

        button.setText("Load File");
        button.setLayoutData(gridData4);
        button1.setText("Save File");
        button1.setLayoutData(gridData5);
        button2.setText("Exit");
        button2.setLayoutData(gridData6);

        gridData4.horizontalAlignment = org.eclipse.swt.layout.GridData.END;
        gridData4.verticalAlignment = org.eclipse.swt.layout.GridData.CENTER;
        gridData4.grabExcessHorizontalSpace = true;
        gridData5.horizontalAlignment = org.eclipse.swt.layout.GridData.CENTER;
        gridData5.verticalAlignment = org.eclipse.swt.layout.GridData.CENTER;
        gridData6.grabExcessHorizontalSpace = true;

        sShell.setSize(new org.eclipse.swt.graphics.Point(393, 279));

        textArea.addModifyListener(new org.eclipse.swt.events.ModifyListener() {
            public void modifyText(org.eclipse.swt.events.ModifyEvent e) {
                if (!hasChanged) {
                    sShell.setText(title + " *");
                    hasChanged = true;
                }
            }
        });

        button2.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
            public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
                doExit();
            }
        });

        button1.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
            public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
                saveFile();
            }
        });

        button.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
            public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
                loadFile();
            }
        });

        sShell.addShellListener(new org.eclipse.swt.events.ShellAdapter() {
            public void shellClosed(org.eclipse.swt.events.ShellEvent e) {
                e.doit = doExit();
            }
        });
    }

    private void loadFile() {
        FileDialog dialog = new FileDialog(sShell, SWT.OPEN);
        String result = dialog.open();
        if (result != null) {
            File f = new File(result);
            try {
                BufferedReader br = new BufferedReader(new FileReader(f));
                StringBuffer buff = new StringBuffer();
                String line = br.readLine();
                while (line != null) {
                    buff.append(line + NEW_LINE);
                    line = br.readLine();
                }
                textArea.setText(buff.toString());
                br.close();
                sShell.setText(title);
                hasChanged = false;
            }
            catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
            catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    private void saveFile() {
        FileDialog dialog = new FileDialog(sShell, SWT.SAVE);
        String result = dialog.open();
        if (result != null) {
            File f = new File(result);
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(f));
                String text = textArea.getText();
                bw.write(text);
                bw.close();
                sShell.setText(title);
                hasChanged = false;
            }
            catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
            catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    private boolean doExit() {
        if (hasChanged) {
            MessageBox mb = new MessageBox(sShell, SWT.ICON_QUESTION | SWT.YES | SWT.NO | SWT.CANCEL);
            mb.setText("Save Changes?");
            mb.setMessage("File has been changed. Save before exit?");
            int state = mb.open();
            if (state == SWT.YES) {
                saveFile();
            }
            else if (state == SWT.CANCEL) {
                return false;
            }
        }
        sShell.close();
        sShell.dispose();
        return true;
    }
*/
}
