package org.eclipse.collections.impl.stack.mutable;

import org.eclipse.collections.api.stack.MutableStack;
import org.eclipse.collections.api.stack.StackIterable;
import org.eclipse.collections.impl.block.factory.Predicates;
import org.eclipse.collections.impl.block.factory.StringPredicates;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.stack.StackIterableTestCase;
import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UnmodifiableStackTest_Purified extends StackIterableTestCase {

    private MutableStack<Integer> mutableStack;

    private MutableStack<Integer> unmodifiableStack;

    private MutableStack<String> unmodifiableStackString;

    @BeforeEach
    public void setUp() {
        this.mutableStack = ArrayStack.newStackFromTopToBottom(1, 2, 3);
        this.unmodifiableStack = new UnmodifiableStack<>(this.mutableStack);
        this.unmodifiableStackString = new UnmodifiableStack<>(ArrayStack.newStackFromTopToBottom("1", "2", "3"));
    }

    @Override
    protected <T> MutableStack<T> newStackWith(T... elements) {
        return ArrayStack.newStackWith(elements).asUnmodifiable();
    }

    @Override
    protected <T> MutableStack<T> newStackFromTopToBottom(T... elements) {
        return ArrayStack.newStackFromTopToBottom(elements).asUnmodifiable();
    }

    @Override
    protected <T> StackIterable<T> newStackFromTopToBottom(Iterable<T> elements) {
        return ArrayStack.newStackFromTopToBottom(elements).asUnmodifiable();
    }

    @Override
    protected <T> StackIterable<T> newStack(Iterable<T> elements) {
        return ArrayStack.newStack(elements).asUnmodifiable();
    }

    @Test
    public void testReject_1() {
        assertEquals(ArrayStack.newStackFromTopToBottom("2", "3"), this.unmodifiableStackString.reject(StringPredicates.contains("1")));
    }

    @Test
    public void testReject_2() {
        assertEquals(FastList.newListWith("2", "3"), this.unmodifiableStackString.reject(StringPredicates.contains("1"), FastList.newList()));
    }
}
