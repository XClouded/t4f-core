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
package io.aos.crypto.spl06;

import java.security.cert.CertStore;
import java.security.cert.CollectionCertStoreParameters;
import java.security.cert.X509CertSelector;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Iterator;

import javax.security.auth.x500.X500Principal;

/**
 * Example using a CertStore and a CertSelector
 */
public class CertStoreExample
{
    public static void main(String... args)
        throws Exception
    {
        X509Certificate[]   chain = PKCS10CertCreateExample.buildChain();
        
        // create the store
        CollectionCertStoreParameters params = new CollectionCertStoreParameters(Arrays.asList(chain));
        CertStore store = CertStore.getInstance("Collection", params, "BC");
        
        // create the selector
        X509CertSelector selector = new X509CertSelector();
        selector.setSubject(new X500Principal("CN=Requested Test Certificate").getEncoded());

        // print the subjects of the results
        Iterator certsIt = store.getCertificates(selector).iterator();
        while (certsIt.hasNext()) 
        {
            X509Certificate cert = (X509Certificate)certsIt.next();
            
            System.out.println(cert.getSubjectX500Principal());
        }
    }
}
