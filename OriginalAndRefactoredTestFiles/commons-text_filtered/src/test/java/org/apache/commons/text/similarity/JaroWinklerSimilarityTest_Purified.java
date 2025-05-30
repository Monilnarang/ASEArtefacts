package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class JaroWinklerSimilarityTest_Purified {

    private static JaroWinklerSimilarity similarity;

    @BeforeAll
    public static void setUp() {
        similarity = new JaroWinklerSimilarity();
    }

    private static CharSequence wrap(final String string) {
        return new CharSequence() {

            @Override
            public char charAt(final int index) {
                return string.charAt(index);
            }

            @Override
            public boolean equals(final Object obj) {
                return string.equals(obj);
            }

            @Override
            public int hashCode() {
                return string.hashCode();
            }

            @Override
            public int length() {
                return string.length();
            }

            @Override
            public CharSequence subSequence(final int start, final int end) {
                return string.subSequence(start, end);
            }

            @Override
            public String toString() {
                return string;
            }
        };
    }

    @Test
    public void testGetJaroWinklerSimilarity_StringString_1() {
        assertEquals(1d, similarity.apply(wrap(""), ""), 0.00001d);
    }

    @Test
    public void testGetJaroWinklerSimilarity_StringString_2() {
        assertEquals(1d, similarity.apply(wrap("foo"), "foo"), 0.00001d);
    }

    @Test
    public void testGetJaroWinklerSimilarity_StringString_3() {
        assertEquals(0.94166d, similarity.apply(wrap("foo"), "foo "), 0.00001d);
    }

    @Test
    public void testGetJaroWinklerSimilarity_StringString_4() {
        assertEquals(0.90666d, similarity.apply(wrap("foo"), "foo  "), 0.00001d);
    }

    @Test
    public void testGetJaroWinklerSimilarity_StringString_5() {
        assertEquals(0.86666d, similarity.apply(wrap("foo"), " foo "), 0.00001d);
    }

    @Test
    public void testGetJaroWinklerSimilarity_StringString_6() {
        assertEquals(0.51111d, similarity.apply(wrap("foo"), "  foo"), 0.00001d);
    }

    @Test
    public void testGetJaroWinklerSimilarity_StringString_7() {
        assertEquals(0.92499d, similarity.apply(wrap("frog"), "fog"), 0.00001d);
    }

    @Test
    public void testGetJaroWinklerSimilarity_StringString_8() {
        assertEquals(0.0d, similarity.apply(wrap("fly"), "ant"), 0.00000000000000000001d);
    }

    @Test
    public void testGetJaroWinklerSimilarity_StringString_9() {
        assertEquals(0.44166d, similarity.apply(wrap("elephant"), "hippo"), 0.00001d);
    }

    @Test
    public void testGetJaroWinklerSimilarity_StringString_10() {
        assertEquals(0.90666d, similarity.apply(wrap("ABC Corporation"), "ABC Corp"), 0.00001d);
    }

    @Test
    public void testGetJaroWinklerSimilarity_StringString_11() {
        assertEquals(0.95251d, similarity.apply(wrap("D N H Enterprises Inc"), "D & H Enterprises, Inc."), 0.00001d);
    }

    @Test
    public void testGetJaroWinklerSimilarity_StringString_12() {
        assertEquals(0.942d, similarity.apply(wrap("My Gym Children's Fitness Center"), "My Gym. Childrens Fitness"), 0.00001d);
    }

    @Test
    public void testGetJaroWinklerSimilarity_StringString_13() {
        assertEquals(0.898018d, similarity.apply(wrap("PENNSYLVANIA"), "PENNCISYLVNIA"), 0.00001d);
    }

    @Test
    public void testGetJaroWinklerSimilarity_StringString_14() {
        assertEquals(0.971428d, similarity.apply(wrap("/opt/software1"), "/opt/software2"), 0.00001d);
    }

    @Test
    public void testGetJaroWinklerSimilarity_StringString_15() {
        assertEquals(0.941666d, similarity.apply(wrap("aaabcd"), "aaacdb"), 0.00001d);
    }

    @Test
    public void testGetJaroWinklerSimilarity_StringString_16() {
        assertEquals(0.911111d, similarity.apply(wrap("John Horn"), "John Hopkins"), 0.00001d);
    }
}
