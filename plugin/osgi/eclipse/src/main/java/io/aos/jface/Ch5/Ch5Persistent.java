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

import java.io.*;
import java.util.StringTokenizer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.*;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

public class Ch5Persistent extends Composite
{
  private static final String END_STYLES_MARK = "***EndStyles***";
  private static final String START_STYLES_MARK = "***Styles***";
  private static final String START_TEXT_MARK = "***Text***";
  private static final String FILE_NAME = "editorData.txt";
  
  private boolean doBold = false;

  private StyledText styledText;
  
  public Ch5Persistent(Composite parent)
  {
    super(parent, SWT.NONE);
    buildControls();
  }
  
  protected void buildControls()
  {
    this.setLayout(new FillLayout());
    styledText = new StyledText(this, SWT.MULTI | SWT.V_SCROLL);
    load();
    
    styledText.addExtendedModifyListener(
      new ExtendedModifyListener() {
        public void modifyText(ExtendedModifyEvent event)
        {
          if(doBold)
          {
            StyleRange style = new StyleRange(event.start, 
                                              event.length, 
                                              null, null, 
                                              SWT.BOLD);
            styledText.setStyleRange(style);
          }
        } 
    } );
    
    styledText.addKeyListener(new KeyAdapter() {
      public void keyPressed(KeyEvent e) 
      {
        switch(e.keyCode)
        {
          case SWT.F1:
            toggleBold(); break;
          default:
            //ignore everything else
        }
    } } );
  }
  
  private void toggleBold()
  {
    doBold = !doBold;
    if(styledText.getSelectionCount() > 0)
    {
      Point selectionRange = styledText.getSelectionRange();
      StyleRange style = new StyleRange(selectionRange.x, 
                                        selectionRange.y, 
                                        null, null, 
                                        doBold ? SWT.BOLD 
                                               : SWT.NORMAL);
      styledText.setStyleRange(style);
    }
  }
  
  private void load()
  {
    File file = new File(FILE_NAME);
    if( file.exists() )
    {
      try
      {
        BufferedReader reader = new BufferedReader(
                                    new FileReader(file));
        String currLine = reader.readLine();
        
        StringTokenizer tokenizer = new StringTokenizer(currLine);
        tokenizer.nextToken(); //discard START_TEXT_MARKER
        String contentLengthString = tokenizer.nextToken();
        int contentLength = Integer.parseInt(contentLengthString);
        
        readContent(reader, contentLength);
        
        //find the beginning of the styles section
        while( ((currLine = reader.readLine()) != null)
                && !START_STYLES_MARK.equals(currLine));
        
        readStyles(reader, currLine);
        reader.close();
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
    }
  }

  private void readStyles(BufferedReader reader, String currLine)
    throws IOException
  {
    while(!END_STYLES_MARK.equals(currLine))
    {
      currLine = reader.readLine();
      if(!END_STYLES_MARK.equals(currLine))
        buildOneStyle(currLine);
    }
  }

  private void readContent(BufferedReader reader, 
                            int contentLength)
    throws IOException
  {
    char[] buffer = new char[contentLength];
    reader.read(buffer, 0, contentLength);

    styledText.append(new String(buffer));
  }

  private void buildOneStyle(String styleText)
  {
    StringTokenizer tokenizer = new StringTokenizer(styleText);
    int startPos = Integer.parseInt(tokenizer.nextToken());
    int length = Integer.parseInt(tokenizer.nextToken());
    
    StyleRange style = new StyleRange(startPos, length, 
                                      null, null, SWT.BOLD);
    styledText.setStyleRange(style);
  }

  private void save()
  {
    try
    {
      PrintWriter writer = new PrintWriter(
                            new BufferedWriter(
                              new FileWriter(FILE_NAME)));
      String text = styledText.getText();
      writer.println(START_TEXT_MARK + " " + text.length());
      writer.println(text);
      writer.println(START_STYLES_MARK);
      StyleRange[] styles = styledText.getStyleRanges();
      for(int i = 0; i < styles.length; i++)
      {
        writer.println(styles[i].start + " " + styles[i].length);
      }
      writer.println(END_STYLES_MARK);
      writer.flush();
      writer.close();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
}
