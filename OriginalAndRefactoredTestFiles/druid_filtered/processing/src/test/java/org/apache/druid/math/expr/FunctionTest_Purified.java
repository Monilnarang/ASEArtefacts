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

public class FunctionTest_Purified extends InitializedNullHandlingTest {

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
    public void testCaseSimple_1() {
        assertExpr("case_simple(x,'baz','is baz','foo','is foo','is other')", "is foo");
    }

    @Test
    public void testCaseSimple_2() {
        assertExpr("case_simple(x,'baz','is baz','bar','is bar','is other')", "is other");
    }

    @Test
    public void testCaseSimple_3() {
        assertExpr("case_simple(y,2,'is 2',3,'is 3','is other')", "is 2");
    }

    @Test
    public void testCaseSimple_4() {
        assertExpr("case_simple(z,2,'is 2',3,'is 3','is other')", "is other");
    }

    @Test
    public void testCaseSearched_1() {
        assertExpr("case_searched(x=='baz','is baz',x=='foo','is foo','is other')", "is foo");
    }

    @Test
    public void testCaseSearched_2() {
        assertExpr("case_searched(x=='baz','is baz',x=='bar','is bar','is other')", "is other");
    }

    @Test
    public void testCaseSearched_3() {
        assertExpr("case_searched(y==2,'is 2',y==3,'is 3','is other')", "is 2");
    }

    @Test
    public void testCaseSearched_4() {
        assertExpr("case_searched(z==2,'is 2',z==3,'is 3','is other')", "is other");
    }

    @Test
    public void testConcat_1() {
        assertExpr("concat(x,' ',y)", "foo 2");
    }

    @Test
    public void testConcat_2() {
        assertArrayExpr("concat(x,' ',nonexistent,' ',y)", null);
    }

    @Test
    public void testConcat_3() {
        assertExpr("concat(z)", "3.1");
    }

    @Test
    public void testConcat_4() {
        assertArrayExpr("concat()", null);
    }

    @Test
    public void testReplace_1() {
        assertExpr("replace(x,'oo','ab')", "fab");
    }

    @Test
    public void testReplace_2() {
        assertExpr("replace(x,x,'ab')", "ab");
    }

    @Test
    public void testReplace_3() {
        assertExpr("replace(x,'oo',y)", "f2");
    }

    @Test
    public void testSubstring_1() {
        assertExpr("substring(x,0,2)", "fo");
    }

    @Test
    public void testSubstring_2() {
        assertExpr("substring(x,1,2)", "oo");
    }

    @Test
    public void testSubstring_3() {
        assertExpr("substring(x,y,1)", "o");
    }

    @Test
    public void testSubstring_4() {
        assertExpr("substring(x,0,-1)", "foo");
    }

    @Test
    public void testSubstring_5() {
        assertExpr("substring(x,0,100)", "foo");
    }

    @Test
    public void testStrlen_1() {
        assertExpr("strlen(x)", 3L);
    }

    @Test
    public void testStrlen_2() {
        assertExpr("strlen(nonexistent)", null);
    }

    @Test
    public void testStrpos_1() {
        assertExpr("strpos(x, 'o')", 1L);
    }

    @Test
    public void testStrpos_2() {
        assertExpr("strpos(x, 'o', 0)", 1L);
    }

    @Test
    public void testStrpos_3() {
        assertExpr("strpos(x, 'o', 1)", 1L);
    }

    @Test
    public void testStrpos_4() {
        assertExpr("strpos(x, 'o', 2)", 2L);
    }

    @Test
    public void testStrpos_5() {
        assertExpr("strpos(x, 'o', 3)", -1L);
    }

    @Test
    public void testStrpos_6() {
        assertExpr("strpos(x, '')", 0L);
    }

    @Test
    public void testStrpos_7() {
        assertExpr("strpos(x, 'x')", -1L);
    }

    @Test
    public void testIsNull_1() {
        assertExpr("isnull(null)", 1L);
    }

    @Test
    public void testIsNull_2() {
        assertExpr("isnull('abc')", 0L);
    }

    @Test
    public void testIsNotNull_1() {
        assertExpr("notnull(null)", 0L);
    }

