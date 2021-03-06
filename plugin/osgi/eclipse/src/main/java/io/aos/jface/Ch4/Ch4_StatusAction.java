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
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.resource.ImageDescriptor;

public class Ch4_StatusAction extends Action
{
  StatusLineManager statman;
  short triggercount = 0;
    
  public Ch4_StatusAction(StatusLineManager sm)
  {
  	super("&Trigger@Ctrl+T", AS_PUSH_BUTTON);
    statman = sm;
    setToolTipText("Trigger the Action");
    setImageDescriptor(ImageDescriptor.createFromFile(this.getClass(),"eclipse.gif"));
  }

  public void run()
  {
  	triggercount++;
	statman.setMessage("The status action has fired. Count: " + triggercount);
  }
}

