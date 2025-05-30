package org.apache.commons.lang3.stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

public class LangCollectorsTest_Purified {

    private static final class Fixture {

        int value;

        private Fixture(final int value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return Integer.toString(value);
        }
    }

    private static final Long _1L = Long.valueOf(1);

    private static final Long _2L = Long.valueOf(2);

    private static final Long _3L = Long.valueOf(3);

    private static final Function<Object, String> TO_STRING = Objects::toString;

    private static final Collector<Object, ?, String> JOINING_0 = LangCollectors.joining();

    private static final Collector<Object, ?, String> JOINING_1 = LangCollectors.joining("-");

    private static final Collector<Object, ?, String> JOINING_3 = LangCollectors.joining("-", "<", ">");

    private static final Collector<Object, ?, String> JOINING_4 = LangCollectors.joining("-", "<", ">", TO_STRING);

    private static final Collector<Object, ?, String> JOINING_4_NUL = LangCollectors.joining("-", "<", ">", o -> Objects.toString(o, "NUL"));

    private String join0(final Object... objects) {
        return LangCollectors.collect(JOINING_0, objects);
    }

    private String join1(final Object... objects) {
        return LangCollectors.collect(JOINING_1, objects);
    }

    private String join3(final Object... objects) {
        return LangCollectors.collect(JOINING_3, objects);
    }

    private String join4(final Object... objects) {
        return LangCollectors.collect(JOINING_4, objects);
    }

    private String join4NullToString(final Object... objects) {
        return LangCollectors.collect(JOINING_4_NUL, objects);
    }

    @Test
    public void testCollectStrings1Arg_1() {
        assertEquals("", join1());
    }

    @Test
    public void testCollectStrings1Arg_2() {
        assertEquals("1", join1("1"));
    }

    @Test
    public void testCollectStrings1Arg_3() {
        assertEquals("1-2", join1("1", "2"));
    }

    @Test
    public void testCollectStrings1Arg_4() {
        assertEquals("1-2-3", join1("1", "2", "3"));
    }

    @Test
    public void testCollectStrings1Arg_5() {
        assertEquals("1-null-3", join1("1", null, "3"));
    }

    @Test
    public void testJoinCollectNonStrings0Arg_1() {
        assertEquals("", join0());
    }

    @Test
    public void testJoinCollectNonStrings0Arg_2() {
        assertEquals("1", join0(_1L));
    }

    @Test
    public void testJoinCollectNonStrings0Arg_3() {
        assertEquals("12", join0(_1L, _2L));
    }

    @Test
    public void testJoinCollectNonStrings0Arg_4() {
        assertEquals("123", join0(_1L, _2L, _3L));
    }

    @Test
    public void testJoinCollectNonStrings0Arg_5() {
        assertEquals("1null3", join0(_1L, null, _3L));
    }

    @Test
    public void testJoinCollectNonStrings0Arg_6() {
        assertEquals("12", join0(new AtomicLong(1), new AtomicLong(2)));
    }

    @Test
    public void testJoinCollectNonStrings0Arg_7() {
        assertEquals("12", join0(new Fixture(1), new Fixture(2)));
    }

    @Test
    public void testJoinCollectNonStrings1Arg_1() {
        assertEquals("", join1());
    }

    @Test
    public void testJoinCollectNonStrings1Arg_2() {
        assertEquals("1", join1(_1L));
    }

    @Test
    public void testJoinCollectNonStrings1Arg_3() {
        assertEquals("1-2", join1(_1L, _2L));
    }

    @Test
    public void testJoinCollectNonStrings1Arg_4() {
        assertEquals("1-2-3", join1(_1L, _2L, _3L));
    }

    @Test
    public void testJoinCollectNonStrings1Arg_5() {
        assertEquals("1-null-3", join1(_1L, null, _3L));
    }

    @Test
    public void testJoinCollectNonStrings1Arg_6() {
        assertEquals("1-2", join1(new AtomicLong(1), new AtomicLong(2)));
    }

    @Test
    public void testJoinCollectNonStrings1Arg_7() {
        assertEquals("1-2", join1(new Fixture(1), new Fixture(2)));
    }

    @Test
    public void testJoinCollectNonStrings3Args_1() {
        assertEquals("<>", join3());
    }

    @Test
    public void testJoinCollectNonStrings3Args_2() {
        assertEquals("<1>", join3(_1L));
    }

    @Test
    public void testJoinCollectNonStrings3Args_3() {
        assertEquals("<1-2>", join3(_1L, _2L));
    }

    @Test
    public void testJoinCollectNonStrings3Args_4() {
        assertEquals("<1-2-3>", join3(_1L, _2L, _3L));
    }

    @Test
    public void testJoinCollectNonStrings3Args_5() {
        assertEquals("<1-null-3>", join3(_1L, null, _3L));
    }

    @Test
    public void testJoinCollectNonStrings3Args_6() {
        assertEquals("<1-2>", join3(new AtomicLong(1), new AtomicLong(2)));
    }

