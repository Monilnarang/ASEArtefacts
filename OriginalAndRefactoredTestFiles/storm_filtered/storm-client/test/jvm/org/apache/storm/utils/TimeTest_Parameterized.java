package org.apache.storm.utils;

import org.apache.storm.utils.Time.SimulatedTime;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class TimeTest_Parameterized {

    @ParameterizedTest
    @MethodSource("Provider_secsToMillisLongTest_1to6")
    public void secsToMillisLongTest_1to6(int param1, int param2) {
        assertEquals(Time.secsToMillisLong(param2), param1);
    }

    static public Stream<Arguments> Provider_secsToMillisLongTest_1to6() {
        return Stream.of(arguments(0, 0), arguments(2, 0.002), arguments(1000, 1), arguments(1080, 1.08), arguments(10000, 10), arguments(10100, 10.1));
    }
}
