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

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class BioTcpWebServer2 extends Thread {

    private File documentRootDirectory;
    private String indexFileName = "index.html";
    private ServerSocket server;
    private int numThreads = 50;

    public BioTcpWebServer2(File documentRootDirectory, int port, String indexFileName) throws IOException {

        if (!documentRootDirectory.isDirectory()) {
            throw new IOException(documentRootDirectory + " does not exist as a directory");
        }
        this.documentRootDirectory = documentRootDirectory;
        this.indexFileName = indexFileName;
        this.server = new ServerSocket(port);
    }

    public BioTcpWebServer2(File documentRootDirectory, int port) throws IOException {
        this(documentRootDirectory, port, "index.html");
    }

    public BioTcpWebServer2(File documentRootDirectory) throws IOException {
        this(documentRootDirectory, 80, "index.html");
    }

    public void run() {

        for (int i = 0; i < numThreads; i++) {
            Thread t = new Thread(new BioTcpRequestProcessor(documentRootDirectory, indexFileName));
            t.start();
        }
        System.out.println("Accepting connections on port " + server.getLocalPort());
        System.out.println("Document Root: " + documentRootDirectory);
        while (true) {
            try {
                Socket request = server.accept();
                BioTcpRequestProcessor.processRequest(request);
            }
            catch (IOException e) {
            }
        }

    }

    public static void main(String... args) {

        // get the Document root
        File docroot;
        try {
            docroot = new File(args[0]);
        }
        catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Usage: java JHTTP docroot port indexfile");
            return;
        }

        // set the port to listen on
        int port;
        try {
            port = Integer.parseInt(args[1]);
            if (port < 0 || port > 65535)
                port = 80;
        }
        catch (Exception e) {
            port = 80;
        }

        try {
            BioTcpWebServer2 webserver = new BioTcpWebServer2(docroot, port);
            webserver.start();
        }
        catch (IOException e) {
            System.out.println("Server could not start because of an " + e.getClass());
            System.out.println(e);
        }

    }

}
