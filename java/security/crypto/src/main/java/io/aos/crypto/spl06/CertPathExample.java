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
 
import java.io.ByteArrayInputStream;
import java.security.cert.CertPath;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Arrays;

/**
 * Basic example of creating and encoding a CertPath.
 */
public class CertPathExample
{
	public static void main(
		String[] args)
	    throws Exception
	{
        X509Certificate[]   chain = PKCS10CertCreateExample.buildChain();
		
        // create the factory and path object
        CertificateFactory  fact = CertificateFactory.getInstance("X.509", "BC");
        CertPath            certPath = fact.generateCertPath(Arrays.asList(chain));

        byte[] encoded = certPath.getEncoded("PEM");

        System.out.println(Utils.toString(encoded));
		
        // re-read the CertPath
        CertPath           newCertPath = fact.generateCertPath(new ByteArrayInputStream(encoded), "PEM");

        if (newCertPath.equals(certPath))
        {
            System.out.println("CertPath recovered correctly");
        }
    }
}
