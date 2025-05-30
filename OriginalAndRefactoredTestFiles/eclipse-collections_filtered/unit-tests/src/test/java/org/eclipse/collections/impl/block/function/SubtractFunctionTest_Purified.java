package org.eclipse.collections.impl.block.function;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SubtractFunctionTest_Purified {

    @Test
    public void subtractIntegerFunction_1() {
        assertEquals(Integer.valueOf(1), SubtractFunction.INTEGER.value(2, 1));
    }

    @Test
    public void subtractIntegerFunction_2() {
        assertEquals(Integer.valueOf(0), SubtractFunction.INTEGER.value(1, 1));
    }

    @Test
    public void subtractIntegerFunction_3() {
        assertEquals(Integer.valueOf(-1), SubtractFunction.INTEGER.value(1, 2));
    }

    @Test
    public void subtractDoubleFunction_1() {
        assertEquals(Double.valueOf(0.5), SubtractFunction.DOUBLE.value(2.0, 1.5));
    }

    @Test
    public void subtractDoubleFunction_2() {
        assertEquals(Double.valueOf(0), SubtractFunction.DOUBLE.value(2.0, 2.0));
    }

    @Test
    public void subtractDoubleFunction_3() {
        assertEquals(Double.valueOf(-0.5), SubtractFunction.DOUBLE.value(1.5, 2.0));
    }

    @Test
    public void subtractLongFunction_1() {
        assertEquals(Long.valueOf(1L), SubtractFunction.LONG.value(2L, 1L));
    }

    @Test
    public void subtractLongFunction_2() {
        assertEquals(Long.valueOf(0L), SubtractFunction.LONG.value(1L, 1L));
    }

    @Test
    public void subtractLongFunction_3() {
        assertEquals(Long.valueOf(-1L), SubtractFunction.LONG.value(1L, 2L));
    }
}
