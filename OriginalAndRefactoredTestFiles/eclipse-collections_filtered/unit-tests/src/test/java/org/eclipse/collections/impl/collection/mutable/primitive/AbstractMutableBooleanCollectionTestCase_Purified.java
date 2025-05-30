package org.eclipse.collections.impl.collection.mutable.primitive;

import java.util.NoSuchElementException;
import org.eclipse.collections.api.BooleanIterable;
import org.eclipse.collections.api.collection.primitive.MutableBooleanCollection;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.iterator.MutableBooleanIterator;
import org.eclipse.collections.impl.bag.mutable.primitive.BooleanHashBag;
import org.eclipse.collections.impl.list.mutable.primitive.BooleanArrayList;
import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class AbstractMutableBooleanCollectionTestCase_Purified extends AbstractBooleanIterableTestCase {

    @Override
    protected abstract MutableBooleanCollection classUnderTest();

    @Override
    protected abstract MutableBooleanCollection newWith(boolean... elements);

    @Override
    protected abstract MutableBooleanCollection newMutableCollectionWith(boolean... elements);

    @Test
    public void clear_1_testMerged_1() {
        MutableBooleanCollection collection = this.classUnderTest();
        collection.clear();
        Verify.assertSize(0, collection);
        Verify.assertEmpty(collection);
        assertFalse(collection.contains(true));
        assertFalse(collection.contains(false));
    }

    @Test
    public void clear_5_testMerged_2() {
        MutableBooleanCollection collection0 = this.newWith();
        collection0.clear();
        Verify.assertEmpty(collection0);
        Verify.assertSize(0, collection0);
        assertEquals(this.newMutableCollectionWith(), collection0);
    }

    @Test
    public void clear_6_testMerged_3() {
        MutableBooleanCollection collection1 = this.newWith(false);
        collection1.clear();
        Verify.assertEmpty(collection1);
        Verify.assertSize(0, collection1);
        assertFalse(collection1.contains(false));
        assertEquals(this.newMutableCollectionWith(), collection1);
    }

    @Test
    public void clear_7_testMerged_4() {
        MutableBooleanCollection collection2 = this.newWith(true);
        collection2.clear();
        Verify.assertEmpty(collection2);
        Verify.assertSize(0, collection2);
        assertFalse(collection2.contains(true));
        assertEquals(this.newMutableCollectionWith(), collection2);
    }

    @Test
    public void clear_8_testMerged_5() {
        MutableBooleanCollection collection3 = this.newWith(true, false);
        collection3.clear();
        Verify.assertEmpty(collection3);
        Verify.assertSize(0, collection3);
        assertFalse(collection3.contains(true));
        assertFalse(collection3.contains(false));
        assertEquals(this.newMutableCollectionWith(), collection3);
    }

    @Test
    public void clear_9_testMerged_6() {
        MutableBooleanCollection collection4 = this.newWith(true, false, true, false, true);
        collection4.clear();
        Verify.assertEmpty(collection4);
        Verify.assertSize(0, collection4);
        assertFalse(collection4.contains(false));
        assertEquals(this.newMutableCollectionWith(), collection4);
    }

    @Test
    public void add_1_testMerged_1() {
        MutableBooleanCollection emptyCollection = this.newWith();
        assertTrue(emptyCollection.add(true));
        assertEquals(this.newMutableCollectionWith(true), emptyCollection);
        assertTrue(emptyCollection.add(false));
        assertEquals(this.newMutableCollectionWith(true, false), emptyCollection);
        assertEquals(this.newMutableCollectionWith(true, false, true), emptyCollection);
        assertEquals(this.newMutableCollectionWith(true, false, true, false), emptyCollection);
    }

    @Test
    public void add_9_testMerged_2() {
        MutableBooleanCollection collection = this.classUnderTest();
        assertTrue(collection.add(false));
        assertEquals(this.newMutableCollectionWith(true, false, true, false), collection);
    }

    @Test
    public void addAllIterable_1_testMerged_1() {
        MutableBooleanCollection collection = this.classUnderTest();
        assertFalse(collection.addAll(this.newMutableCollectionWith()));
        assertTrue(collection.addAll(this.newMutableCollectionWith(false, true, false)));
        assertEquals(this.newMutableCollectionWith(true, false, true, false, true, false), collection);
        assertTrue(collection.addAll(this.newMutableCollectionWith(true, false, true, false, true)));
        assertEquals(this.newMutableCollectionWith(true, false, true, false, true, false, true, false, true, false, true), collection);
    }

    @Test
    public void addAllIterable_6_testMerged_2() {
        MutableBooleanCollection emptyCollection = this.newWith();
        assertTrue(emptyCollection.addAll(BooleanArrayList.newListWith(true, false, true, false, true)));
        assertFalse(emptyCollection.addAll(new BooleanArrayList()));
        assertEquals(this.newMutableCollectionWith(true, false, true, false, true), emptyCollection);
    }

    @Test
    public void remove_1_testMerged_1() {
        MutableBooleanCollection collection = this.classUnderTest();
        assertTrue(collection.remove(false));
        assertEquals(this.newMutableCollectionWith(true, true), collection);
        assertFalse(collection.remove(false));
        assertTrue(collection.remove(true));
        assertEquals(this.newMutableCollectionWith(true), collection);
    }

    @Test
    public void remove_7_testMerged_2() {
        MutableBooleanCollection collection1 = this.newWith();
        assertFalse(collection1.remove(false));
        assertEquals(this.newMutableCollectionWith(), collection1);
        assertTrue(collection1.add(false));
        assertTrue(collection1.remove(false));
        assertEquals(this.newMutableCollectionWith(false), collection1);
    }

    @Test
    public void remove_15_testMerged_3() {
        MutableBooleanCollection collection2 = this.newWith();
        assertFalse(collection2.remove(true));
        assertEquals(this.newMutableCollectionWith(), collection2);
        assertTrue(collection2.add(true));
        assertTrue(collection2.remove(true));
        assertEquals(this.newMutableCollectionWith(true), collection2);
    }

    @Test
    public void removeAll_1() {
        assertFalse(this.newWith().removeAll(true));
    }

    @Test
    public void removeAll_18_testMerged_2() {
        MutableBooleanCollection collection2 = this.newWith(true, false, true, false, true);
        assertFalse(collection2.removeAll());
        assertTrue(collection2.removeAll(true, true));
        assertEquals(this.newMutableCollectionWith(false, false), collection2);
    }

    @Test
    public void removeAll_2_testMerged_3() {
        MutableBooleanCollection collection = this.classUnderTest();
        assertFalse(collection.removeAll());
        assertTrue(collection.removeAll(true));
        assertEquals(this.newMutableCollectionWith(false), collection);
        assertFalse(collection.removeAll(true));
        assertTrue(collection.removeAll(false, true));
        assertEquals(this.newMutableCollectionWith(), collection);
    }

    @Test
    public void removeAll_21_testMerged_4() {
        MutableBooleanCollection collection3 = this.newWith(true, false, true, false, true);
        assertFalse(collection3.removeAll());
        assertTrue(collection3.removeAll(true, false));
        assertEquals(this.newMutableCollectionWith(), collection3);
    }

    @Test
    public void removeAll_24_testMerged_5() {
        MutableBooleanCollection collection4 = this.newWith(true, false, true, false, true);
        assertFalse(collection4.removeAll());
        assertTrue(collection4.removeAll(false, false));
        assertEquals(this.newMutableCollectionWith(true, true, true), collection4);
    }

    @Test
    public void removeAll_9_testMerged_6() {
        MutableBooleanCollection booleanArrayCollection = this.newWith(false, false);
        assertFalse(booleanArrayCollection.removeAll(true));
        assertEquals(this.newMutableCollectionWith(false, false), booleanArrayCollection);
        assertTrue(booleanArrayCollection.removeAll(false));
        assertEquals(this.newMutableCollectionWith(), booleanArrayCollection);
    }

    @Test
    public void removeAll_13_testMerged_7() {
        MutableBooleanCollection collection1 = this.classUnderTest();
        assertFalse(collection1.removeAll());
        assertTrue(collection1.removeAll(true, false));
        assertEquals(this.newMutableCollectionWith(), collection1);
    }

    @Test
    public void removeAll_16_testMerged_8() {
        MutableBooleanCollection trueFalseList = this.newWith(true, false);
        assertTrue(trueFalseList.removeAll(true));
        assertEquals(this.newMutableCollectionWith(false), trueFalseList);
    }

    @Test
    public void removeAll_iterable_17_testMerged_1() {
        MutableBooleanCollection list1 = this.newWith(true, false, true, true);
        assertFalse(list1.removeAll(new BooleanArrayList()));
        assertTrue(list1.removeAll(BooleanArrayList.newListWith(true, true)));
        Verify.assertSize(1, list1);
        assertFalse(list1.contains(true));
        assertEquals(this.newMutableCollectionWith(false), list1);
        assertTrue(list1.removeAll(BooleanArrayList.newListWith(false, false)));
        assertEquals(this.newMutableCollectionWith(), list1);
    }

    @Test
    public void removeAll_iterable_1_testMerged_2() {
        MutableBooleanCollection collection = this.classUnderTest();
        assertFalse(collection.removeAll(this.newMutableCollectionWith()));
        assertTrue(collection.removeAll(this.newMutableCollectionWith(false)));
        assertEquals(this.newMutableCollectionWith(true, true), collection);
        assertTrue(collection.removeAll(this.newMutableCollectionWith(true, true)));
        assertEquals(this.newMutableCollectionWith(), collection);
    }

    @Test
    public void removeAll_iterable_6_testMerged_3() {
        MutableBooleanCollection list = this.classUnderTest();
        assertFalse(list.removeAll(new BooleanArrayList()));
        assertTrue(list.removeAll(new BooleanArrayList(true)));
        assertEquals(this.newMutableCollectionWith(false), list);
        assertTrue(list.removeAll(BooleanArrayList.newListWith(true, false)));
        assertEquals(this.newMutableCollectionWith(), list);
        assertFalse(list.removeAll(BooleanArrayList.newListWith(true, false)));
    }

    @Test
    public void removeAll_iterable_7_testMerged_4() {
        MutableBooleanCollection booleanArrayList = this.newWith(false, false);
        assertFalse(booleanArrayList.removeAll(new BooleanArrayList(true)));
        assertEquals(this.newMutableCollectionWith(false, false), booleanArrayList);
        assertTrue(booleanArrayList.removeAll(new BooleanArrayList(false)));
        assertEquals(this.newMutableCollectionWith(), booleanArrayList);
    }

    @Test
    public void removeAll_iterable_24_testMerged_5() {
        MutableBooleanCollection list2 = this.newWith(true, false, true, false, true);
        assertTrue(list2.removeAll(BooleanHashBag.newBagWith(true, false)));
        assertEquals(this.newMutableCollectionWith(), list2);
    }

    @Test
    public void retainAll_iterable_17_testMerged_1() {
        MutableBooleanCollection list1 = this.newWith(true, false, true, true);
        assertFalse(list1.retainAll(false, false, true));
        assertTrue(list1.retainAll(false, false));
        Verify.assertSize(1, list1);
        assertFalse(list1.contains(true));
        assertEquals(this.newMutableCollectionWith(false), list1);
        assertTrue(list1.retainAll(true, true));
        assertEquals(this.newMutableCollectionWith(), list1);
    }

    @Test
    public void retainAll_iterable_1_testMerged_2() {
        MutableBooleanCollection collection = this.classUnderTest();
        assertFalse(collection.retainAll(true, false));
        assertTrue(collection.retainAll(true));
        assertEquals(this.newMutableCollectionWith(true, true), collection);
        assertTrue(collection.retainAll(false, false));
        assertEquals(this.newMutableCollectionWith(), collection);
    }

    @Test
    public void retainAll_iterable_6_testMerged_3() {
        MutableBooleanCollection list = this.classUnderTest();
        assertFalse(list.retainAll(false, false, true));
        assertTrue(list.retainAll(false));
        assertEquals(this.newMutableCollectionWith(false), list);
        assertTrue(list.retainAll());
        assertEquals(this.newMutableCollectionWith(), list);
        assertFalse(list.retainAll(true, false));
    }

    @Test
    public void retainAll_iterable_7_testMerged_4() {
        MutableBooleanCollection booleanArrayList = this.newWith(false, false);
        assertFalse(booleanArrayList.retainAll(false));
        assertEquals(this.newMutableCollectionWith(false, false), booleanArrayList);
        assertTrue(booleanArrayList.retainAll(true));
        assertEquals(this.newMutableCollectionWith(), booleanArrayList);
    }

    @Test
    public void retainAll_iterable_24_testMerged_5() {
        MutableBooleanCollection list2 = this.newWith(true, false, true, false, true);
        assertTrue(list2.retainAll());
        assertEquals(this.newMutableCollectionWith(), list2);
    }

    @Test
    public void retainAll_17_testMerged_1() {
        MutableBooleanCollection list1 = this.newWith(true, false, true, true);
        assertFalse(list1.retainAll(BooleanArrayList.newListWith(false, false, true)));
        assertTrue(list1.retainAll(BooleanArrayList.newListWith(false, false)));
        Verify.assertSize(1, list1);
        assertFalse(list1.contains(true));
        assertEquals(this.newMutableCollectionWith(false), list1);
        assertTrue(list1.retainAll(BooleanArrayList.newListWith(true, true)));
        assertEquals(this.newMutableCollectionWith(), list1);
    }

    @Test
    public void retainAll_1_testMerged_2() {
        MutableBooleanCollection collection = this.classUnderTest();
        assertFalse(collection.retainAll(this.newMutableCollectionWith(true, false)));
        assertTrue(collection.retainAll(this.newMutableCollectionWith(true)));
        assertEquals(this.newMutableCollectionWith(true, true), collection);
        assertTrue(collection.retainAll(this.newMutableCollectionWith(false, false)));
        assertEquals(this.newMutableCollectionWith(), collection);
    }

    @Test
    public void retainAll_6_testMerged_3() {
        MutableBooleanCollection list = this.classUnderTest();
        assertFalse(list.retainAll(BooleanArrayList.newListWith(false, false, true)));
        assertTrue(list.retainAll(new BooleanArrayList(false)));
        assertEquals(this.newMutableCollectionWith(false), list);
        assertTrue(list.retainAll(new BooleanArrayList()));
        assertEquals(this.newMutableCollectionWith(), list);
        assertFalse(list.retainAll(BooleanArrayList.newListWith(true, false)));
    }

    @Test
    public void retainAll_7_testMerged_4() {
        MutableBooleanCollection booleanArrayList = this.newWith(false, false);
        assertFalse(booleanArrayList.retainAll(new BooleanArrayList(false)));
        assertEquals(this.newMutableCollectionWith(false, false), booleanArrayList);
        assertTrue(booleanArrayList.retainAll(new BooleanArrayList(true)));
        assertEquals(this.newMutableCollectionWith(), booleanArrayList);
    }

    @Test
    public void retainAll_24_testMerged_5() {
        MutableBooleanCollection list2 = this.newWith(true, false, true, false, true);
        assertTrue(list2.retainAll(new BooleanHashBag()));
        assertEquals(this.newMutableCollectionWith(), list2);
    }

    @Test
    public void with_1_testMerged_1() {
        MutableBooleanCollection emptyCollection = this.newWith();
        MutableBooleanCollection collection = emptyCollection.with(true);
        assertSame(emptyCollection, collection);
        assertEquals(this.newMutableCollectionWith(true), collection);
    }

    @Test
    public void with_3() {
        MutableBooleanCollection collection0 = this.newWith().with(true).with(false);
        assertEquals(this.newMutableCollectionWith(true, false), collection0);
    }

    @Test
    public void with_4() {
        MutableBooleanCollection collection1 = this.newWith().with(true).with(false).with(true);
        assertEquals(this.newMutableCollectionWith(true, false, true), collection1);
    }

    @Test
    public void with_5() {
        MutableBooleanCollection collection2 = this.newWith().with(true).with(false).with(true).with(false);
        assertEquals(this.newMutableCollectionWith(true, false, true, false), collection2);
    }

    @Test
    public void with_6() {
        MutableBooleanCollection collection3 = this.newWith().with(true).with(false).with(true).with(false).with(true);
        assertEquals(this.newMutableCollectionWith(true, false, true, false, true), collection3);
    }

    @Test
    public void withAll_1_testMerged_1() {
        MutableBooleanCollection emptyCollection = this.newWith();
        MutableBooleanCollection collection = emptyCollection.withAll(this.newMutableCollectionWith(true));
        assertSame(emptyCollection, collection);
        assertEquals(this.newMutableCollectionWith(true), collection);
    }

    @Test
    public void withAll_3() {
        MutableBooleanCollection collection0 = this.newWith().withAll(this.newMutableCollectionWith(true, false));
        assertEquals(this.newMutableCollectionWith(true, false), collection0);
    }

    @Test
    public void withAll_4() {
        MutableBooleanCollection collection1 = this.newWith().withAll(this.newMutableCollectionWith(true, false, true));
        assertEquals(this.classUnderTest(), collection1);
    }

    @Test
    public void withAll_5() {
        MutableBooleanCollection collection2 = this.newWith().withAll(this.newMutableCollectionWith(true, false, true, false));
        assertEquals(this.newMutableCollectionWith(true, false, true, false), collection2);
    }

    @Test
    public void withAll_6() {
        MutableBooleanCollection collection3 = this.newWith().withAll(this.newMutableCollectionWith(true, false, true, false, true));
        assertEquals(this.newMutableCollectionWith(true, false, true, false, true), collection3);
    }

    @Test
    public void without_1_testMerged_1() {
        MutableBooleanCollection collection = this.newWith(true, false, true, false, true);
        assertEquals(this.newMutableCollectionWith(true, true, false, true), collection.without(false));
        assertEquals(this.newMutableCollectionWith(true, false, true), collection.without(true));
        assertEquals(this.newMutableCollectionWith(true, true), collection.without(false));
        assertEquals(this.newMutableCollectionWith(true), collection.without(true));
        assertEquals(this.newMutableCollectionWith(true), collection.without(false));
        assertEquals(this.newMutableCollectionWith(), collection.without(true));
        assertEquals(this.newMutableCollectionWith(), collection.without(false));
    }

    @Test
    public void without_8_testMerged_2() {
        MutableBooleanCollection collection1 = this.newWith(true, false, true, false, true);
        assertSame(collection1, collection1.without(false));
        assertEquals(this.newMutableCollectionWith(true, true, false, true), collection1);
    }

    @Test
    public void withoutAll_1() {
        MutableBooleanCollection mainCollection = this.newWith(true, false, true, false, true);
        assertEquals(this.newMutableCollectionWith(true, true, true), mainCollection.withoutAll(this.newMutableCollectionWith(false, false)));
    }

    @Test
    public void withoutAll_2_testMerged_2() {
        MutableBooleanCollection collection = this.newWith(true, false, true, false, true);
        assertEquals(this.newMutableCollectionWith(true, true, true), collection.withoutAll(BooleanHashBag.newBagWith(false)));
        assertEquals(this.newMutableCollectionWith(), collection.withoutAll(BooleanHashBag.newBagWith(true, false)));
    }

    @Test
    public void withoutAll_5_testMerged_3() {
        MutableBooleanCollection trueCollection = this.newWith(true, true, true);
        assertEquals(this.newMutableCollectionWith(true, true, true), trueCollection.withoutAll(BooleanArrayList.newListWith(false)));
        MutableBooleanCollection mutableBooleanCollection = trueCollection.withoutAll(BooleanArrayList.newListWith(true));
        assertEquals(this.newMutableCollectionWith(), mutableBooleanCollection);
        assertSame(trueCollection, mutableBooleanCollection);
    }

    @Test
    public void asUnmodifiable_1() {
        Verify.assertInstanceOf(this.newWith(true, false, true).asUnmodifiable().getClass(), this.classUnderTest().asUnmodifiable());
    }

    @Test
    public void asUnmodifiable_2() {
        assertEquals(this.newWith(true, false, true).asUnmodifiable(), this.classUnderTest().asUnmodifiable());
    }

    @Test
    public void asUnmodifiable_3() {
        assertEquals(this.classUnderTest(), this.classUnderTest().asUnmodifiable());
    }
}
