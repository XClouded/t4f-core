class MyService {

    static transactional = true

    def myMethod(fooBarText) {
        log.info "Got text: ${ fooBarText }"
    }

}
