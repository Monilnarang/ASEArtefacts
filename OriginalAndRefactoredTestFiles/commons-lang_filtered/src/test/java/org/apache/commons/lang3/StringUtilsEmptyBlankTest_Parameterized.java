package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class StringUtilsEmptyBlankTest_Parameterized extends AbstractLangTest {

    @Test
    public void testFirstNonBlank_1() {
        assertNull(StringUtils.firstNonBlank());
    }

    @Test
    public void testFirstNonBlank_2() {
        assertNull(StringUtils.firstNonBlank((String[]) null));
    }

    @Test
    public void testFirstNonBlank_3() {
        assertNull(StringUtils.firstNonBlank(null, null, null));
    }

    @Test
    public void testFirstNonBlank_4() {
        assertNull(StringUtils.firstNonBlank(null, "", " "));
    }

    @Test
    public void testFirstNonBlank_5() {
        assertNull(StringUtils.firstNonBlank(null, null, " "));
    }

    @Test
    public void testFirstNonBlank_8() {
        assertEquals("abc", StringUtils.firstNonBlank("abc"));
    }

    @Test
    public void testFirstNonBlank_10() {
        assertEquals("xyz", StringUtils.firstNonBlank(null, "xyz", "abc"));
    }

    @Test
    public void testFirstNonEmpty_1() {
        assertNull(StringUtils.firstNonEmpty());
    }

    @Test
    public void testFirstNonEmpty_2() {
        assertNull(StringUtils.firstNonEmpty((String[]) null));
    }

    @Test
    public void testFirstNonEmpty_3() {
        assertNull(StringUtils.firstNonEmpty(null, null, null));
    }

    @Test
    public void testFirstNonEmpty_5() {
        assertNull(StringUtils.firstNonEmpty(null, null, ""));
    }

    @Test
    public void testFirstNonEmpty_8() {
        assertEquals("abc", StringUtils.firstNonEmpty("abc"));
    }

    @Test
    public void testIsAllBlank_1() {
        assertTrue(StringUtils.isAllBlank((String) null));
    }

    @Test
    public void testIsAllBlank_2() {
        assertTrue(StringUtils.isAllBlank((String[]) null));
    }

    @Test
    public void testIsAllBlank_3() {
        assertTrue(StringUtils.isAllBlank(null, null));
    }

    @Test
    public void testIsAllBlank_4() {
        assertTrue(StringUtils.isAllBlank(null, " "));
    }

    @Test
    public void testIsAllBlank_9() {
        assertFalse(StringUtils.isAllBlank("  bob  ", null));
    }

    @Test
    public void testIsAllEmpty_1() {
        assertTrue(StringUtils.isAllEmpty());
    }

    @Test
    public void testIsAllEmpty_2() {
        assertTrue(StringUtils.isAllEmpty());
    }

    @Test
    public void testIsAllEmpty_3() {
        assertTrue(StringUtils.isAllEmpty((String) null));
    }

    @Test
    public void testIsAllEmpty_4() {
        assertTrue(StringUtils.isAllEmpty((String[]) null));
    }

    @Test
    public void testIsAllEmpty_9() {
        assertFalse(StringUtils.isAllEmpty("  bob  ", null));
    }

    @Test
    public void testIsAllEmpty_12() {
        assertTrue(StringUtils.isAllEmpty("", null));
    }

    @Test
    public void testIsAnyBlank_1() {
        assertTrue(StringUtils.isAnyBlank((String) null));
    }

    @Test
    public void testIsAnyBlank_2() {
        assertFalse(StringUtils.isAnyBlank((String[]) null));
    }

    @Test
    public void testIsAnyBlank_3() {
        assertTrue(StringUtils.isAnyBlank(null, "foo"));
    }

    @Test
    public void testIsAnyBlank_4() {
        assertTrue(StringUtils.isAnyBlank(null, null));
    }

    @Test
    public void testIsAnyBlank_7() {
        assertTrue(StringUtils.isAnyBlank("  bob  ", null));
    }

    @Test
    public void testIsAnyBlank_9() {
        assertFalse(StringUtils.isAnyBlank("foo", "bar"));
    }

    @Test
    public void testIsAnyEmpty_1() {
        assertTrue(StringUtils.isAnyEmpty((String) null));
    }

    @Test
    public void testIsAnyEmpty_2() {
        assertFalse(StringUtils.isAnyEmpty((String[]) null));
    }

    @Test
    public void testIsAnyEmpty_7() {
        assertTrue(StringUtils.isAnyEmpty("  bob  ", null));
    }

    @Test
    public void testIsBlank_1() {
        assertTrue(StringUtils.isBlank(null));
    }

    @Test
    public void testIsBlank_2() {
        assertTrue(StringUtils.isBlank(""));
    }

    @Test
    public void testIsBlank_3() {
        assertTrue(StringUtils.isBlank(StringUtilsTest.WHITESPACE));
    }

    @Test
    public void testIsEmpty_1() {
        assertTrue(StringUtils.isEmpty(null));
    }

    @Test
    public void testIsEmpty_2() {
        assertTrue(StringUtils.isEmpty(""));
    }

    @Test
    public void testIsNoneBlank_1() {
        assertFalse(StringUtils.isNoneBlank((String) null));
    }

    @Test
    public void testIsNoneBlank_2() {
        assertTrue(StringUtils.isNoneBlank((String[]) null));
    }

    @Test
    public void testIsNoneBlank_5() {
        assertFalse(StringUtils.isNoneBlank(null, null));
    }

    @Test
    public void testIsNoneBlank_9() {
        assertFalse(StringUtils.isNoneBlank("  bob  ", null));
    }

    @Test
    public void testIsNoneBlank_11() {
        assertTrue(StringUtils.isNoneBlank("foo", "bar"));
    }

    @Test
    public void testIsNoneEmpty_1() {
        assertFalse(StringUtils.isNoneEmpty((String) null));
    }

    @Test
    public void testIsNoneEmpty_2() {
        assertTrue(StringUtils.isNoneEmpty((String[]) null));
    }

    @Test
    public void testIsNoneEmpty_8() {
        assertFalse(StringUtils.isNoneEmpty("  bob  ", null));
    }

    @Test
    public void testIsNotBlank_1() {
        assertFalse(StringUtils.isNotBlank(null));
    }

    @Test
    public void testIsNotBlank_2() {
        assertFalse(StringUtils.isNotBlank(""));
    }

    @Test
    public void testIsNotBlank_3() {
        assertFalse(StringUtils.isNotBlank(StringUtilsTest.WHITESPACE));
    }

    @Test
    public void testIsNotEmpty_1() {
        assertFalse(StringUtils.isNotEmpty(null));
    }

    @Test
    public void testIsNotEmpty_2() {
        assertFalse(StringUtils.isNotEmpty(""));
    }

    @ParameterizedTest
    @MethodSource("Provider_testFirstNonBlank_6to7_9")
    public void testFirstNonBlank_6to7_9(String param1, String param2) {
        assertEquals(param1, StringUtils.firstNonBlank(param2, "a"));
    }

    static public Stream<Arguments> Provider_testFirstNonBlank_6to7_9() {
        return Stream.of(arguments("a", "a"), arguments("zz", "zz"), arguments("xyz", "xyz"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testFirstNonEmpty_4_10")
    public void testFirstNonEmpty_4_10(String param1, String param2, String param3) {
        assertEquals(param1, StringUtils.firstNonEmpty(param2, param3, " "));
    }

    static public Stream<Arguments> Provider_testFirstNonEmpty_4_10() {
        return Stream.of(arguments(" ", "", " "), arguments("xyz", "xyz", "abc"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testFirstNonEmpty_6to7_9")
    public void testFirstNonEmpty_6to7_9(String param1, String param2) {
        assertEquals(param1, StringUtils.firstNonEmpty(param2, "a"));
    }

    static public Stream<Arguments> Provider_testFirstNonEmpty_6to7_9() {
        return Stream.of(arguments("a", "a"), arguments("zz", "zz"), arguments("xyz", "xyz"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsAllBlank_5to6")
    public void testIsAllBlank_5to6(String param1) {
        assertFalse(StringUtils.isAllBlank(param1, "foo"));
    }

    static public Stream<Arguments> Provider_testIsAllBlank_5to6() {
        return Stream.of(arguments("foo"), arguments("a"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsAllBlank_7to8_10to11")
    public void testIsAllBlank_7to8_10to11(String param1, String param2) {
        assertFalse(StringUtils.isAllBlank(param1, param2));
    }

    static public Stream<Arguments> Provider_testIsAllBlank_7to8_10to11() {
        return Stream.of(arguments("", "bar"), arguments("bob", ""), arguments(" ", "bar"), arguments("foo", "bar"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsAllEmpty_5to6")
    public void testIsAllEmpty_5to6(String param1) {
        assertFalse(StringUtils.isAllEmpty(param1, "foo"));
    }

    static public Stream<Arguments> Provider_testIsAllEmpty_5to6() {
        return Stream.of(arguments("foo"), arguments("a"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsAllEmpty_7to8_10to11")
    public void testIsAllEmpty_7to8_10to11(String param1, String param2) {
        assertFalse(StringUtils.isAllEmpty(param1, param2));
    }

    static public Stream<Arguments> Provider_testIsAllEmpty_7to8_10to11() {
        return Stream.of(arguments("", "bar"), arguments("bob", ""), arguments(" ", "bar"), arguments("foo", "bar"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsAnyBlank_5to6_8")
    public void testIsAnyBlank_5to6_8(String param1, String param2) {
        assertTrue(StringUtils.isAnyBlank(param1, param2));
    }

    static public Stream<Arguments> Provider_testIsAnyBlank_5to6_8() {
        return Stream.of(arguments("", "bar"), arguments("bob", ""), arguments(" ", "bar"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsAnyEmpty_3to4")
    public void testIsAnyEmpty_3to4(String param1) {
        assertTrue(StringUtils.isAnyEmpty(param1, "foo"));
    }

    static public Stream<Arguments> Provider_testIsAnyEmpty_3to4() {
        return Stream.of(arguments("foo"), arguments("a"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsAnyEmpty_5to6")
    public void testIsAnyEmpty_5to6(String param1, String param2) {
        assertTrue(StringUtils.isAnyEmpty(param1, param2));
    }

    static public Stream<Arguments> Provider_testIsAnyEmpty_5to6() {
        return Stream.of(arguments("", "bar"), arguments("bob", ""));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsAnyEmpty_8to9")
    public void testIsAnyEmpty_8to9(String param1, String param2) {
        assertFalse(StringUtils.isAnyEmpty(param1, param2));
    }

    static public Stream<Arguments> Provider_testIsAnyEmpty_8to9() {
        return Stream.of(arguments(" ", "bar"), arguments("foo", "bar"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsBlank_4to6")
    public void testIsBlank_4to6(String param1) {
        assertFalse(StringUtils.isBlank(param1));
    }

    static public Stream<Arguments> Provider_testIsBlank_4to6() {
        return Stream.of(arguments("a"), arguments("foo"), arguments("  foo  "));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsEmpty_3to6")
    public void testIsEmpty_3to6(String param1) {
        assertFalse(StringUtils.isEmpty(param1));
    }

    static public Stream<Arguments> Provider_testIsEmpty_3to6() {
        return Stream.of(arguments(" "), arguments("a"), arguments("foo"), arguments("  foo  "));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsNoneBlank_3to4")
    public void testIsNoneBlank_3to4(String param1) {
        assertFalse(StringUtils.isNoneBlank(param1, "a"));
    }

    static public Stream<Arguments> Provider_testIsNoneBlank_3to4() {
        return Stream.of(arguments("a"), arguments("foo"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsNoneBlank_6to8_10")
    public void testIsNoneBlank_6to8_10(String param1, String param2) {
        assertFalse(StringUtils.isNoneBlank(param1, param2));
    }

    static public Stream<Arguments> Provider_testIsNoneBlank_6to8_10() {
        return Stream.of(arguments("", "bar"), arguments("a", ""), arguments("bob", ""), arguments(" ", "bar"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsNoneEmpty_3to4")
    public void testIsNoneEmpty_3to4(String param1) {
        assertFalse(StringUtils.isNoneEmpty(param1, "a"));
    }

    static public Stream<Arguments> Provider_testIsNoneEmpty_3to4() {
        return Stream.of(arguments("a"), arguments("foo"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsNoneEmpty_5to7")
    public void testIsNoneEmpty_5to7(String param1, String param2) {
        assertFalse(StringUtils.isNoneEmpty(param1, param2));
    }

    static public Stream<Arguments> Provider_testIsNoneEmpty_5to7() {
        return Stream.of(arguments("", "bar"), arguments("bob", ""), arguments("a", ""));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsNoneEmpty_9to10")
    public void testIsNoneEmpty_9to10(String param1, String param2) {
        assertTrue(StringUtils.isNoneEmpty(param1, param2));
    }

    static public Stream<Arguments> Provider_testIsNoneEmpty_9to10() {
        return Stream.of(arguments(" ", "bar"), arguments("foo", "bar"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsNotBlank_4to6")
    public void testIsNotBlank_4to6(String param1) {
        assertTrue(StringUtils.isNotBlank(param1));
    }

    static public Stream<Arguments> Provider_testIsNotBlank_4to6() {
        return Stream.of(arguments("a"), arguments("foo"), arguments("  foo  "));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsNotEmpty_3to6")
    public void testIsNotEmpty_3to6(String param1) {
        assertTrue(StringUtils.isNotEmpty(param1));
    }

    static public Stream<Arguments> Provider_testIsNotEmpty_3to6() {
        return Stream.of(arguments(" "), arguments("a"), arguments("foo"), arguments("  foo  "));
    }
}
