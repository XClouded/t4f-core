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
import javax.usb.*;
import javax.usb.event.*;

public class HotplugListener implements UsbServicesListener {

  public void usbDeviceAttached(UsbServicesEvent event) {
    UsbDevice device = event.getUsbDevice();
    System.out.println(getDeviceInfo(device) + " was added to the bus.");
  }

  public void usbDeviceDetached(UsbServicesEvent event) {
    UsbDevice device = event.getUsbDevice();
    System.out.println(getDeviceInfo(device) + " was removed from the bus.");
  }

  private static String getDeviceInfo(UsbDevice device) {
    try {
      String product = device.getProductString();
      String serial  = device.getSerialNumberString();
      if (product == null) return "Unknown USB device";
      if (serial != null) return product + " " + serial;
      else return product;
    }
    catch (Exception ex) {
    }
    return "Unknown USB device";
  }
}
