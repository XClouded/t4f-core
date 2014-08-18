package io.aos.aop.aspectj.auth.annotation;

import io.aos.aop.aspectj.auth.model.UserProfile;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiresProfile {

    UserProfile value();

}
