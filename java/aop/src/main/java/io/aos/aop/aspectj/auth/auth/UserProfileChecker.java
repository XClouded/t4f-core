package io.aos.aop.aspectj.auth.auth;

import io.aos.aop.aspectj.auth.model.UserProfile;

public interface UserProfileChecker {

    public UserProfile getCurrentUserProfile();

    public UserProfile login(String login, String password);

    public UserProfile logout();

}
