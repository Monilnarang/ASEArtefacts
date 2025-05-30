package org.eclipse.collections.impl.utility.internal.primitive;

import org.eclipse.collections.api.BooleanIterable;
import org.eclipse.collections.impl.block.factory.primitive.BooleanPredicates;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.list.mutable.primitive.BooleanArrayList;
import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BooleanIterableIterateTest_Purified {

    private final BooleanIterable iterable = BooleanArrayList.newListWith(true, false, true);

    @Test
    public void select_target_1() {
        Verify.assertSize(2, BooleanIterableIterate.select(this.iterable, BooleanPredicates.equal(true), new BooleanArrayList(2)));
    }

    @Test
    public void select_target_2() {
        Verify.assertSize(1, BooleanIterableIterate.select(this.iterable, BooleanPredicates.equal(false), new BooleanArrayList(3)));
    }

    @Test
    public void reject_target_1() {
        Verify.assertSize(1, BooleanIterableIterate.reject(this.iterable, BooleanPredicates.equal(true), new BooleanArrayList(1)));
    }

    @Test
    public void reject_target_2() {
        Verify.assertSize(2, BooleanIterableIterate.reject(this.iterable, BooleanPredicates.equal(false), new BooleanArrayList(1)));
    }
}
