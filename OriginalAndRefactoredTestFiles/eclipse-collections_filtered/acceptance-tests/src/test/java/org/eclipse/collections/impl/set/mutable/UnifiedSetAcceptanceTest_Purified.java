package org.eclipse.collections.impl.set.mutable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.api.set.Pool;
import org.eclipse.collections.impl.CollidingInt;
import org.eclipse.collections.impl.block.factory.Procedures;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.test.SerializeTestHelper;
import org.eclipse.collections.impl.test.Verify;
import org.junit.Assert;
import org.junit.Test;

public class UnifiedSetAcceptanceTest_Purified {

    private static void assertUnifiedSetWithCollisions(int shift, int removeStride) {
        UnifiedSet<CollidingInt> set = UnifiedSet.newSet();
        int size = 84000;
        for (int i = 0; i < size; i++) {
            Assert.assertTrue(set.add(new CollidingInt(i, shift)));
        }
        Verify.assertSize(size, set);
        for (int i = 0; i < size; i++) {
            Verify.assertContains(new CollidingInt(i, shift), set);
        }
        for (int i = 0; i < size; i += removeStride) {
            Assert.assertTrue(set.remove(new CollidingInt(i, shift)));
        }
        Verify.assertSize(size - size / removeStride, set);
        for (int i = 0; i < size; i++) {
            if (i % removeStride == 0) {
                Verify.assertNotContains(new CollidingInt(i, shift), set);
            } else {
                Verify.assertContains(new CollidingInt(i, shift), set);
            }
        }
        for (int i = 0; i < size; i++) {
            set.remove(new CollidingInt(i, shift));
        }
        Verify.assertEmpty(set);
    }

    private static void setupAndAssertUnifiedSetWithCollisionsAndNullKey(int shift, int removeStride) {
        UnifiedSet<CollidingInt> set = UnifiedSet.newSet();
        Verify.assertEmpty(set);
        int size = 84000;
        for (int i = 0; i < size; i++) {
            Assert.assertTrue(set.add(new CollidingInt(i, shift)));
        }
        set.add(null);
        UnifiedSet<CollidingInt> clone = set.clone();
        assertUnifiedSetWithCollisionsAndNullKey(shift, removeStride, clone, size);
        assertUnifiedSetWithCollisionsAndNullKey(shift, removeStride, set, size);
    }

    private static void assertUnifiedSetWithCollisionsAndNullKey(int shift, int removeStride, UnifiedSet<CollidingInt> set, int size) {
        Verify.assertSize(size + 1, set);
        for (int i = 0; i < size; i++) {
            Verify.assertContains(new CollidingInt(i, shift), set);
        }
        Verify.assertContains(null, set);
        for (int i = 0; i < size; i += removeStride) {
            Assert.assertTrue(set.remove(new CollidingInt(i, shift)));
        }
        Verify.assertSize(size - size / removeStride + 1, set);
        for (int i = 0; i < size; i++) {
            if (i % removeStride != 0) {
                Verify.assertContains(new CollidingInt(i, shift), set);
            }
        }
        Verify.assertContains(null, set);
        set.remove(null);
        Verify.assertNotContains(null, set);
    }

    private static void assertUnifiedSetClear(int shift) {
        UnifiedSet<CollidingInt> set = UnifiedSet.newSet();
        int size = 100000;
        for (int i = 0; i < size; i++) {
            Assert.assertTrue(set.add(new CollidingInt(i, shift)));
        }
        set.clear();
        Verify.assertEmpty(set);
        for (int i = 0; i < size; i++) {
            Verify.assertNotContains(new CollidingInt(i, shift), set);
        }
    }

    private static void assertUnifiedSetForEach(int shift) {
        UnifiedSet<CollidingInt> set = UnifiedSet.newSet();
        int size = 100000;
        for (int i = 0; i < size; i++) {
            Assert.assertTrue(set.add(new CollidingInt(i, shift)));
        }
        MutableList<CollidingInt> keys = FastList.newList(size);
        set.forEach(Procedures.cast(keys::add));
        Verify.assertSize(size, keys);
        Collections.sort(keys);
        for (int i = 0; i < size; i++) {
            Verify.assertItemAtIndex(new CollidingInt(i, shift), i, keys);
        }
    }

    private static void assertUnifiedSetForEachWith(int shift) {
        UnifiedSet<CollidingInt> set = UnifiedSet.newSet();
        int size = 100000;
        for (int i = 0; i < size; i++) {
            Assert.assertTrue(set.add(new CollidingInt(i, shift)));
        }
        MutableList<CollidingInt> keys = FastList.newList(size);
        set.forEachWith((key, s) -> {
            Assert.assertEquals("foo", s);
            keys.add(key);
        }, "foo");
        Verify.assertSize(size, keys);
        Collections.sort(keys);
        for (int i = 0; i < size; i++) {
            Verify.assertItemAtIndex(new CollidingInt(i, shift), i, keys);
        }
    }

