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
package io.aos.filesystem.tree;
import java.io.*;
import java.util.*;

public class FileSpyTest {

  public static void main(String... args) {
  
    for (int i = 0; i < args.length; i++) {
      File f = new File(args[i]);
      if (f.exists()) {
        System.out.println("Name: " + f.getName());
        System.out.println("Absolute path: " + f.getAbsolutePath());
        try {
          System.out.println("Canonical path: " + f.getCanonicalPath());
        }
        catch (IOException ex) {
          System.out.println("Could not determine the canonical path.");        
        }
        
        String parent = f.getParent();
        if (parent != null) {
          System.out.println("Parent: " + f.getParent());
        }
        
        if (f.canWrite()) System.out.println(f.getName() + " is writable.");
        if (f.canRead()) System.out.println(f.getName() + " is readable.");
        
        if (f.isFile()) {
          System.out.println(f.getName() + " is a file.");
        }
        else if (f.isDirectory()) {
          System.out.println(f.getName() + " is a directory.");
        }
        else {
          System.out.println("What is this?");
        }
        
        if (f.isAbsolute()) {
          System.out.println(f.getPath() + " is an absolute path.");
        }
        else {
          System.out.println(f.getPath() + " is not an absolute path.");
        }
        
        long lm = f.lastModified();
        if (lm != 0) System.out.println("Last Modified at " + new Date(lm));
        
        long length = f.length();
        if (length != 0) {
          System.out.println(f.getName() + " is " + length + " bytes long.");  
        }
      }  
      else {
        System.out.println("I'm sorry. I can't find the file " + args[i]);
      }      
    }
  }
}
