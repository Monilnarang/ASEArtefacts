package org.eclipse.collections.impl.lazy;

import org.eclipse.collections.api.LazyIterable;
import org.eclipse.collections.api.block.procedure.Procedure2;
import org.eclipse.collections.api.block.procedure.primitive.ObjectIntProcedure;
import org.eclipse.collections.impl.block.procedure.CountProcedure;
import org.eclipse.collections.impl.list.Interval;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.math.IntegerSum;
import org.eclipse.collections.impl.math.Sum;
import org.eclipse.collections.impl.test.Verify;
import org.eclipse.collections.impl.utility.LazyIterate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TakeIterableTest_Purified extends AbstractLazyIterableTestCase {

    private TakeIterable<Integer> takeIterable;

    private TakeIterable<Integer> emptyListTakeIterable;

    private TakeIterable<Integer> zeroCountTakeIterable;

    private TakeIterable<Integer> sameCountTakeIterable;

    private TakeIterable<Integer> higherCountTakeIterable;

    @BeforeEach
    public void setUp() {
        this.takeIterable = new TakeIterable<>(Interval.oneTo(5), 2);
        this.emptyListTakeIterable = new TakeIterable<>(FastList.newList(), 2);
        this.zeroCountTakeIterable = new TakeIterable<>(Interval.oneTo(5), 0);
        this.sameCountTakeIterable = new TakeIterable<>(Interval.oneTo(5), 5);
        this.higherCountTakeIterable = new TakeIterable<>(Interval.oneTo(5), 10);
    }

    @Override
    protected <T> LazyIterable<T> newWith(T... elements) {
        return LazyIterate.take(FastList.newListWith(elements), elements.length);
    }

    @Test
    public void basic_1() {
        assertEquals(2, this.takeIterable.size());
    }

    @Test
    public void basic_2() {
        assertEquals(FastList.newListWith(1, 2), this.takeIterable.toList());
    }

    @Test
    public void basic_3() {
        assertEquals(0, this.emptyListTakeIterable.size());
    }

    @Test
    public void basic_4() {
        assertEquals(0, this.zeroCountTakeIterable.size());
    }

    @Test
    public void basic_5() {
        assertEquals(5, this.higherCountTakeIterable.size());
    }

    @Test
    public void basic_6() {
        assertEquals(5, this.sameCountTakeIterable.size());
    }

    @Test
    public void forEach_1() {
        CountProcedure<Integer> cb1 = new CountProcedure<>();
        this.takeIterable.forEach(cb1);
        assertEquals(2, cb1.getCount());
    }

    @Test
    public void forEach_2() {
        CountProcedure<Integer> cb2 = new CountProcedure<>();
        this.emptyListTakeIterable.forEach(cb2);
        assertEquals(0, cb2.getCount());
    }

    @Test
    public void forEach_3() {
        CountProcedure<Integer> cb3 = new CountProcedure<>();
        this.zeroCountTakeIterable.forEach(cb3);
        assertEquals(0, cb3.getCount());
    }

    @Test
    public void forEach_4() {
        CountProcedure<Integer> cb5 = new CountProcedure<>();
        this.sameCountTakeIterable.forEach(cb5);
        assertEquals(5, cb5.getCount());
    }

    @Test
    public void forEach_5() {
        CountProcedure<Integer> cb6 = new CountProcedure<>();
        this.higherCountTakeIterable.forEach(cb6);
        assertEquals(5, cb6.getCount());
    }
}
