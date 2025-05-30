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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class IPv4AddressMatchExprMacroTest_Parameterized extends MacroTestBase {

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

    @ParameterizedTest
    @MethodSource("Provider_testMatchesPrefix_1_4")
    public void testMatchesPrefix_1_4(String param1, String param2) {
        Assert.assertTrue(eval(ExprEval.of(param1).toExpr(), ExprEval.of(param2).toExpr()));
    }

    static public Stream<Arguments> Provider_testMatchesPrefix_1_4() {
        return Stream.of(arguments("192.168.1.250", "192.168.1.251/31"), arguments("192.168.1.251", "192.168.1.251/32"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testMatchesPrefix_2to3")
    public void testMatchesPrefix_2to3(String param1, String param2) {
        Assert.assertFalse(eval(ExprEval.of(param1).toExpr(), ExprEval.of(param2).toExpr()));
    }

    static public Stream<Arguments> Provider_testMatchesPrefix_2to3() {
        return Stream.of(arguments("192.168.1.240", "192.168.1.251/31"), arguments("192.168.1.250", "192.168.1.251/32"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testMatchesPrefix_5_8")
    public void testMatchesPrefix_5_8(String param1, String param2) {
        Assert.assertTrue(eval(ExprEval.of(IPv4AddressExprUtils.parse(param2).longValue()).toExpr(), ExprEval.of(param1).toExpr()));
    }

    static public Stream<Arguments> Provider_testMatchesPrefix_5_8() {
        return Stream.of(arguments("192.168.1.251/31", "192.168.1.250"), arguments("192.168.1.251/32", "192.168.1.251"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testMatchesPrefix_6to7")
    public void testMatchesPrefix_6to7(String param1, String param2) {
        Assert.assertFalse(eval(ExprEval.of(IPv4AddressExprUtils.parse(param2).longValue()).toExpr(), ExprEval.of(param1).toExpr()));
    }

    static public Stream<Arguments> Provider_testMatchesPrefix_6to7() {
        return Stream.of(arguments("192.168.1.251/31", "192.168.1.240"), arguments("192.168.1.251/32", "192.168.1.250"));
    }
}
