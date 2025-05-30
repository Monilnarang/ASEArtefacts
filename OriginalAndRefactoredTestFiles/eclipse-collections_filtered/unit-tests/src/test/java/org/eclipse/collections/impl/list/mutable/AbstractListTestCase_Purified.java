package org.eclipse.collections.impl.list.mutable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import org.eclipse.collections.api.RichIterable;
import org.eclipse.collections.api.collection.MutableCollection;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.factory.Sets;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.ListIterable;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.list.primitive.MutableBooleanList;
import org.eclipse.collections.api.list.primitive.MutableByteList;
import org.eclipse.collections.api.list.primitive.MutableCharList;
import org.eclipse.collections.api.list.primitive.MutableDoubleList;
import org.eclipse.collections.api.list.primitive.MutableFloatList;
import org.eclipse.collections.api.list.primitive.MutableIntList;
import org.eclipse.collections.api.list.primitive.MutableLongList;
import org.eclipse.collections.api.list.primitive.MutableShortList;
import org.eclipse.collections.api.partition.list.PartitionMutableList;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.api.stack.MutableStack;
import org.eclipse.collections.api.tuple.Pair;
import org.eclipse.collections.api.tuple.primitive.ObjectIntPair;
import org.eclipse.collections.impl.block.factory.Comparators;
import org.eclipse.collections.impl.block.factory.HashingStrategies;
import org.eclipse.collections.impl.block.factory.Predicates;
import org.eclipse.collections.impl.block.factory.Predicates2;
import org.eclipse.collections.impl.block.factory.PrimitiveFunctions;
import org.eclipse.collections.impl.block.procedure.CollectionAddProcedure;
import org.eclipse.collections.impl.collection.mutable.AbstractCollectionTestCase;
import org.eclipse.collections.impl.factory.Stacks;
import org.eclipse.collections.impl.factory.primitive.BooleanLists;
import org.eclipse.collections.impl.factory.primitive.ByteLists;
import org.eclipse.collections.impl.factory.primitive.CharLists;
import org.eclipse.collections.impl.factory.primitive.DoubleLists;
import org.eclipse.collections.impl.factory.primitive.FloatLists;
import org.eclipse.collections.impl.factory.primitive.IntLists;
import org.eclipse.collections.impl.factory.primitive.IntSets;
import org.eclipse.collections.impl.factory.primitive.LongLists;
import org.eclipse.collections.impl.factory.primitive.ShortLists;
import org.eclipse.collections.impl.lazy.ReverseIterable;
import org.eclipse.collections.impl.list.Interval;
import org.eclipse.collections.impl.list.fixed.ArrayAdapter;
import org.eclipse.collections.impl.list.mutable.primitive.IntArrayList;
import org.eclipse.collections.impl.set.mutable.UnifiedSet;
import org.eclipse.collections.impl.test.SerializeTestHelper;
import org.eclipse.collections.impl.test.Verify;
import org.eclipse.collections.impl.tuple.Tuples;
import org.eclipse.collections.impl.tuple.primitive.PrimitiveTuples;
import org.junit.jupiter.api.Test;
import static org.eclipse.collections.impl.factory.Iterables.iList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public abstract class AbstractListTestCase_Purified extends AbstractCollectionTestCase {

    @Override
    protected abstract <T> MutableList<T> newWith(T... littleElements);

    @Override
    public void collectBoolean() {
        super.collectBoolean();
        MutableBooleanList result = this.newWith(-1, 0, 1, 4).collectBoolean(PrimitiveFunctions.integerIsPositive());
        assertEquals(BooleanLists.mutable.of(false, false, true, true), result);
    }

    @Override
    public void collectByte() {
        super.collectByte();
        MutableByteList result = this.newWith(1, 2, 3, 4).collectByte(PrimitiveFunctions.unboxIntegerToByte());
        assertEquals(ByteLists.mutable.of((byte) 1, (byte) 2, (byte) 3, (byte) 4), result);
    }

    @Override
    public void collectChar() {
        super.collectChar();
        MutableCharList result = this.newWith(1, 2, 3, 4).collectChar(PrimitiveFunctions.unboxIntegerToChar());
        assertEquals(CharLists.mutable.of((char) 1, (char) 2, (char) 3, (char) 4), result);
    }

    @Override
    public void collectDouble() {
        super.collectDouble();
        MutableDoubleList result = this.newWith(1, 2, 3, 4).collectDouble(PrimitiveFunctions.unboxIntegerToDouble());
        assertEquals(DoubleLists.mutable.of(1.0d, 2.0d, 3.0d, 4.0d), result);
    }

    @Override
    public void collectFloat() {
        super.collectFloat();
        MutableFloatList result = this.newWith(1, 2, 3, 4).collectFloat(PrimitiveFunctions.unboxIntegerToFloat());
        assertEquals(FloatLists.mutable.of(1.0f, 2.0f, 3.0f, 4.0f), result);
    }

    @Override
    public void collectInt() {
        super.collectInt();
        MutableIntList result = this.newWith(1, 2, 3, 4).collectInt(PrimitiveFunctions.unboxIntegerToInt());
        assertEquals(IntLists.mutable.of(1, 2, 3, 4), result);
    }

    @Override
    public void collectLong() {
        super.collectLong();
        MutableLongList result = this.newWith(1, 2, 3, 4).collectLong(PrimitiveFunctions.unboxIntegerToLong());
        assertEquals(LongLists.mutable.of(1L, 2L, 3L, 4L), result);
    }

    @Override
    public void collectShort() {
        super.collectShort();
        MutableShortList result = this.newWith(1, 2, 3, 4).collectShort(PrimitiveFunctions.unboxIntegerToShort());
        assertEquals(ShortLists.mutable.of((short) 1, (short) 2, (short) 3, (short) 4), result);
    }

    protected void validateForEachOnRange(MutableList<Integer> list, int from, int to, List<Integer> expectedOutput) {
        List<Integer> outputList = Lists.mutable.empty();
        list.forEach(from, to, outputList::add);
        assertEquals(expectedOutput, outputList);
    }

    protected void validateForEachWithIndexOnRange(MutableList<Integer> list, int from, int to, List<Integer> expectedOutput) {
        MutableList<Integer> outputList = Lists.mutable.empty();
        list.forEachWithIndex(from, to, (each, index) -> outputList.add(each));
        assertEquals(expectedOutput, outputList);
    }

    @Test
    public void getFirstOptional_1() {
        assertEquals(Integer.valueOf(1), this.newWith(1, 2, 3).getFirstOptional().get());
    }

    @Test
    public void getFirstOptional_2() {
        assertTrue(this.newWith(1, 2, 3).getFirstOptional().isPresent());
    }

    @Test
    public void getFirstOptional_3() {
        assertFalse(this.newWith().getFirstOptional().isPresent());
    }

    @Test
    public void getLastOptional_1() {
        assertEquals(Integer.valueOf(3), this.newWith(1, 2, 3).getLastOptional().get());
    }

    @Test
    public void getLastOptional_2() {
        assertTrue(this.newWith(1, 2, 3).getLastOptional().isPresent());
    }

    @Test
    public void getLastOptional_3() {
        assertFalse(this.newWith().getLastOptional().isPresent());
    }

    @Override
    @Test
    public void toImmutable_1() {
        Verify.assertInstanceOf(ImmutableList.class, this.newWith().toImmutable());
    }

    @Override
    @Test
    public void toImmutable_2() {
        assertSame(this.newWith().toImmutable(), this.newWith().toImmutable());
    }

    @Test
    public void withMethods_1() {
        Verify.assertContainsAll(this.newWith().with(1), 1);
    }

    @Test
    public void withMethods_2() {
        Verify.assertContainsAll(this.newWith(1), 1);
    }

    @Test
    public void withMethods_3() {
        Verify.assertContainsAll(this.newWith(1).with(2), 1, 2);
    }

    @Test
    public void takeWhile_1() {
        assertEquals(iList(1, 2, 3), this.newWith(1, 2, 3, 4, 5).takeWhile(Predicates.lessThan(4)));
    }

    @Test
    public void takeWhile_2() {
        assertEquals(iList(1, 2, 3, 4, 5), this.newWith(1, 2, 3, 4, 5).takeWhile(Predicates.lessThan(10)));
    }

    @Test
    public void takeWhile_3() {
        assertEquals(iList(), this.newWith(1, 2, 3, 4, 5).takeWhile(Predicates.lessThan(0)));
    }

    @Test
    public void dropWhile_1() {
        assertEquals(iList(4, 5), this.newWith(1, 2, 3, 4, 5).dropWhile(Predicates.lessThan(4)));
    }

    @Test
    public void dropWhile_2() {
        assertEquals(iList(), this.newWith(1, 2, 3, 4, 5).dropWhile(Predicates.lessThan(10)));
    }

    @Test
    public void dropWhile_3() {
        assertEquals(iList(1, 2, 3, 4, 5), this.newWith(1, 2, 3, 4, 5).dropWhile(Predicates.lessThan(0)));
    }

    @Test
    public void asReversed_1() {
        Verify.assertInstanceOf(ReverseIterable.class, this.newWith().asReversed());
    }

    @Test
    public void asReversed_2() {
        Verify.assertIterablesEqual(iList(4, 3, 2, 1), this.newWith(1, 2, 3, 4).asReversed());
    }
}
