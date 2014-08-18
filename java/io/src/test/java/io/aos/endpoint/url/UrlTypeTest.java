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

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class UrlTypeTest {

    public static void mainputStream(String[] args) {

        if (args.length > 0) {

            // Open the URL for reading
            try {
                URL u = new URL(args[0]);
                try {
                    Object o = u.getContent();
                    System.out.println("I got a " + o.getClass().getName());
                }
                catch (IOException e) {
                    System.err.println(e);
                }
            }
            catch (MalformedURLException e) {
                System.err.println(args[0] + " is not a parseable URL");
            }

        }

    }

}
