package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class CharUtilsTest_Parameterized extends AbstractLangTest {

    private static final char CHAR_COPY = '\u00a9';

    private static final Character CHARACTER_A = Character.valueOf('A');

    private static final Character CHARACTER_B = Character.valueOf('B');

    @Test
    public void testCompare_2() {
        assertEquals(0, CharUtils.compare('c', 'c'));
    }

    @Test
    public void testConstructor_1() {
        assertNotNull(new CharUtils());
    }

    @Test
    public void testConstructor_2_testMerged_2() {
        final Constructor<?>[] cons = CharUtils.class.getDeclaredConstructors();
        assertEquals(1, cons.length);
        assertTrue(Modifier.isPublic(cons[0].getModifiers()));
    }

    @Test
    public void testConstructor_4() {
        assertTrue(Modifier.isPublic(CharUtils.class.getModifiers()));
    }

    @Test
    public void testConstructor_5() {
        assertFalse(Modifier.isFinal(CharUtils.class.getModifiers()));
    }

    @Test
    public void testToChar_Character_char_1() {
        assertEquals('A', CharUtils.toChar(CHARACTER_A, 'X'));
    }

    @Test
    public void testToChar_Character_char_2() {
        assertEquals('B', CharUtils.toChar(CHARACTER_B, 'X'));
    }

    @Test
    public void testToCharacterObject_String_1() {
        assertNull(CharUtils.toCharacterObject(null));
    }

    @Test
    public void testToCharacterObject_String_2() {
        assertNull(CharUtils.toCharacterObject(""));
    }

    @Test
    public void testToCharacterObject_String_5() {
        assertSame(CharUtils.toCharacterObject("a"), CharUtils.toCharacterObject("a"));
    }

    @Test
    public void testToIntValue_char_int_3() {
        assertEquals(-1, CharUtils.toIntValue('a', -1));
    }

    @Test
    public void testToIntValue_Character_int_3() {
        assertEquals(-1, CharUtils.toIntValue(Character.valueOf('A'), -1));
    }

    @Test
    public void testToIntValue_Character_int_4() {
        assertEquals(-1, CharUtils.toIntValue(null, -1));
    }

    @Test
    public void testToString_Character_1() {
        assertNull(CharUtils.toString(null));
    }

    @Test
    public void testToString_Character_2() {
        assertEquals("A", CharUtils.toString(CHARACTER_A));
    }

    @Test
    public void testToString_Character_3() {
        assertSame(CharUtils.toString(CHARACTER_A), CharUtils.toString(CHARACTER_A));
    }

    @Test
    public void testToUnicodeEscaped_Character_1() {
        assertNull(CharUtils.unicodeEscaped(null));
    }

    @Test
    public void testToUnicodeEscaped_Character_2() {
        assertEquals("\\u0041", CharUtils.unicodeEscaped(CHARACTER_A));
    }

    @ParameterizedTest
    @MethodSource("Provider_testCompare_1_3")
    public void testCompare_1_3(int param1, String param2, String param3) {
        assertTrue(CharUtils.compare(param2, param3) < param1);
    }

    static public Stream<Arguments> Provider_testCompare_1_3() {
        return Stream.of(arguments(0, "a", "b"), arguments(0, "c", "a"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testToChar_Character_char_3to4")
    public void testToChar_Character_char_3to4(String param1, String param2) {
        assertEquals(param1, CharUtils.toChar((Character) null, param2));
    }

    static public Stream<Arguments> Provider_testToChar_Character_char_3to4() {
        return Stream.of(arguments("X", "X"), arguments("X", "X"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testToChar_String_char_1to3")
    public void testToChar_String_char_1to3(String param1, String param2, String param3) {
        assertEquals(param1, CharUtils.toChar(param2, param3));
    }

    static public Stream<Arguments> Provider_testToChar_String_char_1to3() {
        return Stream.of(arguments("A", "A", "X"), arguments("B", "BA", "X"), arguments("X", "", "X"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testToCharacterObject_String_3to4")
    public void testToCharacterObject_String_3to4(String param1, String param2) {
        assertEquals(Character.valueOf(param1), CharUtils.toCharacterObject(param2));
    }

    static public Stream<Arguments> Provider_testToCharacterObject_String_3to4() {
        return Stream.of(arguments("a", "a"), arguments("a", "abc"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testToIntValue_char_int_1to2")
    public void testToIntValue_char_int_1to2(int param1, int param2, int param3) {
        assertEquals(param1, CharUtils.toIntValue(param2, -param3));
    }

    static public Stream<Arguments> Provider_testToIntValue_char_int_1to2() {
        return Stream.of(arguments(0, 0, 1), arguments(3, 3, 1));
    }

    @ParameterizedTest
    @MethodSource("Provider_testToIntValue_Character_int_1to2")
    public void testToIntValue_Character_int_1to2(int param1, int param2, int param3) {
        assertEquals(param1, CharUtils.toIntValue(Character.valueOf(param2), -param3));
    }

    static public Stream<Arguments> Provider_testToIntValue_Character_int_1to2() {
        return Stream.of(arguments(0, 0, 1), arguments(3, 3, 1));
    }
}
