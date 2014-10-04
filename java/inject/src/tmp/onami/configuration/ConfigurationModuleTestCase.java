package io.aos.onami.configuration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import io.aos.onami.configuration.model.JdbcConfiguration;
import io.aos.onami.configuration.model.LdapConfiguration;
import io.aos.onami.configuration.model.MemcachedConfiguration;
import io.aos.onami.configuration.model.MyBatisConfiguration;
import io.aos.onami.configuration.model.ProxyConfiguration;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(ConfigurationRunner.class)
public final class ConfigurationModuleTestCase {

    @Inject
    private MyBatisConfiguration myBatisConfiguration;

    @Inject
    private JdbcConfiguration jdbcConfiguration;

    @Inject
    private LdapConfiguration ldapConfiguration;

    @Inject
    private MemcachedConfiguration memcachedConfiguration;

    @Inject
    private ProxyConfiguration proxyConfiguration;

    @Test
    public void testProxyConfiguration() {
        assertEquals("localhost", proxyConfiguration.getHost());
        assertEquals(8180, proxyConfiguration.getPort());
    }

    @Test
    public void testJdbcConfiguration() {
        assertEquals("com.mysql.jdbc.Driver", jdbcConfiguration.getDriver());
        assertEquals("jdbc:mysql://localhost:3306/rocoto", jdbcConfiguration.getUrl());
        assertEquals("simone", jdbcConfiguration.getUsername());
        assertEquals("rocoto2010", jdbcConfiguration.getPassword());
        assertTrue(jdbcConfiguration.isAutoCommit());
    }

    @Test
    public void testLdapConfiguration() {
        assertEquals("ldap.${not.found}", ldapConfiguration.getHost());
        assertEquals(389, ldapConfiguration.getPort());
        assertTrue(ldapConfiguration.getBaseDN().indexOf('$') < 0);
        assertEquals("", ldapConfiguration.getUser());
    }

    @Test
    public void testMemcachedConfiguration() {
        assertEquals("test_", memcachedConfiguration.getKeyPrefix());
        assertTrue(memcachedConfiguration.isCompressionEnabled());
    }

    @Test
    public void testIBatisConfiguration() {
        assertEquals("test", myBatisConfiguration.getEnvironmentId());
        assertTrue(myBatisConfiguration.isLazyLoadingEnabled());
    }

    public void setProxyConfiguration(ProxyConfiguration proxyConfiguration) {
        this.proxyConfiguration = proxyConfiguration;
    }

    public void setJdbcConfiguration(JdbcConfiguration jdbcConfiguration) {
        this.jdbcConfiguration = jdbcConfiguration;
    }

    public void setLdapConfiguration(LdapConfiguration ldapConfiguration) {
        this.ldapConfiguration = ldapConfiguration;
    }

    public void setMemcachedConfiguration(MemcachedConfiguration memcachedConfiguration) {
        this.memcachedConfiguration = memcachedConfiguration;
    }

    public void setMyBatisConfiguration(MyBatisConfiguration myBatisConfiguration) {
        this.myBatisConfiguration = myBatisConfiguration;
    }

}
