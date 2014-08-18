package io.aos.onami.spi.foo;

import io.aos.onami.spi.annotation.BarBindingAnnotation;

@BarBindingAnnotation(2)
public final class FooServiceImpl2 implements FooService {

    @Override
    public void doSomething() {
        // ... but does nothing
    }

}
