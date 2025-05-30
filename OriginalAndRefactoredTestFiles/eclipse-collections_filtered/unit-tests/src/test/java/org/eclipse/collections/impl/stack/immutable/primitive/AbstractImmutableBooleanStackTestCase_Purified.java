package org.eclipse.collections.impl.stack.immutable.primitive;

import org.eclipse.collections.api.BooleanIterable;
import org.eclipse.collections.api.stack.primitive.ImmutableBooleanStack;
import org.eclipse.collections.api.stack.primitive.MutableBooleanStack;
import org.eclipse.collections.impl.factory.primitive.BooleanStacks;
import org.eclipse.collections.impl.list.mutable.primitive.BooleanArrayList;
import org.eclipse.collections.impl.stack.mutable.primitive.BooleanArrayStack;
import org.eclipse.collections.impl.stack.primitive.AbstractBooleanStackTestCase;
import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class AbstractImmutableBooleanStackTestCase_Purified extends AbstractBooleanStackTestCase {

    @Override
    protected abstract ImmutableBooleanStack classUnderTest();

    @Override
    protected ImmutableBooleanStack newWith(boolean... elements) {
        return BooleanStacks.immutable.of(elements);
    }

    @Override
    protected MutableBooleanStack newMutableCollectionWith(boolean... elements) {
        return BooleanArrayStack.newStackWith(elements);
    }

    @Override
    protected ImmutableBooleanStack newWithTopToBottom(boolean... elements) {
        return ImmutableBooleanArrayStack.newStackFromTopToBottom(elements);
    }

    protected ImmutableBooleanStack newWithIterableTopToBottom(BooleanIterable iterable) {
        return ImmutableBooleanArrayStack.newStackFromTopToBottom(iterable);
    }

    protected ImmutableBooleanStack newWithIterable(BooleanIterable iterable) {
        return ImmutableBooleanArrayStack.newStack(iterable);
    }

    @Override
    @Test
    public void makeString_1() {
        assertEquals(this.createExpectedString("", ", ", ""), this.classUnderTest().makeString());
    }

    @Override
    @Test
    public void makeString_2() {
        assertEquals(this.createExpectedString("", "|", ""), this.classUnderTest().makeString("|"));
    }

    @Override
    @Test
    public void makeString_3() {
        assertEquals(this.createExpectedString("{", "|", "}"), this.classUnderTest().makeString("{", "|", "}"));
    }

    @Override
    @Test
    public void appendString_1() {
        StringBuilder appendable1 = new StringBuilder();
        this.classUnderTest().appendString(appendable1);
        assertEquals(this.createExpectedString("", ", ", ""), appendable1.toString());
    }

    @Override
    @Test
    public void appendString_2() {
        StringBuilder appendable2 = new StringBuilder();
        this.classUnderTest().appendString(appendable2, "|");
        assertEquals(this.createExpectedString("", "|", ""), appendable2.toString());
    }

    @Override
    @Test
    public void appendString_3() {
        StringBuilder appendable3 = new StringBuilder();
        this.classUnderTest().appendString(appendable3, "{", "|", "}");
        assertEquals(this.createExpectedString("{", "|", "}"), appendable3.toString());
    }
}
