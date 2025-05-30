package org.apache.flink.runtime.state;

import org.apache.flink.api.common.JobID;
import org.apache.flink.runtime.checkpoint.OperatorSubtaskState;
import org.apache.flink.runtime.checkpoint.TaskStateSnapshot;
import org.apache.flink.runtime.clusterframework.types.AllocationID;
import org.apache.flink.runtime.jobgraph.JobVertexID;
import org.apache.flink.runtime.jobgraph.OperatorID;
import org.apache.flink.runtime.state.changelog.ChangelogStateBackendHandle.ChangelogStateBackendHandleImpl;
import org.apache.flink.util.FlinkRuntimeException;
import org.apache.flink.util.concurrent.Executors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import static org.assertj.core.api.Assertions.assertThat;

class ChangelogTaskLocalStateStoreTest_Purified extends TaskLocalStateStoreImplTest {

    private LocalSnapshotDirectoryProvider localSnapshotDirectoryProvider;

    @BeforeEach
    @Override
    void before() throws Exception {
        super.before();
        this.taskLocalStateStore = createChangelogTaskLocalStateStore(allocationBaseDirs, jobID, allocationID, jobVertexID, subtaskIdx);
    }

    @Nonnull
    private ChangelogTaskLocalStateStore createChangelogTaskLocalStateStore(File[] allocationBaseDirs, JobID jobID, AllocationID allocationID, JobVertexID jobVertexID, int subtaskIdx) {
        LocalSnapshotDirectoryProviderImpl directoryProvider = new LocalSnapshotDirectoryProviderImpl(allocationBaseDirs, jobID, jobVertexID, subtaskIdx);
        this.localSnapshotDirectoryProvider = directoryProvider;
        LocalRecoveryConfig localRecoveryConfig = LocalRecoveryConfig.backupAndRecoveryEnabled(directoryProvider);
        return new ChangelogTaskLocalStateStore(jobID, allocationID, jobVertexID, subtaskIdx, localRecoveryConfig, Executors.directExecutor());
    }

    private boolean checkMaterializedDirExists(long materializationID) {
        File materializedDir = localSnapshotDirectoryProvider.subtaskSpecificCheckpointDirectory(materializationID);
        return materializedDir.exists();
    }

    private void writeToMaterializedDir(long materializationID) {
        File materializedDir = localSnapshotDirectoryProvider.subtaskSpecificCheckpointDirectory(materializationID);
        if (!materializedDir.exists() && !materializedDir.mkdirs()) {
            throw new FlinkRuntimeException(String.format("Could not create the materialized directory '%s'", materializedDir));
        }
    }

    private TestingTaskStateSnapshot storeChangelogStates(long checkpointID, long materializationID) {
        writeToMaterializedDir(materializationID);
        OperatorID operatorID = new OperatorID();
        TestingTaskStateSnapshot taskStateSnapshot = new TestingTaskStateSnapshot();
        OperatorSubtaskState operatorSubtaskState = OperatorSubtaskState.builder().setManagedKeyedState(new ChangelogStateBackendHandleImpl(Collections.emptyList(), Collections.emptyList(), new KeyGroupRange(0, 3), checkpointID, materializationID, checkpointID)).build();
        taskStateSnapshot.putSubtaskStateByOperatorID(operatorID, operatorSubtaskState);
        taskLocalStateStore.storeLocalState(checkpointID, taskStateSnapshot);
        return taskStateSnapshot;
    }

    @Test
    @Override
    void confirmCheckpoint_1_testMerged_1() throws Exception {
        TestingTaskStateSnapshot stateSnapshot3 = storeChangelogStates(3, 1);
        taskLocalStateStore.confirmCheckpoint(3);
        assertThat(taskLocalStateStore.retrieveLocalState(2)).isNull();
        assertThat(taskLocalStateStore.retrieveLocalState(3)).isEqualTo(stateSnapshot3);
        TestingTaskStateSnapshot stateSnapshot4 = storeChangelogStates(4, 2);
        taskLocalStateStore.confirmCheckpoint(4);
        assertThat(taskLocalStateStore.retrieveLocalState(3)).isNull();
        assertThat(stateSnapshot3.isDiscarded()).isTrue();
        assertThat(taskLocalStateStore.retrieveLocalState(4)).isEqualTo(stateSnapshot4);
    }

    @Test
    @Override
    void confirmCheckpoint_2() throws Exception {
        TestingTaskStateSnapshot stateSnapshot2 = storeChangelogStates(2, 1);
        assertThat(stateSnapshot2.isDiscarded()).isTrue();
    }

    @Test
    @Override
    void confirmCheckpoint_3() throws Exception {
        TestingTaskStateSnapshot stateSnapshot1 = storeChangelogStates(1, 1);
        assertThat(stateSnapshot1.isDiscarded()).isTrue();
    }

    @Test
    @Override
    void confirmCheckpoint_4() throws Exception {
        assertThat(checkMaterializedDirExists(1)).isTrue();
    }

    @Test
    @Override
    void confirmCheckpoint_8() throws Exception {
        assertThat(checkMaterializedDirExists(1)).isFalse();
    }

    @Test
    @Override
    void abortCheckpoint_1_testMerged_1() throws Exception {
        TestingTaskStateSnapshot stateSnapshot1 = storeChangelogStates(1, 1);
        TestingTaskStateSnapshot stateSnapshot3 = storeChangelogStates(3, 2);
        taskLocalStateStore.abortCheckpoint(2);
        assertThat(taskLocalStateStore.retrieveLocalState(2)).isNull();
        assertThat(taskLocalStateStore.retrieveLocalState(1)).isEqualTo(stateSnapshot1);
        assertThat(taskLocalStateStore.retrieveLocalState(3)).isEqualTo(stateSnapshot3);
    }

    @Test
    @Override
    void abortCheckpoint_2() throws Exception {
        TestingTaskStateSnapshot stateSnapshot2 = storeChangelogStates(2, 2);
        assertThat(stateSnapshot2.isDiscarded()).isTrue();
    }

    @Test
    @Override
    void abortCheckpoint_3() throws Exception {
        assertThat(checkMaterializedDirExists(2)).isTrue();
    }

    @Test
    @Override
    void abortCheckpoint_5() throws Exception {
        assertThat(checkMaterializedDirExists(1)).isTrue();
    }

    @Test
    @Override
    void abortCheckpoint_7() throws Exception {
        assertThat(checkMaterializedDirExists(2)).isFalse();
    }
}
