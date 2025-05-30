package org.eclipse.collections.impl.list.fixed;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.eclipse.collections.api.block.function.Function2;
import org.eclipse.collections.api.collection.MutableCollection;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.tuple.Twin;
import org.eclipse.collections.impl.block.factory.Predicates;
import org.eclipse.collections.impl.block.factory.Predicates2;
import org.eclipse.collections.impl.block.function.AddFunction;
import org.eclipse.collections.impl.block.function.PassThruFunction0;
import org.eclipse.collections.impl.block.procedure.CollectionAddProcedure;
import org.eclipse.collections.impl.list.mutable.AbstractListTestCase;
import org.eclipse.collections.impl.list.mutable.ArrayListAdapter;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.list.mutable.UnmodifiableMutableList;
import org.eclipse.collections.impl.set.mutable.UnifiedSet;
import org.eclipse.collections.impl.test.SerializeTestHelper;
import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ArrayAdapterTest_Purified extends AbstractListTestCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArrayAdapterTest.class);

    @Override
    protected <T> MutableList<T> newWith(T... littleElements) {
        return ArrayAdapter.newArrayWith(littleElements);
    }

    private MutableList<Integer> newArray() {
        return ArrayAdapter.newArray();
    }

    @Override
    @Test
    public void getFirst_1() {
        assertEquals(Integer.valueOf(1), this.newWith(1, 2, 3).getFirst());
    }

    @Override
    @Test
    public void getFirst_2() {
        assertNotEquals(Integer.valueOf(3), this.newWith(1, 2, 3).getFirst());
    }

    @Override
    @Test
    public void getLast_1() {
        assertNotEquals(Integer.valueOf(1), this.newWith(1, 2, 3).getLast());
    }

    @Override
    @Test
    public void getLast_2() {
        assertEquals(Integer.valueOf(3), this.newWith(1, 2, 3).getLast());
    }

    @Override
    @Test
    public void isEmpty_1() {
        Verify.assertEmpty(this.newArray());
    }

    @Override
    @Test
    public void isEmpty_2() {
        Verify.assertNotEmpty(this.newWith(1, 2));
    }

    @Override
    @Test
    public void isEmpty_3() {
        assertTrue(this.newWith(1, 2).notEmpty());
    }

    @Override
    @Test
    public void selectWith_1() {
        Verify.assertContainsAll(ArrayAdapter.newArrayWith(1, 2, 3, 4, 5).selectWith(Predicates2.lessThan(), 3), 1, 2);
    }

    @Override
    @Test
    public void selectWith_2() {
        Verify.denyContainsAny(ArrayAdapter.newArrayWith(-1, 2, 3, 4, 5).selectWith(Predicates2.lessThan(), 3), 3, 4, 5);
        Verify.assertContainsAll(ArrayAdapter.newArrayWith(1, 2, 3, 4, 5).selectWith(Predicates2.lessThan(), 3, UnifiedSet.newSet()), 1, 2);
    }

    @Override
    @Test
    public void rejectWith_1() {
        Verify.assertContainsAll(ArrayAdapter.newArrayWith(1, 2, 3, 4).rejectWith(Predicates2.lessThan(), 3), 3, 4);
    }

    @Override
    @Test
    public void rejectWith_2() {
        Verify.assertContainsAll(ArrayAdapter.newArrayWith(1, 2, 3, 4).rejectWith(Predicates2.lessThan(), 3, UnifiedSet.newSet()), 3, 4);
    }

    @Override
    @Test
    public void anySatisfyWith_1() {
        assertFalse(ArrayAdapter.newArrayWith(1, 2, 3).anySatisfyWith(Predicates2.instanceOf(), String.class));
    }

    @Override
    @Test
    public void anySatisfyWith_2() {
        assertTrue(ArrayAdapter.newArrayWith(1, 2, 3).anySatisfyWith(Predicates2.instanceOf(), Integer.class));
    }
}