    private static void assertUnifiedSetForEachWithIndex(int shift) {
        UnifiedSet<CollidingInt> set = UnifiedSet.newSet();
        int size = 100000;
        for (int i = 0; i < size; i++) {
            Assert.assertTrue(set.add(new CollidingInt(i, shift)));
        }
        MutableList<CollidingInt> keys = FastList.newList(size);
        int[] prevIndex = new int[1];
        set.forEachWithIndex((key, index) -> {
            Assert.assertEquals(prevIndex[0], index);
            prevIndex[0]++;
            keys.add(key);
        });
        Verify.assertSize(size, keys);
        Collections.sort(keys);
        for (int i = 0; i < size; i++) {
            Verify.assertItemAtIndex(new CollidingInt(i, shift), i, keys);
        }
    }

    private static void assertUnifiedSetAddAll(int shift) {
        UnifiedSet<CollidingInt> set = UnifiedSet.newSet();
        int size = 100000;
        for (int i = 0; i < size; i++) {
            set.add(new CollidingInt(i, shift));
        }
        UnifiedSet<CollidingInt> newSet = UnifiedSet.newSet(size);
        newSet.addAll(set);
        Verify.assertSize(size, newSet);
        for (int i = 0; i < size; i++) {
            Verify.assertContains(new CollidingInt(i, shift), newSet);
        }
    }

    private static void assertUnifiedSetAddAllWithHashSet(int shift) {
        Set<CollidingInt> set = new HashSet<>();
        int size = 100000;
        for (int i = 0; i < size; i++) {
            set.add(new CollidingInt(i, shift));
        }
        UnifiedSet<CollidingInt> newSet = UnifiedSet.newSet(size);
        newSet.addAll(set);
        Verify.assertSize(size, newSet);
        for (int i = 0; i < size; i++) {
            Verify.assertContains(new CollidingInt(i, shift), newSet);
        }
        UnifiedSet<CollidingInt> newSet2 = UnifiedSet.newSet();
        newSet2.addAll(set);
        Verify.assertSize(size, newSet2);
        for (int i = 0; i < size; i++) {
            Verify.assertContains(new CollidingInt(i, shift), newSet2);
        }
    }

    private static void assertUnifiedSetReplace(int shift) {
        UnifiedSet<CollidingInt> set = UnifiedSet.newSet();
        int size = 100000;
        for (int i = 0; i < size; i++) {
            set.add(new CollidingInt(i, shift));
        }
        for (int i = 0; i < size; i++) {
            set.add(new CollidingInt(i, shift));
        }
        Verify.assertSize(size, set);
        for (int i = 0; i < size; i++) {
            Verify.assertContains(new CollidingInt(i, shift), set);
        }
    }

    private static void runUnifiedSetRetainAllFromList(int shift) {
        UnifiedSet<CollidingInt> set = UnifiedSet.newSet();
        MutableList<CollidingInt> toRetain = Lists.mutable.of();
        int size = 100000;
        for (int i = 0; i < size; i++) {
            set.add(new CollidingInt(i, shift));
            if (i % 2 == 0) {
                toRetain.add(new CollidingInt(i, shift));
            }
        }
        Verify.assertSize(size, set);
        Assert.assertTrue(set.containsAll(toRetain));
        Assert.assertTrue(set.retainAll(toRetain));
        Assert.assertTrue(set.containsAll(toRetain));
        Assert.assertFalse(set.retainAll(toRetain));
        Verify.assertSize(size / 2, set);
        for (int i = 0; i < size; i += 2) {
            Verify.assertContains(new CollidingInt(i, shift), set);
        }
    }

    private static void runUnifiedSetRetainAllFromSet(int shift) {
        UnifiedSet<CollidingInt> set = UnifiedSet.newSet();
        Set<CollidingInt> toRetain = new HashSet<>();
        int size = 100000;
        for (int i = 0; i < size; i++) {
            set.add(new CollidingInt(i, shift));
            if (i % 2 == 0) {
                toRetain.add(new CollidingInt(i, shift));
            }
        }
        Verify.assertSize(size, set);
        Assert.assertTrue(set.containsAll(toRetain));
        Assert.assertTrue(set.retainAll(toRetain));
        Assert.assertTrue(set.containsAll(toRetain));
        Assert.assertFalse(set.retainAll(toRetain));
        Verify.assertSize(size / 2, set);
        for (int i = 0; i < size; i += 2) {
            Verify.assertContains(new CollidingInt(i, shift), set);
        }
    }

