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
package io.aos.crypto.spl10;

import java.io.InputStream;
import java.net.URL;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.security.auth.x500.X500Principal;

/**
 * SSL Client with client side authentication.
 */
public class HTTPSClientExample
    extends SSLClientWithClientAuthTrustExample
{
    /**
     * Verifier to check host has identified itself using "Test CA Certificate".
     */
    private static class Validator
        implements HostnameVerifier
    {
        public boolean verify(String hostName, SSLSession session)
        {
            try
            {
                X500Principal hostID = (X500Principal)session.getPeerPrincipal();
                
                return hostID.getName().equals("CN=Test CA Certificate");
            }
            catch (Exception e)
            {
                return false;
            }
        }
    }
    
    public static void main(
        String[] args)
        throws Exception
    {
		SSLContext       sslContext = createSSLContext();
		SSLSocketFactory fact = sslContext.getSocketFactory();
		
		// specify the URL and connection attributes
		URL url = new URL("https://"+ Utils.HOST + ":" + Utils.PORT_NO);
		
		HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();
		
		connection.setSSLSocketFactory(fact);
		connection.setHostnameVerifier(new Validator());
		
		connection.connect();
		
		// read the response
		InputStream  in = connection.getInputStream();
        
        int ch = 0;
        while ((ch = in.read()) >= 0)
        {
            System.out.print((char)ch);
        }
    }
}
