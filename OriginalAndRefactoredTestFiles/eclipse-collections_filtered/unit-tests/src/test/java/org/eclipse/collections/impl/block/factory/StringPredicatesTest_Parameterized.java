package org.eclipse.collections.impl.block.factory;

import org.eclipse.collections.api.block.predicate.Predicate;
import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class StringPredicatesTest_Parameterized {

    @Test
    public void startsWith_1() {
        assertFalse(StringPredicates.startsWith("Hello").accept(null));
    }

    @Test
    public void startsWith_2() {
        assertTrue(StringPredicates.startsWith("Hello").accept("HelloWorld"));
    }

    @Test
    public void startsWith_3() {
        assertFalse(StringPredicates.startsWith("World").accept("HelloWorld"));
    }

    @Test
    public void startsWith_4() {
        assertEquals("StringPredicates.startsWith(\"Hello\")", StringPredicates.startsWith("Hello").toString());
    }

    @Test
    public void endsWith_1() {
        assertFalse(StringPredicates.endsWith("Hello").accept(null));
    }

    @Test
    public void endsWith_2() {
        assertFalse(StringPredicates.endsWith("Hello").accept("HelloWorld"));
    }

    @Test
    public void endsWith_3() {
        assertTrue(StringPredicates.endsWith("World").accept("HelloWorld"));
    }

    @Test
    public void endsWith_4() {
        assertEquals("StringPredicates.endsWith(\"Hello\")", StringPredicates.endsWith("Hello").toString());
    }

    @Test
    public void equalsIgnoreCase_1() {
        assertFalse(StringPredicates.equalsIgnoreCase("HELLO").accept(null));
    }

    @Test
    public void equalsIgnoreCase_4() {
        assertFalse(StringPredicates.equalsIgnoreCase("Hello").accept("World"));
    }

    @Test
    public void equalsIgnoreCase_5() {
        assertEquals("StringPredicates.equalsIgnoreCase(\"Hello\")", StringPredicates.equalsIgnoreCase("Hello").toString());
    }

    @Test
    public void containsString_1() {
        assertTrue(StringPredicates.contains("Hello").accept("WorldHelloWorld"));
    }

    @Test
    public void containsString_2() {
        assertTrue(StringPredicates.contains("Hello").and(StringPredicates.contains("World")).accept("WorldHelloWorld"));
    }

    @Test
    public void containsString_3() {
        assertFalse(StringPredicates.contains("Goodbye").accept("WorldHelloWorld"));
    }

    @Test
    public void containsString_4() {
        assertEquals("StringPredicates.contains(\"Hello\")", StringPredicates.contains("Hello").toString());
    }

    @Test
    public void containsCharacter_1() {
        assertTrue(StringPredicates.contains("H".charAt(0)).accept("WorldHelloWorld"));
    }

    @Test
    public void containsCharacter_2() {
        assertFalse(StringPredicates.contains("B".charAt(0)).accept("WorldHelloWorld"));
    }

    @Test
    public void containsCharacter_3() {
        assertEquals("StringPredicates.contains(\"H\")", StringPredicates.contains("H".charAt(0)).toString());
    }

    @Test
    public void emptyAndNotEmpty_1() {
        assertFalse(StringPredicates.empty().accept("WorldHelloWorld"));
    }

    @Test
    public void emptyAndNotEmpty_2() {
        assertEquals("StringPredicates.empty()", StringPredicates.empty().toString());
    }

    @Test
    public void emptyAndNotEmpty_3() {
        assertTrue(StringPredicates.notEmpty().accept("WorldHelloWorld"));
    }

    @Test
    public void emptyAndNotEmpty_4() {
        assertEquals("StringPredicates.notEmpty()", StringPredicates.notEmpty().toString());
    }

    @Test
    public void emptyAndNotEmpty_5() {
        assertTrue(StringPredicates.empty().accept(""));
    }

    @Test
    public void emptyAndNotEmpty_6() {
        assertFalse(StringPredicates.notEmpty().accept(""));
    }

    @Test
    public void lessThan_1() {
        assertTrue(StringPredicates.lessThan("b").accept("a"));
    }

    @Test
    public void lessThan_4() {
        assertEquals("StringPredicates.lessThan(\"b\")", StringPredicates.lessThan("b").toString());
    }

    @Test
    public void lessThanOrEqualTo_3() {
        assertFalse(StringPredicates.lessThanOrEqualTo("b").accept("c"));
    }

    @Test
    public void lessThanOrEqualTo_4() {
        assertEquals("StringPredicates.lessThanOrEqualTo(\"b\")", StringPredicates.lessThanOrEqualTo("b").toString());
    }

    @Test
    public void greaterThan_3() {
        assertTrue(StringPredicates.greaterThan("b").accept("c"));
    }

    @Test
    public void greaterThan_4() {
        assertEquals("StringPredicates.greaterThan(\"b\")", StringPredicates.greaterThan("b").toString());
    }

    @Test
    public void greaterThanOrEqualTo_1() {
        assertFalse(StringPredicates.greaterThanOrEqualTo("b").accept("a"));
    }

    @Test
    public void greaterThanOrEqualTo_4() {
        assertEquals("StringPredicates.greaterThanOrEqualTo(\"b\")", StringPredicates.greaterThanOrEqualTo("b").toString());
    }

    @Test
    public void matches_1() {
        assertTrue(StringPredicates.matches("a*b*").accept("aaaaabbbbb"));
    }

    @Test
    public void matches_2() {
        assertFalse(StringPredicates.matches("a*b").accept("ba"));
    }

    @Test
    public void matches_3() {
        assertEquals("StringPredicates.matches(\"a*b\")", StringPredicates.matches("a*b").toString());
    }

    @Test
    public void size_2() {
        assertFalse(StringPredicates.size(0).accept("a"));
    }

    @Test
    public void size_4() {
        assertEquals("StringPredicates.size(2)", StringPredicates.size(2).toString());
    }

    @Test
    public void hasLetters_1() {
        assertTrue(StringPredicates.hasLetters().accept("a2a"));
    }

    @Test
    public void hasLetters_2() {
        assertFalse(StringPredicates.hasLetters().accept("222"));
    }

    @Test
    public void hasLetters_3() {
        assertEquals("StringPredicates.hasLetters()", StringPredicates.hasLetters().toString());
    }

    @Test
    public void hasDigits_1() {
        assertFalse(StringPredicates.hasDigits().accept("aaa"));
    }

    @Test
    public void hasDigits_2() {
        assertTrue(StringPredicates.hasDigits().accept("a22"));
    }

    @Test
    public void hasDigits_3() {
        assertEquals("StringPredicates.hasDigits()", StringPredicates.hasDigits().toString());
    }

    @ParameterizedTest
    @MethodSource("Provider_equalsIgnoreCase_2to3")
    public void equalsIgnoreCase_2to3(String param1, String param2) {
        assertTrue(StringPredicates.equalsIgnoreCase(param2).accept(param1));
    }

    static public Stream<Arguments> Provider_equalsIgnoreCase_2to3() {
        return Stream.of(arguments("hello", "HELLO"), arguments("WORLD", "world"));
    }

    @ParameterizedTest
    @MethodSource("Provider_lessThan_2to3")
    public void lessThan_2to3(String param1, String param2) {
        assertFalse(StringPredicates.lessThan(param2).accept(param1));
    }

    static public Stream<Arguments> Provider_lessThan_2to3() {
        return Stream.of(arguments("b", "b"), arguments("c", "b"));
    }

    @ParameterizedTest
    @MethodSource("Provider_lessThanOrEqualTo_1to2")
    public void lessThanOrEqualTo_1to2(String param1, String param2) {
        assertTrue(StringPredicates.lessThanOrEqualTo(param2).accept(param1));
    }

    static public Stream<Arguments> Provider_lessThanOrEqualTo_1to2() {
        return Stream.of(arguments("a", "b"), arguments("b", "b"));
    }

    @ParameterizedTest
    @MethodSource("Provider_greaterThan_1to2")
    public void greaterThan_1to2(String param1, String param2) {
        assertFalse(StringPredicates.greaterThan(param2).accept(param1));
    }

    static public Stream<Arguments> Provider_greaterThan_1to2() {
        return Stream.of(arguments("a", "b"), arguments("b", "b"));
    }

    @ParameterizedTest
    @MethodSource("Provider_greaterThanOrEqualTo_2to3")
    public void greaterThanOrEqualTo_2to3(String param1, String param2) {
        assertTrue(StringPredicates.greaterThanOrEqualTo(param2).accept(param1));
    }

    static public Stream<Arguments> Provider_greaterThanOrEqualTo_2to3() {
        return Stream.of(arguments("b", "b"), arguments("c", "b"));
    }

    @ParameterizedTest
    @MethodSource("Provider_size_1_3")
    public void size_1_3(String param1, int param2) {
        assertTrue(StringPredicates.size(param2).accept(param1));
    }

    static public Stream<Arguments> Provider_size_1_3() {
        return Stream.of(arguments("a", 1), arguments("ab", 2));
    }
}
