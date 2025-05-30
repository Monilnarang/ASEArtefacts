package org.apache.commons.lang3.mutable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

public class MutableIntTest_Purified extends AbstractLangTest {

    @Test
    public void testConstructors_1() {
        assertEquals(0, new MutableInt().intValue());
    }

    @Test
    public void testConstructors_2() {
        assertEquals(1, new MutableInt(1).intValue());
    }

    @Test
    public void testConstructors_3() {
        assertEquals(2, new MutableInt(Integer.valueOf(2)).intValue());
    }

    @Test
    public void testConstructors_4() {
        assertEquals(3, new MutableInt(new MutableLong(3)).intValue());
    }

    @Test
    public void testConstructors_5() {
        assertEquals(2, new MutableInt("2").intValue());
    }

    @Test
    public void testGetSet_1() {
        assertEquals(0, new MutableInt().intValue());
    }

    @Test
    public void testGetSet_2() {
        assertEquals(Integer.valueOf(0), new MutableInt().getValue());
    }

    @Test
    public void testGetSet_3_testMerged_3() {
        final MutableInt mutNum = new MutableInt(0);
        mutNum.setValue(1);
        assertEquals(1, mutNum.intValue());
        assertEquals(Integer.valueOf(1), mutNum.getValue());
        mutNum.setValue(Integer.valueOf(2));
        assertEquals(2, mutNum.intValue());
        assertEquals(Integer.valueOf(2), mutNum.getValue());
        mutNum.setValue(new MutableLong(3));
        assertEquals(3, mutNum.intValue());
        assertEquals(Integer.valueOf(3), mutNum.getValue());
    }

    @Test
    public void testToInteger_1() {
        assertEquals(Integer.valueOf(0), new MutableInt(0).toInteger());
    }

    @Test
    public void testToInteger_2() {
        assertEquals(Integer.valueOf(123), new MutableInt(123).toInteger());
    }

    @Test
    public void testToString_1() {
        assertEquals("0", new MutableInt(0).toString());
    }

    @Test
    public void testToString_2() {
        assertEquals("10", new MutableInt(10).toString());
    }

    @Test
    public void testToString_3() {
        assertEquals("-123", new MutableInt(-123).toString());
    }
}