    private static void runUnifiedSetToArray(int shift) {
        UnifiedSet<CollidingInt> set = UnifiedSet.newSet();
        int size = 100000;
        for (int i = 0; i < size; i++) {
            set.add(new CollidingInt(i, shift));
        }
        Verify.assertSize(size, set);
        Object[] keys = set.toArray();
        Assert.assertEquals(size, keys.length);
        Arrays.sort(keys);
        for (int i = 0; i < size; i++) {
            Verify.assertItemAtIndex(new CollidingInt(i, shift), i, keys);
        }
    }

    private static void runUnifiedSetIterator(int shift) {
        UnifiedSet<CollidingInt> set = UnifiedSet.newSet();
        int size = 100000;
        for (int i = 0; i < size; i++) {
            set.add(new CollidingInt(i, shift));
        }
        Verify.assertSize(size, set);
        CollidingInt[] keys = new CollidingInt[size];
        int count = 0;
        for (CollidingInt collidingInt : set) {
            keys[count++] = collidingInt;
        }
        Arrays.sort(keys);
        for (int i = 0; i < size; i++) {
            Assert.assertEquals(new CollidingInt(i, shift), keys[i]);
        }
    }

    private static void runUnifiedSetIteratorRemove(int shift, int removeStride) {
        UnifiedSet<CollidingInt> set = UnifiedSet.newSet();
        int size = 100000;
        for (int i = 0; i < size; i++) {
            set.add(new CollidingInt(i, shift));
        }
        Verify.assertSize(size, set);
        int count = 0;
        for (Iterator<CollidingInt> it = set.iterator(); it.hasNext(); ) {
            CollidingInt key = it.next();
            count++;
            if (key.getValue() % removeStride == 0) {
                it.remove();
            }
        }
        Assert.assertEquals(size, count);
        for (int i = 0; i < size; i++) {
            if (i % removeStride != 0) {
                Verify.assertContains("set contains " + i + "for shift " + shift + " and remove stride " + removeStride, new CollidingInt(i, shift), set);
            }
        }
    }

    private static void runUnifiedSetKeySetIteratorRemoveFlip(int shift, int removeStride) {
        UnifiedSet<CollidingInt> set = UnifiedSet.newSet();
        int size = 100000;
        for (int i = 0; i < size; i++) {
            set.add(new CollidingInt(i, shift));
        }
        Verify.assertSize(size, set);
        int count = 0;
        for (Iterator<CollidingInt> it = set.iterator(); it.hasNext(); ) {
            CollidingInt key = it.next();
            count++;
            if (key.getValue() % removeStride != 0) {
                it.remove();
            }
        }
        Assert.assertEquals(size, count);
        for (int i = 0; i < size; i++) {
            if (i % removeStride == 0) {
                Verify.assertContains("set contains " + i + "for shift " + shift + " and remove stride " + removeStride, new CollidingInt(i, shift), set);
            }
        }
    }

    private static void runUnifiedSetSerialize(int shift) {
        UnifiedSet<CollidingInt> set = UnifiedSet.newSet();
        int size = 100000;
        for (int i = 0; i < size; i++) {
            set.add(new CollidingInt(i, shift));
        }
        set.add(null);
        set = SerializeTestHelper.serializeDeserialize(set);
        Verify.assertSize(size + 1, set);
        for (int i = 0; i < size; i++) {
            Verify.assertContains(new CollidingInt(i, shift), set);
        }
        Verify.assertContains(null, set);
    }

    public static <T> T[] shuffle(T[] array) {
        Object[] result = new Object[array.length];
        Random rand = new Random(12345678912345L);
        int left = array.length;
        for (int i = 0; i < array.length; i++) {
            int chosen = rand.nextInt(left);
            result[i] = array[chosen];
            left--;
            array[chosen] = array[left];
            array[left] = null;
        }
        System.arraycopy(result, 0, array, 0, array.length);
        return array;
    }

