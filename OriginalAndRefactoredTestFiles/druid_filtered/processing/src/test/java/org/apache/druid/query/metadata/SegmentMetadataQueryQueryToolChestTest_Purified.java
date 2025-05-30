package org.apache.druid.query.metadata;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.apache.druid.error.DruidException;
import org.apache.druid.error.DruidExceptionMatcher;
import org.apache.druid.jackson.DefaultObjectMapper;
import org.apache.druid.java.util.common.Intervals;
import org.apache.druid.query.CacheStrategy;
import org.apache.druid.query.DataSource;
import org.apache.druid.query.Druids;
import org.apache.druid.query.TableDataSource;
import org.apache.druid.query.UnionDataSource;
import org.apache.druid.query.aggregation.AggregatorFactory;
import org.apache.druid.query.aggregation.DoubleMaxAggregatorFactory;
import org.apache.druid.query.aggregation.DoubleSumAggregatorFactory;
import org.apache.druid.query.aggregation.LongMaxAggregatorFactory;
import org.apache.druid.query.aggregation.LongSumAggregatorFactory;
import org.apache.druid.query.metadata.metadata.AggregatorMergeStrategy;
import org.apache.druid.query.metadata.metadata.ColumnAnalysis;
import org.apache.druid.query.metadata.metadata.SegmentAnalysis;
import org.apache.druid.query.metadata.metadata.SegmentMetadataQuery;
import org.apache.druid.query.spec.LegacySegmentSpec;
import org.apache.druid.segment.column.ColumnType;
import org.apache.druid.segment.column.ValueType;
import org.apache.druid.timeline.LogicalSegment;
import org.apache.druid.timeline.SegmentId;
import org.hamcrest.MatcherAssert;
import org.joda.time.Interval;
import org.joda.time.Period;
import org.junit.Assert;
import org.junit.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SegmentMetadataQueryQueryToolChestTest_Purified {

    private static final DataSource TEST_DATASOURCE = new TableDataSource("dummy");

    private static final SegmentId TEST_SEGMENT_ID1 = SegmentId.of(TEST_DATASOURCE.toString(), Intervals.of("2020-01-01/2020-01-02"), "test", 0);

    private static final SegmentId TEST_SEGMENT_ID2 = SegmentId.of(TEST_DATASOURCE.toString(), Intervals.of("2021-01-01/2021-01-02"), "test", 0);

    private static SegmentAnalysis mergeStrict(SegmentAnalysis analysis1, SegmentAnalysis analysis2) {
        return SegmentMetadataQueryQueryToolChest.finalizeAnalysis(SegmentMetadataQueryQueryToolChest.mergeAnalyses(TEST_DATASOURCE.getTableNames(), analysis1, analysis2, AggregatorMergeStrategy.STRICT));
    }

    private static SegmentAnalysis mergeLenient(SegmentAnalysis analysis1, SegmentAnalysis analysis2) {
        return SegmentMetadataQueryQueryToolChest.finalizeAnalysis(SegmentMetadataQueryQueryToolChest.mergeAnalyses(TEST_DATASOURCE.getTableNames(), analysis1, analysis2, AggregatorMergeStrategy.LENIENT));
    }

    private static SegmentAnalysis mergeEarliest(SegmentAnalysis analysis1, SegmentAnalysis analysis2) {
        return SegmentMetadataQueryQueryToolChest.finalizeAnalysis(SegmentMetadataQueryQueryToolChest.mergeAnalyses(TEST_DATASOURCE.getTableNames(), analysis1, analysis2, AggregatorMergeStrategy.EARLIEST));
    }

    private static SegmentAnalysis mergeLatest(SegmentAnalysis analysis1, SegmentAnalysis analysis2) {
        return SegmentMetadataQueryQueryToolChest.finalizeAnalysis(SegmentMetadataQueryQueryToolChest.mergeAnalyses(TEST_DATASOURCE.getTableNames(), analysis1, analysis2, AggregatorMergeStrategy.LATEST));
    }

    @Test
    public void testMergeWithNullAnalyses_1() {
        final SegmentAnalysis analysis1 = new SegmentAnalysis(TEST_SEGMENT_ID1.toString(), null, new LinkedHashMap<>(), 0, 0, null, null, null, null);
        Assert.assertEquals(analysis1, SegmentMetadataQueryQueryToolChest.mergeAnalyses(TEST_DATASOURCE.getTableNames(), analysis1, null, AggregatorMergeStrategy.STRICT));
    }

    @Test
    public void testMergeWithNullAnalyses_2() {
        final SegmentAnalysis analysis2 = new SegmentAnalysis(TEST_SEGMENT_ID2.toString(), null, new LinkedHashMap<>(), 0, 0, null, null, null, false);
        Assert.assertEquals(analysis2, SegmentMetadataQueryQueryToolChest.mergeAnalyses(TEST_DATASOURCE.getTableNames(), null, analysis2, AggregatorMergeStrategy.STRICT));
    }

    @Test
    public void testMergeWithNullAnalyses_3() {
        Assert.assertNull(SegmentMetadataQueryQueryToolChest.mergeAnalyses(TEST_DATASOURCE.getTableNames(), null, null, AggregatorMergeStrategy.STRICT));
    }

    @Test
    public void testMergeWithNullAnalyses_4() {
        Assert.assertNull(SegmentMetadataQueryQueryToolChest.mergeAnalyses(TEST_DATASOURCE.getTableNames(), null, null, AggregatorMergeStrategy.LENIENT));
    }

    @Test
    public void testMergeWithNullAnalyses_5() {
        Assert.assertNull(SegmentMetadataQueryQueryToolChest.mergeAnalyses(TEST_DATASOURCE.getTableNames(), null, null, AggregatorMergeStrategy.EARLIEST));
    }

    @Test
    public void testMergeWithNullAnalyses_6() {
        Assert.assertNull(SegmentMetadataQueryQueryToolChest.mergeAnalyses(TEST_DATASOURCE.getTableNames(), null, null, AggregatorMergeStrategy.LATEST));
    }
}
