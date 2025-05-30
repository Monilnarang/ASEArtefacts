package org.apache.flink.streaming.runtime.io.checkpointing;

import org.apache.flink.runtime.checkpoint.CheckpointException;
import org.apache.flink.runtime.checkpoint.CheckpointMetaData;
import org.apache.flink.runtime.checkpoint.CheckpointMetricsBuilder;
import org.apache.flink.runtime.checkpoint.CheckpointOptions;
import org.apache.flink.runtime.checkpoint.CheckpointType;
import org.apache.flink.runtime.checkpoint.channel.InputChannelInfo;
import org.apache.flink.runtime.checkpoint.channel.RecordingChannelStateWriter;
import org.apache.flink.runtime.io.network.NettyShuffleEnvironment;
import org.apache.flink.runtime.io.network.NettyShuffleEnvironmentBuilder;
import org.apache.flink.runtime.io.network.TestingConnectionManager;
import org.apache.flink.runtime.io.network.api.CancelCheckpointMarker;
import org.apache.flink.runtime.io.network.api.CheckpointBarrier;
import org.apache.flink.runtime.io.network.api.EndOfPartitionEvent;
import org.apache.flink.runtime.io.network.api.serialization.EventSerializer;
import org.apache.flink.runtime.io.network.partition.consumer.BufferOrEvent;
import org.apache.flink.runtime.io.network.partition.consumer.IndexedInputGate;
import org.apache.flink.runtime.io.network.partition.consumer.InputChannelBuilder;
import org.apache.flink.runtime.io.network.partition.consumer.RemoteInputChannel;
import org.apache.flink.runtime.io.network.partition.consumer.SingleInputGate;
import org.apache.flink.runtime.io.network.partition.consumer.SingleInputGateBuilder;
import org.apache.flink.runtime.io.network.util.TestBufferFactory;
import org.apache.flink.runtime.jobgraph.tasks.AbstractInvokable;
import org.apache.flink.runtime.mailbox.SyncMailboxExecutor;
import org.apache.flink.runtime.operators.testutils.DummyEnvironment;
import org.apache.flink.streaming.runtime.tasks.StreamTask;
import org.apache.flink.streaming.runtime.tasks.TestSubtaskCheckpointCoordinator;
import org.apache.flink.streaming.runtime.tasks.mailbox.MailboxDefaultAction;
import org.apache.flink.util.clock.SystemClock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.annotation.Nullable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import static org.apache.flink.runtime.state.CheckpointStorageLocationReference.getDefault;
import static org.apache.flink.util.Preconditions.checkState;
import static org.assertj.core.api.Assertions.assertThat;

class UnalignedCheckpointsTest_Purified {

    private static final long DEFAULT_CHECKPOINT_ID = 0L;

    private int sizeCounter = 1;

    private CheckpointedInputGate inputGate;

    private RecordingChannelStateWriter channelStateWriter;

    private int[] sequenceNumbers;

    private List<BufferOrEvent> output;

    @BeforeEach
    void setUp() {
        channelStateWriter = new RecordingChannelStateWriter();
    }

    @AfterEach
    void ensureEmpty() throws Exception {
        if (inputGate != null) {
            assertThat(inputGate.pollNext()).isNotPresent();
            assertThat(inputGate.isFinished()).isTrue();
            inputGate.close();
        }
        if (channelStateWriter != null) {
            channelStateWriter.close();
        }
    }

    private void verifyTriggeredCheckpoint(SingleCheckpointBarrierHandler handler, ValidatingCheckpointInvokable invokable, long currentCheckpointId) {
        assertThat(handler.isCheckpointPending()).isFalse();
        assertThat(handler.getLatestCheckpointId()).isEqualTo(currentCheckpointId);
        assertThat(invokable.getAbortedCheckpointId()).isEqualTo(currentCheckpointId);
    }

