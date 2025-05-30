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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class CodePointAdapterTest_Parameterized extends AbstractImmutableIntListTestCase {

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
    public void max_6() {
        assertEquals(this.classUnderTest().size(), this.classUnderTest().max());
    }

    @Override
    @Test
    public void min_2() {
        assertEquals(0L, this.newWith(1, 0, 9, 30, 31, 32).min());
    }

    @Override
    @Test
    public void min_5() {
        assertEquals(1L, this.classUnderTest().min());
    }

    @Override
    @ParameterizedTest
    @MethodSource("Provider_max_1_4to5")
    public void max_1_4to5(long param1, int param2, int param3, int param4) {
        assertEquals(param1, this.newWith(param2, param3, param4).max());
    }

    static public Stream<Arguments> Provider_max_1_4to5() {
        return Stream.of(arguments(9L, 1, 2, 9), arguments(31L, 31, 0, 30), arguments(39L, 32, 39, 35));
    }

    @Override
    @ParameterizedTest
    @MethodSource("Provider_min_1_3to4")
    public void min_1_3to4(long param1, int param2, int param3, int param4) {
        assertEquals(param1, this.newWith(param2, param3, param4).min());
    }

    static public Stream<Arguments> Provider_min_1_3to4() {
        return Stream.of(arguments(1L, 1, 2, 9), arguments(31L, 31, 32, 33), arguments(32L, 32, 39, 35));
    }
}
