package org.apache.commons.text;

import static org.apache.commons.text.StringEscapeUtils.escapeXSI;
import static org.apache.commons.text.StringEscapeUtils.unescapeXSI;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;

public class StringEscapeUtilsTest_Purified {

    private static final String FOO = "foo";

    private static final String[][] HTML_ESCAPES = { { "no escaping", "plain text", "plain text" }, { "no escaping", "plain text", "plain text" }, { "empty string", "", "" }, { "null", null, null }, { "ampersand", "bread &amp; butter", "bread & butter" }, { "quotes", "&quot;bread&quot; &amp; butter", "\"bread\" & butter" }, { "final character only", "greater than &gt;", "greater than >" }, { "first character only", "&lt; less than", "< less than" }, { "apostrophe", "Huntington's chorea", "Huntington's chorea" }, { "languages", "English,Fran&ccedil;ais,\u65E5\u672C\u8A9E (nihongo)", "English,Fran\u00E7ais,\u65E5\u672C\u8A9E (nihongo)" }, { "8-bit ascii shouldn't number-escape", "\u0080\u009F", "\u0080\u009F" } };

    private void assertEscapeJava(final String escaped, final String original) throws IOException {
        assertEscapeJava(escaped, original, null);
    }

    private void assertEscapeJava(final String expected, final String original, String message) throws IOException {
        final String converted = StringEscapeUtils.escapeJava(original);
        message = "escapeJava(String) failed" + (message == null ? "" : ": " + message);
        assertEquals(expected, converted, message);
        final StringWriter writer = new StringWriter();
        StringEscapeUtils.ESCAPE_JAVA.translate(original, writer);
        assertEquals(expected, writer.toString());
    }

    private void assertUnescapeJava(final String unescaped, final String original) throws IOException {
        assertUnescapeJava(unescaped, original, null);
    }

    private void assertUnescapeJava(final String unescaped, final String original, final String message) throws IOException {
        final String actual = StringEscapeUtils.unescapeJava(original);
        assertEquals(unescaped, actual, "unescape(String) failed" + (message == null ? "" : ": " + message) + ": expected '" + StringEscapeUtils.escapeJava(unescaped) + "' actual '" + StringEscapeUtils.escapeJava(actual) + "'");
        final StringWriter writer = new StringWriter();
        StringEscapeUtils.UNESCAPE_JAVA.translate(original, writer);
        assertEquals(unescaped, writer.toString());
    }

    private void checkCsvEscapeWriter(final String expected, final String value) throws IOException {
        final StringWriter writer = new StringWriter();
        StringEscapeUtils.ESCAPE_CSV.translate(value, writer);
        assertEquals(expected, writer.toString());
    }

    private void checkCsvUnescapeWriter(final String expected, final String value) throws IOException {
        final StringWriter writer = new StringWriter();
        StringEscapeUtils.UNESCAPE_CSV.translate(value, writer);
        assertEquals(expected, writer.toString());
    }

    @Test
    public void testConstructor_1() {
        assertNotNull(new StringEscapeUtils());
    }

    @Test
    public void testConstructor_2_testMerged_2() {
        final Constructor<?>[] cons = StringEscapeUtils.class.getDeclaredConstructors();
        assertEquals(1, cons.length);
        assertTrue(Modifier.isPublic(cons[0].getModifiers()));
    }

    @Test
    public void testConstructor_4() {
        assertTrue(Modifier.isPublic(StringEscapeUtils.class.getModifiers()));
    }

    @Test
    public void testConstructor_5() {
        assertFalse(Modifier.isFinal(StringEscapeUtils.class.getModifiers()));
    }

    @Test
    public void testEscapeCsvString_1() {
        assertEquals("foo.bar", StringEscapeUtils.escapeCsv("foo.bar"));
    }

    @Test
    public void testEscapeCsvString_2() {
        assertEquals("\"foo,bar\"", StringEscapeUtils.escapeCsv("foo,bar"));
    }

    @Test
    public void testEscapeCsvString_3() {
        assertEquals("\"foo\nbar\"", StringEscapeUtils.escapeCsv("foo\nbar"));
    }

    @Test
    public void testEscapeCsvString_4() {
        assertEquals("\"foo\rbar\"", StringEscapeUtils.escapeCsv("foo\rbar"));
    }

    @Test
    public void testEscapeCsvString_5() {
        assertEquals("\"foo\"\"bar\"", StringEscapeUtils.escapeCsv("foo\"bar"));
    }

    @Test
    public void testEscapeCsvString_6() {
        assertEquals("foo\uD84C\uDFB4bar", StringEscapeUtils.escapeCsv("foo\uD84C\uDFB4bar"));
    }

    @Test
    public void testEscapeCsvString_7() {
        assertEquals("", StringEscapeUtils.escapeCsv(""));
    }

    @Test
    public void testEscapeCsvString_8() {
        assertNull(StringEscapeUtils.escapeCsv(null));
    }

