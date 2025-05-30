package org.jfree.chart.axis;

import org.jfree.chart.TestUtils;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CategoryLabelWidthTypeTest_Purified {

    @Test
    public void testEquals_1() {
        assertEquals(CategoryLabelWidthType.CATEGORY, CategoryLabelWidthType.CATEGORY);
    }

    @Test
    public void testEquals_2() {
        assertEquals(CategoryLabelWidthType.RANGE, CategoryLabelWidthType.RANGE);
    }
}
