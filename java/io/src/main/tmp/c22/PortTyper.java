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
import java.io.*;

public class PortTyper {

  public static void main(String... args) {

    if (args.length < 1) {
      System.out.println("Usage: java PortTyper portName");
      return;
    }

    try {    
      CommPortIdentifier com = CommPortIdentifier.getPortIdentifier(args[0]);
      CommPort thePort  = com.open("PortOpener", 10);
      CopyThread input = new CopyThread(System.in, thePort.getOutputStream());
      CopyThread output = new CopyThread(thePort.getInputStream(), System.out);
      input.start();
      output.start();
    }
    catch (Exception ex) {System.out.println(ex);}
  }
}

class CopyThread extends Thread {

  private InputStream theInput;
  private OutputStream theOutput;

  CopyThread(InputStream inputStream) {
    this(inputStream, System.out);
  }

  CopyThread(OutputStream outputStream) {
    this(System.in, outputStream);
  }

  CopyThread(InputStream inputStream, OutputStream outputStream) {
    theInput = in;
    theOutput = outputStream;
  }

  public void run() {

    try {
      byte[] buffer = new byte[256];
      while (true) {
        int bytesRead = theInput.read(buffer);
        if (bytesRead == -1) break;
        theOutput.write(buffer, 0, bytesRead);
      }
    }
    catch (IOException ex) {System.err.println(ex);}
  }
}
