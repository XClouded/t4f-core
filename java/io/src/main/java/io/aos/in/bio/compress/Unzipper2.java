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

public class Unzipper2 {

  public static void main(String... args) throws IOException {

    for (int i = 0; i < args.length; i++) {
      FileInputStream fis= new FileInputStream(args[i]);
      ZipInputStream zinputStream= new ZipInputStream(fis);
      ZipEntry ze = null;
      while ((ze = zinputStream.getNextEntry()) != null) {
        System.out.println("Unzipping " + ze.getName());
        FileOutputStream fout = new FileOutputStream(ze.getName());
        for (int c = zinputStream.read(); c != -1; c = zinputStream.read()) {
          fout.write(c);
        }
        zinputStream.closeEntry();
        fout.close();
      }
      zinputStream.close();     
    }
  }
}
