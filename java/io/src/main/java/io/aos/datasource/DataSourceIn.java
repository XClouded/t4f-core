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
package io.aos.datasource;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.activation.DataSource;

public final class DataSourceIn implements DataSource {
    public static final String DEFAULT_TYPE = "application/octet-stream";

    private final InputStream inputStream;
    private final String contentType;

    public DataSourceIn(InputStream inputStream) {
        this(inputStream, null);
    }

    public DataSourceIn(InputStream inputStream, String contentType) {
        this.inputStream = inputStream;
        this.contentType = (contentType != null) ? contentType : DEFAULT_TYPE;
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public String getName() {
       return DataSourceIn.class.getName();
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return inputStream;
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        throw new UnsupportedOperationException("Only support InputStream is supported.");
    }

}
