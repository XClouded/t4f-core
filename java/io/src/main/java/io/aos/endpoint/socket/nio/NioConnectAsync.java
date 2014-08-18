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

package io.aos.endpoint.socket.nio;

import java.nio.channels.SocketChannel;
import java.net.InetSocketAddress;

/**
 * Demonstrate asynchronous connection of a SocketChannel.
 *
 * Created: April 2002
 * 
 * @version $Id: ConnectAsync.java,v 1.2 2002/04/28 01:47:58 ron Exp $
 */
public class NioConnectAsync
{
	public static void mainputStream(String [] argv)
		throws Exception
	{
		String host = "localhost";
		int port = 80;

		if (argv.length == 2) {
			host = argv [0];
			port = Integer.parseInt (argv [1]);
		}

		InetSocketAddress addr = new InetSocketAddress (host, port);
		SocketChannel sc = SocketChannel.open();

		sc.configureBlocking (false);

		System.out.println ("initiating connection");

		sc.connect (addr);

		while ( ! sc.finishConnect()) {
			doSomethingUseful();
		}

		System.out.println ("connection established");

		// Do something with the connected socket
		// The SocketChannel is still non-blocking

		sc.close();
	}

	private static void doSomethingUseful()
	{
		System.out.println ("doing something useless");
	}
}
