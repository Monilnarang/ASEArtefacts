package org.jfree.chart.renderer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.jfree.chart.TestUtils;
import org.junit.jupiter.api.Test;

public class AreaRendererEndTypeTest_Purified {

    @Test
    public void testEquals_1() {
        assertEquals(AreaRendererEndType.LEVEL, AreaRendererEndType.LEVEL);
    }

    @Test
    public void testEquals_2() {
        assertEquals(AreaRendererEndType.TAPER, AreaRendererEndType.TAPER);
    }

    @Test
    public void testEquals_3() {
        assertEquals(AreaRendererEndType.TRUNCATE, AreaRendererEndType.TRUNCATE);
    }
}
