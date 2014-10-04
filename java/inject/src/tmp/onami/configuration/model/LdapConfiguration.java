package io.aos.onami.configuration.model;

import javax.inject.Inject;
import javax.inject.Named;

public final class LdapConfiguration {

    @Inject
    @Named("ldap.host")
    private String host;

    @Inject
    @Named("ldap.port")
    private int port;

    @Inject
    @Named("ldap.baseDN")
    private String baseDN;

    @Inject
    @Named("ldap.user")
    private String user;

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

    public String getBaseDN() {
        return baseDN;
    }

    public void setBaseDN(String baseDN) {
        this.baseDN = baseDN;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

}
