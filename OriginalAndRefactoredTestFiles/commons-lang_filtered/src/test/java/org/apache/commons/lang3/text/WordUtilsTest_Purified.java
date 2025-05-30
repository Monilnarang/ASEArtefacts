package org.apache.commons.lang3.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

@Deprecated
public class WordUtilsTest_Purified extends AbstractLangTest {

    @Test
    public void testCapitalize_String_1() {
        assertNull(WordUtils.capitalize(null));
    }

    @Test
    public void testCapitalize_String_2() {
        assertEquals("", WordUtils.capitalize(""));
    }

    @Test
    public void testCapitalize_String_3() {
        assertEquals("  ", WordUtils.capitalize("  "));
    }

    @Test
    public void testCapitalize_String_4() {
        assertEquals("I", WordUtils.capitalize("I"));
    }

    @Test
    public void testCapitalize_String_5() {
        assertEquals("I", WordUtils.capitalize("i"));
    }

    @Test
    public void testCapitalize_String_6() {
        assertEquals("I Am Here 123", WordUtils.capitalize("i am here 123"));
    }

    @Test
    public void testCapitalize_String_7() {
        assertEquals("I Am Here 123", WordUtils.capitalize("I Am Here 123"));
    }

    @Test
    public void testCapitalize_String_8() {
        assertEquals("I Am HERE 123", WordUtils.capitalize("i am HERE 123"));
    }

    @Test
    public void testCapitalize_String_9() {
        assertEquals("I AM HERE 123", WordUtils.capitalize("I AM HERE 123"));
    }

    @Test
    public void testCapitalizeFully_String_1() {
        assertNull(WordUtils.capitalizeFully(null));
    }

    @Test
    public void testCapitalizeFully_String_2() {
        assertEquals("", WordUtils.capitalizeFully(""));
    }

    @Test
    public void testCapitalizeFully_String_3() {
        assertEquals("  ", WordUtils.capitalizeFully("  "));
    }

    @Test
    public void testCapitalizeFully_String_4() {
        assertEquals("I", WordUtils.capitalizeFully("I"));
    }

    @Test
    public void testCapitalizeFully_String_5() {
        assertEquals("I", WordUtils.capitalizeFully("i"));
    }

    @Test
    public void testCapitalizeFully_String_6() {
        assertEquals("I Am Here 123", WordUtils.capitalizeFully("i am here 123"));
    }

    @Test
    public void testCapitalizeFully_String_7() {
        assertEquals("I Am Here 123", WordUtils.capitalizeFully("I Am Here 123"));
    }

    @Test
    public void testCapitalizeFully_String_8() {
        assertEquals("I Am Here 123", WordUtils.capitalizeFully("i am HERE 123"));
    }

    @Test
    public void testCapitalizeFully_String_9() {
        assertEquals("I Am Here 123", WordUtils.capitalizeFully("I AM HERE 123"));
    }

    @Test
    public void testConstructor_1() {
        assertNotNull(new WordUtils());
    }

    @Test
    public void testConstructor_2_testMerged_2() {
        final Constructor<?>[] cons = WordUtils.class.getDeclaredConstructors();
        assertEquals(1, cons.length);
        assertTrue(Modifier.isPublic(cons[0].getModifiers()));
    }

    @Test
    public void testConstructor_4() {
        assertTrue(Modifier.isPublic(WordUtils.class.getModifiers()));
    }

    @Test
    public void testConstructor_5() {
        assertFalse(Modifier.isFinal(WordUtils.class.getModifiers()));
    }

    @Test
    public void testContainsAllWords_StringString_1() {
        assertFalse(WordUtils.containsAllWords(null, (String) null));
    }

    @Test
    public void testContainsAllWords_StringString_2() {
        assertFalse(WordUtils.containsAllWords(null, ""));
    }

    @Test
    public void testContainsAllWords_StringString_3() {
        assertFalse(WordUtils.containsAllWords(null, "ab"));
    }

    @Test
    public void testContainsAllWords_StringString_4() {
        assertFalse(WordUtils.containsAllWords("", (String) null));
    }

    @Test
    public void testContainsAllWords_StringString_5() {
        assertFalse(WordUtils.containsAllWords("", ""));
    }

    @Test
    public void testContainsAllWords_StringString_6() {
        assertFalse(WordUtils.containsAllWords("", "ab"));
    }

    @Test
    public void testContainsAllWords_StringString_7() {
        assertFalse(WordUtils.containsAllWords("foo", (String) null));
    }

    @Test
    public void testContainsAllWords_StringString_8() {
        assertFalse(WordUtils.containsAllWords("bar", ""));
    }

    @Test
    public void testContainsAllWords_StringString_9() {
        assertFalse(WordUtils.containsAllWords("zzabyycdxx", "by"));
    }

