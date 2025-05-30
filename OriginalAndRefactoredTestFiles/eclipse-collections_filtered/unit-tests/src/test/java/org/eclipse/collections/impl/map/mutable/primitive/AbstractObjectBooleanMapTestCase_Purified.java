package org.eclipse.collections.impl.map.mutable.primitive;

import java.util.Arrays;
import java.util.NoSuchElementException;
import org.eclipse.collections.api.iterator.BooleanIterator;
import org.eclipse.collections.api.map.primitive.ImmutableObjectBooleanMap;
import org.eclipse.collections.api.map.primitive.ObjectBooleanMap;
import org.eclipse.collections.impl.bag.mutable.primitive.BooleanHashBag;
import org.eclipse.collections.impl.block.factory.primitive.BooleanPredicates;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.list.mutable.primitive.BooleanArrayList;
import org.eclipse.collections.impl.map.mutable.UnifiedMap;
import org.eclipse.collections.impl.set.mutable.primitive.BooleanHashSet;
import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class AbstractObjectBooleanMapTestCase_Purified {

    protected abstract ObjectBooleanMap<String> classUnderTest();

    protected abstract <T> ObjectBooleanMap<T> newWithKeysValues(T key1, boolean value1);

    protected abstract <T> ObjectBooleanMap<T> newWithKeysValues(T key1, boolean value1, T key2, boolean value2);

    protected abstract <T> ObjectBooleanMap<T> newWithKeysValues(T key1, boolean value1, T key2, boolean value2, T key3, boolean value3);

    protected abstract <T> ObjectBooleanMap<T> newWithKeysValues(T key1, boolean value1, T key2, boolean value2, T key3, boolean value3, T key4, boolean value4);

    protected abstract <T> ObjectBooleanMap<T> getEmptyMap();

    @Test
    public void get_1() {
        assertTrue(this.classUnderTest().get("0"));
    }

    @Test
    public void get_2() {
        assertTrue(this.classUnderTest().get("1"));
    }

    @Test
    public void get_3() {
        assertFalse(this.classUnderTest().get("2"));
    }

    @Test
    public void get_4() {
        assertFalse(this.classUnderTest().get("5"));
    }

    @Test
    public void getIfAbsent_1() {
        assertTrue(this.classUnderTest().getIfAbsent("0", false));
    }

    @Test
    public void getIfAbsent_2() {
        assertTrue(this.classUnderTest().getIfAbsent("1", false));
    }

    @Test
    public void getIfAbsent_3() {
        assertFalse(this.classUnderTest().getIfAbsent("2", true));
    }

    @Test
    public void getIfAbsent_4() {
        assertTrue(this.classUnderTest().getIfAbsent("5", true));
    }

    @Test
    public void getIfAbsent_5() {
        assertFalse(this.classUnderTest().getIfAbsent("5", false));
    }

    @Test
    public void getIfAbsent_6() {
        assertTrue(this.classUnderTest().getIfAbsent(null, true));
    }

    @Test
    public void getIfAbsent_7() {
        assertFalse(this.classUnderTest().getIfAbsent(null, false));
    }

    @Test
    public void containsKey_1() {
        assertTrue(this.classUnderTest().containsKey("0"));
    }

    @Test
    public void containsKey_2() {
        assertTrue(this.classUnderTest().containsKey("1"));
    }

    @Test
    public void containsKey_3() {
        assertTrue(this.classUnderTest().containsKey("2"));
    }

    @Test
    public void containsKey_4() {
        assertFalse(this.classUnderTest().containsKey("3"));
    }

    @Test
    public void containsKey_5() {
        assertFalse(this.classUnderTest().containsKey(null));
    }

    @Test
    public void containsValue_1() {
        assertTrue(this.classUnderTest().containsValue(true));
    }

    @Test
    public void containsValue_2() {
        assertTrue(this.classUnderTest().containsValue(false));
    }

    @Test
    public void size_1() {
        Verify.assertEmpty(this.getEmptyMap());
    }

    @Test
    public void size_2() {
        Verify.assertSize(1, this.newWithKeysValues(0, false));
    }

    @Test
    public void size_3() {
        Verify.assertSize(1, this.newWithKeysValues(1, true));
    }

    @Test
    public void size_4() {
        Verify.assertSize(1, this.newWithKeysValues(null, false));
    }

    @Test
    public void size_5() {
        Verify.assertSize(2, this.newWithKeysValues(1, false, 5, false));
    }

    @Test
    public void size_6() {
        Verify.assertSize(2, this.newWithKeysValues(0, true, 5, true));
    }

    @Test
    public void isEmpty_1() {
        Verify.assertEmpty(this.getEmptyMap());
    }

    @Test
    public void isEmpty_2() {
        assertFalse(this.classUnderTest().isEmpty());
    }

    @Test
    public void isEmpty_3() {
        assertFalse(this.newWithKeysValues(null, false).isEmpty());
    }

    @Test
    public void isEmpty_4() {
        assertFalse(this.newWithKeysValues(1, true).isEmpty());
    }

    @Test
    public void isEmpty_5() {
        assertFalse(this.newWithKeysValues(0, false).isEmpty());
    }

    @Test
    public void isEmpty_6() {
        assertFalse(this.newWithKeysValues(50, true).isEmpty());
    }

    @Test
    public void notEmpty_1() {
        assertFalse(this.getEmptyMap().notEmpty());
    }

    @Test
    public void notEmpty_2() {
        assertTrue(this.classUnderTest().notEmpty());
    }

    @Test
    public void notEmpty_3() {
        assertTrue(this.newWithKeysValues(1, true).notEmpty());
    }

    @Test
    public void notEmpty_4() {
        assertTrue(this.newWithKeysValues(null, false).notEmpty());
    }

    @Test
    public void notEmpty_5() {
        assertTrue(this.newWithKeysValues(0, true).notEmpty());
    }

    @Test
    public void notEmpty_6() {
        assertTrue(this.newWithKeysValues(50, false).notEmpty());
    }

    @Test
    public void testHashCode_1() {
        assertEquals(UnifiedMap.newWithKeysValues(0, false, 1, true, 32, true).hashCode(), this.newWithKeysValues(32, true, 0, false, 1, true).hashCode());
    }

    @Test
    public void testHashCode_2() {
        assertEquals(UnifiedMap.newWithKeysValues(50, true, 60, true, null, false).hashCode(), this.newWithKeysValues(50, true, 60, true, null, false).hashCode());
    }

    @Test
    public void testHashCode_3() {
        assertEquals(UnifiedMap.newMap().hashCode(), this.getEmptyMap().hashCode());
    }

    @Test
    public void count_1() {
        assertEquals(2L, this.classUnderTest().count(BooleanPredicates.isTrue()));
    }

    @Test
    public void count_2() {
        assertEquals(1L, this.classUnderTest().count(BooleanPredicates.isFalse()));
    }

    @Test
    public void count_3() {
        assertEquals(3L, this.classUnderTest().count(BooleanPredicates.or(BooleanPredicates.isTrue(), BooleanPredicates.isFalse())));
    }

    @Test
    public void count_4() {
        assertEquals(0L, this.classUnderTest().count(BooleanPredicates.and(BooleanPredicates.isTrue(), BooleanPredicates.isFalse())));
    }

    @Test
    public void anySatisfy_1() {
        assertTrue(this.classUnderTest().anySatisfy(BooleanPredicates.isTrue()));
    }

    @Test
    public void anySatisfy_2() {
        assertTrue(this.classUnderTest().anySatisfy(BooleanPredicates.isFalse()));
    }

    @Test
    public void anySatisfy_3() {
        assertTrue(this.classUnderTest().anySatisfy(BooleanPredicates.or(BooleanPredicates.isTrue(), BooleanPredicates.isFalse())));
    }

    @Test
    public void anySatisfy_4() {
        assertFalse(this.classUnderTest().anySatisfy(BooleanPredicates.and(BooleanPredicates.isTrue(), BooleanPredicates.isFalse())));
    }

    @Test
    public void allSatisfy_1() {
        assertFalse(this.classUnderTest().allSatisfy(BooleanPredicates.isTrue()));
    }

    @Test
    public void allSatisfy_2() {
        assertFalse(this.classUnderTest().allSatisfy(BooleanPredicates.isFalse()));
    }

    @Test
    public void allSatisfy_3() {
        assertTrue(this.classUnderTest().allSatisfy(BooleanPredicates.or(BooleanPredicates.isTrue(), BooleanPredicates.isFalse())));
    }

    @Test
    public void allSatisfy_4() {
        assertFalse(this.classUnderTest().allSatisfy(BooleanPredicates.and(BooleanPredicates.isTrue(), BooleanPredicates.isFalse())));
    }

    @Test
    public void noneSatisfy_1() {
        assertFalse(this.classUnderTest().noneSatisfy(BooleanPredicates.isTrue()));
    }

    @Test
    public void noneSatisfy_2() {
        assertFalse(this.classUnderTest().noneSatisfy(BooleanPredicates.isFalse()));
    }

    @Test
    public void noneSatisfy_3() {
        assertTrue(this.classUnderTest().noneSatisfy(BooleanPredicates.and(BooleanPredicates.isTrue(), BooleanPredicates.isFalse())));
    }

    @Test
    public void noneSatisfy_4() {
        assertFalse(this.classUnderTest().noneSatisfy(BooleanPredicates.or(BooleanPredicates.isTrue(), BooleanPredicates.isFalse())));
    }

    @Test
    public void detectIfNone_1() {
        assertTrue(this.classUnderTest().detectIfNone(BooleanPredicates.isTrue(), false));
    }

    @Test
    public void detectIfNone_2() {
        assertFalse(this.classUnderTest().detectIfNone(BooleanPredicates.isFalse(), true));
    }

    @Test
    public void detectIfNone_3() {
        assertFalse(this.newWithKeysValues("0", true, "1", true).detectIfNone(BooleanPredicates.and(BooleanPredicates.isTrue(), BooleanPredicates.isFalse()), false));
    }

    @Test
    public void contains_1() {
        assertTrue(this.classUnderTest().contains(true));
    }

    @Test
    public void contains_2() {
        assertTrue(this.classUnderTest().contains(false));
    }

    @Test
    public void containsAll_1() {
        assertTrue(this.classUnderTest().containsAll(true, false));
    }

    @Test
    public void containsAll_2() {
        assertTrue(this.classUnderTest().containsAll(true, true));
    }

    @Test
    public void containsAll_3() {
        assertTrue(this.classUnderTest().containsAll(false, false));
    }

    @Test
    public void containsAllIterable_1() {
        assertTrue(this.classUnderTest().containsAll(BooleanArrayList.newListWith(true, false)));
    }

    @Test
    public void containsAllIterable_2() {
        assertTrue(this.classUnderTest().containsAll(BooleanArrayList.newListWith(true, true)));
    }

    @Test
    public void containsAllIterable_3() {
        assertTrue(this.classUnderTest().containsAll(BooleanArrayList.newListWith(false, false)));
    }

    @Test
    public void asLazy_1() {
        Verify.assertSize(this.classUnderTest().toList().size(), this.classUnderTest().asLazy().toList());
    }

    @Test
    public void asLazy_2() {
        assertTrue(this.classUnderTest().asLazy().toList().containsAll(this.classUnderTest().toList()));
    }

    @Test
    public void toImmutable_1() {
        assertEquals(this.classUnderTest(), this.classUnderTest().toImmutable());
    }

    @Test
    public void toImmutable_2() {
        Verify.assertInstanceOf(ImmutableObjectBooleanMap.class, this.classUnderTest().toImmutable());
    }
}
