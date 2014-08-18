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
package io.aos.crypto.crypto;

import java.math.BigInteger;
import java.security.*;

public class ElGamalKeyPairGenerator
    extends KeyPairGeneratorSpi {
  private int mStrength = 0;
  private SecureRandom mSecureRandom = null;
  
  // Strength is interpreted as the bit length of p.
  public void initialize(int strength, SecureRandom random) {
    mStrength = strength;
    mSecureRandom = random;
  }
  
  public KeyPair generateKeyPair() {
    if (mSecureRandom == null) {
      mStrength = 1024;
      mSecureRandom = new SecureRandom();
    }
    BigInteger p = new BigInteger(mStrength, 16, mSecureRandom);
    BigInteger g = new BigInteger(mStrength - 1, mSecureRandom);
    BigInteger x = new BigInteger(mStrength - 1, mSecureRandom);
    BigInteger y = g.modPow(x, p);
    
    ElGamalPublicKey publicKey = new ElGamalPublicKey(y, g, p);
    ElGamalPrivateKey privateKey = new ElGamalPrivateKey(x, g, p);
    return new KeyPair(publicKey, privateKey);
  }
}
