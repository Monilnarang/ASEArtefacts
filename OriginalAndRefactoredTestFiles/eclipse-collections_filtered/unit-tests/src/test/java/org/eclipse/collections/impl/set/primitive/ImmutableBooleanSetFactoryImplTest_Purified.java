package org.eclipse.collections.impl.set.primitive;

import org.eclipse.collections.api.set.primitive.ImmutableBooleanSet;
import org.eclipse.collections.impl.factory.primitive.BooleanSets;
import org.eclipse.collections.impl.list.mutable.primitive.BooleanArrayList;
import org.eclipse.collections.impl.set.mutable.primitive.BooleanHashSet;
import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ImmutableBooleanSetFactoryImplTest_Purified {

    @Test
    public void of_1() {
        Verify.assertEmpty(BooleanSets.immutable.of());
    }

    @Test
    public void of_2() {
        assertEquals(BooleanHashSet.newSetWith(true).toImmutable(), BooleanSets.immutable.of(true));
    }
}
