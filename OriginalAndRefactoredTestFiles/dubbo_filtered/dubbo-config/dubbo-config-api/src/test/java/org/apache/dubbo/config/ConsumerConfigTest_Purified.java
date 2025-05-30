package org.apache.dubbo.config;

import org.apache.dubbo.common.utils.JsonUtils;
import org.apache.dubbo.common.utils.SystemPropertyConfigUtils;
import org.apache.dubbo.config.api.DemoService;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.rpc.model.ApplicationModel;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.apache.dubbo.common.constants.CommonConstants.SystemProperty.SYSTEM_TCP_RESPONSE_TIMEOUT;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

class ConsumerConfigTest_Purified {

    @BeforeEach
    public void setUp() {
        DubboBootstrap.reset();
    }

    @AfterEach
    public void afterEach() {
        SysProps.clear();
    }

    @Test
    void testTimeout_1() {
        ConsumerConfig consumer = new ConsumerConfig();
        consumer.setTimeout(10);
        assertThat(consumer.getTimeout(), is(10));
    }

    @Test
    void testTimeout_2() {
        SystemPropertyConfigUtils.clearSystemProperty(SYSTEM_TCP_RESPONSE_TIMEOUT);
        assertThat(SystemPropertyConfigUtils.getSystemProperty(SYSTEM_TCP_RESPONSE_TIMEOUT), equalTo("10"));
    }
}
