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
package io.aos.jface.Ch3;
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;

public class Ch3_Composite extends Composite
{
  
  public Ch3_Composite(Composite parent)
  {
    super(parent, SWT.NONE);
    parent.getShell().setText("Chapter 3 Composite");
    
    Ch3_Group cc1 = new Ch3_Group(this);
    cc1.setLocation(0,0);
    cc1.pack();
    
    Ch3_SashForm cc2 = new Ch3_SashForm(this);
    cc2.setLocation(125,25);
    cc2.pack();
    
    pack();
  }
}
