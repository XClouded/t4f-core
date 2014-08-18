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
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.events.*;

public class Ch4_Composite extends Ch4_MouseKey 
{ 
  
  public Ch4_Composite(Composite parent) 
  { 
  	super(parent); 
  	Button launch = new Button(this, SWT.PUSH); 
  	launch.setText("Launch"); 
  	launch.setLocation(40,120); 
  	launch.pack();
  	
  	launch.addMouseListener(new MouseAdapter() 
  	{ 
  	  public void mouseDown(MouseEvent e) 
  	  { 
  	  	Ch4_Contributions sw = new Ch4_Contributions(); 
  	  	sw.open(); 
  	  } 
  	}); 
  }
}
