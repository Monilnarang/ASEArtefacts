package org.apache.flink.runtime.io.network.buffer;

import org.apache.flink.core.testutils.CheckedThread;
import org.apache.flink.shaded.netty4.io.netty.buffer.ByteBuf;
import org.apache.flink.shaded.netty4.io.netty.buffer.ByteBufUtil;
import org.apache.flink.shaded.netty4.io.netty.util.ByteProcessor;
import org.apache.flink.shaded.netty4.io.netty.util.CharsetUtil;
import org.apache.flink.shaded.netty4.io.netty.util.IllegalReferenceCountException;
import org.apache.flink.shaded.netty4.io.netty.util.internal.PlatformDependent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.ReadOnlyBufferException;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import static org.apache.flink.shaded.netty4.io.netty.buffer.Unpooled.LITTLE_ENDIAN;
import static org.apache.flink.shaded.netty4.io.netty.buffer.Unpooled.buffer;
import static org.apache.flink.shaded.netty4.io.netty.buffer.Unpooled.copiedBuffer;
import static org.apache.flink.shaded.netty4.io.netty.buffer.Unpooled.directBuffer;
import static org.apache.flink.shaded.netty4.io.netty.buffer.Unpooled.unreleasableBuffer;
import static org.apache.flink.shaded.netty4.io.netty.buffer.Unpooled.wrappedBuffer;
import static org.apache.flink.shaded.netty4.io.netty.util.internal.EmptyArrays.EMPTY_BYTES;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.within;
import static org.assertj.core.api.Assumptions.assumeThat;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

abstract class AbstractByteBufTest_Parameterized {

    private static final int CAPACITY = 4096;

    private static final int BLOCK_SIZE = 128;

    private static final int JAVA_BYTEBUFFER_CONSISTENCY_ITERATIONS = 100;

    private long seed;

    private Random random;

    private ByteBuf buffer;

    protected final ByteBuf newBuffer(int capacity) {
        return newBuffer(capacity, Integer.MAX_VALUE);
    }

    protected abstract ByteBuf newBuffer(int capacity, int maxCapacity);

    protected boolean discardReadBytesDoesNotMoveWritableBytes() {
        return true;
    }

    @BeforeEach
    void init() {
        buffer = newBuffer(CAPACITY);
        seed = System.currentTimeMillis();
        random = new Random(seed);
    }

    @AfterEach
    void dispose() {
        if (buffer != null) {
            assertThat(buffer.release()).isTrue();
            assertThat(buffer.refCnt()).isZero();
            try {
                buffer.release();
            } catch (Exception e) {
            }
            buffer = null;
        }
    }

    private static void assertRemainingEquals(ByteBuffer expected, ByteBuffer actual) {
        int remaining = expected.remaining();
        int remaining2 = actual.remaining();
        assumeThat(remaining2).isEqualTo(remaining);
        byte[] array1 = new byte[remaining];
        byte[] array2 = new byte[remaining2];
        expected.get(array1);
        actual.get(array2);
        assertThat(array2).isEqualTo(array1);
    }

    private void ensureWritableDoesNotThrow(boolean force) {
        final ByteBuf buffer = newBuffer(8);
        buffer.writerIndex(buffer.capacity());
        buffer.ensureWritable(8, force);
        buffer.release();
    }

    private ByteBuf releasedBuffer() {
        ByteBuf buffer = newBuffer(8);
        buffer.clear();
        assertThat(buffer.release()).isTrue();
        return buffer;
    }

    private static void assertSliceFailAfterRelease(ByteBuf... bufs) {
        for (ByteBuf buf : bufs) {
            if (buf.refCnt() > 0) {
                buf.release();
            }
        }
        for (ByteBuf buf : bufs) {
            assertThat(buf.refCnt()).isZero();
            assertThatThrownBy(buf::slice).isInstanceOf(IllegalReferenceCountException.class);
        }
    }

    private static void assertRetainedSliceFailAfterRelease(ByteBuf... bufs) {
        for (ByteBuf buf : bufs) {
            if (buf.refCnt() > 0) {
                buf.release();
            }
        }
        for (ByteBuf buf : bufs) {
            assertThat(buf.refCnt()).isZero();
            assertThatThrownBy(buf::retainedSlice).isInstanceOf(IllegalReferenceCountException.class);
        }
    }

    private static void assertDuplicateFailAfterRelease(ByteBuf... bufs) {
        for (ByteBuf buf : bufs) {
            if (buf.refCnt() > 0) {
                buf.release();
            }
        }
        for (ByteBuf buf : bufs) {
            assertThat(buf.refCnt()).isZero();
            assertThatThrownBy(buf::duplicate).isInstanceOf(IllegalReferenceCountException.class);
        }
    }

