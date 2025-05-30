package org.apache.commons.lang3.mutable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

public class MutableLongTest_Purified extends AbstractLangTest {

    @Test
    public void testConstructors_1() {
        assertEquals(0, new MutableLong().longValue());
    }

    @Test
    public void testConstructors_2() {
        assertEquals(1, new MutableLong(1).longValue());
    }

    @Test
    public void testConstructors_3() {
        assertEquals(2, new MutableLong(Long.valueOf(2)).longValue());
    }

    @Test
    public void testConstructors_4() {
        assertEquals(3, new MutableLong(new MutableLong(3)).longValue());
    }

    @Test
    public void testConstructors_5() {
        assertEquals(2, new MutableLong("2").longValue());
    }

    @Test
    public void testGetSet_1() {
        assertEquals(0, new MutableLong().longValue());
    }

    @Test
    public void testGetSet_2() {
        assertEquals(Long.valueOf(0), new MutableLong().getValue());
    }

    @Test
    public void testGetSet_3_testMerged_3() {
        final MutableLong mutNum = new MutableLong(0);
        mutNum.setValue(1);
        assertEquals(1, mutNum.longValue());
        assertEquals(Long.valueOf(1), mutNum.getValue());
        mutNum.setValue(Long.valueOf(2));
        assertEquals(2, mutNum.longValue());
        assertEquals(Long.valueOf(2), mutNum.getValue());
        mutNum.setValue(new MutableLong(3));
        assertEquals(3, mutNum.longValue());
        assertEquals(Long.valueOf(3), mutNum.getValue());
    }

    @Test
    public void testToLong_1() {
        assertEquals(Long.valueOf(0L), new MutableLong(0L).toLong());
    }

    @Test
    public void testToLong_2() {
        assertEquals(Long.valueOf(123L), new MutableLong(123L).toLong());
    }

    @Test
    public void testToString_1() {
        assertEquals("0", new MutableLong(0).toString());
    }

    @Test
    public void testToString_2() {
        assertEquals("10", new MutableLong(10).toString());
    }

    @Test
    public void testToString_3() {
        assertEquals("-123", new MutableLong(-123).toString());
    }
}
