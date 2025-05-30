package org.apache.dubbo.common.utils;

import org.apache.dubbo.common.URL;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Test;
import static org.apache.dubbo.common.constants.CommonConstants.GROUP_KEY;
import static org.apache.dubbo.common.constants.CommonConstants.INTERFACE_KEY;
import static org.apache.dubbo.common.constants.CommonConstants.VERSION_KEY;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class UrlUtilsTest_Parameterized {

    String localAddress = "127.0.0.1";

    private static final String SERVICE_REGISTRY_TYPE = "service";

    private static final String REGISTRY_TYPE_KEY = "registry-type";

    @Test
    void testIsItemMatch_1() throws Exception {
        assertTrue(UrlUtils.isItemMatch(null, null));
    }

    @Test
    void testIsItemMatch_2() throws Exception {
        assertTrue(!UrlUtils.isItemMatch("1", null));
    }

    @Test
    void testIsItemMatch_5() throws Exception {
        assertTrue(UrlUtils.isItemMatch("*", null));
    }

    @Test
    void testIsMatchGlobPattern_2() throws Exception {
        assertTrue(UrlUtils.isMatchGlobPattern("", null));
    }

    @Test
    void testIsMatchGlobPattern_3() throws Exception {
        assertFalse(UrlUtils.isMatchGlobPattern("", "value"));
    }

    @Test
    void testIsMatchGlobPattern_8() throws Exception {
        assertTrue(UrlUtils.isMatchGlobPattern("$key", "value", URL.valueOf("dubbo://localhost:8080/Foo?key=v*e")));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsItemMatch_3_8")
    void testIsItemMatch_3_8(int param1) throws Exception {
        assertTrue(!UrlUtils.isItemMatch(param1, "1"));
    }

    static public Stream<Arguments> Provider_testIsItemMatch_3_8() {
        return Stream.of(arguments(1), arguments("*"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsItemMatch_4_6to7")
    void testIsItemMatch_4_6to7(int param1, int param2) throws Exception {
        assertTrue(UrlUtils.isItemMatch(param1, param2));
    }

    static public Stream<Arguments> Provider_testIsItemMatch_4_6to7() {
        return Stream.of(arguments(1, 1), arguments("*", "*"), arguments("*", 1234));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsMatchGlobPattern_1_4to7")
    void testIsMatchGlobPattern_1_4to7(String param1, String param2) throws Exception {
        assertTrue(UrlUtils.isMatchGlobPattern(param1, param2));
    }

    static public Stream<Arguments> Provider_testIsMatchGlobPattern_1_4to7() {
        return Stream.of(arguments("*", "value"), arguments("value", "value"), arguments("v*", "value"), arguments("*e", "value"), arguments("v*e", "value"));
    }
}
