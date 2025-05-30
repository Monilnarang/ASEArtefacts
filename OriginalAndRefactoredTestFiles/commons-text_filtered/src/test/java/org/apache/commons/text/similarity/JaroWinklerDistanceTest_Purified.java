package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class JaroWinklerDistanceTest_Purified {

    private static JaroWinklerDistance distance;

    @BeforeAll
    public static void setUp() {
        distance = new JaroWinklerDistance();
    }

    @Test
    public void testGetJaroWinklerDistance_StringString_1() {
        assertEquals(0.07501d, distance.apply("frog", "fog"), 0.00001d);
    }

    @Test
    public void testGetJaroWinklerDistance_StringString_2() {
        assertEquals(1.0d, distance.apply("fly", "ant"), 0.00000000000000000001d);
    }

    @Test
    public void testGetJaroWinklerDistance_StringString_3() {
        assertEquals(0.55834d, distance.apply("elephant", "hippo"), 0.00001d);
    }

    @Test
    public void testGetJaroWinklerDistance_StringString_4() {
        assertEquals(0.09334d, distance.apply("ABC Corporation", "ABC Corp"), 0.00001d);
    }

    @Test
    public void testGetJaroWinklerDistance_StringString_5() {
        assertEquals(0.04749d, distance.apply("D N H Enterprises Inc", "D & H Enterprises, Inc."), 0.00001d);
    }

    @Test
    public void testGetJaroWinklerDistance_StringString_6() {
        assertEquals(0.058d, distance.apply("My Gym Children's Fitness Center", "My Gym. Childrens Fitness"), 0.00001d);
    }

    @Test
    public void testGetJaroWinklerDistance_StringString_7() {
        assertEquals(0.101982d, distance.apply("PENNSYLVANIA", "PENNCISYLVNIA"), 0.00001d);
    }

    @Test
    public void testGetJaroWinklerDistance_StringString_8() {
        assertEquals(0.028572d, distance.apply("/opt/software1", "/opt/software2"), 0.00001d);
    }

    @Test
    public void testGetJaroWinklerDistance_StringString_9() {
        assertEquals(0.058334d, distance.apply("aaabcd", "aaacdb"), 0.00001d);
    }

    @Test
    public void testGetJaroWinklerDistance_StringString_10() {
        assertEquals(0.088889d, distance.apply("John Horn", "John Hopkins"), 0.00001d);
    }

    @Test
    public void testGetJaroWinklerDistance_StringString_11() {
        assertEquals(0d, distance.apply("", ""), 0.00001d);
    }

    @Test
    public void testGetJaroWinklerDistance_StringString_12() {
        assertEquals(0d, distance.apply("foo", "foo"), 0.00001d);
    }

    @Test
    public void testGetJaroWinklerDistance_StringString_13() {
        assertEquals(1 - 0.94166d, distance.apply("foo", "foo "), 0.00001d);
    }

    @Test
    public void testGetJaroWinklerDistance_StringString_14() {
        assertEquals(1 - 0.90666d, distance.apply("foo", "foo  "), 0.00001d);
    }

    @Test
    public void testGetJaroWinklerDistance_StringString_15() {
        assertEquals(1 - 0.86666d, distance.apply("foo", " foo "), 0.00001d);
    }

    @Test
    public void testGetJaroWinklerDistance_StringString_16() {
        assertEquals(1 - 0.51111d, distance.apply("foo", "  foo"), 0.00001d);
    }
}
