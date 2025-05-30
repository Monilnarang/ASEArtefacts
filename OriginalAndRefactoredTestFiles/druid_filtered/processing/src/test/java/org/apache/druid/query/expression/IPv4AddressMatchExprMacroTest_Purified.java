package org.apache.druid.query.expression;

import org.apache.druid.math.expr.Expr;
import org.apache.druid.math.expr.ExprEval;
import org.apache.druid.math.expr.ExprMacroTable;
import org.apache.druid.math.expr.ExpressionValidationException;
import org.apache.druid.math.expr.InputBindings;
import org.apache.druid.math.expr.Parser;
import org.junit.Assert;
import org.junit.Test;
import java.util.Arrays;
import java.util.Collections;

public class IPv4AddressMatchExprMacroTest_Purified extends MacroTestBase {

    private static final Expr IPV4 = ExprEval.of("192.168.0.1").toExpr();

    private static final Expr IPV4_LONG = ExprEval.of(3232235521L).toExpr();

    private static final Expr IPV4_UINT = ExprEval.of("3232235521").toExpr();

    private static final Expr IPV4_NETWORK = ExprEval.of("192.168.0.0").toExpr();

    private static final Expr IPV4_BROADCAST = ExprEval.of("192.168.255.255").toExpr();

    private static final Expr IPV6_COMPATIBLE = ExprEval.of("::192.168.0.1").toExpr();

    private static final Expr IPV6_MAPPED = ExprEval.of("::ffff:192.168.0.1").toExpr();

    private static final Expr SUBNET_192_168 = ExprEval.of("192.168.0.0/16").toExpr();

    private static final Expr SUBNET_10 = ExprEval.of("10.0.0.0/8").toExpr();

    private static final Expr NOT_LITERAL = Parser.parse("\"notliteral\"", ExprMacroTable.nil());

    public IPv4AddressMatchExprMacroTest() {
        super(new IPv4AddressMatchExprMacro());
    }

    private boolean eval(Expr... args) {
        Expr expr = apply(Arrays.asList(args));
        ExprEval eval = expr.eval(InputBindings.nilBindings());
        return eval.asBoolean();
    }

    @Test
    public void testMatchesPrefix_1() {
        Assert.assertTrue(eval(ExprEval.of("192.168.1.250").toExpr(), ExprEval.of("192.168.1.251/31").toExpr()));
    }

    @Test
    public void testMatchesPrefix_2() {
        Assert.assertFalse(eval(ExprEval.of("192.168.1.240").toExpr(), ExprEval.of("192.168.1.251/31").toExpr()));
    }

    @Test
    public void testMatchesPrefix_3() {
        Assert.assertFalse(eval(ExprEval.of("192.168.1.250").toExpr(), ExprEval.of("192.168.1.251/32").toExpr()));
    }

    @Test
    public void testMatchesPrefix_4() {
        Assert.assertTrue(eval(ExprEval.of("192.168.1.251").toExpr(), ExprEval.of("192.168.1.251/32").toExpr()));
    }

    @Test
    public void testMatchesPrefix_5() {
        Assert.assertTrue(eval(ExprEval.of(IPv4AddressExprUtils.parse("192.168.1.250").longValue()).toExpr(), ExprEval.of("192.168.1.251/31").toExpr()));
    }

    @Test
    public void testMatchesPrefix_6() {
        Assert.assertFalse(eval(ExprEval.of(IPv4AddressExprUtils.parse("192.168.1.240").longValue()).toExpr(), ExprEval.of("192.168.1.251/31").toExpr()));
    }

    @Test
    public void testMatchesPrefix_7() {
        Assert.assertFalse(eval(ExprEval.of(IPv4AddressExprUtils.parse("192.168.1.250").longValue()).toExpr(), ExprEval.of("192.168.1.251/32").toExpr()));
    }

    @Test
    public void testMatchesPrefix_8() {
        Assert.assertTrue(eval(ExprEval.of(IPv4AddressExprUtils.parse("192.168.1.251").longValue()).toExpr(), ExprEval.of("192.168.1.251/32").toExpr()));
    }
}
