package org.apache.druid.indexing.input;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.apache.druid.data.input.BytesCountingInputEntity;
import org.apache.druid.data.input.ColumnsFilter;
import org.apache.druid.data.input.InputEntity;
import org.apache.druid.data.input.InputEntity.CleanableFile;
import org.apache.druid.data.input.InputRow;
import org.apache.druid.data.input.InputStats;
import org.apache.druid.data.input.MapBasedInputRow;
import org.apache.druid.data.input.impl.DimensionsSpec;
import org.apache.druid.data.input.impl.DoubleDimensionSchema;
import org.apache.druid.data.input.impl.InputStatsImpl;
import org.apache.druid.data.input.impl.LongDimensionSchema;
import org.apache.druid.data.input.impl.StringDimensionSchema;
import org.apache.druid.data.input.impl.TimestampSpec;
import org.apache.druid.hll.HyperLogLogCollector;
import org.apache.druid.hll.HyperLogLogHash;
import org.apache.druid.java.util.common.DateTimes;
import org.apache.druid.java.util.common.FileUtils;
import org.apache.druid.java.util.common.Intervals;
import org.apache.druid.java.util.common.guava.BaseSequence;
import org.apache.druid.java.util.common.guava.BaseSequence.IteratorMaker;
import org.apache.druid.java.util.common.guava.Sequence;
import org.apache.druid.java.util.common.parsers.CloseableIterator;
import org.apache.druid.query.aggregation.AggregatorFactory;
import org.apache.druid.query.aggregation.CountAggregatorFactory;
import org.apache.druid.query.aggregation.hyperloglog.HyperUniquesAggregatorFactory;
import org.apache.druid.query.filter.NotDimFilter;
import org.apache.druid.query.filter.OrDimFilter;
import org.apache.druid.query.filter.SelectorDimFilter;
import org.apache.druid.segment.AutoTypeColumnSchema;
import org.apache.druid.segment.IndexBuilder;
import org.apache.druid.segment.IndexIO;
import org.apache.druid.segment.IndexSpec;
import org.apache.druid.segment.TestHelper;
import org.apache.druid.segment.column.ColumnType;
import org.apache.druid.segment.incremental.IncrementalIndex;
import org.apache.druid.segment.incremental.IncrementalIndexSchema;
import org.apache.druid.segment.loading.NoopSegmentCacheManager;
import org.apache.druid.segment.writeout.OnHeapMemorySegmentWriteOutMediumFactory;
import org.apache.druid.testing.InitializedNullHandlingTest;
import org.apache.druid.timeline.DataSegment;
import org.apache.druid.timeline.partition.TombstoneShardSpec;
import org.joda.time.Interval;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.assertThrows;

