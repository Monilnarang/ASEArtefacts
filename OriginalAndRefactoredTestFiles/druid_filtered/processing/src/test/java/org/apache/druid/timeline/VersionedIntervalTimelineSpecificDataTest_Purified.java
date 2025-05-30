package org.apache.druid.timeline;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.apache.druid.java.util.common.DateTimes;
import org.apache.druid.java.util.common.Intervals;
import org.apache.druid.timeline.partition.IntegerPartitionChunk;
import org.apache.druid.timeline.partition.OvershadowableInteger;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Interval;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.Arrays;
import java.util.Collections;

public class VersionedIntervalTimelineSpecificDataTest_Purified extends VersionedIntervalTimelineTestBase {

    @Before
    public void setUp() {
        timeline = makeStringIntegerTimeline();
        add("2011-04-01/2011-04-03", "1", 2);
        add("2011-04-03/2011-04-06", "1", 3);
        add("2011-04-01/2011-04-09", "2", 1);
        add("2011-04-06/2011-04-09", "3", 4);
        add("2011-04-01/2011-04-02", "3", 5);
        add("2011-05-01/2011-05-02", "1", 6);
        add("2011-05-01/2011-05-05", "2", 7);
        add("2011-05-03/2011-05-04", "3", 8);
        add("2011-05-01/2011-05-10", "4", 9);
        add("2011-10-01/2011-10-02", "1", 1);
        add("2011-10-02/2011-10-03", "3", IntegerPartitionChunk.make(null, 10, 0, new OvershadowableInteger("3", 0, 20)));
        add("2011-10-02/2011-10-03", "3", IntegerPartitionChunk.make(10, null, 1, new OvershadowableInteger("3", 1, 21)));
        add("2011-10-03/2011-10-04", "3", 3);
        add("2011-10-04/2011-10-05", "4", 4);
        add("2011-10-05/2011-10-06", "5", 5);
    }

    @Test
    public void testApril2_1() {
        Assert.assertEquals(makeSingle("2", 1), timeline.remove(Intervals.of("2011-04-01/2011-04-09"), "2", makeSingle("2", 1)));
    }

    @Test
    public void testApril2_2() {
        assertValues(Arrays.asList(createExpected("2011-04-01/2011-04-02", "3", 5), createExpected("2011-04-02/2011-04-03", "1", 2), createExpected("2011-04-03/2011-04-06", "1", 3), createExpected("2011-04-06/2011-04-09", "3", 4)), timeline.lookup(Intervals.of("2011-04-01/2011-04-09")));
    }

    @Test
    public void testApril3_1() {
        Assert.assertEquals(makeSingle("2", 1), timeline.remove(Intervals.of("2011-04-01/2011-04-09"), "2", makeSingle("2", 1)));
    }

    @Test
    public void testApril3_2() {
        Assert.assertEquals(makeSingle("1", 2), timeline.remove(Intervals.of("2011-04-01/2011-04-03"), "1", makeSingle("1", 2)));
    }

    @Test
    public void testApril3_3() {
        assertValues(Arrays.asList(createExpected("2011-04-01/2011-04-02", "3", 5), createExpected("2011-04-03/2011-04-06", "1", 3), createExpected("2011-04-06/2011-04-09", "3", 4)), timeline.lookup(Intervals.of("2011-04-01/2011-04-09")));
    }

    @Test
    public void testApril4_1() {
        Assert.assertEquals(makeSingle("2", 1), timeline.remove(Intervals.of("2011-04-01/2011-04-09"), "2", makeSingle("2", 1)));
    }

    @Test
    public void testApril4_2() {
        assertValues(Arrays.asList(createExpected("2011-04-01/2011-04-02", "3", 5), createExpected("2011-04-02/2011-04-03", "1", 2), createExpected("2011-04-03/2011-04-05", "1", 3)), timeline.lookup(Intervals.of("2011-04-01/2011-04-05")));
    }

    @Test
    public void testApril4_3() {
        assertValues(Arrays.asList(createExpected("2011-04-02T18/2011-04-03", "1", 2), createExpected("2011-04-03/2011-04-04T01", "1", 3)), timeline.lookup(Intervals.of("2011-04-02T18/2011-04-04T01")));
    }

