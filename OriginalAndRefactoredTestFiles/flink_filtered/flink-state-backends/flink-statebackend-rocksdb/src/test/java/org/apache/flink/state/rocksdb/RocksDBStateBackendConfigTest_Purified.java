package org.apache.flink.state.rocksdb;

import org.apache.flink.api.common.JobID;
import org.apache.flink.api.common.typeutils.base.IntSerializer;
import org.apache.flink.configuration.CheckpointingOptions;
import org.apache.flink.configuration.ConfigOption;
import org.apache.flink.configuration.ConfigOptions;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.configuration.CoreOptions;
import org.apache.flink.configuration.MemorySize;
import org.apache.flink.configuration.ReadableConfig;
import org.apache.flink.core.fs.CloseableRegistry;
import org.apache.flink.core.fs.FileSystem;
import org.apache.flink.core.fs.Path;
import org.apache.flink.metrics.MetricGroup;
import org.apache.flink.metrics.groups.UnregisteredMetricsGroup;
import org.apache.flink.runtime.execution.Environment;
import org.apache.flink.runtime.jobgraph.JobVertexID;
import org.apache.flink.runtime.operators.testutils.MockEnvironment;
import org.apache.flink.runtime.operators.testutils.MockEnvironmentBuilder;
import org.apache.flink.runtime.query.KvStateRegistry;
import org.apache.flink.runtime.query.TaskKvStateRegistry;
import org.apache.flink.runtime.state.CheckpointableKeyedStateBackend;
import org.apache.flink.runtime.state.KeyGroupRange;
import org.apache.flink.runtime.state.KeyedStateBackendParametersImpl;
import org.apache.flink.runtime.state.heap.HeapPriorityQueueSetFactory;
import org.apache.flink.runtime.state.ttl.TtlTimeProvider;
import org.apache.flink.runtime.util.TestingTaskManagerRuntimeInfo;
import org.apache.flink.testutils.junit.FailsInGHAContainerWithRootUser;
import org.apache.flink.util.FileUtils;
import org.apache.flink.util.IOUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.jupiter.api.Timeout;
import org.junit.rules.TemporaryFolder;
import org.rocksdb.BlockBasedTableConfig;
import org.rocksdb.BloomFilter;
import org.rocksdb.ColumnFamilyOptions;
import org.rocksdb.CompactionStyle;
import org.rocksdb.CompressionType;
import org.rocksdb.DBOptions;
import org.rocksdb.FlushOptions;
import org.rocksdb.InfoLogLevel;
import org.rocksdb.util.SizeUnit;
import java.io.File;
import java.nio.file.Files;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import static org.apache.flink.state.rocksdb.RocksDBTestUtils.createKeyedStateBackend;
import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@SuppressWarnings("serial")
public class RocksDBStateBackendConfigTest_Purified {

    @Rule
    public final TemporaryFolder tempFolder = new TemporaryFolder();

    private void verifySetParameter(Runnable setter) {
        try {
            setter.run();
            fail("No expected IllegalArgumentException.");
        } catch (IllegalArgumentException expected) {
        }
    }

    static MockEnvironment getMockEnvironment(File tempDir) {
        return MockEnvironment.builder().setUserCodeClassLoader(RocksDBStateBackendConfigTest.class.getClassLoader()).setTaskManagerRuntimeInfo(new TestingTaskManagerRuntimeInfo(new Configuration(), tempDir)).build();
    }

    private void verifyIllegalArgument(ConfigOption<?> configOption, String configValue) {
        Configuration configuration = new Configuration();
        configuration.setString(configOption.key(), configValue);
        EmbeddedRocksDBStateBackend stateBackend = new EmbeddedRocksDBStateBackend();
        try {
            stateBackend.configure(configuration, null);
            fail("Not throwing expected IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
        }
    }

    public static class TestOptionsFactory implements ConfigurableRocksDBOptionsFactory {

        public static final ConfigOption<Integer> BACKGROUND_JOBS_OPTION = ConfigOptions.key("my.custom.rocksdb.backgroundJobs").intType().defaultValue(2);

        private int backgroundJobs = BACKGROUND_JOBS_OPTION.defaultValue();

        @Override
        public DBOptions createDBOptions(DBOptions currentOptions, Collection<AutoCloseable> handlesToClose) {
            return currentOptions.setMaxBackgroundJobs(backgroundJobs);
        }

        @Override
        public ColumnFamilyOptions createColumnOptions(ColumnFamilyOptions currentOptions, Collection<AutoCloseable> handlesToClose) {
            return currentOptions.setCompactionStyle(CompactionStyle.UNIVERSAL);
        }

        @Override
        public RocksDBOptionsFactory configure(ReadableConfig configuration) {
            this.backgroundJobs = configuration.get(BACKGROUND_JOBS_OPTION);
            return this;
        }
    }

    @Test
    public void testConfigureTimerService_1() throws Exception {
        Assert.assertEquals("state.backend.rocksdb.timer-service.factory", RocksDBOptions.TIMER_SERVICE_FACTORY.key());
    }

    @Test
    public void testConfigureTimerService_2() throws Exception {
        Assert.assertEquals(2, EmbeddedRocksDBStateBackend.PriorityQueueStateType.values().length);
    }

    @Test
    public void testConfigureTimerService_3() throws Exception {
        Assert.assertEquals("ROCKSDB", EmbeddedRocksDBStateBackend.PriorityQueueStateType.ROCKSDB.toString());
    }

    @Test
    public void testConfigureTimerService_4() throws Exception {
        Assert.assertEquals("HEAP", EmbeddedRocksDBStateBackend.PriorityQueueStateType.HEAP.toString());
    }

    @Test
    public void testConfigureTimerService_5() throws Exception {
        Assert.assertEquals(EmbeddedRocksDBStateBackend.PriorityQueueStateType.ROCKSDB, RocksDBOptions.TIMER_SERVICE_FACTORY.defaultValue());
    }

    @Test
    public void testConfigureTimerService_6_testMerged_6() throws Exception {
        final MockEnvironment env = getMockEnvironment(tempFolder.newFolder());
        EmbeddedRocksDBStateBackend rocksDbBackend = new EmbeddedRocksDBStateBackend();
        RocksDBKeyedStateBackend<Integer> keyedBackend = createKeyedStateBackend(rocksDbBackend, env, IntSerializer.INSTANCE);
        Assert.assertEquals(RocksDBPriorityQueueSetFactory.class, keyedBackend.getPriorityQueueFactory().getClass());
        keyedBackend.dispose();
        Configuration conf = new Configuration();
        conf.set(RocksDBOptions.TIMER_SERVICE_FACTORY, EmbeddedRocksDBStateBackend.PriorityQueueStateType.HEAP);
        rocksDbBackend = rocksDbBackend.configure(conf, Thread.currentThread().getContextClassLoader());
        keyedBackend = createKeyedStateBackend(rocksDbBackend, env, IntSerializer.INSTANCE);
        Assert.assertEquals(HeapPriorityQueueSetFactory.class, keyedBackend.getPriorityQueueFactory().getClass());
    }
}
