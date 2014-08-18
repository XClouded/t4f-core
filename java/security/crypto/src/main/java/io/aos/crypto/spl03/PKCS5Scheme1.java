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
package io.aos.crypto.spl03;

import java.security.MessageDigest;

/**
 * A basic implementation of PKCS #5 Scheme 1.
 */
public class PKCS5Scheme1
{
    private MessageDigest digest;
    
    public PKCS5Scheme1(
        MessageDigest    digest)
    {
        this.digest = digest;
    }

    public byte[] generateDerivedKey(
        char[] password,
        byte[] salt,
        int    iterationCount)
    {
        for (int i = 0; i != password.length; i++)
        {
            digest.update((byte)password[i]);
        }
        
        digest.update(salt);

        byte[] digestBytes = digest.digest();
        for (int i = 1; i < iterationCount; i++)
        {
            digest.update(digestBytes);
            digestBytes = digest.digest();
        }

        return digestBytes;
    }
}
