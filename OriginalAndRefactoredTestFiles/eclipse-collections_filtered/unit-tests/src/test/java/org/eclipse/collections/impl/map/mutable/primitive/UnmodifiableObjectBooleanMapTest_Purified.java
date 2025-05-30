package org.eclipse.collections.impl.map.mutable.primitive;

import org.eclipse.collections.api.block.function.primitive.BooleanFunction;
import org.eclipse.collections.api.iterator.MutableBooleanIterator;
import org.eclipse.collections.impl.factory.Iterables;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.list.mutable.primitive.BooleanArrayList;
import org.eclipse.collections.impl.test.Verify;
import org.eclipse.collections.impl.tuple.primitive.PrimitiveTuples;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UnmodifiableObjectBooleanMapTest_Purified extends AbstractMutableObjectBooleanMapTestCase {

    private final UnmodifiableObjectBooleanMap<String> map = this.classUnderTest();

    @Override
    protected UnmodifiableObjectBooleanMap<String> classUnderTest() {
        return new UnmodifiableObjectBooleanMap<>(ObjectBooleanHashMap.newWithKeysValues("0", true, "1", true, "2", false));
    }

    @Override
    protected <T> UnmodifiableObjectBooleanMap<T> newWithKeysValues(T key1, boolean value1) {
        return new UnmodifiableObjectBooleanMap<>(ObjectBooleanHashMap.newWithKeysValues(key1, value1));
    }

    @Override
    protected <T> UnmodifiableObjectBooleanMap<T> newWithKeysValues(T key1, boolean value1, T key2, boolean value2) {
        return new UnmodifiableObjectBooleanMap<>(ObjectBooleanHashMap.newWithKeysValues(key1, value1, key2, value2));
    }

    @Override
    protected <T> UnmodifiableObjectBooleanMap<T> newWithKeysValues(T key1, boolean value1, T key2, boolean value2, T key3, boolean value3) {
        return new UnmodifiableObjectBooleanMap<>(ObjectBooleanHashMap.newWithKeysValues(key1, value1, key2, value2, key3, value3));
    }

    @Override
    protected <T> UnmodifiableObjectBooleanMap<T> newWithKeysValues(T key1, boolean value1, T key2, boolean value2, T key3, boolean value3, T key4, boolean value4) {
        return new UnmodifiableObjectBooleanMap<>(ObjectBooleanHashMap.newWithKeysValues(key1, value1, key2, value2, key3, value3, key4, value4));
    }

    @Override
    protected <T> UnmodifiableObjectBooleanMap<T> getEmptyMap() {
        return new UnmodifiableObjectBooleanMap<>(new ObjectBooleanHashMap<>());
    }

    @Override
    @Test
    public void get_1() {
        assertTrue(this.map.get("0"));
    }

    @Override
    @Test
    public void get_2() {
        assertTrue(this.map.get("1"));
    }

    @Override
    @Test
    public void get_3() {
        assertFalse(this.map.get("2"));
    }

    @Override
    @Test
    public void get_4() {
        assertFalse(this.map.get("5"));
    }

    @Override
    @Test
    public void getIfAbsent_1() {
        assertTrue(this.map.getIfAbsent("0", false));
    }

    @Override
    @Test
    public void getIfAbsent_2() {
        assertTrue(this.map.getIfAbsent("1", false));
    }

    @Override
    @Test
    public void getIfAbsent_3() {
        assertFalse(this.map.getIfAbsent("2", true));
    }

    @Override
    @Test
    public void getIfAbsent_4() {
        assertTrue(this.map.getIfAbsent("33", true));
    }

    @Override
    @Test
    public void getIfAbsent_5() {
        assertFalse(this.map.getIfAbsent("33", false));
    }

    @Override
    @Test
    public void contains_1() {
        assertTrue(this.map.contains(true));
    }

    @Override
    @Test
    public void contains_2() {
        assertTrue(this.map.contains(false));
    }

    @Override
    @Test
    public void contains_3() {
        assertFalse(this.getEmptyMap().contains(false));
    }

    @Override
    @Test
    public void contains_4() {
        assertFalse(this.newWithKeysValues("0", true).contains(false));
    }

    @Override
    @Test
    public void containsAllIterable_1() {
        assertTrue(this.map.containsAll(BooleanArrayList.newListWith(true, false)));
    }

    @Override
    @Test
    public void containsAllIterable_2() {
        assertTrue(this.map.containsAll(BooleanArrayList.newListWith(true, true)));
    }

    @Override
    @Test
    public void containsAllIterable_3() {
        assertTrue(this.map.containsAll(BooleanArrayList.newListWith(false, false)));
    }

    @Override
    @Test
    public void containsAllIterable_4() {
        assertFalse(this.getEmptyMap().containsAll(BooleanArrayList.newListWith(false, true)));
    }

    @Override
    @Test
    public void containsAllIterable_5() {
        assertFalse(this.newWithKeysValues("0", true).containsAll(BooleanArrayList.newListWith(false)));
    }

    @Override
    @Test
    public void containsAll_1() {
        assertTrue(this.map.containsAll(true, false));
    }

    @Override
    @Test
    public void containsAll_2() {
        assertTrue(this.map.containsAll(true, true));
    }

    @Override
    @Test
    public void containsAll_3() {
        assertTrue(this.map.containsAll(false, false));
    }

    @Override
    @Test
    public void containsAll_4() {
        assertFalse(this.getEmptyMap().containsAll(false, true));
    }

    @Override
    @Test
    public void containsAll_5() {
        assertFalse(this.newWithKeysValues("0", true).containsAll(false));
    }

    @Override
    @Test
    public void containsKey_1() {
        assertTrue(this.map.containsKey("0"));
    }

    @Override
    @Test
    public void containsKey_2() {
        assertTrue(this.map.containsKey("1"));
    }

    @Override
    @Test
    public void containsKey_3() {
        assertTrue(this.map.containsKey("2"));
    }

    @Override
    @Test
    public void containsKey_4() {
        assertFalse(this.map.containsKey("3"));
    }

    @Override
    @Test
    public void containsKey_5() {
        assertFalse(this.map.containsKey(null));
    }

    @Override
    @Test
    public void containsValue_1() {
        assertTrue(this.map.containsValue(true));
    }

    @Override
    @Test
    public void containsValue_2() {
        assertTrue(this.map.containsValue(false));
    }

    @Override
    @Test
    public void containsValue_3() {
        assertFalse(this.getEmptyMap().contains(true));
    }

    @Override
    @Test
    public void containsValue_4() {
        assertFalse(this.newWithKeysValues("0", false).contains(true));
    }

    @Override
    @Test
    public void size_1() {
        Verify.assertSize(0, this.getEmptyMap());
    }

    @Override
    @Test
    public void size_2() {
        Verify.assertSize(1, this.newWithKeysValues(0, false));
    }

    @Override
    @Test
    public void size_3() {
        Verify.assertSize(1, this.newWithKeysValues(1, true));
    }

    @Override
    @Test
    public void size_4() {
        Verify.assertSize(1, this.newWithKeysValues(null, false));
    }

    @Override
    @Test
    public void size_5() {
        Verify.assertSize(2, this.newWithKeysValues(1, false, 5, false));
    }

    @Override
    @Test
    public void size_6() {
        Verify.assertSize(2, this.newWithKeysValues(0, true, 5, true));
    }
}
