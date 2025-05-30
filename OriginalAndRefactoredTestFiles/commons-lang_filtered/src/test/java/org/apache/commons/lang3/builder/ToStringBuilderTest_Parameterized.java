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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class ToStringBuilderTest_Parameterized extends AbstractLangTest {

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
    public void testAppendSuper_5() {
        assertEquals(baseStr + "[a=hello]", new ToStringBuilder(base).appendSuper(null).append("a", "hello").toString());
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
    @MethodSource("Provider_testAppendToString_1to2")
    public void testAppendToString_1to2(String param1, String param2) {
        assertEquals(baseStr + param1, new ToStringBuilder(base).appendToString(param2).toString());
    }

    static public Stream<Arguments> Provider_testAppendToString_1to2() {
        return Stream.of(arguments("[]", "Integer@8888[]"), arguments("[<null>]", "Integer@8888[<null>]"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testAppendToString_3to4")
    public void testAppendToString_3to4(String param1, String param2, String param3, String param4) {
        assertEquals(baseStr + param1, new ToStringBuilder(base).appendToString(param4).append(param2, param3).toString());
    }

    static public Stream<Arguments> Provider_testAppendToString_3to4() {
        return Stream.of(arguments("[a=hello]", "a", "hello", "Integer@8888[]"), arguments("[<null>,a=hello]", "a", "hello", "Integer@8888[<null>]"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testByte_1_1_1_1")
    public void testByte_1_1_1_1(String param1, int param2) {
        assertEquals(baseStr + param1, new ToStringBuilder(base).append((byte) param2).toString());
    }

    static public Stream<Arguments> Provider_testByte_1_1_1_1() {
        return Stream.of(arguments("[3]", 3), arguments("[A]", 65), arguments("[3.2]", 3.2), arguments("[3]", 3));
    }

    @ParameterizedTest
    @MethodSource("Provider_testByte_2_2_2_2")
    public void testByte_2_2_2_2(String param1, String param2, int param3) {
        assertEquals(baseStr + param1, new ToStringBuilder(base).append(param2, (byte) param3).toString());
    }

    static public Stream<Arguments> Provider_testByte_2_2_2_2() {
        return Stream.of(arguments("[a=3]", "a", 3), arguments("[a=A]", "a", 65), arguments("[a=3.2]", "a", 3.2), arguments("[a=3]", "a", 3));
    }

    @ParameterizedTest
    @MethodSource("Provider_testByte_3_3_3_3")
    public void testByte_3_3_3_3(String param1, String param2, String param3, int param4, int param5) {
        assertEquals(baseStr + param1, new ToStringBuilder(base).append(param3, (byte) param5).append(param2, (byte) param4).toString());
    }

    static public Stream<Arguments> Provider_testByte_3_3_3_3() {
        return Stream.of(arguments("[a=3,b=4]", "b", "a", 4, 3), arguments("[a=A,b=B]", "b", "a", 66, 65), arguments("[a=3.2,b=4.3]", "b", "a", 4.3, 3.2), arguments("[a=3,b=4]", "b", "a", 4, 3));
    }

    @ParameterizedTest
    @MethodSource("Provider_testDouble_1_1_1")
    public void testDouble_1_1_1(String param1, double param2) {
        assertEquals(baseStr + param1, new ToStringBuilder(base).append(param2).toString());
    }

    static public Stream<Arguments> Provider_testDouble_1_1_1() {
        return Stream.of(arguments("[3.2]", 3.2), arguments("[3]", 3), arguments("[3]", 3L));
    }

    @ParameterizedTest
    @MethodSource("Provider_testDouble_2_2_2")
    public void testDouble_2_2_2(String param1, String param2, double param3) {
        assertEquals(baseStr + param1, new ToStringBuilder(base).append(param2, param3).toString());
    }

    static public Stream<Arguments> Provider_testDouble_2_2_2() {
        return Stream.of(arguments("[a=3.2]", "a", 3.2), arguments("[a=3]", "a", 3), arguments("[a=3]", "a", 3L));
    }

    @ParameterizedTest
    @MethodSource("Provider_testDouble_3_3_3")
    public void testDouble_3_3_3(String param1, String param2, double param3, String param4, double param5) {
        assertEquals(baseStr + param1, new ToStringBuilder(base).append(param4, param5).append(param2, param3).toString());
    }

    static public Stream<Arguments> Provider_testDouble_3_3_3() {
        return Stream.of(arguments("[a=3.2,b=4.3]", "b", 4.3, "a", 3.2), arguments("[a=3,b=4]", "b", 4, "a", 3), arguments("[a=3,b=4]", "b", 4L, "a", 3L));
    }
}
