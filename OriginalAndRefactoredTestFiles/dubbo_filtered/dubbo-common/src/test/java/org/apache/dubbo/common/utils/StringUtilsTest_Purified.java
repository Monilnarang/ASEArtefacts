package org.apache.dubbo.common.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Test;
import static java.util.Arrays.asList;
import static org.apache.dubbo.common.constants.CommonConstants.GROUP_KEY;
import static org.apache.dubbo.common.constants.CommonConstants.INTERFACE_KEY;
import static org.apache.dubbo.common.constants.CommonConstants.VERSION_KEY;
import static org.apache.dubbo.common.utils.CollectionUtils.ofSet;
import static org.apache.dubbo.common.utils.StringUtils.splitToList;
import static org.apache.dubbo.common.utils.StringUtils.splitToSet;
import static org.apache.dubbo.common.utils.StringUtils.startsWithIgnoreCase;
import static org.apache.dubbo.common.utils.StringUtils.toCommaDelimitedString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StringUtilsTest_Purified {

    private void assertEqualsWithoutSpaces(String expect, String actual) {
        assertEquals(expect.replaceAll(" ", ""), actual.replaceAll(" ", ""));
    }

    @Test
    void testLength_1() throws Exception {
        assertThat(StringUtils.length(null), equalTo(0));
    }

    @Test
    void testLength_2() throws Exception {
        assertThat(StringUtils.length("abc"), equalTo(3));
    }

    @Test
    void testRepeat_1() throws Exception {
        assertThat(StringUtils.repeat(null, 2), nullValue());
    }

    @Test
    void testRepeat_2() throws Exception {
        assertThat(StringUtils.repeat("", 0), equalTo(""));
    }

    @Test
    void testRepeat_3() throws Exception {
        assertThat(StringUtils.repeat("", 2), equalTo(""));
    }

    @Test
    void testRepeat_4() throws Exception {
        assertThat(StringUtils.repeat("a", 3), equalTo("aaa"));
    }

    @Test
    void testRepeat_5() throws Exception {
        assertThat(StringUtils.repeat("ab", 2), equalTo("abab"));
    }

    @Test
    void testRepeat_6() throws Exception {
        assertThat(StringUtils.repeat("a", -2), equalTo(""));
    }

    @Test
    void testRepeat_7() throws Exception {
        assertThat(StringUtils.repeat(null, null, 2), nullValue());
    }

    @Test
    void testRepeat_8() throws Exception {
        assertThat(StringUtils.repeat(null, "x", 2), nullValue());
    }

    @Test
    void testRepeat_9() throws Exception {
        assertThat(StringUtils.repeat("", null, 0), equalTo(""));
    }

    @Test
    void testRepeat_10() throws Exception {
        assertThat(StringUtils.repeat("", "", 2), equalTo(""));
    }

    @Test
    void testRepeat_11() throws Exception {
        assertThat(StringUtils.repeat("", "x", 3), equalTo("xx"));
    }

    @Test
    void testRepeat_12() throws Exception {
        assertThat(StringUtils.repeat("?", ", ", 3), equalTo("?, ?, ?"));
    }

    @Test
    void testRepeat_13() throws Exception {
        assertThat(StringUtils.repeat('e', 0), equalTo(""));
    }

    @Test
    void testRepeat_14() throws Exception {
        assertThat(StringUtils.repeat('e', 3), equalTo("eee"));
    }

    @Test
    void testStripEnd_1() throws Exception {
        assertThat(StringUtils.stripEnd(null, "*"), nullValue());
    }

    @Test
    void testStripEnd_2() throws Exception {
        assertThat(StringUtils.stripEnd("", null), equalTo(""));
    }

    @Test
    void testStripEnd_3() throws Exception {
        assertThat(StringUtils.stripEnd("abc", ""), equalTo("abc"));
    }

    @Test
    void testStripEnd_4() throws Exception {
        assertThat(StringUtils.stripEnd("abc", null), equalTo("abc"));
    }

    @Test
    void testStripEnd_5() throws Exception {
        assertThat(StringUtils.stripEnd("  abc", null), equalTo("  abc"));
    }

    @Test
    void testStripEnd_6() throws Exception {
        assertThat(StringUtils.stripEnd("abc  ", null), equalTo("abc"));
    }

    @Test
    void testStripEnd_7() throws Exception {
        assertThat(StringUtils.stripEnd(" abc ", null), equalTo(" abc"));
    }

    @Test
    void testStripEnd_8() throws Exception {
        assertThat(StringUtils.stripEnd("  abcyx", "xyz"), equalTo("  abc"));
    }

    @Test
    void testStripEnd_9() throws Exception {
        assertThat(StringUtils.stripEnd("120.00", ".0"), equalTo("12"));
    }

    @Test
    void testReplace_1() throws Exception {
        assertThat(StringUtils.replace(null, "*", "*"), nullValue());
    }

    @Test
    void testReplace_2() throws Exception {
        assertThat(StringUtils.replace("", "*", "*"), equalTo(""));
    }

    @Test
    void testReplace_3() throws Exception {
        assertThat(StringUtils.replace("any", null, "*"), equalTo("any"));
    }

    @Test
    void testReplace_4() throws Exception {
        assertThat(StringUtils.replace("any", "*", null), equalTo("any"));
    }

    @Test
    void testReplace_5() throws Exception {
        assertThat(StringUtils.replace("any", "", "*"), equalTo("any"));
    }

    @Test
    void testReplace_6() throws Exception {
        assertThat(StringUtils.replace("aba", "a", null), equalTo("aba"));
    }

    @Test
    void testReplace_7() throws Exception {
        assertThat(StringUtils.replace("aba", "a", ""), equalTo("b"));
    }

    @Test
    void testReplace_8() throws Exception {
        assertThat(StringUtils.replace("aba", "a", "z"), equalTo("zbz"));
    }

    @Test
    void testReplace_9() throws Exception {
        assertThat(StringUtils.replace(null, "*", "*", 64), nullValue());
    }

    @Test
    void testReplace_10() throws Exception {
        assertThat(StringUtils.replace("", "*", "*", 64), equalTo(""));
    }

    @Test
    void testReplace_11() throws Exception {
        assertThat(StringUtils.replace("any", null, "*", 64), equalTo("any"));
    }

    @Test
    void testReplace_12() throws Exception {
        assertThat(StringUtils.replace("any", "*", null, 64), equalTo("any"));
    }

    @Test
    void testReplace_13() throws Exception {
        assertThat(StringUtils.replace("any", "", "*", 64), equalTo("any"));
    }

    @Test
    void testReplace_14() throws Exception {
        assertThat(StringUtils.replace("any", "*", "*", 0), equalTo("any"));
    }

    @Test
    void testReplace_15() throws Exception {
        assertThat(StringUtils.replace("abaa", "a", null, -1), equalTo("abaa"));
    }

    @Test
    void testReplace_16() throws Exception {
        assertThat(StringUtils.replace("abaa", "a", "", -1), equalTo("b"));
    }

    @Test
    void testReplace_17() throws Exception {
        assertThat(StringUtils.replace("abaa", "a", "z", 0), equalTo("abaa"));
    }

    @Test
    void testReplace_18() throws Exception {
        assertThat(StringUtils.replace("abaa", "a", "z", 1), equalTo("zbaa"));
    }

    @Test
    void testReplace_19() throws Exception {
        assertThat(StringUtils.replace("abaa", "a", "z", 2), equalTo("zbza"));
    }

    @Test
    void testIsBlank_1() throws Exception {
        assertTrue(StringUtils.isBlank(null));
    }

    @Test
    void testIsBlank_2() throws Exception {
        assertTrue(StringUtils.isBlank(""));
    }

    @Test
    void testIsBlank_3() throws Exception {
        assertFalse(StringUtils.isBlank("abc"));
    }

    @Test
    void testIsEmpty_1() throws Exception {
        assertTrue(StringUtils.isEmpty(null));
    }

    @Test
    void testIsEmpty_2() throws Exception {
        assertTrue(StringUtils.isEmpty(""));
    }

    @Test
    void testIsEmpty_3() throws Exception {
        assertFalse(StringUtils.isEmpty("abc"));
    }

    @Test
    void testIsNoneEmpty_1() throws Exception {
        assertFalse(StringUtils.isNoneEmpty(null));
    }

    @Test
    void testIsNoneEmpty_2() throws Exception {
        assertFalse(StringUtils.isNoneEmpty(""));
    }

    @Test
    void testIsNoneEmpty_3() throws Exception {
        assertTrue(StringUtils.isNoneEmpty(" "));
    }

    @Test
    void testIsNoneEmpty_4() throws Exception {
        assertTrue(StringUtils.isNoneEmpty("abc"));
    }

    @Test
    void testIsNoneEmpty_5() throws Exception {
        assertTrue(StringUtils.isNoneEmpty("abc", "def"));
    }

    @Test
    void testIsNoneEmpty_6() throws Exception {
        assertFalse(StringUtils.isNoneEmpty("abc", null));
    }

    @Test
    void testIsNoneEmpty_7() throws Exception {
        assertFalse(StringUtils.isNoneEmpty("abc", ""));
    }

    @Test
    void testIsNoneEmpty_8() throws Exception {
        assertTrue(StringUtils.isNoneEmpty("abc", " "));
    }

    @Test
    void testIsAnyEmpty_1() throws Exception {
        assertTrue(StringUtils.isAnyEmpty(null));
    }

    @Test
    void testIsAnyEmpty_2() throws Exception {
        assertTrue(StringUtils.isAnyEmpty(""));
    }

    @Test
    void testIsAnyEmpty_3() throws Exception {
        assertFalse(StringUtils.isAnyEmpty(" "));
    }

    @Test
    void testIsAnyEmpty_4() throws Exception {
        assertFalse(StringUtils.isAnyEmpty("abc"));
    }

    @Test
    void testIsAnyEmpty_5() throws Exception {
        assertFalse(StringUtils.isAnyEmpty("abc", "def"));
    }

    @Test
    void testIsAnyEmpty_6() throws Exception {
        assertTrue(StringUtils.isAnyEmpty("abc", null));
    }

    @Test
    void testIsAnyEmpty_7() throws Exception {
        assertTrue(StringUtils.isAnyEmpty("abc", ""));
    }

    @Test
    void testIsAnyEmpty_8() throws Exception {
        assertFalse(StringUtils.isAnyEmpty("abc", " "));
    }

    @Test
    void testIsNotEmpty_1() throws Exception {
        assertFalse(StringUtils.isNotEmpty(null));
    }

    @Test
    void testIsNotEmpty_2() throws Exception {
        assertFalse(StringUtils.isNotEmpty(""));
    }

    @Test
    void testIsNotEmpty_3() throws Exception {
        assertTrue(StringUtils.isNotEmpty("abc"));
    }

    @Test
    void testIsEquals_1() throws Exception {
        assertTrue(StringUtils.isEquals(null, null));
    }

    @Test
    void testIsEquals_2() throws Exception {
        assertFalse(StringUtils.isEquals(null, ""));
    }

    @Test
    void testIsEquals_3() throws Exception {
        assertTrue(StringUtils.isEquals("abc", "abc"));
    }

    @Test
    void testIsEquals_4() throws Exception {
        assertFalse(StringUtils.isEquals("abc", "ABC"));
    }

    @Test
    void testIsInteger_1() throws Exception {
        assertFalse(StringUtils.isNumber(null));
    }

    @Test
    void testIsInteger_2() throws Exception {
        assertFalse(StringUtils.isNumber(""));
    }

    @Test
    void testIsInteger_3() throws Exception {
        assertTrue(StringUtils.isNumber("123"));
    }

    @Test
    void testParseInteger_1() throws Exception {
        assertThat(StringUtils.parseInteger(null), equalTo(0));
    }

    @Test
    void testParseInteger_2() throws Exception {
        assertThat(StringUtils.parseInteger("123"), equalTo(123));
    }

    @Test
    void testIsJavaIdentifier_1() throws Exception {
        assertThat(StringUtils.isJavaIdentifier(""), is(false));
    }

    @Test
    void testIsJavaIdentifier_2() throws Exception {
        assertThat(StringUtils.isJavaIdentifier("1"), is(false));
    }

    @Test
    void testIsJavaIdentifier_3() throws Exception {
        assertThat(StringUtils.isJavaIdentifier("abc123"), is(true));
    }

    @Test
    void testIsJavaIdentifier_4() throws Exception {
        assertThat(StringUtils.isJavaIdentifier("abc(23)"), is(false));
    }

    @Test
    void testParseQueryString_1() throws Exception {
        assertThat(StringUtils.getQueryStringValue("key1=value1&key2=value2", "key1"), equalTo("value1"));
    }

    @Test
    void testParseQueryString_2() throws Exception {
        assertThat(StringUtils.getQueryStringValue("key1=value1&key2=value2", "key2"), equalTo("value2"));
    }

    @Test
    void testParseQueryString_3() throws Exception {
        assertThat(StringUtils.getQueryStringValue("", "key1"), isEmptyOrNullString());
    }

    @Test
    void testSplit_1_testMerged_1() throws Exception {
        String str = "d,1,2,4";
        assertEquals(4, StringUtils.split(str, ',').length);
        assertArrayEquals(str.split(","), StringUtils.split(str, ','));
        assertEquals(1, StringUtils.split(str, 'a').length);
        assertArrayEquals(str.split("a"), StringUtils.split(str, 'a'));
    }

    @Test
    void testSplit_5() throws Exception {
        assertEquals(0, StringUtils.split("", 'a').length);
    }

    @Test
    void testSplit_6() throws Exception {
        assertEquals(0, StringUtils.split(null, 'a').length);
    }

    @Test
    void testSplitToList_1_testMerged_1() throws Exception {
        String str = "d,1,2,4";
        assertEquals(4, splitToList(str, ',').size());
        assertEquals(asList(str.split(",")), splitToList(str, ','));
        assertEquals(1, splitToList(str, 'a').size());
        assertEquals(asList(str.split("a")), splitToList(str, 'a'));
    }

    @Test
    void testSplitToList_5() throws Exception {
        assertEquals(0, splitToList("", 'a').size());
    }

    @Test
    void testSplitToList_6() throws Exception {
        assertEquals(0, splitToList(null, 'a').size());
    }

    @Test
    void testIsNumeric_1() throws Exception {
        assertThat(StringUtils.isNumeric("123", false), is(true));
    }

    @Test
    void testIsNumeric_2() throws Exception {
        assertThat(StringUtils.isNumeric("1a3", false), is(false));
    }

    @Test
    void testIsNumeric_3() throws Exception {
        assertThat(StringUtils.isNumeric(null, false), is(false));
    }

    @Test
    void testIsNumeric_4() throws Exception {
        assertThat(StringUtils.isNumeric("0", true), is(true));
    }

    @Test
    void testIsNumeric_5() throws Exception {
        assertThat(StringUtils.isNumeric("0.1", true), is(true));
    }

    @Test
    void testIsNumeric_6() throws Exception {
        assertThat(StringUtils.isNumeric("DUBBO", true), is(false));
    }

    @Test
    void testIsNumeric_7() throws Exception {
        assertThat(StringUtils.isNumeric("", true), is(false));
    }

    @Test
    void testIsNumeric_8() throws Exception {
        assertThat(StringUtils.isNumeric(" ", true), is(false));
    }

    @Test
    void testIsNumeric_9() throws Exception {
        assertThat(StringUtils.isNumeric("   ", true), is(false));
    }

    @Test
    void testIsNumeric_10() throws Exception {
        assertThat(StringUtils.isNumeric("123.3.3", true), is(false));
    }

    @Test
    void testIsNumeric_11() throws Exception {
        assertThat(StringUtils.isNumeric("123.", true), is(true));
    }

    @Test
    void testIsNumeric_12() throws Exception {
        assertThat(StringUtils.isNumeric(".123", true), is(true));
    }

    @Test
    void testIsNumeric_13() throws Exception {
        assertThat(StringUtils.isNumeric("..123", true), is(false));
    }

    @Test
    void testCamelToSplitName_1() throws Exception {
        assertEquals("ab-cd-ef", StringUtils.camelToSplitName("abCdEf", "-"));
    }

    @Test
    void testCamelToSplitName_2() throws Exception {
        assertEquals("ab-cd-ef", StringUtils.camelToSplitName("AbCdEf", "-"));
    }

    @Test
    void testCamelToSplitName_3() throws Exception {
        assertEquals("abcdef", StringUtils.camelToSplitName("abcdef", "-"));
    }

    @Test
    void testCamelToSplitName_4() throws Exception {
        assertEquals("ab-cd-ef", StringUtils.camelToSplitName("ab-cd-ef", "-"));
    }

    @Test
    void testCamelToSplitName_5() throws Exception {
        assertEquals("ab-cd-ef", StringUtils.camelToSplitName("Ab-Cd-Ef", "-"));
    }

    @Test
    void testCamelToSplitName_6() throws Exception {
        assertEquals("Ab_Cd_Ef", StringUtils.camelToSplitName("Ab_Cd_Ef", "-"));
    }

    @Test
    void testCamelToSplitName_7() throws Exception {
        assertEquals("AB_CD_EF", StringUtils.camelToSplitName("AB_CD_EF", "-"));
    }

    @Test
    void testCamelToSplitName_8() throws Exception {
        assertEquals("ab.cd.ef", StringUtils.camelToSplitName("AbCdEf", "."));
    }

    @Test
    void testSnakeCaseToSplitName_1() throws Exception {
        assertEquals("ab-cd-ef", StringUtils.snakeToSplitName("ab_Cd_Ef", "-"));
    }

    @Test
    void testSnakeCaseToSplitName_2() throws Exception {
        assertEquals("ab-cd-ef", StringUtils.snakeToSplitName("Ab_Cd_Ef", "-"));
    }

    @Test
    void testSnakeCaseToSplitName_3() throws Exception {
        assertEquals("ab-cd-ef", StringUtils.snakeToSplitName("ab_cd_ef", "-"));
    }

    @Test
    void testSnakeCaseToSplitName_4() throws Exception {
        assertEquals("ab-cd-ef", StringUtils.snakeToSplitName("AB_CD_EF", "-"));
    }

    @Test
    void testSnakeCaseToSplitName_5() throws Exception {
        assertEquals("abcdef", StringUtils.snakeToSplitName("abcdef", "-"));
    }

    @Test
    void testSnakeCaseToSplitName_6() throws Exception {
        assertEquals("qosEnable", StringUtils.snakeToSplitName("qosEnable", "-"));
    }

    @Test
    void testSnakeCaseToSplitName_7() throws Exception {
        assertEquals("name", StringUtils.snakeToSplitName("NAME", "-"));
    }

    @Test
    void testConvertToSplitName_1() {
        assertEquals("ab-cd-ef", StringUtils.convertToSplitName("ab_Cd_Ef", "-"));
    }

    @Test
    void testConvertToSplitName_2() {
        assertEquals("ab-cd-ef", StringUtils.convertToSplitName("Ab_Cd_Ef", "-"));
    }

    @Test
    void testConvertToSplitName_3() {
        assertEquals("ab-cd-ef", StringUtils.convertToSplitName("ab_cd_ef", "-"));
    }

    @Test
    void testConvertToSplitName_4() {
        assertEquals("ab-cd-ef", StringUtils.convertToSplitName("AB_CD_EF", "-"));
    }

    @Test
    void testConvertToSplitName_5() {
        assertEquals("abcdef", StringUtils.convertToSplitName("abcdef", "-"));
    }

    @Test
    void testConvertToSplitName_6() {
        assertEquals("qos-enable", StringUtils.convertToSplitName("qosEnable", "-"));
    }

    @Test
    void testConvertToSplitName_7() {
        assertEquals("name", StringUtils.convertToSplitName("NAME", "-"));
    }

    @Test
    void testConvertToSplitName_8() {
        assertEquals("ab-cd-ef", StringUtils.convertToSplitName("abCdEf", "-"));
    }

    @Test
    void testConvertToSplitName_9() {
        assertEquals("ab-cd-ef", StringUtils.convertToSplitName("AbCdEf", "-"));
    }

    @Test
    void testConvertToSplitName_10() {
        assertEquals("ab-cd-ef", StringUtils.convertToSplitName("ab-cd-ef", "-"));
    }

    @Test
    void testConvertToSplitName_11() {
        assertEquals("ab-cd-ef", StringUtils.convertToSplitName("Ab-Cd-Ef", "-"));
    }

    @Test
    void testTrim_1() {
        assertEquals("left blank", StringUtils.trim(" left blank"));
    }

    @Test
    void testTrim_2() {
        assertEquals("right blank", StringUtils.trim("right blank "));
    }

    @Test
    void testTrim_3() {
        assertEquals("bi-side blank", StringUtils.trim(" bi-side blank "));
    }

    @Test
    void testToURLKey_1() {
        assertEquals("dubbo.tag1", StringUtils.toURLKey("dubbo_tag1"));
    }

    @Test
    void testToURLKey_2() {
        assertEquals("dubbo.tag1.tag11", StringUtils.toURLKey("dubbo-tag1_tag11"));
    }

    @Test
    void testToOSStyleKey_1() {
        assertEquals("DUBBO_TAG1", StringUtils.toOSStyleKey("dubbo_tag1"));
    }

    @Test
    void testToOSStyleKey_2() {
        assertEquals("DUBBO_TAG1", StringUtils.toOSStyleKey("dubbo.tag1"));
    }

    @Test
    void testToOSStyleKey_3() {
        assertEquals("DUBBO_TAG1_TAG11", StringUtils.toOSStyleKey("dubbo.tag1.tag11"));
    }

    @Test
    void testToOSStyleKey_4() {
        assertEquals("DUBBO_TAG1", StringUtils.toOSStyleKey("tag1"));
    }

    @Test
    void testStartsWithIgnoreCase_1() {
        assertTrue(startsWithIgnoreCase("dubbo.application.name", "dubbo.application."));
    }

    @Test
    void testStartsWithIgnoreCase_2() {
        assertTrue(startsWithIgnoreCase("dubbo.Application.name", "dubbo.application."));
    }

    @Test
    void testStartsWithIgnoreCase_3() {
        assertTrue(startsWithIgnoreCase("Dubbo.application.name", "dubbo.application."));
    }
}
