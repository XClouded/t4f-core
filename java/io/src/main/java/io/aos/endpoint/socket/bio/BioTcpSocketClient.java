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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;

public class BioTcpSocketClient {
    private final static String host = "localhost";
    private static final int port = 19999;
    private final static StringBuffer instr = new StringBuffer();
    private final static String TimeStamp = new java.util.Date().toString();

    public static void main(String... args) throws IOException {

        InetAddress address = InetAddress.getByName(host);

        Socket connection = new Socket(address, port);

        BufferedOutputStream bos = new BufferedOutputStream(connection.getOutputStream());
        OutputStreamWriter osw = new OutputStreamWriter(bos, "US-ASCII");
        String process = "Calling the Socket Server on " + host + " port " + port + " at " + TimeStamp + (char) 13;
        osw.write(process);
        osw.flush();

        BufferedInputStream bis = new BufferedInputStream(connection.getInputStream());
        InputStreamReader isr = new InputStreamReader(bis, "US-ASCII");
        int c;
        while ((c = isr.read()) != 13) {
            instr.append((char) c);
        }

        connection.close();
        System.out.println(instr);

    }

}
