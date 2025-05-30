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

public class IterableStringTokenizerTest_Purified {

    private static final String[] DELIMITERS_ARRAY = { " ", "\t", "\n", "\r", "\f" };

    private static final String DELIMITERS_STRING = String.join("", DELIMITERS_ARRAY);

    private static final String[] DATA = { "a", "b", "c" };

    public static String[] delimiters() {
        return DELIMITERS_ARRAY;
    }

    @Test
    void testNonDefaultDelimiterToList_1() {
        assertEquals(Arrays.asList(DATA), new IterableStringTokenizer("a|b|c", "|").toList());
    }

    @Test
    void testNonDefaultDelimiterToList_2() {
        assertEquals(Arrays.asList(DATA), new IterableStringTokenizer("a!b!c", "!").toList());
    }

    @Test
    void testNonDefaultDelimiterToList_3() {
        assertEquals(Arrays.asList(DATA), new IterableStringTokenizer("a^!b^!c", "^!").toList());
    }

    @Test
    public void testToList_1() {
        assertEquals(Arrays.asList(), new IterableStringTokenizer("").toList());
    }

    @Test
    public void testToList_2() {
        assertEquals(Arrays.asList("a"), new IterableStringTokenizer("a").toList());
    }

    @Test
    public void testToList_3() {
        assertEquals(Arrays.asList("a,b"), new IterableStringTokenizer("a,b").toList());
    }

    @Test
    public void testToList_4() {
        assertEquals(Arrays.asList("a,b,c"), new IterableStringTokenizer("a,b,c").toList());
    }

    @Test
    public void testToList_5() {
        assertEquals(Arrays.asList("a", "b"), new IterableStringTokenizer("a b").toList());
    }

    @Test
    public void testToStream_1() {
        assertEquals(Arrays.asList(), new IterableStringTokenizer("").toList());
    }

    @Test
    public void testToStream_2() {
        assertEquals(Arrays.asList("a"), new IterableStringTokenizer("a").toList());
    }

    @Test
    public void testToStream_3() {
        assertEquals(Arrays.asList("a,b"), new IterableStringTokenizer("a,b").toStream().collect(Collectors.toList()));
    }

    @Test
    public void testToStream_4() {
        assertEquals(Arrays.asList("a,b,c"), new IterableStringTokenizer("a,b,c").toStream().collect(Collectors.toList()));
    }

    @Test
    public void testToStream_5() {
        assertEquals(Arrays.asList("a", "b"), new IterableStringTokenizer("a b").toStream().collect(Collectors.toList()));
    }
}
