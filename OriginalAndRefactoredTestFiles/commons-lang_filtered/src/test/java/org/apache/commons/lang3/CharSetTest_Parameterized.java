package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.lang.reflect.Modifier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class CharSetTest_Parameterized extends AbstractLangTest {

    @Test
    public void testClass_1() {
        assertTrue(Modifier.isPublic(CharSet.class.getModifiers()));
    }

    @Test
    public void testClass_2() {
        assertFalse(Modifier.isFinal(CharSet.class.getModifiers()));
    }

    @Test
    public void testGetInstance_1() {
        assertSame(CharSet.EMPTY, CharSet.getInstance((String) null));
    }

    @Test
    public void testGetInstance_2() {
        assertSame(CharSet.EMPTY, CharSet.getInstance(""));
    }

    @Test
    public void testGetInstance_5() {
        assertSame(CharSet.ASCII_ALPHA_LOWER, CharSet.getInstance("a-z"));
    }

    @Test
    public void testGetInstance_6() {
        assertSame(CharSet.ASCII_ALPHA_UPPER, CharSet.getInstance("A-Z"));
    }

    @Test
    public void testGetInstance_7() {
        assertSame(CharSet.ASCII_NUMERIC, CharSet.getInstance("0-9"));
    }

    @Test
    public void testJavadocExamples_7() {
        assertTrue(CharSet.getInstance("^", "a-c").contains('^'));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetInstance_3to4")
    public void testGetInstance_3to4(String param1) {
        assertSame(CharSet.ASCII_ALPHA, CharSet.getInstance(param1));
    }

    static public Stream<Arguments> Provider_testGetInstance_3to4() {
        return Stream.of(arguments("a-zA-Z"), arguments("A-Za-z"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testJavadocExamples_1_4")
    public void testJavadocExamples_1_4(String param1, String param2) {
        assertFalse(CharSet.getInstance(param2).contains(param1));
    }

    static public Stream<Arguments> Provider_testJavadocExamples_1_4() {
        return Stream.of(arguments("a", "^a-c"), arguments("^", "^^a-c"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testJavadocExamples_2to3_5to6")
    public void testJavadocExamples_2to3_5to6(String param1, String param2) {
        assertTrue(CharSet.getInstance(param2).contains(param1));
    }

    static public Stream<Arguments> Provider_testJavadocExamples_2to3_5to6() {
        return Stream.of(arguments("d", "^a-c"), arguments("a", "^^a-c"), arguments("d", "^a-cd-f"), arguments("^", "a-c^"));
    }
}
