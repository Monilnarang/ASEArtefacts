package org.jfree.data;

import org.jfree.chart.TestUtils;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RangeTest_Purified {

    private static final double EPSILON = 0.0000000001;

    @Test
    public void testCombine_1() {
        assertNull(Range.combine(null, null));
    }

    @Test
    public void testCombine_2_testMerged_2() {
        Range r1 = new Range(1.0, 2.0);
        Range r2 = new Range(1.5, 2.5);
        assertEquals(r1, Range.combine(r1, null));
        assertEquals(r2, Range.combine(null, r2));
        assertEquals(new Range(1.0, 2.5), Range.combine(r1, r2));
        Range r3 = new Range(Double.NaN, 1.3);
        Range rr = Range.combine(r1, r3);
        assertTrue(Double.isNaN(rr.getLowerBound()));
        assertEquals(2.0, rr.getUpperBound(), EPSILON);
        Range r4 = new Range(1.7, Double.NaN);
        rr = Range.combine(r4, r1);
        assertEquals(1.0, rr.getLowerBound(), EPSILON);
        assertTrue(Double.isNaN(rr.getUpperBound()));
    }

    @Test
    public void testCombineIgnoringNaN_1() {
        assertNull(Range.combineIgnoringNaN(null, null));
    }

    @Test
    public void testCombineIgnoringNaN_2_testMerged_2() {
        Range r1 = new Range(1.0, 2.0);
        Range r2 = new Range(1.5, 2.5);
        assertEquals(r1, Range.combineIgnoringNaN(r1, null));
        assertEquals(r2, Range.combineIgnoringNaN(null, r2));
        assertEquals(new Range(1.0, 2.5), Range.combineIgnoringNaN(r1, r2));
        Range r3 = new Range(Double.NaN, 1.3);
        Range rr = Range.combineIgnoringNaN(r1, r3);
        assertEquals(1.0, rr.getLowerBound(), EPSILON);
        assertEquals(2.0, rr.getUpperBound(), EPSILON);
    }

    @Test
    public void testIsNaNRange_1() {
        assertTrue(new Range(Double.NaN, Double.NaN).isNaNRange());
    }

    @Test
    public void testIsNaNRange_2() {
        assertFalse(new Range(1.0, 2.0).isNaNRange());
    }

    @Test
    public void testIsNaNRange_3() {
        assertFalse(new Range(Double.NaN, 2.0).isNaNRange());
    }

    @Test
    public void testIsNaNRange_4() {
        assertFalse(new Range(1.0, Double.NaN).isNaNRange());
    }
}
