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

public class CharUtilsTest_Purified extends AbstractLangTest {

    private static final char CHAR_COPY = '\u00a9';

    private static final Character CHARACTER_A = Character.valueOf('A');

    private static final Character CHARACTER_B = Character.valueOf('B');

    @Test
    public void testCompare_1() {
        assertTrue(CharUtils.compare('a', 'b') < 0);
    }

    @Test
    public void testCompare_2() {
        assertEquals(0, CharUtils.compare('c', 'c'));
    }

    @Test
    public void testCompare_3() {
        assertTrue(CharUtils.compare('c', 'a') > 0);
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
    public void testToChar_Character_char_3() {
        assertEquals('X', CharUtils.toChar((Character) null, 'X'));
    }

    @Test
    public void testToChar_String_char_1() {
        assertEquals('A', CharUtils.toChar("A", 'X'));
    }

    @Test
    public void testToChar_String_char_2() {
        assertEquals('B', CharUtils.toChar("BA", 'X'));
    }

    @Test
    public void testToChar_String_char_3() {
        assertEquals('X', CharUtils.toChar("", 'X'));
    }

    @Test
    public void testToChar_String_char_4() {
        assertEquals('X', CharUtils.toChar((String) null, 'X'));
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
    public void testToCharacterObject_String_3() {
        assertEquals(Character.valueOf('a'), CharUtils.toCharacterObject("a"));
    }

    @Test
    public void testToCharacterObject_String_4() {
        assertEquals(Character.valueOf('a'), CharUtils.toCharacterObject("abc"));
    }

    @Test
    public void testToCharacterObject_String_5() {
        assertSame(CharUtils.toCharacterObject("a"), CharUtils.toCharacterObject("a"));
    }

    @Test
    public void testToIntValue_char_int_1() {
        assertEquals(0, CharUtils.toIntValue('0', -1));
    }

    @Test
    public void testToIntValue_char_int_2() {
        assertEquals(3, CharUtils.toIntValue('3', -1));
    }

    @Test
    public void testToIntValue_char_int_3() {
        assertEquals(-1, CharUtils.toIntValue('a', -1));
    }

    @Test
    public void testToIntValue_Character_int_1() {
        assertEquals(0, CharUtils.toIntValue(Character.valueOf('0'), -1));
    }

    @Test
    public void testToIntValue_Character_int_2() {
        assertEquals(3, CharUtils.toIntValue(Character.valueOf('3'), -1));
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
}
