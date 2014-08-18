package io.aos.aop.interceptor.weekend;

public interface BillingService {

    Receipt chargeOrder(PizzaOrder order, CreditCard creditCard);

}
