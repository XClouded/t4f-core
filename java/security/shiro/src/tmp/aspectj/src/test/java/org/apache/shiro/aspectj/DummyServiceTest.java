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
package org.apache.shiro.aspectj;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 
 * @author J-C Desrochers
 *
 */
public class DummyServiceTest {

  private static DummyService _SECURED_SERVICE;
  private static DummyService _RESTRICTED_SERVICE;
  
  @BeforeClass
  public static void setUpClass() throws Exception {
    AspectjAnnotationsAuthorizingMethodInterceptor.LOG_INTERCEPTED_INVOCATIONS = true;
    Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiroDummyServiceTest.ini");
    SecurityManager securityManager = factory.getInstance();
    SecurityUtils.setSecurityManager(securityManager);
    
    _SECURED_SERVICE = new SecuredDummyService();
    _RESTRICTED_SERVICE = new RestrictedDummyService();
  }
  
  @AfterClass
  public static void tearDownClass() throws Exception {
    AspectjAnnotationsAuthorizingMethodInterceptor.LOG_INTERCEPTED_INVOCATIONS = false;
  }
  
  private Subject _subject;
  
  @Before
  public void setUp() throws Exception {
    _subject = SecurityUtils.getSubject();
    System.out.println("\n\n\n");
  }
  
  @After
  public void tearDown() throws Exception {
    _subject.logout();
  }
  
  
  
  private void loginAsGuest() {
    _subject.login(new UsernamePasswordToken("john", "doe"));
  }
  
  private void loginAsUser() {
    _subject.login(new UsernamePasswordToken("joe", "bob"));
  }
  
  private void loginAsAdmin() {
    _subject.login(new UsernamePasswordToken("root", "secret"));
  }
  
  
  // TEST ANONYMOUS
  
  @Test
  public void testAnonymous_asAnonymous() throws Exception {
    _SECURED_SERVICE.anonymous();
  }
  
  @Test
  public void testAnonymous_asGuest() throws Exception {
    loginAsGuest();
    _SECURED_SERVICE.anonymous();
  }
  
  @Test
  public void testAnonymous_asUser() throws Exception {
    loginAsUser();
    _SECURED_SERVICE.anonymous();
  }
  
  @Test
  public void testAnonymous_asAdmin() throws Exception {
    loginAsAdmin();
    _SECURED_SERVICE.anonymous();
  }

  
  // TEST GUEST
  
  @Test(expected=UnauthenticatedException.class)
  public void testGuest_asAnonymous() throws Exception {
    _SECURED_SERVICE.guest();
  }
  
  @Test
  public void testGuest_asGuest() throws Exception {
    loginAsGuest();
    _SECURED_SERVICE.guest();
  }
  
  @Test
  public void testGuest_asUser() throws Exception {
    loginAsUser();
    _SECURED_SERVICE.guest();
  }
  
  @Test
  public void testGuest_asAdmin() throws Exception {
    loginAsAdmin();
    _SECURED_SERVICE.guest();
  }

  
  // TEST PEEK
  
  @Test(expected=UnauthenticatedException.class)
  public void testPeek_asAnonymous() throws Exception {
    _SECURED_SERVICE.peek();
  }
  
  @Test
  public void testPeek_asGuest() throws Exception {
    loginAsGuest();
    _SECURED_SERVICE.peek();
  }
  
  @Test
  public void testPeek_asUser() throws Exception {
    loginAsUser();
    _SECURED_SERVICE.peek();
  }
  
  @Test
  public void testPeek_asAdmin() throws Exception {
    loginAsAdmin();
    _SECURED_SERVICE.peek();
  }
  
  
  // TEST RETRIEVE
  
  @Test(expected=UnauthorizedException.class)
  public void testRetrieve_asAnonymous() throws Exception {
    _SECURED_SERVICE.retrieve();
  }

  @Test(expected=UnauthorizedException.class)
  public void testRetrieve_asGuest() throws Exception {
    loginAsGuest();
    _SECURED_SERVICE.retrieve();
  }

  @Test
  public void testRetrieve_asUser() throws Exception {
    loginAsUser();
    _SECURED_SERVICE.retrieve();
  }

  @Test
  public void testRetrieve_asAdmin() throws Exception {
    loginAsAdmin();
    _SECURED_SERVICE.retrieve();
  }

  
  // TEST CHANGE
  
  @Test(expected=UnauthorizedException.class)
  public void testChange_asAnonymous() throws Exception {
    _SECURED_SERVICE.change();
  }
  
  @Test(expected=UnauthorizedException.class)
  public void testChange_asGuest() throws Exception {
    loginAsGuest();
    _SECURED_SERVICE.change();
  }
  
  @Test(expected=UnauthorizedException.class)
  public void testChange_asUser() throws Exception {
    loginAsUser();
    _SECURED_SERVICE.change();
  }
  
  @Test
  public void testChange_asAdmin() throws Exception {
    loginAsAdmin();
    _SECURED_SERVICE.change();
  }
  
  
  // TEST RETRIEVE RESTRICTED
  
  @Test(expected=UnauthorizedException.class)
  public void testRetrieveRestricted_asAnonymous() throws Exception {
    _RESTRICTED_SERVICE.retrieve();
  }

  @Test(expected=UnauthorizedException.class)
  public void testRetrieveRestricted_asGuest() throws Exception {
    loginAsGuest();
    _RESTRICTED_SERVICE.retrieve();
  }

  @Test(expected=UnauthorizedException.class)
  public void testRetrieveRestricted_asUser() throws Exception {
    loginAsUser();
    _RESTRICTED_SERVICE.retrieve();
  }

  @Test
  public void testRetrieveRestricted_asAdmin() throws Exception {
    loginAsAdmin();
    _RESTRICTED_SERVICE.retrieve();
  }
  
}
