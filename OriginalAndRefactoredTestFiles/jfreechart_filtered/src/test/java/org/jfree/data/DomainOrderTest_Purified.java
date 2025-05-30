package org.jfree.data;

import org.jfree.chart.TestUtils;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DomainOrderTest_Purified {

    @Test
    public void testEquals_1() {
        assertEquals(DomainOrder.NONE, DomainOrder.NONE);
    }

    @Test
    public void testEquals_2() {
        assertEquals(DomainOrder.ASCENDING, DomainOrder.ASCENDING);
    }

    @Test
    public void testEquals_3() {
        assertEquals(DomainOrder.DESCENDING, DomainOrder.DESCENDING);
    }

    @Test
    public void testEquals_4() {
        assertNotEquals(DomainOrder.NONE, DomainOrder.ASCENDING);
    }

    @Test
    public void testEquals_5() {
        assertNotEquals(DomainOrder.NONE, DomainOrder.DESCENDING);
    }

    @Test
    public void testEquals_6() {
        assertNotEquals(null, DomainOrder.NONE);
    }

    @Test
    public void testEquals_7() {
        assertNotEquals(DomainOrder.ASCENDING, DomainOrder.NONE);
    }

    @Test
    public void testEquals_8() {
        assertNotEquals(DomainOrder.ASCENDING, DomainOrder.DESCENDING);
    }

    @Test
    public void testEquals_9() {
        assertNotEquals(null, DomainOrder.ASCENDING);
    }

    @Test
    public void testEquals_10() {
        assertNotEquals(DomainOrder.DESCENDING, DomainOrder.NONE);
    }

    @Test
    public void testEquals_11() {
        assertNotEquals(DomainOrder.DESCENDING, DomainOrder.ASCENDING);
    }

    @Test
    public void testEquals_12() {
        assertNotEquals(null, DomainOrder.DESCENDING);
    }
}
