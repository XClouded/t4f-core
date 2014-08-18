package io.aos.onami.configuration.model;

import javax.inject.Inject;
import javax.inject.Named;

public final class ProxyConfiguration {

    @Inject
    @Named("proxy.host")
    private String host;

    @Inject
    @Named("proxy.port")
    private int port;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

}
