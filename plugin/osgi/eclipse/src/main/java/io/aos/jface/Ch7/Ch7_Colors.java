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
import org.eclipse.swt.events.*;

public class Ch7_Colors extends Canvas 
{

  public Ch7_Colors(Composite parent) 
  {
	super(parent, SWT.NONE);
	setBackground(this.getDisplay().
	  getSystemColor(SWT.COLOR_DARK_GRAY));
	addPaintListener(drawListener);
  }
	
  PaintListener drawListener = new PaintListener() 
  {
	public void paintControl(PaintEvent pe) 
	{
	  Display disp = pe.display;
	  Color light_gray = new Color(disp, 0xE0, 0xE0, 0xE0);
	  GC gc = pe.gc;
	  gc.setBackground(light_gray);
	  gc.fillPolygon(new int[] {20, 20, 60, 50, 100, 20});
	  gc.fillOval(120, 30, 50, 50);
	  light_gray.dispose();
	}
  };
}
    
		


