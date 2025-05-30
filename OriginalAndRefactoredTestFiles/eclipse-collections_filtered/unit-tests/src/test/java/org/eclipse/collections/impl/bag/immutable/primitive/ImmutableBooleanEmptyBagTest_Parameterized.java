package org.eclipse.collections.impl.bag.immutable.primitive;

import org.eclipse.collections.api.bag.primitive.ImmutableBooleanBag;
import org.eclipse.collections.api.set.primitive.ImmutableBooleanSet;
import org.eclipse.collections.impl.factory.primitive.BooleanBags;
import org.eclipse.collections.impl.factory.primitive.BooleanSets;
import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class ImmutableBooleanEmptyBagTest_Parameterized extends AbstractImmutableBooleanBagTestCase {

    @Override
    protected final ImmutableBooleanBag classUnderTest() {
        return BooleanBags.immutable.of();
    }

    @ParameterizedTest
    @MethodSource("Provider_occurrencesOf_1to2")
    public void occurrencesOf_1to2(int param1) {
        assertEquals(param1, this.classUnderTest().occurrencesOf(true));
    }

    static public Stream<Arguments> Provider_occurrencesOf_1to2() {
        return Stream.of(arguments(0), arguments(0));
    }
}
