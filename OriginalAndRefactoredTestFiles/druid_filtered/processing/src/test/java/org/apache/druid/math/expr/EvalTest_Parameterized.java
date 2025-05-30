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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class EvalTest_Parameterized extends InitializedNullHandlingTest {

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

    @ParameterizedTest
    @MethodSource("Provider_testIsNotDistinctFrom_1_1")
    public void testIsNotDistinctFrom_1_1(long param1) {
        assertEquals(param1, new Function.IsNotDistinctFromFunc().apply(ImmutableList.of(new NullLongExpr(), new NullLongExpr()), InputBindings.nilBindings()).value());
    }

    static public Stream<Arguments> Provider_testIsNotDistinctFrom_1_1() {
        return Stream.of(arguments(1L), arguments(0L));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsNotDistinctFrom_2_2")
    public void testIsNotDistinctFrom_2_2(long param1, long param2) {
        assertEquals(param1, new Function.IsNotDistinctFromFunc().apply(ImmutableList.of(new LongExpr(param2), new NullLongExpr()), InputBindings.nilBindings()).value());
    }

    static public Stream<Arguments> Provider_testIsNotDistinctFrom_2_2() {
        return Stream.of(arguments(0L, 0L), arguments(1L, 0L));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsNotDistinctFrom_3_3")
    public void testIsNotDistinctFrom_3_3(long param1, long param2, long param3) {
        assertEquals(param1, new Function.IsNotDistinctFromFunc().apply(ImmutableList.of(new LongExpr(param2), new LongExpr(param3)), InputBindings.nilBindings()).value());
    }

    static public Stream<Arguments> Provider_testIsNotDistinctFrom_3_3() {
        return Stream.of(arguments(1L, 0L, 0L), arguments(0L, 0L, 0L));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsFalse_1_1_1_1")
    public void testIsFalse_1_1_1_1(long param1) {
        assertEquals(param1, new Function.IsFalseFunc().apply(ImmutableList.of(new NullLongExpr()), InputBindings.nilBindings()).value());
    }

    static public Stream<Arguments> Provider_testIsFalse_1_1_1_1() {
        return Stream.of(arguments(0L), arguments(0L), arguments(1L), arguments(1L));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsFalse_2_2_2_2to3_3_3_3")
    public void testIsFalse_2_2_2_2to3_3_3_3(long param1, long param2) {
        assertEquals(param1, new Function.IsFalseFunc().apply(ImmutableList.of(new LongExpr(param2)), InputBindings.nilBindings()).value());
    }

    static public Stream<Arguments> Provider_testIsFalse_2_2_2_2to3_3_3_3() {
        return Stream.of(arguments(1L, 0L), arguments(0L, 1L), arguments(0L, 0L), arguments(1L, 1L), arguments(0L, 0L), arguments(1L, 1L), arguments(1L, 0L), arguments(0L, 1L));
    }
}
