package org.apache.druid.msq.querykit.common;

import com.google.common.collect.ImmutableList;
import org.apache.druid.frame.key.KeyColumn;
import org.apache.druid.frame.key.KeyOrder;
import org.apache.druid.math.expr.ExprMacroTable;
import org.apache.druid.segment.join.JoinConditionAnalysis;
import org.junit.Assert;
import org.junit.Test;

public class SortMergeJoinFrameProcessorFactoryTest_Purified {

    @Test
    public void test_toKeyColumns_1() {
        Assert.assertEquals(ImmutableList.of(ImmutableList.of(new KeyColumn("x", KeyOrder.ASCENDING)), ImmutableList.of(new KeyColumn("y", KeyOrder.ASCENDING))), SortMergeJoinFrameProcessorFactory.toKeyColumns(JoinConditionAnalysis.forExpression("x == \"j.y\"", "j.", ExprMacroTable.nil())));
    }

    @Test
    public void test_toKeyColumns_2() {
        Assert.assertEquals(ImmutableList.of(ImmutableList.of(), ImmutableList.of()), SortMergeJoinFrameProcessorFactory.toKeyColumns(JoinConditionAnalysis.forExpression("1", "j.", ExprMacroTable.nil())));
    }

    @Test
    public void test_toKeyColumns_3() {
        Assert.assertEquals(ImmutableList.of(ImmutableList.of(new KeyColumn("x", KeyOrder.ASCENDING), new KeyColumn("a", KeyOrder.ASCENDING)), ImmutableList.of(new KeyColumn("y", KeyOrder.ASCENDING), new KeyColumn("b", KeyOrder.ASCENDING))), SortMergeJoinFrameProcessorFactory.toKeyColumns(JoinConditionAnalysis.forExpression("x == \"j.y\" && a == \"j.b\"", "j.", ExprMacroTable.nil())));
    }

    @Test
    public void test_toKeyColumns_4() {
        Assert.assertEquals(ImmutableList.of(ImmutableList.of(new KeyColumn("x", KeyOrder.ASCENDING), new KeyColumn("a", KeyOrder.ASCENDING)), ImmutableList.of(new KeyColumn("y", KeyOrder.ASCENDING), new KeyColumn("b", KeyOrder.ASCENDING))), SortMergeJoinFrameProcessorFactory.toKeyColumns(JoinConditionAnalysis.forExpression("x == \"j.y\" && notdistinctfrom(a, \"j.b\")", "j.", ExprMacroTable.nil())));
    }

    @Test
    public void test_toKeyColumns_5() {
        Assert.assertEquals(ImmutableList.of(ImmutableList.of(new KeyColumn("x", KeyOrder.ASCENDING), new KeyColumn("a", KeyOrder.ASCENDING)), ImmutableList.of(new KeyColumn("y", KeyOrder.ASCENDING), new KeyColumn("b", KeyOrder.ASCENDING))), SortMergeJoinFrameProcessorFactory.toKeyColumns(JoinConditionAnalysis.forExpression("notdistinctfrom(x, \"j.y\") && a == \"j.b\"", "j.", ExprMacroTable.nil())));
    }

    @Test
    public void test_toKeyColumns_6() {
        Assert.assertEquals(ImmutableList.of(ImmutableList.of(new KeyColumn("x", KeyOrder.ASCENDING), new KeyColumn("a", KeyOrder.ASCENDING)), ImmutableList.of(new KeyColumn("y", KeyOrder.ASCENDING), new KeyColumn("b", KeyOrder.ASCENDING))), SortMergeJoinFrameProcessorFactory.toKeyColumns(JoinConditionAnalysis.forExpression("notdistinctfrom(x, \"j.y\") && notdistinctfrom(a, \"j.b\")", "j.", ExprMacroTable.nil())));
    }
}
