package org.apache.flink.runtime.io.network.partition.hybrid.tiered.file;

import org.apache.flink.core.fs.Path;
import org.apache.flink.core.memory.MemorySegment;
import org.apache.flink.core.memory.MemorySegmentFactory;
import org.apache.flink.runtime.io.network.buffer.Buffer;
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
import java.util.List;
import static org.apache.flink.runtime.io.network.partition.hybrid.tiered.common.TieredStorageTestUtils.generateBuffersToWrite;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class SegmentPartitionFileReaderTest_Parameterized {

    private static final TieredStoragePartitionId DEFAULT_PARTITION_ID = TieredStorageIdMappingUtils.convertId(new ResultPartitionID());

    private static final TieredStorageSubpartitionId DEFAULT_SUBPARTITION_ID = new TieredStorageSubpartitionId(0);

    private static final int DEFAULT_NUM_SUBPARTITION = 2;

    private static final int DEFAULT_SEGMENT_NUM = 2;

    private static final int DEFAULT_BUFFER_PER_SEGMENT = 3;

    private static final int DEFAULT_BUFFER_SIZE = 1;

    @TempDir
    private File tempFolder;

    private SegmentPartitionFileReader partitionFileReader;

    @BeforeEach
    void before() {
        Path tieredStorageDir = Path.fromLocalFile(tempFolder);
        SegmentPartitionFileWriter partitionFileWriter = new SegmentPartitionFileWriter(tieredStorageDir.getPath(), DEFAULT_NUM_SUBPARTITION);
        List<PartitionFileWriter.SubpartitionBufferContext> subpartitionBuffers = generateBuffersToWrite(DEFAULT_NUM_SUBPARTITION, DEFAULT_SEGMENT_NUM, DEFAULT_BUFFER_PER_SEGMENT, DEFAULT_BUFFER_SIZE);
        partitionFileWriter.write(DEFAULT_PARTITION_ID, subpartitionBuffers);
        partitionFileWriter.release();
        partitionFileReader = new SegmentPartitionFileReader(tieredStorageDir.getPath());
    }

    private Buffer readBuffer(int bufferIndex, TieredStorageSubpartitionId subpartitionId, int segmentId) throws IOException {
        MemorySegment memorySegment = MemorySegmentFactory.allocateUnpooledSegment(DEFAULT_BUFFER_SIZE);
        PartitionFileReader.ReadBufferResult readBufferResult = partitionFileReader.readBuffer(DEFAULT_PARTITION_ID, subpartitionId, segmentId, bufferIndex, memorySegment, FreeingBufferRecycler.INSTANCE, null, null);
        if (readBufferResult == null) {
            return null;
        }
        return readBufferResult.getReadBuffers().get(0);
    }

    @Test
    void testGetPriority_2() throws IOException {
        assertThat(readBuffer(0, DEFAULT_SUBPARTITION_ID, 0)).isNotNull();
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetPriority_1_3")
    void testGetPriority_1_3(int param1, int param2, int param3) throws IOException {
        assertThat(partitionFileReader.getPriority(DEFAULT_PARTITION_ID, DEFAULT_SUBPARTITION_ID, param2, param3, null)).isEqualTo(-param1);
    }

    static public Stream<Arguments> Provider_testGetPriority_1_3() {
        return Stream.of(arguments(1, 0, 0), arguments(1, 0, 1));
    }
}
