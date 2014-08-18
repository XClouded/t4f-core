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
package io.aos.bundle;

import static org.junit.Assert.*;
import io.aos.bundle.MessageBundle;

import org.junit.Test;

public class MessageBundleTest {
    private static final String TEST_NAME = "test_name";
    private static final String TEST_TITLE = "test_title";
    private static final String TEST_GROUP = "test_group";

    @Test
    public void testMessage() {
        String message = MessageBundle.getString("key1", TEST_NAME, TEST_TITLE, TEST_GROUP);
        assertNotNull(message);
        assertTrue(message.contains(TEST_NAME));
        assertTrue(message.contains(TEST_TITLE));
        assertTrue(message.contains(TEST_GROUP));
        assertEquals("User test_name added new item test_title to group test_group", message);
    }

}
