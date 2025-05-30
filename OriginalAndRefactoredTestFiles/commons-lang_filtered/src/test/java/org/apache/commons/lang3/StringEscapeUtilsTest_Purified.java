package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.apache.commons.lang3.text.translate.CharSequenceTranslator;
import org.apache.commons.lang3.text.translate.NumericEntityEscaper;
import org.junit.jupiter.api.Test;

@Deprecated
public class StringEscapeUtilsTest_Purified extends AbstractLangTest {

    private static final String FOO = "foo";

    private static final String[][] HTML_ESCAPES = { { "no escaping", "plain text", "plain text" }, { "no escaping", "plain text", "plain text" }, { "empty string", "", "" }, { "null", null, null }, { "ampersand", "bread &amp; butter", "bread & butter" }, { "quotes", "&quot;bread&quot; &amp; butter", "\"bread\" & butter" }, { "final character only", "greater than &gt;", "greater than >" }, { "first character only", "&lt; less than", "< less than" }, { "apostrophe", "Huntington's chorea", "Huntington's chorea" }, { "languages", "English,Fran&ccedil;ais,\u65E5\u672C\u8A9E (nihongo)", "English,Fran\u00E7ais,\u65E5\u672C\u8A9E (nihongo)" }, { "8-bit ascii shouldn't number-escape", "\u0080\u009F", "\u0080\u009F" } };

    private void assertEscapeJava(final String escaped, final String original) throws IOException {
        assertEscapeJava(null, escaped, original);
    }

    private void assertEscapeJava(String message, final String expected, final String original) throws IOException {
        final String converted = StringEscapeUtils.escapeJava(original);
        message = "escapeJava(String) failed" + (message == null ? "" : ": " + message);
        assertEquals(expected, converted, message);
        final StringWriter writer = new StringWriter();
        StringEscapeUtils.ESCAPE_JAVA.translate(original, writer);
        assertEquals(expected, writer.toString());
    }

    private void assertUnescapeJava(final String unescaped, final String original) throws IOException {
        assertUnescapeJava(null, unescaped, original);
    }

    private void assertUnescapeJava(final String message, final String unescaped, final String original) throws IOException {
        final String expected = unescaped;
        final String actual = StringEscapeUtils.unescapeJava(original);
        assertEquals(expected, actual, "unescape(String) failed" + (message == null ? "" : ": " + message) + ": expected '" + StringEscapeUtils.escapeJava(expected) + "' actual '" + StringEscapeUtils.escapeJava(actual) + "'");
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
    public void testEscapeHtmlVersions_1() {
        assertEquals("&Beta;", StringEscapeUtils.escapeHtml4("\u0392"));
    }

    @Test
    public void testEscapeHtmlVersions_2() {
        assertEquals("\u0392", StringEscapeUtils.unescapeHtml4("&Beta;"));
    }

    @Test
    public void testEscapeXml_1() throws Exception {
        assertEquals("&lt;abc&gt;", StringEscapeUtils.escapeXml("<abc>"));
    }

    @Test
    public void testEscapeXml_2() throws Exception {
        assertEquals("<abc>", StringEscapeUtils.unescapeXml("&lt;abc&gt;"));
    }

    @Test
    public void testEscapeXml_3() throws Exception {
        assertEquals("\u00A1", StringEscapeUtils.escapeXml("\u00A1"), "XML should not escape >0x7f values");
    }

    @Test
    public void testEscapeXml_4() throws Exception {
        assertEquals("\u00A0", StringEscapeUtils.unescapeXml("&#160;"), "XML should be able to unescape >0x7f values");
    }

    @Test
    public void testEscapeXml_5() throws Exception {
        assertEquals("\u00A0", StringEscapeUtils.unescapeXml("&#0160;"), "XML should be able to unescape >0x7f values with one leading 0");
    }

    @Test
    public void testEscapeXml_6() throws Exception {
        assertEquals("\u00A0", StringEscapeUtils.unescapeXml("&#00160;"), "XML should be able to unescape >0x7f values with two leading 0s");
    }

    @Test
    public void testEscapeXml_7() throws Exception {
        assertEquals("\u00A0", StringEscapeUtils.unescapeXml("&#000160;"), "XML should be able to unescape >0x7f values with three leading 0s");
    }

    @Test
    public void testEscapeXml_8() throws Exception {
        assertEquals("ain't", StringEscapeUtils.unescapeXml("ain&apos;t"));
    }

    @Test
    public void testEscapeXml_9() throws Exception {
        assertEquals("ain&apos;t", StringEscapeUtils.escapeXml("ain't"));
    }

    @Test
    public void testEscapeXml_10() throws Exception {
        assertEquals("", StringEscapeUtils.escapeXml(""));
    }

    @Test
    public void testEscapeXml_11() throws Exception {
        assertNull(StringEscapeUtils.escapeXml(null));
    }

    @Test
    public void testEscapeXml_12() throws Exception {
        assertNull(StringEscapeUtils.unescapeXml(null));
    }

    @Test
    public void testEscapeXml_13_testMerged_13() throws Exception {
        StringWriter sw = new StringWriter();
        StringEscapeUtils.ESCAPE_XML.translate("<abc>", sw);
        assertEquals("&lt;abc&gt;", sw.toString(), "XML was escaped incorrectly");
        sw = new StringWriter();
        StringEscapeUtils.UNESCAPE_XML.translate("&lt;abc&gt;", sw);
        assertEquals("<abc>", sw.toString(), "XML was unescaped incorrectly");
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
        assertEquals("\"foo.bar\"", StringEscapeUtils.unescapeCsv("\"foo.bar\""));
    }

    @Test
    public void testUnescapeXmlSupplementaryCharacters_1() {
        assertEquals("\uD84C\uDFB4", StringEscapeUtils.unescapeXml("&#144308;"), "Supplementary character must be represented using a single escape");
    }

    @Test
    public void testUnescapeXmlSupplementaryCharacters_2() {
        assertEquals("a b c \uD84C\uDFB4", StringEscapeUtils.unescapeXml("a b c &#144308;"), "Supplementary characters mixed with basic characters should be decoded correctly");
    }
}
