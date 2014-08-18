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
package io.aos.io.c22;
import javax.comm.*;
import java.util.*;

public class PortTester {

  public static void main(String... args) {

    Enumeration thePorts = CommPortIdentifier.getPortIdentifiers();
    while (thePorts.hasMoreElements()) {
      CommPortIdentifier com = (CommPortIdentifier) thePorts.nextElement();
      System.out.print(com.getName());

      switch(com.getPortType()) {
        case CommPortIdentifier.PORT_SERIAL:
          System.out.println(", a serial port: ");
          break;
        case CommPortIdentifier.PORT_PARALLEL:
          System.out.println(", a parallel port: ");
          break;
        default:
          // important since other types of ports like USB
          // and firewire are expected to be added in the future
          System.out.println(" , a port of unknown type: ");
          break;
      }

      try {
        CommPort thePort = com.open("Port Tester", 20);
        testProperties(thePort);
        thePort.close();
      }
      catch (PortInUseException ex) {
        System.out.println("Port in use, can't test properties");        
      }
      System.out.println();
    }
  }

  public static void testProperties(CommPort thePort) {

    try {
      thePort.enableReceiveThreshold(10);
      System.out.println("Receive threshold supported");      
    }
    catch (UnsupportedCommOperationException ex) {
      System.out.println("Receive threshold not supported");   
    }

    try {
      thePort.enableReceiveTimeout(10);
      System.out.println("Receive timeout not supported");      
    }
    catch (UnsupportedCommOperationException e) {
      System.out.println("Receive timeout not supported");   
    }
    
    try {
      thePort.enableReceiveFraming(10);
      System.out.println("Receive framing supported");      
    }
    catch (UnsupportedCommOperationException e) {
      System.out.println("Receive framing not supported");   
    }
  } 
}
