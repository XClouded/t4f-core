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
package io.aos.concurrent.count;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.Iterator;
import java.util.Set;
// For Set and Iterator

public class NonBlockingServer {
    public static void main(String... args) throws IOException {
        
        // Get the character encoders and decoders you'll need
        Charset charset = Charset.forName("ISO-8859-1");
        CharsetEncoder encoder = charset.newEncoder();
        CharsetDecoder decoder = charset.newDecoder();

        // Allocate a buffer for communicating with clients
        ByteBuffer buffer = ByteBuffer.allocate(512);

        // All of the channels in this code will be in nonblocking mode. 
        // So create a Selector object that will block while monitoring 
        // all of the channels and stop blocking only when one or more
        // of the channels is ready for I/O of some sort.
        Selector selector = Selector.open();

        // Create a new ServerSocketChannel and bind it to port 8000  
        // Note that this must be done using the underlying ServerSocket
        ServerSocketChannel server = ServerSocketChannel.open();
        server.socket().bind(new java.net.InetSocketAddress(8000));
        // Put the ServerSocketChannel into nonblocking mode
        server.configureBlocking(false);
        // Now register it with the Selector (note that register() is called 
         // on the channel, not on the selector object, however). 
         // The SelectionKey represents the registration of this channel with 
         // this Selector.
        SelectionKey serverkey = server.register(selector,
                                                SelectionKey.OP_ACCEPT);

        for(;;) {  // The main server loop. The server runs forever.
            // This call blocks until there is activity on one of the 
            // registered channels. This is the key method in nonblocking 
            // I/O.
            selector.select();

            // Get a java.util.Set containing the SelectionKey objects for
            // all channels that are ready for I/O.
            Set keys = selector.selectedKeys();

            // Use a java.util.Iterator to loop through the selected keys
            for(Iterator i = keys.iterator(); i.hasNext(); ) {
                // Get the next SelectionKey in the set and remove it
                // from the set. It must be removed explicitly, or it will
                // be returned again by the next call to select().
                SelectionKey key = (SelectionKey) i.next();
                i.remove();

                // Check whether this key is the SelectionKey obtained when
                // you registered the ServerSocketChannel.
                if (key == serverkey) {
                    // Activity on the ServerSocketChannel means a client
                    // is trying to connect to the server.
                    if (key.isAcceptable()) {
                        // Accept the client connection and obtain a 
                        // SocketChannel to communicate with the client.
                        SocketChannel client = server.accept();
                        // Put the client channel in nonblocking mode
                        client.configureBlocking(false);
                        // Now register it with the Selector object, 
                        // telling it that you'd like to know when 
                        // there is data to be read from this channel.
                        SelectionKey clientkey =
                            client.register(selector, 
                            SelectionKey.OP_READ);
                        // Attach some client state to the key. You'll
                        // use this state when you talk to the client.
                        clientkey.attach(new Integer(0));
                    }
                }
                else {
                    // If the key obtained from the Set of keys is not the
                    // ServerSocketChannel key, then it must be a key 
                    // representing one of the client connections.  
                    // Get the channel from the key.
                    SocketChannel client = (SocketChannel) key.channel();

                    // If you are here, there should be data to read from
                    // the channel, but double-check.
                    if (!key.isReadable()) continue;

                    // Now read bytes from the client. Assume that all the
                    // client's bytes are in one read operation.
                    int bytesread = client.read(buffer);

                    // If read() returns -1, it indicates end-of-stream, 
                    // which means the client has disconnected, so 
                    // deregister the selection key and close the channel.
                    if (bytesread == -1) {  
                        key.cancel();
                        client.close();
                        continue;
                    }

                    // Otherwise, decode the bytes to a request string
                    buffer.flip();
                    String request = decoder.decode(buffer).toString();
                    buffer.clear();
                    // Now reply to the client based on the request string
                    if (request.trim().equals("quit")) {
                        // If the request was "quit", send a final message
                        // Close the channel and deregister the 
                        // SelectionKey
                        client.write(encoder.encode(CharBuffer.wrap("Bye.")));
                        key.cancel();
                        client.close();
                    }
                    else {
                        // Otherwise, send a response string comprised of 
                        // the sequence number of this request plus an 
                        // uppercase version of the request string. Note 
                        // that you keep track of the sequence number by 
                        // "attaching" an Integer object to the 
                        // SelectionKey and incrementing it each time.

                        // Get sequence number from SelectionKey
                        int num = ((Integer)key.attachment()).intValue();
                        // For response string
                        String response = num + ": " + request.toUpperCase();
                        // Wrap, encode, and write the response string
                        client.write(encoder.encode(CharBuffer.
                            wrap(response)));
                        // Attach an incremented sequence nubmer to the key
                        key.attach(new Integer(num+1));
                    }
                }
            }
        }
    }
}
