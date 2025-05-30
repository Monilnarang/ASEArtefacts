package org.apache.druid.math.expr;

import com.google.common.collect.ImmutableMap;
import org.apache.druid.testing.InitializedNullHandlingTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class ApplyFunctionTest_Parameterized extends InitializedNullHandlingTest {

    private Expr.ObjectBinding bindings;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setup() {
        ImmutableMap.Builder<String, Object> builder = ImmutableMap.builder();
        builder.put("x", "foo");
        builder.put("y", 2);
        builder.put("z", 3.1);
        builder.put("a", new String[] { "foo", "bar", "baz", "foobar" });
        builder.put("b", new Long[] { 1L, 2L, 3L, 4L, 5L });
        builder.put("c", new Double[] { 3.1, 4.2, 5.3 });
        builder.put("d", new String[] { null });
        builder.put("e", new String[] { null, "foo", "bar" });
        builder.put("f", new String[0]);
        bindings = InputBindings.forMap(builder.build());
    }

    private void assertExpr(final String expression, final Object expectedResult) {
        final Expr expr = Parser.parse(expression, ExprMacroTable.nil());
        Assert.assertEquals(expression, expectedResult, expr.eval(bindings).value());
        final Expr exprNoFlatten = Parser.parse(expression, ExprMacroTable.nil(), false);
        final Expr roundTrip = Parser.parse(exprNoFlatten.stringify(), ExprMacroTable.nil());
        Assert.assertEquals(expr.stringify(), expectedResult, roundTrip.eval(bindings).value());
        final Expr roundTripFlatten = Parser.parse(expr.stringify(), ExprMacroTable.nil());
        Assert.assertEquals(expr.stringify(), expectedResult, roundTripFlatten.eval(bindings).value());
        Assert.assertEquals(expr.stringify(), roundTrip.stringify());
        Assert.assertEquals(expr.stringify(), roundTripFlatten.stringify());
        Assert.assertArrayEquals(expr.getCacheKey(), roundTrip.getCacheKey());
        Assert.assertArrayEquals(expr.getCacheKey(), roundTripFlatten.getCacheKey());
    }

    private void assertExpr(final String expression, final Object[] expectedResult) {
        final Expr expr = Parser.parse(expression, ExprMacroTable.nil());
        final Object[] result = expr.eval(bindings).asArray();
        if (expectedResult.length != 0 || result == null || result.length != 0) {
            Assert.assertArrayEquals(expression, expectedResult, result);
        }
        final Expr exprNoFlatten = Parser.parse(expression, ExprMacroTable.nil(), false);
        final Expr roundTrip = Parser.parse(exprNoFlatten.stringify(), ExprMacroTable.nil());
        final Object[] resultRoundTrip = roundTrip.eval(bindings).asArray();
        if (expectedResult.length != 0 || resultRoundTrip == null || resultRoundTrip.length != 0) {
            Assert.assertArrayEquals(expr.stringify(), expectedResult, resultRoundTrip);
        }
        final Expr roundTripFlatten = Parser.parse(expr.stringify(), ExprMacroTable.nil());
        final Object[] resultRoundTripFlatten = roundTripFlatten.eval(bindings).asArray();
        if (expectedResult.length != 0 || resultRoundTripFlatten == null || resultRoundTripFlatten.length != 0) {
            Assert.assertArrayEquals(expr.stringify(), expectedResult, resultRoundTripFlatten);
        }
        Assert.assertEquals(expr.stringify(), roundTrip.stringify());
        Assert.assertEquals(expr.stringify(), roundTripFlatten.stringify());
        Assert.assertArrayEquals(expr.getCacheKey(), roundTrip.getCacheKey());
        Assert.assertArrayEquals(expr.getCacheKey(), roundTripFlatten.getCacheKey());
    }

    private void assertExpr(final String expression, final Double[] expectedResult) {
        final Expr expr = Parser.parse(expression, ExprMacroTable.nil());
        Object[] result = expr.eval(bindings).asArray();
        Assert.assertEquals(expectedResult.length, result.length);
        for (int i = 0; i < result.length; i++) {
            Assert.assertEquals(expression, expectedResult[i], (Double) result[i], 0.00001);
        }
        final Expr exprNoFlatten = Parser.parse(expression, ExprMacroTable.nil(), false);
        final Expr roundTrip = Parser.parse(exprNoFlatten.stringify(), ExprMacroTable.nil());
        Object[] resultRoundTrip = (Object[]) roundTrip.eval(bindings).value();
        Assert.assertEquals(expectedResult.length, resultRoundTrip.length);
        for (int i = 0; i < resultRoundTrip.length; i++) {
            Assert.assertEquals(expression, expectedResult[i], (Double) resultRoundTrip[i], 0.00001);
        }
        final Expr roundTripFlatten = Parser.parse(expr.stringify(), ExprMacroTable.nil());
        Object[] resultRoundTripFlatten = (Object[]) roundTripFlatten.eval(bindings).value();
        Assert.assertEquals(expectedResult.length, resultRoundTripFlatten.length);
        for (int i = 0; i < resultRoundTripFlatten.length; i++) {
            Assert.assertEquals(expression, expectedResult[i], (Double) resultRoundTripFlatten[i], 0.00001);
        }
        Assert.assertEquals(expr.stringify(), roundTrip.stringify());
        Assert.assertEquals(expr.stringify(), roundTripFlatten.stringify());
        Assert.assertArrayEquals(expr.getCacheKey(), roundTrip.getCacheKey());
        Assert.assertArrayEquals(expr.getCacheKey(), roundTripFlatten.getCacheKey());
    }

    @ParameterizedTest
    @MethodSource("Provider_testAnyMatch_1_1to2_2to3_3to4_4")
    public void testAnyMatch_1_1to2_2to3_3to4_4(String param1, long param2) {
        assertExpr(param1, param2);
    }

    static public Stream<Arguments> Provider_testAnyMatch_1_1to2_2to3_3to4_4() {
        return Stream.of(arguments("any(x -> x > 3, [1, 2, 3, 4])", 1L), arguments("any(x -> x > 3, [1, 2, 3])", 0L), arguments("any(x -> x, map(x -> x > 3, [1, 2, 3, 4]))", 1L), arguments("any(x -> x, map(x -> x > 3, [1, 2, 3]))", 0L), arguments("all(x -> x > 0, [1, 2, 3, 4])", 1L), arguments("all(x -> x > 1, [1, 2, 3, 4])", 0L), arguments("all(x -> x, map(x -> x > 0, [1, 2, 3, 4]))", 1L), arguments("all(x -> x, map(x -> x > 1, [1, 2, 3, 4]))", 0L));
    }
}
