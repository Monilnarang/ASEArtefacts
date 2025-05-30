package org.eclipse.collections.impl.set.mutable;

import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.TreeSet;
import org.eclipse.collections.api.bag.MutableBag;
import org.eclipse.collections.api.factory.Bags;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SynchronizedMutableSet2Test_Purified extends AbstractMutableSetTestCase {

    @Override
    protected <T> MutableSet<T> newWith(T... littleElements) {
        return new SynchronizedMutableSet<>(SetAdapter.adapt(new HashSet<>(UnifiedSet.newSetWith(littleElements))));
    }

    @Override
    public void selectInstancesOf() {
        MutableSet<Number> numbers = new SynchronizedMutableSet<Number>(SetAdapter.adapt(new TreeSet<>((o1, o2) -> Double.compare(o1.doubleValue(), o2.doubleValue())))).withAll(FastList.newListWith(1, 2.0, 3, 4.0, 5));
        MutableSet<Integer> integers = numbers.selectInstancesOf(Integer.class);
        assertEquals(UnifiedSet.newSetWith(1, 3, 5), integers);
        assertEquals(FastList.newListWith(1, 3, 5), integers.toList());
    }

    @Test
    @Override
    public void getFirst_1() {
        assertNotNull(this.newWith(1, 2, 3).getFirst());
    }

    @Test
    @Override
    public void getFirst_2() {
        assertNull(this.newWith().getFirst());
    }

    @Test
    @Override
    public void getFirst_3() {
        assertEquals(Integer.valueOf(1), this.newWith(1).getFirst());
    }

    @Test
    @Override
    public void getFirst_4() {
        int first = this.newWith(1, 2).getFirst().intValue();
        assertTrue(first == 1 || first == 2);
    }

    @Test
    @Override
    public void getLast_1() {
        assertNotNull(this.newWith(1, 2, 3).getLast());
    }

    @Test
    @Override
    public void getLast_2() {
        assertNull(this.newWith().getLast());
    }

    @Test
    @Override
    public void getLast_3() {
        assertEquals(Integer.valueOf(1), this.newWith(1).getLast());
    }

    @Test
    @Override
    public void getLast_4() {
        int last = this.newWith(1, 2).getLast().intValue();
        assertTrue(last == 1 || last == 2);
    }
}