    @Test
    public void testEscapeHtmlThree_1() {
        assertNull(StringEscapeUtils.escapeHtml3(null));
    }

    @Test
    public void testEscapeHtmlThree_2() {
        assertEquals("a", StringEscapeUtils.escapeHtml3("a"));
    }

    @Test
    public void testEscapeHtmlThree_3() {
        assertEquals("&lt;b&gt;a", StringEscapeUtils.escapeHtml3("<b>a"));
    }

    @Test
    public void testEscapeHtmlVersions_1() {
        assertEquals("&Beta;", StringEscapeUtils.escapeHtml4("\u0392"));
    }

    @Test
    public void testEscapeHtmlVersions_2() {
        assertEquals("\u0392", StringEscapeUtils.unescapeHtml4("&Beta;"));
    }

    @Test
    public void testEscapeXml10_1() {
        assertEquals("a&lt;b&gt;c&quot;d&apos;e&amp;f", StringEscapeUtils.escapeXml10("a<b>c\"d'e&f"));
    }

    @Test
    public void testEscapeXml10_2() {
        assertEquals("a\tb\rc\nd", StringEscapeUtils.escapeXml10("a\tb\rc\nd"), "XML 1.0 should not escape \t \n \r");
    }

    @Test
    public void testEscapeXml10_3() {
        assertEquals("ab", StringEscapeUtils.escapeXml10("a\u0000\u0001\u0008\u000b\u000c\u000e\u001fb"), "XML 1.0 should omit most #x0-x8 | #xb | #xc | #xe-#x19");
    }

    @Test
    public void testEscapeXml10_4() {
        assertEquals("a\ud7ff  \ue000b", StringEscapeUtils.escapeXml10("a\ud7ff\ud800 \udfff \ue000b"), "XML 1.0 should omit #xd800-#xdfff");
    }

    @Test
    public void testEscapeXml10_5() {
        assertEquals("a\ufffdb", StringEscapeUtils.escapeXml10("a\ufffd\ufffe\uffffb"), "XML 1.0 should omit #xfffe | #xffff");
    }

    @Test
    public void testEscapeXml10_6() {
        assertEquals("a\u007e&#127;&#132;\u0085&#134;&#159;\u00a0b", StringEscapeUtils.escapeXml10("a\u007e\u007f\u0084\u0085\u0086\u009f\u00a0b"), "XML 1.0 should escape #x7f-#x84 | #x86 - #x9f, for XML 1.1 compatibility");
    }

    @Test
    public void testEscapeXml11_1() {
        assertEquals("a&lt;b&gt;c&quot;d&apos;e&amp;f", StringEscapeUtils.escapeXml11("a<b>c\"d'e&f"));
    }

    @Test
    public void testEscapeXml11_2() {
        assertEquals("a\tb\rc\nd", StringEscapeUtils.escapeXml11("a\tb\rc\nd"), "XML 1.1 should not escape \t \n \r");
    }

    @Test
    public void testEscapeXml11_3() {
        assertEquals("ab", StringEscapeUtils.escapeXml11("a\u0000b"), "XML 1.1 should omit #x0");
    }

    @Test
    public void testEscapeXml11_4() {
        assertEquals("a&#1;&#8;&#11;&#12;&#14;&#31;b", StringEscapeUtils.escapeXml11("a\u0001\u0008\u000b\u000c\u000e\u001fb"), "XML 1.1 should escape #x1-x8 | #xb | #xc | #xe-#x19");
    }

    @Test
    public void testEscapeXml11_5() {
        assertEquals("a\u007e&#127;&#132;\u0085&#134;&#159;\u00a0b", StringEscapeUtils.escapeXml11("a\u007e\u007f\u0084\u0085\u0086\u009f\u00a0b"), "XML 1.1 should escape #x7F-#x84 | #x86-#x9F");
    }

    @Test
    public void testEscapeXml11_6() {
        assertEquals("a\ud7ff  \ue000b", StringEscapeUtils.escapeXml11("a\ud7ff\ud800 \udfff \ue000b"), "XML 1.1 should omit #xd800-#xdfff");
    }

    @Test
    public void testEscapeXml11_7() {
        assertEquals("a\ufffdb", StringEscapeUtils.escapeXml11("a\ufffd\ufffe\uffffb"), "XML 1.1 should omit #xfffe | #xffff");
    }

    @Test
    public void testEscapeXSI_1() {
        assertNull(null, escapeXSI(null));
    }

    @Test
    public void testEscapeXSI_2() {
        assertEquals("He\\ didn\\'t\\ say,\\ \\\"Stop!\\\"", escapeXSI("He didn't say, \"Stop!\""));
    }

    @Test
    public void testEscapeXSI_3() {
        assertEquals("\\\\", escapeXSI("\\"));
    }

    @Test
    public void testEscapeXSI_4() {
        assertEquals("", escapeXSI("\n"));
    }

