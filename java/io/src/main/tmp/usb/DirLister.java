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
package io.aos.io.c24;
import java.io.*;
import java.util.*;
import javax.microedition.io.*;
import javax.microedition.midlet.*;
import javax.microedition.io.file.*;
import javax.microedition.lcdui.*;

public class DirLister extends MIDlet {

  private int level = 0;
    
  public void startApp() { 
     Form form = new Form("File Roots");
     Enumeration roots = FileSystemRegistry.listRoots();
     while (roots.hasMoreElements()) {
       Object next = roots.nextElement();
       String url = "file:///" + next;
       System.out.println(url);
       try {
         FileConnection connection = (FileConnection) Connector.open(url);
         getInfo(connection, form);
       }
       catch (IOException ex) {
         form.append(ex.getMessage() +"\n");
       }
     }
     Display.getDisplay(this).setCurrent(form);
   }

  public void pauseApp() {}

  public void destroyApp(boolean condition) {
    notifyDestroyed();
  }

  private void getInfo(FileConnection connection, Form form) throws IOException { 
    if (connection.isDirectory()) form.append("------\n");
    for (int i = 0; i < level; i++) form.append(" ");
    form.append(connection.getPath() + connection.getName() + "\n");
    if (connection.isDirectory()) {
      level++;
      Enumeration list = connection.list();
      String path =  connection.getPath() + connection.getName();
      while (list.hasMoreElements()) {
        Object next = list.nextElement();
        String url = "file://" + path + next ;
        try {
          FileConnection child = (FileConnection) Connector.open(url);
          getInfo(child, form);
        }
        catch (Exception ex) {
          form.append(ex.getMessage() +"\n");
        }
      }
      level--;
    }  
  }
}
