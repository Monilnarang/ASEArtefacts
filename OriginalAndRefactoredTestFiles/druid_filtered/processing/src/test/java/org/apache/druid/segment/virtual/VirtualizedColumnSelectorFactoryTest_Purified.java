package org.apache.druid.segment.virtual;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.apache.druid.data.input.MapBasedRow;
import org.apache.druid.math.expr.ExprMacroTable;
import org.apache.druid.query.dimension.DefaultDimensionSpec;
import org.apache.druid.segment.RowAdapters;
import org.apache.druid.segment.RowBasedColumnSelectorFactory;
import org.apache.druid.segment.VirtualColumns;
import org.apache.druid.segment.column.ColumnType;
import org.apache.druid.segment.column.RowSignature;
import org.apache.druid.segment.column.ValueType;
import org.apache.druid.testing.InitializedNullHandlingTest;
import org.junit.Assert;
import org.junit.Test;

public class VirtualizedColumnSelectorFactoryTest_Purified extends InitializedNullHandlingTest {

    private final VirtualizedColumnSelectorFactory selectorFactory = new VirtualizedColumnSelectorFactory(RowBasedColumnSelectorFactory.create(RowAdapters.standardRow(), () -> new MapBasedRow(0L, ImmutableMap.of("x", 10L, "y", 20.0)), RowSignature.builder().add("x", ColumnType.LONG).add("y", ColumnType.DOUBLE).build(), false, false), VirtualColumns.create(ImmutableList.of(new ExpressionVirtualColumn("v0", "x + 1", null, ExprMacroTable.nil()), new ExpressionVirtualColumn("v1", "v0 + y", null, ExprMacroTable.nil()))));

    @Test
    public void test_getColumnCapabilities_type_1() {
        Assert.assertEquals(ValueType.LONG, selectorFactory.getColumnCapabilities("x").getType());
    }

    @Test
    public void test_getColumnCapabilities_type_2() {
        Assert.assertEquals(ValueType.DOUBLE, selectorFactory.getColumnCapabilities("y").getType());
    }

    @Test
    public void test_getColumnCapabilities_type_3() {
        Assert.assertEquals(ValueType.LONG, selectorFactory.getColumnCapabilities("v0").getType());
    }

    @Test
    public void test_getColumnCapabilities_type_4() {
        Assert.assertEquals(ValueType.DOUBLE, selectorFactory.getColumnCapabilities("v1").getType());
    }

    @Test
    public void test_getColumnCapabilities_type_5() {
        Assert.assertNull(selectorFactory.getColumnCapabilities("nonexistent"));
    }

    @Test
    public void test_makeColumnValueSelector_1() {
        Assert.assertEquals(10, selectorFactory.makeColumnValueSelector("x").getLong());
    }

    @Test
    public void test_makeColumnValueSelector_2() {
        Assert.assertEquals(20, selectorFactory.makeColumnValueSelector("y").getDouble(), 0.0);
    }

    @Test
    public void test_makeColumnValueSelector_3() {
        Assert.assertEquals(11, selectorFactory.makeColumnValueSelector("v0").getLong());
    }

    @Test
    public void test_makeColumnValueSelector_4() {
        Assert.assertEquals(31, selectorFactory.makeColumnValueSelector("v1").getDouble(), 0.0);
    }

    @Test
    public void test_makeColumnValueSelector_5() {
        Assert.assertEquals(10L, selectorFactory.makeColumnValueSelector("x").getObject());
    }

    @Test
    public void test_makeColumnValueSelector_6() {
        Assert.assertEquals(20.0, selectorFactory.makeColumnValueSelector("y").getObject());
    }

    @Test
    public void test_makeColumnValueSelector_7() {
        Assert.assertEquals(11L, selectorFactory.makeColumnValueSelector("v0").getObject());
    }

    @Test
    public void test_makeColumnValueSelector_8() {
        Assert.assertEquals(31.0, selectorFactory.makeColumnValueSelector("v1").getObject());
    }

    @Test
    public void test_makeColumnValueSelector_9() {
        Assert.assertNull(selectorFactory.makeColumnValueSelector("nonexistent").getObject());
    }

    @Test
    public void test_makeDimensionSelector_1() {
        Assert.assertEquals("10", selectorFactory.makeDimensionSelector(DefaultDimensionSpec.of("x")).getObject());
    }

    @Test
    public void test_makeDimensionSelector_2() {
        Assert.assertEquals("20.0", selectorFactory.makeDimensionSelector(DefaultDimensionSpec.of("y")).getObject());
    }

    @Test
    public void test_makeDimensionSelector_3() {
        Assert.assertEquals("11", selectorFactory.makeDimensionSelector(DefaultDimensionSpec.of("v0")).getObject());
    }

    @Test
    public void test_makeDimensionSelector_4() {
        Assert.assertEquals("31.0", selectorFactory.makeDimensionSelector(DefaultDimensionSpec.of("v1")).getObject());
    }

    @Test
    public void test_makeDimensionSelector_5() {
        Assert.assertNull(selectorFactory.makeDimensionSelector(DefaultDimensionSpec.of("nonexistent")).getObject());
    }
}
