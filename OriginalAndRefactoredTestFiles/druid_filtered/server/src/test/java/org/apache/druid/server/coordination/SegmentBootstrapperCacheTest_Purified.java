package org.apache.druid.server.coordination;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import org.apache.druid.guice.ServerTypeConfig;
import org.apache.druid.java.util.emitter.EmittingLogger;
import org.apache.druid.java.util.emitter.service.ServiceEmitter;
import org.apache.druid.java.util.metrics.StubServiceEmitter;
import org.apache.druid.segment.TestHelper;
import org.apache.druid.segment.TestIndex;
import org.apache.druid.segment.TestSegmentUtils;
import org.apache.druid.segment.loading.DataSegmentPusher;
import org.apache.druid.segment.loading.LeastBytesUsedStorageLocationSelectorStrategy;
import org.apache.druid.segment.loading.SegmentLoaderConfig;
import org.apache.druid.segment.loading.SegmentLoadingException;
import org.apache.druid.segment.loading.SegmentLocalCacheManager;
import org.apache.druid.segment.loading.StorageLocation;
import org.apache.druid.segment.loading.StorageLocationConfig;
import org.apache.druid.server.SegmentManager;
import org.apache.druid.server.metrics.DataSourceTaskIdHolder;
import org.apache.druid.timeline.DataSegment;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SegmentBootstrapperCacheTest_Purified {

    private static final long MAX_SIZE = 1000L;

    private static final long SEGMENT_SIZE = 100L;

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    private TestDataSegmentAnnouncer segmentAnnouncer;

    private TestDataServerAnnouncer serverAnnouncer;

    private SegmentManager segmentManager;

    private SegmentLoaderConfig loaderConfig;

    private SegmentLocalCacheManager cacheManager;

    private TestCoordinatorClient coordinatorClient;

    private ServiceEmitter emitter;

    private ObjectMapper objectMapper;

    @Before
    public void setup() {
        loaderConfig = new SegmentLoaderConfig() {

            @Override
            public File getInfoDir() {
                return temporaryFolder.getRoot();
            }

            @Override
            public List<StorageLocationConfig> getLocations() {
                return Collections.singletonList(new StorageLocationConfig(temporaryFolder.getRoot(), MAX_SIZE, null));
            }
        };
        objectMapper = TestHelper.makeJsonMapper();
        objectMapper.registerSubtypes(TestSegmentUtils.TestLoadSpec.class);
        objectMapper.registerSubtypes(TestSegmentUtils.TestSegmentizerFactory.class);
        final List<StorageLocation> storageLocations = loaderConfig.toStorageLocations();
        cacheManager = new SegmentLocalCacheManager(storageLocations, loaderConfig, new LeastBytesUsedStorageLocationSelectorStrategy(storageLocations), TestIndex.INDEX_IO, objectMapper);
        segmentManager = new SegmentManager(cacheManager);
        serverAnnouncer = new TestDataServerAnnouncer();
        segmentAnnouncer = new TestDataSegmentAnnouncer();
        coordinatorClient = new TestCoordinatorClient();
        emitter = new StubServiceEmitter();
        EmittingLogger.registerEmitter(emitter);
    }

    @Test
    public void testLoadStartStop_1() throws IOException {
        Assert.assertEquals(1, serverAnnouncer.getObservedCount());
    }

    @Test
    public void testLoadStartStop_2() throws IOException {
        Assert.assertEquals(0, serverAnnouncer.getObservedCount());
    }
}
