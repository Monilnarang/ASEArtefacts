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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class FluentBitSetTest_Parameterized extends AbstractLangTest {

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
    public void test_toString_1() {
        assertEquals("{0, 1, 2, 3, 4, 5, 6, 7}", eightFbs.toString(), "Returned incorrect string representation");
    }

    @Test
    public void test_toString_2() {
        eightFbs.clear(2);
        assertEquals("{0, 1, 3, 4, 5, 6, 7}", eightFbs.toString(), "Returned incorrect string representation");
    }

    @ParameterizedTest
    @MethodSource("Provider_test_setRangeInclusive_1_1")
    public void test_setRangeInclusive_1_1(int param1, String param2) {
        assertEquals(param1, eightFbs.size(), param2);
    }

    static public Stream<Arguments> Provider_test_setRangeInclusive_1_1() {
        return Stream.of(arguments(64, "Returned incorrect size"), arguments(64, "Returned incorrect size"));
    }

    @ParameterizedTest
    @MethodSource("Provider_test_setRangeInclusive_2_2")
    public void test_setRangeInclusive_2_2(int param1, String param2, int param3) {
        eightFbs.set(param1);
        assertTrue(eightFbs.size() >= param3, param2);
    }

    static public Stream<Arguments> Provider_test_setRangeInclusive_2_2() {
        return Stream.of(arguments(129, "Returned incorrect size", 129), arguments(129, "Returned incorrect size", 129));
    }
}