    @Test
    public void testMay2_1() {
        Assert.assertNotNull(timeline.remove(Intervals.of("2011-05-01/2011-05-10"), "4", makeSingle("4", 9)));
    }

    @Test
    public void testMay2_2() {
        assertValues(Arrays.asList(createExpected("2011-05-01/2011-05-03", "2", 7), createExpected("2011-05-03/2011-05-04", "3", 8), createExpected("2011-05-04/2011-05-05", "2", 7)), timeline.lookup(Intervals.of("2011-05-01/2011-05-09")));
    }

    @Test
    public void testMay3_1() {
        Assert.assertEquals(makeSingle("4", 9), timeline.remove(Intervals.of("2011-05-01/2011-05-10"), "4", makeSingle("4", 9)));
    }

    @Test
    public void testMay3_2() {
        Assert.assertEquals(makeSingle("2", 7), timeline.remove(Intervals.of("2011-05-01/2011-05-05"), "2", makeSingle("2", 7)));
    }

    @Test
    public void testMay3_3() {
        assertValues(Arrays.asList(createExpected("2011-05-01/2011-05-02", "1", 6), createExpected("2011-05-03/2011-05-04", "3", 8)), timeline.lookup(Intervals.of("2011-05-01/2011-05-09")));
    }

    @Test
    public void testFindChunk_1() {
        assertSingleElementChunks(makeSingle("1", 1), timeline.findChunk(Intervals.of("2011-10-01/2011-10-02"), "1", 0));
    }

    @Test
    public void testFindChunk_2() {
        assertSingleElementChunks(makeSingle("1", 1), timeline.findChunk(Intervals.of("2011-10-01/2011-10-01T10"), "1", 0));
    }

    @Test
    public void testFindChunk_3() {
        assertSingleElementChunks(makeSingle("1", 1), timeline.findChunk(Intervals.of("2011-10-01T02/2011-10-02"), "1", 0));
    }

    @Test
    public void testFindChunk_4() {
        assertSingleElementChunks(makeSingle("1", 1), timeline.findChunk(Intervals.of("2011-10-01T04/2011-10-01T17"), "1", 0));
    }

    @Test
    public void testFindChunk_5_testMerged_5() {
        IntegerPartitionChunk<OvershadowableInteger> expected = IntegerPartitionChunk.make(10, null, 1, new OvershadowableInteger("3", 1, 21));
        IntegerPartitionChunk<OvershadowableInteger> actual = (IntegerPartitionChunk<OvershadowableInteger>) timeline.findChunk(Intervals.of("2011-10-02/2011-10-03"), "3", 1);
        Assert.assertEquals(expected, actual);
        Assert.assertEquals(expected.getObject(), actual.getObject());
        Assert.assertEquals(null, timeline.findChunk(Intervals.of("2011-10-01T04/2011-10-01T17"), "1", 1));
        Assert.assertEquals(null, timeline.findChunk(Intervals.of("2011-10-01T04/2011-10-01T17"), "2", 0));
        Assert.assertEquals(null, timeline.findChunk(Intervals.of("2011-10-01T04/2011-10-02T17"), "1", 0));
    }

    @Test
    public void testPartialPartitionNotReturned_1() {
        assertValues(ImmutableList.of(createExpected("2011-10-05/2011-10-06", "5", 5)), timeline.lookup(Intervals.of("2011-10-05/2011-10-07")));
    }

    @Test
    public void testPartialPartitionNotReturned_2_testMerged_2() {
        Assert.assertTrue("Expected no overshadowed entries", timeline.findFullyOvershadowed().isEmpty());
    }

    @Test
    public void testPartialPartitionNotReturned_3() {
        assertValues(ImmutableList.of(createExpected("2011-10-05/2011-10-06", "5", 5)), timeline.lookup(Intervals.of("2011-10-05/2011-10-07")));
    }

