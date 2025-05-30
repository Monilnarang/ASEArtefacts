package org.apache.druid.granularity;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.apache.druid.jackson.DefaultObjectMapper;
import org.apache.druid.java.util.common.DateTimes;
import org.apache.druid.java.util.common.Intervals;
import org.apache.druid.java.util.common.StringUtils;
import org.apache.druid.java.util.common.granularity.DurationGranularity;
import org.apache.druid.java.util.common.granularity.Granularities;
import org.apache.druid.java.util.common.granularity.Granularity;
import org.apache.druid.java.util.common.granularity.GranularityType;
import org.apache.druid.java.util.common.granularity.PeriodGranularity;
import org.apache.druid.query.expression.TestExprMacroTable;
import org.apache.druid.segment.column.ColumnHolder;
import org.apache.druid.segment.column.ColumnType;
import org.apache.druid.segment.virtual.ExpressionVirtualColumn;
import org.apache.druid.testing.InitializedNullHandlingTest;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Interval;
import org.joda.time.Minutes;
import org.joda.time.Months;
import org.joda.time.Period;
import org.joda.time.Weeks;
import org.joda.time.Years;
import org.junit.Assert;
import org.junit.Test;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

public class QueryGranularityTest_Purified extends InitializedNullHandlingTest {

    private void assertSameDateTime(List<DateTime> expected, Iterable<DateTime> actual) {
        Assert.assertEquals(expected.size(), Iterables.size(actual));
        Iterator<DateTime> actualIter = actual.iterator();
        Iterator<DateTime> expectedIter = expected.iterator();
        while (actualIter.hasNext() && expectedIter.hasNext()) {
            Assert.assertEquals(expectedIter.next(), actualIter.next());
        }
        Assert.assertFalse("actualIter not exhausted!?", actualIter.hasNext());
        Assert.assertFalse("expectedIter not exhausted!?", expectedIter.hasNext());
    }

    private void assertSameInterval(List<DateTime> expected, Iterable<Interval> actual) {
        Assert.assertEquals(expected.size(), Iterables.size(actual));
        Iterator<Interval> actualIter = actual.iterator();
        Iterator<DateTime> expectedIter = expected.iterator();
        while (actualIter.hasNext() && expectedIter.hasNext()) {
            Assert.assertEquals(expectedIter.next(), actualIter.next().getStart());
        }
        Assert.assertFalse("actualIter not exhausted!?", actualIter.hasNext());
        Assert.assertFalse("expectedIter not exhausted!?", expectedIter.hasNext());
    }

    private void assertBucketStart(final Granularity granularity, final DateTime in, final DateTime expectedInProperTz) {
        Assert.assertEquals(StringUtils.format("Granularity [%s] toDateTime(bucketStart(DateTime))", granularity), expectedInProperTz, granularity.toDateTime(granularity.bucketStart(in).getMillis()));
        Assert.assertEquals(StringUtils.format("Granularity [%s] bucketStart(DateTime)", granularity), expectedInProperTz.withZone(in.getZone()), granularity.bucketStart(in));
        Assert.assertEquals(StringUtils.format("Granularity [%s] bucketStart(long)", granularity), expectedInProperTz.getMillis(), granularity.bucketStart(in.getMillis()));
    }

    @Test
    public void testMerge_1() {
        Assert.assertNull(Granularity.mergeGranularities(null));
    }

    @Test
    public void testMerge_2() {
        Assert.assertNull(Granularity.mergeGranularities(ImmutableList.of()));
    }

    @Test
    public void testMerge_3() {
        Assert.assertNull(Granularity.mergeGranularities(Lists.newArrayList(null, Granularities.DAY)));
    }

    @Test
    public void testMerge_4() {
        Assert.assertNull(Granularity.mergeGranularities(Lists.newArrayList(Granularities.DAY, null)));
    }

    @Test
    public void testMerge_5() {
        Assert.assertNull(Granularity.mergeGranularities(Lists.newArrayList(Granularities.DAY, null, Granularities.DAY)));
    }

    @Test
    public void testMerge_6() {
        Assert.assertNull(Granularity.mergeGranularities(ImmutableList.of(Granularities.ALL, Granularities.DAY)));
    }

    @Test
    public void testMerge_7() {
        Assert.assertEquals(Granularities.ALL, Granularity.mergeGranularities(ImmutableList.of(Granularities.ALL, Granularities.ALL)));
    }
}
