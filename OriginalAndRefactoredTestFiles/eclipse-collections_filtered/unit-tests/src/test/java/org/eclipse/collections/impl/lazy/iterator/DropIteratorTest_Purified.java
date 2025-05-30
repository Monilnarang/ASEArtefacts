package org.eclipse.collections.impl.lazy.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.impl.list.Interval;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DropIteratorTest_Purified {

    private static void assertElements(Iterator<Integer> iterator, int count, int size) {
        for (int i = count; i < size; i++) {
            assertTrue(iterator.hasNext());
            assertEquals(Integer.valueOf(i + 1), iterator.next());
        }
        assertFalse(iterator.hasNext());
    }

    @Test
    public void iterator_1_testMerged_1() {
        Interval list = Interval.oneTo(5);
        Iterator<Integer> iterator1 = new DropIterator<>(list.iterator(), 2);
        assertElements(iterator1, 2, list.size());
        Iterator<Integer> iterator2 = new DropIterator<>(list, 5);
        assertElements(iterator2, 5, list.size());
        Iterator<Integer> iterator3 = new DropIterator<>(list, 10);
        assertElements(iterator3, 5, list.size());
        Iterator<Integer> iterator4 = new DropIterator<>(list, 0);
        assertElements(iterator4, 0, list.size());
    }

    @Test
    public void iterator_5() {
        Iterator<Integer> iterator5 = new DropIterator<>(Lists.fixedSize.of(), 0);
        assertElements(iterator5, 0, Lists.fixedSize.<Integer>of().size());
    }
}
