package org.apache.flink.streaming.runtime.tasks;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.typeinfo.BasicTypeInfo;
import org.apache.flink.api.common.typeutils.base.StringSerializer;
import org.apache.flink.core.execution.SavepointFormatType;
import org.apache.flink.core.testutils.OneShotLatch;
import org.apache.flink.metrics.groups.OperatorMetricGroup;
import org.apache.flink.runtime.checkpoint.CheckpointException;
import org.apache.flink.runtime.checkpoint.CheckpointMetaData;
import org.apache.flink.runtime.checkpoint.CheckpointMetricsBuilder;
import org.apache.flink.runtime.checkpoint.CheckpointOptions;
import org.apache.flink.runtime.checkpoint.CheckpointType;
import org.apache.flink.runtime.checkpoint.SavepointType;
import org.apache.flink.runtime.checkpoint.SnapshotType;
import org.apache.flink.runtime.checkpoint.channel.ChannelStateWriter;
import org.apache.flink.runtime.checkpoint.channel.ChannelStateWriterImpl;
import org.apache.flink.runtime.checkpoint.channel.ResultSubpartitionInfo;
import org.apache.flink.runtime.event.AbstractEvent;
import org.apache.flink.runtime.io.network.api.CancelCheckpointMarker;
import org.apache.flink.runtime.io.network.api.writer.NonRecordWriter;
import org.apache.flink.runtime.io.network.api.writer.RecordOrEventCollectingResultPartitionWriter;
import org.apache.flink.runtime.io.network.api.writer.ResultPartitionWriter;
import org.apache.flink.runtime.io.network.buffer.BufferBuilderTestUtils;
import org.apache.flink.runtime.jobgraph.OperatorID;
import org.apache.flink.runtime.metrics.groups.UnregisteredMetricGroups;
import org.apache.flink.runtime.operators.testutils.DummyEnvironment;
import org.apache.flink.runtime.operators.testutils.MockEnvironment;
import org.apache.flink.runtime.operators.testutils.MockInputSplitProvider;
import org.apache.flink.runtime.state.CheckpointStorage;
import org.apache.flink.runtime.state.CheckpointStorageLocationReference;
import org.apache.flink.runtime.state.CheckpointStreamFactory;
import org.apache.flink.runtime.state.DoneFuture;
import org.apache.flink.runtime.state.KeyedStateHandle;
import org.apache.flink.runtime.state.SnapshotResult;
import org.apache.flink.runtime.state.TestCheckpointStorageWorkerView;
import org.apache.flink.runtime.state.TestTaskStateManager;
import org.apache.flink.runtime.state.storage.JobManagerCheckpointStorage;
import org.apache.flink.streaming.api.graph.StreamConfig;
import org.apache.flink.streaming.api.operators.AbstractStreamOperator;
import org.apache.flink.streaming.api.operators.OneInputStreamOperator;
import org.apache.flink.streaming.api.operators.OperatorSnapshotFutures;
import org.apache.flink.streaming.api.operators.StreamMap;
import org.apache.flink.streaming.api.operators.StreamTaskStateInitializer;
import org.apache.flink.streaming.api.operators.StreamTaskStateInitializerImpl;
import org.apache.flink.streaming.api.watermark.Watermark;
import org.apache.flink.streaming.runtime.streamrecord.LatencyMarker;
import org.apache.flink.streaming.runtime.streamrecord.StreamElementSerializer;
import org.apache.flink.streaming.runtime.streamrecord.StreamRecord;
import org.apache.flink.streaming.runtime.tasks.StreamTaskITCase.NoOpStreamTask;
import org.apache.flink.streaming.runtime.watermarkstatus.WatermarkStatus;
import org.apache.flink.streaming.util.MockStreamTaskBuilder;
import org.apache.flink.util.ExceptionUtils;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;
import static org.apache.flink.runtime.checkpoint.CheckpointType.CHECKPOINT;
import static org.apache.flink.shaded.guava33.com.google.common.util.concurrent.MoreExecutors.newDirectExecutorService;
import static org.apache.flink.streaming.api.operators.StreamOperatorUtils.setProcessingTimeService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.fail;

class SubtaskCheckpointCoordinatorTest_Purified {

    private static final CheckpointStorage CHECKPOINT_STORAGE = new JobManagerCheckpointStorage();

    private boolean initCheckpoint(boolean unalignedCheckpointEnabled, SnapshotType checkpointType) throws IOException, CheckpointException {
        class MockWriter extends ChannelStateWriterImpl.NoOpChannelStateWriter {

            private boolean started;

            @Override
            public void start(long checkpointId, CheckpointOptions checkpointOptions) {
                started = true;
            }
        }
        MockWriter writer = new MockWriter();
        SubtaskCheckpointCoordinator coordinator = coordinator(writer);
        CheckpointStorageLocationReference locationReference = CheckpointStorageLocationReference.getDefault();
        coordinator.initInputsCheckpoint(1L, unalignedCheckpointEnabled ? CheckpointOptions.unaligned(CheckpointType.CHECKPOINT, locationReference) : CheckpointOptions.alignedNoTimeout(checkpointType, locationReference));
        return writer.started;
    }

    private static class MapOperator extends StreamMap<String, String> {

        private static final long serialVersionUID = 1L;

