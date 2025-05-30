package org.apache.flink.runtime.scheduler;

import org.apache.flink.runtime.concurrent.ComponentMainThreadExecutor;
import org.apache.flink.runtime.concurrent.ComponentMainThreadExecutorServiceAdapter;
import org.apache.flink.runtime.executiongraph.Execution;
import org.apache.flink.runtime.executiongraph.ExecutionAttemptID;
import org.apache.flink.runtime.executiongraph.ExecutionGraph;
import org.apache.flink.runtime.executiongraph.ExecutionVertex;
import org.apache.flink.runtime.executiongraph.TestingDefaultExecutionGraphBuilder;
import org.apache.flink.runtime.io.network.partition.ResultPartitionID;
import org.apache.flink.runtime.io.network.partition.ResultPartitionType;
import org.apache.flink.runtime.io.network.partition.TestingJobMasterPartitionTracker;
import org.apache.flink.runtime.jobgraph.DistributionPattern;
import org.apache.flink.runtime.jobgraph.JobGraph;
import org.apache.flink.runtime.jobgraph.JobGraphTestUtils;
import org.apache.flink.runtime.jobgraph.JobVertex;
import org.apache.flink.runtime.scheduler.strategy.ExecutionVertexID;
import org.apache.flink.runtime.shuffle.TestingShuffleMaster;
import org.apache.flink.runtime.testtasks.NoOpInvokable;
import org.apache.flink.util.ExecutorUtils;
import org.apache.flink.util.IterableUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import static org.apache.flink.runtime.util.JobVertexConnectionUtils.connectNewDataSetAsInput;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DefaultExecutionDeployerTest_Purified {

    private ScheduledExecutorService executor;

    private ComponentMainThreadExecutor mainThreadExecutor;

    private TestExecutionOperationsDecorator testExecutionOperations;

    private ExecutionVertexVersioner executionVertexVersioner;

    private TestExecutionSlotAllocator testExecutionSlotAllocator;

    private TestingShuffleMaster shuffleMaster;

    private TestingJobMasterPartitionTracker partitionTracker;

    private Duration partitionRegistrationTimeout;

    @BeforeEach
    void setUp() {
        executor = Executors.newSingleThreadScheduledExecutor();
        mainThreadExecutor = ComponentMainThreadExecutorServiceAdapter.forMainThread();
        testExecutionOperations = new TestExecutionOperationsDecorator(new ExecutionOperations() {

            @Override
            public void deploy(Execution execution) {
            }

            @Override
            public CompletableFuture<?> cancel(Execution execution) {
                return null;
            }

            @Override
            public void markFailed(Execution execution, Throwable cause) {
            }
        });
        executionVertexVersioner = new ExecutionVertexVersioner();
        testExecutionSlotAllocator = new TestExecutionSlotAllocator();
        shuffleMaster = new TestingShuffleMaster();
        partitionTracker = new TestingJobMasterPartitionTracker();
        partitionRegistrationTimeout = Duration.ofMillis(5000);
    }

    @AfterEach
    void tearDown() {
        if (executor != null) {
            ExecutorUtils.gracefulShutdown(5, TimeUnit.SECONDS, executor);
        }
    }

    private static JobGraph singleNonParallelJobVertexJobGraph() {
        return singleJobVertexJobGraph(1);
    }

    private static JobGraph singleJobVertexJobGraph(final int parallelism) {
        final JobVertex vertex = new JobVertex("source");
        vertex.setInvokableClass(NoOpInvokable.class);
        vertex.setParallelism(parallelism);
        return JobGraphTestUtils.streamingJobGraph(vertex);
    }

    private static JobGraph nonParallelSourceSinkJobGraph() {
        final JobVertex source = new JobVertex("source");
        source.setInvokableClass(NoOpInvokable.class);
        final JobVertex sink = new JobVertex("sink");
        sink.setInvokableClass(NoOpInvokable.class);
        connectNewDataSetAsInput(sink, source, DistributionPattern.POINTWISE, ResultPartitionType.PIPELINED);
        return JobGraphTestUtils.streamingJobGraph(source, sink);
    }

    private ExecutionGraph createExecutionGraph(final JobGraph jobGraph) throws Exception {
        final ExecutionGraph executionGraph = TestingDefaultExecutionGraphBuilder.newBuilder().setJobGraph(jobGraph).setShuffleMaster(shuffleMaster).setPartitionTracker(partitionTracker).build(executor);
        executionGraph.setInternalTaskFailuresListener(new TestingInternalFailuresListener());
        executionGraph.start(ComponentMainThreadExecutorServiceAdapter.forMainThread());
        return executionGraph;
    }

    private ExecutionDeployer createExecutionDeployer() {
        return new DefaultExecutionDeployer.Factory().createInstance(LoggerFactory.getLogger(DefaultExecutionDeployer.class), testExecutionSlotAllocator, testExecutionOperations, executionVertexVersioner, partitionRegistrationTimeout, (ignored1, ignored2) -> {
        }, mainThreadExecutor);
    }

    private void deployTasks(ExecutionDeployer executionDeployer, ExecutionGraph executionGraph) {
        deployTasks(executionDeployer, IterableUtils.toStream(executionGraph.getAllExecutionVertices()).map(ExecutionVertex::getCurrentExecutionAttempt).collect(Collectors.toList()));
    }

    private void deployTasks(ExecutionDeployer executionDeployer, List<Execution> executions) {
        final Set<ExecutionVertexID> executionVertexIds = executions.stream().map(Execution::getAttemptId).map(ExecutionAttemptID::getExecutionVertexId).collect(Collectors.toSet());
        executionDeployer.allocateSlotsAndDeploy(executions, executionVertexVersioner.recordVertexModifications(executionVertexIds));
    }

    private static Execution getAnyExecution(ExecutionGraph executionGraph) {
        return executionGraph.getRegisteredExecutions().values().iterator().next();
    }

    @Test
    void testDeployTasksOnlyIfAllSlotRequestsAreFulfilled_1() throws Exception {
        assertThat(testExecutionOperations.getDeployedExecutions()).isEmpty();
    }

    @Test
    void testDeployTasksOnlyIfAllSlotRequestsAreFulfilled_2() throws Exception {
        assertThat(testExecutionOperations.getDeployedExecutions()).isEmpty();
    }

    @Test
    void testDeployTasksOnlyIfAllSlotRequestsAreFulfilled_3() throws Exception {
        assertThat(testExecutionOperations.getDeployedExecutions()).hasSize(4);
    }

    @Test
    void testSlotAllocationTimeout_1() throws Exception {
        testExecutionSlotAllocator.disableAutoCompletePendingRequests();
        assertThat(testExecutionSlotAllocator.getPendingRequests()).hasSize(2);
    }

    @Test
    void testSlotAllocationTimeout_2() throws Exception {
        final JobGraph jobGraph = singleJobVertexJobGraph(2);
        final ExecutionGraph executionGraph = createExecutionGraph(jobGraph);
        deployTasks(executionDeployer, executionGraph);
        final ExecutionAttemptID attemptId = getAnyExecution(executionGraph).getAttemptId();
        testExecutionSlotAllocator.timeoutPendingRequest(attemptId);
        assertThat(testExecutionOperations.getFailedExecutions()).containsExactly(attemptId);
    }

    @Test
    void testFailedProducedPartitionRegistration_1() throws Exception {
        assertThat(testExecutionOperations.getFailedExecutions()).isEmpty();
    }

    @Test
    void testFailedProducedPartitionRegistration_2() throws Exception {
        assertThat(testExecutionOperations.getFailedExecutions()).hasSize(1);
    }
}
