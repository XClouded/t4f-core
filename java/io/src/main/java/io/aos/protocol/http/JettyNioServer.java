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
package io.aos.protocol.http;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JettyNioServer {
    private static final Logger LOGGER = LoggerFactory.getLogger(JettyNioServer.class);
    private static final String WEBAPP_DIR = "src/main/webapp";
    private static final int PORT = 8080;
    private static final String WEBAPP_CTX = "/";

    public static void main(String... args) throws Exception {

        String webappDir = WEBAPP_DIR;
        Integer port = PORT;
        String webappContext = WEBAPP_CTX;

        if (args.length > 0) {
            if (args[0].equals(JettyNioServer.class.getName())) {
                webappDir = args[1];
                port = new Integer(args[2]);
                webappContext = args[3];
            }
        }

        Server server = new Server();

        SelectChannelConnector connector = new SelectChannelConnector();
        connector.setPort(port);

        WebAppContext context = new WebAppContext();
        context.setResourceBase(webappDir);
        context.setContextPath(webappContext);
        context.setServer(server);
        context.setDefaultsDescriptor("src/main/resources/jetty-webdefault.xml");

        server.setHandler(context);
        server.setConnectors(new Connector[] { connector });

        LOGGER.info("Starting Web Server on port: " + port);
        server.start();

        LOGGER.info("Microfacet Page Web Server is started - Point your web browser at: http://localhost:" + port + webappContext);

    }

}
