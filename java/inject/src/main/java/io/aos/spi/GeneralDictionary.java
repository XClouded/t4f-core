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
 * GeneralDictionary.java
 *
 * Copyright 2007 Sun Microsystems, Inc. ALL RIGHTS RESERVED Use of
 * this software is authorized pursuant to the terms of the license 
 * found at http://developers.sun.com/berkeley_license.html.
 *
 */
package io.aos.spi;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 *
 */
public class GeneralDictionary implements Dictionary {

    private SortedMap<String, String> map;

    /** Creates a new instance of GeneralDictionary */
    public GeneralDictionary() {
        map = new TreeMap<String, String>();
        map.put("book", "a set of written or printed pages, usually bound with " + "a protective cover");
        map.put("editor", "a person who edits");
    }

    @Override
    public String getDefinition(String word) {
        return map.get(word);
    }

}
