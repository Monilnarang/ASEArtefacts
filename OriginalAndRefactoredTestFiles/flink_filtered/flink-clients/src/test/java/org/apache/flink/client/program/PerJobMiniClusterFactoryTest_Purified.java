package org.apache.flink.client.program;

import org.apache.flink.api.common.JobExecutionResult;
import org.apache.flink.api.common.JobStatus;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.configuration.PipelineOptions;
import org.apache.flink.core.execution.JobClient;
import org.apache.flink.core.execution.SavepointFormatType;
import org.apache.flink.runtime.execution.Environment;
import org.apache.flink.runtime.jobgraph.JobGraph;
import org.apache.flink.runtime.jobgraph.JobGraphTestUtils;
import org.apache.flink.runtime.jobgraph.JobVertex;
import org.apache.flink.runtime.minicluster.MiniCluster;
import org.apache.flink.runtime.testutils.CancelableInvokable;
import org.apache.flink.runtime.testutils.WaitingCancelableInvokable;
import org.apache.flink.shaded.guava33.com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import static org.apache.flink.core.testutils.FlinkAssertions.assertThatFuture;
import static org.apache.flink.util.Preconditions.checkState;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PerJobMiniClusterFactoryTest_Purified {

    private MiniCluster miniCluster;

    @AfterEach
    void teardown() throws Exception {
        if (miniCluster != null) {
            miniCluster.close();
        }
    }

    private PerJobMiniClusterFactory initializeMiniCluster() {
        return initializeMiniCluster(new Configuration());
    }

    private PerJobMiniClusterFactory initializeMiniCluster(Configuration configuration) {
        return PerJobMiniClusterFactory.createWithFactory(configuration, config -> {
            miniCluster = new MiniCluster(config);
            return miniCluster;
        });
    }

    private void assertThatMiniClusterIsShutdown() {
        assertThat(miniCluster.isRunning()).isFalse();
    }

    private static JobGraph getNoopJobGraph() {
        return JobGraphTestUtils.singleNoOpJobGraph();
    }

    private static JobGraph getCancellableJobGraph() {
        JobVertex jobVertex = new JobVertex("jobVertex");
        jobVertex.setInvokableClass(WaitingCancelableInvokable.class);
        jobVertex.setParallelism(1);
        return JobGraphTestUtils.streamingJobGraph(jobVertex);
    }

    private static JobVertex getBlockingJobVertex() {
        JobVertex jobVertex = new JobVertex("jobVertex");
        jobVertex.setInvokableClass(BlockingInvokable.class);
        jobVertex.setParallelism(2);
        return jobVertex;
    }

    public static class BlockingInvokable extends CancelableInvokable {

        private static CountDownLatch latch;

        public BlockingInvokable(Environment environment) {
            super(environment);
        }

        @Override
        public void doInvoke() throws Exception {
            checkState(latch != null, "The invokable should be reset first.");
            latch.countDown();
            waitUntilCancelled();
        }

        public static void reset(int count) {
            latch = new CountDownLatch(count);
        }
    }

    @Test
    void testJobClient_1_testMerged_1() throws Exception {
        PerJobMiniClusterFactory perJobMiniClusterFactory = initializeMiniCluster();
        JobGraph cancellableJobGraph = getCancellableJobGraph();
        JobClient jobClient = perJobMiniClusterFactory.submitJob(cancellableJobGraph, ClassLoader.getSystemClassLoader()).get();
        assertThat(jobClient.getJobID()).isEqualTo(cancellableJobGraph.getJobID());
        assertThat(jobClient.getJobStatus().get()).isIn(JobStatus.CREATED, JobStatus.RUNNING);
        jobClient.cancel().get();
    }

    @Test
    void testJobClient_4() throws Exception {
        assertThatMiniClusterIsShutdown();
    }

    @Test
    void testTurnUpParallelismByOverwriteParallelism_1() throws Exception {
        JobVertex jobVertex = getBlockingJobVertex();
        JobGraph jobGraph = JobGraphTestUtils.streamingJobGraph(jobVertex);
        int overwriteParallelism = jobVertex.getParallelism() + 1;
        BlockingInvokable.reset(overwriteParallelism);
        Configuration configuration = new Configuration();
        configuration.set(PipelineOptions.PARALLELISM_OVERRIDES, ImmutableMap.of(jobVertex.getID().toHexString(), String.valueOf(overwriteParallelism)));
        PerJobMiniClusterFactory perJobMiniClusterFactory = initializeMiniCluster(configuration);
        JobClient jobClient = perJobMiniClusterFactory.submitJob(jobGraph, ClassLoader.getSystemClassLoader()).get();
        jobClient.cancel().get();
    }

    @Test
    void testTurnUpParallelismByOverwriteParallelism_2() throws Exception {
        assertThatMiniClusterIsShutdown();
    }
}
