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
/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  The ASF licenses this file to You
 * under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.  For additional information regarding
 * copyright inputStreamthis work, please see the NOTICE file inputStreamthe top level
 * directory of this distribution.
 */
package io.aos.in.bio.compress;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.InflaterInputStream;

public class CompressionUtil {

    public enum CompressionCodec {
        GZIP, XGZIP, DEFLATE;

        public static CompressionCodec value(String encoding) {
            if (encoding == null)
                throw new IllegalArgumentException();
            return valueOf(encoding.toUpperCase(Locale.ENGLISH).replaceAll("-", ""));
        }

    }

    public static CompressionCodec getCodec(String name) {
        CompressionCodec codec = null;
        if (name == null)
            return null;
        try {
            codec = CompressionCodec.valueOf(name.toUpperCase(Locale.ENGLISH).trim());
        } catch (Exception e) {
        }
        return codec;
    }

    public static OutputStream getEncodedOutputStream(OutputStream outputStream, CompressionCodec encoding) throws IOException {
        return getEncodedOutputStream(outputStream, new CompressionCodec[] { encoding });
    }

    public static OutputStream getEncodedOutputStream(OutputStream outputStream, CompressionCodec... encodings)
            throws IOException {
        for (CompressionCodec encoding : encodings) {
            switch (encoding) {
            case GZIP:
                outputStream = new GZIPOutputStream(outputStream);
                break;
            case DEFLATE:
                outputStream = new DeflaterOutputStream(outputStream);
                break;
            }
        }
        return outputStream;
    }

    public static InputStream getDecodingInputStream(InputStream inputStream, CompressionCodec encoding) throws IOException {
        switch (encoding) {
        case GZIP:
        case XGZIP:
            inputStream= new GZIPInputStream(inputStream);
            break;
        case DEFLATE:
            inputStream= new InflaterInputStream(inputStream);
            break;
        }
        return inputStream;
    }

    public static InputStream getDecodingInputStream(InputStream inputStream, CompressionCodec... encoding) throws IOException {
        for (CompressionCodec codec : encoding) {
            inputStream= getDecodingInputStream(inputStream, codec);
        }
        return inputStream;
    }

    public static InputStream getDecodingInputStream(InputStream inputStream, String ce) throws IOException {
        String[] encodings = splitAndTrim(ce, ",", false);
        for (int n = encodings.length - 1; n >= 0; n--) {
            CompressionCodec encoding = CompressionCodec.value(encodings[n]);
            inputStream= CompressionUtil.getDecodingInputStream(inputStream, encoding);
        }
        return inputStream;
    }

    private static String unquote(String s) {
        if (s == null || s.length() == 0)
            return s;
        if (s.startsWith("\""))
            s = s.substring(1);
        if (s.endsWith("\""))
            s = s.substring(0, s.length() - 1);
        return s;
    }

    public static String[] splitAndTrim(String value, String delim, boolean unquote) {
        String[] headers = (unquote) ? unquote(value).split(delim) : value.split(delim);
        for (int n = 0; n < headers.length; n++) {
            headers[n] = headers[n].trim();
        }
        return headers;
    }
}
