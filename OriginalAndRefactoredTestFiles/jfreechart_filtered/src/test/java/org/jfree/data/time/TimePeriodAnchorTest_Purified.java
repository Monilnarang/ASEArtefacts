package org.jfree.data.time;

import org.jfree.chart.TestUtils;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TimePeriodAnchorTest_Purified {

    @Test
    public void testEquals_1() {
        assertEquals(TimePeriodAnchor.START, TimePeriodAnchor.START);
    }

    @Test
    public void testEquals_2() {
        assertEquals(TimePeriodAnchor.MIDDLE, TimePeriodAnchor.MIDDLE);
    }

    @Test
    public void testEquals_3() {
        assertEquals(TimePeriodAnchor.END, TimePeriodAnchor.END);
    }
}
