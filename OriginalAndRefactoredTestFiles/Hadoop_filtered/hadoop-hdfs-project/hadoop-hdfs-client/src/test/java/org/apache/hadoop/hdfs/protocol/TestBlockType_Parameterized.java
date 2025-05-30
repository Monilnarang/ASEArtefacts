package org.apache.hadoop.hdfs.protocol;

import org.junit.Test;
import static org.apache.hadoop.hdfs.protocol.BlockType.CONTIGUOUS;
import static org.apache.hadoop.hdfs.protocol.BlockType.STRIPED;
import static org.junit.Assert.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class TestBlockType_Parameterized {

    @ParameterizedTest
    @MethodSource("Provider_testGetBlockType_1to16")
    public void testGetBlockType_1to16(long param1) throws Exception {
        assertEquals(BlockType.fromBlockId(param1), CONTIGUOUS);
    }

    static public Stream<Arguments> Provider_testGetBlockType_1to16() {
        return Stream.of(arguments(0x0000000000000000L), arguments(0x1000000000000000L), arguments(0x2000000000000000L), arguments(0x4000000000000000L), arguments(0x7000000000000000L), arguments(0x00000000ffffffffL), arguments(0x10000000ffffffffL), arguments(0x20000000ffffffffL), arguments(0x40000000ffffffffL), arguments(0x70000000ffffffffL), arguments(0x70000000ffffffffL), arguments(0x0fffffffffffffffL), arguments(0x1fffffffffffffffL), arguments(0x2fffffffffffffffL), arguments(0x4fffffffffffffffL), arguments(0x7fffffffffffffffL));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetBlockType_17to28")
    public void testGetBlockType_17to28(long param1) throws Exception {
        assertEquals(BlockType.fromBlockId(param1), STRIPED);
    }

    static public Stream<Arguments> Provider_testGetBlockType_17to28() {
        return Stream.of(arguments(0x8000000000000000L), arguments(0x9000000000000000L), arguments(0xa000000000000000L), arguments(0xf000000000000000L), arguments(0x80000000ffffffffL), arguments(0x90000000ffffffffL), arguments(0xa0000000ffffffffL), arguments(0xf0000000ffffffffL), arguments(0x8fffffffffffffffL), arguments(0x9fffffffffffffffL), arguments(0xafffffffffffffffL), arguments(0xffffffffffffffffL));
    }
}
