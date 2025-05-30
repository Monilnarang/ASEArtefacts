package org.eclipse.collections.impl.lazy;

import org.eclipse.collections.api.LazyIterable;
import org.eclipse.collections.api.block.procedure.Procedure2;
import org.eclipse.collections.api.block.procedure.primitive.ObjectIntProcedure;
import org.eclipse.collections.impl.list.Interval;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.math.IntegerSum;
import org.eclipse.collections.impl.math.Sum;
import org.eclipse.collections.impl.math.SumProcedure;
import org.eclipse.collections.impl.utility.LazyIterate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DropIterableTest_Purified extends AbstractLazyIterableTestCase {

    private DropIterable<Integer> dropIterable;

    private DropIterable<Integer> emptyListDropIterable;

    private DropIterable<Integer> zeroCountDropIterable;

    private DropIterable<Integer> nearCountDropIterable;

    private DropIterable<Integer> sameCountDropIterable;

    private DropIterable<Integer> higherCountDropIterable;

    @BeforeEach
    public void setUp() {
        this.dropIterable = new DropIterable<>(Interval.oneTo(5), 2);
        this.emptyListDropIterable = new DropIterable<>(FastList.newList(), 2);
        this.zeroCountDropIterable = new DropIterable<>(Interval.oneTo(5), 0);
        this.nearCountDropIterable = new DropIterable<>(Interval.oneTo(5), 4);
        this.sameCountDropIterable = new DropIterable<>(Interval.oneTo(5), 5);
        this.higherCountDropIterable = new DropIterable<>(Interval.oneTo(5), 6);
    }

    @Override
    protected <T> LazyIterable<T> newWith(T... elements) {
        return LazyIterate.drop(FastList.newListWith(elements), 0);
    }

    @Test
    public void basic_1() {
        assertEquals(3, this.dropIterable.size());
    }

    @Test
    public void basic_2() {
        assertEquals(FastList.newListWith(3, 4, 5), this.dropIterable.toList());
    }

    @Test
    public void basic_3() {
        assertEquals(0, this.emptyListDropIterable.size());
    }

    @Test
    public void basic_4() {
        assertEquals(5, this.zeroCountDropIterable.size());
    }

    @Test
    public void basic_5() {
        assertEquals(1, this.nearCountDropIterable.size());
    }

    @Test
    public void basic_6() {
        assertEquals(0, this.sameCountDropIterable.size());
    }

    @Test
    public void basic_7() {
        assertEquals(0, this.higherCountDropIterable.size());
    }

    @Test
    public void forEach_1() {
        Sum sum1 = new IntegerSum(0);
        this.dropIterable.forEach(new SumProcedure<>(sum1));
        assertEquals(12, sum1.getValue().intValue());
    }

    @Test
    public void forEach_2() {
        Sum sum2 = new IntegerSum(0);
        this.emptyListDropIterable.forEach(new SumProcedure<>(sum2));
        assertEquals(0, sum2.getValue().intValue());
    }

    @Test
    public void forEach_3() {
        Sum sum3 = new IntegerSum(0);
        this.zeroCountDropIterable.forEach(new SumProcedure<>(sum3));
        assertEquals(15, sum3.getValue().intValue());
    }

    @Test
    public void forEach_4() {
        Sum sum5 = new IntegerSum(0);
        this.nearCountDropIterable.forEach(new SumProcedure<>(sum5));
        assertEquals(5, sum5.getValue().intValue());
    }

    @Test
    public void forEach_5() {
        Sum sum6 = new IntegerSum(0);
        this.sameCountDropIterable.forEach(new SumProcedure<>(sum6));
        assertEquals(0, sum6.getValue().intValue());
    }

    @Test
    public void forEach_6() {
        Sum sum7 = new IntegerSum(0);
        this.higherCountDropIterable.forEach(new SumProcedure<>(sum7));
        assertEquals(0, sum7.getValue().intValue());
    }
}
