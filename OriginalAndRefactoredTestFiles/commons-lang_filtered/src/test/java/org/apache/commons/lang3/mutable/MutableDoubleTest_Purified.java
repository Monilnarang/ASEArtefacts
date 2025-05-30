package org.apache.commons.lang3.mutable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

public class MutableDoubleTest_Purified extends AbstractLangTest {

    @Test
    public void testConstructors_1() {
        assertEquals(0d, new MutableDouble().doubleValue(), 0.0001d);
    }

    @Test
    public void testConstructors_2() {
        assertEquals(1d, new MutableDouble(1d).doubleValue(), 0.0001d);
    }

    @Test
    public void testConstructors_3() {
        assertEquals(2d, new MutableDouble(Double.valueOf(2d)).doubleValue(), 0.0001d);
    }

    @Test
    public void testConstructors_4() {
        assertEquals(3d, new MutableDouble(new MutableDouble(3d)).doubleValue(), 0.0001d);
    }

    @Test
    public void testConstructors_5() {
        assertEquals(2d, new MutableDouble("2.0").doubleValue(), 0.0001d);
    }

    @Test
    public void testGetSet_1() {
        assertEquals(0d, new MutableDouble().doubleValue(), 0.0001d);
    }

    @Test
    public void testGetSet_2() {
        assertEquals(Double.valueOf(0), new MutableDouble().getValue());
    }

    @Test
    public void testGetSet_3_testMerged_3() {
        final MutableDouble mutNum = new MutableDouble(0d);
        mutNum.setValue(1);
        assertEquals(1d, mutNum.doubleValue(), 0.0001d);
        assertEquals(Double.valueOf(1d), mutNum.getValue());
        mutNum.setValue(Double.valueOf(2d));
        assertEquals(2d, mutNum.doubleValue(), 0.0001d);
        assertEquals(Double.valueOf(2d), mutNum.getValue());
        mutNum.setValue(new MutableDouble(3d));
        assertEquals(3d, mutNum.doubleValue(), 0.0001d);
        assertEquals(Double.valueOf(3d), mutNum.getValue());
    }

    @Test
    public void testToDouble_1() {
        assertEquals(Double.valueOf(0d), new MutableDouble(0d).toDouble());
    }

    @Test
    public void testToDouble_2() {
        assertEquals(Double.valueOf(12.3d), new MutableDouble(12.3d).toDouble());
    }

    @Test
    public void testToString_1() {
        assertEquals("0.0", new MutableDouble(0d).toString());
    }

    @Test
    public void testToString_2() {
        assertEquals("10.0", new MutableDouble(10d).toString());
    }

    @Test
    public void testToString_3() {
        assertEquals("-123.0", new MutableDouble(-123d).toString());
    }
}
