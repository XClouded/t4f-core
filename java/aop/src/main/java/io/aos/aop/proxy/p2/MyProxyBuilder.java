package io.aos.aop.proxy.p2;

import java.lang.reflect.Proxy;

public class MyProxyBuilder {

    public static MyProxyInterface newInstance(MyProxyInterface delegate, Class<?>... interfaces) {
        return (MyProxyInterface) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                interfaces, new MyProxyInvocationHandler(delegate));
    }

}
