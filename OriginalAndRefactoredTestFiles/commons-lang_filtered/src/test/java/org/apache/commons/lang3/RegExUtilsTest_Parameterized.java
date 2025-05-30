package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class RegExUtilsTest_Parameterized extends AbstractLangTest {

    @Test
    public void testRemoveAll_1() {
        assertNull(RegExUtils.removeAll((CharSequence) null, Pattern.compile("")));
    }

    @Test
    public void testRemoveAll_2() {
        assertEquals("any", RegExUtils.removeAll((CharSequence) "any", (Pattern) null));
    }

    @Test
    public void testRemoveAllDeprecated_1() {
        assertNull(RegExUtils.removeAll(null, Pattern.compile("")));
    }

    @Test
    public void testRemoveAllDeprecated_2() {
        assertEquals("any", RegExUtils.removeAll("any", (Pattern) null));
    }

    @Test
    public void testRemoveFirst_1() {
        assertNull(RegExUtils.removeFirst((CharSequence) null, Pattern.compile("")));
    }

    @Test
    public void testRemoveFirst_2() {
        assertEquals("any", RegExUtils.removeFirst((CharSequence) "any", (Pattern) null));
    }

    @Test
    public void testRemoveFirstDeprecated_1() {
        assertNull(RegExUtils.removeFirst(null, Pattern.compile("")));
    }

    @Test
    public void testRemoveFirstDeprecated_2() {
        assertEquals("any", RegExUtils.removeFirst("any", (Pattern) null));
    }

    @Test
    public void testRemovePattern_1() {
        assertNull(RegExUtils.removePattern((CharSequence) null, ""));
    }

    @Test
    public void testRemovePattern_2() {
        assertEquals("any", RegExUtils.removePattern((CharSequence) "any", (String) null));
    }

    @Test
    public void testRemovePatternDeprecated_1() {
        assertNull(RegExUtils.removePattern(null, ""));
    }

    @Test
    public void testRemovePatternDeprecated_2() {
        assertEquals("any", RegExUtils.removePattern("any", (String) null));
    }

    @Test
    public void testReplaceAll_1() {
        assertNull(RegExUtils.replaceAll((CharSequence) null, Pattern.compile(""), ""));
    }

    @Test
    public void testReplaceAll_2() {
        assertEquals("any", RegExUtils.replaceAll((CharSequence) "any", (Pattern) null, ""));
    }

    @Test
    public void testReplaceAll_3() {
        assertEquals("any", RegExUtils.replaceAll((CharSequence) "any", Pattern.compile(""), null));
    }

    @Test
    public void testReplaceAllDeprecated_1() {
        assertNull(RegExUtils.replaceAll(null, Pattern.compile(""), ""));
    }

    @Test
    public void testReplaceAllDeprecated_2() {
        assertEquals("any", RegExUtils.replaceAll("any", (Pattern) null, ""));
    }

    @Test
    public void testReplaceAllDeprecated_3() {
        assertEquals("any", RegExUtils.replaceAll("any", Pattern.compile(""), null));
    }

    @Test
    public void testReplaceFirst_1() {
        assertNull(RegExUtils.replaceFirst((CharSequence) null, Pattern.compile(""), ""));
    }

    @Test
    public void testReplaceFirst_2() {
        assertEquals("any", RegExUtils.replaceFirst((CharSequence) "any", (Pattern) null, ""));
    }

    @Test
    public void testReplaceFirst_3() {
        assertEquals("any", RegExUtils.replaceFirst((CharSequence) "any", Pattern.compile(""), null));
    }

    @Test
    public void testReplaceFirstDeprecated_1() {
        assertNull(RegExUtils.replaceFirst(null, Pattern.compile(""), ""));
    }

    @Test
    public void testReplaceFirstDeprecated_2() {
        assertEquals("any", RegExUtils.replaceFirst("any", (Pattern) null, ""));
    }

    @Test
    public void testReplaceFirstDeprecated_3() {
        assertEquals("any", RegExUtils.replaceFirst("any", Pattern.compile(""), null));
    }

    @Test
    public void testReplacePattern_1() {
        assertNull(RegExUtils.replacePattern((CharSequence) null, "", ""));
    }

    @Test
    public void testReplacePattern_2() {
        assertEquals("any", RegExUtils.replacePattern((CharSequence) "any", (String) null, ""));
    }

    @Test
    public void testReplacePattern_3() {
        assertEquals("any", RegExUtils.replacePattern((CharSequence) "any", "", null));
    }

    @Test
    public void testReplacePatternDeprecated_1() {
        assertNull(RegExUtils.replacePattern(null, "", ""));
    }

    @Test
    public void testReplacePatternDeprecated_2() {
        assertEquals("any", RegExUtils.replacePattern("any", (String) null, ""));
    }

    @Test
    public void testReplacePatternDeprecated_3() {
        assertEquals("any", RegExUtils.replacePattern("any", "", null));
    }

    @ParameterizedTest
    @MethodSource("Provider_testRemoveAll_3to9_11to12")
    public void testRemoveAll_3to9_11to12(String param1, String param2, String param3) {
        assertEquals(param1, RegExUtils.removeAll((CharSequence) param2, Pattern.compile(param3)));
    }

    static public Stream<Arguments> Provider_testRemoveAll_3to9_11to12() {
        return Stream.of(arguments("any", "any", ""), arguments("", "any", ".*"), arguments("", "any", ".+"), arguments("", "any", ".?"), arguments("A\nB", "A<__>\n<__>B", "<.*>"), arguments("AB", "A<__>\n<__>B", "(?s)<.*>"), arguments("ABC123", "ABCabc123abc", "[a-z]"), arguments("AB", "A<__>\\n<__>B", "<.*>"), arguments("", "<A>x\\ny</A>", "<A>.*</A>"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testRemoveAll_10_13")
    public void testRemoveAll_10_13(String param1, String param2, String param3) {
        assertEquals(param1, RegExUtils.removeAll((CharSequence) param2, Pattern.compile(param3, Pattern.DOTALL)));
    }

    static public Stream<Arguments> Provider_testRemoveAll_10_13() {
        return Stream.of(arguments("AB", "A<__>\n<__>B", "<.*>"), arguments("", "<A>\nxy\n</A>", "<A>.*</A>"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testRemoveAllDeprecated_3to9_11to12")
    public void testRemoveAllDeprecated_3to9_11to12(String param1, String param2, String param3) {
        assertEquals(param1, RegExUtils.removeAll(param2, Pattern.compile(param3)));
    }

    static public Stream<Arguments> Provider_testRemoveAllDeprecated_3to9_11to12() {
        return Stream.of(arguments("any", "any", ""), arguments("", "any", ".*"), arguments("", "any", ".+"), arguments("", "any", ".?"), arguments("A\nB", "A<__>\n<__>B", "<.*>"), arguments("AB", "A<__>\n<__>B", "(?s)<.*>"), arguments("ABC123", "ABCabc123abc", "[a-z]"), arguments("AB", "A<__>\\n<__>B", "<.*>"), arguments("", "<A>x\\ny</A>", "<A>.*</A>"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testRemoveAllDeprecated_10_13")
    public void testRemoveAllDeprecated_10_13(String param1, String param2, String param3) {
        assertEquals(param1, RegExUtils.removeAll(param2, Pattern.compile(param3, Pattern.DOTALL)));
    }

    static public Stream<Arguments> Provider_testRemoveAllDeprecated_10_13() {
        return Stream.of(arguments("AB", "A<__>\n<__>B", "<.*>"), arguments("", "<A>\nxy\n</A>", "<A>.*</A>"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testRemoveFirst_3to10")
    public void testRemoveFirst_3to10(String param1, String param2, String param3) {
        assertEquals(param1, RegExUtils.removeFirst((CharSequence) param2, Pattern.compile(param3)));
    }

    static public Stream<Arguments> Provider_testRemoveFirst_3to10() {
        return Stream.of(arguments("any", "any", ""), arguments("", "any", ".*"), arguments("", "any", ".+"), arguments("bc", "abc", ".?"), arguments("A\n<__>B", "A<__>\n<__>B", "<.*>"), arguments("AB", "A<__>\n<__>B", "(?s)<.*>"), arguments("ABCbc123", "ABCabc123", "[a-z]"), arguments("ABC123abc", "ABCabc123abc", "[a-z]+"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testRemoveFirstDeprecated_3to10")
    public void testRemoveFirstDeprecated_3to10(String param1, String param2, String param3) {
        assertEquals(param1, RegExUtils.removeFirst(param2, Pattern.compile(param3)));
    }

    static public Stream<Arguments> Provider_testRemoveFirstDeprecated_3to10() {
        return Stream.of(arguments("any", "any", ""), arguments("", "any", ".*"), arguments("", "any", ".+"), arguments("bc", "abc", ".?"), arguments("A\n<__>B", "A<__>\n<__>B", "<.*>"), arguments("AB", "A<__>\n<__>B", "(?s)<.*>"), arguments("ABCbc123", "ABCabc123", "[a-z]"), arguments("ABC123abc", "ABCabc123abc", "[a-z]+"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testRemovePattern_3to10")
    public void testRemovePattern_3to10(String param1, String param2, String param3) {
        assertEquals(param1, RegExUtils.removePattern((CharSequence) param3, param2));
    }

    static public Stream<Arguments> Provider_testRemovePattern_3to10() {
        return Stream.of(arguments("", "", ""), arguments("", ".*", ""), arguments("", ".+", ""), arguments("AB", "<.*>", "A<__>\n<__>B"), arguments("AB", "<.*>", "A<__>\\n<__>B"), arguments("", "<A>.*</A>", "<A>x\\ny</A>"), arguments("", "<A>.*</A>", "<A>\nxy\n</A>"), arguments("ABC123", "[a-z]", "ABCabc123"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testRemovePatternDeprecated_3to10")
    public void testRemovePatternDeprecated_3to10(String param1, String param2, String param3) {
        assertEquals(param1, RegExUtils.removePattern(param2, param3));
    }

    static public Stream<Arguments> Provider_testRemovePatternDeprecated_3to10() {
        return Stream.of(arguments("", "", ""), arguments("", "", ".*"), arguments("", "", ".+"), arguments("AB", "A<__>\n<__>B", "<.*>"), arguments("AB", "A<__>\\n<__>B", "<.*>"), arguments("", "<A>x\\ny</A>", "<A>.*</A>"), arguments("", "<A>\nxy\n</A>", "<A>.*</A>"), arguments("ABC123", "ABCabc123", "[a-z]"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testReplaceAll_4to9_11_13to16")
    public void testReplaceAll_4to9_11_13to16(String param1, String param2, String param3, String param4) {
        assertEquals(param1, RegExUtils.replaceAll((CharSequence) param3, Pattern.compile(param4), param2));
    }

    static public Stream<Arguments> Provider_testReplaceAll_4to9_11_13to16() {
        return Stream.of(arguments("zzz", "zzz", "", ""), arguments("zzz", "zzz", "", ".*"), arguments("", "zzz", "", ".+"), arguments("ZZaZZbZZcZZ", "ZZ", "abc", ""), arguments("z\nz", "z", "<__>\n<__>", "<.*>"), arguments("z", "z", "<__>\n<__>", "(?s)<.*>"), arguments("z", "z", "<__>\\n<__>", "<.*>"), arguments("ABC___123", "_", "ABCabc123", "[a-z]"), arguments("ABC_123", "_", "ABCabc123", "[^A-Z0-9]+"), arguments("ABC123", "", "ABCabc123", "[^A-Z0-9]+"), arguments("Lorem_ipsum_dolor_sit", "_$2", "Lorem ipsum  dolor   sit", "( +)([a-z]+)"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testReplaceAll_10_12")
    public void testReplaceAll_10_12(String param1, String param2, String param3, String param4) {
        assertEquals(param1, RegExUtils.replaceAll((CharSequence) param3, Pattern.compile(param4, Pattern.DOTALL), param2));
    }

    static public Stream<Arguments> Provider_testReplaceAll_10_12() {
        return Stream.of(arguments("z", "z", "<__>\n<__>", "<.*>"), arguments("X", "X", "<A>\nxy\n</A>", "<A>.*</A>"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testReplaceAllDeprecated_4to9_11_13to16")
    public void testReplaceAllDeprecated_4to9_11_13to16(String param1, String param2, String param3, String param4) {
        assertEquals(param1, RegExUtils.replaceAll(param2, Pattern.compile(param4), param3));
    }

    static public Stream<Arguments> Provider_testReplaceAllDeprecated_4to9_11_13to16() {
        return Stream.of(arguments("zzz", "", "zzz", ""), arguments("zzz", "", "zzz", ".*"), arguments("", "", "zzz", ".+"), arguments("ZZaZZbZZcZZ", "abc", "ZZ", ""), arguments("z\nz", "<__>\n<__>", "z", "<.*>"), arguments("z", "<__>\n<__>", "z", "(?s)<.*>"), arguments("z", "<__>\\n<__>", "z", "<.*>"), arguments("ABC___123", "ABCabc123", "_", "[a-z]"), arguments("ABC_123", "ABCabc123", "_", "[^A-Z0-9]+"), arguments("ABC123", "ABCabc123", "", "[^A-Z0-9]+"), arguments("Lorem_ipsum_dolor_sit", "Lorem ipsum  dolor   sit", "_$2", "( +)([a-z]+)"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testReplaceAllDeprecated_10_12")
    public void testReplaceAllDeprecated_10_12(String param1, String param2, String param3, String param4) {
        assertEquals(param1, RegExUtils.replaceAll(param2, Pattern.compile(param4, Pattern.DOTALL), param3));
    }

    static public Stream<Arguments> Provider_testReplaceAllDeprecated_10_12() {
        return Stream.of(arguments("z", "<__>\n<__>", "z", "<.*>"), arguments("X", "<A>\nxy\n</A>", "X", "<A>.*</A>"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testReplaceFirst_4to13")
    public void testReplaceFirst_4to13(String param1, String param2, String param3, String param4) {
        assertEquals(param1, RegExUtils.replaceFirst((CharSequence) param3, Pattern.compile(param4), param2));
    }

    static public Stream<Arguments> Provider_testReplaceFirst_4to13() {
        return Stream.of(arguments("zzz", "zzz", "", ""), arguments("zzz", "zzz", "", ".*"), arguments("", "zzz", "", ".+"), arguments("ZZabc", "ZZ", "abc", ""), arguments("z\n<__>", "z", "<__>\n<__>", "<.*>"), arguments("z", "z", "<__>\n<__>", "(?s)<.*>"), arguments("ABC_bc123", "_", "ABCabc123", "[a-z]"), arguments("ABC_123abc", "_", "ABCabc123abc", "[^A-Z0-9]+"), arguments("ABC123abc", "", "ABCabc123abc", "[^A-Z0-9]+"), arguments("Lorem_ipsum  dolor   sit", "_$2", "Lorem ipsum  dolor   sit", "( +)([a-z]+)"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testReplaceFirstDeprecated_4to13")
    public void testReplaceFirstDeprecated_4to13(String param1, String param2, String param3, String param4) {
        assertEquals(param1, RegExUtils.replaceFirst(param2, Pattern.compile(param4), param3));
    }

    static public Stream<Arguments> Provider_testReplaceFirstDeprecated_4to13() {
        return Stream.of(arguments("zzz", "", "zzz", ""), arguments("zzz", "", "zzz", ".*"), arguments("", "", "zzz", ".+"), arguments("ZZabc", "abc", "ZZ", ""), arguments("z\n<__>", "<__>\n<__>", "z", "<.*>"), arguments("z", "<__>\n<__>", "z", "(?s)<.*>"), arguments("ABC_bc123", "ABCabc123", "_", "[a-z]"), arguments("ABC_123abc", "ABCabc123abc", "_", "[^A-Z0-9]+"), arguments("ABC123abc", "ABCabc123abc", "", "[^A-Z0-9]+"), arguments("Lorem_ipsum  dolor   sit", "Lorem ipsum  dolor   sit", "_$2", "( +)([a-z]+)"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testReplacePattern_4to13")
    public void testReplacePattern_4to13(String param1, String param2, String param3, String param4) {
        assertEquals(param1, RegExUtils.replacePattern((CharSequence) param4, param2, param3));
    }

    static public Stream<Arguments> Provider_testReplacePattern_4to13() {
        return Stream.of(arguments("zzz", "", "zzz", ""), arguments("zzz", ".*", "zzz", ""), arguments("", ".+", "zzz", ""), arguments("z", "<.*>", "z", "<__>\n<__>"), arguments("z", "<.*>", "z", "<__>\\n<__>"), arguments("X", "<A>.*</A>", "X", "<A>\nxy\n</A>"), arguments("ABC___123", "[a-z]", "_", "ABCabc123"), arguments("ABC_123", "[^A-Z0-9]+", "_", "ABCabc123"), arguments("ABC123", "[^A-Z0-9]+", "", "ABCabc123"), arguments("Lorem_ipsum_dolor_sit", "( +)([a-z]+)", "_$2", "Lorem ipsum  dolor   sit"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testReplacePatternDeprecated_4to13")
    public void testReplacePatternDeprecated_4to13(String param1, String param2, String param3, String param4) {
        assertEquals(param1, RegExUtils.replacePattern(param2, param3, param4));
    }

    static public Stream<Arguments> Provider_testReplacePatternDeprecated_4to13() {
        return Stream.of(arguments("zzz", "", "", "zzz"), arguments("zzz", "", ".*", "zzz"), arguments("", "", ".+", "zzz"), arguments("z", "<__>\n<__>", "<.*>", "z"), arguments("z", "<__>\\n<__>", "<.*>", "z"), arguments("X", "<A>\nxy\n</A>", "<A>.*</A>", "X"), arguments("ABC___123", "ABCabc123", "[a-z]", "_"), arguments("ABC_123", "ABCabc123", "[^A-Z0-9]+", "_"), arguments("ABC123", "ABCabc123", "[^A-Z0-9]+", ""), arguments("Lorem_ipsum_dolor_sit", "Lorem ipsum  dolor   sit", "( +)([a-z]+)", "_$2"));
    }
}
