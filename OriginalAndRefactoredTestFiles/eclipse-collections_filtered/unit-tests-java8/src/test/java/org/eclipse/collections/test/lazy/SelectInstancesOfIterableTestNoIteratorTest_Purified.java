package org.eclipse.collections.test.lazy;

import java.util.Objects;
import java.util.Optional;
import org.eclipse.collections.api.LazyIterable;
import org.eclipse.collections.impl.block.factory.Comparators;
import org.eclipse.collections.impl.lazy.SelectInstancesOfIterable;
import org.eclipse.collections.test.LazyNoIteratorTestCase;
import org.eclipse.collections.test.NoDetectOptionalNullTestCase;
import org.eclipse.collections.test.list.mutable.FastListNoIterator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class SelectInstancesOfIterableTestNoIteratorTest_Purified implements LazyNoIteratorTestCase, NoDetectOptionalNullTestCase {

    @Override
    public <T> LazyIterable<T> newWith(T... elements) {
        return (LazyIterable<T>) new SelectInstancesOfIterable<>(new FastListNoIterator<T>().with(elements), Object.class);
    }

    @Override
    @Test
    public void RichIterable_minOptional_maxOptional_1() {
        assertEquals(Optional.of(-1), this.newWith(-1, 0, 1).minOptional());
    }

    @Override
    @Test
    public void RichIterable_minOptional_maxOptional_2() {
        assertEquals(Optional.of(-1), this.newWith(1, 0, -1).minOptional());
    }

    @Override
    @Test
    public void RichIterable_minOptional_maxOptional_3() {
        assertSame(Optional.empty(), this.newWith().minOptional());
    }

    @Override
    @Test
    public void RichIterable_minOptional_maxOptional_4() {
        assertSame(Optional.empty(), this.newWith(new Object[] { null }).minOptional());
    }

    @Override
    @Test
    public void RichIterable_minOptional_maxOptional_5() {
        assertEquals(Optional.of(1), this.newWith(-1, 0, 1).maxOptional());
    }

    @Override
    @Test
    public void RichIterable_minOptional_maxOptional_6() {
        assertEquals(Optional.of(1), this.newWith(1, 0, -1).maxOptional());
    }

    @Override
    @Test
    public void RichIterable_minOptional_maxOptional_7() {
        assertSame(Optional.empty(), this.newWith().maxOptional());
    }

    @Override
    @Test
    public void RichIterable_minOptional_maxOptional_8() {
        assertSame(Optional.empty(), this.newWith(new Object[] { null }).maxOptional());
    }

    @Override
    @Test
    public void RichIterable_minOptional_maxOptional_9() {
        assertEquals(Optional.of(1), this.newWith(-1, 0, 1).minOptional(Comparators.reverseNaturalOrder()));
    }

    @Override
    @Test
    public void RichIterable_minOptional_maxOptional_10() {
        assertEquals(Optional.of(1), this.newWith(1, 0, -1).minOptional(Comparators.reverseNaturalOrder()));
    }

    @Override
    @Test
    public void RichIterable_minOptional_maxOptional_11() {
        assertSame(Optional.empty(), this.newWith().minOptional(Comparators.reverseNaturalOrder()));
    }

    @Override
    @Test
    public void RichIterable_minOptional_maxOptional_12() {
        assertSame(Optional.empty(), this.newWith(new Object[] { null }).minOptional(Comparators.reverseNaturalOrder()));
    }

    @Override
    @Test
    public void RichIterable_minOptional_maxOptional_13() {
        assertEquals(Optional.of(-1), this.newWith(-1, 0, 1).maxOptional(Comparators.reverseNaturalOrder()));
    }

    @Override
    @Test
    public void RichIterable_minOptional_maxOptional_14() {
        assertEquals(Optional.of(-1), this.newWith(1, 0, -1).maxOptional(Comparators.reverseNaturalOrder()));
    }

    @Override
    @Test
    public void RichIterable_minOptional_maxOptional_15() {
        assertSame(Optional.empty(), this.newWith().maxOptional(Comparators.reverseNaturalOrder()));
    }

    @Override
    @Test
    public void RichIterable_minOptional_maxOptional_16() {
        assertSame(Optional.empty(), this.newWith(new Object[] { null }).maxOptional(Comparators.reverseNaturalOrder()));
    }
}
