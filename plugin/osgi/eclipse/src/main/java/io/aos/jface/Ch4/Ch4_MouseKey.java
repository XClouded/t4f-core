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
package io.aos.jface.Ch4;
import org.eclipse.swt.events.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.*;

public class Ch4_MouseKey extends Composite 
{ 
  Label output; 
	
  Ch4_MouseKey(Composite parent) 
  { 
  	super(parent, SWT.NULL);
  	Button typed = new Button(this, SWT.PUSH); 
  	typed.setText("Typed"); 
  	typed.setLocation(2,10); 
  	typed.pack(); 
  	
  	typed.addKeyListener(new KeyAdapter() 
    { 
      public void keyPressed(KeyEvent e) 
      { 
        keyHandler(); 
      } 
    }); 
  	Button untyped = new Button(this, SWT.PUSH); 
  	untyped.setText("Untyped"); 
  	untyped.setLocation(80,10); 
  	untyped.pack(); 
  	untyped.addListener(SWT.MouseEnter, UntypedListener); 
  	untyped.addListener(SWT.MouseExit, UntypedListener); 
  	
  	output = new Label(this, SWT.SHADOW_OUT); 
  	output.setBounds(40,70,90,40); 
  	output.setText("No Event"); 
  	
  	pack(); 
  } 
  
  Listener UntypedListener = new Listener() 
  { 
  	public void handleEvent(Event event) 
  	{ 
      switch (event.type) 
	  { 
        case SWT.MouseEnter: 
          output.setText("Mouse Enter"); 
          break; 
        case SWT.MouseExit: 
          output.setText("Mouse Exit"); 
          break; 
        } 
      } 
  	}; 
  	
  void keyHandler() 
  { 
  	output.setText("Key Event"); 
  }
}
