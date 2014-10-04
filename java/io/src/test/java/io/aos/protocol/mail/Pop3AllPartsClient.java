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


public class Pop3AllPartsClient {

  public static void main(String... args) {
    
    if (args.length == 0) {
      System.err.println(
       "Usage: java AllPartsClient protocol://username@host:port/foldername");
      return; 
    }
    
    URLName server = new URLName(args[0]);

    try {

      Session session = Session.getDefaultInstance(new Properties(), 
       new MailAuthenticator(server.getUsername()));

      // Connect to the server and open the folder
      Folder folder = session.getFolder(server);
      if (folder == null) {
        System.out.println("Folder " + server.getFile() + " not found.");
        System.exit(1);
      }  
      folder.open(Folder.READ_ONLY);
      
      // Get the messages from the server
      Message[] messages = folder.getMessages();
      for (int i = 0; i < messages.length; i++) {
        System.out.println("------------ Message " + (i+1) 
         + " ------------");
         
        // Print message headers
        Enumeration headers = messages[i].getAllHeaders();
        while (headers.hasMoreElements()) {
          Header h = (Header) headers.nextElement();
          System.out.println(h.getName() + ": " + h.getValue());
        }       
        System.out.println();
        
        // Enumerate parts
        Object body = messages[i].getContent();
        if (body instanceof Multipart) {
          processMultipart((Multipart) body);          
        }
        else { // ordinary message
          processPart(messages[i]);
        }
        
        System.out.println();
        
      } 

      // Close the connection 
      // but don't remove the messages from the server
      folder.close(false);
      
    } 
    catch (Exception e) {
      e.printStackTrace();
    }  
          
    // Since we may have brought up a GUI to authenticate,
    // we can't rely on returning from main() to exit
    System.exit(0);     
    
  }
  
  
  public static void processMultipart(Multipart mp) 
   throws MessagingException {

    for (int i = 0; i < mp.getCount(); i++) {
      processPart(mp.getBodyPart(i));
    }
    
  }

  public static void processPart(Part p) {
    
    try {
      String fileName = p.getFileName();
      String disposition = p.getDisposition();
      String contentType = p.getContentType();
      if (fileName == null && (disposition.equals(Part.ATTACHMENT) 
       || !contentType.equals("text/plain"))) {
        // pick a random file name. This requires Java 1.2 or later.
        fileName = File.createTempFile("attachment", ".txt").getName();
      }
      if (fileName == null) { // likely inline
        p.writeTo(System.out);
      }
      else {
        File f = new File(fileName);
        // find a version that does not yet exist
        for (int i = 1; f.exists(); i++) {
          String newName = fileName + " " + i;
          f = new File(newName);
        }
        FileOutputStream outputStream = new FileOutputStream(f);
        
        // We can't just use p.writeTo() here because it doesn't
        // decode the attachment. Instead we copy the input stream 
        // onto the output stream which does automatically decode
        // Base-64, quoted printable, and a variety of other formats.
        InputStream inputStream = new BufferedInputStream(p.getInputStream());
        int b;
        while ((b = inputStream.read()) != -1) outputStream.write(b); 
        outputStream.flush();
        outputStream.close();
        inputStream.close();
      }
    }    
    catch (Exception e) {
      System.err.println(e);
      e.printStackTrace();
    }
    
  }

}
