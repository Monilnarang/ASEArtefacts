package org.eclipse.collections.impl.block.function;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class MinAndMaxBlocksTest_Parameterized {

    private static final Double FORTY_TWO_DOUBLE = 42.0;

    private static final Integer FORTY_TWO_INTEGER = 42;

    private static final Long FORTY_TWO_LONG = 42L;

    @Test
    public void minBlocks_3() {
        assertEquals(new Double(-1.0), MinFunction.DOUBLE.value(1.0, -1.0));
    }

    @Test
    public void minBlocks_6() {
        assertEquals(Integer.valueOf(-1), MinFunction.INTEGER.value(1, -1));
    }

    @Test
    public void minBlocks_9() {
        assertEquals(Long.valueOf(-1L), MinFunction.LONG.value(1L, -1L));
    }

    @Test
    public void minBlocksNull_1() {
        assertSame(FORTY_TWO_DOUBLE, MinFunction.DOUBLE.value(null, FORTY_TWO_DOUBLE));
    }

    @Test
    public void minBlocksNull_2() {
        assertSame(FORTY_TWO_DOUBLE, MinFunction.DOUBLE.value(FORTY_TWO_DOUBLE, null));
    }

    @Test
    public void minBlocksNull_3() {
        assertSame(null, MinFunction.DOUBLE.value(null, null));
    }

    @Test
    public void minBlocksNull_4() {
        assertSame(FORTY_TWO_INTEGER, MinFunction.INTEGER.value(null, FORTY_TWO_INTEGER));
    }

    @Test
    public void minBlocksNull_5() {
        assertSame(FORTY_TWO_INTEGER, MinFunction.INTEGER.value(FORTY_TWO_INTEGER, null));
    }

    @Test
    public void minBlocksNull_6() {
        assertSame(null, MinFunction.INTEGER.value(null, null));
    }

    @Test
    public void minBlocksNull_7() {
        assertSame(FORTY_TWO_LONG, MinFunction.LONG.value(null, FORTY_TWO_LONG));
    }

    @Test
    public void minBlocksNull_8() {
        assertSame(FORTY_TWO_LONG, MinFunction.LONG.value(FORTY_TWO_LONG, null));
    }

    @Test
    public void minBlocksNull_9() {
        assertSame(null, MinFunction.LONG.value(null, null));
    }

    @Test
    public void maxBlocks_3() {
        assertEquals(new Double(1.0), MaxFunction.DOUBLE.value(1.0, -1.0));
    }

    @Test
    public void maxBlocks_6() {
        assertEquals(Integer.valueOf(1), MaxFunction.INTEGER.value(1, -1));
    }

    @Test
    public void maxBlocks_9() {
        assertEquals(Long.valueOf(1L), MaxFunction.LONG.value(1L, -1L));
    }

    @Test
    public void maxBlocksNull_1() {
        assertSame(FORTY_TWO_DOUBLE, MaxFunction.DOUBLE.value(null, FORTY_TWO_DOUBLE));
    }

    @Test
    public void maxBlocksNull_2() {
        assertSame(FORTY_TWO_DOUBLE, MaxFunction.DOUBLE.value(FORTY_TWO_DOUBLE, null));
    }

    @Test
    public void maxBlocksNull_3() {
        assertSame(null, MaxFunction.DOUBLE.value(null, null));
    }

    @Test
    public void maxBlocksNull_4() {
        assertSame(FORTY_TWO_INTEGER, MaxFunction.INTEGER.value(null, FORTY_TWO_INTEGER));
    }

    @Test
    public void maxBlocksNull_5() {
        assertSame(FORTY_TWO_INTEGER, MaxFunction.INTEGER.value(FORTY_TWO_INTEGER, null));
    }

    @Test
    public void maxBlocksNull_6() {
        assertSame(null, MaxFunction.INTEGER.value(null, null));
    }

    @Test
    public void maxBlocksNull_7() {
        assertSame(FORTY_TWO_LONG, MaxFunction.LONG.value(null, FORTY_TWO_LONG));
    }

    @Test
    public void maxBlocksNull_8() {
        assertSame(FORTY_TWO_LONG, MaxFunction.LONG.value(FORTY_TWO_LONG, null));
    }

    @Test
    public void maxBlocksNull_9() {
        assertSame(null, MaxFunction.LONG.value(null, null));
    }

    @ParameterizedTest
    @MethodSource("Provider_minBlocks_1to2")
    public void minBlocks_1to2(double param1, double param2, double param3) {
        assertEquals(new Double(param1), MinFunction.DOUBLE.value(param2, param3));
    }

    static public Stream<Arguments> Provider_minBlocks_1to2() {
        return Stream.of(arguments(1.0, 1.0, 2.0), arguments(0.0, 0.0, 1.0));
    }

    @ParameterizedTest
    @MethodSource("Provider_minBlocks_4to5")
    public void minBlocks_4to5(int param1, int param2, int param3) {
        assertEquals(Integer.valueOf(param1), MinFunction.INTEGER.value(param2, param3));
    }

    static public Stream<Arguments> Provider_minBlocks_4to5() {
        return Stream.of(arguments(1, 1, 2), arguments(0, 0, 1));
    }

    @ParameterizedTest
    @MethodSource("Provider_minBlocks_7to8")
    public void minBlocks_7to8(long param1, long param2, long param3) {
        assertEquals(Long.valueOf(param1), MinFunction.LONG.value(param2, param3));
    }

    static public Stream<Arguments> Provider_minBlocks_7to8() {
        return Stream.of(arguments(1L, 1L, 2L), arguments(0L, 0L, 1L));
    }

    @ParameterizedTest
    @MethodSource("Provider_maxBlocks_1to2")
    public void maxBlocks_1to2(double param1, double param2, double param3) {
        assertEquals(new Double(param1), MaxFunction.DOUBLE.value(param2, param3));
    }

    static public Stream<Arguments> Provider_maxBlocks_1to2() {
        return Stream.of(arguments(2.0, 1.0, 2.0), arguments(1.0, 0.0, 1.0));
    }

    @ParameterizedTest
    @MethodSource("Provider_maxBlocks_4to5")
    public void maxBlocks_4to5(int param1, int param2, int param3) {
        assertEquals(Integer.valueOf(param1), MaxFunction.INTEGER.value(param2, param3));
    }

    static public Stream<Arguments> Provider_maxBlocks_4to5() {
        return Stream.of(arguments(2, 1, 2), arguments(1, 0, 1));
    }

    @ParameterizedTest
    @MethodSource("Provider_maxBlocks_7to8")
    public void maxBlocks_7to8(long param1, long param2, long param3) {
        assertEquals(Long.valueOf(param1), MaxFunction.LONG.value(param2, param3));
    }

    static public Stream<Arguments> Provider_maxBlocks_7to8() {
        return Stream.of(arguments(2L, 1L, 2L), arguments(1L, 0L, 1L));
    }
}
