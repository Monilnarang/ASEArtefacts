package org.eclipse.collections.impl.test;

import java.util.Stack;
import java.util.Vector;
import java.util.concurrent.Callable;
import org.eclipse.collections.api.block.function.Function;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.factory.SortedSets;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.set.sorted.MutableSortedSet;
import org.eclipse.collections.api.tuple.Triplet;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ClassComparerTest_Purified {

    private static class CollectingAppendable implements Appendable {

        private final MutableList<CharSequence> contents = Lists.mutable.empty();

        @Override
        public Appendable append(CharSequence csq) {
            this.contents.add(csq);
            return this;
        }

        @Override
        public Appendable append(CharSequence csq, int start, int end) {
            this.contents.add(csq);
            return this;
        }

        @Override
        public Appendable append(char c) {
            this.contents.add(String.valueOf(c));
            return this;
        }

        public MutableList<CharSequence> getContents() {
            return this.contents;
        }
    }

    @Test
    public void isProperSupersetOf_1() {
        assertTrue(ClassComparer.isProperSupersetOf(Stack.class, Vector.class));
    }

    @Test
    public void isProperSupersetOf_2() {
        assertFalse(ClassComparer.isProperSupersetOf(Vector.class, Stack.class));
    }

    @Test
    public void isProperSubsetOf_1() {
        assertTrue(ClassComparer.isProperSubsetOf(Vector.class, Stack.class));
    }

    @Test
    public void isProperSubsetOf_2() {
        assertFalse(ClassComparer.isProperSubsetOf(Stack.class, Vector.class));
    }
}
