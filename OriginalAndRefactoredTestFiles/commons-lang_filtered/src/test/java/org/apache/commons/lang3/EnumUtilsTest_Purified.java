package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.ToIntFunction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EnumUtilsTest_Purified extends AbstractLangTest {

    private void assertArrayEquals(final long[] actual, final long... expected) {
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void testGenerateBitVector_1() {
        assertEquals(0L, EnumUtils.generateBitVector(Traffic.class, EnumSet.noneOf(Traffic.class)));
    }

    @Test
    public void testGenerateBitVector_2() {
        assertEquals(1L, EnumUtils.generateBitVector(Traffic.class, EnumSet.of(Traffic.RED)));
    }

    @Test
    public void testGenerateBitVector_3() {
        assertEquals(2L, EnumUtils.generateBitVector(Traffic.class, EnumSet.of(Traffic.AMBER)));
    }

    @Test
    public void testGenerateBitVector_4() {
        assertEquals(4L, EnumUtils.generateBitVector(Traffic.class, EnumSet.of(Traffic.GREEN)));
    }

    @Test
    public void testGenerateBitVector_5() {
        assertEquals(3L, EnumUtils.generateBitVector(Traffic.class, EnumSet.of(Traffic.RED, Traffic.AMBER)));
    }

    @Test
    public void testGenerateBitVector_6() {
        assertEquals(5L, EnumUtils.generateBitVector(Traffic.class, EnumSet.of(Traffic.RED, Traffic.GREEN)));
    }

    @Test
    public void testGenerateBitVector_7() {
        assertEquals(6L, EnumUtils.generateBitVector(Traffic.class, EnumSet.of(Traffic.AMBER, Traffic.GREEN)));
    }

    @Test
    public void testGenerateBitVector_8() {
        assertEquals(7L, EnumUtils.generateBitVector(Traffic.class, EnumSet.of(Traffic.RED, Traffic.AMBER, Traffic.GREEN)));
    }

    @Test
    public void testGenerateBitVector_9() {
        assertEquals(1L << 31, EnumUtils.generateBitVector(Enum64.class, EnumSet.of(Enum64.A31)));
    }

    @Test
    public void testGenerateBitVector_10() {
        assertEquals(1L << 32, EnumUtils.generateBitVector(Enum64.class, EnumSet.of(Enum64.A32)));
    }

    @Test
    public void testGenerateBitVector_11() {
        assertEquals(1L << 63, EnumUtils.generateBitVector(Enum64.class, EnumSet.of(Enum64.A63)));
    }

    @Test
    public void testGenerateBitVector_12() {
        assertEquals(Long.MIN_VALUE, EnumUtils.generateBitVector(Enum64.class, EnumSet.of(Enum64.A63)));
    }

    @Test
    public void testGenerateBitVectorFromArray_1() {
        assertEquals(0L, EnumUtils.generateBitVector(Traffic.class));
    }

    @Test
    public void testGenerateBitVectorFromArray_2() {
        assertEquals(1L, EnumUtils.generateBitVector(Traffic.class, Traffic.RED));
    }

    @Test
    public void testGenerateBitVectorFromArray_3() {
        assertEquals(2L, EnumUtils.generateBitVector(Traffic.class, Traffic.AMBER));
    }

    @Test
    public void testGenerateBitVectorFromArray_4() {
        assertEquals(4L, EnumUtils.generateBitVector(Traffic.class, Traffic.GREEN));
    }

    @Test
    public void testGenerateBitVectorFromArray_5() {
        assertEquals(3L, EnumUtils.generateBitVector(Traffic.class, Traffic.RED, Traffic.AMBER));
    }

    @Test
    public void testGenerateBitVectorFromArray_6() {
        assertEquals(5L, EnumUtils.generateBitVector(Traffic.class, Traffic.RED, Traffic.GREEN));
    }

    @Test
    public void testGenerateBitVectorFromArray_7() {
        assertEquals(6L, EnumUtils.generateBitVector(Traffic.class, Traffic.AMBER, Traffic.GREEN));
    }

    @Test
    public void testGenerateBitVectorFromArray_8() {
        assertEquals(7L, EnumUtils.generateBitVector(Traffic.class, Traffic.RED, Traffic.AMBER, Traffic.GREEN));
    }

    @Test
    public void testGenerateBitVectorFromArray_9() {
        assertEquals(7L, EnumUtils.generateBitVector(Traffic.class, Traffic.RED, Traffic.AMBER, Traffic.GREEN, Traffic.GREEN));
    }

    @Test
    public void testGenerateBitVectorFromArray_10() {
        assertEquals(1L << 31, EnumUtils.generateBitVector(Enum64.class, Enum64.A31));
    }

    @Test
    public void testGenerateBitVectorFromArray_11() {
        assertEquals(1L << 32, EnumUtils.generateBitVector(Enum64.class, Enum64.A32));
    }

    @Test
    public void testGenerateBitVectorFromArray_12() {
        assertEquals(1L << 63, EnumUtils.generateBitVector(Enum64.class, Enum64.A63));
    }

    @Test
    public void testGenerateBitVectorFromArray_13() {
        assertEquals(Long.MIN_VALUE, EnumUtils.generateBitVector(Enum64.class, Enum64.A63));
    }

    @Test
    public void testGenerateBitVectors_1() {
        assertArrayEquals(EnumUtils.generateBitVectors(Traffic.class, EnumSet.noneOf(Traffic.class)), 0L);
    }

    @Test
    public void testGenerateBitVectors_2() {
        assertArrayEquals(EnumUtils.generateBitVectors(Traffic.class, EnumSet.of(Traffic.RED)), 1L);
    }

    @Test
    public void testGenerateBitVectors_3() {
        assertArrayEquals(EnumUtils.generateBitVectors(Traffic.class, EnumSet.of(Traffic.AMBER)), 2L);
    }

    @Test
    public void testGenerateBitVectors_4() {
        assertArrayEquals(EnumUtils.generateBitVectors(Traffic.class, EnumSet.of(Traffic.GREEN)), 4L);
    }

    @Test
    public void testGenerateBitVectors_5() {
        assertArrayEquals(EnumUtils.generateBitVectors(Traffic.class, EnumSet.of(Traffic.RED, Traffic.AMBER)), 3L);
    }

    @Test
    public void testGenerateBitVectors_6() {
        assertArrayEquals(EnumUtils.generateBitVectors(Traffic.class, EnumSet.of(Traffic.RED, Traffic.GREEN)), 5L);
    }

    @Test
    public void testGenerateBitVectors_7() {
        assertArrayEquals(EnumUtils.generateBitVectors(Traffic.class, EnumSet.of(Traffic.AMBER, Traffic.GREEN)), 6L);
    }

    @Test
    public void testGenerateBitVectors_8() {
        assertArrayEquals(EnumUtils.generateBitVectors(Traffic.class, EnumSet.of(Traffic.RED, Traffic.AMBER, Traffic.GREEN)), 7L);
    }

    @Test
    public void testGenerateBitVectors_9() {
        assertArrayEquals(EnumUtils.generateBitVectors(Enum64.class, EnumSet.of(Enum64.A31)), 1L << 31);
    }

    @Test
    public void testGenerateBitVectors_10() {
        assertArrayEquals(EnumUtils.generateBitVectors(Enum64.class, EnumSet.of(Enum64.A32)), 1L << 32);
    }

    @Test
    public void testGenerateBitVectors_11() {
        assertArrayEquals(EnumUtils.generateBitVectors(Enum64.class, EnumSet.of(Enum64.A63)), 1L << 63);
    }

    @Test
    public void testGenerateBitVectors_12() {
        assertArrayEquals(EnumUtils.generateBitVectors(Enum64.class, EnumSet.of(Enum64.A63)), Long.MIN_VALUE);
    }

    @Test
    public void testGenerateBitVectors_13() {
        assertArrayEquals(EnumUtils.generateBitVectors(TooMany.class, EnumSet.of(TooMany.M2)), 1L, 0L);
    }

    @Test
    public void testGenerateBitVectors_14() {
        assertArrayEquals(EnumUtils.generateBitVectors(TooMany.class, EnumSet.of(TooMany.L2, TooMany.M2)), 1L, 1L << 63);
    }

    @Test
    public void testGenerateBitVectorsFromArray_1() {
        assertArrayEquals(EnumUtils.generateBitVectors(Traffic.class), 0L);
    }

    @Test
    public void testGenerateBitVectorsFromArray_2() {
        assertArrayEquals(EnumUtils.generateBitVectors(Traffic.class, Traffic.RED), 1L);
    }

    @Test
    public void testGenerateBitVectorsFromArray_3() {
        assertArrayEquals(EnumUtils.generateBitVectors(Traffic.class, Traffic.AMBER), 2L);
    }

    @Test
    public void testGenerateBitVectorsFromArray_4() {
        assertArrayEquals(EnumUtils.generateBitVectors(Traffic.class, Traffic.GREEN), 4L);
    }

    @Test
    public void testGenerateBitVectorsFromArray_5() {
        assertArrayEquals(EnumUtils.generateBitVectors(Traffic.class, Traffic.RED, Traffic.AMBER), 3L);
    }

    @Test
    public void testGenerateBitVectorsFromArray_6() {
        assertArrayEquals(EnumUtils.generateBitVectors(Traffic.class, Traffic.RED, Traffic.GREEN), 5L);
    }

    @Test
    public void testGenerateBitVectorsFromArray_7() {
        assertArrayEquals(EnumUtils.generateBitVectors(Traffic.class, Traffic.AMBER, Traffic.GREEN), 6L);
    }

    @Test
    public void testGenerateBitVectorsFromArray_8() {
        assertArrayEquals(EnumUtils.generateBitVectors(Traffic.class, Traffic.RED, Traffic.AMBER, Traffic.GREEN), 7L);
    }

    @Test
    public void testGenerateBitVectorsFromArray_9() {
        assertArrayEquals(EnumUtils.generateBitVectors(Traffic.class, Traffic.RED, Traffic.AMBER, Traffic.GREEN, Traffic.GREEN), 7L);
    }

    @Test
    public void testGenerateBitVectorsFromArray_10() {
        assertArrayEquals(EnumUtils.generateBitVectors(Enum64.class, Enum64.A31), 1L << 31);
    }

    @Test
    public void testGenerateBitVectorsFromArray_11() {
        assertArrayEquals(EnumUtils.generateBitVectors(Enum64.class, Enum64.A32), 1L << 32);
    }

    @Test
    public void testGenerateBitVectorsFromArray_12() {
        assertArrayEquals(EnumUtils.generateBitVectors(Enum64.class, Enum64.A63), 1L << 63);
    }

    @Test
    public void testGenerateBitVectorsFromArray_13() {
        assertArrayEquals(EnumUtils.generateBitVectors(Enum64.class, Enum64.A63), Long.MIN_VALUE);
    }

    @Test
    public void testGenerateBitVectorsFromArray_14() {
        assertArrayEquals(EnumUtils.generateBitVectors(TooMany.class, TooMany.M2), 1L, 0L);
    }

    @Test
    public void testGenerateBitVectorsFromArray_15() {
        assertArrayEquals(EnumUtils.generateBitVectors(TooMany.class, TooMany.L2, TooMany.M2), 1L, 1L << 63);
    }

    @Test
    public void testGetEnum_1() {
        assertEquals(Traffic.RED, EnumUtils.getEnum(Traffic.class, "RED"));
    }

    @Test
    public void testGetEnum_2() {
        assertEquals(Traffic.AMBER, EnumUtils.getEnum(Traffic.class, "AMBER"));
    }

    @Test
    public void testGetEnum_3() {
        assertEquals(Traffic.GREEN, EnumUtils.getEnum(Traffic.class, "GREEN"));
    }

    @Test
    public void testGetEnum_4() {
        assertNull(EnumUtils.getEnum(Traffic.class, "PURPLE"));
    }

    @Test
    public void testGetEnum_5() {
        assertNull(EnumUtils.getEnum(Traffic.class, null));
    }

    @Test
    public void testGetEnum_defaultEnum_1() {
        assertEquals(Traffic.RED, EnumUtils.getEnum(Traffic.class, "RED", Traffic.AMBER));
    }

    @Test
    public void testGetEnum_defaultEnum_2() {
        assertEquals(Traffic.AMBER, EnumUtils.getEnum(Traffic.class, "AMBER", Traffic.GREEN));
    }

    @Test
    public void testGetEnum_defaultEnum_3() {
        assertEquals(Traffic.GREEN, EnumUtils.getEnum(Traffic.class, "GREEN", Traffic.RED));
    }

    @Test
    public void testGetEnum_defaultEnum_4() {
        assertEquals(Traffic.AMBER, EnumUtils.getEnum(Traffic.class, "PURPLE", Traffic.AMBER));
    }

    @Test
    public void testGetEnum_defaultEnum_5() {
        assertEquals(Traffic.GREEN, EnumUtils.getEnum(Traffic.class, "PURPLE", Traffic.GREEN));
    }

    @Test
    public void testGetEnum_defaultEnum_6() {
        assertEquals(Traffic.RED, EnumUtils.getEnum(Traffic.class, "PURPLE", Traffic.RED));
    }

    @Test
    public void testGetEnum_defaultEnum_7() {
        assertEquals(Traffic.AMBER, EnumUtils.getEnum(Traffic.class, null, Traffic.AMBER));
    }

    @Test
    public void testGetEnum_defaultEnum_8() {
        assertEquals(Traffic.GREEN, EnumUtils.getEnum(Traffic.class, null, Traffic.GREEN));
    }

    @Test
    public void testGetEnum_defaultEnum_9() {
        assertEquals(Traffic.RED, EnumUtils.getEnum(Traffic.class, null, Traffic.RED));
    }

    @Test
    public void testGetEnum_defaultEnum_10() {
        assertNull(EnumUtils.getEnum(Traffic.class, "PURPLE", null));
    }

    @Test
    public void testGetEnumIgnoreCase_1() {
        assertEquals(Traffic.RED, EnumUtils.getEnumIgnoreCase(Traffic.class, "red"));
    }

    @Test
    public void testGetEnumIgnoreCase_2() {
        assertEquals(Traffic.AMBER, EnumUtils.getEnumIgnoreCase(Traffic.class, "Amber"));
    }

    @Test
    public void testGetEnumIgnoreCase_3() {
        assertEquals(Traffic.GREEN, EnumUtils.getEnumIgnoreCase(Traffic.class, "grEEn"));
    }

    @Test
    public void testGetEnumIgnoreCase_4() {
        assertNull(EnumUtils.getEnumIgnoreCase(Traffic.class, "purple"));
    }

    @Test
    public void testGetEnumIgnoreCase_5() {
        assertNull(EnumUtils.getEnumIgnoreCase(Traffic.class, null));
    }

    @Test
    public void testGetEnumIgnoreCase_defaultEnum_1() {
        assertEquals(Traffic.RED, EnumUtils.getEnumIgnoreCase(Traffic.class, "red", Traffic.AMBER));
    }

    @Test
    public void testGetEnumIgnoreCase_defaultEnum_2() {
        assertEquals(Traffic.AMBER, EnumUtils.getEnumIgnoreCase(Traffic.class, "Amber", Traffic.GREEN));
    }

    @Test
    public void testGetEnumIgnoreCase_defaultEnum_3() {
        assertEquals(Traffic.GREEN, EnumUtils.getEnumIgnoreCase(Traffic.class, "grEEn", Traffic.RED));
    }

    @Test
    public void testGetEnumIgnoreCase_defaultEnum_4() {
        assertEquals(Traffic.AMBER, EnumUtils.getEnumIgnoreCase(Traffic.class, "PURPLE", Traffic.AMBER));
    }

    @Test
    public void testGetEnumIgnoreCase_defaultEnum_5() {
        assertEquals(Traffic.GREEN, EnumUtils.getEnumIgnoreCase(Traffic.class, "purple", Traffic.GREEN));
    }

    @Test
    public void testGetEnumIgnoreCase_defaultEnum_6() {
        assertEquals(Traffic.RED, EnumUtils.getEnumIgnoreCase(Traffic.class, "pUrPlE", Traffic.RED));
    }

    @Test
    public void testGetEnumIgnoreCase_defaultEnum_7() {
        assertEquals(Traffic.AMBER, EnumUtils.getEnumIgnoreCase(Traffic.class, null, Traffic.AMBER));
    }

    @Test
    public void testGetEnumIgnoreCase_defaultEnum_8() {
        assertEquals(Traffic.GREEN, EnumUtils.getEnumIgnoreCase(Traffic.class, null, Traffic.GREEN));
    }

    @Test
    public void testGetEnumIgnoreCase_defaultEnum_9() {
        assertEquals(Traffic.RED, EnumUtils.getEnumIgnoreCase(Traffic.class, null, Traffic.RED));
    }

    @Test
    public void testGetEnumIgnoreCase_defaultEnum_10() {
        assertNull(EnumUtils.getEnumIgnoreCase(Traffic.class, "PURPLE", null));
    }

    @Test
    public void testIsValidEnum_1() {
        assertTrue(EnumUtils.isValidEnum(Traffic.class, "RED"));
    }

    @Test
    public void testIsValidEnum_2() {
        assertTrue(EnumUtils.isValidEnum(Traffic.class, "AMBER"));
    }

    @Test
    public void testIsValidEnum_3() {
        assertTrue(EnumUtils.isValidEnum(Traffic.class, "GREEN"));
    }

    @Test
    public void testIsValidEnum_4() {
        assertFalse(EnumUtils.isValidEnum(Traffic.class, "PURPLE"));
    }

    @Test
    public void testIsValidEnum_5() {
        assertFalse(EnumUtils.isValidEnum(Traffic.class, null));
    }

    @Test
    public void testIsValidEnumIgnoreCase_1() {
        assertTrue(EnumUtils.isValidEnumIgnoreCase(Traffic.class, "red"));
    }

    @Test
    public void testIsValidEnumIgnoreCase_2() {
        assertTrue(EnumUtils.isValidEnumIgnoreCase(Traffic.class, "Amber"));
    }

    @Test
    public void testIsValidEnumIgnoreCase_3() {
        assertTrue(EnumUtils.isValidEnumIgnoreCase(Traffic.class, "grEEn"));
    }

    @Test
    public void testIsValidEnumIgnoreCase_4() {
        assertFalse(EnumUtils.isValidEnumIgnoreCase(Traffic.class, "purple"));
    }

    @Test
    public void testIsValidEnumIgnoreCase_5() {
        assertFalse(EnumUtils.isValidEnumIgnoreCase(Traffic.class, null));
    }

    @Test
    public void testProcessBitVector_1() {
        assertEquals(EnumSet.noneOf(Traffic.class), EnumUtils.processBitVector(Traffic.class, 0L));
    }

    @Test
    public void testProcessBitVector_2() {
        assertEquals(EnumSet.of(Traffic.RED), EnumUtils.processBitVector(Traffic.class, 1L));
    }

    @Test
    public void testProcessBitVector_3() {
        assertEquals(EnumSet.of(Traffic.AMBER), EnumUtils.processBitVector(Traffic.class, 2L));
    }

    @Test
    public void testProcessBitVector_4() {
        assertEquals(EnumSet.of(Traffic.RED, Traffic.AMBER), EnumUtils.processBitVector(Traffic.class, 3L));
    }

    @Test
    public void testProcessBitVector_5() {
        assertEquals(EnumSet.of(Traffic.GREEN), EnumUtils.processBitVector(Traffic.class, 4L));
    }

    @Test
    public void testProcessBitVector_6() {
        assertEquals(EnumSet.of(Traffic.RED, Traffic.GREEN), EnumUtils.processBitVector(Traffic.class, 5L));
    }

    @Test
    public void testProcessBitVector_7() {
        assertEquals(EnumSet.of(Traffic.AMBER, Traffic.GREEN), EnumUtils.processBitVector(Traffic.class, 6L));
    }

    @Test
    public void testProcessBitVector_8() {
        assertEquals(EnumSet.of(Traffic.RED, Traffic.AMBER, Traffic.GREEN), EnumUtils.processBitVector(Traffic.class, 7L));
    }

    @Test
    public void testProcessBitVector_9() {
        assertEquals(EnumSet.of(Enum64.A31), EnumUtils.processBitVector(Enum64.class, 1L << 31));
    }

    @Test
    public void testProcessBitVector_10() {
        assertEquals(EnumSet.of(Enum64.A32), EnumUtils.processBitVector(Enum64.class, 1L << 32));
    }

    @Test
    public void testProcessBitVector_11() {
        assertEquals(EnumSet.of(Enum64.A63), EnumUtils.processBitVector(Enum64.class, 1L << 63));
    }

    @Test
    public void testProcessBitVector_12() {
        assertEquals(EnumSet.of(Enum64.A63), EnumUtils.processBitVector(Enum64.class, Long.MIN_VALUE));
    }

    @Test
    public void testProcessBitVectors_1() {
        assertEquals(EnumSet.noneOf(Traffic.class), EnumUtils.processBitVectors(Traffic.class, 0L));
    }

    @Test
    public void testProcessBitVectors_2() {
        assertEquals(EnumSet.of(Traffic.RED), EnumUtils.processBitVectors(Traffic.class, 1L));
    }

    @Test
    public void testProcessBitVectors_3() {
        assertEquals(EnumSet.of(Traffic.AMBER), EnumUtils.processBitVectors(Traffic.class, 2L));
    }

    @Test
    public void testProcessBitVectors_4() {
        assertEquals(EnumSet.of(Traffic.RED, Traffic.AMBER), EnumUtils.processBitVectors(Traffic.class, 3L));
    }

    @Test
    public void testProcessBitVectors_5() {
        assertEquals(EnumSet.of(Traffic.GREEN), EnumUtils.processBitVectors(Traffic.class, 4L));
    }

    @Test
    public void testProcessBitVectors_6() {
        assertEquals(EnumSet.of(Traffic.RED, Traffic.GREEN), EnumUtils.processBitVectors(Traffic.class, 5L));
    }

    @Test
    public void testProcessBitVectors_7() {
        assertEquals(EnumSet.of(Traffic.AMBER, Traffic.GREEN), EnumUtils.processBitVectors(Traffic.class, 6L));
    }

    @Test
    public void testProcessBitVectors_8() {
        assertEquals(EnumSet.of(Traffic.RED, Traffic.AMBER, Traffic.GREEN), EnumUtils.processBitVectors(Traffic.class, 7L));
    }

    @Test
    public void testProcessBitVectors_9() {
        assertEquals(EnumSet.noneOf(Traffic.class), EnumUtils.processBitVectors(Traffic.class, 0L, 0L));
    }

    @Test
    public void testProcessBitVectors_10() {
        assertEquals(EnumSet.of(Traffic.RED), EnumUtils.processBitVectors(Traffic.class, 0L, 1L));
    }

    @Test
    public void testProcessBitVectors_11() {
        assertEquals(EnumSet.of(Traffic.AMBER), EnumUtils.processBitVectors(Traffic.class, 0L, 2L));
    }

    @Test
    public void testProcessBitVectors_12() {
        assertEquals(EnumSet.of(Traffic.RED, Traffic.AMBER), EnumUtils.processBitVectors(Traffic.class, 0L, 3L));
    }

    @Test
    public void testProcessBitVectors_13() {
        assertEquals(EnumSet.of(Traffic.GREEN), EnumUtils.processBitVectors(Traffic.class, 0L, 4L));
    }

    @Test
    public void testProcessBitVectors_14() {
        assertEquals(EnumSet.of(Traffic.RED, Traffic.GREEN), EnumUtils.processBitVectors(Traffic.class, 0L, 5L));
    }

    @Test
    public void testProcessBitVectors_15() {
        assertEquals(EnumSet.of(Traffic.AMBER, Traffic.GREEN), EnumUtils.processBitVectors(Traffic.class, 0L, 6L));
    }

    @Test
    public void testProcessBitVectors_16() {
        assertEquals(EnumSet.of(Traffic.RED, Traffic.AMBER, Traffic.GREEN), EnumUtils.processBitVectors(Traffic.class, 0L, 7L));
    }

    @Test
    public void testProcessBitVectors_17() {
        assertEquals(EnumSet.noneOf(Traffic.class), EnumUtils.processBitVectors(Traffic.class, 666L, 0L));
    }

    @Test
    public void testProcessBitVectors_18() {
        assertEquals(EnumSet.of(Traffic.RED), EnumUtils.processBitVectors(Traffic.class, 666L, 1L));
    }

    @Test
    public void testProcessBitVectors_19() {
        assertEquals(EnumSet.of(Traffic.AMBER), EnumUtils.processBitVectors(Traffic.class, 666L, 2L));
    }

    @Test
    public void testProcessBitVectors_20() {
        assertEquals(EnumSet.of(Traffic.RED, Traffic.AMBER), EnumUtils.processBitVectors(Traffic.class, 666L, 3L));
    }

    @Test
    public void testProcessBitVectors_21() {
        assertEquals(EnumSet.of(Traffic.GREEN), EnumUtils.processBitVectors(Traffic.class, 666L, 4L));
    }

    @Test
    public void testProcessBitVectors_22() {
        assertEquals(EnumSet.of(Traffic.RED, Traffic.GREEN), EnumUtils.processBitVectors(Traffic.class, 666L, 5L));
    }

    @Test
    public void testProcessBitVectors_23() {
        assertEquals(EnumSet.of(Traffic.AMBER, Traffic.GREEN), EnumUtils.processBitVectors(Traffic.class, 666L, 6L));
    }

    @Test
    public void testProcessBitVectors_24() {
        assertEquals(EnumSet.of(Traffic.RED, Traffic.AMBER, Traffic.GREEN), EnumUtils.processBitVectors(Traffic.class, 666L, 7L));
    }

    @Test
    public void testProcessBitVectors_25() {
        assertEquals(EnumSet.of(Enum64.A31), EnumUtils.processBitVectors(Enum64.class, 1L << 31));
    }

    @Test
    public void testProcessBitVectors_26() {
        assertEquals(EnumSet.of(Enum64.A32), EnumUtils.processBitVectors(Enum64.class, 1L << 32));
    }

    @Test
    public void testProcessBitVectors_27() {
        assertEquals(EnumSet.of(Enum64.A63), EnumUtils.processBitVectors(Enum64.class, 1L << 63));
    }

    @Test
    public void testProcessBitVectors_28() {
        assertEquals(EnumSet.of(Enum64.A63), EnumUtils.processBitVectors(Enum64.class, Long.MIN_VALUE));
    }

    @Test
    public void testProcessBitVectors_longClass_1() {
        assertEquals(EnumSet.noneOf(TooMany.class), EnumUtils.processBitVectors(TooMany.class, 0L));
    }

    @Test
    public void testProcessBitVectors_longClass_2() {
        assertEquals(EnumSet.of(TooMany.A), EnumUtils.processBitVectors(TooMany.class, 1L));
    }

    @Test
    public void testProcessBitVectors_longClass_3() {
        assertEquals(EnumSet.of(TooMany.B), EnumUtils.processBitVectors(TooMany.class, 2L));
    }

    @Test
    public void testProcessBitVectors_longClass_4() {
        assertEquals(EnumSet.of(TooMany.A, TooMany.B), EnumUtils.processBitVectors(TooMany.class, 3L));
    }

    @Test
    public void testProcessBitVectors_longClass_5() {
        assertEquals(EnumSet.of(TooMany.C), EnumUtils.processBitVectors(TooMany.class, 4L));
    }

    @Test
    public void testProcessBitVectors_longClass_6() {
        assertEquals(EnumSet.of(TooMany.A, TooMany.C), EnumUtils.processBitVectors(TooMany.class, 5L));
    }

    @Test
    public void testProcessBitVectors_longClass_7() {
        assertEquals(EnumSet.of(TooMany.B, TooMany.C), EnumUtils.processBitVectors(TooMany.class, 6L));
    }

    @Test
    public void testProcessBitVectors_longClass_8() {
        assertEquals(EnumSet.of(TooMany.A, TooMany.B, TooMany.C), EnumUtils.processBitVectors(TooMany.class, 7L));
    }

    @Test
    public void testProcessBitVectors_longClass_9() {
        assertEquals(EnumSet.noneOf(TooMany.class), EnumUtils.processBitVectors(TooMany.class, 0L, 0L));
    }

    @Test
    public void testProcessBitVectors_longClass_10() {
        assertEquals(EnumSet.of(TooMany.A), EnumUtils.processBitVectors(TooMany.class, 0L, 1L));
    }

    @Test
    public void testProcessBitVectors_longClass_11() {
        assertEquals(EnumSet.of(TooMany.B), EnumUtils.processBitVectors(TooMany.class, 0L, 2L));
    }

    @Test
    public void testProcessBitVectors_longClass_12() {
        assertEquals(EnumSet.of(TooMany.A, TooMany.B), EnumUtils.processBitVectors(TooMany.class, 0L, 3L));
    }

    @Test
    public void testProcessBitVectors_longClass_13() {
        assertEquals(EnumSet.of(TooMany.C), EnumUtils.processBitVectors(TooMany.class, 0L, 4L));
    }

    @Test
    public void testProcessBitVectors_longClass_14() {
        assertEquals(EnumSet.of(TooMany.A, TooMany.C), EnumUtils.processBitVectors(TooMany.class, 0L, 5L));
    }

    @Test
    public void testProcessBitVectors_longClass_15() {
        assertEquals(EnumSet.of(TooMany.B, TooMany.C), EnumUtils.processBitVectors(TooMany.class, 0L, 6L));
    }

    @Test
    public void testProcessBitVectors_longClass_16() {
        assertEquals(EnumSet.of(TooMany.A, TooMany.B, TooMany.C), EnumUtils.processBitVectors(TooMany.class, 0L, 7L));
    }

    @Test
    public void testProcessBitVectors_longClass_17() {
        assertEquals(EnumSet.of(TooMany.A, TooMany.B, TooMany.C), EnumUtils.processBitVectors(TooMany.class, 0L, 7L));
    }

    @Test
    public void testProcessBitVectors_longClass_18() {
        assertEquals(EnumSet.of(TooMany.M2), EnumUtils.processBitVectors(TooMany.class, 1L, 0L));
    }

    @Test
    public void testProcessBitVectors_longClass_19() {
        assertEquals(EnumSet.of(TooMany.A, TooMany.M2), EnumUtils.processBitVectors(TooMany.class, 1L, 1L));
    }

    @Test
    public void testProcessBitVectors_longClass_20() {
        assertEquals(EnumSet.of(TooMany.B, TooMany.M2), EnumUtils.processBitVectors(TooMany.class, 1L, 2L));
    }

    @Test
    public void testProcessBitVectors_longClass_21() {
        assertEquals(EnumSet.of(TooMany.A, TooMany.B, TooMany.M2), EnumUtils.processBitVectors(TooMany.class, 1L, 3L));
    }

    @Test
    public void testProcessBitVectors_longClass_22() {
        assertEquals(EnumSet.of(TooMany.C, TooMany.M2), EnumUtils.processBitVectors(TooMany.class, 1L, 4L));
    }

    @Test
    public void testProcessBitVectors_longClass_23() {
        assertEquals(EnumSet.of(TooMany.A, TooMany.C, TooMany.M2), EnumUtils.processBitVectors(TooMany.class, 1L, 5L));
    }

    @Test
    public void testProcessBitVectors_longClass_24() {
        assertEquals(EnumSet.of(TooMany.B, TooMany.C, TooMany.M2), EnumUtils.processBitVectors(TooMany.class, 1L, 6L));
    }

    @Test
    public void testProcessBitVectors_longClass_25() {
        assertEquals(EnumSet.of(TooMany.A, TooMany.B, TooMany.C, TooMany.M2), EnumUtils.processBitVectors(TooMany.class, 1L, 7L));
    }

    @Test
    public void testProcessBitVectors_longClass_26() {
        assertEquals(EnumSet.of(TooMany.A, TooMany.B, TooMany.C, TooMany.M2), EnumUtils.processBitVectors(TooMany.class, 1L, 7L));
    }

    @Test
    public void testProcessBitVectors_longClass_27() {
        assertEquals(EnumSet.of(TooMany.M2), EnumUtils.processBitVectors(TooMany.class, 9L, 0L));
    }

    @Test
    public void testProcessBitVectors_longClass_28() {
        assertEquals(EnumSet.of(TooMany.A, TooMany.M2), EnumUtils.processBitVectors(TooMany.class, 9L, 1L));
    }

    @Test
    public void testProcessBitVectors_longClass_29() {
        assertEquals(EnumSet.of(TooMany.B, TooMany.M2), EnumUtils.processBitVectors(TooMany.class, 9L, 2L));
    }

    @Test
    public void testProcessBitVectors_longClass_30() {
        assertEquals(EnumSet.of(TooMany.A, TooMany.B, TooMany.M2), EnumUtils.processBitVectors(TooMany.class, 9L, 3L));
    }

    @Test
    public void testProcessBitVectors_longClass_31() {
        assertEquals(EnumSet.of(TooMany.C, TooMany.M2), EnumUtils.processBitVectors(TooMany.class, 9L, 4L));
    }

    @Test
    public void testProcessBitVectors_longClass_32() {
        assertEquals(EnumSet.of(TooMany.A, TooMany.C, TooMany.M2), EnumUtils.processBitVectors(TooMany.class, 9L, 5L));
    }

    @Test
    public void testProcessBitVectors_longClass_33() {
        assertEquals(EnumSet.of(TooMany.B, TooMany.C, TooMany.M2), EnumUtils.processBitVectors(TooMany.class, 9L, 6L));
    }

    @Test
    public void testProcessBitVectors_longClass_34() {
        assertEquals(EnumSet.of(TooMany.A, TooMany.B, TooMany.C, TooMany.M2), EnumUtils.processBitVectors(TooMany.class, 9L, 7L));
    }

    @Test
    public void testProcessBitVectors_longClass_35() {
        assertEquals(EnumSet.of(TooMany.A, TooMany.B, TooMany.C, TooMany.M2), EnumUtils.processBitVectors(TooMany.class, 9L, 7L));
    }
}