    private BufferOrEvent createBarrier(long checkpointId, int channel) {
        return createBarrier(checkpointId, channel, System.currentTimeMillis());
    }

    private BufferOrEvent createBarrier(long checkpointId, int channel, long timestamp) {
        sizeCounter++;
        return new BufferOrEvent(new CheckpointBarrier(checkpointId, timestamp, CheckpointOptions.unaligned(CheckpointType.CHECKPOINT, getDefault())), new InputChannelInfo(0, channel));
    }

    private BufferOrEvent createCancellationBarrier(long checkpointId, int channel) {
        sizeCounter++;
        return new BufferOrEvent(new CancelCheckpointMarker(checkpointId), new InputChannelInfo(0, channel));
    }

    private BufferOrEvent createBuffer(int channel, int size) {
        return new BufferOrEvent(TestBufferFactory.createBuffer(size), new InputChannelInfo(0, channel));
    }

    private BufferOrEvent createBuffer(int channel) {
        final int size = sizeCounter++;
        return new BufferOrEvent(TestBufferFactory.createBuffer(size), new InputChannelInfo(0, channel));
    }

    private static BufferOrEvent createEndOfPartition(int channel) {
        return new BufferOrEvent(EndOfPartitionEvent.INSTANCE, new InputChannelInfo(0, channel));
    }

    private CheckpointedInputGate createInputGate(int numberOfChannels, AbstractInvokable toNotify) throws IOException {
        return createInputGate(numberOfChannels, toNotify, true);
    }

    private CheckpointedInputGate createInputGate(int numberOfChannels, AbstractInvokable toNotify, boolean enableCheckpointsAfterTasksFinished) throws IOException {
        final NettyShuffleEnvironment environment = new NettyShuffleEnvironmentBuilder().build();
        SingleInputGate gate = new SingleInputGateBuilder().setNumberOfChannels(numberOfChannels).setupBufferPoolFactory(environment).build();
        gate.setInputChannels(IntStream.range(0, numberOfChannels).mapToObj(channelIndex -> InputChannelBuilder.newBuilder().setChannelIndex(channelIndex).setStateWriter(channelStateWriter).setupFromNettyShuffleEnvironment(environment).setConnectionManager(new TestingConnectionManager()).buildRemoteChannel(gate)).toArray(RemoteInputChannel[]::new));
        sequenceNumbers = new int[numberOfChannels];
        gate.setup();
        gate.requestPartitions();
        return createCheckpointedInputGate(gate, toNotify, enableCheckpointsAfterTasksFinished);
    }

    private BufferOrEvent[] addSequence(CheckpointedInputGate inputGate, BufferOrEvent... sequence) throws Exception {
        output = new ArrayList<>();
        addSequence(inputGate, output, sequenceNumbers, sequence);
        sizeCounter = 1;
        return sequence;
    }

    static BufferOrEvent[] addSequence(CheckpointedInputGate inputGate, List<BufferOrEvent> output, int[] sequenceNumbers, BufferOrEvent... sequence) throws Exception {
        for (BufferOrEvent bufferOrEvent : sequence) {
            if (bufferOrEvent.isEvent()) {
                bufferOrEvent = new BufferOrEvent(EventSerializer.toBuffer(bufferOrEvent.getEvent(), bufferOrEvent.getEvent() instanceof CheckpointBarrier), bufferOrEvent.getChannelInfo(), bufferOrEvent.moreAvailable(), bufferOrEvent.morePriorityEvents());
            }
            ((RemoteInputChannel) inputGate.getChannel(bufferOrEvent.getChannelInfo().getInputChannelIdx())).onBuffer(bufferOrEvent.getBuffer(), sequenceNumbers[bufferOrEvent.getChannelInfo().getInputChannelIdx()]++, 0, 0);
            while (inputGate.pollNext().map(output::add).isPresent()) {
            }
        }
        return sequence;
    }

