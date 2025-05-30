package org.eclipse.collections.impl.block.function;

import org.eclipse.collections.impl.block.factory.PrimitiveFunctions;
import org.eclipse.collections.impl.set.mutable.UnifiedSet;
import org.eclipse.collections.impl.set.mutable.primitive.DoubleHashSet;
import org.eclipse.collections.impl.set.mutable.primitive.FloatHashSet;
import org.eclipse.collections.impl.set.mutable.primitive.IntHashSet;
import org.eclipse.collections.impl.set.mutable.primitive.LongHashSet;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PrimitiveFunctionsTest_Purified {

    @Test
    public void unboxNumberToInt_1() {
        assertEquals(IntHashSet.newSetWith(1, 2, 3), UnifiedSet.newSetWith(Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(3)).collectInt(PrimitiveFunctions.unboxNumberToInt()));
    }

    @Test
    public void unboxNumberToInt_2() {
        assertEquals(IntHashSet.newSetWith(1, 2, 3), UnifiedSet.newSetWith(1.1, 2.2, 3.3).collectInt(PrimitiveFunctions.unboxNumberToInt()));
    }
}
