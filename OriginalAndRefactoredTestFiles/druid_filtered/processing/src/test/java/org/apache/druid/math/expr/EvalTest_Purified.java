package org.apache.druid.math.expr;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import junitparams.converters.Nullable;
import org.apache.druid.collections.SerializablePair;
import org.apache.druid.java.util.common.IAE;
import org.apache.druid.java.util.common.StringUtils;
import org.apache.druid.segment.column.TypeStrategies;
import org.apache.druid.segment.column.TypeStrategiesTest;
import org.apache.druid.segment.nested.StructuredData;
import org.apache.druid.testing.InitializedNullHandlingTest;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class EvalTest_Purified extends InitializedNullHandlingTest {

    @BeforeClass
    public static void setupClass() {
        TypeStrategies.registerComplex(TypeStrategiesTest.NULLABLE_TEST_PAIR_TYPE.getComplexTypeName(), new TypeStrategiesTest.NullableLongPairTypeStrategy());
    }

    private long evalLong(String x, Expr.ObjectBinding bindings) {
        ExprEval ret = eval(x, bindings);
        assertEquals(ExpressionType.LONG, ret.type());
        return ret.asLong();
    }

    private double evalDouble(String x, Expr.ObjectBinding bindings) {
        ExprEval ret = eval(x, bindings);
        assertEquals(ExpressionType.DOUBLE, ret.type());
        return ret.asDouble();
    }

    private ExprEval eval(String x, Expr.ObjectBinding bindings) {
        return Parser.parse(x, ExprMacroTable.nil()).eval(bindings);
    }

    private void assertBestEffortOf(@Nullable Object val, ExpressionType expectedType, @Nullable Object expectedValue) {
        ExprEval eval = ExprEval.bestEffortOf(val);
        Assert.assertEquals(expectedType, eval.type());
        if (eval.type().isArray()) {
            Assert.assertArrayEquals((Object[]) expectedValue, eval.asArray());
        } else {
            Assert.assertEquals(expectedValue, eval.value());
        }
        eval = ExprEval.ofType(eval.type(), val);
        Assert.assertEquals(expectedType, eval.type());
        if (eval.type().isArray()) {
            Assert.assertArrayEquals((Object[]) expectedValue, eval.asArray());
        } else {
            Assert.assertEquals(expectedValue, eval.value());
        }
    }

    @Test
    public void testIsNotDistinctFrom_1() {
        assertEquals(1L, new Function.IsNotDistinctFromFunc().apply(ImmutableList.of(new NullLongExpr(), new NullLongExpr()), InputBindings.nilBindings()).value());
    }

    @Test
    public void testIsNotDistinctFrom_2() {
        assertEquals(0L, new Function.IsNotDistinctFromFunc().apply(ImmutableList.of(new LongExpr(0L), new NullLongExpr()), InputBindings.nilBindings()).value());
    }

    @Test
    public void testIsNotDistinctFrom_3() {
        assertEquals(1L, new Function.IsNotDistinctFromFunc().apply(ImmutableList.of(new LongExpr(0L), new LongExpr(0L)), InputBindings.nilBindings()).value());
    }

    @Test
    public void testIsDistinctFrom_1() {
        assertEquals(0L, new Function.IsDistinctFromFunc().apply(ImmutableList.of(new NullLongExpr(), new NullLongExpr()), InputBindings.nilBindings()).value());
    }

    @Test
    public void testIsDistinctFrom_2() {
        assertEquals(1L, new Function.IsDistinctFromFunc().apply(ImmutableList.of(new LongExpr(0L), new NullLongExpr()), InputBindings.nilBindings()).value());
    }

    @Test
    public void testIsDistinctFrom_3() {
        assertEquals(0L, new Function.IsDistinctFromFunc().apply(ImmutableList.of(new LongExpr(0L), new LongExpr(0L)), InputBindings.nilBindings()).value());
    }

    @Test
    public void testIsFalse_1() {
        assertEquals(0L, new Function.IsFalseFunc().apply(ImmutableList.of(new NullLongExpr()), InputBindings.nilBindings()).value());
    }

    @Test
    public void testIsFalse_2() {
        assertEquals(1L, new Function.IsFalseFunc().apply(ImmutableList.of(new LongExpr(0L)), InputBindings.nilBindings()).value());
    }

    @Test
    public void testIsFalse_3() {
        assertEquals(0L, new Function.IsFalseFunc().apply(ImmutableList.of(new LongExpr(1L)), InputBindings.nilBindings()).value());
    }

    @Test
    public void testIsTrue_1() {
        assertEquals(0L, new Function.IsTrueFunc().apply(ImmutableList.of(new NullLongExpr()), InputBindings.nilBindings()).value());
    }

    @Test
    public void testIsTrue_2() {
        assertEquals(0L, new Function.IsTrueFunc().apply(ImmutableList.of(new LongExpr(0L)), InputBindings.nilBindings()).value());
    }

    @Test
    public void testIsTrue_3() {
        assertEquals(1L, new Function.IsTrueFunc().apply(ImmutableList.of(new LongExpr(1L)), InputBindings.nilBindings()).value());
    }

    @Test
    public void testIsNotFalse_1() {
        assertEquals(1L, new Function.IsNotFalseFunc().apply(ImmutableList.of(new NullLongExpr()), InputBindings.nilBindings()).value());
    }

    @Test
    public void testIsNotFalse_2() {
        assertEquals(0L, new Function.IsNotFalseFunc().apply(ImmutableList.of(new LongExpr(0L)), InputBindings.nilBindings()).value());
    }

    @Test
    public void testIsNotFalse_3() {
        assertEquals(1L, new Function.IsNotFalseFunc().apply(ImmutableList.of(new LongExpr(1L)), InputBindings.nilBindings()).value());
    }

    @Test
    public void testIsNotTrue_1() {
        assertEquals(1L, new Function.IsNotTrueFunc().apply(ImmutableList.of(new NullLongExpr()), InputBindings.nilBindings()).value());
    }

    @Test
    public void testIsNotTrue_2() {
        assertEquals(1L, new Function.IsNotTrueFunc().apply(ImmutableList.of(new LongExpr(0L)), InputBindings.nilBindings()).value());
    }

    @Test
    public void testIsNotTrue_3() {
        assertEquals(0L, new Function.IsNotTrueFunc().apply(ImmutableList.of(new LongExpr(1L)), InputBindings.nilBindings()).value());
    }
}
