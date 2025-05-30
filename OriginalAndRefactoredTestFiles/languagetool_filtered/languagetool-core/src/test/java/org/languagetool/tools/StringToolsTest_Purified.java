package org.languagetool.tools;

import org.junit.Test;
import org.languagetool.FakeLanguage;
import org.languagetool.Language;
import org.languagetool.TestTools;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

public class StringToolsTest_Purified {

    @Test
    public void testToId_1() {
        assertEquals("SS", "ß".toUpperCase());
    }

    @Test
    public void testToId_2_testMerged_2() {
        FakeLanguage german = new FakeLanguage("de");
        assertEquals("BL_Q_A__UEBEL_OEAESSOE", StringTools.toId(" Bl'a (übel öäßÖ ", german));
        assertEquals("FOOÓÉÉ", StringTools.toId("fooóéÉ", german));
    }

    @Test
    public void testToId_3() {
        FakeLanguage portuguese = new FakeLanguage("pt");
        assertEquals("ÜSS_ÇÃÔ_OÙ_Ñ", StringTools.toId("üß çãÔ-où Ñ", portuguese));
    }

    @Test
    public void testIsAllUppercase_1() {
        assertTrue(StringTools.isAllUppercase("A"));
    }

    @Test
    public void testIsAllUppercase_2() {
        assertTrue(StringTools.isAllUppercase("ABC"));
    }

    @Test
    public void testIsAllUppercase_3() {
        assertTrue(StringTools.isAllUppercase("ASV-EDR"));
    }

    @Test
    public void testIsAllUppercase_4() {
        assertTrue(StringTools.isAllUppercase("ASV-ÖÄÜ"));
    }

    @Test
    public void testIsAllUppercase_5() {
        assertTrue(StringTools.isAllUppercase(""));
    }

    @Test
    public void testIsAllUppercase_6() {
        assertFalse(StringTools.isAllUppercase("ß"));
    }

    @Test
    public void testIsAllUppercase_7() {
        assertFalse(StringTools.isAllUppercase("AAAAAAAAAAAAq"));
    }

    @Test
    public void testIsAllUppercase_8() {
        assertFalse(StringTools.isAllUppercase("a"));
    }

    @Test
    public void testIsAllUppercase_9() {
        assertFalse(StringTools.isAllUppercase("abc"));
    }

    @Test
    public void testIsMixedCase_1() {
        assertTrue(StringTools.isMixedCase("AbC"));
    }

    @Test
    public void testIsMixedCase_2() {
        assertTrue(StringTools.isMixedCase("MixedCase"));
    }

    @Test
    public void testIsMixedCase_3() {
        assertTrue(StringTools.isMixedCase("iPod"));
    }

    @Test
    public void testIsMixedCase_4() {
        assertTrue(StringTools.isMixedCase("AbCdE"));
    }

    @Test
    public void testIsMixedCase_5() {
        assertFalse(StringTools.isMixedCase(""));
    }

    @Test
    public void testIsMixedCase_6() {
        assertFalse(StringTools.isMixedCase("ABC"));
    }

    @Test
    public void testIsMixedCase_7() {
        assertFalse(StringTools.isMixedCase("abc"));
    }

    @Test
    public void testIsMixedCase_8() {
        assertFalse(StringTools.isMixedCase("!"));
    }

    @Test
    public void testIsMixedCase_9() {
        assertFalse(StringTools.isMixedCase("Word"));
    }

    @Test
    public void testIsCapitalizedWord_1() {
        assertTrue(StringTools.isCapitalizedWord("Abc"));
    }

    @Test
    public void testIsCapitalizedWord_2() {
        assertTrue(StringTools.isCapitalizedWord("Uppercase"));
    }

    @Test
    public void testIsCapitalizedWord_3() {
        assertTrue(StringTools.isCapitalizedWord("Ipod"));
    }

    @Test
    public void testIsCapitalizedWord_4() {
        assertFalse(StringTools.isCapitalizedWord(""));
    }

    @Test
    public void testIsCapitalizedWord_5() {
        assertFalse(StringTools.isCapitalizedWord("ABC"));
    }

    @Test
    public void testIsCapitalizedWord_6() {
        assertFalse(StringTools.isCapitalizedWord("abc"));
    }

    @Test
    public void testIsCapitalizedWord_7() {
        assertFalse(StringTools.isCapitalizedWord("!"));
    }

    @Test
    public void testIsCapitalizedWord_8() {
        assertFalse(StringTools.isCapitalizedWord("wOrD"));
    }

    @Test
    public void testStartsWithUppercase_1() {
        assertTrue(StringTools.startsWithUppercase("A"));
    }

    @Test
    public void testStartsWithUppercase_2() {
        assertTrue(StringTools.startsWithUppercase("ÄÖ"));
    }

    @Test
    public void testStartsWithUppercase_3() {
        assertFalse(StringTools.startsWithUppercase(""));
    }

    @Test
    public void testStartsWithUppercase_4() {
        assertFalse(StringTools.startsWithUppercase("ß"));
    }

