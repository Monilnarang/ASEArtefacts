package org.apache.commons.lang3.mutable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class MutableShortTest_Parameterized extends AbstractLangTest {

    @Test
    public void testToString_3() {
        assertEquals("-123", new MutableShort((short) -123).toString());
    }

    @ParameterizedTest
    @MethodSource("Provider_testToShort_1to2")
    public void testToShort_1to2(int param1, int param2) {
        assertEquals(Short.valueOf((short) param1), new MutableShort((short) param2).toShort());
    }

    static public Stream<Arguments> Provider_testToShort_1to2() {
        return Stream.of(arguments(0, 0), arguments(123, 123));
    }

    @ParameterizedTest
    @MethodSource("Provider_testToString_1to2")
    public void testToString_1to2(int param1, int param2) {
        assertEquals(param1, new MutableShort((short) param2).toString());
    }

    static public Stream<Arguments> Provider_testToString_1to2() {
        return Stream.of(arguments(0, 0), arguments(10, 10));
    }
}
