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

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * some utilities for James unit testing
 */
public class BioTcpSpamAssassinMimeMessageBuilder {

    private static int m_counter = 0;

    public static String newId() {
        m_counter++;
        return "MockMailUtil-ID-" + m_counter;
    }
    
    public static BioTcpSpamAssassinMimeMessageMock buildMimeMessage() throws MessagingException {
        return buildMimeMessage(null, null);
    }
    
    public static BioTcpSpamAssassinMimeMessageMock buildMimeMessageWithSubject(String subject) throws MessagingException {
        return buildMimeMessage(null, null, subject, 0);
    }
    
    public static BioTcpSpamAssassinMimeMessageMock buildMimeMessage(String subject, int number) throws MessagingException {
        return buildMimeMessage(null, null, subject, number);
    }
    
    public static BioTcpSpamAssassinMimeMessageMock buildMimeMessage(String headerName, String headerValue) throws MessagingException {
        return buildMimeMessage(headerName, headerValue, "testmail", 0);
    }
    
    public static BioTcpSpamAssassinMimeMessageMock buildMimeMessage(String headerName, String headerValue, String subject, int number) throws MessagingException {
        String sender = "test@james.apache.org";
        String rcpt = "test2@james.apache.org";

        BioTcpSpamAssassinMimeMessageMock mockedMimeMessage = new BioTcpSpamAssassinMimeMessageMock(number);
        mockedMimeMessage.setFrom(new InternetAddress(sender));
        mockedMimeMessage.setRecipients(MimeMessage.RecipientType.TO, rcpt);
        if (headerName != null) mockedMimeMessage.setHeader(headerName, headerValue);
        if (subject != null) mockedMimeMessage.setSubject(subject);
        mockedMimeMessage.setText("testtext");
        mockedMimeMessage.saveChanges();
        return mockedMimeMessage;
    }

}
