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
package io.aos.crypto.spl05;

import java.security.AlgorithmParameters;

import javax.crypto.spec.IvParameterSpec;

import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.util.ASN1Dump;

/**
 * Example showing IV encoding
 */
public class IVExample
{

    public static void main(String... args)
        throws Exception
    {
        // set up the parameters object
        AlgorithmParameters	params = AlgorithmParameters.getInstance("AES", "BC");
        IvParameterSpec		ivSpec = new IvParameterSpec(new byte[16]);
        
        params.init(ivSpec);
        
        // look at the ASN.1 encodng.
        ASN1InputStream     aIn = new ASN1InputStream(params.getEncoded("ASN.1"));
        
        System.out.println(ASN1Dump.dumpAsString(aIn.readObject()));
    }
}
