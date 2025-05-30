package org.eclipse.collections.impl.string.immutable;

import java.util.Arrays;
import java.util.stream.Collectors;
import org.eclipse.collections.api.IntIterable;
import org.eclipse.collections.api.LazyIntIterable;
import org.eclipse.collections.api.list.primitive.ImmutableIntList;
import org.eclipse.collections.impl.block.factory.primitive.IntPredicates;
import org.eclipse.collections.impl.factory.Strings;
import org.eclipse.collections.impl.list.immutable.primitive.AbstractImmutableIntListTestCase;
import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class CodePointAdapterTest_Purified extends AbstractImmutableIntListTestCase {

    private static final String UNICODE_STRING = "\u3042\uD840\uDC00\u3044\uD840\uDC03\u3046\uD83D\uDE09";

    @Override
    protected ImmutableIntList classUnderTest() {
        return CodePointAdapter.from(1, 2, 3);
    }

    @Override
    protected ImmutableIntList newWith(int... elements) {
        return CodePointAdapter.from(elements);
    }

    @Override
    public void toReversed() {
        super.toReversed();
        assertEquals("cba", CodePointAdapter.adapt("abc").toReversed().toString());
    }

    private static class SBAppendable implements Appendable {

        private final StringBuilder builder = new StringBuilder();

        @Override
        public Appendable append(char c) {
            return this.builder.append(c);
        }

        @Override
        public Appendable append(CharSequence csq) {
            return this.builder.append(csq);
        }

        @Override
        public Appendable append(CharSequence csq, int start, int end) {
            return this.builder.append(csq, start, end);
        }

        @Override
        public String toString() {
            return this.builder.toString();
        }
    }

    @Override
    @Test
    public void max_1() {
        assertEquals(9L, this.newWith(1, 2, 9).max());
    }

    @Override
    @Test
    public void max_2() {
        assertEquals(32L, this.newWith(1, 0, 9, 30, 31, 32).max());
    }

    @Override
    @Test
    public void max_3() {
        assertEquals(32L, this.newWith(0, 9, 30, 31, 32).max());
    }

    @Override
    @Test
    public void max_4() {
        assertEquals(31L, this.newWith(31, 0, 30).max());
    }

    @Override
    @Test
    public void max_5() {
        assertEquals(39L, this.newWith(32, 39, 35).max());
    }

    @Override
    @Test
    public void max_6() {
        assertEquals(this.classUnderTest().size(), this.classUnderTest().max());
    }

    @Override
    @Test
    public void min_1() {
        assertEquals(1L, this.newWith(1, 2, 9).min());
    }

    @Override
    @Test
    public void min_2() {
        assertEquals(0L, this.newWith(1, 0, 9, 30, 31, 32).min());
    }

    @Override
    @Test
    public void min_3() {
        assertEquals(31L, this.newWith(31, 32, 33).min());
    }

    @Override
    @Test
    public void min_4() {
        assertEquals(32L, this.newWith(32, 39, 35).min());
    }

    @Override
    @Test
    public void min_5() {
        assertEquals(1L, this.classUnderTest().min());
    }
}
