package org.apache.druid.server.coordination;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Scopes;
import com.google.inject.name.Names;
import org.apache.druid.guice.LazySingleton;
import org.apache.druid.guice.LifecycleModule;
import org.apache.druid.guice.ServerTypeConfig;
import org.apache.druid.jackson.JacksonModule;
import org.apache.druid.java.util.common.Intervals;
import org.apache.druid.java.util.emitter.EmittingLogger;
import org.apache.druid.java.util.metrics.StubServiceEmitter;
import org.apache.druid.segment.loading.SegmentLoaderConfig;
import org.apache.druid.segment.loading.StorageLocationConfig;
import org.apache.druid.server.SegmentManager;
import org.apache.druid.server.metrics.DataSourceTaskIdHolder;
import org.apache.druid.test.utils.TestSegmentCacheManager;
import org.apache.druid.timeline.DataSegment;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static org.apache.druid.segment.TestSegmentUtils.makeSegment;

public class SegmentBootstrapperTest_Purified {

    private static final int COUNT = 50;

    private TestDataSegmentAnnouncer segmentAnnouncer;

    private TestDataServerAnnouncer serverAnnouncer;

    private SegmentLoaderConfig segmentLoaderConfig;

    private TestCoordinatorClient coordinatorClient;

    private StubServiceEmitter serviceEmitter;

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Before
    public void setUp() throws IOException {
        final File segmentCacheDir = temporaryFolder.newFolder();
        segmentAnnouncer = new TestDataSegmentAnnouncer();
        serverAnnouncer = new TestDataServerAnnouncer();
        segmentLoaderConfig = new SegmentLoaderConfig() {

            @Override
            public File getInfoDir() {
                return segmentCacheDir;
            }

            @Override
            public int getNumLoadingThreads() {
                return 5;
            }

            @Override
            public int getAnnounceIntervalMillis() {
                return 50;
            }

            @Override
            public List<StorageLocationConfig> getLocations() {
                return Collections.singletonList(new StorageLocationConfig(segmentCacheDir, null, null));
            }
        };
        coordinatorClient = new TestCoordinatorClient();
        serviceEmitter = new StubServiceEmitter();
        EmittingLogger.registerEmitter(serviceEmitter);
    }

    private static <T> void assertUnsortedListsAreEqual(List<T> expected, List<T> actual) {
        Assert.assertEquals(expected.size(), actual.size());
        Assert.assertEquals(Set.copyOf(expected), Set.copyOf(actual));
    }

    @Test
    public void testLoadBootstrapSegmentsWhenExceptionThrown_1_testMerged_1() throws Exception {
        final TestSegmentCacheManager cacheManager = new TestSegmentCacheManager();
        final SegmentManager segmentManager = new SegmentManager(cacheManager);
        Assert.assertTrue(segmentManager.getDataSourceCounts().isEmpty());
        Assert.assertEquals(ImmutableList.of(), cacheManager.getObservedBootstrapSegments());
        Assert.assertEquals(ImmutableList.of(), cacheManager.getObservedBootstrapSegmentsLoadedIntoPageCache());
    }

    @Test
    public void testLoadBootstrapSegmentsWhenExceptionThrown_2() throws Exception {
        Assert.assertEquals(1, serverAnnouncer.getObservedCount());
    }

    @Test
    public void testLoadBootstrapSegmentsWhenExceptionThrown_4() throws Exception {
        Assert.assertEquals(ImmutableList.of(), segmentAnnouncer.getObservedSegments());
    }
}
