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
package io.aos.endpoint.socket.bio;

import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;

public class BioTcpUrlTimeClient {

    public static void main(String... args) {

        System.setProperty("java.protocol.handler.pkgs", "com.macfaq.net.www.protocol");

        try {
            // You can replace this with your own time server
            // use tock.usno.navy.mil????
            URL u = new URL("time://vision.poly.edu/");
            Class[] types = { String.class, Date.class, Calendar.class, Long.class };
            Object o = u.getContent(types);
            System.out.println(o);
        }
        catch (IOException e) {
            // Let's see what went wrong
            e.printStackTrace();
        }

    }

}
