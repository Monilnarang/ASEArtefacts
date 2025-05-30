package org.apache.druid.segment.join;

import com.google.common.collect.ImmutableList;
import org.apache.druid.math.expr.ExprMacroTable;
import org.apache.druid.query.InlineDataSource;
import org.apache.druid.query.TableDataSource;
import org.apache.druid.segment.column.ColumnType;
import org.apache.druid.segment.column.RowSignature;
import org.apache.druid.segment.join.table.IndexedTableJoinable;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import java.util.Optional;

public class InlineJoinableFactoryTest_Purified {

    private static final String PREFIX = "j.";

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private final InlineJoinableFactory factory = new InlineJoinableFactory();

    private final InlineDataSource inlineDataSource = InlineDataSource.fromIterable(ImmutableList.of(new Object[] { "foo", 1L }, new Object[] { "bar", 2L }), RowSignature.builder().add("str", ColumnType.STRING).add("long", ColumnType.LONG).build());

    private static JoinConditionAnalysis makeCondition(final String condition) {
        return JoinConditionAnalysis.forExpression(condition, PREFIX, ExprMacroTable.nil());
    }

    @Test
    public void testIsDirectlyJoinable_1() {
        Assert.assertTrue(factory.isDirectlyJoinable(inlineDataSource));
    }

    @Test
    public void testIsDirectlyJoinable_2() {
        Assert.assertFalse(factory.isDirectlyJoinable(new TableDataSource("foo")));
    }
}
