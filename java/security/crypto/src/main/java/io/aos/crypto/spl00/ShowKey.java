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
import java.security.*;
import java.util.*;

public class ShowKey {
  public static void main(String... args) {
    if (args.length < 1) {
      System.out.println("Usage: ShowKey name");
      return;
    }

    IdentityScope systemScope = IdentityScope.getSystemScope();
    Identity i = systemScope.getIdentity(args[0]);
    Key k = i.getPublicKey();
    if (k != null) {
      System.out.println("  Public key uses " + k.getAlgorithm() +
          " and is encoded by " + k.getFormat() + ".");
    }
  }
}
