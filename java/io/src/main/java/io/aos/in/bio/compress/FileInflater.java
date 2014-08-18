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
import java.io.*;
import java.util.zip.*;

public class FileInflater {

  public static void main(String... args) {

    for (int i = 0; i < args.length; i++) {
      if (args[i].toLowerCase().endsWith(FileDeflater.DEFLATE_SUFFIX)) {
        try {
          FileInputStream fis= new FileInputStream(args[i]);      
          InflaterInputStream iis = new InflaterInputStream(fis);
          FileOutputStream fout = new FileOutputStream(
           args[i].substring(0, args[i].length()-4));
           for (int c = iis.read(); c != -1; c = iis.read()) {
             fout.write(c);
          }
          fout.close();
        }
        catch (IOException ex) {
          System.err.println(ex);
        }
      }
      else {
        System.err.println(args[i] + " does not appear to be a deflated file.");
      }
    }
  }
}
