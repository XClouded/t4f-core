package io.aos.guice.generics;

import javax.inject.Inject;

public class ApiServiceField {
    
    @Inject
    Api<Object> objectApi;
    
    @Inject
    Api<String> stringApi;
    
}
