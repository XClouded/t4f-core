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
package io.aos.jface.Ch7;
import org.eclipse.swt.graphics.*;

public class FlagGraphic 
{
	
  public FlagGraphic() 
  {
    int pix = 20;
    int numRows = 6;
    int numCols = 11;

    PaletteData PD = new PaletteData(new RGB[] 
	{
      new RGB(0x00, 0x00, 0x00),
      new RGB(0x80, 0x80, 0x80),
      new RGB(0xFF, 0xFF, 0xFF)
    });
	
    final ImageData FlagData = new ImageData(pix*numCols, pix*numRows, 2, PD);
    for(int x=0; x<pix*numCols; x++) 
    {
	  for(int y=0; y<pix*numRows; y++) 
	  {
        int value = (((x / pix) % 3) + (3 - ((y / pix) % 3))) % 3;
		FlagData.setPixel(x,y,value);
      }
    }
  }
}
