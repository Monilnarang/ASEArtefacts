package org.apache.hadoop.hdfs.protocol;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.junit.Test;

public class TestExtendedBlock_Purified {

    static final String POOL_A = "blockpool-a";

    static final String POOL_B = "blockpool-b";

    static final Block BLOCK_1_GS1 = new Block(1L, 100L, 1L);

    static final Block BLOCK_1_GS2 = new Block(1L, 100L, 2L);

    static final Block BLOCK_2_GS1 = new Block(2L, 100L, 1L);

    private static void assertNotEquals(Object a, Object b) {
        assertFalse("expected not equal: '" + a + "' and '" + b + "'", a.equals(b));
    }

    @Test
    public void testEquals_1() {
        assertEquals(new ExtendedBlock(POOL_A, BLOCK_1_GS1), new ExtendedBlock(POOL_A, BLOCK_1_GS1));
    }

    @Test
    public void testEquals_2() {
        assertNotEquals(new ExtendedBlock(POOL_A, BLOCK_1_GS1), new ExtendedBlock(POOL_B, BLOCK_1_GS1));
    }

    @Test
    public void testEquals_3() {
        assertNotEquals(new ExtendedBlock(POOL_A, BLOCK_1_GS1), new ExtendedBlock(POOL_A, BLOCK_2_GS1));
    }

    @Test
    public void testEquals_4() {
        assertEquals(new ExtendedBlock(POOL_A, BLOCK_1_GS1), new ExtendedBlock(POOL_A, BLOCK_1_GS2));
    }

    @Test
    public void testHashcode_1() {
        assertNotEquals(new ExtendedBlock(POOL_A, BLOCK_1_GS1).hashCode(), new ExtendedBlock(POOL_B, BLOCK_1_GS1).hashCode());
    }

    @Test
    public void testHashcode_2() {
        assertNotEquals(new ExtendedBlock(POOL_A, BLOCK_1_GS1).hashCode(), new ExtendedBlock(POOL_A, BLOCK_2_GS1).hashCode());
    }

    @Test
    public void testHashcode_3() {
        assertEquals(new ExtendedBlock(POOL_A, BLOCK_1_GS1).hashCode(), new ExtendedBlock(POOL_A, BLOCK_1_GS1).hashCode());
    }
}
