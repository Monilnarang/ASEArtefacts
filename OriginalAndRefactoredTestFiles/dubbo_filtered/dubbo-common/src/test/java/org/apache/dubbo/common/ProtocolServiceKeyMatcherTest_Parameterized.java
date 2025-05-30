package org.apache.dubbo.common;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class ProtocolServiceKeyMatcherTest_Parameterized {

    @Test
    void testProtocol_2() {
        Assertions.assertFalse(ProtocolServiceKey.Matcher.isMatch(new ProtocolServiceKey(null, null, null, "dubbo"), new ProtocolServiceKey(null, null, null, null)));
    }

    @Test
    void testProtocol_3() {
        Assertions.assertFalse(ProtocolServiceKey.Matcher.isMatch(new ProtocolServiceKey(null, null, null, "dubbo"), new ProtocolServiceKey("DemoService", null, null, "dubbo")));
    }

    @Test
    void testProtocol_4() {
        Assertions.assertTrue(ProtocolServiceKey.Matcher.isMatch(new ProtocolServiceKey(null, null, null, null), new ProtocolServiceKey(null, null, null, "dubbo")));
    }

    @ParameterizedTest
    @MethodSource("Provider_testProtocol_1_5to6_8to9_11_13_15")
    void testProtocol_1_5to6_8to9_11_13_15(String param1, String param2) {
        Assertions.assertTrue(ProtocolServiceKey.Matcher.isMatch(new ProtocolServiceKey(param1, param2, null, "dubbo"), new ProtocolServiceKey(null, null, null, "dubbo")));
    }

    static public Stream<Arguments> Provider_testProtocol_1_5to6_8to9_11_13_15() {
        return Stream.of(arguments("dubbo", "dubbo"), arguments("", "dubbo"), arguments("*", "dubbo"), arguments("dubbo1,dubbo2", "dubbo1"), arguments("dubbo1,dubbo2", "dubbo2"), arguments("dubbo1,,dubbo2", ""), arguments(",dubbo1,dubbo2", ""), arguments("dubbo1,dubbo2,", ""));
    }

    @ParameterizedTest
    @MethodSource("Provider_testProtocol_7_16to18")
    void testProtocol_7_16to18(String param1, String param2) {
        Assertions.assertFalse(ProtocolServiceKey.Matcher.isMatch(new ProtocolServiceKey(param1, param2, null, "dubbo1,dubbo2"), new ProtocolServiceKey(null, null, null, "dubbo")));
    }

    static public Stream<Arguments> Provider_testProtocol_7_16to18() {
        return Stream.of(arguments("dubbo1,dubbo2", "dubbo"), arguments("dubbo1,,dubbo2", "dubbo"), arguments(",dubbo1,dubbo2", "dubbo"), arguments("dubbo1,dubbo2,", "dubbo"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testProtocol_10_12_14")
    void testProtocol_10_12_14(String param1) {
        Assertions.assertTrue(ProtocolServiceKey.Matcher.isMatch(new ProtocolServiceKey(param1, null, null, "dubbo1,,dubbo2"), new ProtocolServiceKey(null, null, null, null)));
    }

    static public Stream<Arguments> Provider_testProtocol_10_12_14() {
        return Stream.of(arguments("dubbo1,,dubbo2"), arguments(",dubbo1,dubbo2"), arguments("dubbo1,dubbo2,"));
    }
}
