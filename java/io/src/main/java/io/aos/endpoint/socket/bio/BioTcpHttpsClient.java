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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class BioTcpHttpsClient {

    public static void main(String... args) {

        if (args.length == 0) {
            System.out.println("Usage: java HTTPSClient host");
            return;
        }

        int port = 443; // default https port
        String host = args[0];

        try {

            SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();

            SSLSocket socket = (SSLSocket) factory.createSocket(host, port);

            // enable all the suites
            String[] supported = socket.getSupportedCipherSuites();
            socket.setEnabledCipherSuites(supported);

            Writer outputStream = new OutputStreamWriter(socket.getOutputStream());
            // https requires the full URL inputStreamthe GET line
            outputStream.write("GET http://" + host + "/ HTTP/1.1\r\n");
            outputStream.write("\r\n");
            outputStream.flush();

            // read response
            BufferedReader inputStream= new BufferedReader(new InputStreamReader(socket.getInputStream()));
            int c;
            while ((c = inputStream.read()) != -1) {
                System.out.write(c);
            }

            outputStream.close();
            inputStream.close();
            socket.close();

        } catch (IOException e) {
            System.err.println(e);
        }

    }

}
