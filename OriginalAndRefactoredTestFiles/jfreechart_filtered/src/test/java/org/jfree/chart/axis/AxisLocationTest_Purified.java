package org.jfree.chart.axis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.jfree.chart.TestUtils;
import org.junit.jupiter.api.Test;

public class AxisLocationTest_Purified {

    @Test
    public void testEquals_1() {
        assertEquals(AxisLocation.TOP_OR_RIGHT, AxisLocation.TOP_OR_RIGHT);
    }

    @Test
    public void testEquals_2() {
        assertEquals(AxisLocation.BOTTOM_OR_RIGHT, AxisLocation.BOTTOM_OR_RIGHT);
    }

    @Test
    public void testEquals_3() {
        assertEquals(AxisLocation.TOP_OR_LEFT, AxisLocation.TOP_OR_LEFT);
    }

    @Test
    public void testEquals_4() {
        assertEquals(AxisLocation.BOTTOM_OR_LEFT, AxisLocation.BOTTOM_OR_LEFT);
    }
}
