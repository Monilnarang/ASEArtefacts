package org.eclipse.collections.impl.collection.mutable.primitive;

import java.util.EmptyStackException;
import org.eclipse.collections.api.BooleanIterable;
import org.eclipse.collections.api.stack.primitive.MutableBooleanStack;
import org.eclipse.collections.impl.list.mutable.primitive.BooleanArrayList;
import org.eclipse.collections.impl.stack.mutable.primitive.SynchronizedBooleanStack;
import org.eclipse.collections.impl.stack.mutable.primitive.UnmodifiableBooleanStack;
import org.eclipse.collections.impl.stack.primitive.AbstractBooleanStackTestCase;
import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class AbstractMutableBooleanStackTestCase_Purified extends AbstractBooleanStackTestCase {

    @Override
    protected abstract MutableBooleanStack classUnderTest();

    @Override
    protected abstract MutableBooleanStack newWith(boolean... elements);

    @Override
    protected abstract MutableBooleanStack newMutableCollectionWith(boolean... elements);

    @Override
    protected abstract MutableBooleanStack newWithTopToBottom(boolean... elements);

    protected abstract MutableBooleanStack newWithIterableTopToBottom(BooleanIterable iterable);

    protected abstract MutableBooleanStack newWithIterable(BooleanIterable iterable);

    @Override
    public void peekAtIndex() {
        super.peekAtIndex();
        MutableBooleanStack stack = this.classUnderTest();
        stack.pop(2);
        assertEquals((this.classUnderTest().size() & 1) != 0, stack.peekAt(0));
    }

    @Test
    public void clear_1() {
        MutableBooleanStack stack = this.classUnderTest();
        stack.clear();
        Verify.assertSize(0, stack);
    }

    @Test
    public void clear_2_testMerged_2() {
        MutableBooleanStack stack1 = this.newWith();
        Verify.assertSize(0, stack1);
    }

    @Test
    public void asSynchronized_1() {
        Verify.assertInstanceOf(SynchronizedBooleanStack.class, this.classUnderTest().asSynchronized());
    }

    @Test
    public void asSynchronized_2() {
        assertEquals(this.classUnderTest(), this.classUnderTest().asSynchronized());
    }

    @Test
    public void asUnmodifiable_1() {
        Verify.assertInstanceOf(UnmodifiableBooleanStack.class, this.classUnderTest().asUnmodifiable());
    }

    @Test
    public void asUnmodifiable_2() {
        assertEquals(this.classUnderTest(), this.classUnderTest().asUnmodifiable());
    }
}
