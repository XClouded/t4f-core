package io.aos.aop.interceptor.weekend;

import io.aos.aop.interceptor.weekend.BillingService;
import io.aos.aop.interceptor.weekend.CreditCard;
import io.aos.aop.interceptor.weekend.NotOnWeekendsModule;
import io.aos.aop.interceptor.weekend.PizzaOrder;

import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class NotOnWeekendsTest {
    
    @Test
    public void test() {
        Injector injector = Guice.createInjector(new NotOnWeekendsModule());
        BillingService billingService = injector.getInstance(BillingService.class);
        billingService.chargeOrder(new PizzaOrder(), new CreditCard());
    }

}
