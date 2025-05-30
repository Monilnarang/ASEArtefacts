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

class UrlUtilsTest_Purified {

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
    void testIsItemMatch_3() throws Exception {
        assertTrue(!UrlUtils.isItemMatch(null, "1"));
    }

    @Test
    void testIsItemMatch_4() throws Exception {
        assertTrue(UrlUtils.isItemMatch("1", "1"));
    }

    @Test
    void testIsItemMatch_5() throws Exception {
        assertTrue(UrlUtils.isItemMatch("*", null));
    }

    @Test
    void testIsItemMatch_6() throws Exception {
        assertTrue(UrlUtils.isItemMatch("*", "*"));
    }

    @Test
    void testIsItemMatch_7() throws Exception {
        assertTrue(UrlUtils.isItemMatch("*", "1234"));
    }

    @Test
    void testIsItemMatch_8() throws Exception {
        assertTrue(!UrlUtils.isItemMatch(null, "*"));
    }

    @Test
    void testIsMatchGlobPattern_1() throws Exception {
        assertTrue(UrlUtils.isMatchGlobPattern("*", "value"));
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
    void testIsMatchGlobPattern_4() throws Exception {
        assertTrue(UrlUtils.isMatchGlobPattern("value", "value"));
    }

    @Test
    void testIsMatchGlobPattern_5() throws Exception {
        assertTrue(UrlUtils.isMatchGlobPattern("v*", "value"));
    }

    @Test
    void testIsMatchGlobPattern_6() throws Exception {
        assertTrue(UrlUtils.isMatchGlobPattern("*e", "value"));
    }

    @Test
    void testIsMatchGlobPattern_7() throws Exception {
        assertTrue(UrlUtils.isMatchGlobPattern("v*e", "value"));
    }

    @Test
    void testIsMatchGlobPattern_8() throws Exception {
        assertTrue(UrlUtils.isMatchGlobPattern("$key", "value", URL.valueOf("dubbo://localhost:8080/Foo?key=v*e")));
    }
}
