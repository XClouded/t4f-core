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
package io.aos.io.c24;
import java.io.IOException;
import java.util.Date;

import javax.microedition.io.*;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.Display;
import javax.microedition.midlet.*;

public class TimeServer extends MIDlet {
    
   protected void startApp() {    
     DatagramConnection connection;
     try {
       connection = (DatagramConnection) Connector.open("datagram://:37");
       Datagram incoming = connection.newDatagram(128);
       Datagram response = connection.newDatagram(4);
       while (true) {
         try {
           connection.receive(incoming);
           response.reset();
           response.setAddress(incoming);
           response.setData(getTime(), 0, 4);
           connection.send(response);
           incoming.reset();
         }
         catch (IOException ex) {
           // As long as it's just an error on this one connection
           // we can ignore it
         }
      }     
    }
    catch (IOException ex) {
      // If we can't open the channel, put up an Alert
      Alert alert = new Alert("UDP Error");
      alert.setTimeout(Alert.FOREVER);
      alert.setString("Could not connect to port 37. Needs root privileges?");
      Display.getDisplay(this).setCurrent(alert);
    }
  }
      
  private byte[] getTime() {
      
    byte[] result = new byte[4];
    Date now = new Date();
      
    // The time protocol uses an unsigned 4-byte int so we have
    // to do all the arithmetic with longs and then extract the
    // four low-order bytes
    long secondsSince1970 = now.getTime()/1000;
    long differenceBetweenEpochs = 2208988800L;
    long secondsSince1900 = differenceBetweenEpochs + secondsSince1970;
    result[0] = (byte) ((secondsSince1900 & 0xFF000000) >>> 24);
    result[1] = (byte) ((secondsSince1900 & 0xFF0000) >>> 16);
    result[2] = (byte) ((secondsSince1900 & 0xFF00) >>> 8);
    result[3] = (byte) (secondsSince1900 & 0xFF);
  
    return result;
  }

  protected void pauseApp() {}
  protected void destroyApp(boolean unconditional)  {}
}
