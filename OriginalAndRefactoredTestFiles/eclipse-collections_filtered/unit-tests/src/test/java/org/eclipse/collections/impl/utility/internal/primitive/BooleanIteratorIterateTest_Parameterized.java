package org.eclipse.collections.impl.utility.internal.primitive;

import org.eclipse.collections.api.BooleanIterable;
import org.eclipse.collections.impl.block.factory.primitive.BooleanPredicates;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.list.mutable.primitive.BooleanArrayList;
import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class BooleanIteratorIterateTest_Parameterized {

    private final BooleanIterable iterable = BooleanArrayList.newListWith(true, false, true);

    @ParameterizedTest
    @MethodSource("Provider_select_target_1to2")
    public void select_target_1to2(int param1, int param2) {
        Verify.assertSize(param1, BooleanIteratorIterate.select(this.iterable.booleanIterator(), BooleanPredicates.equal(param2), new BooleanArrayList(2)));
    }

    static public Stream<Arguments> Provider_select_target_1to2() {
        return Stream.of(arguments(1, 2), arguments(2, 3));
    }

    @ParameterizedTest
    @MethodSource("Provider_reject_target_1to2")
    public void reject_target_1to2(int param1, int param2) {
        Verify.assertSize(param1, BooleanIteratorIterate.reject(this.iterable.booleanIterator(), BooleanPredicates.equal(param2), new BooleanArrayList(1)));
    }

    static public Stream<Arguments> Provider_reject_target_1to2() {
        return Stream.of(arguments(1, 1), arguments(2, 0));
    }
}
