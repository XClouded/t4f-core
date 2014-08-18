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

import org.eclipse.swt.widgets.*;
import org.eclipse.draw2d.*;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.*;

public class Draw2D_Example 
{
  public static void main(String... args)
  {
    final Label label = new Label("Press a button!");  	
    Shell shell = new Shell();

    LightweightSystem lws = new LightweightSystem(shell);
    Figure parent = new Figure();
    parent.setLayoutManager(new XYLayout());
    lws.setContents(parent);
    
    Clickable above = new CheckBox("I'm above!");
    parent.add(above, new Rectangle(10,10,80,20));    
    ButtonModel aModel = new ToggleModel();
    aModel.addChangeListener(new ChangeListener() 
    {
      public void handleStateChanged(ChangeEvent e) 
      {
        label.setText("Above");
      }
    });
    above.setModel(aModel);
    
    Clickable below = new CheckBox("I'm below!");
    parent.add(below, new Rectangle(10,40,80,20));    
    ButtonModel bModel = new ToggleModel();
    bModel.addChangeListener(new ChangeListener() 
    {
      public void handleStateChanged(ChangeEvent e) 
      {
        label.setText("Below");
      }
    });
    below.setModel(bModel);
    
    ButtonGroup bGroup = new ButtonGroup();
    bGroup.add(aModel);
    bGroup.add(bModel);
    bGroup.setDefault(bModel);
    
    parent.add(label, new Rectangle(10,70,80,20));    
    shell.setSize(130,120);
    shell.open();
    shell.setText("Example");
    Display display = Display.getDefault();
    while (!shell.isDisposed()) 
    {
      if (!display.readAndDispatch())
        display.sleep ();
    }
  }
}
