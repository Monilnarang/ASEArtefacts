package org.eclipse.collections.api.factory.stack;

import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.factory.Stacks;
import org.eclipse.collections.api.stack.MutableStack;
import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MutableStackFactoryTest_Purified {

    private final MutableStackFactory mutableStackFactory = Stacks.mutable;

    @Test
    public void of_1_testMerged_1() {
        MutableStack<Integer> stack = this.mutableStackFactory.of();
        assertNotNull(stack);
        Verify.assertEmpty(stack);
    }

    @Test
    public void of_3_testMerged_2() {
        MutableStack<Integer> intStack = this.mutableStackFactory.of(1, 2, 3);
        Verify.assertSize(3, intStack);
        Verify.assertContainsAll(intStack, 1, 2, 3);
        assertEquals(3, (long) intStack.pop());
        assertEquals(2, (long) intStack.pop());
        assertEquals(1, (long) intStack.pop());
    }
}
