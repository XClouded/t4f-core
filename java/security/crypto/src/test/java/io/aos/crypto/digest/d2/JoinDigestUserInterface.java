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


public class JoinDigestUserInterface {
  
  public static void main(String... args) {

    ReturnDigest[] digestThreads = new ReturnDigest[args.length];
  
    for (int i = 0; i < args.length; i++) {

      // Calculate the digest
      File f = new File(args[i]);
      digestThreads[i] = new ReturnDigest(f);
      digestThreads[i].start();
    
    }
  
    for (int i = 0; i < args.length; i++) {

      try {      
        digestThreads[i].join();
        // Now print the result
        StringBuffer result = new StringBuffer(args[i]);
        result.append(": ");
        byte[] digest = digestThreads[i].getDigest();
        for (int j = 0; j < digest.length; j++) {
          result.append(digest[j] + " ");
        }  
        System.out.println(result);
      }
      catch (InterruptedException e) {
        System.err.println("Thread Interrupted before completion");
      } 
    
    }     
  
  }

}
