package org.eclipse.collections.impl.lazy.primitive;

import org.eclipse.collections.api.InternalIterable;
import org.eclipse.collections.api.LazyIterable;
import org.eclipse.collections.api.block.procedure.Procedure;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.impl.block.factory.Predicates;
import org.eclipse.collections.impl.block.factory.Predicates2;
import org.eclipse.collections.impl.block.factory.Procedures;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.list.mutable.primitive.BooleanArrayList;
import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FlatCollectBooleanToObjectIterableTest_Purified {

    private LazyIterable<Boolean> newPrimitiveWith(boolean... elements) {
        return new FlatCollectBooleanToObjectIterable<>(BooleanArrayList.newListWith(elements), Lists.mutable::with);
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

    @Test
    public void detect_1() {
        assertEquals(Boolean.TRUE, this.newPrimitiveWith(true, false).detect(Predicates.equal(Boolean.TRUE)));
    }

    @Test
    public void detect_2() {
        assertNull(this.newPrimitiveWith(true).detect(Predicates.equal(Boolean.FALSE)));
    }

    @Test
    public void detectOptional_1() {
        assertEquals(Boolean.TRUE, this.newPrimitiveWith(true, false).detectOptional(Predicates.equal(Boolean.TRUE)).get());
    }

    @Test
    public void detectOptional_2() {
        assertFalse(this.newPrimitiveWith(true).detectOptional(Predicates.equal(Boolean.FALSE)).isPresent());
    }

    @Test
    public void anySatisfy_1() {
        assertTrue(this.newPrimitiveWith(true).anySatisfy(Predicates.equal(Boolean.TRUE)));
    }

    @Test
    public void anySatisfy_2() {
        assertFalse(this.newPrimitiveWith(true).anySatisfy(Predicates.equal(Boolean.FALSE)));
    }

    @Test
    public void anySatisfyWith_1() {
        assertTrue(this.newPrimitiveWith(true).anySatisfyWith(Predicates2.equal(), Boolean.TRUE));
    }

    @Test
    public void anySatisfyWith_2() {
        assertFalse(this.newPrimitiveWith(true).anySatisfyWith(Predicates2.equal(), Boolean.FALSE));
    }

    @Test
    public void allSatisfy_1() {
        assertFalse(this.newPrimitiveWith(true, false).allSatisfy(Predicates.equal(Boolean.TRUE)));
    }

    @Test
    public void allSatisfy_2() {
        assertTrue(this.newPrimitiveWith(true).allSatisfy(Predicates.equal(Boolean.TRUE)));
    }

    @Test
    public void allSatisfyWith_1() {
        assertFalse(this.newPrimitiveWith(true, false).allSatisfyWith(Predicates2.equal(), Boolean.TRUE));
    }

    @Test
    public void allSatisfyWith_2() {
        assertTrue(this.newPrimitiveWith(true).allSatisfyWith(Predicates2.equal(), Boolean.TRUE));
    }

    @Test
    public void noneSatisfy_1() {
        assertFalse(this.newPrimitiveWith(true).noneSatisfy(Predicates.equal(Boolean.TRUE)));
    }

    @Test
    public void noneSatisfy_2() {
        assertTrue(this.newPrimitiveWith(false).noneSatisfy(Predicates.equal(Boolean.TRUE)));
    }

    @Test
    public void noneSatisfyWith_1() {
        assertFalse(this.newPrimitiveWith(true).noneSatisfyWith(Predicates2.equal(), Boolean.TRUE));
    }

    @Test
    public void noneSatisfyWith_2() {
        assertTrue(this.newPrimitiveWith(false).noneSatisfyWith(Predicates2.equal(), Boolean.TRUE));
    }
}