    @Test
    public void testIsNotNull_2() {
        assertExpr("notnull('abc')", 1L);
    }

    @Test
    public void testLpad_1() {
        assertExpr("lpad(x, 5, 'ab')", "abfoo");
    }

    @Test
    public void testLpad_2() {
        assertExpr("lpad(x, 4, 'ab')", "afoo");
    }

    @Test
    public void testLpad_3() {
        assertExpr("lpad(x, 2, 'ab')", "fo");
    }

    @Test
    public void testLpad_4() {
        assertExpr("lpad(x, -1, 'ab')", "");
    }

    @Test
    public void testLpad_5() {
        assertExpr("lpad(null, 5, 'ab')", null);
    }

    @Test
    public void testLpad_6() {
        assertExpr("lpad(x, 2, '')", "fo");
    }

    @Test
    public void testLpad_7() {
        assertExpr("lpad(x, 6, '')", "foo");
    }

    @Test
    public void testLpad_8() {
        assertExpr("lpad('', 3, '*')", "***");
    }

    @Test
    public void testLpad_9() {
        assertExpr("lpad(x, 2, null)", null);
    }

    @Test
    public void testLpad_10() {
        assertExpr("lpad(a, 4, '*')", "[foo");
    }

    @Test
    public void testLpad_11() {
        assertExpr("lpad(a, 2, '*')", "[f");
    }

    @Test
    public void testLpad_12() {
        assertExpr("lpad(a, 2, '')", "[f");
    }

    @Test
    public void testLpad_13() {
        assertExpr("lpad(b, 4, '*')", "[1, ");
    }

    @Test
    public void testLpad_14() {
        assertExpr("lpad(b, 2, '')", "[1");
    }

    @Test
    public void testLpad_15() {
        assertExpr("lpad(b, 2, null)", null);
    }

    @Test
    public void testLpad_16() {
        assertExpr("lpad(x, 5, x)", "fofoo");
    }

    @Test
    public void testLpad_17() {
        assertExpr("lpad(x, 5, y)", "22foo");
    }

    @Test
    public void testLpad_18() {
        assertExpr("lpad(x, 5, z)", "3.foo");
    }

    @Test
    public void testLpad_19() {
        assertExpr("lpad(y, 5, x)", "foof2");
    }

    @Test
    public void testLpad_20() {
        assertExpr("lpad(z, 5, y)", "223.1");
    }

    @Test
    public void testRpad_1() {
        assertExpr("rpad(x, 5, 'ab')", "fooab");
    }

    @Test
    public void testRpad_2() {
        assertExpr("rpad(x, 4, 'ab')", "fooa");
    }

    @Test
    public void testRpad_3() {
        assertExpr("rpad(x, 2, 'ab')", "fo");
    }

    @Test
    public void testRpad_4() {
        assertExpr("rpad(x, -1, 'ab')", "");
    }

    @Test
    public void testRpad_5() {
        assertExpr("rpad(null, 5, 'ab')", null);
    }

    @Test
    public void testRpad_6() {
        assertExpr("rpad(x, 2, '')", "fo");
    }

    @Test
    public void testRpad_7() {
        assertExpr("rpad(x, 6, '')", "foo");
    }

    @Test
    public void testRpad_8() {
        assertExpr("rpad('', 3, '*')", "***");
    }

    @Test
    public void testRpad_9() {
        assertExpr("rpad(x, 2, null)", null);
    }

    @Test
    public void testRpad_10() {
        assertExpr("rpad(a, 2, '*')", "[f");
    }

    @Test
    public void testRpad_11() {
        assertExpr("rpad(a, 2, '')", "[f");
    }

    @Test
    public void testRpad_12() {
        assertExpr("rpad(b, 4, '*')", "[1, ");
    }

    @Test
    public void testRpad_13() {
        assertExpr("rpad(b, 2, '')", "[1");
    }

    @Test
    public void testRpad_14() {
        assertExpr("rpad(b, 2, null)", null);
    }

    @Test
    public void testRpad_15() {
        assertExpr("rpad(x, 5, x)", "foofo");
    }

