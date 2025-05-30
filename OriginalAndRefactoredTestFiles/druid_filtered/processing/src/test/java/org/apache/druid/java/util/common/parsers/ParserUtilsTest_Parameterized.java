package org.apache.druid.java.util.common.parsers;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.junit.Test;
import java.util.Collections;
import java.util.List;
import static org.apache.druid.java.util.common.parsers.ParserUtils.findDuplicates;
import static org.apache.druid.java.util.common.parsers.ParserUtils.getTransformationFunction;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class ParserUtilsTest_Parameterized {

    @Test
    public void testInputWithDelimiterAndParserEnabled_12() {
        assertEquals(ImmutableList.of(-1.0, -2.2, 3.1, 0.2, -2.1), getTransformationFunction("|", Splitter.on("|"), true).apply("-1.0|-2.2|3.1|0.2|-2.1"));
    }

    @Test
    public void testInputWithDelimiterAndParserEnabled_13() {
        assertEquals(ImmutableList.of(-1.23, 3.13, 23L), getTransformationFunction("|", Splitter.on("|"), true).apply("-1.23|3.13|23"));
    }

    @Test
    public void testInputWithDelimiterAndParserEnabled_14() {
        assertEquals(ImmutableList.of(-1.23, 3.13, 23L, "foo", -9L), getTransformationFunction("|", Splitter.on("|"), true).apply("-1.23|3.13|23|foo|-9"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testInputWithDelimiterAndParserDisabled_1_1_1_1")
    public void testInputWithDelimiterAndParserDisabled_1_1_1_1(String param1, String param2) {
        assertNull(getTransformationFunction(param2, Splitter.on("|"), true).apply(param1));
    }

    static public Stream<Arguments> Provider_testInputWithDelimiterAndParserDisabled_1_1_1_1() {
        return Stream.of(arguments("|", "|"), arguments("|", "|"), arguments("|", "$"), arguments("$", "$"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testInputWithDelimiterAndParserDisabled_2_2_2_2to3_3to4_4to5_5to6_6_6to7_7_7_7to8_8_8to9_9_9_9to10_10to11_11to12_12")
    public void testInputWithDelimiterAndParserDisabled_2_2_2_2to3_3to4_4to5_5to6_6_6to7_7_7_7to8_8_8to9_9_9_9to10_10to11_11to12_12(String param1, String param2, String param3, String param4) {
        assertEquals(param1, getTransformationFunction(param3, Splitter.on("|"), param4).apply(param2));
    }

    static public Stream<Arguments> Provider_testInputWithDelimiterAndParserDisabled_2_2_2_2to3_3to4_4to5_5to6_6_6to7_7_7_7to8_8_8to9_9_9_9to10_10to11_11to12_12() {
        return Stream.of(arguments("", "", "|", "|"), arguments(100, 100, "|", "|"), arguments(1.23, 1.23, "|", "|"), arguments(-2.0, -2.0, "|", "|"), arguments(1e2, 1e2, "|", "|"), arguments("", "", "|", "|"), arguments(100L, 100, "|", "|"), arguments(1.23, 1.23, "|", "|"), arguments(100.0, 1e2, "$", "|"), arguments("", "", "|", "$"), arguments("foo|boo", "foo|boo", "$", "$"), arguments(100, 100, "$", "$"), arguments(1.23, 1.23, "$", "$"), arguments(-2.0, -2.0, "$", "$"), arguments(1e2, 1e2, "$", "$"), arguments("1|2|3", "1|2|3", "$", "$"), arguments("1|-2|3|0|-2", "1|-2|3|0|-2", "$", "$"), arguments("-1.0|-2.2|3.1|0.2|-2.1", "-1.0|-2.2|3.1|0.2|-2.1", "$", "$"), arguments("-1.23|3.13|23", "-1.23|3.13|23", "$", "$"), arguments("-1.23|3.13|23|foo|-9", "-1.23|3.13|23|foo|-9", "$", "$"), arguments("", "", "$", "$"), arguments("foo|boo", "foo|boo", "$", "$"), arguments(100L, 100, "$", "$"), arguments(1.23, 1.23, "$", "$"), arguments(100.0, 1e2, "$", "$"), arguments("1|2|3", "1|2|3", "$", "$"), arguments("1|-2|3|0|-2", "1|-2|3|0|-2", "$", "$"), arguments("-1.0|-2.2|3.1|0.2|-2.1", "-1.0|-2.2|3.1|0.2|-2.1", "$", "$"), arguments("-1.23|3.13|23", "-1.23|3.13|23", "$", "$"), arguments("-1.23|3.13|23|foo|-9", "-1.23|3.13|23|foo|-9", "$", "$"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testInputWithDelimiterAndParserDisabled_3_3")
    public void testInputWithDelimiterAndParserDisabled_3_3(String param1, String param2, String param3, String param4, String param5) {
        assertEquals(ImmutableList.of(param1, param2), getTransformationFunction(param4, Splitter.on("|"), param5).apply(param3));
    }

    static public Stream<Arguments> Provider_testInputWithDelimiterAndParserDisabled_3_3() {
        return Stream.of(arguments("foo", "boo", "foo|boo", "|", "|"), arguments("foo", "boo", "foo|boo", "|", "|"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testInputWithDelimiterAndParserDisabled_4_4_10_10_13")
    public void testInputWithDelimiterAndParserDisabled_4_4_10_10_13(int param1, int param2, int param3, String param4, String param5, String param6) {
        assertEquals(ImmutableList.of(param1, param2, param3), getTransformationFunction(param5, Splitter.on("|"), param6).apply(param4));
    }

    static public Stream<Arguments> Provider_testInputWithDelimiterAndParserDisabled_4_4_10_10_13() {
        return Stream.of(arguments(1, 2, 3, "1|2|3", "|", "|"), arguments(1, 2, 3, "1|2|3", "|", "|"), arguments(-1.23, 3.13, 23, "-1.23|3.13|23", "|", "|"), arguments(1L, 2L, 3L, "1|2|3", "|", "|"), arguments(1L, 2L, 3L, "1|2|3", "|", "|"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testInputWithDelimiterAndParserDisabled_5_11to12_14")
    public void testInputWithDelimiterAndParserDisabled_5_11to12_14(int param1, int param2, int param3, int param4, int param5, String param6, String param7, String param8) {
        assertEquals(ImmutableList.of(param1, param2, param3, param4, param5), getTransformationFunction(param7, Splitter.on("|"), param8).apply(param6));
    }

    static public Stream<Arguments> Provider_testInputWithDelimiterAndParserDisabled_5_11to12_14() {
        return Stream.of(arguments(1, -2, 3, 0, -2, "1|-2|3|0|-2", "|", "|"), arguments(1, -2, 3, 0, -2, "1|-2|3|0|-2", "|", "|"), arguments(-1.0, -2.2, 3.1, 0.2, -2.1, "-1.0|-2.2|3.1|0.2|-2.1", "|", "|"), arguments(-1.23, 3.13, 23, "foo", -9, "-1.23|3.13|23|foo|-9", "|", "|"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testInputWithDelimiterAndParserEnabled_5_11")
    public void testInputWithDelimiterAndParserEnabled_5_11(long param1, long param2, long param3, String param4, long param5, long param6, String param7, String param8) {
        assertEquals(ImmutableList.of(param1, -param5, param2, param3, -param6), getTransformationFunction(param7, Splitter.on("|"), param8).apply(param4));
    }

    static public Stream<Arguments> Provider_testInputWithDelimiterAndParserEnabled_5_11() {
        return Stream.of(arguments(1L, 3L, 0L, "1|-2|3|0|-2", 2L, 2L, "|", "|"), arguments(1L, 3L, 0L, "1|-2|3|0|-2", 2L, 2L, "|", "|"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testInputWithDelimiterAndParserEnabled_6_8")
    public void testInputWithDelimiterAndParserEnabled_6_8(double param1, double param2, String param3, String param4) {
        assertEquals(-param1, getTransformationFunction(param3, Splitter.on("|"), param4).apply(param2));
    }

    static public Stream<Arguments> Provider_testInputWithDelimiterAndParserEnabled_6_8() {
        return Stream.of(arguments(2.0, -2.0, "|", "|"), arguments(2.0, -2.0, "$", "$"));
    }
}
