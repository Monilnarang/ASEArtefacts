package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class CaseUtilsTest_Parameterized {

    @Test
    public void testConstructor_1() {
        assertNotNull(new CaseUtils());
    }

    @Test
    public void testConstructor_2_testMerged_2() {
        final Constructor<?>[] cons = CaseUtils.class.getDeclaredConstructors();
        assertEquals(1, cons.length);
        assertTrue(Modifier.isPublic(cons[0].getModifiers()));
    }

    @Test
    public void testConstructor_4() {
        assertTrue(Modifier.isPublic(CaseUtils.class.getModifiers()));
    }

    @Test
    public void testConstructor_5() {
        assertFalse(Modifier.isFinal(CaseUtils.class.getModifiers()));
    }

    @Test
    public void testToCamelCase_1() {
        assertNull(CaseUtils.toCamelCase(null, false, null));
    }

    @Test
    public void testToCamelCase_8_testMerged_8() {
        final char[] chars = { '-', '+', ' ', '@' };
        assertEquals("", CaseUtils.toCamelCase("-+@ ", true, chars));
        assertEquals("toCamelCase", CaseUtils.toCamelCase("   to-CAMEL-cASE", false, chars));
        assertEquals("ToCamelCase", CaseUtils.toCamelCase("@@@@   to+CAMEL@cASE ", true, chars));
        assertEquals("ToCaMeLCase", CaseUtils.toCamelCase("To+CA+ME L@cASE", true, chars));
    }

    @ParameterizedTest
    @MethodSource("Provider_testToCamelCase_2to4_16to21")
    public void testToCamelCase_2to4_16to21(String param1, String param2) {
        assertEquals(param1, CaseUtils.toCamelCase(param1, true, null));
    }

    static public Stream<Arguments> Provider_testToCamelCase_2to4_16to21() {
        return Stream.of(arguments("", ""), arguments("", "  "), arguments("aBC@def", "a  b  c  @def"), arguments("ToCamelCase", "TO CAMEL CASE"), arguments("toCamelCase", "TO CAMEL CASE"), arguments("toCamelCase", "TO CAMEL CASE"), arguments("tocamelcase", "tocamelcase"), arguments("Tocamelcase", "tocamelcase"), arguments("tocamelcase", "Tocamelcase"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testToCamelCase_5_22to24")
    public void testToCamelCase_5_22to24(String param1, String param2) {
        assertEquals(param1, CaseUtils.toCamelCase("a b c @def", true));
    }

    static public Stream<Arguments> Provider_testToCamelCase_5_22to24() {
        return Stream.of(arguments("ABC@def", "a b c @def"), arguments("Tocamelcase", "tocamelcase"), arguments("tocamelcase", "tocamelcase"), arguments("\uD800\uDF00\uD800\uDF02", "\uD800\uDF00 \uD800\uDF02"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testToCamelCase_6to7_12")
    public void testToCamelCase_6to7_12(String param1, String param2, String param3) {
        assertEquals(param1, CaseUtils.toCamelCase("a b c @def", true, '-'));
    }

    static public Stream<Arguments> Provider_testToCamelCase_6to7_12() {
        return Stream.of(arguments("ABC@def", "a b c @def", "-"), arguments("ABC@def", "a b c @def", "-"), arguments("toCamelCase", "To.Camel.Case", "."));
    }

    @ParameterizedTest
    @MethodSource("Provider_testToCamelCase_13to15_25")
    public void testToCamelCase_13to15_25(String param1, String param2, String param3, String param4) {
        assertEquals(param1, CaseUtils.toCamelCase("To.Camel-Case", false, '-', '.'));
    }

    static public Stream<Arguments> Provider_testToCamelCase_13to15_25() {
        return Stream.of(arguments("toCamelCase", "To.Camel-Case", "-", "."), arguments("toCamelCase", " to @ Camel case", "-", "@"), arguments("ToCamelCase", " @to @ Camel case", "-", "@"), arguments("\uD800\uDF00\uD800\uDF01\uD800\uDF02\uD800\uDF03", "\uD800\uDF00\uD800\uDF01\uD800\uDF14\uD800\uDF02\uD800\uDF03", "\uD800", "\uDF14"));
    }
}
