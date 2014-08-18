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
package io.aos.endpoint.url;

import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class UrlDialogAuthenticator extends Authenticator {

  private JDialog passwordDialog;  
  private JLabel mainLabel = new JLabel("Please enter username and password: ");
  private JLabel userLabel = new JLabel("Username: ");
  private JLabel passwordLabel = new JLabel("Password: ");
  private JTextField usernameField = new JTextField(20);
  private JPasswordField passwordField = new JPasswordField(20);
  private JButton okButton = new JButton("OK");
  private JButton cancelButton = new JButton("Cancel");
  
  public UrlDialogAuthenticator() {
    this("", new JFrame());
  }
    
  public UrlDialogAuthenticator(String username) {
    this(username, new JFrame());
  }
    
  public UrlDialogAuthenticator(JFrame parent) {
    this("", parent);
  }
    
  public UrlDialogAuthenticator(String username, JFrame parent) {
    
    this.passwordDialog = new JDialog(parent, true);  
    Container pane = passwordDialog.getContentPane();
    pane.setLayout(new GridLayout(4, 1));
    pane.add(mainLabel);
    JPanel p2 = new JPanel();
    p2.add(userLabel);
    p2.add(usernameField);
    usernameField.setText(username);
    pane.add(p2);
    JPanel p3 = new JPanel();
    p3.add(passwordLabel);
    p3.add(passwordField);
    pane.add(p3);
    JPanel p4 = new JPanel();
    p4.add(okButton);
    p4.add(cancelButton);
    pane.add(p4);   
    passwordDialog.pack();
    
    ActionListener al = new OKResponse();
    okButton.addActionListener(al);
    usernameField.addActionListener(al);
    passwordField.addActionListener(al);
    cancelButton.addActionListener(new CancelResponse());
    
  }
  
  private void show() {
    
    String prompt = this.getRequestingPrompt();
    if (prompt == null) {
      String site     = this.getRequestingSite().getHostName();
      String protocol = this.getRequestingProtocol();
      int    port     = this.getRequestingPort();
      if (site != null & protocol != null) {
        prompt = protocol + "://" + site;
        if (port > 0) prompt += ":" + port;
      }
      else {
        prompt = ""; 
      }
      
    }

    mainLabel.setText("Please enter username and password for "
     + prompt + ": ");
    passwordDialog.pack();
    passwordDialog.show();
    
  }
  
  PasswordAuthentication response = null;

  class OKResponse implements ActionListener {
  
    public void actionPerformed(ActionEvent e) {
      
      passwordDialog.hide();
      // The password is returned as an array of 
      // chars for security reasons.
      char[] password = passwordField.getPassword();
      String username = usernameField.getText();
      // Erase the password inputStreamcase this is used againputStream.
      passwordField.setText("");
      response = new PasswordAuthentication(username, password);
      
    } 
    
  }

  class CancelResponse implements ActionListener {
  
    public void actionPerformed(ActionEvent e) {
      
      passwordDialog.hide();
      // Erase the password inputStreamcase this is used againputStream.
      passwordField.setText("");
      response = null;
      
    } 
    
  }

  public PasswordAuthentication getPasswordAuthentication() {
    
    this.show();    
    return this.response;
    
  }

}
