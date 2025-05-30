package org.apache.druid.segment.join;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import org.apache.druid.segment.DimensionSelector;
import org.apache.druid.segment.data.IndexedInts;
import org.junit.Assert;
import org.junit.Test;

public class PossiblyNullDimensionSelectorTest_Purified {

    private boolean isNull = false;

    private final DimensionSelector onNullSelector = makeSelector(DimensionSelector.constant(null));

    private final DimensionSelector onNonnullSelector = makeSelector(DimensionSelector.constant("foo"));

    private DimensionSelector makeSelector(final DimensionSelector baseSelector) {
        return new PossiblyNullDimensionSelector(baseSelector, () -> isNull);
    }

    private static void assertRowsEqual(final int[] expected, final IndexedInts actual) {
        Assert.assertEquals(IntArrayList.wrap(expected), toList(actual));
    }

    private static IntList toList(final IndexedInts ints) {
        final IntList retVal = new IntArrayList(ints.size());
        final int size = ints.size();
        for (int i = 0; i < size; i++) {
            retVal.add(ints.get(i));
        }
        return retVal;
    }

    @Test
    public void test_lookupName_onNonnullSelector_1() {
        Assert.assertNull(onNonnullSelector.lookupName(0));
    }

    @Test
    public void test_lookupName_onNonnullSelector_2() {
        Assert.assertEquals("foo", onNonnullSelector.lookupName(1));
    }

    @Test
    public void test_lookupId_onNonnullSelector_1() {
        Assert.assertEquals(0, onNonnullSelector.idLookup().lookupId(null));
    }

    @Test
    public void test_lookupId_onNonnullSelector_2() {
        Assert.assertEquals(1, onNonnullSelector.idLookup().lookupId("foo"));
    }
}
