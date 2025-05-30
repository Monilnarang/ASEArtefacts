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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class StringIterateTest_Parameterized {

    public static final String THE_QUICK_BROWN_FOX_JUMPS_OVER_THE_LAZY_DOG = "The quick brown fox jumps over the lazy dog.";

    public static final String ALPHABET_LOWERCASE = "abcdefghijklmnopqrstuvwxyz";

    public static final Twin<String> HALF_ABET = StringIterate.splitAtIndex(ALPHABET_LOWERCASE, 13);

    public static final String TQBFJOTLD_MINUS_HALF_ABET_1 = "t qu rown ox ups ovr t zy o.";

    public static final String TQBFJOTLD_MINUS_HALF_ABET_2 = "he ick b f jm e he la dg.";

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
    public void englishToUpperCase_3() {
        assertSame("A,B,C", StringIterate.englishToUpperCase("A,B,C"));
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
    public void getLastToken_4() {
        assertNull(StringIterate.getLastToken(null, "~|~"));
    }

    @Test
    public void getFirstToken_4() {
        assertNull(StringIterate.getFirstToken(null, "~|~"));
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
    public void notEmpty_1() {
        assertFalse(StringIterate.notEmpty(""));
    }

    @Test
    public void chunk_2() {
        assertEquals(Lists.immutable.with("abc", "def"), StringIterate.chunk("abcdef", 3));
    }

    @Test
    public void chunk_6() {
        assertEquals(Lists.immutable.with(), StringIterate.chunk("", 2));
    }

    @ParameterizedTest
    @MethodSource("Provider_englishToUpperLowerCase_1_1to2")
    public void englishToUpperLowerCase_1_1to2(String param1, String param2) {
        assertEquals(param1, StringIterate.englishToUpperCase(param2));
    }

    static public Stream<Arguments> Provider_englishToUpperLowerCase_1_1to2() {
        return Stream.of(arguments("ABC", "abc"), arguments("ABC", "abc"), arguments("A,B,C", "a,b,c"));
    }

    @ParameterizedTest
    @MethodSource("Provider_englishToUpperLowerCase_1to2_2")
    public void englishToUpperLowerCase_1to2_2(String param1, String param2) {
        assertEquals(param1, StringIterate.englishToLowerCase(param2));
    }

    static public Stream<Arguments> Provider_englishToUpperLowerCase_1to2_2() {
        return Stream.of(arguments("abc", "ABC"), arguments("abc", "ABC"), arguments("a,b,c", "A,B,C"));
    }

    @ParameterizedTest
    @MethodSource("Provider_collectCodePointUnicode_1to2")
    public void collectCodePointUnicode_1to2(String param1, String param2) {
        assertEquals(param1, StringIterate.collect(param2, CodePointFunction.PASS_THRU));
    }

    static public Stream<Arguments> Provider_collectCodePointUnicode_1to2() {
        return Stream.of(arguments("\u3042\uD840\uDC00\u3044\uD840\uDC03\u3046\uD83D\uDE09", "\u3042\uD840\uDC00\u3044\uD840\uDC03\u3046\uD83D\uDE09"), arguments("\u3042\uD840\uDC00\u3044\uD840\uDC03\u3046\uD83D\uDE09", "\u3042\uD840\uDC00\u3044\uD840\uDC03\u3046\uD83D\uDE09"));
    }

    @ParameterizedTest
    @MethodSource("Provider_isNumber_2to3")
    public void isNumber_2to3(String param1) {
        assertFalse(StringIterate.isNumber(param1));
    }

    static public Stream<Arguments> Provider_isNumber_2to3() {
        return Stream.of(arguments("abc"), arguments(""));
    }

    @ParameterizedTest
    @MethodSource("Provider_isAlphaNumeric_1to3")
    public void isAlphaNumeric_1to3(int param1) {
        assertTrue(StringIterate.isAlphaNumeric(param1));
    }

    static public Stream<Arguments> Provider_isAlphaNumeric_1to3() {
        return Stream.of(arguments(123), arguments("abc"), arguments("123abc"));
    }

    @ParameterizedTest
    @MethodSource("Provider_isAlphaNumeric_4to5")
    public void isAlphaNumeric_4to5(String param1) {
        assertFalse(StringIterate.isAlphaNumeric(param1));
    }

    static public Stream<Arguments> Provider_isAlphaNumeric_4to5() {
        return Stream.of(arguments("!@#"), arguments(""));
    }

    @ParameterizedTest
    @MethodSource("Provider_getLastToken_1to3_5to6")
    public void getLastToken_1to3_5to6(String param1, String param2, String param3) {
        assertEquals(param1, StringIterate.getLastToken(param2, param3));
    }

    static public Stream<Arguments> Provider_getLastToken_1to3_5to6() {
        return Stream.of(arguments("charlie", "alpha~|~beta~|~charlie", "~|~"), arguments(123, 123, "~|~"), arguments("", "", "~|~"), arguments("", "123~|~", "~|~"), arguments(123, "~|~123", "~|~"));
    }

    @ParameterizedTest
    @MethodSource("Provider_getFirstToken_1to3_5to6")
    public void getFirstToken_1to3_5to6(String param1, String param2, String param3) {
        assertEquals(param1, StringIterate.getFirstToken(param2, param3));
    }

    static public Stream<Arguments> Provider_getFirstToken_1to3_5to6() {
        return Stream.of(arguments("alpha", "alpha~|~beta~|~charlie", "~|~"), arguments(123, 123, "~|~"), arguments("", "", "~|~"), arguments(123, "123~|~", "~|~"), arguments("", "~|~123,", "~|~"));
    }

    @ParameterizedTest
    @MethodSource("Provider_isEmpty_2to3")
    public void isEmpty_2to3(String param1) {
        assertFalse(StringIterate.isEmpty(param1));
    }

    static public Stream<Arguments> Provider_isEmpty_2to3() {
        return Stream.of(arguments("   "), arguments(1));
    }

    @ParameterizedTest
    @MethodSource("Provider_notEmpty_2to3")
    public void notEmpty_2to3(String param1) {
        assertTrue(StringIterate.notEmpty(param1));
    }

    static public Stream<Arguments> Provider_notEmpty_2to3() {
        return Stream.of(arguments("   "), arguments(1));
    }

    @ParameterizedTest
    @MethodSource("Provider_repeat_1to4")
    public void repeat_1to4(String param1, String param2, int param3) {
        assertEquals(param1, StringIterate.repeat(param2, param3));
    }

    static public Stream<Arguments> Provider_repeat_1to4() {
        return Stream.of(arguments("", "", 42), arguments("    ", " ", 4), arguments("        ", " ", 8), arguments("CubedCubedCubed", "Cubed", 3));
    }

    @ParameterizedTest
    @MethodSource("Provider_padOrTrim_1to3")
    public void padOrTrim_1to3(String param1, String param2, int param3) {
        assertEquals(param1, StringIterate.padOrTrim(param2, param3));
    }

    static public Stream<Arguments> Provider_padOrTrim_1to3() {
        return Stream.of(arguments("abcdefghijkl", "abcdefghijkl", 12), arguments("this n", "this needs to be trimmed", 6), arguments("pad this      ", "pad this", 14));
    }

    @ParameterizedTest
    @MethodSource("Provider_chunk_1_3")
    public void chunk_1_3(String param1, String param2, String param3, String param4, int param5) {
        assertEquals(Lists.immutable.with(param1, param2, param3), StringIterate.chunk(param4, param5));
    }

    static public Stream<Arguments> Provider_chunk_1_3() {
        return Stream.of(arguments("ab", "cd", "ef", "abcdef", 2), arguments("abc", "def", "g", "abcdefg", 3));
    }

    @ParameterizedTest
    @MethodSource("Provider_chunk_4to5")
    public void chunk_4to5(String param1, String param2, int param3) {
        assertEquals(Lists.immutable.with(param1), StringIterate.chunk(param2, param3));
    }

    static public Stream<Arguments> Provider_chunk_4to5() {
        return Stream.of(arguments("abcdef", "abcdef", 6), arguments("abcdef", "abcdef", 7));
    }
}
