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
package io.aos.crypto.digest;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class TrueMirror {

    public static void main(String... args) throws IOException, NoSuchAlgorithmException {

        if (args.length != 2) {
            System.err.println("Usage: java TrueMirror url1 url2");
            return;
        }

        URL source = new URL(args[0]);
        URL mirror = new URL(args[1]);
        byte[] sourceDigest = getDigestFromURL(source);
        byte[] mirrorDigest = getDigestFromURL(mirror);
        if (MessageDigest.isEqual(sourceDigest, mirrorDigest)) {
            System.out.println(mirror + " is up to date");
        }
        else {
            System.out.println(mirror + " needs to be updated");
        }
    }

    public static byte[] getDigestFromURL(URL u) throws IOException, NoSuchAlgorithmException {

        MessageDigest md5 = MessageDigest.getInstance("MD5");
        InputStream inputStream = u.openStream();
        byte[] data = new byte[128];
        while (true) {
            int bytesRead = inputStream.read(data);
            if (bytesRead < 0) { // end of stream
                break;
            }
            md5.update(data, 0, bytesRead);
        }
        return md5.digest();
    }

}
