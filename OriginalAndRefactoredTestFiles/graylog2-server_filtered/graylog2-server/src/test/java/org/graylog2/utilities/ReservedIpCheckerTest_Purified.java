package org.graylog2.utilities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ReservedIpCheckerTest_Purified {

    @Test
    void testIsReservedIpAddress_1() {
        Assertions.assertTrue(ReservedIpChecker.getInstance().isReservedIpAddress("127.0.0.1"));
    }

    @Test
    void testIsReservedIpAddress_2() {
        Assertions.assertTrue(ReservedIpChecker.getInstance().isReservedIpAddress("192.168.1.10"));
    }

    @Test
    void testIsReservedIpAddress_3() {
        Assertions.assertFalse(ReservedIpChecker.getInstance().isReservedIpAddress("104.44.23.89"));
    }
}
