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
package io.aos.jface.AppC;
import org.eclipse.draw2d.*;
import org.eclipse.draw2d.geometry.*;
import java.util.*;

abstract public class ActivityFigure
  extends Figure
{
  Rectangle r = new Rectangle();
  Hashtable targetAnchors = new Hashtable();
  Hashtable sourceAnchors = new Hashtable();
  String message = new String();
  
  public void setName(String msg)
  {
    message = msg;
    repaint();
  }
  
  public ConnectionAnchor ConnectionAnchorAt(Point p)
  {
    ConnectionAnchor closest = null;
    long min = Long.MAX_VALUE;
    Hashtable conn = getSourceConnectionAnchors();
    conn.putAll(getTargetConnectionAnchors());
    Enumeration e = conn.elements();
    while (e.hasMoreElements())
    {
      ConnectionAnchor c = (ConnectionAnchor) e.nextElement();
      Point p2 = c.getLocation(null);
      long d = p.getDistance2(p2);
      if (d < min)
      {
        min = d;
        closest = c;
      }
    }
    return closest;
  }
  
  public ConnectionAnchor getSourceConnectionAnchor(String name)
  {
    return (ConnectionAnchor)sourceAnchors.get(name);
  }
  
  public ConnectionAnchor getTargetConnectionAnchor(String name)
  {
    return (ConnectionAnchor)targetAnchors.get(name);
  }
  
  public String getSourceAnchorName(ConnectionAnchor c)
  {
    Enumeration enum1 = sourceAnchors.keys();
    String name;
    while (enum1.hasMoreElements())
    {
      name = (String)enum1.nextElement();
      if (sourceAnchors.get(name).equals(c))
      return name;
    }
    return null;
  }
  
  public String getTargetAnchorName(ConnectionAnchor c)
  {
    Enumeration enum1 = targetAnchors.keys();
    String name;
    while (enum1.hasMoreElements())
    {
      name = (String)enum1.nextElement();
      if (targetAnchors.get(name).equals(c))
      return name;
    }
    return null;
  }
  
  public ConnectionAnchor getSourceConnectionAnchorAt(Point p)
  {
    ConnectionAnchor closest = null;
    long min = Long.MAX_VALUE;
    Enumeration e = getSourceConnectionAnchors().elements();
    while (e.hasMoreElements())
    {
      ConnectionAnchor c = (ConnectionAnchor) e.nextElement();
      Point p2 = c.getLocation(null);
      long d = p.getDistance2(p2);
      if (d < min)
      {
        min = d;
        closest = c;
      }
    }
    return closest;
  }
  
  public Hashtable getSourceConnectionAnchors()
  {
    return sourceAnchors;
  }
  
  public ConnectionAnchor getTargetConnectionAnchorAt(Point p)
  {
    ConnectionAnchor closest = null;
    long min = Long.MAX_VALUE;
    Enumeration e = getTargetConnectionAnchors().elements();
    while (e.hasMoreElements())
    {
      ConnectionAnchor c = (ConnectionAnchor) e.nextElement();
      Point p2 = c.getLocation(null);
      long d = p.getDistance2(p2);
      if (d < min)
      {
        min = d;
        closest = c;
      }
    }
    return closest;
  }
  
  public Hashtable getTargetConnectionAnchors()
  {
    return targetAnchors;
  }
}
