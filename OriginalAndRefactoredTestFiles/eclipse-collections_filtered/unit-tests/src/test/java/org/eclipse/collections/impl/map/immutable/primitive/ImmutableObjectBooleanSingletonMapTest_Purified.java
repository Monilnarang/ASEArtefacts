package org.eclipse.collections.impl.map.immutable.primitive;

import java.util.NoSuchElementException;
import org.eclipse.collections.api.iterator.BooleanIterator;
import org.eclipse.collections.api.map.primitive.ImmutableObjectBooleanMap;
import org.eclipse.collections.api.map.primitive.ObjectBooleanMap;
import org.eclipse.collections.impl.bag.mutable.primitive.BooleanHashBag;
import org.eclipse.collections.impl.block.factory.primitive.BooleanPredicates;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.list.mutable.primitive.BooleanArrayList;
import org.eclipse.collections.impl.map.mutable.primitive.ObjectBooleanHashMap;
import org.eclipse.collections.impl.set.mutable.primitive.BooleanHashSet;
import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ImmutableObjectBooleanSingletonMapTest_Purified extends AbstractImmutableObjectBooleanMapTestCase {

    @Override
    protected ImmutableObjectBooleanMap<String> classUnderTest() {
        return ObjectBooleanHashMap.newWithKeysValues("1", true).toImmutable();
    }

    @Override
    @Test
    public void containsKey_1() {
        assertFalse(this.classUnderTest().containsKey("0"));
    }

    @Override
    @Test
    public void containsKey_2() {
        assertTrue(this.classUnderTest().containsKey("1"));
    }

    @Override
    @Test
    public void containsKey_3() {
        assertFalse(this.classUnderTest().containsKey("2"));
    }

    @Override
    @Test
    public void containsKey_4() {
        assertFalse(this.classUnderTest().containsKey("3"));
    }

    @Override
    @Test
    public void containsKey_5() {
        assertFalse(this.classUnderTest().containsKey(null));
    }

    @Override
    @Test
    public void containsValue_1() {
        assertFalse(this.classUnderTest().containsValue(false));
    }

    @Override
    @Test
    public void containsValue_2() {
        assertTrue(this.classUnderTest().containsValue(true));
    }

    @Override
    @Test
    public void getIfAbsent_1() {
        assertTrue(this.classUnderTest().getIfAbsent("0", true));
    }

    @Override
    @Test
    public void getIfAbsent_2() {
        assertTrue(this.classUnderTest().getIfAbsent("1", false));
    }

    @Override
    @Test
    public void getIfAbsent_3() {
        assertFalse(this.classUnderTest().getIfAbsent("2", false));
    }

    @Override
    @Test
    public void getIfAbsent_4() {
        assertFalse(this.classUnderTest().getIfAbsent("5", false));
    }

    @Override
    @Test
    public void getIfAbsent_5() {
        assertTrue(this.classUnderTest().getIfAbsent("5", true));
    }

    @Override
    @Test
    public void getIfAbsent_6() {
        assertTrue(this.classUnderTest().getIfAbsent(null, true));
    }

    @Override
    @Test
    public void getIfAbsent_7() {
        assertFalse(this.classUnderTest().getIfAbsent(null, false));
    }

    @Override
    @Test
    public void anySatisfy_1() {
        assertTrue(this.classUnderTest().anySatisfy(BooleanPredicates.isTrue()));
    }

    @Override
    @Test
    public void anySatisfy_2() {
        assertFalse(this.classUnderTest().anySatisfy(BooleanPredicates.isFalse()));
    }

    @Override
    @Test
    public void anySatisfy_3() {
        assertTrue(this.classUnderTest().anySatisfy(BooleanPredicates.or(BooleanPredicates.isTrue(), BooleanPredicates.isFalse())));
    }

    @Override
    @Test
    public void anySatisfy_4() {
        assertFalse(this.classUnderTest().anySatisfy(BooleanPredicates.and(BooleanPredicates.isTrue(), BooleanPredicates.isFalse())));
    }

    @Override
    @Test
    public void contains_1() {
        assertFalse(this.classUnderTest().contains(false));
    }

    @Override
    @Test
    public void contains_2() {
        assertTrue(this.classUnderTest().contains(true));
    }

    @Override
    @Test
    public void get_1() {
        assertFalse(this.classUnderTest().get("0"));
    }

    @Override
    @Test
    public void get_2() {
        assertTrue(this.classUnderTest().get("1"));
    }

    @Override
    @Test
    public void get_3() {
        assertFalse(this.classUnderTest().get(null));
    }

    @Override
    @Test
    public void containsAll_1() {
        assertFalse(this.classUnderTest().containsAll(false, false));
    }

    @Override
    @Test
    public void containsAll_2() {
        assertFalse(this.classUnderTest().containsAll(true, false));
    }

    @Override
    @Test
    public void containsAll_3() {
        assertTrue(this.classUnderTest().containsAll(true));
    }

    @Override
    @Test
    public void containsAll_4() {
        assertTrue(this.classUnderTest().containsAll());
    }

    @Override
    @Test
    public void containsAllIterable_1() {
        assertFalse(this.classUnderTest().containsAll(BooleanArrayList.newListWith(false, false)));
    }

    @Override
    @Test
    public void containsAllIterable_2() {
        assertFalse(this.classUnderTest().containsAll(BooleanArrayList.newListWith(true, false)));
    }

    @Override
    @Test
    public void containsAllIterable_3() {
        assertTrue(this.classUnderTest().containsAll(BooleanArrayList.newListWith(true)));
    }

    @Override
    @Test
    public void containsAllIterable_4() {
        assertTrue(this.classUnderTest().containsAll(new BooleanArrayList()));
    }
}
