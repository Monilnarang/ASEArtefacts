package org.apache.commons.lang3.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Arrays;
import java.util.Collections;
import org.apache.commons.lang3.AbstractLangTest;
import org.apache.commons.lang3.builder.ToStringStyleTest.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class ShortPrefixToStringStyleTest_Parameterized extends AbstractLangTest {

    private final Integer base = Integer.valueOf(5);

    private final String baseStr = "Integer";

    @BeforeEach
    public void setUp() {
        ToStringBuilder.setDefaultStyle(ToStringStyle.SHORT_PREFIX_STYLE);
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
    public void testCollection_3_testMerged_3() {
        final Integer i3 = Integer.valueOf(3);
        final Integer i4 = Integer.valueOf(4);
        assertEquals(baseStr + "[a=<size=1>]", new ToStringBuilder(base).append("a", Collections.singletonList(i3), false).toString());
        assertEquals(baseStr + "[a=[3]]", new ToStringBuilder(base).append("a", Collections.singletonList(i3), true).toString());
        assertEquals(baseStr + "[a=<size=2>]", new ToStringBuilder(base).append("a", Arrays.asList(i3, i4), false).toString());
        assertEquals(baseStr + "[a=[3, 4]]", new ToStringBuilder(base).append("a", Arrays.asList(i3, i4), true).toString());
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

    @Test
    public void testObject_1() {
        assertEquals(baseStr + "[<null>]", new ToStringBuilder(base).append((Object) null).toString());
    }

    @Test
    public void testObject_2_testMerged_2() {
        final Integer i3 = Integer.valueOf(3);
        final Integer i4 = Integer.valueOf(4);
        assertEquals(baseStr + "[3]", new ToStringBuilder(base).append(i3).toString());
        assertEquals(baseStr + "[a=3]", new ToStringBuilder(base).append("a", i3).toString());
        assertEquals(baseStr + "[a=3,b=4]", new ToStringBuilder(base).append("a", i3).append("b", i4).toString());
        assertEquals(baseStr + "[a=<Integer>]", new ToStringBuilder(base).append("a", i3, false).toString());
    }

    @Test
    public void testObject_3() {
        assertEquals(baseStr + "[a=<null>]", new ToStringBuilder(base).append("a", (Object) null).toString());
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

    @ParameterizedTest
    @MethodSource("Provider_testCollection_1to2")
    public void testCollection_1to2(String param1, String param2) {
        assertEquals(baseStr + param1, new ToStringBuilder(base).append(param2, Collections.emptyList(), false).toString());
    }

    static public Stream<Arguments> Provider_testCollection_1to2() {
        return Stream.of(arguments("[a=<size=0>]", "a"), arguments("[a=[]]", "a"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testMap_1to2")
    public void testMap_1to2(String param1, String param2) {
        assertEquals(baseStr + param1, new ToStringBuilder(base).append(param2, Collections.emptyMap(), false).toString());
    }

    static public Stream<Arguments> Provider_testMap_1to2() {
        return Stream.of(arguments("[a=<size=0>]", "a"), arguments("[a={}]", "a"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testMap_3to4")
    public void testMap_3to4(String param1, String param2, String param3, String param4) {
        assertEquals(baseStr + param1, new ToStringBuilder(base).append(param2, Collections.singletonMap(param4, "v"), param3).toString());
    }

    static public Stream<Arguments> Provider_testMap_3to4() {
        return Stream.of(arguments("[a=<size=1>]", "a", "k", "v"), arguments("[a={k=v}]", "a", "k", "v"));
    }
}
