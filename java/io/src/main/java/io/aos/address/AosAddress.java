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

import java.net.InetAddress;

public class AosAddress {

    public int ipVersion(InetAddress ia) {
        byte[] address = ia.getAddress();
        if (address.length == 4) {
            return 4;
        }
        else if (address.length == 16) {
            return 6;
        }
        return -1;
    }

    public static char ipClass(InetAddress ia) {
        byte[] address = ia.getAddress();
        if (address.length != 4) {
            throw new IllegalArgumentException("Do not understand IPv6 addresses!");
        }
        int firstByte = address[0];
        if ((firstByte & 0x80) == 0) {
            return 'A';
        }
        else if ((firstByte & 0xC0) == 0x80) {
            return 'B';
        }
        else if ((firstByte & 0xE0) == 0xC0) {
            return 'C';
        }
        else if ((firstByte & 0xF0) == 0xE0) {
            return 'D';
        }
        else if ((firstByte & 0xF8) == 0xF0) {
            return 'E';
        }
        else {
            return 'F';
        }
    }

}
