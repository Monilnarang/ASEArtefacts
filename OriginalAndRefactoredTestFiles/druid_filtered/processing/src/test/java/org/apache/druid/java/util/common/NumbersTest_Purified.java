package org.apache.druid.java.util.common;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class NumbersTest_Purified {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testParseLong_1() {
        final String strVal = "100";
        Assert.assertEquals(100L, Numbers.parseLong(strVal));
    }

    @Test
    public void testParseLong_2() {
        final Long longVal = 100L;
        Assert.assertEquals(100L, Numbers.parseLong(longVal));
    }

    @Test
    public void testParseLong_3() {
        final Double doubleVal = 100.;
        Assert.assertEquals(100L, Numbers.parseLong(doubleVal));
    }

    @Test
    public void testParseInt_1() {
        final String strVal = "100";
        Assert.assertEquals(100, Numbers.parseInt(strVal));
    }

    @Test
    public void testParseInt_2() {
        final Integer longVal = 100;
        Assert.assertEquals(100, Numbers.parseInt(longVal));
    }

    @Test
    public void testParseInt_3() {
        final Float floatVal = 100.F;
        Assert.assertEquals(100, Numbers.parseInt(floatVal));
    }

    @Test
    public void testParseBoolean_1() {
        final String strVal = "false";
        Assert.assertEquals(false, Numbers.parseBoolean(strVal));
    }

    @Test
    public void testParseBoolean_2() {
        final Boolean booleanVal = Boolean.FALSE;
        Assert.assertEquals(false, Numbers.parseBoolean(booleanVal));
    }

    @Test
    public void testParseLongObject_1() {
        Assert.assertEquals(null, Numbers.parseLongObject(null));
    }

    @Test
    public void testParseLongObject_2() {
        Assert.assertEquals((Long) 1L, Numbers.parseLongObject("1"));
    }

    @Test
    public void testParseLongObject_3() {
        Assert.assertEquals((Long) 32L, Numbers.parseLongObject("32.1243"));
    }

    @Test
    public void testParseDoubleObject_1() {
        Assert.assertEquals(null, Numbers.parseLongObject(null));
    }

    @Test
    public void testParseDoubleObject_2() {
        Assert.assertEquals((Double) 1.0, Numbers.parseDoubleObject("1"));
    }

    @Test
    public void testParseDoubleObject_3() {
        Assert.assertEquals((Double) 32.1243, Numbers.parseDoubleObject("32.1243"));
    }
}
