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
package io.aos.address;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class AddressHostLookup {

    public static void main(String... args) {
    
        if (args.length > 0) { // use command line
            for (int i = 0; i < args.length; i++) {
                System.out.println(lookup(args[i]));
            }
        } else {
            BufferedReader inputStream= new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter names and IP addresses. Enter \"exit\" to quit.");
            try {
                while (true) {
                    String host = inputStream.readLine();
                    if (host.equalsIgnoreCase("exit") || host.equalsIgnoreCase("quit")) {
                        break;
                    }
                    System.out.println(lookup(host));
                }
            } catch (IOException e) {
                System.err.println(e);
            }
    
        }
    
    }

    @Test
    public void testByName(String[] args) {

        try {
            InetAddress address = InetAddress.getByName("www.oreilly.com");
            System.out.println(address);
        } catch (UnknownHostException e) {
            System.out.println("Could not find www.oreilly.com");
        }

    }

    private static String lookup(String host) {

        InetAddress thisComputer;
        byte[] address;

        // get the bytes of the IP address
        try {
            thisComputer = InetAddress.getByName(host);
            address = thisComputer.getAddress();
        } catch (UnknownHostException e) {
            return "Cannot find host " + host;
        }

        if (isHostname(host)) {
            // Print the IP address
            String dottedQuad = "";
            for (int i = 0; i < address.length; i++) {
                int unsignedByte = address[i] < 0 ? address[i] + 256 : address[i];
                dottedQuad += unsignedByte;
                if (i != address.length - 1)
                    dottedQuad += ".";
            }
            return dottedQuad;
        } else { // this is an IP address
            return thisComputer.getHostName();
        }

    } // end lookup

    private static boolean isHostname(String host) {

        char[] ca = host.toCharArray();
        // if we see a character that is neither a digit nor a period
        // then host is probably a hostname
        for (int i = 0; i < ca.length; i++) {
            if (!Character.isDigit(ca[i])) {
                if (ca[i] != '.')
                    return true;
            }
        }

        // Everything was either a digit or a period
        // so host looks like an IP address inputStreamdotted quad format
        return false;

    } // end isHostName

} // end HostLookup
