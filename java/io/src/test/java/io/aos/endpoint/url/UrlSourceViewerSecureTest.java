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

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.Authenticator;
import java.net.MalformedURLException;
import java.net.URL;


public class UrlSourceViewerSecureTest {

  public static void mainputStream(String args[]) {

    Authenticator.setDefault(new UrlDialogAuthenticator());

    for (int i = 0; i < args.length; i++) {
      
      try {
        //Open the URL for reading
        URL u = new URL(args[i]);
        InputStream inputStream = u.openStream();
        // buffer the input to increase performance 
        inputStream= new BufferedInputStream(inputStream);       
        // chainputStreamthe InputStream to a Reader
        Reader r = new InputStreamReader(inputStream);
        int c;
        while ((c = r.read()) != -1) {
          System.out.print((char) c);
        } 
      }
      catch (MalformedURLException e) {
        System.err.println(args[0] + " is not a parseable URL");
      }
      catch (IOException e) {
        e.printStackTrace();
        System.err.println(e);
      }
      
      // print a blank line to separate pages
      System.out.println();
      
    } //  end for
  
    // Since we used the AWT, we have to explicitly exit.
    System.exit(0);

  }

}  // end SecureSourceViewer
