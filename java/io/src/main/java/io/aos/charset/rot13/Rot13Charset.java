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
package io.aos.charset.rot13;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;

/**
 * A Charset implementation which performs Rot13 encoding. Rot-13 encoding is a
 * simple text obfuscation algorithm which shifts alphabetical characters by 13
 * so that 'a' becomes 'n', 'o' becomes 'b', etc.
 * 
 * This algorithm was popularized by the Usenet discussion forums
 * many years ago to mask naughty words, hide answers to questions, and so on. 
 * 
 * The Rot13 algorithm is symmetrical, applying it to text that has been scrambled by 
 * Rot13 will give you the original unscrambled text.
 * 
 * Applying this Charset encoding to an output stream will cause everything you
 * write to that stream to be Rot13 scrambled as it's written outputStream. And
 * appying it to an input stream causes data read to be Rot13 descrambled as
 * it's read.
 */
public class Rot13Charset extends Charset {
    /** The name of the base charset encoding we delegate to. */
    private static final String BASE_CHARSET_NAME = "UTF-8";

    /** Handle to the real charset we'll use for transcoding between
     * characters and bytes. Doing this allows applying the Rot13
     * algorithm to multi-byte charset encodings. But only the
     * ASCII alpha chars will be rotated, no matter the base encoding.
     */
    Charset baseCharset;

    /**
     * Constructor for the Rot13 charset. Call the superclass constructor to
     * pass along the name(s) we'll be known by. Then save a reference to the
     * delegate Charset.
     */
    protected Rot13Charset(String canonical, String[] aliases) {
        super(canonical, aliases);
        // Save the base charset we're delegating to.
        baseCharset = Charset.forName(BASE_CHARSET_NAME);
    }

    /**
     * Called by users of this Charset to obtain an encoder. This implementation
     * instantiates an instance of a private class (defined below) and passes it
     * an encoder from the base Charset.
     */
    public CharsetEncoder newEncoder() {
        return new Rot13Encoder(this, baseCharset.newEncoder());
    }

    /**
     * Called by users of this Charset to obtain a decoder. This implementation
     * instantiates an instance of a private class (defined below) and passes it
     * a decoder from the base Charset.
     */
    public CharsetDecoder newDecoder() {
        return new Rot13Decoder(this, baseCharset.newDecoder());
    }

    /**
     * This method must be implemented by concrete Charsets. We always say no,
     * which is safe.
     */
    public boolean contains(Charset cs) {
        return (false);
    }

    /**
     * Common routine to rotate all the ASCII alpha chars in the given
     * CharBuffer by 13. Note that this code explicitly compares for upper and
     * lower case ASCII chars rather than using the methods
     * Character.isLowerCase and Character.isUpperCase. This is because the
     * rotate-by-13 scheme only works properly for the alphabetic characters of
     * the ASCII charset and those methods can return true for non-ASCII Unicode
     * chars.
     */
    private void rot13(CharBuffer cb) {
        for (int pos = cb.position(); pos < cb.limit(); pos++) {
            char c = cb.get(pos);
            char a = '\u0000';
            // Is it lower case alpha?
            if ((c >= 'a') && (c <= 'z')) {
                a = 'a';
            }
            // Is it upper case alpha?
            if ((c >= 'A') && (c <= 'Z')) {
                a = 'A';
            }
            // If either, roll it by 13
            if (a != '\u0000') {
                c = (char) ((((c - a) + 13) % 26) + a);
                cb.put(pos, c);
            }
        }
    }

    /**
     * The encoder implementation for the Rot13 Charset. This class, and the
     * matching decoder class below, should also override the "impl" methods,
     * such as implOnMalformedInput() and make passthrough calls to the
     * baseEncoder object. That is left as an exercise for the hacker.
     */
    private class Rot13Encoder extends CharsetEncoder {
        private CharsetEncoder baseEncoder;

        /**
         * Constructor, call the superclass constructor with the Charset object
         * and the encodings sizes from the delegate encoder.
         */
        Rot13Encoder(Charset cs, CharsetEncoder baseEncoder) {
            super(cs, baseEncoder.averageBytesPerChar(), baseEncoder.maxBytesPerChar());
            this.baseEncoder = baseEncoder;
        }

        /**
         * Implementation of the encoding loop. First, we apply the Rot13
         * scrambling algorithm to the CharBuffer, then reset the encoder for
         * the base Charset and call it's encode() method to do the actual
         * encoding. This may not work properly for non-Latin charsets. The
         * CharBuffer passed in may be read-only or re-used by the caller for
         * other purposes so we duplicate it and apply the Rot13 encoding to the
         * copy. We DO want to advance the position of the input buffer to
         * reflect the chars consumed.
         */
        protected CoderResult encodeLoop(CharBuffer cb, ByteBuffer bb) {
            CharBuffer tmpcb = CharBuffer.allocate(cb.remaining());
            while (cb.hasRemaining()) {
                tmpcb.put(cb.get());
            }
            tmpcb.rewind();
            rot13(tmpcb);
            baseEncoder.reset();
            CoderResult cr = baseEncoder.encode(tmpcb, bb, true);
            // If error or output overflow, we need to adjust
            // the position of the input buffer to match what
            // was really consumed from the temp buffer. If
            // underflow (all input consumed) this is a no-op.
            cb.position(cb.position() - tmpcb.remaining());
            return cr;
        }

    }

    /**
     * The decoder implementation for the Rot13 Charset.
     */
    private class Rot13Decoder extends CharsetDecoder {
        private CharsetDecoder baseDecoder;

        /**
         * Constructor, call the superclass constructor with the Charset object
         * and pass alon the chars/byte values from the delegate decoder.
         */
        Rot13Decoder(Charset cs, CharsetDecoder baseDecoder) {
            super(cs, baseDecoder.averageCharsPerByte(), baseDecoder.maxCharsPerByte());

            this.baseDecoder = baseDecoder;
        }

        /**
         * Implementation of the decoding loop. First, we reset the decoder for
         * the base charset, then call it to decode the bytes into characters,
         * saving the result code. The CharBuffer is then de-scrambled with the
         * Rot13 algorithm and the result code is returned. This may not work
         * properly for non-Latin charsets.
         */
        protected CoderResult decodeLoop(ByteBuffer bb, CharBuffer cb) {
            baseDecoder.reset();

            CoderResult result = baseDecoder.decode(bb, cb, true);

            rot13(cb);

            return (result);
        }
        
    }

}
