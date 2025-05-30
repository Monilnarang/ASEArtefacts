package org.apache.commons.lang3.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Arrays;
import java.util.Collections;
import org.apache.commons.lang3.AbstractLangTest;
import org.apache.commons.lang3.builder.ToStringStyleTest.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MultiLineToStringStyleTest_Purified extends AbstractLangTest {

    private final Integer base = Integer.valueOf(5);

    private final String baseStr = base.getClass().getName() + "@" + Integer.toHexString(System.identityHashCode(base));

    @BeforeEach
    public void setUp() {
        ToStringBuilder.setDefaultStyle(ToStringStyle.MULTI_LINE_STYLE);
    }

    @AfterEach
    public void tearDown() {
        ToStringBuilder.setDefaultStyle(ToStringStyle.DEFAULT_STYLE);
    }

    @Test
    public void testAppendSuper_1() {
        assertEquals(baseStr + "[" + System.lineSeparator() + "]", new ToStringBuilder(base).appendSuper("Integer@8888[" + System.lineSeparator() + "]").toString());
    }

    @Test
    public void testAppendSuper_2() {
        assertEquals(baseStr + "[" + System.lineSeparator() + "  <null>" + System.lineSeparator() + "]", new ToStringBuilder(base).appendSuper("Integer@8888[" + System.lineSeparator() + "  <null>" + System.lineSeparator() + "]").toString());
    }

    @Test
    public void testAppendSuper_3() {
        assertEquals(baseStr + "[" + System.lineSeparator() + "  a=hello" + System.lineSeparator() + "]", new ToStringBuilder(base).appendSuper("Integer@8888[" + System.lineSeparator() + "]").append("a", "hello").toString());
    }

    @Test
    public void testAppendSuper_4() {
        assertEquals(baseStr + "[" + System.lineSeparator() + "  <null>" + System.lineSeparator() + "  a=hello" + System.lineSeparator() + "]", new ToStringBuilder(base).appendSuper("Integer@8888[" + System.lineSeparator() + "  <null>" + System.lineSeparator() + "]").append("a", "hello").toString());
    }

    @Test
    public void testAppendSuper_5() {
        assertEquals(baseStr + "[" + System.lineSeparator() + "  a=hello" + System.lineSeparator() + "]", new ToStringBuilder(base).appendSuper(null).append("a", "hello").toString());
    }

    @Test
    public void testCollection_1() {
        assertEquals(baseStr + "[" + System.lineSeparator() + "  a=<size=0>" + System.lineSeparator() + "]", new ToStringBuilder(base).append("a", Collections.emptyList(), false).toString());
    }

    @Test
    public void testCollection_2() {
        assertEquals(baseStr + "[" + System.lineSeparator() + "  a=[]" + System.lineSeparator() + "]", new ToStringBuilder(base).append("a", Collections.emptyList(), true).toString());
    }

    @Test
    public void testCollection_3_testMerged_3() {
        final Integer i3 = Integer.valueOf(3);
        final Integer i4 = Integer.valueOf(4);
        assertEquals(baseStr + "[" + System.lineSeparator() + "  a=<size=1>" + System.lineSeparator() + "]", new ToStringBuilder(base).append("a", Collections.singletonList(i3), false).toString());
        assertEquals(baseStr + "[" + System.lineSeparator() + "  a=[3]" + System.lineSeparator() + "]", new ToStringBuilder(base).append("a", Collections.singletonList(i3), true).toString());
        assertEquals(baseStr + "[" + System.lineSeparator() + "  a=<size=2>" + System.lineSeparator() + "]", new ToStringBuilder(base).append("a", Arrays.asList(i3, i4), false).toString());
        assertEquals(baseStr + "[" + System.lineSeparator() + "  a=[3, 4]" + System.lineSeparator() + "]", new ToStringBuilder(base).append("a", Arrays.asList(i3, i4), true).toString());
    }

    @Test
    public void testLong_1() {
        assertEquals(baseStr + "[" + System.lineSeparator() + "  3" + System.lineSeparator() + "]", new ToStringBuilder(base).append(3L).toString());
    }

    @Test
    public void testLong_2() {
        assertEquals(baseStr + "[" + System.lineSeparator() + "  a=3" + System.lineSeparator() + "]", new ToStringBuilder(base).append("a", 3L).toString());
    }

    @Test
    public void testLong_3() {
        assertEquals(baseStr + "[" + System.lineSeparator() + "  a=3" + System.lineSeparator() + "  b=4" + System.lineSeparator() + "]", new ToStringBuilder(base).append("a", 3L).append("b", 4L).toString());
    }

    @Test
    public void testMap_1() {
        assertEquals(baseStr + "[" + System.lineSeparator() + "  a=<size=0>" + System.lineSeparator() + "]", new ToStringBuilder(base).append("a", Collections.emptyMap(), false).toString());
    }

    @Test
    public void testMap_2() {
        assertEquals(baseStr + "[" + System.lineSeparator() + "  a={}" + System.lineSeparator() + "]", new ToStringBuilder(base).append("a", Collections.emptyMap(), true).toString());
    }

    @Test
    public void testMap_3() {
        assertEquals(baseStr + "[" + System.lineSeparator() + "  a=<size=1>" + System.lineSeparator() + "]", new ToStringBuilder(base).append("a", Collections.singletonMap("k", "v"), false).toString());
    }

    @Test
    public void testMap_4() {
        assertEquals(baseStr + "[" + System.lineSeparator() + "  a={k=v}" + System.lineSeparator() + "]", new ToStringBuilder(base).append("a", Collections.singletonMap("k", "v"), true).toString());
    }

    @Test
    public void testObject_1() {
        assertEquals(baseStr + "[" + System.lineSeparator() + "  <null>" + System.lineSeparator() + "]", new ToStringBuilder(base).append((Object) null).toString());
    }

    @Test
    public void testObject_2_testMerged_2() {
        final Integer i3 = Integer.valueOf(3);
        final Integer i4 = Integer.valueOf(4);
        assertEquals(baseStr + "[" + System.lineSeparator() + "  3" + System.lineSeparator() + "]", new ToStringBuilder(base).append(i3).toString());
        assertEquals(baseStr + "[" + System.lineSeparator() + "  a=3" + System.lineSeparator() + "]", new ToStringBuilder(base).append("a", i3).toString());
        assertEquals(baseStr + "[" + System.lineSeparator() + "  a=3" + System.lineSeparator() + "  b=4" + System.lineSeparator() + "]", new ToStringBuilder(base).append("a", i3).append("b", i4).toString());
        assertEquals(baseStr + "[" + System.lineSeparator() + "  a=<Integer>" + System.lineSeparator() + "]", new ToStringBuilder(base).append("a", i3, false).toString());
    }

    @Test
    public void testObject_3() {
        assertEquals(baseStr + "[" + System.lineSeparator() + "  a=<null>" + System.lineSeparator() + "]", new ToStringBuilder(base).append("a", (Object) null).toString());
    }
}
