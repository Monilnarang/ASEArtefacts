package org.eclipse.collections.impl.block.factory.primitive;

import org.eclipse.collections.api.list.primitive.CharList;
import org.eclipse.collections.impl.block.predicate.primitive.CharPredicate;
import org.eclipse.collections.impl.factory.primitive.CharLists;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Deprecated
public class CharPredicateTest_Purified {

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
    public void isDigit_2() {
        assertFalseHelper(CharLists.mutable.of('A', 'B', 'C', '.'), CharPredicate.IS_DIGIT);
    }

    @Test
    public void isDigit_3() {
        assertFalseHelper(CharLists.mutable.of('a', 'b', 'c', '.'), CharPredicate.IS_DIGIT);
    }

    @Test
    public void isDigitOrDot_1() {
        assertTrueHelper(CharLists.mutable.of('0', '1', '2', '3', '.'), CharPredicate.IS_DIGIT_OR_DOT);
    }

    @Test
    public void isDigitOrDot_2() {
        assertFalseHelper(CharLists.mutable.of('A', 'B', 'C'), CharPredicate.IS_DIGIT_OR_DOT);
    }

    @Test
    public void isDigitOrDot_3() {
        assertFalseHelper(CharLists.mutable.of('a', 'b', 'c'), CharPredicate.IS_DIGIT_OR_DOT);
    }

    @Test
    public void isLetter_1() {
        assertTrueHelper(CharLists.mutable.of('A', 'B', 'C'), CharPredicate.IS_LETTER);
    }

    @Test
    public void isLetter_2() {
        assertTrueHelper(CharLists.mutable.of('a', 'b', 'c'), CharPredicate.IS_LETTER);
    }

    @Test
    public void isLetter_3() {
        assertFalseHelper(CharLists.mutable.of('0', '1', '2', '3', '.'), CharPredicate.IS_LETTER);
    }

    @Test
    public void isLetterOrDigit_1() {
        assertTrueHelper(CharLists.mutable.of('A', 'B', 'C', '0', '1', '2', '3'), CharPredicate.IS_LETTER_OR_DIGIT);
    }

    @Test
    public void isLetterOrDigit_2() {
        assertTrueHelper(CharLists.mutable.of('a', 'b', 'c', '0', '1', '2', '3'), CharPredicate.IS_LETTER_OR_DIGIT);
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
    public void isWhitespace_2() {
        assertFalseHelper(CharLists.mutable.of('A', 'B', 'C', '0', '1', '2', '3'), CharPredicate.IS_WHITESPACE);
    }

    @Test
    public void isWhitespace_3() {
        assertFalseHelper(CharLists.mutable.of('a', 'b', 'c', '0', '1', '2', '3'), CharPredicate.IS_WHITESPACE);
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
    public void isUndefined_2() {
        assertFalseHelper(CharLists.mutable.of('A', 'B', 'C', '0', '1', '2', '3'), CharPredicate.IS_UNDEFINED);
    }

    @Test
    public void isUndefined_3() {
        assertFalseHelper(CharLists.mutable.of('a', 'b', 'c', '0', '1', '2', '3'), CharPredicate.IS_UNDEFINED);
    }

    @Test
    public void isUndefined_4() {
        assertFalseHelper(CharLists.mutable.of('.', '$', '*'), CharPredicate.IS_UNDEFINED);
    }
}
