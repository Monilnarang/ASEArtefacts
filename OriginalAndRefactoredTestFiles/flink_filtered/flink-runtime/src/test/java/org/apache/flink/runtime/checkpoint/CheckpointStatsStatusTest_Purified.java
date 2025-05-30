package org.apache.flink.runtime.checkpoint;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class CheckpointStatsStatusTest_Purified {

    @Test
    void testStatusValues_1_testMerged_1() {
        CheckpointStatsStatus inProgress = CheckpointStatsStatus.IN_PROGRESS;
        assertThat(inProgress.isInProgress()).isTrue();
        assertThat(inProgress.isCompleted()).isFalse();
        assertThat(inProgress.isFailed()).isFalse();
    }

    @Test
    void testStatusValues_4_testMerged_2() {
        CheckpointStatsStatus completed = CheckpointStatsStatus.COMPLETED;
        assertThat(completed.isInProgress()).isFalse();
        assertThat(completed.isCompleted()).isTrue();
        assertThat(completed.isFailed()).isFalse();
    }

    @Test
    void testStatusValues_7_testMerged_3() {
        CheckpointStatsStatus failed = CheckpointStatsStatus.FAILED;
        assertThat(failed.isInProgress()).isFalse();
        assertThat(failed.isCompleted()).isFalse();
        assertThat(failed.isFailed()).isTrue();
    }
}
