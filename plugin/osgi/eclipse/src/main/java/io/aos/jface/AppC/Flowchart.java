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
package io.aos.jface.AppC;

import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Flowchart {
    
    public static void main(String... args) {

        Shell shell = new Shell();
        shell.setSize(200, 300);
        shell.open();
        shell.setText("Flowchart");
        LightweightSystem lws = new LightweightSystem(shell);
        ChartFigure flowchart = new ChartFigure();
        lws.setContents(flowchart);

        TerminatorFigure start = new TerminatorFigure();
        start.setName("Start");
        start.setBounds(new Rectangle(40, 20, 80, 20));
        DecisionFigure dec = new DecisionFigure();
        dec.setName("Should I?");
        dec.setBounds(new Rectangle(30, 60, 100, 60));
        ProcessFigure proc = new ProcessFigure();
        proc.setName("Do it!");
        proc.setBounds(new Rectangle(40, 140, 80, 40));
        TerminatorFigure stop = new TerminatorFigure();
        stop.setName("End");
        stop.setBounds(new Rectangle(40, 200, 80, 20));

        PathFigure path1 = new PathFigure();
        path1.setSourceAnchor(start.outAnchor);
        path1.setTargetAnchor(dec.inAnchor);
        PathFigure path2 = new PathFigure();
        path2.setSourceAnchor(dec.yesAnchor);
        path2.setTargetAnchor(proc.inAnchor);
        PathFigure path3 = new PathFigure();
        path3.setSourceAnchor(dec.noAnchor);
        path3.setTargetAnchor(stop.inAnchor);
        PathFigure path4 = new PathFigure();
        path4.setSourceAnchor(proc.outAnchor);
        path4.setTargetAnchor(stop.inAnchor);

        flowchart.add(start);
        flowchart.add(dec);
        flowchart.add(proc);
        flowchart.add(stop);
        flowchart.add(path1);
        flowchart.add(path2);
        flowchart.add(path3);
        flowchart.add(path4);

        new Dnd(start);
        new Dnd(proc);
        new Dnd(dec);
        new Dnd(stop);

        Display display = Display.getDefault();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch())
                display.sleep();
        }
    }
}
