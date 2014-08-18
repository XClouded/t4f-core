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

public class FileDeflater {

  public final static String DEFLATE_SUFFIX = ".dfl";

  public static void main(String... args) {

    for (int i = 0; i < args.length; i++) {
      try {
        FileInputStream fis= new FileInputStream(args[i]);      
        FileOutputStream fout = new FileOutputStream(args[i] + DEFLATE_SUFFIX);
        DeflaterOutputStream dos = new DeflaterOutputStream(fout);
        for (int c = fis.read(); c != -1; c = fis.read()) {
            dos.write(c);
        }
        dos.close();
        fis.close();
      }
      catch (IOException ex) {
        System.err.println(ex);
      }
    }
  }
}
