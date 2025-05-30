package org.apache.commons.lang3.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class RecursiveToStringStyleTest_Parameterized extends AbstractLangTest {

    static class Job {

        String title;
    }

    static class Person {

        String name;

        int age;

        boolean smoker;

        Job job;
    }

    private final Integer base = Integer.valueOf(5);

    private final String baseStr = base.getClass().getName() + "@" + Integer.toHexString(System.identityHashCode(base));

    @BeforeEach
    public void setUp() {
        ToStringBuilder.setDefaultStyle(new RecursiveToStringStyle());
    }

    @AfterEach
    public void tearDown() {
        ToStringBuilder.setDefaultStyle(ToStringStyle.DEFAULT_STYLE);
    }

    @Test
    public void testAppendSuper_5() {
        assertEquals(baseStr + "[a=hello]", new ToStringBuilder(base).appendSuper(null).append("a", "hello").toString());
    }

    @Test
    public void testLong_1() {
        assertEquals(baseStr + "[3]", new ToStringBuilder(base).append(3L).toString());
    }

    @Test
    public void testLong_2() {
        assertEquals(baseStr + "[a=3]", new ToStringBuilder(base).append("a", 3L).toString());
    }

    @Test
    public void testLong_3() {
        assertEquals(baseStr + "[a=3,b=4]", new ToStringBuilder(base).append("a", 3L).append("b", 4L).toString());
    }

    @ParameterizedTest
    @MethodSource("Provider_testAppendSuper_1to2")
    public void testAppendSuper_1to2(String param1, String param2) {
        assertEquals(baseStr + param1, new ToStringBuilder(base).appendSuper(param2).toString());
    }

    static public Stream<Arguments> Provider_testAppendSuper_1to2() {
        return Stream.of(arguments("[]", "Integer@8888[]"), arguments("[<null>]", "Integer@8888[<null>]"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testAppendSuper_3to4")
    public void testAppendSuper_3to4(String param1, String param2, String param3, String param4) {
        assertEquals(baseStr + param1, new ToStringBuilder(base).appendSuper(param4).append(param2, param3).toString());
    }

    static public Stream<Arguments> Provider_testAppendSuper_3to4() {
        return Stream.of(arguments("[a=hello]", "a", "hello", "Integer@8888[]"), arguments("[<null>,a=hello]", "a", "hello", "Integer@8888[<null>]"));
    }
}
