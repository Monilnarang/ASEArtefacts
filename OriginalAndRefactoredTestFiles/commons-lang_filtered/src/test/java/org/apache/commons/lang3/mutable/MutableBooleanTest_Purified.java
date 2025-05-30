package org.apache.commons.lang3.mutable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

public class MutableBooleanTest_Purified extends AbstractLangTest {

    @Test
    public void testConstructors_1() {
        assertFalse(new MutableBoolean().booleanValue());
    }

    @Test
    public void testConstructors_2() {
        assertTrue(new MutableBoolean(true).booleanValue());
    }

    @Test
    public void testConstructors_3() {
        assertFalse(new MutableBoolean(false).booleanValue());
    }

    @Test
    public void testConstructors_4() {
        assertTrue(new MutableBoolean(Boolean.TRUE).booleanValue());
    }

    @Test
    public void testConstructors_5() {
        assertFalse(new MutableBoolean(Boolean.FALSE).booleanValue());
    }

    @Test
    public void testGetSet_1() {
        assertFalse(new MutableBoolean().booleanValue());
    }

    @Test
    public void testGetSet_2() {
        assertEquals(Boolean.FALSE, new MutableBoolean().getValue());
    }

    @Test
    public void testGetSet_3_testMerged_3() {
        final MutableBoolean mutBool = new MutableBoolean(false);
        assertEquals(Boolean.FALSE, mutBool.toBoolean());
        assertFalse(mutBool.booleanValue());
        assertTrue(mutBool.isFalse());
        assertFalse(mutBool.isTrue());
        mutBool.setValue(Boolean.TRUE);
        assertEquals(Boolean.TRUE, mutBool.toBoolean());
        assertTrue(mutBool.booleanValue());
        assertFalse(mutBool.isFalse());
        assertTrue(mutBool.isTrue());
    }

    @Test
    public void testToString_1() {
        assertEquals(Boolean.FALSE.toString(), new MutableBoolean(false).toString());
    }

    @Test
    public void testToString_2() {
        assertEquals(Boolean.TRUE.toString(), new MutableBoolean(true).toString());
    }
}
