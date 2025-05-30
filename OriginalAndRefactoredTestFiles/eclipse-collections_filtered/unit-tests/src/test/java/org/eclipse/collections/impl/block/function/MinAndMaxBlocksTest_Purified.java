package org.eclipse.collections.impl.block.function;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class MinAndMaxBlocksTest_Purified {

    private static final Double FORTY_TWO_DOUBLE = 42.0;

    private static final Integer FORTY_TWO_INTEGER = 42;

    private static final Long FORTY_TWO_LONG = 42L;

    @Test
    public void minBlocks_1() {
        assertEquals(new Double(1.0), MinFunction.DOUBLE.value(1.0, 2.0));
    }

    @Test
    public void minBlocks_2() {
        assertEquals(new Double(0.0), MinFunction.DOUBLE.value(0.0, 1.0));
    }

    @Test
    public void minBlocks_3() {
        assertEquals(new Double(-1.0), MinFunction.DOUBLE.value(1.0, -1.0));
    }

    @Test
    public void minBlocks_4() {
        assertEquals(Integer.valueOf(1), MinFunction.INTEGER.value(1, 2));
    }

    @Test
    public void minBlocks_5() {
        assertEquals(Integer.valueOf(0), MinFunction.INTEGER.value(0, 1));
    }

    @Test
    public void minBlocks_6() {
        assertEquals(Integer.valueOf(-1), MinFunction.INTEGER.value(1, -1));
    }

    @Test
    public void minBlocks_7() {
        assertEquals(Long.valueOf(1L), MinFunction.LONG.value(1L, 2L));
    }

    @Test
    public void minBlocks_8() {
        assertEquals(Long.valueOf(0L), MinFunction.LONG.value(0L, 1L));
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
    public void maxBlocks_1() {
        assertEquals(new Double(2.0), MaxFunction.DOUBLE.value(1.0, 2.0));
    }

    @Test
    public void maxBlocks_2() {
        assertEquals(new Double(1.0), MaxFunction.DOUBLE.value(0.0, 1.0));
    }

    @Test
    public void maxBlocks_3() {
        assertEquals(new Double(1.0), MaxFunction.DOUBLE.value(1.0, -1.0));
    }

    @Test
    public void maxBlocks_4() {
        assertEquals(Integer.valueOf(2), MaxFunction.INTEGER.value(1, 2));
    }

    @Test
    public void maxBlocks_5() {
        assertEquals(Integer.valueOf(1), MaxFunction.INTEGER.value(0, 1));
    }

    @Test
    public void maxBlocks_6() {
        assertEquals(Integer.valueOf(1), MaxFunction.INTEGER.value(1, -1));
    }

    @Test
    public void maxBlocks_7() {
        assertEquals(Long.valueOf(2L), MaxFunction.LONG.value(1L, 2L));
    }

    @Test
    public void maxBlocks_8() {
        assertEquals(Long.valueOf(1L), MaxFunction.LONG.value(0L, 1L));
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
}
