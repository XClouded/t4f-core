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
package io.aos.crypto.spl07;

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.cert.CertStore;
import java.security.cert.CollectionCertStoreParameters;
import java.security.cert.X509CRL;
import java.security.cert.X509CRLEntry;
import java.security.cert.X509CRLSelector;
import java.security.cert.X509Certificate;
import java.util.*;

/**
 * Using the X509CRLSelector and the CertStore classes.
 */
public class CRLCertStoreExample
{
    public static void main(String... args)
        throws Exception
    {
        // create CA keys and certificate
        KeyPair              caPair = Utils.generateRSAKeyPair();
        X509Certificate      caCert = Utils.generateRootCert(caPair);
        BigInteger           revokedSerialNumber = BigInteger.valueOf(2);
        
        // create a CRL revoking certificate number 2
        X509CRL	             crl = X509CRLExample.createCRL(caCert, caPair.getPrivate(), revokedSerialNumber);
        
        // place the CRL into a CertStore
        CollectionCertStoreParameters params = new CollectionCertStoreParameters(Collections.singleton(crl));
        CertStore                     store = CertStore.getInstance("Collection", params, "BC");
        X509CRLSelector               selector = new X509CRLSelector();
        
        selector.addIssuerName(caCert.getSubjectX500Principal().getEncoded());
        
        Iterator                      it = store.getCRLs(selector).iterator();
        
        while (it.hasNext())
        {
            crl = (X509CRL)it.next();
            
            // verify the CRL
            crl.verify(caCert.getPublicKey(), "BC");
	        
            // check if the CRL revokes certificate number 2
            X509CRLEntry entry = crl.getRevokedCertificate(revokedSerialNumber);
            System.out.println("Revocation Details:");
            System.out.println("  Certificate number: " + entry.getSerialNumber());
            System.out.println("  Issuer            : " + crl.getIssuerX500Principal());
        }
    }
}
