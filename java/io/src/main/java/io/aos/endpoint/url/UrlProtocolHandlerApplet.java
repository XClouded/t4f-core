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
package io.aos.endpoint.url;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.TextArea;
import java.net.URL;
import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;
import java.util.Enumeration;
import java.util.Properties;

public class UrlProtocolHandlerApplet extends Applet implements URLStreamHandlerFactory {
    private static final long serialVersionUID = 1L;

    private TextArea results = new TextArea(80, 40);

    public void init() {
        this.setLayout(new BorderLayout());
        this.add("Center", results);
    }

    public URLStreamHandler createURLStreamHandler(String scheme) {
        return null;
    }

    public void start() {
        try {
            Properties p = System.getProperties();
            Enumeration list = p.elements();
            String s = "";
            while (list.hasMoreElements()) {
                Object o = list.nextElement();
                s += o.toString();
                s += "\r\n";
            }

            results.append(s);
        }
        catch (Throwable e) {
            results.append(e.toString() + "\r\n");
        }
        try {
            results.append("Trying to get java.version\r\n");
            String s = System.getProperty("java.version", ".");
            if (s == null)
                s = "no such property";
            s += "\r\n";
            results.append(s);
        }
        catch (Throwable e) {
            results.append(e.toString() + "\r\n");
        }
        try {
            results.append("Trying to get classpath\r\n");
            String s = System.getProperty("java.class.path", ".");
            if (s == null)
                s = "no such property";
            s += "\r\n";
            results.append(s);
        }
        catch (Throwable e) {
            results.append(e.toString() + "\r\n");
        }
        try {
            results.append("Trying to get java.protocol.handler.pkgs\r\n");
            String s = System.getProperty("java.protocol.handler.pkgs");
            if (s == null)
                s = "no such property";
            s += "; get succeeded\r\n";
            results.append(s);
        }
        catch (Throwable e) {
            results.append(e.toString() + "\r\n");
        }
        try {
            results.append("Trying to get java.content.handler.pkgs\r\n");
            String s = System.getProperty("java.content.handler.pkgs");
            if (s == null)
                s = "no such property";
            s += "; get succeeded\r\n";
            results.append(s);
        }
        catch (Throwable e) {
            results.append(e.toString() + "\r\n");
        }
        try {
            results.append("Trying to set java.protocol.handler.pkgs\r\n");
            System.setProperty("java.protocol.handler.pkgs", "com.macfaq.net.www.protocol");
        }
        catch (Throwable e) {
            results.append(e.toString() + "\r\n");
        }
        try {
            results.append("Trying to set URLStreamHandlerFactory\r\n");
            URL.setURLStreamHandlerFactory(this);
        }
        catch (Throwable e) {
            results.append(e.toString() + "\r\n");
        }
        try {
            results.append("Trying to construct chargen URL\r\n");
            URL u = new URL("chargen://vision.poly.edu");
            results.append(u + "\r\n");
        }
        catch (Throwable e) {
            results.append(e.toString() + "\r\n");
        }
        try {
            results.append("Trying to construct impgen URL\r\n");
            URL u = new URL("impgen://vision.poly.edu");
            results.append(u + "\r\n");
        }
        catch (Throwable e) {
            results.append(e.toString() + "\r\n");
        }

    }

}
