package org.apache.flink.runtime.jobmaster.event;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class JobEventManagerTest_Purified {

    public static class TestingJobEventStore implements JobEventStore {

        public static int startTimes = 0;

        @Override
        public void start() {
            startTimes++;
        }

        @Override
        public void stop(boolean clear) {
        }

        @Override
        public void writeEvent(JobEvent jobEvent, boolean cutBlock) {
        }

        @Override
        public JobEvent readEvent() {
            return null;
        }

        @Override
        public boolean isEmpty() throws Exception {
            return false;
        }

        public static void init() {
            startTimes = 0;
        }
    }

    @Test
    void testStartTwice_1() throws Exception {
        JobEventManager jobEventManager = new JobEventManager(new TestingJobEventStore());
        jobEventManager.start();
        assertThat(jobEventManager.isRunning()).isTrue();
    }

    @Test
    void testStartTwice_2_testMerged_2() throws Exception {
        TestingJobEventStore.init();
        assertThat(TestingJobEventStore.startTimes).isEqualTo(1);
    }
}
