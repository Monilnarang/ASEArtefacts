package org.apache.druid.segment.vector;

import com.google.common.collect.Sets;
import org.apache.druid.collections.IntSetTestUtility;
import org.apache.druid.collections.bitmap.ImmutableBitmap;
import org.apache.druid.collections.bitmap.MutableBitmap;
import org.apache.druid.collections.bitmap.WrappedBitSetBitmap;
import org.apache.druid.collections.bitmap.WrappedConciseBitmap;
import org.apache.druid.collections.bitmap.WrappedImmutableConciseBitmap;
import org.apache.druid.collections.bitmap.WrappedRoaringBitmap;
import org.apache.druid.extendedset.intset.ImmutableConciseSet;
import org.junit.Assert;
import org.junit.Test;
import org.roaringbitmap.PeekableIntIterator;
import java.util.ArrayList;
import java.util.Set;

public class VectorSelectorUtilsTest_Purified {

    private static final Set<Integer> NULLS = IntSetTestUtility.getSetBits();

    private static final Set<Integer> NULLS_PATTERN = alternatngPattern(10, 12);

    public static void populate(MutableBitmap bitmap, Set<Integer> setBits) {
        for (int i : setBits) {
            bitmap.add(i);
        }
    }

    private static Set<Integer> alternatngPattern(int smallSize, int rowCount) {
        ArrayList<Integer> bits = new ArrayList<>();
        boolean flipped = true;
        for (int i = 0; i < rowCount; i++) {
            if (i > 0 && i % smallSize == 0) {
                flipped = !flipped;
            }
            if (flipped) {
                bits.add(i);
            }
        }
        return Sets.newTreeSet(bits);
    }

    private void assertNullVector(ImmutableBitmap bitmap, Set<Integer> nulls) {
        PeekableIntIterator iterator = bitmap.peekableIterator();
        final int vectorSize = 32;
        final boolean[] nullVector = new boolean[vectorSize];
        ReadableVectorOffset someOffset = new NoFilterVectorOffset(vectorSize, 0, vectorSize);
        VectorSelectorUtils.populateNullVector(nullVector, someOffset, iterator);
        for (int i = 0; i < vectorSize; i++) {
            Assert.assertEquals(nulls.contains(i), nullVector[i]);
        }
        iterator = bitmap.peekableIterator();
        final int smallerVectorSize = 8;
        boolean[] smallVector = null;
        for (int offset = 0; offset < smallerVectorSize * 4; offset += smallerVectorSize) {
            ReadableVectorOffset smallOffset = new NoFilterVectorOffset(smallerVectorSize, offset, offset + smallerVectorSize);
            smallVector = VectorSelectorUtils.populateNullVector(smallVector, smallOffset, iterator);
            for (int i = 0; i < smallerVectorSize; i++) {
                if (smallVector == null) {
                    Assert.assertFalse(nulls.contains(offset + i));
                } else {
                    Assert.assertEquals(nulls.contains(offset + i), smallVector[i]);
                }
            }
        }
        iterator = bitmap.peekableIterator();
        ReadableVectorOffset allTheNulls = new BitmapVectorOffset(nulls.size(), bitmap, 0, 32);
        smallVector = VectorSelectorUtils.populateNullVector(smallVector, allTheNulls, iterator);
        for (int i = 0; i < nulls.size(); i++) {
            Assert.assertTrue(smallVector[i]);
        }
    }

    @Test
    public void testConciseMutableNullVector_1() {
        final WrappedConciseBitmap bitmap = new WrappedConciseBitmap();
        populate(bitmap, NULLS);
        assertNullVector(bitmap, NULLS);
    }

    @Test
    public void testConciseMutableNullVector_2() {
        final WrappedConciseBitmap bitmap2 = new WrappedConciseBitmap();
        populate(bitmap2, NULLS_PATTERN);
        assertNullVector(bitmap2, NULLS_PATTERN);
    }

    @Test
    public void testRoaringMutableNullVector_1() {
        WrappedRoaringBitmap bitmap = new WrappedRoaringBitmap();
        populate(bitmap, NULLS);
        assertNullVector(bitmap, NULLS);
    }

    @Test
    public void testRoaringMutableNullVector_2() {
        WrappedRoaringBitmap bitmap2 = new WrappedRoaringBitmap();
        populate(bitmap2, NULLS_PATTERN);
        assertNullVector(bitmap2, NULLS_PATTERN);
    }

    @Test
    public void testRoaringImmutableNullVector_1() {
        WrappedRoaringBitmap bitmap = new WrappedRoaringBitmap();
        populate(bitmap, NULLS);
        assertNullVector(bitmap.toImmutableBitmap(), NULLS);
    }

    @Test
    public void testRoaringImmutableNullVector_2() {
        WrappedRoaringBitmap bitmap2 = new WrappedRoaringBitmap();
        populate(bitmap2, NULLS_PATTERN);
        assertNullVector(bitmap2.toImmutableBitmap(), NULLS_PATTERN);
    }
}
