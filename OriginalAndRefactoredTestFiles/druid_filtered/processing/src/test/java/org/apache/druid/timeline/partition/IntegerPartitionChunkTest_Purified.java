package org.apache.druid.timeline.partition;

import org.junit.Assert;
import org.junit.Test;

public class IntegerPartitionChunkTest_Purified {

    private static IntegerPartitionChunk<OvershadowableInteger> make(Integer start, Integer end, int chunkNumber, int obj) {
        return new IntegerPartitionChunk<>(start, end, chunkNumber, new OvershadowableInteger(obj));
    }

    @Test
    public void testAbuts_1_testMerged_1() {
        IntegerPartitionChunk<OvershadowableInteger> lhs = make(null, 10, 0, 1);
        Assert.assertTrue(lhs.abuts(make(10, null, 1, 2)));
        Assert.assertFalse(lhs.abuts(make(11, null, 2, 3)));
        Assert.assertFalse(lhs.abuts(make(null, null, 3, 4)));
    }

    @Test
    public void testAbuts_4() {
        Assert.assertFalse(make(null, null, 0, 1).abuts(make(null, null, 1, 2)));
    }

    @Test
    public void testIsStart_1() {
        Assert.assertTrue(make(null, 10, 0, 1).isStart());
    }

    @Test
    public void testIsStart_2() {
        Assert.assertFalse(make(10, null, 0, 1).isStart());
    }

    @Test
    public void testIsStart_3() {
        Assert.assertFalse(make(10, 11, 0, 1).isStart());
    }

    @Test
    public void testIsStart_4() {
        Assert.assertTrue(make(null, null, 0, 1).isStart());
    }

    @Test
    public void testIsEnd_1() {
        Assert.assertFalse(make(null, 10, 0, 1).isEnd());
    }

    @Test
    public void testIsEnd_2() {
        Assert.assertTrue(make(10, null, 0, 1).isEnd());
    }

    @Test
    public void testIsEnd_3() {
        Assert.assertFalse(make(10, 11, 0, 1).isEnd());
    }

    @Test
    public void testIsEnd_4() {
        Assert.assertTrue(make(null, null, 0, 1).isEnd());
    }

    @Test
    public void testCompareTo_1() {
        Assert.assertEquals(0, make(null, null, 0, 1).compareTo(make(null, null, 0, 1)));
    }

    @Test
    public void testCompareTo_2() {
        Assert.assertEquals(0, make(10, null, 0, 1).compareTo(make(10, null, 0, 2)));
    }

    @Test
    public void testCompareTo_3() {
        Assert.assertEquals(0, make(null, 10, 0, 1).compareTo(make(null, 10, 0, 2)));
    }

    @Test
    public void testCompareTo_4() {
        Assert.assertEquals(0, make(10, 11, 0, 1).compareTo(make(10, 11, 0, 2)));
    }

    @Test
    public void testCompareTo_5() {
        Assert.assertEquals(-1, make(null, 10, 0, 1).compareTo(make(10, null, 1, 2)));
    }

    @Test
    public void testCompareTo_6() {
        Assert.assertEquals(-1, make(11, 20, 0, 1).compareTo(make(20, 33, 1, 1)));
    }

    @Test
    public void testCompareTo_7() {
        Assert.assertEquals(1, make(20, 33, 1, 1).compareTo(make(11, 20, 0, 1)));
    }

    @Test
    public void testCompareTo_8() {
        Assert.assertEquals(1, make(10, null, 1, 1).compareTo(make(null, 10, 0, 1)));
    }

    @Test
    public void testEquals_1() {
        Assert.assertEquals(make(null, null, 0, 1), make(null, null, 0, 1));
    }

    @Test
    public void testEquals_2() {
        Assert.assertEquals(make(null, 10, 0, 1), make(null, 10, 0, 1));
    }

    @Test
    public void testEquals_3() {
        Assert.assertEquals(make(10, null, 0, 1), make(10, null, 0, 1));
    }

    @Test
    public void testEquals_4() {
        Assert.assertEquals(make(10, 11, 0, 1), make(10, 11, 0, 1));
    }
}
