package io.aos.aop.aspectj.auth;

import static org.aspectj.lang.Aspects.aspectOf;
import io.aos.aop.aspectj.auth.aspect.ProfileVerificationAspect;
import io.aos.aop.aspectj.auth.auth.UserProfileChecker;
import io.aos.aop.aspectj.auth.auth.UserProfileCheckerImpl;
import io.aos.aop.aspectj.auth.contact.ContactManager;
import io.aos.aop.aspectj.auth.contact.ContactManagerImpl;

import com.google.inject.AbstractModule;

public class AspectJAuthModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ContactManager.class).to(ContactManagerImpl.class).asEagerSingleton();
        bind(UserProfileChecker.class).to(UserProfileCheckerImpl.class).asEagerSingleton();
        requestInjection(aspectOf(ProfileVerificationAspect.class));
    }

}
