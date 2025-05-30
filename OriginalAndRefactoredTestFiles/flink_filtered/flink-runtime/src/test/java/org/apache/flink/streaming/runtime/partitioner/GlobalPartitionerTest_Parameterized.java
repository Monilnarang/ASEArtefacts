package org.apache.flink.streaming.runtime.partitioner;

import org.apache.flink.api.java.tuple.Tuple;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class GlobalPartitionerTest_Parameterized extends StreamPartitionerTest {

    @Override
    StreamPartitioner<Tuple> createPartitioner() {
        StreamPartitioner<Tuple> partitioner = new GlobalPartitioner<>();
        assertThat(partitioner.isBroadcast()).isFalse();
        return partitioner;
    }

    @ParameterizedTest
    @MethodSource("Provider_testSelectChannels_1to3")
    void testSelectChannels_1to3(int param1, int param2) {
        assertSelectedChannelWithSetup(param1, param2);
    }

    static public Stream<Arguments> Provider_testSelectChannels_1to3() {
        return Stream.of(arguments(0, 1), arguments(0, 2), arguments(0, 1024));
    }
}
