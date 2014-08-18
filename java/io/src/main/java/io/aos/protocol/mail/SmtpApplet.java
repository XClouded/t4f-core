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

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SmtpApplet extends Applet {
    private static final long serialVersionUID = 1L;

    private Button sendButton = new Button("Send Message");
    private Label fromLabel = new Label("From: ");
    private Label subjectLabel = new Label("Subject: ");
    private TextField fromField = new TextField(40);
    private TextField subjectField = new TextField(40);
    private TextArea message = new TextArea(30, 60);

    private String toAddress = "";

    public SmtpApplet() {

        this.setLayout(new BorderLayout());

        Panel north = new Panel();
        north.setLayout(new GridLayout(3, 1));

        Panel n1 = new Panel();
        n1.add(fromLabel);
        n1.add(fromField);
        north.add(n1);

        Panel n2 = new Panel();
        n2.add(subjectLabel);
        n2.add(subjectField);
        north.add(n2);

        this.add(north, BorderLayout.NORTH);

        message.setFont(new Font("Monospaced", Font.PLAIN, 12));
        this.add(message, BorderLayout.CENTER);

        Panel south = new Panel();
        south.setLayout(new FlowLayout(FlowLayout.CENTER));
        south.add(sendButton);
        sendButton.addActionListener(new SendAction());
        this.add(south, BorderLayout.SOUTH);

    }

    public void init() {

        String subject = this.getParameter("subject");
        if (subject == null)
            subject = "";
        subjectField.setText(subject);

        toAddress = this.getParameter("to");
        if (toAddress == null)
            toAddress = "";

        String fromAddress = this.getParameter("from");
        if (fromAddress == null)
            fromAddress = "";
        fromField.setText(fromAddress);

    }

    class SendAction implements ActionListener {

        public void actionPerformed(ActionEvent evt) {

            try {
                Properties props = new Properties();
                props.put("mail.host", getCodeBase().getHost());

                Session mailConnection = Session.getInstance(props, null);
                final Message msg = new MimeMessage(mailConnection);

                Address to = new InternetAddress(toAddress);
                Address from = new InternetAddress(fromField.getText());

                msg.setContent(message.getText(), "text/plain");
                msg.setFrom(from);
                msg.setRecipient(Message.RecipientType.TO, to);
                msg.setSubject(subjectField.getText());

                // This can take a non-trivial amount of time so
                // spawn a thread to handle it.
                Runnable r = new Runnable() {
                    public void run() {
                        try {
                            Transport.send(msg);
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                Thread t = new Thread(r);
                t.start();

                message.setText("");
            }
            catch (Exception e) {
                // We should really bring up a more specific error dialog here.
                e.printStackTrace();
            }

        }

    }

}
