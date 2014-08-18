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
package io.aos.aop.proxy.p1;

import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

/**
 * Utility Class to assist in Properties management and parsing.
 */
public class PropertyUtil {

    public interface Delims {
        public static final String seperators = ",";
        public static final char dot = '.';
    }

    private ResourceBundle bundle;
    private String context;

    public static PropertyUtil get(String baseName) {
        ResourceBundle bundle = ResourceBundle.getBundle(baseName);
        return new PropertyUtil(bundle, null);
    }

    public static PropertyUtil get(String baseName, String context) {
        ResourceBundle bundle = ResourceBundle.getBundle(baseName);
        return new PropertyUtil(bundle, context);
    }

    public static PropertyUtil get(ResourceBundle bundle, String context) {
        return new PropertyUtil(bundle, context);
    }

    private PropertyUtil(ResourceBundle bundle, String context) {
        this.bundle = bundle;
        this.context = context;

    }

    public String getAsString(String key) {
        try {
            String newKey = concatKeys(context, key);
            return (String) bundle.getString(newKey);

        } catch (MissingResourceException e) {
            return null;
        }
    }

    public String getAsStringNoContext(String key) {
        try {
            return (String) bundle.getString(key);

        } catch (MissingResourceException e) {
            return null;
        }
    }

    /**
     * Break up a propety value into a list of strings
     */
    public static List<String> toList(String propertyValue) {
        if (propertyValue == null)
            return new ArrayList();
        StringTokenizer t = new StringTokenizer(propertyValue,
                Delims.seperators);
        ArrayList l = new ArrayList();
        while (t.hasMoreTokens()) {
            String token = t.nextToken().trim();
            if (token.length() > 0)
                l.add(token);
        } // end while
        return l;
    }

    /**
     * Create a new key from two keys
     */
    public static String concatKeys(String s1, String s2) {
        if (s1 == null)
            return s2;
        return s1 + Delims.dot + s2;
    }

    /**
     * Create a new key from a list keys
     */
    public static String concatKeys(String[] keys) {
        StringBuffer newKey = new StringBuffer();
        for (int i = 0; i < keys.length; ++i) {
            newKey.append(keys[i]);
            if (i < (keys.length - 1)) {
                newKey.append(Delims.dot);
            }
        }
        return newKey.toString();
    }

    /**
     * convert a property value to an int primitive
     */
    public static int getAsInt(String propertyValue) {

        try {
            return Integer.parseInt(propertyValue);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * convert a property value to a long primitive
     */
    public static long getAsLong(String propertyValue) {

        try {
            return Long.parseLong(propertyValue);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * convert a property value to boolean primitive
     */
    public static boolean getAsBoolean(String propertyValue) {
        if (propertyValue == null)
            return false;
        if (propertyValue.length() == 0)
            return false;
        if (propertyValue.equalsIgnoreCase("on"))
            return true;
        if (propertyValue.equalsIgnoreCase("off"))
            return false;
        if (propertyValue.equalsIgnoreCase("y"))
            return true;
        if (propertyValue.equalsIgnoreCase("n"))
            return false;
        return Boolean.getBoolean(propertyValue);
    }

}
