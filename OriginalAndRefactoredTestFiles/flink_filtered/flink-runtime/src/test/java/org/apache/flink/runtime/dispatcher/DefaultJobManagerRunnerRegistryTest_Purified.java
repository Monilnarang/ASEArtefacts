package org.apache.flink.runtime.dispatcher;

import org.apache.flink.api.common.JobID;
import org.apache.flink.core.testutils.FlinkAssertions;
import org.apache.flink.runtime.concurrent.UnsupportedOperationExecutor;
import org.apache.flink.runtime.jobmaster.JobManagerRunner;
import org.apache.flink.runtime.jobmaster.TestingJobManagerRunner;
import org.apache.flink.util.concurrent.Executors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.NoSuchElementException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import static org.apache.flink.core.testutils.FlinkAssertions.assertThatFuture;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DefaultJobManagerRunnerRegistryTest_Purified {

    private JobManagerRunnerRegistry testInstance;

    @BeforeEach
    void setup() {
        testInstance = new DefaultJobManagerRunnerRegistry(4);
    }

    private TestingJobManagerRunner registerTestingJobManagerRunner() {
        final TestingJobManagerRunner jobManagerRunner = TestingJobManagerRunner.newBuilder().build();
        testInstance.register(jobManagerRunner);
        assertThat(testInstance.isRegistered(jobManagerRunner.getJobID())).isTrue();
        assertThat(jobManagerRunner.getTerminationFuture()).isNotDone();
        return jobManagerRunner;
    }

    @Test
    void size_1() {
        assertThat(testInstance.size()).isZero();
    }

    @Test
    void size_2_testMerged_2() {
        testInstance.register(TestingJobManagerRunner.newBuilder().build());
        assertThat(testInstance.size()).isOne();
        assertThat(testInstance.size()).isEqualTo(2);
    }

    @Test
    void testGetRunningJobIds_1() {
        assertThat(testInstance.getRunningJobIds()).isEmpty();
    }

    @Test
    void testGetRunningJobIds_2() {
        final JobID jobId0 = new JobID();
        final JobID jobId1 = new JobID();
        testInstance.register(TestingJobManagerRunner.newBuilder().setJobId(jobId0).build());
        testInstance.register(TestingJobManagerRunner.newBuilder().setJobId(jobId1).build());
        assertThat(testInstance.getRunningJobIds()).containsExactlyInAnyOrder(jobId0, jobId1);
    }

    @Test
    void testGetJobManagerRunners_1() {
        assertThat(testInstance.getJobManagerRunners()).isEmpty();
    }

    @Test
    void testGetJobManagerRunners_2() {
        final JobManagerRunner jobManagerRunner0 = TestingJobManagerRunner.newBuilder().build();
        final JobManagerRunner jobManagerRunner1 = TestingJobManagerRunner.newBuilder().build();
        testInstance.register(jobManagerRunner0);
        testInstance.register(jobManagerRunner1);
        assertThat(testInstance.getJobManagerRunners()).containsExactlyInAnyOrder(jobManagerRunner0, jobManagerRunner1);
    }
}
