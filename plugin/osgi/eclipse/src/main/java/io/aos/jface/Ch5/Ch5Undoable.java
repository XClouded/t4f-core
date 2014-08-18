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

import java.util.LinkedList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.*;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

public class Ch5Undoable extends Composite
{
  private static final int MAX_STACK_SIZE = 25;
  private List undoStack;
  private List redoStack;

  private StyledText styledText;
  
  public Ch5Undoable(Composite parent)
  {
    super(parent, SWT.NONE);
    undoStack = new LinkedList();
    redoStack = new LinkedList();
    buildControls();
  }
  
  private void buildControls()
  {
    this.setLayout(new FillLayout());
    styledText = new StyledText(this, SWT.MULTI | SWT.V_SCROLL);
    
    styledText.addExtendedModifyListener(
      new ExtendedModifyListener() {
        public void modifyText(ExtendedModifyEvent event)
        {
          String currText = styledText.getText();
          String newText = currText.substring(event.start, 
                                      event.start + event.length);
          if( newText != null && newText.length() > 0 )
          {
            if( undoStack.size() == MAX_STACK_SIZE )
            {
              undoStack.remove( undoStack.size() - 1 );
            }
            undoStack.add(0, newText);
          }
    } } );
    
    styledText.addKeyListener(new KeyAdapter() {
      public void keyPressed(KeyEvent e) 
      {
        switch(e.keyCode)
        {
          case SWT.F1:
            undo(); break;
          case SWT.F2:
            redo(); break;
          default:
            //ignore everything else
        }
    } } );
  }
  
  private void undo()
  {
    if( undoStack.size() > 0 )
    {
      String lastEdit = (String)undoStack.remove(0);
      int editLength = lastEdit.length();
      String currText = styledText.getText();
      int startReplaceIndex = currText.length() - editLength;
      styledText.replaceTextRange(startReplaceIndex, 
                                  editLength, "");
      redoStack.add(0, lastEdit);    
    }
  }
  
  private void redo()
  {
    if( redoStack.size() > 0 )
    {
      String text = (String)redoStack.remove(0);
      moveCursorToEnd();
      styledText.append(text);
      moveCursorToEnd();
    }
  }

  private void moveCursorToEnd()
  {
    styledText.setCaretOffset(styledText.getText().length());
  }
}
