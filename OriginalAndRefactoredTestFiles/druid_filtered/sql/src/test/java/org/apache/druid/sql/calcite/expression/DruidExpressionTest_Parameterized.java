package org.apache.druid.sql.calcite.expression;

import org.apache.druid.math.expr.Expr;
import org.apache.druid.math.expr.ExprMacroTable;
import org.apache.druid.math.expr.ExpressionType;
import org.apache.druid.math.expr.Parser;
import org.apache.druid.segment.column.ColumnType;
import org.apache.druid.testing.InitializedNullHandlingTest;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class DruidExpressionTest_Parameterized extends InitializedNullHandlingTest {

    @Test
    public void test_doubleLiteral_asString_2() {
        Assert.assertEquals("-2.0", DruidExpression.doubleLiteral(-2));
    }

    @Test
    public void test_doubleLiteral_asString_7() {
        Assert.assertEquals("NaN", DruidExpression.doubleLiteral(Double.NaN));
    }

    @Test
    public void test_doubleLiteral_asString_8() {
        Assert.assertEquals("Infinity", DruidExpression.doubleLiteral(Double.POSITIVE_INFINITY));
    }

    @Test
    public void test_doubleLiteral_asString_9() {
        Assert.assertEquals("-Infinity", DruidExpression.doubleLiteral(Double.NEGATIVE_INFINITY));
    }

    @Test
    public void test_doubleLiteral_asString_10() {
        Assert.assertEquals("4.9E-324", DruidExpression.doubleLiteral(Double.MIN_VALUE));
    }

    @Test
    public void test_doubleLiteral_asString_11() {
        Assert.assertEquals("1.7976931348623157E308", DruidExpression.doubleLiteral(Double.MAX_VALUE));
    }

    @Test
    public void test_doubleLiteral_asString_12() {
        Assert.assertEquals("2.2250738585072014E-308", DruidExpression.doubleLiteral(Double.MIN_NORMAL));
    }

    @Test
    public void test_longLiteral_asString_2() {
        Assert.assertEquals("-2", DruidExpression.longLiteral(-2));
    }

    @Test
    public void test_longLiteral_asString_4() {
        Assert.assertEquals("9223372036854775807", DruidExpression.longLiteral(Long.MAX_VALUE));
    }

    @Test
    public void test_longLiteral_asString_5() {
        Assert.assertEquals("-9223372036854775808", DruidExpression.longLiteral(Long.MIN_VALUE));
    }

    @ParameterizedTest
    @MethodSource("Provider_test_doubleLiteral_asString_1_3to6")
    public void test_doubleLiteral_asString_1_3to6(double param1, int param2) {
        Assert.assertEquals(param1, DruidExpression.doubleLiteral(param2));
    }

    static public Stream<Arguments> Provider_test_doubleLiteral_asString_1_3to6() {
        return Stream.of(arguments(0.0, 0), arguments(2.0, 2), arguments(2.1, 2.1), arguments(2.12345678, 2.12345678), arguments(2.2E122, 2.2e122));
    }

    @ParameterizedTest
    @MethodSource("Provider_test_longLiteral_asString_1_3")
    public void test_longLiteral_asString_1_3(int param1, int param2) {
        Assert.assertEquals(param1, DruidExpression.longLiteral(param2));
    }

    static public Stream<Arguments> Provider_test_longLiteral_asString_1_3() {
        return Stream.of(arguments(0, 0), arguments(2, 2));
    }
}