    @Test
    public void testJoinCollectNonStrings3Args_7() {
        assertEquals("<1-2>", join3(new Fixture(1), new Fixture(2)));
    }

    @Test
    public void testJoinCollectNonStrings4Args_1() {
        assertEquals("<>", join4());
    }

    @Test
    public void testJoinCollectNonStrings4Args_2() {
        assertEquals("<1>", join4(_1L));
    }

    @Test
    public void testJoinCollectNonStrings4Args_3() {
        assertEquals("<1-2>", join4(_1L, _2L));
    }

    @Test
    public void testJoinCollectNonStrings4Args_4() {
        assertEquals("<1-2-3>", join4(_1L, _2L, _3L));
    }

    @Test
    public void testJoinCollectNonStrings4Args_5() {
        assertEquals("<1-null-3>", join4(_1L, null, _3L));
    }

    @Test
    public void testJoinCollectNonStrings4Args_6() {
        assertEquals("<1-NUL-3>", join4NullToString(_1L, null, _3L));
    }

    @Test
    public void testJoinCollectNonStrings4Args_7() {
        assertEquals("<1-2>", join4(new AtomicLong(1), new AtomicLong(2)));
    }

    @Test
    public void testJoinCollectNonStrings4Args_8() {
        assertEquals("<1-2>", join4(new Fixture(1), new Fixture(2)));
    }

    @Test
    public void testJoinCollectNullArgs_1() {
        assertEquals("", join0((Object[]) null));
    }

    @Test
    public void testJoinCollectNullArgs_2() {
        assertEquals("", join1((Object[]) null));
    }

    @Test
    public void testJoinCollectNullArgs_3() {
        assertEquals("<>", join3((Object[]) null));
    }

    @Test
    public void testJoinCollectNullArgs_4() {
        assertEquals("<>", join4NullToString((Object[]) null));
    }

    @Test
    public void testJoinCollectStrings0Arg_1() {
        assertEquals("", join0());
    }

    @Test
    public void testJoinCollectStrings0Arg_2() {
        assertEquals("1", join0("1"));
    }

    @Test
    public void testJoinCollectStrings0Arg_3() {
        assertEquals("12", join0("1", "2"));
    }

    @Test
    public void testJoinCollectStrings0Arg_4() {
        assertEquals("123", join0("1", "2", "3"));
    }

    @Test
    public void testJoinCollectStrings0Arg_5() {
        assertEquals("1null3", join0("1", null, "3"));
    }

    @Test
    public void testJoinCollectStrings3Args_1() {
        assertEquals("<>", join3());
    }

    @Test
    public void testJoinCollectStrings3Args_2() {
        assertEquals("<1>", join3("1"));
    }

    @Test
    public void testJoinCollectStrings3Args_3() {
        assertEquals("<1-2>", join3("1", "2"));
    }

    @Test
    public void testJoinCollectStrings3Args_4() {
        assertEquals("<1-2-3>", join3("1", "2", "3"));
    }

    @Test
    public void testJoinCollectStrings3Args_5() {
        assertEquals("<1-null-3>", join3("1", null, "3"));
    }

    @Test
    public void testJoinCollectStrings4Args_1() {
        assertEquals("<>", join4());
    }

    @Test
    public void testJoinCollectStrings4Args_2() {
        assertEquals("<1>", join4("1"));
    }

    @Test
    public void testJoinCollectStrings4Args_3() {
        assertEquals("<1-2>", join4("1", "2"));
    }

    @Test
    public void testJoinCollectStrings4Args_4() {
        assertEquals("<1-2-3>", join4("1", "2", "3"));
    }

    @Test
    public void testJoinCollectStrings4Args_5() {
        assertEquals("<1-null-3>", join4("1", null, "3"));
    }

    @Test
    public void testJoinCollectStrings4Args_6() {
        assertEquals("<1-NUL-3>", join4NullToString("1", null, "3"));
    }

    @Test
    public void testJoiningNonStrings3Args_1() {
        assertEquals("<>", Stream.of().collect(JOINING_3));
    }

    @Test
    public void testJoiningNonStrings3Args_2() {
        assertEquals("<1>", Stream.of(_1L).collect(JOINING_3));
    }

    @Test
    public void testJoiningNonStrings3Args_3() {
        assertEquals("<1-2>", Stream.of(_1L, _2L).collect(JOINING_3));
    }

    @Test
    public void testJoiningNonStrings3Args_4() {
        assertEquals("<1-2-3>", Stream.of(_1L, _2L, _3L).collect(JOINING_3));
    }

    @Test
    public void testJoiningNonStrings3Args_5() {
        assertEquals("<1-null-3>", Stream.of(_1L, null, _3L).collect(JOINING_3));
    }

    @Test
    public void testJoiningNonStrings3Args_6() {
        assertEquals("<1-2>", Stream.of(new AtomicLong(1), new AtomicLong(2)).collect(JOINING_3));
    }

