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
package io.aos.filesystem.filter;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

public class FileExtensionsFilter implements FilenameFilter  {
  List<String> extensions = new ArrayList<String>();

  public FileExtensionsFilter(String extension) {
    if (extension.indexOf('.') != -1) {
      extension = extension.substring(extension.lastIndexOf('.')+1);
    }
    extensions.add(extension);
  }

  public void addExtension(String extension) {
    if (extension.indexOf('.') != -1) {
      extension = extension.substring(extension.lastIndexOf('.')+1);
    }
    extensions.add(extension);
  }

  @Override
  public boolean accept(File directory, String filename) {
    String extension = filename.substring(filename.lastIndexOf('.')+1);
    if (extensions.contains(extension)) {
      return true;
    }
    return false;
  }

}
