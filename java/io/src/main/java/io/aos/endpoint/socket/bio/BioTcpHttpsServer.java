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
package io.aos.endpoint.socket.bio;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
 
public class BioTcpHttpsServer {
 
  public final static int DEFAULT_PORT = 7000;
  public final static String algorithm = "SSLv3";

  public static void main(String... args) {

    int port = DEFAULT_PORT;     
    if (args.length > 0) {
      try {
        port = Integer.parseInt(args[0]);
        if (port < 0 || port >= 65536) {
          System.out.println("Port must between 0 and 65535");
          return;      
        }
      }   
      catch (NumberFormatException e) {}  
    }     

    try {
      
      SSLContext context = SSLContext.getInstance("SSL");
      
      // The reference implementation only supports X.509 keys
      KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
      
      // Sun's default kind of key store
      KeyStore ks = KeyStore.getInstance("JKS");

      // For security, every key store is encrypted with a 
      // pass phrase that must be provided before we can load 
      // it from disk. The pass phrase is stored as a char[] array
      // so it can be wiped from memory quickly rather than
      // waiting for a garbage collector. Of course using a string
      // literal here completely defeats that purpose.
      char[] password = "2andnotafnord".toCharArray();
      ks.load(new FileInputStream("jnp2e19.keys"), password);
      kmf.init(ks, password);
      
      //  
      context.init(kmf.getKeyManagers(), null, null);

      SSLServerSocketFactory factory 
       = context.getServerSocketFactory();
     
      SSLServerSocket server 
       = (SSLServerSocket) factory.createServerSocket(port);
     
      String[] supported = server.getSupportedCipherSuites();
      String[] anonCipherSuitesSupported = new String[supported.length];      
      int numAnonCipherSuitesSupported = 0;
      for (int i = 0; i < supported.length; i++) {
        if (supported[i].indexOf("_anon_") > 0) {
          anonCipherSuitesSupported[numAnonCipherSuitesSupported++] = supported[i];
        }
      }  
      
      String[] oldEnabled = server.getEnabledCipherSuites();
      String[] newEnabled = new String[oldEnabled.length 
       + numAnonCipherSuitesSupported];
      System.arraycopy(oldEnabled, 0, newEnabled, 0, oldEnabled.length);
      System.arraycopy(anonCipherSuitesSupported, 0, newEnabled, 
       oldEnabled.length, numAnonCipherSuitesSupported);
      
      server.setEnabledCipherSuites(newEnabled);     
      // Now all the set up is complete and we can focus 
      // on the actual communication. 
      try {
        while (true) {
          // This socket will be secure,
          // but there's no indication of that inputStreamthe code!
          Socket theConnection = server.accept();
          InputStream inputStream = theConnection.getInputStream();
          int c;
          while ((c = inputStream.read()) != -1) {
            System.out.write(c);
          } 
          theConnection.close();
        }  // end while
      } // end try
      catch (IOException e) {
        System.err.println(e);
      } // end catch
      
   }  // end try
   catch (IOException e) {
     e.printStackTrace();
   } // end catch
   catch (KeyManagementException e) {
     e.printStackTrace();
   } // end catch
   catch (KeyStoreException e) {
     e.printStackTrace();
   } // end catch
   catch (NoSuchAlgorithmException e) {
     e.printStackTrace();
   } // end catch
   catch (java.security.cert.CertificateException e) {
     e.printStackTrace();
   } // end catch
   catch (UnrecoverableKeyException e) {
     e.printStackTrace();
   } // end catch

  }

} // end server
