package org.apache.druid.indexer;

import com.google.common.primitives.Bytes;
import org.apache.druid.java.util.common.DateTimes;
import org.apache.druid.java.util.common.Pair;
import org.hamcrest.number.OrderingComparison;
import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BucketTest_Purified {

    Bucket bucket;

    int shardNum;

    int partitionNum;

    DateTime time;

    @Before
    public void setUp() {
        time = new DateTime(2014, 11, 24, 10, 30, ISOChronology.getInstanceUTC());
        shardNum = 1;
        partitionNum = 1;
        bucket = new Bucket(shardNum, time, partitionNum);
    }

    @After
    public void tearDown() {
        bucket = null;
    }

    @Test
    public void testEquals_1() {
        Assert.assertFalse("Object should not be equals to NULL", bucket.equals(null));
    }

    @Test
    public void testEquals_2() {
        Assert.assertFalse("Objects do not have the same Class", bucket.equals(0));
    }

    @Test
    public void testEquals_3() {
        Assert.assertFalse("Objects do not have the same partitionNum", bucket.equals(new Bucket(shardNum, time, partitionNum + 1)));
    }

    @Test
    public void testEquals_4() {
        Assert.assertFalse("Objects do not have the same shardNum", bucket.equals(new Bucket(shardNum + 1, time, partitionNum)));
    }

    @Test
    public void testEquals_5() {
        Assert.assertFalse("Objects do not have the same time", bucket.equals(new Bucket(shardNum, DateTimes.nowUtc(), partitionNum)));
    }

    @Test
    public void testEquals_6() {
        Assert.assertFalse("Object do have NULL time", bucket.equals(new Bucket(shardNum, null, partitionNum)));
    }

    @Test
    public void testEquals_7() {
        Assert.assertTrue("Objects must be the same", bucket.equals(new Bucket(shardNum, time, partitionNum)));
    }
}
