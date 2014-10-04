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
import javax.microedition.midlet.*;
import javax.microedition.io.*;
import java.io.*;
import java.util.Date;

public class TCPTimeServer extends MIDlet {    

  private ServerSocketConnection server;
  // The time protocol sets the epoch at 1900,
  // the java Date class at 1970. This number 
  // converts between them.   
  private long differenceBetweenEpochs = 2208988800L;
  
  protected void startApp() {
    try {
      server = (ServerSocketConnection) Connector.open("socket://:37");
      Runnable r = new Runnable() {
        public void run() {
          while (true) {
            try {
              StreamConnection conn = server.acceptAndOpen();
              Date now = new Date();
              long msSince1970 = now.getTime();
              long secondsSince1900 = msSince1970/1000L + differenceBetweenEpochs;
              DataOutputStream outputStream = conn.openDataOutputStream();
              // write the low order four bytes
              outputStream.write( (int) ((secondsSince1900 >>> 24) & 0xFFL));
              outputStream.write( (int) ((secondsSince1900 >>> 16) & 0xFFL));
              outputStream.write( (int) ((secondsSince1900 >>> 8) & 0xFFL));
              outputStream.write( (int) (secondsSince1900 & 0xFFL));
              outputStream.close();
            }
            catch (IOException ex) {
            }
          } 
        }
      };
      Thread t = new Thread(r);
      t.start();
    }
    catch (IOException ex) {
      // not much we can do about this here
    }
  }

  protected void pauseApp() {}

  protected void destroyApp(boolean unconditional) {
    try {
        server.close();
    }
    catch (IOException ex) {
        // We tried;
    }
  }
}
