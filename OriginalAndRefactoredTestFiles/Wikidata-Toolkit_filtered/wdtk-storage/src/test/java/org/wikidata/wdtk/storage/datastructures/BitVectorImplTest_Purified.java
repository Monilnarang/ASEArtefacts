package org.wikidata.wdtk.storage.datastructures;

import org.junit.Assert;
import org.junit.Test;

public class BitVectorImplTest_Purified {

    void assertEqualsForBitVector(BitVector bv0, BitVector bv1) {
        Assert.assertEquals(bv0, bv0);
        Assert.assertEquals(bv0, bv1);
        Assert.assertEquals(bv1, bv0);
        Assert.assertEquals(bv0.hashCode(), bv1.hashCode());
    }

    @Test
    public void testGetOutOfRange_1() {
        Assert.assertFalse(new BitVectorImpl().getBit(1));
    }

    @Test
    public void testGetOutOfRange_2() {
        Assert.assertFalse(new BitVectorImpl().getBit(Long.MAX_VALUE));
    }
}
