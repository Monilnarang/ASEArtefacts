package org.eclipse.collections.impl.utility;

import org.eclipse.collections.api.bag.MutableBag;
import org.eclipse.collections.api.block.function.Function;
import org.eclipse.collections.api.block.procedure.primitive.CharProcedure;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.list.primitive.CharList;
import org.eclipse.collections.api.list.primitive.ImmutableCharList;
import org.eclipse.collections.api.list.primitive.IntList;
import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.api.set.primitive.ImmutableCharSet;
import org.eclipse.collections.api.set.primitive.ImmutableIntSet;
import org.eclipse.collections.api.tuple.Twin;
import org.eclipse.collections.impl.block.factory.Functions;
import org.eclipse.collections.impl.block.factory.Procedures;
import org.eclipse.collections.impl.block.factory.primitive.CharPredicates;
import org.eclipse.collections.impl.block.factory.primitive.CharToCharFunctions;
import org.eclipse.collections.impl.block.function.AddFunction;
import org.eclipse.collections.impl.block.function.primitive.CodePointFunction;
import org.eclipse.collections.impl.block.predicate.CodePointPredicate;
import org.eclipse.collections.impl.block.procedure.primitive.CodePointProcedure;
import org.eclipse.collections.impl.factory.primitive.CharSets;
import org.eclipse.collections.impl.factory.primitive.IntSets;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.list.mutable.primitive.CharArrayList;
import org.eclipse.collections.impl.set.mutable.UnifiedSet;
import org.eclipse.collections.impl.string.immutable.CharAdapter;
import org.eclipse.collections.impl.string.immutable.CodePointAdapter;
import org.eclipse.collections.impl.string.immutable.CodePointList;
import org.eclipse.collections.impl.test.Verify;
import org.eclipse.collections.impl.tuple.Tuples;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class StringIterateTest_Purified {

    public static final String THE_QUICK_BROWN_FOX_JUMPS_OVER_THE_LAZY_DOG = "The quick brown fox jumps over the lazy dog.";

    public static final String ALPHABET_LOWERCASE = "abcdefghijklmnopqrstuvwxyz";

    public static final Twin<String> HALF_ABET = StringIterate.splitAtIndex(ALPHABET_LOWERCASE, 13);

    public static final String TQBFJOTLD_MINUS_HALF_ABET_1 = "t qu rown ox ups ovr t zy o.";

    public static final String TQBFJOTLD_MINUS_HALF_ABET_2 = "he ick b f jm e he la dg.";

    @Test
    public void englishToUpperLowerCase_1() {
        assertEquals("ABC", StringIterate.englishToUpperCase("abc"));
    }

    @Test
    public void englishToUpperLowerCase_2() {
        assertEquals("abc", StringIterate.englishToLowerCase("ABC"));
    }

    @Test
    public void collect_1() {
        assertEquals("ABC", StringIterate.collect("abc", CharToCharFunctions.toUpperCase()));
    }

    @Test
    public void collect_2() {
        assertEquals("abc", StringIterate.collect("abc", CharToCharFunctions.toLowerCase()));
    }

    @Test
    public void collectCodePoint_1() {
        assertEquals("ABC", StringIterate.collect("abc", CodePointFunction.TO_UPPERCASE));
    }

    @Test
    public void collectCodePoint_2() {
        assertEquals("abc", StringIterate.collect("abc", CodePointFunction.TO_LOWERCASE));
    }

    @Test
    public void collectCodePointUnicode_1() {
        assertEquals("\u3042\uD840\uDC00\u3044\uD840\uDC03\u3046\uD83D\uDE09", StringIterate.collect("\u3042\uD840\uDC00\u3044\uD840\uDC03\u3046\uD83D\uDE09", CodePointFunction.PASS_THRU));
    }

    @Test
    public void collectCodePointUnicode_2() {
        assertEquals("\u3042\uD840\uDC00\u3044\uD840\uDC03\u3046\uD83D\uDE09", StringIterate.collect("\u3042\uD840\uDC00\u3044\uD840\uDC03\u3046\uD83D\uDE09", CodePointFunction.PASS_THRU));
    }

    @Test
    public void englishToUpperCase_1() {
        assertEquals("ABC", StringIterate.englishToUpperCase("abc"));
    }

    @Test
    public void englishToUpperCase_2() {
        assertEquals("A,B,C", StringIterate.englishToUpperCase("a,b,c"));
    }

    @Test
    public void englishToUpperCase_3() {
        assertSame("A,B,C", StringIterate.englishToUpperCase("A,B,C"));
    }

    @Test
    public void englishToLowerCase_1() {
        assertEquals("abc", StringIterate.englishToLowerCase("ABC"));
    }

    @Test
    public void englishToLowerCase_2() {
        assertEquals("a,b,c", StringIterate.englishToLowerCase("A,B,C"));
    }

    @Test
    public void englishToLowerCase_3() {
        assertSame("a,b,c", StringIterate.englishToLowerCase("a,b,c"));
    }

    @Test
    public void allSatisfy_1() {
        assertTrue(StringIterate.allSatisfy("MARY", CharPredicates.isUpperCase()));
    }

    @Test
    public void allSatisfy_2() {
        assertFalse(StringIterate.allSatisfy("Mary", CharPredicates.isUpperCase()));
    }

    @Test
    public void allSatisfyCodePoint_1() {
        assertTrue(StringIterate.allSatisfy("MARY", CodePointPredicate.IS_UPPERCASE));
    }

    @Test
    public void allSatisfyCodePoint_2() {
        assertFalse(StringIterate.allSatisfy("Mary", CodePointPredicate.IS_UPPERCASE));
    }

    @Test
    public void allSatisfyCodePointUnicode_1() {
        assertTrue(StringIterate.allSatisfy("\u3042\u3044\u3046", CodePointPredicate.IS_BMP));
    }

    @Test
    public void allSatisfyCodePointUnicode_2() {
        assertFalse(StringIterate.allSatisfy("\uD840\uDC00\uD840\uDC03\uD83D\uDE09", CodePointPredicate.IS_BMP));
    }

    @Test
    public void anySatisfy_1() {
        assertTrue(StringIterate.anySatisfy("MARY", CharPredicates.isUpperCase()));
    }

    @Test
    public void anySatisfy_2() {
        assertFalse(StringIterate.anySatisfy("mary", CharPredicates.isUpperCase()));
    }

    @Test
    public void anySatisfyCodePoint_1() {
        assertTrue(StringIterate.anySatisfy("MARY", CodePointPredicate.IS_UPPERCASE));
    }

    @Test
    public void anySatisfyCodePoint_2() {
        assertFalse(StringIterate.anySatisfy("mary", CodePointPredicate.IS_UPPERCASE));
    }

    @Test
    public void anySatisfyCodePointUnicode_1() {
        assertTrue(StringIterate.anySatisfy("\u3042\u3044\u3046", CodePointPredicate.IS_BMP));
    }

    @Test
    public void anySatisfyCodePointUnicode_2() {
        assertFalse(StringIterate.anySatisfy("\uD840\uDC00\uD840\uDC03\uD83D\uDE09", CodePointPredicate.IS_BMP));
    }

    @Test
    public void noneSatisfy_1() {
        assertFalse(StringIterate.noneSatisfy("MaRy", CharPredicates.isUpperCase()));
    }

    @Test
    public void noneSatisfy_2() {
        assertTrue(StringIterate.noneSatisfy("mary", CharPredicates.isUpperCase()));
    }

    @Test
    public void noneSatisfyCodePoint_1() {
        assertFalse(StringIterate.noneSatisfy("MaRy", CodePointPredicate.IS_UPPERCASE));
    }

    @Test
    public void noneSatisfyCodePoint_2() {
        assertTrue(StringIterate.noneSatisfy("mary", CodePointPredicate.IS_UPPERCASE));
    }

    @Test
    public void noneSatisfyCodePointUnicode_1() {
        assertFalse(StringIterate.noneSatisfy("\u3042\u3044\u3046", CodePointPredicate.IS_BMP));
    }

    @Test
    public void noneSatisfyCodePointUnicode_2() {
        assertTrue(StringIterate.noneSatisfy("\uD840\uDC00\uD840\uDC03\uD83D\uDE09", CodePointPredicate.IS_BMP));
    }

    @Test
    public void isNumber_1() {
        assertTrue(StringIterate.isNumber("123"));
    }

    @Test
    public void isNumber_2() {
        assertFalse(StringIterate.isNumber("abc"));
    }

    @Test
    public void isNumber_3() {
        assertFalse(StringIterate.isNumber(""));
    }

    @Test
    public void isAlphaNumeric_1() {
        assertTrue(StringIterate.isAlphaNumeric("123"));
    }

    @Test
    public void isAlphaNumeric_2() {
        assertTrue(StringIterate.isAlphaNumeric("abc"));
    }

    @Test
    public void isAlphaNumeric_3() {
        assertTrue(StringIterate.isAlphaNumeric("123abc"));
    }

    @Test
    public void isAlphaNumeric_4() {
        assertFalse(StringIterate.isAlphaNumeric("!@#"));
    }

    @Test
    public void isAlphaNumeric_5() {
        assertFalse(StringIterate.isAlphaNumeric(""));
    }

    @Test
    public void getLastToken_1() {
        assertEquals("charlie", StringIterate.getLastToken("alpha~|~beta~|~charlie", "~|~"));
    }

    @Test
    public void getLastToken_2() {
        assertEquals("123", StringIterate.getLastToken("123", "~|~"));
    }

    @Test
    public void getLastToken_3() {
        assertEquals("", StringIterate.getLastToken("", "~|~"));
    }

    @Test
    public void getLastToken_4() {
        assertNull(StringIterate.getLastToken(null, "~|~"));
    }

    @Test
    public void getLastToken_5() {
        assertEquals("", StringIterate.getLastToken("123~|~", "~|~"));
    }

    @Test
    public void getLastToken_6() {
        assertEquals("123", StringIterate.getLastToken("~|~123", "~|~"));
    }

    @Test
    public void getFirstToken_1() {
        assertEquals("alpha", StringIterate.getFirstToken("alpha~|~beta~|~charlie", "~|~"));
    }

    @Test
    public void getFirstToken_2() {
        assertEquals("123", StringIterate.getFirstToken("123", "~|~"));
    }

    @Test
    public void getFirstToken_3() {
        assertEquals("", StringIterate.getFirstToken("", "~|~"));
    }

    @Test
    public void getFirstToken_4() {
        assertNull(StringIterate.getFirstToken(null, "~|~"));
    }

    @Test
    public void getFirstToken_5() {
        assertEquals("123", StringIterate.getFirstToken("123~|~", "~|~"));
    }

    @Test
    public void getFirstToken_6() {
        assertEquals("", StringIterate.getFirstToken("~|~123,", "~|~"));
    }

    @Test
    public void isEmptyOrWhitespace_1() {
        assertTrue(StringIterate.isEmptyOrWhitespace("   "));
    }

    @Test
    public void isEmptyOrWhitespace_2() {
        assertFalse(StringIterate.isEmptyOrWhitespace(" 1  "));
    }

    @Test
    public void notEmptyOrWhitespace_1() {
        assertFalse(StringIterate.notEmptyOrWhitespace("   "));
    }

    @Test
    public void notEmptyOrWhitespace_2() {
        assertTrue(StringIterate.notEmptyOrWhitespace(" 1  "));
    }

    @Test
    public void isEmpty_1() {
        assertTrue(StringIterate.isEmpty(""));
    }

    @Test
    public void isEmpty_2() {
        assertFalse(StringIterate.isEmpty("   "));
    }

    @Test
    public void isEmpty_3() {
        assertFalse(StringIterate.isEmpty("1"));
    }

    @Test
    public void notEmpty_1() {
        assertFalse(StringIterate.notEmpty(""));
    }

    @Test
    public void notEmpty_2() {
        assertTrue(StringIterate.notEmpty("   "));
    }

    @Test
    public void notEmpty_3() {
        assertTrue(StringIterate.notEmpty("1"));
    }

    @Test
    public void repeat_1() {
        assertEquals("", StringIterate.repeat("", 42));
    }

    @Test
    public void repeat_2() {
        assertEquals("    ", StringIterate.repeat(' ', 4));
    }

    @Test
    public void repeat_3() {
        assertEquals("        ", StringIterate.repeat(" ", 8));
    }

    @Test
    public void repeat_4() {
        assertEquals("CubedCubedCubed", StringIterate.repeat("Cubed", 3));
    }

    @Test
    public void padOrTrim_1() {
        assertEquals("abcdefghijkl", StringIterate.padOrTrim("abcdefghijkl", 12));
    }

    @Test
    public void padOrTrim_2() {
        assertEquals("this n", StringIterate.padOrTrim("this needs to be trimmed", 6));
    }

    @Test
    public void padOrTrim_3() {
        assertEquals("pad this      ", StringIterate.padOrTrim("pad this", 14));
    }

    @Test
    public void chunk_1() {
        assertEquals(Lists.immutable.with("ab", "cd", "ef"), StringIterate.chunk("abcdef", 2));
    }

    @Test
    public void chunk_2() {
        assertEquals(Lists.immutable.with("abc", "def"), StringIterate.chunk("abcdef", 3));
    }

    @Test
    public void chunk_3() {
        assertEquals(Lists.immutable.with("abc", "def", "g"), StringIterate.chunk("abcdefg", 3));
    }

    @Test
    public void chunk_4() {
        assertEquals(Lists.immutable.with("abcdef"), StringIterate.chunk("abcdef", 6));
    }

    @Test
    public void chunk_5() {
        assertEquals(Lists.immutable.with("abcdef"), StringIterate.chunk("abcdef", 7));
    }

    @Test
    public void chunk_6() {
        assertEquals(Lists.immutable.with(), StringIterate.chunk("", 2));
    }
}
