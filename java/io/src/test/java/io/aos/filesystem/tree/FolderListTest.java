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
package io.aos.filesystem.tree;

import io.aos.filesystem.filter.FileExtensionFilter;

import java.io.File;

public class FolderListTest {

    public static void list(File dir) {
        String[] strs = dir.list();
        for (int i = 0; i < strs.length; i++) {
            System.out.println("listDirectory : " + strs[i]);
        }
    }

    public static void list(File dir, String pattern) {
        FileExtensionFilter nf = new FileExtensionFilter(pattern);
        String[] strs = dir.list(nf);
        for (int i = 0; i < strs.length; i++) {
            System.out.println("listDirectoryWithFilter with pattern='" + pattern + "' : " + strs[i]);
        }
    }

}
