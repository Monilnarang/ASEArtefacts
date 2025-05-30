package org.apache.commons.lang3.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Arrays;
import java.util.Collections;
import org.apache.commons.lang3.AbstractLangTest;
import org.apache.commons.lang3.builder.ToStringStyleTest.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class NoClassNameToStringStyleTest_Purified extends AbstractLangTest {

    private final Integer base = Integer.valueOf(5);

    @BeforeEach
    public void setUp() {
        ToStringBuilder.setDefaultStyle(ToStringStyle.NO_CLASS_NAME_STYLE);
    }

    @AfterEach
    public void tearDown() {
        ToStringBuilder.setDefaultStyle(ToStringStyle.DEFAULT_STYLE);
    }

    @Test
    public void testAppendSuper_1() {
        assertEquals("[]", new ToStringBuilder(base).appendSuper("Integer@8888[]").toString());
    }

    @Test
    public void testAppendSuper_2() {
        assertEquals("[<null>]", new ToStringBuilder(base).appendSuper("Integer@8888[<null>]").toString());
    }

    @Test
    public void testAppendSuper_3() {
        assertEquals("[a=hello]", new ToStringBuilder(base).appendSuper("Integer@8888[]").append("a", "hello").toString());
    }

    @Test
    public void testAppendSuper_4() {
        assertEquals("[<null>,a=hello]", new ToStringBuilder(base).appendSuper("Integer@8888[<null>]").append("a", "hello").toString());
    }

    @Test
    public void testAppendSuper_5() {
        assertEquals("[a=hello]", new ToStringBuilder(base).appendSuper(null).append("a", "hello").toString());
    }

    @Test
    public void testCollection_1() {
        assertEquals("[a=<size=0>]", new ToStringBuilder(base).append("a", Collections.emptyList(), false).toString());
    }

    @Test
    public void testCollection_2() {
        assertEquals("[a=[]]", new ToStringBuilder(base).append("a", Collections.emptyList(), true).toString());
    }

    @Test
    public void testCollection_3_testMerged_3() {
        final Integer i3 = Integer.valueOf(3);
        final Integer i4 = Integer.valueOf(4);
        assertEquals("[a=<size=1>]", new ToStringBuilder(base).append("a", Collections.singletonList(i3), false).toString());
        assertEquals("[a=[3]]", new ToStringBuilder(base).append("a", Collections.singletonList(i3), true).toString());
        assertEquals("[a=<size=2>]", new ToStringBuilder(base).append("a", Arrays.asList(i3, i4), false).toString());
        assertEquals("[a=[3, 4]]", new ToStringBuilder(base).append("a", Arrays.asList(i3, i4), true).toString());
    }

    @Test
    public void testLong_1() {
        assertEquals("[3]", new ToStringBuilder(base).append(3L).toString());
    }

    @Test
    public void testLong_2() {
        assertEquals("[a=3]", new ToStringBuilder(base).append("a", 3L).toString());
    }

    @Test
    public void testLong_3() {
        assertEquals("[a=3,b=4]", new ToStringBuilder(base).append("a", 3L).append("b", 4L).toString());
    }

    @Test
    public void testMap_1() {
        assertEquals("[a=<size=0>]", new ToStringBuilder(base).append("a", Collections.emptyMap(), false).toString());
    }

    @Test
    public void testMap_2() {
        assertEquals("[a={}]", new ToStringBuilder(base).append("a", Collections.emptyMap(), true).toString());
    }

    @Test
    public void testMap_3() {
        assertEquals("[a=<size=1>]", new ToStringBuilder(base).append("a", Collections.singletonMap("k", "v"), false).toString());
    }

    @Test
    public void testMap_4() {
        assertEquals("[a={k=v}]", new ToStringBuilder(base).append("a", Collections.singletonMap("k", "v"), true).toString());
    }

    @Test
    public void testObject_1() {
        assertEquals("[<null>]", new ToStringBuilder(base).append((Object) null).toString());
    }

    @Test
    public void testObject_2_testMerged_2() {
        final Integer i3 = Integer.valueOf(3);
        final Integer i4 = Integer.valueOf(4);
        assertEquals("[3]", new ToStringBuilder(base).append(i3).toString());
        assertEquals("[a=3]", new ToStringBuilder(base).append("a", i3).toString());
        assertEquals("[a=3,b=4]", new ToStringBuilder(base).append("a", i3).append("b", i4).toString());
        assertEquals("[a=<Integer>]", new ToStringBuilder(base).append("a", i3, false).toString());
    }

    @Test
    public void testObject_3() {
        assertEquals("[a=<null>]", new ToStringBuilder(base).append("a", (Object) null).toString());
    }
}
