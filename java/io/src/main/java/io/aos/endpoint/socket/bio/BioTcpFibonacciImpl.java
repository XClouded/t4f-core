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
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.math.BigInteger;


public class BioTcpFibonacciImpl extends UnicastRemoteObject implements BioTcpFibonacci {

  public BioTcpFibonacciImpl() throws RemoteException {
    UnicastRemoteObject.exportObject(this);
  //  setLog(System.out);
  }
    
  public BigInteger getFibonacci(int n) throws RemoteException {
    
    return this.getFibonacci(new BigInteger(Long.toString(n)));  
    
  }
  
  public BigInteger getFibonacci(BigInteger n) throws RemoteException {  
    
    System.out.println("Calculating the " + n + "th Fibonacci number");
    BigInteger zero = new BigInteger("0");
    BigInteger one  = new BigInteger("1");
    
    if (n.equals(zero)) return zero;
    if (n.equals(one)) return one; 

    BigInteger i  = one;
    BigInteger a  = zero;
    BigInteger b  = one;

    while (i.compareTo(n) == -1) {
      BigInteger temp = b;
      b = b.add(a);
      a = temp; 
      i = i.add(one);
    }

    return b;

  }

}
