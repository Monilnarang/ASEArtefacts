package org.apache.flink.runtime.asyncprocessing;

import org.apache.flink.api.common.operators.MailboxExecutor;
import org.apache.flink.api.common.state.v2.State;
import org.apache.flink.api.common.state.v2.ValueStateDescriptor;
import org.apache.flink.api.common.typeinfo.BasicTypeInfo;
import org.apache.flink.api.common.typeutils.base.IntSerializer;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.core.fs.CloseableRegistry;
import org.apache.flink.core.state.InternalStateFuture;
import org.apache.flink.core.state.StateFutureImpl.AsyncFrameworkExceptionHandler;
import org.apache.flink.core.state.StateFutureUtils;
import org.apache.flink.metrics.Gauge;
import org.apache.flink.metrics.MetricGroup;
import org.apache.flink.metrics.groups.UnregisteredMetricsGroup;
import org.apache.flink.runtime.asyncprocessing.EpochManager.Epoch;
import org.apache.flink.runtime.asyncprocessing.EpochManager.ParallelMode;
import org.apache.flink.runtime.asyncprocessing.declare.DeclarationManager;
import org.apache.flink.runtime.mailbox.SyncMailboxExecutor;
import org.apache.flink.runtime.state.AsyncKeyedStateBackend;
import org.apache.flink.runtime.state.StateBackend;
import org.apache.flink.runtime.state.StateBackendTestUtils;
import org.apache.flink.runtime.state.VoidNamespace;
import org.apache.flink.runtime.state.VoidNamespaceSerializer;
import org.apache.flink.runtime.state.v2.AbstractValueState;
import org.apache.flink.util.FlinkRuntimeException;
import org.apache.flink.util.Preconditions;
import org.apache.flink.util.function.ThrowingRunnable;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Supplier;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class AsyncExecutionControllerTest_Purified {

    AsyncExecutionController<String> aec;

    AtomicInteger output;

    TestValueState valueState;

    final Runnable userCode = () -> {
        valueState.asyncValue().thenCompose(val -> {
            int updated = (val == null ? 1 : (val + 1));
            return valueState.asyncUpdate(updated).thenCompose(o -> StateFutureUtils.completedFuture(updated));
        }).thenAccept(val -> output.set(val));
    };

    final Map<String, Gauge> registeredGauges = new HashMap<>();

    void setup(int batchSize, long timeout, int maxInFlight, MailboxExecutor mailboxExecutor, AsyncFrameworkExceptionHandler exceptionHandler, CloseableRegistry closeableRegistry) throws IOException {
        StateExecutor stateExecutor = new TestStateExecutor();
        ValueStateDescriptor<Integer> stateDescriptor = new ValueStateDescriptor<>("test-value-state", BasicTypeInfo.INT_TYPE_INFO);
        Supplier<State> stateSupplier = () -> new TestValueState(aec, new TestUnderlyingState(), stateDescriptor);
        StateBackend testAsyncStateBackend = StateBackendTestUtils.buildAsyncStateBackend(stateSupplier, stateExecutor);
        assertThat(testAsyncStateBackend.supportsAsyncKeyedStateBackend()).isTrue();
        AsyncKeyedStateBackend<String> asyncKeyedStateBackend;
        try {
            asyncKeyedStateBackend = testAsyncStateBackend.createAsyncKeyedStateBackend(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        closeableRegistry.registerCloseable(asyncKeyedStateBackend);
        closeableRegistry.registerCloseable(asyncKeyedStateBackend::dispose);
        UnregisteredMetricsGroup metricsGroup = new UnregisteredMetricsGroup() {

            String prefix = "";

            @Override
            public <T, G extends Gauge<T>> G gauge(String name, G gauge) {
                registeredGauges.put(prefix + "." + name, gauge);
                return gauge;
            }

            @Override
            public MetricGroup addGroup(String name) {
                prefix = name;
                return this;
            }
        };
        aec = new AsyncExecutionController<>(mailboxExecutor, exceptionHandler, stateExecutor, new DeclarationManager(), 128, batchSize, timeout, maxInFlight, null, metricsGroup.addGroup("asyncStateProcessing"));
        asyncKeyedStateBackend.setup(aec);
        try {
            valueState = asyncKeyedStateBackend.getOrCreateKeyedState(VoidNamespace.INSTANCE, VoidNamespaceSerializer.INSTANCE, stateDescriptor);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        output = new AtomicInteger();
    }

    static class TestUnderlyingState {

        private final HashMap<Tuple2<String, String>, Integer> hashMap;

        public TestUnderlyingState() {
            this.hashMap = new HashMap<>();
        }

        public Integer get(String key, String namespace) {
            return hashMap.get(Tuple2.of(key, namespace));
        }

        public void update(String key, String namespace, Integer val) {
            hashMap.put(Tuple2.of(key, namespace), val);
        }
    }

    static class TestValueState extends AbstractValueState<String, String, Integer> {

        private final TestUnderlyingState underlyingState;

        public TestValueState(StateRequestHandler stateRequestHandler, TestUnderlyingState underlyingState, ValueStateDescriptor<Integer> stateDescriptor) {
            super(stateRequestHandler, stateDescriptor.getSerializer());
            this.underlyingState = underlyingState;
            assertThat(this.getValueSerializer()).isEqualTo(IntSerializer.INSTANCE);
        }
    }

    static class TestStateExecutor implements StateExecutor {

        public TestStateExecutor() {
        }

        @Override
        @SuppressWarnings({ "unchecked", "rawtypes" })
        public CompletableFuture<Void> executeBatchRequests(StateRequestContainer stateRequestContainer) {
            Preconditions.checkArgument(stateRequestContainer instanceof MockStateRequestContainer);
            CompletableFuture<Void> future = new CompletableFuture<>();
            for (StateRequest request : ((MockStateRequestContainer) stateRequestContainer).getStateRequestList()) {
                executeRequestSync(request);
            }
            future.complete(null);
            return future;
        }

        @Override
        public StateRequestContainer createStateRequestContainer() {
            return new MockStateRequestContainer();
        }

        @Override
        public void executeRequestSync(StateRequest<?, ?, ?, ?> request) {
            if (request.getRequestType() == StateRequestType.VALUE_GET) {
                Preconditions.checkState(request.getState() != null);
                TestValueState state = (TestValueState) request.getState();
                Integer val = state.underlyingState.get((String) request.getRecordContext().getKey(), (String) request.getRecordContext().getNamespace(state));
                ((InternalStateFuture<Integer>) request.getFuture()).complete(val);
            } else if (request.getRequestType() == StateRequestType.VALUE_UPDATE) {
                Preconditions.checkState(request.getState() != null);
                TestValueState state = (TestValueState) request.getState();
                state.underlyingState.update((String) request.getRecordContext().getKey(), (String) request.getRecordContext().getNamespace(state), (Integer) request.getPayload());
                request.getFuture().complete(null);
            } else {
                throw new UnsupportedOperationException("Unsupported request type");
            }
        }

        @Override
        public boolean fullyLoaded() {
            return false;
        }

        @Override
        public void shutdown() {
        }
    }

    static class TestAsyncFrameworkExceptionHandler implements AsyncFrameworkExceptionHandler {

        String message = null;

        Throwable exception = null;

        public void handleException(String message, Throwable exception) {
            this.message = message;
            this.exception = exception;
        }
    }

    static class TestMailboxExecutor implements MailboxExecutor {

        Exception lastException = null;

        boolean failWhenExecute = false;

        public TestMailboxExecutor(boolean fail) {
            this.failWhenExecute = fail;
        }

        @Override
        public void execute(MailOptions mailOptions, ThrowingRunnable<? extends Exception> command, String descriptionFormat, Object... descriptionArgs) {
            if (failWhenExecute) {
                throw new RuntimeException("Fail to execute.");
            }
            try {
                command.run();
            } catch (Exception e) {
                this.lastException = e;
            }
        }

        @Override
        public void yield() throws InterruptedException, FlinkRuntimeException {
        }

        @Override
        public boolean tryYield() throws FlinkRuntimeException {
            return false;
        }

        @Override
        public boolean shouldInterrupt() {
            return false;
        }
    }

    @Test
    void testBasicRun_1_testMerged_1() throws IOException {
        String record1 = "key1-r1";
        String key1 = "key1";
        RecordContext<String> recordContext1 = aec.buildContext(record1, key1);
        aec.setCurrentContext(recordContext1);
        assertThat(aec.stateRequestsBuffer.activeQueueSize()).isEqualTo(1);
        assertThat(aec.keyAccountingUnit.occupiedCount()).isEqualTo(1);
        assertThat(aec.inFlightRecordNum.get()).isEqualTo(1);
        aec.triggerIfNeeded(true);
        assertThat(aec.stateRequestsBuffer.activeQueueSize()).isEqualTo(0);
        assertThat(aec.keyAccountingUnit.occupiedCount()).isEqualTo(0);
        assertThat(recordContext1.getReferenceCount()).isEqualTo(0);
        assertThat(aec.inFlightRecordNum.get()).isEqualTo(0);
        String record2 = "key1-r2";
        String key2 = "key1";
        RecordContext<String> recordContext2 = aec.buildContext(record2, key2);
        aec.setCurrentContext(recordContext2);
        String record3 = "key1-r3";
        String key3 = "key1";
        RecordContext<String> recordContext3 = aec.buildContext(record3, key3);
        aec.setCurrentContext(recordContext3);
        assertThat(aec.stateRequestsBuffer.blockingQueueSize()).isEqualTo(1);
        assertThat(aec.inFlightRecordNum.get()).isEqualTo(2);
        assertThat(recordContext2.getReferenceCount()).isEqualTo(0);
        assertThat(aec.stateRequestsBuffer.blockingQueueSize()).isEqualTo(0);
        assertThat(recordContext3.getReferenceCount()).isEqualTo(0);
        String record4 = "key3-r3";
        String key4 = "key3";
        RecordContext<String> recordContext4 = aec.buildContext(record4, key4);
        aec.setCurrentContext(recordContext4);
        assertThat(recordContext4.getReferenceCount()).isEqualTo(0);
    }

    @Test
    void testBasicRun_4() throws IOException {
        assertThat(registeredGauges.get("asyncStateProcessing.numInFlightRecords").getValue()).isEqualTo(1);
    }

    @Test
    void testBasicRun_5() throws IOException {
        assertThat(registeredGauges.get("asyncStateProcessing.activeBufferSize").getValue()).isEqualTo(1);
    }

    @Test
    void testBasicRun_6() throws IOException {
        assertThat(registeredGauges.get("asyncStateProcessing.blockingBufferSize").getValue()).isEqualTo(0);
    }

    @Test
    void testBasicRun_39() throws IOException {
        assertThat(output.get()).isEqualTo(3);
    }

    @Test
    void testBasicRun_7() throws IOException {
        assertThat(registeredGauges.get("asyncStateProcessing.numBlockingKeys").getValue()).isEqualTo(0);
    }

    @Test
    void testBasicRun_12() throws IOException {
        assertThat(output.get()).isEqualTo(1);
    }

    @Test
    void testBasicRun_50() throws IOException {
        assertThat(output.get()).isEqualTo(1);
    }

    @Test
    void testBasicRun_23() throws IOException {
        assertThat(registeredGauges.get("asyncStateProcessing.numInFlightRecords").getValue()).isEqualTo(2);
    }

    @Test
    void testBasicRun_24() throws IOException {
        assertThat(registeredGauges.get("asyncStateProcessing.activeBufferSize").getValue()).isEqualTo(1);
    }

    @Test
    void testBasicRun_25() throws IOException {
        assertThat(registeredGauges.get("asyncStateProcessing.blockingBufferSize").getValue()).isEqualTo(1);
    }

    @Test
    void testBasicRun_26() throws IOException {
        assertThat(registeredGauges.get("asyncStateProcessing.numBlockingKeys").getValue()).isEqualTo(1);
    }

    @Test
    void testBasicRun_30() throws IOException {
        assertThat(output.get()).isEqualTo(2);
    }

    @Test
    void testRecordsRunInOrder_1_testMerged_1() throws IOException {
        String record1 = "key1-r1";
        String key1 = "key1";
        RecordContext<String> recordContext1 = aec.buildContext(record1, key1);
        aec.setCurrentContext(recordContext1);
        String record2 = "key2-r1";
        String key2 = "key2";
        RecordContext<String> recordContext2 = aec.buildContext(record2, key2);
        aec.setCurrentContext(recordContext2);
        String record3 = "key1-r2";
        String key3 = "key1";
        RecordContext<String> recordContext3 = aec.buildContext(record3, key3);
        aec.setCurrentContext(recordContext3);
        assertThat(aec.stateRequestsBuffer.activeQueueSize()).isEqualTo(2);
        assertThat(aec.keyAccountingUnit.occupiedCount()).isEqualTo(2);
        assertThat(aec.inFlightRecordNum.get()).isEqualTo(3);
        assertThat(aec.stateRequestsBuffer.blockingQueueSize()).isEqualTo(1);
        aec.triggerIfNeeded(true);
        assertThat(aec.stateRequestsBuffer.activeQueueSize()).isEqualTo(1);
        assertThat(aec.keyAccountingUnit.occupiedCount()).isEqualTo(1);
        assertThat(aec.inFlightRecordNum.get()).isEqualTo(1);
        assertThat(aec.stateRequestsBuffer.blockingQueueSize()).isEqualTo(0);
        assertThat(recordContext1.getReferenceCount()).isEqualTo(0);
        assertThat(recordContext2.getReferenceCount()).isEqualTo(0);
        assertThat(recordContext3.getReferenceCount()).isEqualTo(0);
        assertThat(aec.inFlightRecordNum.get()).isEqualTo(0);
    }

    @Test
    void testRecordsRunInOrder_18() throws IOException {
        assertThat(output.get()).isEqualTo(2);
    }

    @Test
    void testRecordsRunInOrder_13() throws IOException {
        assertThat(output.get()).isEqualTo(1);
    }
}
