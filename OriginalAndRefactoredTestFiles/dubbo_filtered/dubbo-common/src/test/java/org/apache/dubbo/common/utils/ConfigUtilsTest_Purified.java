package org.apache.dubbo.common.utils;

import org.apache.dubbo.common.config.CompositeConfiguration;
import org.apache.dubbo.common.config.InmemoryConfiguration;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.threadpool.ThreadPool;
import org.apache.dubbo.rpc.model.ApplicationModel;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ConfigUtilsTest_Purified {

    private Properties properties;

    @BeforeEach
    public void setUp() throws Exception {
        properties = ConfigUtils.getProperties(Collections.emptySet());
    }

    @AfterEach
    public void tearDown() throws Exception {
    }

    @Test
    void testIsEmpty_1() throws Exception {
        assertThat(ConfigUtils.isEmpty(null), is(true));
    }

    @Test
    void testIsEmpty_2() throws Exception {
        assertThat(ConfigUtils.isEmpty(""), is(true));
    }

    @Test
    void testIsEmpty_3() throws Exception {
        assertThat(ConfigUtils.isEmpty("false"), is(true));
    }

    @Test
    void testIsEmpty_4() throws Exception {
        assertThat(ConfigUtils.isEmpty("FALSE"), is(true));
    }

    @Test
    void testIsEmpty_5() throws Exception {
        assertThat(ConfigUtils.isEmpty("0"), is(true));
    }

    @Test
    void testIsEmpty_6() throws Exception {
        assertThat(ConfigUtils.isEmpty("null"), is(true));
    }

    @Test
    void testIsEmpty_7() throws Exception {
        assertThat(ConfigUtils.isEmpty("NULL"), is(true));
    }

    @Test
    void testIsEmpty_8() throws Exception {
        assertThat(ConfigUtils.isEmpty("n/a"), is(true));
    }

    @Test
    void testIsEmpty_9() throws Exception {
        assertThat(ConfigUtils.isEmpty("N/A"), is(true));
    }

    @Test
    void testIsDefault_1() throws Exception {
        assertThat(ConfigUtils.isDefault("true"), is(true));
    }

    @Test
    void testIsDefault_2() throws Exception {
        assertThat(ConfigUtils.isDefault("TRUE"), is(true));
    }

    @Test
    void testIsDefault_3() throws Exception {
        assertThat(ConfigUtils.isDefault("default"), is(true));
    }

    @Test
    void testIsDefault_4() throws Exception {
        assertThat(ConfigUtils.isDefault("DEFAULT"), is(true));
    }
}
