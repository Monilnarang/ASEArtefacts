package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class JaroWinklerSimilarityTest_Parameterized {

    private static JaroWinklerSimilarity similarity;

    @BeforeAll
    public static void setUp() {
        similarity = new JaroWinklerSimilarity();
    }

    private static CharSequence wrap(final String string) {
        return new CharSequence() {

            @Override
            public char charAt(final int index) {
                return string.charAt(index);
            }

            @Override
            public boolean equals(final Object obj) {
                return string.equals(obj);
            }

            @Override
            public int hashCode() {
                return string.hashCode();
            }

            @Override
            public int length() {
                return string.length();
            }

            @Override
            public CharSequence subSequence(final int start, final int end) {
                return string.subSequence(start, end);
            }

            @Override
            public String toString() {
                return string;
            }
        };
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetJaroWinklerSimilarity_StringString_1to16")
    public void testGetJaroWinklerSimilarity_StringString_1to16(double param1, double param2, String param3, String param4) {
        assertEquals(param1, similarity.apply(wrap(param4), param3), param2);
    }

    static public Stream<Arguments> Provider_testGetJaroWinklerSimilarity_StringString_1to16() {
        return Stream.of(arguments(1d, 0.00001d, "", ""), arguments(1d, 0.00001d, "foo", "foo"), arguments(0.94166d, 0.00001d, "foo ", "foo"), arguments(0.90666d, 0.00001d, "foo  ", "foo"), arguments(0.86666d, 0.00001d, " foo ", "foo"), arguments(0.51111d, 0.00001d, "  foo", "foo"), arguments(0.92499d, 0.00001d, "fog", "frog"), arguments(0.0d, 0.00000000000000000001d, "ant", "fly"), arguments(0.44166d, 0.00001d, "hippo", "elephant"), arguments(0.90666d, 0.00001d, "ABC Corp", "ABC Corporation"), arguments(0.95251d, 0.00001d, "D & H Enterprises, Inc.", "D N H Enterprises Inc"), arguments(0.942d, 0.00001d, "My Gym. Childrens Fitness", "My Gym Children's Fitness Center"), arguments(0.898018d, 0.00001d, "PENNCISYLVNIA", "PENNSYLVANIA"), arguments(0.971428d, 0.00001d, "/opt/software2", "/opt/software1"), arguments(0.941666d, 0.00001d, "aaacdb", "aaabcd"), arguments(0.911111d, 0.00001d, "John Hopkins", "John Horn"));
    }
}
