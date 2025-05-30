package org.apache.hadoop.hdfs.protocol;

import org.junit.Test;
import static org.apache.hadoop.hdfs.protocol.BlockType.CONTIGUOUS;
import static org.apache.hadoop.hdfs.protocol.BlockType.STRIPED;
import static org.junit.Assert.*;

public class TestBlockType_Purified {

    @Test
    public void testGetBlockType_1() throws Exception {
        assertEquals(BlockType.fromBlockId(0x0000000000000000L), CONTIGUOUS);
    }

    @Test
    public void testGetBlockType_2() throws Exception {
        assertEquals(BlockType.fromBlockId(0x1000000000000000L), CONTIGUOUS);
    }

    @Test
    public void testGetBlockType_3() throws Exception {
        assertEquals(BlockType.fromBlockId(0x2000000000000000L), CONTIGUOUS);
    }

    @Test
    public void testGetBlockType_4() throws Exception {
        assertEquals(BlockType.fromBlockId(0x4000000000000000L), CONTIGUOUS);
    }

    @Test
    public void testGetBlockType_5() throws Exception {
        assertEquals(BlockType.fromBlockId(0x7000000000000000L), CONTIGUOUS);
    }

    @Test
    public void testGetBlockType_6() throws Exception {
        assertEquals(BlockType.fromBlockId(0x00000000ffffffffL), CONTIGUOUS);
    }

    @Test
    public void testGetBlockType_7() throws Exception {
        assertEquals(BlockType.fromBlockId(0x10000000ffffffffL), CONTIGUOUS);
    }

    @Test
    public void testGetBlockType_8() throws Exception {
        assertEquals(BlockType.fromBlockId(0x20000000ffffffffL), CONTIGUOUS);
    }

    @Test
    public void testGetBlockType_9() throws Exception {
        assertEquals(BlockType.fromBlockId(0x40000000ffffffffL), CONTIGUOUS);
    }

    @Test
    public void testGetBlockType_10() throws Exception {
        assertEquals(BlockType.fromBlockId(0x70000000ffffffffL), CONTIGUOUS);
    }

    @Test
    public void testGetBlockType_11() throws Exception {
        assertEquals(BlockType.fromBlockId(0x70000000ffffffffL), CONTIGUOUS);
    }

    @Test
    public void testGetBlockType_12() throws Exception {
        assertEquals(BlockType.fromBlockId(0x0fffffffffffffffL), CONTIGUOUS);
    }

    @Test
    public void testGetBlockType_13() throws Exception {
        assertEquals(BlockType.fromBlockId(0x1fffffffffffffffL), CONTIGUOUS);
    }

    @Test
    public void testGetBlockType_14() throws Exception {
        assertEquals(BlockType.fromBlockId(0x2fffffffffffffffL), CONTIGUOUS);
    }

    @Test
    public void testGetBlockType_15() throws Exception {
        assertEquals(BlockType.fromBlockId(0x4fffffffffffffffL), CONTIGUOUS);
    }

    @Test
    public void testGetBlockType_16() throws Exception {
        assertEquals(BlockType.fromBlockId(0x7fffffffffffffffL), CONTIGUOUS);
    }

    @Test
    public void testGetBlockType_17() throws Exception {
        assertEquals(BlockType.fromBlockId(0x8000000000000000L), STRIPED);
    }

    @Test
    public void testGetBlockType_18() throws Exception {
        assertEquals(BlockType.fromBlockId(0x9000000000000000L), STRIPED);
    }

    @Test
    public void testGetBlockType_19() throws Exception {
        assertEquals(BlockType.fromBlockId(0xa000000000000000L), STRIPED);
    }

    @Test
    public void testGetBlockType_20() throws Exception {
        assertEquals(BlockType.fromBlockId(0xf000000000000000L), STRIPED);
    }

    @Test
    public void testGetBlockType_21() throws Exception {
        assertEquals(BlockType.fromBlockId(0x80000000ffffffffL), STRIPED);
    }

    @Test
    public void testGetBlockType_22() throws Exception {
        assertEquals(BlockType.fromBlockId(0x90000000ffffffffL), STRIPED);
    }

    @Test
    public void testGetBlockType_23() throws Exception {
        assertEquals(BlockType.fromBlockId(0xa0000000ffffffffL), STRIPED);
    }

    @Test
    public void testGetBlockType_24() throws Exception {
        assertEquals(BlockType.fromBlockId(0xf0000000ffffffffL), STRIPED);
    }

    @Test
    public void testGetBlockType_25() throws Exception {
        assertEquals(BlockType.fromBlockId(0x8fffffffffffffffL), STRIPED);
    }

    @Test
    public void testGetBlockType_26() throws Exception {
        assertEquals(BlockType.fromBlockId(0x9fffffffffffffffL), STRIPED);
    }

    @Test
    public void testGetBlockType_27() throws Exception {
        assertEquals(BlockType.fromBlockId(0xafffffffffffffffL), STRIPED);
    }

    @Test
    public void testGetBlockType_28() throws Exception {
        assertEquals(BlockType.fromBlockId(0xffffffffffffffffL), STRIPED);
    }
}
