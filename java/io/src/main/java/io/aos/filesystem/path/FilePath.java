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
package io.aos.filesystem.path;

import java.io.File;
import java.io.IOException;

public class FilePath {
    private String sourceDirectoryName;
    private String canonicalPath;
    private String pakkage;

    public FilePath(Class<?> clazz, String sourceDirectoryName) throws IOException {
        this.sourceDirectoryName = sourceDirectoryName;
        File file = new File(".");
        canonicalPath = file.getCanonicalPath();
        System.out.println("File canonical Path=" + canonicalPath);
        pakkage = clazz.getPackage().getName();
        pakkage = pakkage.replace('.', File.separatorChar);
        System.out.println("Formatted Package=" + pakkage);
    }

    public String path(String fileName) {
        return canonicalPath + File.separatorChar + sourceDirectoryName + File.separatorChar + pakkage + File.separatorChar + fileName;
    }

}
