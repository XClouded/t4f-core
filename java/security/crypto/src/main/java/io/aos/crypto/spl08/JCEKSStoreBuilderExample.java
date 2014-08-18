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
package io.aos.crypto.spl08;

import java.security.KeyStore;

/**
 * Basic example of use of KeyStore.Builder to create an object that
 * can be used recover a private key.
 */
public class JCEKSStoreBuilderExample
{   
    public static void main(
        String[]    args)
        throws Exception
    {
        KeyStore store = JCEKSStoreEntryExample.createKeyStore();
        
        char[]   password = "storePassword".toCharArray();
        
        // create the builder
        KeyStore.Builder builder = KeyStore.Builder.newInstance(store, new KeyStore.PasswordProtection(JCEKSStoreEntryExample.keyPassword));
        
        // use the builder to recover the KeyStore and obtain the key
        store = builder.getKeyStore();
        
        KeyStore.ProtectionParameter param = builder.getProtectionParameter(Utils.END_ENTITY_ALIAS);

        KeyStore.Entry entry = store.getEntry(Utils.END_ENTITY_ALIAS, param);

        System.out.println("recovered " + entry.getClass());
    }
}