    @Test
    public void testStandaloneAmphersand_1() {
        assertEquals("<P&O>", StringEscapeUtils.unescapeHtml4("&lt;P&O&gt;"));
    }

    @Test
    public void testStandaloneAmphersand_2() {
        assertEquals("test & <", StringEscapeUtils.unescapeHtml4("test & &lt;"));
    }

    @Test
    public void testStandaloneAmphersand_3() {
        assertEquals("<P&O>", StringEscapeUtils.unescapeXml("&lt;P&O&gt;"));
    }

    @Test
    public void testStandaloneAmphersand_4() {
        assertEquals("test & <", StringEscapeUtils.unescapeXml("test & &lt;"));
    }

    @Test
    public void testUnescapeCsvString_1() {
        assertEquals("foo.bar", StringEscapeUtils.unescapeCsv("foo.bar"));
    }

    @Test
    public void testUnescapeCsvString_2() {
        assertEquals("foo,bar", StringEscapeUtils.unescapeCsv("\"foo,bar\""));
    }

    @Test
    public void testUnescapeCsvString_3() {
        assertEquals("foo\nbar", StringEscapeUtils.unescapeCsv("\"foo\nbar\""));
    }

    @Test
    public void testUnescapeCsvString_4() {
        assertEquals("foo\rbar", StringEscapeUtils.unescapeCsv("\"foo\rbar\""));
    }

    @Test
    public void testUnescapeCsvString_5() {
        assertEquals("foo\"bar", StringEscapeUtils.unescapeCsv("\"foo\"\"bar\""));
    }

    @Test
    public void testUnescapeCsvString_6() {
        assertEquals("foo\uD84C\uDFB4bar", StringEscapeUtils.unescapeCsv("foo\uD84C\uDFB4bar"));
    }

    @Test
    public void testUnescapeCsvString_7() {
        assertEquals("", StringEscapeUtils.unescapeCsv(""));
    }

    @Test
    public void testUnescapeCsvString_8() {
        assertNull(StringEscapeUtils.unescapeCsv(null));
    }

    @Test
    public void testUnescapeCsvString_9() {
        assertEquals("foo.bar", StringEscapeUtils.unescapeCsv("\"foo.bar\""));
    }

    @Test
    public void testUnescapeEcmaScript_1() {
        assertNull(StringEscapeUtils.unescapeEcmaScript(null));
    }

    @Test
    public void testUnescapeEcmaScript_2() {
        assertEquals("8lvc1u+6B#-I", StringEscapeUtils.unescapeEcmaScript("8lvc1u+6B#-I"));
    }

    @Test
    public void testUnescapeEcmaScript_3() {
        assertEquals("<script src=\"build/main.bundle.js\"></script>", StringEscapeUtils.unescapeEcmaScript("<script src=\"build/main.bundle.js\"></script>"));
    }

    @Test
    public void testUnescapeEcmaScript_4() {
        assertEquals("<script src=\"build/main.bundle.js\"></script>>", StringEscapeUtils.unescapeEcmaScript("<script src=\"build/main.bundle.js\"></script>>"));
    }

    @Test
    public void testUnescapeJson_1() {
        assertEquals("", StringEscapeUtils.unescapeJson(""));
    }

    @Test
    public void testUnescapeJson_2() {
        assertEquals(" ", StringEscapeUtils.unescapeJson(" "));
    }

    @Test
    public void testUnescapeJson_3() {
        assertEquals("a:b", StringEscapeUtils.unescapeJson("a:b"));
    }

    @Test
    public void testUnescapeJson_4() {
        final String jsonString = "{\"age\":100,\"name\":\"kyong.com\n\",\"messages\":[\"msg 1\",\"msg 2\",\"msg 3\"]}";
        assertEquals(jsonString, StringEscapeUtils.unescapeJson(jsonString));
    }

    @Test
    public void testUnescapeXmlSupplementaryCharacters_1() {
        assertEquals("\uD84C\uDFB4", StringEscapeUtils.unescapeXml("&#144308;"), "Supplementary character must be represented using a single escape");
    }

    @Test
    public void testUnescapeXmlSupplementaryCharacters_2() {
        assertEquals("a b c \uD84C\uDFB4", StringEscapeUtils.unescapeXml("a b c &#144308;"), "Supplementary characters mixed with basic characters should be decoded correctly");
    }

    @Test
    public void testUnscapeXSI_1() {
        assertNull(null, unescapeXSI(null));
    }

    @Test
    public void testUnscapeXSI_2() {
        assertEquals("\"", unescapeXSI("\\\""));
    }

    @Test
    public void testUnscapeXSI_3() {
        assertEquals("He didn't say, \"Stop!\"", unescapeXSI("He\\ didn\\'t\\ say,\\ \\\"Stop!\\\""));
    }

    @Test
    public void testUnscapeXSI_4() {
        assertEquals("\\", unescapeXSI("\\\\"));
    }

    @Test
    public void testUnscapeXSI_5() {
        assertEquals("", unescapeXSI("\\"));
    }
}
