package org.apache.dubbo.common;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ProtocolServiceKeyMatcherTest_Purified {

    @Test
    void testProtocol_1() {
        Assertions.assertTrue(ProtocolServiceKey.Matcher.isMatch(new ProtocolServiceKey(null, null, null, "dubbo"), new ProtocolServiceKey(null, null, null, "dubbo")));
    }

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

    @Test
    void testProtocol_5() {
        Assertions.assertTrue(ProtocolServiceKey.Matcher.isMatch(new ProtocolServiceKey(null, null, null, ""), new ProtocolServiceKey(null, null, null, "dubbo")));
    }

    @Test
    void testProtocol_6() {
        Assertions.assertTrue(ProtocolServiceKey.Matcher.isMatch(new ProtocolServiceKey(null, null, null, "*"), new ProtocolServiceKey(null, null, null, "dubbo")));
    }

    @Test
    void testProtocol_7() {
        Assertions.assertFalse(ProtocolServiceKey.Matcher.isMatch(new ProtocolServiceKey(null, null, null, "dubbo1,dubbo2"), new ProtocolServiceKey(null, null, null, "dubbo")));
    }

    @Test
    void testProtocol_8() {
        Assertions.assertTrue(ProtocolServiceKey.Matcher.isMatch(new ProtocolServiceKey(null, null, null, "dubbo1,dubbo2"), new ProtocolServiceKey(null, null, null, "dubbo1")));
    }

    @Test
    void testProtocol_9() {
        Assertions.assertTrue(ProtocolServiceKey.Matcher.isMatch(new ProtocolServiceKey(null, null, null, "dubbo1,dubbo2"), new ProtocolServiceKey(null, null, null, "dubbo2")));
    }

    @Test
    void testProtocol_10() {
        Assertions.assertTrue(ProtocolServiceKey.Matcher.isMatch(new ProtocolServiceKey(null, null, null, "dubbo1,,dubbo2"), new ProtocolServiceKey(null, null, null, null)));
    }

    @Test
    void testProtocol_11() {
        Assertions.assertTrue(ProtocolServiceKey.Matcher.isMatch(new ProtocolServiceKey(null, null, null, "dubbo1,,dubbo2"), new ProtocolServiceKey(null, null, null, "")));
    }

    @Test
    void testProtocol_12() {
        Assertions.assertTrue(ProtocolServiceKey.Matcher.isMatch(new ProtocolServiceKey(null, null, null, ",dubbo1,dubbo2"), new ProtocolServiceKey(null, null, null, null)));
    }

    @Test
    void testProtocol_13() {
        Assertions.assertTrue(ProtocolServiceKey.Matcher.isMatch(new ProtocolServiceKey(null, null, null, ",dubbo1,dubbo2"), new ProtocolServiceKey(null, null, null, "")));
    }

    @Test
    void testProtocol_14() {
        Assertions.assertTrue(ProtocolServiceKey.Matcher.isMatch(new ProtocolServiceKey(null, null, null, "dubbo1,dubbo2,"), new ProtocolServiceKey(null, null, null, null)));
    }

    @Test
    void testProtocol_15() {
        Assertions.assertTrue(ProtocolServiceKey.Matcher.isMatch(new ProtocolServiceKey(null, null, null, "dubbo1,dubbo2,"), new ProtocolServiceKey(null, null, null, "")));
    }

    @Test
    void testProtocol_16() {
        Assertions.assertFalse(ProtocolServiceKey.Matcher.isMatch(new ProtocolServiceKey(null, null, null, "dubbo1,,dubbo2"), new ProtocolServiceKey(null, null, null, "dubbo")));
    }

    @Test
    void testProtocol_17() {
        Assertions.assertFalse(ProtocolServiceKey.Matcher.isMatch(new ProtocolServiceKey(null, null, null, ",dubbo1,dubbo2"), new ProtocolServiceKey(null, null, null, "dubbo")));
    }

    @Test
    void testProtocol_18() {
        Assertions.assertFalse(ProtocolServiceKey.Matcher.isMatch(new ProtocolServiceKey(null, null, null, "dubbo1,dubbo2,"), new ProtocolServiceKey(null, null, null, "dubbo")));
    }
}
