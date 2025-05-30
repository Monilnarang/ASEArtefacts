package org.apache.druid.segment.realtime.appenderator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import org.apache.druid.jackson.DefaultObjectMapper;
import org.apache.druid.java.util.common.Intervals;
import org.apache.druid.timeline.partition.LinearShardSpec;
import org.junit.Assert;
import org.junit.Test;
import java.util.HashMap;
import java.util.Map;

public class CommittedTest_Purified {

    private static final ObjectMapper OBJECT_MAPPER = new DefaultObjectMapper();

    private static final SegmentIdWithShardSpec IDENTIFIER_OBJECT1 = new SegmentIdWithShardSpec("foo", Intervals.of("2000/2001"), "2000", new LinearShardSpec(1));

    private static final SegmentIdWithShardSpec IDENTIFIER_OBJECT2 = new SegmentIdWithShardSpec("foo", Intervals.of("2001/2002"), "2001", new LinearShardSpec(1));

    private static final SegmentIdWithShardSpec IDENTIFIER_OBJECT3 = new SegmentIdWithShardSpec("foo", Intervals.of("2001/2002"), "2001", new LinearShardSpec(2));

    private static final String IDENTIFIER1 = IDENTIFIER_OBJECT1.toString();

    private static final String IDENTIFIER2 = IDENTIFIER_OBJECT2.toString();

    private static final String IDENTIFIER3 = IDENTIFIER_OBJECT3.toString();

    private static Committed fixedInstance() {
        final Map<String, Integer> hydrants = new HashMap<>();
        hydrants.put(IDENTIFIER1, 3);
        hydrants.put(IDENTIFIER2, 2);
        return new Committed(hydrants, ImmutableMap.of("metadata", "foo"));
    }

    @Test
    public void testGetCommittedHydrant_1() {
        Assert.assertEquals(3, fixedInstance().getCommittedHydrants(IDENTIFIER1));
    }

    @Test
    public void testGetCommittedHydrant_2() {
        Assert.assertEquals(2, fixedInstance().getCommittedHydrants(IDENTIFIER2));
    }

    @Test
    public void testGetCommittedHydrant_3() {
        Assert.assertEquals(0, fixedInstance().getCommittedHydrants(IDENTIFIER3));
    }

    @Test
    public void testWithout_1() {
        Assert.assertEquals(0, fixedInstance().without(IDENTIFIER1).getCommittedHydrants(IDENTIFIER1));
    }

    @Test
    public void testWithout_2() {
        Assert.assertEquals(2, fixedInstance().without(IDENTIFIER1).getCommittedHydrants(IDENTIFIER2));
    }
}
