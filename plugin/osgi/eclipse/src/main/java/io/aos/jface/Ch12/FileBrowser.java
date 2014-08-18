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

import java.io.*;
import java.util.*;

import org.eclipse.jface.viewers.*;
import org.eclipse.swt.dnd.*;
import org.eclipse.swt.widgets.Composite;

public class FileBrowser
{
  private ListViewer viewer;
  private File currentDirectory;

  public FileBrowser(Composite parent)
  {
    super();
    buildListViewer(parent);

    Transfer[] types = new Transfer[] { 
      FileTransfer.getInstance()
    };

    viewer.addDropSupport(DND.DROP_COPY, 
                          types, 
                          new FileDropListener(this));
    viewer.addDragSupport(DND.DROP_COPY, 
                          types, 
                          new FileDragListener(this));
  }

  private void buildListViewer(Composite parent)
  {
    viewer = new ListViewer(parent);
    viewer.setLabelProvider(new LabelProvider()
    {
      public String getText(Object element)
      {
        File file = (File) element;
        String name = file.getName();
        return file.isDirectory() ? "<Dir> " + name : name;
      }
    });

    viewer.setContentProvider(new IStructuredContentProvider()
    {

      public Object[] getElements(Object inputElement)
      {
        File file = (File) inputElement;
        if (file.isDirectory())
        {
          return file.listFiles();
        }
        else
        {
          return new Object[] { file.getName()};
        }
      }

      public void dispose()
      {
      }

      public void inputChanged(Viewer viewer, 
                                Object oldInput, 
                                Object newInput)
      {
      }
    });

    viewer.setSorter(new ViewerSorter()
    {

      public int category(Object element)
      {
        return ((File) element).isDirectory() ? 0 : 1;
      }

      public int compare(Viewer viewer, Object e1, Object e2)
      {
        int cat1 = category(e1);
        int cat2 = category(e2);
        if (cat1 != cat2)
          return cat1 - cat2;

        return ((File) e1).getName().compareTo(
                  ((File) e2).getName());
      }
    });

    viewer.addDoubleClickListener(new IDoubleClickListener()
    {

      public void doubleClick(DoubleClickEvent event)
      {
        IStructuredSelection selection =
          (IStructuredSelection) event.getSelection();
        setCurrentDirectory((File) selection.getFirstElement());
      }
    });

    setCurrentDirectory(File.listRoots()[0]);
  }

  private void setCurrentDirectory(File directory)
  {
    if (!directory.isDirectory())
      throw new RuntimeException(
        directory + " is not a directory!");

    currentDirectory = directory;
    viewer.setInput(directory);
  }

  String[] getSelectedFiles()
  {
    IStructuredSelection selection =
      (IStructuredSelection) viewer.getSelection();
    List fileNameList = new LinkedList();
    Iterator iterator = selection.iterator();
    while (iterator.hasNext())
    {
      File file = (File) iterator.next();
      fileNameList.add(file.getAbsoluteFile().toString());
    }
    return (String[]) fileNameList.toArray(
      new String[fileNameList.size()]);
  }

  void copyFiles(String[] sourceFileList)
  {
    for (int i = 0; i < sourceFileList.length; i++)
    {
      File sourceFile = new File(sourceFileList[i]);
      if (sourceFile.canRead() && currentDirectory.canWrite())
      {
        File destFile =
          new File(currentDirectory, sourceFile.getName());
        if (!destFile.exists())
        {
          FileOutputStream out;
          FileInputStream in;
          try
          {
            out = new FileOutputStream(destFile);
            in = new FileInputStream(sourceFile);
            byte[] buffer = new byte[1024];
            while ((in.read(buffer)) != -1)
            {
              out.write(buffer);
            }
            out.flush();
            out.close();
            in.close();
            viewer.refresh();
          }
          catch (FileNotFoundException e)
          {
            e.printStackTrace();
          }
          catch (IOException e)
          {
            e.printStackTrace();
          }
        }
        else
        {
          System.out.println(
            destFile + " already exists, refusing to clobber");
        }
      }
      else
      {
        System.out.println(
          "Sorry, either your source file is not readable " +
          "or the target directory is not writable");
      }
    }
  }
}
