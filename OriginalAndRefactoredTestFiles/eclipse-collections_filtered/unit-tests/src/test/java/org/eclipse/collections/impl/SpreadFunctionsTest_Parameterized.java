package org.eclipse.collections.impl;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class SpreadFunctionsTest_Parameterized {

    @Test
    public void doubleSpreadOne_1() {
        assertEquals(-7831749829746778771L, SpreadFunctions.doubleSpreadOne(25.0d));
    }

    @Test
    public void doubleSpreadOne_2() {
        assertEquals(3020681792480265713L, SpreadFunctions.doubleSpreadOne(-25.0d));
    }

    @Test
    public void doubleSpreadTwo_1() {
        assertEquals(-7260239113076190123L, SpreadFunctions.doubleSpreadTwo(25.0d));
    }

    @Test
    public void doubleSpreadTwo_2() {
        assertEquals(-2923962723742798781L, SpreadFunctions.doubleSpreadTwo(-25.0d));
    }

    @Test
    public void longSpreadTwo_1() {
        assertEquals(-3823225069572514692L, SpreadFunctions.longSpreadTwo(12345L));
    }

    @Test
    public void longSpreadTwo_2() {
        assertEquals(7979914854381881740L, SpreadFunctions.longSpreadTwo(23456L));
    }

    @Test
    public void intSpreadOne_1() {
        assertEquals(-540084185L, SpreadFunctions.intSpreadOne(100));
    }

    @Test
    public void intSpreadOne_2() {
        assertEquals(1432552655L, SpreadFunctions.intSpreadOne(101));
    }

    @Test
    public void floatSpreadOne_1() {
        assertEquals(-1053442875L, SpreadFunctions.floatSpreadOne(9876.0F));
    }

    @Test
    public void floatSpreadOne_2() {
        assertEquals(-640291382L, SpreadFunctions.floatSpreadOne(-9876.0F));
    }

    @Test
    public void floatSpreadTwo_1() {
        assertEquals(-1971373820L, SpreadFunctions.floatSpreadTwo(9876.0F));
    }

    @Test
    public void floatSpreadTwo_2() {
        assertEquals(-1720924552L, SpreadFunctions.floatSpreadTwo(-9876.0F));
    }

    @ParameterizedTest
    @MethodSource("Provider_longSpreadOne_1to2")
    public void longSpreadOne_1to2(long param1, long param2) {
        assertEquals(param1, SpreadFunctions.longSpreadOne(param2));
    }

    static public Stream<Arguments> Provider_longSpreadOne_1to2() {
        return Stream.of(arguments(7972739338299824895L, 12345L), arguments(5629574755565220972L, 23456L));
    }

    @ParameterizedTest
    @MethodSource("Provider_intSpreadTwo_1to2")
    public void intSpreadTwo_1to2(long param1, int param2) {
        assertEquals(param1, SpreadFunctions.intSpreadTwo(param2));
    }

    static public Stream<Arguments> Provider_intSpreadTwo_1to2() {
        return Stream.of(arguments(961801704L, 100), arguments(662527578L, 101));
    }

    @ParameterizedTest
    @MethodSource("Provider_shortSpreadOne_1to2")
    public void shortSpreadOne_1to2(long param1, int param2) {
        assertEquals(-param1, SpreadFunctions.shortSpreadOne((short) param2));
    }

    static public Stream<Arguments> Provider_shortSpreadOne_1to2() {
        return Stream.of(arguments(1526665035L, 123), arguments(1120388305L, 234));
    }

    @ParameterizedTest
    @MethodSource("Provider_shortSpreadTwo_1to2")
    public void shortSpreadTwo_1to2(long param1, int param2) {
        assertEquals(-param1, SpreadFunctions.shortSpreadTwo((short) param2));
    }

    static public Stream<Arguments> Provider_shortSpreadTwo_1to2() {
        return Stream.of(arguments(474242978L, 123), arguments(1572485272L, 234));
    }
}
