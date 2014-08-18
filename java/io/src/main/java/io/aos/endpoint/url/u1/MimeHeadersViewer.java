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
package io.aos.endpoint.url.u1;
import java.net.*;
import java.io.*;
import java.util.*;

public class MimeHeadersViewer {

  public static void main(String... args) {

    for (int i=0; i < args.length; i++) {
      try {
        URL u = new URL(args[i]);
        URLConnection uc = u.openConnection();
        System.out.println("Content-type: " + uc.getContentType());
        System.out.println("Content-encoding: " 
         + uc.getContentEncoding());
        System.out.println("Date: " + new Date(uc.getDate()));
        System.out.println("Last modified: " 
         + new Date(uc.getLastModified()));
        System.out.println("Expiration date: " 
         + new Date(uc.getExpiration()));
        System.out.println("Content-length: " + uc.getContentLength());
      }  // end try
      catch (MalformedURLException e) {
        System.err.println(args[i] + " is not a URL I understand");
      }
      catch (IOException e) {
        System.err.println(e);
      }      
      System.out.println();       
    }  // end for
      
  } 

}  // end MIMEHeadersViewer
