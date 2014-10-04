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

import java.io.IOException;
import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.URLName;
import javax.mail.search.FlagTerm;
import javax.mail.search.NotTerm;

import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class Imap4ClientTest {
    private static final String IMAP_HOSTNAME = "localhost";
    private static final String IMAP_PORTNUMBER = "1433";
    private static final String USERNAME = "eric@localhost.net";
    private static final String PASSWORD = "eric";
    private static final String FOLDER = "INBOX";

    @Test
    public void testWithSearchTerm() throws Exception {

        Properties properties = System.getProperties();
        properties.put("mail.store.protocol", "imap");
        properties.put("mail.debug", "true");
        properties.put("mail.imap.port", IMAP_PORTNUMBER);

        Session session = Session.getInstance(properties);
        URLName urlName = new URLName("imap://" + USERNAME.replaceAll("@", "%40") + ":" + PASSWORD + "@"
                + IMAP_HOSTNAME + "/" + FOLDER);
        System.out.println(urlName.toString());
        Store store = session.getStore(urlName);
        store.connect();
        Folder folder = store.getFolder(urlName);
        folder.open(Folder.READ_WRITE);
        folder.getMessageCount();
        Message[] messages = folder.getMessages();
        for (Message message : messages) {
            System.out.println(message.getSubject());
        }
        // NotTerm notJunk = new NotTerm(new FlagTerm(new
        // Flags(Flags.Flag.SEEN), true));
        NotTerm notJunk = new NotTerm(new FlagTerm(new Flags("foo"), true));
        folder.search(notJunk);

    }

    public static void imapRead() throws MessagingException, IOException {

        String host = "localhost";
        String user = "dvlp.marketing@u-mangate.be";
        String passwd = "wxcvbn";

        URLName u = new URLName("imap", host, -1, "INBOX", user, passwd);

        Properties props = System.getProperties();

        props.put("imap.class", "com.sun.imap.IMAPStore");
        props.put("imap.connectiontimeout", "60000");
        props.put("imap.timeout", "60000");

        Session session = Session.getInstance(props, null);

        Store store = null;
        Folder rf = null;

        store = session.getStore(u);
        store.connect();
        System.out.println("connection ok");
        rf = store.getDefaultFolder();
        System.out.println(rf.getFullName());
        Folder inputStream= rf.getFolder("INBOX");
        System.out.println(inputStream.getFullName() + " / " + inputStream.getMessageCount());
        inputStream.open(Folder.READ_WRITE);
        Message[] messages = inputStream.getMessages();

        for (int i = 0; i < messages.length; i++) {
            Message m = messages[i];
            System.out.println("message subject : " + m.getSubject());
            System.out.println("message from : " + m.getFrom()[0].toString());
            System.out.println("message text : " + m.getContent());

            // if ( m.getSubject().equals("coucou") ){
            // System.out.println("trying to delete message");
            // boolean b = m.isSet(Flags.Flag.DELETED);
            // System.out.println(b);
            // if (!b)m.setFlag(Flags.Flag.DELETED,true);
            // inputStream.expunge();
            // }
        }
    }

}
