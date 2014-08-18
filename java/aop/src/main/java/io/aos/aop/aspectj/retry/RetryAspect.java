package io.aos.aop.aspectj.retry;

import io.aos.aop.util.retry.annotation.RetryOnException;
import io.aos.aop.util.retry.executor.RetryExecutor;
import io.aos.aop.util.retry.executor.RetryExecutor.Callable;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class RetryAspect {

    @Around("execution(* *.*(..)) && @annotation(io.aos.aop.util.retry.annotation.RetryOnException)")
    public Object retryIfFails(final ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        RetryOnException retryIfFails = proceedingJoinPoint.getTarget().getClass()
                .getMethod(proceedingJoinPoint.getSignature().getName(), asClasses(proceedingJoinPoint.getArgs()))
                .getAnnotation(RetryOnException.class);
        
        RetryExecutor retryExecutor = new RetryExecutor();

        return retryExecutor.executeWithRetry(retryIfFails.maxRetry(), retryIfFails.message(), retryIfFails.sleepMillis(),
                new Callable() {
                    public Object call() throws Throwable {
                        return proceedingJoinPoint.proceed();
                    }
                });

    }

    private Class<?>[] asClasses(Object[] objects) {
        Class<?>[] classes = new Class[objects.length];
        for (int i = 0; i < classes.length; i++) {
            classes[i] = objects[i].getClass();
        }
        return classes;
    }

}
