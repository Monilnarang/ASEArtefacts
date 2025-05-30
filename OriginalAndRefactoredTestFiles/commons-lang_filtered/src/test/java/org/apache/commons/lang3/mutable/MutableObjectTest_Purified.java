package org.apache.commons.lang3.mutable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

public class MutableObjectTest_Purified extends AbstractLangTest {

    @Test
    public void testConstructors_1() {
        assertNull(new MutableObject<String>().getValue());
    }

    @Test
    public void testConstructors_2() {
        final Integer i = Integer.valueOf(6);
        assertSame(i, new MutableObject<>(i).getValue());
    }

    @Test
    public void testConstructors_3() {
        assertSame("HI", new MutableObject<>("HI").getValue());
    }

    @Test
    public void testConstructors_4() {
        assertSame(null, new MutableObject<>(null).getValue());
    }

    @Test
    public void testGetSet_1() {
        assertNull(new MutableObject<>().getValue());
    }

    @Test
    public void testGetSet_2_testMerged_2() {
        final MutableObject<String> mutNum = new MutableObject<>();
        mutNum.setValue("HELLO");
        assertSame("HELLO", mutNum.getValue());
        mutNum.setValue(null);
        assertSame(null, mutNum.getValue());
    }

    @Test
    public void testToString_1() {
        assertEquals("HI", new MutableObject<>("HI").toString());
    }

    @Test
    public void testToString_2() {
        assertEquals("10.0", new MutableObject<>(Double.valueOf(10)).toString());
    }

    @Test
    public void testToString_3() {
        assertEquals("null", new MutableObject<>(null).toString());
    }
}
