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

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.Socket;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class BioTcpWhoisGuiMain extends JFrame {

    public final static int port = 43;
    public final static String hostname = "whois.internic.net";

    private JTextField searchString = new JTextField(30);
    private JTextArea detailView = new JTextArea(12, 40);
    private DefaultListModel namesModel = new DefaultListModel();
    private JList names = new JList(namesModel);
    private JButton findButton = new JButton("Find");;
    private ButtonGroup searchIn = new ButtonGroup();
    private ButtonGroup searchFor = new ButtonGroup();
    private JCheckBox exactMatch = new JCheckBox("Exact Match", true);

    public static void main(String... args) {
    
        BioTcpWhoisGuiMain a = new BioTcpWhoisGuiMain();
        a.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        a.pack();
        a.show();
    
    }

    public BioTcpWhoisGuiMain() {

        super("whois");
        Container pane = this.getContentPane();
        detailView.setEditable(false);
        JScrollPane jsp1 = new JScrollPane(detailView);

        Panel CenterPanel = new Panel();
        CenterPanel.setLayout(new GridLayout(1, 2, 10, 10));
        JScrollPane jsp = new JScrollPane(names);
        CenterPanel.add(jsp);
        CenterPanel.add(jsp1);
        pane.add("Center", CenterPanel);

        // You don't want the buttons inputStreamthe south and north
        // to fill the entire sections so add Panels there
        // and use FlowLayouts inputStreamthe Panel
        Panel NorthPanel = new Panel();
        Panel NorthPanelTop = new Panel();
        NorthPanelTop.setLayout(new FlowLayout(FlowLayout.LEFT));
        NorthPanelTop.add(new Label("Whois: "));
        NorthPanelTop.add("North", searchString);
        NorthPanelTop.add(exactMatch);
        NorthPanel.setLayout(new BorderLayout(2, 1));
        NorthPanel.add("North", NorthPanelTop);
        Panel NorthPanelBottom = new Panel();
        NorthPanelBottom.setLayout(new GridLayout(1, 2, 5, 5));
        NorthPanelBottom.add(initRecordType());
        NorthPanelBottom.add(initSearchFields());
        NorthPanel.add("Center", NorthPanelBottom);
        Panel SouthPanel = new Panel();

        SouthPanel.add("South", findButton);
        pane.add("South", SouthPanel);
        pane.add("North", NorthPanel);

        ActionListener al = new LookupNames();
        findButton.addActionListener(al);
        searchString.addActionListener(al);

        names.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int index = names.locationToIndex(e.getPoint());
                getFullRecord(namesModel.elementAt(index).toString());
            }
        });

    }

    private JPanel initRecordType() {

        JPanel p = new JPanel();
        p.setLayout(new GridLayout(6, 2, 5, 2));
        p.add(new JLabel("Search for:"));
        p.add(new JLabel(""));

        JRadioButton any = new JRadioButton("Any", true);
        any.setActionCommand("Any");
        searchFor.add(any);
        p.add(any);

        p.add(this.makeRadioButton("Network"));
        p.add(this.makeRadioButton("Person"));
        p.add(this.makeRadioButton("Host"));
        p.add(this.makeRadioButton("Domain"));
        p.add(this.makeRadioButton("Organization"));
        p.add(this.makeRadioButton("Group"));
        p.add(this.makeRadioButton("Gateway"));
        p.add(this.makeRadioButton("ASN"));

        return p;

    }

    private JRadioButton makeRadioButton(String label) {

        JRadioButton button = new JRadioButton(label, false);
        button.setActionCommand(label);
        searchFor.add(button);
        return button;

    }

    private JRadioButton makeSearchInRadioButton(String label) {

        JRadioButton button = new JRadioButton(label, false);
        button.setActionCommand(label);
        searchIn.add(button);
        return button;

    }

    private JPanel initSearchFields() {

        JPanel p = new JPanel();
        p.setLayout(new GridLayout(6, 1, 5, 2));
        p.add(new JLabel("Search In: "));

        JRadioButton all = new JRadioButton("All", true);
        all.setActionCommand("All");
        searchIn.add(all);
        p.add(all);

        p.add(this.makeSearchInRadioButton("Name"));
        p.add(this.makeSearchInRadioButton("Mailbox"));
        p.add(this.makeSearchInRadioButton("Handle"));

        return p;

    }

    class LookupNames implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            lookUpNames(searchString.getText());
        }

    }

    public String makeSuffix() {

        String suffix = "";
        if (!exactMatch.isSelected())
            suffix = ".";
        return suffix;

    }

    public String makePrefix() {

        String searchForLabel = searchFor.getSelection().getActionCommand();
        String searchInLabel = searchIn.getSelection().getActionCommand();
        if (searchInLabel.equals("All"))
            searchInLabel = "";
        else
            searchInLabel += " ";
        if (searchForLabel.equals("Any"))
            searchForLabel = "";
        else
            searchForLabel += " ";
        String prefix = searchForLabel + searchInLabel + "$";
        return prefix;

    }

    public void lookUpNames(String name) {

        try {
            Socket theSocket = new Socket(hostname, port);
            Writer outputStream = new OutputStreamWriter(theSocket.getOutputStream(), "ASCII");
            BufferedReader inputStream = new BufferedReader(new InputStreamReader(theSocket.getInputStream(), "ASCII"));
            String query = this.makePrefix() + searchString.getText() + this.makeSuffix() + "\r\n";
            outputStream.write(query);
            outputStream.flush();
            namesModel.clear();
            String s;
            while ((s = inputStream.readLine()) != null) {
                // throw away NSI legal gibberish
                if (s.indexOf('\t') >= 0) {
                    // tab stops seem to be every 8 spaces
                    s = s.replace('\t', ' ');
                    namesModel.addElement(s);
                }
            }
        }
        catch (IOException e) {
            System.err.println(e);
        }

    }

    public void getFullRecord(String summary) {

        String handle = getHandle(summary);
        try {
            Socket theSocket = new Socket(hostname, port);
            Writer outputStream = new OutputStreamWriter(theSocket.getOutputStream(), "ASCII");
            Reader inputStream = new InputStreamReader(theSocket.getInputStream(), "ASCII");
            outputStream.write("!" + handle + "\r\n");
            outputStream.flush();
            detailView.setText("");
            int c;
            while ((c = inputStream.read()) != -1)
                detailView.append(String.valueOf((char) c));
        }
        catch (IOException e) {
            System.err.println(e);
        }

    }

    private static String getHandle(String s) {
        int begin = s.indexOf("(") + 1;
        int end = s.indexOf(")", begin);
        return s.substring(begin, end);
    }

}
