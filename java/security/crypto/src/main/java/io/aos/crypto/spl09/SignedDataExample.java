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
package io.aos.crypto.spl09;

import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.*;
import java.util.Arrays;

import org.bouncycastle.cms.CMSProcessable;
import org.bouncycastle.cms.CMSProcessableByteArray;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataGenerator;

/**
 * Example of generating a detached signature.
 */
public class SignedDataExample
    extends SignedDataProcessor
{
    public static void main(String... args)
        throws Exception
    {
        KeyStore        credentials = Utils.createCredentials();
        PrivateKey      key = (PrivateKey)credentials.getKey(Utils.END_ENTITY_ALIAS, Utils.KEY_PASSWD);
        Certificate[]   chain = credentials.getCertificateChain(Utils.END_ENTITY_ALIAS);
        CertStore       certsAndCRLs = CertStore.getInstance("Collection",
                            new CollectionCertStoreParameters(Arrays.asList(chain)), "BC");
        X509Certificate cert = (X509Certificate)chain[0];

        // set up the generator
        CMSSignedDataGenerator gen = new CMSSignedDataGenerator();

        gen.addSigner(key, cert, CMSSignedDataGenerator.DIGEST_SHA224);

        gen.addCertificatesAndCRLs(certsAndCRLs);
        
        // create the signed-data object
        CMSProcessable  data = new CMSProcessableByteArray("Hello World!".getBytes());

        CMSSignedData signed = gen.generate(data, "BC");
        
        // recreate
        signed = new CMSSignedData(data, signed.getEncoded());
        
        // verification step
        X509Certificate rootCert = (X509Certificate)credentials.getCertificate(Utils.ROOT_ALIAS);

        if (isValid(signed, rootCert))
        {
            System.out.println("verification succeeded");
        }
        else
        {
            System.out.println("verification failed");
        }
    }
}
