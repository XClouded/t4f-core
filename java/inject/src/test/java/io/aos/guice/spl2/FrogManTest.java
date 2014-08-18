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
package io.aos.guice.spl2;

import io.aos.guice.spl2.FrogMan;
import io.aos.guice.spl2.Vehicle;

import java.io.IOException;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class FrogManTest {

    @Before
    public void setUp() throws Exception {
    }

    static class MockVehicle implements Vehicle {

        boolean didZoom;

        public String zoom() {
            this.didZoom = true;
            return "Mock Vehicle Zoomed.";
        }

    }

    @Test
    public void testFightCrime() throws IOException {
        MockVehicle mockVehicle = new MockVehicle();
        StringBuilder recorder = new StringBuilder();

        FrogMan hero = new FrogMan(mockVehicle);
        // FrogMan hero = new FrogMan();
        // hero.configure(mockVehicle, recorder);
        hero.recorder = recorder;
        hero.fightCrime();

        Assert.assertTrue(mockVehicle.didZoom);

    }

}
