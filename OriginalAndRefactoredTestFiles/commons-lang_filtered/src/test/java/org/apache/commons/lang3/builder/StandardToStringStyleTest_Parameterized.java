package org.apache.commons.lang3.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

public class StandardToStringStyleTest_Parameterized extends AbstractLangTest {

    private static final StandardToStringStyle STYLE = new StandardToStringStyle();

    static {
        STYLE.setUseShortClassName(true);
        STYLE.setUseIdentityHashCode(false);
        STYLE.setArrayStart("[");
        STYLE.setArraySeparator(", ");
        STYLE.setArrayEnd("]");
        STYLE.setNullText("%NULL%");
        STYLE.setSizeStartText("%SIZE=");
        STYLE.setSizeEndText("%");
        STYLE.setSummaryObjectStartText("%");
        STYLE.setSummaryObjectEndText("%");
        STYLE.setUseClassName(true);
        STYLE.setUseFieldNames(true);
        STYLE.setUseClassName(true);
        STYLE.setUseFieldNames(true);
        STYLE.setDefaultFullDetail(true);
        STYLE.setArrayContentDetail(true);
        STYLE.setFieldNameValueSeparator("=");
    }

    private final Integer base = Integer.valueOf(5);

    private final String baseStr = "Integer";

    @BeforeEach
    public void setUp() {
        ToStringBuilder.setDefaultStyle(STYLE);
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
        assertEquals(baseStr + "[a=%SIZE=1%]", new ToStringBuilder(base).append("a", Collections.singletonList(i3), false).toString());
        assertEquals(baseStr + "[a=[3]]", new ToStringBuilder(base).append("a", Collections.singletonList(i3), true).toString());
        assertEquals(baseStr + "[a=%SIZE=2%]", new ToStringBuilder(base).append("a", Arrays.asList(i3, i4), false).toString());
        assertEquals(baseStr + "[a=[3, 4]]", new ToStringBuilder(base).append("a", Arrays.asList(i3, i4), true).toString());
    }

    @Test
    public void testDefaultGetter_1() {
        assertEquals("[", STYLE.getContentStart());
    }

    @Test
    public void testDefaultGetter_2() {
        assertEquals("]", STYLE.getContentEnd());
    }

    @Test
    public void testDefaultGetter_3() {
        assertEquals("=", STYLE.getFieldNameValueSeparator());
    }

    @Test
    public void testDefaultGetter_4() {
        assertEquals(",", STYLE.getFieldSeparator());
    }

    @Test
    public void testDefaultGetter_5() {
        assertEquals("%NULL%", STYLE.getNullText());
    }

    @Test
    public void testDefaultGetter_6() {
        assertEquals("%SIZE=", STYLE.getSizeStartText());
    }

    @Test
    public void testDefaultGetter_7() {
        assertEquals("%", STYLE.getSizeEndText());
    }

    @Test
    public void testDefaultGetter_8() {
        assertEquals("%", STYLE.getSummaryObjectStartText());
    }

    @Test
    public void testDefaultGetter_9() {
        assertEquals("%", STYLE.getSummaryObjectEndText());
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
        assertEquals(baseStr + "[%NULL%]", new ToStringBuilder(base).append((Object) null).toString());
    }

    @Test
    public void testObject_2_testMerged_2() {
        final Integer i3 = Integer.valueOf(3);
        final Integer i4 = Integer.valueOf(4);
        assertEquals(baseStr + "[3]", new ToStringBuilder(base).append(i3).toString());
        assertEquals(baseStr + "[a=3]", new ToStringBuilder(base).append("a", i3).toString());
        assertEquals(baseStr + "[a=3,b=4]", new ToStringBuilder(base).append("a", i3).append("b", i4).toString());
        assertEquals(baseStr + "[a=%Integer%]", new ToStringBuilder(base).append("a", i3, false).toString());
    }

    @Test
    public void testObject_3() {
        assertEquals(baseStr + "[a=%NULL%]", new ToStringBuilder(base).append("a", (Object) null).toString());
    }

    @ParameterizedTest
    @MethodSource("Provider_testAppendSuper_1to2")
    public void testAppendSuper_1to2(String param1, String param2) {
        assertEquals(baseStr + param1, new ToStringBuilder(base).appendSuper(param2).toString());
    }

    static public Stream<Arguments> Provider_testAppendSuper_1to2() {
        return Stream.of(arguments("[]", "Integer@8888[]"), arguments("[%NULL%]", "Integer@8888[%NULL%]"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testAppendSuper_3to4")
    public void testAppendSuper_3to4(String param1, String param2, String param3, String param4) {
        assertEquals(baseStr + param1, new ToStringBuilder(base).appendSuper(param4).append(param2, param3).toString());
    }

    static public Stream<Arguments> Provider_testAppendSuper_3to4() {
        return Stream.of(arguments("[a=hello]", "a", "hello", "Integer@8888[]"), arguments("[%NULL%,a=hello]", "a", "hello", "Integer@8888[%NULL%]"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testCollection_1to2")
    public void testCollection_1to2(String param1, String param2) {
        assertEquals(baseStr + param1, new ToStringBuilder(base).append(param2, Collections.emptyList(), false).toString());
    }

    static public Stream<Arguments> Provider_testCollection_1to2() {
        return Stream.of(arguments("[a=%SIZE=0%]", "a"), arguments("[a=[]]", "a"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testMap_1to2")
    public void testMap_1to2(String param1, String param2) {
        assertEquals(baseStr + param1, new ToStringBuilder(base).append(param2, Collections.emptyMap(), false).toString());
    }

    static public Stream<Arguments> Provider_testMap_1to2() {
        return Stream.of(arguments("[a=%SIZE=0%]", "a"), arguments("[a={}]", "a"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testMap_3to4")
    public void testMap_3to4(String param1, String param2, String param3, String param4) {
        assertEquals(baseStr + param1, new ToStringBuilder(base).append(param2, Collections.singletonMap(param4, "v"), param3).toString());
    }

    static public Stream<Arguments> Provider_testMap_3to4() {
        return Stream.of(arguments("[a=%SIZE=1%]", "a", "k", "v"), arguments("[a={k=v}]", "a", "k", "v"));
    }
}
