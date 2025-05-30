package org.apache.hadoop.security.authorize;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.apache.hadoop.conf.Configuration;
import org.junit.Test;

public class TestProxyServers_Purified {

    @Test
    public void testProxyServer_1() {
        assertFalse(ProxyServers.isProxyServer("1.1.1.1"));
    }

    @Test
    public void testProxyServer_2() {
        assertFalse(ProxyServers.isProxyServer("1.1.1.1"));
    }

    @Test
    public void testProxyServer_3_testMerged_3() {
        conf.set(ProxyServers.CONF_HADOOP_PROXYSERVERS, "2.2.2.2, 3.3.3.3");
        assertTrue(ProxyServers.isProxyServer("2.2.2.2"));
        assertTrue(ProxyServers.isProxyServer("3.3.3.3"));
    }
}
