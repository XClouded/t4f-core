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
import java.util.*;

import org.eclipse.jface.action.*;
import org.eclipse.jface.resource.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.dnd.*;

public class CopyFileNamesToClipboardAction extends Action
{
  Explorer window;

  public CopyFileNamesToClipboardAction(Explorer w)
  {
    window = w;
    setToolTipText("Copy absolute file names of selected files to the clipboard");
    setText("Copy File &Names@Ctrl+Shift+C");
    setImageDescriptor(
      ImageDescriptor.createFromURL(Util.newURL("file:icons/copy.gif")));
  }

  public void run()
  {
    Clipboard clipboard = Util.getClipboard();
    TextTransfer text_transfer = TextTransfer.getInstance();
    
    IStructuredSelection selection = window.getTableSelection();
    if (selection.isEmpty())
    {
      return;
    }

    StringBuffer string_buffer = new StringBuffer();
    for (Iterator i = selection.iterator(); i.hasNext();)
    {
      File file = (File) i.next();
      string_buffer.append(" ");
      string_buffer.append(file.getAbsolutePath());
    }

    clipboard.setContents(
      new Object[] { string_buffer.toString()},
      new Transfer[] { text_transfer });
      
    window.setStatus(string_buffer.toString());
  }
}
