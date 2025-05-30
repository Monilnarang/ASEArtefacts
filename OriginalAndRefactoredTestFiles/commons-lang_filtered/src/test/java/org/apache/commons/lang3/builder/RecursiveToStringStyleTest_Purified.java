package org.apache.commons.lang3.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RecursiveToStringStyleTest_Purified extends AbstractLangTest {

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
    public void testAppendSuper_1() {
        assertEquals(baseStr + "[]", new ToStringBuilder(base).appendSuper("Integer@8888[]").toString());
    }

    @Test
    public void testAppendSuper_2() {
        assertEquals(baseStr + "[<null>]", new ToStringBuilder(base).appendSuper("Integer@8888[<null>]").toString());
    }

    @Test
    public void testAppendSuper_3() {
        assertEquals(baseStr + "[a=hello]", new ToStringBuilder(base).appendSuper("Integer@8888[]").append("a", "hello").toString());
    }

    @Test
    public void testAppendSuper_4() {
        assertEquals(baseStr + "[<null>,a=hello]", new ToStringBuilder(base).appendSuper("Integer@8888[<null>]").append("a", "hello").toString());
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
}
