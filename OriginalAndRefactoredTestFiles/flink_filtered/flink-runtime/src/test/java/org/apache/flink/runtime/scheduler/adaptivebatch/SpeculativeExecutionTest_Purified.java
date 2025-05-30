package org.apache.flink.runtime.scheduler.adaptivebatch;

import org.apache.flink.api.common.JobStatus;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.configuration.SlowTaskDetectorOptions;
import org.apache.flink.runtime.blocklist.BlockedNode;
import org.apache.flink.runtime.blocklist.BlocklistOperations;
import org.apache.flink.runtime.concurrent.ComponentMainThreadExecutor;
import org.apache.flink.runtime.concurrent.ComponentMainThreadExecutorServiceAdapter;
import org.apache.flink.runtime.execution.ExecutionState;
import org.apache.flink.runtime.execution.SuppressRestartsException;
import org.apache.flink.runtime.executiongraph.DefaultExecutionGraph;
import org.apache.flink.runtime.executiongraph.Execution;
import org.apache.flink.runtime.executiongraph.ExecutionAttemptID;
import org.apache.flink.runtime.executiongraph.ExecutionJobVertex;
import org.apache.flink.runtime.executiongraph.ExecutionVertex;
import org.apache.flink.runtime.executiongraph.failover.TestRestartBackoffTimeStrategy;
import org.apache.flink.runtime.io.network.partition.PartitionNotFoundException;
import org.apache.flink.runtime.io.network.partition.ResultPartitionID;
import org.apache.flink.runtime.io.network.partition.ResultPartitionType;
import org.apache.flink.runtime.jobgraph.DistributionPattern;
import org.apache.flink.runtime.jobgraph.JobGraph;
import org.apache.flink.runtime.jobgraph.JobGraphTestUtils;
import org.apache.flink.runtime.jobgraph.JobVertex;
import org.apache.flink.runtime.scheduler.DefaultExecutionOperations;
import org.apache.flink.runtime.scheduler.DefaultSchedulerBuilder;
import org.apache.flink.runtime.scheduler.TestExecutionOperationsDecorator;
import org.apache.flink.runtime.scheduler.TestExecutionSlotAllocator;
import org.apache.flink.runtime.scheduler.TestExecutionSlotAllocatorFactory;
import org.apache.flink.runtime.scheduler.exceptionhistory.RootExceptionHistoryEntry;
import org.apache.flink.runtime.scheduler.strategy.ExecutionVertexID;
import org.apache.flink.runtime.taskmanager.TaskExecutionState;
import org.apache.flink.runtime.testutils.DirectScheduledExecutorService;
import org.apache.flink.testutils.TestingUtils;
import org.apache.flink.testutils.executor.TestExecutorExtension;
import org.apache.flink.util.ExecutorUtils;
import org.apache.flink.util.concurrent.ManuallyTriggeredScheduledExecutor;
import org.apache.flink.shaded.guava33.com.google.common.collect.ImmutableMap;
import org.apache.flink.shaded.guava33.com.google.common.collect.Iterables;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static org.apache.flink.runtime.executiongraph.ExecutionGraphTestUtils.completeCancellingForAllVertices;
import static org.apache.flink.runtime.executiongraph.ExecutionGraphTestUtils.createNoOpVertex;
import static org.apache.flink.runtime.scheduler.DefaultSchedulerBuilder.createCustomParallelismDecider;
import static org.apache.flink.runtime.scheduler.DefaultSchedulerTest.singleNonParallelJobVertexJobGraphForBatch;
import static org.apache.flink.runtime.scheduler.SchedulerTestingUtils.createCanceledTaskExecutionState;
import static org.apache.flink.runtime.scheduler.SchedulerTestingUtils.createFailedTaskExecutionState;
import static org.apache.flink.runtime.scheduler.SchedulerTestingUtils.createFinishedTaskExecutionState;
import static org.apache.flink.runtime.scheduler.adaptivebatch.AdaptiveBatchSchedulerTest.createResultPartitionBytesForExecution;
import static org.apache.flink.runtime.util.JobVertexConnectionUtils.connectNewDataSetAsInput;
import static org.assertj.core.api.Assertions.assertThat;

