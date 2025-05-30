package org.apache.druid.query.dimension;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import org.apache.commons.lang3.mutable.MutableInt;
import org.apache.druid.java.util.common.NonnullPair;
import org.apache.druid.segment.DimensionSelector;
import org.apache.druid.segment.data.IndexedInts;
import org.apache.druid.testing.InitializedNullHandlingTest;
import org.junit.Assert;
import org.junit.Test;
import java.util.List;
import java.util.function.Supplier;

public class ListFilteredDimensionSpecDimensionSelectorTest_Purified extends InitializedNullHandlingTest {

    private final ListFilteredDimensionSpec targetWithAllowList = new ListFilteredDimensionSpec(new DefaultDimensionSpec("foo", "bar"), ImmutableSet.of("val1_1", "val2_2", "val2_3"), true);

    private final ListFilteredDimensionSpec targetWithDenyList = new ListFilteredDimensionSpec(new DefaultDimensionSpec("foo", "bar"), ImmutableSet.of("val1_1", "val2_2", "val2_3"), false);

    private final List<List<String>> data = ImmutableList.of(ImmutableList.of("val1_1", "val1_2"), ImmutableList.of("val2_1", "val2_2", "val2_3"), ImmutableList.of("val3_1"));

    private NonnullPair<Object2IntMap<String>, Int2ObjectMap<String>> createDictionaries(List<List<String>> values) {
        Object2IntMap<String> dictionary = new Object2IntOpenHashMap<>();
        Int2ObjectMap<String> reverseDictionary = new Int2ObjectOpenHashMap<>();
        MutableInt nextId = new MutableInt(0);
        for (List<String> multiValue : values) {
            for (String value : multiValue) {
                int dictId = dictionary.computeIntIfAbsent(value, k -> nextId.getAndIncrement());
                reverseDictionary.putIfAbsent(dictId, value);
            }
        }
        return new NonnullPair<>(dictionary, reverseDictionary);
    }

    private void assertAllowListFiltering(RowSupplier rowSupplier, DimensionSelector selector) {
        rowSupplier.set(data.get(0));
        IndexedInts encodedRow = selector.getRow();
        Assert.assertEquals(1, encodedRow.size());
        Assert.assertEquals("val1_1", selector.lookupName(encodedRow.get(0)));
        rowSupplier.set(data.get(1));
        encodedRow = selector.getRow();
        Assert.assertEquals(2, encodedRow.size());
        Assert.assertEquals("val2_2", selector.lookupName(encodedRow.get(0)));
        Assert.assertEquals("val2_3", selector.lookupName(encodedRow.get(1)));
        rowSupplier.set(data.get(2));
        encodedRow = selector.getRow();
        Assert.assertEquals(0, encodedRow.size());
    }

    private void assertDenyListFiltering(RowSupplier rowSupplier, DimensionSelector selector) {
        rowSupplier.set(data.get(0));
        IndexedInts encodedRow = selector.getRow();
        Assert.assertEquals(1, encodedRow.size());
        Assert.assertEquals("val1_2", selector.lookupName(encodedRow.get(0)));
        rowSupplier.set(data.get(1));
        encodedRow = selector.getRow();
        Assert.assertEquals(1, encodedRow.size());
        Assert.assertEquals("val2_1", selector.lookupName(encodedRow.get(0)));
        rowSupplier.set(data.get(2));
        encodedRow = selector.getRow();
        Assert.assertEquals(1, encodedRow.size());
        Assert.assertEquals("val3_1", selector.lookupName(encodedRow.get(0)));
    }

    private static class RowSupplier implements Supplier<List<String>> {

        private List<String> row;

        public void set(List<String> row) {
            this.row = row;
        }

        @Override
        public List<String> get() {
            return row;
        }
    }

    @Test
    public void testNullDimensionSelectorReturnNull_1() {
        Assert.assertNull(targetWithAllowList.decorate((DimensionSelector) null));
    }

    @Test
    public void testNullDimensionSelectorReturnNull_2() {
        Assert.assertNull(targetWithDenyList.decorate((DimensionSelector) null));
    }
}
