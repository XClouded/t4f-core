package io.aos.aop.interceptor.weekend;

import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;

public class NotOnWeekendsModule extends AbstractModule {

    protected void configure() {

        bind(BillingService.class).to(RealBillingService.class);

        bindInterceptor(Matchers.any(), Matchers.annotatedWith(NotOnWeekends.class), new WeekendBlocker());

    }

}