    @Test
    public void testPartialPartitionNotReturned_5() {
        add("2011-10-06/2011-10-07", "6", IntegerPartitionChunk.make(null, 10, 0, new OvershadowableInteger("6", 0, 60)));
        add("2011-10-06/2011-10-07", "6", IntegerPartitionChunk.make(10, 20, 1, new OvershadowableInteger("6", 1, 61)));
        add("2011-10-06/2011-10-07", "6", IntegerPartitionChunk.make(20, null, 2, new OvershadowableInteger("6", 2, 62)));
        assertValues(ImmutableList.of(createExpected("2011-10-05/2011-10-06", "5", 5), createExpected("2011-10-06/2011-10-07", "6", Arrays.asList(IntegerPartitionChunk.make(null, 10, 0, new OvershadowableInteger("6", 0, 60)), IntegerPartitionChunk.make(10, 20, 1, new OvershadowableInteger("6", 1, 61)), IntegerPartitionChunk.make(20, null, 2, new OvershadowableInteger("6", 2, 62))))), timeline.lookup(Intervals.of("2011-10-05/2011-10-07")));
    }

    @Test
    public void testIncompletePartitionDoesNotOvershadow_1_testMerged_1() {
        Assert.assertTrue("Expected no overshadowed entries", timeline.findFullyOvershadowed().isEmpty());
    }

    @Test
    public void testIncompletePartitionDoesNotOvershadow_3() {
        assertValues(ImmutableSet.of(createExpected("2011-10-05/2011-10-06", "5", 5)), timeline.findFullyOvershadowed());
    }

    @Test
    public void testRemovePartitionMakesIncomplete_1() {
        final IntegerPartitionChunk<OvershadowableInteger> chunk = IntegerPartitionChunk.make(null, 10, 0, new OvershadowableInteger("6", 0, 60));
        Assert.assertEquals(chunk, timeline.remove(Intervals.of("2011-10-05/2011-10-07"), "6", chunk));
    }

    @Test
    public void testRemovePartitionMakesIncomplete_2() {
        assertValues(ImmutableList.of(createExpected("2011-10-05/2011-10-06", "5", 5)), timeline.lookup(Intervals.of("2011-10-05/2011-10-07")));
    }

    @Test
    public void testRemovePartitionMakesIncomplete_3() {
        Assert.assertTrue("Expected no overshadowed entries", timeline.findFullyOvershadowed().isEmpty());
    }

    @Test
    public void testInsertAndRemoveSameThingsion_1() {
        assertValues(Collections.singletonList(createExpected("2011-05-01/2011-05-09", "5", 10)), timeline.lookup(Intervals.of("2011-05-01/2011-05-09")));
    }

    @Test
    public void testInsertAndRemoveSameThingsion_2() {
        Assert.assertEquals(makeSingle("5", 10), timeline.remove(Intervals.of("2011-05-01/2011-05-10"), "5", makeSingle("5", 10)));
    }

    @Test
    public void testInsertAndRemoveSameThingsion_3() {
        assertValues(Collections.singletonList(createExpected("2011-05-01/2011-05-09", "4", 9)), timeline.lookup(Intervals.of("2011-05-01/2011-05-09")));
    }

    @Test
    public void testInsertAndRemoveSameThingsion_4() {
        assertValues(Collections.singletonList(createExpected("2011-05-01/2011-05-09", "5", 10)), timeline.lookup(Intervals.of("2011-05-01/2011-05-09")));
    }

    @Test
    public void testInsertAndRemoveSameThingsion_5() {
        Assert.assertEquals(makeSingle("4", 9), timeline.remove(Intervals.of("2011-05-01/2011-05-10"), "4", makeSingle("4", 9)));
    }

    @Test
    public void testInsertAndRemoveSameThingsion_6() {
        assertValues(Collections.singletonList(createExpected("2011-05-01/2011-05-09", "5", 10)), timeline.lookup(Intervals.of("2011-05-01/2011-05-09")));
    }

    @Test
    public void testRemoveSomethingDontHave_1() {
        Assert.assertNull("Don't have it, should be null", timeline.remove(Intervals.of("1970-01-01/2025-04-20"), "1", makeSingle("1", 1)));
    }

    @Test
    public void testRemoveSomethingDontHave_2() {
        Assert.assertNull("Don't have it, should be null", timeline.remove(Intervals.of("2011-04-01/2011-04-09"), "version does not exist", makeSingle("version does not exist", 1)));
    }
}
