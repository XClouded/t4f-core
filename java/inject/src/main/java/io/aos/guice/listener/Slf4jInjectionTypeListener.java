package io.aos.guice.listener;

import com.google.inject.ProvisionException;
import com.google.inject.TypeLiteral;
import com.google.inject.spi.InjectionListener;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

/**
 * 
 */
class Slf4jInjectionTypeListener implements TypeListener {
  @Override
  public <I> void hear(final TypeLiteral<I> type, TypeEncounter<I> encounter) {
    final Field field = getLoggerField(type.getRawType());

    if (field != null) {
      encounter.register(new InjectionListener<I>() {
        @Override
        public void afterInjection(I injectee) {
          try {
            boolean b = field.isAccessible();
            if (!b) field.setAccessible(true);
            field.set(injectee,
                LoggerFactory.getLogger(type.getRawType()));
            if (!b) field.setAccessible(false);
          } catch (IllegalAccessException e) {
            throw new ProvisionException(
                "Unable to inject SLF4J logger", e);
          }
        }
      });
    }
  }

  protected Field getLoggerField(Class<?> clazz) {
    // search for Logger in current class and return it if found
    for (final Field field : clazz.getDeclaredFields()) {
      final Class<?> typeOfField = field.getType();
      if (Logger.class.isAssignableFrom(typeOfField)) {

        return field;
      }
    }

    // search for Logger in superclass if not found in this class
    if (clazz.getSuperclass() != null) {
      return getLoggerField(clazz.getSuperclass());
    }

    // not in current class and not having superclass, return null
    return null;
  }
}
