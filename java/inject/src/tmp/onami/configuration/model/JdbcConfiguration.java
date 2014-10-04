package io.aos.onami.configuration.model;

import javax.inject.Inject;
import javax.inject.Named;

public final class JdbcConfiguration {

    @Inject
    @Named("JDBC.driver")
    private String driver;

    @Inject
    @Named("JDBC.url")
    private String url;

    @Inject
    @Named("JDBC.username")
    private String username;

    @Inject
    @Named("JDBC.password")
    private String password;

    @Inject
    @Named("JDBC.autoCommit")
    private boolean autoCommit;

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAutoCommit() {
        return autoCommit;
    }

    public void setAutoCommit(boolean autoCommit) {
        this.autoCommit = autoCommit;
    }

}
