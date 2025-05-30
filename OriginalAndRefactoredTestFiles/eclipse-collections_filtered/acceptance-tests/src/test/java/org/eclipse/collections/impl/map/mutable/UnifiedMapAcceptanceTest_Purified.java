package org.eclipse.collections.impl.map.mutable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.impl.CollidingInt;
import org.eclipse.collections.impl.test.SerializeTestHelper;
import org.eclipse.collections.impl.test.Verify;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UnifiedMapAcceptanceTest_Purified {

    private static final Logger LOGGER = LoggerFactory.getLogger(UnifiedMapAcceptanceTest.class);

    private static final Comparator<Map.Entry<CollidingInt, String>> ENTRY_COMPARATOR = (o1, o2) -> o1.getKey().compareTo(o2.getKey());

    private static final Comparator<String> VALUE_COMPARATOR = (o1, o2) -> Integer.parseInt(o1.substring(1)) - Integer.parseInt(o2.substring(1));

    private static String createVal(int i) {
        return "X" + i;
    }

    private static void assertUnifiedMapWithCollisions(int shift, int removeStride) {
        UnifiedMap<CollidingInt, String> map = UnifiedMap.newMap();
        int size = 100000;
        for (int i = 0; i < size; i++) {
            map.put(new CollidingInt(i, shift), UnifiedMapAcceptanceTest.createVal(i));
        }
        Verify.assertSize(size, map);
        for (int i = 0; i < size; i++) {
            Verify.assertContainsKey(new CollidingInt(i, shift), map);
            Verify.assertContainsKeyValue(new CollidingInt(i, shift), UnifiedMapAcceptanceTest.createVal(i), map);
        }
        for (int i = 0; i < size; i += removeStride) {
            Assert.assertEquals(UnifiedMapAcceptanceTest.createVal(i), map.remove(new CollidingInt(i, shift)));
        }
        Verify.assertSize(size - size / removeStride, map);
        for (int i = 0; i < size; i++) {
            if (i % removeStride == 0) {
                Verify.assertNotContainsKey(new CollidingInt(i, shift), map);
                Assert.assertNull(map.get(new CollidingInt(i, shift)));
            } else {
                Verify.assertContainsKey(new CollidingInt(i, shift), map);
                Verify.assertContainsKeyValue(new CollidingInt(i, shift), UnifiedMapAcceptanceTest.createVal(i), map);
            }
        }
        for (int i = 0; i < size; i++) {
            map.remove(new CollidingInt(i, shift));
        }
        Verify.assertSize(0, map);
    }

    private static void assertUnifiedMapWithCollisionsAndNullKey(int shift, int removeStride) {
        UnifiedMap<CollidingInt, String> map = UnifiedMap.newMap();
        Assert.assertTrue(map.isEmpty());
        int size = 100000;
        for (int i = 0; i < size; i++) {
            map.put(new CollidingInt(i, shift), UnifiedMapAcceptanceTest.createVal(i));
        }
        map.put(null, "Y");
        Verify.assertSize(size + 1, map);
        for (int i = 0; i < size; i++) {
            Verify.assertContainsKey(new CollidingInt(i, shift), map);
            Verify.assertContainsKeyValue(new CollidingInt(i, shift), UnifiedMapAcceptanceTest.createVal(i), map);
        }
        Assert.assertTrue(map.containsKey(null));
        Assert.assertEquals("Y", map.get(null));
        for (int i = 0; i < size; i += removeStride) {
            Assert.assertEquals(UnifiedMapAcceptanceTest.createVal(i), map.remove(new CollidingInt(i, shift)));
        }
        Verify.assertSize(size - size / removeStride + 1, map);
        for (int i = 0; i < size; i++) {
            if (i % removeStride != 0) {
                Verify.assertContainsKey(new CollidingInt(i, shift), map);
                Verify.assertContainsKeyValue(new CollidingInt(i, shift), UnifiedMapAcceptanceTest.createVal(i), map);
            }
        }
        Assert.assertTrue(map.containsKey(null));
        Assert.assertEquals("Y", map.get(null));
        map.remove(null);
        Assert.assertFalse(map.containsKey(null));
        Assert.assertNull(map.get(null));
    }

    private static void assertUnifiedMapClear(int shift) {
        UnifiedMap<CollidingInt, String> map = UnifiedMap.newMap();
        int size = 100000;
        for (int i = 0; i < size; i++) {
            map.put(new CollidingInt(i, shift), UnifiedMapAcceptanceTest.createVal(i));
        }
        map.clear();
        Verify.assertSize(0, map);
        for (int i = 0; i < size; i++) {
            Verify.assertNotContainsKey(new CollidingInt(i, shift), map);
            Assert.assertNull(map.get(new CollidingInt(i, shift)));
        }
    }

    private static void assertUnifiedMapForEachEntry(int shift) {
        UnifiedMap<CollidingInt, String> map = UnifiedMap.newMap();
        int size = 100000;
        for (int i = 0; i < size; i++) {
            map.put(new CollidingInt(i, shift), UnifiedMapAcceptanceTest.createVal(i));
        }
        int[] count = new int[1];
        map.forEachKeyValue((key, value) -> {
            Assert.assertEquals(UnifiedMapAcceptanceTest.createVal(key.getValue()), value);
            count[0]++;
        });
        Assert.assertEquals(size, count[0]);
    }

    private static void assertUnifiedMapForEachKey(int shift) {
        UnifiedMap<CollidingInt, String> map = UnifiedMap.newMap();
        int size = 100000;
        for (int i = 0; i < size; i++) {
            map.put(new CollidingInt(i, shift), UnifiedMapAcceptanceTest.createVal(i));
        }
        List<CollidingInt> keys = new ArrayList<>(size);
        map.forEachKey(keys::add);
        Verify.assertSize(size, keys);
        Collections.sort(keys);
        for (int i = 0; i < size; i++) {
            Assert.assertEquals(new CollidingInt(i, shift), keys.get(i));
        }
    }

    private static void assertUnifiedMapForEachValue(int shift) {
        UnifiedMap<CollidingInt, String> map = UnifiedMap.newMap();
        int size = 100000;
        for (int i = 0; i < size; i++) {
            map.put(new CollidingInt(i, shift), UnifiedMapAcceptanceTest.createVal(i));
        }
        List<String> values = new ArrayList<>(size);
        map.forEachValue(values::add);
        Verify.assertSize(size, values);
        Collections.sort(values, UnifiedMapAcceptanceTest.VALUE_COMPARATOR);
        for (int i = 0; i < size; i++) {
            Assert.assertEquals(UnifiedMapAcceptanceTest.createVal(i), values.get(i));
        }
    }

    private static void assertUnifiedMapEqualsAndHashCode(int shift) {
        MutableMap<CollidingInt, String> map1 = UnifiedMap.newMap();
        Map<CollidingInt, String> map2 = new HashMap<>();
        MutableMap<CollidingInt, String> map3 = UnifiedMap.newMap();
        MutableMap<CollidingInt, String> map4 = UnifiedMap.newMap();
        int size = 100000;
        for (int i = 0; i < size; i++) {
            map1.put(new CollidingInt(i, shift), UnifiedMapAcceptanceTest.createVal(i));
            map2.put(new CollidingInt(i, shift), UnifiedMapAcceptanceTest.createVal(i));
            map3.put(new CollidingInt(i, shift), UnifiedMapAcceptanceTest.createVal(i));
            map4.put(new CollidingInt(size - i - 1, shift), UnifiedMapAcceptanceTest.createVal(size - i - 1));
        }
        Assert.assertEquals(map2, map1);
        Assert.assertEquals(map1, map2);
        Assert.assertEquals(map2.hashCode(), map1.hashCode());
        Assert.assertEquals(map1, map3);
        Assert.assertEquals(map1.hashCode(), map3.hashCode());
        Assert.assertEquals(map2, map4);
        Assert.assertEquals(map4, map2);
        Assert.assertEquals(map2.hashCode(), map4.hashCode());
        Verify.assertSetsEqual(map2.entrySet(), map1.entrySet());
        Verify.assertSetsEqual(map1.entrySet(), map2.entrySet());
        Assert.assertEquals(map2.entrySet().hashCode(), map1.entrySet().hashCode());
        Verify.assertSetsEqual(map1.entrySet(), map3.entrySet());
        Assert.assertEquals(map1.entrySet().hashCode(), map3.entrySet().hashCode());
        Verify.assertSetsEqual(map2.entrySet(), map4.entrySet());
        Verify.assertSetsEqual(map4.entrySet(), map2.entrySet());
        Assert.assertEquals(map2.entrySet().hashCode(), map4.entrySet().hashCode());
        Verify.assertSetsEqual(map2.keySet(), map1.keySet());
        Verify.assertSetsEqual(map1.keySet(), map2.keySet());
        Assert.assertEquals(map2.keySet().hashCode(), map1.keySet().hashCode());
        Verify.assertSetsEqual(map1.keySet(), map3.keySet());
        Assert.assertEquals(map1.keySet().hashCode(), map3.keySet().hashCode());
        Verify.assertSetsEqual(map2.keySet(), map4.keySet());
        Verify.assertSetsEqual(map4.keySet(), map2.keySet());
        Assert.assertEquals(map2.keySet().hashCode(), map4.keySet().hashCode());
    }

    private static void assertUnifiedMapPutAll(int shift) {
        UnifiedMap<CollidingInt, String> map = UnifiedMap.newMap();
        int size = 100000;
        for (int i = 0; i < size; i++) {
            map.put(new CollidingInt(i, shift), UnifiedMapAcceptanceTest.createVal(i));
        }
        UnifiedMap<CollidingInt, String> newMap = UnifiedMap.newMap(size);
        newMap.putAll(map);
        Verify.assertSize(size, newMap);
        for (int i = 0; i < size; i++) {
            Verify.assertContainsKey(new CollidingInt(i, shift), newMap);
            Verify.assertContainsKeyValue(new CollidingInt(i, shift), UnifiedMapAcceptanceTest.createVal(i), newMap);
        }
    }

    private static void assertUnifiedMapPutAllWithHashMap(int shift) {
        Map<CollidingInt, String> map = new HashMap<>();
        int size = 100000;
        for (int i = 0; i < size; i++) {
            map.put(new CollidingInt(i, shift), UnifiedMapAcceptanceTest.createVal(i));
        }
        UnifiedMap<CollidingInt, String> newMap = UnifiedMap.newMap(size);
        newMap.putAll(map);
        Verify.assertSize(size, newMap);
        for (int i = 0; i < size; i++) {
            Verify.assertContainsKey(new CollidingInt(i, shift), newMap);
            Verify.assertContainsKeyValue(new CollidingInt(i, shift), UnifiedMapAcceptanceTest.createVal(i), newMap);
        }
    }

    private static void assertUnifiedMapReplace(int shift) {
        UnifiedMap<CollidingInt, String> map = UnifiedMap.newMap();
        int size = 100000;
        for (int i = 0; i < size; i++) {
            map.put(new CollidingInt(i, shift), UnifiedMapAcceptanceTest.createVal(i));
        }
        for (int i = 0; i < size; i++) {
            map.put(new CollidingInt(i, shift), "Y" + i);
        }
        Verify.assertSize(size, map);
        for (int i = 0; i < size; i++) {
            Assert.assertEquals("Y" + i, map.get(new CollidingInt(i, shift)));
        }
    }

    private static void runUnifiedMapContainsValue(int shift) {
        UnifiedMap<CollidingInt, String> map = UnifiedMap.newMap();
        int size = 1000;
        for (int i = 0; i < size; i++) {
            map.put(new CollidingInt(i, shift), UnifiedMapAcceptanceTest.createVal(i));
        }
        for (int i = 0; i < size; i++) {
            Assert.assertTrue(map.containsValue(UnifiedMapAcceptanceTest.createVal(i)));
        }
    }

    private static void runUnifiedMapKeySet(int shift) {
        UnifiedMap<CollidingInt, String> map = UnifiedMap.newMap();
        int size = 100000;
        for (int i = 0; i < size; i++) {
            map.put(new CollidingInt(i, shift), UnifiedMapAcceptanceTest.createVal(i));
        }
        Verify.assertSize(size, map);
        Set<CollidingInt> keySet = map.keySet();
        Verify.assertSize(size, keySet);
        for (int i = 0; i < size; i++) {
            Verify.assertContains(new CollidingInt(i, shift), keySet);
        }
        for (int i = 0; i < size; i += 2) {
            Assert.assertTrue(keySet.remove(new CollidingInt(i, shift)));
        }
        Verify.assertSize(size / 2, map);
        Verify.assertSize(size / 2, keySet);
        for (int i = 1; i < size; i += 2) {
            Verify.assertContainsKey(new CollidingInt(i, shift), map);
            Verify.assertContains(new CollidingInt(i, shift), keySet);
            Verify.assertContainsKeyValue(new CollidingInt(i, shift), UnifiedMapAcceptanceTest.createVal(i), map);
        }
    }

    private static void runUnifiedMapKeySetRetainAll(int shift) {
        UnifiedMap<CollidingInt, String> map = UnifiedMap.newMap();
        List<CollidingInt> toRetain = new ArrayList<>();
        int size = 100000;
        for (int i = 0; i < size; i++) {
            map.put(new CollidingInt(i, shift), UnifiedMapAcceptanceTest.createVal(i));
            if (i % 2 == 0) {
                toRetain.add(new CollidingInt(i, shift));
            }
        }
        Verify.assertSize(size, map);
        Set<CollidingInt> keySet = map.keySet();
        Assert.assertTrue(keySet.containsAll(toRetain));
        Assert.assertTrue(keySet.retainAll(toRetain));
        Assert.assertTrue(keySet.containsAll(toRetain));
        Assert.assertFalse(keySet.retainAll(toRetain));
        Verify.assertSize(size / 2, map);
        Verify.assertSize(size / 2, keySet);
        for (int i = 0; i < size; i += 2) {
            Verify.assertContainsKey(new CollidingInt(i, shift), map);
            Verify.assertContains(new CollidingInt(i, shift), keySet);
            Verify.assertContainsKeyValue(new CollidingInt(i, shift), UnifiedMapAcceptanceTest.createVal(i), map);
        }
    }

    private static void runUnifiedMapKeySetRemoveAll(int shift) {
        UnifiedMap<CollidingInt, String> map = UnifiedMap.newMap();
        List<CollidingInt> toRemove = new ArrayList<>();
        int size = 100000;
        for (int i = 0; i < size; i++) {
            map.put(new CollidingInt(i, shift), UnifiedMapAcceptanceTest.createVal(i));
            if (i % 2 == 0) {
                toRemove.add(new CollidingInt(i, shift));
            }
        }
        Verify.assertSize(size, map);
        Set<CollidingInt> keySet = map.keySet();
        Assert.assertTrue(keySet.removeAll(toRemove));
        Assert.assertFalse(keySet.removeAll(toRemove));
        Verify.assertSize(size / 2, map);
        Verify.assertSize(size / 2, keySet);
        for (int i = 1; i < size; i += 2) {
            Verify.assertContainsKey(new CollidingInt(i, shift), map);
            Verify.assertContains(new CollidingInt(i, shift), keySet);
            Verify.assertContainsKeyValue(new CollidingInt(i, shift), UnifiedMapAcceptanceTest.createVal(i), map);
        }
    }

    private static void runUnifiedMapKeySetToArray(int shift) {
        UnifiedMap<CollidingInt, String> map = UnifiedMap.newMap();
        int size = 100000;
        for (int i = 0; i < size; i++) {
            map.put(new CollidingInt(i, shift), UnifiedMapAcceptanceTest.createVal(i));
        }
        Verify.assertSize(size, map);
        Set<CollidingInt> keySet = map.keySet();
        Object[] keys = keySet.toArray();
        Arrays.sort(keys);
        for (int i = 0; i < size; i++) {
            Assert.assertEquals(new CollidingInt(i, shift), keys[i]);
        }
    }

    private static void runUnifiedMapKeySetIterator(int shift) {
        UnifiedMap<CollidingInt, String> map = UnifiedMap.newMap();
        int size = 100000;
        for (int i = 0; i < size; i++) {
            map.put(new CollidingInt(i, shift), UnifiedMapAcceptanceTest.createVal(i));
        }
        Verify.assertSize(size, map);
        Set<CollidingInt> keySet = map.keySet();
        CollidingInt[] keys = new CollidingInt[size];
        int count = 0;
        for (CollidingInt collidingInt : keySet) {
            keys[count++] = collidingInt;
        }
        Arrays.sort(keys);
        for (int i = 0; i < size; i++) {
            Assert.assertEquals(new CollidingInt(i, shift), keys[i]);
        }
    }

    private static void runUnifiedMapKeySetIteratorRemove(int shift, int removeStride) {
        UnifiedMap<CollidingInt, String> map = UnifiedMap.newMap();
        int size = 100000;
        for (int i = 0; i < size; i++) {
            map.put(new CollidingInt(i, shift), UnifiedMapAcceptanceTest.createVal(i));
        }
        Verify.assertSize(size, map);
        Set<CollidingInt> keySet = map.keySet();
        int count = 0;
        for (Iterator<CollidingInt> it = keySet.iterator(); it.hasNext(); ) {
            CollidingInt key = it.next();
            count++;
            if (key.getValue() % removeStride == 0) {
                it.remove();
            }
        }
        Assert.assertEquals(size, count);
        for (int i = 0; i < size; i++) {
            if (i % removeStride != 0) {
                Assert.assertTrue("map contains " + i + "for shift " + shift + " and remove stride " + removeStride, map.containsKey(new CollidingInt(i, shift)));
                Verify.assertContainsKeyValue(new CollidingInt(i, shift), UnifiedMapAcceptanceTest.createVal(i), map);
            }
        }
    }

    private static void runUnifiedMapKeySetIteratorRemoveFlip(int shift, int removeStride) {
        UnifiedMap<CollidingInt, String> map = UnifiedMap.newMap();
        int size = 100000;
        for (int i = 0; i < size; i++) {
            map.put(new CollidingInt(i, shift), UnifiedMapAcceptanceTest.createVal(i));
        }
        Verify.assertSize(size, map);
        Set<CollidingInt> keySet = map.keySet();
        int count = 0;
        for (Iterator<CollidingInt> it = keySet.iterator(); it.hasNext(); ) {
            CollidingInt key = it.next();
            count++;
            if (key.getValue() % removeStride != 0) {
                it.remove();
            }
        }
        Assert.assertEquals(size, count);
        for (int i = 0; i < size; i++) {
            if (i % removeStride == 0) {
                Assert.assertTrue("map contains " + i + "for shift " + shift + " and remove stride " + removeStride, map.containsKey(new CollidingInt(i, shift)));
                Verify.assertContainsKeyValue(new CollidingInt(i, shift), UnifiedMapAcceptanceTest.createVal(i), map);
            }
        }
    }

    private static void runUnifiedMapEntrySet(int shift) {
        UnifiedMap<CollidingInt, String> map = UnifiedMap.newMap();
        int size = 100000;
        for (int i = 0; i < size; i++) {
            map.put(new CollidingInt(i, shift), UnifiedMapAcceptanceTest.createVal(i));
        }
        Verify.assertSize(size, map);
        Set<Map.Entry<CollidingInt, String>> entrySet = map.entrySet();
        Verify.assertSize(size, entrySet);
        for (int i = 0; i < size; i++) {
            Verify.assertContains(new Entry(new CollidingInt(i, shift), UnifiedMapAcceptanceTest.createVal(i)), entrySet);
        }
        for (int i = 0; i < size; i += 2) {
            Assert.assertTrue(entrySet.remove(new Entry(new CollidingInt(i, shift), UnifiedMapAcceptanceTest.createVal(i))));
        }
        Verify.assertSize(size / 2, map);
        Verify.assertSize(size / 2, entrySet);
        for (int i = 1; i < size; i += 2) {
            Verify.assertContainsKey(new CollidingInt(i, shift), map);
            Verify.assertContains(new Entry(new CollidingInt(i, shift), UnifiedMapAcceptanceTest.createVal(i)), entrySet);
            Verify.assertContainsKeyValue(new CollidingInt(i, shift), UnifiedMapAcceptanceTest.createVal(i), map);
        }
    }

    private static void runUnifiedMapEntrySetRetainAll(int shift) {
        UnifiedMap<CollidingInt, String> map = UnifiedMap.newMap();
        List<Entry> toRetain = new ArrayList<>();
        int size = 100000;
        for (int i = 0; i < size; i++) {
            map.put(new CollidingInt(i, shift), UnifiedMapAcceptanceTest.createVal(i));
            if (i % 2 == 0) {
                toRetain.add(new Entry(new CollidingInt(i, shift), UnifiedMapAcceptanceTest.createVal(i)));
            }
        }
        Verify.assertSize(size, map);
        Set<Map.Entry<CollidingInt, String>> entrySet = map.entrySet();
        Assert.assertTrue(entrySet.containsAll(toRetain));
        Assert.assertTrue(entrySet.retainAll(toRetain));
        Assert.assertTrue(entrySet.containsAll(toRetain));
        Assert.assertFalse(entrySet.retainAll(toRetain));
        Verify.assertSize(size / 2, map);
        Verify.assertSize(size / 2, entrySet);
        for (int i = 0; i < size; i += 2) {
            Verify.assertContainsKey(new CollidingInt(i, shift), map);
            Verify.assertContains(new Entry(new CollidingInt(i, shift), UnifiedMapAcceptanceTest.createVal(i)), entrySet);
            Verify.assertContainsKeyValue(new CollidingInt(i, shift), UnifiedMapAcceptanceTest.createVal(i), map);
        }
    }

    private static void runUnifiedMapEntrySetRemoveAll(int shift) {
        UnifiedMap<CollidingInt, String> map = UnifiedMap.newMap();
        List<Entry> toRemove = new ArrayList<>();
        int size = 100000;
        for (int i = 0; i < size; i++) {
            map.put(new CollidingInt(i, shift), UnifiedMapAcceptanceTest.createVal(i));
            if (i % 2 == 0) {
                toRemove.add(new Entry(new CollidingInt(i, shift), UnifiedMapAcceptanceTest.createVal(i)));
            }
        }
        Verify.assertSize(size, map);
        Set<Map.Entry<CollidingInt, String>> entrySet = map.entrySet();
        Assert.assertTrue(entrySet.removeAll(toRemove));
        Assert.assertFalse(entrySet.removeAll(toRemove));
        Verify.assertSize(size / 2, map);
        Verify.assertSize(size / 2, entrySet);
        for (int i = 1; i < size; i += 2) {
            Verify.assertContainsKey(new CollidingInt(i, shift), map);
            Verify.assertContains(new Entry(new CollidingInt(i, shift), UnifiedMapAcceptanceTest.createVal(i)), entrySet);
            Verify.assertContainsKeyValue(new CollidingInt(i, shift), UnifiedMapAcceptanceTest.createVal(i), map);
        }
    }

    private static void runUnifiedMapEntrySetToArray(int shift) {
        UnifiedMap<CollidingInt, String> map = UnifiedMap.newMap();
        int size = 100000;
        for (int i = 0; i < size; i++) {
            map.put(new CollidingInt(i, shift), UnifiedMapAcceptanceTest.createVal(i));
        }
        Verify.assertSize(size, map);
        Set<Map.Entry<CollidingInt, String>> entrySet = map.entrySet();
        Map.Entry<CollidingInt, String>[] entries = entrySet.toArray(new Map.Entry[0]);
        Arrays.sort(entries, UnifiedMapAcceptanceTest.ENTRY_COMPARATOR);
        for (int i = 0; i < size; i++) {
            Assert.assertEquals(new Entry(new CollidingInt(i, shift), UnifiedMapAcceptanceTest.createVal(i)), entries[i]);
        }
    }

    private static void runUnifiedMapEntrySetIterator(int shift) {
        UnifiedMap<CollidingInt, String> map = UnifiedMap.newMap();
        int size = 100000;
        for (int i = 0; i < size; i++) {
            map.put(new CollidingInt(i, shift), UnifiedMapAcceptanceTest.createVal(i));
        }
        Verify.assertSize(size, map);
        Set<Map.Entry<CollidingInt, String>> entrySet = map.entrySet();
        Map.Entry<CollidingInt, String>[] entries = new Map.Entry[size];
        int count = 0;
        for (Map.Entry<CollidingInt, String> collidingIntStringEntry : entrySet) {
            entries[count++] = collidingIntStringEntry;
        }
        Arrays.sort(entries, UnifiedMapAcceptanceTest.ENTRY_COMPARATOR);
        for (int i = 0; i < size; i++) {
            Assert.assertEquals(new Entry(new CollidingInt(i, shift), UnifiedMapAcceptanceTest.createVal(i)), entries[i]);
        }
    }

    private static void runUnifiedMapEntrySetIteratorSetValue(int shift) {
        UnifiedMap<CollidingInt, String> map = UnifiedMap.newMap();
        int size = 100000;
        for (int i = 0; i < size; i++) {
            map.put(new CollidingInt(i, shift), UnifiedMapAcceptanceTest.createVal(i));
        }
        Verify.assertSize(size, map);
        Set<Map.Entry<CollidingInt, String>> entrySet = map.entrySet();
        for (Map.Entry<CollidingInt, String> entry : entrySet) {
            CollidingInt key = entry.getKey();
            entry.setValue("Y" + key.getValue());
        }
        Map.Entry<CollidingInt, String>[] entries = new Map.Entry[size];
        int count = 0;
        for (Map.Entry<CollidingInt, String> collidingIntStringEntry : entrySet) {
            entries[count++] = collidingIntStringEntry;
        }
        Arrays.sort(entries, UnifiedMapAcceptanceTest.ENTRY_COMPARATOR);
        for (int i = 0; i < size; i++) {
            Assert.assertEquals(new Entry(new CollidingInt(i, shift), "Y" + i), entries[i]);
        }
    }

    private static void runUnifiedMapEntrySetIteratorRemove(int shift, int removeStride) {
        UnifiedMap<CollidingInt, String> map = UnifiedMap.newMap();
        int size = 100000;
        for (int i = 0; i < size; i++) {
            map.put(new CollidingInt(i, shift), UnifiedMapAcceptanceTest.createVal(i));
        }
        Verify.assertSize(size, map);
        Set<Map.Entry<CollidingInt, String>> entrySet = map.entrySet();
        int count = 0;
        for (Iterator<Map.Entry<CollidingInt, String>> it = entrySet.iterator(); it.hasNext(); ) {
            CollidingInt entry = it.next().getKey();
            count++;
            if (entry.getValue() % removeStride == 0) {
                it.remove();
            }
        }
        Assert.assertEquals(size, count);
        for (int i = 0; i < size; i++) {
            if (i % removeStride != 0) {
                Assert.assertTrue("map contains " + i + "for shift " + shift + " and remove stride " + removeStride, map.containsKey(new CollidingInt(i, shift)));
                Verify.assertContainsKeyValue(new CollidingInt(i, shift), UnifiedMapAcceptanceTest.createVal(i), map);
            }
        }
    }

    private static void runUnifiedMapEntrySetIteratorRemoveFlip(int shift, int removeStride) {
        UnifiedMap<CollidingInt, String> map = UnifiedMap.newMap();
        int size = 100000;
        for (int i = 0; i < size; i++) {
            map.put(new CollidingInt(i, shift), UnifiedMapAcceptanceTest.createVal(i));
        }
        Verify.assertSize(size, map);
        Set<Map.Entry<CollidingInt, String>> entrySet = map.entrySet();
        int count = 0;
        for (Iterator<Map.Entry<CollidingInt, String>> it = entrySet.iterator(); it.hasNext(); ) {
            CollidingInt entry = it.next().getKey();
            count++;
            if (entry.getValue() % removeStride != 0) {
                it.remove();
            }
        }
        Assert.assertEquals(size, count);
        for (int i = 0; i < size; i++) {
            if (i % removeStride == 0) {
                Assert.assertTrue("map contains " + i + "for shift " + shift + " and remove stride " + removeStride, map.containsKey(new CollidingInt(i, shift)));
                Verify.assertContainsKeyValue(new CollidingInt(i, shift), UnifiedMapAcceptanceTest.createVal(i), map);
            }
        }
    }

    private static void runUnifiedMapValues(int shift) {
        UnifiedMap<CollidingInt, String> map = UnifiedMap.newMap();
        int size = 1000;
        for (int i = 0; i < size; i++) {
            map.put(new CollidingInt(i, shift), UnifiedMapAcceptanceTest.createVal(i));
        }
        Verify.assertSize(size, map);
        Collection<String> values = map.values();
        Assert.assertEquals(size, values.size());
        for (int i = 0; i < size; i++) {
            Verify.assertContains(UnifiedMapAcceptanceTest.createVal(i), values);
        }
        for (int i = 0; i < size; i += 2) {
            Assert.assertTrue(values.remove(UnifiedMapAcceptanceTest.createVal(i)));
        }
        Verify.assertSize(size / 2, map);
        Verify.assertSize(size / 2, values);
        for (int i = 1; i < size; i += 2) {
            Verify.assertContainsKey(new CollidingInt(i, shift), map);
            Verify.assertContains(UnifiedMapAcceptanceTest.createVal(i), values);
            Verify.assertContainsKeyValue(new CollidingInt(i, shift), UnifiedMapAcceptanceTest.createVal(i), map);
        }
    }

    private static void runUnifiedMapValuesRetainAll(int shift) {
        UnifiedMap<CollidingInt, String> map = UnifiedMap.newMap();
        List<String> toRetain = new ArrayList<>();
        int size = 1000;
        for (int i = 0; i < size; i++) {
            map.put(new CollidingInt(i, shift), UnifiedMapAcceptanceTest.createVal(i));
            if (i % 2 == 0) {
                toRetain.add(UnifiedMapAcceptanceTest.createVal(i));
            }
        }
        Verify.assertSize(size, map);
        Collection<String> values = map.values();
        Assert.assertTrue(values.containsAll(toRetain));
        Assert.assertTrue(values.retainAll(toRetain));
        Assert.assertTrue(values.containsAll(toRetain));
        Assert.assertFalse(values.retainAll(toRetain));
        Verify.assertSize(size / 2, map);
        Verify.assertSize(size / 2, values);
        for (int i = 0; i < size; i += 2) {
            Verify.assertContainsKey(new CollidingInt(i, shift), map);
            Verify.assertContains(UnifiedMapAcceptanceTest.createVal(i), values);
            Verify.assertContainsKeyValue(new CollidingInt(i, shift), UnifiedMapAcceptanceTest.createVal(i), map);
        }
    }

    private static void runUnifiedMapValuesRemoveAll(int shift) {
        UnifiedMap<CollidingInt, String> map = UnifiedMap.newMap();
        List<String> toRemove = new ArrayList<>();
        int size = 1000;
        for (int i = 0; i < size; i++) {
            map.put(new CollidingInt(i, shift), UnifiedMapAcceptanceTest.createVal(i));
            if (i % 2 == 0) {
                toRemove.add(UnifiedMapAcceptanceTest.createVal(i));
            }
        }
        Verify.assertSize(size, map);
        Collection<String> values = map.values();
        Assert.assertTrue(values.removeAll(toRemove));
        Assert.assertFalse(values.removeAll(toRemove));
        Verify.assertSize(size / 2, map);
        Verify.assertSize(size / 2, values);
        for (int i = 1; i < size; i += 2) {
            Verify.assertContainsKey(new CollidingInt(i, shift), map);
            Verify.assertContains(UnifiedMapAcceptanceTest.createVal(i), values);
            Verify.assertContainsKeyValue(new CollidingInt(i, shift), UnifiedMapAcceptanceTest.createVal(i), map);
        }
    }

    private static void runUnifiedMapValuesToArray(int shift) {
        UnifiedMap<CollidingInt, String> map = UnifiedMap.newMap();
        int size = 1000;
        for (int i = 0; i < size; i++) {
            map.put(new CollidingInt(i, shift), UnifiedMapAcceptanceTest.createVal(i));
        }
        Verify.assertSize(size, map);
        Collection<String> values = map.values();
        String[] entries = values.toArray(new String[0]);
        Arrays.sort(entries, UnifiedMapAcceptanceTest.VALUE_COMPARATOR);
        for (int i = 0; i < size; i++) {
            Assert.assertEquals(UnifiedMapAcceptanceTest.createVal(i), entries[i]);
        }
    }

    private static void runUnifiedMapValuesIterator(int shift) {
        UnifiedMap<CollidingInt, String> map = UnifiedMap.newMap();
        int size = 1000;
        for (int i = 0; i < size; i++) {
            map.put(new CollidingInt(i, shift), UnifiedMapAcceptanceTest.createVal(i));
        }
        Verify.assertSize(size, map);
        Collection<String> values = map.values();
        String[] valuesArray = new String[size];
        int count = 0;
        for (String value : values) {
            valuesArray[count++] = value;
        }
        Arrays.sort(valuesArray, UnifiedMapAcceptanceTest.VALUE_COMPARATOR);
        for (int i = 0; i < size; i++) {
            Assert.assertEquals(UnifiedMapAcceptanceTest.createVal(i), valuesArray[i]);
        }
    }

    private static void runUnifiedMapValuesIteratorRemove(int shift, int removeStride) {
        UnifiedMap<CollidingInt, String> map = UnifiedMap.newMap();
        int size = 100000;
        for (int i = 0; i < size; i++) {
            map.put(new CollidingInt(i, shift), UnifiedMapAcceptanceTest.createVal(i));
        }
        Verify.assertSize(size, map);
        Collection<String> values = map.values();
        int count = 0;
        for (Iterator<String> it = values.iterator(); it.hasNext(); ) {
            String value = it.next();
            int x = Integer.parseInt(value.substring(1));
            count++;
            if (x % removeStride == 0) {
                it.remove();
            }
        }
        Assert.assertEquals(size, count);
        for (int i = 0; i < size; i++) {
            if (i % removeStride != 0) {
                Assert.assertTrue("map contains " + i + "for shift " + shift + " and remove stride " + removeStride, map.containsKey(new CollidingInt(i, shift)));
                Verify.assertContainsKeyValue(new CollidingInt(i, shift), UnifiedMapAcceptanceTest.createVal(i), map);
            }
        }
    }

    private static void runUnifiedMapValuesIteratorRemoveFlip(int shift, int removeStride) {
        UnifiedMap<CollidingInt, String> map = UnifiedMap.newMap();
        int size = 100000;
        for (int i = 0; i < size; i++) {
            map.put(new CollidingInt(i, shift), UnifiedMapAcceptanceTest.createVal(i));
        }
        Verify.assertSize(size, map);
        Collection<String> values = map.values();
        int count = 0;
        for (Iterator<String> it = values.iterator(); it.hasNext(); ) {
            String value = it.next();
            int x = Integer.parseInt(value.substring(1));
            count++;
            if (x % removeStride != 0) {
                it.remove();
            }
        }
        Assert.assertEquals(size, count);
        for (int i = 0; i < size; i++) {
            if (i % removeStride == 0) {
                Assert.assertTrue("map contains " + i + "for shift " + shift + " and remove stride " + removeStride, map.containsKey(new CollidingInt(i, shift)));
                Verify.assertContainsKeyValue(new CollidingInt(i, shift), UnifiedMapAcceptanceTest.createVal(i), map);
            }
        }
    }

    private static void runUnifiedMapSerialize(int shift) {
        UnifiedMap<CollidingInt, String> map = UnifiedMap.newMap();
        int size = 100000;
        for (int i = 0; i < size; i++) {
            map.put(new CollidingInt(i, shift), UnifiedMapAcceptanceTest.createVal(i));
        }
        String nullVal = "Y99999999";
        map.put(null, nullVal);
        map = SerializeTestHelper.serializeDeserialize(map);
        Verify.assertSize(size + 1, map);
        for (int i = 0; i < size; i++) {
            Verify.assertContainsKey(new CollidingInt(i, shift), map);
            Verify.assertContainsKeyValue(new CollidingInt(i, shift), UnifiedMapAcceptanceTest.createVal(i), map);
        }
        Assert.assertTrue(map.containsKey(null));
        Assert.assertEquals(nullVal, map.get(null));
        Set<CollidingInt> keySet = SerializeTestHelper.serializeDeserialize(map.keySet());
        Verify.assertSize(size + 1, keySet);
        for (int i = 0; i < size; i++) {
            Verify.assertContains(new CollidingInt(i, shift), keySet);
        }
        Verify.assertContains(null, keySet);
        Set<Map.Entry<CollidingInt, String>> entrySet = SerializeTestHelper.serializeDeserialize(map.entrySet());
        Verify.assertSize(size + 1, entrySet);
        for (int i = 0; i < size; i++) {
            Verify.assertContains(new Entry(new CollidingInt(i, shift), UnifiedMapAcceptanceTest.createVal(i)), entrySet);
        }
        Verify.assertContains(new Entry(null, nullVal), entrySet);
        for (Map.Entry<CollidingInt, String> e : entrySet) {
            CollidingInt key = e.getKey();
            if (key == null) {
                Assert.assertEquals(nullVal, e.getValue());
            } else {
                Assert.assertEquals(UnifiedMapAcceptanceTest.createVal(key.getValue()), e.getValue());
            }
        }
        List<String> values = new ArrayList<>(SerializeTestHelper.serializeDeserialize(map.values()));
        Collections.sort(values, UnifiedMapAcceptanceTest.VALUE_COMPARATOR);
        Verify.assertSize(size + 1, values);
        for (int i = 0; i < size; i++) {
            Assert.assertEquals(UnifiedMapAcceptanceTest.createVal(i), values.get(i));
        }
        Assert.assertEquals(nullVal, values.get(values.size() - 1));
    }

    public void perfTestUnifiedMapGet() {
        for (int i = 1000000; i > 10; i /= 10) {
            this.runGetTest(new UnifiedMap<>(), "Unified Map", i);
        }
    }

    public void perfTestJdkHashMapGet() {
        for (int i = 1000000; i > 10; i /= 10) {
            this.runGetTest(new HashMap<>(), "JDK HashMap", i);
        }
    }

    public void perfTestUnifiedMapCollidingGet() {
        this.runCollidingGetTest(new UnifiedMap<>(), "Unified Map", 1);
        this.runCollidingGetTest(new UnifiedMap<>(), "Unified Map", 2);
        this.runCollidingGetTest(new UnifiedMap<>(), "Unified Map", 3);
    }

    public void perfTestJdkHashMapCollidingGet() {
        this.runCollidingGetTest(new HashMap<>(), "JDK HashMap", 1);
        this.runCollidingGetTest(new HashMap<>(), "JDK HashMap", 2);
        this.runCollidingGetTest(new HashMap<>(), "JDK HashMap", 3);
    }

    private void runGetTest(Map<CollidingInt, String> map, String mapName, int size) {
        Integer[] keys = UnifiedMapAcceptanceTest.createMap((Map<Object, String>) (Map<?, ?>) map, size);
        UnifiedMapAcceptanceTest.sleep(100L);
        int n = 10000000 / size;
        int max = 4;
        for (int i = 0; i < max; i++) {
            long startTime = System.nanoTime();
            UnifiedMapAcceptanceTest.runMapGet(map, keys, n);
            long runTimes = System.nanoTime() - startTime;
            LOGGER.info("{} get: {} ns per get on map size {}", mapName, (double) runTimes / (double) n / size, size);
        }
        map = null;
        System.gc();
        Thread.yield();
        System.gc();
        Thread.yield();
    }

    private static Integer[] createMap(Map<Object, String> map, int size) {
        Integer[] keys = new Integer[size];
        for (int i = 0; i < size; i++) {
            keys[i] = i;
            map.put(i, UnifiedMapAcceptanceTest.createVal(i));
        }
        Verify.assertSize(size, map);
        return keys;
    }

    private static void runMapGet(Map<CollidingInt, String> map, Object[] keys, int n) {
        for (int i = 0; i < n; i++) {
            for (Object key : keys) {
                map.get(key);
            }
        }
    }

    private void runCollidingGetTest(Map<CollidingInt, String> map, String mapName, int shift) {
        int size = 100000;
        Object[] keys = UnifiedMapAcceptanceTest.createCollidingMap(map, size, shift);
        UnifiedMapAcceptanceTest.sleep(100L);
        int n = 100;
        int max = 5;
        for (int i = 0; i < max; i++) {
            long startTime = System.nanoTime();
            UnifiedMapAcceptanceTest.runMapGet(map, keys, n);
            long runTimes = System.nanoTime() - startTime;
            LOGGER.info("{} with {} collisions. get: {} ns per get", mapName, 1 << shift, (double) runTimes / (double) n / size);
        }
    }

    private static CollidingInt[] createCollidingMap(Map<CollidingInt, String> map, int size, int shift) {
        CollidingInt[] keys = new CollidingInt[size];
        for (int i = 0; i < size; i++) {
            keys[i] = new CollidingInt(i, shift);
            map.put(keys[i], UnifiedMapAcceptanceTest.createVal(i));
        }
        Assert.assertEquals(size, map.size());
        return keys;
    }

    private static void sleep(long millis) {
        long now = System.currentTimeMillis();
        long target = now + millis;
        while (now < target) {
            try {
                Thread.sleep(target - now);
            } catch (InterruptedException ignored) {
                Assert.fail("why were we interrupted?");
            }
            now = System.currentTimeMillis();
        }
    }

    public static final class Entry implements Map.Entry<CollidingInt, String> {

        private final CollidingInt key;

        private String value;

        private Entry(CollidingInt key, String value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public CollidingInt getKey() {
            return this.key;
        }

        @Override
        public String getValue() {
            return this.value;
        }

        @Override
        public String setValue(String value) {
            String ret = this.value;
            this.value = value;
            return ret;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry<?, ?> entry = (Map.Entry<?, ?>) o;
            if (!Objects.equals(this.key, entry.getKey())) {
                return false;
            }
            return Objects.equals(this.value, entry.getValue());
        }

        @Override
        public int hashCode() {
            return this.key == null ? 0 : this.key.hashCode();
        }
    }

    @Test
    public void unifiedMapWithCollisions_1() {
        UnifiedMapAcceptanceTest.assertUnifiedMapWithCollisions(0, 2);
    }

    @Test
    public void unifiedMapWithCollisions_2() {
        UnifiedMapAcceptanceTest.assertUnifiedMapWithCollisions(1, 2);
    }

    @Test
    public void unifiedMapWithCollisions_3() {
        UnifiedMapAcceptanceTest.assertUnifiedMapWithCollisions(2, 2);
    }

    @Test
    public void unifiedMapWithCollisions_4() {
        UnifiedMapAcceptanceTest.assertUnifiedMapWithCollisions(3, 2);
    }

    @Test
    public void unifiedMapWithCollisions_5() {
        UnifiedMapAcceptanceTest.assertUnifiedMapWithCollisions(4, 2);
    }

    @Test
    public void unifiedMapWithCollisions_6() {
        UnifiedMapAcceptanceTest.assertUnifiedMapWithCollisions(0, 4);
    }

    @Test
    public void unifiedMapWithCollisions_7() {
        UnifiedMapAcceptanceTest.assertUnifiedMapWithCollisions(1, 4);
    }

    @Test
    public void unifiedMapWithCollisions_8() {
        UnifiedMapAcceptanceTest.assertUnifiedMapWithCollisions(2, 4);
    }

    @Test
    public void unifiedMapWithCollisions_9() {
        UnifiedMapAcceptanceTest.assertUnifiedMapWithCollisions(3, 4);
    }

    @Test
    public void unifiedMapWithCollisions_10() {
        UnifiedMapAcceptanceTest.assertUnifiedMapWithCollisions(4, 4);
    }

    @Test
    public void unifiedMapWithCollisions_11() {
        UnifiedMapAcceptanceTest.assertUnifiedMapWithCollisions(0, 8);
    }

    @Test
    public void unifiedMapWithCollisions_12() {
        UnifiedMapAcceptanceTest.assertUnifiedMapWithCollisions(1, 8);
    }

    @Test
    public void unifiedMapWithCollisions_13() {
        UnifiedMapAcceptanceTest.assertUnifiedMapWithCollisions(2, 8);
    }

    @Test
    public void unifiedMapWithCollisions_14() {
        UnifiedMapAcceptanceTest.assertUnifiedMapWithCollisions(3, 8);
    }

    @Test
    public void unifiedMapWithCollisions_15() {
        UnifiedMapAcceptanceTest.assertUnifiedMapWithCollisions(4, 8);
    }

    @Test
    public void unifiedMapWithCollisionsAndNullKey_1() {
        UnifiedMapAcceptanceTest.assertUnifiedMapWithCollisionsAndNullKey(0, 2);
    }

    @Test
    public void unifiedMapWithCollisionsAndNullKey_2() {
        UnifiedMapAcceptanceTest.assertUnifiedMapWithCollisionsAndNullKey(1, 2);
    }

    @Test
    public void unifiedMapWithCollisionsAndNullKey_3() {
        UnifiedMapAcceptanceTest.assertUnifiedMapWithCollisionsAndNullKey(2, 2);
    }

    @Test
    public void unifiedMapWithCollisionsAndNullKey_4() {
        UnifiedMapAcceptanceTest.assertUnifiedMapWithCollisionsAndNullKey(3, 2);
    }

    @Test
    public void unifiedMapWithCollisionsAndNullKey_5() {
        UnifiedMapAcceptanceTest.assertUnifiedMapWithCollisionsAndNullKey(4, 2);
    }

    @Test
    public void unifiedMapWithCollisionsAndNullKey_6() {
        UnifiedMapAcceptanceTest.assertUnifiedMapWithCollisionsAndNullKey(0, 4);
    }

    @Test
    public void unifiedMapWithCollisionsAndNullKey_7() {
        UnifiedMapAcceptanceTest.assertUnifiedMapWithCollisionsAndNullKey(1, 4);
    }

    @Test
    public void unifiedMapWithCollisionsAndNullKey_8() {
        UnifiedMapAcceptanceTest.assertUnifiedMapWithCollisionsAndNullKey(2, 4);
    }

    @Test
    public void unifiedMapWithCollisionsAndNullKey_9() {
        UnifiedMapAcceptanceTest.assertUnifiedMapWithCollisionsAndNullKey(3, 4);
    }

    @Test
    public void unifiedMapWithCollisionsAndNullKey_10() {
        UnifiedMapAcceptanceTest.assertUnifiedMapWithCollisionsAndNullKey(4, 4);
    }

    @Test
    public void unifiedMapWithCollisionsAndNullKey_11() {
        UnifiedMapAcceptanceTest.assertUnifiedMapWithCollisionsAndNullKey(0, 8);
    }

    @Test
    public void unifiedMapWithCollisionsAndNullKey_12() {
        UnifiedMapAcceptanceTest.assertUnifiedMapWithCollisionsAndNullKey(1, 8);
    }

    @Test
    public void unifiedMapWithCollisionsAndNullKey_13() {
        UnifiedMapAcceptanceTest.assertUnifiedMapWithCollisionsAndNullKey(2, 8);
    }

    @Test
    public void unifiedMapWithCollisionsAndNullKey_14() {
        UnifiedMapAcceptanceTest.assertUnifiedMapWithCollisionsAndNullKey(3, 8);
    }

    @Test
    public void unifiedMapWithCollisionsAndNullKey_15() {
        UnifiedMapAcceptanceTest.assertUnifiedMapWithCollisionsAndNullKey(4, 8);
    }

    @Test
    public void unifiedMapClear_1() {
        UnifiedMapAcceptanceTest.assertUnifiedMapClear(0);
    }

    @Test
    public void unifiedMapClear_2() {
        UnifiedMapAcceptanceTest.assertUnifiedMapClear(1);
    }

    @Test
    public void unifiedMapClear_3() {
        UnifiedMapAcceptanceTest.assertUnifiedMapClear(2);
    }

    @Test
    public void unifiedMapClear_4() {
        UnifiedMapAcceptanceTest.assertUnifiedMapClear(3);
    }

    @Test
    public void unifiedMapForEachEntry_1() {
        UnifiedMapAcceptanceTest.assertUnifiedMapForEachEntry(0);
    }

    @Test
    public void unifiedMapForEachEntry_2() {
        UnifiedMapAcceptanceTest.assertUnifiedMapForEachEntry(1);
    }

    @Test
    public void unifiedMapForEachEntry_3() {
        UnifiedMapAcceptanceTest.assertUnifiedMapForEachEntry(2);
    }

    @Test
    public void unifiedMapForEachEntry_4() {
        UnifiedMapAcceptanceTest.assertUnifiedMapForEachEntry(3);
    }

    @Test
    public void unifiedMapForEachKey_1() {
        UnifiedMapAcceptanceTest.assertUnifiedMapForEachKey(0);
    }

    @Test
    public void unifiedMapForEachKey_2() {
        UnifiedMapAcceptanceTest.assertUnifiedMapForEachKey(1);
    }

    @Test
    public void unifiedMapForEachKey_3() {
        UnifiedMapAcceptanceTest.assertUnifiedMapForEachKey(2);
    }

    @Test
    public void unifiedMapForEachKey_4() {
        UnifiedMapAcceptanceTest.assertUnifiedMapForEachKey(3);
    }

    @Test
    public void unifiedMapForEachValue_1() {
        UnifiedMapAcceptanceTest.assertUnifiedMapForEachValue(0);
    }

    @Test
    public void unifiedMapForEachValue_2() {
        UnifiedMapAcceptanceTest.assertUnifiedMapForEachValue(1);
    }

    @Test
    public void unifiedMapForEachValue_3() {
        UnifiedMapAcceptanceTest.assertUnifiedMapForEachValue(2);
    }

    @Test
    public void unifiedMapForEachValue_4() {
        UnifiedMapAcceptanceTest.assertUnifiedMapForEachValue(3);
    }

    @Test
    public void unifiedMapEqualsAndHashCode_1() {
        UnifiedMapAcceptanceTest.assertUnifiedMapEqualsAndHashCode(0);
    }

    @Test
    public void unifiedMapEqualsAndHashCode_2() {
        UnifiedMapAcceptanceTest.assertUnifiedMapEqualsAndHashCode(1);
    }

    @Test
    public void unifiedMapEqualsAndHashCode_3() {
        UnifiedMapAcceptanceTest.assertUnifiedMapEqualsAndHashCode(2);
    }

    @Test
    public void unifiedMapEqualsAndHashCode_4() {
        UnifiedMapAcceptanceTest.assertUnifiedMapEqualsAndHashCode(3);
    }

    @Test
    public void unifiedMapPutAll_1() {
        UnifiedMapAcceptanceTest.assertUnifiedMapPutAll(0);
    }

    @Test
    public void unifiedMapPutAll_2() {
        UnifiedMapAcceptanceTest.assertUnifiedMapPutAll(1);
    }

    @Test
    public void unifiedMapPutAll_3() {
        UnifiedMapAcceptanceTest.assertUnifiedMapPutAll(2);
    }

    @Test
    public void unifiedMapPutAll_4() {
        UnifiedMapAcceptanceTest.assertUnifiedMapPutAll(3);
    }

    @Test
    public void unifiedMapPutAllWithHashMap_1() {
        UnifiedMapAcceptanceTest.assertUnifiedMapPutAllWithHashMap(0);
    }

    @Test
    public void unifiedMapPutAllWithHashMap_2() {
        UnifiedMapAcceptanceTest.assertUnifiedMapPutAllWithHashMap(1);
    }

    @Test
    public void unifiedMapPutAllWithHashMap_3() {
        UnifiedMapAcceptanceTest.assertUnifiedMapPutAllWithHashMap(2);
    }

    @Test
    public void unifiedMapPutAllWithHashMap_4() {
        UnifiedMapAcceptanceTest.assertUnifiedMapPutAllWithHashMap(3);
    }

    @Test
    public void unifiedMapReplace_1() {
        UnifiedMapAcceptanceTest.assertUnifiedMapReplace(0);
    }

    @Test
    public void unifiedMapReplace_2() {
        UnifiedMapAcceptanceTest.assertUnifiedMapReplace(1);
    }

    @Test
    public void unifiedMapReplace_3() {
        UnifiedMapAcceptanceTest.assertUnifiedMapReplace(2);
    }

    @Test
    public void unifiedMapReplace_4() {
        UnifiedMapAcceptanceTest.assertUnifiedMapReplace(3);
    }
}