    private static void assertRetainedDuplicateFailAfterRelease(ByteBuf... bufs) {
        for (ByteBuf buf : bufs) {
            if (buf.refCnt() > 0) {
                buf.release();
            }
        }
        for (ByteBuf buf : bufs) {
            assertThat(buf.refCnt()).isZero();
            assertThatThrownBy(buf::retainedDuplicate).isInstanceOf(IllegalReferenceCountException.class);
        }
    }

    private static final CharBuffer EXTENDED_ASCII_CHARS, ASCII_CHARS;

    static {
        char[] chars = new char[256];
        for (char c = 0; c < chars.length; c++) {
            chars[c] = c;
        }
        EXTENDED_ASCII_CHARS = CharBuffer.wrap(chars);
        ASCII_CHARS = CharBuffer.wrap(chars, 0, 128);
    }

    static final class TestGatheringByteChannel implements GatheringByteChannel {

        private final ByteArrayOutputStream out = new ByteArrayOutputStream();

        private final WritableByteChannel channel = Channels.newChannel(out);

        private final int limit;

        TestGatheringByteChannel(int limit) {
            this.limit = limit;
        }

        TestGatheringByteChannel() {
            this(Integer.MAX_VALUE);
        }

        @Override
        public long write(ByteBuffer[] srcs, int offset, int length) throws IOException {
            long written = 0;
            for (; offset < length; offset++) {
                written += write(srcs[offset]);
                if (written >= limit) {
                    break;
                }
            }
            return written;
        }

        @Override
        public long write(ByteBuffer[] srcs) throws IOException {
            return write(srcs, 0, srcs.length);
        }

        @Override
        public int write(ByteBuffer src) throws IOException {
            int oldLimit = src.limit();
            if (limit < src.remaining()) {
                src.limit(src.position() + limit);
            }
            int w = channel.write(src);
            src.limit(oldLimit);
            return w;
        }

        @Override
        public boolean isOpen() {
            return channel.isOpen();
        }

        @Override
        public void close() throws IOException {
            channel.close();
        }

        public byte[] writtenBytes() {
            return out.toByteArray();
        }
    }

    private static final class DevNullGatheringByteChannel implements GatheringByteChannel {

        @Override
        public long write(ByteBuffer[] srcs, int offset, int length) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long write(ByteBuffer[] srcs) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int write(ByteBuffer src) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean isOpen() {
            return false;
        }

        @Override
        public void close() {
            throw new UnsupportedOperationException();
        }
    }

    private static final class TestScatteringByteChannel implements ScatteringByteChannel {

        @Override
        public long read(ByteBuffer[] dsts, int offset, int length) {
            throw new UnsupportedOperationException();
        }

        @Override
        public long read(ByteBuffer[] dsts) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int read(ByteBuffer dst) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean isOpen() {
            return false;
        }

        @Override
        public void close() {
            throw new UnsupportedOperationException();
        }
    }

    private static final class TestByteProcessor implements ByteProcessor {

        @Override
        public boolean process(byte value) {
            return true;
        }
    }

    @Test
    void initialState_1() {
        assertThat(buffer.capacity()).isEqualTo(CAPACITY);
    }

    @Test
    void initialState_2() {
        assertThat(buffer.readerIndex()).isZero();
    }

    @Test
    void testSliceEndianness_1() {
        assertThat(buffer.slice(0, buffer.capacity()).order()).isEqualTo(buffer.order());
    }

    @Test
    void testSliceIndex_1() {
        assertThat(buffer.slice(0, buffer.capacity()).readerIndex()).isZero();
    }

    @Test
    void testSliceIndex_5() {
        assertThat(buffer.slice(0, buffer.capacity()).writerIndex()).isEqualTo(buffer.capacity());
    }

    @ParameterizedTest
    @MethodSource("Provider_testSliceEndianness_2to4")
    void testSliceEndianness_2to4(int param1, int param2) {
        assertThat(buffer.slice(param1, buffer.capacity() - param2).order()).isEqualTo(buffer.order());
    }

    static public Stream<Arguments> Provider_testSliceEndianness_2to4() {
        return Stream.of(arguments(0, 1), arguments(1, 1), arguments(1, 2));
    }

    @ParameterizedTest
    @MethodSource("Provider_testSliceIndex_2to4")
    void testSliceIndex_2to4(int param1, int param2) {
        assertThat(buffer.slice(param1, buffer.capacity() - param2).readerIndex()).isZero();
    }

    static public Stream<Arguments> Provider_testSliceIndex_2to4() {
        return Stream.of(arguments(0, 1), arguments(1, 1), arguments(1, 2));
    }

    @ParameterizedTest
    @MethodSource("Provider_testSliceIndex_6to8")
    void testSliceIndex_6to8(int param1, int param2, int param3) {
        assertThat(buffer.slice(param2, buffer.capacity() - param3).writerIndex()).isEqualTo(buffer.capacity() - param1);
    }

    static public Stream<Arguments> Provider_testSliceIndex_6to8() {
        return Stream.of(arguments(1, 0, 1), arguments(1, 1, 1), arguments(2, 1, 2));
    }
}
