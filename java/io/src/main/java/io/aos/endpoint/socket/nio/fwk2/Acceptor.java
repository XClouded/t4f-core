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
package io.aos.endpoint.socket.nio.fwk2;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import javax.net.ssl.SSLContext;

/**
 * A Runnable class which sits in a loop accepting SocketChannels,
 * then registers the Channels with the read/write Selector.
 *
 * @author Mark Reinhold
 * @author Brad R. Wetmore
 * @version 1.5, 10/03/23
 */
class Acceptor implements Runnable {

    private ServerSocketChannel ssc;
    private Dispatcher d;

    private SSLContext sslContext;

    Acceptor(ServerSocketChannel ssc, Dispatcher d, SSLContext sslContext) {
	this.ssc = ssc;
	this.d = d;
	this.sslContext = sslContext;
    }

    public void run() {
	for (;;) {
	    try {
		SocketChannel sc = ssc.accept();

		ChannelIO cio = (sslContext != null ?
		    ChannelIOSecure.getInstance(
			sc, false /* non-blocking */, sslContext) :
		    ChannelIO.getInstance(
			sc, false /* non-blocking */));

		RequestHandler rh = new RequestHandler(cio);

		d.register(cio.getSocketChannel(), SelectionKey.OP_READ, rh);

	    } catch (IOException x) {
		x.printStackTrace();
		break;
	    }
	}
    }
}
