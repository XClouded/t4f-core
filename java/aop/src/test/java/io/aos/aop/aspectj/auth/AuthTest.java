package io.aos.aop.aspectj.auth;

import io.aos.aop.aspectj.auth.AopAuthModule;
import io.aos.aop.aspectj.auth.AspectJAuthModule;
import io.aos.aop.aspectj.auth.auth.UserProfileChecker;
import io.aos.aop.aspectj.auth.contact.ContactManager;
import io.aos.aop.aspectj.auth.exception.InsufficientPrivilegeException;
import io.aos.aop.aspectj.auth.model.Person;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class AuthTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthTest.class);

    @Test(
            expected = InsufficientPrivilegeException.class)
    public void testAop() {

        Injector injector = Guice.createInjector(new AopAuthModule());

        ContactManager contacts = injector.getInstance(ContactManager.class);
        UserProfileChecker profileChecker = injector.getInstance(UserProfileChecker.class);

        profileChecker.login("John", "secret");
        contacts.add(new Person("John Doe", "john.doe@aos.io"));
        contacts.add(new Person("Jack", "jack@aos.io"));
        profileChecker.logout();

        profileChecker.login("Jack", "1234");
        LOGGER.info("" + contacts.lookup("John Doe"));
        profileChecker.logout();

        contacts.add(new Person("Mr Bean", "mrbean@gmail.com"));

    }

    @Test(
            expected = InsufficientPrivilegeException.class)
    public void testAspectJ() {

        Injector injector = Guice.createInjector(new AspectJAuthModule());

        ContactManager contacts = injector.getInstance(ContactManager.class);
        UserProfileChecker profileChecker = injector.getInstance(UserProfileChecker.class);

        profileChecker.login("John", "secret");
        contacts.add(new Person("John Doe", "john.doe@aos.io"));
        contacts.add(new Person("Jack", "jack@aos.io"));
        profileChecker.logout();

        profileChecker.login("Jack", "1234");
        LOGGER.info("" + contacts.lookup("John Doe"));
        profileChecker.logout();

        contacts.add(new Person("Mr Bean", "mrbean@gmail.com"));

    }

}
