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
package io.aos.crypto.digest.d1;

import static org.junit.Assert.assertNotNull;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.junit.Test;

public class DigestTest {

    @Test
    public void md5() throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        digest("test", md5);
        MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
        digest("test", sha1);
        MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
        digest("test", sha256);
    }

    private void digest(String message, MessageDigest md) {

        StringBuffer sb = new StringBuffer();
        byte buf[] = message.getBytes();
        byte[] md5 = md.digest(buf);
        for (int i = 0; i < md5.length; i++) {
            String tmpStr = "0" + Integer.toHexString((0xff & md5[i]));
            sb.append(tmpStr.substring(tmpStr.length() - 2));
        }
        String digest = sb.toString();
        assertNotNull(digest);
        System.out.printf("%s: \t%s\n", md.getAlgorithm(), digest);

    }

}
