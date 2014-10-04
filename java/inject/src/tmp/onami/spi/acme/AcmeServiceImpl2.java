package io.aos.onami.spi.acme;

import javax.inject.Named;

@Named("second")
public final class AcmeServiceImpl2 implements AcmeService {

    @Override
    public void doSomething() {
        // but does nothing
    }

}
