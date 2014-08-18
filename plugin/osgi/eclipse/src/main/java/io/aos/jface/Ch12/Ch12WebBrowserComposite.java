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
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

public class Ch12WebBrowserComposite extends Composite
{
  private Browser browser;

  public Ch12WebBrowserComposite(Composite parent)
  {
    super(parent, SWT.NONE);

    GridLayout layout = new GridLayout(2, true);
    setLayout(layout);

    browser = new Browser(this, SWT.NONE);
    GridData layoutData = new GridData(GridData.FILL_BOTH);
    layoutData.horizontalSpan = 2;
    layoutData.verticalSpan = 2;
    browser.setLayoutData(layoutData);
    browser.setUrl("http://www.slashdot.org");
    
    final Text text = new Text(this, SWT.SINGLE);
    layoutData = new GridData(GridData.FILL_HORIZONTAL);
    text.setLayoutData(layoutData);

    Button openButton = new Button(this, SWT.PUSH);
    openButton.setText("Open");
    openButton.addSelectionListener(new SelectionListener() {
      public void widgetSelected(SelectionEvent e)
      {
        browser.setUrl(text.getText());
      }
      public void widgetDefaultSelected(SelectionEvent e) {}
    });
    
    Button backButton = new Button(this, SWT.PUSH);
    backButton.setText("Back");
    backButton.addSelectionListener(new SelectionListener() {
      public void widgetSelected(SelectionEvent e)
      {
        browser.back();
      }
      public void widgetDefaultSelected(SelectionEvent e) {}
    });
    
    Button forwardButton = new Button(this, SWT.PUSH);
    forwardButton.setText("Forward");
    forwardButton.addSelectionListener(new SelectionListener() {
      public void widgetSelected(SelectionEvent e)
      {
        browser.forward();
      }
      public void widgetDefaultSelected(SelectionEvent e) {}
    });    
  }
}
