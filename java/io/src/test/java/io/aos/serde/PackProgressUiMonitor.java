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
package io.aos.serde;

import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.jar.Pack200;

import javax.swing.ProgressMonitor;

public class PackProgressUiMonitor extends ProgressMonitor implements PropertyChangeListener {

    public PackProgressUiMonitor(Component parent) {
        super(parent, null, "Packing", -1, 100);
    }

    public void propertyChange(PropertyChangeEvent event) {
        if (event.getPropertyName().equals(Pack200.Packer.PROGRESS)) {
            String newValue = (String) event.getNewValue();
            int value = Integer.parseInt(newValue);
            this.setProgress(value);
        }
    }

}
