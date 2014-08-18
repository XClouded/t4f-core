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
package io.aos.crypto.spl05;


import io.aos.crypto.spl04.Utils;

import java.security.*;
import java.security.spec.X509EncodedKeySpec;

import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.util.ASN1Dump;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;


/**
 * Simple example showing use of X509EncodedKeySpec
 */
public class X509EncodedKeySpecExample
{
    public static void main(
        String[]    args)
        throws Exception
    {
        // create the keys
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", "BC");
        
        generator.initialize(128, Utils.createFixedRandom());

        KeyPair               pair = generator.generateKeyPair();

        // dump public key
        ASN1InputStream	      aIn = new ASN1InputStream(pair.getPublic().getEncoded());
        SubjectPublicKeyInfo  info = SubjectPublicKeyInfo.getInstance(aIn.readObject());
        
        System.out.println(ASN1Dump.dumpAsString(info));        
        System.out.println(ASN1Dump.dumpAsString(info.getPublicKey()));

        // create from specification
        X509EncodedKeySpec  x509Spec = new X509EncodedKeySpec(pair.getPublic().getEncoded());
        KeyFactory          keyFact = KeyFactory.getInstance("RSA", "BC");
        PublicKey           pubKey = keyFact.generatePublic(x509Spec);
        
        if (pubKey.equals(pair.getPublic()))
        {
            System.out.println("key recovery successful");
        }
        else
        {
            System.out.println("key recovery failed");
        }
    }
}
