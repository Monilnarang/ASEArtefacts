package org.apache.commons.lang3.mutable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class MutableLongTest_Parameterized extends AbstractLangTest {

    @Test
    public void testConstructors_3() {
        assertEquals(2, new MutableLong(Long.valueOf(2)).longValue());
    }

    @Test
    public void testConstructors_4() {
        assertEquals(3, new MutableLong(new MutableLong(3)).longValue());
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
    public void testToString_3() {
        assertEquals("-123", new MutableLong(-123).toString());
    }

    @ParameterizedTest
    @MethodSource("Provider_testConstructors_1_1")
    public void testConstructors_1_1(int param1) {
        assertEquals(param1, new MutableLong().longValue());
    }

    static public Stream<Arguments> Provider_testConstructors_1_1() {
        return Stream.of(arguments(0), arguments(0));
    }

    @ParameterizedTest
    @MethodSource("Provider_testConstructors_2_5")
    public void testConstructors_2_5(int param1, int param2) {
        assertEquals(param1, new MutableLong(param2).longValue());
    }

    static public Stream<Arguments> Provider_testConstructors_2_5() {
        return Stream.of(arguments(1, 1), arguments(2, 2));
    }

    @ParameterizedTest
    @MethodSource("Provider_testToLong_1to2")
    public void testToLong_1to2(long param1, long param2) {
        assertEquals(Long.valueOf(param1), new MutableLong(param2).toLong());
    }

    static public Stream<Arguments> Provider_testToLong_1to2() {
        return Stream.of(arguments(0L, 0L), arguments(123L, 123L));
    }

    @ParameterizedTest
    @MethodSource("Provider_testToString_1to2")
    public void testToString_1to2(int param1, int param2) {
        assertEquals(param1, new MutableLong(param2).toString());
    }

    static public Stream<Arguments> Provider_testToString_1to2() {
        return Stream.of(arguments(0, 0), arguments(10, 10));
    }
}
