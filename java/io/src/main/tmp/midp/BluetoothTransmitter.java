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
package io.aos.io.c25;
import java.io.*;
import javax.bluetooth.*;
import javax.microedition.io.*;

public class BluetoothTransmitter {
  
  public static void main(String... args) {
    
    try {
      String url = BluetoothServiceFinder.getConnectionURL(BluetoothReceiver.UUID);
      if (url == null) {
        System.out.println("No receiver in range");
        return; 
      }
      System.out.println("Connecting to " + url);
      L2CAPConnection conn = (L2CAPConnection) Connector.open(url);
      int mtu = conn.getTransmitMTU(); // maximum packet length we can send
      // use safe???
      BufferedReader reader = new BufferedReader(
       new InputStreamReader(System.in));
      
      while (true) {
        String line = reader.readLine();
        if (".".equals(line)) {
          byte[] end = {0};
          conn.send(end);
          break; 
        }
        line += "\r\n";
        // Now we need to make sure this fits into the MTU
        byte[][] packets = segment(line, mtu);
        for (int i = 0; i < packets.length; i++) {
          conn.send(packets[i]);
        }
      }
      
    } 
    catch (IOException ex) {
      ex.printStackTrace();
    }
    System.exit(0);
  }

  private static byte[][] segment(String line, int mtu) {
    
    int numPackets = (line.length()-1)/mtu + 1;
    byte[][] packets = new byte[numPackets][mtu];
    try {
      byte[] data = line.getBytes("UTF-8");
      // The last packet will normally not fill a complete MTU
      for (int i = 0; i < numPackets-1; i++) {
        System.arraycopy(data, i*mtu, packets[i], 0, mtu );
      }
      System.arraycopy(data, (numPackets-1)*mtu, packets[numPackets-1],
                       0, data.length - ((numPackets-1)*mtu) );
      return packets;
    } 
    catch (UnsupportedEncodingException ex) {
      throw new RuntimeException("Broken VM does not support UTF-8");
    }
  }
}
