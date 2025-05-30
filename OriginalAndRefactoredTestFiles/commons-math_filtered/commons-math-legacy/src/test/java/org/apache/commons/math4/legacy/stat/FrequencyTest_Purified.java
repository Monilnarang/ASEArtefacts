package org.apache.commons.math4.legacy.stat;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.math4.legacy.TestUtils;
import org.junit.Assert;
import org.junit.Test;

public final class FrequencyTest_Purified {

    private static final long ONE_LONG = 1L;

    private static final long TWO_LONG = 2L;

    private static final long THREE_LONG = 3L;

    private static final int ONE = 1;

    private static final int TWO = 2;

    private static final int THREE = 3;

    private static final double TOLERANCE = 10E-15d;

    @Test
    public void testCounts_17_testMerged_1() {
        Frequency<Integer> fInteger = new Frequency<>();
        fInteger.addValue(1);
        fInteger.addValue(Integer.valueOf(1));
        fInteger.addValue(ONE);
        fInteger.addValue(2);
        fInteger.addValue(Integer.valueOf(-1));
        Assert.assertEquals("1 count", 3, fInteger.getCount(1));
        Assert.assertEquals("1 count", 3, fInteger.getCount(Integer.valueOf(1)));
        Assert.assertEquals("0 cum pct", 0.2, fInteger.getCumPct(0), TOLERANCE);
        Assert.assertEquals("1 pct", 0.6, fInteger.getPct(Integer.valueOf(1)), TOLERANCE);
        Assert.assertEquals("-2 cum pct", 0, fInteger.getCumPct(-2), TOLERANCE);
        Assert.assertEquals("10 cum pct", 1, fInteger.getCumPct(10), TOLERANCE);
    }

    @Test
    public void testCounts_1_testMerged_2() {
        Frequency<Long> fLong = new Frequency<>();
        Assert.assertEquals("total count", 0, fLong.getSumFreq());
        fLong.addValue(ONE_LONG);
        fLong.addValue(TWO_LONG);
        fLong.addValue(1L);
        Assert.assertEquals("one frequency count", 3, fLong.getCount(1L));
        Assert.assertEquals("two frequency count", 1, fLong.getCount(2L));
        Assert.assertEquals("three frequency count", 0, fLong.getCount(3L));
        Assert.assertEquals("total count", 4, fLong.getSumFreq());
        Assert.assertEquals("zero cumulative frequency", 0, fLong.getCumFreq(0L));
        Assert.assertEquals("one cumulative frequency", 3, fLong.getCumFreq(1L));
        Assert.assertEquals("two cumulative frequency", 4, fLong.getCumFreq(2L));
        Assert.assertEquals("Integer argument cum freq", 4, fLong.getCumFreq(Long.valueOf(2)));
        Assert.assertEquals("five cumulative frequency", 4, fLong.getCumFreq(5L));
        Assert.assertEquals("foo cumulative frequency", 0, fLong.getCumFreq(-1L));
    }

    @Test
    public void testCounts_26_testMerged_3() {
        Frequency<Character> fChar = new Frequency<>();
        Assert.assertEquals(0L, fChar.getCount('a'));
        Assert.assertEquals(0L, fChar.getCumFreq('b'));
        TestUtils.assertEquals(Double.NaN, fChar.getPct('a'), 0.0);
        TestUtils.assertEquals(Double.NaN, fChar.getCumPct('b'), 0.0);
        fChar.addValue('a');
        fChar.addValue('b');
        fChar.addValue('c');
        fChar.addValue('d');
        Assert.assertEquals(1L, fChar.getCount('a'));
        Assert.assertEquals(2L, fChar.getCumFreq('b'));
        Assert.assertEquals(0.25, fChar.getPct('a'), 0.0);
        Assert.assertEquals(0.5, fChar.getCumPct('b'), 0.0);
        Assert.assertEquals(1.0, fChar.getCumPct('e'), 0.0);
    }

    @Test
    public void testCounts_13_testMerged_4() {
        Frequency<String> fString = new Frequency<>();
        fString.addValue("one");
        fString.addValue("One");
        fString.addValue("oNe");
        fString.addValue("Z");
        Assert.assertEquals("one cumulative frequency", 1, fString.getCount("one"));
        Assert.assertEquals("Z cumulative pct", 0.5, fString.getCumPct("Z"), TOLERANCE);
        Assert.assertEquals("z cumulative pct", 1.0, fString.getCumPct("z"), TOLERANCE);
        Assert.assertEquals("Ot cumulative pct", 0.25, fString.getCumPct("Ot"), TOLERANCE);
        fString = new Frequency<>(String.CASE_INSENSITIVE_ORDER);
        Assert.assertEquals("one count", 3, fString.getCount("one"));
        Assert.assertEquals("Z cumulative pct -- case insensitive", 1, fString.getCumPct("Z"), TOLERANCE);
        Assert.assertEquals("z cumulative pct -- case insensitive", 1, fString.getCumPct("z"), TOLERANCE);
    }
}
