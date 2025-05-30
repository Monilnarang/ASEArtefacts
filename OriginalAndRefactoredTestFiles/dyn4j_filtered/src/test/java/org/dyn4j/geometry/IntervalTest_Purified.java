package org.dyn4j.geometry;

import junit.framework.TestCase;
import org.junit.Test;

public class IntervalTest_Purified {

    @Test
    public void clamp_1_testMerged_1() {
        Interval i = new Interval(-1.0, 6.5);
        TestCase.assertEquals(2.0, i.clamp(2.0));
        TestCase.assertEquals(-1.0, i.clamp(-2.0));
        TestCase.assertEquals(6.5, i.clamp(7.0));
    }

    @Test
    public void clamp_2() {
        TestCase.assertEquals(2.0, Interval.clamp(2.0, -1.0, 6.5));
    }
}
