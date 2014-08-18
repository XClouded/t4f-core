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
import javax.bluetooth.*;

public class BluetoothProperties {
  
  public static void main(String... args) throws Exception {
    LocalDevice device  = LocalDevice.getLocalDevice();
    System.out.println("API version: " 
        + device.getProperty("bluetooth.api.version"));
    System.out.println("bluetooth.master.switch: " 
        + device.getProperty("bluetooth.master.switch"));
    System.out.println("Maximum number of service attributes: "
        + device.getProperty("bluetooth.sd.attr.retrievable.max"));
    System.out.println("Maximum number of connected devices: " 
        + device.getProperty("bluetooth.connected.devices.max"));
    System.out.println("Maximum receive MTU size in L2CAP: " 
        + device.getProperty("bluetooth.l2cap.receiveMTU.max"));
    System.out.println(
        "Maximum number of simultaneous service discovery transactions: " 
        + device.getProperty("bluetooth.sd.trans.max"));
    System.out.println("Inquiry scanning allowed during connection: " 
        + device.getProperty("bluetooth.connected.inquiry.scan"));
    System.out.println("Page scanning allowed during connection: " 
        + device.getProperty("bluetooth.connected.page.scan"));
    System.out.println("Inquiry allowed during connection: " 
        + device.getProperty("bluetooth.connected.inquiry"));
    System.out.println("Page allowed during connection: " 
        + device.getProperty("bluetooth.connected.page"));
    System.exit(0);
  }
}
