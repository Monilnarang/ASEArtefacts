package org.apache.druid.segment.loading;

import com.fasterxml.jackson.databind.InjectableValues;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.apache.druid.error.DruidException;
import org.apache.druid.error.DruidExceptionMatcher;
import org.apache.druid.java.util.common.FileUtils;
import org.apache.druid.java.util.common.Intervals;
import org.apache.druid.java.util.emitter.EmittingLogger;
import org.apache.druid.segment.CursorFactory;
import org.apache.druid.segment.QueryableIndex;
import org.apache.druid.segment.ReferenceCountingSegment;
import org.apache.druid.segment.SegmentLazyLoadFailCallback;
import org.apache.druid.segment.TestHelper;
import org.apache.druid.segment.TestIndex;
import org.apache.druid.segment.TestSegmentUtils;
import org.apache.druid.server.metrics.NoopServiceEmitter;
import org.apache.druid.timeline.DataSegment;
import org.apache.druid.timeline.partition.NoneShardSpec;
import org.apache.druid.timeline.partition.TombstoneShardSpec;
import org.hamcrest.MatcherAssert;
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
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

public class SegmentLocalCacheManagerTest_Purified {

    @Rule
    public final TemporaryFolder tmpFolder = new TemporaryFolder();

    private ObjectMapper jsonMapper;

    private File localSegmentCacheFolder;

    private SegmentLocalCacheManager manager;

    @Before
    public void setUp() throws Exception {
        jsonMapper = TestHelper.makeJsonMapper();
        jsonMapper.registerSubtypes(new NamedType(LocalLoadSpec.class, "local"), new NamedType(TombstoneLoadSpec.class, "tombstone"));
        jsonMapper.setInjectableValues(new InjectableValues.Std().addValue(LocalDataSegmentPuller.class, new LocalDataSegmentPuller()));
        EmittingLogger.registerEmitter(new NoopServiceEmitter());
        localSegmentCacheFolder = tmpFolder.newFolder("segment_cache_folder");
        final List<StorageLocationConfig> locationConfigs = new ArrayList<>();
        final StorageLocationConfig locationConfig = new StorageLocationConfig(localSegmentCacheFolder, 10000000000L, null);
        locationConfigs.add(locationConfig);
        final SegmentLoaderConfig loaderConfig = new SegmentLoaderConfig().withLocations(locationConfigs);
        manager = new SegmentLocalCacheManager(loaderConfig.toStorageLocations(), loaderConfig, new LeastBytesUsedStorageLocationSelectorStrategy(loaderConfig.toStorageLocations()), TestIndex.INDEX_IO, jsonMapper);
        Assert.assertTrue(manager.canHandleSegments());
    }

    private DataSegment dataSegmentWithInterval(String intervalStr) {
        return dataSegmentWithInterval(intervalStr, 10L);
    }

    private DataSegment dataSegmentWithInterval(String intervalStr, long size) {
        return DataSegment.builder().dataSource("test_segment_loader").interval(Intervals.of(intervalStr)).loadSpec(ImmutableMap.of("type", "local", "path", "somewhere")).version("2015-05-27T03:38:35.683Z").dimensions(ImmutableList.of()).metrics(ImmutableList.of()).shardSpec(NoneShardSpec.instance()).binaryVersion(9).size(size).build();
    }

    private void createLocalSegmentFile(File segmentSrcFolder, String localSegmentPath) throws Exception {
        final File localSegmentFile = new File(segmentSrcFolder, localSegmentPath);
        FileUtils.mkdirp(localSegmentFile);
        final File indexZip = new File(localSegmentFile, "index.zip");
        indexZip.createNewFile();
    }

    private StorageLocationConfig createStorageLocationConfig(String localPath, long maxSize, boolean writable) throws Exception {
        final File localStorageFolder = tmpFolder.newFolder(localPath);
        localStorageFolder.setWritable(writable);
        final StorageLocationConfig locationConfig = new StorageLocationConfig(localStorageFolder, maxSize, 1.0);
        return locationConfig;
    }

    private void writeSegmentFile(final DataSegment segment) throws IOException {
        final File segmentFile = new File(localSegmentCacheFolder, DataSegmentPusher.getDefaultStorageDir(segment, false));
        FileUtils.mkdirp(segmentFile);
    }

    @Test
    public void testIfSegmentIsLoaded_1() throws IOException {
        final DataSegment cachedSegment = dataSegmentWithInterval("2014-10-20T00:00:00Z/P1D");
        Assert.assertTrue("Expect cache hit", manager.isSegmentCached(cachedSegment));
    }

    @Test
    public void testIfSegmentIsLoaded_2() throws IOException {
        final DataSegment uncachedSegment = dataSegmentWithInterval("2014-10-21T00:00:00Z/P1D");
        Assert.assertFalse("Expect cache miss", manager.isSegmentCached(uncachedSegment));
    }
}
