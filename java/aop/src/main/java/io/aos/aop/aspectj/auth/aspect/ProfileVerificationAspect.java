package io.aos.aop.aspectj.auth.aspect;

import static io.aos.aop.aspectj.auth.model.UserProfile.ADMIN;
import static io.aos.aop.aspectj.auth.model.UserProfile.USER;
import io.aos.aop.aspectj.auth.annotation.RequiresProfile;
import io.aos.aop.aspectj.auth.annotation.WithUserProfileVerification;
import io.aos.aop.aspectj.auth.auth.UserProfileChecker;
import io.aos.aop.aspectj.auth.exception.InsufficientPrivilegeException;
import io.aos.aop.aspectj.auth.model.UserProfile;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import com.google.inject.Inject;

@Aspect
public class ProfileVerificationAspect {

    @Inject
    UserProfileChecker userProfileChecker;

    @Before("execution( * *(..) ) && @annotation( required ) && within( @WithUserProfileVerification * )")
    public void verify(RequiresProfile required) {

        // DO NOT REMOVE THIS - Declaration  that helps AspectJ to find the correct annotation
        WithUserProfileVerification pv = null;

        UserProfile expected = required.value();
        UserProfile current = userProfileChecker.getCurrentUserProfile();

        if (insufficientProfile(expected, current)) {
            throw new InsufficientPrivilegeException("The current user profile (" + current + ") is not sufficient: " + required);
        }

    }

    private boolean insufficientProfile(UserProfile required, UserProfile current) {
        return (required == ADMIN && current != ADMIN) || (required == USER && (current != USER && current != ADMIN));
    }

}
