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
import java.security.cert.CertStore;
import java.security.cert.Certificate;
import java.security.cert.CollectionCertStoreParameters;
import java.security.cert.X509Certificate;
import java.util.Arrays;

import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.bouncycastle.cms.KEKRecipientId;
import org.bouncycastle.cms.RecipientId;
import org.bouncycastle.cms.RecipientInformation;
import org.bouncycastle.cms.RecipientInformationStore;
import org.bouncycastle.mail.smime.SMIMEEnveloped;
import org.bouncycastle.mail.smime.SMIMEEnvelopedGenerator;
import org.bouncycastle.mail.smime.SMIMESigned;
import org.bouncycastle.mail.smime.SMIMEUtil;

/**
 * a simple example that creates and processes an enveloped signed mail message.
 */
public class EnvelopedSignedMailExample
    extends SignedDataProcessor
{
    public static void main(
        String[] args)
        throws Exception
    {
        KeyStore        credentials = Utils.createCredentials();
        PrivateKey      key = (PrivateKey)credentials.getKey(Utils.END_ENTITY_ALIAS, Utils.KEY_PASSWD);
        Certificate[]   chain = credentials.getCertificateChain(Utils.END_ENTITY_ALIAS);
        CertStore       certsAndCRLs = CertStore.getInstance("Collection",
                            new CollectionCertStoreParameters(Arrays.asList(chain)), "BC");
        X509Certificate cert = (X509Certificate)chain[0];

        // create the message we want signed
        MimeBodyPart    dataPart = new MimeBodyPart();

        dataPart.setText("Hello world!");
        
        // create the signed message
        MimeMultipart signedMultipart = SignedMailExample.createMultipartWithSignature(key, cert, certsAndCRLs, dataPart);

        // create the body part containing the signed message
        MimeBodyPart signedPart = new MimeBodyPart();

        signedPart.setContent(signedMultipart);
        
        // set up the enveloped message generator
        SMIMEEnvelopedGenerator  gen = new SMIMEEnvelopedGenerator();
          
        gen.addKeyTransRecipient(cert);

        // generate the enveloped message
        MimeBodyPart envPart = gen.generate(signedPart, SMIMEEnvelopedGenerator.AES256_CBC, "BC");

        // create the mail message
        MimeMessage mail = Utils.createMimeMessage("example signed and enveloped message", envPart.getContent(), envPart.getContentType());

        // create the enveloped object from the mail message
        SMIMEEnveloped     enveloped = new SMIMEEnveloped(mail);
        
        // look for our recipient identifier
        RecipientId        recId = new KEKRecipientId(null);

        recId.setSerialNumber(cert.getSerialNumber());
        recId.setIssuer(cert.getIssuerX500Principal().getEncoded());

        RecipientInformationStore   recipients = enveloped.getRecipientInfos();
        RecipientInformation        recipient = recipients.get(recId);

        // decryption step
        MimeBodyPart        res = SMIMEUtil.toMimeBodyPart(recipient.getContent(key, "BC"));

        // extract the multi-part from the body part.
        if (res.getContent() instanceof MimeMultipart)
        {
            SMIMESigned     signed = new SMIMESigned(
                                            (MimeMultipart)res.getContent());

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
            
            // content display step
            MimeBodyPart            content = signed.getContent();

            System.out.print("Content: ");
            System.out.println(content.getContent());
        }
        else
        {
            System.out.println("wrong content found");
        }
    }
}
