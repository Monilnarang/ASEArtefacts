package org.apache.druid.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Ordering;
import org.apache.druid.java.util.common.Intervals;
import org.apache.druid.java.util.common.MapUtils;
import org.apache.druid.java.util.common.concurrent.Execs;
import org.apache.druid.query.TableDataSource;
import org.apache.druid.segment.ReferenceCountingSegment;
import org.apache.druid.segment.SegmentLazyLoadFailCallback;
import org.apache.druid.segment.TestHelper;
import org.apache.druid.segment.TestIndex;
import org.apache.druid.segment.TestSegmentUtils;
import org.apache.druid.segment.loading.LeastBytesUsedStorageLocationSelectorStrategy;
import org.apache.druid.segment.loading.SegmentLoaderConfig;
import org.apache.druid.segment.loading.SegmentLoadingException;
import org.apache.druid.segment.loading.SegmentLocalCacheManager;
import org.apache.druid.segment.loading.StorageLocation;
import org.apache.druid.segment.loading.StorageLocationConfig;
import org.apache.druid.server.SegmentManager.DataSourceState;
import org.apache.druid.timeline.DataSegment;
import org.apache.druid.timeline.VersionedIntervalTimeline;
import org.apache.druid.timeline.partition.NumberedOverwriteShardSpec;
import org.apache.druid.timeline.partition.PartitionIds;
import org.joda.time.Interval;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class SegmentManagerTest_Purified {

    private static final List<DataSegment> SEGMENTS = ImmutableList.of(TestSegmentUtils.makeSegment("small_source", "0", Intervals.of("0/1000")), TestSegmentUtils.makeSegment("small_source", "0", Intervals.of("1000/2000")), TestSegmentUtils.makeSegment("large_source", "0", Intervals.of("0/1000")), TestSegmentUtils.makeSegment("large_source", "0", Intervals.of("1000/2000")), TestSegmentUtils.makeSegment("large_source", "1", Intervals.of("1000/2000")));

    private ExecutorService executor;

    private SegmentManager segmentManager;

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Before
    public void setup() throws IOException {
        final File segmentCacheDir = temporaryFolder.newFolder();
        final SegmentLoaderConfig loaderConfig = new SegmentLoaderConfig() {

            @Override
            public File getInfoDir() {
                return segmentCacheDir;
            }

            @Override
            public List<StorageLocationConfig> getLocations() {
                return Collections.singletonList(new StorageLocationConfig(segmentCacheDir, null, null));
            }
        };
        final ObjectMapper objectMapper = TestHelper.makeJsonMapper();
        objectMapper.registerSubtypes(TestSegmentUtils.TestLoadSpec.class);
        objectMapper.registerSubtypes(TestSegmentUtils.TestSegmentizerFactory.class);
        final List<StorageLocation> storageLocations = loaderConfig.toStorageLocations();
        final SegmentLocalCacheManager cacheManager = new SegmentLocalCacheManager(storageLocations, loaderConfig, new LeastBytesUsedStorageLocationSelectorStrategy(storageLocations), TestIndex.INDEX_IO, objectMapper);
        segmentManager = new SegmentManager(cacheManager);
        executor = Execs.multiThreaded(SEGMENTS.size(), "SegmentManagerTest-%d");
    }

    @After
    public void tearDown() {
        executor.shutdownNow();
    }

    private Void loadSegmentOrFail(DataSegment segment) {
        try {
            segmentManager.loadSegment(segment);
        } catch (IOException | SegmentLoadingException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    private void assertResult(List<DataSegment> expectedExistingSegments) {
        final Map<String, Long> expectedDataSourceSizes = expectedExistingSegments.stream().collect(Collectors.toMap(DataSegment::getDataSource, DataSegment::getSize, Long::sum));
        final Map<String, Long> expectedDataSourceCounts = expectedExistingSegments.stream().collect(Collectors.toMap(DataSegment::getDataSource, segment -> 1L, Long::sum));
        final Set<String> expectedDataSourceNames = expectedExistingSegments.stream().map(DataSegment::getDataSource).collect(Collectors.toSet());
        final Map<String, VersionedIntervalTimeline<String, ReferenceCountingSegment>> expectedTimelines = new HashMap<>();
        for (DataSegment segment : expectedExistingSegments) {
            final VersionedIntervalTimeline<String, ReferenceCountingSegment> expectedTimeline = expectedTimelines.computeIfAbsent(segment.getDataSource(), k -> new VersionedIntervalTimeline<>(Ordering.natural()));
            expectedTimeline.add(segment.getInterval(), segment.getVersion(), segment.getShardSpec().createChunk(ReferenceCountingSegment.wrapSegment(new TestSegmentUtils.SegmentForTesting(segment.getDataSource(), (Interval) segment.getLoadSpec().get("interval"), MapUtils.getString(segment.getLoadSpec(), "version")), segment.getShardSpec())));
        }
        Assert.assertEquals(expectedDataSourceNames, segmentManager.getDataSourceNames());
        Assert.assertEquals(expectedDataSourceCounts, segmentManager.getDataSourceCounts());
        Assert.assertEquals(expectedDataSourceSizes, segmentManager.getDataSourceSizes());
        final Map<String, DataSourceState> dataSources = segmentManager.getDataSources();
        Assert.assertEquals(expectedTimelines.size(), dataSources.size());
        dataSources.forEach((sourceName, dataSourceState) -> {
            Assert.assertEquals(expectedDataSourceCounts.get(sourceName).longValue(), dataSourceState.getNumSegments());
            Assert.assertEquals(expectedDataSourceSizes.get(sourceName).longValue(), dataSourceState.getTotalSegmentSize());
            Assert.assertEquals(expectedTimelines.get(sourceName).getAllTimelineEntries(), dataSourceState.getTimeline().getAllTimelineEntries());
        });
    }

    @Test
    public void testLoadAndDropNonRootGenerationSegment_1() throws SegmentLoadingException, IOException {
        final DataSegment segment = new DataSegment("small_source", Intervals.of("0/1000"), "0", ImmutableMap.of("type", "test", "interval", Intervals.of("0/1000"), "version", 0), new ArrayList<>(), new ArrayList<>(), new NumberedOverwriteShardSpec(PartitionIds.NON_ROOT_GEN_START_PARTITION_ID + 10, 10, 20, (short) 1, (short) 1), 0, 10);
        segmentManager.loadSegment(segment);
        assertResult(ImmutableList.of(segment));
    }

    @Test
    public void testLoadAndDropNonRootGenerationSegment_2() throws SegmentLoadingException, IOException {
        assertResult(ImmutableList.of());
    }
}
