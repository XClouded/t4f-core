package io.aos.guice.generics;

import java.util.List;

public interface Api<T> {
    
    List<T> list();

}