    private CheckpointedInputGate createCheckpointedInputGate(IndexedInputGate gate, AbstractInvokable toNotify) {
        return createCheckpointedInputGate(gate, toNotify, true);
    }

    private CheckpointedInputGate createCheckpointedInputGate(IndexedInputGate gate, AbstractInvokable toNotify, boolean enableCheckpointsAfterTasksFinished) {
        final SingleCheckpointBarrierHandler barrierHandler = SingleCheckpointBarrierHandler.createUnalignedCheckpointBarrierHandler(new TestSubtaskCheckpointCoordinator(channelStateWriter), "Test", toNotify, SystemClock.getInstance(), enableCheckpointsAfterTasksFinished, gate);
        return new CheckpointedInputGate(gate, barrierHandler, new SyncMailboxExecutor());
    }

    private void assertInflightData(BufferOrEvent... expected) {
        Collection<BufferOrEvent> andResetInflightData = getAndResetInflightData();
        assertThat(getIds(andResetInflightData)).as("Unexpected in-flight sequence: " + andResetInflightData).isEqualTo(getIds(Arrays.asList(expected)));
    }

    private Collection<BufferOrEvent> getAndResetInflightData() {
        final List<BufferOrEvent> inflightData = channelStateWriter.getAddedInput().entries().stream().map(entry -> new BufferOrEvent(entry.getValue(), entry.getKey())).collect(Collectors.toList());
        channelStateWriter.reset();
        return inflightData;
    }

    private void assertOutput(BufferOrEvent... expectedSequence) {
        assertThat(getIds(output)).as("Unexpected output sequence").isEqualTo(getIds(Arrays.asList(expectedSequence)));
    }

    private List<Object> getIds(Collection<BufferOrEvent> buffers) {
        return buffers.stream().filter(boe -> !boe.isEvent() || !(boe.getEvent() instanceof CheckpointBarrier || boe.getEvent() instanceof CancelCheckpointMarker)).map(boe -> boe.isBuffer() ? boe.getSize() - 1 : boe.getEvent()).collect(Collectors.toList());
    }

    private CheckpointBarrier buildCheckpointBarrier(long id) {
        return new CheckpointBarrier(id, 0, CheckpointOptions.unaligned(CheckpointType.CHECKPOINT, getDefault()));
    }

    static class ValidatingCheckpointHandler extends org.apache.flink.streaming.runtime.io.checkpointing.ValidatingCheckpointHandler {

        public ValidatingCheckpointHandler(long nextExpectedCheckpointId) {
            super(nextExpectedCheckpointId);
        }

        @Override
        public void abortCheckpointOnBarrier(long checkpointId, CheckpointException cause) {
            super.abortCheckpointOnBarrier(checkpointId, cause);
            nextExpectedCheckpointId = -1;
        }
    }

    static class ValidateAsyncFutureNotCompleted extends ValidatingCheckpointHandler {

        @Nullable
        private CheckpointedInputGate inputGate;

        public ValidateAsyncFutureNotCompleted(long nextExpectedCheckpointId) {
            super(nextExpectedCheckpointId);
        }

        @Override
        public void abortCheckpointOnBarrier(long checkpointId, CheckpointException cause) {
            super.abortCheckpointOnBarrier(checkpointId, cause);
            checkState(inputGate != null);
            assertThat(inputGate.getAllBarriersReceivedFuture(checkpointId)).isNotDone();
        }

        public void setInputGate(CheckpointedInputGate inputGate) {
            this.inputGate = inputGate;
        }
    }

    private static final class ValidatingCheckpointInvokable extends StreamTask {

        private long expectedCheckpointId;

        private int totalNumCheckpoints;

        private long abortedCheckpointId;

        ValidatingCheckpointInvokable() throws Exception {
            super(new DummyEnvironment("test", 1, 0));
        }

        @Override
        public void init() {
        }

        @Override
        protected void processInput(MailboxDefaultAction.Controller controller) {
        }

