package org.eclipse.collections.impl.lazy;

import org.eclipse.collections.api.LazyIterable;
import org.eclipse.collections.api.block.procedure.Procedure2;
import org.eclipse.collections.api.block.procedure.primitive.ObjectIntProcedure;
import org.eclipse.collections.impl.block.factory.Predicates;
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

public class TakeWhileIterableTest_Purified extends AbstractLazyIterableTestCase {

    private TakeWhileIterable<Integer> takeWhileIterable;

    private TakeWhileIterable<Integer> emptyListTakeWhileIterable;

    private TakeWhileIterable<Integer> alwaysFalseTakeWhileIterable;

    private TakeWhileIterable<Integer> alwaysTrueTakeWhileIterable;

    @BeforeEach
    public void setUp() {
        this.takeWhileIterable = new TakeWhileIterable<>(Interval.oneTo(5), each -> each <= 2);
        this.emptyListTakeWhileIterable = new TakeWhileIterable<>(FastList.newList(), each -> each <= 2);
        this.alwaysFalseTakeWhileIterable = new TakeWhileIterable<>(Interval.oneTo(5), Predicates.alwaysFalse());
        this.alwaysTrueTakeWhileIterable = new TakeWhileIterable<>(Interval.oneTo(5), Predicates.alwaysTrue());
    }

    @Override
    protected <T> LazyIterable<T> newWith(T... elements) {
        return LazyIterate.takeWhile(FastList.newListWith(elements), Predicates.alwaysTrue());
    }

    @Test
    public void basic_1() {
        assertEquals(2, this.takeWhileIterable.size());
    }

    @Test
    public void basic_2() {
        assertEquals(FastList.newListWith(1, 2), this.takeWhileIterable.toList());
    }

    @Test
    public void basic_3() {
        assertEquals(0, this.emptyListTakeWhileIterable.size());
    }

    @Test
    public void basic_4() {
        assertEquals(0, this.alwaysFalseTakeWhileIterable.size());
    }

    @Test
    public void basic_5() {
        assertEquals(5, this.alwaysTrueTakeWhileIterable.size());
    }

    @Test
    public void forEach_1() {
        CountProcedure<Integer> cb1 = new CountProcedure<>();
        this.takeWhileIterable.forEach(cb1);
        assertEquals(2, cb1.getCount());
    }

    @Test
    public void forEach_2() {
        CountProcedure<Integer> cb2 = new CountProcedure<>();
        this.emptyListTakeWhileIterable.forEach(cb2);
        assertEquals(0, cb2.getCount());
    }

    @Test
    public void forEach_3() {
        CountProcedure<Integer> cb3 = new CountProcedure<>();
        this.alwaysFalseTakeWhileIterable.forEach(cb3);
        assertEquals(0, cb3.getCount());
    }

    @Test
    public void forEach_4() {
        CountProcedure<Integer> cb5 = new CountProcedure<>();
        this.alwaysTrueTakeWhileIterable.forEach(cb5);
        assertEquals(5, cb5.getCount());
    }
}
