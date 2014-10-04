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
package io.aos.protocol.mail;
import io.aos.protocol.mail.MailAuthenticator;

import javax.mail.*;
import javax.mail.internet.*;

import java.util.*;
import java.io.*;


public class Pop3SecureClient {

  public static void main(String... args) {
    
    Properties props = new Properties(); 

    String host = "utopia.poly.edu";
    String provider = "pop3";

    try {

      // Connect to the POP3 server
      Session session = Session.getDefaultInstance(props, 
       new MailAuthenticator());
      Store store = session.getStore(provider);
      store.connect(host, null, null);
      
      // Open the folder
      Folder inbox = store.getFolder("INBOX");
      if (inbox == null) {
        System.out.println("No INBOX");
        System.exit(1);
      }  
      inbox.open(Folder.READ_ONLY);
      
      // Get the messages from the server
      Message[] messages = inbox.getMessages();
      for (int i = 0; i < messages.length; i++) {
        System.out.println("------------ Message " + (i+1) 
         + " ------------");
        messages[i].writeTo(System.out);
      } 

      // Close the connection 
      // but don't remove the messages from the server
      inbox.close(false);
      store.close();
      
    } 
    catch (Exception e) {
      e.printStackTrace();
    } 
    
    // since we brought up a GUI returning from main() won't exit
    System.exit(0);   
    
  }

}
