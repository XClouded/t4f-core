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
import java.security.*;
import java.util.*;


public class ListCallbackDigest implements Runnable {

  private File input;
  List listenerList = new Vector();

  public ListCallbackDigest(File input) {
   this.input = input;
  }
 
  public synchronized void addDigestListener(DigestListener l) {
    listenerList.add(l);
  }
  
  public synchronized void removeDigestListener(DigestListener l) {
    listenerList.remove(l);
  } 
  
  private synchronized void sendDigest(byte[] digest) {
    ListIterator iterator = listenerList.listIterator();
    while (iterator.hasNext()) {
      DigestListener dl = (DigestListener) iterator.next();
      dl.digestCalculated(digest);
    }
  }  

  public void run() {
    
    try {
      FileInputStream in = new FileInputStream(input);
      MessageDigest sha = MessageDigest.getInstance("SHA");
      DigestInputStream din = new DigestInputStream(in, sha);
      int b;
      while ((b = din.read()) != -1) ;
      din.close();
      byte[] digest = sha.digest();
      this.sendDigest(digest);
    }
    catch (IOException e) {
      System.err.println(e);
    }
    catch (NoSuchAlgorithmException e) {
      System.err.println(e);
    }
    
  }

}
