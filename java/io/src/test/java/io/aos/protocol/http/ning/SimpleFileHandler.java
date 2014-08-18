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
package io.aos.protocol.http.ning;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.ning.http.client.AsyncHandler;
import com.ning.http.client.HttpResponseBodyPart;
import com.ning.http.client.HttpResponseHeaders;
import com.ning.http.client.HttpResponseStatus;

public class SimpleFileHandler implements AsyncHandler<File> {
    private File file;
    private final FileOutputStream outputStream;
    private boolean failed = false;

    public SimpleFileHandler(File f) throws IOException {
        file = f;
        outputStream = new FileOutputStream(f);
    }

    public com.ning.http.client.AsyncHandler.STATE onBodyPartReceived(
            HttpResponseBodyPart part) throws IOException {
        if (!failed) {
            part.writeTo(outputStream);
        }
        return STATE.CONTINUE;
    }

    public File onCompleted() throws IOException {
        outputStream.close();
        if (failed) {
            file.delete();
            return null;
        }
        return file;
    }

    public com.ning.http.client.AsyncHandler.STATE onHeadersReceived(
            HttpResponseHeaders h) {
        // nothing to check here as of yet
        return STATE.CONTINUE;
    }

    public com.ning.http.client.AsyncHandler.STATE onStatusReceived(
            HttpResponseStatus status) {
        failed = (status.getStatusCode() != 200);
        return failed ? STATE.ABORT : STATE.CONTINUE;
    }

    public void onThrowable(Throwable t) {
        failed = true;
    }
}
