package org.jfree.data;

import org.jfree.chart.TestUtils;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RangeTypeTest_Purified {

    @Test
    public void testEquals_1() {
        assertEquals(RangeType.FULL, RangeType.FULL);
    }

    @Test
    public void testEquals_2() {
        assertEquals(RangeType.NEGATIVE, RangeType.NEGATIVE);
    }

    @Test
    public void testEquals_3() {
        assertEquals(RangeType.POSITIVE, RangeType.POSITIVE);
    }

    @Test
    public void testEquals_4() {
        assertNotEquals(RangeType.FULL, RangeType.NEGATIVE);
    }

    @Test
    public void testEquals_5() {
        assertNotEquals(RangeType.FULL, RangeType.POSITIVE);
    }

    @Test
    public void testEquals_6() {
        assertNotEquals(null, RangeType.FULL);
    }

    @Test
    public void testEquals_7() {
        assertNotEquals(RangeType.NEGATIVE, RangeType.FULL);
    }

    @Test
    public void testEquals_8() {
        assertNotEquals(RangeType.NEGATIVE, RangeType.POSITIVE);
    }

    @Test
    public void testEquals_9() {
        assertNotEquals(null, RangeType.NEGATIVE);
    }

    @Test
    public void testEquals_10() {
        assertNotEquals(RangeType.POSITIVE, RangeType.NEGATIVE);
    }

    @Test
    public void testEquals_11() {
        assertNotEquals(RangeType.POSITIVE, RangeType.FULL);
    }

    @Test
    public void testEquals_12() {
        assertNotEquals(null, RangeType.POSITIVE);
    }
}
