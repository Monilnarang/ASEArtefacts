package org.apache.druid.msq.querykit.common;

import com.google.common.collect.ImmutableList;
import org.apache.druid.frame.key.KeyColumn;
import org.apache.druid.frame.key.KeyOrder;
import org.apache.druid.math.expr.ExprMacroTable;
import org.apache.druid.segment.join.JoinConditionAnalysis;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class SortMergeJoinFrameProcessorFactoryTest_Parameterized {

    @Test
    public void test_toKeyColumns_1() {
        Assert.assertEquals(ImmutableList.of(ImmutableList.of(new KeyColumn("x", KeyOrder.ASCENDING)), ImmutableList.of(new KeyColumn("y", KeyOrder.ASCENDING))), SortMergeJoinFrameProcessorFactory.toKeyColumns(JoinConditionAnalysis.forExpression("x == \"j.y\"", "j.", ExprMacroTable.nil())));
    }

    @Test
    public void test_toKeyColumns_2() {
        Assert.assertEquals(ImmutableList.of(ImmutableList.of(), ImmutableList.of()), SortMergeJoinFrameProcessorFactory.toKeyColumns(JoinConditionAnalysis.forExpression("1", "j.", ExprMacroTable.nil())));
    }

    @ParameterizedTest
    @MethodSource("Provider_test_toKeyColumns_3to6")
    public void test_toKeyColumns_3to6(String param1, String param2, String param3, String param4, String param5, String param6) {
        Assert.assertEquals(ImmutableList.of(ImmutableList.of(new KeyColumn(param3, KeyOrder.ASCENDING), new KeyColumn(param4, KeyOrder.ASCENDING)), ImmutableList.of(new KeyColumn(param5, KeyOrder.ASCENDING), new KeyColumn(param6, KeyOrder.ASCENDING))), SortMergeJoinFrameProcessorFactory.toKeyColumns(JoinConditionAnalysis.forExpression(param1, param2, ExprMacroTable.nil())));
    }

    static public Stream<Arguments> Provider_test_toKeyColumns_3to6() {
        return Stream.of(arguments("x == \"j.y\" && a == \"j.b\"", "j.", "x", "a", "y", "b"), arguments("x == \"j.y\" && notdistinctfrom(a, \"j.b\")", "j.", "x", "a", "y", "b"), arguments("notdistinctfrom(x, \"j.y\") && a == \"j.b\"", "j.", "x", "a", "y", "b"), arguments("notdistinctfrom(x, \"j.y\") && notdistinctfrom(a, \"j.b\")", "j.", "x", "a", "y", "b"));
    }
}
