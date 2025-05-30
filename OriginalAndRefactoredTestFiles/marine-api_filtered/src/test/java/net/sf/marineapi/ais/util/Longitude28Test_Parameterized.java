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

public class Longitude28Test_Parameterized {

    private static final double DELTA = 0.00001;

    @Test
    public void conversionToKnotsWorks_3() {
        assertEquals(0.0, Longitude28.toDegrees(0), 0.00001);
    }

    @ParameterizedTest
    @MethodSource("Provider_conversionToKnotsWorks_1_1to2")
    public void conversionToKnotsWorks_1_1to2(double param1, int param2, int param3, double param4) {
        assertEquals(-param1, Longitude28.toDegrees(Double.valueOf(-param4 * param3 * param2).intValue()), DELTA);
    }

    static public Stream<Arguments> Provider_conversionToKnotsWorks_1_1to2() {
        return Stream.of(arguments(180.0, 10000, 60, 180.0), arguments(45.1, 10000, 60, 45.1), arguments(201.1, 10000, 60, 201.1));
    }

    @ParameterizedTest
    @MethodSource("Provider_conversionToKnotsWorks_2to5")
    public void conversionToKnotsWorks_2to5(double param1, int param2, double param3, int param4) {
        assertEquals(param1, Longitude28.toDegrees(Double.valueOf(param3 * param4 * param2).intValue()), DELTA);
    }

    static public Stream<Arguments> Provider_conversionToKnotsWorks_2to5() {
        return Stream.of(arguments(45.9, 10000, 45.9, 60), arguments(180.0, 10000, 180.0, 60), arguments(181.1, 10000, 181.1, 60), arguments(202.3, 10000, 202.3, 60));
    }
}
