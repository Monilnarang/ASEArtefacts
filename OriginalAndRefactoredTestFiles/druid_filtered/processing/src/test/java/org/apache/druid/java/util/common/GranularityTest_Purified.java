package org.apache.druid.java.util.common;

import com.google.common.collect.ImmutableList;
import org.apache.druid.java.util.common.granularity.Granularities;
import org.apache.druid.java.util.common.granularity.Granularity;
import org.apache.druid.java.util.common.granularity.GranularityType;
import org.apache.druid.java.util.common.granularity.PeriodGranularity;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.IllegalFieldValueException;
import org.joda.time.Interval;
import org.joda.time.Period;
import org.joda.time.chrono.ISOChronology;
import org.junit.Assert;
import org.junit.Test;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class GranularityTest_Purified {

    final Granularity NONE = Granularities.NONE;

    final Granularity SECOND = Granularities.SECOND;

    final Granularity MINUTE = Granularities.MINUTE;

    final Granularity HOUR = Granularities.HOUR;

    final Granularity SIX_HOUR = Granularities.SIX_HOUR;

    final Granularity EIGHT_HOUR = Granularities.EIGHT_HOUR;

    final Granularity FIFTEEN_MINUTE = Granularities.FIFTEEN_MINUTE;

    final Granularity DAY = Granularities.DAY;

    final Granularity WEEK = Granularities.WEEK;

    final Granularity MONTH = Granularities.MONTH;

    final Granularity YEAR = Granularities.YEAR;

    final Granularity ALL = Granularities.ALL;

    private void checkToDate(Granularity granularity, Granularity.Formatter formatter, PathDate[] checks) {
        for (PathDate pd : checks) {
            if (pd.exception == null) {
                Assert.assertEquals(StringUtils.format("[%s,%s] Expected path %s to return date %s", granularity, formatter, pd.path, pd.date), pd.date, granularity.toDate(pd.path, formatter));
                if (formatter.equals(Granularity.Formatter.DEFAULT)) {
                    Assert.assertEquals(StringUtils.format("[%s] Expected toDate(%s) to return the same as toDate(%s, DEFAULT)", granularity, pd.path, pd.path), granularity.toDate(pd.path), granularity.toDate(pd.path, formatter));
                }
                if (pd.date != null) {
                    Assert.assertEquals(StringUtils.format("[%s,%s] Expected date %s to return date %s", granularity, formatter, pd.date, pd.date), pd.date, granularity.toDate(granularity.getFormatter(formatter).print(pd.date) + "/", formatter));
                }
            } else {
                boolean flag = false;
                try {
                    granularity.toDate(pd.path, formatter);
                } catch (Exception e) {
                    if (e.getClass() == pd.exception) {
                        flag = true;
                    }
                }
                Assert.assertTrue(StringUtils.format("[%s,%s] Expected exception %s for path: %s", granularity, formatter, pd.exception, pd.path), flag);
            }
        }
    }

    private static class PathDate {

        public final String path;

        public final DateTime date;

        public final Class<? extends Exception> exception;

        private PathDate(DateTime date, Class<? extends Exception> exception, String path) {
            this.path = path;
            this.date = date;
            this.exception = exception;
        }
    }

    @Test
    public void testIsFinerComparator_1() {
        Assert.assertTrue(Granularity.IS_FINER_THAN.compare(NONE, SECOND) < 0);
    }

    @Test
    public void testIsFinerComparator_2() {
        Assert.assertTrue(Granularity.IS_FINER_THAN.compare(SECOND, NONE) > 0);
    }

    @Test
    public void testIsFinerComparator_3() {
        Assert.assertTrue(Granularity.IS_FINER_THAN.compare(NONE, MINUTE) < 0);
    }

    @Test
    public void testIsFinerComparator_4() {
        Assert.assertTrue(Granularity.IS_FINER_THAN.compare(MINUTE, NONE) > 0);
    }

    @Test
    public void testIsFinerComparator_5() {
        Assert.assertTrue(Granularity.IS_FINER_THAN.compare(DAY, MONTH) < 0);
    }

    @Test
    public void testIsFinerComparator_6() {
        Assert.assertTrue(Granularity.IS_FINER_THAN.compare(Granularities.YEAR, ALL) < 0);
    }

    @Test
    public void testIsFinerComparator_7() {
        Assert.assertTrue(Granularity.IS_FINER_THAN.compare(Granularities.ALL, YEAR) > 0);
    }

    @Test
    public void testIsFinerComparator_8() {
        Granularity day = DAY;
        Assert.assertTrue(Granularity.IS_FINER_THAN.compare(DAY, day) == 0);
    }

    @Test
    public void testIsFinerComparator_9() {
        Granularity none = NONE;
        Assert.assertTrue(Granularity.IS_FINER_THAN.compare(NONE, none) == 0);
    }

    @Test
    public void testIsFinerComparator_10() {
        Granularity all = ALL;
        Assert.assertTrue(Granularity.IS_FINER_THAN.compare(ALL, all) == 0);
    }
}
