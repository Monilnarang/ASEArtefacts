package net.sf.marineapi.ais.util;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class SpeedOverGroundTest_Parameterized {

    private static final double DELTA = 0.001;

    @Test
    public void conversionReturnsOnInvalidValues_1() {
        assertEquals(-10.1, SpeedOverGround.toKnots(-101), DELTA);
    }

    @ParameterizedTest
    @MethodSource("Provider_conversionToKnotsWorks_1to2_2to3_3to4")
    public void conversionToKnotsWorks_1to2_2to3_3to4(double param1, int param2) {
        assertEquals(param1, SpeedOverGround.toKnots(param2), DELTA);
    }

    static public Stream<Arguments> Provider_conversionToKnotsWorks_1to2_2to3_3to4() {
        return Stream.of(arguments(0.0, 0), arguments(0.1, 1), arguments(90.9, 909), arguments(102.2, 1022), arguments(102.3, 1023), arguments(4567.8, 45678));
    }
}
