package org.apache.druid.segment.virtual;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.apache.druid.data.input.InputRow;
import org.apache.druid.data.input.MapBasedInputRow;
import org.apache.druid.data.input.Row;
import org.apache.druid.java.util.common.DateTimes;
import org.apache.druid.math.expr.ExprEval;
import org.apache.druid.math.expr.Parser;
import org.apache.druid.query.dimension.DefaultDimensionSpec;
import org.apache.druid.query.dimension.DimensionSpec;
import org.apache.druid.query.dimension.ExtractionDimensionSpec;
import org.apache.druid.query.expression.TestExprMacroTable;
import org.apache.druid.query.extraction.BucketExtractionFn;
import org.apache.druid.query.filter.DruidPredicateFactory;
import org.apache.druid.query.filter.DruidPredicateMatch;
import org.apache.druid.query.filter.StringPredicateDruidPredicateFactory;
import org.apache.druid.query.filter.ValueMatcher;
import org.apache.druid.query.monomorphicprocessing.RuntimeShapeInspector;
import org.apache.druid.segment.BaseFloatColumnValueSelector;
import org.apache.druid.segment.BaseLongColumnValueSelector;
import org.apache.druid.segment.BaseObjectColumnValueSelector;
import org.apache.druid.segment.ColumnSelectorFactory;
import org.apache.druid.segment.ColumnValueSelector;
import org.apache.druid.segment.ConstantDimensionSelector;
import org.apache.druid.segment.ConstantMultiValueDimensionSelector;
import org.apache.druid.segment.DimensionSelector;
import org.apache.druid.segment.IdLookup;
import org.apache.druid.segment.RowAdapters;
import org.apache.druid.segment.RowBasedColumnSelectorFactory;
import org.apache.druid.segment.column.ColumnCapabilities;
import org.apache.druid.segment.column.ColumnCapabilitiesImpl;
import org.apache.druid.segment.column.ColumnType;
import org.apache.druid.segment.column.RowSignature;
import org.apache.druid.segment.column.ValueType;
import org.apache.druid.segment.data.IndexedInts;
import org.apache.druid.testing.InitializedNullHandlingTest;
import org.junit.Assert;
import org.junit.Test;
import javax.annotation.Nullable;
import java.util.Arrays;

public class ExpressionVirtualColumnTest_Purified extends InitializedNullHandlingTest {

    private static final InputRow ROW0 = new MapBasedInputRow(DateTimes.of("2000-01-01T00:00:00").getMillis(), ImmutableList.of(), ImmutableMap.of());

    private static final InputRow ROW1 = new MapBasedInputRow(DateTimes.of("2000-01-01T00:00:00").getMillis(), ImmutableList.of(), ImmutableMap.of("x", 4));

    private static final InputRow ROW2 = new MapBasedInputRow(DateTimes.of("2000-01-01T02:00:00").getMillis(), ImmutableList.of(), ImmutableMap.of("x", 2.1, "y", 3L, "z", "foobar"));

    private static final InputRow ROW3 = new MapBasedInputRow(DateTimes.of("2000-01-02T01:00:00").getMillis(), ImmutableList.of(), ImmutableMap.of("x", 2L, "y", 3L, "z", "foobar"));

    private static final InputRow ROWMULTI = new MapBasedInputRow(DateTimes.of("2000-01-02T01:00:00").getMillis(), ImmutableList.of(), ImmutableMap.of("x", 2L, "y", 3L, "a", ImmutableList.of("a", "b", "c"), "b", ImmutableList.of("1", "2", "3"), "c", ImmutableList.of("4", "5", "6")));

    private static final InputRow ROWMULTI2 = new MapBasedInputRow(DateTimes.of("2000-01-02T01:00:00").getMillis(), ImmutableList.of(), ImmutableMap.of("x", 3L, "y", 4L, "a", ImmutableList.of("d", "e", "f"), "b", ImmutableList.of("3", "4", "5"), "c", ImmutableList.of("7", "8", "9")));

