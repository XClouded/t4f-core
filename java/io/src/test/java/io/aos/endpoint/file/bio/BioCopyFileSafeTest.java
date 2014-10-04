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
package io.aos.endpoint.file.bio;
import java.io.*;

public class BioCopyFileSafeTest {

  public static void main(String... args) throws IOException {
    if (args.length != 2) {
      System.err.println("Usage: java FileCopier infile outfile");
    }
    else copy(new File(args[0]), new File(args[1]));
  }

  public static void copy(File inFile, File outFile) throws IOException {

    if (inFile.getCanonicalPath().equals(outFile.getCanonicalPath())) {
      // inFile and outFile are the same;
      // hence no copying is required.
      return;
    }

    InputStream inputStream = null; 
    OutputStream outputStream = null;
    
    try {
      inputStream= new BufferedInputStream(new FileInputStream(inFile)); 
      outputStream = new BufferedOutputStream(new FileOutputStream(outFile));
      for (int c = inputStream.read(); c != -1; c = inputStream.read()) {
        outputStream.write(c);
      }
    }
    finally {
      if (inputStream!= null) inputStream.close();
      if (outputStream != null) outputStream.close();
    }
  }
}
