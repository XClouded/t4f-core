package io.aos.aop.aspectj.retry;

import static org.aspectj.lang.Aspects.aspectOf;
import io.aos.aop.util.retry.service.Connect;
import io.aos.aop.util.retry.service.ConnectExcepting;
import io.aos.aop.util.retry.service.ConnectRecovering;
import io.aos.aop.util.retry.service.ConnectSucceeding;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

public class RetryMain {

    public static void main(String... args) throws Exception {
        doWithSuccess();
        doWithRecover();
        doWithException();
    }

    public static void doWithSuccess() throws Exception {

        Module module = new AbstractModule() {
            @Override
            protected void configure() {
                bind(Connect.class).to(ConnectSucceeding.class).asEagerSingleton();
                install(new RetryAspectModule());
            }
        };
        Injector injector = Guice.createInjector(module);
        Connect connect = injector.getInstance(Connect.class);
        connect.connect();

    }

    public static void doWithRecover() throws Exception {

        Module module = new AbstractModule() {
            @Override
            protected void configure() {
                bind(Connect.class).to(ConnectRecovering.class).asEagerSingleton();
                install(new RetryAspectModule());
            }
        };
        Injector injector = Guice.createInjector(module);
        Connect connect = injector.getInstance(Connect.class);
        connect.connect();

    }

    public static void doWithException() throws Exception {

        Module module = new AbstractModule() {
            @Override
            protected void configure() {
                bind(Connect.class).to(ConnectExcepting.class).asEagerSingleton();
                install(new RetryAspectModule());
            }
        };
        Injector injector = Guice.createInjector(module);
        Connect connect = injector.getInstance(Connect.class);
        connect.connect();

    }

}
