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

public class MutableByteTest_Parameterized extends AbstractLangTest {

    @Test
    public void testConstructors_2() {
        assertEquals((byte) 1, new MutableByte((byte) 1).byteValue());
    }

    @Test
    public void testConstructors_3() {
        assertEquals((byte) 2, new MutableByte(Byte.valueOf((byte) 2)).byteValue());
    }

    @Test
    public void testConstructors_4() {
        assertEquals((byte) 3, new MutableByte(new MutableByte((byte) 3)).byteValue());
    }

    @Test
    public void testConstructors_5() {
        assertEquals((byte) 2, new MutableByte("2").byteValue());
    }

    @Test
    public void testGetSet_2() {
        assertEquals(Byte.valueOf((byte) 0), new MutableByte().getValue());
    }

    @Test
    public void testGetSet_3_testMerged_3() {
        final MutableByte mutNum = new MutableByte((byte) 0);
        mutNum.setValue((byte) 1);
        assertEquals((byte) 1, mutNum.byteValue());
        assertEquals(Byte.valueOf((byte) 1), mutNum.getValue());
        mutNum.setValue(Byte.valueOf((byte) 2));
        assertEquals((byte) 2, mutNum.byteValue());
        assertEquals(Byte.valueOf((byte) 2), mutNum.getValue());
        mutNum.setValue(new MutableByte((byte) 3));
        assertEquals((byte) 3, mutNum.byteValue());
        assertEquals(Byte.valueOf((byte) 3), mutNum.getValue());
    }

    @Test
    public void testToString_3() {
        assertEquals("-123", new MutableByte((byte) -123).toString());
    }

    @ParameterizedTest
    @MethodSource("Provider_testConstructors_1_1")
    public void testConstructors_1_1(int param1) {
        assertEquals((byte) param1, new MutableByte().byteValue());
    }

    static public Stream<Arguments> Provider_testConstructors_1_1() {
        return Stream.of(arguments(0), arguments(0));
    }

    @ParameterizedTest
    @MethodSource("Provider_testToByte_1to2")
    public void testToByte_1to2(int param1, int param2) {
        assertEquals(Byte.valueOf((byte) param1), new MutableByte((byte) param2).toByte());
    }

    static public Stream<Arguments> Provider_testToByte_1to2() {
        return Stream.of(arguments(0, 0), arguments(123, 123));
    }

    @ParameterizedTest
    @MethodSource("Provider_testToString_1to2")
    public void testToString_1to2(int param1, int param2) {
        assertEquals(param1, new MutableByte((byte) param2).toString());
    }

    static public Stream<Arguments> Provider_testToString_1to2() {
        return Stream.of(arguments(0, 0), arguments(10, 10));
    }
}
