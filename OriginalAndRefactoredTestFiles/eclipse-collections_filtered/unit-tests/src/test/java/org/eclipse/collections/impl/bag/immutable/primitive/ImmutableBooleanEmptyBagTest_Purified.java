package org.eclipse.collections.impl.bag.immutable.primitive;

import org.eclipse.collections.api.bag.primitive.ImmutableBooleanBag;
import org.eclipse.collections.api.set.primitive.ImmutableBooleanSet;
import org.eclipse.collections.impl.factory.primitive.BooleanBags;
import org.eclipse.collections.impl.factory.primitive.BooleanSets;
import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ImmutableBooleanEmptyBagTest_Purified extends AbstractImmutableBooleanBagTestCase {

    @Override
    protected final ImmutableBooleanBag classUnderTest() {
        return BooleanBags.immutable.of();
    }

    @Test
    public void occurrencesOf_1() {
        assertEquals(0, this.classUnderTest().occurrencesOf(true));
    }

    @Test
    public void occurrencesOf_2() {
        assertEquals(0, this.classUnderTest().occurrencesOf(false));
    }
}
