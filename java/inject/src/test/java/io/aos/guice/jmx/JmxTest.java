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
package io.aos.guice.jmx;

import io.aos.guice.jmx.Hello;
import io.aos.guice.jmx.HelloMBean;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;

import org.junit.Test;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.tools.jmx.Manager;

/**
 * Using Guice with JMX 2.0 Annotation:In Spring, you could easily enable any
 * bean as MBean by annotation. There is no such feature in Guice out of the
 * box, but it can be implemented easily. My requirement is to expose any method
 * or attribute of Guice injected objects to JMX with annotation only.
 * 
 * For non-Guice injected objects, or if you are not going to use Guice, you may
 * use the MBeans.register(Object) method to do the registration. JMX 2.0 style
 * annotation will be used. JMX 2.0 was originally a Java 7 feature, but was
 * postponed to Java 8. As the standard annotations are not available yet, we’ll
 * create @MBean, @ManagedAttribute, and @ManagedOperation under a custom
 * package.
 * 
 * There are a number of projects use annotation to register MBeans, for example
 * JGroups, Infinispan, Voldemort, Spring. I looked at their implementation and
 * at the end copied the implementation from JGroups. Let’s look at the code.
 * Notice that most of the code are simplified for publishing to this post. It
 * covers only the JMX/MBean related part, e.g. some log are not instantiated.
 * 
 * MBeans.java – a helper class, Notice that my implementation supports
 * registering singleton only. You have to change the objectName string to allow
 * using different type/name.
 * 
 * @Singleton public class MBeans{
 * 
 *            MBeanServer server =
 *            ManagementFactory.getPlatformMBeanServer();//@Inject MBeanServer
 *            server; public void register(Object... objects) throw Exception{
 *            for (Object object : objects){ ObjectName objectName = new
 *            ObjectName(String.format("MY_PACKAGE:type=%s",
 *            object.getClass().getSimpleName())); ResourceDMBean mbean = new
 *            ResourceDMBean(object);//JGroups' ResourceDMBean
 *            server.registerMBean(mbean, objectName); } } } ResourceDMBean.java
 *            – @See org.jgroups.jmx.jgroups.ResourceDMBean. Notice that if you
 *            don’t want to use @org.jgroups.annotations.MBean (i.e. under
 *            jgroups package), you’ll need to create the class in your own
 *            project and use your own MBean, ManagedOperation and
 *            ManagedAttribute annotations. JMXModule.java public class
 *            JMXModule implements Module{ final MBeans mbeans = new MBeans();
 * @Override public void configure(Binder binder){
 *           binder.bind(MBeans.class).toInstance(mbeans);
 *           binder.bindListener(new MBeanMatcher(), new TypeListener(){ public
 *           void hear(TypeLiteral type, TypeEncounter encounter){
 *           encounter.register(new InjectionListener(){
 * @Override public void afterInjection(Object o){ mbeans.register(o); } }); }
 *           }); } public static class MBeanMatcher implements Matcher{
 * @Override public boolean matches(TypeLiteral typeLiteral){ return
 *           typeLiteral.getRawType().isAnnotationPresent(MBean.class); }
 * @Override public Matcher and(Matcher matcher){ return null; }
 * @Override public Matcher or(Matcher matcher){ return null; } } }
 * 
 *           User code- It is recommended to expose only primary type as
 *           attribute or return type of operation
 * @MBean public class MyService{
 * @ManagedAttribute String text;
 * @ManagedOperation public String getDateAsString(){ return new
 *                   Date().toString(); } }
 * 
 *                   If you are not familar with Guice, remember to install the
 *                   JMXModule, e.g.
 * 
 *                   public class MyApplication{ public static void
 *                   main(String... args){ Injector g = Guice.createInjector(
 *                   new JMXModule() yourAppModule()); MyService my =
 *                   g.getInstance(MyService.class); while(true){} //loop! } }
 * 
 *                   For those who are familiar with Guice already, the magic is
 *                   at Binder.bindListener().
 * 
 * 
 */
public class JmxTest {

    @Test
    public void test() throws InterruptedException {
        Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(MBeanServer.class).toInstance(ManagementFactory.getPlatformMBeanServer());
                bind(HelloMBean.class).to(Hello.class).asEagerSingleton();
            }
        });
        Manager.manage("Guice Binding Information", injector);
    }

}
