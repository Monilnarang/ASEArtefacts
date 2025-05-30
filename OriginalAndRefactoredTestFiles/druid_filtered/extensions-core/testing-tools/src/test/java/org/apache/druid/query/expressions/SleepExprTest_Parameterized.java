package org.apache.druid.query.expressions;

import org.apache.druid.java.util.common.StringUtils;
import org.apache.druid.math.expr.Expr;
import org.apache.druid.math.expr.ExprMacroTable;
import org.apache.druid.math.expr.InputBindings;
import org.apache.druid.math.expr.Parser;
import org.apache.druid.testing.InitializedNullHandlingTest;
import org.junit.Assert;
import org.junit.Test;
import java.util.Collections;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class SleepExprTest_Parameterized extends InitializedNullHandlingTest {

    private final ExprMacroTable exprMacroTable = new ExprMacroTable(Collections.singletonList(new SleepExprMacro()));

    private void assertTimeElapsed(String expression, long expectedTimeElapsedMs) {
        final long detla = 50;
        final long before = System.currentTimeMillis();
        final Expr expr = Parser.parse(expression, exprMacroTable);
        expr.eval(InputBindings.nilBindings()).value();
        final long after = System.currentTimeMillis();
        final long elapsed = after - before;
        Assert.assertTrue(StringUtils.format("Expected [%s], but actual elapsed was [%s]", expectedTimeElapsedMs, elapsed), elapsed >= expectedTimeElapsedMs && elapsed < expectedTimeElapsedMs + detla);
    }

    private void assertExpr(final String expression) {
        final Expr expr = Parser.parse(expression, exprMacroTable);
        Assert.assertNull(expression, expr.eval(InputBindings.nilBindings()).value());
        final Expr exprNoFlatten = Parser.parse(expression, exprMacroTable, false);
        final Expr roundTrip = Parser.parse(exprNoFlatten.stringify(), exprMacroTable);
        Assert.assertNull(expr.stringify(), roundTrip.eval(InputBindings.nilBindings()).value());
        final Expr roundTripFlatten = Parser.parse(expr.stringify(), exprMacroTable);
        Assert.assertNull(expr.stringify(), roundTripFlatten.eval(InputBindings.nilBindings()).value());
        Assert.assertEquals(expr.stringify(), roundTrip.stringify());
        Assert.assertEquals(expr.stringify(), roundTripFlatten.stringify());
        Assert.assertArrayEquals(expr.getCacheKey(), roundTrip.getCacheKey());
        Assert.assertArrayEquals(expr.getCacheKey(), roundTripFlatten.getCacheKey());
    }

    @ParameterizedTest
    @MethodSource("Provider_testSleep_1to5")
    public void testSleep_1to5(String param1) {
        assertExpr(param1);
    }

    static public Stream<Arguments> Provider_testSleep_1to5() {
        return Stream.of(arguments("sleep(1)"), arguments("sleep(0.5)"), arguments("sleep(null)"), arguments("sleep(0)"), arguments("sleep(-1)"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testSleep_6to10")
    public void testSleep_6to10(String param1, int param2) {
        assertTimeElapsed(param1, param2);
    }

    static public Stream<Arguments> Provider_testSleep_6to10() {
        return Stream.of(arguments("sleep(1)", 1000), arguments("sleep(0.5)", 500), arguments("sleep(null)", 0), arguments("sleep(0)", 0), arguments("sleep(-1)", 0));
    }
}
