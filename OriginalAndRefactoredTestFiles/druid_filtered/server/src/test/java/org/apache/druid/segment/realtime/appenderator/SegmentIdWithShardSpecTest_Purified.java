package org.apache.druid.segment.realtime.appenderator;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.druid.jackson.DefaultObjectMapper;
import org.apache.druid.java.util.common.Intervals;
import org.apache.druid.timeline.partition.NumberedShardSpec;
import org.joda.time.Interval;
import org.junit.Assert;
import org.junit.Test;

public class SegmentIdWithShardSpecTest_Purified {

    private static final String DATA_SOURCE = "foo";

    private static final Interval INTERVAL = Intervals.of("2000/PT1H");

    private static final String VERSION = "v1";

    private static final NumberedShardSpec SHARD_SPEC_0 = new NumberedShardSpec(0, 2);

    private static final NumberedShardSpec SHARD_SPEC_1 = new NumberedShardSpec(1, 2);

    private static final SegmentIdWithShardSpec ID_0 = new SegmentIdWithShardSpec(DATA_SOURCE, INTERVAL, VERSION, SHARD_SPEC_0);

    private static final SegmentIdWithShardSpec ID_1 = new SegmentIdWithShardSpec(DATA_SOURCE, INTERVAL, VERSION, SHARD_SPEC_1);

    @Test
    public void testAsString_1() {
        Assert.assertEquals("foo_2000-01-01T00:00:00.000Z_2000-01-01T01:00:00.000Z_v1", ID_0.toString());
    }

    @Test
    public void testAsString_2() {
        Assert.assertEquals("foo_2000-01-01T00:00:00.000Z_2000-01-01T01:00:00.000Z_v1_1", ID_1.toString());
    }
}
