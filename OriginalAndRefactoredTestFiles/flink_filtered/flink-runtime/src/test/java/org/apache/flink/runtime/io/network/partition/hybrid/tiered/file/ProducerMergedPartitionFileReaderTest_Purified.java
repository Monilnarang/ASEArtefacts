package org.apache.flink.runtime.io.network.partition.hybrid.tiered.file;

import org.apache.flink.core.memory.MemorySegment;
import org.apache.flink.core.memory.MemorySegmentFactory;
import org.apache.flink.runtime.io.network.buffer.Buffer;
import org.apache.flink.runtime.io.network.buffer.CompositeBuffer;
import org.apache.flink.runtime.io.network.buffer.FreeingBufferRecycler;
import org.apache.flink.runtime.io.network.partition.ResultPartitionID;
import org.apache.flink.runtime.io.network.partition.hybrid.tiered.common.TieredStorageIdMappingUtils;
import org.apache.flink.runtime.io.network.partition.hybrid.tiered.common.TieredStoragePartitionId;
import org.apache.flink.runtime.io.network.partition.hybrid.tiered.common.TieredStorageSubpartitionId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ExecutionException;
import static org.apache.flink.runtime.io.network.partition.BufferReaderWriterUtil.HEADER_LENGTH;
import static org.apache.flink.runtime.io.network.partition.hybrid.tiered.common.TieredStorageTestUtils.generateBuffersToWrite;
import static org.assertj.core.api.Assertions.assertThat;

class ProducerMergedPartitionFileReaderTest_Purified {

    private static final int DEFAULT_NUM_SUBPARTITION = 1;

    private static final int DEFAULT_SEGMENT_NUM = 1;

    private static final int DEFAULT_SEGMENT_ID = 0;

    private static final int DEFAULT_BUFFER_NUMBER = 5;

    private static final int DEFAULT_BUFFER_SIZE = 10;

    private static final String DEFAULT_TEST_FILE_NAME = "testFile";

    private static final String DEFAULT_TEST_INDEX_NAME = "testIndex";

    private static final TieredStoragePartitionId DEFAULT_PARTITION_ID = TieredStorageIdMappingUtils.convertId(new ResultPartitionID());

    private static final TieredStorageSubpartitionId DEFAULT_SUBPARTITION_ID = new TieredStorageSubpartitionId(0);

    @TempDir
    private Path tempFolder;

    private Path testFilePath;

    private ProducerMergedPartitionFileReader partitionFileReader;

    @BeforeEach
    void before() throws ExecutionException, InterruptedException {
        Path testIndexPath = new File(tempFolder.toFile(), DEFAULT_TEST_INDEX_NAME).toPath();
        ProducerMergedPartitionFileIndex partitionFileIndex = new ProducerMergedPartitionFileIndex(DEFAULT_NUM_SUBPARTITION, testIndexPath, 256, Long.MAX_VALUE);
        testFilePath = new File(tempFolder.toFile(), DEFAULT_TEST_FILE_NAME).toPath();
        ProducerMergedPartitionFileWriter partitionFileWriter = new ProducerMergedPartitionFileWriter(testFilePath, partitionFileIndex);
        List<PartitionFileWriter.SubpartitionBufferContext> subpartitionBuffers = generateBuffersToWrite(DEFAULT_NUM_SUBPARTITION, DEFAULT_SEGMENT_NUM, DEFAULT_BUFFER_NUMBER, DEFAULT_BUFFER_SIZE);
        partitionFileWriter.write(DEFAULT_PARTITION_ID, subpartitionBuffers).get();
        partitionFileReader = new ProducerMergedPartitionFileReader(testFilePath, partitionFileIndex);
    }

    private List<Buffer> readBuffer(int bufferIndex, TieredStorageSubpartitionId subpartitionId) throws IOException {
        return readBuffer(bufferIndex, subpartitionId, null, null).getReadBuffers();
    }

    private PartitionFileReader.ReadBufferResult readBuffer(int bufferIndex, TieredStorageSubpartitionId subpartitionId, PartitionFileReader.ReadProgress readProgress, CompositeBuffer partialBuffer) throws IOException {
        MemorySegment memorySegment = MemorySegmentFactory.allocateUnpooledSegment(DEFAULT_BUFFER_SIZE);
        return partitionFileReader.readBuffer(DEFAULT_PARTITION_ID, subpartitionId, DEFAULT_SEGMENT_ID, bufferIndex, memorySegment, FreeingBufferRecycler.INSTANCE, readProgress, partialBuffer);
    }

    @Test
    void testRelease_1() {
        assertThat(testFilePath.toFile().exists()).isTrue();
    }

    @Test
    void testRelease_2() {
        partitionFileReader.release();
        assertThat(testFilePath.toFile().exists()).isFalse();
    }
}
