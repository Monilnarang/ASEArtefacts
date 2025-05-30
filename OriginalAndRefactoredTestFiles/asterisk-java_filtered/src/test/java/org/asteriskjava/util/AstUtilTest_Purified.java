package org.asteriskjava.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AstUtilTest_Purified {

    @Test
    void testIsTrue_1() {
        assertTrue(AstUtil.isTrue("on"), "on must be true");
    }

    @Test
    void testIsTrue_2() {
        assertTrue(AstUtil.isTrue("ON"), "ON must be true");
    }

    @Test
    void testIsTrue_3() {
        assertTrue(AstUtil.isTrue("Enabled"), "Enabled must be true");
    }

    @Test
    void testIsTrue_4() {
        assertTrue(AstUtil.isTrue("true"), "true must be true");
    }

    @Test
    void testIsTrue_5() {
        assertFalse(AstUtil.isTrue("false"), "false must be false");
    }

    @Test
    void testIsTrue_6() {
        assertFalse(AstUtil.isTrue(null), "null must be false");
    }

    @Test
    void testParseCallerIdName_1() {
        assertEquals("Hans Wurst", AstUtil.parseCallerId("\"Hans Wurst\"<1234>")[0]);
    }

    @Test
    void testParseCallerIdName_2() {
        assertEquals("Hans Wurst", AstUtil.parseCallerId("\"Hans Wurst\" <1234>")[0]);
    }

    @Test
    void testParseCallerIdName_3() {
        assertEquals("Hans Wurst", AstUtil.parseCallerId("\" Hans Wurst \" <1234>")[0]);
    }

    @Test
    void testParseCallerIdName_4() {
        assertEquals("Hans Wurst", AstUtil.parseCallerId("  \"Hans Wurst  \"   <1234>  ")[0]);
    }

    @Test
    void testParseCallerIdName_5() {
        assertEquals("Hans Wurst", AstUtil.parseCallerId("  \"  Hans Wurst  \"   <1234>  ")[0]);
    }

    @Test
    void testParseCallerIdName_6() {
        assertEquals("Hans Wurst", AstUtil.parseCallerId("Hans Wurst <1234>")[0]);
    }

    @Test
    void testParseCallerIdName_7() {
        assertEquals("Hans Wurst", AstUtil.parseCallerId(" Hans Wurst  <1234>  ")[0]);
    }

    @Test
    void testParseCallerIdName_8() {
        assertEquals("Hans <Wurst>", AstUtil.parseCallerId("\"Hans <Wurst>\" <1234>")[0]);
    }

    @Test
    void testParseCallerIdName_9() {
        assertEquals("Hans Wurst", AstUtil.parseCallerId("Hans Wurst")[0]);
    }

    @Test
    void testParseCallerIdName_10() {
        assertEquals("Hans Wurst", AstUtil.parseCallerId(" Hans Wurst ")[0]);
    }

    @Test
    void testParseCallerIdName_11() {
        assertEquals(null, AstUtil.parseCallerId("<1234>")[0]);
    }

    @Test
    void testParseCallerIdName_12() {
        assertEquals(null, AstUtil.parseCallerId(" <1234> ")[0]);
    }

    @Test
    void testParseCallerIdName_13() {
        assertEquals("<1234", AstUtil.parseCallerId(" <1234 ")[0]);
    }

    @Test
    void testParseCallerIdName_14() {
        assertEquals("1234>", AstUtil.parseCallerId(" 1234> ")[0]);
    }

    @Test
    void testParseCallerIdName_15() {
        assertEquals(null, AstUtil.parseCallerId(null)[0]);
    }

    @Test
    void testParseCallerIdName_16() {
        assertEquals(null, AstUtil.parseCallerId("")[0]);
    }

    @Test
    void testParseCallerIdName_17() {
        assertEquals(null, AstUtil.parseCallerId(" ")[0]);
    }

    @Test
    void testParseCallerIdNumber_1() {
        assertEquals("1234", AstUtil.parseCallerId("\"Hans Wurst\"<1234>")[1]);
    }

    @Test
    void testParseCallerIdNumber_2() {
        assertEquals("1234", AstUtil.parseCallerId("\"Hans Wurst\" <1234>")[1]);
    }

    @Test
    void testParseCallerIdNumber_3() {
        assertEquals("1234", AstUtil.parseCallerId("Hans Wurst <  1234 >   ")[1]);
    }

    @Test
    void testParseCallerIdNumber_4() {
        assertEquals(null, AstUtil.parseCallerId("\"Hans Wurst\"")[1]);
    }

    @Test
    void testParseCallerIdNumber_5() {
        assertEquals(null, AstUtil.parseCallerId("Hans Wurst")[1]);
    }

    @Test
    void testParseCallerIdNumber_6() {
        assertEquals(null, AstUtil.parseCallerId("Hans Wurst <>")[1]);
    }

    @Test
    void testParseCallerIdNumber_7() {
        assertEquals(null, AstUtil.parseCallerId("Hans Wurst <  > ")[1]);
    }

    @Test
    void testParseCallerIdNumber_8() {
        assertEquals(null, AstUtil.parseCallerId(null)[1]);
    }

    @Test
    void testParseCallerIdNumber_9() {
        assertEquals(null, AstUtil.parseCallerId("")[1]);
    }

    @Test
    void testParseCallerIdNumber_10() {
        assertEquals(null, AstUtil.parseCallerId(" ")[1]);
    }

    @Test
    void testIsNull_1() {
        assertTrue(AstUtil.isNull(null), "null must be null");
    }

    @Test
    void testIsNull_2() {
        assertTrue(AstUtil.isNull("unknown"), "unknown must be null");
    }

    @Test
    void testIsNull_3() {
        assertTrue(AstUtil.isNull("<unknown>"), "<unknown> must be null");
    }

    @Test
    void testIsEqual_1() {
        assertTrue(AstUtil.isEqual(null, null));
    }

    @Test
    void testIsEqual_2() {
        assertTrue(AstUtil.isEqual("", ""));
    }

    @Test
    void testIsEqual_3() {
        assertFalse(AstUtil.isEqual(null, ""));
    }

    @Test
    void testIsEqual_4() {
        assertFalse(AstUtil.isEqual("", null));
    }

    @Test
    void testIsEqual_5() {
        assertFalse(AstUtil.isEqual("", "1"));
    }
}
