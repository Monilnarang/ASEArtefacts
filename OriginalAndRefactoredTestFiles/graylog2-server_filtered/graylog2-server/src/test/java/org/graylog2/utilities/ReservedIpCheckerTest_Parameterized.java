package org.graylog2.utilities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class ReservedIpCheckerTest_Parameterized {

    @Test
    void testIsReservedIpAddress_3() {
        Assertions.assertFalse(ReservedIpChecker.getInstance().isReservedIpAddress("104.44.23.89"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsReservedIpAddress_1to2")
    void testIsReservedIpAddress_1to2(String param1) {
        Assertions.assertTrue(ReservedIpChecker.getInstance().isReservedIpAddress(param1));
    }

    static public Stream<Arguments> Provider_testIsReservedIpAddress_1to2() {
        return Stream.of(arguments("127.0.0.1"), arguments("192.168.1.10"));
    }
}
