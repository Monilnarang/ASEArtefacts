package org.apache.druid.indexer;

import org.junit.Assert;
import org.junit.Test;

public class CompactionEngineTest_Purified {

    @Test
    public void testToString_1() {
        Assert.assertEquals("native", CompactionEngine.NATIVE.toString());
    }

    @Test
    public void testToString_2() {
        Assert.assertEquals("msq", CompactionEngine.MSQ.toString());
    }
}
