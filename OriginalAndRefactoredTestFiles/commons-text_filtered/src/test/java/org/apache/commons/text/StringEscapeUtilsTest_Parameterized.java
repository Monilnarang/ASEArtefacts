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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class StringEscapeUtilsTest_Parameterized {

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
    public void testEscapeCsvString_8() {
        assertNull(StringEscapeUtils.escapeCsv(null));
    }

    @Test
    public void testEscapeHtmlThree_1() {
        assertNull(StringEscapeUtils.escapeHtml3(null));
    }

    @Test
    public void testEscapeHtmlVersions_1() {
        assertEquals("&Beta;", StringEscapeUtils.escapeHtml4("\u0392"));
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
    public void testEscapeXSI_1() {
        assertNull(null, escapeXSI(null));
    }

    @Test
    public void testUnescapeCsvString_8() {
        assertNull(StringEscapeUtils.unescapeCsv(null));
    }

    @Test
    public void testUnescapeEcmaScript_1() {
        assertNull(StringEscapeUtils.unescapeEcmaScript(null));
    }

    @Test
    public void testUnescapeJson_4() {
        final String jsonString = "{\"age\":100,\"name\":\"kyong.com\n\",\"messages\":[\"msg 1\",\"msg 2\",\"msg 3\"]}";
        assertEquals(jsonString, StringEscapeUtils.unescapeJson(jsonString));
    }

    @Test
    public void testUnscapeXSI_1() {
        assertNull(null, unescapeXSI(null));
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
    @MethodSource("Provider_testEscapeHtmlThree_2to3")
    public void testEscapeHtmlThree_2to3(String param1, String param2) {
        assertEquals(param1, StringEscapeUtils.escapeHtml3(param2));
    }

    static public Stream<Arguments> Provider_testEscapeHtmlThree_2to3() {
        return Stream.of(arguments("a", "a"), arguments("&lt;b&gt;a", "<b>a"));
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
    @MethodSource("Provider_testEscapeXSI_2to4")
    public void testEscapeXSI_2to4(String param1, String param2) {
        assertEquals(param1, escapeXSI(param2));
    }

    static public Stream<Arguments> Provider_testEscapeXSI_2to4() {
        return Stream.of(arguments("He\\ didn\\'t\\ say,\\ \\\"Stop!\\\"", "He didn't say, \"Stop!\""), arguments("\\\\", "\\"), arguments("", "\n"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testStandaloneAmphersand_3to4")
    public void testStandaloneAmphersand_3to4(String param1, String param2) {
        assertEquals(param1, StringEscapeUtils.unescapeXml(param2));
    }

    static public Stream<Arguments> Provider_testStandaloneAmphersand_3to4() {
        return Stream.of(arguments("<P&O>", "&lt;P&O&gt;"), arguments("test & <", "test & &lt;"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testUnescapeCsvString_1to7_9")
    public void testUnescapeCsvString_1to7_9(String param1, String param2) {
        assertEquals(param1, StringEscapeUtils.unescapeCsv(param2));
    }

    static public Stream<Arguments> Provider_testUnescapeCsvString_1to7_9() {
        return Stream.of(arguments("foo.bar", "foo.bar"), arguments("foo,bar", "\"foo,bar\""), arguments("foo\nbar", "\"foo\nbar\""), arguments("foo\rbar", "\"foo\rbar\""), arguments("foo\"bar", "\"foo\"\"bar\""), arguments("foo\uD84C\uDFB4bar", "foo\uD84C\uDFB4bar"), arguments("", ""), arguments("foo.bar", "\"foo.bar\""));
    }

    @ParameterizedTest
    @MethodSource("Provider_testUnescapeEcmaScript_2to4")
    public void testUnescapeEcmaScript_2to4(String param1, String param2) {
        assertEquals(param1, StringEscapeUtils.unescapeEcmaScript(param2));
    }

    static public Stream<Arguments> Provider_testUnescapeEcmaScript_2to4() {
        return Stream.of(arguments("8lvc1u+6B#-I", "8lvc1u+6B#-I"), arguments("<script src=\"build/main.bundle.js\"></script>", "<script src=\"build/main.bundle.js\"></script>"), arguments("<script src=\"build/main.bundle.js\"></script>>", "<script src=\"build/main.bundle.js\"></script>>"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testUnescapeJson_1to3")
    public void testUnescapeJson_1to3(String param1, String param2) {
        assertEquals(param1, StringEscapeUtils.unescapeJson(param2));
    }

    static public Stream<Arguments> Provider_testUnescapeJson_1to3() {
        return Stream.of(arguments("", ""), arguments(" ", " "), arguments("a:b", "a:b"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testUnescapeXmlSupplementaryCharacters_1to2")
    public void testUnescapeXmlSupplementaryCharacters_1to2(String param1, String param2, String param3) {
        assertEquals(param1, StringEscapeUtils.unescapeXml(param3), param2);
    }

    static public Stream<Arguments> Provider_testUnescapeXmlSupplementaryCharacters_1to2() {
        return Stream.of(arguments("\uD84C\uDFB4", "Supplementary character must be represented using a single escape", "&#144308;"), arguments("a b c \uD84C\uDFB4", "Supplementary characters mixed with basic characters should be decoded correctly", "a b c &#144308;"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testUnscapeXSI_2to5")
    public void testUnscapeXSI_2to5(String param1, String param2) {
        assertEquals(param1, unescapeXSI(param2));
    }

    static public Stream<Arguments> Provider_testUnscapeXSI_2to5() {
        return Stream.of(arguments("\"", "\\\""), arguments("He didn't say, \"Stop!\"", "He\\ didn\\'t\\ say,\\ \\\"Stop!\\\""), arguments("\\", "\\\\"), arguments("", "\\"));
    }
}
