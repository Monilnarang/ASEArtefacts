package org.eclipse.collections.impl.collection.mutable.primitive;

import java.util.Arrays;
import java.util.NoSuchElementException;
import org.eclipse.collections.api.BooleanIterable;
import org.eclipse.collections.api.LazyBooleanIterable;
import org.eclipse.collections.api.RichIterable;
import org.eclipse.collections.api.iterator.BooleanIterator;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.impl.bag.mutable.primitive.BooleanHashBag;
import org.eclipse.collections.impl.block.factory.primitive.BooleanPredicates;
import org.eclipse.collections.impl.factory.primitive.BooleanLists;
import org.eclipse.collections.impl.factory.primitive.BooleanSets;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.list.mutable.primitive.BooleanArrayList;
import org.eclipse.collections.impl.math.MutableInteger;
import org.eclipse.collections.impl.set.mutable.primitive.BooleanHashSet;
import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class AbstractBooleanIterableTestCase_Purified {

    protected abstract BooleanIterable classUnderTest();

    protected abstract BooleanIterable newWith(boolean... elements);

    protected abstract BooleanIterable newMutableCollectionWith(boolean... elements);

    protected abstract RichIterable<Object> newObjectCollectionWith(Object... elements);

    @Test
    public void newCollectionWith_1_testMerged_1() {
        BooleanIterable iterable = this.newWith(true, false, true);
        Verify.assertSize(3, iterable);
        assertTrue(iterable.containsAll(true, false, true));
    }

    @Test
    public void newCollectionWith_3_testMerged_2() {
        BooleanIterable iterable1 = this.newWith();
        Verify.assertEmpty(iterable1);
        assertFalse(iterable1.containsAll(true, false, true));
    }

    @Test
    public void newCollectionWith_5_testMerged_3() {
        BooleanIterable iterable2 = this.newWith(true);
        Verify.assertSize(1, iterable2);
        assertFalse(iterable2.containsAll(true, false, true));
        assertTrue(iterable2.containsAll(true, true));
    }

    @Test
    public void newCollection_1() {
        assertEquals(this.newMutableCollectionWith(), this.newWith());
    }

    @Test
    public void newCollection_2() {
        assertEquals(this.newMutableCollectionWith(true, false, true), this.newWith(true, false, true));
    }

    @Test
    public void isEmpty_1() {
        Verify.assertEmpty(this.newWith());
    }

    @Test
    public void isEmpty_2() {
        Verify.assertNotEmpty(this.classUnderTest());
    }

    @Test
    public void isEmpty_3() {
        Verify.assertNotEmpty(this.newWith(false));
    }

    @Test
    public void isEmpty_4() {
        Verify.assertNotEmpty(this.newWith(true));
    }

    @Test
    public void notEmpty_1() {
        assertFalse(this.newWith().notEmpty());
    }

    @Test
    public void notEmpty_2() {
        assertTrue(this.classUnderTest().notEmpty());
    }

    @Test
    public void notEmpty_3() {
        assertTrue(this.newWith(false).notEmpty());
    }

    @Test
    public void notEmpty_4() {
        assertTrue(this.newWith(true).notEmpty());
    }

    @Test
    public void contains_1_testMerged_1() {
        BooleanIterable emptyCollection = this.newWith();
        assertFalse(emptyCollection.contains(true));
        assertFalse(emptyCollection.contains(false));
    }

    @Test
    public void contains_3_testMerged_2() {
        BooleanIterable booleanIterable = this.classUnderTest();
        int size = booleanIterable.size();
        assertEquals(size >= 1, booleanIterable.contains(true));
        assertEquals(size >= 2, booleanIterable.contains(false));
    }

    @Test
    public void contains_5() {
        assertFalse(this.newWith(true, true, true).contains(false));
    }

    @Test
    public void contains_6() {
        assertFalse(this.newWith(false, false, false).contains(true));
    }

    @Test
    public void containsAllArray_1_testMerged_1() {
        BooleanIterable iterable = this.classUnderTest();
        int size = iterable.size();
        assertEquals(size >= 1, iterable.containsAll(true));
        assertEquals(size >= 2, iterable.containsAll(true, false, true));
        assertEquals(size >= 2, iterable.containsAll(true, false));
        assertEquals(size >= 1, iterable.containsAll(true, true));
        assertEquals(size >= 2, iterable.containsAll(false, false));
    }

    @Test
    public void containsAllArray_6_testMerged_2() {
        BooleanIterable emptyCollection = this.newWith();
        assertFalse(emptyCollection.containsAll(true));
        assertFalse(emptyCollection.containsAll(false));
        assertFalse(emptyCollection.containsAll(false, true, false));
    }

    @Test
    public void containsAllArray_9() {
        assertFalse(this.newWith(true, true).containsAll(false, true, false));
    }

    @Test
    public void containsAllArray_10() {
        BooleanIterable trueCollection = this.newWith(true, true, true, true);
        assertFalse(trueCollection.containsAll(true, false));
    }

    @Test
    public void containsAllArray_11() {
        BooleanIterable falseCollection = this.newWith(false, false, false, false);
        assertFalse(falseCollection.containsAll(true, false));
    }

    @Test
    public void containsAllIterable_1_testMerged_1() {
        BooleanIterable emptyCollection = this.newWith();
        assertTrue(emptyCollection.containsAll(new BooleanArrayList()));
        assertFalse(emptyCollection.containsAll(BooleanArrayList.newListWith(true)));
        assertFalse(emptyCollection.containsAll(BooleanArrayList.newListWith(false)));
    }

    @Test
    public void containsAllIterable_4_testMerged_2() {
        BooleanIterable booleanIterable = this.classUnderTest();
        int size = booleanIterable.size();
        assertTrue(booleanIterable.containsAll(new BooleanArrayList()));
        assertEquals(size >= 1, booleanIterable.containsAll(BooleanArrayList.newListWith(true)));
        assertEquals(size >= 2, booleanIterable.containsAll(BooleanArrayList.newListWith(false)));
        assertEquals(size >= 2, booleanIterable.containsAll(BooleanArrayList.newListWith(true, false)));
    }

    @Test
    public void containsAllIterable_8_testMerged_3() {
        BooleanIterable iterable = this.newWith(true, true, false, false, false);
        assertTrue(iterable.containsAll(BooleanArrayList.newListWith(true)));
        assertTrue(iterable.containsAll(BooleanArrayList.newListWith(false)));
        assertTrue(iterable.containsAll(BooleanArrayList.newListWith(true, false)));
        assertTrue(iterable.containsAll(BooleanArrayList.newListWith(true, true)));
        assertTrue(iterable.containsAll(BooleanArrayList.newListWith(false, false)));
        assertTrue(iterable.containsAll(BooleanArrayList.newListWith(true, false, true)));
    }

    @Test
    public void containsAllIterable_14() {
        assertFalse(this.newWith(true, true).containsAll(BooleanArrayList.newListWith(false, true, false)));
    }

    @Test
    public void containsAllIterable_15() {
        BooleanIterable trueCollection = this.newWith(true, true, true, true);
        assertFalse(trueCollection.containsAll(BooleanArrayList.newListWith(true, false)));
    }

    @Test
    public void containsAllIterable_16() {
        BooleanIterable falseCollection = this.newWith(false, false, false, false);
        assertFalse(falseCollection.containsAll(BooleanArrayList.newListWith(true, false)));
    }

    @Test
    public void containsAnyArray_1_testMerged_1() {
        BooleanIterable iterable = this.newWith(true);
        assertTrue(iterable.containsAny(true, false));
        assertFalse(iterable.containsAny());
        assertTrue(iterable.containsAny(true));
        assertFalse(iterable.containsAny(false, false, false));
    }

    @Test
    public void containsAnyArray_5_testMerged_2() {
        BooleanIterable iterable2 = this.newWith(true, false);
        assertTrue(iterable2.containsAny(true));
        assertFalse(iterable2.containsAny());
        assertTrue(iterable2.containsAny(false, false));
        assertTrue(iterable2.containsAny(true, false, true, false));
    }

    @Test
    public void containsAnyArray_9_testMerged_3() {
        BooleanIterable emptyIterable = this.newWith();
        assertFalse(emptyIterable.containsAny(true, true));
        assertFalse(emptyIterable.containsAny());
        assertFalse(emptyIterable.containsAny(false, true, true));
        assertFalse(emptyIterable.containsAny(false));
    }

    @Test
    public void containsAnyIterable_1_testMerged_1() {
        BooleanIterable iterable = this.newWith(true);
        assertTrue(iterable.containsAny(BooleanLists.immutable.with(true, false)));
        assertFalse(iterable.containsAny(BooleanLists.mutable.empty()));
        assertTrue(iterable.containsAny(BooleanLists.immutable.with(true)));
        assertFalse(iterable.containsAny(BooleanLists.immutable.with(false, false, false)));
    }

    @Test
    public void containsAnyIterable_5_testMerged_2() {
        BooleanIterable iterable2 = this.newWith(true, false);
        assertTrue(iterable2.containsAny(BooleanSets.immutable.with(true)));
        assertFalse(iterable2.containsAny(BooleanSets.mutable.empty()));
        assertTrue(iterable2.containsAny(BooleanSets.immutable.with(false, false)));
        assertTrue(iterable2.containsAny(BooleanSets.mutable.with(true, false, true, false)));
    }

    @Test
    public void containsAnyIterable_9_testMerged_3() {
        BooleanIterable emptyIterable = this.newWith();
        assertFalse(emptyIterable.containsAny(BooleanLists.immutable.with(true, true)));
        assertFalse(emptyIterable.containsAny(BooleanLists.mutable.empty()));
        assertFalse(emptyIterable.containsAny(BooleanLists.immutable.with(false, true, true)));
        assertFalse(emptyIterable.containsAny(BooleanLists.mutable.with(false)));
    }

    @Test
    public void containsNoneArray_1_testMerged_1() {
        BooleanIterable iterable = this.newWith(false);
        assertTrue(iterable.containsNone(true, true));
        assertTrue(iterable.containsNone());
        assertFalse(iterable.containsNone(true, false));
        assertFalse(iterable.containsNone(false));
    }

    @Test
    public void containsNoneArray_5_testMerged_2() {
        BooleanIterable iterable2 = this.newWith(true, false, false);
        assertFalse(iterable2.containsNone(true, false));
        assertTrue(iterable2.containsNone());
        assertFalse(iterable2.containsNone(false, false, false));
        assertFalse(iterable2.containsNone(false));
    }

    @Test
    public void containsNoneArray_9_testMerged_3() {
        BooleanIterable emptyIterable = this.newWith();
        assertTrue(emptyIterable.containsNone(true, true));
        assertTrue(emptyIterable.containsNone());
        assertTrue(emptyIterable.containsNone(true, false));
        assertTrue(emptyIterable.containsNone(false));
    }

    @Test
    public void containsNoneIterable_1_testMerged_1() {
        BooleanIterable iterable = this.newWith(false);
        assertTrue(iterable.containsNone(BooleanLists.immutable.with(true, true)));
        assertTrue(iterable.containsNone(BooleanLists.mutable.empty()));
        assertFalse(iterable.containsNone(BooleanLists.immutable.with(true, false)));
        assertFalse(iterable.containsNone(BooleanLists.mutable.with(false)));
    }

    @Test
    public void containsNoneIterable_5_testMerged_2() {
        BooleanIterable iterable2 = this.newWith(true, false, false);
        assertFalse(iterable2.containsNone(BooleanSets.immutable.with(true, false)));
        assertTrue(iterable2.containsNone(BooleanSets.mutable.empty()));
        assertFalse(iterable2.containsNone(BooleanSets.immutable.with(false, false, false)));
        assertFalse(iterable2.containsNone(BooleanSets.mutable.with(false)));
    }

    @Test
    public void containsNoneIterable_9_testMerged_3() {
        BooleanIterable emptyIterable = this.newWith();
        assertTrue(emptyIterable.containsNone(BooleanLists.immutable.with(true, true)));
        assertTrue(emptyIterable.containsNone(BooleanLists.mutable.empty()));
        assertTrue(emptyIterable.containsNone(BooleanLists.immutable.with(true, false)));
        assertTrue(emptyIterable.containsNone(BooleanLists.mutable.with(false)));
    }

    @Test
    public void size_1() {
        Verify.assertSize(0, this.newWith());
    }

    @Test
    public void size_2() {
        Verify.assertSize(1, this.newWith(true));
    }

    @Test
    public void size_3() {
        Verify.assertSize(1, this.newWith(false));
    }

    @Test
    public void size_4() {
        Verify.assertSize(2, this.newWith(true, false));
    }

    @Test
    public void count_1() {
        assertEquals(2L, this.newWith(true, false, true).count(BooleanPredicates.isTrue()));
    }

    @Test
    public void count_2() {
        assertEquals(0L, this.newWith().count(BooleanPredicates.isFalse()));
    }

    @Test
    public void count_3_testMerged_3() {
        BooleanIterable iterable = this.newWith(true, false, false, true, true, true);
        assertEquals(4L, iterable.count(BooleanPredicates.isTrue()));
        assertEquals(2L, iterable.count(BooleanPredicates.isFalse()));
        assertEquals(6L, iterable.count(BooleanPredicates.or(BooleanPredicates.isTrue(), BooleanPredicates.isFalse())));
    }

    @Test
    public void count_6_testMerged_4() {
        BooleanIterable iterable1 = this.classUnderTest();
        int size = iterable1.size();
        int halfSize = size / 2;
        assertEquals((size & 1) == 1 ? halfSize + 1 : halfSize, iterable1.count(BooleanPredicates.isTrue()));
        assertEquals(halfSize, iterable1.count(BooleanPredicates.isFalse()));
    }

    @Test
    public void anySatisfy_1_testMerged_1() {
        BooleanIterable booleanIterable = this.classUnderTest();
        int size = booleanIterable.size();
        assertEquals(size >= 1, booleanIterable.anySatisfy(BooleanPredicates.isTrue()));
        assertEquals(size >= 2, booleanIterable.anySatisfy(BooleanPredicates.isFalse()));
    }

    @Test
    public void anySatisfy_3() {
        assertFalse(this.newWith(true, true).anySatisfy(BooleanPredicates.isFalse()));
    }

    @Test
    public void anySatisfy_4() {
        assertFalse(this.newWith().anySatisfy(BooleanPredicates.isFalse()));
    }

    @Test
    public void anySatisfy_5() {
        assertFalse(this.newWith().anySatisfy(BooleanPredicates.isTrue()));
    }

    @Test
    public void anySatisfy_6() {
        assertTrue(this.newWith(true).anySatisfy(BooleanPredicates.isTrue()));
    }

    @Test
    public void anySatisfy_7() {
        assertFalse(this.newWith(false).anySatisfy(BooleanPredicates.isTrue()));
    }

    @Test
    public void anySatisfy_8() {
        assertTrue(this.newWith(false, false, false).anySatisfy(BooleanPredicates.isFalse()));
    }

    @Test
    public void allSatisfy_1_testMerged_1() {
        BooleanIterable booleanIterable = this.classUnderTest();
        int size = booleanIterable.size();
        assertEquals(size <= 1, booleanIterable.allSatisfy(BooleanPredicates.isTrue()));
        assertEquals(size == 0, booleanIterable.allSatisfy(BooleanPredicates.isFalse()));
    }

    @Test
    public void allSatisfy_3() {
        assertTrue(this.newWith().allSatisfy(BooleanPredicates.isTrue()));
    }

    @Test
    public void allSatisfy_4() {
        assertTrue(this.newWith().allSatisfy(BooleanPredicates.isFalse()));
    }

    @Test
    public void allSatisfy_5() {
        assertTrue(this.newWith(false, false).allSatisfy(BooleanPredicates.isFalse()));
    }

    @Test
    public void allSatisfy_6() {
        assertFalse(this.newWith(true, false).allSatisfy(BooleanPredicates.isTrue()));
    }

    @Test
    public void allSatisfy_7() {
        assertTrue(this.newWith(true, true, true).allSatisfy(BooleanPredicates.isTrue()));
    }

    @Test
    public void allSatisfy_8() {
        assertTrue(this.newWith(false, false, false).allSatisfy(BooleanPredicates.isFalse()));
    }

    @Test
    public void noneSatisfy_1_testMerged_1() {
        BooleanIterable booleanIterable = this.classUnderTest();
        int size = booleanIterable.size();
        assertEquals(size == 0, booleanIterable.noneSatisfy(BooleanPredicates.isTrue()));
        assertEquals(size <= 1, booleanIterable.noneSatisfy(BooleanPredicates.isFalse()));
    }

    @Test
    public void noneSatisfy_3() {
        assertTrue(this.newWith().noneSatisfy(BooleanPredicates.isTrue()));
    }

    @Test
    public void noneSatisfy_4() {
        assertTrue(this.newWith().noneSatisfy(BooleanPredicates.isFalse()));
    }

    @Test
    public void noneSatisfy_5() {
        assertTrue(this.newWith(false, false).noneSatisfy(BooleanPredicates.isTrue()));
    }

    @Test
    public void noneSatisfy_6() {
        assertTrue(this.newWith(true, true).noneSatisfy(BooleanPredicates.isFalse()));
    }

    @Test
    public void noneSatisfy_7() {
        assertFalse(this.newWith(true, true).noneSatisfy(BooleanPredicates.isTrue()));
    }

    @Test
    public void noneSatisfy_8() {
        assertTrue(this.newWith(false, false, false).noneSatisfy(BooleanPredicates.isTrue()));
    }

    @Test
    public void select_1_testMerged_1() {
        BooleanIterable iterable = this.classUnderTest();
        int size = iterable.size();
        int halfSize = size / 2;
        Verify.assertSize((size & 1) == 1 ? halfSize + 1 : halfSize, iterable.select(BooleanPredicates.isTrue()));
        Verify.assertSize(halfSize, iterable.select(BooleanPredicates.isFalse()));
    }

    @Test
    public void select_3_testMerged_2() {
        BooleanIterable iterable1 = this.newWith(false, true, false, false, true, true, true);
        assertEquals(this.newMutableCollectionWith(true, true, true, true), iterable1.select(BooleanPredicates.isTrue()));
        assertEquals(this.newMutableCollectionWith(false, false, false), iterable1.select(BooleanPredicates.isFalse()));
    }

    @Test
    public void reject_1_testMerged_1() {
        BooleanIterable iterable = this.classUnderTest();
        int size = iterable.size();
        int halfSize = size / 2;
        Verify.assertSize(halfSize, iterable.reject(BooleanPredicates.isTrue()));
        Verify.assertSize((size & 1) == 1 ? halfSize + 1 : halfSize, iterable.reject(BooleanPredicates.isFalse()));
    }

    @Test
    public void reject_3_testMerged_2() {
        BooleanIterable iterable1 = this.newWith(false, true, false, false, true, true, true);
        assertEquals(this.newMutableCollectionWith(false, false, false), iterable1.reject(BooleanPredicates.isTrue()));
        assertEquals(this.newMutableCollectionWith(true, true, true, true), iterable1.reject(BooleanPredicates.isFalse()));
    }

    @Test
    public void detectIfNone_1_testMerged_1() {
        BooleanIterable iterable = this.classUnderTest();
        int size = iterable.size();
        assertEquals(size < 2, iterable.detectIfNone(BooleanPredicates.isFalse(), true));
        assertTrue(iterable.detectIfNone(BooleanPredicates.and(BooleanPredicates.isTrue(), BooleanPredicates.isFalse()), true));
    }

    @Test
    public void detectIfNone_3_testMerged_2() {
        BooleanIterable iterable1 = this.newWith(true, true, true);
        assertFalse(iterable1.detectIfNone(BooleanPredicates.isFalse(), false));
        assertTrue(iterable1.detectIfNone(BooleanPredicates.isFalse(), true));
        assertTrue(iterable1.detectIfNone(BooleanPredicates.isTrue(), false));
        assertTrue(iterable1.detectIfNone(BooleanPredicates.isTrue(), true));
    }

    @Test
    public void detectIfNone_7_testMerged_3() {
        BooleanIterable iterable2 = this.newWith(false, false, false);
        assertTrue(iterable2.detectIfNone(BooleanPredicates.isTrue(), true));
        assertFalse(iterable2.detectIfNone(BooleanPredicates.isTrue(), false));
        assertFalse(iterable2.detectIfNone(BooleanPredicates.isFalse(), true));
        assertFalse(iterable2.detectIfNone(BooleanPredicates.isFalse(), false));
    }

    @Test
    public void testEquals_1_testMerged_1() {
        BooleanIterable iterable1 = this.newWith(true, false, true, false);
        BooleanIterable iterable2 = this.newWith(true, false, true, false);
        BooleanIterable iterable3 = this.newWith(false, false, false, true);
        BooleanIterable iterable4 = this.newWith(true, true, true);
        Verify.assertEqualsAndHashCode(iterable1, iterable2);
        Verify.assertPostSerializedEqualsAndHashCode(iterable1);
        assertNotEquals(iterable1, iterable3);
        assertNotEquals(iterable1, iterable4);
    }

    @Test
    public void testEquals_2() {
        Verify.assertEqualsAndHashCode(this.newWith(), this.newWith());
    }

    @Test
    public void testEquals_3_testMerged_3() {
        BooleanIterable iterable6 = this.newWith(true);
        Verify.assertPostSerializedEqualsAndHashCode(iterable6);
        assertNotEquals(iterable6, this.newWith(true, false));
    }

    @Test
    public void testEquals_5() {
        BooleanIterable iterable5 = this.newWith(true, true, false, false, false);
        Verify.assertPostSerializedEqualsAndHashCode(iterable5);
    }

    @Test
    public void testEquals_8() {
        assertNotEquals(this.newWith(), this.newWith(true));
    }

    @Test
    public void testHashCode_1() {
        assertEquals(this.newObjectCollectionWith().hashCode(), this.newWith().hashCode());
    }

    @Test
    public void testHashCode_2() {
        assertEquals(this.newObjectCollectionWith(true, false, true).hashCode(), this.newWith(true, false, true).hashCode());
    }

    @Test
    public void testHashCode_3() {
        assertEquals(this.newObjectCollectionWith(true).hashCode(), this.newWith(true).hashCode());
    }

    @Test
    public void testHashCode_4() {
        assertEquals(this.newObjectCollectionWith(false).hashCode(), this.newWith(false).hashCode());
    }

    @Test
    public void testToString_1() {
        assertEquals("[]", this.newWith().toString());
    }

    @Test
    public void testToString_2() {
        assertEquals("[true]", this.newWith(true).toString());
    }

    @Test
    public void testToString_3() {
        BooleanIterable iterable = this.newWith(true, false);
        assertTrue("[true, false]".equals(iterable.toString()) || "[false, true]".equals(iterable.toString()));
    }

    @Test
    public void makeString_1() {
        assertEquals("true", this.newWith(true).makeString("/"));
    }

    @Test
    public void makeString_2() {
        assertEquals("", this.newWith().makeString());
    }

    @Test
    public void makeString_3() {
        assertEquals("", this.newWith().makeString("/"));
    }

    @Test
    public void makeString_4() {
        assertEquals("[]", this.newWith().makeString("[", "/", "]"));
    }

    @Test
    public void makeString_5_testMerged_5() {
        BooleanIterable iterable = this.newWith(true, false);
        assertTrue("true, false".equals(iterable.makeString()) || "false, true".equals(iterable.makeString()));
        assertTrue("true/false".equals(iterable.makeString("/")) || "false/true".equals(iterable.makeString("/")), iterable.makeString("/"));
        assertTrue("[true/false]".equals(iterable.makeString("[", "/", "]")) || "[false/true]".equals(iterable.makeString("[", "/", "]")), iterable.makeString("[", "/", "]"));
    }

    @Test
    public void appendString_1_testMerged_1() {
        StringBuilder appendable = new StringBuilder();
        this.newWith().appendString(appendable);
        assertEquals("", appendable.toString());
        this.newWith().appendString(appendable, "/");
        this.newWith().appendString(appendable, "[", "/", "]");
        assertEquals("[]", appendable.toString());
    }

    @Test
    public void appendString_4() {
        StringBuilder appendable1 = new StringBuilder();
        this.newWith(true).appendString(appendable1);
        assertEquals("true", appendable1.toString());
    }

    @Test
    public void appendString_5_testMerged_3() {
        StringBuilder appendable2 = new StringBuilder();
        BooleanIterable iterable = this.newWith(true, false);
        iterable.appendString(appendable2);
        assertTrue("true, false".equals(appendable2.toString()) || "false, true".equals(appendable2.toString()));
        StringBuilder appendable3 = new StringBuilder();
        iterable.appendString(appendable3, "/");
        assertTrue("true/false".equals(appendable3.toString()) || "false/true".equals(appendable3.toString()));
        StringBuilder appendable4 = new StringBuilder();
        iterable.appendString(appendable4, "[", ", ", "]");
        assertEquals(iterable.toString(), appendable4.toString());
    }

    @Test
    public void toList_1() {
        BooleanIterable iterable = this.newWith(true, false);
        assertTrue(BooleanArrayList.newListWith(false, true).equals(iterable.toList()) || BooleanArrayList.newListWith(true, false).equals(iterable.toList()));
    }

    @Test
    public void toList_2() {
        BooleanIterable iterable1 = this.newWith(true);
        assertEquals(BooleanArrayList.newListWith(true), iterable1.toList());
    }

    @Test
    public void toList_3() {
        BooleanIterable iterable0 = this.newWith();
        assertEquals(BooleanArrayList.newListWith(), iterable0.toList());
    }

    @Test
    public void toSet_1() {
        assertEquals(BooleanHashSet.newSetWith(), this.newWith().toSet());
    }

    @Test
    public void toSet_2() {
        assertEquals(BooleanHashSet.newSetWith(true), this.newWith(true).toSet());
    }

    @Test
    public void toSet_3() {
        assertEquals(BooleanHashSet.newSetWith(true, false), this.newWith(true, false, false, true, true, true).toSet());
    }

    @Test
    public void toBag_1() {
        assertEquals(BooleanHashBag.newBagWith(), this.newWith().toBag());
    }

    @Test
    public void toBag_2() {
        assertEquals(BooleanHashBag.newBagWith(true), this.newWith(true).toBag());
    }

    @Test
    public void toBag_3() {
        assertEquals(BooleanHashBag.newBagWith(true, false, true), this.newWith(true, false, true).toBag());
    }

    @Test
    public void toBag_4() {
        assertEquals(BooleanHashBag.newBagWith(false, false, true, true, true, true), this.newWith(true, false, false, true, true, true).toBag());
    }
}
