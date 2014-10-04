class MyMessageRoute {
    def configure = {
        from("seda:input.queue").filter {
            it.in.body.contains("FooBar")
        }.to("bean:myService?method=myMethod")
    }
}
