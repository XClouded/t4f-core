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
package io.aos.crypto.spl10;

import java.io.FileOutputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;

import javax.security.auth.x500.X500PrivateCredential;

/**
 * Create the various credentials for an SSL session
 */
public class CreateKeyStores
{
    public static void main(String... args)
        throws Exception
    {
        X500PrivateCredential    rootCredential = Utils.createRootCredential();
        X500PrivateCredential    interCredential = Utils.createIntermediateCredential(rootCredential.getPrivateKey(), rootCredential.getCertificate());
        X500PrivateCredential    endCredential = Utils.createEndEntityCredential(interCredential.getPrivateKey(), interCredential.getCertificate());
        
        // client credentials
        KeyStore keyStore = KeyStore.getInstance("PKCS12", "BC");
        
        keyStore.load(null, null);
        
        keyStore.setKeyEntry(Utils.CLIENT_NAME, endCredential.getPrivateKey(), Utils.CLIENT_PASSWORD, 
                new Certificate[] { endCredential.getCertificate(), interCredential.getCertificate(), rootCredential.getCertificate() });
        
        keyStore.store(new FileOutputStream(Utils.CLIENT_NAME + ".p12"), Utils.CLIENT_PASSWORD);
        
        // trust store for client
        keyStore = KeyStore.getInstance("JKS");
        
        keyStore.load(null, null);
        
        keyStore.setCertificateEntry(Utils.SERVER_NAME, rootCredential.getCertificate());
        
        keyStore.store(new FileOutputStream(Utils.TRUST_STORE_NAME + ".jks"), Utils.TRUST_STORE_PASSWORD);
        
        // server credentials
        keyStore = KeyStore.getInstance("JKS");
        
        keyStore.load(null, null);
        
        keyStore.setKeyEntry(Utils.SERVER_NAME, rootCredential.getPrivateKey(), Utils.SERVER_PASSWORD,
                new Certificate[] { rootCredential.getCertificate() });
        
        keyStore.store(new FileOutputStream(Utils.SERVER_NAME + ".jks"), Utils.SERVER_PASSWORD);
    }
}
