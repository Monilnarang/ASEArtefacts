package org.eclipse.collections.impl.utility;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import org.eclipse.collections.api.RichIterable;
import org.eclipse.collections.api.block.function.Function;
import org.eclipse.collections.api.block.function.Function3;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.list.primitive.MutableBooleanList;
import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.api.map.primitive.ObjectDoubleMap;
import org.eclipse.collections.api.map.primitive.ObjectLongMap;
import org.eclipse.collections.api.multimap.Multimap;
import org.eclipse.collections.api.multimap.MutableMultimap;
import org.eclipse.collections.api.partition.PartitionIterable;
import org.eclipse.collections.api.tuple.Pair;
import org.eclipse.collections.api.tuple.Twin;
import org.eclipse.collections.impl.block.factory.Comparators;
import org.eclipse.collections.impl.block.factory.Functions;
import org.eclipse.collections.impl.block.factory.Functions2;
import org.eclipse.collections.impl.block.factory.HashingStrategies;
import org.eclipse.collections.impl.block.factory.IntegerPredicates;
import org.eclipse.collections.impl.block.factory.Predicates;
import org.eclipse.collections.impl.block.factory.Predicates2;
import org.eclipse.collections.impl.block.factory.PrimitiveFunctions;
import org.eclipse.collections.impl.block.function.AddFunction;
import org.eclipse.collections.impl.block.function.MaxSizeFunction;
import org.eclipse.collections.impl.block.function.MinSizeFunction;
import org.eclipse.collections.impl.block.function.NegativeIntervalFunction;
import org.eclipse.collections.impl.block.procedure.FastListCollectProcedure;
import org.eclipse.collections.impl.block.procedure.MapPutProcedure;
import org.eclipse.collections.impl.list.Interval;
import org.eclipse.collections.impl.list.mutable.AddToList;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.list.mutable.primitive.BooleanArrayList;
import org.eclipse.collections.impl.list.mutable.primitive.ByteArrayList;
import org.eclipse.collections.impl.list.mutable.primitive.CharArrayList;
import org.eclipse.collections.impl.list.mutable.primitive.DoubleArrayList;
import org.eclipse.collections.impl.list.mutable.primitive.FloatArrayList;
import org.eclipse.collections.impl.list.mutable.primitive.IntArrayList;
import org.eclipse.collections.impl.list.mutable.primitive.LongArrayList;
import org.eclipse.collections.impl.list.mutable.primitive.ShortArrayList;
import org.eclipse.collections.impl.map.mutable.UnifiedMap;
import org.eclipse.collections.impl.multimap.list.FastListMultimap;
import org.eclipse.collections.impl.test.Verify;
import org.eclipse.collections.impl.tuple.Tuples;
import org.junit.jupiter.api.Test;
import static org.eclipse.collections.impl.factory.Iterables.iList;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class ArrayIterateTest_Purified {

    private static final Integer[] INTEGER_ARRAY = { 5, 4, 3, 2, 1 };

    private Integer[] threeIntegerArray2() {
        return new Integer[] { 1, 2, 3 };
    }

    private BooleanArrayList getExpectedBooleanResults() {
        return BooleanArrayList.newListWith(false, false, true);
    }

    private ByteArrayList getExpectedByteResults() {
        return ByteArrayList.newListWith((byte) -1, (byte) 0, (byte) 42);
    }

    private CharArrayList getExpectedCharResults() {
        return CharArrayList.newListWith((char) -1, (char) 0, (char) 42);
    }

    private DoubleArrayList getExpectedDoubleResults() {
        return DoubleArrayList.newListWith(-1.0d, 0.0d, 42.0d);
    }

    private FloatArrayList getExpectedFloatResults() {
        return FloatArrayList.newListWith(-1.0f, 0.0f, 42.0f);
    }

    private IntArrayList getExpectedIntResults() {
        return IntArrayList.newListWith(-1, 0, 42);
    }

    private LongArrayList getExpectedLongResults() {
        return LongArrayList.newListWith(-1L, 0L, 42L);
    }

    private ShortArrayList getExpectedShortResults() {
        return ShortArrayList.newListWith((short) -1, (short) 0, (short) 42);
    }

    private Integer[] createIntegerArray(int size) {
        Integer[] array = new Integer[size];
        for (int i = 0; i < size; i++) {
            array[i] = 1;
        }
        return array;
    }

    @Test
    public void rejectWith_1() {
        Verify.assertEmpty(ArrayIterate.rejectWith(INTEGER_ARRAY, Predicates2.instanceOf(), Integer.class));
    }

    @Test
    public void rejectWith_2() {
        Verify.assertEmpty(ArrayIterate.rejectWith(INTEGER_ARRAY, Predicates2.instanceOf(), Integer.class, new ArrayList<>()));
    }

    @Test
    public void take_1() {
        assertEquals(ListIterate.take(Interval.zeroTo(0), 0), ArrayIterate.take(Interval.zeroTo(0).toArray(), 0));
    }

    @Test
    public void take_2() {
        assertEquals(ListIterate.take(Interval.zeroTo(5), 1), ArrayIterate.take(Interval.zeroTo(5).toArray(), 1));
    }

    @Test
    public void take_3() {
        assertEquals(ListIterate.take(Interval.zeroTo(5), 2), ArrayIterate.take(Interval.zeroTo(5).toArray(), 2));
    }

    @Test
    public void take_4() {
        assertEquals(ListIterate.take(Interval.zeroTo(0), 5), ArrayIterate.take(Interval.zeroTo(0).toArray(), 5));
    }

    @Test
    public void take_5() {
        assertEquals(ListIterate.take(Interval.zeroTo(5), 5), ArrayIterate.take(Interval.zeroTo(5).toArray(), 5));
    }

    @Test
    public void take_6() {
        assertEquals(ListIterate.take(Interval.zeroTo(10), 5), ArrayIterate.take(Interval.zeroTo(10).toArray(), 5));
    }

    @Test
    public void take_7() {
        assertEquals(ListIterate.take(Interval.zeroTo(10), 15), ArrayIterate.take(Interval.zeroTo(10).toArray(), 15));
    }

    @Test
    public void take_8() {
        assertEquals(ListIterate.take(Interval.zeroTo(10), Integer.MAX_VALUE), ArrayIterate.take(Interval.zeroTo(10).toArray(), Integer.MAX_VALUE));
    }

    @Test
    public void take_target_1() {
        assertEquals(ListIterate.take(Interval.zeroTo(0), 0, FastList.newListWith(-1)), ArrayIterate.take(Interval.zeroTo(0).toArray(), 0, FastList.newListWith(-1)));
    }

    @Test
    public void take_target_2() {
        assertEquals(ListIterate.take(Interval.zeroTo(0), 5, FastList.newListWith(-1)), ArrayIterate.take(Interval.zeroTo(0).toArray(), 5, FastList.newListWith(-1)));
    }

    @Test
    public void take_target_3() {
        assertEquals(ListIterate.take(Interval.zeroTo(5), 5, FastList.newListWith(-1)), ArrayIterate.take(Interval.zeroTo(5).toArray(), 5, FastList.newListWith(-1)));
    }

    @Test
    public void take_target_4() {
        assertEquals(ListIterate.take(Interval.zeroTo(10), 5, FastList.newListWith(-1)), ArrayIterate.take(Interval.zeroTo(10).toArray(), 5, FastList.newListWith(-1)));
    }

    @Test
    public void take_target_5() {
        assertEquals(ListIterate.take(Interval.zeroTo(10), 15, FastList.newListWith(-1)), ArrayIterate.take(Interval.zeroTo(10).toArray(), 15, FastList.newListWith(-1)));
    }

    @Test
    public void take_target_6() {
        assertEquals(ListIterate.take(Interval.zeroTo(10), Integer.MAX_VALUE, FastList.newListWith(-1)), ArrayIterate.take(Interval.zeroTo(10).toArray(), Integer.MAX_VALUE, FastList.newListWith(-1)));
    }

    @Test
    public void drop_1() {
        assertEquals(ListIterate.drop(Interval.zeroTo(5).toList(), 0), ArrayIterate.drop(Interval.zeroTo(5).toList().toArray(), 0));
    }

    @Test
    public void drop_2() {
        assertEquals(ListIterate.drop(Interval.zeroTo(5).toList(), 1), ArrayIterate.drop(Interval.zeroTo(5).toList().toArray(), 1));
    }

    @Test
    public void drop_3() {
        assertEquals(ListIterate.drop(Interval.zeroTo(0).toList(), 5), ArrayIterate.drop(Interval.zeroTo(0).toList().toArray(), 5));
    }

    @Test
    public void drop_4() {
        assertEquals(ListIterate.drop(Interval.zeroTo(5), 5), ArrayIterate.drop(Interval.zeroTo(5).toArray(), 5));
    }

    @Test
    public void drop_5() {
        assertEquals(ListIterate.drop(Interval.zeroTo(10), 5), ArrayIterate.drop(Interval.zeroTo(10).toArray(), 5));
    }

    @Test
    public void drop_6() {
        assertEquals(ListIterate.drop(Interval.zeroTo(10), 15), ArrayIterate.drop(Interval.zeroTo(10).toArray(), 15));
    }

    @Test
    public void drop_7() {
        assertEquals(ListIterate.drop(Interval.zeroTo(10), Integer.MAX_VALUE), ArrayIterate.drop(Interval.zeroTo(10).toArray(), Integer.MAX_VALUE));
    }

    @Test
    public void drop_target_1() {
        assertEquals(ListIterate.drop(Interval.zeroTo(0).toList(), 5, FastList.newListWith(-1)), ArrayIterate.drop(Interval.zeroTo(0).toList().toArray(), 5, FastList.newListWith(-1)));
    }

    @Test
    public void drop_target_2() {
        assertEquals(ListIterate.drop(Interval.zeroTo(5), 5, FastList.newListWith(-1)), ArrayIterate.drop(Interval.zeroTo(5).toArray(), 5, FastList.newListWith(-1)));
    }

    @Test
    public void drop_target_3() {
        assertEquals(ListIterate.drop(Interval.zeroTo(10), 5, FastList.newListWith(-1)), ArrayIterate.drop(Interval.zeroTo(10).toArray(), 5, FastList.newListWith(-1)));
    }

    @Test
    public void drop_target_4() {
        assertEquals(ListIterate.drop(Interval.zeroTo(10), 15, FastList.newListWith(-1)), ArrayIterate.drop(Interval.zeroTo(10).toArray(), 15, FastList.newListWith(-1)));
    }

    @Test
    public void drop_target_5() {
        assertEquals(ListIterate.drop(Interval.zeroTo(10), Integer.MAX_VALUE, FastList.newListWith(-1)), ArrayIterate.drop(Interval.zeroTo(10).toArray(), Integer.MAX_VALUE, FastList.newListWith(-1)));
    }
}
