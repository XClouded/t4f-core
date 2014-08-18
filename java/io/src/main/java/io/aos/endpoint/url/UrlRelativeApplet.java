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
import java.awt.GridLayout;
import java.awt.Label;
import java.net.MalformedURLException;
import java.net.URL;

public class UrlRelativeApplet extends Applet {
    private static final long serialVersionUID = 1L;

    public void init() {

        try {
            URL base = this.getDocumentBase();
            URL relative = new URL(base, "mailinglists.html");
            this.setLayout(new GridLayout(2, 1));
            this.add(new Label(base.toString()));
            this.add(new Label(relative.toString()));
        }
        catch (MalformedURLException e) {
            this.add(new Label("This shouldn't happen!"));
        }

    }

}
