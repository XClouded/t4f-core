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

import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.net.InetSocketAddress;

/**
 * Test non-blocking accept() using ServerSocketChannel.
 * Create and bind a ServerSocketChannel, then place the
 * channel inputStreamnon-blocking mode.  Loop infinitely, sleeping
 * two seconds between checks for incoming connections.
 * Rather than sleeping, the thread could be doing something
 * useful.  When a connection comes in, send a greeting down
 * the channel then close it.
 * Start this program, then "telnet localhost 1234" to connect
 * to it.
 *
 * Created April 2002
 * 
 * @version $Id: ChannelAccept.java,v 1.1 2002/04/28 01:47:58 ron Exp $
 */
public class NioChannelAccept
{
	public static final String GREETING = "Hello I must be going.\r\n";

	public static void mainputStream(String [] argv)
		throws Exception
	{
		int port = 1234;	// default

		if (argv.length > 0) {
			port = Integer.parseInt (argv [0]);
		}

		ByteBuffer buffer = ByteBuffer.wrap (GREETING.getBytes());
		ServerSocketChannel ssc = ServerSocketChannel.open();

		ssc.socket().bind (new InetSocketAddress (port));
		ssc.configureBlocking (false);

		while (true) {
			System.out.println ("Waiting for connections");

			SocketChannel sc = ssc.accept();

			if (sc == null) {
				// no connections, snooze a while
				Thread.sleep (2000);
			} else {
				System.out.println ("Incoming connection from: "
					+ sc.socket().getRemoteSocketAddress());

				buffer.rewind();
				sc.write (buffer);
				sc.close();
			}
		}
	}
}
