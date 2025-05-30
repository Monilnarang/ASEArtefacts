package org.eclipse.collections.impl.block.function;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class SubtractFunctionTest_Parameterized {

    @Test
    public void subtractIntegerFunction_3() {
        assertEquals(Integer.valueOf(-1), SubtractFunction.INTEGER.value(1, 2));
    }

    @Test
    public void subtractDoubleFunction_3() {
        assertEquals(Double.valueOf(-0.5), SubtractFunction.DOUBLE.value(1.5, 2.0));
    }

    @Test
    public void subtractLongFunction_3() {
        assertEquals(Long.valueOf(-1L), SubtractFunction.LONG.value(1L, 2L));
    }

    @ParameterizedTest
    @MethodSource("Provider_subtractIntegerFunction_1to2")
    public void subtractIntegerFunction_1to2(int param1, int param2, int param3) {
        assertEquals(Integer.valueOf(param1), SubtractFunction.INTEGER.value(param2, param3));
    }

    static public Stream<Arguments> Provider_subtractIntegerFunction_1to2() {
        return Stream.of(arguments(1, 2, 1), arguments(0, 1, 1));
    }

    @ParameterizedTest
    @MethodSource("Provider_subtractDoubleFunction_1to2")
    public void subtractDoubleFunction_1to2(double param1, double param2, double param3) {
        assertEquals(Double.valueOf(param1), SubtractFunction.DOUBLE.value(param2, param3));
    }

    static public Stream<Arguments> Provider_subtractDoubleFunction_1to2() {
        return Stream.of(arguments(0.5, 2.0, 1.5), arguments(0, 2.0, 2.0));
    }

    @ParameterizedTest
    @MethodSource("Provider_subtractLongFunction_1to2")
    public void subtractLongFunction_1to2(long param1, long param2, long param3) {
        assertEquals(Long.valueOf(param1), SubtractFunction.LONG.value(param2, param3));
    }

    static public Stream<Arguments> Provider_subtractLongFunction_1to2() {
        return Stream.of(arguments(1L, 2L, 1L), arguments(0L, 1L, 1L));
    }
}