    private static final InputRow ROWMULTI3 = new MapBasedInputRow(DateTimes.of("2000-01-02T01:00:00").getMillis(), ImmutableList.of(), ImmutableMap.of("x", 3L, "y", 4L, "b", Arrays.asList("3", null, "5")));

    private static final ExpressionVirtualColumn X_PLUS_Y = new ExpressionVirtualColumn("expr", "x + y", ColumnType.FLOAT, TestExprMacroTable.INSTANCE);

    private static final ExpressionVirtualColumn CONSTANT_LIKE = new ExpressionVirtualColumn("expr", "like('foo', 'f%')", ColumnType.FLOAT, TestExprMacroTable.INSTANCE);

    private static final ExpressionVirtualColumn CONSTANT_NULL_ARITHMETIC = new ExpressionVirtualColumn("expr", "2.1 + null", ColumnType.FLOAT, TestExprMacroTable.INSTANCE);

    private static final ExpressionVirtualColumn Z_LIKE = new ExpressionVirtualColumn("expr", "like(z, 'f%')", ColumnType.FLOAT, TestExprMacroTable.INSTANCE);

    private static final ExpressionVirtualColumn Z_CONCAT_X = new ExpressionVirtualColumn("expr", "z + cast(x, 'string')", ColumnType.STRING, TestExprMacroTable.INSTANCE);

    private static final ExpressionVirtualColumn Z_CONCAT_NONEXISTENT = new ExpressionVirtualColumn("expr", "concat(z, nonexistent)", ColumnType.STRING, TestExprMacroTable.INSTANCE);

    private static final ExpressionVirtualColumn TIME_FLOOR = new ExpressionVirtualColumn("expr", "timestamp_floor(__time, 'P1D')", ColumnType.LONG, TestExprMacroTable.INSTANCE);

    private static final ExpressionVirtualColumn SCALE_LONG = new ExpressionVirtualColumn("expr", "x * 2", ColumnType.LONG, TestExprMacroTable.INSTANCE);

    private static final ExpressionVirtualColumn SCALE_FLOAT = new ExpressionVirtualColumn("expr", "x * 2", ColumnType.FLOAT, TestExprMacroTable.INSTANCE);

    private static final ExpressionVirtualColumn SCALE_LIST_IMPLICIT = new ExpressionVirtualColumn("expr", "b * 2", ColumnType.STRING, TestExprMacroTable.INSTANCE);

    private static final ExpressionVirtualColumn SCALE_LIST_EXPLICIT = new ExpressionVirtualColumn("expr", "map(b -> b * 2, b)", ColumnType.STRING, TestExprMacroTable.INSTANCE);

    private static final ExpressionVirtualColumn SCALE_LIST_SELF_IMPLICIT = new ExpressionVirtualColumn("expr", "b * b", ColumnType.STRING, TestExprMacroTable.INSTANCE);

    private static final ExpressionVirtualColumn SCALE_LIST_SELF_EXPLICIT = new ExpressionVirtualColumn("expr", "map(b -> b * b, b)", ColumnType.STRING, TestExprMacroTable.INSTANCE);

    private static final ThreadLocal<Row> CURRENT_ROW = new ThreadLocal<>();

    private static final ColumnSelectorFactory COLUMN_SELECTOR_FACTORY = RowBasedColumnSelectorFactory.create(RowAdapters.standardRow(), CURRENT_ROW::get, RowSignature.empty(), false, false);

    @Test
    public void testRequiredColumns_1() {
        Assert.assertEquals(ImmutableList.of("x", "y"), X_PLUS_Y.requiredColumns());
    }

    @Test
    public void testRequiredColumns_2() {
        Assert.assertEquals(ImmutableList.of(), CONSTANT_LIKE.requiredColumns());
    }

    @Test
    public void testRequiredColumns_3() {
        Assert.assertEquals(ImmutableList.of("z"), Z_LIKE.requiredColumns());
    }

    @Test
    public void testRequiredColumns_4() {
        Assert.assertEquals(ImmutableList.of("x", "z"), Z_CONCAT_X.requiredColumns());
    }
}
