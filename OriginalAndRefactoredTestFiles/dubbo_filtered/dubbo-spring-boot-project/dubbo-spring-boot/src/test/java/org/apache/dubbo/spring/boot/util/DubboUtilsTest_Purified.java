package org.apache.dubbo.spring.boot.util;

import org.junit.jupiter.api.Test;
import static org.apache.dubbo.spring.boot.util.DubboUtils.BASE_PACKAGES_PROPERTY_NAME;
import static org.apache.dubbo.spring.boot.util.DubboUtils.DEFAULT_MULTIPLE_CONFIG_PROPERTY_VALUE;
import static org.apache.dubbo.spring.boot.util.DubboUtils.DEFAULT_OVERRIDE_CONFIG_PROPERTY_VALUE;
import static org.apache.dubbo.spring.boot.util.DubboUtils.DUBBO_APPLICATION_ID_PROPERTY;
import static org.apache.dubbo.spring.boot.util.DubboUtils.DUBBO_APPLICATION_NAME_PROPERTY;
import static org.apache.dubbo.spring.boot.util.DubboUtils.DUBBO_APPLICATION_QOS_ENABLE_PROPERTY;
import static org.apache.dubbo.spring.boot.util.DubboUtils.DUBBO_CONFIG_MULTIPLE_PROPERTY;
import static org.apache.dubbo.spring.boot.util.DubboUtils.DUBBO_CONFIG_PREFIX;
import static org.apache.dubbo.spring.boot.util.DubboUtils.DUBBO_GITHUB_URL;
import static org.apache.dubbo.spring.boot.util.DubboUtils.DUBBO_MAILING_LIST;
import static org.apache.dubbo.spring.boot.util.DubboUtils.DUBBO_PREFIX;
import static org.apache.dubbo.spring.boot.util.DubboUtils.DUBBO_SCAN_PREFIX;
import static org.apache.dubbo.spring.boot.util.DubboUtils.DUBBO_SPRING_BOOT_GITHUB_URL;
import static org.apache.dubbo.spring.boot.util.DubboUtils.DUBBO_SPRING_BOOT_GIT_URL;
import static org.apache.dubbo.spring.boot.util.DubboUtils.DUBBO_SPRING_BOOT_ISSUES_URL;
import static org.apache.dubbo.spring.boot.util.DubboUtils.MULTIPLE_CONFIG_PROPERTY_NAME;
import static org.apache.dubbo.spring.boot.util.DubboUtils.OVERRIDE_CONFIG_FULL_PROPERTY_NAME;
import static org.apache.dubbo.spring.boot.util.DubboUtils.SPRING_APPLICATION_NAME_PROPERTY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DubboUtilsTest_Purified {

    @Test
    void testConstants_1() {
        assertEquals("dubbo", DUBBO_PREFIX);
    }

    @Test
    void testConstants_2() {
        assertEquals("dubbo.scan.", DUBBO_SCAN_PREFIX);
    }

    @Test
    void testConstants_3() {
        assertEquals("base-packages", BASE_PACKAGES_PROPERTY_NAME);
    }

    @Test
    void testConstants_4() {
        assertEquals("dubbo.config.", DUBBO_CONFIG_PREFIX);
    }

    @Test
    void testConstants_5() {
        assertEquals("multiple", MULTIPLE_CONFIG_PROPERTY_NAME);
    }

    @Test
    void testConstants_6() {
        assertEquals("dubbo.config.override", OVERRIDE_CONFIG_FULL_PROPERTY_NAME);
    }

    @Test
    void testConstants_7() {
        assertEquals("https://github.com/apache/dubbo/tree/3.0/dubbo-spring-boot", DUBBO_SPRING_BOOT_GITHUB_URL);
    }

    @Test
    void testConstants_8() {
        assertEquals("https://github.com/apache/dubbo.git", DUBBO_SPRING_BOOT_GIT_URL);
    }

    @Test
    void testConstants_9() {
        assertEquals("https://github.com/apache/dubbo/issues", DUBBO_SPRING_BOOT_ISSUES_URL);
    }

    @Test
    void testConstants_10() {
        assertEquals("https://github.com/apache/dubbo", DUBBO_GITHUB_URL);
    }

    @Test
    void testConstants_11() {
        assertEquals("dev@dubbo.apache.org", DUBBO_MAILING_LIST);
    }

    @Test
    void testConstants_12() {
        assertEquals("spring.application.name", SPRING_APPLICATION_NAME_PROPERTY);
    }

    @Test
    void testConstants_13() {
        assertEquals("dubbo.application.id", DUBBO_APPLICATION_ID_PROPERTY);
    }

    @Test
    void testConstants_14() {
        assertEquals("dubbo.application.name", DUBBO_APPLICATION_NAME_PROPERTY);
    }

    @Test
    void testConstants_15() {
        assertEquals("dubbo.application.qos-enable", DUBBO_APPLICATION_QOS_ENABLE_PROPERTY);
    }

    @Test
    void testConstants_16() {
        assertEquals("dubbo.config.multiple", DUBBO_CONFIG_MULTIPLE_PROPERTY);
    }

    @Test
    void testConstants_17() {
        assertTrue(DEFAULT_MULTIPLE_CONFIG_PROPERTY_VALUE);
    }

    @Test
    void testConstants_18() {
        assertTrue(DEFAULT_OVERRIDE_CONFIG_PROPERTY_VALUE);
    }
}
