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
package io.aos.io.c22;
import javax.comm.*;

public class PortCloser extends Thread {

  CommPort thePort; 
  Thread join1;
  Thread join2;

  public PortCloser(CommPort port, Thread t1, Thread t2) {
    this.thePort = port;
    join1 = t1;
    join2 = t2;
  }

  public void run() {
  
    try {
      join1.join();
      join2.join();
      // execution stops until both these thread finish
    }
    catch (InterruptedException e) {
    }
   
  
    try {
      thePort.close();  
    }
    catch (Exception e) {
      // the port's probably already been closed
    }
    
  }

}
