package org.apache.druid.timeline.partition;

import org.apache.druid.data.input.StringTuple;
import org.junit.Assert;
import org.junit.Test;

public class StringPartitionChunkTest_Purified {

    @Test
    public void testIsStart_1() {
        Assert.assertTrue(StringPartitionChunk.make(null, StringTuple.create("10", "abc"), 0, 1).isStart());
    }

    @Test
    public void testIsStart_2() {
        Assert.assertFalse(StringPartitionChunk.make(StringTuple.create("10", "abc"), null, 0, 1).isStart());
    }

    @Test
    public void testIsStart_3() {
        Assert.assertFalse(StringPartitionChunk.make(StringTuple.create("10", "abc"), StringTuple.create("11", "def"), 0, 1).isStart());
    }

    @Test
    public void testIsStart_4() {
        Assert.assertTrue(StringPartitionChunk.makeForSingleDimension(null, "10", 0, 1).isStart());
    }

    @Test
    public void testIsStart_5() {
        Assert.assertFalse(StringPartitionChunk.makeForSingleDimension("10", null, 0, 1).isStart());
    }

    @Test
    public void testIsStart_6() {
        Assert.assertFalse(StringPartitionChunk.makeForSingleDimension("10", "11", 0, 1).isStart());
    }

    @Test
    public void testIsStart_7() {
        Assert.assertTrue(StringPartitionChunk.makeForSingleDimension(null, null, 0, 1).isStart());
    }

    @Test
    public void testIsEnd_1() {
        Assert.assertFalse(StringPartitionChunk.make(null, StringTuple.create("10", "abc"), 0, 1).isEnd());
    }

    @Test
    public void testIsEnd_2() {
        Assert.assertTrue(StringPartitionChunk.make(StringTuple.create("10", "abc"), null, 0, 1).isEnd());
    }

    @Test
    public void testIsEnd_3() {
        Assert.assertFalse(StringPartitionChunk.make(StringTuple.create("10", "abc"), StringTuple.create("11", "def"), 0, 1).isEnd());
    }

    @Test
    public void testIsEnd_4() {
        Assert.assertFalse(StringPartitionChunk.makeForSingleDimension(null, "10", 0, 1).isEnd());
    }

    @Test
    public void testIsEnd_5() {
        Assert.assertTrue(StringPartitionChunk.makeForSingleDimension("10", null, 0, 1).isEnd());
    }

    @Test
    public void testIsEnd_6() {
        Assert.assertFalse(StringPartitionChunk.makeForSingleDimension("10", "11", 0, 1).isEnd());
    }

    @Test
    public void testIsEnd_7() {
        Assert.assertTrue(StringPartitionChunk.makeForSingleDimension(null, null, 0, 1).isEnd());
    }

    @Test
    public void testCompareTo_1() {
        Assert.assertEquals(0, StringPartitionChunk.make(null, null, 0, 1).compareTo(StringPartitionChunk.make(null, null, 0, 2)));
    }

    @Test
    public void testCompareTo_2() {
        Assert.assertEquals(0, StringPartitionChunk.make(StringTuple.create("10", "abc"), null, 0, 1).compareTo(StringPartitionChunk.make(StringTuple.create("10", "abc"), null, 0, 2)));
    }

    @Test
    public void testCompareTo_3() {
        Assert.assertEquals(0, StringPartitionChunk.make(null, StringTuple.create("10", "abc"), 1, 1).compareTo(StringPartitionChunk.make(null, StringTuple.create("10", "abc"), 1, 2)));
    }

    @Test
    public void testCompareTo_4() {
        Assert.assertEquals(0, StringPartitionChunk.make(StringTuple.create("10", "abc"), StringTuple.create("11", "aa"), 1, 1).compareTo(StringPartitionChunk.make(StringTuple.create("10", "abc"), StringTuple.create("11", "aa"), 1, 2)));
    }

