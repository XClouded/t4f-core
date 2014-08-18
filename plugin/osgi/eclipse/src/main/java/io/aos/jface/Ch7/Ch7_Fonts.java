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

public class Ch7_Fonts extends Canvas 
{
  static Shell mainShell;
  static Composite comp;
  FontData fontdata;

  public Ch7_Fonts(Composite parent) 
  {			
	super(parent, SWT.BORDER);
	parent.setSize(600, 200);
	addPaintListener(DrawListener);
	comp = this;
	mainShell = parent.getShell();

	Button fontChoice = new Button(this, SWT.CENTER);
	fontChoice.setBounds(20,20,100,20);
	fontChoice.setText("Choose font");
	fontChoice.addMouseListener(new MouseAdapter() 
	{
	  public void mouseDown(MouseEvent me) 
	  {
		FontDialog fd = new FontDialog(mainShell);
		fontdata = fd.open();
		comp.redraw();
	  }
	});
  }
	
  PaintListener DrawListener = new PaintListener() 
  {
	public void paintControl(PaintEvent pe) 
	{
	  Display disp = pe.display;
	  GC gc = pe.gc;
	  gc.setBackground(pe.display.getSystemColor(SWT.COLOR_DARK_GRAY));
	  if (fontdata != null) 
	  {
		Font GCFont = new Font(disp, fontdata);
		gc.setFont(GCFont);
		FontMetrics fm = gc.getFontMetrics();
		gc.drawText("The average character width for this font is " + 
		  fm.getAverageCharWidth() + " pixels.", 20, 60);
		gc.drawText("The ascent for this font is " + fm.getAscent() + " pixels.", 20, 100, true);
		gc.drawText("The &descent for this font is " + fm.getDescent()+ " pixels.", 20, 140, SWT.DRAW_MNEMONIC|SWT.DRAW_TRANSPARENT);
		GCFont.dispose();
	  }
	}
  };
}
 
		


