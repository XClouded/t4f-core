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
package io.aos.crypto.spl00;

import io.aos.crypto.util.Base64;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

public class Spill {

    public static void main(String... args) throws Exception {

        if (args.length != 1) {
            System.out.println("Usage: Spill file");
            return;
        }

        BufferedReader in = new BufferedReader(new FileReader(args[0]));
        String begin = in.readLine();
        if (begin.equals("-----BEGIN CERTIFICATE-----") == false)
            throw new IOException("Couldn't find certificate beginning");
        String base64 = new String();
        boolean trucking = true;
        while (trucking) {
            String line = in.readLine();
            if (line.startsWith("-----"))
                trucking = false;
            else
                base64 += line;
        }
        in.close();
        byte[] certificateData = Base64.decode(base64);

        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        X509Certificate c = (X509Certificate) cf.generateCertificate(new ByteArrayInputStream(certificateData));

        System.out.println("Subject: " + c.getSubjectDN().getName());
        System.out.println("Issuer : " + c.getIssuerDN().getName());
        System.out.println("Serial number: " + c.getSerialNumber().toString(16));
        System.out.println("Valid from " + c.getNotBefore() + " to " + c.getNotAfter());

        System.out.println("Fingerprints:");
        doFingerprint(certificateData, "MD5");
        doFingerprint(certificateData, "SHA");
    }

    protected static void doFingerprint(byte[] certificateBytes, String algorithm) throws Exception {
        System.out.print("  " + algorithm + ": ");
        MessageDigest md = MessageDigest.getInstance(algorithm);
        md.update(certificateBytes);
        byte[] digest = md.digest();

        for (int i = 0; i < digest.length; i++) {
            if (i != 0)
                System.out.print(":");
            int b = digest[i] & 0xff;
            String hex = Integer.toHexString(b);
            if (hex.length() == 1)
                System.out.print("0");
            System.out.print(hex);
        }
        System.out.println();
    }
}
