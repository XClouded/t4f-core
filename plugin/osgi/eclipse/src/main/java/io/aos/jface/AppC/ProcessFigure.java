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

public class ProcessFigure extends ActivityFigure
{
  FixedAnchor inAnchor, outAnchor;
  public ProcessFigure()
  {
    inAnchor = new FixedAnchor(this);
    inAnchor.place = new Point(1, 0);
    targetAnchors.put("in_proc", inAnchor);
    outAnchor = new FixedAnchor(this);
    outAnchor.place = new Point(1, 2);
    sourceAnchors.put("out_proc", outAnchor);
  }
  
  public void paintFigure(Graphics g)
  {
    Rectangle r = bounds;
    g.drawText(message, r.x + r.width/4, r.y + r.height/4);
    g.drawRectangle(r.x, r.y, r.width-1, r.height-1);
  }
}
