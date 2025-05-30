package org.apache.druid.frame.processor;

import it.unimi.dsi.fastutil.ints.IntSet;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Assert;
import org.junit.Test;

public class ReturnOrAwaitTest_Purified {

    @Test
    public void testToString_1() {
        Assert.assertEquals("await=any{0, 1}", ReturnOrAwait.awaitAny(IntSet.of(0, 1)).toString());
    }

    @Test
    public void testToString_2() {
        Assert.assertEquals("await=all{0, 1}", ReturnOrAwait.awaitAll(2).toString());
    }

    @Test
    public void testToString_3() {
        Assert.assertEquals("return=xyzzy", ReturnOrAwait.returnObject("xyzzy").toString());
    }
}
