package org.eclipse.collections.impl.bag.mutable;

import org.eclipse.collections.api.bag.MutableBag;
import org.eclipse.collections.api.factory.Sets;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.impl.collection.mutable.UnmodifiableMutableCollectionTestCase;
import org.eclipse.collections.impl.map.mutable.UnifiedMap;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MultiReaderHashBagAsReadUntouchableTest_Purified extends UnmodifiableMutableCollectionTestCase<Integer> {

    @Override
    protected MutableBag<Integer> getCollection() {
        return MultiReaderHashBag.newBagWith(1, 1).asReadUntouchable();
    }

    @Test
    public void occurrencesOf_1() {
        assertEquals(2, this.getCollection().occurrencesOf(1));
    }

    @Test
    public void occurrencesOf_2() {
        assertEquals(0, this.getCollection().occurrencesOf(0));
    }
}