    @Test
    public void testJoiningNonStrings3Args_7() {
        assertEquals("<1-2>", Stream.of(new Fixture(1), new Fixture(2)).collect(JOINING_3));
    }

    @Test
    public void testJoiningNonStrings4Args_1() {
        assertEquals("<>", Stream.of().collect(JOINING_4));
    }

    @Test
    public void testJoiningNonStrings4Args_2() {
        assertEquals("<1>", Stream.of(_1L).collect(JOINING_4));
    }

    @Test
    public void testJoiningNonStrings4Args_3() {
        assertEquals("<1-2>", Stream.of(_1L, _2L).collect(JOINING_4));
    }

    @Test
    public void testJoiningNonStrings4Args_4() {
        assertEquals("<1-2-3>", Stream.of(_1L, _2L, _3L).collect(JOINING_4));
    }

    @Test
    public void testJoiningNonStrings4Args_5() {
        assertEquals("<1-null-3>", Stream.of(_1L, null, _3L).collect(JOINING_4));
    }

    @Test
    public void testJoiningNonStrings4Args_6() {
        assertEquals("<1-NUL-3>", Stream.of(_1L, null, _3L).collect(JOINING_4_NUL));
    }

    @Test
    public void testJoiningNonStrings4Args_7() {
        assertEquals("<1-2>", Stream.of(new AtomicLong(1), new AtomicLong(2)).collect(JOINING_4));
    }

    @Test
    public void testJoiningNonStrings4Args_8() {
        assertEquals("<1-2>", Stream.of(new Fixture(1), new Fixture(2)).collect(JOINING_4));
    }

    @Test
    public void testJoiningStrings0Arg_1() {
        assertEquals("", Stream.of().collect(JOINING_0));
    }

    @Test
    public void testJoiningStrings0Arg_2() {
        assertEquals("1", Stream.of("1").collect(JOINING_0));
    }

    @Test
    public void testJoiningStrings0Arg_3() {
        assertEquals("12", Stream.of("1", "2").collect(JOINING_0));
    }

    @Test
    public void testJoiningStrings0Arg_4() {
        assertEquals("123", Stream.of("1", "2", "3").collect(JOINING_0));
    }

    @Test
    public void testJoiningStrings0Arg_5() {
        assertEquals("1null3", Stream.of("1", null, "3").collect(JOINING_0));
    }

    @Test
    public void testJoiningStrings1Arg_1() {
        assertEquals("", Stream.of().collect(JOINING_1));
    }

    @Test
    public void testJoiningStrings1Arg_2() {
        assertEquals("1", Stream.of("1").collect(JOINING_1));
    }

    @Test
    public void testJoiningStrings1Arg_3() {
        assertEquals("1-2", Stream.of("1", "2").collect(JOINING_1));
    }

    @Test
    public void testJoiningStrings1Arg_4() {
        assertEquals("1-2-3", Stream.of("1", "2", "3").collect(JOINING_1));
    }

    @Test
    public void testJoiningStrings1Arg_5() {
        assertEquals("1-null-3", Stream.of("1", null, "3").collect(JOINING_1));
    }

    @Test
    public void testJoiningStrings3Args_1() {
        assertEquals("<>", Stream.of().collect(JOINING_3));
    }

    @Test
    public void testJoiningStrings3Args_2() {
        assertEquals("<1>", Stream.of("1").collect(JOINING_3));
    }

    @Test
    public void testJoiningStrings3Args_3() {
        assertEquals("<1-2>", Stream.of("1", "2").collect(JOINING_3));
    }

    @Test
    public void testJoiningStrings3Args_4() {
        assertEquals("<1-2-3>", Stream.of("1", "2", "3").collect(JOINING_3));
    }

    @Test
    public void testJoiningStrings3Args_5() {
        assertEquals("<1-null-3>", Stream.of("1", null, "3").collect(JOINING_3));
    }

    @Test
    public void testJoiningStrings4Args_1() {
        assertEquals("<>", Stream.of().collect(JOINING_4));
    }

    @Test
    public void testJoiningStrings4Args_2() {
        assertEquals("<1>", Stream.of("1").collect(JOINING_4));
    }

    @Test
    public void testJoiningStrings4Args_3() {
        assertEquals("<1-2>", Stream.of("1", "2").collect(JOINING_4));
    }

    @Test
    public void testJoiningStrings4Args_4() {
        assertEquals("<1-2-3>", Stream.of("1", "2", "3").collect(JOINING_4));
    }

    @Test
    public void testJoiningStrings4Args_5() {
        assertEquals("<1-null-3>", Stream.of("1", null, "3").collect(JOINING_4));
    }

    @Test
    public void testJoiningStrings4Args_6() {
        assertEquals("<1-NUL-3>", Stream.of("1", null, "3").collect(JOINING_4_NUL));
    }
}
