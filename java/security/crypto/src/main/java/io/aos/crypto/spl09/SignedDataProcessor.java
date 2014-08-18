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

import java.security.cert.*;
import java.util.Iterator;

import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.cms.SignerInformationStore;

/**
 * Base class for signed examples.
 */
public class SignedDataProcessor
{
    /**
     * Return a boolean array representing keyUsage with digitalSignature set.
     */
    static boolean[] getKeyUsageForSignature()
    {
        boolean[] val = new boolean[9];

        val[0] = true;

        return val;
    }
    
    /**
     * Take a CMS SignedData message and a trust anchor and determine if
     * the message is signed with a valid signature from a end entity
     * entity certificate recognized by the trust anchor rootCert.
     */
    public static boolean isValid(
        CMSSignedData   signedData,
        X509Certificate rootCert)
        throws Exception
    {
        CertStore certsAndCRLs = signedData.getCertificatesAndCRLs("Collection", "BC");
        SignerInformationStore  signers = signedData.getSignerInfos();
        Iterator                it = signers.getSigners().iterator();

        if (it.hasNext())
        {
            SignerInformation         signer = (SignerInformation)it.next();
            X509CertSelector          signerConstraints = signer.getSID();
            
            signerConstraints.setKeyUsage(getKeyUsageForSignature());
            
            PKIXCertPathBuilderResult result = Utils.buildPath(rootCert, signer.getSID(), certsAndCRLs);

            return signer.verify(result.getPublicKey(), "BC");
        }
        
        return false;
    }
}