    @Test
    public void testStartsWithUppercase_5() {
        assertFalse(StringTools.startsWithUppercase("-"));
    }

    @Test
    public void testUppercaseFirstChar_1() {
        assertEquals(null, StringTools.uppercaseFirstChar(null));
    }

    @Test
    public void testUppercaseFirstChar_2() {
        assertEquals("", StringTools.uppercaseFirstChar(""));
    }

    @Test
    public void testUppercaseFirstChar_3() {
        assertEquals("A", StringTools.uppercaseFirstChar("A"));
    }

    @Test
    public void testUppercaseFirstChar_4() {
        assertEquals("Öäü", StringTools.uppercaseFirstChar("öäü"));
    }

    @Test
    public void testUppercaseFirstChar_5() {
        assertEquals("ßa", StringTools.uppercaseFirstChar("ßa"));
    }

    @Test
    public void testUppercaseFirstChar_6() {
        assertEquals("'Test'", StringTools.uppercaseFirstChar("'test'"));
    }

    @Test
    public void testUppercaseFirstChar_7() {
        assertEquals("''Test", StringTools.uppercaseFirstChar("''test"));
    }

    @Test
    public void testUppercaseFirstChar_8() {
        assertEquals("''T", StringTools.uppercaseFirstChar("''t"));
    }

    @Test
    public void testUppercaseFirstChar_9() {
        assertEquals("'''", StringTools.uppercaseFirstChar("'''"));
    }

    @Test
    public void testLowercaseFirstChar_1() {
        assertEquals(null, StringTools.lowercaseFirstChar(null));
    }

    @Test
    public void testLowercaseFirstChar_2() {
        assertEquals("", StringTools.lowercaseFirstChar(""));
    }

    @Test
    public void testLowercaseFirstChar_3() {
        assertEquals("a", StringTools.lowercaseFirstChar("A"));
    }

    @Test
    public void testLowercaseFirstChar_4() {
        assertEquals("öäü", StringTools.lowercaseFirstChar("Öäü"));
    }

    @Test
    public void testLowercaseFirstChar_5() {
        assertEquals("ßa", StringTools.lowercaseFirstChar("ßa"));
    }

    @Test
    public void testLowercaseFirstChar_6() {
        assertEquals("'test'", StringTools.lowercaseFirstChar("'Test'"));
    }

    @Test
    public void testLowercaseFirstChar_7() {
        assertEquals("''test", StringTools.lowercaseFirstChar("''Test"));
    }

    @Test
    public void testLowercaseFirstChar_8() {
        assertEquals("''t", StringTools.lowercaseFirstChar("''T"));
    }

    @Test
    public void testLowercaseFirstChar_9() {
        assertEquals("'''", StringTools.lowercaseFirstChar("'''"));
    }

    @Test
    public void testEscapeXMLandHTML_1() {
        assertEquals("foo bar", StringTools.escapeXML("foo bar"));
    }

    @Test
    public void testEscapeXMLandHTML_2() {
        assertEquals("!ä&quot;&lt;&gt;&amp;&amp;", StringTools.escapeXML("!ä\"<>&&"));
    }

    @Test
    public void testEscapeXMLandHTML_3() {
        assertEquals("!ä&quot;&lt;&gt;&amp;&amp;", StringTools.escapeHTML("!ä\"<>&&"));
    }

    @Test
    public void testAddSpace_1_testMerged_1() {
        Language demoLanguage = TestTools.getDemoLanguage();
        assertEquals(" ", StringTools.addSpace("word", demoLanguage));
        assertEquals("", StringTools.addSpace(",", demoLanguage));
    }

    @Test
    public void testAddSpace_5() {
        assertEquals("", StringTools.addSpace(".", new FakeLanguage("fr")));
    }

    @Test
    public void testAddSpace_6() {
        assertEquals("", StringTools.addSpace(".", new FakeLanguage("de")));
    }

    @Test
    public void testAddSpace_7() {
        assertEquals(" ", StringTools.addSpace("!", new FakeLanguage("fr")));
    }

    @Test
    public void testAddSpace_8() {
        assertEquals("", StringTools.addSpace("!", new FakeLanguage("de")));
    }

    @Test
    public void testIsWhitespace_1() {
        assertEquals(true, StringTools.isWhitespace("\uFEFF"));
    }

    @Test
    public void testIsWhitespace_2() {
        assertEquals(true, StringTools.isWhitespace("  "));
    }

    @Test
    public void testIsWhitespace_3() {
        assertEquals(true, StringTools.isWhitespace("\t"));
    }

    @Test
    public void testIsWhitespace_4() {
        assertEquals(true, StringTools.isWhitespace("\u2002"));
    }

    @Test
    public void testIsWhitespace_5() {
        assertEquals(true, StringTools.isWhitespace("\u00a0"));
    }

    @Test
    public void testIsWhitespace_6() {
        assertEquals(false, StringTools.isWhitespace("abc"));
    }

    @Test
    public void testIsWhitespace_7() {
        assertEquals(false, StringTools.isWhitespace("\\u02"));
    }

