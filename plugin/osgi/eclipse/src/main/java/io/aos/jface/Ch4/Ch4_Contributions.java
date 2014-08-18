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
import org.eclipse.jface.window.*;
import org.eclipse.jface.action.*;

public class Ch4_Contributions extends ApplicationWindow 
{ 
  StatusLineManager slm = new StatusLineManager(); 
  Ch4_StatusAction status_action = new Ch4_StatusAction(slm); 
  ActionContributionItem aci = new ActionContributionItem(status_action); 
  
  public Ch4_Contributions() 
  { 
  	super(null); 
  	addStatusLine(); 
  	addMenuBar(); 
  	addToolBar(SWT.FLAT | SWT.WRAP); 
  } protected 
  
  Control createContents(Composite parent) 
  { 
  	getShell().setText("Action/Contribution Example"); 
  	parent.setSize(290,150); 
  	aci.fill(parent); 
  	return parent; 
  } 
  
  public static void main(String... args) 
  { 
  	Ch4_Contributions swin = new Ch4_Contributions(); 
  	swin.setBlockOnOpen(true); 
  	swin.open(); 
  	Display.getCurrent().dispose();
  }
  
  protected MenuManager createMenuManager() 
  { 
  	MenuManager main_menu = new MenuManager(null); 
  	MenuManager action_menu = new MenuManager("Menu"); 
  	main_menu.add(action_menu); 
  	action_menu.add(status_action); 
  	return main_menu; 
  } 
  
  protected ToolBarManager createToolBarManager(int style) 
  { 
  	ToolBarManager tool_bar_manager = new ToolBarManager(style); 
  	tool_bar_manager.add(status_action); 
  	return tool_bar_manager; 
  } 
  
  protected StatusLineManager createStatusLineManager() 
  { 
  	return slm; 
  }
}
