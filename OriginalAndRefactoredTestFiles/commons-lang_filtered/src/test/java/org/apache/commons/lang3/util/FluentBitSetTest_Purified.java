package org.apache.commons.lang3.util;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import java.util.BitSet;
import org.apache.commons.lang3.AbstractLangTest;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FluentBitSetTest_Purified extends AbstractLangTest {

    private BitSet eightBs;

    private FluentBitSet eightFbs;

    @BeforeEach
    public void beforeEach() {
        eightFbs = newInstance();
        for (int i = 0; i < 8; i++) {
            eightFbs.set(i);
        }
        eightBs = eightFbs.bitSet();
    }

    private FluentBitSet newInstance() {
        return new FluentBitSet();
    }

    private FluentBitSet newInstance(final int nbits) {
        return new FluentBitSet(nbits);
    }

    @Test
    public void test_setRangeInclusive_1() {
        assertEquals(64, eightFbs.size(), "Returned incorrect size");
    }

    @Test
    public void test_setRangeInclusive_2() {
        eightFbs.set(129);
        assertTrue(eightFbs.size() >= 129, "Returned incorrect size");
    }

    @Test
    public void test_size_1() {
        assertEquals(64, eightFbs.size(), "Returned incorrect size");
    }

    @Test
    public void test_size_2() {
        eightFbs.set(129);
        assertTrue(eightFbs.size() >= 129, "Returned incorrect size");
    }

    @Test
    public void test_toString_1() {
        assertEquals("{0, 1, 2, 3, 4, 5, 6, 7}", eightFbs.toString(), "Returned incorrect string representation");
    }

    @Test
    public void test_toString_2() {
        eightFbs.clear(2);
        assertEquals("{0, 1, 3, 4, 5, 6, 7}", eightFbs.toString(), "Returned incorrect string representation");
    }
}
