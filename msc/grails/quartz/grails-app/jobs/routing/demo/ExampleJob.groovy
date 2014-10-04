package routing.demo


class ExampleJob {
    def timeout = 5000l // execute job once in 5 seconds

    def execute() {
        sendMessage("seda:input", "Hello, world! from a Quartz job...")
    }
}
