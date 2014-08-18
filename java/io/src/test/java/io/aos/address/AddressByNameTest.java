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

import java.net.*;

import org.junit.Test;

public class AddressByNameTest {

    @Test
    public void test1() {

        try {
            InetAddress address = InetAddress.getByName("204.148.40.9");
            System.out.println(address);
        }
        catch (UnknownHostException e) {
            System.out.println("Could not find 204.148.40.9");
        }

    }

    @Test
    public void test2() throws UnknownHostException {
        InetAddress ia = InetAddress.getByName("152.2.22.3");
        System.out.println(ia.getHostName());
    }

}
