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
import java.net.*;
import java.math.BigInteger;


public class BioTcpFibonacciClient {

  public static void main(String... args) {
        
    if (args.length == 0 || !args[0].startsWith("rmi:")) {
      System.err.println(
"Usage: java Fibonacci client rmi://host.domain:port/fibonacci number");
      return;   
    }
            
    try {      
      Object o = Naming.lookup(args[0]);
      BioTcpFibonacci calculator = (BioTcpFibonacci) o;
      for (int i = 1; i < args.length; i++) {
        try {
          BigInteger index = new BigInteger(args[i]);
          BigInteger f = calculator.getFibonacci(index);
          System.out.println("The " + args[i] + "th Fibonacci number is " 
           + f);
        }
        catch (NumberFormatException e) {
          System.err.println(args[i] + "is not an integer.");
        }
      } 
    }
    catch (MalformedURLException e) {
      System.err.println(args[0] + " is not a valid RMI URL");
    }
    catch (RemoteException e) {
      System.err.println("Remote object threw exception " + e);
    }
    catch (NotBoundException e) {
      System.err.println(
       "Could not find the requested remote object on the server");
    } 

  }

}
