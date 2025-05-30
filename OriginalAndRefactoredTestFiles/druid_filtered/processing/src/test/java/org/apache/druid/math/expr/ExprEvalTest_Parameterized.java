package org.apache.druid.math.expr;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.apache.druid.collections.SerializablePair;
import org.apache.druid.java.util.common.IAE;
import org.apache.druid.java.util.common.ISE;
import org.apache.druid.java.util.common.NonnullPair;
import org.apache.druid.java.util.common.StringUtils;
import org.apache.druid.segment.column.TypeStrategies;
import org.apache.druid.segment.column.TypeStrategiesTest;
import org.apache.druid.segment.column.Types;
import org.apache.druid.testing.InitializedNullHandlingTest;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class ExprEvalTest_Parameterized extends InitializedNullHandlingTest {

    private static int MAX_SIZE_BYTES = 1 << 13;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    ByteBuffer buffer = ByteBuffer.allocate(1 << 16);

    @BeforeClass
    public static void setup() {
        TypeStrategies.registerComplex(TypeStrategiesTest.NULLABLE_TEST_PAIR_TYPE.getComplexTypeName(), new TypeStrategiesTest.NullableLongPairTypeStrategy());
    }

    private void assertExpr(int position, Object expected) {
        assertExpr(position, ExprEval.bestEffortOf(expected));
    }

    private void assertExpr(int position, ExprEval expected) {
        assertExpr(position, expected, MAX_SIZE_BYTES);
    }

    private void assertExpr(int position, ExprEval expected, int maxSizeBytes) {
        ExprEval.serialize(buffer, position, expected.type(), expected, maxSizeBytes);
        if (expected.type().isArray()) {
            Assert.assertArrayEquals("deserialized value with buffer references allowed", expected.asArray(), ExprEval.deserialize(buffer, position, MAX_SIZE_BYTES, expected.type(), true).asArray());
            Assert.assertArrayEquals("deserialized value with buffer references not allowed", expected.asArray(), ExprEval.deserialize(buffer, position, MAX_SIZE_BYTES, expected.type(), false).asArray());
        } else {
            Assert.assertEquals("deserialized value with buffer references allowed", expected.value(), ExprEval.deserialize(buffer, position, MAX_SIZE_BYTES, expected.type(), true).value());
            Assert.assertEquals("deserialized value with buffer references not allowed", expected.value(), ExprEval.deserialize(buffer, position, MAX_SIZE_BYTES, expected.type(), false).value());
        }
    }

    @Test
    public void testStringSerde_3() {
        assertExpr(0, ExprEval.bestEffortOf(null));
    }

    @Test
    public void testLongSerde_3() {
        assertExpr(1234, ExprEval.ofLong(null));
    }

    @Test
    public void testDoubleSerde_3() {
        assertExpr(1234, ExprEval.ofDouble(null));
    }

    @ParameterizedTest
    @MethodSource("Provider_testStringSerde_1_1_1to2_2_2")
    public void testStringSerde_1_1_1to2_2_2(int param1, String param2) {
        assertExpr(param1, param2);
    }

    static public Stream<Arguments> Provider_testStringSerde_1_1_1to2_2_2() {
        return Stream.of(arguments(0, "hello"), arguments(1234, "hello"), arguments(0, 1L), arguments(1234, 1L), arguments(0, 1.123), arguments(1234, 1.123));
    }
}
