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

import java.net.*;
import java.io.*;
import java.util.*;


public class XTime extends ContentHandler {

  public Object getContent(URLConnection uc) throws IOException {

    Class[] classes = new Class[1];
    classes[0] = Date.class;
    return this.getContent(uc, classes); 

  }

  public Object getContent(URLConnection uc, Class[] classes)
   throws IOException {
    
    InputStream inputStream = uc.getInputStream();
    for (int i = 0; i < classes.length; i++) {
      if (classes[i] == InputStream.class) {
        return inputStream;  
      } 
      else if (classes[i] == Long.class) {
        long secondsSince1900 = readSecondsSince1900(inputStream);
        return new Long(secondsSince1900);
      }
      else if (classes[i] == Date.class) {
        long secondsSince1900 = readSecondsSince1900(inputStream);
        Date time = shiftEpochs(secondsSince1900);
        return time;
      }
      else if (classes[i] == Calendar.class) {
        long secondsSince1900 = readSecondsSince1900(inputStream);
        Date time = shiftEpochs(secondsSince1900);
        Calendar c = Calendar.getInstance();
        c.setTime(time);
        return c;
      }
      else if (classes[i] == String.class) {
        long secondsSince1900 = readSecondsSince1900(inputStream);
        Date time = shiftEpochs(secondsSince1900);
        return time.toString();
      }      
    }
    
    return null; // no requested type available
    
  }
  
  private long readSecondsSince1900(InputStream inputStream) 
   throws IOException {
    
    long secondsSince1900 = 0;
    for (int j = 0; j < 4; j++) {
      secondsSince1900 = (secondsSince1900 << 8) | inputStream.read();
    }
    return secondsSince1900;
    
  }
  
  private Date shiftEpochs(long secondsSince1900) {
  
    // The time protocol sets the epoch at 1900, the Java Date class
    //  at 1970. This number converts between them.
    long differenceBetweenEpochs = 2208988800L;
    
    long secondsSince1970 = secondsSince1900 - differenceBetweenEpochs;       
    long msSince1970 = secondsSince1970 * 1000;
    Date time = new Date(msSince1970);
    return time;
    
  }

}
