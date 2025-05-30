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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@Deprecated
public class StringEscapeUtilsTest_Parameterized extends AbstractLangTest {

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
    public void testEscapeCsvString_8() {
        assertNull(StringEscapeUtils.escapeCsv(null));
    }

    @Test
    public void testEscapeHtmlVersions_1() {
        assertEquals("&Beta;", StringEscapeUtils.escapeHtml4("\u0392"));
    }

    @Test
    public void testEscapeXml_3() throws Exception {
        assertEquals("\u00A1", StringEscapeUtils.escapeXml("\u00A1"), "XML should not escape >0x7f values");
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
    public void testEscapeXml11_1() {
        assertEquals("a&lt;b&gt;c&quot;d&apos;e&amp;f", StringEscapeUtils.escapeXml11("a<b>c\"d'e&f"));
    }

    @Test
    public void testUnescapeCsvString_8() {
        assertNull(StringEscapeUtils.unescapeCsv(null));
    }

    @ParameterizedTest
    @MethodSource("Provider_testEscapeCsvString_1to7")
    public void testEscapeCsvString_1to7(String param1, String param2) {
        assertEquals(param1, StringEscapeUtils.escapeCsv(param2));
    }

    static public Stream<Arguments> Provider_testEscapeCsvString_1to7() {
        return Stream.of(arguments("foo.bar", "foo.bar"), arguments("\"foo,bar\"", "foo,bar"), arguments("\"foo\nbar\"", "foo\nbar"), arguments("\"foo\rbar\"", "foo\rbar"), arguments("\"foo\"\"bar\"", "foo\"bar"), arguments("foo\uD84C\uDFB4bar", "foo\uD84C\uDFB4bar"), arguments("", ""));
    }

    @ParameterizedTest
    @MethodSource("Provider_testEscapeHtmlVersions_1to2_2")
    public void testEscapeHtmlVersions_1to2_2(String param1, String param2) {
        assertEquals(param1, StringEscapeUtils.unescapeHtml4(param2));
    }

    static public Stream<Arguments> Provider_testEscapeHtmlVersions_1to2_2() {
        return Stream.of(arguments("\u0392", "&Beta;"), arguments("<P&O>", "&lt;P&O&gt;"), arguments("test & <", "test & &lt;"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testEscapeXml_1_9to10")
    public void testEscapeXml_1_9to10(String param1, String param2) throws Exception {
        assertEquals(param1, StringEscapeUtils.escapeXml(param2));
    }

    static public Stream<Arguments> Provider_testEscapeXml_1_9to10() {
        return Stream.of(arguments("&lt;abc&gt;", "<abc>"), arguments("ain&apos;t", "ain't"), arguments("", ""));
    }

    @ParameterizedTest
    @MethodSource("Provider_testEscapeXml_2to4_8")
    public void testEscapeXml_2to4_8(String param1, String param2) throws Exception {
        assertEquals(param1, StringEscapeUtils.unescapeXml(param2));
    }

    static public Stream<Arguments> Provider_testEscapeXml_2to4_8() {
        return Stream.of(arguments("<abc>", "&lt;abc&gt;"), arguments("ain't", "ain&apos;t"), arguments("<P&O>", "&lt;P&O&gt;"), arguments("test & <", "test & &lt;"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testEscapeXml_1to2_4to7")
    public void testEscapeXml_1to2_4to7(String param1, String param2, String param3) throws Exception {
        assertEquals(param1, StringEscapeUtils.unescapeXml(param3), param2);
    }

    static public Stream<Arguments> Provider_testEscapeXml_1to2_4to7() {
        return Stream.of(arguments("\u00A0", "XML should be able to unescape >0x7f values", "&#160;"), arguments("\u00A0", "XML should be able to unescape >0x7f values with one leading 0", "&#0160;"), arguments("\u00A0", "XML should be able to unescape >0x7f values with two leading 0s", "&#00160;"), arguments("\u00A0", "XML should be able to unescape >0x7f values with three leading 0s", "&#000160;"), arguments("\uD84C\uDFB4", "Supplementary character must be represented using a single escape", "&#144308;"), arguments("a b c \uD84C\uDFB4", "Supplementary characters mixed with basic characters should be decoded correctly", "a b c &#144308;"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testEscapeXml10_2to6")
    public void testEscapeXml10_2to6(String param1, String param2, String param3) {
        assertEquals(param1, StringEscapeUtils.escapeXml10(param3), param2);
    }

    static public Stream<Arguments> Provider_testEscapeXml10_2to6() {
        return Stream.of(arguments("a\tb\rc\nd", "XML 1.0 should not escape \t \n \r", "a\tb\rc\nd"), arguments("ab", "XML 1.0 should omit most #x0-x8 | #xb | #xc | #xe-#x19", "a\u0000\u0001\u0008\u000b\u000c\u000e\u001fb"), arguments("a\ud7ff  \ue000b", "XML 1.0 should omit #xd800-#xdfff", "a\ud7ff\ud800 \udfff \ue000b"), arguments("a\ufffdb", "XML 1.0 should omit #xfffe | #xffff", "a\ufffd\ufffe\uffffb"), arguments("a\u007e&#127;&#132;\u0085&#134;&#159;\u00a0b", "XML 1.0 should escape #x7f-#x84 | #x86 - #x9f, for XML 1.1 compatibility", "a\u007e\u007f\u0084\u0085\u0086\u009f\u00a0b"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testEscapeXml11_2to7")
    public void testEscapeXml11_2to7(String param1, String param2, String param3) {
        assertEquals(param1, StringEscapeUtils.escapeXml11(param3), param2);
    }

    static public Stream<Arguments> Provider_testEscapeXml11_2to7() {
        return Stream.of(arguments("a\tb\rc\nd", "XML 1.1 should not escape \t \n \r", "a\tb\rc\nd"), arguments("ab", "XML 1.1 should omit #x0", "a\u0000b"), arguments("a&#1;&#8;&#11;&#12;&#14;&#31;b", "XML 1.1 should escape #x1-x8 | #xb | #xc | #xe-#x19", "a\u0001\u0008\u000b\u000c\u000e\u001fb"), arguments("a\u007e&#127;&#132;\u0085&#134;&#159;\u00a0b", "XML 1.1 should escape #x7F-#x84 | #x86-#x9F", "a\u007e\u007f\u0084\u0085\u0086\u009f\u00a0b"), arguments("a\ud7ff  \ue000b", "XML 1.1 should omit #xd800-#xdfff", "a\ud7ff\ud800 \udfff \ue000b"), arguments("a\ufffdb", "XML 1.1 should omit #xfffe | #xffff", "a\ufffd\ufffe\uffffb"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testUnescapeCsvString_1to7_9")
    public void testUnescapeCsvString_1to7_9(String param1, String param2) {
        assertEquals(param1, StringEscapeUtils.unescapeCsv(param2));
    }

    static public Stream<Arguments> Provider_testUnescapeCsvString_1to7_9() {
        return Stream.of(arguments("foo.bar", "foo.bar"), arguments("foo,bar", "\"foo,bar\""), arguments("foo\nbar", "\"foo\nbar\""), arguments("foo\rbar", "\"foo\rbar\""), arguments("foo\"bar", "\"foo\"\"bar\""), arguments("foo\uD84C\uDFB4bar", "foo\uD84C\uDFB4bar"), arguments("", ""), arguments("\"foo.bar\"", "\"foo.bar\""));
    }
}
