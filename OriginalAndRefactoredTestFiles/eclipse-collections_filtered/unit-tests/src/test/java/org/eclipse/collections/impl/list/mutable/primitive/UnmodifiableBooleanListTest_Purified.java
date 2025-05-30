package org.eclipse.collections.impl.list.mutable.primitive;

import org.eclipse.collections.api.iterator.MutableBooleanIterator;
import org.eclipse.collections.api.list.primitive.MutableBooleanList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UnmodifiableBooleanListTest_Purified extends AbstractBooleanListTestCase {

    private final UnmodifiableBooleanList list = this.classUnderTest();

    @Override
    protected final UnmodifiableBooleanList classUnderTest() {
        return new UnmodifiableBooleanList(BooleanArrayList.newListWith(true, false, true));
    }

    @Override
    protected UnmodifiableBooleanList newWith(boolean... elements) {
        return new UnmodifiableBooleanList(BooleanArrayList.newListWith(elements));
    }

    @Override
    @Test
    public void asUnmodifiable_1() {
        assertSame(this.list, this.list.asUnmodifiable());
    }

    @Override
    @Test
    public void asUnmodifiable_2() {
        assertEquals(this.list, this.list.asUnmodifiable());
    }
}
