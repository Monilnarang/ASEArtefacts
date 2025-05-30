package org.apache.druid.segment.incremental;

import org.apache.druid.java.util.common.parsers.ParseException;
import org.junit.Assert;
import org.junit.Test;

public class IncrementalIndexAddResultTest_Purified {

    @Test
    public void testIsRowAdded_1() {
        Assert.assertTrue(new IncrementalIndexAddResult(0, 0L).isRowAdded());
    }

    @Test
    public void testIsRowAdded_2() {
        Assert.assertFalse(new IncrementalIndexAddResult(0, 0L, "test").isRowAdded());
    }

    @Test
    public void testIsRowAdded_3() {
        Assert.assertFalse(new IncrementalIndexAddResult(0, 0L, new ParseException(null, "test")).isRowAdded());
    }

    @Test
    public void testHasParseException_1() {
        Assert.assertFalse(new IncrementalIndexAddResult(0, 0L).hasParseException());
    }

    @Test
    public void testHasParseException_2() {
        Assert.assertFalse(new IncrementalIndexAddResult(0, 0L, "test").hasParseException());
    }

    @Test
    public void testHasParseException_3() {
        Assert.assertTrue(new IncrementalIndexAddResult(0, 0L, new ParseException(null, "test")).hasParseException());
    }
}
