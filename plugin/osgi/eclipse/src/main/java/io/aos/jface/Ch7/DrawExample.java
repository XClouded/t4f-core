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
package io.aos.jface.Ch7;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;

public class DrawExample
{

  public static void main (String [] args)
  {
    Display display = new Display();
    Shell shell = new Shell(display);
    shell.setText("Drawing Example");
    
    Canvas canvas = new Canvas(shell, SWT.NONE);
    canvas.setSize(150, 150);
    canvas.setLocation(20, 20);
    shell.open ();
    shell.setSize(200,220);
    
    GC gc = new GC(canvas);
    gc.drawRectangle(10, 10, 40, 45);
    gc.drawOval(65, 10, 30, 35);
    gc.drawLine(130, 10, 90, 80);
    gc.drawPolygon(new int[] {20, 70, 45, 90, 70, 70});
    gc.drawPolyline(new int[] {10,120,70,100,100,130,130,75});
    gc.dispose();
    
    while (!shell.isDisposed())
    {
      if (!display.readAndDispatch())
        display.sleep();
    }
    display.dispose();
  }
}
