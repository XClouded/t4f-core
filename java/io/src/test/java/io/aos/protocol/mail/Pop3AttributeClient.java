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

import java.util.Date;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.URLName;
import javax.mail.internet.InternetAddress;

public class Pop3AttributeClient {

    public static void main(String... args) {

        if (args.length == 0) {
            System.err.println("Usage: java AttributeClient protocol://username@host/foldername");
            return;
        }

        URLName server = new URLName(args[0]);

        try {

            Session session = Session.getDefaultInstance(new Properties(), new MailAuthenticator(server.getUsername()));

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
                System.out.println("------------ Message " + (i + 1) + " ------------");
                String from = InternetAddress.toString(messages[i].getFrom());
                if (from != null)
                    System.out.println("From: " + from);
                String to = InternetAddress.toString(messages[i].getRecipients(Message.RecipientType.TO));
                if (to != null)
                    System.out.println("To: " + to);
                String subject = messages[i].getSubject();
                if (subject != null)
                    System.out.println("Subject: " + subject);
                Date sent = messages[i].getSentDate();
                if (sent != null)
                    System.out.println("Sent: " + sent);

                System.out.println();
                // Here's the attributes...
                System.out.println("This message is approximately " + messages[i].getSize() + " bytes long.");
                System.out.println("This message has approximately " + messages[i].getLineCount() + " lines.");
                String disposition = messages[i].getDisposition();
                if (disposition == null)
                    ; // do nothing
                else if (disposition.equals(Part.INLINE)) {
                    System.out.println("This part should be displayed inline");
                }
                else if (disposition.equals(Part.ATTACHMENT)) {
                    System.out.println("This part is an attachment");
                    String fileName = messages[i].getFileName();
                    if (fileName != null) {
                        System.out.println("The file name of this attachment is " + fileName);
                    }
                }
                String description = messages[i].getDescription();
                if (description != null) {
                    System.out.println("The description of this message is " + description);
                }

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

}
