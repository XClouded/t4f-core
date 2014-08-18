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
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.widgets.*;

public class Ch3_SashForm extends Composite
{
  public Ch3_SashForm(Composite parent)
  {
    super(parent, SWT.NONE);
    
    SashForm sf = new SashForm(this, SWT.VERTICAL);
    sf.setSize(120,80);
    
    Button button1 = new Button(sf, SWT.ARROW | SWT.UP);
    button1.setSize(120,40);
    
    Button button2 = new Button(sf, SWT.ARROW | SWT.DOWN);
    button2.setBounds(0,40,120,40);
  }
}


