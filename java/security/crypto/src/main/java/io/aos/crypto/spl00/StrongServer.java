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
package io.aos.crypto.spl00;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;

public class StrongServer {
  protected KeyStore mKeyStore;
  
  public StrongServer(KeyStore keystore) { mKeyStore = keystore; }
  
  public boolean authenticate(InputStream inStream)
      throws IOException, NoSuchAlgorithmException,
      InvalidKeyException, SignatureException, KeyStoreException {
    DataInputStream in = new DataInputStream(inStream);

    String user = in.readUTF();
    long t = in.readLong();
    double q = in.readDouble();
    int length = in.readInt();
    byte[] signature = new byte[length];
    in.readFully(signature);

    Signature s = Signature.getInstance("DSA");
    s.initVerify(mKeyStore.getCertificate(user).getPublicKey());
    s.update(Protection.makeBytes(t, q));
    return s.verify(signature);
  }

  public static void main(String... args) throws Exception {
    if (args.length != 2) {
      System.out.println("Usage: StrongServer keystore storepass");
      return;
    }
    
    String keystorefile = args[0];
    String storepass = args[1];
    
    int port = 7999;
    ServerSocket s = new ServerSocket(port);
    Socket client = s.accept();

    KeyStore keystore = KeyStore.getInstance(null);
    keystore.load(new FileInputStream(keystorefile), storepass.toCharArray());
    StrongServer server = new StrongServer(keystore);
    if (server.authenticate(client.getInputStream()))
      System.out.println("Client logged in.");
    else
      System.out.println("Client failed to log in.");

    s.close();
  }
}