        @Override
        public void abortCheckpointOnBarrier(long checkpointId, CheckpointException cause) throws IOException {
            abortedCheckpointId = checkpointId;
        }

        public void triggerCheckpointOnBarrier(CheckpointMetaData checkpointMetaData, CheckpointOptions checkpointOptions, CheckpointMetricsBuilder checkpointMetrics) {
            expectedCheckpointId = checkpointMetaData.getCheckpointId();
            totalNumCheckpoints++;
        }

        long getTriggeredCheckpointId() {
            return expectedCheckpointId;
        }

        int getTotalTriggeredCheckpoints() {
            return totalNumCheckpoints;
        }

        long getAbortedCheckpointId() {
            return abortedCheckpointId;
        }
    }

    @Test
    void testSingleChannelNoBarriers_1() throws Exception {
        inputGate = createInputGate(1, new ValidatingCheckpointHandler(1));
        final BufferOrEvent[] sequence = addSequence(inputGate, createBuffer(0), createBuffer(0), createBuffer(0), createEndOfPartition(0));
        assertOutput(sequence);
    }

    @Test
    void testSingleChannelNoBarriers_2() throws Exception {
        assertInflightData();
    }

    @Test
    void testMultiChannelNoBarriers_1() throws Exception {
        inputGate = createInputGate(4, new ValidatingCheckpointHandler(1));
        final BufferOrEvent[] sequence = addSequence(inputGate, createBuffer(2), createBuffer(2), createBuffer(0), createBuffer(1), createBuffer(0), createEndOfPartition(0), createBuffer(3), createBuffer(1), createEndOfPartition(3), createBuffer(1), createEndOfPartition(1), createBuffer(2), createEndOfPartition(2));
        assertOutput(sequence);
    }

    @Test
    void testMultiChannelNoBarriers_2() throws Exception {
        assertInflightData();
    }

    @Test
    void testMultiChannelWithBarriers_17() throws Exception {
        assertInflightData();
    }

    @Test
    void testMultiChannelWithBarriers_1_testMerged_2() throws Exception {
        ValidatingCheckpointHandler handler = new ValidatingCheckpointHandler(1);
        inputGate = createInputGate(3, handler);
        BufferOrEvent[] sequence1 = addSequence(inputGate, createBuffer(0), createBuffer(2), createBuffer(0), createBarrier(1, 1), createBarrier(1, 2), createBuffer(2), createBuffer(1), createBuffer(0), createBarrier(1, 0));
        assertOutput(sequence1);
        assertInflightData(sequence1[7]);
        BufferOrEvent[] sequence2 = addSequence(inputGate, createBuffer(0), createBuffer(0), createBuffer(1), createBuffer(1), createBuffer(2), createBarrier(2, 0), createBarrier(2, 1), createBarrier(2, 2));
        assertOutput(sequence2);
        BufferOrEvent[] sequence3 = addSequence(inputGate, createBuffer(2), createBuffer(2), createBarrier(3, 2), createBuffer(2), createBuffer(2), createBarrier(3, 0), createBarrier(3, 1));
        assertOutput(sequence3);
        addSequence(inputGate, createBarrier(4, 1), createBarrier(4, 2), createBarrier(4, 0));
        BufferOrEvent[] sequence5 = addSequence(inputGate, createBuffer(0), createBuffer(2), createBuffer(0), createBarrier(5, 1), createBuffer(2), createBuffer(0), createBuffer(2), createBuffer(1), createBarrier(5, 2), createBuffer(1), createBuffer(0), createBuffer(2), createBuffer(1), createBarrier(5, 0));
        assertOutput(sequence5);
        assertInflightData(sequence5[4], sequence5[5], sequence5[6], sequence5[10]);
        BufferOrEvent[] sequence6 = addSequence(inputGate, createBuffer(0), createEndOfPartition(0), createEndOfPartition(1), createEndOfPartition(2));
        assertOutput(sequence6);
    }

