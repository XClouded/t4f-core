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

import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;

public class Pop3Client {

    public static void main(String... args) {

        Properties props = new Properties();

        String host = "utopia.poly.edu";
        String username = "eharold";
        String password = "mypassword";
        String provider = "pop3";

        try {

            // Connect to the POP3 server
            Session session = Session.getDefaultInstance(props, null);
            Store store = session.getStore(provider);
            store.connect(host, username, password);

            // Open the folder
            Folder inbox = store.getFolder("INBOX");
            if (inbox == null) {
                System.out.println("No INBOX");
                System.exit(1);
            }
            inbox.open(Folder.READ_ONLY);

            Message[] messages = inbox.getMessages();
            for (int i = 0; i < messages.length; i++) {
                System.out.println("------------ Message " + (i + 1) + " ------------");
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

    }

    public static void printMessage(Message m) throws Exception {

        // Print the message header
        Address[] from = m.getFrom();
        if (from != null) {
            for (int i = 0; i < from.length; i++) {
                System.out.println("From: " + from[i]);
            }
        }

        Address[] to = m.getRecipients(Message.RecipientType.TO);
        if (to != null) {
            for (int i = 0; i < to.length; i++) {
                System.out.println("To: " + to[i]);
            }
        }

        String subject = m.getSubject();
        if (subject != null) {
            System.out.println("Subject: " + subject);
        }

        Date d = m.getSentDate();
        if (d != null) {
            System.out.println("Date: " + d);
        }

        System.out.println();

        Object content = m.getContent();
        if (content instanceof String) {
            System.out.println(content);
        }
        else if (content instanceof InputStream) {
            InputStream inputStream = (InputStream) content;
            int c;
            while ((c = inputStream.read()) != -1)
                System.out.write(c);
        }
        else {
            // This is actually likely to be a multi-part MIME
            // message. We'll cover this inputStreama later example.
            System.out.println("Unrecognized Content Type");
        }

    }

}
