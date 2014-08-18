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

import java.security.*;

import javax.crypto.Cipher;

import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.util.ASN1Dump;

/**
 * Basic class for exploring a PKCS #1 V1.5 Signature.
 */
public class PKCS1SigEncodingExample
{
    public static void main(
        String[]    args)
        throws Exception
    {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA", "BC");
        
        keyGen.initialize(512, new SecureRandom());
        
        KeyPair             keyPair = keyGen.generateKeyPair();
        Signature           signature = Signature.getInstance("SHA256withRSA", "BC");

        // generate a signature
        signature.initSign(keyPair.getPrivate());

        byte[] message = new byte[] { (byte)'a', (byte)'b', (byte)'c' };

        signature.update(message);

        byte[]  sigBytes = signature.sign();
        
        // open the signature
        Cipher	cipher = Cipher.getInstance("RSA/None/PKCS1Padding", "BC");
        
        cipher.init(Cipher.DECRYPT_MODE, keyPair.getPublic());
        
        byte[]  decSig = cipher.doFinal(sigBytes);
        
        // parse the signature
        ASN1InputStream	aIn = new ASN1InputStream(decSig);
        ASN1Sequence	seq = (ASN1Sequence)aIn.readObject();
        
        System.out.println(ASN1Dump.dumpAsString(seq));
        
        // grab a digest of the correct type
        MessageDigest	hash = MessageDigest.getInstance("SHA-256", "BC");
        
        hash.update(message);

        ASN1OctetString	sigHash = (ASN1OctetString)seq.getObjectAt(1);
        if (MessageDigest.isEqual(hash.digest(), sigHash.getOctets()))
        {
            System.out.println("hash verification succeeded");
        }
        else
        {
            System.out.println("hash verification failed");
        }
    }
}
