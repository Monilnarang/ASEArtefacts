package org.eclipse.collections.impl.lazy.primitive;

import org.eclipse.collections.api.InternalIterable;
import org.eclipse.collections.api.LazyIterable;
import org.eclipse.collections.api.block.procedure.Procedure;
import org.eclipse.collections.impl.block.factory.Procedures;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.list.mutable.primitive.BooleanArrayList;
import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CollectBooleanToObjectIterableTest_Purified {

    private LazyIterable<Boolean> newPrimitiveWith(boolean... elements) {
        return new CollectBooleanToObjectIterable<>(BooleanArrayList.newListWith(elements), Boolean::valueOf);
    }

    @Test
    public void sizeEmptyNotEmpty_1() {
        Verify.assertIterableSize(2, this.newPrimitiveWith(true, false));
    }

    @Test
    public void sizeEmptyNotEmpty_2() {
        Verify.assertIterableEmpty(this.newPrimitiveWith());
    }

    @Test
    public void sizeEmptyNotEmpty_3() {
        assertTrue(this.newPrimitiveWith(true, false).notEmpty());
    }
}
