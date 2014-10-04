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
package io.aos.concurrent.count;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/**
 * Get a document from a web server using HTTPS. Usage:
 *   java HttpsDownload <hostname> <filename>
 **/
public class HttpsDownload {
    public static void main(String... args) throws IOException {
        // Get a SocketFactory object for creating SSL sockets
        SSLSocketFactory factory =
            (SSLSocketFactory) SSLSocketFactory.getDefault();

        // Use the factory to create a secure socket connected to the 
        // HTTPS port of the specified web server.
        SSLSocket sslsock=(SSLSocket)factory.createSocket(args[0], // Hostname
                                                            443); // HTTPS port

        // Get the certificate presented by the web server
        SSLSession session = sslsock.getSession();
        X509Certificate cert;   
        try { cert = (X509Certificate)session.getPeerCertificates()[0]; }
        catch(SSLPeerUnverifiedException e) { // If no or invalid certificate
            System.err.println(session.getPeerHost() +
                               " did not present a valid certificate.");
            return;
        }

        // Display details about the certificate
        System.out.println(session.getPeerHost() + 
                           " has presented a certificate belonging to:");
        System.out.println("\t[" + cert.getSubjectDN().getName() + "]");
        System.out.println("The certificate bears the valid signature of:");
        System.out.println("\t[" + cert.getIssuerDN().getName() + "]");

        // If the user does not trust the certificate, abort
        System.out.print("Do you trust this certificate (y/n)? ");
        System.out.flush();
        BufferedReader console =
            new BufferedReader(new InputStreamReader(System.in));
        if (Character.toLowerCase(console.readLine().charAt(0)) != 'y') return;

        // Now use the secure socket just as you would use a regular socket
        // First, send a regular HTTP request over the SSL socket
        PrintWriter out = new PrintWriter(sslsock.getOutputStream());

        out.print("GET " + args[1] + " HTTP/1.0\r\n\r\n");
        out.flush();

        // Next, read the server's response and print it to the console
        BufferedReader in = 
         new BufferedReader(new InputStreamReader(sslsock.getInputStream()));
        String line;
        while((line = in.readLine()) != null) System.out.println(line);
        
        // Finally, close the socket
        sslsock.close(); 
    }
}
