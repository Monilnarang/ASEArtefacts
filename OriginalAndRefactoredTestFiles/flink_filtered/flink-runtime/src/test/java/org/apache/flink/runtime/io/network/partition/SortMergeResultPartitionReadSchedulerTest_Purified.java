package org.apache.flink.runtime.io.network.partition;

import org.apache.flink.core.memory.MemorySegment;
import org.apache.flink.core.memory.MemorySegmentFactory;
import org.apache.flink.runtime.concurrent.ManuallyTriggeredScheduledExecutorService;
import org.apache.flink.runtime.io.disk.BatchShuffleReadBufferPool;
import org.apache.flink.runtime.io.network.buffer.Buffer;
import org.apache.flink.runtime.io.network.buffer.CompositeBuffer;
import org.apache.flink.runtime.io.network.buffer.FullyFilledBuffer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.io.TempDir;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.Duration;
import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;
import static org.apache.flink.runtime.io.network.partition.PartitionedFileWriteReadTest.createAndConfigIndexEntryBuffer;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SortMergeResultPartitionReadSchedulerTest_Purified {

    private static final int bufferSize = 1024;

    private static final byte[] dataBytes = new byte[bufferSize];

    private static final int totalBytes = bufferSize * 2;

    private static final int numThreads = 4;

    private static final int numSubpartitions = 10;

    private static final int numBuffersPerSubpartition = 10;

    private PartitionedFile partitionedFile;

    private PartitionedFileReader fileReader;

    private FileChannel dataFileChannel;

    private FileChannel indexFileChannel;

    private BatchShuffleReadBufferPool bufferPool;

    private ExecutorService executor;

    private SortMergeResultPartitionReadScheduler readScheduler;

    @BeforeEach
    void before(@TempDir Path basePath) throws Exception {
        Random random = new Random();
        random.nextBytes(dataBytes);
        partitionedFile = PartitionTestUtils.createPartitionedFile(basePath.toString(), numSubpartitions, numBuffersPerSubpartition, bufferSize, dataBytes);
        dataFileChannel = openFileChannel(partitionedFile.getDataFilePath());
        indexFileChannel = openFileChannel(partitionedFile.getIndexFilePath());
        fileReader = new PartitionedFileReader(partitionedFile, new ResultSubpartitionIndexSet(0), dataFileChannel, indexFileChannel, BufferReaderWriterUtil.allocatedHeaderBuffer(), createAndConfigIndexEntryBuffer(), 0);
        bufferPool = new BatchShuffleReadBufferPool(totalBytes, bufferSize);
        executor = Executors.newFixedThreadPool(numThreads);
        readScheduler = new SortMergeResultPartitionReadScheduler(bufferPool, executor, new Object());
    }

    @AfterEach
    void after() throws Exception {
        dataFileChannel.close();
        indexFileChannel.close();
        partitionedFile.deleteQuietly();
        bufferPool.destroy();
        executor.shutdown();
    }

    private static class FakeBatchShuffleReadBufferPool extends BatchShuffleReadBufferPool {

        private final Queue<MemorySegment> requestedBuffers;

        FakeBatchShuffleReadBufferPool(long totalBytes, int bufferSize) throws Exception {
            super(totalBytes, bufferSize);
            this.requestedBuffers = new LinkedList<>(requestBuffers());
        }

        @Override
        public long getLastBufferOperationTimestamp() {
            recycle(requestedBuffers.poll());
            return super.getLastBufferOperationTimestamp();
        }

        @Override
        public void destroy() {
            recycle(requestedBuffers);
            requestedBuffers.clear();
            super.destroy();
        }
    }

    private static FileChannel openFileChannel(Path path) throws IOException {
        return FileChannel.open(path, StandardOpenOption.READ);
    }

    private void assertAllResourcesReleased() {
        assertThat(readScheduler.getDataFileChannel()).isNull();
        assertThat(readScheduler.getIndexFileChannel()).isNull();
        assertThat(readScheduler.isRunning()).isFalse();
        assertThat(readScheduler.getNumPendingReaders()).isZero();
        if (!bufferPool.isDestroyed()) {
            assertThat(bufferPool.getNumTotalBuffers()).isEqualTo(bufferPool.getAvailableBuffers());
        }
    }

    private void waitUntilReadFinish() throws Exception {
        while (readScheduler.isRunning()) {
            Thread.sleep(100);
        }
    }

    @Test
    void testOnReadBufferRequestError_1_testMerged_1() throws Exception {
        ManuallyTriggeredScheduledExecutorService schedulerExecutor = new ManuallyTriggeredScheduledExecutorService();
        readScheduler = new SortMergeResultPartitionReadScheduler(bufferPool, schedulerExecutor, new Object());
        SortMergeSubpartitionReader subpartitionReader = readScheduler.createSubpartitionReader(new NoOpBufferAvailablityListener(), new ResultSubpartitionIndexSet(0), partitionedFile, 0);
        assertThat(schedulerExecutor.numQueuedRunnables()).isEqualTo(1);
        assertThat(subpartitionReader.isReleased()).isTrue();
        assertThat(subpartitionReader.getFailureCause()).isNotNull();
        assertThat(subpartitionReader.getAvailabilityAndBacklog(false).isAvailable()).isTrue();
    }

    @Test
    void testOnReadBufferRequestError_5() throws Exception {
        assertAllResourcesReleased();
    }
}
