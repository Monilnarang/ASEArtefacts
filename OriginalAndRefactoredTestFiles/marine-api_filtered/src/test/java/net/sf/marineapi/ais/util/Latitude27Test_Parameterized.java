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

public class Latitude27Test_Parameterized {

    private static final double DELTA = 0.00001;

    @Test
    public void conversionToKnotsWorks_3() {
        assertEquals(0.0, Latitude27.toDegrees(0), 0.00001);
    }

    @ParameterizedTest
    @MethodSource("Provider_conversionToKnotsWorks_1_1to2")
    public void conversionToKnotsWorks_1_1to2(double param1, int param2, int param3, double param4) {
        assertEquals(-param1, Latitude27.toDegrees(Double.valueOf(-param4 * param3 * param2).intValue()), DELTA);
    }

    static public Stream<Arguments> Provider_conversionToKnotsWorks_1_1to2() {
        return Stream.of(arguments(90.0, 10000, 60, 90.0), arguments(45.1, 10000, 60, 45.1), arguments(101.1, 10000, 60, 101.1));
    }

    @ParameterizedTest
    @MethodSource("Provider_conversionToKnotsWorks_2to5")
    public void conversionToKnotsWorks_2to5(double param1, int param2, double param3, int param4) {
        assertEquals(param1, Latitude27.toDegrees(Double.valueOf(param3 * param4 * param2).intValue()), DELTA);
    }

    static public Stream<Arguments> Provider_conversionToKnotsWorks_2to5() {
        return Stream.of(arguments(45.9, 10000, 45.9, 60), arguments(90.0, 10000, 90.0, 60), arguments(91.1, 10000, 91.1, 60), arguments(102.3, 10000, 102.3, 60));
    }
}
