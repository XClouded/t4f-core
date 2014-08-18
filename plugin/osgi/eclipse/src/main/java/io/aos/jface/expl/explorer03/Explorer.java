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
package io.aos.jface.expl.explorer03;

import java.io.*;

import org.eclipse.jface.viewers.*;
import org.eclipse.jface.window.*;
import org.eclipse.swt.*;
import org.eclipse.swt.custom.*;
import org.eclipse.swt.widgets.*;

public class Explorer extends ApplicationWindow
{
  public Explorer()
  {
    super(null);
    addStatusLine();
  }

  protected Control createContents(Composite parent)
  {
    getShell().setText("JFace File Explorer");
    SashForm sash_form = new SashForm(parent, SWT.HORIZONTAL | SWT.NULL);

    TreeViewer tv = new TreeViewer(sash_form);
    tv.setContentProvider(new FileTreeContentProvider());
    tv.setLabelProvider(new FileTreeLabelProvider());
    tv.setInput(new File("C:\\"));
    tv.addFilter(new AllowOnlyFoldersFilter());

    final TableViewer tbv =
      new TableViewer(sash_form, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI);
    tbv.setContentProvider(new FileTableContentProvider());
    tbv.setLabelProvider(new FileTableLabelProvider());
    tbv.setSorter(new FileSorter());

    TableColumn column = new TableColumn(tbv.getTable(), SWT.LEFT);
    column.setText("Name");
    column.setWidth(200);

    column = new TableColumn(tbv.getTable(), SWT.RIGHT);
    column.setText("Size");
    column.setWidth(100);

    tbv.getTable().setHeaderVisible(true);

    tv.addSelectionChangedListener(new ISelectionChangedListener()
    {
      public void selectionChanged(SelectionChangedEvent event)
      {
        IStructuredSelection selection =
          (IStructuredSelection) event.getSelection();

        Object selected_file = selection.getFirstElement();
        tbv.setInput(selected_file);
      }
    });

    tbv.addSelectionChangedListener(new ISelectionChangedListener()
    {
      public void selectionChanged(SelectionChangedEvent event)
      {
        IStructuredSelection selection =
          (IStructuredSelection) event.getSelection();

        setStatus("Number of items selected is " + selection.size());
      }
    });

    return sash_form;
  }

  public static void main(String... args)
  {
    Explorer w = new Explorer();
    w.setBlockOnOpen(true);
    w.open();
    Display.getCurrent().dispose();
  }
}
