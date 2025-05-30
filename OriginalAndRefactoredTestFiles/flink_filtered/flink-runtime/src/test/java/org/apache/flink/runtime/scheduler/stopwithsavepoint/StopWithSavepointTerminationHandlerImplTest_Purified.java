package org.apache.flink.runtime.scheduler.stopwithsavepoint;

import org.apache.flink.api.common.JobID;
import org.apache.flink.core.execution.SavepointFormatType;
import org.apache.flink.runtime.checkpoint.CheckpointProperties;
import org.apache.flink.runtime.checkpoint.CompletedCheckpoint;
import org.apache.flink.runtime.checkpoint.TestingCheckpointScheduling;
import org.apache.flink.runtime.execution.ExecutionState;
import org.apache.flink.runtime.scheduler.SchedulerNG;
import org.apache.flink.runtime.scheduler.TestingSchedulerNG;
import org.apache.flink.runtime.state.StreamStateHandle;
import org.apache.flink.runtime.state.testutils.EmptyStreamStateHandle;
import org.apache.flink.runtime.state.testutils.TestCompletedCheckpointStorageLocation;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;
import static org.apache.flink.core.testutils.FlinkAssertions.assertThatFuture;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class StopWithSavepointTerminationHandlerImplTest_Purified {

    private static final Logger log = LoggerFactory.getLogger(StopWithSavepointTerminationHandlerImplTest.class);

    private static final JobID JOB_ID = new JobID();

    private final TestingCheckpointScheduling checkpointScheduling = new TestingCheckpointScheduling(false);

    private StopWithSavepointTerminationHandlerImpl createTestInstanceFailingOnGlobalFailOver() {
        return createTestInstance(throwableCausingGlobalFailOver -> {
            throw new AssertionError("No global failover should be triggered.");
        });
    }

    private StopWithSavepointTerminationHandlerImpl createTestInstance(Consumer<Throwable> handleGlobalFailureConsumer) {
        checkpointScheduling.stopCheckpointScheduler();
        final SchedulerNG scheduler = TestingSchedulerNG.newBuilder().setHandleGlobalFailureConsumer(handleGlobalFailureConsumer).build();
        return new StopWithSavepointTerminationHandlerImpl(JOB_ID, scheduler, checkpointScheduling, log);
    }

    public void assertSavepointCreationFailure(Consumer<StopWithSavepointTerminationHandler> handleExecutionsTermination) {
        final StopWithSavepointTerminationHandlerImpl testInstance = createTestInstanceFailingOnGlobalFailOver();
        final String expectedErrorMessage = "Expected exception during savepoint creation.";
        testInstance.handleSavepointCreation(null, new Exception(expectedErrorMessage));
        handleExecutionsTermination.accept(testInstance);
        assertThatThrownBy(() -> testInstance.getSavepointPath().get()).withFailMessage("An ExecutionException is expected.").isInstanceOf(Throwable.class).hasMessageContaining(expectedErrorMessage);
        assertThat(checkpointScheduling.isEnabled()).withFailMessage("Checkpoint scheduling should be enabled.").isTrue();
    }

    private static CompletedCheckpoint createCompletedSavepoint(StreamStateHandle streamStateHandle) {
        return new CompletedCheckpoint(JOB_ID, 0, 0L, 0L, new HashMap<>(), null, CheckpointProperties.forSavepoint(true, SavepointFormatType.CANONICAL), new TestCompletedCheckpointStorageLocation(streamStateHandle, "savepoint-path"), null);
    }

    @Test
    void testHappyPath_1_testMerged_1() throws ExecutionException, InterruptedException {
        final StopWithSavepointTerminationHandlerImpl testInstance = createTestInstanceFailingOnGlobalFailOver();
        final EmptyStreamStateHandle streamStateHandle = new EmptyStreamStateHandle();
        final CompletedCheckpoint completedSavepoint = createCompletedSavepoint(streamStateHandle);
        testInstance.handleSavepointCreation(completedSavepoint, null);
        testInstance.handleExecutionsTermination(Collections.singleton(ExecutionState.FINISHED));
        assertThatFuture(testInstance.getSavepointPath()).isCompletedWithValue(completedSavepoint.getExternalPointer());
    }

    @Test
    void testHappyPath_3() throws ExecutionException, InterruptedException {
    }
}
