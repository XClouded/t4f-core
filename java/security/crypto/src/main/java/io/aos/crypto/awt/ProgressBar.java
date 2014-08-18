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
package io.aos.crypto.awt;

import java.awt.*;

public class ProgressBar extends Canvas {
    private int mLevel;
    private int mMaximum;
    private Color mFrameColor;

    public ProgressBar() {
        this(100);
    }

    public ProgressBar(int max) {
        setForeground(Color.blue);
        mFrameColor = Color.black;
        setMaximum(max);
        setLevel(0);
    }

    public void setMaximum(int max) {
        mMaximum = max;
        repaint();
    }

    public void setLevel(int level) {
        mLevel = (level > mMaximum) ? mMaximum : level;
        repaint();
    }

    public void update(Graphics g) {
        paint(g);
    }

    public void paint(Graphics g) {
        Dimension d = getSize();
        double ratio = (double) ((double) mLevel / (double) mMaximum);
        int x = (int) ((double) d.width * ratio);

        g.setColor(mFrameColor);
        g.drawRect(0, 0, d.width - 1, d.height - 1);

        g.setColor(getForeground());
        g.fillRect(1, 1, x, d.height - 2);

        g.setColor(getBackground());
        g.fillRect(x + 1, 1, d.width - 2 - x, d.height - 2);
    }

    public Dimension getMinimumSize() {
        return new Dimension(10, 1);
    }

    public Dimension getPreferredSize() {
        return new Dimension(100, 10);
    }
}
