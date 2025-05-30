package org.apache.flink.runtime.dispatcher.cleanup;

import org.apache.flink.api.common.JobID;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.runtime.blob.BlobServer;
import org.apache.flink.runtime.blob.TestingBlobStoreBuilder;
import org.apache.flink.runtime.concurrent.ComponentMainThreadExecutorServiceAdapter;
import org.apache.flink.runtime.dispatcher.JobManagerRunnerRegistry;
import org.apache.flink.runtime.dispatcher.TestingJobManagerRunnerRegistry;
import org.apache.flink.runtime.highavailability.HighAvailabilityServices;
import org.apache.flink.runtime.highavailability.TestingHighAvailabilityServices;
import org.apache.flink.runtime.jobmanager.ExecutionPlanWriter;
import org.apache.flink.runtime.metrics.MetricRegistry;
import org.apache.flink.runtime.metrics.groups.JobManagerMetricGroup;
import org.apache.flink.runtime.metrics.util.TestingMetricRegistry;
import org.apache.flink.runtime.testutils.TestingExecutionPlanStore;
import org.apache.flink.util.concurrent.Executors;
import org.apache.flink.util.concurrent.FutureUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeoutException;
import static org.assertj.core.api.Assertions.assertThat;

public class DispatcherResourceCleanerFactoryTest_Purified {

    private static final JobID JOB_ID = new JobID();

    private CleanableBlobServer blobServer;

    private CompletableFuture<JobID> jobManagerRunnerRegistryLocalCleanupFuture;

    private CompletableFuture<Void> jobManagerRunnerRegistryLocalCleanupResultFuture;

    private CompletableFuture<JobID> executionPlanWriterLocalCleanupFuture;

    private CompletableFuture<JobID> executionPlanWriterGlobalCleanupFuture;

    private CompletableFuture<JobID> highAvailabilityServicesGlobalCleanupFuture;

    private JobManagerMetricGroup jobManagerMetricGroup;

    private DispatcherResourceCleanerFactory testInstance;

    @BeforeEach
    public void setup() throws Exception {
        blobServer = new CleanableBlobServer();
        MetricRegistry metricRegistry = TestingMetricRegistry.builder().build();
        jobManagerMetricGroup = JobManagerMetricGroup.createJobManagerMetricGroup(metricRegistry, "ignored hostname");
        jobManagerMetricGroup.addJob(JOB_ID, "ignored job name");
        testInstance = new DispatcherResourceCleanerFactory(Executors.directExecutor(), TestingRetryStrategies.NO_RETRY_STRATEGY, createJobManagerRunnerRegistry(), createExecutionPlanWriter(), blobServer, createHighAvailabilityServices(), jobManagerMetricGroup);
    }

    private JobManagerRunnerRegistry createJobManagerRunnerRegistry() {
        jobManagerRunnerRegistryLocalCleanupFuture = new CompletableFuture<>();
        jobManagerRunnerRegistryLocalCleanupResultFuture = new CompletableFuture<>();
        return TestingJobManagerRunnerRegistry.builder().withLocalCleanupAsyncFunction((jobId, executor) -> {
            jobManagerRunnerRegistryLocalCleanupFuture.complete(jobId);
            return jobManagerRunnerRegistryLocalCleanupResultFuture;
        }).build();
    }

    private ExecutionPlanWriter createExecutionPlanWriter() throws Exception {
        executionPlanWriterLocalCleanupFuture = new CompletableFuture<>();
        executionPlanWriterGlobalCleanupFuture = new CompletableFuture<>();
        final TestingExecutionPlanStore executionPlanStore = TestingExecutionPlanStore.newBuilder().setGlobalCleanupFunction((jobId, executor) -> {
            executionPlanWriterGlobalCleanupFuture.complete(jobId);
            return FutureUtils.completedVoidFuture();
        }).setLocalCleanupFunction((jobId, ignoredExecutor) -> {
            executionPlanWriterLocalCleanupFuture.complete(jobId);
            return FutureUtils.completedVoidFuture();
        }).build();
        executionPlanStore.start(null);
        return executionPlanStore;
    }

    private HighAvailabilityServices createHighAvailabilityServices() {
        highAvailabilityServicesGlobalCleanupFuture = new CompletableFuture<>();
        final TestingHighAvailabilityServices haServices = new TestingHighAvailabilityServices();
        haServices.setGlobalCleanupFuture(highAvailabilityServicesGlobalCleanupFuture);
        return haServices;
    }

    private void assertCleanupNotTriggered() {
        assertThat(jobManagerRunnerRegistryLocalCleanupFuture).isNotDone();
        assertNoRegularCleanupsTriggered();
    }

    private void assertWaitingForPrioritizedCleanupToFinish() {
        assertThat(jobManagerRunnerRegistryLocalCleanupFuture).isCompleted();
        assertNoRegularCleanupsTriggered();
    }

