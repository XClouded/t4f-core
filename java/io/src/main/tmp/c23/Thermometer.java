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
import java.util.*;
import javax.usb.*;
import javax.usb.util.*;
import java.io.*;

public class Thermometer {

  public final static int VERNIER_VENDOR_ID = 0x8F7;
  public final static int GOTEMP_PRODUCT_ID = 2;

  public static void main(String... args) throws UsbException, IOException {
    UsbDevice probe = findProbe();
    if (probe == null) {
      System.err.println("No Go!Temp probe attached.");
      return;
    }
   
    UsbConfiguration config = probe.getActiveUsbConfiguration();
    UsbInterface theInterface = config.getUsbInterface((byte) 0);
    theInterface.claim(new UsbInterfacePolicy() {
      public boolean forceClaim(UsbInterface usbInterface) {
        return true;
      }
    });

    UsbEndpoint endpoint = (UsbEndpoint) theInterface.getUsbEndpoints().get(0);
    UsbPipe pipe = endpoint.getUsbPipe();

    // set up the IRP
    UsbIrp irp = pipe.createUsbIrp();
    byte[] data = new byte[8];
    irp.setData(data);
    pipe.open();

    outer: while (true) {
      pipe.syncSubmit(irp); 
      int numberOfMeasurements = data[0];
      for (int i = 0; i < numberOfMeasurements; i++) {
        int result = UsbUtil.toShort(data[2*i+3], data[2*i+2]);
        int sequenceNumber = UsbUtil.unsignedInt(data[1]);
        double temperature = result / 128.0;
        if (temperature > 110.0) {
          System.err.println("Maximum accurate temperature exceeded.");
          break outer;
        }
        else if (temperature < -10) {
          System.err.println("Minimum accurate temperature exceeded.");
          break outer;
        }
        System.out.println("Measurement " + sequenceNumber + ": " 
         + temperature + "°C");
      }
      // get ready to reuse IRP
      irp.setComplete(false);
    }
    pipe.abortAllSubmissions();
    pipe.close();
    theInterface.release();
  }
  
  private static UsbDevice findProbe() throws UsbException, IOException {
    UsbServices services = UsbHostManager.getUsbServices();
    UsbHub root = services.getRootUsbHub();
    return searchDevices(root);
  }

  private static UsbDevice searchDevices(UsbHub hub) 
   throws UsbException, IOException {
    List devices = hub.getAttachedUsbDevices();
    Iterator iterator = devices.iterator();
    while (iterator.hasNext()) {
      UsbDevice device = (UsbDevice) iterator.next();
      UsbDeviceDescriptor descriptor = device.getUsbDeviceDescriptor();
      int manufacturerCode = descriptor.idVendor();
      int productCode = descriptor.idProduct();

      if (manufacturerCode == VERNIER_VENDOR_ID 
       && productCode == GOTEMP_PRODUCT_ID) {
        return device;
      }
      else if (device.isUsbHub()) {
        UsbDevice found = searchDevices((UsbHub) device);
        if (found != null) return found;
      }
    } 
    return null; // didn't find it
  }
}
