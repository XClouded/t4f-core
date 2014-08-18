package io.aos.guice.generics;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import io.aos.guice.generics.Api;
import io.aos.guice.generics.ApiModule;
import io.aos.guice.generics.ApiServiceConstructor;
import io.aos.guice.generics.ApiServiceField;
import io.aos.guice.generics.ObjectApi;
import io.aos.guice.generics.StringApi;

import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;

public class ApiTest {
    
    private static Injector injector = Guice.createInjector(new ApiModule());
    
    @Test
    public void testKey() {
        
        ObjectApi objectApi = (ObjectApi) injector.getInstance(Key.get(new TypeLiteral<Api<Object>>(){}));
        assertNotNull(objectApi);

        StringApi stringApi = (StringApi) injector.getInstance(Key.get(new TypeLiteral<Api<String>>(){}));
        assertNotNull(stringApi);
        
    }
    
    @Test
    public void testFieldInject() {

        ApiServiceField apiServiceField = new ApiServiceField();
        injector.injectMembers(apiServiceField);
        
        assertTrue(apiServiceField.objectApi.getClass() == ObjectApi.class);
        assertTrue(apiServiceField.stringApi.getClass() == StringApi.class);
        
    }

    @Test
    public void testConctructorInject() {

        ApiServiceConstructor apiServiceConstructor = injector.getInstance(ApiServiceConstructor.class);
        
        assertTrue(apiServiceConstructor.objectApi.getClass() == ObjectApi.class);
        assertTrue(apiServiceConstructor.stringApi.getClass() == StringApi.class);
        
    }

}