    @Test
    public void testRpad_16() {
        assertExpr("rpad(x, 5, y)", "foo22");
    }

    @Test
    public void testRpad_17() {
        assertExpr("rpad(x, 5, z)", "foo3.");
    }

    @Test
    public void testRpad_18() {
        assertExpr("rpad(y, 5, x)", "2foof");
    }

    @Test
    public void testRpad_19() {
        assertExpr("rpad(z, 5, y)", "3.122");
    }

    @Test
    public void testArrayLength_1() {
        assertExpr("array_length([1,2,3])", 3L);
    }

    @Test
    public void testArrayLength_2() {
        assertExpr("array_length(a)", 4L);
    }

    @Test
    public void testArrayLength_3() {
        assertExpr("array_length(nestedArray)", 2L, typedBindings);
    }

    @Test
    public void testArrayOffset_1() {
        assertExpr("array_offset([1, 2, 3], 2)", 3L);
    }

    @Test
    public void testArrayOffset_2() {
        assertArrayExpr("array_offset([1, 2, 3], 3)", null);
    }

    @Test
    public void testArrayOffset_3() {
        assertArrayExpr("array_offset([1, 2, 3], -1)", null);
    }

    @Test
    public void testArrayOffset_4() {
        assertExpr("array_offset(a, 2)", "baz");
    }

    @Test
    public void testArrayOffset_5() {
        assertExpr("array_offset(nestedArray, 1)", ImmutableMap.of("x", 4L, "y", 6.6), typedBindings);
    }

    @Test
    public void testArrayOrdinal_1() {
        assertExpr("array_ordinal([1, 2, 3], 3)", 3L);
    }

    @Test
    public void testArrayOrdinal_2() {
        assertArrayExpr("array_ordinal([1, 2, 3], 4)", null);
    }

    @Test
    public void testArrayOrdinal_3() {
        assertArrayExpr("array_ordinal([1, 2, 3], 0)", null);
    }

    @Test
    public void testArrayOrdinal_4() {
        assertExpr("array_ordinal(a, 3)", "baz");
    }

    @Test
    public void testArrayOrdinal_5() {
        assertExpr("array_ordinal(nestedArray, 2)", ImmutableMap.of("x", 4L, "y", 6.6), typedBindings);
    }

    @Test
    public void testArrayOffsetOf_1() {
        assertExpr("array_offset_of([1, 2, 3], 3)", 2L);
    }

    @Test
    public void testArrayOffsetOf_2() {
        assertExpr("array_offset_of([1, 2, 3], 4)", null);
    }

    @Test
    public void testArrayOffsetOf_3() {
        assertExpr("array_offset_of(a, 'baz')", 2L);
    }

    @Test
    public void testArrayOrdinalOf_1() {
        assertExpr("array_ordinal_of([1, 2, 3], 3)", 3L);
    }

    @Test
    public void testArrayOrdinalOf_2() {
        assertExpr("array_ordinal_of([1, 2, 3], 4)", null);
    }

    @Test
    public void testArrayOrdinalOf_3() {
        assertExpr("array_ordinal_of(a, 'baz')", 3L);
    }

    @Test
    public void testScalarInArray_1() {
        assertExpr("scalar_in_array(2, [1, 2, 3])", 1L);
    }

    @Test
    public void testScalarInArray_2() {
        assertExpr("scalar_in_array(2.1, [1, 2, 3])", 0L);
    }

    @Test
    public void testScalarInArray_3() {
        assertExpr("scalar_in_array(2, [1.1, 2.1, 3.1])", 0L);
    }

    @Test
    public void testScalarInArray_4() {
        assertExpr("scalar_in_array(2, [1.1, 2.0, 3.1])", 1L);
    }

    @Test
    public void testScalarInArray_5() {
        assertExpr("scalar_in_array(4, [1, 2, 3])", 0L);
    }

    @Test
    public void testScalarInArray_6() {
        assertExpr("scalar_in_array(b, [3, 4])", 0L);
    }

    @Test
    public void testScalarInArray_7() {
        assertExpr("scalar_in_array(1, null)", null);
    }

