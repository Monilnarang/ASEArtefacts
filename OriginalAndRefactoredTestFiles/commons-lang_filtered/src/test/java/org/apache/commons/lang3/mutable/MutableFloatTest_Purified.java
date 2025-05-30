package org.apache.commons.lang3.mutable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

public class MutableFloatTest_Purified extends AbstractLangTest {

    @Test
    public void testConstructors_1() {
        assertEquals(0f, new MutableFloat().floatValue(), 0.0001f);
    }

    @Test
    public void testConstructors_2() {
        assertEquals(1f, new MutableFloat(1f).floatValue(), 0.0001f);
    }

    @Test
    public void testConstructors_3() {
        assertEquals(2f, new MutableFloat(Float.valueOf(2f)).floatValue(), 0.0001f);
    }

    @Test
    public void testConstructors_4() {
        assertEquals(3f, new MutableFloat(new MutableFloat(3f)).floatValue(), 0.0001f);
    }

    @Test
    public void testConstructors_5() {
        assertEquals(2f, new MutableFloat("2.0").floatValue(), 0.0001f);
    }

    @Test
    public void testGetSet_1() {
        assertEquals(0f, new MutableFloat().floatValue(), 0.0001f);
    }

    @Test
    public void testGetSet_2() {
        assertEquals(Float.valueOf(0), new MutableFloat().getValue());
    }

    @Test
    public void testGetSet_3_testMerged_3() {
        final MutableFloat mutNum = new MutableFloat(0f);
        mutNum.setValue(1);
        assertEquals(1f, mutNum.floatValue(), 0.0001f);
        assertEquals(Float.valueOf(1f), mutNum.getValue());
        mutNum.setValue(Float.valueOf(2f));
        assertEquals(2f, mutNum.floatValue(), 0.0001f);
        assertEquals(Float.valueOf(2f), mutNum.getValue());
        mutNum.setValue(new MutableFloat(3f));
        assertEquals(3f, mutNum.floatValue(), 0.0001f);
        assertEquals(Float.valueOf(3f), mutNum.getValue());
    }

    @Test
    public void testToFloat_1() {
        assertEquals(Float.valueOf(0f), new MutableFloat(0f).toFloat());
    }

    @Test
    public void testToFloat_2() {
        assertEquals(Float.valueOf(12.3f), new MutableFloat(12.3f).toFloat());
    }

    @Test
    public void testToString_1() {
        assertEquals("0.0", new MutableFloat(0f).toString());
    }

    @Test
    public void testToString_2() {
        assertEquals("10.0", new MutableFloat(10f).toString());
    }

    @Test
    public void testToString_3() {
        assertEquals("-123.0", new MutableFloat(-123f).toString());
    }
}
