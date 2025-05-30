package org.eclipse.collections.impl.set.mutable;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.eclipse.collections.api.LazyIterable;
import org.eclipse.collections.api.RichIterable;
import org.eclipse.collections.api.bag.sorted.ImmutableSortedBag;
import org.eclipse.collections.api.bag.sorted.MutableSortedBag;
import org.eclipse.collections.api.block.function.Function;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.api.set.UnsortedSetIterable;
import org.eclipse.collections.api.tuple.Pair;
import org.eclipse.collections.impl.Counter;
import org.eclipse.collections.impl.IntegerWithCast;
import org.eclipse.collections.impl.bag.sorted.mutable.TreeBag;
import org.eclipse.collections.impl.block.factory.Predicates;
import org.eclipse.collections.impl.block.factory.Predicates2;
import org.eclipse.collections.impl.block.factory.Procedures;
import org.eclipse.collections.impl.block.procedure.CollectionAddProcedure;
import org.eclipse.collections.impl.collection.mutable.AbstractCollectionTestCase;
import org.eclipse.collections.impl.list.Interval;
import org.eclipse.collections.impl.list.fixed.ArrayAdapter;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.test.Verify;
import org.eclipse.collections.impl.utility.Iterate;
import org.junit.jupiter.api.Test;
import static org.eclipse.collections.impl.factory.Iterables.iSet;
import static org.eclipse.collections.impl.factory.Iterables.mList;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class AbstractMutableSetTestCase_Purified extends AbstractCollectionTestCase {

    protected static final Integer COLLISION_1 = 0;

    protected static final Integer COLLISION_2 = 17;

    protected static final Integer COLLISION_3 = 34;

    protected static final Integer COLLISION_4 = 51;

    protected static final Integer COLLISION_5 = 68;

    protected static final Integer COLLISION_6 = 85;

    protected static final Integer COLLISION_7 = 102;

    protected static final Integer COLLISION_8 = 119;

    protected static final Integer COLLISION_9 = 136;

    protected static final Integer COLLISION_10 = 152;

    protected static final MutableList<Integer> COLLISIONS = FastList.newListWith(COLLISION_1, COLLISION_2, COLLISION_3, COLLISION_4, COLLISION_5);

    protected static final MutableList<Integer> MORE_COLLISIONS = FastList.newList(COLLISIONS).with(COLLISION_6, COLLISION_7, COLLISION_8, COLLISION_9);

    protected static final int SIZE = 8;

    protected static final String[] FREQUENT_COLLISIONS = { "\u9103\ufffe", "\u9104\uffdf", "\u9105\uffc0", "\u9106\uffa1", "\u9107\uff82", "\u9108\uff63", "\u9109\uff44", "\u910a\uff25", "\u910b\uff06", "\u910c\ufee7" };

    @Override
    protected abstract <T> MutableSet<T> newWith(T... littleElements);

    private void assertIsEmpty(boolean isEmpty, MutableSet<?> set) {
        assertEquals(isEmpty, set.isEmpty());
        assertEquals(!isEmpty, set.notEmpty());
    }

    @Override
    @Test
    public void select_1() {
        Verify.assertContainsAll(this.newWith(1, 2, 3, 4, 5).select(Predicates.lessThan(3)), 1, 2);
    }

    @Override
    @Test
    public void select_2() {
        Verify.assertContainsAll(this.newWith(-1, 2, 3, 4, 5).select(Predicates.lessThan(3), FastList.newList()), -1, 2);
    }

    @Override
    @Test
    public void reject_1() {
        Verify.assertContainsAll(this.newWith(1, 2, 3, 4).reject(Predicates.lessThan(3)), 3, 4);
    }

    @Override
    @Test
    public void reject_2() {
        Verify.assertContainsAll(this.newWith(1, 2, 3, 4).reject(Predicates.lessThan(3), FastList.newList()), 3, 4);
    }

    @Override
    @Test
    public void getFirst_1() {
        assertNotNull(this.newWith(1, 2, 3).getFirst());
    }

    @Override
    @Test
    public void getFirst_2() {
        assertNull(this.newWith().getFirst());
    }

    @Override
    @Test
    public void getLast_1() {
        assertNotNull(this.newWith(1, 2, 3).getLast());
    }

    @Override
    @Test
    public void getLast_2() {
        assertNull(this.newWith().getLast());
    }
}
