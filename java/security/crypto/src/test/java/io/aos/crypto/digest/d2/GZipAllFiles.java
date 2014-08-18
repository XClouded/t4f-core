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


public class GZipAllFiles {
  
  public final static int THREAD_COUNT = 4;
  private static int filesToBeCompressed = -1;

  public static void main(String... args) {

    Vector pool = new Vector();
    GZipThread[] threads = new GZipThread[THREAD_COUNT];
    
    for (int i = 0; i < threads.length; i++) {
      threads[i] = new GZipThread(pool); 
      threads[i].start();
    }

    int totalFiles = 0;
    for (int i = 0; i < args.length; i++) {
      
      File f = new File(args[i]);
      if (f.exists()) {
        if (f.isDirectory()) {
          File[] files = f.listFiles();
          for (int j = 0; j < files.length; j++) {
            if (!files[j].isDirectory()) { // don't recurse directories
              totalFiles++;
              synchronized (pool) {
                pool.add(files[j]);
                pool.notifyAll();
              }
            }
          }
        } 
        else {
          totalFiles++;
          synchronized (pool) {
            pool.add(0, f);
            pool.notifyAll();
          }
        }
        
      } // end if
      
    } // end for
    
    filesToBeCompressed = totalFiles;
    
    // make sure that any waiting thread knows that no 
    // more files will be added to the pool
    for (int i = 0; i < threads.length; i++) {
      threads[i].interrupt();
    }

  }

  public static int getNumberOfFilesToBeCompressed() {
    return filesToBeCompressed;
  }

}
