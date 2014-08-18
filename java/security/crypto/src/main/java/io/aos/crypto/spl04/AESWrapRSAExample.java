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
package io.aos.crypto.spl04;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;

import javax.crypto.Cipher;

/**
 * Wrapping an RSA Key using AES
 */
public class AESWrapRSAExample
{
    public static void main(
        String[]    args)
		throws Exception
    {
        Cipher       cipher = Cipher.getInstance("AES/ECB/PKCS7Padding", "BC");
        SecureRandom random = new SecureRandom();
        
        KeyPairGenerator fact = KeyPairGenerator.getInstance("RSA", "BC");
        fact.initialize(1024, new SecureRandom());

        KeyPair     keyPair = fact.generateKeyPair();
        Key         wrapKey = Utils.createKeyForAES(256, random);
        
        // wrap the RSA private key
        cipher.init(Cipher.WRAP_MODE, wrapKey);
        
        byte[] wrappedKey = cipher.wrap(keyPair.getPrivate());

        // unwrap the RSA private key
        cipher.init(Cipher.UNWRAP_MODE, wrapKey);
        
        Key key = cipher.unwrap(wrappedKey, "RSA", Cipher.PRIVATE_KEY);

        if (keyPair.getPrivate().equals(key))
        {
            System.out.println("Key recovered.");
        }
		else
		{
		    System.out.println("Key recovery failed.");
		}
    }
}
