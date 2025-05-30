package org.eclipse.collections.impl.lazy;

import org.eclipse.collections.api.LazyIterable;
import org.eclipse.collections.api.block.procedure.Procedure2;
import org.eclipse.collections.api.block.procedure.primitive.ObjectIntProcedure;
import org.eclipse.collections.impl.block.factory.Predicates;
import org.eclipse.collections.impl.list.Interval;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.math.IntegerSum;
import org.eclipse.collections.impl.math.Sum;
import org.eclipse.collections.impl.math.SumProcedure;
import org.eclipse.collections.impl.utility.LazyIterate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DropWhileIterableTest_Purified extends AbstractLazyIterableTestCase {

    private DropWhileIterable<Integer> dropWhileIterable;

    private DropWhileIterable<Integer> emptyListDropWhileIterable;

    private DropWhileIterable<Integer> alwaysFalseDropWhileIterable;

    private DropWhileIterable<Integer> mostlyFalseDropWhileIterable;

    private DropWhileIterable<Integer> alwaysTrueDropWhileIterable;

    @BeforeEach
    public void setUp() {
        this.dropWhileIterable = new DropWhileIterable<>(Interval.oneTo(5), each -> each <= 2);
        this.emptyListDropWhileIterable = new DropWhileIterable<>(FastList.newList(), each -> each <= 2);
        this.alwaysFalseDropWhileIterable = new DropWhileIterable<>(Interval.oneTo(5), Predicates.alwaysFalse());
        this.mostlyFalseDropWhileIterable = new DropWhileIterable<>(Interval.oneTo(5), each -> each <= 4);
        this.alwaysTrueDropWhileIterable = new DropWhileIterable<>(Interval.oneTo(5), Predicates.alwaysTrue());
    }

    @Override
    protected <T> LazyIterable<T> newWith(T... elements) {
        return LazyIterate.dropWhile(FastList.newListWith(elements), Predicates.alwaysFalse());
    }

    @Test
    public void basic_1() {
        assertEquals(3, this.dropWhileIterable.size());
    }

    @Test
    public void basic_2() {
        assertEquals(FastList.newListWith(3, 4, 5), this.dropWhileIterable.toList());
    }

    @Test
    public void basic_3() {
        assertEquals(0, this.emptyListDropWhileIterable.size());
    }

    @Test
    public void basic_4() {
        assertEquals(5, this.alwaysFalseDropWhileIterable.size());
    }

    @Test
    public void basic_5() {
        assertEquals(1, this.mostlyFalseDropWhileIterable.size());
    }

    @Test
    public void basic_6() {
        assertEquals(0, this.alwaysTrueDropWhileIterable.size());
    }

    @Test
    public void forEach_1() {
        Sum sum1 = new IntegerSum(0);
        this.dropWhileIterable.forEach(new SumProcedure<>(sum1));
        assertEquals(12, sum1.getValue().intValue());
    }

    @Test
    public void forEach_2() {
        Sum sum2 = new IntegerSum(0);
        this.emptyListDropWhileIterable.forEach(new SumProcedure<>(sum2));
        assertEquals(0, sum2.getValue().intValue());
    }

    @Test
    public void forEach_3() {
        Sum sum3 = new IntegerSum(0);
        this.alwaysFalseDropWhileIterable.forEach(new SumProcedure<>(sum3));
        assertEquals(15, sum3.getValue().intValue());
    }

    @Test
    public void forEach_4() {
        Sum sum5 = new IntegerSum(0);
        this.mostlyFalseDropWhileIterable.forEach(new SumProcedure<>(sum5));
        assertEquals(5, sum5.getValue().intValue());
    }

    @Test
    public void forEach_5() {
        Sum sum6 = new IntegerSum(0);
        this.alwaysTrueDropWhileIterable.forEach(new SumProcedure<>(sum6));
        assertEquals(0, sum6.getValue().intValue());
    }
}
