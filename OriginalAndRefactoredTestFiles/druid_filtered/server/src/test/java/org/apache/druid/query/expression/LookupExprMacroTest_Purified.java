package org.apache.druid.query.expression;

import com.google.common.collect.ImmutableMap;
import org.apache.druid.math.expr.Expr;
import org.apache.druid.math.expr.ExprMacroTable;
import org.apache.druid.math.expr.ExpressionType;
import org.apache.druid.math.expr.InputBindings;
import org.apache.druid.math.expr.Parser;
import org.apache.druid.testing.InitializedNullHandlingTest;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class LookupExprMacroTest_Purified extends InitializedNullHandlingTest {

    private static final Expr.ObjectBinding BINDINGS = InputBindings.forInputSuppliers(ImmutableMap.<String, InputBindings.InputSupplier<?>>builder().put("x", InputBindings.inputSupplier(ExpressionType.STRING, () -> "foo")).build());

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private void assertExpr(final String expression, final Object expectedResult) {
        final Expr expr = Parser.parse(expression, LookupEnabledTestExprMacroTable.INSTANCE);
        Assert.assertEquals(expression, expectedResult, expr.eval(BINDINGS).value());
        final Expr exprNotFlattened = Parser.parse(expression, LookupEnabledTestExprMacroTable.INSTANCE, false);
        final Expr roundTripNotFlattened = Parser.parse(exprNotFlattened.stringify(), LookupEnabledTestExprMacroTable.INSTANCE);
        Assert.assertEquals(exprNotFlattened.stringify(), expectedResult, roundTripNotFlattened.eval(BINDINGS).value());
        final Expr roundTrip = Parser.parse(expr.stringify(), LookupEnabledTestExprMacroTable.INSTANCE);
        Assert.assertEquals(exprNotFlattened.stringify(), expectedResult, roundTrip.eval(BINDINGS).value());
    }

    @Test
    public void testLookupMissingValue_1() {
        assertExpr("lookup(y, 'lookyloo', 'N/A')", "N/A");
    }

    @Test
    public void testLookupMissingValue_2() {
        assertExpr("lookup(y, 'lookyloo', null)", null);
    }
}
