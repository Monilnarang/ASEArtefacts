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

public class DruidExpressionTest_Purified extends InitializedNullHandlingTest {

    @Test
    public void test_doubleLiteral_asString_1() {
        Assert.assertEquals("0.0", DruidExpression.doubleLiteral(0));
    }

    @Test
    public void test_doubleLiteral_asString_2() {
        Assert.assertEquals("-2.0", DruidExpression.doubleLiteral(-2));
    }

    @Test
    public void test_doubleLiteral_asString_3() {
        Assert.assertEquals("2.0", DruidExpression.doubleLiteral(2));
    }

    @Test
    public void test_doubleLiteral_asString_4() {
        Assert.assertEquals("2.1", DruidExpression.doubleLiteral(2.1));
    }

    @Test
    public void test_doubleLiteral_asString_5() {
        Assert.assertEquals("2.12345678", DruidExpression.doubleLiteral(2.12345678));
    }

    @Test
    public void test_doubleLiteral_asString_6() {
        Assert.assertEquals("2.2E122", DruidExpression.doubleLiteral(2.2e122));
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
    public void test_longLiteral_asString_1() {
        Assert.assertEquals("0", DruidExpression.longLiteral(0));
    }

    @Test
    public void test_longLiteral_asString_2() {
        Assert.assertEquals("-2", DruidExpression.longLiteral(-2));
    }

    @Test
    public void test_longLiteral_asString_3() {
        Assert.assertEquals("2", DruidExpression.longLiteral(2));
    }

    @Test
    public void test_longLiteral_asString_4() {
        Assert.assertEquals("9223372036854775807", DruidExpression.longLiteral(Long.MAX_VALUE));
    }

    @Test
    public void test_longLiteral_asString_5() {
        Assert.assertEquals("-9223372036854775808", DruidExpression.longLiteral(Long.MIN_VALUE));
    }
}
