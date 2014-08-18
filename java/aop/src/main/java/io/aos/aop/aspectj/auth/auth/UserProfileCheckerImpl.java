package io.aos.aop.aspectj.auth.auth;

import static io.aos.aop.aspectj.auth.model.UserProfile.ADMIN;
import static io.aos.aop.aspectj.auth.model.UserProfile.ANONYMOUS;
import static io.aos.aop.aspectj.auth.model.UserProfile.USER;
import io.aos.aop.aspectj.auth.model.UserProfile;

public class UserProfileCheckerImpl implements UserProfileChecker {

    private UserProfile userProfile = ANONYMOUS;

    public UserProfile getCurrentUserProfile() {
        return userProfile;
    }

    public UserProfile login(String login, String password) {
        if (login.equals("John") && password.equals("secret")) {
            userProfile = ADMIN;
        }

        else if (login.equals("Jack") && password.equals("1234")) {
            userProfile = USER;
        }

        else {
            userProfile = ANONYMOUS;
        }

        return getCurrentUserProfile();
    }

    public UserProfile logout() {
        return ANONYMOUS;
    }

}
