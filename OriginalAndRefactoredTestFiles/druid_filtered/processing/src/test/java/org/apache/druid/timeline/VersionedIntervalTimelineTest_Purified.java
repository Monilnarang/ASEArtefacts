package org.apache.druid.timeline;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.druid.java.util.common.DateTimes;
import org.apache.druid.java.util.common.Intervals;
import org.apache.druid.timeline.partition.IntegerPartitionChunk;
import org.apache.druid.timeline.partition.OvershadowableInteger;
import org.apache.druid.timeline.partition.PartitionHolder;
import org.joda.time.Interval;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class VersionedIntervalTimelineTest_Purified extends VersionedIntervalTimelineTestBase {

    @Before
    public void setUp() {
        timeline = makeStringIntegerTimeline();
    }

    @Test
    public void testIsOvershadowedWithNonOverlappingSegmentsInTimeline_1() {
        Assert.assertFalse(timeline.isOvershadowed(Intervals.of("2011-04-01/2011-04-03"), "0", new OvershadowableInteger("0", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithNonOverlappingSegmentsInTimeline_2() {
        Assert.assertFalse(timeline.isOvershadowed(Intervals.of("2011-04-01/2011-04-05"), "0", new OvershadowableInteger("0", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithNonOverlappingSegmentsInTimeline_3() {
        Assert.assertFalse(timeline.isOvershadowed(Intervals.of("2011-04-01/2011-04-06"), "0", new OvershadowableInteger("0", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithNonOverlappingSegmentsInTimeline_4() {
        Assert.assertFalse(timeline.isOvershadowed(Intervals.of("2011-04-01/2011-04-07"), "0", new OvershadowableInteger("0", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithNonOverlappingSegmentsInTimeline_5() {
        Assert.assertFalse(timeline.isOvershadowed(Intervals.of("2011-04-01/2011-04-08"), "0", new OvershadowableInteger("0", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithNonOverlappingSegmentsInTimeline_6() {
        Assert.assertFalse(timeline.isOvershadowed(Intervals.of("2011-04-01/2011-04-09"), "0", new OvershadowableInteger("0", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithNonOverlappingSegmentsInTimeline_7() {
        Assert.assertFalse(timeline.isOvershadowed(Intervals.of("2011-04-01/2011-04-10"), "0", new OvershadowableInteger("0", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithNonOverlappingSegmentsInTimeline_8() {
        Assert.assertFalse(timeline.isOvershadowed(Intervals.of("2011-04-01/2011-04-30"), "0", new OvershadowableInteger("0", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithNonOverlappingSegmentsInTimeline_9() {
        Assert.assertTrue(timeline.isOvershadowed(Intervals.of("2011-04-05/2011-04-06"), "0", new OvershadowableInteger("0", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithNonOverlappingSegmentsInTimeline_10() {
        Assert.assertTrue(timeline.isOvershadowed(Intervals.of("2011-04-05/2011-04-07"), "0", new OvershadowableInteger("0", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithNonOverlappingSegmentsInTimeline_11() {
        Assert.assertTrue(timeline.isOvershadowed(Intervals.of("2011-04-05/2011-04-08"), "0", new OvershadowableInteger("0", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithNonOverlappingSegmentsInTimeline_12() {
        Assert.assertTrue(timeline.isOvershadowed(Intervals.of("2011-04-05/2011-04-09"), "0", new OvershadowableInteger("0", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithNonOverlappingSegmentsInTimeline_13() {
        Assert.assertFalse(timeline.isOvershadowed(Intervals.of("2011-04-05/2011-04-06"), "1", new OvershadowableInteger("1", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithNonOverlappingSegmentsInTimeline_14() {
        Assert.assertFalse(timeline.isOvershadowed(Intervals.of("2011-04-05/2011-04-07"), "1", new OvershadowableInteger("1", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithNonOverlappingSegmentsInTimeline_15() {
        Assert.assertFalse(timeline.isOvershadowed(Intervals.of("2011-04-05/2011-04-08"), "1", new OvershadowableInteger("1", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithNonOverlappingSegmentsInTimeline_16() {
        Assert.assertFalse(timeline.isOvershadowed(Intervals.of("2011-04-05/2011-04-09"), "1", new OvershadowableInteger("1", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithNonOverlappingSegmentsInTimeline_17() {
        Assert.assertFalse(timeline.isOvershadowed(Intervals.of("2011-04-05/2011-04-06"), "2", new OvershadowableInteger("2", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithNonOverlappingSegmentsInTimeline_18() {
        Assert.assertFalse(timeline.isOvershadowed(Intervals.of("2011-04-05/2011-04-07"), "2", new OvershadowableInteger("2", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithNonOverlappingSegmentsInTimeline_19() {
        Assert.assertFalse(timeline.isOvershadowed(Intervals.of("2011-04-05/2011-04-08"), "2", new OvershadowableInteger("2", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithNonOverlappingSegmentsInTimeline_20() {
        Assert.assertFalse(timeline.isOvershadowed(Intervals.of("2011-04-05/2011-04-09"), "2", new OvershadowableInteger("2", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithNonOverlappingSegmentsInTimeline_21() {
        Assert.assertTrue(timeline.isOvershadowed(Intervals.of("2011-04-06/2011-04-07"), "0", new OvershadowableInteger("0", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithNonOverlappingSegmentsInTimeline_22() {
        Assert.assertTrue(timeline.isOvershadowed(Intervals.of("2011-04-06/2011-04-08"), "0", new OvershadowableInteger("0", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithNonOverlappingSegmentsInTimeline_23() {
        Assert.assertTrue(timeline.isOvershadowed(Intervals.of("2011-04-06/2011-04-09"), "0", new OvershadowableInteger("0", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithNonOverlappingSegmentsInTimeline_24() {
        Assert.assertFalse(timeline.isOvershadowed(Intervals.of("2011-04-06/2011-04-10"), "0", new OvershadowableInteger("0", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithNonOverlappingSegmentsInTimeline_25() {
        Assert.assertFalse(timeline.isOvershadowed(Intervals.of("2011-04-06/2011-04-30"), "0", new OvershadowableInteger("0", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithNonOverlappingSegmentsInTimeline_26() {
        Assert.assertTrue(timeline.isOvershadowed(Intervals.of("2011-04-07/2011-04-08"), "0", new OvershadowableInteger("0", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithNonOverlappingSegmentsInTimeline_27() {
        Assert.assertTrue(timeline.isOvershadowed(Intervals.of("2011-04-07/2011-04-09"), "0", new OvershadowableInteger("0", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithNonOverlappingSegmentsInTimeline_28() {
        Assert.assertFalse(timeline.isOvershadowed(Intervals.of("2011-04-07/2011-04-10"), "0", new OvershadowableInteger("0", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithNonOverlappingSegmentsInTimeline_29() {
        Assert.assertFalse(timeline.isOvershadowed(Intervals.of("2011-04-07/2011-04-30"), "0", new OvershadowableInteger("0", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithNonOverlappingSegmentsInTimeline_30() {
        Assert.assertTrue(timeline.isOvershadowed(Intervals.of("2011-04-08/2011-04-09"), "0", new OvershadowableInteger("0", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithNonOverlappingSegmentsInTimeline_31() {
        Assert.assertFalse(timeline.isOvershadowed(Intervals.of("2011-04-08/2011-04-10"), "0", new OvershadowableInteger("0", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithNonOverlappingSegmentsInTimeline_32() {
        Assert.assertFalse(timeline.isOvershadowed(Intervals.of("2011-04-08/2011-04-30"), "0", new OvershadowableInteger("0", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithNonOverlappingSegmentsInTimeline_33() {
        Assert.assertFalse(timeline.isOvershadowed(Intervals.of("2011-04-09/2011-04-10"), "0", new OvershadowableInteger("0", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithNonOverlappingSegmentsInTimeline_34() {
        Assert.assertFalse(timeline.isOvershadowed(Intervals.of("2011-04-09/2011-04-15"), "0", new OvershadowableInteger("0", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithNonOverlappingSegmentsInTimeline_35() {
        Assert.assertFalse(timeline.isOvershadowed(Intervals.of("2011-04-09/2011-04-17"), "0", new OvershadowableInteger("0", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithNonOverlappingSegmentsInTimeline_36() {
        Assert.assertFalse(timeline.isOvershadowed(Intervals.of("2011-04-09/2011-04-19"), "0", new OvershadowableInteger("0", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithNonOverlappingSegmentsInTimeline_37() {
        Assert.assertFalse(timeline.isOvershadowed(Intervals.of("2011-04-09/2011-04-30"), "0", new OvershadowableInteger("0", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithNonOverlappingSegmentsInTimeline_38() {
        Assert.assertTrue(timeline.isOvershadowed(Intervals.of("2011-04-15/2011-04-16"), "0", new OvershadowableInteger("0", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithNonOverlappingSegmentsInTimeline_39() {
        Assert.assertTrue(timeline.isOvershadowed(Intervals.of("2011-04-15/2011-04-17"), "0", new OvershadowableInteger("0", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithNonOverlappingSegmentsInTimeline_40() {
        Assert.assertTrue(timeline.isOvershadowed(Intervals.of("2011-04-15/2011-04-18"), "0", new OvershadowableInteger("0", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithNonOverlappingSegmentsInTimeline_41() {
        Assert.assertTrue(timeline.isOvershadowed(Intervals.of("2011-04-15/2011-04-19"), "0", new OvershadowableInteger("0", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithNonOverlappingSegmentsInTimeline_42() {
        Assert.assertFalse(timeline.isOvershadowed(Intervals.of("2011-04-15/2011-04-20"), "0", new OvershadowableInteger("0", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithNonOverlappingSegmentsInTimeline_43() {
        Assert.assertFalse(timeline.isOvershadowed(Intervals.of("2011-04-15/2011-04-30"), "0", new OvershadowableInteger("0", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithNonOverlappingSegmentsInTimeline_44() {
        Assert.assertFalse(timeline.isOvershadowed(Intervals.of("2011-04-19/2011-04-20"), "0", new OvershadowableInteger("0", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithNonOverlappingSegmentsInTimeline_45() {
        Assert.assertFalse(timeline.isOvershadowed(Intervals.of("2011-04-21/2011-04-22"), "0", new OvershadowableInteger("0", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithOverlappingSegmentsInTimeline_1() {
        Assert.assertFalse(timeline.isOvershadowed(Intervals.of("2011-04-01/2011-04-03"), "0", new OvershadowableInteger("0", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithOverlappingSegmentsInTimeline_2() {
        Assert.assertFalse(timeline.isOvershadowed(Intervals.of("2011-04-01/2011-04-05"), "0", new OvershadowableInteger("0", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithOverlappingSegmentsInTimeline_3() {
        Assert.assertFalse(timeline.isOvershadowed(Intervals.of("2011-04-01/2011-04-06"), "0", new OvershadowableInteger("0", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithOverlappingSegmentsInTimeline_4() {
        Assert.assertFalse(timeline.isOvershadowed(Intervals.of("2011-04-01/2011-04-07"), "0", new OvershadowableInteger("0", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithOverlappingSegmentsInTimeline_5() {
        Assert.assertFalse(timeline.isOvershadowed(Intervals.of("2011-04-01/2011-04-08"), "0", new OvershadowableInteger("0", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithOverlappingSegmentsInTimeline_6() {
        Assert.assertFalse(timeline.isOvershadowed(Intervals.of("2011-04-01/2011-04-09"), "0", new OvershadowableInteger("0", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithOverlappingSegmentsInTimeline_7() {
        Assert.assertFalse(timeline.isOvershadowed(Intervals.of("2011-04-01/2011-04-10"), "0", new OvershadowableInteger("0", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithOverlappingSegmentsInTimeline_8() {
        Assert.assertFalse(timeline.isOvershadowed(Intervals.of("2011-04-01/2011-04-11"), "0", new OvershadowableInteger("0", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithOverlappingSegmentsInTimeline_9() {
        Assert.assertFalse(timeline.isOvershadowed(Intervals.of("2011-04-01/2011-04-30"), "0", new OvershadowableInteger("0", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithOverlappingSegmentsInTimeline_10() {
        Assert.assertTrue(timeline.isOvershadowed(Intervals.of("2011-04-05/2011-04-06"), "0", new OvershadowableInteger("0", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithOverlappingSegmentsInTimeline_11() {
        Assert.assertTrue(timeline.isOvershadowed(Intervals.of("2011-04-05/2011-04-07"), "0", new OvershadowableInteger("0", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithOverlappingSegmentsInTimeline_12() {
        Assert.assertTrue(timeline.isOvershadowed(Intervals.of("2011-04-05/2011-04-08"), "0", new OvershadowableInteger("0", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithOverlappingSegmentsInTimeline_13() {
        Assert.assertTrue(timeline.isOvershadowed(Intervals.of("2011-04-05/2011-04-09"), "0", new OvershadowableInteger("0", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithOverlappingSegmentsInTimeline_14() {
        Assert.assertTrue(timeline.isOvershadowed(Intervals.of("2011-04-05/2011-04-10"), "0", new OvershadowableInteger("0", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithOverlappingSegmentsInTimeline_15() {
        Assert.assertTrue(timeline.isOvershadowed(Intervals.of("2011-04-05/2011-04-11"), "0", new OvershadowableInteger("0", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithOverlappingSegmentsInTimeline_16() {
        Assert.assertFalse(timeline.isOvershadowed(Intervals.of("2011-04-05/2011-04-06"), "12", new OvershadowableInteger("12", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithOverlappingSegmentsInTimeline_17() {
        Assert.assertFalse(timeline.isOvershadowed(Intervals.of("2011-04-05/2011-04-07"), "12", new OvershadowableInteger("12", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithOverlappingSegmentsInTimeline_18() {
        Assert.assertFalse(timeline.isOvershadowed(Intervals.of("2011-04-05/2011-04-08"), "12", new OvershadowableInteger("12", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithOverlappingSegmentsInTimeline_19() {
        Assert.assertFalse(timeline.isOvershadowed(Intervals.of("2011-04-05/2011-04-09"), "12", new OvershadowableInteger("12", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithOverlappingSegmentsInTimeline_20() {
        Assert.assertFalse(timeline.isOvershadowed(Intervals.of("2011-04-05/2011-04-10"), "12", new OvershadowableInteger("12", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithOverlappingSegmentsInTimeline_21() {
        Assert.assertFalse(timeline.isOvershadowed(Intervals.of("2011-04-05/2011-04-11"), "12", new OvershadowableInteger("12", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithOverlappingSegmentsInTimeline_22() {
        Assert.assertFalse(timeline.isOvershadowed(Intervals.of("2011-04-05/2011-04-06"), "13", new OvershadowableInteger("13", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithOverlappingSegmentsInTimeline_23() {
        Assert.assertFalse(timeline.isOvershadowed(Intervals.of("2011-04-05/2011-04-07"), "13", new OvershadowableInteger("13", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithOverlappingSegmentsInTimeline_24() {
        Assert.assertFalse(timeline.isOvershadowed(Intervals.of("2011-04-05/2011-04-08"), "13", new OvershadowableInteger("13", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithOverlappingSegmentsInTimeline_25() {
        Assert.assertFalse(timeline.isOvershadowed(Intervals.of("2011-04-05/2011-04-09"), "13", new OvershadowableInteger("13", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithOverlappingSegmentsInTimeline_26() {
        Assert.assertFalse(timeline.isOvershadowed(Intervals.of("2011-04-05/2011-04-10"), "13", new OvershadowableInteger("13", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithOverlappingSegmentsInTimeline_27() {
        Assert.assertFalse(timeline.isOvershadowed(Intervals.of("2011-04-05/2011-04-11"), "13", new OvershadowableInteger("13", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithOverlappingSegmentsInTimeline_28() {
        Assert.assertFalse(timeline.isOvershadowed(Intervals.of("2011-04-05/2011-04-12"), "0", new OvershadowableInteger("0", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithOverlappingSegmentsInTimeline_29() {
        Assert.assertFalse(timeline.isOvershadowed(Intervals.of("2011-04-05/2011-04-15"), "0", new OvershadowableInteger("0", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithOverlappingSegmentsInTimeline_30() {
        Assert.assertFalse(timeline.isOvershadowed(Intervals.of("2011-04-05/2011-04-16"), "0", new OvershadowableInteger("0", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithOverlappingSegmentsInTimeline_31() {
        Assert.assertFalse(timeline.isOvershadowed(Intervals.of("2011-04-05/2011-04-17"), "0", new OvershadowableInteger("0", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithOverlappingSegmentsInTimeline_32() {
        Assert.assertFalse(timeline.isOvershadowed(Intervals.of("2011-04-05/2011-04-18"), "0", new OvershadowableInteger("0", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithOverlappingSegmentsInTimeline_33() {
        Assert.assertFalse(timeline.isOvershadowed(Intervals.of("2011-04-05/2011-04-19"), "0", new OvershadowableInteger("0", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithOverlappingSegmentsInTimeline_34() {
        Assert.assertFalse(timeline.isOvershadowed(Intervals.of("2011-04-05/2011-04-20"), "0", new OvershadowableInteger("0", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithOverlappingSegmentsInTimeline_35() {
        Assert.assertFalse(timeline.isOvershadowed(Intervals.of("2011-04-05/2011-04-21"), "0", new OvershadowableInteger("0", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithOverlappingSegmentsInTimeline_36() {
        Assert.assertFalse(timeline.isOvershadowed(Intervals.of("2011-04-05/2011-04-22"), "0", new OvershadowableInteger("0", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithOverlappingSegmentsInTimeline_37() {
        Assert.assertTrue(timeline.isOvershadowed(Intervals.of("2011-04-06/2011-04-07"), "0", new OvershadowableInteger("0", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithOverlappingSegmentsInTimeline_38() {
        Assert.assertTrue(timeline.isOvershadowed(Intervals.of("2011-04-06/2011-04-08"), "0", new OvershadowableInteger("0", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithOverlappingSegmentsInTimeline_39() {
        Assert.assertTrue(timeline.isOvershadowed(Intervals.of("2011-04-06/2011-04-09"), "0", new OvershadowableInteger("0", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithOverlappingSegmentsInTimeline_40() {
        Assert.assertTrue(timeline.isOvershadowed(Intervals.of("2011-04-06/2011-04-10"), "0", new OvershadowableInteger("0", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithOverlappingSegmentsInTimeline_41() {
        Assert.assertTrue(timeline.isOvershadowed(Intervals.of("2011-04-06/2011-04-11"), "0", new OvershadowableInteger("0", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithOverlappingSegmentsInTimeline_42() {
        Assert.assertFalse(timeline.isOvershadowed(Intervals.of("2011-04-06/2011-04-12"), "0", new OvershadowableInteger("0", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithOverlappingSegmentsInTimeline_43() {
        Assert.assertFalse(timeline.isOvershadowed(Intervals.of("2011-04-06/2011-04-15"), "0", new OvershadowableInteger("0", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithOverlappingSegmentsInTimeline_44() {
        Assert.assertFalse(timeline.isOvershadowed(Intervals.of("2011-04-06/2011-04-16"), "0", new OvershadowableInteger("0", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithOverlappingSegmentsInTimeline_45() {
        Assert.assertFalse(timeline.isOvershadowed(Intervals.of("2011-04-06/2011-04-17"), "0", new OvershadowableInteger("0", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithOverlappingSegmentsInTimeline_46() {
        Assert.assertFalse(timeline.isOvershadowed(Intervals.of("2011-04-06/2011-04-18"), "0", new OvershadowableInteger("0", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithOverlappingSegmentsInTimeline_47() {
        Assert.assertFalse(timeline.isOvershadowed(Intervals.of("2011-04-06/2011-04-19"), "0", new OvershadowableInteger("0", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithOverlappingSegmentsInTimeline_48() {
        Assert.assertFalse(timeline.isOvershadowed(Intervals.of("2011-04-06/2011-04-20"), "0", new OvershadowableInteger("0", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithOverlappingSegmentsInTimeline_49() {
        Assert.assertFalse(timeline.isOvershadowed(Intervals.of("2011-04-06/2011-04-21"), "0", new OvershadowableInteger("0", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithOverlappingSegmentsInTimeline_50() {
        Assert.assertFalse(timeline.isOvershadowed(Intervals.of("2011-04-06/2011-04-22"), "0", new OvershadowableInteger("0", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithOverlappingSegmentsInTimeline_51() {
        Assert.assertFalse(timeline.isOvershadowed(Intervals.of("2011-04-12/2011-04-15"), "0", new OvershadowableInteger("0", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithOverlappingSegmentsInTimeline_52() {
        Assert.assertFalse(timeline.isOvershadowed(Intervals.of("2011-04-12/2011-04-16"), "0", new OvershadowableInteger("0", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithOverlappingSegmentsInTimeline_53() {
        Assert.assertFalse(timeline.isOvershadowed(Intervals.of("2011-04-12/2011-04-17"), "0", new OvershadowableInteger("0", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithOverlappingSegmentsInTimeline_54() {
        Assert.assertFalse(timeline.isOvershadowed(Intervals.of("2011-04-12/2011-04-18"), "0", new OvershadowableInteger("0", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithOverlappingSegmentsInTimeline_55() {
        Assert.assertFalse(timeline.isOvershadowed(Intervals.of("2011-04-12/2011-04-19"), "0", new OvershadowableInteger("0", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithOverlappingSegmentsInTimeline_56() {
        Assert.assertFalse(timeline.isOvershadowed(Intervals.of("2011-04-12/2011-04-20"), "0", new OvershadowableInteger("0", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithOverlappingSegmentsInTimeline_57() {
        Assert.assertFalse(timeline.isOvershadowed(Intervals.of("2011-04-12/2011-04-21"), "0", new OvershadowableInteger("0", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithOverlappingSegmentsInTimeline_58() {
        Assert.assertFalse(timeline.isOvershadowed(Intervals.of("2011-04-12/2011-04-22"), "0", new OvershadowableInteger("0", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithOverlappingSegmentsInTimeline_59() {
        Assert.assertTrue(timeline.isOvershadowed(Intervals.of("2011-04-15/2011-04-21"), "0", new OvershadowableInteger("0", 0, 1)));
    }

    @Test
    public void testIsOvershadowedWithOverlappingSegmentsInTimeline_60() {
        Assert.assertFalse(timeline.isOvershadowed(Intervals.of("2011-04-21/2011-04-22"), "0", new OvershadowableInteger("0", 0, 1)));
    }
}
