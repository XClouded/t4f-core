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
package io.aos.crypto.digest.d2;
import java.io.*;
import java.util.*;
import java.util.zip.*;


public class GZipThread extends Thread {

  private List pool;
  private static int filesCompressed = 0;

  public GZipThread(List pool) {
    this.pool = pool;
  }
  
  private static synchronized void incrementFilesCompressed() {
    filesCompressed++;
  }

  public void run() {
    
    while (filesCompressed != GZipAllFiles.getNumberOfFilesToBeCompressed()) {
    
      File input = null;
      
      synchronized (pool) {         
        while (pool.isEmpty()) {
          if (filesCompressed == GZipAllFiles.getNumberOfFilesToBeCompressed()) return;
          try {
            pool.wait();
          }
          catch (InterruptedException e) {
          }
        }

        input = (File) pool.remove(pool.size()-1); 
      
      }
    
      // don't compress an already compressed file
      if (!input.getName().endsWith(".gz")) { 
      
        try {
          InputStream in = new FileInputStream(input);
          in = new BufferedInputStream(in);
          
          File output = new File(input.getParent(), input.getName() + ".gz");
          if (!output.exists()) { // Don't overwrite an existing file
            OutputStream out = new FileOutputStream(output);
            out = new GZIPOutputStream(out);
            out = new BufferedOutputStream(out);
            int b;
            while ((b = in.read()) != -1) out.write(b);
            out.flush();
            out.close();
            incrementFilesCompressed();
            in.close();
          }
        }
        catch (IOException e) {
          System.err.println(e);
        }
        
      } // end if 
  
    } // end while
    
  } // end run

} // end ZipThread
