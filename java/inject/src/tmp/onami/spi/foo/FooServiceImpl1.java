package io.aos.onami.spi.foo;

import io.aos.onami.spi.annotation.BarBindingAnnotation;

@BarBindingAnnotation(1)
public final class FooServiceImpl1 implements FooService {

    @Override
    public void doSomething() {
        // ... but does nothing
    }

}
