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
package io.aos.io.c23;
import java.io.UnsupportedEncodingException;
import java.util.*;
import javax.usb.*;

public class USBStringLister {

  public static void main(String... args) 
   throws UsbException, UnsupportedEncodingException {
    UsbServices services = UsbHostManager.getUsbServices();
    UsbHub root = services.getRootUsbHub();
    listDevices(root);
  }
  
  public static void listDevices(UsbHub hub) 
   throws UnsupportedEncodingException, UsbException {
    List devices = hub.getAttachedUsbDevices();
    Iterator iterator = devices.iterator();
    while (iterator.hasNext()) {
      UsbDevice device = (UsbDevice) iterator.next();
      listStrings(device);
      if (device.isUsbHub()) {
        listDevices((UsbHub) device);
      }
    } 
  }

  public static void listStrings(UsbDevice device) 
   throws UnsupportedEncodingException, UsbException {

    for (int i = 0; i <= 255; i++) {
      try {
        String s = device.getString((byte) i);
        System.out.println("  " + i + ":\t" + s);
      }
      catch (UsbStallException ex) {
        // We've reached the end of the table for this device.
        break;
      }
    }
    System.out.println();
  }
}
