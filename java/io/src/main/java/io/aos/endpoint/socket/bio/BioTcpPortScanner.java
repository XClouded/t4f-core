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
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class BioTcpPortScanner {

    public static void main(String... args) {

        String host = "localhost";

        if (args.length > 0) {
            host = args[0];
        }

        Socket connection = null;
        try {
            InetAddress theAddress = InetAddress.getByName(host);
            for (int i = 1; i < 65536; i++) {
                try {
                    connection = new Socket(host, i);
                    System.out.println("There is a server on port " + i + " of " + host);
                }
                catch (IOException e) {
                    // must not be a server on this port
                }
            }
        }
        catch (UnknownHostException e) {
            System.err.println(e);
        }
        finally {
            try {
                if (connection != null)
                    connection.close();
            }
            catch (IOException e) {
            }
        }

    }

}