    private static void assertUnifiedSetEqualsAndHashCode(int shift) {
        MutableSet<CollidingInt> set1 = UnifiedSet.newSet();
        Set<CollidingInt> set2 = new HashSet<>();
        MutableSet<CollidingInt> set3 = UnifiedSet.newSet();
        MutableSet<CollidingInt> set4 = UnifiedSet.newSet();
        int size = 100000;
        for (int i = 0; i < size; i++) {
            set1.add(new CollidingInt(i, shift));
            set2.add(new CollidingInt(i, shift));
            set3.add(new CollidingInt(i, shift));
            set4.add(new CollidingInt(size - i - 1, shift));
        }
        Verify.assertSetsEqual(set2, set1);
        Verify.assertSetsEqual(set1, set2);
        Verify.assertEqualsAndHashCode(set2, set1);
        Verify.assertSetsEqual(set1, set3);
        Verify.assertEqualsAndHashCode(set1, set3);
        Verify.assertSetsEqual(set2, set4);
        Verify.assertSetsEqual(set4, set2);
        Verify.assertEqualsAndHashCode(set2, set4);
    }

    private static void runUnifiedSetRemoveAll(int shift) {
        UnifiedSet<CollidingInt> set = UnifiedSet.newSet();
        List<CollidingInt> toRemove = new ArrayList<>();
        int size = 100000;
        for (int i = 0; i < size; i++) {
            set.add(new CollidingInt(i, shift));
            if (i % 2 == 0) {
                toRemove.add(new CollidingInt(i, shift));
            }
        }
        Verify.assertSize(size, set);
        Assert.assertTrue(set.removeAll(toRemove));
        Assert.assertFalse(set.removeAll(toRemove));
        Verify.assertSize(size / 2, set);
        for (int i = 1; i < size; i += 2) {
            Verify.assertContains(new CollidingInt(i, shift), set);
        }
    }

    private void assertUnifiedSetPutDoesNotReplace(int shift) {
        UnifiedSet<CollidingIntWithFlag> set = UnifiedSet.newSet();
        for (int i = 0; i < 1000; i++) {
            Assert.assertTrue(set.add(new CollidingIntWithFlag(i, shift, false)));
        }
        Assert.assertEquals(1000, set.size());
        for (int i = 0; i < 1000; i++) {
            Assert.assertFalse(set.add(new CollidingIntWithFlag(i, shift, true)));
        }
        Assert.assertEquals(1000, set.size());
        for (CollidingIntWithFlag ciwf : set) {
            Assert.assertFalse(ciwf.flag);
        }
    }

    private void runUnifiedSetAsPool(int shift) {
        CollidingInt[] toPool = new CollidingInt[5000];
        Pool<CollidingInt> set = new UnifiedSet<>();
        for (int i = 0; i < toPool.length; i++) {
            toPool[i] = new CollidingInt(i, shift);
            Assert.assertSame(toPool[i], set.put(toPool[i]));
        }
        for (int i = 0; i < toPool.length; i++) {
            Assert.assertSame(toPool[i], set.put(new CollidingInt(i, shift)));
        }
        Random random = new Random();
        for (int i = 0; i < toPool.length * 4; i++) {
            int x = random.nextInt(toPool.length);
            Assert.assertSame(toPool[x], set.put(new CollidingInt(x, shift)));
        }
        for (int i = 0; i < toPool.length; i++) {
            Assert.assertSame(toPool[i], set.get(toPool[i]));
        }
        for (int i = 0; i < toPool.length * 4; i++) {
            int x = random.nextInt(toPool.length);
            Assert.assertSame(toPool[x], set.removeFromPool(new CollidingInt(x, shift)));
            toPool[x] = null;
        }
    }

    private void runUnifiedSetAsPoolRandomInput(int shift) {
        CollidingInt[] toPool = new CollidingInt[5000];
        for (int i = 0; i < toPool.length; i++) {
            toPool[i] = new CollidingInt(i, shift);
        }
        toPool = shuffle(toPool);
        Pool<CollidingInt> set = new UnifiedSet<>();
        for (int i = 0; i < toPool.length; i++) {
            Assert.assertSame(toPool[i], set.put(toPool[i]));
        }
        for (int i = 0; i < toPool.length; i++) {
            Assert.assertSame(toPool[i], set.put(new CollidingInt(toPool[i].getValue(), shift)));
        }
        Random random = new Random();
        for (int i = 0; i < toPool.length * 4; i++) {
            int x = random.nextInt(toPool.length);
            Assert.assertSame(toPool[x], set.put(new CollidingInt(toPool[x].getValue(), shift)));
        }
        for (int i = 0; i < toPool.length; i++) {
            Assert.assertSame(toPool[i], set.get(toPool[i]));
        }
    }

    private static final class CollidingIntWithFlag extends CollidingInt {

        private static final long serialVersionUID = 1L;

        private final boolean flag;

        private CollidingIntWithFlag(int value, int shift, boolean flag) {
            super(value, shift);
            this.flag = flag;
        }
    }

    @Test
    public void testUnifiedSetClear_1() {
        assertUnifiedSetClear(0);
    }

