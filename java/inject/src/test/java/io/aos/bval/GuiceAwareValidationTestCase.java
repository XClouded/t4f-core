/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.aos.bval;

import io.aos.bval.exception.DummyException;
import io.aos.bval.model.Country;
import io.aos.bval.service.DummyCountryService;

import java.util.Set;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import junit.framework.TestCase;

import org.apache.bval.guice.ValidationModule;

import com.google.inject.Guice;

public final class GuiceAwareValidationTestCase extends TestCase {

    @Inject
    private Validator validator;

    @Inject
    private DummyCountryService dummyCountryDao;

    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    public void setDummyCountryDao(DummyCountryService dummyCountryDao) {
        this.dummyCountryDao = dummyCountryDao;
    }

    @Override
    protected void setUp() throws Exception {
        Guice.createInjector(new ValidationModule()).injectMembers(this);
    }

    @Override
    protected void tearDown() throws Exception {
        this.validator = null;
        this.dummyCountryDao = null;
    }

    public void testInjectedValidation() {
        Country country = new Country();
        country.setName("Belgium");
        country.setIso2Code("be");
        country.setIso3Code("bel");

        Set<ConstraintViolation<Country>> violations = this.validator.validate(country);
        assertTrue(violations.isEmpty());
    }

    public void testAopInjectedValidation() {
        this.dummyCountryDao.insertCountry("Belgium", "be", "bel");
    }

    public void testAopInjectedFailedValidation() {
        try {
            this.dummyCountryDao.insertCountry("Belgium", "bel", "bel");
            fail("javax.validation.ConstraintViolationException expected");
        }
        catch (ConstraintViolationException cve) {
            // do nothing
        }
    }

    public void testRethrowWrappedException() {
        try {
            this.dummyCountryDao.updateCountry(new Country());
            fail(DummyException.class.getName() + " expected");
        }
        catch (Exception e) {
            assertEquals(DummyException.class, e.getClass());
            assertTrue(e.getMessage().startsWith("This is just a dummy message "));
        }
    }

}