    @Test
    public void testContainsAllWords_StringString_10() {
        assertTrue(WordUtils.containsAllWords("lorem ipsum dolor sit amet", "ipsum", "lorem", "dolor"));
    }

    @Test
    public void testContainsAllWords_StringString_11() {
        assertFalse(WordUtils.containsAllWords("lorem ipsum dolor sit amet", "ipsum", null, "lorem", "dolor"));
    }

    @Test
    public void testContainsAllWords_StringString_12() {
        assertFalse(WordUtils.containsAllWords("lorem ipsum null dolor sit amet", "ipsum", null, "lorem", "dolor"));
    }

    @Test
    public void testContainsAllWords_StringString_13() {
        assertFalse(WordUtils.containsAllWords("ab", "b"));
    }

    @Test
    public void testContainsAllWords_StringString_14() {
        assertFalse(WordUtils.containsAllWords("ab", "z"));
    }

    @Test
    public void testInitials_String_1() {
        assertNull(WordUtils.initials(null));
    }

    @Test
    public void testInitials_String_2() {
        assertEquals("", WordUtils.initials(""));
    }

    @Test
    public void testInitials_String_3() {
        assertEquals("", WordUtils.initials("  "));
    }

    @Test
    public void testInitials_String_4() {
        assertEquals("I", WordUtils.initials("I"));
    }

    @Test
    public void testInitials_String_5() {
        assertEquals("i", WordUtils.initials("i"));
    }

    @Test
    public void testInitials_String_6() {
        assertEquals("BJL", WordUtils.initials("Ben John Lee"));
    }

    @Test
    public void testInitials_String_7() {
        assertEquals("BJL", WordUtils.initials("   Ben \n   John\tLee\t"));
    }

    @Test
    public void testInitials_String_8() {
        assertEquals("BJ", WordUtils.initials("Ben J.Lee"));
    }

    @Test
    public void testInitials_String_9() {
        assertEquals("BJ.L", WordUtils.initials(" Ben   John  . Lee"));
    }

    @Test
    public void testInitials_String_10() {
        assertEquals("iah1", WordUtils.initials("i am here 123"));
    }

    @Test
    public void testSwapCase_String_1() {
        assertNull(WordUtils.swapCase(null));
    }

    @Test
    public void testSwapCase_String_2() {
        assertEquals("", WordUtils.swapCase(""));
    }

    @Test
    public void testSwapCase_String_3() {
        assertEquals("  ", WordUtils.swapCase("  "));
    }

    @Test
    public void testSwapCase_String_4() {
        assertEquals("i", WordUtils.swapCase("I"));
    }

    @Test
    public void testSwapCase_String_5() {
        assertEquals("I", WordUtils.swapCase("i"));
    }

    @Test
    public void testSwapCase_String_6() {
        assertEquals("I AM HERE 123", WordUtils.swapCase("i am here 123"));
    }

    @Test
    public void testSwapCase_String_7() {
        assertEquals("i aM hERE 123", WordUtils.swapCase("I Am Here 123"));
    }

    @Test
    public void testSwapCase_String_8() {
        assertEquals("I AM here 123", WordUtils.swapCase("i am HERE 123"));
    }

    @Test
    public void testSwapCase_String_9() {
        assertEquals("i am here 123", WordUtils.swapCase("I AM HERE 123"));
    }

    @Test
    public void testSwapCase_String_10() {
        final String test = "This String contains a TitleCase character: \u01C8";
        final String expect = "tHIS sTRING CONTAINS A tITLEcASE CHARACTER: \u01C9";
        assertEquals(expect, WordUtils.swapCase(test));
    }

    @Test
    public void testUncapitalize_String_1() {
        assertNull(WordUtils.uncapitalize(null));
    }

    @Test
    public void testUncapitalize_String_2() {
        assertEquals("", WordUtils.uncapitalize(""));
    }

    @Test
    public void testUncapitalize_String_3() {
        assertEquals("  ", WordUtils.uncapitalize("  "));
    }

    @Test
    public void testUncapitalize_String_4() {
        assertEquals("i", WordUtils.uncapitalize("I"));
    }

    @Test
    public void testUncapitalize_String_5() {
        assertEquals("i", WordUtils.uncapitalize("i"));
    }

    @Test
    public void testUncapitalize_String_6() {
        assertEquals("i am here 123", WordUtils.uncapitalize("i am here 123"));
    }

    @Test
    public void testUncapitalize_String_7() {
        assertEquals("i am here 123", WordUtils.uncapitalize("I Am Here 123"));
    }

    @Test
    public void testUncapitalize_String_8() {
        assertEquals("i am hERE 123", WordUtils.uncapitalize("i am HERE 123"));
    }

    @Test
    public void testUncapitalize_String_9() {
        assertEquals("i aM hERE 123", WordUtils.uncapitalize("I AM HERE 123"));
    }

