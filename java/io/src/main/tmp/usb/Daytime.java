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
import javax.microedition.lcdui.*;
import javax.microedition.io.*;
import java.io.*;

public class Daytime extends MIDlet {    

  public Daytime() {
        
    Form form = new Form("Network Time");
    InputStream inputStream = null;
    try {
      in = Connector.openInputStream("socket://time-a.nist.gov:13");
      StringBuffer sb = new StringBuffer();
      for (int c = inputStream.read(); c != -1; c = inputStream.read()) {
        sb.append((char) c);
      }
      form.append(sb.toString());
    }
    catch (IOException ex) {
      form.append(ex.getMessage());
    }
    finally {
      try {
        if (in != null) inputStream.close();
      }
      catch (IOException ex) { /* Oh well. we tried.*/ }
    }
 
    Display.getDisplay(this).setCurrent(form);
  }

  public void startApp() {}
  public void pauseApp() {}
  public void destroyApp(boolean unconditional) {}
}
