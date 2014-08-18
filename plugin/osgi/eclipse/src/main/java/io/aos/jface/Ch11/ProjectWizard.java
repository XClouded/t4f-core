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

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;

public class ProjectWizard extends Wizard
{

  public ProjectWizard()
  {
    super();
  }

  public void addPages()
  {
    addPage(new DirectoryPage());
    addPage(new ChooseDirectoryPage());
    addPage(new SummaryPage());
  }

  public boolean performFinish()
  {
    DirectoryPage dirPage = getDirectoryPage();
    if (dirPage.useDefaultDirectory())
    {
      System.out.println("Using default directory");
    }
    else
    {
      ChooseDirectoryPage choosePage = getChoosePage();
      System.out.println(
        "Using directory: " + choosePage.getDirectory());
    }
    return true;
  }

  private ChooseDirectoryPage getChoosePage()
  {
    return (ChooseDirectoryPage) getPage(
      ChooseDirectoryPage.PAGE_NAME);
  }

  private DirectoryPage getDirectoryPage()
  {
    return (DirectoryPage) getPage(DirectoryPage.PAGE_NAME);
  }

  public boolean performCancel()
  {
    System.out.println("Perform Cancel called");
    return true;
  }

  public IWizardPage getNextPage(IWizardPage page)
  {
    if (page instanceof DirectoryPage)
    {
      DirectoryPage dirPage = (DirectoryPage) page;
      if (dirPage.useDefaultDirectory())
      {
        SummaryPage summaryPage =
          (SummaryPage) getPage(SummaryPage.PAGE_NAME);
        summaryPage.updateText("Using default directory");
        return summaryPage;
      }
    }

    IWizardPage nextPage = super.getNextPage(page);
    if (nextPage instanceof SummaryPage)
    {
      SummaryPage summary = (SummaryPage) nextPage;
      DirectoryPage dirPage = getDirectoryPage();
      summary.updateText(
        dirPage.useDefaultDirectory()
         ? "Using default directory"
         : "Using directory:" + getChoosePage().getDirectory());
    }
    return nextPage;
  }
}