    @Test
    public void testIsWhitespace_8() {
        assertEquals(false, StringTools.isWhitespace("\u0001"));
    }

    @Test
    public void testIsWhitespace_9() {
        assertEquals(true, StringTools.isWhitespace("\u202F"));
    }

    @Test
    public void testIsPositiveNumber_1() {
        assertEquals(true, StringTools.isPositiveNumber('3'));
    }

    @Test
    public void testIsPositiveNumber_2() {
        assertEquals(false, StringTools.isPositiveNumber('a'));
    }

    @Test
    public void testIsEmpty_1() {
        assertEquals(true, StringTools.isEmpty(""));
    }

    @Test
    public void testIsEmpty_2() {
        assertEquals(true, StringTools.isEmpty(null));
    }

    @Test
    public void testIsEmpty_3() {
        assertEquals(false, StringTools.isEmpty("a"));
    }

    @Test
    public void testFilterXML_1() {
        assertEquals("test", StringTools.filterXML("test"));
    }

    @Test
    public void testFilterXML_2() {
        assertEquals("<<test>>", StringTools.filterXML("<<test>>"));
    }

    @Test
    public void testFilterXML_3() {
        assertEquals("test", StringTools.filterXML("<b>test</b>"));
    }

    @Test
    public void testFilterXML_4() {
        assertEquals("A sentence with a test", StringTools.filterXML("A sentence with a <em>test</em>"));
    }

    @Test
    public void testAsString_1() {
        assertNull(StringTools.asString(null));
    }

    @Test
    public void testAsString_2() {
        assertEquals("foo!", "foo!");
    }

    @Test
    public void testIsCamelCase_1() {
        assertFalse(StringTools.isCamelCase("abc"));
    }

    @Test
    public void testIsCamelCase_2() {
        assertFalse(StringTools.isCamelCase("ABC"));
    }

    @Test
    public void testIsCamelCase_3() {
        assertTrue(StringTools.isCamelCase("iSomething"));
    }

    @Test
    public void testIsCamelCase_4() {
        assertTrue(StringTools.isCamelCase("iSomeThing"));
    }

    @Test
    public void testIsCamelCase_5() {
        assertTrue(StringTools.isCamelCase("mRNA"));
    }

    @Test
    public void testIsCamelCase_6() {
        assertTrue(StringTools.isCamelCase("microRNA"));
    }

    @Test
    public void testIsCamelCase_7() {
        assertTrue(StringTools.isCamelCase("microSomething"));
    }

    @Test
    public void testIsCamelCase_8() {
        assertTrue(StringTools.isCamelCase("iSomeTHING"));
    }

    @Test
    public void testStringForSpeller_1() {
        String arabicChars = "\u064B \u064C \u064D \u064E \u064F \u0650 \u0651 \u0652 \u0670";
        assertTrue(StringTools.stringForSpeller(arabicChars).equals(arabicChars));
    }

    @Test
    public void testStringForSpeller_2() {
        String russianChars = "а б в г д е ё ж з и й к л м н о п р с т у ф х ц ч ш щ ъ ы ь э ю я";
        assertTrue(StringTools.stringForSpeller(russianChars).equals(russianChars));
    }

    @Test
    public void testStringForSpeller_3_testMerged_3() {
        String emojiStr = "🧡 Prueva";
        assertTrue(StringTools.stringForSpeller(emojiStr).equals("   Prueva"));
        emojiStr = "\uD83E\uDDE1\uD83D\uDEB4\uD83C\uDFFD♂\uFE0F Prueva";
        assertTrue(StringTools.stringForSpeller(emojiStr).equals("         Prueva"));
    }

    @Test
    public void testTitlecaseGlobal_1() {
        assertEquals("The Lord of the Rings", StringTools.titlecaseGlobal("the lord of the rings"));
    }

    @Test
    public void testTitlecaseGlobal_2() {
        assertEquals("Rhythm and Blues", StringTools.titlecaseGlobal("rhythm And blues"));
    }

    @Test
    public void testTitlecaseGlobal_3() {
        assertEquals("Memória de Leitura", StringTools.titlecaseGlobal("memória de leitura"));
    }

    @Test
    public void testTitlecaseGlobal_4() {
        assertEquals("Fond du Lac", StringTools.titlecaseGlobal("fond du lac"));
    }

    @Test
    public void testTitlecaseGlobal_5() {
        assertEquals("El Niño de las Islas", StringTools.titlecaseGlobal("el niño de Las islas"));
    }

    @Test
    public void testAllStartWithLowercase_1() {
        assertTrue(StringTools.allStartWithLowercase("the lord of the rings"));
    }

    @Test
    public void testAllStartWithLowercase_2() {
        assertFalse(StringTools.allStartWithLowercase("the Fellowship of the Ring"));
    }

    @Test
    public void testAllStartWithLowercase_3() {
        assertTrue(StringTools.allStartWithLowercase("bilbo"));
    }

    @Test
    public void testAllStartWithLowercase_4() {
        assertFalse(StringTools.allStartWithLowercase("Baggins"));
    }
}
