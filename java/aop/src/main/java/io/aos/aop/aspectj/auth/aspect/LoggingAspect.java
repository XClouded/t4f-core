package io.aos.aop.aspectj.auth.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class LoggingAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("call( * io.aos.aop.aspectj.auth.contact.ContactManager.*(..) )")
    public void methodCalled(JoinPoint thisJoinPoint) {

        LOGGER.info("Calling: " + thisJoinPoint.getSignature().getName());

    }

}
