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
package io.aos.jface.expl.explorer01;

import java.io.*;

import org.eclipse.jface.action.*;
import org.eclipse.jface.resource.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.program.*;

public class OpenAction
  extends Action
  implements ISelectionChangedListener, IDoubleClickListener
{
  Explorer window;

  public OpenAction(Explorer w)
  {
    window = w;
    setText("Run");
    setToolTipText("Run the associated program on a file");
    setEnabled(false);
    setImageDescriptor(
      ImageDescriptor.createFromURL(Util.newURL("file:icons/run.gif")));
  }

  public void run()
  {
    IStructuredSelection selection = window.getTableSelection();
    if (selection.size() != 1)
    {
      return;
    }

    File selected_file = (File) selection.getFirstElement();
    if (selected_file.isFile())
    {
      Program.launch(selected_file.getAbsolutePath());
    }
  }

  public void selectionChanged(SelectionChangedEvent event)
  {
    setText("Run");
    setToolTipText("Run the associated program on a file");
    setEnabled(false);

    IStructuredSelection selection = window.getTableSelection();
    if (selection.size() != 1)
    {
      setToolTipText(
        getToolTipText() + " (Only enabled when exactly one item is selected)");
      return;
    }

    File file = (File) selection.getFirstElement();
    if (file.isFile())
    {
      setEnabled(true);
      setText("Run the associated program on " + file.getName());
      setToolTipText(
        "Run the program asociated with "
          + file.getName()
          + " with this file as the argument");
    }
  }

  public void doubleClick(DoubleClickEvent event)
  {
    run();
  }
}
