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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.util.Enumeration;

import javax.crypto.SecretKey;
import javax.security.auth.x500.X500PrivateCredential;

/**
 * Example of using a JCEKS key store with KeyStore.Entry and 
 * KeyStore.ProtectionParameter objects.
 */
public class JCEKSStoreEntryExample
{
    public static char[]   keyPassword = "endPassword".toCharArray();
    public static char[]   secretKeyPassword = "secretPassword".toCharArray();
    
    public static KeyStore createKeyStore()
        throws Exception
    {
        KeyStore store = KeyStore.getInstance("JCEKS");

        // initialize
        store.load(null, null);
        
        X500PrivateCredential    rootCredential = Utils.createRootCredential();
        X500PrivateCredential    interCredential = Utils.createIntermediateCredential(rootCredential.getPrivateKey(), rootCredential.getCertificate());
        X500PrivateCredential    endCredential = Utils.createEndEntityCredential(interCredential.getPrivateKey(), interCredential.getCertificate());

        Certificate[]            chain = new Certificate[3];
        
        chain[0] = endCredential.getCertificate();
        chain[1] = interCredential.getCertificate();
        chain[2] = rootCredential.getCertificate();
        
        SecretKey                 secret = Utils.createKeyForAES(256, new SecureRandom());
        
        // set the entries
        store.setEntry(rootCredential.getAlias(), new KeyStore.TrustedCertificateEntry(rootCredential.getCertificate()), null);
        store.setEntry(endCredential.getAlias(), new KeyStore.PrivateKeyEntry(endCredential.getPrivateKey(), chain), new KeyStore.PasswordProtection(keyPassword));
        store.setEntry("secret", new KeyStore.SecretKeyEntry(secret), new KeyStore.PasswordProtection(secretKeyPassword));

        return store;
    }
    
    public static void main(
        String[]    args)
        throws Exception
    {
        KeyStore store = createKeyStore();
        char[]   password = "storePassword".toCharArray();
        
        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
        
        // save the store
        store.store(bOut, password);
        
        // reload from scratch
        store = KeyStore.getInstance("JCEKS");
        
        store.load(new ByteArrayInputStream(bOut.toByteArray()), password);

        Enumeration en = store.aliases();
        while (en.hasMoreElements())
        {
            String alias = (String)en.nextElement();
            System.out.println("found " + alias + ", isCertificate? " + store.isCertificateEntry(alias) + ", secret key entry? " + store.entryInstanceOf(alias, KeyStore.SecretKeyEntry.class));
        }
    }
}
