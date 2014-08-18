package io.aos.aop.aspectj.auth;

import static com.google.inject.matcher.Matchers.annotatedWith;
import static com.google.inject.matcher.Matchers.any;
import static com.google.inject.matcher.Matchers.subclassesOf;
import io.aos.aop.aspectj.auth.annotation.RequiresProfile;
import io.aos.aop.aspectj.auth.annotation.WithUserProfileVerification;
import io.aos.aop.aspectj.auth.auth.UserProfileChecker;
import io.aos.aop.aspectj.auth.auth.UserProfileCheckerImpl;
import io.aos.aop.aspectj.auth.contact.ContactManager;
import io.aos.aop.aspectj.auth.contact.ContactManagerImpl;
import io.aos.aop.aspectj.auth.interceptor.LoggingInterceptor;
import io.aos.aop.aspectj.auth.interceptor.UserProfileInterceptor;

import com.google.inject.AbstractModule;

public class AopAuthModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ContactManager.class).to(ContactManagerImpl.class).asEagerSingleton();
        bind(UserProfileChecker.class).to(UserProfileCheckerImpl.class).asEagerSingleton();
        UserProfileInterceptor userProfileInterceptor = new UserProfileInterceptor();
        requestInjection(userProfileInterceptor);
        bindInterceptor(
                annotatedWith(WithUserProfileVerification.class),
                annotatedWith(RequiresProfile.class),
                userProfileInterceptor);
        bindInterceptor(
                subclassesOf(ContactManager.class),
                any(),
                new LoggingInterceptor());
        }

}
