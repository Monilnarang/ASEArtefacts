package org.jfree.chart.plot;

import org.jfree.chart.TestUtils;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlotOrientationTest_Purified {

    @Test
    public void testEquals_1() {
        assertEquals(PlotOrientation.HORIZONTAL, PlotOrientation.HORIZONTAL);
    }

    @Test
    public void testEquals_2() {
        assertEquals(PlotOrientation.VERTICAL, PlotOrientation.VERTICAL);
    }

    @Test
    public void testEquals_3() {
        assertNotEquals(PlotOrientation.HORIZONTAL, PlotOrientation.VERTICAL);
    }

    @Test
    public void testEquals_4() {
        assertNotEquals(PlotOrientation.VERTICAL, PlotOrientation.HORIZONTAL);
    }
}
