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

public class Dnd extends MouseMotionListener.Stub
  implements MouseListener
{
  public Dnd(IFigure figure)
  {
    figure.addMouseMotionListener(this);
    figure.addMouseListener(this);
  }

  Point start;
  public void mouseReleased(MouseEvent e){}
  public void mouseClicked(MouseEvent e){}
  public void mouseDoubleClicked(MouseEvent e){}
  
  public void mousePressed(MouseEvent e)
  {
    start = e.getLocation();
  }
  
  public void mouseDragged(MouseEvent e)
  {
    Point p = e.getLocation();
    Dimension d = p.getDifference(start);
    start = p;
    Figure f = ((Figure)e.getSource());
    f.setBounds(f.getBounds().getTranslated(d.width, d.height));
  }
};
