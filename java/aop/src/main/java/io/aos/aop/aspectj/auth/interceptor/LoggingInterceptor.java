package io.aos.aop.aspectj.auth.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
public class LoggingInterceptor implements MethodInterceptor {
    private Logger LOGGER = LoggerFactory.getLogger(LoggingInterceptor.class.getName());
 
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {

        LOGGER.info(
                methodInvocation.getClass().getName() +
                methodInvocation.getMethod().getName() +
                "invocation" +
                methodInvocation.getArguments());

        Object result = null;
        try {
            result = methodInvocation.proceed();
        } finally {
            LOGGER.info(
                    methodInvocation.getClass().getName() +
                    methodInvocation.getMethod().getName() +
                    "return" +
                    result);
 
            return result;
        }
 
    }
 
}
