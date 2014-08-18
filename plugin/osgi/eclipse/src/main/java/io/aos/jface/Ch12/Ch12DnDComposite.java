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
package io.aos.jface.Ch12;

import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.*;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

public class Ch12DnDComposite extends Composite {

    public Ch12DnDComposite(Composite parent) {
        super(parent, SWT.NONE);

        FillLayout layout = new FillLayout();
        setLayout(layout);

        Text leftText = new Text(this, SWT.MULTI);
        Text rightText = new Text(this, SWT.MULTI);
        
        createDragSource(leftText);
        createDragSource(rightText);
        
        createDropTarget(leftText);
        createDropTarget(rightText);                
    }

    private void createDropTarget(final Text targetText)
    {
      Transfer[] types = new Transfer[] { TextTransfer.getInstance() };
      DropTarget dropTarget = new DropTarget(targetText, DND.DROP_COPY);
      dropTarget.setTransfer(types);
      
      dropTarget.addDropListener(new DropTargetListener() {
      
        public void dragEnter(DropTargetEvent event)
        {
        }
      
        public void dragLeave(DropTargetEvent event)
        {
        }
      
        public void dragOperationChanged(DropTargetEvent event)
        {
        }
      
        public void dragOver(DropTargetEvent event)
        {
        }
      
        public void drop(DropTargetEvent event)
        {
          String data = (String)event.data;
          targetText.append(data);
        }
      
        public void dropAccept(DropTargetEvent event)
        {
        }
      } );
    }

    private void createDragSource(final Text sourceText)
    {
      Transfer[] types = new Transfer[]{ TextTransfer.getInstance() };
      DragSource dragSource = new DragSource(sourceText, DND.DROP_COPY);
      dragSource.setTransfer(types);
      dragSource.addDragListener(new DragSourceListener() {
      
        public void dragStart(DragSourceEvent event)
        {
          if(sourceText.getSelectionText().length() > 0)
          {
            event.doit = true;
          }
        }
      
        public void dragSetData(DragSourceEvent event)
        {
          event.data = sourceText.getSelection();
        }
      
        public void dragFinished(DragSourceEvent event)
        {
          //do nothing
        }
      } );
    }
}
