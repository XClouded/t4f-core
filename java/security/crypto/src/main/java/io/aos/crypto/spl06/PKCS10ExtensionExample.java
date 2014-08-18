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

import java.io.OutputStreamWriter;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.Vector;

import javax.security.auth.x500.X500Principal;

import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSet;
import org.bouncycastle.asn1.pkcs.Attribute;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.GeneralNames;
import org.bouncycastle.asn1.x509.X509Extension;
import org.bouncycastle.asn1.x509.X509Extensions;
import org.bouncycastle.jce.PKCS10CertificationRequest;
import org.bouncycastle.openssl.PEMWriter;

/**
 * Generation of a basic PKCS #10 request with an extension.
 */
public class PKCS10ExtensionExample
{
    public static PKCS10CertificationRequest generateRequest(
        KeyPair pair)
        throws Exception
    {
        // create a SubjectAlternativeName extension value
        GeneralNames  subjectAltNames = new GeneralNames(
                 new GeneralName(GeneralName.rfc822Name, "test@test.test"));

        // create the extensions object and add it as an attribute
        Vector  oids = new Vector();
        Vector	values = new Vector();

        oids.add(X509Extensions.SubjectAlternativeName);
        values.add(new X509Extension(false, new DEROctetString(subjectAltNames)));
        
        X509Extensions	extensions = new X509Extensions(oids, values);
        
        Attribute  attribute = new Attribute(
                                 PKCSObjectIdentifiers.pkcs_9_at_extensionRequest, 
                                 new DERSet(extensions));
        
        return new PKCS10CertificationRequest(
                "SHA256withRSA",
                new X500Principal("CN=Requested Test Certificate"),
                pair.getPublic(),
                new DERSet(attribute),
                pair.getPrivate());
    }
    
    public static void main(
        String[]    args)
        throws Exception
    {
        // create the keys
        KeyPairGenerator kpGen = KeyPairGenerator.getInstance("RSA", "BC");
        
        kpGen.initialize(1024, Utils.createFixedRandom());
        
        KeyPair          pair = kpGen.generateKeyPair();
        
        PKCS10CertificationRequest  request = generateRequest(pair);
        
        PEMWriter        pemWrt = new PEMWriter(new OutputStreamWriter(System.out));
        
        pemWrt.writeObject(request);
        
        pemWrt.close();
    }
}
