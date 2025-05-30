package org.apache.flink.client;

import org.apache.flink.api.common.JobStatus;
import org.apache.flink.client.program.PerJobMiniClusterFactory;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.configuration.DeploymentOptions;
import org.apache.flink.core.execution.JobClient;
import org.apache.flink.core.testutils.FlinkAssertions;
import org.apache.flink.runtime.dispatcher.Dispatcher;
import org.apache.flink.runtime.jobgraph.JobGraph;
import org.apache.flink.runtime.jobgraph.JobGraphTestUtils;
import org.apache.flink.runtime.jobgraph.JobVertex;
import org.apache.flink.runtime.minicluster.MiniCluster;
import org.apache.flink.runtime.testutils.WaitingCancelableInvokable;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import java.time.Duration;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import static org.assertj.core.api.Assertions.assertThat;

class ClientHeartbeatTest_Purified {

    private final long clientHeartbeatInterval = 50;

    private final long clientHeartbeatTimeout = 1000;

    private MiniCluster miniCluster;

    @AfterEach
    void teardown() throws Exception {
        if (miniCluster != null) {
            miniCluster.close();
        }
    }

    private Configuration createConfiguration(boolean shutdownOnAttachedExit) {
        Configuration configuration = new Configuration();
        configuration.set(Dispatcher.CLIENT_ALIVENESS_CHECK_DURATION, Duration.ofMillis(clientHeartbeatInterval));
        if (shutdownOnAttachedExit) {
            configuration.set(DeploymentOptions.ATTACHED, true);
            configuration.set(DeploymentOptions.SHUTDOWN_IF_ATTACHED, true);
        }
        return configuration;
    }

    private JobClient submitJob(Configuration configuration) throws Exception {
        PerJobMiniClusterFactory perJobMiniClusterFactory = PerJobMiniClusterFactory.createWithFactory(configuration, config -> {
            miniCluster = new MiniCluster(config);
            return miniCluster;
        });
        JobGraph cancellableJobGraph = getCancellableJobGraph();
        if (configuration.get(DeploymentOptions.ATTACHED) && configuration.get(DeploymentOptions.SHUTDOWN_IF_ATTACHED)) {
            cancellableJobGraph.setInitialClientHeartbeatTimeout(clientHeartbeatTimeout);
        }
        return perJobMiniClusterFactory.submitJob(cancellableJobGraph, ClassLoader.getSystemClassLoader()).get();
    }

    private static JobGraph getCancellableJobGraph() {
        JobVertex jobVertex = new JobVertex("jobVertex");
        jobVertex.setInvokableClass(WaitingCancelableInvokable.class);
        jobVertex.setParallelism(1);
        return JobGraphTestUtils.streamingJobGraph(jobVertex);
    }

    @Test
    void testJobCancelledIfClientHeartbeatTimeout_1() throws Exception {
        JobClient jobClient = submitJob(createConfiguration(true));
    }

    @Test
    void testJobCancelledIfClientHeartbeatTimeout_2() throws Exception {
        assertThat(miniCluster.isRunning()).isFalse();
    }
}
