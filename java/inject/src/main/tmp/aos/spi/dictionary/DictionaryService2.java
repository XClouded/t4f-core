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
 * DictionaryService.java
 *
 * Copyright 2007 Sun Microsystems, Inc. ALL RIGHTS RESERVED Use of
 * this software is authorized pursuant to the terms of the license 
 * found at http://developers.sun.com/berkeley_license.html.
 *
 *
 */

package io.aos.spi.dictionary;

import java.util.Collection;
import org.openide.util.Lookup;
import org.openide.util.Lookup.Result;
import org.openide.util.Lookup.Template;

/**
 *
 * @author John O'Conner
 */
public class DictionaryService2 {
    
    private static DictionaryService2 service;
    private Lookup dictionaryLookup;
    private Collection<Dictionary> dictionaries;
    private Template dictionaryTemplate;
    private Result dictionaryResults;
    
    /**
     * Creates a new instance of DictionaryService
     */
    private DictionaryService2() {
        dictionaryLookup = Lookup.getDefault();
        dictionaryTemplate = new Template(Dictionary.class);
        dictionaryResults = dictionaryLookup.lookup(dictionaryTemplate);
        dictionaries = dictionaryResults.allInstances();
    }
    
    public static synchronized DictionaryService2 getInstance() {
        if (service == null) {
            service = new DictionaryService2();
        }
        return service;
    }
    
    
    public String getDefinition(String word) {
        String definition = null;
        for(Dictionary d: dictionaries) {
            definition = d.getDefinition(word);
            if (d != null) break;
        }
        return definition;
    }
    
    public static void main(String... args) {
        DictionaryService2 lookup = DictionaryService2.getInstance();
        String def = lookup.getDefinition("book");
        System.out.println("def: " + def);
    }
}
