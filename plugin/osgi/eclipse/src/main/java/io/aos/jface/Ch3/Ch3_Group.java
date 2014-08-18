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

public class Ch3_Group extends Composite
{
  public Ch3_Group(Composite parent)
  {
    super(parent, SWT.NONE);
    Group group = new Group(this, SWT.SHADOW_ETCHED_IN);
    group.setText("Group Label");
    
    Label label = new Label(group, SWT.NONE);
    label.setText("Two buttons:");
    label.setLocation(20,20);
    label.pack();
    
    Button button1 = new Button(group, SWT.PUSH);
    button1.setText("Push button");
    button1.setLocation(20,45);
    button1.pack();
    
    Button button2 = new Button(group, SWT.CHECK);
    button2.setText("Check button");
    button2.setBounds(20,75,90,30);
    group.pack();
  }
}