    @Test
    public void testCompareTo_5() {
        Assert.assertEquals(-1, StringPartitionChunk.make(null, StringTuple.create("10", "abc"), 0, 1).compareTo(StringPartitionChunk.make(StringTuple.create("10", "abc"), null, 1, 2)));
    }

    @Test
    public void testCompareTo_6() {
        Assert.assertEquals(-1, StringPartitionChunk.make(StringTuple.create("11", "b"), StringTuple.create("20", "a"), 0, 1).compareTo(StringPartitionChunk.make(StringTuple.create("20", "a"), StringTuple.create("33", "z"), 1, 1)));
    }

    @Test
    public void testCompareTo_7() {
        Assert.assertEquals(1, StringPartitionChunk.make(StringTuple.create("20", "a"), StringTuple.create("33", "z"), 1, 1).compareTo(StringPartitionChunk.make(StringTuple.create("11", "b"), StringTuple.create("20", "a"), 0, 1)));
    }

    @Test
    public void testCompareTo_8() {
        Assert.assertEquals(0, StringPartitionChunk.makeForSingleDimension(null, null, 0, 1).compareTo(StringPartitionChunk.makeForSingleDimension(null, null, 0, 2)));
    }

    @Test
    public void testCompareTo_9() {
        Assert.assertEquals(0, StringPartitionChunk.makeForSingleDimension("10", null, 0, 1).compareTo(StringPartitionChunk.makeForSingleDimension("10", null, 0, 2)));
    }

    @Test
    public void testCompareTo_10() {
        Assert.assertEquals(0, StringPartitionChunk.makeForSingleDimension(null, "10", 1, 1).compareTo(StringPartitionChunk.makeForSingleDimension(null, "10", 1, 2)));
    }

    @Test
    public void testCompareTo_11() {
        Assert.assertEquals(0, StringPartitionChunk.makeForSingleDimension("10", "11", 1, 1).compareTo(StringPartitionChunk.makeForSingleDimension("10", "11", 1, 2)));
    }

    @Test
    public void testCompareTo_12() {
        Assert.assertEquals(-1, StringPartitionChunk.makeForSingleDimension(null, "10", 0, 1).compareTo(StringPartitionChunk.makeForSingleDimension("10", null, 1, 2)));
    }

    @Test
    public void testCompareTo_13() {
        Assert.assertEquals(-1, StringPartitionChunk.makeForSingleDimension("11", "20", 0, 1).compareTo(StringPartitionChunk.makeForSingleDimension("20", "33", 1, 1)));
    }

    @Test
    public void testCompareTo_14() {
        Assert.assertEquals(1, StringPartitionChunk.makeForSingleDimension("20", "33", 1, 1).compareTo(StringPartitionChunk.makeForSingleDimension("11", "20", 0, 1)));
    }

    @Test
    public void testCompareTo_15() {
        Assert.assertEquals(1, StringPartitionChunk.makeForSingleDimension("10", null, 1, 1).compareTo(StringPartitionChunk.makeForSingleDimension(null, "10", 0, 1)));
    }

    @Test
    public void testEquals_1() {
        Assert.assertEquals(StringPartitionChunk.makeForSingleDimension(null, null, 0, 1), StringPartitionChunk.makeForSingleDimension(null, null, 0, 1));
    }

    @Test
    public void testEquals_2() {
        Assert.assertEquals(StringPartitionChunk.makeForSingleDimension(null, "10", 0, 1), StringPartitionChunk.makeForSingleDimension(null, "10", 0, 1));
    }

    @Test
    public void testEquals_3() {
        Assert.assertEquals(StringPartitionChunk.makeForSingleDimension("10", null, 0, 1), StringPartitionChunk.makeForSingleDimension("10", null, 0, 1));
    }

    @Test
    public void testEquals_4() {
        Assert.assertEquals(StringPartitionChunk.makeForSingleDimension("10", "11", 0, 1), StringPartitionChunk.makeForSingleDimension("10", "11", 0, 1));
    }
}
