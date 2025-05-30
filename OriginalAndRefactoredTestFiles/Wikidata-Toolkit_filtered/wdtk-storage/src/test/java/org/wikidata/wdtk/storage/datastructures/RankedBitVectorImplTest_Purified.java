package org.wikidata.wdtk.storage.datastructures;

import java.util.Iterator;
import org.junit.Assert;
import org.junit.Test;

public class RankedBitVectorImplTest_Purified {

    void assertCorrectCount(RankedBitVector bv) {
        for (long index = 0; index < bv.size(); index++) {
            assertCorrectCount(bv, index);
        }
    }

    void assertCorrectCount(RankedBitVector bv, long position) {
        long expectedCountBitsFalse = countBits(bv, false, position);
        long computedCountBitsFalse = bv.countBits(false, position);
        Assert.assertEquals(expectedCountBitsFalse, computedCountBitsFalse);
        long expectedCountBitsTrue = countBits(bv, true, position);
        long computedCountBitsTrue = bv.countBits(true, position);
        Assert.assertEquals(expectedCountBitsTrue, computedCountBitsTrue);
    }

    void assertCorrectFindPosition(RankedBitVector bv) {
        for (long index = 0; index < bv.size(); index++) {
            assertCorrectFindPosition(bv, index);
        }
    }

    void assertCorrectFindPosition(RankedBitVector bv, long nOccurrences) {
        long expectedFindPositionFalse = findPosition(bv, false, nOccurrences);
        long computedFindPositionFalse = bv.findPosition(false, nOccurrences);
        Assert.assertEquals(expectedFindPositionFalse, computedFindPositionFalse);
        long expectedFindPositionTrue = findPosition(bv, true, nOccurrences);
        long computedFindPositionTrue = bv.findPosition(true, nOccurrences);
        Assert.assertEquals(expectedFindPositionTrue, computedFindPositionTrue);
    }

    void assertEqualsForBitVector(RankedBitVector bv0, RankedBitVector bv1) {
        Assert.assertEquals(bv0, bv0);
        Assert.assertEquals(bv0, bv1);
        Assert.assertEquals(bv1, bv0);
        Assert.assertEquals(bv0.hashCode(), bv1.hashCode());
    }

    long countBits(BitVector bv, boolean bit, long position) {
        long ret = 0;
        for (long index = 0; index <= position; index++) {
            if (bv.getBit(index) == bit) {
                ret++;
            }
        }
        return ret;
    }

    long findPosition(BitVector bv, boolean bit, long nOccurrences) {
        if (nOccurrences == 0) {
            return RankedBitVector.NOT_FOUND;
        }
        long accumOccurrences = 0;
        for (long index = 0; index < bv.size(); index++) {
            if (bv.getBit(index) == bit) {
                accumOccurrences++;
            }
            if (accumOccurrences == nOccurrences) {
                return index;
            }
        }
        return RankedBitVector.NOT_FOUND;
    }

    @Test
    public void testEmptyBitVector_1_testMerged_1() {
        RankedBitVectorImpl bv0 = new RankedBitVectorImpl();
        Assert.assertEquals(0, bv0.size());
        assertCorrectCount(bv0);
        assertCorrectFindPosition(bv0);
        Assert.assertNotEquals(bv0, new Object());
        Assert.assertEquals(bv0, new BitVectorImpl());
    }

    @Test
    public void testEmptyBitVector_6_testMerged_2() {
        RankedBitVector bv1 = new RankedBitVectorImpl();
        RankedBitVectorImpl bv2 = new RankedBitVectorImpl(0);
        assertEqualsForBitVector(bv1, bv2);
        assertCorrectCount(bv2);
        assertCorrectFindPosition(bv2);
    }
}
