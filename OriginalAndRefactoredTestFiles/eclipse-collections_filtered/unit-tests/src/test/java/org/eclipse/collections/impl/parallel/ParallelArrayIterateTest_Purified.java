package org.eclipse.collections.impl.parallel;

import org.eclipse.collections.api.block.procedure.Procedure;
import org.eclipse.collections.impl.list.Interval;
import org.eclipse.collections.impl.math.IntegerSum;
import org.eclipse.collections.impl.math.LongSum;
import org.eclipse.collections.impl.math.Sum;
import org.eclipse.collections.impl.math.SumCombiner;
import org.eclipse.collections.impl.math.SumProcedure;
import org.eclipse.collections.impl.test.Verify;
import org.eclipse.collections.impl.utility.ArrayIterate;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ParallelArrayIterateTest_Purified {

    private Integer[] createIntegerArray(int size) {
        Integer[] array = new Integer[size];
        for (int i = 0; i < size; i++) {
            array[i] = 1;
        }
        return array;
    }

    private Sum parallelSum(Object[] array, Sum parallelSum) {
        ParallelArrayIterate.forEach(array, new SumProcedure<>(parallelSum), new SumCombiner<>(parallelSum));
        return parallelSum;
    }

    private void basicTestParallelSums(Object[] array, Sum parallelSum1, Sum parallelSum2) throws InterruptedException {
        Thread thread1 = new Thread(() -> this.parallelSum(array, parallelSum1));
        thread1.start();
        Thread thread2 = new Thread(() -> this.parallelSum(array, parallelSum2));
        thread2.start();
        thread1.join();
        thread2.join();
    }

    private void basicTestLinearSums(Object[] array, Sum linearSum1, Sum linearSum2) throws InterruptedException {
        Thread thread1 = new Thread(() -> this.linearSum(array, linearSum1));
        thread1.start();
        Thread thread2 = new Thread(() -> this.linearSum(array, linearSum2));
        thread2.start();
        thread1.join();
        thread2.join();
    }

    private void linearSum(Object[] array, Sum linearSum) {
        ArrayIterate.forEach(array, new SumProcedure<>(linearSum));
    }

    @Test
    public void parallelForEach_1() {
        Sum sum1 = new IntegerSum(0);
        ParallelArrayIterate.forEach(array1, new SumProcedure<>(sum1), new SumCombiner<>(sum1), 1, array1.length / 2);
        assertEquals(16, sum1.getValue());
    }

    @Test
    public void parallelForEach_2() {
        Sum sum2 = new IntegerSum(0);
        ParallelArrayIterate.forEach(array2, new SumProcedure<>(sum2), new SumCombiner<>(sum2));
        assertEquals(7, sum2.getValue());
    }

    @Test
    public void parallelForEach_3() {
        Sum sum3 = new IntegerSum(0);
        ParallelArrayIterate.forEach(array3, new SumProcedure<>(sum3), new SumCombiner<>(sum3), 1, array3.length / 2);
        assertEquals(15, sum3.getValue());
    }

    @Test
    public void parallelForEach_4() {
        Sum sum4 = new IntegerSum(0);
        ParallelArrayIterate.forEach(array4, new SumProcedure<>(sum4), new SumCombiner<>(sum4));
        assertEquals(35, sum4.getValue());
    }

    @Test
    public void parallelForEach_5() {
        Sum sum5 = new IntegerSum(0);
        ParallelArrayIterate.forEach(array5, new SumProcedure<>(sum5), new SumCombiner<>(sum5), 1, array5.length / 2);
        assertEquals(40, sum5.getValue());
    }
}
