package org.apache.commons.validator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class GenericValidatorTest_Parameterized {

    @ParameterizedTest
    @MethodSource("Provider_testMaxLength_1_5to6_9to11")
    public void testMaxLength_1_5to6_9to11(String param1, String param2, int param3, int param4) {
        assertFalse(GenericValidator.maxLength(param2, param3, param4), param1);
    }

    static public Stream<Arguments> Provider_testMaxLength_1_5to6_9to11() {
        return Stream.of(arguments("Max=4 End=0", "12345\n\r", 4, 0), arguments("Max=4 End=1", "12345\n\r", 4, 1), arguments("Max=5 End=1", "12345\n\r", 5, 1), arguments("Max=4 End=2", "12345\n\r", 4, 2), arguments("Max=5 End=2", "12345\n\r", 5, 2), arguments("Max=6 End=2", "12345\n\r", 6, 2));
    }

    @ParameterizedTest
    @MethodSource("Provider_testMaxLength_2to4_7to8_12")
    public void testMaxLength_2to4_7to8_12(String param1, String param2, int param3, int param4) {
        assertTrue(GenericValidator.maxLength(param2, param3, param4), param1);
    }

    static public Stream<Arguments> Provider_testMaxLength_2to4_7to8_12() {
        return Stream.of(arguments("Max=5 End=0", "12345\n\r", 5, 0), arguments("Max=6 End=0", "12345\n\r", 6, 0), arguments("Max=7 End=0", "12345\n\r", 7, 0), arguments("Max=6 End=1", "12345\n\r", 6, 1), arguments("Max=7 End=1", "12345\n\r", 7, 1), arguments("Max=7 End=2", "12345\n\r", 7, 2));
    }

    @ParameterizedTest
    @MethodSource("Provider_testMinLength_1_5to6_9to11")
    public void testMinLength_1_5to6_9to11(String param1, String param2, int param3, int param4) {
        assertTrue(GenericValidator.minLength(param2, param3, param4), param1);
    }

    static public Stream<Arguments> Provider_testMinLength_1_5to6_9to11() {
        return Stream.of(arguments("Min=5 End=0", "12345\n\r", 5, 0), arguments("Min=5 End=1", "12345\n\r", 5, 1), arguments("Min=6 End=1", "12345\n\r", 6, 1), arguments("Min=5 End=2", "12345\n\r", 5, 2), arguments("Min=6 End=2", "12345\n\r", 6, 2), arguments("Min=7 End=2", "12345\n\r", 7, 2));
    }

    @ParameterizedTest
    @MethodSource("Provider_testMinLength_2to4_7to8_12")
    public void testMinLength_2to4_7to8_12(String param1, String param2, int param3, int param4) {
        assertFalse(GenericValidator.minLength(param2, param3, param4), param1);
    }

    static public Stream<Arguments> Provider_testMinLength_2to4_7to8_12() {
        return Stream.of(arguments("Min=6 End=0", "12345\n\r", 6, 0), arguments("Min=7 End=0", "12345\n\r", 7, 0), arguments("Min=8 End=0", "12345\n\r", 8, 0), arguments("Min=7 End=1", "12345\n\r", 7, 1), arguments("Min=8 End=1", "12345\n\r", 8, 1), arguments("Min=8 End=2", "12345\n\r", 8, 2));
    }
}
