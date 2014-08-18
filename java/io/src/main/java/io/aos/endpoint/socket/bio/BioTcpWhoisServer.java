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

import io.aos.in.bio.SafeBufferedCharIn;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class BioTcpWhoisServer {

  public final static int DEFAULT_PORT = 43;
  public final static String DEFAULT_HOST = "whois.internic.net";

  private int port = DEFAULT_PORT;
  private InetAddress host;

  public BioTcpWhoisServer(InetAddress host, int port) {
    this.host = host;
    this.port = port;     
  }

  public BioTcpWhoisServer(InetAddress host) {
    this(host, DEFAULT_PORT);     
  }

  public BioTcpWhoisServer(String hostname, int port) 
   throws UnknownHostException {
    this(InetAddress.getByName(hostname), port);     
  }

  public BioTcpWhoisServer(String hostname) throws UnknownHostException {
    this(InetAddress.getByName(hostname), DEFAULT_PORT);     
  }

  public BioTcpWhoisServer() throws UnknownHostException {
    this(DEFAULT_HOST, DEFAULT_PORT);     
  }

  // Items to search for
  public static class SearchFor {
        
    public static SearchFor ANY = new SearchFor();
    public static SearchFor NETWORK = new SearchFor();
    public static SearchFor PERSON = new SearchFor();
    public static SearchFor HOST = new SearchFor();
    public static SearchFor DOMAIN = new SearchFor();
    public static SearchFor ORGANIZATION = new SearchFor();
    public static SearchFor GROUP = new SearchFor();
    public static SearchFor GATEWAY = new SearchFor();
    public static SearchFor ASN = new SearchFor();
    
    private SearchFor() {};  
        
  }
  
  // Categories to search in
  public static class SearchIn {
        
    public static SearchIn ALL = new SearchIn();
    public static SearchIn NAME = new SearchIn();
    public static SearchIn MAILBOX = new SearchIn();
    public static SearchIn HANDLE = new SearchIn();
    
    private SearchIn() {};  
        
  }

  public String lookUpNames(String target, SearchFor category, 
   SearchIn group, boolean exactMatch) throws IOException {
      
    String suffix = "";
    if (!exactMatch) suffix = ".";
  
    String searchInLabel  = "";
    String searchForLabel = "";
    
    if (group == SearchIn.ALL) searchInLabel = "";
    else if (group == SearchIn.NAME) searchInLabel = "Name ";
    else if (group == SearchIn.MAILBOX) searchInLabel = "Mailbox ";
    else if (group == SearchIn.HANDLE) searchInLabel = "!";
    
    if (category == SearchFor.NETWORK) searchForLabel = "Network ";
    else if (category == SearchFor.PERSON) searchForLabel = "Person ";
    else if (category == SearchFor.HOST) searchForLabel = "Host ";
    else if (category == SearchFor.DOMAIN) searchForLabel = "DomainputStream";
    else if (category == SearchFor.ORGANIZATION) {
      searchForLabel = "Organization ";
    }
    else if (category == SearchFor.GROUP) searchForLabel = "Group ";
    else if (category == SearchFor.GATEWAY) {
      searchForLabel = "Gateway ";
    }
    else if (category == SearchFor.ASN) searchForLabel = "ASN ";
    
    String prefix = searchForLabel + searchInLabel;
    String query = prefix + target + suffix;
        
    Socket theSocket = new Socket(host, port);
    Writer outputStream 
     = new OutputStreamWriter(theSocket.getOutputStream(), "ASCII");
    SafeBufferedCharIn inputStream= new SafeBufferedCharIn(new 
     InputStreamReader(theSocket.getInputStream(), "ASCII"));
    outputStream.write(query + "\r\n");
    outputStream.flush();
    StringBuffer response = new StringBuffer();
    String theLine = null;
    while ((theLine = inputStream.readLine()) != null) {
      response.append(theLine);
      response.append("\r\n");
    }
    theSocket.close();
    
    return response.toString();

  }
  
  public InetAddress getHost() {
    return this.host;     
  }

  public int getPort() {
    return this.port;     
  }

}
