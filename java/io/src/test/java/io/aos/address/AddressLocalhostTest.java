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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.junit.Test;

public class AddressLocalhostTest {

    @Test
    public void testLocalhost() throws UnknownHostException {
        InetAddress address = InetAddress.getLocalHost();
        System.out.println(address);
    }

    @Test
    public void testLocalhostExtended() throws UnknownHostException {
        InetAddress localhost = InetAddress.getLocalHost();
        assertNotNull(localhost);
        assertNotNull(localhost.getHostName());
        System.out.println("Local Hostname=" + localhost.getHostName());
        byte[] localhostAddress = localhost.getAddress();
        String ipAddrStr = new String();
        for (int i = 0; i < localhostAddress.length; i++) {
            if (i > 0) {
                ipAddrStr += ".";
            }
            ipAddrStr += localhostAddress[i] & 0xFF;
        }
        assertNotSame(0, ipAddrStr.length());
        System.out.println("Localhost IP Address=" + ipAddrStr);
    }

}
