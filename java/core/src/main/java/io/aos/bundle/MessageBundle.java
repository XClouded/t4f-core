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

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class MessageBundle {
    private static final String BUNDLE_NAME = "io.aos.bundle.messages";

    private MessageBundle() {
        /* Only Static */
    }

    public static String getString(String key) {
        return getString(key, Locale.ENGLISH);
    }

    public static String getString(String key, Locale locale) {
        try {
            return ResourceBundle.getBundle(BUNDLE_NAME, locale).getString(key);
        } catch (MissingResourceException e) {
            return "!! " + key + " !!";
        }
    }

    public static String getString(String key, Object... params) {
        try {
            return MessageFormat.format(ResourceBundle.getBundle(BUNDLE_NAME).getString(key), params);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }

}
