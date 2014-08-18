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
import java.util.TooManyListenersException;

public class PhoneListener implements SerialPortEventListener {

  public static void main(String... args) {
  
    String portName = "COM1";
    if (args.length > 0) portName = args[0];
    
    PhoneListener pl = new PhoneListener();
    
    try {
      CommPortIdentifier cpi = CommPortIdentifier.getPortIdentifier(portName);
      if (cpi.getPortType() == CommPortIdentifier.PORT_SERIAL) {
        SerialPort modem = (SerialPort) cpi.open("Phone Listener", 1000);
        modem.notifyOnRingIndicator(true);
        modem.addEventListener(pl);
      }
    }
    catch (NoSuchPortException ex) {
      System.err.println("Usage: java PhoneListener port_name");
    }
    catch (TooManyListenersException ex) {
      // shouldn't happen in this example
    }
    catch (PortInUseException ex) {System.err.println(ex);}
  }

  public void serialEvent(SerialPortEvent evt) {
  
    System.err.println(evt.getEventType());
    if (evt.getEventType() == SerialPortEvent.RI) {
      System.out.println("The phone is ringing");
    }
  }
} 
