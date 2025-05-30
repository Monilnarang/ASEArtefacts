package org.eclipse.collections.impl.stack.immutable;

import java.util.EmptyStackException;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ListIterable;
import org.eclipse.collections.api.stack.ImmutableStack;
import org.eclipse.collections.api.tuple.Pair;
import org.eclipse.collections.impl.factory.Stacks;
import org.eclipse.collections.impl.stack.mutable.ArrayStack;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ImmutableArrayStackTest_Purified extends ImmutableStackTestCase {

    @Override
    protected <T> ImmutableStack<T> newStackWith(T... elements) {
        return Stacks.immutable.of(elements);
    }

    @Override
    protected <T> ImmutableStack<T> newStackFromTopToBottom(T... elements) {
        return Stacks.immutable.ofReversed(elements);
    }

    @Override
    protected <T> ImmutableStack<T> newStackFromTopToBottom(Iterable<T> elements) {
        return Stacks.immutable.ofAllReversed(elements);
    }

    @Override
    protected <T> ImmutableStack<T> newStack(Iterable<T> elements) {
        return Stacks.immutable.ofAll(elements);
    }

    @Override
    @Test
    public void testEquals_1() {
        assertEquals(ImmutableArrayStack.newStack(), ArrayStack.newStackWith());
    }

    @Override
    @Test
    public void testEquals_2() {
        assertNotEquals(this.newStackWith(4, 5, 6), ArrayStack.newStackWith(1, 2, 3));
    }

    @Test
    public void push_1_testMerged_1() {
        ImmutableStack<Integer> stack = this.newStackWith(1, 2, 3);
        ImmutableStack<Integer> modifiedStack = stack.push(4);
        assertEquals(this.newStackWith(1, 2, 3, 4), modifiedStack);
        assertNotSame(modifiedStack, stack);
        assertEquals(this.newStackWith(1, 2, 3), stack);
    }

    @Test
    public void push_5_testMerged_2() {
        ImmutableStack<Integer> stack1 = this.newStackWith();
        ImmutableStack<Integer> modifiedStack1 = stack1.push(1);
        assertEquals(this.newStackWith(1), modifiedStack1);
        assertNotSame(modifiedStack1, stack1);
        assertEquals(this.newStackWith(), stack1);
    }
}
