package org.apache.dubbo.common;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ServiceKeyMatcherTest_Purified {

    @Test
    void testInterface_1() {
        Assertions.assertTrue(ServiceKey.Matcher.isMatch(new ServiceKey(null, null, null), new ServiceKey(null, null, null)));
    }

    @Test
    void testInterface_2() {
        Assertions.assertFalse(ServiceKey.Matcher.isMatch(new ServiceKey("DemoService", null, null), new ServiceKey(null, null, null)));
    }

    @Test
    void testInterface_3() {
        Assertions.assertFalse(ServiceKey.Matcher.isMatch(new ServiceKey(null, null, null), new ServiceKey("DemoService", null, null)));
    }

    @Test
    void testInterface_4() {
        Assertions.assertFalse(ServiceKey.Matcher.isMatch(new ServiceKey("*", null, null), new ServiceKey("DemoService", null, null)));
    }

    @Test
    void testInterface_5() {
        Assertions.assertFalse(ServiceKey.Matcher.isMatch(new ServiceKey("*", null, null), new ServiceKey(null, null, null)));
    }

    @Test
    void testVersion_1() {
        Assertions.assertTrue(ServiceKey.Matcher.isMatch(new ServiceKey(null, "1.0.0", null), new ServiceKey(null, "1.0.0", null)));
    }

    @Test
    void testVersion_2() {
        Assertions.assertTrue(ServiceKey.Matcher.isMatch(new ServiceKey(null, null, null), new ServiceKey(null, null, null)));
    }

    @Test
    void testVersion_3() {
        Assertions.assertFalse(ServiceKey.Matcher.isMatch(new ServiceKey(null, "1.0.0", null), new ServiceKey(null, null, null)));
    }

    @Test
    void testVersion_4() {
        Assertions.assertFalse(ServiceKey.Matcher.isMatch(new ServiceKey(null, null, null), new ServiceKey(null, "1.0.0", null)));
    }

    @Test
    void testVersion_5() {
        Assertions.assertTrue(ServiceKey.Matcher.isMatch(new ServiceKey(null, "1.0.0,1.0.1", null), new ServiceKey(null, "1.0.0", null)));
    }

    @Test
    void testVersion_6() {
        Assertions.assertFalse(ServiceKey.Matcher.isMatch(new ServiceKey(null, "1.0.0,1.0.1", null), new ServiceKey(null, "1.0.2", null)));
    }

    @Test
    void testVersion_7_testMerged_7() {
        Assertions.assertFalse(ServiceKey.Matcher.isMatch(new ServiceKey(null, "1.0.0,1.0.1", null), new ServiceKey(null, null, null)));
    }

    @Test
    void testVersion_8() {
        Assertions.assertTrue(ServiceKey.Matcher.isMatch(new ServiceKey(null, ",1.0.0,1.0.1", null), new ServiceKey(null, null, null)));
    }

    @Test
    void testVersion_9() {
        Assertions.assertTrue(ServiceKey.Matcher.isMatch(new ServiceKey(null, ",1.0.0,1.0.1", null), new ServiceKey(null, "", null)));
    }

    @Test
    void testVersion_10() {
        Assertions.assertTrue(ServiceKey.Matcher.isMatch(new ServiceKey(null, "1.0.0,,1.0.1", null), new ServiceKey(null, null, null)));
    }

    @Test
    void testVersion_11() {
        Assertions.assertTrue(ServiceKey.Matcher.isMatch(new ServiceKey(null, "1.0.0,,1.0.1", null), new ServiceKey(null, "", null)));
    }

    @Test
    void testVersion_12() {
        Assertions.assertTrue(ServiceKey.Matcher.isMatch(new ServiceKey(null, "1.0.0,1.0.1,", null), new ServiceKey(null, null, null)));
    }

    @Test
    void testVersion_13() {
        Assertions.assertTrue(ServiceKey.Matcher.isMatch(new ServiceKey(null, "1.0.0,1.0.1,", null), new ServiceKey(null, "", null)));
    }

    @Test
    void testVersion_15() {
        Assertions.assertFalse(ServiceKey.Matcher.isMatch(new ServiceKey(null, "1.0.0,1.0.1", null), new ServiceKey(null, "", null)));
    }

    @Test
    void testVersion_16_testMerged_15() {
        Assertions.assertFalse(ServiceKey.Matcher.isMatch(new ServiceKey(null, ",1.0.0,1.0.1", null), new ServiceKey(null, "1.0.2", null)));
    }

    @Test
    void testVersion_18() {
        Assertions.assertTrue(ServiceKey.Matcher.isMatch(new ServiceKey(null, "*", null), new ServiceKey(null, null, null)));
    }

    @Test
    void testVersion_19() {
        Assertions.assertTrue(ServiceKey.Matcher.isMatch(new ServiceKey(null, "*", null), new ServiceKey(null, "", null)));
    }

    @Test
    void testVersion_20() {
        Assertions.assertTrue(ServiceKey.Matcher.isMatch(new ServiceKey(null, "*", null), new ServiceKey(null, "1.0.0", null)));
    }

    @Test
    void testGroup_1() {
        Assertions.assertTrue(ServiceKey.Matcher.isMatch(new ServiceKey(null, null, "group1"), new ServiceKey(null, null, "group1")));
    }

    @Test
    void testGroup_2() {
        Assertions.assertFalse(ServiceKey.Matcher.isMatch(new ServiceKey(null, null, "group1"), new ServiceKey(null, null, null)));
    }

    @Test
    void testGroup_3() {
        Assertions.assertFalse(ServiceKey.Matcher.isMatch(new ServiceKey(null, null, null), new ServiceKey(null, null, "group1")));
    }

    @Test
    void testGroup_4() {
        Assertions.assertTrue(ServiceKey.Matcher.isMatch(new ServiceKey(null, null, "group1, group2"), new ServiceKey(null, null, "group1")));
    }

    @Test
    void testGroup_5() {
        Assertions.assertFalse(ServiceKey.Matcher.isMatch(new ServiceKey(null, null, "group2, group3"), new ServiceKey(null, null, "group1")));
    }

    @Test
    void testGroup_6() {
        Assertions.assertFalse(ServiceKey.Matcher.isMatch(new ServiceKey(null, null, "group2, group3"), new ServiceKey(null, null, null)));
    }

    @Test
    void testGroup_7() {
        Assertions.assertFalse(ServiceKey.Matcher.isMatch(new ServiceKey(null, null, "group2, group3"), new ServiceKey(null, null, "")));
    }

    @Test
    void testGroup_8() {
        Assertions.assertTrue(ServiceKey.Matcher.isMatch(new ServiceKey(null, null, ",group2"), new ServiceKey(null, null, "")));
    }

    @Test
    void testGroup_9() {
        Assertions.assertTrue(ServiceKey.Matcher.isMatch(new ServiceKey(null, null, "group2,"), new ServiceKey(null, null, "")));
    }

    @Test
    void testGroup_10() {
        Assertions.assertTrue(ServiceKey.Matcher.isMatch(new ServiceKey(null, null, "group2, ,group3"), new ServiceKey(null, null, "")));
    }

    @Test
    void testGroup_11() {
        Assertions.assertFalse(ServiceKey.Matcher.isMatch(new ServiceKey(null, null, ",group2"), new ServiceKey(null, null, "group1")));
    }

    @Test
    void testGroup_12() {
        Assertions.assertFalse(ServiceKey.Matcher.isMatch(new ServiceKey(null, null, "group2,"), new ServiceKey(null, null, "group1")));
    }

    @Test
    void testGroup_13() {
        Assertions.assertFalse(ServiceKey.Matcher.isMatch(new ServiceKey(null, null, "group2, ,group3"), new ServiceKey(null, null, "group1")));
    }

    @Test
    void testGroup_14() {
        Assertions.assertTrue(ServiceKey.Matcher.isMatch(new ServiceKey(null, null, "*"), new ServiceKey(null, null, "")));
    }

    @Test
    void testGroup_15() {
        Assertions.assertTrue(ServiceKey.Matcher.isMatch(new ServiceKey(null, null, "*"), new ServiceKey(null, null, null)));
    }

    @Test
    void testGroup_16() {
        Assertions.assertTrue(ServiceKey.Matcher.isMatch(new ServiceKey(null, null, "*"), new ServiceKey(null, null, "group1")));
    }
}
