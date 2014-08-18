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

import java.util.Arrays;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.bouncycastle.cms.CMSEnvelopedData;
import org.bouncycastle.cms.CMSEnvelopedDataGenerator;
import org.bouncycastle.cms.CMSProcessable;
import org.bouncycastle.cms.CMSProcessableByteArray;
import org.bouncycastle.cms.KEKRecipientId;
import org.bouncycastle.cms.RecipientId;
import org.bouncycastle.cms.RecipientInformation;
import org.bouncycastle.cms.RecipientInformationStore;

/**
 * Demonstrate creation and processing a key-encrypted key enveloped-message.
 */
public class KEKEnvelopedDataExample
{
    public static void main(String... args)
        throws Exception
    {
        KeyGenerator    keyGen = KeyGenerator.getInstance("DESEDE", "BC");
        SecretKey       key  = keyGen.generateKey();
        
        // set up the generator
        CMSEnvelopedDataGenerator edGen = new CMSEnvelopedDataGenerator();

        byte[]  kekID = new byte[] { 1, 2, 3, 4, 5 };

        edGen.addKEKRecipient(key, kekID);
        
        // create the enveloped-data object
        CMSProcessable  data = new CMSProcessableByteArray("Hello World!".getBytes());

        CMSEnvelopedData enveloped = edGen.generate(
                                data,
                                CMSEnvelopedDataGenerator.AES128_CBC, "BC");
        // recreate
        enveloped = new CMSEnvelopedData(enveloped.getEncoded());

        // look for our recipient
        RecipientId     recId = new KEKRecipientId(kekID);

        RecipientInformationStore   recipients = enveloped.getRecipientInfos();
        RecipientInformation        recipient = recipients.get(recId);
        
        if (recipient != null)
        {
            // decrypt the data
            byte[] recData = recipient.getContent(key, "BC");

            // compare recovered data to the original data
            if (Arrays.equals((byte[])data.getContent(), recData))
            {
                System.out.println("data recovery succeeded");
            }
            else
            {
                System.out.println("data recovery failed");
            }
        }
        else
        {
            System.out.println("could not find a matching recipient");
        }
    }
}
