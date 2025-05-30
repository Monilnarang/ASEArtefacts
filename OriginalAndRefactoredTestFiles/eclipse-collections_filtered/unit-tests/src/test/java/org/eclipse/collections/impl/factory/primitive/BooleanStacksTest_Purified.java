package org.eclipse.collections.impl.factory.primitive;

import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.factory.stack.primitive.ImmutableBooleanStackFactory;
import org.eclipse.collections.api.stack.primitive.ImmutableBooleanStack;
import org.eclipse.collections.api.stack.primitive.MutableBooleanStack;
import org.eclipse.collections.impl.stack.mutable.primitive.BooleanArrayStack;
import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BooleanStacksTest_Purified {

    @Test
    public void empty_1() {
        assertTrue(BooleanStacks.immutable.of().isEmpty());
    }

    @Test
    public void empty_2() {
        assertTrue(BooleanStacks.mutable.of().isEmpty());
    }

    @Test
    public void ofAllBooleanIterable_1() {
        assertEquals(new BooleanArrayStack(), BooleanStacks.immutable.ofAll(BooleanLists.mutable.empty()));
    }

    @Test
    public void ofAllBooleanIterable_2() {
        assertEquals(BooleanArrayStack.newStackWith(true), BooleanStacks.immutable.ofAll(BooleanLists.mutable.with(true)));
    }

    @Test
    public void ofAllBooleanIterable_3() {
        assertEquals(BooleanArrayStack.newStackWith(true, false), BooleanStacks.immutable.ofAll(BooleanLists.mutable.with(true, false)));
    }

    @Test
    public void ofAllBooleanIterable_4() {
        assertEquals(BooleanArrayStack.newStackWith(true, false, false, true), BooleanStacks.immutable.ofAll(BooleanLists.mutable.with(true, false, false, true)));
    }

    @Test
    public void ofAllBooleanIterable_5() {
        assertEquals(new BooleanArrayStack(), BooleanStacks.mutable.ofAll(BooleanLists.mutable.empty()));
    }

    @Test
    public void ofAllBooleanIterable_6() {
        assertEquals(BooleanArrayStack.newStackWith(true), BooleanStacks.mutable.ofAll(BooleanLists.mutable.with(true)));
    }

    @Test
    public void ofAllBooleanIterable_7() {
        assertEquals(BooleanArrayStack.newStackWith(true, false), BooleanStacks.mutable.ofAll(BooleanLists.mutable.with(true, false)));
    }

    @Test
    public void ofAllBooleanIterable_8() {
        assertEquals(BooleanArrayStack.newStackWith(true, false, false, true), BooleanStacks.mutable.ofAll(BooleanLists.mutable.with(true, false, false, true)));
    }

    @Test
    public void ofAllIterable_1() {
        assertEquals(new BooleanArrayStack(), BooleanStacks.immutable.ofAll(Lists.mutable.empty()));
    }

    @Test
    public void ofAllIterable_2() {
        assertEquals(BooleanArrayStack.newStackWith(true), BooleanStacks.immutable.ofAll(Lists.mutable.with(true)));
    }

    @Test
    public void ofAllIterable_3() {
        assertEquals(BooleanArrayStack.newStackWith(true, false), BooleanStacks.immutable.ofAll(Lists.mutable.with(true, false)));
    }

    @Test
    public void ofAllIterable_4() {
        assertEquals(BooleanArrayStack.newStackWith(true, false, false, true), BooleanStacks.immutable.ofAll(Lists.mutable.with(true, false, false, true)));
    }

    @Test
    public void ofAllIterable_5() {
        assertEquals(new BooleanArrayStack(), BooleanStacks.mutable.ofAll(Lists.mutable.empty()));
    }

    @Test
    public void ofAllIterable_6() {
        assertEquals(BooleanArrayStack.newStackWith(true), BooleanStacks.mutable.ofAll(Lists.mutable.with(true)));
    }

    @Test
    public void ofAllIterable_7() {
        assertEquals(BooleanArrayStack.newStackWith(true, false), BooleanStacks.mutable.ofAll(Lists.mutable.with(true, false)));
    }

    @Test
    public void ofAllIterable_8() {
        assertEquals(BooleanArrayStack.newStackWith(true, false, false, true), BooleanStacks.mutable.ofAll(Lists.mutable.with(true, false, false, true)));
    }

    @Test
    public void ofAllReversed_1() {
        assertEquals(new BooleanArrayStack(), BooleanStacks.immutable.ofAllReversed(BooleanLists.mutable.empty()));
    }

    @Test
    public void ofAllReversed_2() {
        assertEquals(BooleanArrayStack.newStackFromTopToBottom(true), BooleanStacks.immutable.ofAllReversed(BooleanLists.mutable.with(true)));
    }

    @Test
    public void ofAllReversed_3() {
        assertEquals(BooleanArrayStack.newStackFromTopToBottom(true, false), BooleanStacks.immutable.ofAllReversed(BooleanLists.mutable.with(true, false)));
    }

    @Test
    public void ofAllReversed_4() {
        assertEquals(BooleanArrayStack.newStackFromTopToBottom(true, false, false, true), BooleanStacks.immutable.ofAllReversed(BooleanLists.mutable.with(true, false, false, true)));
    }

    @Test
    public void ofAllReversed_5() {
        assertEquals(new BooleanArrayStack(), BooleanStacks.mutable.ofAllReversed(BooleanLists.mutable.empty()));
    }

    @Test
    public void ofAllReversed_6() {
        assertEquals(BooleanArrayStack.newStackFromTopToBottom(true), BooleanStacks.mutable.ofAllReversed(BooleanLists.mutable.with(true)));
    }

    @Test
    public void ofAllReversed_7() {
        assertEquals(BooleanArrayStack.newStackFromTopToBottom(true, false), BooleanStacks.mutable.ofAllReversed(BooleanLists.mutable.with(true, false)));
    }

    @Test
    public void ofAllReversed_8() {
        assertEquals(BooleanArrayStack.newStackFromTopToBottom(true, false, false, true), BooleanStacks.mutable.ofAllReversed(BooleanLists.mutable.with(true, false, false, true)));
    }
}
