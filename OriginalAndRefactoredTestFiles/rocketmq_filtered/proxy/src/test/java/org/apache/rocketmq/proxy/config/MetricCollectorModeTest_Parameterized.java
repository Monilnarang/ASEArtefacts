package org.apache.rocketmq.proxy.config;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class MetricCollectorModeTest_Parameterized {

    @ParameterizedTest
    @MethodSource("Provider_testGetEnumByOrdinal_1_4")
    public void testGetEnumByOrdinal_1_4(String param1) {
        Assert.assertEquals(MetricCollectorMode.OFF, MetricCollectorMode.getEnumByString(param1));
    }

    static public Stream<Arguments> Provider_testGetEnumByOrdinal_1_4() {
        return Stream.of(arguments("off"), arguments("OFF"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetEnumByOrdinal_2_5")
    public void testGetEnumByOrdinal_2_5(String param1) {
        Assert.assertEquals(MetricCollectorMode.ON, MetricCollectorMode.getEnumByString(param1));
    }

    static public Stream<Arguments> Provider_testGetEnumByOrdinal_2_5() {
        return Stream.of(arguments("on"), arguments("ON"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetEnumByOrdinal_3_6")
    public void testGetEnumByOrdinal_3_6(String param1) {
        Assert.assertEquals(MetricCollectorMode.PROXY, MetricCollectorMode.getEnumByString(param1));
    }

    static public Stream<Arguments> Provider_testGetEnumByOrdinal_3_6() {
        return Stream.of(arguments("proxy"), arguments("PROXY"));
    }
}
