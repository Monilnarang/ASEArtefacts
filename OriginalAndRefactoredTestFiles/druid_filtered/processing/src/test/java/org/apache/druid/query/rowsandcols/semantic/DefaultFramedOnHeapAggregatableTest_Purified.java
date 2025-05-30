package org.apache.druid.query.rowsandcols.semantic;

import org.junit.Test;
import static org.apache.druid.query.rowsandcols.semantic.DefaultFramedOnHeapAggregatable.invertedOrderForLastK;
import static org.junit.Assert.assertEquals;

public class DefaultFramedOnHeapAggregatableTest_Purified {

    @Test
    public void testInvertedOrderForLastK_1() {
        assertEquals(0, invertedOrderForLastK(0, 3, 1));
    }

    @Test
    public void testInvertedOrderForLastK_2() {
        assertEquals(1, invertedOrderForLastK(1, 3, 1));
    }

    @Test
    public void testInvertedOrderForLastK_3() {
        assertEquals(2, invertedOrderForLastK(2, 3, 1));
    }

    @Test
    public void testInvertedOrderForLastK2_1() {
        assertEquals(0, invertedOrderForLastK(0, 3, 2));
    }

    @Test
    public void testInvertedOrderForLastK2_2() {
        assertEquals(2, invertedOrderForLastK(1, 3, 2));
    }

    @Test
    public void testInvertedOrderForLastK2_3() {
        assertEquals(1, invertedOrderForLastK(2, 3, 2));
    }

    @Test
    public void testInvertedOrderForLastK3_1() {
        assertEquals(2, invertedOrderForLastK(0, 3, 3));
    }

    @Test
    public void testInvertedOrderForLastK3_2() {
        assertEquals(1, invertedOrderForLastK(1, 3, 3));
    }

    @Test
    public void testInvertedOrderForLastK3_3() {
        assertEquals(0, invertedOrderForLastK(2, 3, 3));
    }
}
