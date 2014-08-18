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
package io.aos.jface.Ch5;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

public class Ch5Capitalizer extends Composite
{

  public Ch5Capitalizer(Composite parent)
  {
    super(parent, SWT.NONE);
    buildControls();
  }
  
  private void buildControls()
  {
    this.setLayout(new FillLayout());
    Text text = new Text(this, SWT.MULTI | SWT.V_SCROLL);
    
    text.addVerifyListener(new VerifyListener() {
      public void verifyText(VerifyEvent e) {
        if( e.text.startsWith("1") )
        {
          e.doit = false;
        }
        else
        {
          e.text = e.text.toUpperCase();
        }
    } } );
  }
}
