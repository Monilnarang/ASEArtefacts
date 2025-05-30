package org.eclipse.collections.impl.bag.mutable;

import org.eclipse.collections.api.bag.MutableBag;
import org.eclipse.collections.api.factory.Sets;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.impl.collection.mutable.UnmodifiableMutableCollectionTestCase;
import org.eclipse.collections.impl.map.mutable.UnifiedMap;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class MultiReaderHashBagAsReadUntouchableTest_Parameterized extends UnmodifiableMutableCollectionTestCase<Integer> {

    @Override
    protected MutableBag<Integer> getCollection() {
        return MultiReaderHashBag.newBagWith(1, 1).asReadUntouchable();
    }

    @ParameterizedTest
    @MethodSource("Provider_occurrencesOf_1to2")
    public void occurrencesOf_1to2(int param1, int param2) {
        assertEquals(param1, this.getCollection().occurrencesOf(param2));
    }

    static public Stream<Arguments> Provider_occurrencesOf_1to2() {
        return Stream.of(arguments(2, 1), arguments(0, 0));
    }
}
