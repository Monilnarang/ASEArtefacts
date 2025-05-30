package org.eclipse.collections.impl.map.mutable.primitive;

import java.util.NoSuchElementException;
import org.eclipse.collections.api.BooleanIterable;
import org.eclipse.collections.api.block.function.primitive.BooleanToObjectFunction;
import org.eclipse.collections.api.collection.primitive.MutableBooleanCollection;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.iterator.BooleanIterator;
import org.eclipse.collections.api.iterator.MutableBooleanIterator;
import org.eclipse.collections.impl.block.factory.primitive.BooleanPredicates;
import org.eclipse.collections.impl.collection.mutable.primitive.AbstractMutableBooleanCollectionTestCase;
import org.eclipse.collections.impl.collection.mutable.primitive.SynchronizedBooleanCollection;
import org.eclipse.collections.impl.collection.mutable.primitive.UnmodifiableBooleanCollection;
import org.eclipse.collections.impl.factory.primitive.BooleanBags;
import org.eclipse.collections.impl.list.mutable.primitive.BooleanArrayList;
import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class ObjectBooleanHashMapValuesTestCase_Purified extends AbstractMutableBooleanCollectionTestCase {

    @Override
    public void containsAllArray() {
        MutableBooleanCollection emptyCollection = this.newWith();
        assertFalse(emptyCollection.containsAll(true));
        assertFalse(emptyCollection.containsAll(false));
        assertFalse(emptyCollection.containsAll(false, true, false));
        MutableBooleanCollection collection = this.classUnderTest();
        assertTrue(collection.containsAll());
        assertTrue(collection.containsAll(true));
        assertTrue(collection.containsAll(false));
        assertTrue(collection.containsAll(false, true));
    }

    @Override
    public void containsAllIterable() {
        MutableBooleanCollection emptyCollection1 = this.newWith();
        assertTrue(emptyCollection1.containsAll(new BooleanArrayList()));
        assertFalse(emptyCollection1.containsAll(BooleanArrayList.newListWith(true)));
        assertFalse(emptyCollection1.containsAll(BooleanArrayList.newListWith(false)));
        MutableBooleanCollection collection = this.classUnderTest();
        assertTrue(collection.containsAll(new BooleanArrayList()));
        assertTrue(collection.containsAll(BooleanArrayList.newListWith(true)));
        assertTrue(collection.containsAll(BooleanArrayList.newListWith(false)));
        assertTrue(collection.containsAll(BooleanArrayList.newListWith(false, true)));
    }

    @Override
    public void chunk() {
        BooleanIterable iterable1 = this.newWith(true);
        Verify.assertIterablesEqual(Lists.mutable.with(BooleanBags.mutable.with(true)).toSet(), iterable1.chunk(1).toSet());
        BooleanIterable iterable2 = this.newWith(false);
        Verify.assertIterablesEqual(Lists.mutable.with(BooleanBags.mutable.with(false)).toSet(), iterable2.chunk(1).toSet());
        BooleanIterable iterable3 = this.newWith(false, true);
        Verify.assertIterablesEqual(Lists.mutable.with(BooleanBags.mutable.with(false), BooleanBags.mutable.with(true)).toSet(), iterable3.chunk(1).toSet());
        Verify.assertIterablesEqual(Lists.mutable.with(BooleanBags.mutable.with(false, true)), iterable3.chunk(2));
        Verify.assertIterablesEqual(Lists.mutable.with(BooleanBags.mutable.with(false, true)), iterable3.chunk(3));
        assertThrows(IllegalArgumentException.class, () -> this.classUnderTest().chunk(0));
        assertThrows(IllegalArgumentException.class, () -> this.classUnderTest().chunk(-1));
    }

    @Override
    public void newCollection() {
    }

    @Override
    @Test
    public void appendString_1_testMerged_1() {
        StringBuilder appendable = new StringBuilder();
        this.newWith().appendString(appendable);
        assertEquals("", appendable.toString());
        this.newWith().appendString(appendable, "/");
        this.newWith().appendString(appendable, "[", "/", "]");
        assertEquals("[]", appendable.toString());
    }

    @Override
    @Test
    public void appendString_4() {
        StringBuilder appendable1 = new StringBuilder();
        this.newWith(true).appendString(appendable1);
        assertEquals("true", appendable1.toString());
    }

    @Override
    @Test
    public void appendString_5() {
        StringBuilder appendable2 = new StringBuilder();
        iterable.appendString(appendable2);
        assertTrue("true, false".equals(appendable2.toString()) || "false, true".equals(appendable2.toString()));
    }

    @Override
    @Test
    public void appendString_6() {
        StringBuilder appendable3 = new StringBuilder();
        iterable.appendString(appendable3, "/");
        assertTrue("true/false".equals(appendable3.toString()) || "false/true".equals(appendable3.toString()));
    }
}
