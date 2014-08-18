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
import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.rmi.*;
import java.math.BigInteger;

public class BioTcpFibonacciApplet extends Applet {

  private TextArea  resultArea 
   = new TextArea("", 20, 72, TextArea.SCROLLBARS_BOTH);
  private TextField inputArea  = new TextField(24);
  private Button calculate = new Button("Calculate");
  private String server;

  public void init() {
    
    this.setLayout(new BorderLayout());
    
    Panel north = new Panel();
    north.add(new Label("Type a non-negative integer"));
    north.add(inputArea);
    north.add(calculate);
    this.add(resultArea, BorderLayout.CENTER);
    this.add(north, BorderLayout.NORTH);
    Calculator c = new Calculator();
    inputArea.addActionListener(c);
    calculate.addActionListener(c);
    resultArea.setEditable(false);
    
    server = "rmi://" + this.getCodeBase().getHost() + "/fibonacci";
    
  }

  class Calculator implements ActionListener {
    
    public void actionPerformed(ActionEvent e) {
      
      try {
        String input = inputArea.getText();
        if (input != null) {
          BigInteger index = new BigInteger(input);            
          BioTcpFibonacci f = (BioTcpFibonacci) Naming.lookup(server);
          BigInteger result =  f.getFibonacci(index);
          resultArea.setText(result.toString());
        }
      } 
      catch (Exception ex) {
        resultArea.setText(ex.getMessage());
      }          
      
    }
    
  }

}
