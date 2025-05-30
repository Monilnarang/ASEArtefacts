package org.apache.flink.runtime.checkpoint;

import org.apache.flink.core.execution.SavepointFormatType;
import org.apache.flink.runtime.checkpoint.CheckpointCoordinator.CheckpointTriggerRequest;
import org.apache.flink.util.clock.ManualClock;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.rangeClosed;
import static org.apache.flink.runtime.checkpoint.CheckpointFailureReason.MINIMUM_TIME_BETWEEN_CHECKPOINTS;
import static org.apache.flink.runtime.checkpoint.CheckpointFailureReason.TOO_MANY_CHECKPOINT_REQUESTS;
import static org.apache.flink.runtime.checkpoint.CheckpointRetentionPolicy.NEVER_RETAIN_AFTER_TERMINATION;
import static org.assertj.core.api.Assertions.assertThat;

class CheckpointRequestDeciderTest_Purified {

    private enum TriggerExpectation {

        IMMEDIATELY, AFTER_PAUSE, DROPPED
    }

    private void assertFailed(CheckpointTriggerRequest request, CheckpointFailureReason reason) {
        assertThat(request.getOnCompletionFuture()).isCompletedExceptionally();
        request.getOnCompletionFuture().handle((checkpoint, throwable) -> {
            assertThat(checkpoint).isNull();
            assertThat(throwable).isNotNull().isInstanceOfSatisfying(CheckpointException.class, e -> assertThat(e.getCheckpointFailureReason()).isEqualTo(reason));
            return null;
        }).join();
    }

    public CheckpointRequestDecider decider(int maxQueuedRequests) {
        return decider(maxQueuedRequests, 1, 1, new AtomicInteger(0), new AtomicInteger(0));
    }

    private CheckpointRequestDecider decider(int maxQueued, int maxPending, int minPause, AtomicInteger currentPending, AtomicInteger currentCleaning) {
        ManualClock clock = new ManualClock();
        clock.advanceTime(1, TimeUnit.DAYS);
        return new CheckpointRequestDecider(maxPending, NO_OP, clock, minPause, currentPending::get, currentCleaning::get, maxQueued);
    }

    private static final BiConsumer<Long, Long> NO_OP = (currentTimeMillis, tillNextMillis) -> {
    };

    static CheckpointTriggerRequest regularCheckpoint() {
        return checkpointRequest(true);
    }

    private static CheckpointTriggerRequest manualCheckpoint() {
        return checkpointRequest(false);
    }

    private static CheckpointTriggerRequest regularSavepoint() {
        return savepointRequest(true, false);
    }

    private static CheckpointTriggerRequest periodicSavepoint() {
        return savepointRequest(true, true);
    }

    private static CheckpointTriggerRequest nonForcedPeriodicSavepoint() {
        return savepointRequest(false, true);
    }

    private static CheckpointTriggerRequest nonForcedSavepoint() {
        return savepointRequest(false, false);
    }

    private static CheckpointTriggerRequest savepointRequest(boolean force, boolean periodic) {
        return new CheckpointTriggerRequest(CheckpointProperties.forSavepoint(force, SavepointFormatType.CANONICAL), null, periodic);
    }

    private static CheckpointTriggerRequest checkpointRequest(boolean periodic) {
        return new CheckpointTriggerRequest(CheckpointProperties.forCheckpoint(NEVER_RETAIN_AFTER_TERMINATION), null, periodic);
    }

    @Test
    void testQueueSizeLimitPriority_1() {
        CheckpointTriggerRequest checkpoint = regularCheckpoint();
        decider.chooseRequestToExecute(checkpoint, isTriggering, 0);
        assertFailed(checkpoint, TOO_MANY_CHECKPOINT_REQUESTS);
    }

    @Test
    void testQueueSizeLimitPriority_2() {
        CheckpointTriggerRequest savepoint = regularSavepoint();
        decider.chooseRequestToExecute(savepoint, isTriggering, 0);
        assertThat(savepoint.getOnCompletionFuture()).isNotDone();
    }
}
