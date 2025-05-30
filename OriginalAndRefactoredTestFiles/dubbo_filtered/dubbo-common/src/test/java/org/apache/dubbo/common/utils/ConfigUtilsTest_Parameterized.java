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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class ConfigUtilsTest_Parameterized {

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

    @ParameterizedTest
    @MethodSource("Provider_testIsEmpty_2to9")
    void testIsEmpty_2to9(String param1) throws Exception {
        assertThat(ConfigUtils.isEmpty(param1), is(true));
    }

    static public Stream<Arguments> Provider_testIsEmpty_2to9() {
        return Stream.of(arguments(""), arguments(false), arguments(false), arguments(0), arguments(null), arguments("NULL"), arguments("n/a"), arguments("N/A"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsDefault_1to4")
    void testIsDefault_1to4(boolean param1) throws Exception {
        assertThat(ConfigUtils.isDefault(param1), is(true));
    }

    static public Stream<Arguments> Provider_testIsDefault_1to4() {
        return Stream.of(arguments(true), arguments(true), arguments("default"), arguments("DEFAULT"));
    }
}
