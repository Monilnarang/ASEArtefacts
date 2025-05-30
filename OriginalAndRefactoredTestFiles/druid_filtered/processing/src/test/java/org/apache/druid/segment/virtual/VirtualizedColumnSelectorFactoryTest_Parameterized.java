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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class VirtualizedColumnSelectorFactoryTest_Parameterized extends InitializedNullHandlingTest {

    private final VirtualizedColumnSelectorFactory selectorFactory = new VirtualizedColumnSelectorFactory(RowBasedColumnSelectorFactory.create(RowAdapters.standardRow(), () -> new MapBasedRow(0L, ImmutableMap.of("x", 10L, "y", 20.0)), RowSignature.builder().add("x", ColumnType.LONG).add("y", ColumnType.DOUBLE).build(), false, false), VirtualColumns.create(ImmutableList.of(new ExpressionVirtualColumn("v0", "x + 1", null, ExprMacroTable.nil()), new ExpressionVirtualColumn("v1", "v0 + y", null, ExprMacroTable.nil()))));

    @Test
    public void test_getColumnCapabilities_type_5() {
        Assert.assertNull(selectorFactory.getColumnCapabilities("nonexistent"));
    }

    @Test
    public void test_makeColumnValueSelector_9() {
        Assert.assertNull(selectorFactory.makeColumnValueSelector("nonexistent").getObject());
    }

    @Test
    public void test_makeDimensionSelector_5() {
        Assert.assertNull(selectorFactory.makeDimensionSelector(DefaultDimensionSpec.of("nonexistent")).getObject());
    }

    @ParameterizedTest
    @MethodSource("Provider_test_getColumnCapabilities_type_1_3")
    public void test_getColumnCapabilities_type_1_3(String param1) {
        Assert.assertEquals(ValueType.LONG, selectorFactory.getColumnCapabilities(param1).getType());
    }

    static public Stream<Arguments> Provider_test_getColumnCapabilities_type_1_3() {
        return Stream.of(arguments("x"), arguments("v0"));
    }

    @ParameterizedTest
    @MethodSource("Provider_test_getColumnCapabilities_type_2_4")
    public void test_getColumnCapabilities_type_2_4(String param1) {
        Assert.assertEquals(ValueType.DOUBLE, selectorFactory.getColumnCapabilities(param1).getType());
    }

    static public Stream<Arguments> Provider_test_getColumnCapabilities_type_2_4() {
        return Stream.of(arguments("y"), arguments("v1"));
    }

    @ParameterizedTest
    @MethodSource("Provider_test_makeColumnValueSelector_1_3")
    public void test_makeColumnValueSelector_1_3(int param1, String param2) {
        Assert.assertEquals(param1, selectorFactory.makeColumnValueSelector(param2).getLong());
    }

    static public Stream<Arguments> Provider_test_makeColumnValueSelector_1_3() {
        return Stream.of(arguments(10, "x"), arguments(11, "v0"));
    }

    @ParameterizedTest
    @MethodSource("Provider_test_makeColumnValueSelector_2_4")
    public void test_makeColumnValueSelector_2_4(int param1, double param2, String param3) {
        Assert.assertEquals(param1, selectorFactory.makeColumnValueSelector(param3).getDouble(), param2);
    }

    static public Stream<Arguments> Provider_test_makeColumnValueSelector_2_4() {
        return Stream.of(arguments(20, 0.0, "y"), arguments(31, 0.0, "v1"));
    }

    @ParameterizedTest
    @MethodSource("Provider_test_makeColumnValueSelector_5to8")
    public void test_makeColumnValueSelector_5to8(long param1, String param2) {
        Assert.assertEquals(param1, selectorFactory.makeColumnValueSelector(param2).getObject());
    }

    static public Stream<Arguments> Provider_test_makeColumnValueSelector_5to8() {
        return Stream.of(arguments(10L, "x"), arguments(20.0, "y"), arguments(11L, "v0"), arguments(31.0, "v1"));
    }

    @ParameterizedTest
    @MethodSource("Provider_test_makeDimensionSelector_1to4")
    public void test_makeDimensionSelector_1to4(int param1, String param2) {
        Assert.assertEquals(param1, selectorFactory.makeDimensionSelector(DefaultDimensionSpec.of(param2)).getObject());
    }

    static public Stream<Arguments> Provider_test_makeDimensionSelector_1to4() {
        return Stream.of(arguments(10, "x"), arguments(20.0, "y"), arguments(11, "v0"), arguments(31.0, "v1"));
    }
}
