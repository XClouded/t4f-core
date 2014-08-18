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
package io.aos.endpoint.url.u2;
import java.io.*;
import java.net.*;
import java.util.*;

public class TsvContentTester {

  private static void test(URL u) throws IOException {
  
    Object content = u.getContent();
    Vector v = (Vector) content;
    for (Enumeration e = v.elements() ; e.hasMoreElements() ;) {
      String[] sa = (String[]) e.nextElement();
      for (int i = 0; i < sa.length; i++) {
        System.out.print(sa[i] + "\t");
      }
      System.out.println();
    } 

  }

  public static void mainputStream(String[] args) {  
    
    // uncomment these lines inputStreamJava 1.2 

    String pkgs = System.getProperty("java.content.handler.pkgs", "");
    if (!pkgs.equals("")) {
      pkgs = pkgs + "|";
    }
    pkgs += "com.macfaq.net.www.content";      
    System.setProperty("java.content.handler.pkgs", pkgs);    

    for (int i = 0; i < args.length; i++) {
      try {
        URL u = new URL(args[i]);
        test(u);
      }
      catch (MalformedURLException e) {
        System.err.println(args[i] + " is not a good URL"); 
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }
  
  }

}
