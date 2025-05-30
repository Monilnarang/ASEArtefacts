package com.graphhopper.routing.util.parsers;

import com.graphhopper.routing.util.parsers.helpers.OSMValueExtractor;
import org.junit.jupiter.api.Test;
import static com.graphhopper.routing.util.parsers.helpers.OSMValueExtractor.conditionalWeightToTons;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class OSMValueExtractorTest_Parameterized {

    private final double DELTA = 0.001;

    @ParameterizedTest
    @MethodSource("Provider_stringToTons_1to11")
    public void stringToTons_1to11(double param1, double param2) {
        assertEquals(param1, OSMValueExtractor.stringToTons(param2), DELTA);
    }

    static public Stream<Arguments> Provider_stringToTons_1to11() {
        return Stream.of(arguments(1.5, 1.5), arguments(1.5, "1.5 t"), arguments(1.5, "1.5   t"), arguments(1.5, "1.5 tons"), arguments(1.5, "1.5 ton"), arguments(1.5, "3306.9 lbs"), arguments(3, "3 T"), arguments(3, "3ton"), arguments(10, "10000 kg"), arguments(25.401, "28 st"), arguments(6, "6t mgw"));
    }

    @ParameterizedTest
    @MethodSource("Provider_stringToMeter_1to9_12to18")
    public void stringToMeter_1to9_12to18(double param1, double param2) {
        assertEquals(param1, OSMValueExtractor.stringToMeter(param2), DELTA);
    }

    static public Stream<Arguments> Provider_stringToMeter_1to9_12to18() {
        return Stream.of(arguments(1.5, 1.5), arguments(1.5, "1.5m"), arguments(1.5, "1.5 m"), arguments(1.5, "1.5   m"), arguments(1.5, "1.5 meter"), arguments(1.499, "4 ft 11 in"), arguments(1.499, "4'11''"), arguments(3, "3 m."), arguments(3, "3meters"), arguments(2.921, "9 ft 7in"), arguments(2.921, "9'7\""), arguments(2.921, "9'7''"), arguments(2.921, "9' 7\""), arguments(2.743, "9'"), arguments(2.743, "9 feet"), arguments(1.5, "150 cm"));
    }

    @ParameterizedTest
    @MethodSource("Provider_stringToMeter_10to11")
    public void stringToMeter_10to11(double param1, int param2, String param3) {
        assertEquals(param1 * param2, OSMValueExtractor.stringToMeter(param3), DELTA);
    }

    static public Stream<Arguments> Provider_stringToMeter_10to11() {
        return Stream.of(arguments(0.8, 3, "~3"), arguments(3, 0.8, "3 m approx"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testConditionalWeightToTons_1to2")
    public void testConditionalWeightToTons_1to2(double param1, String param2) {
        assertEquals(param1, conditionalWeightToTons(param2));
    }

    static public Stream<Arguments> Provider_testConditionalWeightToTons_1to2() {
        return Stream.of(arguments(7.5, "no @ (weight>7.5)"), arguments(7.5, "delivery @ (Mo-Sa 06:00-12:00); no @ (weight>7.5); no @ (length>12)"));
    }
}
