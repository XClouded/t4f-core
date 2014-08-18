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
package io.aos.jface.Ch6;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.*;

public class RadialLayout extends Layout
{
  public RadialLayout()
  {
    super();
  }

  protected Point computeSize(Composite composite, 
                              int wHint, int hHint, 
                              boolean flushCache)
  {
    Point maxDimensions = 
            calculateMaxDimensions(composite.getChildren()); 
    int stepsPerHemisphere = 
            stepsPerHemisphere(composite.getChildren().length);

    int maxWidth = maxDimensions.x;
    int maxHeight = maxDimensions.y;
    
    int dimensionMultiplier = (stepsPerHemisphere + 1);
    int controlWidth = maxWidth * dimensionMultiplier;
    int controlHeight = maxHeight * dimensionMultiplier;
    int diameter = Math.max(controlWidth, controlHeight);
    Point preferredSize = new Point(diameter, 
                                    diameter);
    
    if(wHint != SWT.DEFAULT)
    {
      if(preferredSize.x > wHint)
      {
        preferredSize.x = wHint;
      }
    }
    
    if(hHint != SWT.DEFAULT)
    {
      if(preferredSize.y > hHint)
      {
        preferredSize.y = hHint;
      }
    }
    
    return preferredSize;
  }

  protected void layout(Composite composite, boolean flushCache)
  {
    Point[] positions = calculateControlPositions(composite);
    Control[] controls = composite.getChildren();
    for(int i = 0; i < controls.length; i++)
    {
      Point preferredSize = controls[i].computeSize(SWT.DEFAULT,
                                                   SWT.DEFAULT);
      controls[i].setBounds(positions[i].x, positions[i].y,
                            preferredSize.x, preferredSize.y);
    }
  }
  
  private Point[] calculateControlPositions(Composite composite)
  {
    int controlCount = composite.getChildren().length;
    int stepsPerHemisphere = stepsPerHemisphere(controlCount);
    Point[] positions = new Point[controlCount];
    
    Point maxControlDimensions = 
            calculateMaxDimensions(composite.getChildren());
    int maxControlWidth = maxControlDimensions.x;
    
    Rectangle clientArea = composite.getClientArea();
    int smallestDimension = 
            Math.min(clientArea.width, clientArea.height);
    int radius = (smallestDimension / 2) - maxControlWidth;
    Point center = new Point(clientArea.width / 2, 
                              clientArea.height / 2);
    long radiusSquared = radius * radius;
    
    int stepXDistance = 
          calculateStepDistance(radius * 2, stepsPerHemisphere);
    
    int signMultiplier = 1;
    int x = -radius;
    int y;
    Control[] controls = composite.getChildren();
    for(int i = 0; i < controlCount; i++)
    {
      Point currSize = controls[i].getSize();
      long xSquared = x * x;
      
      int sqrRoot = (int)Math.sqrt(radiusSquared - xSquared);
      y = signMultiplier * sqrRoot;
      int translatedX = x + center.x;
      int translatedY = y + center.y;
      positions[i] = new Point(translatedX - (currSize.x  / 2),
                               translatedY - (currSize.y / 2) );
      
      x = x + (signMultiplier * stepXDistance);
      //we've finished the upper hemisphere, now do the lower
      if(x >= radius)
      {
        x = radius - (x - radius);
        signMultiplier = -1;
      }
    }
    
    return positions;
  }
  
  private Point calculateMaxDimensions(Control[] controls)
  {
    Point maxes = new Point(0, 0);
    
    for(int i = 0; i < controls.length; i++)
    {
      Point controlSize =
              controls[i].computeSize(SWT.DEFAULT, SWT.DEFAULT);
      maxes.x = Math.max(maxes.x, controlSize.x);
      maxes.y = Math.max(maxes.y, controlSize.y);
    }
    
    return maxes;
  }

  private int stepsPerHemisphere(int totalObjects)
  {
    return (totalObjects / 2) - 1;
  }
  
  private int calculateStepDistance(int clientAreaDimensionSize,
                                    int stepCount)
  {
    return clientAreaDimensionSize / (stepCount + 1);
  }

}
