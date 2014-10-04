package io.aos.onami.logging;

import org.apache.onami.logging.core.InjectLogger;
import org.apache.onami.logging.slf4j.Slf4jLoggingModule;
import org.apache.onami.logging.testfw.AbstractLoggerInectionTestCase;
import org.junit.Test;
import org.slf4j.Logger;
import org.testng.annotations.BeforeTest;

import com.google.inject.TypeLiteral;
import com.google.inject.matcher.Matchers;

public final class Slf4jLoggingTestCase extends AbstractLoggerInectionTestCase<Logger> {

    @InjectLogger
    private Logger logger;

    @BeforeTest
    public void setUp() {
        super.setUp(new Slf4jLoggingModule(Matchers.only(TypeLiteral.get(this.getClass()))));
    }

    @Test
    public void injectAndVerify() {
        this.injectAndVerify(this.logger);
    }

}