class SpeculativeExecutionTest_Purified {

    @RegisterExtension
    private static final TestExecutorExtension<ScheduledExecutorService> EXECUTOR_RESOURCE = TestingUtils.defaultExecutorExtension();

    private ScheduledExecutorService futureExecutor;

    private ManuallyTriggeredScheduledExecutor taskRestartExecutor;

    private TestExecutionOperationsDecorator testExecutionOperations;

    private TestBlocklistOperations testBlocklistOperations;

    private TestRestartBackoffTimeStrategy restartStrategy;

    private TestExecutionSlotAllocatorFactory testExecutionSlotAllocatorFactory;

    private TestExecutionSlotAllocator testExecutionSlotAllocator;

    @BeforeEach
    void setUp() {
        futureExecutor = new DirectScheduledExecutorService();
        taskRestartExecutor = new ManuallyTriggeredScheduledExecutor();
        testExecutionOperations = new TestExecutionOperationsDecorator(new DefaultExecutionOperations());
        testBlocklistOperations = new TestBlocklistOperations();
        restartStrategy = new TestRestartBackoffTimeStrategy(true, 0);
        testExecutionSlotAllocatorFactory = new TestExecutionSlotAllocatorFactory();
        testExecutionSlotAllocator = testExecutionSlotAllocatorFactory.getTestExecutionSlotAllocator();
    }

    @AfterEach
    void tearDown() {
        if (futureExecutor != null) {
            ExecutorUtils.gracefulShutdown(10, TimeUnit.SECONDS, futureExecutor);
        }
    }

    static Stream<ResultPartitionType> supportedResultPartitionType() {
        return Stream.of(ResultPartitionType.BLOCKING, ResultPartitionType.HYBRID_FULL, ResultPartitionType.HYBRID_SELECTIVE);
    }

    private static Execution getExecution(ExecutionVertex executionVertex, int attemptNumber) {
        return executionVertex.getCurrentExecutions().stream().filter(e -> e.getAttemptNumber() == attemptNumber).findFirst().get();
    }

    private static ExecutionVertex getOnlyExecutionVertex(AdaptiveBatchScheduler scheduler) {
        return Iterables.getOnlyElement(scheduler.getExecutionGraph().getAllExecutionVertices());
    }

    private AdaptiveBatchScheduler createSchedulerAndStartScheduling() {
        return createSchedulerAndStartScheduling(singleNonParallelJobVertexJobGraphForBatch());
    }

