package org.apache.flink.runtime.scheduler;

import org.apache.flink.api.common.JobID;
import org.apache.flink.api.common.JobStatus;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.configuration.MetricOptions;
import org.apache.flink.configuration.WebOptions;
import org.apache.flink.core.execution.JobStatusHook;
import org.apache.flink.core.execution.SavepointFormatType;
import org.apache.flink.core.failure.FailureEnricher;
import org.apache.flink.core.failure.TestingFailureEnricher;
import org.apache.flink.core.testutils.OneShotLatch;
import org.apache.flink.core.testutils.ScheduledTask;
import org.apache.flink.metrics.Gauge;
import org.apache.flink.runtime.checkpoint.CheckpointCoordinator;
import org.apache.flink.runtime.checkpoint.CheckpointException;
import org.apache.flink.runtime.checkpoint.CheckpointFailureReason;
import org.apache.flink.runtime.checkpoint.CheckpointIDCounter;
import org.apache.flink.runtime.checkpoint.CheckpointRecoveryFactory;
import org.apache.flink.runtime.checkpoint.CheckpointsCleaner;
import org.apache.flink.runtime.checkpoint.CompletedCheckpoint;
import org.apache.flink.runtime.checkpoint.CompletedCheckpointStore;
import org.apache.flink.runtime.checkpoint.StandaloneCheckpointIDCounter;
import org.apache.flink.runtime.checkpoint.StandaloneCompletedCheckpointStore;
import org.apache.flink.runtime.checkpoint.TestingCheckpointRecoveryFactory;
import org.apache.flink.runtime.checkpoint.TestingCompletedCheckpointStore;
import org.apache.flink.runtime.checkpoint.hooks.TestMasterHook;
import org.apache.flink.runtime.clusterframework.types.ResourceProfile;
import org.apache.flink.runtime.concurrent.ComponentMainThreadExecutor;
import org.apache.flink.runtime.concurrent.ComponentMainThreadExecutorServiceAdapter;
import org.apache.flink.runtime.execution.ExecutionState;
import org.apache.flink.runtime.executiongraph.ArchivedExecution;
import org.apache.flink.runtime.executiongraph.ArchivedExecutionGraph;
import org.apache.flink.runtime.executiongraph.ArchivedExecutionVertex;
import org.apache.flink.runtime.executiongraph.ErrorInfo;
import org.apache.flink.runtime.executiongraph.ExecutionAttemptID;
import org.apache.flink.runtime.executiongraph.ExecutionVertex;
import org.apache.flink.runtime.executiongraph.TestingJobStatusHook;
import org.apache.flink.runtime.executiongraph.failover.FailoverStrategy;
import org.apache.flink.runtime.executiongraph.failover.RestartAllFailoverStrategy;
import org.apache.flink.runtime.executiongraph.failover.RestartPipelinedRegionFailoverStrategy;
import org.apache.flink.runtime.executiongraph.failover.TestRestartBackoffTimeStrategy;
import org.apache.flink.runtime.executiongraph.utils.SimpleAckingTaskManagerGateway;
import org.apache.flink.runtime.executiongraph.utils.TestFailoverStrategyFactory;
import org.apache.flink.runtime.failure.FailureEnricherUtils;
import org.apache.flink.runtime.io.network.partition.ResultPartitionID;
import org.apache.flink.runtime.io.network.partition.ResultPartitionType;
import org.apache.flink.runtime.io.network.partition.TestingJobMasterPartitionTracker;
import org.apache.flink.runtime.jobgraph.DistributionPattern;
import org.apache.flink.runtime.jobgraph.JobGraph;
import org.apache.flink.runtime.jobgraph.JobGraphTestUtils;
import org.apache.flink.runtime.jobgraph.JobVertex;
import org.apache.flink.runtime.jobgraph.JobVertexID;
import org.apache.flink.runtime.jobgraph.tasks.AbstractInvokable;
import org.apache.flink.runtime.jobmanager.scheduler.NoResourceAvailableException;
import org.apache.flink.runtime.jobmaster.LogicalSlot;
import org.apache.flink.runtime.jobmaster.TestingLogicalSlotBuilder;
import org.apache.flink.runtime.jobmaster.slotpool.DeclarativeSlotPoolBridgeBuilder;
import org.apache.flink.runtime.jobmaster.slotpool.LocationPreferenceSlotSelectionStrategy;
import org.apache.flink.runtime.jobmaster.slotpool.PhysicalSlotProvider;
import org.apache.flink.runtime.jobmaster.slotpool.PhysicalSlotProviderImpl;
import org.apache.flink.runtime.jobmaster.slotpool.SlotPool;
import org.apache.flink.runtime.messages.checkpoint.AcknowledgeCheckpoint;
import org.apache.flink.runtime.metrics.MetricRegistry;
import org.apache.flink.runtime.metrics.groups.JobManagerMetricGroup;
import org.apache.flink.runtime.metrics.util.TestingMetricRegistry;
import org.apache.flink.runtime.scheduler.adaptive.AdaptiveSchedulerTest;
import org.apache.flink.runtime.scheduler.exceptionhistory.ExceptionHistoryEntryTestingUtils;
import org.apache.flink.runtime.scheduler.exceptionhistory.RootExceptionHistoryEntry;
import org.apache.flink.runtime.scheduler.strategy.ExecutionVertexID;
import org.apache.flink.runtime.scheduler.strategy.PipelinedRegionSchedulingStrategy;
import org.apache.flink.runtime.scheduler.strategy.SchedulingExecutionVertex;
import org.apache.flink.runtime.scheduler.strategy.SchedulingStrategyFactory;
import org.apache.flink.runtime.scheduler.strategy.SchedulingTopology;
import org.apache.flink.runtime.scheduler.strategy.TestSchedulingStrategy;
import org.apache.flink.runtime.shuffle.TestingShuffleMaster;
import org.apache.flink.runtime.state.SharedStateRegistryImpl;
import org.apache.flink.runtime.taskmanager.LocalTaskManagerLocation;
import org.apache.flink.runtime.taskmanager.TaskExecutionState;
import org.apache.flink.runtime.taskmanager.TaskManagerLocation;
import org.apache.flink.runtime.testtasks.NoOpInvokable;
import org.apache.flink.runtime.testutils.DirectScheduledExecutorService;
import org.apache.flink.runtime.util.ResourceCounter;
import org.apache.flink.util.ExecutorUtils;
import org.apache.flink.util.FlinkException;
import org.apache.flink.util.Preconditions;
import org.apache.flink.util.concurrent.FutureUtils;
import org.apache.flink.util.concurrent.ManuallyTriggeredScheduledExecutor;
import org.apache.flink.util.concurrent.ScheduledExecutor;
import org.apache.flink.util.function.BiFunctionWithException;
import org.apache.flink.shaded.guava33.com.google.common.collect.Iterables;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import static org.apache.flink.core.testutils.FlinkAssertions.assertThatFuture;
import static org.apache.flink.runtime.executiongraph.ExecutionGraphTestUtils.createExecutionAttemptId;
import static org.apache.flink.runtime.jobmaster.slotpool.SlotPoolTestUtils.createSlotOffersForResourceRequirements;
import static org.apache.flink.runtime.scheduler.SchedulerTestingUtils.acknowledgePendingCheckpoint;
import static org.apache.flink.runtime.scheduler.SchedulerTestingUtils.createFailedTaskExecutionState;
import static org.apache.flink.runtime.scheduler.SchedulerTestingUtils.enableCheckpointing;
import static org.apache.flink.runtime.scheduler.SchedulerTestingUtils.getCheckpointCoordinator;
import static org.apache.flink.runtime.util.JobVertexConnectionUtils.connectNewDataSetAsInput;
import static org.apache.flink.util.ExceptionUtils.findThrowable;
import static org.apache.flink.util.ExceptionUtils.findThrowableWithMessage;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class DefaultSchedulerTest_Purified {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultSchedulerTest.class);

    private static final int TIMEOUT_MS = 1000;

    private final ManuallyTriggeredScheduledExecutor taskRestartExecutor = new ManuallyTriggeredScheduledExecutor();

    private ExecutorService executor;

    private ScheduledExecutorService scheduledExecutorService;

    private Configuration configuration;

    private TestRestartBackoffTimeStrategy testRestartBackoffTimeStrategy;

    private TestExecutionOperationsDecorator testExecutionOperations;

    private ExecutionVertexVersioner executionVertexVersioner;

    private TestExecutionSlotAllocatorFactory executionSlotAllocatorFactory;

    private TestExecutionSlotAllocator testExecutionSlotAllocator;

    private TestingShuffleMaster shuffleMaster;

    private TestingJobMasterPartitionTracker partitionTracker;

    private Duration timeout;

    @BeforeEach
    void setUp() {
        executor = Executors.newSingleThreadExecutor();
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        configuration = new Configuration();
        testRestartBackoffTimeStrategy = new TestRestartBackoffTimeStrategy(true, 0);
        testExecutionOperations = new TestExecutionOperationsDecorator(new DefaultExecutionOperations());
        executionVertexVersioner = new ExecutionVertexVersioner();
        executionSlotAllocatorFactory = new TestExecutionSlotAllocatorFactory();
        testExecutionSlotAllocator = executionSlotAllocatorFactory.getTestExecutionSlotAllocator();
        shuffleMaster = new TestingShuffleMaster();
        partitionTracker = new TestingJobMasterPartitionTracker();
        timeout = Duration.ofSeconds(60);
    }

    @AfterEach
    void tearDown() {
        if (scheduledExecutorService != null) {
            ExecutorUtils.gracefulShutdown(TIMEOUT_MS, TimeUnit.MILLISECONDS, scheduledExecutorService);
        }
        if (executor != null) {
            ExecutorUtils.gracefulShutdown(TIMEOUT_MS, TimeUnit.MILLISECONDS, executor);
        }
    }

    private void assertCheckpointSchedulingOperationHavingNoEffectAfterJobFinished(Consumer<DefaultScheduler> callSchedulingOperation) {
        final JobGraph jobGraph = singleNonParallelJobVertexJobGraph();
        enableCheckpointing(jobGraph);
        final DefaultScheduler scheduler = createSchedulerAndStartScheduling(jobGraph);
        assertThat(scheduler.getCheckpointCoordinator()).isNotNull();
        scheduler.updateTaskExecutionState(new TaskExecutionState(Iterables.getOnlyElement(scheduler.getExecutionGraph().getAllExecutionVertices()).getCurrentExecutionAttempt().getAttemptId(), ExecutionState.FINISHED));
        assertThat(scheduler.getCheckpointCoordinator()).isNull();
        callSchedulingOperation.accept(scheduler);
        assertThat(scheduler.getCheckpointCoordinator()).isNull();
    }

    private void commonJobStatusHookTest(ExecutionState expectedExecutionState, JobStatus expectedJobStatus) throws Exception {
        final JobGraph jobGraph = singleNonParallelJobVertexJobGraph();
        TestingJobStatusHook jobStatusHook = new TestingJobStatusHook();
        final List<JobID> onCreatedJobList = new LinkedList<>();
        jobStatusHook.setOnCreatedConsumer((jobId) -> onCreatedJobList.add(jobId));
        final List<JobID> onJobStatusList = new LinkedList<>();
        switch(expectedJobStatus) {
            case FAILED:
                jobStatusHook.setOnFailedConsumer((jobID, throwable) -> onJobStatusList.add(jobID));
                break;
            case CANCELED:
                jobStatusHook.setOnCanceledConsumer((jobID) -> onJobStatusList.add(jobID));
                break;
            case FINISHED:
                jobStatusHook.setOnFinishedConsumer((jobID) -> onJobStatusList.add(jobID));
                break;
            default:
                throw new UnsupportedOperationException("JobStatusHook test is not supported: " + expectedJobStatus);
        }
        List<JobStatusHook> jobStatusHooks = new ArrayList<>();
        jobStatusHooks.add(jobStatusHook);
        jobGraph.setJobStatusHooks(jobStatusHooks);
        testRestartBackoffTimeStrategy.setCanRestart(false);
        final DefaultScheduler scheduler = createSchedulerAndStartScheduling(jobGraph);
        final ArchivedExecutionVertex onlyExecutionVertex = Iterables.getOnlyElement(scheduler.requestJob().getArchivedExecutionGraph().getAllExecutionVertices());
        final ExecutionAttemptID attemptId = onlyExecutionVertex.getCurrentExecutionAttempt().getAttemptId();
        if (JobStatus.CANCELED == expectedJobStatus) {
            scheduler.cancel();
        }
        scheduler.updateTaskExecutionState(new TaskExecutionState(attemptId, expectedExecutionState));
        taskRestartExecutor.triggerScheduledTasks();
        waitForTermination(scheduler);
        final JobStatus jobStatus = scheduler.requestJobStatus();
        assertThat(jobStatus).isEqualTo(expectedJobStatus);
        assertThat(onCreatedJobList).singleElement().isEqualTo(jobGraph.getJobID());
        assertThat(onCreatedJobList).singleElement().isEqualTo(jobGraph.getJobID());
    }

    public static void doTestCheckpointCleanerIsClosedAfterCheckpointServices(BiFunction<CheckpointRecoveryFactory, CheckpointsCleaner, SchedulerNG> schedulerFactory, ScheduledExecutorService executorService, Logger logger) throws Exception {
        final CountDownLatch checkpointServicesShutdownBlocked = new CountDownLatch(1);
        final CountDownLatch cleanerClosed = new CountDownLatch(1);
        final CompletedCheckpointStore completedCheckpointStore = new StandaloneCompletedCheckpointStore(1) {

            @Override
            public void shutdown(JobStatus jobStatus, CheckpointsCleaner checkpointsCleaner) throws Exception {
                checkpointServicesShutdownBlocked.await();
                super.shutdown(jobStatus, checkpointsCleaner);
            }
        };
        final CheckpointIDCounter checkpointIDCounter = new StandaloneCheckpointIDCounter() {

            @Override
            public CompletableFuture<Void> shutdown(JobStatus jobStatus) {
                try {
                    checkpointServicesShutdownBlocked.await();
                } catch (InterruptedException e) {
                    logger.error("An error occurred while executing waiting for the CheckpointServices shutdown.", e);
                    Thread.currentThread().interrupt();
                }
                return super.shutdown(jobStatus);
            }
        };
        final CheckpointsCleaner checkpointsCleaner = new CheckpointsCleaner() {

            @Override
            public synchronized CompletableFuture<Void> closeAsync() {
                cleanerClosed.countDown();
                return super.closeAsync();
            }
        };
        final SchedulerNG scheduler = schedulerFactory.apply(new TestingCheckpointRecoveryFactory(completedCheckpointStore, checkpointIDCounter), checkpointsCleaner);
        final CompletableFuture<Void> schedulerClosed = new CompletableFuture<>();
        final CountDownLatch schedulerClosing = new CountDownLatch(1);
        executorService.submit(() -> {
            scheduler.closeAsync().thenRun(() -> schedulerClosed.complete(null));
            schedulerClosing.countDown();
        });
        schedulerClosing.await();
        assertThat(cleanerClosed.await(10, TimeUnit.MILLISECONDS)).withFailMessage("CheckpointCleaner should not close before checkpoint services.").isFalse();
        checkpointServicesShutdownBlocked.countDown();
        cleanerClosed.await();
        schedulerClosed.get();
    }

    public static void runCloseAsyncCompletesInMainThreadTest(ScheduledExecutorService singleThreadExecutorService, BiFunctionWithException<ComponentMainThreadExecutor, CheckpointsCleaner, SchedulerNG, Exception> schedulerFactory) throws Exception {
        final OneShotLatch cleanerCloseLatch = new OneShotLatch();
        final CompletableFuture<Void> cleanerCloseFuture = new CompletableFuture<>();
        final CheckpointsCleaner checkpointsCleaner = new CheckpointsCleaner() {

            @Override
            public CompletableFuture<Void> closeAsync() {
                cleanerCloseLatch.trigger();
                return cleanerCloseFuture;
            }
        };
        final ComponentMainThreadExecutor mainThreadExecutor = ComponentMainThreadExecutorServiceAdapter.forSingleThreadExecutor(singleThreadExecutorService);
        final SchedulerNG scheduler = schedulerFactory.apply(mainThreadExecutor, checkpointsCleaner);
        mainThreadExecutor.execute(scheduler::startScheduling);
        final CompletableFuture<Void> closeFuture = new CompletableFuture<>();
        mainThreadExecutor.execute(() -> {
            FutureUtils.forward(scheduler.closeAsync().thenRun(() -> {
                mainThreadExecutor.assertRunningInMainThread();
            }), closeFuture);
        });
        cleanerCloseLatch.await();
        Thread.sleep(50);
        cleanerCloseFuture.complete(null);
        closeFuture.join();
    }

    private static long initiateFailure(DefaultScheduler scheduler, ExecutionAttemptID executionAttemptId, Throwable exception) {
        scheduler.updateTaskExecutionState(new TaskExecutionState(executionAttemptId, ExecutionState.FAILED, exception));
        return getFailureTimestamp(scheduler, executionAttemptId);
    }

    private static long getFailureTimestamp(DefaultScheduler scheduler, ExecutionAttemptID executionAttemptId) {
        final ExecutionVertex failedExecutionVertex = StreamSupport.stream(scheduler.getExecutionGraph().getAllExecutionVertices().spliterator(), false).filter(v -> executionAttemptId.equals(v.getCurrentExecutionAttempt().getAttemptId())).findFirst().orElseThrow(() -> new IllegalArgumentException("No ExecutionVertex available for the passed ExecutionAttemptId " + executionAttemptId));
        return failedExecutionVertex.getFailureInfo().map(ErrorInfo::getTimestamp).orElseThrow(() -> new IllegalStateException("No failure was set for ExecutionVertex having the passed execution " + executionAttemptId));
    }

    private static JobVertex createVertex(String name, int parallelism) {
        final JobVertex v = new JobVertex(name);
        v.setParallelism(parallelism);
        v.setInvokableClass(AbstractInvokable.class);
        return v;
    }

    private void waitForTermination(final DefaultScheduler scheduler) throws Exception {
        scheduler.getJobTerminationFuture().get(TIMEOUT_MS, TimeUnit.MILLISECONDS);
    }

    public static JobGraph singleNonParallelJobVertexJobGraph() {
        return singleJobVertexJobGraph(1);
    }

    public static JobGraph singleNonParallelJobVertexJobGraphForBatch() {
        return singleJobVertexJobGraphForBatch(1);
    }

    private static JobGraph singleJobVertexJobGraphForBatch(final int parallelism) {
        final JobVertex vertex = new JobVertex("source");
        vertex.setInvokableClass(NoOpInvokable.class);
        vertex.setParallelism(parallelism);
        return JobGraphTestUtils.batchJobGraph(vertex);
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

    private static JobGraph sourceSinkJobGraph(final int parallelism) {
        final JobVertex source = new JobVertex("source");
        source.setParallelism(parallelism);
        source.setInvokableClass(NoOpInvokable.class);
        final JobVertex sink = new JobVertex("sink");
        sink.setParallelism(parallelism);
        sink.setInvokableClass(NoOpInvokable.class);
        connectNewDataSetAsInput(sink, source, DistributionPattern.ALL_TO_ALL, ResultPartitionType.PIPELINED);
        return JobGraphTestUtils.streamingJobGraph(source, sink);
    }

    private static JobVertex getOnlyJobVertex(final JobGraph jobGraph) {
        final List<JobVertex> sortedVertices = jobGraph.getVerticesSortedTopologicallyFromSources();
        Preconditions.checkState(sortedVertices.size() == 1);
        return sortedVertices.get(0);
    }

    private DefaultScheduler createSchedulerAndStartScheduling(final JobGraph jobGraph, final Collection<FailureEnricher> failureEnrichers) {
        return createSchedulerAndStartScheduling(jobGraph, ComponentMainThreadExecutorServiceAdapter.forMainThread(), failureEnrichers);
    }

    private DefaultScheduler createSchedulerAndStartScheduling(final JobGraph jobGraph) {
        return createSchedulerAndStartScheduling(jobGraph, ComponentMainThreadExecutorServiceAdapter.forMainThread());
    }

    private DefaultScheduler createSchedulerAndStartScheduling(final JobGraph jobGraph, final ComponentMainThreadExecutor mainThreadExecutor) {
        return createSchedulerAndStartScheduling(jobGraph, mainThreadExecutor, Collections.emptySet());
    }

    private DefaultScheduler createSchedulerAndStartScheduling(final JobGraph jobGraph, final ComponentMainThreadExecutor mainThreadExecutor, final Collection<FailureEnricher> failureEnrichers) {
        try {
            final DefaultScheduler scheduler = createSchedulerBuilder(jobGraph, mainThreadExecutor, failureEnrichers).build();
            mainThreadExecutor.execute(scheduler::startScheduling);
            return scheduler;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private DefaultScheduler createScheduler(final JobGraph jobGraph, final ComponentMainThreadExecutor mainThreadExecutor, final SchedulingStrategyFactory schedulingStrategyFactory) throws Exception {
        return createSchedulerBuilder(jobGraph, mainThreadExecutor, Collections.emptySet()).setSchedulingStrategyFactory(schedulingStrategyFactory).build();
    }

    private DefaultScheduler createScheduler(final JobGraph jobGraph, final ComponentMainThreadExecutor mainThreadExecutor, final SchedulingStrategyFactory schedulingStrategyFactory, final FailoverStrategy.Factory failoverStrategyFactory) throws Exception {
        return createSchedulerBuilder(jobGraph, mainThreadExecutor, Collections.emptySet()).setSchedulingStrategyFactory(schedulingStrategyFactory).setFailoverStrategyFactory(failoverStrategyFactory).build();
    }

    private DefaultScheduler createScheduler(final JobGraph jobGraph, final ComponentMainThreadExecutor mainThreadExecutor, final SchedulingStrategyFactory schedulingStrategyFactory, final FailoverStrategy.Factory failoverStrategyFactory, final ScheduledExecutor delayExecutor) throws Exception {
        return createSchedulerBuilder(jobGraph, mainThreadExecutor, Collections.emptySet()).setDelayExecutor(delayExecutor).setSchedulingStrategyFactory(schedulingStrategyFactory).setFailoverStrategyFactory(failoverStrategyFactory).build();
    }

    private DefaultSchedulerBuilder createSchedulerBuilder(final JobGraph jobGraph, final ComponentMainThreadExecutor mainThreadExecutor) {
        return createSchedulerBuilder(jobGraph, mainThreadExecutor, Collections.emptySet());
    }

    private DefaultSchedulerBuilder createSchedulerBuilder(final JobGraph jobGraph, final ComponentMainThreadExecutor mainThreadExecutor, final Collection<FailureEnricher> failureEnrichers) {
        return new DefaultSchedulerBuilder(jobGraph, mainThreadExecutor, executor, scheduledExecutorService, taskRestartExecutor).setLogger(LOG).setJobMasterConfiguration(configuration).setSchedulingStrategyFactory(new PipelinedRegionSchedulingStrategy.Factory()).setFailoverStrategyFactory(new RestartPipelinedRegionFailoverStrategy.Factory()).setRestartBackoffTimeStrategy(testRestartBackoffTimeStrategy).setExecutionOperations(testExecutionOperations).setExecutionVertexVersioner(executionVertexVersioner).setExecutionSlotAllocatorFactory(executionSlotAllocatorFactory).setFailureEnrichers(failureEnrichers).setShuffleMaster(shuffleMaster).setPartitionTracker(partitionTracker).setRpcTimeout(timeout);
    }

    private static class ReorganizableManuallyTriggeredScheduledExecutor extends ManuallyTriggeredScheduledExecutor {

        private final List<ScheduledTask<?>> scheduledTasks = new ArrayList<>();

        @Override
        public ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
            return schedule(() -> {
                command.run();
                return null;
            }, delay, unit);
        }

        @Override
        public <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit) {
            final ScheduledTask<V> scheduledTask = new ScheduledTask<>(callable, unit.convert(delay, TimeUnit.MILLISECONDS));
            scheduledTasks.add(scheduledTask);
            return scheduledTask;
        }

        public List<ScheduledTask<?>> getCollectedScheduledTasks() {
            return scheduledTasks;
        }

        void scheduleCollectedScheduledTasks() {
            for (ScheduledTask<?> scheduledTask : scheduledTasks) {
                super.schedule(scheduledTask.getCallable(), scheduledTask.getDelay(TimeUnit.MILLISECONDS), TimeUnit.MILLISECONDS);
            }
            scheduledTasks.clear();
        }

        @Override
        public void triggerNonPeriodicScheduledTask() {
            scheduleCollectedScheduledTasks();
            super.triggerNonPeriodicScheduledTask();
        }

        @Override
        public void triggerNonPeriodicScheduledTasks() {
            scheduleCollectedScheduledTasks();
            super.triggerNonPeriodicScheduledTasks();
        }
    }

    private CountDownLatch getCheckpointTriggeredLatch() {
        final CountDownLatch checkpointTriggeredLatch = new CountDownLatch(1);
        final SimpleAckingTaskManagerGateway taskManagerGateway = new SimpleAckingTaskManagerGateway();
        testExecutionSlotAllocator.getLogicalSlotBuilder().setTaskManagerGateway(taskManagerGateway);
        taskManagerGateway.setCheckpointConsumer((executionAttemptID, jobId, checkpointId, timestamp, checkpointOptions) -> {
            checkpointTriggeredLatch.countDown();
        });
        return checkpointTriggeredLatch;
    }

    private void transitionToRunning(DefaultScheduler scheduler, ExecutionAttemptID attemptId) {
        Preconditions.checkState(scheduler.updateTaskExecutionState(new TaskExecutionState(attemptId, ExecutionState.INITIALIZING)));
        Preconditions.checkState(scheduler.updateTaskExecutionState(new TaskExecutionState(attemptId, ExecutionState.RUNNING)));
    }

    @Test
    void allocationIsCanceledWhenVertexIsFailedOrCanceled_1_testMerged_1() throws Exception {
        testExecutionSlotAllocator.disableAutoCompletePendingRequests();
        assertThat(testExecutionSlotAllocator.getPendingRequests()).hasSize(2);
        assertThat(testExecutionSlotAllocator.getPendingRequests()).isEmpty();
    }

    @Test
    void allocationIsCanceledWhenVertexIsFailedOrCanceled_2_testMerged_2() throws Exception {
        final JobGraph jobGraph = singleJobVertexJobGraph(2);
        final DefaultScheduler scheduler = createScheduler(jobGraph, ComponentMainThreadExecutorServiceAdapter.forMainThread(), new PipelinedRegionSchedulingStrategy.Factory(), new RestartAllFailoverStrategy.Factory());
        scheduler.startScheduling();
        Iterator<ArchivedExecutionVertex> vertexIterator = scheduler.requestJob().getArchivedExecutionGraph().getAllExecutionVertices().iterator();
        ArchivedExecutionVertex v1 = vertexIterator.next();
        final String exceptionMessage = "expected exception";
        scheduler.updateTaskExecutionState(new TaskExecutionState(v1.getCurrentExecutionAttempt().getAttemptId(), ExecutionState.FAILED, new RuntimeException(exceptionMessage)));
        vertexIterator = scheduler.requestJob().getArchivedExecutionGraph().getAllExecutionVertices().iterator();
        v1 = vertexIterator.next();
        ArchivedExecutionVertex v2 = vertexIterator.next();
        assertThat(v1.getExecutionState()).isEqualTo(ExecutionState.FAILED);
        assertThat(v2.getExecutionState()).isEqualTo(ExecutionState.CANCELED);
    }

    @Test
    void testFailedProducedPartitionRegistration_1() {
        assertThat(testExecutionOperations.getCanceledVertices()).isEmpty();
    }

    @Test
    void testFailedProducedPartitionRegistration_2() {
        assertThat(testExecutionOperations.getFailedVertices()).isEmpty();
    }

    @Test
    void testFailedProducedPartitionRegistration_3() {
        assertThat(testExecutionOperations.getCanceledVertices()).hasSize(2);
    }

    @Test
    void testFailedProducedPartitionRegistration_4() {
        assertThat(testExecutionOperations.getFailedVertices()).hasSize(1);
    }

    @Test
    void testDirectExceptionOnProducedPartitionRegistration_1() {
        assertThat(testExecutionOperations.getCanceledVertices()).hasSize(2);
    }

    @Test
    void testDirectExceptionOnProducedPartitionRegistration_2() {
        assertThat(testExecutionOperations.getFailedVertices()).hasSize(1);
    }
}