    @Test
    void testMultiChannelWithBarriers_2() throws Exception {
        assertThat(channelStateWriter.getLastStartedCheckpointId()).isOne();
    }

    @Test
    void testMultiChannelWithBarriers_5() throws Exception {
        assertThat(channelStateWriter.getLastStartedCheckpointId()).isEqualTo(2L);
    }

    @Test
    void testMultiChannelWithBarriers_6() throws Exception {
        assertInflightData();
    }

    @Test
    void testMultiChannelWithBarriers_8() throws Exception {
        assertThat(channelStateWriter.getLastStartedCheckpointId()).isEqualTo(3L);
    }

    @Test
    void testMultiChannelWithBarriers_9() throws Exception {
        assertInflightData();
    }

    @Test
    void testMultiChannelWithBarriers_10() throws Exception {
        assertOutput();
    }

    @Test
    void testMultiChannelWithBarriers_11() throws Exception {
        assertThat(channelStateWriter.getLastStartedCheckpointId()).isEqualTo(4L);
    }

    @Test
    void testMultiChannelWithBarriers_12() throws Exception {
        assertInflightData();
    }

    @Test
    void testMultiChannelWithBarriers_14() throws Exception {
        assertThat(channelStateWriter.getLastStartedCheckpointId()).isEqualTo(5L);
    }

    @Test
    void testMultiChannelTrailingInflightData_1() throws Exception {
        ValidatingCheckpointHandler handler = new ValidatingCheckpointHandler(1);
        inputGate = createInputGate(3, handler, false);
        BufferOrEvent[] sequence = addSequence(inputGate, createBuffer(0), createBuffer(1), createBuffer(2), createBarrier(1, 1), createBarrier(1, 2), createBarrier(1, 0), createBuffer(2), createBuffer(1), createBuffer(0), createBarrier(2, 1), createBuffer(1), createBuffer(1), createEndOfPartition(1), createBuffer(0), createBuffer(2), createBarrier(2, 2), createBuffer(2), createEndOfPartition(2), createBuffer(0), createEndOfPartition(0));
        assertOutput(sequence);
    }

    @Test
    void testMultiChannelTrailingInflightData_2() throws Exception {
        assertThat(channelStateWriter.getLastStartedCheckpointId()).isEqualTo(2L);
    }

    @Test
    void testMultiChannelTrailingInflightData_3() throws Exception {
        assertInflightData();
    }

    @Test
    void testMissingCancellationBarriers_1_testMerged_1() throws Exception {
        ValidatingCheckpointHandler handler = new ValidatingCheckpointHandler(1);
        inputGate = createInputGate(2, handler);
        final BufferOrEvent[] sequence = addSequence(inputGate, createBarrier(1L, 0), createCancellationBarrier(2L, 0), createCancellationBarrier(3L, 0), createCancellationBarrier(3L, 1), createBuffer(0), createEndOfPartition(0), createEndOfPartition(1));
        assertOutput(sequence);
        assertThat(handler.getLastCanceledCheckpointId()).isEqualTo(3L);
    }

    @Test
    void testMissingCancellationBarriers_2() throws Exception {
        assertThat(channelStateWriter.getLastStartedCheckpointId()).isOne();
    }

    @Test
    void testMissingCancellationBarriers_4() throws Exception {
        assertInflightData();
    }

    @Test
    void testEarlyCleanup_1_testMerged_1() throws Exception {
        ValidatingCheckpointHandler handler = new ValidatingCheckpointHandler(1);
        inputGate = createInputGate(3, handler, false);
        final BufferOrEvent[] sequence1 = addSequence(inputGate, createBuffer(0), createBuffer(1), createBuffer(2), createBarrier(1, 1), createBarrier(1, 2), createBarrier(1, 0));
        assertOutput(sequence1);
        final BufferOrEvent[] sequence2 = addSequence(inputGate, createBuffer(2), createBuffer(1), createBuffer(0), createBarrier(2, 1), createBuffer(1), createBuffer(1), createEndOfPartition(1), createBuffer(0), createBuffer(2), createBarrier(2, 2), createBuffer(2), createEndOfPartition(2), createBuffer(0), createEndOfPartition(0));
        assertOutput(sequence2);
    }

