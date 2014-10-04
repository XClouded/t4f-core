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

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.junit.Ignore;
import org.junit.Test;
import org.xbill.DNS.DClass;
import org.xbill.DNS.ExtendedResolver;
import org.xbill.DNS.Message;
import org.xbill.DNS.Name;
import org.xbill.DNS.Record;
import org.xbill.DNS.Resolver;
import org.xbill.DNS.ReverseMap;
import org.xbill.DNS.Section;
import org.xbill.DNS.Type;

@Ignore
public class AddressDnsTest {

    @Test
    public void test1() throws UnknownHostException {
        InetAddress addr = InetAddress.getByName("127.0.0.1");
        System.out.println(addr.getHostName());
    }

    @Test
    public void test2() throws UnknownHostException {
        long before = System.currentTimeMillis();
        InetAddress addr = InetAddress.getByName("127.0.0.1");
        System.out.println(addr.getHostName());
        long after = System.currentTimeMillis();
        System.out.println((after - before) + " ms");
    }

    @Test
    public void test3() throws IOException {
        long now = System.currentTimeMillis();
        System.out.println(reverseDns("192.222.1.13"));
        System.out.println(reverseDns("208.201.239.36"));
        long after = System.currentTimeMillis();
        System.out.println((after - now) + " ms");
    }

    private String reverseDns(String hostIp) throws IOException {
        Resolver res = new ExtendedResolver();
        Name name = ReverseMap.fromAddress(hostIp);
        int type = Type.PTR;
        int dclass = DClass.IN;
        Record rec = Record.newRecord(name, type, dclass);
        Message query = Message.newQuery(rec);
        Message response = res.send(query);
        Record[] answers = response.getSectionArray(Section.ANSWER);
        assertNotNull(answers[0].rdataToString());
        return answers[0].rdataToString();
    }

}
