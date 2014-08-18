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
package io.aos.guice.named;

import static junit.framework.Assert.assertNotNull;
import io.aos.guice.named.SimpleConfigurationMapModule;
import io.aos.guice.named.SimpleMapConfiguration;

import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class SimpleConfigurationTest {

    @Test
    public void testSimpleConfiguration() {
        test(Guice.createInjector(new SimpleConfigurationMapModule()));
    }

    @Test
    public void testSimpleConfigurationMap() {
        test(Guice.createInjector(new SimpleConfigurationMapModule()));
    }

    private void test(Injector injector) {
        SimpleMapConfiguration instance = injector.getInstance(SimpleMapConfiguration.class);
        assertNotNull(instance.getJdbcUrl());
        assertNotNull(instance.getLoginTimeoutSeconds());
    }

}
