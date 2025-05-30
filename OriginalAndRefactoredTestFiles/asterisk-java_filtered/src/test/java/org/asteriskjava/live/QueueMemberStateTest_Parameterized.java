package org.asteriskjava.live;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class QueueMemberStateTest_Parameterized {

    @ParameterizedTest
    @MethodSource("Provider_testValueOf_1to2")
    void testValueOf_1to2(String param1) {
        assertEquals(QueueMemberState.DEVICE_INUSE, QueueMemberState.valueOf(param1));
    }

    static public Stream<Arguments> Provider_testValueOf_1to2() {
        return Stream.of(arguments("DEVICE_INUSE"), arguments(2));
    }
}