    @Test
    public void testWrap_StringInt_1() {
        assertNull(WordUtils.wrap(null, 20));
    }

    @Test
    public void testWrap_StringInt_2() {
        assertNull(WordUtils.wrap(null, -1));
    }

    @Test
    public void testWrap_StringInt_3() {
        assertEquals("", WordUtils.wrap("", 20));
    }

    @Test
    public void testWrap_StringInt_4() {
        assertEquals("", WordUtils.wrap("", -1));
    }

    @Test
    public void testWrap_StringInt_5_testMerged_5() {
        final String systemNewLine = System.lineSeparator();
        String input = "Here is one line of text that is going to be wrapped after 20 columns.";
        String expected = "Here is one line of" + systemNewLine + "text that is going" + systemNewLine + "to be wrapped after" + systemNewLine + "20 columns.";
        assertEquals(expected, WordUtils.wrap(input, 20));
        input = "Click here to jump to the commons website - https://commons.apache.org";
        expected = "Click here to jump" + systemNewLine + "to the commons" + systemNewLine + "website -" + systemNewLine + "https://commons.apache.org";
        input = "Click here, https://commons.apache.org, to jump to the commons website";
        expected = "Click here," + systemNewLine + "https://commons.apache.org," + systemNewLine + "to jump to the" + systemNewLine + "commons website";
        input = "word1             word2                        word3";
        expected = "word1  " + systemNewLine + "word2  " + systemNewLine + "word3";
        assertEquals(expected, WordUtils.wrap(input, 7));
    }

    @Test
    public void testWrap_StringIntStringBoolean_1() {
        assertNull(WordUtils.wrap(null, 20, "\n", false));
    }

    @Test
    public void testWrap_StringIntStringBoolean_2() {
        assertNull(WordUtils.wrap(null, 20, "\n", true));
    }

    @Test
    public void testWrap_StringIntStringBoolean_3() {
        assertNull(WordUtils.wrap(null, 20, null, true));
    }

    @Test
    public void testWrap_StringIntStringBoolean_4() {
        assertNull(WordUtils.wrap(null, 20, null, false));
    }

    @Test
    public void testWrap_StringIntStringBoolean_5() {
        assertNull(WordUtils.wrap(null, -1, null, true));
    }

    @Test
    public void testWrap_StringIntStringBoolean_6() {
        assertNull(WordUtils.wrap(null, -1, null, false));
    }

    @Test
    public void testWrap_StringIntStringBoolean_7() {
        assertEquals("", WordUtils.wrap("", 20, "\n", false));
    }

    @Test
    public void testWrap_StringIntStringBoolean_8() {
        assertEquals("", WordUtils.wrap("", 20, "\n", true));
    }

    @Test
    public void testWrap_StringIntStringBoolean_9() {
        assertEquals("", WordUtils.wrap("", 20, null, false));
    }

    @Test
    public void testWrap_StringIntStringBoolean_10() {
        assertEquals("", WordUtils.wrap("", 20, null, true));
    }

    @Test
    public void testWrap_StringIntStringBoolean_11() {
        assertEquals("", WordUtils.wrap("", -1, null, false));
    }

    @Test
    public void testWrap_StringIntStringBoolean_12() {
        assertEquals("", WordUtils.wrap("", -1, null, true));
    }

    @Test
    public void testWrap_StringIntStringBoolean_13_testMerged_13() {
        String input = "Here is one line of text that is going to be wrapped after 20 columns.";
        String expected = "Here is one line of\ntext that is going\nto be wrapped after\n20 columns.";
        assertEquals(expected, WordUtils.wrap(input, 20, "\n", false));
        assertEquals(expected, WordUtils.wrap(input, 20, "\n", true));
        input = "Here is one line of text that is going to be wrapped after 20 columns.";
        expected = "Here is one line of<br />text that is going<br />to be wrapped after<br />20 columns.";
        assertEquals(expected, WordUtils.wrap(input, 20, "<br />", false));
        assertEquals(expected, WordUtils.wrap(input, 20, "<br />", true));
        input = "Here is one line";
        expected = "Here\nis one\nline";
        assertEquals(expected, WordUtils.wrap(input, 6, "\n", false));
        expected = "Here\nis\none\nline";
        assertEquals(expected, WordUtils.wrap(input, 2, "\n", false));
        assertEquals(expected, WordUtils.wrap(input, -1, "\n", false));
        final String systemNewLine = System.lineSeparator();
        expected = "Here is one line of" + systemNewLine + "text that is going" + systemNewLine + "to be wrapped after" + systemNewLine + "20 columns.";
        assertEquals(expected, WordUtils.wrap(input, 20, null, false));
        assertEquals(expected, WordUtils.wrap(input, 20, null, true));
    }
}
