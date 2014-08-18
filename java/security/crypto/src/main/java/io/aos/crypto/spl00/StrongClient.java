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
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Date;

public class StrongClient {
  public void sendAuthentication(String user, PrivateKey key,
      OutputStream outStream) throws IOException, NoSuchAlgorithmException,
      InvalidKeyException, SignatureException {
    DataOutputStream out = new DataOutputStream(outStream);
    long t = (new Date()).getTime();
    double q = Math.random();

    Signature s = Signature.getInstance("DSA");
    s.initSign(key);
    s.update(Protection.makeBytes(t, q));
    byte[] signature = s.sign();

    out.writeUTF(user);
    out.writeLong(t);
    out.writeDouble(q);
    out.writeInt(signature.length);
    out.write(signature);
    out.flush();
  }

  public static void main(String... args) throws Exception {
    if (args.length != 5) {
      System.out.println(
          "Usage: StrongClient host keystore storepass alias keypass");
      return;
    }
    
    String host = args[0];
    String keystorefile = args[1];
    String storepass = args[2];
    String alias = args[3];
    String keypass = args[4];
    
    int port = 7999;
    Socket s = new Socket(host, port);

    StrongClient client = new StrongClient();
    KeyStore keystore = KeyStore.getInstance(null);
    keystore.load(new FileInputStream(keystorefile), storepass.toCharArray());
    Key key = keystore.getKey(alias, keypass.toCharArray());
    client.sendAuthentication(alias, (PrivateKey) key, s.getOutputStream());

    s.close();
  }
}
