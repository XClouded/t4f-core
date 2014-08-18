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
package io.aos.jface.Ch12;

import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.*;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

public class Ch12FileBrowserComposite extends Composite
{

  private FileBrowser browser;

  public Ch12FileBrowserComposite(Composite parent)
  {
    super(parent, SWT.NONE);

    RowLayout layout = new RowLayout(SWT.HORIZONTAL);
    setLayout(layout);

    Button copyButton = new Button(this, SWT.PUSH);
    copyButton.setText("Copy");
    copyButton.addSelectionListener(new SelectionListener()
    {

      public void widgetSelected(SelectionEvent e)
      {
        Clipboard clipboard = new Clipboard(getDisplay());

        FileTransfer transfer = FileTransfer.getInstance();
        clipboard.setContents(
          new Object[] { browser.getSelectedFiles()},
          new Transfer[] { transfer });
        clipboard.dispose();
      }

      public void widgetDefaultSelected(SelectionEvent e) {}
    });

    Button pasteButton = new Button(this, SWT.PUSH);
    pasteButton.setText("Paste");
    pasteButton.addSelectionListener(new SelectionListener()
    {

      public void widgetSelected(SelectionEvent e)
      {
        Clipboard clipboard = new Clipboard(getDisplay());
        FileTransfer transfer = FileTransfer.getInstance();

        Object data = clipboard.getContents(transfer);
        if (data != null)
        {
          browser.copyFiles((String[]) data);
        }
        clipboard.dispose();
      }

      public void widgetDefaultSelected(SelectionEvent e) {}
    });

    browser = new FileBrowser(this);
    new FileBrowser(this);
  }
}
