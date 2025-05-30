package org.apache.dubbo.common;

import org.apache.dubbo.common.url.component.ServiceConfigURL;
import org.apache.dubbo.common.utils.CollectionUtils;
import org.apache.dubbo.common.utils.SystemPropertyConfigUtils;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.apache.dubbo.common.constants.CommonConstants.OS_WIN_PREFIX;
import static org.apache.dubbo.common.constants.CommonConstants.SystemProperty.SYSTEM_OS_NAME;
import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class URLTest_Parameterized {

    private void assertURLStrDecoder(URL url) {
        String fullURLStr = url.toFullString();
        URL newUrl = URLStrParser.parseEncodedStr(URL.encode(fullURLStr));
        assertEquals(URL.valueOf(fullURLStr), newUrl);
        URL newUrl2 = URLStrParser.parseDecodedStr(fullURLStr);
        assertEquals(URL.valueOf(fullURLStr), newUrl2);
    }

    @ParameterizedTest
    @MethodSource("Provider_testDefaultPort_1to2")
    void testDefaultPort_1to2(String param1, String param2, int param3) {
        Assertions.assertEquals(param1, URL.appendDefaultPort(param2, param3));
    }

    static public Stream<Arguments> Provider_testDefaultPort_1to2() {
        return Stream.of(arguments("10.20.153.10:2181", "10.20.153.10:0", 2181), arguments("10.20.153.10:2181", "10.20.153.10", 2181));
    }
}