    @Test
    public void testScalarInArray_8() {
        assertExpr("scalar_in_array(null, null)", null);
    }

    @Test
    public void testScalarInArray_9() {
        assertExpr("scalar_in_array(null, [1, null, 2])", 1L);
    }

    @Test
    public void testScalarInArray_10() {
        assertExpr("scalar_in_array(null, [1, 2])", null);
    }

    @Test
    public void testArrayContains_1() {
        assertExpr("array_contains([1, 2, 3], 2)", 1L);
    }

    @Test
    public void testArrayContains_2() {
        assertExpr("array_contains([1, 2, 3], 4)", 0L);
    }

    @Test
    public void testArrayContains_3() {
        assertExpr("array_contains([1, 2, 3], [2, 3])", 1L);
    }

    @Test
    public void testArrayContains_4() {
        assertExpr("array_contains([1, 2, 3], [3, 4])", 0L);
    }

    @Test
    public void testArrayContains_5() {
        assertExpr("array_contains(b, [3, 4])", 1L);
    }

    @Test
    public void testArrayContains_6() {
        assertExpr("array_contains(null, [3, 4])", null);
    }

    @Test
    public void testArrayContains_7() {
        assertExpr("array_contains(null, null)", null);
    }

    @Test
    public void testArrayContains_8() {
        assertExpr("array_contains([1, null, 2], null)", 1L);
    }

    @Test
    public void testArrayContains_9() {
        assertExpr("array_contains([1, null, 2], [null])", 1L);
    }

    @Test
    public void testArrayContains_10() {
        assertExpr("array_contains([1, 2], null)", 0L);
    }

    @Test
    public void testArrayOverlap_1() {
        assertExpr("array_overlap([1, 2, 3], [2, 4, 6])", 1L);
    }

    @Test
    public void testArrayOverlap_2() {
        assertExpr("array_overlap([1, 2, 3], [4, 5, 6])", 0L);
    }

    @Test
    public void testArrayOverlap_3() {
        assertExpr("array_overlap(null, [4, 5, 6])", null);
    }

    @Test
    public void testArrayOverlap_4() {
        assertExpr("array_overlap([4, null], [4, 5, 6])", 1L);
    }

    @Test
    public void testArrayOverlap_5() {
        assertExpr("array_overlap([4, 5, 6], null)", 0L);
    }

    @Test
    public void testArrayOverlap_6() {
        assertExpr("array_overlap([4, 5, 6], [null])", 0L);
    }

    @Test
    public void testArrayOverlap_7() {
        assertExpr("array_overlap([4, 5, null, 6], null)", 0L);
    }

    @Test
    public void testArrayOverlap_8() {
        assertExpr("array_overlap([4, 5, null, 6], [null])", 1L);
    }

    @Test
    public void testArrayToString_1() {
        assertExpr("array_to_string([1, 2, 3], ',')", "1,2,3");
    }

    @Test
    public void testArrayToString_2() {
        assertExpr("array_to_string([1], '|')", "1");
    }

    @Test
    public void testArrayToString_3() {
        assertExpr("array_to_string(a, '|')", "foo|bar|baz|foobar");
    }

    @Test
    public void testRoundWithNonNumericValuesShouldReturn0_1() {
        assertExpr("round(nan)", 0D);
    }

    @Test
    public void testRoundWithNonNumericValuesShouldReturn0_2() {
        assertExpr("round(nan, 5)", 0D);
    }

    @Test
    public void testRoundWithNonNumericValuesShouldReturn0_3() {
        assertExpr("round(inf)", Double.MAX_VALUE);
    }

    @Test
    public void testRoundWithNonNumericValuesShouldReturn0_4() {
        assertExpr("round(inf, 4)", Double.MAX_VALUE);
    }

    @Test
    public void testRoundWithNonNumericValuesShouldReturn0_5() {
        assertExpr("round(-inf)", -1 * Double.MAX_VALUE);
    }

    @Test
    public void testRoundWithNonNumericValuesShouldReturn0_6() {
        assertExpr("round(-inf, 3)", -1 * Double.MAX_VALUE);
    }

    @Test
    public void testRoundWithNonNumericValuesShouldReturn0_7() {
        assertExpr("round(-inf, -5)", -1 * Double.MAX_VALUE);
    }

