package io.aos.groovy.jar

import groovy.util.GroovyTestCase

/**
 * Tests for the {@link Helper} class.
 */
class HelperTest extends GroovyTestCase {

    void testHelp() {
        new Helper().help(new Example())
    }

}
