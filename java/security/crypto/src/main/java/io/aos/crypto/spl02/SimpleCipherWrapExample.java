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
package io.aos.crypto.spl02;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

public class SimpleCipherWrapExample
{
    public static void main(
        String[]    args)
        throws Exception
    {
        // create a key to wrap
        
        KeyGenerator generator = KeyGenerator.getInstance("AES", "BC");
        generator.init(128);

        Key	keyToBeWrapped = generator.generateKey();

        System.out.println("input    : " + Utils.toHex(keyToBeWrapped.getEncoded()));
        
        // create a wrapper and do the wrapping
        
        Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding", "BC");
        
        KeyGenerator keyGen = KeyGenerator.getInstance("AES", "BC");
        keyGen.init(256);
        
        Key wrapKey = keyGen.generateKey();
        
        cipher.init(Cipher.ENCRYPT_MODE, wrapKey);
        
        byte[] wrappedKey = cipher.doFinal(keyToBeWrapped.getEncoded());

        System.out.println("wrapped  : " + Utils.toHex(wrappedKey));
        
        // unwrap the wrapped key
        
        cipher.init(Cipher.DECRYPT_MODE, wrapKey);
        
        Key key = new SecretKeySpec(cipher.doFinal(wrappedKey), "AES");

        System.out.println("unwrapped: " + Utils.toHex(key.getEncoded()));
    }
}
