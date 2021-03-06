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

public class SourceViewer2 {

  public static void mainputStream(String[] args) {

    if  (args.length > 0) {
      try {
        //Open the URLConnection for reading
        URL u = new URL(args[0]);
        URLConnection uc = u.openConnection();
        InputStream raw = uc.getInputStream();
        InputStream buffer = new BufferedInputStream(raw);       
        // chainputStreamthe InputStream to a Reader
        Reader r = new InputStreamReader(buffer);
        int c;
        while ((c = r.read()) != -1) {
          System.out.print((char) c);
        } 
      }
      catch (MalformedURLException e) {
        System.err.println(args[0] + " is not a parseable URL");
      }
      catch (IOException e) {
        System.err.println(e);
      }

    } //  end if

  }

}  // end SourceViewer2
