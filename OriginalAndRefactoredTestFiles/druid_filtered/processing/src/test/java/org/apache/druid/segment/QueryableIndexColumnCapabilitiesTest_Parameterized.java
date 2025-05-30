package org.apache.druid.segment;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.apache.druid.data.input.InputRow;
import org.apache.druid.data.input.InputRowSchema;
import org.apache.druid.data.input.impl.DimensionSchema;
import org.apache.druid.data.input.impl.DimensionsSpec;
import org.apache.druid.data.input.impl.DoubleDimensionSchema;
import org.apache.druid.data.input.impl.FloatDimensionSchema;
import org.apache.druid.data.input.impl.LongDimensionSchema;
import org.apache.druid.data.input.impl.MapInputRowParser;
import org.apache.druid.data.input.impl.TimestampSpec;
import org.apache.druid.java.util.common.DateTimes;
import org.apache.druid.query.aggregation.AggregatorFactory;
import org.apache.druid.query.aggregation.CountAggregatorFactory;
import org.apache.druid.query.aggregation.DoubleSumAggregatorFactory;
import org.apache.druid.query.aggregation.FloatSumAggregatorFactory;
import org.apache.druid.query.aggregation.LongSumAggregatorFactory;
import org.apache.druid.query.aggregation.hyperloglog.HyperUniquesAggregatorFactory;
import org.apache.druid.segment.column.CapabilitiesBasedFormat;
import org.apache.druid.segment.column.ColumnCapabilities;
import org.apache.druid.segment.column.ColumnCapabilitiesImpl;
import org.apache.druid.segment.column.ColumnHolder;
import org.apache.druid.segment.column.ColumnType;
import org.apache.druid.segment.column.ValueType;
import org.apache.druid.segment.incremental.IncrementalIndex;
import org.apache.druid.segment.incremental.IncrementalIndexSchema;
import org.apache.druid.testing.InitializedNullHandlingTest;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class QueryableIndexColumnCapabilitiesTest_Parameterized extends InitializedNullHandlingTest {

    @ClassRule
    public static TemporaryFolder temporaryFolder = new TemporaryFolder();

    private static IncrementalIndex INC_INDEX;

    private static QueryableIndex MMAP_INDEX;

    private static IncrementalIndex INC_INDEX_WITH_NULLS;

    private static QueryableIndex MMAP_INDEX_WITH_NULLS;

    @BeforeClass
    public static void setup() throws IOException {
        InputRowSchema rowSchema = new InputRowSchema(new TimestampSpec("time", "auto", null), new DimensionsSpec(ImmutableList.<DimensionSchema>builder().addAll(DimensionsSpec.getDefaultSchemas(ImmutableList.of("d1", "d2"))).add(new DoubleDimensionSchema("d3")).add(new FloatDimensionSchema("d4")).add(new LongDimensionSchema("d5")).build()), null);
        AggregatorFactory[] metricsSpecs = new AggregatorFactory[] { new CountAggregatorFactory("cnt"), new DoubleSumAggregatorFactory("m1", "d3"), new FloatSumAggregatorFactory("m2", "d4"), new LongSumAggregatorFactory("m3", "d5"), new HyperUniquesAggregatorFactory("m4", "d1") };
        List<InputRow> rows = new ArrayList<>();
        Map<String, Object> event = ImmutableMap.<String, Object>builder().put("time", DateTimes.nowUtc().getMillis()).put("d1", "some string").put("d2", ImmutableList.of("some", "list")).put("d3", 1.234).put("d4", 1.234f).put("d5", 10L).build();
        rows.add(MapInputRowParser.parse(rowSchema, event));
        IndexBuilder builder = IndexBuilder.create().rows(rows).schema(new IncrementalIndexSchema.Builder().withMetrics(metricsSpecs).withDimensionsSpec(rowSchema.getDimensionsSpec()).withRollup(false).build()).tmpDir(temporaryFolder.newFolder());
        INC_INDEX = builder.buildIncrementalIndex();
        MMAP_INDEX = builder.buildMMappedIndex();
        List<InputRow> rowsWithNulls = new ArrayList<>();
        rowsWithNulls.add(MapInputRowParser.parse(rowSchema, event));
        Map<String, Object> eventWithNulls = new HashMap<>();
        eventWithNulls.put("time", DateTimes.nowUtc().getMillis());
        eventWithNulls.put("d1", null);
        eventWithNulls.put("d2", ImmutableList.of());
        eventWithNulls.put("d3", null);
        eventWithNulls.put("d4", null);
        eventWithNulls.put("d5", null);
        rowsWithNulls.add(MapInputRowParser.parse(rowSchema, eventWithNulls));
        IndexBuilder builderWithNulls = IndexBuilder.create().rows(rowsWithNulls).schema(new IncrementalIndexSchema.Builder().withMetrics(metricsSpecs).withDimensionsSpec(rowSchema.getDimensionsSpec()).withRollup(false).build()).tmpDir(temporaryFolder.newFolder());
        INC_INDEX_WITH_NULLS = builderWithNulls.buildIncrementalIndex();
        MMAP_INDEX_WITH_NULLS = builderWithNulls.buildMMappedIndex();
    }

    @AfterClass
    public static void teardown() {
        INC_INDEX.close();
        MMAP_INDEX.close();
        INC_INDEX_WITH_NULLS.close();
        MMAP_INDEX_WITH_NULLS.close();
    }

    private void assertComplexColumnCapabilites(ColumnCapabilities caps) {
        Assert.assertEquals(HyperUniquesAggregatorFactory.TYPE, caps.toColumnType());
        Assert.assertFalse(caps.hasBitmapIndexes());
        Assert.assertFalse(caps.isDictionaryEncoded().isTrue());
        Assert.assertFalse(caps.areDictionaryValuesSorted().isTrue());
        Assert.assertFalse(caps.areDictionaryValuesUnique().isTrue());
        Assert.assertFalse(caps.hasSpatialIndexes());
        Assert.assertFalse(caps.hasMultipleValues().isUnknown());
        Assert.assertTrue(caps.hasNulls().isTrue());
    }

    private void assertNonStringColumnCapabilities(ColumnCapabilities caps, ColumnType valueType) {
        Assert.assertEquals(valueType, caps.toColumnType());
        Assert.assertFalse(caps.hasBitmapIndexes());
        Assert.assertFalse(caps.isDictionaryEncoded().isTrue());
        Assert.assertFalse(caps.areDictionaryValuesSorted().isTrue());
        Assert.assertFalse(caps.areDictionaryValuesUnique().isTrue());
        Assert.assertFalse(caps.hasMultipleValues().isMaybeTrue());
        Assert.assertFalse(caps.hasSpatialIndexes());
        Assert.assertFalse(caps.hasNulls().isTrue());
    }

    private void assertNonStringColumnCapabilitiesWithNulls(ColumnCapabilities caps, ColumnType valueType) {
        Assert.assertEquals(valueType, caps.toColumnType());
        Assert.assertFalse(caps.hasBitmapIndexes());
        Assert.assertFalse(caps.isDictionaryEncoded().isTrue());
        Assert.assertFalse(caps.areDictionaryValuesSorted().isTrue());
        Assert.assertFalse(caps.areDictionaryValuesUnique().isTrue());
        Assert.assertFalse(caps.hasMultipleValues().isMaybeTrue());
        Assert.assertFalse(caps.hasSpatialIndexes());
        Assert.assertTrue(caps.hasNulls().isMaybeTrue());
    }

    @Test
    public void testNumericColumns_1() {
        assertNonStringColumnCapabilities(INC_INDEX.getColumnCapabilities(ColumnHolder.TIME_COLUMN_NAME), ColumnType.LONG);
    }

    @Test
    public void testNumericColumns_8() {
        assertNonStringColumnCapabilities(MMAP_INDEX.getColumnHolder(ColumnHolder.TIME_COLUMN_NAME).getCapabilities(), ColumnType.LONG);
    }

    @Test
    public void testNumericColumnsWithNulls_1() {
        assertNonStringColumnCapabilities(INC_INDEX_WITH_NULLS.getColumnCapabilities(ColumnHolder.TIME_COLUMN_NAME), ColumnType.LONG);
    }

    @Test
    public void testNumericColumnsWithNulls_8() {
        assertNonStringColumnCapabilities(MMAP_INDEX_WITH_NULLS.getColumnHolder(ColumnHolder.TIME_COLUMN_NAME).getCapabilities(), ColumnType.LONG);
    }

    @Test
    public void testComplexColumn_1() {
        assertComplexColumnCapabilites(INC_INDEX.getColumnCapabilities("m4"));
    }

    @Test
    public void testComplexColumn_2() {
        assertComplexColumnCapabilites(MMAP_INDEX.getColumnHolder("m4").getCapabilities());
    }

    @Test
    public void testComplexColumn_3() {
        assertComplexColumnCapabilites(INC_INDEX_WITH_NULLS.getColumnCapabilities("m4"));
    }

    @Test
    public void testComplexColumn_4() {
        assertComplexColumnCapabilites(MMAP_INDEX_WITH_NULLS.getColumnHolder("m4").getCapabilities());
    }

    @ParameterizedTest
    @MethodSource("Provider_testNumericColumns_2_5")
    public void testNumericColumns_2_5(String param1) {
        assertNonStringColumnCapabilities(INC_INDEX.getColumnCapabilities(param1), ColumnType.DOUBLE);
    }

    static public Stream<Arguments> Provider_testNumericColumns_2_5() {
        return Stream.of(arguments("d3"), arguments("m1"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testNumericColumns_3_6")
    public void testNumericColumns_3_6(String param1) {
        assertNonStringColumnCapabilities(INC_INDEX.getColumnCapabilities(param1), ColumnType.FLOAT);
    }

    static public Stream<Arguments> Provider_testNumericColumns_3_6() {
        return Stream.of(arguments("d4"), arguments("m2"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testNumericColumns_4_7")
    public void testNumericColumns_4_7(String param1) {
        assertNonStringColumnCapabilities(INC_INDEX.getColumnCapabilities(param1), ColumnType.LONG);
    }

    static public Stream<Arguments> Provider_testNumericColumns_4_7() {
        return Stream.of(arguments("d5"), arguments("m3"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testNumericColumns_9_12")
    public void testNumericColumns_9_12(String param1) {
        assertNonStringColumnCapabilities(MMAP_INDEX.getColumnHolder(param1).getCapabilities(), ColumnType.DOUBLE);
    }

    static public Stream<Arguments> Provider_testNumericColumns_9_12() {
        return Stream.of(arguments("d3"), arguments("m1"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testNumericColumns_10_13")
    public void testNumericColumns_10_13(String param1) {
        assertNonStringColumnCapabilities(MMAP_INDEX.getColumnHolder(param1).getCapabilities(), ColumnType.FLOAT);
    }

    static public Stream<Arguments> Provider_testNumericColumns_10_13() {
        return Stream.of(arguments("d4"), arguments("m2"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testNumericColumns_11_14")
    public void testNumericColumns_11_14(String param1) {
        assertNonStringColumnCapabilities(MMAP_INDEX.getColumnHolder(param1).getCapabilities(), ColumnType.LONG);
    }

    static public Stream<Arguments> Provider_testNumericColumns_11_14() {
        return Stream.of(arguments("d5"), arguments("m3"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testNumericColumnsWithNulls_2_5")
    public void testNumericColumnsWithNulls_2_5(String param1) {
        assertNonStringColumnCapabilitiesWithNulls(INC_INDEX_WITH_NULLS.getColumnCapabilities(param1), ColumnType.DOUBLE);
    }

    static public Stream<Arguments> Provider_testNumericColumnsWithNulls_2_5() {
        return Stream.of(arguments("d3"), arguments("m1"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testNumericColumnsWithNulls_3_6")
    public void testNumericColumnsWithNulls_3_6(String param1) {
        assertNonStringColumnCapabilitiesWithNulls(INC_INDEX_WITH_NULLS.getColumnCapabilities(param1), ColumnType.FLOAT);
    }

    static public Stream<Arguments> Provider_testNumericColumnsWithNulls_3_6() {
        return Stream.of(arguments("d4"), arguments("m2"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testNumericColumnsWithNulls_4_7")
    public void testNumericColumnsWithNulls_4_7(String param1) {
        assertNonStringColumnCapabilitiesWithNulls(INC_INDEX_WITH_NULLS.getColumnCapabilities(param1), ColumnType.LONG);
    }

    static public Stream<Arguments> Provider_testNumericColumnsWithNulls_4_7() {
        return Stream.of(arguments("d5"), arguments("m3"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testNumericColumnsWithNulls_9_12")
    public void testNumericColumnsWithNulls_9_12(String param1) {
        assertNonStringColumnCapabilitiesWithNulls(MMAP_INDEX_WITH_NULLS.getColumnHolder(param1).getCapabilities(), ColumnType.DOUBLE);
    }

    static public Stream<Arguments> Provider_testNumericColumnsWithNulls_9_12() {
        return Stream.of(arguments("d3"), arguments("m1"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testNumericColumnsWithNulls_10_13")
    public void testNumericColumnsWithNulls_10_13(String param1) {
        assertNonStringColumnCapabilitiesWithNulls(MMAP_INDEX_WITH_NULLS.getColumnHolder(param1).getCapabilities(), ColumnType.FLOAT);
    }

    static public Stream<Arguments> Provider_testNumericColumnsWithNulls_10_13() {
        return Stream.of(arguments("d4"), arguments("m2"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testNumericColumnsWithNulls_11_14")
    public void testNumericColumnsWithNulls_11_14(String param1) {
        assertNonStringColumnCapabilitiesWithNulls(MMAP_INDEX_WITH_NULLS.getColumnHolder(param1).getCapabilities(), ColumnType.LONG);
    }

    static public Stream<Arguments> Provider_testNumericColumnsWithNulls_11_14() {
        return Stream.of(arguments("d5"), arguments("m3"));
    }
}
