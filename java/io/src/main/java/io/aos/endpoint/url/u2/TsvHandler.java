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


import io.aos.in.bio.SafeBufferedCharIn;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ContentHandler;
import java.net.URLConnection;
import java.util.Vector;


public class TsvHandler extends ContentHandler {

  public Object getContent(URLConnection uc) throws IOException {
  
    String theLine;
    Vector v = new Vector();
     
    InputStreamReader isr = new InputStreamReader(uc.getInputStream());
    SafeBufferedCharIn inputStream= new SafeBufferedCharIn(isr);   
    while ((theLine = inputStream.readLine()) != null) {
      String[] linearray = lineToArray(theLine);
      v.addElement(linearray);
    }
      
    return v;
  
  }

  private String[] lineToArray(String line)  {
  
    int numFields = 1;
    for (int i = 0; i < line.length(); i++) {      
      if (line.charAt(i) == '\t') numFields++;
    }
    String[] fields = new String[numFields];
    int position = 0;
    for (int i = 0; i < numFields; i++) {
      StringBuffer buffer = new StringBuffer();
      while (position < line.length() && line.charAt(position) != '\t') {
        buffer.append(line.charAt(position));
        position++;
      }
      fields[i] = buffer.toString();
      position++;
    }
    
    return fields;
  
  }

}
