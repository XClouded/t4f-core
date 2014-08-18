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

import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.DropTargetListener;

final class FileDropListener implements DropTargetListener
{
  private final FileBrowser browser;

  FileDropListener(FileBrowser browser)
  {
    this.browser = browser;
  }
  public void dragEnter(DropTargetEvent event) {}
  public void dragLeave(DropTargetEvent event) {}
  public void dragOperationChanged(DropTargetEvent event) {}
  public void dragOver(DropTargetEvent event) {}
  public void dropAccept(DropTargetEvent event) {}

  public void drop(DropTargetEvent event)
  {
    String[] sourceFileList = (String[])event.data;      
    browser.copyFiles(sourceFileList);
  }
}
