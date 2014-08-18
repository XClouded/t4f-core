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
package io.aos.endpoint.url;
import java.net.*;

public class UrlSplitterTest {

  public static void main(String... args) {

    for (int i = 0; i < args.length; i++) {
      try {
        URL u = new URL(args[i]);
        System.out.println("The URL is " + u);
        System.out.println("The scheme is " + u.getProtocol());        
        System.out.println("The user info is " + u.getUserInfo());
        System.out.println("The authority is " + u.getAuthority());
        
        String host = u.getHost();
        if (host != null) {
          int atSign = host.indexOf('@');  
          if (atSign != -1) host = host.substring(atSign+1);
          System.out.println("The host is " + host);   
        }
        else {          
          System.out.println("The host is null.");   
        }

        System.out.println("The port is " + u.getPort());
        System.out.println("The file is " + u.getFile());
        System.out.println("The path is " + u.getPath());
        System.out.println("The ref is " + u.getRef());
        System.out.println("The query string is " + u.getQuery());
      }  // end try
      catch (MalformedURLException e) {
        System.err.println(args[i] + " is not a URL I understand.");
      }
      System.out.println();
    }  // end for

  } 

}  // end URLSplitter
