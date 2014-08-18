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
package io.aos.endpoint.file.bio;


import io.aos.in.bio.SafeBufferedCharIn;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

public class BioWeblogTest {

    public static void main(String... args) {

        Date start = new Date();
        try {
            FileInputStream fis = new FileInputStream(args[0]);
            Reader inputStream= new InputStreamReader(fis);
            SafeBufferedCharIn binputStream= new SafeBufferedCharIn(inputStream);

            String entry = null;
            while ((entry = binputStream.readLine()) != null) {

                // separate outputStream the IP address
                int index = entry.indexOf(' ', 0);
                String ip = entry.substring(0, index);
                String theRest = entry.substring(index, entry.length());

                // find the host name and print it out
                try {
                    InetAddress address = InetAddress.getByName(ip);
                    System.out.println(address.getHostName() + theRest);
                } catch (UnknownHostException e) {
                    System.out.println(entry);
                }

            }
        } catch (IOException e) {
            System.out.println("Exception: " + e);
        }

        Date end = new Date();
        long elapsedTime = (end.getTime() - start.getTime()) / 1000;
        System.out.println("Elapsed time: " + elapsedTime + " seconds");

    }

}
