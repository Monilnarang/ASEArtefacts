package org.eclipse.collections.impl.concurrent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.impl.CollidingInt;
import org.eclipse.collections.impl.block.factory.Procedures;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.set.mutable.MultiReaderUnifiedSet;
import org.eclipse.collections.impl.test.SerializeTestHelper;
import org.eclipse.collections.impl.test.Verify;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class MultiReaderUnifiedSetAcceptanceTest_Parameterized {

    private static void assertUnifiedSetClear(int shift) {
        MultiReaderUnifiedSet<CollidingInt> set = MultiReaderUnifiedSet.newSet();
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
        MultiReaderUnifiedSet<CollidingInt> set = MultiReaderUnifiedSet.newSet();
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
        MultiReaderUnifiedSet<CollidingInt> set = MultiReaderUnifiedSet.newSet();
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
        MultiReaderUnifiedSet<CollidingInt> set = MultiReaderUnifiedSet.newSet();
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
        MultiReaderUnifiedSet<CollidingInt> set = MultiReaderUnifiedSet.newSet();
        int size = 100000;
        for (int i = 0; i < size; i++) {
            set.add(new CollidingInt(i, shift));
        }
        MultiReaderUnifiedSet<CollidingInt> newSet = MultiReaderUnifiedSet.newSet(size);
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
        MultiReaderUnifiedSet<CollidingInt> newSet = MultiReaderUnifiedSet.newSet(size);
        newSet.addAll(set);
        Verify.assertSize(size, newSet);
        for (int i = 0; i < size; i++) {
            Verify.assertContains(new CollidingInt(i, shift), newSet);
        }
        MultiReaderUnifiedSet<CollidingInt> newSet2 = MultiReaderUnifiedSet.newSet();
        newSet2.addAll(set);
        Verify.assertSize(size, newSet2);
        for (int i = 0; i < size; i++) {
            Verify.assertContains(new CollidingInt(i, shift), newSet2);
        }
    }

    private static void assertUnifiedSetReplace(int shift) {
        MultiReaderUnifiedSet<CollidingInt> set = MultiReaderUnifiedSet.newSet();
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
        MultiReaderUnifiedSet<CollidingInt> set = MultiReaderUnifiedSet.newSet();
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
        MultiReaderUnifiedSet<CollidingInt> set = MultiReaderUnifiedSet.newSet();
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
        MultiReaderUnifiedSet<CollidingInt> set = MultiReaderUnifiedSet.newSet();
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

    private static void runUnifiedSetSerialize(int shift) {
        MultiReaderUnifiedSet<CollidingInt> set = MultiReaderUnifiedSet.newSet();
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

    private static void assertUnifiedSetEqualsAndHashCode(int shift) {
        MutableSet<CollidingInt> set1 = MultiReaderUnifiedSet.newSet();
        Set<CollidingInt> set2 = new HashSet<>();
        MutableSet<CollidingInt> set3 = MultiReaderUnifiedSet.newSet();
        MutableSet<CollidingInt> set4 = MultiReaderUnifiedSet.newSet();
        int size = 100000;
        for (int i = 0; i < size; i++) {
            set1.add(new CollidingInt(i, shift));
            set2.add(new CollidingInt(i, shift));
            set3.add(new CollidingInt(i, shift));
            set4.add(new CollidingInt(size - i - 1, shift));
        }
        Assert.assertEquals(set1, set2);
        Assert.assertEquals(set1.hashCode(), set2.hashCode());
        Verify.assertSetsEqual(set1, set3);
        Verify.assertEqualsAndHashCode(set1, set3);
        Verify.assertSetsEqual(set2, set4);
        Assert.assertEquals(set4, set2);
        Assert.assertEquals(set2.hashCode(), set4.hashCode());
    }

    private static void runUnifiedSetRemoveAll(int shift) {
        MultiReaderUnifiedSet<CollidingInt> set = MultiReaderUnifiedSet.newSet();
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
        MultiReaderUnifiedSet<CollidingIntWithFlag> set = MultiReaderUnifiedSet.newSet();
        for (int i = 0; i < 1000; i++) {
            Assert.assertTrue(set.add(new CollidingIntWithFlag(i, shift, false)));
        }
        Assert.assertEquals(1000, set.size());
        for (int i = 0; i < 1000; i++) {
            Assert.assertFalse(set.add(new CollidingIntWithFlag(i, shift, true)));
        }
        Assert.assertEquals(1000, set.size());
        set.withReadLockAndDelegate(delegate -> {
            for (CollidingIntWithFlag ciwf : delegate) {
                Assert.assertFalse(ciwf.isFlag());
            }
        });
    }

    private static final class CollidingIntWithFlag extends CollidingInt {

        private static final long serialVersionUID = 1L;

        private final boolean flag;

        private CollidingIntWithFlag(int value, int shift, boolean flag) {
            super(value, shift);
            this.flag = flag;
        }

        public boolean isFlag() {
            return this.flag;
        }
    }

    @ParameterizedTest
    @MethodSource("Provider_testUnifiedSetClear_1to4")
    public void testUnifiedSetClear_1to4(int param1) {
        assertUnifiedSetClear(param1);
    }

    static public Stream<Arguments> Provider_testUnifiedSetClear_1to4() {
        return Stream.of(arguments(0), arguments(1), arguments(2), arguments(3));
    }

    @ParameterizedTest
    @MethodSource("Provider_testUnifiedSetForEach_1to4")
    public void testUnifiedSetForEach_1to4(int param1) {
        assertUnifiedSetForEach(param1);
    }

    static public Stream<Arguments> Provider_testUnifiedSetForEach_1to4() {
        return Stream.of(arguments(0), arguments(1), arguments(2), arguments(3));
    }

    @ParameterizedTest
    @MethodSource("Provider_testUnifiedSetForEachWith_1to4")
    public void testUnifiedSetForEachWith_1to4(int param1) {
        assertUnifiedSetForEachWith(param1);
    }

    static public Stream<Arguments> Provider_testUnifiedSetForEachWith_1to4() {
        return Stream.of(arguments(0), arguments(1), arguments(2), arguments(3));
    }

    @ParameterizedTest
    @MethodSource("Provider_testUnifiedSetForEachWithIndex_1to4")
    public void testUnifiedSetForEachWithIndex_1to4(int param1) {
        assertUnifiedSetForEachWithIndex(param1);
    }

    static public Stream<Arguments> Provider_testUnifiedSetForEachWithIndex_1to4() {
        return Stream.of(arguments(0), arguments(1), arguments(2), arguments(3));
    }

    @ParameterizedTest
    @MethodSource("Provider_testUnifiedSetAddAll_1to4")
    public void testUnifiedSetAddAll_1to4(int param1) {
        assertUnifiedSetAddAll(param1);
    }

    static public Stream<Arguments> Provider_testUnifiedSetAddAll_1to4() {
        return Stream.of(arguments(0), arguments(1), arguments(2), arguments(3));
    }

    @ParameterizedTest
    @MethodSource("Provider_testUnifiedSetAddAllWithHashSet_1to4")
    public void testUnifiedSetAddAllWithHashSet_1to4(int param1) {
        assertUnifiedSetAddAllWithHashSet(param1);
    }

    static public Stream<Arguments> Provider_testUnifiedSetAddAllWithHashSet_1to4() {
        return Stream.of(arguments(0), arguments(1), arguments(2), arguments(3));
    }

    @ParameterizedTest
    @MethodSource("Provider_testUnifiedSetReplace_1to4")
    public void testUnifiedSetReplace_1to4(int param1) {
        assertUnifiedSetReplace(param1);
    }

    static public Stream<Arguments> Provider_testUnifiedSetReplace_1to4() {
        return Stream.of(arguments(0), arguments(1), arguments(2), arguments(3));
    }

    @ParameterizedTest
    @MethodSource("Provider_testUnifiedSetEqualsAndHashCode_1to4")
    public void testUnifiedSetEqualsAndHashCode_1to4(int param1) {
        assertUnifiedSetEqualsAndHashCode(param1);
    }

    static public Stream<Arguments> Provider_testUnifiedSetEqualsAndHashCode_1to4() {
        return Stream.of(arguments(0), arguments(1), arguments(2), arguments(3));
    }

    @ParameterizedTest
    @MethodSource("Provider_testUnifiedSetPutDoesNotReplace_1to5")
    public void testUnifiedSetPutDoesNotReplace_1to5(int param1) {
        this.assertUnifiedSetPutDoesNotReplace(param1);
    }

    static public Stream<Arguments> Provider_testUnifiedSetPutDoesNotReplace_1to5() {
        return Stream.of(arguments(0), arguments(1), arguments(2), arguments(3), arguments(4));
    }
}