    private AdaptiveBatchScheduler createSchedulerAndStartScheduling(final JobGraph jobGraph) {
        final ComponentMainThreadExecutor mainThreadExecutor = ComponentMainThreadExecutorServiceAdapter.forMainThread();
        try {
            final AdaptiveBatchScheduler scheduler = createScheduler(jobGraph, mainThreadExecutor);
            mainThreadExecutor.execute(scheduler::startScheduling);
            return scheduler;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private AdaptiveBatchScheduler createScheduler(final JobGraph jobGraph, final ComponentMainThreadExecutor mainThreadExecutor) throws Exception {
        return createSchedulerBuilder(jobGraph, mainThreadExecutor).buildAdaptiveBatchJobScheduler(true);
    }

    private DefaultSchedulerBuilder createSchedulerBuilder(final JobGraph jobGraph, final ComponentMainThreadExecutor mainThreadExecutor) {
        final Configuration configuration = new Configuration();
        configuration.set(SlowTaskDetectorOptions.CHECK_INTERVAL, Duration.ofDays(1));
        return new DefaultSchedulerBuilder(jobGraph, mainThreadExecutor, EXECUTOR_RESOURCE.getExecutor()).setBlocklistOperations(testBlocklistOperations).setExecutionOperations(testExecutionOperations).setFutureExecutor(futureExecutor).setDelayExecutor(taskRestartExecutor).setRestartBackoffTimeStrategy(restartStrategy).setExecutionSlotAllocatorFactory(testExecutionSlotAllocatorFactory).setJobMasterConfiguration(configuration);
    }

    private static void notifySlowTask(final AdaptiveBatchScheduler scheduler, final Execution slowTask) {
        ((DefaultSpeculativeExecutionHandler) scheduler.getSpeculativeExecutionHandler()).notifySlowTasks(ImmutableMap.of(slowTask.getVertex().getID(), Collections.singleton(slowTask.getAttemptId())));
    }

    private static void notifySlowTask(final AdaptiveBatchScheduler scheduler, final Map<ExecutionVertexID, Collection<ExecutionAttemptID>> slowTasks) {
        ((DefaultSpeculativeExecutionHandler) scheduler.getSpeculativeExecutionHandler()).notifySlowTasks(slowTasks);
    }

    private long getNumSlowExecutionVertices(AdaptiveBatchScheduler scheduler) {
        return ((DefaultSpeculativeExecutionHandler) scheduler.getSpeculativeExecutionHandler()).getNumSlowExecutionVertices();
    }

    private long getNumEffectiveSpeculativeExecutions(AdaptiveBatchScheduler scheduler) {
        return ((DefaultSpeculativeExecutionHandler) scheduler.getSpeculativeExecutionHandler()).getNumEffectiveSpeculativeExecutions();
    }

    private static class TestBlocklistOperations implements BlocklistOperations {

        private final List<BlockedNode> blockedNodes = new ArrayList<>();

        @Override
        public void addNewBlockedNodes(Collection<BlockedNode> newNodes) {
            blockedNodes.addAll(newNodes);
        }

        public Set<String> getAllBlockedNodeIds() {
            return blockedNodes.stream().map(BlockedNode::getNodeId).collect(Collectors.toSet());
        }
    }

    @Test
    void testNotifySlowTasks_1() {
        assertThat(testExecutionOperations.getDeployedExecutions()).hasSize(1);
    }

    @Test
    void testNotifySlowTasks_2() {
        assertThat(testExecutionOperations.getDeployedExecutions()).hasSize(2);
    }

    @Test
    void testNotifySlowTasks_3_testMerged_3() {
        final AdaptiveBatchScheduler scheduler = createSchedulerAndStartScheduling();
        final ExecutionVertex ev = getOnlyExecutionVertex(scheduler);
        final Execution attempt1 = ev.getCurrentExecutionAttempt();
        final long timestamp = System.currentTimeMillis();
        notifySlowTask(scheduler, attempt1);
        assertThat(testBlocklistOperations.getAllBlockedNodeIds()).containsExactly(attempt1.getAssignedResourceLocation().getNodeId());
        final Execution attempt2 = getExecution(ev, 1);
        assertThat(attempt2.getState()).isEqualTo(ExecutionState.DEPLOYING);
        assertThat(attempt2.getStateTimestamp(ExecutionState.CREATED)).isGreaterThanOrEqualTo(timestamp);
    }

    @Test
    void testNotifyDuplicatedSlowTasks_1() {
        assertThat(testExecutionOperations.getDeployedExecutions()).hasSize(2);
    }

    @Test
    void testNotifyDuplicatedSlowTasks_2() {
        assertThat(testExecutionOperations.getDeployedExecutions()).hasSize(2);
    }

    @Test
    void testNotifyDuplicatedSlowTasks_3() {
        assertThat(testExecutionOperations.getDeployedExecutions()).hasSize(3);
    }

    @Test
    void testRestartVertexIfAllSpeculativeExecutionFailed_1() {
        assertThat(testExecutionOperations.getDeployedExecutions()).hasSize(2);
    }

    @Test
    void testRestartVertexIfAllSpeculativeExecutionFailed_2() {
        assertThat(testExecutionOperations.getDeployedExecutions()).hasSize(3);
    }

    @Test
    void testRestartVertexIfPartitionExceptionHappened_1() {
        final AdaptiveBatchScheduler scheduler = createSchedulerAndStartScheduling();
        final ExecutionVertex ev = getOnlyExecutionVertex(scheduler);
        final Execution attempt1 = ev.getCurrentExecutionAttempt();
        final Execution attempt2 = getExecution(ev, 1);
        assertThat(attempt2.getState()).isEqualTo(ExecutionState.CANCELING);
    }

    @Test
    void testRestartVertexIfPartitionExceptionHappened_2() {
        assertThat(testExecutionOperations.getDeployedExecutions()).hasSize(3);
    }
}
