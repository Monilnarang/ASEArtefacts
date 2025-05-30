package org.asteriskjava.live;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CallerIdTest_Purified {

    @Test
    void testToString_1() {
        assertEquals("\"Hans Wurst\" <1234>", new CallerId("Hans Wurst", "1234").toString());
    }

    @Test
    void testToString_2() {
        assertEquals("<1234>", new CallerId(null, "1234").toString());
    }

    @Test
    void testToString_3() {
        assertEquals("\"Hans Wurst\"", new CallerId("Hans Wurst", null).toString());
    }
}
