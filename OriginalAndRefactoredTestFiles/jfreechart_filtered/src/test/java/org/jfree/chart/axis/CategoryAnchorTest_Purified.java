package org.jfree.chart.axis;

import org.jfree.chart.TestUtils;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CategoryAnchorTest_Purified {

    @Test
    public void testEquals_1() {
        assertEquals(CategoryAnchor.START, CategoryAnchor.START);
    }

    @Test
    public void testEquals_2() {
        assertEquals(CategoryAnchor.MIDDLE, CategoryAnchor.MIDDLE);
    }

    @Test
    public void testEquals_3() {
        assertEquals(CategoryAnchor.END, CategoryAnchor.END);
    }

    @Test
    public void testEquals_4() {
        assertNotEquals(CategoryAnchor.START, CategoryAnchor.END);
    }

    @Test
    public void testEquals_5() {
        assertNotEquals(CategoryAnchor.MIDDLE, CategoryAnchor.END);
    }
}