    @Test
    void testEarlyCleanup_2() throws Exception {
        assertThat(channelStateWriter.getLastStartedCheckpointId()).isOne();
    }

    @Test
    void testEarlyCleanup_3() throws Exception {
        assertInflightData();
    }

    @Test
    void testEarlyCleanup_5() throws Exception {
        assertThat(channelStateWriter.getLastStartedCheckpointId()).isEqualTo(2L);
    }

    @Test
    void testEarlyCleanup_6() throws Exception {
        assertInflightData();
    }

    @Test
    void testStartAlignmentWithClosedChannels_1_testMerged_1() throws Exception {
        ValidatingCheckpointHandler handler = new ValidatingCheckpointHandler(2);
        inputGate = createInputGate(4, handler);
        final BufferOrEvent[] sequence1 = addSequence(inputGate, createEndOfPartition(2), createEndOfPartition(1), createBuffer(0), createBuffer(0), createBuffer(3), createBarrier(2, 3), createBarrier(2, 0));
        assertOutput(sequence1);
        final BufferOrEvent[] sequence2 = addSequence(inputGate, createBuffer(3), createBuffer(0), createBarrier(3, 3), createBuffer(3), createBuffer(0), createBarrier(3, 0));
        assertOutput(sequence2);
        assertInflightData(sequence2[4]);
        final BufferOrEvent[] sequence3 = addSequence(inputGate, createBarrier(4, 0), createBarrier(4, 3));
        assertOutput(sequence3);
        final BufferOrEvent[] sequence4 = addSequence(inputGate, createBuffer(0), createBuffer(0), createBuffer(3), createEndOfPartition(0));
        assertOutput(sequence4);
        final BufferOrEvent[] sequence5 = addSequence(inputGate, createBuffer(3), createBarrier(5, 3), createBuffer(3), createEndOfPartition(3));
        assertOutput(sequence5);
    }

    @Test
    void testStartAlignmentWithClosedChannels_2() throws Exception {
        assertThat(channelStateWriter.getLastStartedCheckpointId()).isEqualTo(2L);
    }

    @Test
    void testStartAlignmentWithClosedChannels_3() throws Exception {
        assertInflightData();
    }

    @Test
    void testStartAlignmentWithClosedChannels_5() throws Exception {
        assertThat(channelStateWriter.getLastStartedCheckpointId()).isEqualTo(3L);
    }

    @Test
    void testStartAlignmentWithClosedChannels_8() throws Exception {
        assertThat(channelStateWriter.getLastStartedCheckpointId()).isEqualTo(4L);
    }

    @Test
    void testStartAlignmentWithClosedChannels_9() throws Exception {
        assertInflightData();
    }

    @Test
    void testStartAlignmentWithClosedChannels_11() throws Exception {
        assertThat(channelStateWriter.getLastStartedCheckpointId()).isEqualTo(-1L);
    }

    @Test
    void testStartAlignmentWithClosedChannels_12() throws Exception {
        assertInflightData();
    }

    @Test
    void testStartAlignmentWithClosedChannels_14() throws Exception {
        assertThat(channelStateWriter.getLastStartedCheckpointId()).isEqualTo(5L);
    }

    @Test
    void testStartAlignmentWithClosedChannels_15() throws Exception {
        assertInflightData();
    }

