package org.eclipse.collections.impl.block.factory.primitive;

import org.eclipse.collections.api.list.primitive.CharList;
import org.eclipse.collections.impl.block.predicate.primitive.CharPredicate;
import org.eclipse.collections.impl.factory.primitive.CharLists;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@Deprecated
public class CharPredicateTest_Parameterized {

    private static void assertTrueHelper(CharList charList, CharPredicate predicate) {
        charList.forEach(element -> assertTrue(predicate.accept(element)));
    }

    private static void assertFalseHelper(CharList charList, CharPredicate predicate) {
        charList.forEach(element -> assertFalse(predicate.accept(element)));
    }

    @Test
    public void isUpperCase_1() {
        assertTrueHelper(CharLists.mutable.of('A', 'B', 'C'), CharPredicate.IS_UPPERCASE);
    }

    @Test
    public void isUpperCase_2() {
        assertFalseHelper(CharLists.mutable.of('a', 'b', 'c', '1', '.'), CharPredicate.IS_UPPERCASE);
    }

    @Test
    public void isLowerCase_1() {
        assertTrueHelper(CharLists.mutable.of('a', 'b', 'c'), CharPredicate.IS_LOWERCASE);
    }

    @Test
    public void isLowerCase_2() {
        assertFalseHelper(CharLists.mutable.of('A', 'B', 'C', '1', '.'), CharPredicate.IS_LOWERCASE);
    }

    @Test
    public void isDigit_1() {
        assertTrueHelper(CharLists.mutable.of('0', '1', '2', '3'), CharPredicate.IS_DIGIT);
    }

    @Test
    public void isDigitOrDot_1() {
        assertTrueHelper(CharLists.mutable.of('0', '1', '2', '3', '.'), CharPredicate.IS_DIGIT_OR_DOT);
    }

    @Test
    public void isLetter_3() {
        assertFalseHelper(CharLists.mutable.of('0', '1', '2', '3', '.'), CharPredicate.IS_LETTER);
    }

    @Test
    public void isLetterOrDigit_3() {
        assertFalseHelper(CharLists.mutable.of('.', '$', '*'), CharPredicate.IS_LETTER_OR_DIGIT);
    }

    @Test
    public void isWhitespace_1() {
        assertTrueHelper(CharLists.mutable.of(' '), CharPredicate.IS_WHITESPACE);
    }

    @Test
    public void isWhitespace_4() {
        assertFalseHelper(CharLists.mutable.of('.', '$', '*'), CharPredicate.IS_WHITESPACE);
    }

    @Test
    public void isUndefined_1() {
        assertTrue(CharPredicates.isUndefined().accept((char) 888));
    }

    @Test
    public void isUndefined_4() {
        assertFalseHelper(CharLists.mutable.of('.', '$', '*'), CharPredicate.IS_UNDEFINED);
    }

    @ParameterizedTest
    @MethodSource("Provider_isDigit_2to3")
    public void isDigit_2to3(String param1, String param2, String param3, String param4) {
        assertFalseHelper(CharLists.mutable.of(param1, param2, param3, param4), CharPredicate.IS_DIGIT);
    }

    static public Stream<Arguments> Provider_isDigit_2to3() {
        return Stream.of(arguments("A", "B", "C", "."), arguments("a", "b", "c", "."));
    }

    @ParameterizedTest
    @MethodSource("Provider_isDigitOrDot_2to3")
    public void isDigitOrDot_2to3(String param1, String param2, String param3) {
        assertFalseHelper(CharLists.mutable.of(param1, param2, param3), CharPredicate.IS_DIGIT_OR_DOT);
    }

    static public Stream<Arguments> Provider_isDigitOrDot_2to3() {
        return Stream.of(arguments("A", "B", "C"), arguments("a", "b", "c"));
    }

    @ParameterizedTest
    @MethodSource("Provider_isLetter_1to2")
    public void isLetter_1to2(String param1, String param2, String param3) {
        assertTrueHelper(CharLists.mutable.of(param1, param2, param3), CharPredicate.IS_LETTER);
    }

    static public Stream<Arguments> Provider_isLetter_1to2() {
        return Stream.of(arguments("A", "B", "C"), arguments("a", "b", "c"));
    }

    @ParameterizedTest
    @MethodSource("Provider_isLetterOrDigit_1to2")
    public void isLetterOrDigit_1to2(String param1, String param2, String param3, int param4, int param5, int param6, int param7) {
        assertTrueHelper(CharLists.mutable.of(param1, param2, param3, param4, param5, param6, param7), CharPredicate.IS_LETTER_OR_DIGIT);
    }

    static public Stream<Arguments> Provider_isLetterOrDigit_1to2() {
        return Stream.of(arguments("A", "B", "C", 0, 1, 2, 3), arguments("a", "b", "c", 0, 1, 2, 3));
    }

    @ParameterizedTest
    @MethodSource("Provider_isWhitespace_2to3")
    public void isWhitespace_2to3(String param1, String param2, String param3, int param4, int param5, int param6, int param7) {
        assertFalseHelper(CharLists.mutable.of(param1, param2, param3, param4, param5, param6, param7), CharPredicate.IS_WHITESPACE);
    }

    static public Stream<Arguments> Provider_isWhitespace_2to3() {
        return Stream.of(arguments("A", "B", "C", 0, 1, 2, 3), arguments("a", "b", "c", 0, 1, 2, 3));
    }

    @ParameterizedTest
    @MethodSource("Provider_isUndefined_2to3")
    public void isUndefined_2to3(String param1, String param2, String param3, int param4, int param5, int param6, int param7) {
        assertFalseHelper(CharLists.mutable.of(param1, param2, param3, param4, param5, param6, param7), CharPredicate.IS_UNDEFINED);
    }

    static public Stream<Arguments> Provider_isUndefined_2to3() {
        return Stream.of(arguments("A", "B", "C", 0, 1, 2, 3), arguments("a", "b", "c", 0, 1, 2, 3));
    }
}
