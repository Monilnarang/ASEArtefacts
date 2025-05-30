package org.apache.flink.runtime.io.network.partition.hybrid.tiered.netty;

import org.apache.flink.core.memory.MemorySegmentFactory;
import org.apache.flink.runtime.io.network.buffer.BufferBuilderTestUtils;
import org.apache.flink.runtime.io.network.buffer.FreeingBufferRecycler;
import org.apache.flink.runtime.io.network.buffer.NetworkBuffer;
import org.apache.flink.runtime.io.network.partition.BufferAvailabilityListener;
import org.apache.flink.runtime.io.network.partition.ResultSubpartition.BufferAndBacklog;
import org.apache.flink.runtime.io.network.partition.ResultSubpartitionView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import static org.apache.flink.runtime.io.network.buffer.Buffer.DataType.END_OF_SEGMENT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TieredStorageResultSubpartitionViewTest_Purified {

    private static final int TIER_NUMBER = 2;

    private CompletableFuture<Void> availabilityListener;

    private List<NettyPayloadManager> nettyPayloadManagers;

    private List<CompletableFuture<NettyConnectionId>> connectionBrokenConsumers;

    private TieredStorageResultSubpartitionView tieredStorageResultSubpartitionView;

    @BeforeEach
    void before() {
        availabilityListener = new CompletableFuture<>();
        nettyPayloadManagers = createNettyPayloadManagers();
        connectionBrokenConsumers = Arrays.asList(new CompletableFuture<>(), new CompletableFuture<>());
        tieredStorageResultSubpartitionView = new TieredStorageResultSubpartitionView(createBufferAvailabilityListener(availabilityListener), nettyPayloadManagers, createNettyConnectionIds(), createNettyServiceProducers(connectionBrokenConsumers));
    }

    private static void checkBufferAndBacklog(BufferAndBacklog bufferAndBacklog, int backlog) {
        assertThat(bufferAndBacklog).isNotNull();
        assertThat(bufferAndBacklog.buffer()).isNotNull();
        assertThat(bufferAndBacklog.buffersInBacklog()).isEqualTo(backlog);
    }

    private static BufferAvailabilityListener createBufferAvailabilityListener(CompletableFuture<Void> notifier) {
        return (ResultSubpartitionView view) -> notifier.complete(null);
    }

    private static List<NettyPayloadManager> createNettyPayloadManagers() {
        List<NettyPayloadManager> nettyPayloadManagers = new ArrayList<>();
        for (int index = 0; index < TIER_NUMBER; ++index) {
            NettyPayloadManager nettyPayloadManager = new NettyPayloadManager();
            nettyPayloadManager.add(NettyPayload.newSegment(index));
            nettyPayloadManager.add(NettyPayload.newBuffer(BufferBuilderTestUtils.buildSomeBuffer(0), 0, index));
            nettyPayloadManager.add(NettyPayload.newBuffer(new NetworkBuffer(MemorySegmentFactory.allocateUnpooledSegment(0), FreeingBufferRecycler.INSTANCE, END_OF_SEGMENT), 1, index));
            nettyPayloadManagers.add(nettyPayloadManager);
        }
        return nettyPayloadManagers;
    }

    private static List<NettyPayloadManager> createNettyPayloadQueuesWithError(Throwable error) {
        List<NettyPayloadManager> nettyPayloadManagers = new ArrayList<>();
        for (int index = 0; index < TIER_NUMBER; ++index) {
            NettyPayloadManager queue = new NettyPayloadManager();
            queue.add(NettyPayload.newSegment(index));
            queue.add(NettyPayload.newError(error));
            nettyPayloadManagers.add(queue);
        }
        return nettyPayloadManagers;
    }

    private static List<NettyConnectionId> createNettyConnectionIds() {
        List<NettyConnectionId> nettyConnectionIds = new ArrayList<>();
        for (int index = 0; index < TIER_NUMBER; ++index) {
            nettyConnectionIds.add(NettyConnectionId.newId());
        }
        return nettyConnectionIds;
    }

    private static List<NettyServiceProducer> createNettyServiceProducers(List<CompletableFuture<NettyConnectionId>> connectionBrokenConsumers) {
        List<NettyServiceProducer> nettyServiceProducers = new ArrayList<>();
        for (int index = 0; index < connectionBrokenConsumers.size(); ++index) {
            int indexNumber = index;
            nettyServiceProducers.add(new TestingNettyServiceProducer.Builder().setConnectionBrokenConsumer(connectionId -> connectionBrokenConsumers.get(indexNumber).complete(connectionId)).build());
        }
        return nettyServiceProducers;
    }

    @Test
    void testGetNumberOfQueuedBuffers_1() {
        assertThat(tieredStorageResultSubpartitionView.getNumberOfQueuedBuffers()).isEqualTo(1);
    }

    @Test
    void testGetNumberOfQueuedBuffers_2() {
        assertThat(tieredStorageResultSubpartitionView.unsynchronizedGetNumberOfQueuedBuffers()).isEqualTo(1);
    }
}
