package org.apache.flink.runtime.metrics.groups;

import org.apache.flink.api.common.JobID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.apache.flink.runtime.metrics.NoOpMetricRegistry.INSTANCE;
import static org.assertj.core.api.Assertions.assertThat;

class TaskManagerMetricGroupTest_Purified {

    private static final JobID JOB_ID = new JobID();

    private static final String JOB_NAME = "test job";

    private TaskManagerMetricGroup metricGroup;

    @BeforeEach
    void before() {
        metricGroup = new TaskManagerMetricGroup(INSTANCE, "testHost", "testTm");
    }

    @AfterEach
    void after() {
        if (!metricGroup.isClosed()) {
            metricGroup.close();
        }
    }

    @Test
    void testGetSameJob_1() {
        assertThat(metricGroup.addJob(JOB_ID, JOB_NAME)).isSameAs(metricGroup.addJob(JOB_ID, JOB_NAME));
    }

    @Test
    void testGetSameJob_2() {
        assertThat(metricGroup.addJob(new JobID(), "another job")).isNotSameAs(metricGroup.addJob(JOB_ID, JOB_NAME));
    }
}
