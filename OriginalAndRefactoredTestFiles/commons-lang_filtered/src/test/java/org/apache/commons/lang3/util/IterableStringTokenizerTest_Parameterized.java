package org.apache.commons.lang3.util;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class IterableStringTokenizerTest_Parameterized {

    private static final String[] DELIMITERS_ARRAY = { " ", "\t", "\n", "\r", "\f" };

    private static final String DELIMITERS_STRING = String.join("", DELIMITERS_ARRAY);

    private static final String[] DATA = { "a", "b", "c" };

    public static String[] delimiters() {
        return DELIMITERS_ARRAY;
    }

    @Test
    public void testToList_5() {
        assertEquals(Arrays.asList("a", "b"), new IterableStringTokenizer("a b").toList());
    }

    @Test
    public void testToStream_5() {
        assertEquals(Arrays.asList("a", "b"), new IterableStringTokenizer("a b").toStream().collect(Collectors.toList()));
    }

    @ParameterizedTest
    @MethodSource("Provider_testNonDefaultDelimiterToList_1to3")
    void testNonDefaultDelimiterToList_1to3(String param1, String param2) {
        assertEquals(Arrays.asList(DATA), new IterableStringTokenizer(param1, param2).toList());
    }

    static public Stream<Arguments> Provider_testNonDefaultDelimiterToList_1to3() {
        return Stream.of(arguments("a|b|c", "|"), arguments("a!b!c", "!"), arguments("a^!b^!c", "^!"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testToList_1_1")
    public void testToList_1_1(String param1) {
        assertEquals(Arrays.asList(), new IterableStringTokenizer(param1).toList());
    }

    static public Stream<Arguments> Provider_testToList_1_1() {
        return Stream.of(arguments(""), arguments(""));
    }

    @ParameterizedTest
    @MethodSource("Provider_testToList_2_2to4")
    public void testToList_2_2to4(String param1, String param2) {
        assertEquals(Arrays.asList(param1), new IterableStringTokenizer(param2).toList());
    }

    static public Stream<Arguments> Provider_testToList_2_2to4() {
        return Stream.of(arguments("a", "a"), arguments("a,b", "a,b"), arguments("a,b,c", "a,b,c"), arguments("a", "a"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testToStream_3to4")
    public void testToStream_3to4(String param1, String param2) {
        assertEquals(Arrays.asList(param1), new IterableStringTokenizer(param2).toStream().collect(Collectors.toList()));
    }

    static public Stream<Arguments> Provider_testToStream_3to4() {
        return Stream.of(arguments("a,b", "a,b"), arguments("a,b,c", "a,b,c"));
    }
}
