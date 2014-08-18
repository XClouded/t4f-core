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
package io.aos.jface.expl.explorer03;

import java.net.*;

import org.eclipse.jface.resource.*;

public class Util
{
  private static ImageRegistry image_registry;

  public static URL newURL(String url_name)
  {
    try
    {
      return new URL(url_name);
    }
    catch (MalformedURLException e)
    {
      throw new RuntimeException("Malformed URL " + url_name, e);
    }
  }

  public static ImageRegistry getImageRegistry()
  {
    if (image_registry == null)
    {
      image_registry = new ImageRegistry();
      image_registry.put(
        "folder",
        ImageDescriptor.createFromURL(newURL("file:icons/folder.gif")));
      image_registry.put(
        "file",
        ImageDescriptor.createFromURL(newURL("file:icons/file.gif")));
    }
    return image_registry;
  }
}
