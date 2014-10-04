package io.aos.onami.configuration;

import static com.google.inject.Guice.createInjector;
import static org.apache.onami.configuration.OnamiVariablesExpander.expandVariables;

import java.io.File;
import java.net.URI;

import org.apache.onami.configuration.ConfigurationModule;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

import com.google.inject.Injector;

/**
 * @since 6.0
 */
public final class ConfigurationRunner extends BlockJUnit4ClassRunner {

    private final Injector injector;

    public ConfigurationRunner(Class<?> clazz) throws InitializationError {

        super(clazz);

        injector = createInjector(expandVariables(new ConfigurationModule() {

            @Override
            protected void bindConfigurations() {

                bindEnvironmentVariables();
                bindSystemProperties();

                // bindProperty("test.suites").toValue("${user.dir}/src/test/resources/testng.xml");

                bindProperties(URI.create("classpath:/aos/onami/configuration/ldap.properties"));
                bindProperties("proxy.xml").inXMLFormat();

                File parentConf = new File("src/test/resources/aos/onami/configuration");
                bindProperties(new File(parentConf, "ibatis.properties"));
                bindProperties(new File(parentConf, "jdbc.properties"));
                bindProperties(new File(parentConf, "memcached.xml")).inXMLFormat();
            }

        }));
    }

    @Override
    protected Object createTest() throws Exception {
        return injector.getInstance(getTestClass().getJavaClass());
    }

}
