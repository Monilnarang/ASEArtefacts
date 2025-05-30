package org.apache.flink.runtime.io.network.buffer;

import org.apache.flink.core.memory.MemorySegment;
import org.apache.flink.core.memory.MemorySegmentFactory;
import org.apache.flink.runtime.io.network.api.EndOfPartitionEvent;
import org.apache.flink.runtime.io.network.api.serialization.EventSerializer;
import org.apache.flink.runtime.io.network.netty.NettyBufferPool;
import org.apache.flink.shaded.netty4.io.netty.buffer.ByteBuf;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.ByteBuffer;
import static org.assertj.core.api.Assertions.assertThat;

class ReadOnlySlicedBufferTest_Purified {

    private static final int BUFFER_SIZE = 1024;

    private static final int DATA_SIZE = 10;

    private NetworkBuffer buffer;

    @BeforeEach
    void setUp() throws Exception {
        final MemorySegment segment = MemorySegmentFactory.allocateUnpooledSegment(BUFFER_SIZE);
        buffer = new NetworkBuffer(segment, FreeingBufferRecycler.INSTANCE, Buffer.DataType.DATA_BUFFER, 0);
        for (int i = 0; i < DATA_SIZE; ++i) {
            buffer.writeByte(i);
        }
    }

    private static void assertReadableBytes(Buffer actualBuffer, int... expectedBytes) {
        ByteBuffer actualBytesBuffer = actualBuffer.getNioBufferReadable();
        int[] actual = new int[actualBytesBuffer.limit()];
        for (int i = 0; i < actual.length; ++i) {
            actual[i] = actualBytesBuffer.get();
        }
        assertThat(actual).isEqualTo(expectedBytes);
        ByteBuf buffer = (ByteBuf) actualBuffer;
        for (int i = 0; i < buffer.readableBytes(); ++i) {
            actual[i] = buffer.getByte(buffer.readerIndex() + i);
        }
        assertThat(actual).isEqualTo(expectedBytes);
        for (int i = 0; i < buffer.readableBytes(); ++i) {
            actual[i] = buffer.readByte();
        }
        assertThat(actual).isEqualTo(expectedBytes);
    }

    @Test
    void testForwardsIsBuffer_1() throws IOException {
        assertThat(buffer.readOnlySlice().isBuffer()).isEqualTo(buffer.isBuffer());
    }

    @Test
    void testForwardsIsBuffer_2() throws IOException {
        assertThat(buffer.readOnlySlice(1, 2).isBuffer()).isEqualTo(buffer.isBuffer());
    }

    @Test
    void testForwardsIsBuffer_3_testMerged_3() throws IOException {
        Buffer eventBuffer = EventSerializer.toBuffer(EndOfPartitionEvent.INSTANCE, false);
        assertThat(eventBuffer.readOnlySlice().isBuffer()).isEqualTo(eventBuffer.isBuffer());
        assertThat(eventBuffer.readOnlySlice(1, 2).isBuffer()).isEqualTo(eventBuffer.isBuffer());
    }

    @Test
    void testForwardsGetMemorySegment_1() {
        assertThat(buffer.readOnlySlice().getMemorySegment()).isSameAs(buffer.getMemorySegment());
    }

    @Test
    void testForwardsGetMemorySegment_2() {
        assertThat(buffer.readOnlySlice(1, 2).getMemorySegment()).isSameAs(buffer.getMemorySegment());
    }

    @Test
    void testForwardsGetRecycler_1() {
        assertThat(buffer.readOnlySlice().getRecycler()).isSameAs(buffer.getRecycler());
    }

    @Test
    void testForwardsGetRecycler_2() {
        assertThat(buffer.readOnlySlice(1, 2).getRecycler()).isSameAs(buffer.getRecycler());
    }

    @Test
    void testGetMaxCapacity_1() {
        assertThat(buffer.readOnlySlice().getMaxCapacity()).isEqualTo(DATA_SIZE);
    }

    @Test
    void testGetMaxCapacity_2() {
        assertThat(buffer.readOnlySlice(1, 2).getMaxCapacity()).isEqualTo(2);
    }

    @Test
    void testReadableBytes_1() {
        assertThat(buffer.readOnlySlice().readableBytes()).isEqualTo(buffer.readableBytes());
    }

    @Test
    void testReadableBytes_2() {
        assertThat(buffer.readOnlySlice(1, 2).readableBytes()).isEqualTo(2);
    }
}
