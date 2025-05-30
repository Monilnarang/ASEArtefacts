package org.graylog.plugins.pipelineprocessor.functions.strings;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class ShannonEntropyTest_Parameterized {

    @ParameterizedTest
    @MethodSource("Provider_testEntropyCalcForChars_1_4to7")
    public void testEntropyCalcForChars_1_4to7(double param1, int param2) {
        assertEquals(param1, ShannonEntropy.calculateForChars(param2));
    }

    static public Stream<Arguments> Provider_testEntropyCalcForChars_1_4to7() {
        return Stream.of(arguments(0D, 1111), arguments(0.46899559358928133D, 1555555555), arguments(1.0D, 1111155555), arguments(3.3219280948873635D, 1234567890), arguments(5.1699250014423095D, "1234567890qwertyuiopasdfghjklzxcvbnm"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testEntropyCalcForChars_2to3")
    public void testEntropyCalcForChars_2to3(double param1, double param2, double param3) {
        assertEquals(param1, ShannonEntropy.calculateForChars(param3), param2);
    }

    static public Stream<Arguments> Provider_testEntropyCalcForChars_2to3() {
        return Stream.of(arguments(0D, 0.0D, 5555555555), arguments(0D, 0.0D, 5555555555));
    }
}
