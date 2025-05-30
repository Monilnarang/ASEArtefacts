package org.apache.flink.runtime.io.network.buffer;

import org.apache.flink.runtime.io.network.partition.PrioritizedDeque;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import java.nio.ByteBuffer;
import static java.util.Objects.requireNonNull;
import static org.apache.flink.runtime.io.network.buffer.BufferBuilderTestUtils.assertContent;
import static org.apache.flink.runtime.io.network.buffer.BufferBuilderTestUtils.createEmptyBufferBuilder;
import static org.apache.flink.runtime.io.network.buffer.BufferBuilderTestUtils.toByteBuffer;
import static org.assertj.core.api.Assertions.assertThat;

class BufferConsumerWithPartialRecordLengthTest_Purified {

    private static final int BUFFER_INT_SIZE = 4;

    private static final int BUFFER_SIZE = BUFFER_INT_SIZE * Integer.BYTES;

    private final PrioritizedDeque<BufferConsumerWithPartialRecordLength> buffers = new PrioritizedDeque<>();

    private BufferBuilder builder = null;

    @AfterEach
    void clear() {
        buffers.clear();
        builder = null;
    }

    private void writeToBuffer(ByteBuffer record) {
        if (builder == null) {
            builder = createEmptyBufferBuilder(BUFFER_SIZE);
            buffers.add(new BufferConsumerWithPartialRecordLength(builder.createBufferConsumerFromBeginning(), 0));
        }
        builder.appendAndCommit(record);
        while (record.hasRemaining()) {
            builder.finish();
            builder = createEmptyBufferBuilder(BUFFER_SIZE);
            final int partialRecordBytes = builder.appendAndCommit(record);
            buffers.add(new BufferConsumerWithPartialRecordLength(builder.createBufferConsumerFromBeginning(), partialRecordBytes));
        }
        if (builder.isFull()) {
            builder.finish();
            builder = null;
        }
    }

    @Test
    void partialRecordTestCase_1() {
        assertThat(buffers).hasSize(2);
    }

    @Test
    void partialRecordTestCase_2_testMerged_2() {
        BufferConsumerWithPartialRecordLength consumer1 = buffers.poll();
        assertThat(requireNonNull(consumer1).getPartialRecordLength()).isZero();
        assertThat(consumer1.cleanupPartialRecord()).isTrue();
        assertContent(consumer1.build(), FreeingBufferRecycler.INSTANCE, 0, 1, 2, 3);
        BufferConsumerWithPartialRecordLength consumer2 = buffers.poll();
        assertThat(requireNonNull(consumer2).cleanupPartialRecord()).isTrue();
        assertThat(consumer2.build().readableBytes()).isZero();
    }

    @Test
    void partialLongRecordSpanningBufferTestCase_1() {
        assertThat(buffers).hasSize(3);
    }

    @Test
    void partialLongRecordSpanningBufferTestCase_2_testMerged_2() {
        buffers.poll();
        BufferConsumerWithPartialRecordLength consumer2 = buffers.poll();
        assertThat(requireNonNull(consumer2).getPartialRecordLength()).isEqualTo(BUFFER_SIZE);
        assertThat(consumer2.cleanupPartialRecord()).isFalse();
        assertThat(consumer2.build().readableBytes()).isZero();
        BufferConsumerWithPartialRecordLength consumer3 = buffers.poll();
        assertThat(requireNonNull(consumer3).cleanupPartialRecord()).isTrue();
        assertContent(consumer3.build(), FreeingBufferRecycler.INSTANCE, 8, 9);
    }

    @Test
    void partialLongRecordEndsWithFullBufferTestCase_1() {
        assertThat(buffers).hasSize(3);
    }

    @Test
    void partialLongRecordEndsWithFullBufferTestCase_2_testMerged_2() {
        buffers.poll();
        BufferConsumerWithPartialRecordLength consumer2 = buffers.poll();
        assertThat(requireNonNull(consumer2).getPartialRecordLength()).isEqualTo(BUFFER_SIZE);
        assertThat(consumer2.cleanupPartialRecord()).isFalse();
        assertThat(consumer2.build().readableBytes()).isZero();
        BufferConsumerWithPartialRecordLength consumer3 = buffers.poll();
        assertThat(requireNonNull(consumer3).cleanupPartialRecord()).isTrue();
        assertContent(consumer3.build(), FreeingBufferRecycler.INSTANCE, 8, 9);
    }

    @Test
    void readPositionNotAtTheBeginningOfTheBufferTestCase_1() {
        assertThat(buffers).hasSize(2);
    }

    @Test
    void readPositionNotAtTheBeginningOfTheBufferTestCase_2_testMerged_2() {
        buffers.poll();
        BufferConsumerWithPartialRecordLength consumer2 = buffers.poll();
        requireNonNull(consumer2).build();
        assertThat(consumer2.getPartialRecordLength()).isEqualTo(4);
        assertThat(consumer2.cleanupPartialRecord()).isTrue();
        assertContent(consumer2.build(), FreeingBufferRecycler.INSTANCE, 8, 9);
    }
}
