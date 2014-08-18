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

import java.util.*;

public class WordTracker
{
  private int maxQueueSize;
  private List wordBuffer;
  private Map knownWords = new HashMap();
  

  public WordTracker(int queueSize)
  {
    maxQueueSize = queueSize;
    wordBuffer = new LinkedList();
  }

  public int getWordCount()
  {
    return wordBuffer.size();
  }

  public void add(String word)
  {
    if( wordIsNotKnown(word) )
    {
      flushOldestWord();
      insertNewWord(word);
    }
  }

  private void insertNewWord(String word)
  {
    wordBuffer.add(0, word);
    knownWords.put(word, word);
  }

  private void flushOldestWord()
  {
    if( wordBuffer.size() == maxQueueSize )
    {
      String removedWord = 
              (String)wordBuffer.remove(maxQueueSize - 1);
      knownWords.remove(removedWord);
    }
  }

  private boolean wordIsNotKnown(String word)
  {
    return knownWords.get(word) == null;
  }

  public List suggest(String word)
  {
    List suggestions = new LinkedList();
    for( Iterator i = wordBuffer.iterator(); i.hasNext(); )
    {
      String currWord = (String)i.next();
      if( currWord.startsWith(word) )
      {
        suggestions.add(currWord);
      }
    }
    return suggestions;
  }

}