    private void assertNoRegularCleanupsTriggered() {
        assertThat(executionPlanWriterLocalCleanupFuture).isNotDone();
        assertThat(executionPlanWriterGlobalCleanupFuture).isNotDone();
        assertThat(blobServer.getLocalCleanupFuture()).isNotDone();
        assertThat(blobServer.getGlobalCleanupFuture()).isNotDone();
        assertThat(highAvailabilityServicesGlobalCleanupFuture).isNotDone();
        assertThat(jobManagerMetricGroup.numRegisteredJobMetricGroups()).isEqualTo(1);
    }

    private void assertJobMetricGroupCleanedUp() {
        assertThat(jobManagerMetricGroup.numRegisteredJobMetricGroups()).isEqualTo(0);
    }

    private static class CleanableBlobServer extends BlobServer {

        private final CompletableFuture<JobID> localCleanupFuture = new CompletableFuture<>();

        private final CompletableFuture<JobID> globalCleanupFuture = new CompletableFuture<>();

        public CleanableBlobServer() throws IOException {
            super(new Configuration(), new File("non-existent-file"), new TestingBlobStoreBuilder().createTestingBlobStore());
        }

        @Override
        public CompletableFuture<Void> localCleanupAsync(JobID jobId, Executor ignoredExecutor) {
            localCleanupFuture.complete(jobId);
            return FutureUtils.completedVoidFuture();
        }

        @Override
        public CompletableFuture<Void> globalCleanupAsync(JobID jobId, Executor ignoredExecutor) {
            globalCleanupFuture.complete(jobId);
            return FutureUtils.completedVoidFuture();
        }

        public CompletableFuture<JobID> getLocalCleanupFuture() {
            return localCleanupFuture;
        }

        public CompletableFuture<JobID> getGlobalCleanupFuture() {
            return globalCleanupFuture;
        }
    }

    @Test
    public void testLocalResourceCleaning_1() {
        assertCleanupNotTriggered();
    }

    @Test
    public void testLocalResourceCleaning_2() {
        assertWaitingForPrioritizedCleanupToFinish();
    }

    @Test
    public void testLocalResourceCleaning_3_testMerged_3() {
        final CompletableFuture<Void> cleanupResultFuture = testInstance.createLocalResourceCleaner(ComponentMainThreadExecutorServiceAdapter.forMainThread()).cleanupAsync(JOB_ID);
        assertThat(cleanupResultFuture).isNotCompleted();
        assertThat(blobServer.getLocalCleanupFuture()).isCompleted();
        assertThat(blobServer.getGlobalCleanupFuture()).isNotDone();
        assertThat(cleanupResultFuture).isCompleted();
    }

    @Test
    public void testLocalResourceCleaning_4() {
        assertThat(jobManagerRunnerRegistryLocalCleanupFuture).isCompleted();
    }

    @Test
    public void testLocalResourceCleaning_5() {
        assertThat(executionPlanWriterLocalCleanupFuture).isCompleted();
    }

    @Test
    public void testLocalResourceCleaning_6() {
        assertThat(executionPlanWriterGlobalCleanupFuture).isNotDone();
    }

    @Test
    public void testLocalResourceCleaning_9() {
        assertThat(highAvailabilityServicesGlobalCleanupFuture).isNotDone();
    }

    @Test
    public void testLocalResourceCleaning_10() {
        assertJobMetricGroupCleanedUp();
    }

    @Test
    public void testGlobalResourceCleaning_1() throws ExecutionException, InterruptedException, TimeoutException {
        assertCleanupNotTriggered();
    }

    @Test
    public void testGlobalResourceCleaning_2() throws ExecutionException, InterruptedException, TimeoutException {
        assertWaitingForPrioritizedCleanupToFinish();
    }

    @Test
    public void testGlobalResourceCleaning_3_testMerged_3() throws ExecutionException, InterruptedException, TimeoutException {
        final CompletableFuture<Void> cleanupResultFuture = testInstance.createGlobalResourceCleaner(ComponentMainThreadExecutorServiceAdapter.forMainThread()).cleanupAsync(JOB_ID);
        assertThat(cleanupResultFuture).isNotCompleted();
        assertThat(blobServer.getLocalCleanupFuture()).isNotDone();
        assertThat(blobServer.getGlobalCleanupFuture()).isCompleted();
        assertThat(cleanupResultFuture).isCompleted();
    }

    @Test
    public void testGlobalResourceCleaning_4() throws ExecutionException, InterruptedException, TimeoutException {
        assertThat(jobManagerRunnerRegistryLocalCleanupFuture).isCompleted();
    }

    @Test
    public void testGlobalResourceCleaning_5() throws ExecutionException, InterruptedException, TimeoutException {
        assertThat(executionPlanWriterLocalCleanupFuture).isNotDone();
    }

    @Test
    public void testGlobalResourceCleaning_6() throws ExecutionException, InterruptedException, TimeoutException {
        assertThat(executionPlanWriterGlobalCleanupFuture).isCompleted();
    }

    @Test
    public void testGlobalResourceCleaning_9() throws ExecutionException, InterruptedException, TimeoutException {
        assertThat(highAvailabilityServicesGlobalCleanupFuture).isCompleted();
    }

    @Test
    public void testGlobalResourceCleaning_10() throws ExecutionException, InterruptedException, TimeoutException {
        assertJobMetricGroupCleanedUp();
    }
}
