package io.aos.onami.spi;

import static com.google.inject.Guice.createInjector;
import static org.apache.onami.spi.modules.GuiceServiceLoader.loadModules;
import static org.junit.Assert.assertEquals;
import io.aos.onami.spi.acme.AcmeService;
import io.aos.onami.spi.acme.AcmeServiceImpl1;

import org.junit.Before;
import org.junit.Test;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;

public class GuiceServiceLoaderTest {

    @Inject
    private AcmeService acmeService;

    public void setAcmeService(AcmeService acmeService) {
        this.acmeService = acmeService;
    }

    @Before
    public void setUp() {
        createInjector(loadModules()).getMembersInjector(GuiceServiceLoaderTest.class).injectMembers(this);
    }

    @Test
    public void verifyRightModulesWereLoaded() {
        assertEquals(AcmeServiceImpl1.class, acmeService.getClass());
    }

    public static class AcmeModule extends AbstractModule {

        @Override
        protected void configure() {
            bind(AcmeService.class).to(AcmeServiceImpl1.class);
        }

    }

}
