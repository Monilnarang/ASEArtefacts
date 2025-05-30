package org.apache.flink.runtime.executiongraph.failover;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.configuration.TraceOptions;
import org.apache.flink.core.failure.TestingFailureEnricher;
import org.apache.flink.metrics.groups.UnregisteredMetricsGroup;
import org.apache.flink.runtime.concurrent.ComponentMainThreadExecutorServiceAdapter;
import org.apache.flink.runtime.execution.SuppressRestartsException;
import org.apache.flink.runtime.executiongraph.Execution;
import org.apache.flink.runtime.jobgraph.JobVertexID;
import org.apache.flink.runtime.scheduler.adaptive.JobFailureMetricReporter;
import org.apache.flink.runtime.scheduler.strategy.ExecutionVertexID;
import org.apache.flink.runtime.scheduler.strategy.SchedulingExecutionVertex;
import org.apache.flink.runtime.scheduler.strategy.SchedulingTopology;
import org.apache.flink.runtime.scheduler.strategy.TestingSchedulingTopology;
import org.apache.flink.testutils.TestingUtils;
import org.apache.flink.testutils.executor.TestExecutorExtension;
import org.apache.flink.traces.Span;
import org.apache.flink.traces.SpanBuilder;
import org.apache.flink.util.IterableUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ExecutionFailureHandlerTest_Purified {

    @RegisterExtension
    private static final TestExecutorExtension<ScheduledExecutorService> EXECUTOR_RESOURCE = TestingUtils.defaultExecutorExtension();

    private static final long RESTART_DELAY_MS = 1234L;

    private SchedulingTopology schedulingTopology;

    private TestFailoverStrategy failoverStrategy;

    private AtomicBoolean isNewAttempt;

    private TestRestartBackoffTimeStrategy backoffTimeStrategy;

    private ExecutionFailureHandler executionFailureHandler;

    private TestingFailureEnricher testingFailureEnricher;

    private List<Span> spanCollector;

    @BeforeEach
    void setUp() {
        TestingSchedulingTopology topology = new TestingSchedulingTopology();
        topology.newExecutionVertex();
        schedulingTopology = topology;
        failoverStrategy = new TestFailoverStrategy();
        testingFailureEnricher = new TestingFailureEnricher();
        isNewAttempt = new AtomicBoolean(true);
        backoffTimeStrategy = new TestRestartBackoffTimeStrategy(true, RESTART_DELAY_MS, isNewAttempt::get);
        spanCollector = new CopyOnWriteArrayList<>();
        Configuration configuration = new Configuration();
        configuration.set(TraceOptions.REPORT_EVENTS_AS_SPANS, Boolean.TRUE);
        executionFailureHandler = new ExecutionFailureHandler(configuration, schedulingTopology, failoverStrategy, backoffTimeStrategy, ComponentMainThreadExecutorServiceAdapter.forMainThread(), Collections.singleton(testingFailureEnricher), null, null, new UnregisteredMetricsGroup() {

            @Override
            public void addSpan(SpanBuilder spanBuilder) {
                spanCollector.add(spanBuilder.build());
            }
        });
    }

    private static class TestFailoverStrategy implements FailoverStrategy {

        private Set<ExecutionVertexID> tasksToRestart;

        public TestFailoverStrategy() {
        }

        public void setTasksToRestart(final Set<ExecutionVertexID> tasksToRestart) {
            this.tasksToRestart = tasksToRestart;
        }

        @Override
        public Set<ExecutionVertexID> getTasksNeedingRestart(final ExecutionVertexID executionVertexId, final Throwable cause) {
            return tasksToRestart;
        }
    }

    private void checkMetrics(List<Span> results, boolean global, boolean canRestart) {
        assertThat(results).isNotEmpty();
        for (Span span : results) {
            assertThat(span.getScope()).isEqualTo(JobFailureMetricReporter.class.getCanonicalName());
            assertThat(span.getName()).isEqualTo("JobFailure");
            Map<String, Object> attributes = span.getAttributes();
            assertThat(attributes).containsEntry("failureLabel.failKey", "failValue");
            assertThat(attributes).containsEntry("canRestart", String.valueOf(canRestart));
            assertThat(attributes).containsEntry("isGlobalFailure", String.valueOf(global));
        }
    }

    @Test
    void testUnrecoverableErrorCheck_1() {
        assertThat(ExecutionFailureHandler.isUnrecoverableError(new Exception())).isFalse();
    }

    @Test
    void testUnrecoverableErrorCheck_2() {
        assertThat(ExecutionFailureHandler.isUnrecoverableError(new SuppressRestartsException(new Exception()))).isTrue();
    }

    @Test
    void testUnrecoverableErrorCheck_3() {
        assertThat(ExecutionFailureHandler.isUnrecoverableError(new Exception(new SuppressRestartsException(new Exception())))).isTrue();
    }
}
