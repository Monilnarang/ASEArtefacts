package org.apache.flink.runtime.shuffle;

import org.apache.flink.configuration.BatchExecutionOptions;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.configuration.HighAvailabilityOptions;
import org.apache.flink.core.fs.FileSystem;
import org.apache.flink.core.fs.Path;
import org.apache.flink.runtime.highavailability.HighAvailabilityServicesUtils;
import org.apache.flink.util.concurrent.Executors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import static org.assertj.core.api.Assertions.assertThat;

class ShuffleMasterSnapshotUtilTest_Purified {

    private ShuffleMaster<?> shuffleMaster;

    private Configuration configuration;

    private ShuffleMasterSnapshot restoredSnapshot;

    private boolean triggeredSnapshot;

    @TempDir
    java.nio.file.Path temporaryFolder;

    @BeforeEach
    public void setUp() {
        shuffleMaster = new NettyShuffleMaster(new ShuffleMasterContextImpl(new Configuration(), throwable -> {
        })) {

            @Override
            public void snapshotState(CompletableFuture<ShuffleMasterSnapshot> snapshotFuture) {
                snapshotFuture.complete(new TestingShuffleMasterSnapshot());
                triggeredSnapshot = true;
            }

            @Override
            public void restoreState(ShuffleMasterSnapshot snapshot) {
                restoredSnapshot = snapshot;
            }
        };
        restoredSnapshot = null;
        triggeredSnapshot = false;
        configuration = new Configuration();
        configuration.set(BatchExecutionOptions.JOB_RECOVERY_ENABLED, true);
        configuration.set(HighAvailabilityOptions.HA_STORAGE_PATH, temporaryFolder.toString());
    }

    private static final class TestingShuffleMasterSnapshot implements ShuffleMasterSnapshot {

        @Override
        public boolean isIncremental() {
            return false;
        }
    }

    @Test
    void testRestoreOrSnapshotShuffleMaster_1_testMerged_1() throws IOException {
        String clusterId = configuration.get(HighAvailabilityOptions.HA_CLUSTER_ID);
        Path path = new Path(HighAvailabilityServicesUtils.getClusterHighAvailableStoragePath(configuration), "shuffleMaster-snapshot");
        FileSystem fileSystem = path.getFileSystem();
        assertThat(fileSystem.exists(new Path(path, clusterId))).isFalse();
        assertThat(ShuffleMasterSnapshotUtil.isShuffleMasterSnapshotExist(path, clusterId)).isFalse();
        ShuffleMasterSnapshotUtil.restoreOrSnapshotShuffleMaster(shuffleMaster, configuration, Executors.directExecutor());
        assertThat(fileSystem.exists(new Path(path, clusterId))).isTrue();
        assertThat(ShuffleMasterSnapshotUtil.isShuffleMasterSnapshotExist(path, clusterId)).isTrue();
        ShuffleMasterSnapshot snapshot = ShuffleMasterSnapshotUtil.readSnapshot(path, clusterId);
        assertThat(snapshot).isInstanceOf(TestingShuffleMasterSnapshot.class);
    }

    @Test
    void testRestoreOrSnapshotShuffleMaster_6() throws IOException {
        assertThat(restoredSnapshot).isNull();
    }

    @Test
    void testRestoreOrSnapshotShuffleMaster_7() throws IOException {
        assertThat(triggeredSnapshot).isTrue();
    }

    @Test
    void testRestoreOrSnapshotShuffleMaster_8() throws IOException {
        assertThat(restoredSnapshot).isInstanceOf(TestingShuffleMasterSnapshot.class);
    }

    @Test
    void testRestoreOrSnapshotShuffleMaster_9() throws IOException {
        assertThat(triggeredSnapshot).isFalse();
    }
}
