package io.aos.aop.util.retry.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RetryOnException {

    int maxRetry() default 3;

    int sleepMillis() default 1000;

    String message() default "Operation failed.";

}
