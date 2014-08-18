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

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.URLName;
import javax.mail.internet.InternetAddress;

public class Pop3FlagsClient {

    public static void main(String... args) {

        if (args.length == 0) {
            System.err.println("Usage: java FlagsClient protocol://username@host/foldername");
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
                System.out.println("------------ Message " + (i + 1) + " ------------");
                // Get the headers
                String from = InternetAddress.toString(messages[i].getFrom());
                if (from != null)
                    System.out.println("From: " + from);
                String replyTo = InternetAddress.toString(messages[i].getReplyTo());
                if (replyTo != null)
                    System.out.println("Reply-to: " + replyTo);
                String to = InternetAddress.toString(messages[i].getRecipients(Message.RecipientType.TO));
                if (to != null)
                    System.out.println("To: " + to);
                String cc = InternetAddress.toString(messages[i].getRecipients(Message.RecipientType.CC));
                if (cc != null)
                    System.out.println("Cc: " + cc);
                String bcc = InternetAddress.toString(messages[i].getRecipients(Message.RecipientType.BCC));
                if (bcc != null)
                    System.out.println("Bcc: " + to);
                String subject = messages[i].getSubject();
                if (subject != null)
                    System.out.println("Subject: " + subject);
                Date sent = messages[i].getSentDate();
                if (sent != null)
                    System.out.println("Sent: " + sent);
                Date received = messages[i].getReceivedDate();
                if (received != null)
                    System.out.println("Received: " + received);

                // Now test the flags:
                if (messages[i].isSet(Flags.Flag.DELETED)) {
                    System.out.println("Deleted");
                }
                if (messages[i].isSet(Flags.Flag.ANSWERED)) {
                    System.out.println("Answered");
                }
                if (messages[i].isSet(Flags.Flag.DRAFT)) {
                    System.out.println("Draft");
                }
                if (messages[i].isSet(Flags.Flag.FLAGGED)) {
                    System.out.println("Marked");
                }
                if (messages[i].isSet(Flags.Flag.RECENT)) {
                    System.out.println("Recent");
                }
                if (messages[i].isSet(Flags.Flag.SEEN)) {
                    System.out.println("Read");
                }
                if (messages[i].isSet(Flags.Flag.USER)) {
                    // We don't know what the user flags might be
                    // inputStreamadvance
                    // so they're returned as an arrya of strings
                    String[] userFlags = messages[i].getFlags().getUserFlags();
                    for (int j = 0; j < userFlags.length; j++) {
                        System.out.println("User flag: " + userFlags[j]);
                    }
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

}
