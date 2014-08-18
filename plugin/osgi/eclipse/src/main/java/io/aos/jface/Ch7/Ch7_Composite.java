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
import java.io.*;
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;

public class Ch7_Composite extends Canvas
{
  public Ch7_Composite(Composite parent)
  {
    super(parent, SWT.BORDER);
    Ch7_Colors drawing = new Ch7_Colors(this);
    drawing.setBounds(20,20,200,100);
    Ch7_Fonts fontbox = new Ch7_Fonts(this);
    fontbox.setBounds(0,150,500,200);
    Ch7_Images flagmaker = new Ch7_Images();
    addPaintListener(new PaintListener()
    {
      public void paintControl(PaintEvent pe)
      {
        Display disp = pe.display;
        GC gc = pe.gc;
        InputStream is=getClass().getResourceAsStream("FlagGIF.gif");
        Image flag = new Image(disp, is);
        gc.drawImage(flag, 255, 10);
        flag.dispose();
      }
    });
  }
}

