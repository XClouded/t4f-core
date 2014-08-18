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
package io.aos.jface.Ch10;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

public class UsernamePasswordDialog extends Dialog
{
  private static final int RESET_ID = 
                  IDialogConstants.NO_TO_ALL_ID + 1;
  
  private Text usernameField;
  private Text passwordField;

  public UsernamePasswordDialog(Shell parentShell)
  {
    super(parentShell);
  }

  protected Control createDialogArea(Composite parent)
  {
    Composite comp = (Composite)super.createDialogArea(parent);
    
    GridLayout layout = (GridLayout)comp.getLayout();
    layout.numColumns = 2;
    
    Label usernameLabel = new Label(comp, SWT.RIGHT);
    usernameLabel.setText("Username: ");
    
    usernameField = new Text(comp, SWT.SINGLE);
    GridData data = new GridData(GridData.FILL_HORIZONTAL);
    usernameField.setLayoutData(data);
    
    Label passwordLabel = new Label(comp, SWT.RIGHT);
    passwordLabel.setText("Password: ");
    
    passwordField = new Text(comp, SWT.SINGLE | SWT.PASSWORD);
    data = new GridData(GridData.FILL_HORIZONTAL);
    passwordField.setLayoutData(data);

    return comp;
  }
  
  protected void createButtonsForButtonBar(Composite parent)
  {
    super.createButtonsForButtonBar(parent);
    createButton(parent, RESET_ID, "Reset All", false);
  }
  
  protected void buttonPressed(int buttonId)
  {
    if(buttonId == RESET_ID)
    {
      usernameField.setText("");
      passwordField.setText("");
    }
    else
    {
      super.buttonPressed(buttonId);
    }
  }
}
