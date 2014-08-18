package io.aos.aop.aspectj.retry;

import static org.aspectj.lang.Aspects.aspectOf;

import com.google.inject.AbstractModule;

public class RetryAspectModule extends AbstractModule {

    @Override
    protected void configure() {
        requestInjection(aspectOf(RetryAspect.class));
    }

}
