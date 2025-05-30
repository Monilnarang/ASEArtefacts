package org.apache.druid.segment.realtime;

import org.apache.druid.java.util.common.ISE;
import org.apache.druid.java.util.common.Pair;
import org.apache.druid.segment.IncrementalIndexSegment;
import org.apache.druid.segment.QueryableIndexSegment;
import org.apache.druid.segment.ReferenceCountingSegment;
import org.apache.druid.segment.SegmentReference;
import org.apache.druid.segment.TestIndex;
import org.apache.druid.testing.InitializedNullHandlingTest;
import org.apache.druid.timeline.SegmentId;
import org.joda.time.Interval;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.Closeable;
import java.io.IOException;
import java.util.Optional;
import java.util.function.Function;

public class FireHydrantTest_Purified extends InitializedNullHandlingTest {

    private IncrementalIndexSegment incrementalIndexSegment;

    private QueryableIndexSegment queryableIndexSegment;

    private FireHydrant hydrant;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setup() {
        incrementalIndexSegment = new IncrementalIndexSegment(TestIndex.getIncrementalTestIndex(), SegmentId.dummy("test"));
        queryableIndexSegment = new QueryableIndexSegment(TestIndex.getMMappedTestIndex(), SegmentId.dummy("test"));
        hydrant = new FireHydrant(incrementalIndexSegment, 0);
    }

    @Test
    public void testGetIncrementedSegmentNotSwapped_1() {
        Assert.assertEquals(0, hydrant.getHydrantSegment().getNumReferences());
    }

    @Test
    public void testGetIncrementedSegmentNotSwapped_2_testMerged_2() {
        ReferenceCountingSegment segment = hydrant.getIncrementedSegment();
        Assert.assertNotNull(segment);
        Assert.assertTrue(segment.getBaseSegment() == incrementalIndexSegment);
        Assert.assertEquals(1, segment.getNumReferences());
    }
}
