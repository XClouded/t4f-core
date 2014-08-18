package io.aos.onami.spi;

public final class FromSystemPropertiesTest {
/*
    @Inject
    @BarBindingAnnotation(1)
    private FooService fooService1;

    @Inject
    @BarBindingAnnotation(2)
    private FooService fooService2;

    public void setFooService1(FooService fooService1) {
        this.fooService1 = fooService1;
    }

    public void setFooService2(FooService fooService2) {
        this.fooService2 = fooService2;
    }

    @Before
    public void setUp() {

        System.setProperty("aos.onami.spi.foo.FooService",
                "aos.onami.spi.foo.FooServiceImpl1,aos.onami.spi.foo.FooServiceImpl2");

        createInjector(new ServiceLoaderModule() {

            @Override
            protected void configureServices() {
                discover(FooService.class);
            }


        }).getMembersInjector(FromSystemPropertiesTest.class).injectMembers(this);
    }

    @Test
    public void injectedServicesCaughtFromSystemProperties() {
        assertEquals(FooServiceImpl1.class, fooService1.getClass());
        assertEquals(FooServiceImpl2.class, fooService2.getClass());
    }
*/
}
