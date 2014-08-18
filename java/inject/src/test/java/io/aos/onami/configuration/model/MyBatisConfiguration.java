package io.aos.onami.configuration.model;

import javax.inject.Inject;
import javax.inject.Named;

public final class MyBatisConfiguration {

    @Inject
    @Named("ibatis.environment.id")
    private String environmentId;

    @Inject
    @Named("ibatis.configuration.lazyLoadingEnabled")
    private boolean lazyLoadingEnabled;

    public String getEnvironmentId() {
        return environmentId;
    }

    public void setEnvironmentId(String environmentId) {
        this.environmentId = environmentId;
    }

    public boolean isLazyLoadingEnabled() {
        return lazyLoadingEnabled;
    }

    public void setLazyLoadingEnabled(boolean lazyLoadingEnabled) {
        this.lazyLoadingEnabled = lazyLoadingEnabled;
    }

}
