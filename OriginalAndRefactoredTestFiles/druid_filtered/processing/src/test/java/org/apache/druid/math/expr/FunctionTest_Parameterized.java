package org.apache.druid.math.expr;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.apache.druid.error.DruidException;
import org.apache.druid.guice.BuiltInTypesModule;
import org.apache.druid.java.util.common.IAE;
import org.apache.druid.java.util.common.Pair;
import org.apache.druid.java.util.common.StringUtils;
import org.apache.druid.math.expr.Expr.ObjectBinding;
import org.apache.druid.segment.column.TypeStrategies;
import org.apache.druid.segment.column.TypeStrategiesTest;
import org.apache.druid.segment.column.TypeStrategy;
import org.apache.druid.segment.nested.StructuredData;
import org.apache.druid.testing.InitializedNullHandlingTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class FunctionTest_Parameterized extends InitializedNullHandlingTest {

    private Expr.ObjectBinding bestEffortBindings;

    private Expr.ObjectBinding typedBindings;

    private Expr.ObjectBinding[] allBindings;

    @BeforeClass
    public static void setupClass() {
        TypeStrategies.registerComplex(TypeStrategiesTest.NULLABLE_TEST_PAIR_TYPE.getComplexTypeName(), new TypeStrategiesTest.NullableLongPairTypeStrategy());
        BuiltInTypesModule.registerHandlersAndSerde();
    }

    @Before
    public void setup() {
        ImmutableMap.Builder<String, ExpressionType> inputTypesBuilder = ImmutableMap.builder();
        inputTypesBuilder.put("x", ExpressionType.STRING).put("y", ExpressionType.LONG).put("z", ExpressionType.DOUBLE).put("d", ExpressionType.DOUBLE).put("maxLong", ExpressionType.LONG).put("minLong", ExpressionType.LONG).put("f", ExpressionType.DOUBLE).put("nan", ExpressionType.DOUBLE).put("inf", ExpressionType.DOUBLE).put("-inf", ExpressionType.DOUBLE).put("o", ExpressionType.LONG).put("od", ExpressionType.DOUBLE).put("of", ExpressionType.DOUBLE).put("a", ExpressionType.STRING_ARRAY).put("b", ExpressionType.LONG_ARRAY).put("c", ExpressionType.DOUBLE_ARRAY).put("someComplex", ExpressionType.fromColumnType(TypeStrategiesTest.NULLABLE_TEST_PAIR_TYPE)).put("str1", ExpressionType.STRING).put("str2", ExpressionType.STRING).put("nestedArray", ExpressionType.NESTED_DATA).put("emptyArray", ExpressionType.STRING_ARRAY);
        final StructuredData nestedArray = StructuredData.wrap(ImmutableList.of(ImmutableMap.of("x", 2L, "y", 3.3), ImmutableMap.of("x", 4L, "y", 6.6)));
        ImmutableMap.Builder<String, Object> builder = ImmutableMap.builder();
        builder.put("x", "foo").put("y", 2).put("z", 3.1).put("d", 34.56D).put("maxLong", Long.MAX_VALUE).put("minLong", Long.MIN_VALUE).put("f", 12.34F).put("nan", Double.NaN).put("inf", Double.POSITIVE_INFINITY).put("-inf", Double.NEGATIVE_INFINITY).put("o", 0).put("od", 0D).put("of", 0F).put("a", new String[] { "foo", "bar", "baz", "foobar" }).put("b", new Long[] { 1L, 2L, 3L, 4L, 5L }).put("c", new Double[] { 3.1, 4.2, 5.3 }).put("someComplex", new TypeStrategiesTest.NullableLongPair(1L, 2L)).put("str1", "v1").put("str2", "v2").put("nestedArray", nestedArray).put("emptyArray", new Object[] {});
        bestEffortBindings = InputBindings.forMap(builder.build());
        typedBindings = InputBindings.forMap(builder.build(), InputBindings.inspectorFromTypeMap(inputTypesBuilder.build()));
        allBindings = new Expr.ObjectBinding[] { bestEffortBindings, typedBindings };
    }

    private void assertExpr(final String expression, @Nullable final Object expectedResult) {
        for (Expr.ObjectBinding toUse : allBindings) {
            assertExpr(expression, expectedResult, toUse);
        }
    }

    private void assertExpr(final String expression, @Nullable final Object expectedResult, Expr.ObjectBinding bindings) {
        final Expr expr = Parser.parse(expression, ExprMacroTable.nil());
        Assert.assertEquals(expression, expectedResult, expr.eval(bindings).value());
        final Expr exprNoFlatten = Parser.parse(expression, ExprMacroTable.nil(), false);
        final Expr roundTrip = Parser.parse(exprNoFlatten.stringify(), ExprMacroTable.nil());
        Assert.assertEquals(expr.stringify(), expectedResult, roundTrip.eval(bindings).value());
        final Expr roundTripFlatten = Parser.parse(expr.stringify(), ExprMacroTable.nil());
        Assert.assertEquals(expr.stringify(), expectedResult, roundTripFlatten.eval(bindings).value());
        final Expr singleThreaded = Expr.singleThreaded(expr, bindings);
        Assert.assertEquals(singleThreaded.stringify(), expectedResult, singleThreaded.eval(bindings).value());
        final Expr singleThreadedNoFlatten = Expr.singleThreaded(exprNoFlatten, bindings);
        Assert.assertEquals(singleThreadedNoFlatten.stringify(), expectedResult, singleThreadedNoFlatten.eval(bindings).value());
        Assert.assertEquals(expr.stringify(), roundTrip.stringify());
        Assert.assertEquals(expr.stringify(), roundTripFlatten.stringify());
        Assert.assertArrayEquals(expr.getCacheKey(), roundTrip.getCacheKey());
        Assert.assertArrayEquals(expr.getCacheKey(), roundTripFlatten.getCacheKey());
    }

    private void assertArrayExpr(final String expression, @Nullable final Object[] expectedResult) {
        for (Expr.ObjectBinding toUse : allBindings) {
            assertArrayExpr(expression, expectedResult, toUse);
        }
    }

    private void assertArrayExpr(final String expression, @Nullable final Object[] expectedResult, Expr.ObjectBinding bindings) {
        final Expr expr = Parser.parse(expression, ExprMacroTable.nil());
        Assert.assertArrayEquals(expression, expectedResult, expr.eval(bindings).asArray());
        final Expr exprNoFlatten = Parser.parse(expression, ExprMacroTable.nil(), false);
        final Expr roundTrip = Parser.parse(exprNoFlatten.stringify(), ExprMacroTable.nil());
        Assert.assertArrayEquals(expression, expectedResult, roundTrip.eval(bindings).asArray());
        final Expr roundTripFlatten = Parser.parse(expr.stringify(), ExprMacroTable.nil());
        Assert.assertArrayEquals(expression, expectedResult, roundTripFlatten.eval(bindings).asArray());
        Assert.assertEquals(expr.stringify(), roundTrip.stringify());
        Assert.assertEquals(expr.stringify(), roundTripFlatten.stringify());
        Assert.assertArrayEquals(expr.getCacheKey(), roundTrip.getCacheKey());
        Assert.assertArrayEquals(expr.getCacheKey(), roundTripFlatten.getCacheKey());
    }

    @Test
    public void testArrayLength_3() {
        assertExpr("array_length(nestedArray)", 2L, typedBindings);
    }

    @Test
    public void testRoundWithExtremeNumbers_1() {
        assertExpr("round(maxLong)", BigDecimal.valueOf(Long.MAX_VALUE).setScale(0, RoundingMode.HALF_UP).longValue());
    }

    @Test
    public void testRoundWithExtremeNumbers_4() {
        assertExpr("round(minLong - 1, -2)", BigDecimal.valueOf(Long.MAX_VALUE).setScale(-2, RoundingMode.HALF_UP).longValue());
    }

    @Test
    public void testRoundWithExtremeNumbers_5() {
        assertExpr("round(CAST(maxLong, 'DOUBLE') + 1, 1)", BigDecimal.valueOf(((double) Long.MAX_VALUE) + 1).setScale(1, RoundingMode.HALF_UP).doubleValue());
    }

    @Test
    public void testRoundWithExtremeNumbers_6() {
        assertExpr("round(CAST(minLong, 'DOUBLE') - 1, -2)", BigDecimal.valueOf(((double) Long.MIN_VALUE) - 1).setScale(-2, RoundingMode.HALF_UP).doubleValue());
    }

    @ParameterizedTest
    @MethodSource("Provider_testCaseSimple_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1to2_2_2_2_2_2_2_2_2_2_2_2_2_2_2_2_2_2_2_2_2_2_2to3_3_3_3_3_3_3_3_3_3_3_3_3_3_3_3_3_3_3_3to4_4_4_4_4_4_4_4_4_4_4_4_4_4_4_4_4to5_5_5_5_5_5_5_5_5to6_6_6_6_6_6_6_6_6to7_7_7_7_7_7_7to8_8_8_8_8_8_8_8to9_9_9_9_9_9to10_10_10_10_10to11_11_11_11to12_12_12_12_12to13_13_13_13to14_14to15_15to16_16_16to17_17to18_18to19_19to20")
    public void testCaseSimple_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1to2_2_2_2_2_2_2_2_2_2_2_2_2_2_2_2_2_2_2_2_2_2_2to3_3_3_3_3_3_3_3_3_3_3_3_3_3_3_3_3_3_3_3to4_4_4_4_4_4_4_4_4_4_4_4_4_4_4_4_4to5_5_5_5_5_5_5_5_5to6_6_6_6_6_6_6_6_6to7_7_7_7_7_7_7to8_8_8_8_8_8_8_8to9_9_9_9_9_9to10_10_10_10_10to11_11_11_11to12_12_12_12_12to13_13_13_13to14_14to15_15to16_16_16to17_17to18_18to19_19to20(String param1, String param2) {
        assertExpr(param1, param2);
    }

    static public Stream<Arguments> Provider_testCaseSimple_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1_1to2_2_2_2_2_2_2_2_2_2_2_2_2_2_2_2_2_2_2_2_2_2_2to3_3_3_3_3_3_3_3_3_3_3_3_3_3_3_3_3_3_3_3to4_4_4_4_4_4_4_4_4_4_4_4_4_4_4_4_4to5_5_5_5_5_5_5_5_5to6_6_6_6_6_6_6_6_6to7_7_7_7_7_7_7to8_8_8_8_8_8_8_8to9_9_9_9_9_9to10_10_10_10_10to11_11_11_11to12_12_12_12_12to13_13_13_13to14_14to15_15to16_16_16to17_17to18_18to19_19to20() {
        return Stream.of(arguments("case_simple(x,'baz','is baz','foo','is foo','is other')", "is foo"), arguments("case_simple(x,'baz','is baz','bar','is bar','is other')", "is other"), arguments("case_simple(y,2,'is 2',3,'is 3','is other')", "is 2"), arguments("case_simple(z,2,'is 2',3,'is 3','is other')", "is other"), arguments("case_searched(x=='baz','is baz',x=='foo','is foo','is other')", "is foo"), arguments("case_searched(x=='baz','is baz',x=='bar','is bar','is other')", "is other"), arguments("case_searched(y==2,'is 2',y==3,'is 3','is other')", "is 2"), arguments("case_searched(z==2,'is 2',z==3,'is 3','is other')", "is other"), arguments("concat(x,' ',y)", "foo 2"), arguments("concat(z)", 3.1), arguments("replace(x,'oo','ab')", "fab"), arguments("replace(x,x,'ab')", "ab"), arguments("replace(x,'oo',y)", "f2"), arguments("substring(x,0,2)", "fo"), arguments("substring(x,1,2)", "oo"), arguments("substring(x,y,1)", "o"), arguments("substring(x,0,-1)", "foo"), arguments("substring(x,0,100)", "foo"), arguments("strlen(x)", 3L), arguments("strpos(x, 'o')", 1L), arguments("strpos(x, 'o', 0)", 1L), arguments("strpos(x, 'o', 1)", 1L), arguments("strpos(x, 'o', 2)", 2L), arguments("strpos(x, '')", 0L), arguments("isnull(null)", 1L), arguments("isnull('abc')", 0L), arguments("notnull(null)", 0L), arguments("notnull('abc')", 1L), arguments("lpad(x, 5, 'ab')", "abfoo"), arguments("lpad(x, 4, 'ab')", "afoo"), arguments("lpad(x, 2, 'ab')", "fo"), arguments("lpad(x, -1, 'ab')", ""), arguments("lpad(x, 2, '')", "fo"), arguments("lpad(x, 6, '')", "foo"), arguments("lpad('', 3, '*')", "***"), arguments("lpad(a, 4, '*')", "[foo"), arguments("lpad(a, 2, '*')", "[f"), arguments("lpad(a, 2, '')", "[f"), arguments("lpad(b, 4, '*')", "[1, "), arguments("lpad(b, 2, '')", "[1"), arguments("lpad(x, 5, x)", "fofoo"), arguments("lpad(x, 5, y)", "22foo"), arguments("lpad(x, 5, z)", "3.foo"), arguments("lpad(y, 5, x)", "foof2"), arguments("lpad(z, 5, y)", 223.1), arguments("rpad(x, 5, 'ab')", "fooab"), arguments("rpad(x, 4, 'ab')", "fooa"), arguments("rpad(x, 2, 'ab')", "fo"), arguments("rpad(x, -1, 'ab')", ""), arguments("rpad(x, 2, '')", "fo"), arguments("rpad(x, 6, '')", "foo"), arguments("rpad('', 3, '*')", "***"), arguments("rpad(a, 2, '*')", "[f"), arguments("rpad(a, 2, '')", "[f"), arguments("rpad(b, 4, '*')", "[1, "), arguments("rpad(b, 2, '')", "[1"), arguments("rpad(x, 5, x)", "foofo"), arguments("rpad(x, 5, y)", "foo22"), arguments("rpad(x, 5, z)", "foo3."), arguments("rpad(y, 5, x)", "2foof"), arguments("rpad(z, 5, y)", 3.122), arguments("array_length([1,2,3])", 3L), arguments("array_length(a)", 4L), arguments("array_offset([1, 2, 3], 2)", 3L), arguments("array_offset(a, 2)", "baz"), arguments("array_ordinal([1, 2, 3], 3)", 3L), arguments("array_ordinal(a, 3)", "baz"), arguments("array_offset_of([1, 2, 3], 3)", 2L), arguments("array_offset_of(a, 'baz')", 2L), arguments("array_ordinal_of([1, 2, 3], 3)", 3L), arguments("array_ordinal_of(a, 'baz')", 3L), arguments("scalar_in_array(2, [1, 2, 3])", 1L), arguments("scalar_in_array(2.1, [1, 2, 3])", 0L), arguments("scalar_in_array(2, [1.1, 2.1, 3.1])", 0L), arguments("scalar_in_array(2, [1.1, 2.0, 3.1])", 1L), arguments("scalar_in_array(4, [1, 2, 3])", 0L), arguments("scalar_in_array(b, [3, 4])", 0L), arguments("scalar_in_array(null, [1, null, 2])", 1L), arguments("array_contains([1, 2, 3], 2)", 1L), arguments("array_contains([1, 2, 3], 4)", 0L), arguments("array_contains([1, 2, 3], [2, 3])", 1L), arguments("array_contains([1, 2, 3], [3, 4])", 0L), arguments("array_contains(b, [3, 4])", 1L), arguments("array_contains([1, null, 2], null)", 1L), arguments("array_contains([1, null, 2], [null])", 1L), arguments("array_contains([1, 2], null)", 0L), arguments("array_overlap([1, 2, 3], [2, 4, 6])", 1L), arguments("array_overlap([1, 2, 3], [4, 5, 6])", 0L), arguments("array_overlap([4, null], [4, 5, 6])", 1L), arguments("array_overlap([4, 5, 6], null)", 0L), arguments("array_overlap([4, 5, 6], [null])", 0L), arguments("array_overlap([4, 5, null, 6], null)", 0L), arguments("array_overlap([4, 5, null, 6], [null])", 1L), arguments("array_to_string([1, 2, 3], ',')", "1,2,3"), arguments("array_to_string([1], '|')", 1), arguments("array_to_string(a, '|')", "foo|bar|baz|foobar"), arguments("round(nan)", 0D), arguments("round(nan, 5)", 0D), arguments("round(0/od)", 0D), arguments("round(od/od)", 0D), arguments("round(0/of)", 0D), arguments("round(of/of)", 0D), arguments("round(y)", 2L), arguments("round(y, 2)", 2L), arguments("round(y, -1)", 0L), arguments("round(d)", 35D), arguments("round(d, 2)", 34.56D), arguments("round(d, y)", 34.56D), arguments("round(d, 1)", 34.6D), arguments("round(d, -1)", 30D), arguments("round(f)", 12D), arguments("round(f, 2)", 12.34D), arguments("round(f, y)", 12.34D), arguments("round(f, 1)", 12.3D), arguments("round(f, -1)", 10D), arguments("human_readable_binary_byte_format(-1024)", "-1.00 KiB"), arguments("human_readable_binary_byte_format(1024)", "1.00 KiB"), arguments("human_readable_binary_byte_format(1024*1024)", "1.00 MiB"), arguments("human_readable_binary_byte_format(1024*1024*1024)", "1.00 GiB"), arguments("human_readable_binary_byte_format(1024*1024*1024*1024)", "1.00 TiB"), arguments("human_readable_binary_byte_format(1024*1024*1024*1024*1024)", "1.00 PiB"), arguments("human_readable_decimal_byte_format(-1000)", "-1.00 KB"), arguments("human_readable_decimal_byte_format(1000)", "1.00 KB"), arguments("human_readable_decimal_byte_format(1000*1000)", "1.00 MB"), arguments("human_readable_decimal_byte_format(1000*1000*1000)", "1.00 GB"), arguments("human_readable_decimal_byte_format(1000*1000*1000*1000)", "1.00 TB"), arguments("human_readable_decimal_format(-1000)", "-1.00 K"), arguments("human_readable_decimal_format(1000)", "1.00 K"), arguments("human_readable_decimal_format(1000*1000)", "1.00 M"), arguments("human_readable_decimal_format(1000*1000*1000)", "1.00 G"), arguments("human_readable_decimal_format(1000*1000*1000*1000)", "1.00 T"), arguments("human_readable_binary_byte_format(1024, 0)", "1 KiB"), arguments("human_readable_binary_byte_format(1024*1024, 1)", "1.0 MiB"), arguments("human_readable_binary_byte_format(1024*1024*1024, 2)", "1.00 GiB"), arguments("human_readable_binary_byte_format(1024*1024*1024*1024, 3)", "1.000 TiB"), arguments("human_readable_decimal_byte_format(1234, 0)", "1 KB"), arguments("human_readable_decimal_byte_format(1234*1000, 1)", "1.2 MB"), arguments("human_readable_decimal_byte_format(1234*1000*1000, 2)", "1.23 GB"), arguments("human_readable_decimal_byte_format(1234*1000*1000*1000, 3)", "1.234 TB"), arguments("human_readable_decimal_format(1234, 0)", "1 K"), arguments("human_readable_decimal_format(1234*1000,1)", "1.2 M"), arguments("human_readable_decimal_format(1234*1000*1000,2)", "1.23 G"), arguments("human_readable_decimal_format(1234*1000*1000*1000,3)", "1.234 T"), arguments("human_readable_binary_byte_format(f)", "12 B"), arguments("human_readable_binary_byte_format(nan)", "0 B"), arguments("human_readable_binary_byte_format(inf)", "8.00 EiB"), arguments("human_readable_binary_byte_format(-inf)", "-8.00 EiB"), arguments("human_readable_binary_byte_format(o)", "0 B"), arguments("human_readable_binary_byte_format(od)", "0 B"), arguments("human_readable_binary_byte_format(of)", "0 B"), arguments("safe_divide(3, 1)", 3L), arguments("safe_divide(4.5, 2)", 2.25), arguments("safe_divide(0, NaN)", 0.0), arguments("safe_divide(0, maxLong)", 0L), arguments("safe_divide(0.0, inf)", 0.0), arguments("decode_base64_utf8('aGVsbG8=')", "hello"), arguments("decode_base64_utf8('V2hlbiBhbiBvbmlvbiBpcyBjdXQsIGNlcnRhaW4gKGxhY2hyeW1hdG9yKSBjb21wb3VuZHMgYXJlIHJlbGVhc2VkIGNhdXNpbmcgdGhlIG5lcnZlcyBhcm91bmQgdGhlIGV5ZXMgKGxhY3JpbWFsIGdsYW5kcykgdG8gYmVjb21lIGlycml0YXRlZC4=')", "When an onion is cut, certain (lachrymator) compounds are released causing the nerves around the eyes (lacrimal glands) to become irritated."), arguments("decode_base64_utf8('eyJ0ZXN0IjogMX0=')", "{\"test\": 1}"), arguments("decode_base64_utf8('')", ""));
    }

    @ParameterizedTest
    @MethodSource("Provider_testConcat_2_2_2to3_3to4")
    public void testConcat_2_2_2to3_3to4(String param1) {
        assertArrayExpr(param1, null);
    }

    static public Stream<Arguments> Provider_testConcat_2_2_2to3_3to4() {
        return Stream.of(arguments("concat(x,' ',nonexistent,' ',y)"), arguments("concat()"), arguments("array_offset([1, 2, 3], 3)"), arguments("array_offset([1, 2, 3], -1)"), arguments("array_ordinal([1, 2, 3], 4)"), arguments("array_ordinal([1, 2, 3], 0)"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testStrlen_1to2_2_2to3_3to5_5_5to7_7to8_8to9_9to11_14to15")
    public void testStrlen_1to2_2_2to3_3to5_5_5to7_7to8_8to9_9to11_14to15(String param1) {
        assertExpr(param1, null);
    }

    static public Stream<Arguments> Provider_testStrlen_1to2_2_2to3_3to5_5_5to7_7to8_8to9_9to11_14to15() {
        return Stream.of(arguments("strlen(nonexistent)"), arguments("lpad(null, 5, 'ab')"), arguments("lpad(x, 2, null)"), arguments("lpad(b, 2, null)"), arguments("rpad(null, 5, 'ab')"), arguments("rpad(x, 2, null)"), arguments("rpad(b, 2, null)"), arguments("array_offset_of([1, 2, 3], 4)"), arguments("array_ordinal_of([1, 2, 3], 4)"), arguments("scalar_in_array(1, null)"), arguments("scalar_in_array(null, null)"), arguments("scalar_in_array(null, [1, 2])"), arguments("array_contains(null, [3, 4])"), arguments("array_contains(null, null)"), arguments("array_overlap(null, [4, 5, 6])"), arguments("human_readable_binary_byte_format(nonexist)"), arguments("safe_divide(3, 0)"), arguments("safe_divide(1, 0.0)"), arguments("safe_divide(NaN, 0.0)"), arguments("safe_divide(maxLong,0)"), arguments("safe_divide(0,0)"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testStrpos_5_7_10")
    public void testStrpos_5_7_10(String param1, long param2) {
        assertExpr(param1, -param2);
    }

    static public Stream<Arguments> Provider_testStrpos_5_7_10() {
        return Stream.of(arguments("strpos(x, 'o', 3)", 1L), arguments("strpos(x, 'x')", 1L), arguments("safe_divide(0.0, -inf)", 0.0));
    }

    @ParameterizedTest
    @MethodSource("Provider_testArrayOffset_5_5")
    public void testArrayOffset_5_5(String param1, String param2, long param3, String param4, double param5) {
        assertExpr(param1, ImmutableMap.of(param2, param3, param4, param5), typedBindings);
    }

    static public Stream<Arguments> Provider_testArrayOffset_5_5() {
        return Stream.of(arguments("array_offset(nestedArray, 1)", "x", 4L, "y", 6.6), arguments("array_ordinal(nestedArray, 2)", "x", 4L, "y", 6.6));
    }

    @ParameterizedTest
    @MethodSource("Provider_testRoundWithNonNumericValuesShouldReturn0_3to4_10_14")
    public void testRoundWithNonNumericValuesShouldReturn0_3to4_10_14(String param1) {
        assertExpr(param1, Double.MAX_VALUE);
    }

    static public Stream<Arguments> Provider_testRoundWithNonNumericValuesShouldReturn0_3to4_10_14() {
        return Stream.of(arguments("round(inf)"), arguments("round(inf, 4)"), arguments("round(1/od)"), arguments("round(1/of)"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testRoundWithNonNumericValuesShouldReturn0_5to7_11_15")
    public void testRoundWithNonNumericValuesShouldReturn0_5to7_11_15(String param1, int param2) {
        assertExpr(param1, -param2 * Double.MAX_VALUE);
    }

    static public Stream<Arguments> Provider_testRoundWithNonNumericValuesShouldReturn0_5to7_11_15() {
        return Stream.of(arguments("round(-inf)", 1), arguments("round(-inf, 3)", 1), arguments("round(-inf, -5)", 1), arguments("round(-1/od)", 1), arguments("round(-1/of)", 1));
    }

    @ParameterizedTest
    @MethodSource("Provider_testRoundWithExtremeNumbers_2to3")
    public void testRoundWithExtremeNumbers_2to3(String param1, int param2) {
        assertExpr(param1, BigDecimal.valueOf(Long.MIN_VALUE).setScale(param2, RoundingMode.HALF_UP).longValue());
    }

    static public Stream<Arguments> Provider_testRoundWithExtremeNumbers_2to3() {
        return Stream.of(arguments("round(minLong)", 0), arguments("round(maxLong + 1, 1)", 1));
    }

    @ParameterizedTest
    @MethodSource("Provider_testComplexDecodeNull_1to2")
    public void testComplexDecodeNull_1to2(String param1) {
        assertExpr(StringUtils.format("complex_decode_base64('%s', null)", TypeStrategiesTest.NULLABLE_TEST_PAIR_TYPE.getComplexTypeName()), param1);
    }

    static public Stream<Arguments> Provider_testComplexDecodeNull_1to2() {
        return Stream.of(arguments("complex_decode_base64('%s', null)"), arguments("decode_base64_complex('%s', null)"));
    }
}
