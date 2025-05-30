package org.apache.druid.timeline.partition;

import org.apache.druid.data.input.StringTuple;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class StringPartitionChunkTest_Parameterized {

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
    public void testCompareTo_5() {
        Assert.assertEquals(-1, StringPartitionChunk.make(null, StringTuple.create("10", "abc"), 0, 1).compareTo(StringPartitionChunk.make(StringTuple.create("10", "abc"), null, 1, 2)));
    }

    @Test
    public void testCompareTo_6() {
        Assert.assertEquals(-1, StringPartitionChunk.make(StringTuple.create("11", "b"), StringTuple.create("20", "a"), 0, 1).compareTo(StringPartitionChunk.make(StringTuple.create("20", "a"), StringTuple.create("33", "z"), 1, 1)));
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
    public void testCompareTo_12() {
        Assert.assertEquals(-1, StringPartitionChunk.makeForSingleDimension(null, "10", 0, 1).compareTo(StringPartitionChunk.makeForSingleDimension("10", null, 1, 2)));
    }

    @Test
    public void testCompareTo_13() {
        Assert.assertEquals(-1, StringPartitionChunk.makeForSingleDimension("11", "20", 0, 1).compareTo(StringPartitionChunk.makeForSingleDimension("20", "33", 1, 1)));
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

    @ParameterizedTest
    @MethodSource("Provider_testCompareTo_4_7")
    public void testCompareTo_4_7(int param1, int param2, int param3, int param4, int param5, int param6, String param7, int param8, String param9, int param10, String param11, int param12, String param13) {
        Assert.assertEquals(param1, StringPartitionChunk.make(StringTuple.create(param6, param7), StringTuple.create(param8, param9), param2, param3).compareTo(StringPartitionChunk.make(StringTuple.create(param10, param11), StringTuple.create(param12, param13), param4, param5)));
    }

    static public Stream<Arguments> Provider_testCompareTo_4_7() {
        return Stream.of(arguments(0, 1, 1, 1, 2, 10, "abc", 11, "aa", 10, "abc", 11, "aa"), arguments(1, 1, 1, 0, 1, 20, "a", 33, "z", 11, "b", 20, "a"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testCompareTo_11_14")
    public void testCompareTo_11_14(int param1, int param2, int param3, int param4, int param5, int param6, int param7, int param8, int param9) {
        Assert.assertEquals(param1, StringPartitionChunk.makeForSingleDimension(param2, param3, param4, param5).compareTo(StringPartitionChunk.makeForSingleDimension(param6, param7, param8, param9)));
    }

    static public Stream<Arguments> Provider_testCompareTo_11_14() {
        return Stream.of(arguments(0, 10, 11, 1, 1, 10, 11, 1, 2), arguments(1, 20, 33, 1, 1, 11, 20, 0, 1));
    }
}
