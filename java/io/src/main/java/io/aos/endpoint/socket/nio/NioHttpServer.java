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

import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.regex.Pattern;

/**
 * Simple-minded HTTP server using MappedByteBuffers. This example will be
 * expanded significantly
 */
public class NioHttpServer {
    private static final int DEFAULT_PORT = 8880;
    private static final String DEFAULT_ROOT_DIR = ".";

    private static final String LINE_SEP = "\r\n";
    private static final String HTTP_HDR = "HTTP/1.0 200 OK" + LINE_SEP + "Server: Ronsoft Sample Server" + LINE_SEP
            + "Content-Type: text/plain" + LINE_SEP;

    private ByteBuffer staticHdr = ByteBuffer.wrap(HTTP_HDR.getBytes());

    private Charset utf8 = Charset.forName("UTF-8");
    private Pattern space = Pattern.compile("\\s+");
    // private Matcher spaceMatch = space.matcher ("");

    private String rootDir = DEFAULT_ROOT_DIR;

    public static void main(String... argv) throws Exception {
        int port = DEFAULT_PORT;

        if (argv.length != 0) {
            port = Integer.parseInt(argv[0]);
        }

        NioHttpServer server = new NioHttpServer();

        System.out.println("Starting server on port " + port);
        server.runServer(port);
    }

    public void runServer(int port) throws Exception {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ByteBuffer temp = ByteBuffer.allocate(1024 * 10);
        ssc.socket().bind(new InetSocketAddress(port));

        while (true) {
            SocketChannel socket = ssc.accept();

            temp.clear();
            socket.read(temp);

            temp.flip();
            CharBuffer cb = utf8.decode(temp);

            System.out.println("cb = " + cb);
            String[] tokens = space.split(cb, 3);

            String filename = rootDir + tokens[1];

            FileInputStream fis = new FileInputStream(filename);
            FileChannel fc = fis.getChannel();

            sendFile(fc, socket, URLConnection.guessContentTypeFromName(filename));

            fc.close();
            fis.close();
            socket.close();
        }
    }

    public void sendFile(FileChannel file, GatheringByteChannel outputStream, String contentType) throws Exception {
        MappedByteBuffer fileData = file.map(MapMode.READ_ONLY, 0, file.size());
        sendBuffer(fileData, outputStream, contentType);
    }

    private CharBuffer cbtemp = CharBuffer.allocate(1024);
    private ByteBuffer dynHdr = ByteBuffer.allocate(1024);

    private void sendBuffer(ByteBuffer data, GatheringByteChannel channel, String contentType) throws Exception {
        ByteBuffer[] buffers = { staticHdr, dynHdr, data };

        staticHdr.rewind();

        cbtemp.clear();
        cbtemp.put("Content-Length: " + data.limit());
        cbtemp.put(LINE_SEP);
        cbtemp.put("Content-Type: ");
        cbtemp.put(contentType);
        cbtemp.put(LINE_SEP);
        cbtemp.put(LINE_SEP);
        cbtemp.flip();

        buffers[1] = utf8.encode(cbtemp);

        while (channel.write(buffers) != 0) {
            // nothing
        }
    }

}