    @Test
    void testEndOfStreamWhileCheckpoint_1_testMerged_1() throws Exception {
        ValidatingCheckpointHandler handler = new ValidatingCheckpointHandler(1);
        inputGate = createInputGate(3, handler);
        final BufferOrEvent[] sequence1 = addSequence(inputGate, createBarrier(1, 0), createBarrier(1, 1), createBarrier(1, 2));
        assertOutput(sequence1);
        final BufferOrEvent[] sequence2 = addSequence(inputGate, createBuffer(0), createBuffer(0), createBuffer(2), createBarrier(2, 2), createBarrier(2, 0), createBuffer(0), createBuffer(2), createBuffer(1), createEndOfPartition(2), createEndOfPartition(1), createBuffer(0), createEndOfPartition(0));
        assertOutput(sequence2);
        assertInflightData(sequence2[7]);
    }

    @Test
    void testEndOfStreamWhileCheckpoint_2() throws Exception {
        assertThat(channelStateWriter.getLastStartedCheckpointId()).isOne();
    }

    @Test
    void testEndOfStreamWhileCheckpoint_3() throws Exception {
        assertInflightData();
    }

    @Test
    void testEndOfStreamWhileCheckpoint_5() throws Exception {
        assertThat(channelStateWriter.getLastStartedCheckpointId()).isEqualTo(2L);
    }

    @Test
    void testSingleChannelAbortCheckpoint_1_testMerged_1() throws Exception {
        ValidatingCheckpointHandler handler = new ValidatingCheckpointHandler(1);
        inputGate = createInputGate(1, handler);
        final BufferOrEvent[] sequence1 = addSequence(inputGate, createBuffer(0), createBarrier(1, 0), createBuffer(0), createBarrier(2, 0), createCancellationBarrier(4, 0));
        assertOutput(sequence1);
        assertThat(handler.getLastCanceledCheckpointId()).isEqualTo(4L);
        final BufferOrEvent[] sequence2 = addSequence(inputGate, createBarrier(5, 0), createBuffer(0), createCancellationBarrier(6, 0), createBuffer(0), createEndOfPartition(0));
        assertOutput(sequence2);
        assertThat(handler.getLastCanceledCheckpointId()).isEqualTo(6L);
    }

    @Test
    void testSingleChannelAbortCheckpoint_2() throws Exception {
        assertThat(channelStateWriter.getLastStartedCheckpointId()).isEqualTo(2L);
    }

    @Test
    void testSingleChannelAbortCheckpoint_4() throws Exception {
        assertInflightData();
    }

    @Test
    void testSingleChannelAbortCheckpoint_6() throws Exception {
        assertThat(channelStateWriter.getLastStartedCheckpointId()).isEqualTo(5L);
    }

    @Test
    void testSingleChannelAbortCheckpoint_8() throws Exception {
        assertInflightData();
    }

    @Test
    void testMultiChannelAbortCheckpoint_17() throws Exception {
        assertInflightData();
    }

    @Test
    void testMultiChannelAbortCheckpoint_1_testMerged_2() throws Exception {
        ValidatingCheckpointHandler handler = new ValidatingCheckpointHandler(1);
        inputGate = createInputGate(3, handler);
        final BufferOrEvent[] sequence1 = addSequence(inputGate, createBuffer(0), createBuffer(2), createBuffer(0), createBarrier(1, 1), createBarrier(1, 2), createBuffer(2), createBuffer(1), createBarrier(1, 0), createBuffer(0), createBuffer(2));
        assertOutput(sequence1);
        final BufferOrEvent[] sequence2 = addSequence(inputGate, createBarrier(2, 0), createBarrier(2, 2), createBuffer(0), createBuffer(2), createCancellationBarrier(2, 1));
        assertOutput(sequence2);
        assertThat(handler.getLastCanceledCheckpointId()).isEqualTo(2L);
        final BufferOrEvent[] sequence3 = addSequence(inputGate, createBuffer(2), createBuffer(1), createBarrier(3, 1), createBarrier(3, 2), createBarrier(3, 0));
        assertOutput(sequence3);
        final BufferOrEvent[] sequence4 = addSequence(inputGate, createBuffer(0), createBuffer(1), createCancellationBarrier(4, 1), createBarrier(4, 2), createBuffer(0), createBarrier(4, 0));
        assertOutput(sequence4);
        assertThat(handler.getLastCanceledCheckpointId()).isEqualTo(4L);
        final BufferOrEvent[] sequence5 = addSequence(inputGate, createBuffer(0), createBuffer(1), createBuffer(2), createBarrier(5, 2), createBarrier(5, 1), createBarrier(5, 0), createBuffer(0), createBuffer(1));
        assertOutput(sequence5);
        final BufferOrEvent[] sequence6 = addSequence(inputGate, createCancellationBarrier(6, 1), createCancellationBarrier(6, 2), createBarrier(6, 0), createBuffer(0), createEndOfPartition(0), createEndOfPartition(1), createEndOfPartition(2));
        assertOutput(sequence6);
        assertThat(handler.getLastCanceledCheckpointId()).isEqualTo(6L);
    }

