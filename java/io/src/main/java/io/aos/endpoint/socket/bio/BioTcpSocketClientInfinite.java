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

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;

public class BioTcpSocketClientInfinite {

    public static void main(String... args) throws IOException {

        String host = "localhost";
        InetAddress address = InetAddress.getByName(host);
        int port = 25;
        Socket connection = new Socket(address, port);

        // String request = "ehlo test" + (char) 13 + (char) 10;
        String request = "ehlo test$$ezzeopiiop(éàççç~~~~^ùù***\")\'\"époi\n\n'";
        BufferedOutputStream bos = new BufferedOutputStream(connection.getOutputStream());
        OutputStreamWriter osw = new OutputStreamWriter(bos, "UTF-8");

        while (true) {
            osw.write(request);
        }
        /*
         * osw.flush();
         * 
         * BufferedInputStream bis = new
         * BufferedInputStream(connection.getInputStream());
         * 
         * InputStreamReader isr = new InputStreamReader(bis, "UTF-8");
         * 
         * StringBuffer response = new StringBuffer(); int c; while ((c =
         * isr.read()) != 13) { response.append((char) c); }
         * 
         * connection.close();
         * 
         * System.out.println(response);
         */
    }

}
