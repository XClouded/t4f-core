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
package io.aos.in.bio.compress;
import java.util.zip.*;
import java.io.*;

public class Zipper {

  public static void main(String... args) throws IOException {
  
    if (args.length < 2) {
      System.out.println("Usage: java Zipper [-d level] name.zip"+
                         " file1 file2...");
      return; 
    }
    
    String outputFile = args[0];
    // Default to maximum compression
    int level = 9;
    int start = 1;
    if (args[0].equals("-d")) {
      try {
        level = Integer.parseInt(args[1]);
        outputFile = args[2];
        start = 3;
      }
      catch (Exception ex) {
        System.out.println("Usage: java Zipper [-d level] name.zip"
                        + " file1 file2...");
        return;         
      }
    }    
    
    FileOutputStream fout = new FileOutputStream(outputFile);
    ZipOutputStream zout = new ZipOutputStream(fout);
    zout.setLevel(level);
    for (int i = start; i < args.length; i++) {
      ZipEntry ze = new ZipEntry(args[i]);
      FileInputStream fis= new FileInputStream(args[i]);
      try {
        System.out.println("Compressing " + args[i]);
        zout.putNextEntry(ze);
        for (int c = fis.read(); c != -1; c = fis.read()) {
          zout.write(c);
        }
      }
      finally {
        fis.close();
      }
    }
    zout.close();
  }
}
