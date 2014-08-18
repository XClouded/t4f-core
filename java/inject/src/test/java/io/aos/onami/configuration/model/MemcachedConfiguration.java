package io.aos.onami.configuration.model;

import javax.inject.Inject;
import javax.inject.Named;

public final class MemcachedConfiguration {

    @Inject
    @Named("com.ibaguice.memcached.keyprefix")
    private String keyPrefix;

    @Inject
    @Named("com.ibaguice.memcached.compression")
    private boolean compressionEnabled;

    public String getKeyPrefix() {
        return keyPrefix;
    }

    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }

    public boolean isCompressionEnabled() {
        return compressionEnabled;
    }

    public void setCompressionEnabled(boolean compressionEnabled) {
        this.compressionEnabled = compressionEnabled;
    }

}
