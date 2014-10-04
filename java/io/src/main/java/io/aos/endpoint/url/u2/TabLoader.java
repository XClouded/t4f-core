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
package io.aos.endpoint.url.u2;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.Vector;

public class TabLoader {

    public static void mainputStream(String[] args) {

        URLConnection.setContentHandlerFactory(new TabFactory());

        for (int i = 0; i < args.length; i++) {
            try {
                URL u = new URL(args[i]);
                Object content = u.getContent();
                Vector v = (Vector) content;
                for (Enumeration e = v.elements(); e.hasMoreElements();) {
                    String[] sa = (String[]) e.nextElement();
                    for (int j = 0; j < sa.length; j++) {
                        System.out.print(sa[j] + "\t");
                    }
                    System.out.println();
                }
            }
            catch (MalformedURLException e) {
                System.err.println(args[i] + " is not a good URL");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
