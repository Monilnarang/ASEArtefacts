package org.eclipse.collections.impl.map.sorted.mutable;

import java.util.Map;
import java.util.TreeMap;
import org.eclipse.collections.impl.block.factory.Comparators;
import org.eclipse.collections.impl.factory.SortedMaps;
import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UnmodifiableSortedMapTest_Purified {

    private final UnmodifiableSortedMap<Integer, String> map = new UnmodifiableSortedMap<>(new TreeMap<>(SortedMaps.mutable.of(1, "1", 2, "2", 3, "3", 4, "4")));

    private final UnmodifiableSortedMap<Integer, String> revMap = new UnmodifiableSortedMap<>(new TreeMap<>(SortedMaps.mutable.of(Comparators.reverseNaturalOrder(), 1, "1", 2, "2", 3, "3", 4, "4")));

    private void checkMutability(Map<Integer, String> map) {
        assertThrows(UnsupportedOperationException.class, () -> map.put(3, "1"));
        assertThrows(UnsupportedOperationException.class, () -> map.putAll(SortedMaps.mutable.of(1, "1", 2, "2")));
        assertThrows(UnsupportedOperationException.class, () -> map.remove(2));
        assertThrows(UnsupportedOperationException.class, map::clear);
    }

    @Test
    public void firstKey_1() {
        assertEquals(1, this.map.firstKey().intValue());
    }

    @Test
    public void firstKey_2() {
        assertEquals(4, this.revMap.firstKey().intValue());
    }

    @Test
    public void lasKey_1() {
        assertEquals(4, this.map.lastKey().intValue());
    }

    @Test
    public void lasKey_2() {
        assertEquals(1, this.revMap.lastKey().intValue());
    }
}
