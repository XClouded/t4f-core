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
package io.aos.jface.Ch11;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class SummaryPage extends WizardPage
{
  public static final String PAGE_NAME = "Summary";

  private Label textLabel;

  public SummaryPage()
  {
    super(PAGE_NAME, "Summary Page", null);
  }

  public void createControl(Composite parent)
  {
    Composite topLevel = new Composite(parent, SWT.NONE);
    topLevel.setLayout(new FillLayout());

    textLabel = new Label(topLevel, SWT.CENTER);
    textLabel.setText("");

    setControl(topLevel);
    setPageComplete(true);
  }

  public void updateText(String newText)
  {
    textLabel.setText(newText);
  }
}
