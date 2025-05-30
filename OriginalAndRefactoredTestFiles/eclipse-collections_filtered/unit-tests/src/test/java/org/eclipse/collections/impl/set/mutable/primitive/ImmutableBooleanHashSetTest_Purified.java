package org.eclipse.collections.impl.set.mutable.primitive;

import java.util.Arrays;
import java.util.NoSuchElementException;
import org.eclipse.collections.api.LazyBooleanIterable;
import org.eclipse.collections.api.block.function.primitive.BooleanToObjectFunction;
import org.eclipse.collections.api.block.function.primitive.ObjectBooleanToObjectFunction;
import org.eclipse.collections.api.collection.primitive.ImmutableBooleanCollection;
import org.eclipse.collections.api.factory.Sets;
import org.eclipse.collections.api.iterator.BooleanIterator;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.api.set.primitive.ImmutableBooleanSet;
import org.eclipse.collections.api.set.primitive.MutableBooleanSet;
import org.eclipse.collections.api.tuple.primitive.BooleanBooleanPair;
import org.eclipse.collections.impl.bag.mutable.primitive.BooleanHashBag;
import org.eclipse.collections.impl.block.factory.primitive.BooleanPredicates;
import org.eclipse.collections.impl.collection.immutable.primitive.AbstractImmutableBooleanCollectionTestCase;
import org.eclipse.collections.impl.list.mutable.primitive.BooleanArrayList;
import org.eclipse.collections.impl.math.MutableInteger;
import org.eclipse.collections.impl.set.mutable.UnifiedSet;
import org.eclipse.collections.impl.test.Verify;
import org.eclipse.collections.impl.tuple.primitive.PrimitiveTuples;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ImmutableBooleanHashSetTest_Purified extends AbstractImmutableBooleanCollectionTestCase {

    private ImmutableBooleanSet emptySet;

    private ImmutableBooleanSet falseSet;

    private ImmutableBooleanSet trueSet;

    private ImmutableBooleanSet trueFalseSet;

    @Override
    protected ImmutableBooleanSet classUnderTest() {
        return BooleanHashSet.newSetWith(true, false).toImmutable();
    }

    @Override
    protected ImmutableBooleanSet newWith(boolean... elements) {
        return BooleanHashSet.newSetWith(elements).toImmutable();
    }

    @Override
    protected MutableBooleanSet newMutableCollectionWith(boolean... elements) {
        return BooleanHashSet.newSetWith(elements);
    }

    @Override
    protected MutableSet<Object> newObjectCollectionWith(Object... elements) {
        return UnifiedSet.newSetWith(elements);
    }

    @BeforeEach
    public void setup() {
        this.emptySet = this.newWith();
        this.falseSet = this.newWith(false);
        this.trueSet = this.newWith(true);
        this.trueFalseSet = this.newWith(true, false);
    }

    private void assertSizeAndContains(ImmutableBooleanCollection collection, boolean... elements) {
        assertEquals(elements.length, collection.size());
        for (boolean i : elements) {
            assertTrue(collection.contains(i));
        }
    }

    @Override
    @Test
    public void isEmpty_1() {
        Verify.assertEmpty(this.emptySet);
    }

    @Override
    @Test
    public void isEmpty_2() {
        Verify.assertNotEmpty(this.falseSet);
    }

    @Override
    @Test
    public void isEmpty_3() {
        Verify.assertNotEmpty(this.trueSet);
    }

    @Override
    @Test
    public void isEmpty_4() {
        Verify.assertNotEmpty(this.trueFalseSet);
    }

    @Override
    @Test
    public void notEmpty_1() {
        assertFalse(this.emptySet.notEmpty());
    }

    @Override
    @Test
    public void notEmpty_2() {
        assertTrue(this.falseSet.notEmpty());
    }

    @Override
    @Test
    public void notEmpty_3() {
        assertTrue(this.trueSet.notEmpty());
    }

    @Override
    @Test
    public void notEmpty_4() {
        assertTrue(this.trueFalseSet.notEmpty());
    }

    @Override
    @Test
    public void contains_1() {
        assertFalse(this.emptySet.contains(true));
    }

    @Override
    @Test
    public void contains_2() {
        assertFalse(this.emptySet.contains(false));
    }

    @Override
    @Test
    public void contains_3() {
        assertTrue(this.falseSet.contains(false));
    }

    @Override
    @Test
    public void contains_4() {
        assertFalse(this.falseSet.contains(true));
    }

    @Override
    @Test
    public void contains_5() {
        assertTrue(this.trueSet.contains(true));
    }

    @Override
    @Test
    public void contains_6() {
        assertFalse(this.trueSet.contains(false));
    }

    @Override
    @Test
    public void contains_7() {
        assertTrue(this.trueFalseSet.contains(true));
    }

    @Override
    @Test
    public void contains_8() {
        assertTrue(this.trueFalseSet.contains(false));
    }

    @Override
    @Test
    public void containsAllArray_1() {
        assertFalse(this.emptySet.containsAll(true));
    }

    @Override
    @Test
    public void containsAllArray_2() {
        assertFalse(this.emptySet.containsAll(true, false));
    }

    @Override
    @Test
    public void containsAllArray_3() {
        assertTrue(this.falseSet.containsAll(false, false));
    }

    @Override
    @Test
    public void containsAllArray_4() {
        assertFalse(this.falseSet.containsAll(true, true));
    }

    @Override
    @Test
    public void containsAllArray_5() {
        assertFalse(this.falseSet.containsAll(true, false, true));
    }

    @Override
    @Test
    public void containsAllArray_6() {
        assertTrue(this.trueSet.containsAll(true, true));
    }

    @Override
    @Test
    public void containsAllArray_7() {
        assertFalse(this.trueSet.containsAll(false, false));
    }

    @Override
    @Test
    public void containsAllArray_8() {
        assertFalse(this.trueSet.containsAll(true, false, false));
    }

    @Override
    @Test
    public void containsAllArray_9() {
        assertTrue(this.trueFalseSet.containsAll(true, true));
    }

    @Override
    @Test
    public void containsAllArray_10() {
        assertTrue(this.trueFalseSet.containsAll(false, false));
    }

    @Override
    @Test
    public void containsAllArray_11() {
        assertTrue(this.trueFalseSet.containsAll(false, true, true));
    }

    @Override
    @Test
    public void containsAllIterable_1() {
        assertFalse(this.emptySet.containsAll(BooleanArrayList.newListWith(true)));
    }

    @Override
    @Test
    public void containsAllIterable_2() {
        assertFalse(this.emptySet.containsAll(BooleanArrayList.newListWith(true, false)));
    }

    @Override
    @Test
    public void containsAllIterable_3() {
        assertTrue(this.falseSet.containsAll(BooleanArrayList.newListWith(false, false)));
    }

    @Override
    @Test
    public void containsAllIterable_4() {
        assertFalse(this.falseSet.containsAll(BooleanArrayList.newListWith(true, true)));
    }

    @Override
    @Test
    public void containsAllIterable_5() {
        assertFalse(this.falseSet.containsAll(BooleanArrayList.newListWith(true, false, true)));
    }

    @Override
    @Test
    public void containsAllIterable_6() {
        assertTrue(this.trueSet.containsAll(BooleanArrayList.newListWith(true, true)));
    }

    @Override
    @Test
    public void containsAllIterable_7() {
        assertFalse(this.trueSet.containsAll(BooleanArrayList.newListWith(false, false)));
    }

    @Override
    @Test
    public void containsAllIterable_8() {
        assertFalse(this.trueSet.containsAll(BooleanArrayList.newListWith(true, false, false)));
    }

    @Override
    @Test
    public void containsAllIterable_9() {
        assertTrue(this.trueFalseSet.containsAll(BooleanArrayList.newListWith(true, true)));
    }

    @Override
    @Test
    public void containsAllIterable_10() {
        assertTrue(this.trueFalseSet.containsAll(BooleanArrayList.newListWith(false, false)));
    }

    @Override
    @Test
    public void containsAllIterable_11() {
        assertTrue(this.trueFalseSet.containsAll(BooleanArrayList.newListWith(false, true, true)));
    }

    @Override
    @Test
    public void toList_1() {
        assertEquals(new BooleanArrayList(), this.emptySet.toList());
    }

    @Override
    @Test
    public void toList_2() {
        assertEquals(BooleanArrayList.newListWith(false), this.falseSet.toList());
    }

    @Override
    @Test
    public void toList_3() {
        assertEquals(BooleanArrayList.newListWith(true), this.trueSet.toList());
    }

    @Override
    @Test
    public void toList_4() {
        assertTrue(BooleanArrayList.newListWith(false, true).equals(this.trueFalseSet.toList()) || BooleanArrayList.newListWith(true, false).equals(this.trueFalseSet.toList()));
    }

    @Override
    @Test
    public void toSet_1() {
        assertEquals(new BooleanHashSet(), this.emptySet.toSet());
    }

    @Override
    @Test
    public void toSet_2() {
        assertEquals(BooleanHashSet.newSetWith(false), this.falseSet.toSet());
    }

    @Override
    @Test
    public void toSet_3() {
        assertEquals(BooleanHashSet.newSetWith(true), this.trueSet.toSet());
    }

    @Override
    @Test
    public void toSet_4() {
        assertEquals(BooleanHashSet.newSetWith(false, true), this.trueFalseSet.toSet());
    }

    @Override
    @Test
    public void toBag_1() {
        assertEquals(new BooleanHashBag(), this.emptySet.toBag());
    }

    @Override
    @Test
    public void toBag_2() {
        assertEquals(BooleanHashBag.newBagWith(false), this.falseSet.toBag());
    }

    @Override
    @Test
    public void toBag_3() {
        assertEquals(BooleanHashBag.newBagWith(true), this.trueSet.toBag());
    }

    @Override
    @Test
    public void toBag_4() {
        assertEquals(BooleanHashBag.newBagWith(false, true), this.trueFalseSet.toBag());
    }

    @Override
    @Test
    public void testEquals_1() {
        assertNotEquals(this.falseSet, this.emptySet);
    }

    @Override
    @Test
    public void testEquals_2() {
        assertNotEquals(this.falseSet, this.trueSet);
    }

    @Override
    @Test
    public void testEquals_3() {
        assertNotEquals(this.falseSet, this.trueFalseSet);
    }

    @Override
    @Test
    public void testEquals_4() {
        assertNotEquals(this.trueSet, this.emptySet);
    }

    @Override
    @Test
    public void testEquals_5() {
        assertNotEquals(this.trueSet, this.trueFalseSet);
    }

    @Override
    @Test
    public void testEquals_6() {
        assertNotEquals(this.trueFalseSet, this.emptySet);
    }

    @Override
    @Test
    public void testEquals_7() {
        Verify.assertEqualsAndHashCode(this.newWith(false, true), this.trueFalseSet);
    }

    @Override
    @Test
    public void testEquals_8() {
        Verify.assertEqualsAndHashCode(this.newWith(true, false), this.trueFalseSet);
    }

    @Override
    @Test
    public void testEquals_9() {
        Verify.assertPostSerializedIdentity(this.emptySet);
    }

    @Override
    @Test
    public void testEquals_10() {
        Verify.assertPostSerializedIdentity(this.falseSet);
    }

    @Override
    @Test
    public void testEquals_11() {
        Verify.assertPostSerializedIdentity(this.trueSet);
    }

    @Override
    @Test
    public void testEquals_12() {
        Verify.assertPostSerializedIdentity(this.trueFalseSet);
    }

    @Override
    @Test
    public void testHashCode_1() {
        assertEquals(UnifiedSet.newSet().hashCode(), this.emptySet.hashCode());
    }

    @Override
    @Test
    public void testHashCode_2() {
        assertEquals(UnifiedSet.newSetWith(false).hashCode(), this.falseSet.hashCode());
    }

    @Override
    @Test
    public void testHashCode_3() {
        assertEquals(UnifiedSet.newSetWith(true).hashCode(), this.trueSet.hashCode());
    }

    @Override
    @Test
    public void testHashCode_4() {
        assertEquals(UnifiedSet.newSetWith(true, false).hashCode(), this.trueFalseSet.hashCode());
    }

    @Override
    @Test
    public void testHashCode_5() {
        assertEquals(UnifiedSet.newSetWith(false, true).hashCode(), this.trueFalseSet.hashCode());
    }

    @Override
    @Test
    public void testHashCode_6() {
        assertNotEquals(UnifiedSet.newSetWith(false).hashCode(), this.trueFalseSet.hashCode());
    }

    @Override
    @Test
    public void count_1() {
        assertEquals(0L, this.emptySet.count(BooleanPredicates.isTrue()));
    }

    @Override
    @Test
    public void count_2() {
        assertEquals(0L, this.falseSet.count(BooleanPredicates.isTrue()));
    }

    @Override
    @Test
    public void count_3() {
        assertEquals(1L, this.falseSet.count(BooleanPredicates.isFalse()));
    }

    @Override
    @Test
    public void count_4() {
        assertEquals(0L, this.trueSet.count(BooleanPredicates.isFalse()));
    }

    @Override
    @Test
    public void count_5() {
        assertEquals(1L, this.trueFalseSet.count(BooleanPredicates.isTrue()));
    }

    @Override
    @Test
    public void count_6() {
        assertEquals(0L, this.trueFalseSet.count(BooleanPredicates.and(BooleanPredicates.isFalse(), BooleanPredicates.isTrue())));
    }

    @Override
    @Test
    public void count_7() {
        assertEquals(1L, this.trueFalseSet.count(BooleanPredicates.isFalse()));
    }

    @Override
    @Test
    public void count_8() {
        assertEquals(1L, this.trueFalseSet.count(BooleanPredicates.isTrue()));
    }

    @Override
    @Test
    public void count_9() {
        assertEquals(2L, this.trueFalseSet.count(BooleanPredicates.or(BooleanPredicates.isFalse(), BooleanPredicates.isTrue())));
    }

    @Override
    @Test
    public void anySatisfy_1() {
        assertFalse(this.emptySet.anySatisfy(BooleanPredicates.or(BooleanPredicates.isTrue(), BooleanPredicates.isFalse())));
    }

    @Override
    @Test
    public void anySatisfy_2() {
        assertFalse(this.falseSet.anySatisfy(BooleanPredicates.isTrue()));
    }

    @Override
    @Test
    public void anySatisfy_3() {
        assertTrue(this.falseSet.anySatisfy(BooleanPredicates.isFalse()));
    }

    @Override
    @Test
    public void anySatisfy_4() {
        assertFalse(this.trueSet.anySatisfy(BooleanPredicates.isFalse()));
    }

    @Override
    @Test
    public void anySatisfy_5() {
        assertTrue(this.trueSet.anySatisfy(BooleanPredicates.isTrue()));
    }

    @Override
    @Test
    public void anySatisfy_6() {
        assertTrue(this.trueFalseSet.anySatisfy(BooleanPredicates.isTrue()));
    }

    @Override
    @Test
    public void anySatisfy_7() {
        assertTrue(this.trueFalseSet.anySatisfy(BooleanPredicates.isFalse()));
    }

    @Override
    @Test
    public void anySatisfy_8() {
        assertFalse(this.trueFalseSet.anySatisfy(BooleanPredicates.and(BooleanPredicates.isFalse(), BooleanPredicates.isTrue())));
    }

    @Override
    @Test
    public void allSatisfy_1() {
        assertTrue(this.emptySet.allSatisfy(BooleanPredicates.or(BooleanPredicates.isTrue(), BooleanPredicates.isFalse())));
    }

    @Override
    @Test
    public void allSatisfy_2() {
        assertFalse(this.falseSet.allSatisfy(BooleanPredicates.isTrue()));
    }

    @Override
    @Test
    public void allSatisfy_3() {
        assertTrue(this.falseSet.allSatisfy(BooleanPredicates.isFalse()));
    }

    @Override
    @Test
    public void allSatisfy_4() {
        assertFalse(this.trueSet.allSatisfy(BooleanPredicates.isFalse()));
    }

    @Override
    @Test
    public void allSatisfy_5() {
        assertTrue(this.trueSet.allSatisfy(BooleanPredicates.isTrue()));
    }

    @Override
    @Test
    public void allSatisfy_6() {
        assertFalse(this.trueFalseSet.allSatisfy(BooleanPredicates.isTrue()));
    }

    @Override
    @Test
    public void allSatisfy_7() {
        assertFalse(this.trueFalseSet.allSatisfy(BooleanPredicates.isFalse()));
    }

    @Override
    @Test
    public void allSatisfy_8() {
        assertFalse(this.trueFalseSet.allSatisfy(BooleanPredicates.and(BooleanPredicates.isFalse(), BooleanPredicates.isTrue())));
    }

    @Override
    @Test
    public void allSatisfy_9() {
        assertTrue(this.trueFalseSet.allSatisfy(BooleanPredicates.or(BooleanPredicates.isFalse(), BooleanPredicates.isTrue())));
    }

    @Override
    @Test
    public void noneSatisfy_1() {
        assertTrue(this.emptySet.noneSatisfy(BooleanPredicates.or(BooleanPredicates.isTrue(), BooleanPredicates.isFalse())));
    }

    @Override
    @Test
    public void noneSatisfy_2() {
        assertFalse(this.falseSet.noneSatisfy(BooleanPredicates.isFalse()));
    }

    @Override
    @Test
    public void noneSatisfy_3() {
        assertTrue(this.falseSet.noneSatisfy(BooleanPredicates.isTrue()));
    }

    @Override
    @Test
    public void noneSatisfy_4() {
        assertFalse(this.trueSet.noneSatisfy(BooleanPredicates.isTrue()));
    }

    @Override
    @Test
    public void noneSatisfy_5() {
        assertTrue(this.trueSet.noneSatisfy(BooleanPredicates.isFalse()));
    }

    @Override
    @Test
    public void noneSatisfy_6() {
        assertFalse(this.trueFalseSet.noneSatisfy(BooleanPredicates.isTrue()));
    }

    @Override
    @Test
    public void noneSatisfy_7() {
        assertFalse(this.trueFalseSet.noneSatisfy(BooleanPredicates.isFalse()));
    }

    @Override
    @Test
    public void noneSatisfy_8() {
        assertTrue(this.trueFalseSet.noneSatisfy(BooleanPredicates.and(BooleanPredicates.isFalse(), BooleanPredicates.isTrue())));
    }

    @Override
    @Test
    public void noneSatisfy_9() {
        assertFalse(this.trueFalseSet.noneSatisfy(BooleanPredicates.or(BooleanPredicates.isFalse(), BooleanPredicates.isTrue())));
    }

    @Override
    @Test
    public void select_1() {
        Verify.assertEmpty(this.emptySet.select(BooleanPredicates.or(BooleanPredicates.isTrue(), BooleanPredicates.isFalse())));
    }

    @Override
    @Test
    public void select_2() {
        Verify.assertEmpty(this.falseSet.select(BooleanPredicates.isTrue()));
    }

    @Override
    @Test
    public void select_3() {
        Verify.assertSize(1, this.falseSet.select(BooleanPredicates.isFalse()));
    }

    @Override
    @Test
    public void select_4() {
        Verify.assertEmpty(this.trueSet.select(BooleanPredicates.isFalse()));
    }

    @Override
    @Test
    public void select_5() {
        Verify.assertSize(1, this.trueSet.select(BooleanPredicates.isTrue()));
    }

    @Override
    @Test
    public void select_6() {
        Verify.assertSize(1, this.trueFalseSet.select(BooleanPredicates.isFalse()));
    }

    @Override
    @Test
    public void select_7() {
        Verify.assertSize(1, this.trueFalseSet.select(BooleanPredicates.isTrue()));
    }

    @Override
    @Test
    public void select_8() {
        Verify.assertEmpty(this.trueFalseSet.select(BooleanPredicates.and(BooleanPredicates.isFalse(), BooleanPredicates.isTrue())));
    }

    @Override
    @Test
    public void select_9() {
        Verify.assertSize(2, this.trueFalseSet.select(BooleanPredicates.or(BooleanPredicates.isFalse(), BooleanPredicates.isTrue())));
    }

    @Override
    @Test
    public void reject_1() {
        Verify.assertEmpty(this.emptySet.reject(BooleanPredicates.and(BooleanPredicates.isTrue(), BooleanPredicates.isFalse())));
    }

    @Override
    @Test
    public void reject_2() {
        Verify.assertEmpty(this.trueSet.reject(BooleanPredicates.isTrue()));
    }

    @Override
    @Test
    public void reject_3() {
        Verify.assertSize(1, this.trueSet.reject(BooleanPredicates.isFalse()));
    }

    @Override
    @Test
    public void reject_4() {
        Verify.assertEmpty(this.falseSet.reject(BooleanPredicates.isFalse()));
    }

    @Override
    @Test
    public void reject_5() {
        Verify.assertSize(1, this.falseSet.reject(BooleanPredicates.isTrue()));
    }

    @Override
    @Test
    public void reject_6() {
        Verify.assertSize(1, this.trueFalseSet.reject(BooleanPredicates.isFalse()));
    }

    @Override
    @Test
    public void reject_7() {
        Verify.assertSize(1, this.trueFalseSet.reject(BooleanPredicates.isTrue()));
    }

    @Override
    @Test
    public void reject_8() {
        Verify.assertEmpty(this.trueFalseSet.reject(BooleanPredicates.or(BooleanPredicates.isFalse(), BooleanPredicates.isTrue())));
    }

    @Override
    @Test
    public void reject_9() {
        Verify.assertSize(2, this.trueFalseSet.reject(BooleanPredicates.and(BooleanPredicates.isFalse(), BooleanPredicates.isTrue())));
    }

    @Override
    @Test
    public void detectIfNone_1() {
        assertTrue(this.emptySet.detectIfNone(BooleanPredicates.or(BooleanPredicates.isTrue(), BooleanPredicates.isFalse()), true));
    }

    @Override
    @Test
    public void detectIfNone_2() {
        assertFalse(this.emptySet.detectIfNone(BooleanPredicates.or(BooleanPredicates.isTrue(), BooleanPredicates.isFalse()), false));
    }

    @Override
    @Test
    public void detectIfNone_3() {
        assertTrue(this.falseSet.detectIfNone(BooleanPredicates.isTrue(), true));
    }

    @Override
    @Test
    public void detectIfNone_4() {
        assertFalse(this.falseSet.detectIfNone(BooleanPredicates.isTrue(), false));
    }

    @Override
    @Test
    public void detectIfNone_5() {
        assertFalse(this.falseSet.detectIfNone(BooleanPredicates.isFalse(), true));
    }

    @Override
    @Test
    public void detectIfNone_6() {
        assertFalse(this.falseSet.detectIfNone(BooleanPredicates.isFalse(), false));
    }

    @Override
    @Test
    public void detectIfNone_7() {
        assertTrue(this.trueSet.detectIfNone(BooleanPredicates.isFalse(), true));
    }

    @Override
    @Test
    public void detectIfNone_8() {
        assertFalse(this.trueSet.detectIfNone(BooleanPredicates.isFalse(), false));
    }

    @Override
    @Test
    public void detectIfNone_9() {
        assertTrue(this.trueSet.detectIfNone(BooleanPredicates.isTrue(), true));
    }

    @Override
    @Test
    public void detectIfNone_10() {
        assertTrue(this.trueSet.detectIfNone(BooleanPredicates.isTrue(), false));
    }

    @Override
    @Test
    public void detectIfNone_11() {
        assertTrue(this.trueFalseSet.detectIfNone(BooleanPredicates.and(BooleanPredicates.isFalse(), BooleanPredicates.isTrue()), true));
    }

    @Override
    @Test
    public void detectIfNone_12() {
        assertFalse(this.trueFalseSet.detectIfNone(BooleanPredicates.and(BooleanPredicates.isFalse(), BooleanPredicates.isTrue()), false));
    }

    @Override
    @Test
    public void detectIfNone_13() {
        assertFalse(this.trueFalseSet.detectIfNone(BooleanPredicates.isFalse(), true));
    }

    @Override
    @Test
    public void detectIfNone_14() {
        assertTrue(this.trueFalseSet.detectIfNone(BooleanPredicates.isTrue(), false));
    }

    @Override
    @Test
    public void testToString_1() {
        assertEquals("[]", this.emptySet.toString());
    }

    @Override
    @Test
    public void testToString_2() {
        assertEquals("[false]", this.falseSet.toString());
    }

    @Override
    @Test
    public void testToString_3() {
        assertEquals("[true]", this.trueSet.toString());
    }

    @Override
    @Test
    public void testToString_4() {
        assertTrue("[true, false]".equals(this.trueFalseSet.toString()) || "[false, true]".equals(this.trueFalseSet.toString()));
    }

    @Override
    @Test
    public void makeString_1() {
        assertEquals("", this.emptySet.makeString());
    }

    @Override
    @Test
    public void makeString_2() {
        assertEquals("false", this.falseSet.makeString());
    }

    @Override
    @Test
    public void makeString_3() {
        assertEquals("true", this.trueSet.makeString());
    }

    @Override
    @Test
    public void makeString_4() {
        assertTrue("true, false".equals(this.trueFalseSet.makeString()) || "false, true".equals(this.trueFalseSet.makeString()));
    }

    @Override
    @Test
    public void makeString_5() {
        assertEquals("", this.emptySet.makeString("/"));
    }

    @Override
    @Test
    public void makeString_6() {
        assertEquals("false", this.falseSet.makeString("/"));
    }

    @Override
    @Test
    public void makeString_7() {
        assertEquals("true", this.trueSet.makeString("/"));
    }

    @Override
    @Test
    public void makeString_8() {
        assertTrue("true/false".equals(this.trueFalseSet.makeString("/")) || "false/true".equals(this.trueFalseSet.makeString("/")), trueFalseSet.makeString("/"));
    }

    @Override
    @Test
    public void makeString_9() {
        assertEquals("[]", this.emptySet.makeString("[", "/", "]"));
    }

    @Override
    @Test
    public void makeString_10() {
        assertEquals("[false]", this.falseSet.makeString("[", "/", "]"));
    }

    @Override
    @Test
    public void makeString_11() {
        assertEquals("[true]", this.trueSet.makeString("[", "/", "]"));
    }

    @Override
    @Test
    public void makeString_12() {
        assertTrue("[true/false]".equals(this.trueFalseSet.makeString("[", "/", "]")) || "[false/true]".equals(this.trueFalseSet.makeString("[", "/", "]")), trueFalseSet.makeString("[", "/", "]"));
    }

    @Override
    @Test
    public void appendString_1() {
        StringBuilder appendable = new StringBuilder();
        this.emptySet.appendString(appendable);
        assertEquals("", appendable.toString());
    }

    @Override
    @Test
    public void appendString_2() {
        StringBuilder appendable1 = new StringBuilder();
        this.falseSet.appendString(appendable1);
        assertEquals("false", appendable1.toString());
    }

    @Override
    @Test
    public void appendString_3() {
        StringBuilder appendable2 = new StringBuilder();
        this.trueSet.appendString(appendable2);
        assertEquals("true", appendable2.toString());
    }

    @Override
    @Test
    public void appendString_4() {
        StringBuilder appendable3 = new StringBuilder();
        this.trueFalseSet.appendString(appendable3);
        assertTrue("true, false".equals(appendable3.toString()) || "false, true".equals(appendable3.toString()));
    }

    @Override
    @Test
    public void appendString_5() {
        StringBuilder appendable4 = new StringBuilder();
        this.trueFalseSet.appendString(appendable4, "[", ", ", "]");
        assertTrue("[true, false]".equals(appendable4.toString()) || "[false, true]".equals(appendable4.toString()));
    }

    @Override
    @Test
    public void asLazy_1() {
        Verify.assertInstanceOf(LazyBooleanIterable.class, this.emptySet.asLazy());
    }

    @Override
    @Test
    public void asLazy_2() {
        assertEquals(this.emptySet, this.emptySet.asLazy().toSet());
    }

    @Override
    @Test
    public void asLazy_3() {
        assertEquals(this.falseSet, this.falseSet.asLazy().toSet());
    }

    @Override
    @Test
    public void asLazy_4() {
        assertEquals(this.trueSet, this.trueSet.asLazy().toSet());
    }

    @Override
    @Test
    public void asLazy_5() {
        assertEquals(this.trueFalseSet, this.trueFalseSet.asLazy().toSet());
    }
}
