package org.eclipse.collections.impl.set.mutable.primitive;

import java.util.Arrays;
import java.util.NoSuchElementException;
import org.eclipse.collections.api.LazyBooleanIterable;
import org.eclipse.collections.api.block.function.primitive.BooleanToObjectFunction;
import org.eclipse.collections.api.block.function.primitive.ObjectBooleanToObjectFunction;
import org.eclipse.collections.api.collection.primitive.MutableBooleanCollection;
import org.eclipse.collections.api.factory.Sets;
import org.eclipse.collections.api.iterator.BooleanIterator;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.api.set.primitive.MutableBooleanSet;
import org.eclipse.collections.api.tuple.primitive.BooleanBooleanPair;
import org.eclipse.collections.impl.bag.mutable.primitive.BooleanHashBag;
import org.eclipse.collections.impl.block.factory.primitive.BooleanPredicates;
import org.eclipse.collections.impl.collection.mutable.primitive.AbstractMutableBooleanCollectionTestCase;
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
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class AbstractBooleanSetTestCase_Purified extends AbstractMutableBooleanCollectionTestCase {

    private MutableBooleanSet emptySet;

    private MutableBooleanSet setWithFalse;

    private MutableBooleanSet setWithTrue;

    private MutableBooleanSet setWithTrueFalse;

    @Override
    protected abstract MutableBooleanSet classUnderTest();

    @Override
    protected abstract MutableBooleanSet newWith(boolean... elements);

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
        this.setWithFalse = this.newWith(false);
        this.setWithTrue = this.newWith(true);
        this.setWithTrueFalse = this.newWith(true, false);
    }

    @Override
    @Test
    public void isEmpty_1() {
        Verify.assertEmpty(this.emptySet);
    }

    @Override
    @Test
    public void isEmpty_2() {
        Verify.assertNotEmpty(this.setWithFalse);
    }

    @Override
    @Test
    public void isEmpty_3() {
        Verify.assertNotEmpty(this.setWithTrue);
    }

    @Override
    @Test
    public void isEmpty_4() {
        Verify.assertNotEmpty(this.setWithTrueFalse);
    }

    @Override
    @Test
    public void notEmpty_1() {
        assertFalse(this.emptySet.notEmpty());
    }

    @Override
    @Test
    public void notEmpty_2() {
        assertTrue(this.setWithFalse.notEmpty());
    }

    @Override
    @Test
    public void notEmpty_3() {
        assertTrue(this.setWithTrue.notEmpty());
    }

    @Override
    @Test
    public void notEmpty_4() {
        assertTrue(this.setWithTrueFalse.notEmpty());
    }

    @Override
    @Test
    public void clear_1() {
        Verify.assertEmpty(this.emptySet);
    }

    @Override
    @Test
    public void clear_2() {
        Verify.assertEmpty(this.setWithFalse);
    }

    @Override
    @Test
    public void clear_3() {
        Verify.assertEmpty(this.setWithTrue);
    }

    @Override
    @Test
    public void clear_4() {
        Verify.assertEmpty(this.setWithTrueFalse);
    }

    @Override
    @Test
    public void clear_5() {
        assertFalse(this.setWithFalse.contains(false));
    }

    @Override
    @Test
    public void clear_6() {
        assertFalse(this.setWithTrue.contains(true));
    }

    @Override
    @Test
    public void clear_7() {
        assertFalse(this.setWithTrueFalse.contains(true));
    }

    @Override
    @Test
    public void clear_8() {
        assertFalse(this.setWithTrueFalse.contains(false));
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
        assertTrue(this.setWithFalse.contains(false));
    }

    @Override
    @Test
    public void contains_4() {
        assertFalse(this.setWithFalse.contains(true));
    }

    @Override
    @Test
    public void contains_5() {
        assertTrue(this.setWithTrue.contains(true));
    }

    @Override
    @Test
    public void contains_6() {
        assertFalse(this.setWithTrue.contains(false));
    }

    @Override
    @Test
    public void contains_7() {
        assertTrue(this.setWithTrueFalse.contains(true));
    }

    @Override
    @Test
    public void contains_8() {
        assertTrue(this.setWithTrueFalse.contains(false));
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
        assertTrue(this.setWithFalse.containsAll(false, false));
    }

    @Override
    @Test
    public void containsAllArray_4() {
        assertFalse(this.setWithFalse.containsAll(true, true));
    }

    @Override
    @Test
    public void containsAllArray_5() {
        assertFalse(this.setWithFalse.containsAll(true, false, true));
    }

    @Override
    @Test
    public void containsAllArray_6() {
        assertTrue(this.setWithTrue.containsAll(true, true));
    }

    @Override
    @Test
    public void containsAllArray_7() {
        assertFalse(this.setWithTrue.containsAll(false, false));
    }

    @Override
    @Test
    public void containsAllArray_8() {
        assertFalse(this.setWithTrue.containsAll(true, false, false));
    }

    @Override
    @Test
    public void containsAllArray_9() {
        assertTrue(this.setWithTrueFalse.containsAll(true, true));
    }

    @Override
    @Test
    public void containsAllArray_10() {
        assertTrue(this.setWithTrueFalse.containsAll(false, false));
    }

    @Override
    @Test
    public void containsAllArray_11() {
        assertTrue(this.setWithTrueFalse.containsAll(false, true, true));
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
        assertTrue(this.setWithFalse.containsAll(BooleanArrayList.newListWith(false, false)));
    }

    @Override
    @Test
    public void containsAllIterable_4() {
        assertFalse(this.setWithFalse.containsAll(BooleanArrayList.newListWith(true, true)));
    }

    @Override
    @Test
    public void containsAllIterable_5() {
        assertFalse(this.setWithFalse.containsAll(BooleanArrayList.newListWith(true, false, true)));
    }

    @Override
    @Test
    public void containsAllIterable_6() {
        assertTrue(this.setWithTrue.containsAll(BooleanArrayList.newListWith(true, true)));
    }

    @Override
    @Test
    public void containsAllIterable_7() {
        assertFalse(this.setWithTrue.containsAll(BooleanArrayList.newListWith(false, false)));
    }

    @Override
    @Test
    public void containsAllIterable_8() {
        assertFalse(this.setWithTrue.containsAll(BooleanArrayList.newListWith(true, false, false)));
    }

    @Override
    @Test
    public void containsAllIterable_9() {
        assertTrue(this.setWithTrueFalse.containsAll(BooleanArrayList.newListWith(true, true)));
    }

    @Override
    @Test
    public void containsAllIterable_10() {
        assertTrue(this.setWithTrueFalse.containsAll(BooleanArrayList.newListWith(false, false)));
    }

    @Override
    @Test
    public void containsAllIterable_11() {
        assertTrue(this.setWithTrueFalse.containsAll(BooleanArrayList.newListWith(false, true, true)));
    }

    @Override
    @Test
    public void addAllArray_1() {
        assertTrue(this.emptySet.addAll(true, false, true));
    }

    @Override
    @Test
    public void addAllArray_2() {
        assertEquals(BooleanHashSet.newSetWith(true, false), this.emptySet);
    }

    @Override
    @Test
    public void addAllArray_3() {
        assertFalse(this.setWithFalse.addAll(false, false));
    }

    @Override
    @Test
    public void addAllArray_4() {
        assertTrue(this.setWithFalse.addAll(true, false, true));
    }

    @Override
    @Test
    public void addAllArray_5() {
        assertEquals(BooleanHashSet.newSetWith(true, false), this.setWithFalse);
    }

    @Override
    @Test
    public void addAllArray_6() {
        assertFalse(this.setWithTrue.addAll(true, true));
    }

    @Override
    @Test
    public void addAllArray_7() {
        assertTrue(this.setWithTrue.addAll(true, false, true));
    }

    @Override
    @Test
    public void addAllArray_8() {
        assertEquals(BooleanHashSet.newSetWith(true, false), this.setWithTrue);
    }

    @Override
    @Test
    public void addAllArray_9() {
        assertFalse(this.setWithTrueFalse.addAll(true, false));
    }

    @Override
    @Test
    public void addAllArray_10() {
        assertEquals(BooleanHashSet.newSetWith(true, false), this.setWithTrueFalse);
    }

    @Override
    @Test
    public void addAllIterable_1() {
        assertTrue(this.emptySet.addAll(BooleanHashSet.newSetWith(true, false, true)));
    }

    @Override
    @Test
    public void addAllIterable_2() {
        assertEquals(BooleanHashSet.newSetWith(true, false), this.emptySet);
    }

    @Override
    @Test
    public void addAllIterable_3() {
        assertFalse(this.setWithFalse.addAll(BooleanHashSet.newSetWith(false, false)));
    }

    @Override
    @Test
    public void addAllIterable_4() {
        assertTrue(this.setWithFalse.addAll(BooleanHashSet.newSetWith(true, false, true)));
    }

    @Override
    @Test
    public void addAllIterable_5() {
        assertEquals(BooleanHashSet.newSetWith(true, false), this.setWithFalse);
    }

    @Override
    @Test
    public void addAllIterable_6() {
        assertFalse(this.setWithTrue.addAll(BooleanHashSet.newSetWith(true, true)));
    }

    @Override
    @Test
    public void addAllIterable_7() {
        assertTrue(this.setWithTrue.addAll(BooleanHashSet.newSetWith(true, false, true)));
    }

    @Override
    @Test
    public void addAllIterable_8() {
        assertEquals(BooleanHashSet.newSetWith(true, false), this.setWithTrue);
    }

    @Override
    @Test
    public void addAllIterable_9() {
        assertFalse(this.setWithTrueFalse.addAll(BooleanHashSet.newSetWith(true, false)));
    }

    @Override
    @Test
    public void addAllIterable_10() {
        assertEquals(BooleanHashSet.newSetWith(true, false), this.setWithTrueFalse);
    }

    @Override
    @Test
    public void without_1() {
        assertEquals(BooleanHashSet.newSetWith(true), this.setWithTrueFalse.without(false));
    }

    @Override
    @Test
    public void without_2() {
        assertSame(this.setWithTrueFalse, this.setWithTrueFalse.without(false));
    }

    @Override
    @Test
    public void without_3() {
        assertEquals(BooleanHashSet.newSetWith(false), this.newWith(true, false).without(true));
    }

    @Override
    @Test
    public void without_4() {
        assertEquals(BooleanHashSet.newSetWith(), this.setWithTrueFalse.without(true));
    }

    @Override
    @Test
    public void without_5() {
        assertEquals(BooleanHashSet.newSetWith(true), this.setWithTrue.without(false));
    }

    @Override
    @Test
    public void without_6() {
        assertEquals(BooleanHashSet.newSetWith(), this.setWithTrue.without(true));
    }

    @Override
    @Test
    public void without_7() {
        assertEquals(BooleanHashSet.newSetWith(false), this.setWithFalse.without(true));
    }

    @Override
    @Test
    public void without_8() {
        assertEquals(BooleanHashSet.newSetWith(), this.setWithFalse.without(false));
    }

    @Override
    @Test
    public void without_9() {
        assertEquals(new BooleanHashSet(), this.emptySet.without(true));
    }

    @Override
    @Test
    public void without_10() {
        assertEquals(new BooleanHashSet(), this.emptySet.without(false));
    }

    @Override
    @Test
    public void withoutAll_1() {
        assertEquals(BooleanHashSet.newSetWith(true), this.setWithTrueFalse.withoutAll(BooleanArrayList.newListWith(false, false)));
    }

    @Override
    @Test
    public void withoutAll_2() {
        assertSame(this.setWithTrueFalse, this.setWithTrueFalse.withoutAll(BooleanArrayList.newListWith(false, false)));
    }

    @Override
    @Test
    public void withoutAll_3() {
        assertEquals(BooleanHashSet.newSetWith(false), this.newWith(true, false).withoutAll(BooleanArrayList.newListWith(true, true)));
    }

    @Override
    @Test
    public void withoutAll_4() {
        assertEquals(BooleanHashSet.newSetWith(), this.newWith(true, false).withoutAll(BooleanArrayList.newListWith(true, false)));
    }

    @Override
    @Test
    public void withoutAll_5() {
        assertEquals(BooleanHashSet.newSetWith(), this.setWithTrueFalse.withoutAll(BooleanArrayList.newListWith(true)));
    }

    @Override
    @Test
    public void withoutAll_6() {
        assertEquals(BooleanHashSet.newSetWith(true), this.setWithTrue.withoutAll(BooleanArrayList.newListWith(false)));
    }

    @Override
    @Test
    public void withoutAll_7() {
        assertEquals(BooleanHashSet.newSetWith(), this.setWithTrue.withoutAll(BooleanArrayList.newListWith(true)));
    }

    @Override
    @Test
    public void withoutAll_8() {
        assertEquals(BooleanHashSet.newSetWith(), this.newWith(true).withoutAll(BooleanArrayList.newListWith(false, true)));
    }

    @Override
    @Test
    public void withoutAll_9() {
        assertEquals(BooleanHashSet.newSetWith(false), this.setWithFalse.withoutAll(BooleanArrayList.newListWith(true)));
    }

    @Override
    @Test
    public void withoutAll_10() {
        assertEquals(BooleanHashSet.newSetWith(), this.setWithFalse.withoutAll(BooleanArrayList.newListWith(false)));
    }

    @Override
    @Test
    public void withoutAll_11() {
        assertEquals(BooleanHashSet.newSetWith(), this.newWith(false).withoutAll(BooleanArrayList.newListWith(true, false)));
    }

    @Override
    @Test
    public void withoutAll_12() {
        assertEquals(new BooleanHashSet(), this.emptySet.withoutAll(BooleanArrayList.newListWith(true)));
    }

    @Override
    @Test
    public void withoutAll_13() {
        assertEquals(new BooleanHashSet(), this.emptySet.withoutAll(BooleanArrayList.newListWith(false)));
    }

    @Override
    @Test
    public void withoutAll_14() {
        assertEquals(new BooleanHashSet(), this.emptySet.withoutAll(BooleanArrayList.newListWith(false, true)));
    }

    @Override
    @Test
    public void toList_1() {
        assertEquals(new BooleanArrayList(), this.emptySet.toList());
    }

    @Override
    @Test
    public void toList_2() {
        assertEquals(BooleanArrayList.newListWith(false), this.setWithFalse.toList());
    }

    @Override
    @Test
    public void toList_3() {
        assertEquals(BooleanArrayList.newListWith(true), this.setWithTrue.toList());
    }

    @Override
    @Test
    public void toList_4() {
        assertTrue(BooleanArrayList.newListWith(false, true).equals(this.setWithTrueFalse.toList()) || BooleanArrayList.newListWith(true, false).equals(this.setWithTrueFalse.toList()));
    }

    @Override
    @Test
    public void toSet_1() {
        assertEquals(new BooleanHashSet(), this.emptySet.toSet());
    }

    @Override
    @Test
    public void toSet_2() {
        assertEquals(BooleanHashSet.newSetWith(false), this.setWithFalse.toSet());
    }

    @Override
    @Test
    public void toSet_3() {
        assertEquals(BooleanHashSet.newSetWith(true), this.setWithTrue.toSet());
    }

    @Override
    @Test
    public void toSet_4() {
        assertEquals(BooleanHashSet.newSetWith(false, true), this.setWithTrueFalse.toSet());
    }

    @Override
    @Test
    public void toBag_1() {
        assertEquals(new BooleanHashBag(), this.emptySet.toBag());
    }

    @Override
    @Test
    public void toBag_2() {
        assertEquals(BooleanHashBag.newBagWith(false), this.setWithFalse.toBag());
    }

    @Override
    @Test
    public void toBag_3() {
        assertEquals(BooleanHashBag.newBagWith(true), this.setWithTrue.toBag());
    }

    @Override
    @Test
    public void toBag_4() {
        assertEquals(BooleanHashBag.newBagWith(false, true), this.setWithTrueFalse.toBag());
    }

    @Override
    @Test
    public void testEquals_1() {
        assertNotEquals(this.setWithFalse, this.emptySet);
    }

    @Override
    @Test
    public void testEquals_2() {
        assertNotEquals(this.setWithFalse, this.setWithTrue);
    }

    @Override
    @Test
    public void testEquals_3() {
        assertNotEquals(this.setWithFalse, this.setWithTrueFalse);
    }

    @Override
    @Test
    public void testEquals_4() {
        assertNotEquals(this.setWithTrue, this.emptySet);
    }

    @Override
    @Test
    public void testEquals_5() {
        assertNotEquals(this.setWithTrue, this.setWithTrueFalse);
    }

    @Override
    @Test
    public void testEquals_6() {
        assertNotEquals(this.setWithTrueFalse, this.emptySet);
    }

    @Override
    @Test
    public void testEquals_7() {
        Verify.assertEqualsAndHashCode(this.newWith(false, true), this.setWithTrueFalse);
    }

    @Override
    @Test
    public void testEquals_8() {
        Verify.assertEqualsAndHashCode(this.newWith(true, false), this.setWithTrueFalse);
    }

    @Override
    @Test
    public void testEquals_9() {
        Verify.assertPostSerializedEqualsAndHashCode(this.emptySet);
    }

    @Override
    @Test
    public void testEquals_10() {
        Verify.assertPostSerializedEqualsAndHashCode(this.setWithFalse);
    }

    @Override
    @Test
    public void testEquals_11() {
        Verify.assertPostSerializedEqualsAndHashCode(this.setWithTrue);
    }

    @Override
    @Test
    public void testEquals_12() {
        Verify.assertPostSerializedEqualsAndHashCode(this.setWithTrueFalse);
    }

    @Override
    @Test
    public void testHashCode_1() {
        assertEquals(UnifiedSet.newSet().hashCode(), this.emptySet.hashCode());
    }

    @Override
    @Test
    public void testHashCode_2() {
        assertEquals(UnifiedSet.newSetWith(false).hashCode(), this.setWithFalse.hashCode());
    }

    @Override
    @Test
    public void testHashCode_3() {
        assertEquals(UnifiedSet.newSetWith(true).hashCode(), this.setWithTrue.hashCode());
    }

    @Override
    @Test
    public void testHashCode_4() {
        assertEquals(UnifiedSet.newSetWith(true, false).hashCode(), this.setWithTrueFalse.hashCode());
    }

    @Override
    @Test
    public void testHashCode_5() {
        assertEquals(UnifiedSet.newSetWith(false, true).hashCode(), this.setWithTrueFalse.hashCode());
    }

    @Override
    @Test
    public void testHashCode_6() {
        assertNotEquals(UnifiedSet.newSetWith(false).hashCode(), this.setWithTrueFalse.hashCode());
    }

    @Override
    @Test
    public void count_1() {
        assertEquals(0L, this.emptySet.count(BooleanPredicates.isTrue()));
    }

    @Override
    @Test
    public void count_2() {
        assertEquals(0L, this.setWithFalse.count(BooleanPredicates.isTrue()));
    }

    @Override
    @Test
    public void count_3() {
        assertEquals(1L, this.setWithFalse.count(BooleanPredicates.isFalse()));
    }

    @Override
    @Test
    public void count_4() {
        assertEquals(0L, this.setWithTrue.count(BooleanPredicates.isFalse()));
    }

    @Override
    @Test
    public void count_5() {
        assertEquals(1L, this.setWithTrueFalse.count(BooleanPredicates.isTrue()));
    }

    @Override
    @Test
    public void count_6() {
        assertEquals(0L, this.setWithTrueFalse.count(BooleanPredicates.and(BooleanPredicates.isFalse(), BooleanPredicates.isTrue())));
    }

    @Override
    @Test
    public void count_7() {
        assertEquals(1L, this.setWithTrueFalse.count(BooleanPredicates.isFalse()));
    }

    @Override
    @Test
    public void count_8() {
        assertEquals(1L, this.setWithTrueFalse.count(BooleanPredicates.isTrue()));
    }

    @Override
    @Test
    public void count_9() {
        assertEquals(2L, this.setWithTrueFalse.count(BooleanPredicates.or(BooleanPredicates.isFalse(), BooleanPredicates.isTrue())));
    }

    @Override
    @Test
    public void anySatisfy_1() {
        assertFalse(this.emptySet.anySatisfy(BooleanPredicates.or(BooleanPredicates.isTrue(), BooleanPredicates.isFalse())));
    }

    @Override
    @Test
    public void anySatisfy_2() {
        assertFalse(this.setWithFalse.anySatisfy(BooleanPredicates.isTrue()));
    }

    @Override
    @Test
    public void anySatisfy_3() {
        assertTrue(this.setWithFalse.anySatisfy(BooleanPredicates.isFalse()));
    }

    @Override
    @Test
    public void anySatisfy_4() {
        assertFalse(this.setWithTrue.anySatisfy(BooleanPredicates.isFalse()));
    }

    @Override
    @Test
    public void anySatisfy_5() {
        assertTrue(this.setWithTrue.anySatisfy(BooleanPredicates.isTrue()));
    }

    @Override
    @Test
    public void anySatisfy_6() {
        assertTrue(this.setWithTrueFalse.anySatisfy(BooleanPredicates.isTrue()));
    }

    @Override
    @Test
    public void anySatisfy_7() {
        assertTrue(this.setWithTrueFalse.anySatisfy(BooleanPredicates.isFalse()));
    }

    @Override
    @Test
    public void anySatisfy_8() {
        assertFalse(this.setWithTrueFalse.anySatisfy(BooleanPredicates.and(BooleanPredicates.isFalse(), BooleanPredicates.isTrue())));
    }

    @Override
    @Test
    public void allSatisfy_1() {
        assertTrue(this.emptySet.allSatisfy(BooleanPredicates.or(BooleanPredicates.isTrue(), BooleanPredicates.isFalse())));
    }

    @Override
    @Test
    public void allSatisfy_2() {
        assertFalse(this.setWithFalse.allSatisfy(BooleanPredicates.isTrue()));
    }

    @Override
    @Test
    public void allSatisfy_3() {
        assertTrue(this.setWithFalse.allSatisfy(BooleanPredicates.isFalse()));
    }

    @Override
    @Test
    public void allSatisfy_4() {
        assertFalse(this.setWithTrue.allSatisfy(BooleanPredicates.isFalse()));
    }

    @Override
    @Test
    public void allSatisfy_5() {
        assertTrue(this.setWithTrue.allSatisfy(BooleanPredicates.isTrue()));
    }

    @Override
    @Test
    public void allSatisfy_6() {
        assertFalse(this.setWithTrueFalse.allSatisfy(BooleanPredicates.isTrue()));
    }

    @Override
    @Test
    public void allSatisfy_7() {
        assertFalse(this.setWithTrueFalse.allSatisfy(BooleanPredicates.isFalse()));
    }

    @Override
    @Test
    public void allSatisfy_8() {
        assertFalse(this.setWithTrueFalse.allSatisfy(BooleanPredicates.and(BooleanPredicates.isFalse(), BooleanPredicates.isTrue())));
    }

    @Override
    @Test
    public void allSatisfy_9() {
        assertTrue(this.setWithTrueFalse.allSatisfy(BooleanPredicates.or(BooleanPredicates.isFalse(), BooleanPredicates.isTrue())));
    }

    @Override
    @Test
    public void noneSatisfy_1() {
        assertTrue(this.emptySet.noneSatisfy(BooleanPredicates.or(BooleanPredicates.isTrue(), BooleanPredicates.isFalse())));
    }

    @Override
    @Test
    public void noneSatisfy_2() {
        assertFalse(this.setWithFalse.noneSatisfy(BooleanPredicates.isFalse()));
    }

    @Override
    @Test
    public void noneSatisfy_3() {
        assertTrue(this.setWithFalse.noneSatisfy(BooleanPredicates.isTrue()));
    }

    @Override
    @Test
    public void noneSatisfy_4() {
        assertFalse(this.setWithTrue.noneSatisfy(BooleanPredicates.isTrue()));
    }

    @Override
    @Test
    public void noneSatisfy_5() {
        assertTrue(this.setWithTrue.noneSatisfy(BooleanPredicates.isFalse()));
    }

    @Override
    @Test
    public void noneSatisfy_6() {
        assertFalse(this.setWithTrueFalse.noneSatisfy(BooleanPredicates.isTrue()));
    }

    @Override
    @Test
    public void noneSatisfy_7() {
        assertFalse(this.setWithTrueFalse.noneSatisfy(BooleanPredicates.isFalse()));
    }

    @Override
    @Test
    public void noneSatisfy_8() {
        assertTrue(this.setWithTrueFalse.noneSatisfy(BooleanPredicates.and(BooleanPredicates.isFalse(), BooleanPredicates.isTrue())));
    }

    @Override
    @Test
    public void noneSatisfy_9() {
        assertFalse(this.setWithTrueFalse.noneSatisfy(BooleanPredicates.or(BooleanPredicates.isFalse(), BooleanPredicates.isTrue())));
    }

    @Override
    @Test
    public void select_1() {
        Verify.assertEmpty(this.emptySet.select(BooleanPredicates.or(BooleanPredicates.isTrue(), BooleanPredicates.isFalse())));
    }

    @Override
    @Test
    public void select_2() {
        Verify.assertEmpty(this.setWithFalse.select(BooleanPredicates.isTrue()));
    }

    @Override
    @Test
    public void select_3() {
        Verify.assertSize(1, this.setWithFalse.select(BooleanPredicates.isFalse()));
    }

    @Override
    @Test
    public void select_4() {
        Verify.assertEmpty(this.setWithTrue.select(BooleanPredicates.isFalse()));
    }

    @Override
    @Test
    public void select_5() {
        Verify.assertSize(1, this.setWithTrue.select(BooleanPredicates.isTrue()));
    }

    @Override
    @Test
    public void select_6() {
        Verify.assertSize(1, this.setWithTrueFalse.select(BooleanPredicates.isFalse()));
    }

    @Override
    @Test
    public void select_7() {
        Verify.assertSize(1, this.setWithTrueFalse.select(BooleanPredicates.isTrue()));
    }

    @Override
    @Test
    public void select_8() {
        Verify.assertEmpty(this.setWithTrueFalse.select(BooleanPredicates.and(BooleanPredicates.isFalse(), BooleanPredicates.isTrue())));
    }

    @Override
    @Test
    public void select_9() {
        Verify.assertSize(2, this.setWithTrueFalse.select(BooleanPredicates.or(BooleanPredicates.isFalse(), BooleanPredicates.isTrue())));
    }

    @Override
    @Test
    public void reject_1() {
        Verify.assertEmpty(this.emptySet.reject(BooleanPredicates.and(BooleanPredicates.isTrue(), BooleanPredicates.isFalse())));
    }

    @Override
    @Test
    public void reject_2() {
        Verify.assertEmpty(this.setWithTrue.reject(BooleanPredicates.isTrue()));
    }

    @Override
    @Test
    public void reject_3() {
        Verify.assertSize(1, this.setWithTrue.reject(BooleanPredicates.isFalse()));
    }

    @Override
    @Test
    public void reject_4() {
        Verify.assertEmpty(this.setWithFalse.reject(BooleanPredicates.isFalse()));
    }

    @Override
    @Test
    public void reject_5() {
        Verify.assertSize(1, this.setWithFalse.reject(BooleanPredicates.isTrue()));
    }

    @Override
    @Test
    public void reject_6() {
        Verify.assertSize(1, this.setWithTrueFalse.reject(BooleanPredicates.isFalse()));
    }

    @Override
    @Test
    public void reject_7() {
        Verify.assertSize(1, this.setWithTrueFalse.reject(BooleanPredicates.isTrue()));
    }

    @Override
    @Test
    public void reject_8() {
        Verify.assertEmpty(this.setWithTrueFalse.reject(BooleanPredicates.or(BooleanPredicates.isFalse(), BooleanPredicates.isTrue())));
    }

    @Override
    @Test
    public void reject_9() {
        Verify.assertSize(2, this.setWithTrueFalse.reject(BooleanPredicates.and(BooleanPredicates.isFalse(), BooleanPredicates.isTrue())));
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
        assertTrue(this.setWithFalse.detectIfNone(BooleanPredicates.isTrue(), true));
    }

    @Override
    @Test
    public void detectIfNone_4() {
        assertFalse(this.setWithFalse.detectIfNone(BooleanPredicates.isTrue(), false));
    }

    @Override
    @Test
    public void detectIfNone_5() {
        assertFalse(this.setWithFalse.detectIfNone(BooleanPredicates.isFalse(), true));
    }

    @Override
    @Test
    public void detectIfNone_6() {
        assertFalse(this.setWithFalse.detectIfNone(BooleanPredicates.isFalse(), false));
    }

    @Override
    @Test
    public void detectIfNone_7() {
        assertTrue(this.setWithTrue.detectIfNone(BooleanPredicates.isFalse(), true));
    }

    @Override
    @Test
    public void detectIfNone_8() {
        assertFalse(this.setWithTrue.detectIfNone(BooleanPredicates.isFalse(), false));
    }

    @Override
    @Test
    public void detectIfNone_9() {
        assertTrue(this.setWithTrue.detectIfNone(BooleanPredicates.isTrue(), true));
    }

    @Override
    @Test
    public void detectIfNone_10() {
        assertTrue(this.setWithTrue.detectIfNone(BooleanPredicates.isTrue(), false));
    }

    @Override
    @Test
    public void detectIfNone_11() {
        assertTrue(this.setWithTrueFalse.detectIfNone(BooleanPredicates.and(BooleanPredicates.isFalse(), BooleanPredicates.isTrue()), true));
    }

    @Override
    @Test
    public void detectIfNone_12() {
        assertFalse(this.setWithTrueFalse.detectIfNone(BooleanPredicates.and(BooleanPredicates.isFalse(), BooleanPredicates.isTrue()), false));
    }

    @Override
    @Test
    public void detectIfNone_13() {
        assertFalse(this.setWithTrueFalse.detectIfNone(BooleanPredicates.isFalse(), true));
    }

    @Override
    @Test
    public void detectIfNone_14() {
        assertTrue(this.setWithTrueFalse.detectIfNone(BooleanPredicates.isTrue(), false));
    }

    @Override
    @Test
    public void testToString_1() {
        assertEquals("[]", this.emptySet.toString());
    }

    @Override
    @Test
    public void testToString_2() {
        assertEquals("[false]", this.setWithFalse.toString());
    }

    @Override
    @Test
    public void testToString_3() {
        assertEquals("[true]", this.setWithTrue.toString());
    }

    @Override
    @Test
    public void testToString_4() {
        assertTrue("[true, false]".equals(this.setWithTrueFalse.toString()) || "[false, true]".equals(this.setWithTrueFalse.toString()));
    }

    @Override
    @Test
    public void makeString_1() {
        assertEquals("", this.emptySet.makeString());
    }

    @Override
    @Test
    public void makeString_2() {
        assertEquals("false", this.setWithFalse.makeString());
    }

    @Override
    @Test
    public void makeString_3() {
        assertEquals("true", this.setWithTrue.makeString());
    }

    @Override
    @Test
    public void makeString_4() {
        assertTrue("true, false".equals(this.setWithTrueFalse.makeString()) || "false, true".equals(this.setWithTrueFalse.makeString()));
    }

    @Override
    @Test
    public void makeString_5() {
        assertEquals("", this.emptySet.makeString("/"));
    }

    @Override
    @Test
    public void makeString_6() {
        assertEquals("false", this.setWithFalse.makeString("/"));
    }

    @Override
    @Test
    public void makeString_7() {
        assertEquals("true", this.setWithTrue.makeString("/"));
    }

    @Override
    @Test
    public void makeString_8() {
        assertTrue("true/false".equals(this.setWithTrueFalse.makeString("/")) || "false/true".equals(this.setWithTrueFalse.makeString("/")), this.setWithTrueFalse.makeString("/"));
    }

    @Override
    @Test
    public void makeString_9() {
        assertEquals("[]", this.emptySet.makeString("[", "/", "]"));
    }

    @Override
    @Test
    public void makeString_10() {
        assertEquals("[false]", this.setWithFalse.makeString("[", "/", "]"));
    }

    @Override
    @Test
    public void makeString_11() {
        assertEquals("[true]", this.setWithTrue.makeString("[", "/", "]"));
    }

    @Override
    @Test
    public void makeString_12() {
        assertTrue("[true/false]".equals(this.setWithTrueFalse.makeString("[", "/", "]")) || "[false/true]".equals(this.setWithTrueFalse.makeString("[", "/", "]")), this.setWithTrueFalse.makeString("[", "/", "]"));
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
        this.setWithFalse.appendString(appendable1);
        assertEquals("false", appendable1.toString());
    }

    @Override
    @Test
    public void appendString_3() {
        StringBuilder appendable2 = new StringBuilder();
        this.setWithTrue.appendString(appendable2);
        assertEquals("true", appendable2.toString());
    }

    @Override
    @Test
    public void appendString_4() {
        StringBuilder appendable3 = new StringBuilder();
        this.setWithTrueFalse.appendString(appendable3);
        assertTrue("true, false".equals(appendable3.toString()) || "false, true".equals(appendable3.toString()));
    }

    @Override
    @Test
    public void appendString_5() {
        StringBuilder appendable4 = new StringBuilder();
        this.setWithTrueFalse.appendString(appendable4, "[", ", ", "]");
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
        assertEquals(this.setWithFalse, this.setWithFalse.asLazy().toSet());
    }

    @Override
    @Test
    public void asLazy_4() {
        assertEquals(this.setWithTrue, this.setWithTrue.asLazy().toSet());
    }

    @Override
    @Test
    public void asLazy_5() {
        assertEquals(this.setWithTrueFalse, this.setWithTrueFalse.asLazy().toSet());
    }

    @Override
    @Test
    public void asSynchronized_1() {
        Verify.assertInstanceOf(SynchronizedBooleanSet.class, this.emptySet.asSynchronized());
    }

    @Override
    @Test
    public void asSynchronized_2() {
        assertEquals(new SynchronizedBooleanSet(this.emptySet), this.emptySet.asSynchronized());
    }

    @Override
    @Test
    public void asSynchronized_3() {
        assertEquals(new SynchronizedBooleanSet(this.setWithFalse), this.setWithFalse.asSynchronized());
    }

    @Override
    @Test
    public void asSynchronized_4() {
        assertEquals(new SynchronizedBooleanSet(this.setWithTrue), this.setWithTrue.asSynchronized());
    }

    @Override
    @Test
    public void asSynchronized_5() {
        assertEquals(new SynchronizedBooleanSet(this.setWithTrueFalse), this.setWithTrueFalse.asSynchronized());
    }

    @Override
    @Test
    public void asUnmodifiable_1() {
        Verify.assertInstanceOf(UnmodifiableBooleanSet.class, this.emptySet.asUnmodifiable());
    }

    @Override
    @Test
    public void asUnmodifiable_2() {
        assertEquals(new UnmodifiableBooleanSet(this.emptySet), this.emptySet.asUnmodifiable());
    }

    @Override
    @Test
    public void asUnmodifiable_3() {
        assertEquals(new UnmodifiableBooleanSet(this.setWithFalse), this.setWithFalse.asUnmodifiable());
    }

    @Override
    @Test
    public void asUnmodifiable_4() {
        assertEquals(new UnmodifiableBooleanSet(this.setWithTrue), this.setWithTrue.asUnmodifiable());
    }

    @Override
    @Test
    public void asUnmodifiable_5() {
        assertEquals(new UnmodifiableBooleanSet(this.setWithTrueFalse), this.setWithTrueFalse.asUnmodifiable());
    }
}