    @Test
    public void testRoundWithNonNumericValuesShouldReturn0_8() {
        assertExpr("round(0/od)", 0D);
    }

    @Test
    public void testRoundWithNonNumericValuesShouldReturn0_9() {
        assertExpr("round(od/od)", 0D);
    }

    @Test
    public void testRoundWithNonNumericValuesShouldReturn0_10() {
        assertExpr("round(1/od)", Double.MAX_VALUE);
    }

    @Test
    public void testRoundWithNonNumericValuesShouldReturn0_11() {
        assertExpr("round(-1/od)", -1 * Double.MAX_VALUE);
    }

    @Test
    public void testRoundWithNonNumericValuesShouldReturn0_12() {
        assertExpr("round(0/of)", 0D);
    }

    @Test
    public void testRoundWithNonNumericValuesShouldReturn0_13() {
        assertExpr("round(of/of)", 0D);
    }

    @Test
    public void testRoundWithNonNumericValuesShouldReturn0_14() {
        assertExpr("round(1/of)", Double.MAX_VALUE);
    }

    @Test
    public void testRoundWithNonNumericValuesShouldReturn0_15() {
        assertExpr("round(-1/of)", -1 * Double.MAX_VALUE);
    }

    @Test
    public void testRoundWithLong_1() {
        assertExpr("round(y)", 2L);
    }

    @Test
    public void testRoundWithLong_2() {
        assertExpr("round(y, 2)", 2L);
    }

    @Test
    public void testRoundWithLong_3() {
        assertExpr("round(y, -1)", 0L);
    }

    @Test
    public void testRoundWithDouble_1() {
        assertExpr("round(d)", 35D);
    }

    @Test
    public void testRoundWithDouble_2() {
        assertExpr("round(d, 2)", 34.56D);
    }

    @Test
    public void testRoundWithDouble_3() {
        assertExpr("round(d, y)", 34.56D);
    }

    @Test
    public void testRoundWithDouble_4() {
        assertExpr("round(d, 1)", 34.6D);
    }

    @Test
    public void testRoundWithDouble_5() {
        assertExpr("round(d, -1)", 30D);
    }

    @Test
    public void testRoundWithFloat_1() {
        assertExpr("round(f)", 12D);
    }

    @Test
    public void testRoundWithFloat_2() {
        assertExpr("round(f, 2)", 12.34D);
    }

    @Test
    public void testRoundWithFloat_3() {
        assertExpr("round(f, y)", 12.34D);
    }

    @Test
    public void testRoundWithFloat_4() {
        assertExpr("round(f, 1)", 12.3D);
    }

    @Test
    public void testRoundWithFloat_5() {
        assertExpr("round(f, -1)", 10D);
    }

    @Test
    public void testRoundWithExtremeNumbers_1() {
        assertExpr("round(maxLong)", BigDecimal.valueOf(Long.MAX_VALUE).setScale(0, RoundingMode.HALF_UP).longValue());
    }

    @Test
    public void testRoundWithExtremeNumbers_2() {
        assertExpr("round(minLong)", BigDecimal.valueOf(Long.MIN_VALUE).setScale(0, RoundingMode.HALF_UP).longValue());
    }

