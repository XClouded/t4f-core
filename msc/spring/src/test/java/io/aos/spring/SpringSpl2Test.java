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
package io.aos.spring;

import io.aos.spring.ModelSplParent;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringSpl2Test {

    @Test
    public void test() {

        ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"/aos/t4f/spring/spring-beans-spl-2.xml"});
        
        ModelSplParent modelSplParent = context.getBean("modelSplParent", ModelSplParent.class);

        System.out.println("modelSplParent Hash Code=" + modelSplParent.hashCode());
        System.out.println("modelSplParent.modelSplChild Hash Code=" + modelSplParent.getModelSplChild().hashCode());

    }

}
