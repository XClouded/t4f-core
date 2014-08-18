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

public class TerminatorFigure extends ActivityFigure
{
  FixedAnchor inAnchor, outAnchor;
  public TerminatorFigure()
  {
    inAnchor = new FixedAnchor(this);
    inAnchor.place = new Point(1, 0);
    targetAnchors.put("in_term",inAnchor);
    outAnchor = new FixedAnchor(this);
    outAnchor.place = new Point(1, 2);
    sourceAnchors.put("out_term",outAnchor);
  }
 
  public void paintFigure(Graphics g)
  {
    Rectangle r = bounds;
    g.drawArc(r.x + r.width/8, r.y, r.width/4, r.height-1, 90, 180);
    g.drawLine(r.x + r.width/4, r.y, r.x + 3*r.width/4, r.y);
    g.drawLine(r.x + r.width/4, r.y + r.height-1, r.x + 3*r.width/4,
    r.y + r.height-1);
    g.drawArc(r.x + 5*r.width/8, r.y, r.width/4, r.height-1, 270, 180);
    g.drawText(message, r.x+3*r.width/8, r.y+r.height/8);
  }
}
