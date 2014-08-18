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
import java.net.MalformedURLException;
import java.net.URL;

public class UrlProtocolApplet extends Applet {
    private static final long serialVersionUID = 1L;

    private TextArea results = new TextArea();

    @Override
    public void init() {
        this.setLayout(new BorderLayout());
        this.add("Center", results);
    }

    @Override
    public void start() {

        String host = "www.peacefire.org";
        
        String file = "/bypass/SurfWatch/";
        
        String[] schemes = { "http", "https", "ftp", "mailto", "telnet", "file", "ldap", "gopher", "jdbc", "rmi",
                "jndi", "jar", "doc", "netdoc", "nfs", "verbatim", "finger", "daytime", "systemresource" };
        
        for (int i = 0; i < schemes.length; i++) {
            try {
                URL u = new URL(schemes[i], host, file);
                results.append(schemes[i] + " is supported\r\n");
            }
            catch (MalformedURLException e) {
                results.append(schemes[i] + " is not supported\r\n");
            }
        }

    }

}
