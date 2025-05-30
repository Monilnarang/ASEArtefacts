package org.apache.dubbo.common.config.configcenter;

import org.apache.dubbo.common.URL;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.apache.dubbo.common.config.configcenter.AbstractDynamicConfiguration.DEFAULT_THREAD_POOL_KEEP_ALIVE_TIME;
import static org.apache.dubbo.common.config.configcenter.AbstractDynamicConfiguration.DEFAULT_THREAD_POOL_PREFIX;
import static org.apache.dubbo.common.config.configcenter.AbstractDynamicConfiguration.DEFAULT_THREAD_POOL_SIZE;
import static org.apache.dubbo.common.config.configcenter.AbstractDynamicConfiguration.GROUP_PARAM_NAME;
import static org.apache.dubbo.common.config.configcenter.AbstractDynamicConfiguration.PARAM_NAME_PREFIX;
import static org.apache.dubbo.common.config.configcenter.AbstractDynamicConfiguration.THREAD_POOL_KEEP_ALIVE_TIME_PARAM_NAME;
import static org.apache.dubbo.common.config.configcenter.AbstractDynamicConfiguration.THREAD_POOL_PREFIX_PARAM_NAME;
import static org.apache.dubbo.common.config.configcenter.AbstractDynamicConfiguration.THREAD_POOL_SIZE_PARAM_NAME;
import static org.apache.dubbo.common.config.configcenter.AbstractDynamicConfiguration.TIMEOUT_PARAM_NAME;
import static org.apache.dubbo.common.config.configcenter.DynamicConfiguration.DEFAULT_GROUP;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

class AbstractDynamicConfigurationTest_Purified {

    private AbstractDynamicConfiguration configuration;

    @BeforeEach
    public void init() {
        configuration = new AbstractDynamicConfiguration(null) {

            @Override
            protected String doGetConfig(String key, String group) {
                return null;
            }

            @Override
            protected void doClose() {
            }

            @Override
            protected boolean doRemoveConfig(String key, String group) {
                return false;
            }
        };
    }

    @Test
    void testConstants_1() {
        assertEquals("dubbo.config-center.", PARAM_NAME_PREFIX);
    }

    @Test
    void testConstants_2() {
        assertEquals("dubbo.config-center.workers", DEFAULT_THREAD_POOL_PREFIX);
    }

    @Test
    void testConstants_3() {
        assertEquals("dubbo.config-center.thread-pool.prefix", THREAD_POOL_PREFIX_PARAM_NAME);
    }

    @Test
    void testConstants_4() {
        assertEquals("dubbo.config-center.thread-pool.size", THREAD_POOL_SIZE_PARAM_NAME);
    }

    @Test
    void testConstants_5() {
        assertEquals("dubbo.config-center.thread-pool.keep-alive-time", THREAD_POOL_KEEP_ALIVE_TIME_PARAM_NAME);
    }

    @Test
    void testConstants_6() {
        assertEquals(1, DEFAULT_THREAD_POOL_SIZE);
    }

    @Test
    void testConstants_7() {
        assertEquals(60 * 1000, DEFAULT_THREAD_POOL_KEEP_ALIVE_TIME);
    }

    @Test
    void testConstants_8() {
        assertEquals("dubbo.config-center.group", GROUP_PARAM_NAME);
    }

    @Test
    void testConstants_9() {
        assertEquals("dubbo.config-center.timeout", TIMEOUT_PARAM_NAME);
    }

    @Test
    void testPublishConfig_1() {
        assertFalse(configuration.publishConfig(null, null));
    }

    @Test
    void testPublishConfig_2() {
        assertFalse(configuration.publishConfig(null, null, null));
    }

    @Test
    void testGetConfig_1() {
        assertNull(configuration.getConfig(null, null));
    }

    @Test
    void testGetConfig_2() {
        assertNull(configuration.getConfig(null, null, 200));
    }

    @Test
    void testGetProperties_1() {
        assertNull(configuration.getProperties(null, null));
    }

    @Test
    void testGetProperties_2() {
        assertNull(configuration.getProperties(null, null, 100L));
    }

    @Test
    void testGetGroupAndGetDefaultGroup_1() {
        assertEquals(configuration.getGroup(), configuration.getDefaultGroup());
    }

    @Test
    void testGetGroupAndGetDefaultGroup_2() {
        assertEquals(DEFAULT_GROUP, configuration.getDefaultGroup());
    }

    @Test
    void testGetTimeoutAndGetDefaultTimeout_1() {
        assertEquals(configuration.getTimeout(), configuration.getDefaultTimeout());
    }

    @Test
    void testGetTimeoutAndGetDefaultTimeout_2() {
        assertEquals(-1L, configuration.getDefaultTimeout());
    }
}