public class DruidSegmentReaderTest_Purified extends InitializedNullHandlingTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    private File segmentDirectory;

    private long segmentSize;

    private final IndexIO indexIO = TestHelper.getTestIndexIO();

    private DimensionsSpec dimensionsSpec;

    private List<AggregatorFactory> metrics;

    private InputStats inputStats;

    @Before
    public void setUp() throws IOException {
        dimensionsSpec = new DimensionsSpec(ImmutableList.of(StringDimensionSchema.create("strCol"), new DoubleDimensionSchema("dblCol")));
        metrics = ImmutableList.of(new CountAggregatorFactory("cnt"), new HyperUniquesAggregatorFactory("met_s", "strCol"));
        final List<InputRow> rows = ImmutableList.of(new MapBasedInputRow(DateTimes.of("2000"), ImmutableList.of("strCol", "dblCol"), ImmutableMap.<String, Object>builder().put("strCol", "foo").put("dblCol", 1.23).build()), new MapBasedInputRow(DateTimes.of("2000T01"), ImmutableList.of("strCol", "dblCol"), ImmutableMap.<String, Object>builder().put("strCol", "bar").put("dblCol", 4.56).build()));
        inputStats = new InputStatsImpl();
        persistSegment(rows);
    }

    private InputEntity makeInputEntity(final Interval interval) {
        return new BytesCountingInputEntity(makeInputEntity(interval, segmentDirectory, ImmutableList.of("strCol", "dblCol"), ImmutableList.of("cnt", "met_s")), inputStats);
    }

    public static DruidSegmentInputEntity makeInputEntity(final Interval interval, final File segmentDirectory, final List<String> dimensions, final List<String> metrics) {
        return new DruidSegmentInputEntity(new NoopSegmentCacheManager() {

            @Override
            public File getSegmentFiles(DataSegment segment) {
                return segmentDirectory;
            }
        }, DataSegment.builder().dataSource("ds").dimensions(dimensions).metrics(metrics).interval(interval).version("1").size(0).build(), interval);
    }

    public static DruidSegmentInputEntity makeTombstoneInputEntity(final Interval interval) {
        return new DruidSegmentInputEntity(new NoopSegmentCacheManager(), DataSegment.builder().dataSource("ds").interval(Intervals.of("2000/P1D")).version("1").shardSpec(new TombstoneShardSpec()).loadSpec(ImmutableMap.of("type", "tombstone")).size(1).build(), interval);
    }

    private List<InputRow> readRows(DruidSegmentReader reader) throws IOException {
        final List<InputRow> rows = new ArrayList<>();
        try (final CloseableIterator<Map<String, Object>> iterator = reader.intermediateRowIterator()) {
            while (iterator.hasNext()) {
                rows.addAll(reader.parseInputRows(iterator.next()));
            }
        }
        return rows;
    }

    private List<InputRow> readRows(DruidTombstoneSegmentReader reader) throws IOException {
        final List<InputRow> rows = new ArrayList<>();
        try (final CloseableIterator<Map<String, Object>> iterator = reader.intermediateRowIterator()) {
            while (iterator.hasNext()) {
                rows.addAll(reader.parseInputRows(iterator.next()));
            }
        }
        return rows;
    }

    private static HyperLogLogCollector makeHLLC(final String... values) {
        final HyperLogLogCollector collector = HyperLogLogCollector.makeLatestCollector();
        for (String value : values) {
            collector.add(HyperLogLogHash.getDefault().hash(value));
        }
        return collector;
    }

    private void persistSegment(List<InputRow> rows) throws IOException {
        final IncrementalIndex incrementalIndex = IndexBuilder.create().schema(new IncrementalIndexSchema.Builder().withDimensionsSpec(dimensionsSpec).withMetrics(metrics.toArray(new AggregatorFactory[0])).withRollup(false).build()).rows(rows).buildIncrementalIndex();
        segmentDirectory = temporaryFolder.newFolder();
        try {
            TestHelper.getTestIndexMergerV9(OnHeapMemorySegmentWriteOutMediumFactory.instance()).persist(incrementalIndex, segmentDirectory, IndexSpec.DEFAULT, null);
            segmentSize = FileUtils.getFileSize(segmentDirectory);
        } finally {
            incrementalIndex.close();
        }
    }

    @Test
    public void testReader_1() throws IOException {
        final DruidSegmentReader reader = new DruidSegmentReader(makeInputEntity(Intervals.of("2000/P1D")), indexIO, new TimestampSpec("__time", "millis", DateTimes.of("1971")), new DimensionsSpec(ImmutableList.of(StringDimensionSchema.create("strCol"), new DoubleDimensionSchema("dblCol"))), ColumnsFilter.all(), null, temporaryFolder.newFolder());
        Assert.assertEquals(ImmutableList.of(new MapBasedInputRow(DateTimes.of("2000"), ImmutableList.of("strCol", "dblCol"), ImmutableMap.<String, Object>builder().put("__time", DateTimes.of("2000T").getMillis()).put("strCol", "foo").put("dblCol", 1.23d).put("cnt", 1L).put("met_s", makeHLLC("foo")).build()), new MapBasedInputRow(DateTimes.of("2000T01"), ImmutableList.of("strCol", "dblCol"), ImmutableMap.<String, Object>builder().put("__time", DateTimes.of("2000T01").getMillis()).put("strCol", "bar").put("dblCol", 4.56d).put("cnt", 1L).put("met_s", makeHLLC("bar")).build())), readRows(reader));
    }

    @Test
    public void testReader_2() throws IOException {
        Assert.assertEquals(segmentSize, inputStats.getProcessedBytes());
    }

    @Test
    public void testReaderAutoTimestampFormat_1() throws IOException {
        final DruidSegmentReader reader = new DruidSegmentReader(makeInputEntity(Intervals.of("2000/P1D")), indexIO, new TimestampSpec("__time", "auto", DateTimes.of("1971")), new DimensionsSpec(ImmutableList.of(StringDimensionSchema.create("strCol"), new DoubleDimensionSchema("dblCol"))), ColumnsFilter.all(), null, temporaryFolder.newFolder());
        Assert.assertEquals(ImmutableList.of(new MapBasedInputRow(DateTimes.of("2000"), ImmutableList.of("strCol", "dblCol"), ImmutableMap.<String, Object>builder().put("__time", DateTimes.of("2000T").getMillis()).put("strCol", "foo").put("dblCol", 1.23d).put("cnt", 1L).put("met_s", makeHLLC("foo")).build()), new MapBasedInputRow(DateTimes.of("2000T01"), ImmutableList.of("strCol", "dblCol"), ImmutableMap.<String, Object>builder().put("__time", DateTimes.of("2000T01").getMillis()).put("strCol", "bar").put("dblCol", 4.56d).put("cnt", 1L).put("met_s", makeHLLC("bar")).build())), readRows(reader));
    }

    @Test
    public void testReaderAutoTimestampFormat_2() throws IOException {
        Assert.assertEquals(segmentSize, inputStats.getProcessedBytes());
    }

    @Test
    public void testReaderWithDimensionExclusions_1() throws IOException {
        final DruidSegmentReader reader = new DruidSegmentReader(makeInputEntity(Intervals.of("2000/P1D")), indexIO, new TimestampSpec("__time", "millis", DateTimes.of("1971")), DimensionsSpec.builder().setDimensionExclusions(ImmutableList.of("__time", "strCol", "cnt", "met_s")).build(), ColumnsFilter.all(), null, temporaryFolder.newFolder());
        Assert.assertEquals(ImmutableList.of(new MapBasedInputRow(DateTimes.of("2000"), ImmutableList.of("dblCol"), ImmutableMap.<String, Object>builder().put("__time", DateTimes.of("2000T").getMillis()).put("strCol", "foo").put("dblCol", 1.23d).put("cnt", 1L).put("met_s", makeHLLC("foo")).build()), new MapBasedInputRow(DateTimes.of("2000T01"), ImmutableList.of("dblCol"), ImmutableMap.<String, Object>builder().put("__time", DateTimes.of("2000T01").getMillis()).put("strCol", "bar").put("dblCol", 4.56d).put("cnt", 1L).put("met_s", makeHLLC("bar")).build())), readRows(reader));
    }

    @Test
    public void testReaderWithDimensionExclusions_2() throws IOException {
        Assert.assertEquals(segmentSize, inputStats.getProcessedBytes());
    }

    @Test
    public void testReaderWithInclusiveColumnsFilter_1() throws IOException {
        final DruidSegmentReader reader = new DruidSegmentReader(makeInputEntity(Intervals.of("2000/P1D")), indexIO, new TimestampSpec("__time", "millis", DateTimes.of("1971")), new DimensionsSpec(ImmutableList.of(StringDimensionSchema.create("strCol"), new DoubleDimensionSchema("dblCol"))), ColumnsFilter.inclusionBased(ImmutableSet.of("__time", "strCol", "dblCol")), null, temporaryFolder.newFolder());
        Assert.assertEquals(ImmutableList.of(new MapBasedInputRow(DateTimes.of("2000"), ImmutableList.of("strCol", "dblCol"), ImmutableMap.<String, Object>builder().put("__time", DateTimes.of("2000T").getMillis()).put("strCol", "foo").put("dblCol", 1.23d).build()), new MapBasedInputRow(DateTimes.of("2000T01"), ImmutableList.of("strCol", "dblCol"), ImmutableMap.<String, Object>builder().put("__time", DateTimes.of("2000T01").getMillis()).put("strCol", "bar").put("dblCol", 4.56d).build())), readRows(reader));
    }

    @Test
    public void testReaderWithInclusiveColumnsFilter_2() throws IOException {
        Assert.assertEquals(segmentSize, inputStats.getProcessedBytes());
    }

    @Test
    public void testReaderWithInclusiveColumnsFilterNoTimestamp_1() throws IOException {
        final DruidSegmentReader reader = new DruidSegmentReader(makeInputEntity(Intervals.of("2000/P1D")), indexIO, new TimestampSpec("__time", "millis", DateTimes.of("1971")), new DimensionsSpec(ImmutableList.of(StringDimensionSchema.create("strCol"), new DoubleDimensionSchema("dblCol"))), ColumnsFilter.inclusionBased(ImmutableSet.of("strCol", "dblCol")), null, temporaryFolder.newFolder());
        Assert.assertEquals(ImmutableList.of(new MapBasedInputRow(DateTimes.of("1971"), ImmutableList.of("strCol", "dblCol"), ImmutableMap.<String, Object>builder().put("strCol", "foo").put("dblCol", 1.23d).build()), new MapBasedInputRow(DateTimes.of("1971"), ImmutableList.of("strCol", "dblCol"), ImmutableMap.<String, Object>builder().put("strCol", "bar").put("dblCol", 4.56d).build())), readRows(reader));
    }

    @Test
    public void testReaderWithInclusiveColumnsFilterNoTimestamp_2() throws IOException {
        Assert.assertEquals(segmentSize, inputStats.getProcessedBytes());
    }

    @Test
    public void testReaderWithFilter_1() throws IOException {
        final DruidSegmentReader reader = new DruidSegmentReader(makeInputEntity(Intervals.of("2000/P1D")), indexIO, new TimestampSpec("__time", "millis", DateTimes.of("1971")), new DimensionsSpec(ImmutableList.of(StringDimensionSchema.create("strCol"), new DoubleDimensionSchema("dblCol"))), ColumnsFilter.all(), new SelectorDimFilter("dblCol", "1.23", null), temporaryFolder.newFolder());
        Assert.assertEquals(ImmutableList.of(new MapBasedInputRow(DateTimes.of("2000"), ImmutableList.of("strCol", "dblCol"), ImmutableMap.<String, Object>builder().put("__time", DateTimes.of("2000T").getMillis()).put("strCol", "foo").put("dblCol", 1.23d).put("cnt", 1L).put("met_s", makeHLLC("foo")).build())), readRows(reader));
    }

    @Test
    public void testReaderWithFilter_2() throws IOException {
        Assert.assertEquals(segmentSize, inputStats.getProcessedBytes());
    }

    @Test
    public void testReaderTimestampFromDouble_1() throws IOException {
        final DruidSegmentReader reader = new DruidSegmentReader(makeInputEntity(Intervals.of("2000/P1D")), indexIO, new TimestampSpec("dblCol", "posix", null), new DimensionsSpec(ImmutableList.of(StringDimensionSchema.create("strCol"), new DoubleDimensionSchema("dblCol"))), ColumnsFilter.all(), null, temporaryFolder.newFolder());
        Assert.assertEquals(ImmutableList.of(new MapBasedInputRow(DateTimes.of("1970-01-01T00:00:01.000Z"), ImmutableList.of("strCol", "dblCol"), ImmutableMap.<String, Object>builder().put("__time", DateTimes.of("2000T").getMillis()).put("strCol", "foo").put("dblCol", 1.23d).put("cnt", 1L).put("met_s", makeHLLC("foo")).build()), new MapBasedInputRow(DateTimes.of("1970-01-01T00:00:04.000Z"), ImmutableList.of("strCol", "dblCol"), ImmutableMap.<String, Object>builder().put("__time", DateTimes.of("2000T01").getMillis()).put("strCol", "bar").put("dblCol", 4.56d).put("cnt", 1L).put("met_s", makeHLLC("bar")).build())), readRows(reader));
    }

    @Test
    public void testReaderTimestampFromDouble_2() throws IOException {
        Assert.assertEquals(segmentSize, inputStats.getProcessedBytes());
    }

    @Test
    public void testReaderTimestampAsPosixIncorrectly_1() throws IOException {
        final DruidSegmentReader reader = new DruidSegmentReader(makeInputEntity(Intervals.of("2000/P1D")), indexIO, new TimestampSpec("__time", "posix", null), new DimensionsSpec(ImmutableList.of(StringDimensionSchema.create("strCol"), new DoubleDimensionSchema("dblCol"))), ColumnsFilter.all(), null, temporaryFolder.newFolder());
        Assert.assertEquals(ImmutableList.of(new MapBasedInputRow(DateTimes.of("31969-04-01T00:00:00.000Z"), ImmutableList.of("strCol", "dblCol"), ImmutableMap.<String, Object>builder().put("__time", DateTimes.of("2000T").getMillis()).put("strCol", "foo").put("dblCol", 1.23d).put("cnt", 1L).put("met_s", makeHLLC("foo")).build()), new MapBasedInputRow(DateTimes.of("31969-05-12T16:00:00.000Z"), ImmutableList.of("strCol", "dblCol"), ImmutableMap.<String, Object>builder().put("__time", DateTimes.of("2000T01").getMillis()).put("strCol", "bar").put("dblCol", 4.56d).put("cnt", 1L).put("met_s", makeHLLC("bar")).build())), readRows(reader));
    }

    @Test
    public void testReaderTimestampAsPosixIncorrectly_2() throws IOException {
        Assert.assertEquals(segmentSize, inputStats.getProcessedBytes());
    }

    @Test
    public void testReaderTimestampSpecDefault_1() throws IOException {
        final DruidSegmentReader reader = new DruidSegmentReader(makeInputEntity(Intervals.of("2000/P1D")), indexIO, new TimestampSpec(null, null, DateTimes.of("1971")), new DimensionsSpec(ImmutableList.of(StringDimensionSchema.create("strCol"), new DoubleDimensionSchema("dblCol"))), ColumnsFilter.all(), null, temporaryFolder.newFolder());
        Assert.assertEquals(ImmutableList.of(new MapBasedInputRow(DateTimes.of("1971"), ImmutableList.of("strCol", "dblCol"), ImmutableMap.<String, Object>builder().put("__time", DateTimes.of("2000T").getMillis()).put("strCol", "foo").put("dblCol", 1.23d).put("cnt", 1L).put("met_s", makeHLLC("foo")).build()), new MapBasedInputRow(DateTimes.of("1971"), ImmutableList.of("strCol", "dblCol"), ImmutableMap.<String, Object>builder().put("__time", DateTimes.of("2000T01").getMillis()).put("strCol", "bar").put("dblCol", 4.56d).put("cnt", 1L).put("met_s", makeHLLC("bar")).build())), readRows(reader));
    }

    @Test
    public void testReaderTimestampSpecDefault_2() throws IOException {
        Assert.assertEquals(segmentSize, inputStats.getProcessedBytes());
    }
}
