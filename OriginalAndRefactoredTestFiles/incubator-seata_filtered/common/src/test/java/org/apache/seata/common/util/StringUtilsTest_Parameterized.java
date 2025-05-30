package org.apache.seata.common.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.seata.common.Constants;
import org.apache.seata.common.holder.ObjectHolder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class StringUtilsTest_Parameterized {

    private Iterator<String> emptyIterator;

    private Iterator<String> singleElementIterator;

    private Iterator<String> multipleElementsIterator;

    @BeforeEach
    void setUp() {
        emptyIterator = Collections.emptyIterator();
        singleElementIterator = Collections.singletonList("Hello").iterator();
        multipleElementsIterator = Arrays.asList("Hello", "World", "Java").iterator();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @interface TestAnnotation {

        boolean test() default false;
    }

    interface TestInterface {

        void test();
    }

    abstract class TestAbstractClass {

        abstract void test();
    }

    @TestAnnotation(test = true)
    static class TestClass {

        public static boolean hashCodeTriggered = false;

        public static boolean toStringTriggered = false;

        private TestClass obj;

        private String s;

        @Override
        public int hashCode() {
            hashCodeTriggered = true;
            return super.hashCode();
        }

        @Override
        public String toString() {
            toStringTriggered = true;
            return StringUtils.toString(this);
        }

        public TestClass getObj() {
            return obj;
        }

        public void setObj(TestClass obj) {
            this.obj = obj;
        }
    }

    static class CycleDependency {

        public static boolean hashCodeTriggered = false;

        public static boolean toStringTriggered = false;

        public static final CycleDependency A = new CycleDependency("a");

        public static final CycleDependency B = new CycleDependency("b");

        private String s;

        private CycleDependency obj;

        private CycleDependency(String s) {
            this.s = s;
        }

        public CycleDependency getObj() {
            return obj;
        }

        public void setObj(CycleDependency obj) {
            this.obj = obj;
        }

        @Override
        public int hashCode() {
            hashCodeTriggered = true;
            return super.hashCode();
        }

        @Override
        public String toString() {
            toStringTriggered = true;
            return "(" + "s=" + s + "," + "obj=" + (obj != this ? String.valueOf(obj) : "(this CycleDependency)") + ')';
        }
    }

    @Test
    public void testIsNullOrEmpty_1() {
        assertThat(StringUtils.isNullOrEmpty(null)).isTrue();
    }

    @Test
    public void testIsNullOrEmpty_3() {
        assertThat(StringUtils.isNullOrEmpty("")).isTrue();
    }

    @Test
    public void testIsBlank_1() {
        assertThat(StringUtils.isBlank(null)).isTrue();
    }

    @Test
    public void testIsBlank_2() {
        assertThat(StringUtils.isBlank("abc")).isFalse();
    }

    @Test
    public void testIsNotBlank_1() {
        assertThat(StringUtils.isNotBlank(null)).isFalse();
    }

    @Test
    public void testIsNotBlank_2() {
        assertThat(StringUtils.isNotBlank("abc")).isTrue();
    }

    @Test
    public void testTrimToNull_1() {
        assertThat(StringUtils.trimToNull(null)).isNull();
    }

    @Test
    public void testTrimToNull_2() {
        assertThat(StringUtils.trimToNull("abc")).isEqualTo("abc");
    }

    @Test
    public void testTrim_1() {
        assertThat(StringUtils.trim(null)).isNull();
    }

    @Test
    public void testIsEmpty_1() {
        assertThat(StringUtils.isEmpty(null)).isTrue();
    }

    @Test
    public void testIsEmpty_3() {
        assertThat(StringUtils.isEmpty("")).isTrue();
    }

    @Test
    public void testIsNotEmpty_1() {
        assertThat(StringUtils.isNotEmpty(null)).isFalse();
    }

    @Test
    public void testIsNotEmpty_3() {
        assertThat(StringUtils.isNotEmpty("")).isFalse();
    }

    @Test
    public void testInputStream2String_1() throws IOException {
        assertNull(StringUtils.inputStream2String(null));
    }

    @Test
    public void testInputStream2String_2() throws IOException {
        String data = "abc\n" + ":\"klsdf\n" + "2ks,x:\".,-3sd˚ø≤ø¬≥";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(data.getBytes(Constants.DEFAULT_CHARSET));
        assertThat(StringUtils.inputStream2String(inputStream)).isEqualTo(data);
    }

    @Test
    void testEquals_1() {
        Assertions.assertTrue(StringUtils.equals("1", "1"));
    }

    @Test
    void testEquals_2() {
        Assertions.assertFalse(StringUtils.equals("1", "2"));
    }

    @Test
    void testEqualsIgnoreCase_4() {
        Assertions.assertFalse(StringUtils.equalsIgnoreCase("1", "2"));
    }

    @Test
    public void testHasLowerCase_1() {
        Assertions.assertFalse(StringUtils.hasLowerCase(null));
    }

    @Test
    public void testHasLowerCase_2() {
        Assertions.assertFalse(StringUtils.hasLowerCase("A"));
    }

    @Test
    public void testHasLowerCase_3() {
        Assertions.assertTrue(StringUtils.hasLowerCase("a"));
    }

    @Test
    public void testHasUpperCase_1() {
        Assertions.assertFalse(StringUtils.hasUpperCase(null));
    }

    @Test
    public void testHasUpperCase_2() {
        Assertions.assertFalse(StringUtils.hasUpperCase("a"));
    }

    @Test
    public void testHasUpperCase_3() {
        Assertions.assertTrue(StringUtils.hasUpperCase("A"));
    }

    @Test
    void hasLengthNullCharSequenceReturnsFalse_1() {
        String nullCharSequence = null;
        Assertions.assertFalse(StringUtils.hasLength(nullCharSequence));
    }

    @Test
    void hasLengthNullCharSequenceReturnsFalse_2() {
        String emptyCharSequence = "";
        Assertions.assertFalse(StringUtils.hasLength(emptyCharSequence));
    }

    @Test
    void hasLengthNullCharSequenceReturnsFalse_3() {
        String singleCharSequence = "a";
        Assertions.assertTrue(StringUtils.hasLength(singleCharSequence));
    }

    @Test
    void hasLengthNullCharSequenceReturnsFalse_4() {
        String multipleCharSequence = "abc";
        Assertions.assertTrue(StringUtils.hasLength(multipleCharSequence));
    }

    @Test
    void hasTextNullCharSequenceReturnsFalse_1() {
        String nullCharSequence = null;
        Assertions.assertFalse(StringUtils.hasText(nullCharSequence));
    }

    @Test
    void hasTextNullCharSequenceReturnsFalse_2() {
        String emptyCharSequence = "";
        Assertions.assertFalse(StringUtils.hasText(emptyCharSequence));
    }

    @Test
    void hasTextNullCharSequenceReturnsFalse_3() {
        String singleCharSequence = "a";
        Assertions.assertTrue(StringUtils.hasText(singleCharSequence));
    }

    @Test
    void hasTextNullCharSequenceReturnsFalse_4() {
        String multipleCharSequence = "abc";
        Assertions.assertTrue(StringUtils.hasText(multipleCharSequence));
    }

    @Test
    void hasTextNullCharSequenceReturnsFalse_5() {
        Assertions.assertFalse(StringUtils.hasText("   "));
    }

    @Test
    void hasTextNullCharSequenceReturnsFalse_6() {
        String whitespaceCharSequence = " a b c ";
        Assertions.assertTrue(StringUtils.hasText(whitespaceCharSequence));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsNullOrEmpty_2_4")
    public void testIsNullOrEmpty_2_4(String param1) {
        assertThat(StringUtils.isNullOrEmpty(param1)).isFalse();
    }

    static public Stream<Arguments> Provider_testIsNullOrEmpty_2_4() {
        return Stream.of(arguments("abc"), arguments(" "));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsBlank_3to4")
    public void testIsBlank_3to4(String param1) {
        assertThat(StringUtils.isBlank(param1)).isTrue();
    }

    static public Stream<Arguments> Provider_testIsBlank_3to4() {
        return Stream.of(arguments(""), arguments(" "));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsNotBlank_3to4")
    public void testIsNotBlank_3to4(String param1) {
        assertThat(StringUtils.isNotBlank(param1)).isFalse();
    }

    static public Stream<Arguments> Provider_testIsNotBlank_3to4() {
        return Stream.of(arguments(""), arguments(" "));
    }

    @ParameterizedTest
    @MethodSource("Provider_testTrimToNull_3to4")
    public void testTrimToNull_3to4(String param1) {
        assertThat(StringUtils.trimToNull(param1)).isNull();
    }

    static public Stream<Arguments> Provider_testTrimToNull_3to4() {
        return Stream.of(arguments(""), arguments(" "));
    }

    @ParameterizedTest
    @MethodSource("Provider_testTrim_2to4")
    public void testTrim_2to4(String param1, String param2) {
        assertThat(StringUtils.trim(param2)).isEqualTo(param1);
    }

    static public Stream<Arguments> Provider_testTrim_2to4() {
        return Stream.of(arguments("abc", "abc"), arguments("", ""), arguments("", " "));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsEmpty_2_4")
    public void testIsEmpty_2_4(String param1) {
        assertThat(StringUtils.isEmpty(param1)).isFalse();
    }

    static public Stream<Arguments> Provider_testIsEmpty_2_4() {
        return Stream.of(arguments("abc"), arguments(" "));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsNotEmpty_2_4")
    public void testIsNotEmpty_2_4(String param1) {
        assertThat(StringUtils.isNotEmpty(param1)).isTrue();
    }

    static public Stream<Arguments> Provider_testIsNotEmpty_2_4() {
        return Stream.of(arguments("abc"), arguments(" "));
    }

    @ParameterizedTest
    @MethodSource("Provider_testHump2Line_1to3")
    public void testHump2Line_1to3(String param1, String param2) {
        assertThat(StringUtils.hump2Line(param2).equals(param1)).isTrue();
    }

    static public Stream<Arguments> Provider_testHump2Line_1to3() {
        return Stream.of(arguments("abcD", "abc-d"), arguments("a-bc", "aBc"), arguments("abc", "abc"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testEquals_3_6")
    void testEquals_3_6(int param1) {
        Assertions.assertFalse(StringUtils.equals(param1, "1"));
    }

    static public Stream<Arguments> Provider_testEquals_3_6() {
        return Stream.of(arguments(1), arguments(""));
    }

    @ParameterizedTest
    @MethodSource("Provider_testEquals_4to5")
    void testEquals_4to5(int param1) {
        Assertions.assertFalse(StringUtils.equals(param1, null));
    }

    static public Stream<Arguments> Provider_testEquals_4to5() {
        return Stream.of(arguments(1), arguments(""));
    }

    @ParameterizedTest
    @MethodSource("Provider_testEqualsIgnoreCase_1to3")
    void testEqualsIgnoreCase_1to3(String param1, String param2) {
        Assertions.assertTrue(StringUtils.equalsIgnoreCase(param1, param2));
    }

    static public Stream<Arguments> Provider_testEqualsIgnoreCase_1to3() {
        return Stream.of(arguments("a", "a"), arguments("a", "A"), arguments("A", "a"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testEqualsIgnoreCase_5_8")
    void testEqualsIgnoreCase_5_8(int param1) {
        Assertions.assertFalse(StringUtils.equalsIgnoreCase(param1, "1"));
    }

    static public Stream<Arguments> Provider_testEqualsIgnoreCase_5_8() {
        return Stream.of(arguments(1), arguments(""));
    }

    @ParameterizedTest
    @MethodSource("Provider_testEqualsIgnoreCase_6to7")
    void testEqualsIgnoreCase_6to7(int param1) {
        Assertions.assertFalse(StringUtils.equalsIgnoreCase(param1, null));
    }

    static public Stream<Arguments> Provider_testEqualsIgnoreCase_6to7() {
        return Stream.of(arguments(1), arguments(""));
    }
}
