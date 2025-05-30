package org.jfree.chart.labels;

import org.jfree.chart.TestUtils;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ItemLabelAnchorTest_Purified {

    @Test
    public void testEquals_1() {
        assertEquals(ItemLabelAnchor.INSIDE1, ItemLabelAnchor.INSIDE1);
    }

    @Test
    public void testEquals_2() {
        assertNotEquals(ItemLabelAnchor.INSIDE1, ItemLabelAnchor.INSIDE2);
    }
}