        public MapOperator() {
            super((MapFunction<String, String>) value -> value);
        }

        @Override
        public void notifyCheckpointAborted(long checkpointId) throws Exception {
        }
    }

    private OperatorChain<?, ?> getOperatorChain(MockEnvironment mockEnvironment) throws Exception {
        return new RegularOperatorChain<>(new MockStreamTaskBuilder(mockEnvironment).build(), new NonRecordWriter<>());
    }

    private <T> OperatorChain<T, AbstractStreamOperator<T>> operatorChain(OneInputStreamOperator<T, T>... streamOperators) throws Exception {
        return OperatorChainTest.setupOperatorChain(streamOperators);
    }

    private static final class BlockingRunnableFuture implements RunnableFuture<SnapshotResult<KeyedStateHandle>> {

        private final CompletableFuture<SnapshotResult<KeyedStateHandle>> future = new CompletableFuture<>();

        private final OneShotLatch signalRunLatch = new OneShotLatch();

        private final CountDownLatch countDownLatch;

        private final SnapshotResult<KeyedStateHandle> value;

        private BlockingRunnableFuture() {
            this.countDownLatch = new CountDownLatch(2);
            this.value = SnapshotResult.empty();
        }

        @Override
        public void run() {
            signalRunLatch.trigger();
            countDownLatch.countDown();
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                ExceptionUtils.rethrow(e);
            }
            future.complete(value);
        }

        @Override
        public boolean cancel(boolean mayInterruptIfRunning) {
            future.cancel(mayInterruptIfRunning);
            return true;
        }

        @Override
        public boolean isCancelled() {
            return future.isCancelled();
        }

        @Override
        public boolean isDone() {
            return future.isDone();
        }

        @Override
        public SnapshotResult<KeyedStateHandle> get() throws InterruptedException, ExecutionException {
            return future.get();
        }

        @Override
        public SnapshotResult<KeyedStateHandle> get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException {
            return future.get();
        }

        void awaitRun() throws InterruptedException {
            signalRunLatch.await();
        }
    }

    private static class CheckpointOperator implements OneInputStreamOperator<String, String> {

        private static final long serialVersionUID = 1L;

        private final OperatorSnapshotFutures operatorSnapshotFutures;

        private boolean checkpointed = false;

        CheckpointOperator(OperatorSnapshotFutures operatorSnapshotFutures) {
            this.operatorSnapshotFutures = operatorSnapshotFutures;
        }

        boolean isCheckpointed() {
            return checkpointed;
        }

        @Override
        public void open() throws Exception {
        }

        @Override
        public void finish() throws Exception {
        }

        @Override
        public void close() throws Exception {
        }

        @Override
        public void prepareSnapshotPreBarrier(long checkpointId) {
        }

        @Override
        public OperatorSnapshotFutures snapshotState(long checkpointId, long timestamp, CheckpointOptions checkpointOptions, CheckpointStreamFactory storageLocation) throws Exception {
            this.checkpointed = true;
            return operatorSnapshotFutures;
        }

        @Override
        public void initializeState(StreamTaskStateInitializer streamTaskStateManager) throws Exception {
        }

        @Override
        public void setKeyContextElement1(StreamRecord<?> record) {
        }

        @Override
        public void setKeyContextElement2(StreamRecord<?> record) {
        }

        @Override
        public OperatorMetricGroup getMetricGroup() {
            return UnregisteredMetricGroups.createUnregisteredOperatorMetricGroup();
        }

        @Override
        public OperatorID getOperatorID() {
            return new OperatorID();
        }

        @Override
        public void notifyCheckpointComplete(long checkpointId) {
        }

        @Override
        public void notifyCheckpointAborted(long checkpointId) {
        }

        @Override
        public void setCurrentKey(Object key) {
        }

        @Override
        public Object getCurrentKey() {
            return null;
        }

        @Override
        public void processElement(StreamRecord<String> element) throws Exception {
        }

        @Override
        public void processWatermark(Watermark mark) throws Exception {
        }

        @Override
        public void processLatencyMarker(LatencyMarker latencyMarker) {
        }

        @Override
        public void processWatermarkStatus(WatermarkStatus watermarkStatus) throws Exception {
        }
    }

    private static SubtaskCheckpointCoordinator coordinator(ChannelStateWriter channelStateWriter) throws IOException {
        return new SubtaskCheckpointCoordinatorImpl(new TestCheckpointStorageWorkerView(100), "test", StreamTaskActionExecutor.IMMEDIATE, newDirectExecutorService(), new DummyEnvironment(), (message, unused) -> fail(message), (unused1, unused2) -> CompletableFuture.completedFuture(null), 0, channelStateWriter, true, (callable, duration) -> () -> {
        });
    }

    @Test
    void testInitCheckpoint_1() throws IOException, CheckpointException {
        assertThat(initCheckpoint(true, CHECKPOINT)).isTrue();
    }

    @Test
    void testInitCheckpoint_2() throws IOException, CheckpointException {
        assertThat(initCheckpoint(false, CHECKPOINT)).isFalse();
    }

    @Test
    void testInitCheckpoint_3() throws IOException, CheckpointException {
        assertThat(initCheckpoint(false, SavepointType.savepoint(SavepointFormatType.CANONICAL))).isFalse();
    }
}
