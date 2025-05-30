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

public class SleepExprTest_Purified extends InitializedNullHandlingTest {

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

    @Test
    public void testSleep_1() {
        assertExpr("sleep(1)");
    }

    @Test
    public void testSleep_2() {
        assertExpr("sleep(0.5)");
    }

    @Test
    public void testSleep_3() {
        assertExpr("sleep(null)");
    }

    @Test
    public void testSleep_4() {
        assertExpr("sleep(0)");
    }

    @Test
    public void testSleep_5() {
        assertExpr("sleep(-1)");
    }

    @Test
    public void testSleep_6() {
        assertTimeElapsed("sleep(1)", 1000);
    }

    @Test
    public void testSleep_7() {
        assertTimeElapsed("sleep(0.5)", 500);
    }

    @Test
    public void testSleep_8() {
        assertTimeElapsed("sleep(null)", 0);
    }

    @Test
    public void testSleep_9() {
        assertTimeElapsed("sleep(0)", 0);
    }

    @Test
    public void testSleep_10() {
        assertTimeElapsed("sleep(-1)", 0);
    }
}
