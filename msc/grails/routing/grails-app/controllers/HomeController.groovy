class HomeController {
    def index = {
        sendMessage("seda:input", "Hello, world! from home controller")
        render text: "OK", contentType: "text/plain"
    }
}
