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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;

import org.junit.Test;

public class FileAttributesTest {

    @Test
    public void test() throws IOException {

        Path p = Paths.get("pom.xml");

        BasicFileAttributes basicFileAttributes = Files.readAttributes(p, BasicFileAttributes.class,
                LinkOption.NOFOLLOW_LINKS);
        System.out.println("creationTime: " + basicFileAttributes.creationTime());
        System.out.println("lastAccessTime: " + basicFileAttributes.lastAccessTime());
        System.out.println("lastModifiedTime: " + basicFileAttributes.lastModifiedTime());
        System.out.println("isDirectory: " + basicFileAttributes.isDirectory());
        System.out.println("isSymbolicLink: " + basicFileAttributes.isSymbolicLink());
        System.out.println("size: " + basicFileAttributes.size());

        Set<PosixFilePermission> perms = PosixFilePermissions.fromString("rw-------");
        FileAttribute<Set<PosixFilePermission>> posixAttr = PosixFilePermissions.asFileAttribute(perms);
        Files.setPosixFilePermissions(p, perms);

    }

}