    @Test
    public void testRoundWithExtremeNumbers_3() {
        assertExpr("round(maxLong + 1, 1)", BigDecimal.valueOf(Long.MIN_VALUE).setScale(1, RoundingMode.HALF_UP).longValue());
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

    @Test
    public void testSizeFormat_1() {
        assertExpr("human_readable_binary_byte_format(-1024)", "-1.00 KiB");
    }

    @Test
    public void testSizeFormat_2() {
        assertExpr("human_readable_binary_byte_format(1024)", "1.00 KiB");
    }

    @Test
    public void testSizeFormat_3() {
        assertExpr("human_readable_binary_byte_format(1024*1024)", "1.00 MiB");
    }

    @Test
    public void testSizeFormat_4() {
        assertExpr("human_readable_binary_byte_format(1024*1024*1024)", "1.00 GiB");
    }

    @Test
    public void testSizeFormat_5() {
        assertExpr("human_readable_binary_byte_format(1024*1024*1024*1024)", "1.00 TiB");
    }

    @Test
    public void testSizeFormat_6() {
        assertExpr("human_readable_binary_byte_format(1024*1024*1024*1024*1024)", "1.00 PiB");
    }

    @Test
    public void testSizeFormat_7() {
        assertExpr("human_readable_decimal_byte_format(-1000)", "-1.00 KB");
    }

    @Test
    public void testSizeFormat_8() {
        assertExpr("human_readable_decimal_byte_format(1000)", "1.00 KB");
    }

    @Test
    public void testSizeFormat_9() {
        assertExpr("human_readable_decimal_byte_format(1000*1000)", "1.00 MB");
    }

    @Test
    public void testSizeFormat_10() {
        assertExpr("human_readable_decimal_byte_format(1000*1000*1000)", "1.00 GB");
    }

    @Test
    public void testSizeFormat_11() {
        assertExpr("human_readable_decimal_byte_format(1000*1000*1000*1000)", "1.00 TB");
    }

    @Test
    public void testSizeFormat_12() {
        assertExpr("human_readable_decimal_format(-1000)", "-1.00 K");
    }

    @Test
    public void testSizeFormat_13() {
        assertExpr("human_readable_decimal_format(1000)", "1.00 K");
    }

    @Test
    public void testSizeFormat_14() {
        assertExpr("human_readable_decimal_format(1000*1000)", "1.00 M");
    }

    @Test
    public void testSizeFormat_15() {
        assertExpr("human_readable_decimal_format(1000*1000*1000)", "1.00 G");
    }

    @Test
    public void testSizeFormat_16() {
        assertExpr("human_readable_decimal_format(1000*1000*1000*1000)", "1.00 T");
    }

    @Test
    public void testSizeFormatWithDifferentPrecision_1() {
        assertExpr("human_readable_binary_byte_format(1024, 0)", "1 KiB");
    }

    @Test
    public void testSizeFormatWithDifferentPrecision_2() {
        assertExpr("human_readable_binary_byte_format(1024*1024, 1)", "1.0 MiB");
    }

    @Test
    public void testSizeFormatWithDifferentPrecision_3() {
        assertExpr("human_readable_binary_byte_format(1024*1024*1024, 2)", "1.00 GiB");
    }

    @Test
    public void testSizeFormatWithDifferentPrecision_4() {
        assertExpr("human_readable_binary_byte_format(1024*1024*1024*1024, 3)", "1.000 TiB");
    }

    @Test
    public void testSizeFormatWithDifferentPrecision_5() {
        assertExpr("human_readable_decimal_byte_format(1234, 0)", "1 KB");
    }

    @Test
    public void testSizeFormatWithDifferentPrecision_6() {
        assertExpr("human_readable_decimal_byte_format(1234*1000, 1)", "1.2 MB");
    }

    @Test
    public void testSizeFormatWithDifferentPrecision_7() {
        assertExpr("human_readable_decimal_byte_format(1234*1000*1000, 2)", "1.23 GB");
    }

    @Test
    public void testSizeFormatWithDifferentPrecision_8() {
        assertExpr("human_readable_decimal_byte_format(1234*1000*1000*1000, 3)", "1.234 TB");
    }

    @Test
    public void testSizeFormatWithDifferentPrecision_9() {
        assertExpr("human_readable_decimal_format(1234, 0)", "1 K");
    }

    @Test
    public void testSizeFormatWithDifferentPrecision_10() {
        assertExpr("human_readable_decimal_format(1234*1000,1)", "1.2 M");
    }

    @Test
    public void testSizeFormatWithDifferentPrecision_11() {
        assertExpr("human_readable_decimal_format(1234*1000*1000,2)", "1.23 G");
    }

    @Test
    public void testSizeFormatWithDifferentPrecision_12() {
        assertExpr("human_readable_decimal_format(1234*1000*1000*1000,3)", "1.234 T");
    }

    @Test
    public void testSizeFormatWithEdgeCases_1() {
        assertExpr("human_readable_binary_byte_format(nonexist)", null);
    }

    @Test
    public void testSizeFormatWithEdgeCases_2() {
        assertExpr("human_readable_binary_byte_format(f)", "12 B");
    }

    @Test
    public void testSizeFormatWithEdgeCases_3() {
        assertExpr("human_readable_binary_byte_format(nan)", "0 B");
    }

    @Test
    public void testSizeFormatWithEdgeCases_4() {
        assertExpr("human_readable_binary_byte_format(inf)", "8.00 EiB");
    }

    @Test
    public void testSizeFormatWithEdgeCases_5() {
        assertExpr("human_readable_binary_byte_format(-inf)", "-8.00 EiB");
    }

    @Test
    public void testSizeFormatWithEdgeCases_6() {
        assertExpr("human_readable_binary_byte_format(o)", "0 B");
    }

    @Test
    public void testSizeFormatWithEdgeCases_7() {
        assertExpr("human_readable_binary_byte_format(od)", "0 B");
    }

    @Test
    public void testSizeFormatWithEdgeCases_8() {
        assertExpr("human_readable_binary_byte_format(of)", "0 B");
    }

    @Test
    public void testSafeDivide_1() {
        assertExpr("safe_divide(3, 1)", 3L);
    }

    @Test
    public void testSafeDivide_2() {
        assertExpr("safe_divide(4.5, 2)", 2.25);
    }

    @Test
    public void testSafeDivide_3() {
        assertExpr("safe_divide(3, 0)", null);
    }

    @Test
    public void testSafeDivide_4() {
        assertExpr("safe_divide(1, 0.0)", null);
    }

    @Test
    public void testSafeDivide_5() {
        assertExpr("safe_divide(NaN, 0.0)", null);
    }

    @Test
    public void testSafeDivide_6() {
        assertExpr("safe_divide(0, NaN)", 0.0);
    }

    @Test
    public void testSafeDivide_7() {
        assertExpr("safe_divide(0, maxLong)", 0L);
    }

    @Test
    public void testSafeDivide_8() {
        assertExpr("safe_divide(maxLong,0)", null);
    }

    @Test
    public void testSafeDivide_9() {
        assertExpr("safe_divide(0.0, inf)", 0.0);
    }

    @Test
    public void testSafeDivide_10() {
        assertExpr("safe_divide(0.0, -inf)", -0.0);
    }

    @Test
    public void testSafeDivide_11() {
        assertExpr("safe_divide(0,0)", null);
    }

    @Test
    public void testDecodeBase64UTF_1() {
        assertExpr("decode_base64_utf8('aGVsbG8=')", "hello");
    }

    @Test
    public void testDecodeBase64UTF_2() {
        assertExpr("decode_base64_utf8('V2hlbiBhbiBvbmlvbiBpcyBjdXQsIGNlcnRhaW4gKGxhY2hyeW1hdG9yKSBjb21wb3VuZHMgYXJlIHJlbGVhc2VkIGNhdXNpbmcgdGhlIG5lcnZlcyBhcm91bmQgdGhlIGV5ZXMgKGxhY3JpbWFsIGdsYW5kcykgdG8gYmVjb21lIGlycml0YXRlZC4=')", "When an onion is cut, certain (lachrymator) compounds are released causing the nerves around the eyes (lacrimal glands) to become irritated.");
    }

    @Test
    public void testDecodeBase64UTF_3() {
        assertExpr("decode_base64_utf8('eyJ0ZXN0IjogMX0=')", "{\"test\": 1}");
    }

    @Test
    public void testDecodeBase64UTF_4() {
        assertExpr("decode_base64_utf8('')", "");
    }

    @Test
    public void testComplexDecodeNull_1() {
        assertExpr(StringUtils.format("complex_decode_base64('%s', null)", TypeStrategiesTest.NULLABLE_TEST_PAIR_TYPE.getComplexTypeName()), null);
    }

    @Test
    public void testComplexDecodeNull_2() {
        assertExpr(StringUtils.format("decode_base64_complex('%s', null)", TypeStrategiesTest.NULLABLE_TEST_PAIR_TYPE.getComplexTypeName()), null);
    }
}
