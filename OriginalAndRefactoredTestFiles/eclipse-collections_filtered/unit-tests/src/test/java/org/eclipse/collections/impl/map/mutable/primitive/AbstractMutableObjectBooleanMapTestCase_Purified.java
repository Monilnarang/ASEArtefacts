package org.eclipse.collections.impl.map.mutable.primitive;

import org.eclipse.collections.api.block.function.primitive.BooleanFunction;
import org.eclipse.collections.api.block.function.primitive.BooleanFunction0;
import org.eclipse.collections.api.iterator.MutableBooleanIterator;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.map.primitive.MutableObjectBooleanMap;
import org.eclipse.collections.api.tuple.primitive.ObjectBooleanPair;
import org.eclipse.collections.impl.factory.Iterables;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.list.mutable.primitive.BooleanArrayList;
import org.eclipse.collections.impl.test.Verify;
import org.eclipse.collections.impl.tuple.primitive.PrimitiveTuples;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class AbstractMutableObjectBooleanMapTestCase_Purified extends AbstractObjectBooleanMapTestCase {

    protected final MutableObjectBooleanMap<String> map = this.classUnderTest();

    @Override
    protected abstract MutableObjectBooleanMap<String> classUnderTest();

    @Override
    protected abstract <T> MutableObjectBooleanMap<T> newWithKeysValues(T key1, boolean value1);

    @Override
    protected abstract <T> MutableObjectBooleanMap<T> newWithKeysValues(T key1, boolean value1, T key2, boolean value2);

    @Override
    protected abstract <T> MutableObjectBooleanMap<T> newWithKeysValues(T key1, boolean value1, T key2, boolean value2, T key3, boolean value3);

    @Override
    protected abstract <T> MutableObjectBooleanMap<T> newWithKeysValues(T key1, boolean value1, T key2, boolean value2, T key3, boolean value3, T key4, boolean value4);

    @Override
    protected abstract <T> MutableObjectBooleanMap<T> getEmptyMap();

    @Override
    public void get() {
        super.get();
        MutableObjectBooleanMap<String> map1 = this.classUnderTest();
        map1.put("0", false);
        assertFalse(map1.get("0"));
        map1.put("5", true);
        assertTrue(map1.get("5"));
        map1.put(null, true);
        assertTrue(map1.get(null));
    }

    @Override
    public void getIfAbsent() {
        super.getIfAbsent();
        MutableObjectBooleanMap<String> map1 = this.classUnderTest();
        map1.removeKey("0");
        assertTrue(map1.getIfAbsent("0", true));
        assertFalse(map1.getIfAbsent("0", false));
        map1.put("0", false);
        assertFalse(map1.getIfAbsent("0", true));
        map1.put("5", true);
        assertTrue(map1.getIfAbsent("5", false));
        map1.put(null, false);
        assertFalse(map1.getIfAbsent(null, true));
    }

    @Override
    public void getOrThrow() {
        super.getOrThrow();
        MutableObjectBooleanMap<String> map1 = this.classUnderTest();
        map1.removeKey("0");
        assertThrows(IllegalStateException.class, () -> map1.getOrThrow("0"));
        map1.put("0", false);
        assertFalse(map1.getOrThrow("0"));
        map1.put("5", true);
        assertTrue(map1.getOrThrow("5"));
        map1.put(null, false);
        assertFalse(map1.getOrThrow(null));
    }

    @Override
    public void containsKey() {
        super.containsKey();
        MutableObjectBooleanMap<String> map1 = this.classUnderTest();
        map1.removeKey("0");
        assertFalse(map1.containsKey("0"));
        assertFalse(map1.get("0"));
        map1.removeKey("0");
        assertFalse(map1.containsKey("0"));
        assertFalse(map1.get("0"));
        map1.removeKey("1");
        assertFalse(map1.containsKey("1"));
        assertFalse(map1.get("1"));
        map1.removeKey("2");
        assertFalse(map1.containsKey("2"));
        assertFalse(map1.get("2"));
        map1.removeKey("3");
        assertFalse(map1.containsKey("3"));
        assertFalse(map1.get("3"));
        map1.put(null, true);
        assertTrue(map1.containsKey(null));
        map1.removeKey(null);
        assertFalse(map1.containsKey(null));
    }

    @Override
    public void containsValue() {
        super.containsValue();
        this.classUnderTest().clear();
        this.classUnderTest().put("5", true);
        assertTrue(this.classUnderTest().containsValue(true));
        this.classUnderTest().put(null, false);
        assertTrue(this.classUnderTest().containsValue(false));
    }

    @Override
    public void size() {
        super.size();
        MutableObjectBooleanMap<Integer> hashMap1 = this.newWithKeysValues(1, true, 0, false);
        Verify.assertSize(2, hashMap1);
        hashMap1.removeKey(1);
        Verify.assertSize(1, hashMap1);
        hashMap1.removeKey(0);
        Verify.assertSize(0, hashMap1);
    }

    @Override
    public void contains() {
        super.contains();
        MutableObjectBooleanMap<String> map1 = this.classUnderTest();
        map1.clear();
        map1.put("5", true);
        assertTrue(map1.contains(true));
        map1.put(null, false);
        assertTrue(map1.contains(false));
        map1.removeKey("5");
        assertFalse(map1.contains(true));
        assertTrue(map1.contains(false));
        map1.removeKey(null);
        assertFalse(map1.contains(false));
    }

    @Override
    public void containsAll() {
        super.containsAll();
        MutableObjectBooleanMap<String> map1 = this.classUnderTest();
        map1.clear();
        map1.put("5", true);
        assertTrue(map1.containsAll(true));
        assertFalse(map1.containsAll(true, false));
        assertFalse(map1.containsAll(false, false));
        map1.put(null, false);
        assertTrue(map1.containsAll(false));
        assertTrue(map1.containsAll(true, false));
        map1.removeKey("5");
        assertFalse(map1.containsAll(true));
        assertFalse(map1.containsAll(true, false));
        assertTrue(map1.containsAll(false, false));
        map1.removeKey(null);
        assertFalse(map1.containsAll(false, true));
    }

    @Override
    public void containsAllIterable() {
        super.containsAllIterable();
        MutableObjectBooleanMap<String> map1 = this.classUnderTest();
        map1.clear();
        map1.put("5", true);
        assertTrue(map1.containsAll(BooleanArrayList.newListWith(true)));
        assertFalse(map1.containsAll(BooleanArrayList.newListWith(true, false)));
        assertFalse(map1.containsAll(BooleanArrayList.newListWith(false, false)));
        map1.put(null, false);
        assertTrue(map1.containsAll(BooleanArrayList.newListWith(false)));
        assertTrue(map1.containsAll(BooleanArrayList.newListWith(true, false)));
        map1.removeKey("5");
        assertFalse(map1.containsAll(BooleanArrayList.newListWith(true)));
        assertFalse(map1.containsAll(BooleanArrayList.newListWith(true, false)));
        assertTrue(map1.containsAll(BooleanArrayList.newListWith(false, false)));
        map1.removeKey(null);
        assertFalse(map1.containsAll(BooleanArrayList.newListWith(false, true)));
    }

    protected static MutableList<String> generateCollisions() {
        MutableList<String> collisions = FastList.newList();
        ObjectBooleanHashMap<String> hashMap = new ObjectBooleanHashMap<>();
        for (int each = 3; collisions.size() <= 10; each++) {
            if (hashMap.spread(String.valueOf(each)) == hashMap.spread(String.valueOf(3))) {
                collisions.add(String.valueOf(each));
            }
        }
        return collisions;
    }

    @Test
    public void asUnmodifiable_1() {
        Verify.assertInstanceOf(UnmodifiableObjectBooleanMap.class, this.map.asUnmodifiable());
    }

    @Test
    public void asUnmodifiable_2() {
        assertEquals(new UnmodifiableObjectBooleanMap<>(this.map), this.map.asUnmodifiable());
    }

    @Test
    public void asSynchronized_1() {
        Verify.assertInstanceOf(SynchronizedObjectBooleanMap.class, this.map.asSynchronized());
    }

    @Test
    public void asSynchronized_2() {
        assertEquals(new SynchronizedObjectBooleanMap<>(this.map), this.map.asSynchronized());
    }
}
