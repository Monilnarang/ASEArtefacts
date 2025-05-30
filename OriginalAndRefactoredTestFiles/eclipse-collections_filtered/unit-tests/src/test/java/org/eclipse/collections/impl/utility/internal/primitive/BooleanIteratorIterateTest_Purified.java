package org.eclipse.collections.impl.utility.internal.primitive;

import org.eclipse.collections.api.BooleanIterable;
import org.eclipse.collections.impl.block.factory.primitive.BooleanPredicates;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.list.mutable.primitive.BooleanArrayList;
import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.Test;

public class BooleanIteratorIterateTest_Purified {

    private final BooleanIterable iterable = BooleanArrayList.newListWith(true, false, true);

    @Test
    public void select_target_1() {
        Verify.assertSize(1, BooleanIteratorIterate.select(this.iterable.booleanIterator(), BooleanPredicates.equal(false), new BooleanArrayList(2)));
    }

    @Test
    public void select_target_2() {
        Verify.assertSize(2, BooleanIteratorIterate.select(this.iterable.booleanIterator(), BooleanPredicates.equal(true), new BooleanArrayList(3)));
    }

    @Test
    public void reject_target_1() {
        Verify.assertSize(1, BooleanIteratorIterate.reject(this.iterable.booleanIterator(), BooleanPredicates.equal(true), new BooleanArrayList(1)));
    }

    @Test
    public void reject_target_2() {
        Verify.assertSize(2, BooleanIteratorIterate.reject(this.iterable.booleanIterator(), BooleanPredicates.equal(false), new BooleanArrayList(0)));
    }
}
