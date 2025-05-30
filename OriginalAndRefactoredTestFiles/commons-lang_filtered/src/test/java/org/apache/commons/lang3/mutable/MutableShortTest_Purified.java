package org.apache.commons.lang3.mutable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

public class MutableShortTest_Purified extends AbstractLangTest {

    @Test
    public void testToShort_1() {
        assertEquals(Short.valueOf((short) 0), new MutableShort((short) 0).toShort());
    }

    @Test
    public void testToShort_2() {
        assertEquals(Short.valueOf((short) 123), new MutableShort((short) 123).toShort());
    }

    @Test
    public void testToString_1() {
        assertEquals("0", new MutableShort((short) 0).toString());
    }

    @Test
    public void testToString_2() {
        assertEquals("10", new MutableShort((short) 10).toString());
    }

    @Test
    public void testToString_3() {
        assertEquals("-123", new MutableShort((short) -123).toString());
    }
}
