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

class URLTest_Purified {

    private void assertURLStrDecoder(URL url) {
        String fullURLStr = url.toFullString();
        URL newUrl = URLStrParser.parseEncodedStr(URL.encode(fullURLStr));
        assertEquals(URL.valueOf(fullURLStr), newUrl);
        URL newUrl2 = URLStrParser.parseDecodedStr(fullURLStr);
        assertEquals(URL.valueOf(fullURLStr), newUrl2);
    }

    @Test
    void testDefaultPort_1() {
        Assertions.assertEquals("10.20.153.10:2181", URL.appendDefaultPort("10.20.153.10:0", 2181));
    }

    @Test
    void testDefaultPort_2() {
        Assertions.assertEquals("10.20.153.10:2181", URL.appendDefaultPort("10.20.153.10", 2181));
    }
}
