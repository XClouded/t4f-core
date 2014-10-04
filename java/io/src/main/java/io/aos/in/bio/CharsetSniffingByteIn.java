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
package io.aos.in.bio;

import io.aos.in.bio.ahead.PeekAheadIn;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Will attempt to autodetect the character encoding from the stream By default,
 * this will preserve the BOM if it exists
 */
public class CharsetSniffingByteIn extends FilterInputStream {

    public static enum Encoding {
        UTF32be("UTF-32", true, new byte[] { 0x00, 0x00, 0xFFFFFFFE, 0xFFFFFFFF }), //
        UTF32le("UTF-32", true, new byte[] {0xFFFFFFFF, 0xFFFFFFFE, 0x00, 0x00 }), //
        INVALID(null, true, new byte[] { 0xFFFFFFFE, 0xFFFFFFFF, 0x00,0x00 }, new byte[] { 0x00, 0x00, 0xFFFFFFFF, 0xFFFFFFFE }), //
        UTF16be("UTF-16", true, new byte[] {0xFFFFFFFE, 0xFFFFFFFF }), //
        UTF16le("UTF-16", true, new byte[] { 0xFFFFFFFF, 0xFFFFFFFE }), //
        UTF8("UTF-8", true, new byte[] { 0xFFFFFFEF, 0xFFFFFFBB, 0xFFFFFFBF }), //
        UTF32be2("UTF-32be", false, new byte[] { 0x00, 0x00, 0x00, 0x3C }), //
        UTF32le2("UTF-32le", false, new byte[] { 0x3C, 0x00, 0x00, 0x00 }), //
        UTF16be2("UTF-16be", false, new byte[] { 0x00, 0x3C, 0x00, 0x3F }), //
        UTF16le2("UTF-16le", false, new byte[] { 0x3C, 0x00, 0x3F, 0x00 }) //
        ;
        private final String enc;
        private final byte[][] checks;
        private final boolean bom;
        Encoding(String name, boolean bom, byte[]... checks) {
            this.enc = name;
            this.checks = checks;
            this.bom = bom;
        }
        public String getEncoding() {
            return enc;
        }
        public boolean getBom() {
            return bom;
        }
        public int equals(byte[] bom) {
            for (byte[] check : checks) {
                if (CharsetSniffingByteIn.equals(bom, check.length, check))
                    return check.length;
            }
            return 0;
        }
    }

    protected String encoding;
    protected boolean bomset = false;
    protected final boolean preserve;

    public CharsetSniffingByteIn(InputStream inputStream) {
        this(inputStream, true);
    }

    public CharsetSniffingByteIn(InputStream inputStream, boolean preserveBom) {
        super(!(inputStream instanceof PeekAheadIn) ? new PeekAheadIn(inputStream, 4) : inputStream);
        this.preserve = preserveBom;
        try {
            encoding = detectEncoding();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isBomSet() {
        return bomset;
    }

    public String getEncoding() {
        return encoding;
    }

    protected PeekAheadIn getInternal() {
        return (PeekAheadIn) in;
    }

    private static boolean equals(byte[] a1, int len, byte[] a2) {
        for (int n = 0, i = 0; n < len; n++, i++) {
            if (a1[n] != a2[i])
                return false;
        }
        return true;
    }

    protected String detectEncoding() throws IOException {
        PeekAheadIn pinputStream= (PeekAheadIn) this.in;
        byte[] bom = new byte[4];
        pinputStream.peek(bom);
        bomset = false;
        for (Encoding enc : Encoding.values()) {
            int bomlen = enc.equals(bom);
            if (bomlen > 0) {
                bomset = enc.getBom();
                if (bomset && !preserve) // consume the bom
                    pinputStream.read(new byte[bomlen]);
                return enc.getEncoding();
            }
        }
        return null;
    }

}