    @Test
    void testMultiChannelAbortCheckpoint_2() throws Exception {
        assertThat(channelStateWriter.getLastStartedCheckpointId()).isOne();
    }

    @Test
    void testMultiChannelAbortCheckpoint_19() throws Exception {
        assertThat(channelStateWriter.getLastStartedCheckpointId()).isEqualTo(-1L);
    }

    @Test
    void testMultiChannelAbortCheckpoint_3() throws Exception {
        assertInflightData();
    }

    @Test
    void testMultiChannelAbortCheckpoint_21() throws Exception {
        assertInflightData();
    }

    @Test
    void testMultiChannelAbortCheckpoint_5() throws Exception {
        assertThat(channelStateWriter.getLastStartedCheckpointId()).isEqualTo(2L);
    }

    @Test
    void testMultiChannelAbortCheckpoint_7() throws Exception {
        assertInflightData();
    }

    @Test
    void testMultiChannelAbortCheckpoint_9() throws Exception {
        assertThat(channelStateWriter.getLastStartedCheckpointId()).isEqualTo(3L);
    }

    @Test
    void testMultiChannelAbortCheckpoint_10() throws Exception {
        assertInflightData();
    }

    @Test
    void testMultiChannelAbortCheckpoint_12() throws Exception {
        assertThat(channelStateWriter.getLastStartedCheckpointId()).isEqualTo(-1L);
    }

    @Test
    void testMultiChannelAbortCheckpoint_14() throws Exception {
        assertInflightData();
    }

    @Test
    void testMultiChannelAbortCheckpoint_16() throws Exception {
        assertThat(channelStateWriter.getLastStartedCheckpointId()).isEqualTo(5L);
    }

    @Test
    void testTriggerCheckpointsAfterReceivedEndOfPartition_1_testMerged_1() throws Exception {
        ValidatingCheckpointHandler validator = new ValidatingCheckpointHandler(-1);
        inputGate = createInputGate(3, validator);
        BufferOrEvent[] sequence1 = addSequence(inputGate, createEndOfPartition(0), createBarrier(3, 1), createBuffer(1), createBuffer(2), createEndOfPartition(1), createBarrier(3, 2));
        assertOutput(sequence1);
        assertInflightData(sequence1[3]);
        assertThat(validator.triggeredCheckpoints).containsExactly(3L);
        assertThat(validator.getAbortedCheckpointCounter()).isZero();
        BufferOrEvent[] sequence2 = addSequence(inputGate, createBuffer(2), createBarrier(4, 2), createEndOfPartition(2));
        assertOutput(sequence2);
        assertThat(validator.triggeredCheckpoints).containsExactly(3L, 4L);
    }

    @Test
    void testTriggerCheckpointsAfterReceivedEndOfPartition_6() throws Exception {
        assertInflightData();
    }
}
