package io.undertow.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import org.junit.Assert;
import org.junit.Test;

public class ByteRangeTestCase_Purified {

    @Test
    public void testParse_1() {
        Assert.assertNull(ByteRange.parse(null));
    }

    @Test
    public void testParse_2() {
        Assert.assertNull(ByteRange.parse("foo"));
    }

    @Test
    public void testParse_3() {
        Assert.assertNull(ByteRange.parse("bytes=1"));
    }

    @Test
    public void testParse_4() {
        Assert.assertNull(ByteRange.parse("bytes=a-"));
    }

    @Test
    public void testParse_5() {
        Assert.assertNull(ByteRange.parse("foobarbaz"));
    }

    @Test
    public void testParse_6() {
        Assert.assertNull(ByteRange.parse("bytes=--1"));
    }

    @Test
    public void testParse_7() {
        Assert.assertEquals(1, ByteRange.parse("bytes=2-").getRanges());
    }

    @Test
    public void testParse_8() {
        Assert.assertEquals(1, ByteRange.parse("bytes=-20").getRanges());
    }
}
