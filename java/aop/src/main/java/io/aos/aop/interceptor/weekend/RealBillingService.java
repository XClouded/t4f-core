package io.aos.aop.interceptor.weekend;

public class RealBillingService implements BillingService {

    @Override
    @NotOnWeekends
    public Receipt chargeOrder(PizzaOrder order, CreditCard creditCard) {
        return new Receipt();
    }
}