    @Test
    public void testUnifiedSetClear_2() {
        assertUnifiedSetClear(1);
    }

    @Test
    public void testUnifiedSetClear_3() {
        assertUnifiedSetClear(2);
    }

    @Test
    public void testUnifiedSetClear_4() {
        assertUnifiedSetClear(3);
    }

    @Test
    public void testUnifiedSetForEach_1() {
        assertUnifiedSetForEach(0);
    }

    @Test
    public void testUnifiedSetForEach_2() {
        assertUnifiedSetForEach(1);
    }

    @Test
    public void testUnifiedSetForEach_3() {
        assertUnifiedSetForEach(2);
    }

    @Test
    public void testUnifiedSetForEach_4() {
        assertUnifiedSetForEach(3);
    }

    @Test
    public void testUnifiedSetForEachWith_1() {
        assertUnifiedSetForEachWith(0);
    }

    @Test
    public void testUnifiedSetForEachWith_2() {
        assertUnifiedSetForEachWith(1);
    }

    @Test
    public void testUnifiedSetForEachWith_3() {
        assertUnifiedSetForEachWith(2);
    }

    @Test
    public void testUnifiedSetForEachWith_4() {
        assertUnifiedSetForEachWith(3);
    }

    @Test
    public void testUnifiedSetForEachWithIndex_1() {
        assertUnifiedSetForEachWithIndex(0);
    }

    @Test
    public void testUnifiedSetForEachWithIndex_2() {
        assertUnifiedSetForEachWithIndex(1);
    }

    @Test
    public void testUnifiedSetForEachWithIndex_3() {
        assertUnifiedSetForEachWithIndex(2);
    }

    @Test
    public void testUnifiedSetForEachWithIndex_4() {
        assertUnifiedSetForEachWithIndex(3);
    }

    @Test
    public void testUnifiedSetAddAll_1() {
        assertUnifiedSetAddAll(0);
    }

    @Test
    public void testUnifiedSetAddAll_2() {
        assertUnifiedSetAddAll(1);
    }

    @Test
    public void testUnifiedSetAddAll_3() {
        assertUnifiedSetAddAll(2);
    }

    @Test
    public void testUnifiedSetAddAll_4() {
        assertUnifiedSetAddAll(3);
    }

    @Test
    public void testUnifiedSetAddAllWithHashSet_1() {
        assertUnifiedSetAddAllWithHashSet(0);
    }

    @Test
    public void testUnifiedSetAddAllWithHashSet_2() {
        assertUnifiedSetAddAllWithHashSet(1);
    }

    @Test
    public void testUnifiedSetAddAllWithHashSet_3() {
        assertUnifiedSetAddAllWithHashSet(2);
    }

    @Test
    public void testUnifiedSetAddAllWithHashSet_4() {
        assertUnifiedSetAddAllWithHashSet(3);
    }

    @Test
    public void testUnifiedSetReplace_1() {
        assertUnifiedSetReplace(0);
    }

    @Test
    public void testUnifiedSetReplace_2() {
        assertUnifiedSetReplace(1);
    }

    @Test
    public void testUnifiedSetReplace_3() {
        assertUnifiedSetReplace(2);
    }

    @Test
    public void testUnifiedSetReplace_4() {
        assertUnifiedSetReplace(3);
    }

    @Test
    public void testUnifiedSetEqualsAndHashCode_1() {
        assertUnifiedSetEqualsAndHashCode(0);
    }

    @Test
    public void testUnifiedSetEqualsAndHashCode_2() {
        assertUnifiedSetEqualsAndHashCode(1);
    }

    @Test
    public void testUnifiedSetEqualsAndHashCode_3() {
        assertUnifiedSetEqualsAndHashCode(2);
    }

    @Test
    public void testUnifiedSetEqualsAndHashCode_4() {
        assertUnifiedSetEqualsAndHashCode(3);
    }

    @Test
    public void testUnifiedSetPutDoesNotReplace_1() {
        this.assertUnifiedSetPutDoesNotReplace(0);
    }

    @Test
    public void testUnifiedSetPutDoesNotReplace_2() {
        this.assertUnifiedSetPutDoesNotReplace(1);
    }

    @Test
    public void testUnifiedSetPutDoesNotReplace_3() {
        this.assertUnifiedSetPutDoesNotReplace(2);
    }

    @Test
    public void testUnifiedSetPutDoesNotReplace_4() {
        this.assertUnifiedSetPutDoesNotReplace(3);
    }

    @Test
    public void testUnifiedSetPutDoesNotReplace_5() {
        this.assertUnifiedSetPutDoesNotReplace(4);
    }
}
