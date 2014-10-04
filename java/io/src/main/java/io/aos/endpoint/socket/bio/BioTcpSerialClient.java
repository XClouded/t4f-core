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
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class BioTcpSerialClient {
    public static void main(String... args) throws IOException {

        Socket echoSocket = null;
        ObjectOutputStream outputStream = null;
        ObjectInputStream inputStream = null;

        try {
            // echoSocket = new Socket("taranis", 7);
            echoSocket = new Socket("127.0.0.1", 10007);

            outputStream = new ObjectOutputStream(echoSocket.getOutputStream());
            inputStream = new ObjectInputStream(echoSocket.getInputStream());

        }
        catch (UnknownHostException e) {
            System.err.println("Don't know about host: taranis.");
            System.exit(1);
        }
        catch (IOException e) {
            System.err.println("Couldn't get I/O for " + "the connection to: taranis.");
            System.exit(1);
        }

        BioTcpSerialPoint3d pt1 = new BioTcpSerialPoint3d(7, 8, 10);
        BioTcpSerialPoint3d pt2 = null;

        System.out.println("Sending point: " + pt1 + " to Server");
        outputStream.writeObject(pt1);
        outputStream.flush();
        System.out.println("Send point, waiting for return value");

        try {
            pt2 = (BioTcpSerialPoint3d) inputStream.readObject();
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        System.out.println("Got point: " + pt2 + " from Server");

        outputStream.close();
        inputStream.close();
        echoSocket.close();
    }
}
