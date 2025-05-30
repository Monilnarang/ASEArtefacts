package org.apache.commons.lang3.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

public class ToStringBuilderTest_Purified extends AbstractLangTest {

    @SuppressWarnings("unused")
    final class InheritedReflectionStaticFieldsFixture extends SimpleReflectionStaticFieldsFixture {

        static final String staticString2 = "staticString2";

        static final int staticInt2 = 67890;
    }

    final class MultiLineTestObject {

        Integer i = Integer.valueOf(31337);

        @Override
        public String toString() {
            return new ToStringBuilder(this).append("testInt", i).toString();
        }
    }

    static class ObjectCycle {

        Object obj;

        @Override
        public String toString() {
            return new ToStringBuilder(this).append(obj).toString();
        }
    }

    static class Outer {

        final class Inner {

            @Override
            public String toString() {
                return ToStringBuilder.reflectionToString(this);
            }
        }

        Inner inner = new Inner();

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this);
        }
    }

    final class ReflectionStaticFieldsFixture {

        static final String staticString = "staticString";

        static final int staticInt = 12345;

        static final transient String staticTransientString = "staticTransientString";

        static final transient int staticTransientInt = 54321;

        String instanceString = "instanceString";

        int instanceInt = 67890;

        transient String transientString = "transientString";

        transient int transientInt = 98765;
    }

    static class ReflectionTestCycleA {

        ReflectionTestCycleB b;

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this);
        }
    }

    static class ReflectionTestCycleB {

        ReflectionTestCycleA a;

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this);
        }
    }

    static class ReflectionTestFixtureA {

        @SuppressWarnings("unused")
        private final char a = 'a';

        @SuppressWarnings("unused")
        private final transient char transientA = 't';
    }

    static class ReflectionTestFixtureB extends ReflectionTestFixtureA {

        @SuppressWarnings("unused")
        private final char b = 'b';

        @SuppressWarnings("unused")
        private final transient char transientB = 't';
    }

    private static final class SelfInstanceTwoVarsReflectionTestFixture {

        @SuppressWarnings("unused")
        private final SelfInstanceTwoVarsReflectionTestFixture typeIsSelf;

        private final String otherType = "The Other Type";

        SelfInstanceTwoVarsReflectionTestFixture() {
            this.typeIsSelf = this;
        }

        public String getOtherType() {
            return this.otherType;
        }

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this);
        }
    }

    private static final class SelfInstanceVarReflectionTestFixture {

        @SuppressWarnings("unused")
        private final SelfInstanceVarReflectionTestFixture typeIsSelf;

        SelfInstanceVarReflectionTestFixture() {
            this.typeIsSelf = this;
        }

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this);
        }
    }

    class SimpleReflectionStaticFieldsFixture {

        static final String staticString = "staticString";

        static final int staticInt = 12345;
    }

    static class SimpleReflectionTestFixture {

        Object o;

        SimpleReflectionTestFixture() {
        }

        SimpleReflectionTestFixture(final Object o) {
            this.o = o;
        }

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this);
        }
    }

    private static final int ARRAYLIST_INITIAL_CAPACITY = 10;

    private final Integer base = Integer.valueOf(5);

    private final String baseStr = base.getClass().getName() + "@" + Integer.toHexString(System.identityHashCode(base));

    public void assertReflectionArray(final String expected, final Object actual) {
        if (actual == null) {
            return;
        }
        assertEquals(expected, ToStringBuilder.reflectionToString(actual));
        assertEquals(expected, ToStringBuilder.reflectionToString(actual, null));
        assertEquals(expected, ToStringBuilder.reflectionToString(actual, null, true));
        assertEquals(expected, ToStringBuilder.reflectionToString(actual, null, false));
    }

    private String toBaseString(final Object o) {
        return o.getClass().getName() + "@" + Integer.toHexString(System.identityHashCode(o));
    }

    public <T> String toStringWithStatics(final T object, final ToStringStyle style, final Class<? super T> reflectUpToClass) {
        return ReflectionToStringBuilder.toString(object, style, false, true, reflectUpToClass);
    }

    @Test
    public void testAppendAsObjectToString_1() {
        final String objectToAppend1 = "";
        assertEquals(baseStr + "[" + toBaseString(objectToAppend1) + "]", new ToStringBuilder(base).appendAsObjectToString(objectToAppend1).toString());
    }

    @Test
    public void testAppendAsObjectToString_2() {
        final Boolean objectToAppend2 = Boolean.TRUE;
        assertEquals(baseStr + "[" + toBaseString(objectToAppend2) + "]", new ToStringBuilder(base).appendAsObjectToString(objectToAppend2).toString());
    }

    @Test
    public void testAppendAsObjectToString_3() {
        final Object objectToAppend3 = new Object();
        assertEquals(baseStr + "[" + toBaseString(objectToAppend3) + "]", new ToStringBuilder(base).appendAsObjectToString(objectToAppend3).toString());
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
    public void testAppendToString_1() {
        assertEquals(baseStr + "[]", new ToStringBuilder(base).appendToString("Integer@8888[]").toString());
    }

    @Test
    public void testAppendToString_2() {
        assertEquals(baseStr + "[<null>]", new ToStringBuilder(base).appendToString("Integer@8888[<null>]").toString());
    }

    @Test
    public void testAppendToString_3() {
        assertEquals(baseStr + "[a=hello]", new ToStringBuilder(base).appendToString("Integer@8888[]").append("a", "hello").toString());
    }

    @Test
    public void testAppendToString_4() {
        assertEquals(baseStr + "[<null>,a=hello]", new ToStringBuilder(base).appendToString("Integer@8888[<null>]").append("a", "hello").toString());
    }

    @Test
    public void testAppendToString_5() {
        assertEquals(baseStr + "[a=hello]", new ToStringBuilder(base).appendToString(null).append("a", "hello").toString());
    }

    @Test
    public void testBoolean_1() {
        assertEquals(baseStr + "[true]", new ToStringBuilder(base).append(true).toString());
    }

    @Test
    public void testBoolean_2() {
        assertEquals(baseStr + "[a=true]", new ToStringBuilder(base).append("a", true).toString());
    }

    @Test
    public void testBoolean_3() {
        assertEquals(baseStr + "[a=true,b=false]", new ToStringBuilder(base).append("a", true).append("b", false).toString());
    }

    @Test
    public void testByte_1() {
        assertEquals(baseStr + "[3]", new ToStringBuilder(base).append((byte) 3).toString());
    }

    @Test
    public void testByte_2() {
        assertEquals(baseStr + "[a=3]", new ToStringBuilder(base).append("a", (byte) 3).toString());
    }

    @Test
    public void testByte_3() {
        assertEquals(baseStr + "[a=3,b=4]", new ToStringBuilder(base).append("a", (byte) 3).append("b", (byte) 4).toString());
    }

    @Test
    public void testChar_1() {
        assertEquals(baseStr + "[A]", new ToStringBuilder(base).append((char) 65).toString());
    }

    @Test
    public void testChar_2() {
        assertEquals(baseStr + "[a=A]", new ToStringBuilder(base).append("a", (char) 65).toString());
    }

    @Test
    public void testChar_3() {
        assertEquals(baseStr + "[a=A,b=B]", new ToStringBuilder(base).append("a", (char) 65).append("b", (char) 66).toString());
    }

    @Test
    public void testConstructToStringBuilder_1_testMerged_1() {
        final ToStringBuilder stringBuilder1 = new ToStringBuilder(base, null, null);
        assertEquals(ToStringStyle.DEFAULT_STYLE, stringBuilder1.getStyle());
        assertNotNull(stringBuilder1.getStringBuffer());
        assertNotNull(stringBuilder1.toString());
    }

    @Test
    public void testConstructToStringBuilder_4_testMerged_2() {
        final ToStringBuilder stringBuilder2 = new ToStringBuilder(base, ToStringStyle.DEFAULT_STYLE, new StringBuffer(1024));
        assertEquals(ToStringStyle.DEFAULT_STYLE, stringBuilder2.getStyle());
        assertNotNull(stringBuilder2.getStringBuffer());
        assertNotNull(stringBuilder2.toString());
    }

    @Test
    public void testDouble_1() {
        assertEquals(baseStr + "[3.2]", new ToStringBuilder(base).append(3.2).toString());
    }

    @Test
    public void testDouble_2() {
        assertEquals(baseStr + "[a=3.2]", new ToStringBuilder(base).append("a", 3.2).toString());
    }

    @Test
    public void testDouble_3() {
        assertEquals(baseStr + "[a=3.2,b=4.3]", new ToStringBuilder(base).append("a", 3.2).append("b", 4.3).toString());
    }

    @Test
    public void testFloat_1() {
        assertEquals(baseStr + "[3.2]", new ToStringBuilder(base).append((float) 3.2).toString());
    }

    @Test
    public void testFloat_2() {
        assertEquals(baseStr + "[a=3.2]", new ToStringBuilder(base).append("a", (float) 3.2).toString());
    }

    @Test
    public void testFloat_3() {
        assertEquals(baseStr + "[a=3.2,b=4.3]", new ToStringBuilder(base).append("a", (float) 3.2).append("b", (float) 4.3).toString());
    }

    @Test
    public void testInt_1() {
        assertEquals(baseStr + "[3]", new ToStringBuilder(base).append(3).toString());
    }

    @Test
    public void testInt_2() {
        assertEquals(baseStr + "[a=3]", new ToStringBuilder(base).append("a", 3).toString());
    }

    @Test
    public void testInt_3() {
        assertEquals(baseStr + "[a=3,b=4]", new ToStringBuilder(base).append("a", 3).append("b", 4).toString());
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
    public void testShort_1() {
        assertEquals(baseStr + "[3]", new ToStringBuilder(base).append((short) 3).toString());
    }

    @Test
    public void testShort_2() {
        assertEquals(baseStr + "[a=3]", new ToStringBuilder(base).append("a", (short) 3).toString());
    }

    @Test
    public void testShort_3() {
        assertEquals(baseStr + "[a=3,b=4]", new ToStringBuilder(base).append("a", (short) 3).append("b", (short) 4).toString());
    }
}
