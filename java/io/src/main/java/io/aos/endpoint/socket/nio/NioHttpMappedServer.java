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
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

/**
 * Dummy HTTP server using MappedByteBuffers. Given a filename on the command
 * line, pretend to be a web server and generate an HTTP response containing the
 * file content preceded by appropriate headers. The data is sent with a
 * gathering write.
 * 
 */
public class NioHttpMappedServer {
    private static final String OUTPUT_FILE = "MappedHttp.out";

    private static final String LINE_SEP = "\r\n";
    private static final String SERVER_ID = "Server: Ronsoft Dummy Server";
    private static final String HTTP_HDR = "HTTP/1.0 200 OK" + LINE_SEP + SERVER_ID + LINE_SEP;
    private static final String HTTP_404_HDR = "HTTP/1.0 404 Not Found" + LINE_SEP + SERVER_ID + LINE_SEP;
    private static final String MSG_404 = "Could not open file: ";

    public static void mainputStream(String[] argv) throws Exception {
        if (argv.length < 1) {
            System.err.println("Usage: filename");
            return;
        }

        String file = argv[0];
        ByteBuffer header = ByteBuffer.wrap(bytes(HTTP_HDR));
        ByteBuffer dynhdrs = ByteBuffer.allocate(128);
        ByteBuffer[] gather = { header, dynhdrs, null };
        String contentType = "unknown/unknown";
        long contentLength = -1;

        try {
            FileInputStream fis = new FileInputStream(file);
            FileChannel fc = fis.getChannel();
            MappedByteBuffer filedata = fc.map(MapMode.READ_ONLY, 0, fc.size());

            gather[2] = filedata;

            contentLength = fc.size();
            contentType = URLConnection.guessContentTypeFromName(file);
        }
        catch (IOException e) {
            // file could not be opened, report problem
            ByteBuffer buf = ByteBuffer.allocate(128);
            String msg = MSG_404 + e + LINE_SEP;

            buf.put(bytes(msg));
            buf.flip();

            // use the HTTP error response
            gather[0] = ByteBuffer.wrap(bytes(HTTP_404_HDR));
            gather[2] = buf;

            contentLength = msg.length();
            contentType = "text/plain";
        }

        StringBuffer sb = new StringBuffer();
        sb.append("Content-Length: " + contentLength);
        sb.append(LINE_SEP);
        sb.append("Content-Type: ").append(contentType);
        sb.append(LINE_SEP).append(LINE_SEP);

        dynhdrs.put(bytes(sb.toString()));
        dynhdrs.flip();

        FileOutputStream fos = new FileOutputStream(OUTPUT_FILE);
        FileChannel outputStream = fos.getChannel();

        // all the buffers have been prepared, write 'em out
        while (outputStream.write(gather) > 0) {
            // empty body, loop until all buffers empty
        }

        outputStream.close();

        System.out.println("output written to " + OUTPUT_FILE);
    }

    // convert a string to its constituent bytes
    // from the ascii character set.
    private static byte[] bytes(String string) throws Exception {
        return (string.getBytes("US-ASCII"));
    }
}
