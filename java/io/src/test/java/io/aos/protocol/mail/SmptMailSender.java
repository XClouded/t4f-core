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

import javax.mail.*;
import javax.mail.internet.*;

import java.io.UnsupportedEncodingException;
import java.util.*;

public class SmptMailSender {

    public static void main(String... args) throws UnsupportedEncodingException, MessagingException {

        Properties props = new Properties();
        props.put("mail.host", "mail.cloud9.net");

        Session mailConnection = Session.getInstance(props, null);

        Address bill = new InternetAddress("god@microsoft.com", "Bill Gates");
        Address elliotte = new InternetAddress("elharo@metalab.unc.edu");

        Message msg = new MimeMessage(mailConnection);

        msg.setFrom(bill);
        msg.setRecipient(Message.RecipientType.TO, elliotte);
        msg.setSubject("You must comply.");
        msg.setContent("Resistance is futile. You will be assimilated!", "text/plain");

        Transport.send(msg);

    }

}
