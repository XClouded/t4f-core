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
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

public class Ch12ClipboardComposite extends Composite {

    public Ch12ClipboardComposite(Composite parent) {
        super(parent, SWT.NONE);

        FillLayout layout = new FillLayout();
        setLayout(layout);
        
        Button b = new Button(this, SWT.NONE);
        b.setText("Copy to system clipboard");
        
        b.addSelectionListener(new SelectionListener() {

          public void widgetSelected(SelectionEvent e)
          {
            Clipboard clipboard = new Clipboard(getDisplay());
            String rtfData = "{\\rtf1\\b\\i Hello World}";
            RTFTransfer rtfTransfer = RTFTransfer.getInstance();
            clipboard.setContents(new Object[] {rtfData}, new Transfer[] {rtfTransfer});
            clipboard.dispose();
          }

          public void widgetDefaultSelected(SelectionEvent e)
          {
          }
          
        });

    }
}
