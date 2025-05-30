package org.jfree.chart.axis;

import org.jfree.chart.TestUtils;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DateTickMarkPositionTest_Purified {

    @Test
    public void testEquals_1() {
        assertEquals(DateTickMarkPosition.START, DateTickMarkPosition.START);
    }

    @Test
    public void testEquals_2() {
        assertEquals(DateTickMarkPosition.MIDDLE, DateTickMarkPosition.MIDDLE);
    }

    @Test
    public void testEquals_3() {
        assertEquals(DateTickMarkPosition.END, DateTickMarkPosition.END);
    }

    @Test
    public void testEquals_4() {
        assertNotEquals(null, DateTickMarkPosition.START);
    }

    @Test
    public void testEquals_5() {
        assertNotEquals(DateTickMarkPosition.START, DateTickMarkPosition.END);
    }

    @Test
    public void testEquals_6() {
        assertNotEquals(DateTickMarkPosition.MIDDLE, DateTickMarkPosition.END);
    }
}
