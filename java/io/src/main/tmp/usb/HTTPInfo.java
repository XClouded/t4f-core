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
import io.netty.handler.codec.redis.Command;

import java.awt.TextField;
import java.io.IOException;
import java.text.Normalizer.Form;

import org.apache.commons.httpclient.HttpConnection;
import org.eclipse.jdt.internal.compiler.util.Util.Displayable;
import org.eclipse.jetty.server.Connector;

public class HTTPInfo extends MIDlet implements CommandListener {

  private Display display;
  private TextBox textBox;
  
  private Form getInfo(String url) {
    Form form = new Form("HTTP Info");
    HttpConnection connection = null;
    try {
        connection = (HttpConnection) Connector.open(url);
        connection.setRequestMethod("HEAD");
        for (int i = 0; ; i++) {
            String key = connection.getHeaderFieldKey(i);
            String value = connection.getHeaderField(i);
            if (value == null) break;
            if (key != null) form.append(key + ": " + value + "\n");
            else form.append("***" + value + "\n");;
        }
    }
    catch (Exception ex) {
       form.append(ex.getMessage() +"\n");
    }
   finally {
      try {
        if (connection != null) connection.close();
      }
      catch (IOException ex) { /* Oh well. we tried.*/ }
    }
 
    return form;
  }

  public void startApp() {
    display = Display.getDisplay(this);

    if (textBox == null) {
      textBox = new TextBox("URL", "http://", 255, TextField.URL);
    }
    display.setCurrent(textBox);

    Command getInfo = new Command("HTTP Headers", Command.OK, 10);
    textBox.addCommand(getInfo);
    textBox.setCommandListener(this);
  }

  public void commandAction(Command command, Displayable displayable) {
    Thread t = new Thread(
       new Runnable() {
         public void run() {
           display.setCurrent(getInfo(textBox.getString()));
         }
       }
     );
     t.start();
  }

  protected void pauseApp() {}
  protected void destroyApp(boolean unconditional) {}
}
