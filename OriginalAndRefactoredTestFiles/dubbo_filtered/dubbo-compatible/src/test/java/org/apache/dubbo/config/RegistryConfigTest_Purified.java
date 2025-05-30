package org.apache.dubbo.config;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import com.alibaba.dubbo.config.RegistryConfig;
import org.junit.jupiter.api.Test;
import static org.apache.dubbo.common.constants.CommonConstants.SHUTDOWN_WAIT_KEY;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.not;

class RegistryConfigTest_Purified {

    @Test
    void testWait_1() throws Exception {
        RegistryConfig registry = new RegistryConfig();
        registry.setWait(10);
        assertThat(registry.getWait(), is(10));
    }

    @Test
    void testWait_2() throws Exception {
        assertThat(System.getProperty(SHUTDOWN_WAIT_KEY), equalTo("10"));
    }
}
