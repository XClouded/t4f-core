package io.aos.guice.generics;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;

public class ApiModule extends AbstractModule {

    @Override
    protected void configure() {
        
        bind(new TypeLiteral<Api<Object>>(){}).to(ObjectApi.class);
        bind(new TypeLiteral<Api<String>>(){}).to(StringApi.class);
        
    }

}
