package org.apache.druid.k8s.overlord.common;

import io.fabric8.kubernetes.api.model.batch.v1.JobBuilder;
import io.fabric8.kubernetes.api.model.batch.v1.JobStatusBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class JobStatusTest_Purified {

    @Test
    void testJobsActive_1() {
        Assertions.assertFalse(JobStatus.isActive(null));
    }

    @Test
    void testJobsActive_2() {
        Assertions.assertFalse(JobStatus.isActive(new JobBuilder().build()));
    }

    @Test
    void testJobsActive_3() {
        Assertions.assertFalse(JobStatus.isActive(new JobBuilder().withStatus(new JobStatusBuilder().withActive(null).build()).build()));
    }

    @Test
    void testJobsActive_4() {
        Assertions.assertFalse(JobStatus.isActive(new JobBuilder().withStatus(new JobStatusBuilder().withActive(0).build()).build()));
    }

    @Test
    void testJobsActive_5() {
        Assertions.assertTrue(JobStatus.isActive(new JobBuilder().withStatus(new JobStatusBuilder().withActive(1).build()).build()));
    }

    @Test
    void testJobsSucceeded_1() {
        Assertions.assertFalse(JobStatus.isSucceeded(null));
    }

    @Test
    void testJobsSucceeded_2() {
        Assertions.assertFalse(JobStatus.isSucceeded(new JobBuilder().build()));
    }

    @Test
    void testJobsSucceeded_3() {
        Assertions.assertFalse(JobStatus.isSucceeded(new JobBuilder().withStatus(new JobStatusBuilder().withSucceeded(null).build()).build()));
    }

    @Test
    void testJobsSucceeded_4() {
        Assertions.assertFalse(JobStatus.isSucceeded(new JobBuilder().withStatus(new JobStatusBuilder().withSucceeded(0).build()).build()));
    }

    @Test
    void testJobsSucceeded_5() {
        Assertions.assertTrue(JobStatus.isSucceeded(new JobBuilder().withStatus(new JobStatusBuilder().withSucceeded(1).build()).build()));
    }

    @Test
    void testJobsFailed_1() {
        Assertions.assertFalse(JobStatus.isFailed(null));
    }

    @Test
    void testJobsFailed_2() {
        Assertions.assertFalse(JobStatus.isFailed(new JobBuilder().build()));
    }

    @Test
    void testJobsFailed_3() {
        Assertions.assertFalse(JobStatus.isFailed(new JobBuilder().withStatus(new JobStatusBuilder().withFailed(null).build()).build()));
    }

    @Test
    void testJobsFailed_4() {
        Assertions.assertFalse(JobStatus.isFailed(new JobBuilder().withStatus(new JobStatusBuilder().withFailed(0).build()).build()));
    }

    @Test
    void testJobsFailed_5() {
        Assertions.assertTrue(JobStatus.isFailed(new JobBuilder().withStatus(new JobStatusBuilder().withFailed(1).build()).build()));
    }
}
