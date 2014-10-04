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

import java.nio.charset.Charset;
import java.nio.charset.spi.CharsetProvider;
import java.util.HashSet;
import java.util.Iterator;

/**
 * A CharsetProvider class which makes available the charsets provided by
 * Ronsoft. Currently there is only one, namely the X-ROT13 charset. This is not
 * a registered IANA charset, so it's name begins with "X-" to avoid name
 * clashes with offical charsets.
 * 
 * To activate this CharsetProvider, it's necessary to add a file to the
 * classpath of the JVM runtime at the following location:
 * META-INF/services/java.nio.charsets.spi.CharsetProvider
 * 
 * That file must contain a line with the fully qualified name of this class on
 * a line by itself: com.ronsoft.books.nio.charset.RonsoftCharsetProvider
 * 
 * See the javadoc page for java.nio.charsets.spi.CharsetProvider for full
 * details.
 */
public class Rot13CharsetProvider extends CharsetProvider {
    private static final String CHARSET_NAME = "X-ROT13";
    private Charset rot13 = null;

    /**
     * Constructor, instantiate a Charset object and save the reference.
     */
    public Rot13CharsetProvider() {
        this.rot13 = new Rot13Charset(CHARSET_NAME, new String[0]);
    }

    /**
     * Called by Charset static methods to find a particular named Charset. If
     * it's the name of this charset (we don't have any aliases) then return the
     * Rot13 Charset, else return null.
     */
    public Charset charsetForName(String charsetName) {
        if (charsetName.equalsIgnoreCase(CHARSET_NAME)) {
            return (rot13);
        }
        return (null);
    }

    /**
     * Return an Iterator over the set of Charset objects we provide.
     * 
     * @return An Iterator object containing references to all the Charset
     *         objects provided by this class.
     */
    public Iterator<Charset> charsets() {
        HashSet<Charset> set = new HashSet<Charset>(1);
        set.add(rot13);
        return (set.iterator());
    }

}
