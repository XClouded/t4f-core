class ExampleRoute {
    def configure = {
        from('seda:input').to('stream:out')
    }
}
