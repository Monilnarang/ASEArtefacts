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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class BooleanIterableIterateTest_Parameterized {

    private final BooleanIterable iterable = BooleanArrayList.newListWith(true, false, true);

    @ParameterizedTest
    @MethodSource("Provider_select_target_1to2")
    public void select_target_1to2(int param1, int param2) {
        Verify.assertSize(param1, BooleanIterableIterate.select(this.iterable, BooleanPredicates.equal(param2), new BooleanArrayList(2)));
    }

    static public Stream<Arguments> Provider_select_target_1to2() {
        return Stream.of(arguments(2, 2), arguments(1, 3));
    }

    @ParameterizedTest
    @MethodSource("Provider_reject_target_1to2")
    public void reject_target_1to2(int param1, int param2) {
        Verify.assertSize(param1, BooleanIterableIterate.reject(this.iterable, BooleanPredicates.equal(param2), new BooleanArrayList(1)));
    }

    static public Stream<Arguments> Provider_reject_target_1to2() {
        return Stream.of(arguments(1, 1), arguments(2, 1));
    }
}
