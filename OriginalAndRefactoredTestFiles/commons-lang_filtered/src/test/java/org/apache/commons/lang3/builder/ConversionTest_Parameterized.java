package org.apache.commons.lang3.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.apache.commons.lang3.Conversion;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class ConversionTest_Parameterized {

    @ParameterizedTest
    @MethodSource("Provider_testHexToByte_1to2")
    void testHexToByte_1to2(int param1, int param2, int param3, int param4, int param5, int param6) {
        assertEquals((byte) param1, Conversion.hexToByte(param2, param3, (byte) param6, param4, param5));
    }

    static public Stream<Arguments> Provider_testHexToByte_1to2() {
        return Stream.of(arguments(0, 00, 0, 0, 0, 0), arguments(0, 00, 0, 0, 2, 0));
    }
}
