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


import io.aos.crypto.spl09.Utils;

import java.io.*;
import java.math.BigInteger;
import java.security.cert.*;
import java.util.*;

import javax.security.auth.x500.X500PrivateCredential;

import org.bouncycastle.asn1.x509.*;
import org.bouncycastle.jce.PKCS10CertificationRequest;
import org.bouncycastle.openssl.PEMReader;
import org.bouncycastle.x509.X509V3CertificateGenerator;
import org.bouncycastle.x509.extension.*;


/**
 * Example showing the processing of a PEM encoded PKCS #10 encoded request 
 * in a file called "pkcs10.req". A PKCS7 certificate path is generated as a
 * response in the file "pkcs7.pth". 
 * <p>
 * The certificate and its chain will be valid for 50 seconds.
 */
public class CertReqSolution
{
    public static void main(String... args)
        throws Exception
    {
        // create the CA certificates
        X500PrivateCredential rootCredential = Utils.createRootCredential();
        X500PrivateCredential interCredential = Utils.createIntermediateCredential(
                rootCredential.getPrivateKey(), rootCredential.getCertificate());
        
        // parse the request
        PEMReader   pRd = new PEMReader(
                                 new InputStreamReader(
                                         new FileInputStream("pkcs10.req")));
        
        PKCS10CertificationRequest request = (PKCS10CertificationRequest)pRd.readObject();

        // get our validation certificate
        X509Certificate caCert = interCredential.getCertificate();
        
        X509V3CertificateGenerator  certGen = new X509V3CertificateGenerator();

        certGen.setSerialNumber(BigInteger.valueOf(System.currentTimeMillis()));
        certGen.setIssuerDN(caCert.getSubjectX500Principal());
        certGen.setNotBefore(new Date(System.currentTimeMillis()));
        certGen.setNotAfter(new Date(System.currentTimeMillis() + 50000));
        certGen.setSubjectDN(request.getCertificationRequestInfo().getSubject());
        certGen.setPublicKey(request.getPublicKey("BC"));
        certGen.setSignatureAlgorithm("SHA256WithRSAEncryption");
        
        // provide some basic extensions and mark the certificate as appropriate for signing and encipherment
        certGen.addExtension(X509Extensions.AuthorityKeyIdentifier, false, new AuthorityKeyIdentifierStructure(caCert));
        
        certGen.addExtension(X509Extensions.SubjectKeyIdentifier, false, new SubjectKeyIdentifierStructure(request.getPublicKey("BC")));
        
        certGen.addExtension(X509Extensions.BasicConstraints, true, new BasicConstraints(false));
        
        certGen.addExtension(X509Extensions.KeyUsage, true, new KeyUsage(KeyUsage.digitalSignature | KeyUsage.keyEncipherment));
        
        // create the chain
        List chain = Arrays.asList(
                new Certificate[] { 
                        certGen.generateX509Certificate(interCredential.getPrivateKey(), "BC"), 
                        interCredential.getCertificate(), 
                        rootCredential.getCertificate() });
        
        // create the CertPath
        CertificateFactory fact = CertificateFactory.getInstance("X.509", "BC");
        
        CertPath path = fact.generateCertPath(chain);
        
        // write it out
        FileOutputStream fOut = new FileOutputStream("pkcs7.pth");
        
        fOut.write(path.getEncoded("PKCS7"));
        
        fOut.close();
    }
}
