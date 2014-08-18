package io.aos.guice.listener;

import com.google.inject.AbstractModule;

import static com.google.inject.matcher.Matchers.any;

/**
 * Module to install which enables automatic injection of slf4j loggers into
 * Guice-managed objects (by field injection only).
 *
 * 
 */
public class Slf4jModule extends AbstractModule {
  @Override
  protected void configure() {
    bindListener(any(), new Slf4jInjectionTypeListener());
  }
}
