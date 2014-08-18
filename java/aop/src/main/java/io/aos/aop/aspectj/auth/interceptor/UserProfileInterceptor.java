package io.aos.aop.aspectj.auth.interceptor;

import static io.aos.aop.aspectj.auth.model.UserProfile.ADMIN;
import static io.aos.aop.aspectj.auth.model.UserProfile.USER;
import io.aos.aop.aspectj.auth.annotation.RequiresProfile;
import io.aos.aop.aspectj.auth.auth.UserProfileChecker;
import io.aos.aop.aspectj.auth.exception.InsufficientPrivilegeException;
import io.aos.aop.aspectj.auth.model.UserProfile;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import com.google.inject.Inject;
 
public class UserProfileInterceptor implements MethodInterceptor {
 
    @Inject
    private UserProfileChecker profileChecker;
 
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        UserProfile required = methodInvocation.getMethod().getAnnotation(RequiresProfile.class).value();
        UserProfile current = profileChecker.getCurrentUserProfile();
 
        if (insufficientProfile(required, current)) {
            throw new InsufficientPrivilegeException("The current user profile (" + current + ") is not sufficient: " + required);
        } else {
            return methodInvocation.proceed();
        }
    }
 
    private boolean insufficientProfile(UserProfile required, UserProfile current) {
        return (required == ADMIN && current != ADMIN)
                || (required == USER && (current != USER && current != ADMIN));
    }
 
}
