package io.aos.guice.generics;

import javax.inject.Inject;

public class ApiServiceConstructor {
    
    Api<Object> objectApi;
    
    Api<String> stringApi;
    
    @Inject
    public ApiServiceConstructor(Api<Object> objectApi, Api<String> stringApi) {
        this.objectApi = objectApi;
        this.stringApi = stringApi;
    }
    

}
