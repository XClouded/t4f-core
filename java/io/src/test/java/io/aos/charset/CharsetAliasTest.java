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
package io.aos.charset;

import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Map;

import org.junit.Test;

public class CharsetAliasTest {

    @Test
    public void testAlias() {
        Map<String, Charset> charsets = Charset.availableCharsets();
        Iterator<Charset> iterator = charsets.values().iterator();
        while (iterator.hasNext()) {
            Charset cs = (Charset) iterator.next();
            System.out.print(cs.displayName());
            if (cs.isRegistered()) {
                System.out.print(" (registered): ");
            }
            else {
                System.out.print(" (unregistered): ");
            }
            System.out.print(cs.name());
            Iterator<String> names = cs.aliases().iterator();
            while (names.hasNext()) {
                System.out.print(", ");
                System.out.print(names.next());
            }
            System.out.println();
        }
    }

}
