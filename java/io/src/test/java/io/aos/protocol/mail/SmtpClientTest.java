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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class SmtpClientTest {
    private static final String SMTP_HOSTNAME = "localhost";
    private static final String SMTP_PORTNUMBER = "2525";
    private static final String SENDER_MAIL_ADRESS = "eric@localhost.net";
    private static final String RECEIVER_MAIL_ADRESS = "eric@localhost.net";
    private static final String SUBJECT = "Test mail";
    private static final String CONTENT = "Hello, this is a test...";
    private static final int MAILS_COUNT = 1;

    @Test
    public void testSendMails() throws UnsupportedEncodingException, MessagingException {
        for (int i = 0; i < MAILS_COUNT; i++) {
            doSendMail();
        }
    }

    private void doSendMail() throws UnsupportedEncodingException, MessagingException {

        Properties properties = System.getProperties();
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.host", SMTP_HOSTNAME);
        properties.put("mail.smtp.port", SMTP_PORTNUMBER);
        properties.put("mail.debug", "true");
        Session session = Session.getDefaultInstance(properties, null);

        Message message = new MimeMessage(session);

        message.setText(CONTENT);
        message.setSubject(SUBJECT);

        message.setFrom(new InternetAddress(SENDER_MAIL_ADRESS));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(RECEIVER_MAIL_ADRESS));

        Transport.send(message);

    }

    public static void smtpHtmlMultipartEmbeddedAttachmentSend() throws MessagingException {

        Properties properties = System.getProperties();
        properties.put("smtp.host", "localhost");

        Session sess = Session.getDefaultInstance(properties, null);
        System.out.println(" : " + sess);

        MimeMessage message = new MimeMessage(sess);
        message.setSubject("test");
        Address toAddress = new InternetAddress("eric.charles@u-mangate.com");
        message.setRecipient(Message.RecipientType.TO, toAddress);
        Address fromAddress = new InternetAddress("eric.charles@u-mangate.com");
        message.setFrom(fromAddress);
        // message.setHeader("Disposition-Notification-To","eric.charles@u-mangate.com");

        MimeMultipart mpAlt = new MimeMultipart("alternative");
        MimeMultipart mpRel = new MimeMultipart("related");

        MimeBodyPart part = new MimeBodyPart();
        String body = "<html><body><center><h1>page d'acceuil</h1><br/><hr/><br/>"
                + "<img src=\"cid:imgfoc\"/><br/><hr/><br/>"
                + "<a href=\"http://www.u-mangate.be\">u-mangate</a></center></body></html>";

        part.setContent(body, "text/html; charset=\"ISO-8859-1\"");
        mpAlt.addBodyPart(part);

        part = new MimeBodyPart();
        part.setContent(mpAlt);
        mpRel.addBodyPart(part);

        part = new MimeBodyPart();
        DataSource fds = new FileDataSource(new File("c:/a.gif"));
        part.setDataHandler(new DataHandler(fds));
        part.setHeader("Content-ID", "<" + "imgfoc" + ">");
        part.setFileName("test.zip");
        mpRel.addBodyPart(part);

        message.setContent(mpRel);
        message.saveChanges();

        Transport.send(message);
    }

    public static void smtpSend() throws MessagingException {

        Properties properties = System.getProperties();
        properties.put("smtp.host", "localhost");

        Session sess = Session.getDefaultInstance(properties, null);
        System.out.println(" : " + sess);

        Message message = new MimeMessage(sess);
        message.setText("coucou");
        message.setSubject("test");

        javax.mail.Address toAddress = new InternetAddress("eric.charles@u-mangate.be");
        message.setRecipient(javax.mail.Message.RecipientType.TO, toAddress);

        javax.mail.Address fromAddress = new InternetAddress("eric.charles@u-mangate.be");
        message.setFrom(fromAddress);
        // message.setHeader("Disposition-Notification-To","eric.charles@u-mangate.be");
        // Transport transport = sess.getTransport("smtp");
        Transport.send(message);

    }

    public static void smtpHtmlSend() throws MessagingException {

        Properties properties = System.getProperties();
        properties.put(".mail.smtp.host", "mail.u-mangate.be");
        Session sess = Session.getDefaultInstance(properties, null);
        MimeMessage message = new MimeMessage(sess);
        message.setSubject("test");
        javax.mail.Address toAddress = new InternetAddress("eric.charles@u-mangate.com");
        message.setRecipient(javax.mail.Message.RecipientType.TO, toAddress);
        javax.mail.Address fromAddress = new InternetAddress("eric.charles@u-mangate.com");
        message.setFrom(fromAddress);
        // message.setHeader("Disposition-Notification-To","eric.charles@u-mangate.be");

        Transport transport = sess.getTransport("smtp");

        String body = "<html><body><center><h1>page d'acceuil</h1><br/><hr/><br/>"
                + "<img src=\"http://www.aos.be/images/gsm1.gif\"/><br/><hr/><br/>"
                + "<a href=\"http://www.u-mangate.be\">u-mangate</a></center></body></html>";

        // message.setDataHandler(new DataHandler(body,"text/html"));
        message.setContent(body, "text/html; charset=\"ISO-8859-1\"");

        transport.send(message);

    }

    public static void smtpHtmlMultipartTxtAttachmentSend() throws MessagingException, IOException {

        Properties properties = System.getProperties();
        properties.put("smtp.host", "u-mangate.be");

        Session sess = Session.getDefaultInstance(properties, null);
        System.out.println(" : " + sess);

        MimeMessage message = new MimeMessage(sess);
        message.setSubject("test");

        Address toAddress = new InternetAddress("dvlp.marketing@u-mangate.be");
        message.setRecipient(Message.RecipientType.TO, toAddress);
        Address fromAddress = new InternetAddress("eric.charles@u-mangate.be");
        message.setFrom(fromAddress);

        // message.setHeader("Disposition-Notification-To","eric.charles@u-mangate.be");

        MimeMultipart mp = new MimeMultipart();
        MimeBodyPart part = new MimeBodyPart();

        part.setText("test");
        mp.addBodyPart(part);
        FileInputStream fis = new FileInputStream("c:/boot.ini");
        byte[] b = new byte[1];
        StringBuffer sb = new StringBuffer();
        while ((fis.read(b)) != -1) {
            sb.append(new String(b));
        }

        // System.out.println(sb.toString());

        part = new MimeBodyPart();
        part.setContent(sb.toString(), "text/plain");
        part.setContentID("imgfoc");
        mp.addBodyPart(part);

        message.setContent(mp);
        message.saveChanges();

        Transport.send(message);

    }

}
