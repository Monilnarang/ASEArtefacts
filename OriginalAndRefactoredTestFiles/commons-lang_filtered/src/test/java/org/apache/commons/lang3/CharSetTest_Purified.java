package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.lang.reflect.Modifier;
import org.junit.jupiter.api.Test;

public class CharSetTest_Purified extends AbstractLangTest {

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
    public void testGetInstance_3() {
        assertSame(CharSet.ASCII_ALPHA, CharSet.getInstance("a-zA-Z"));
    }

    @Test
    public void testGetInstance_4() {
        assertSame(CharSet.ASCII_ALPHA, CharSet.getInstance("A-Za-z"));
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
    public void testJavadocExamples_1() {
        assertFalse(CharSet.getInstance("^a-c").contains('a'));
    }

    @Test
    public void testJavadocExamples_2() {
        assertTrue(CharSet.getInstance("^a-c").contains('d'));
    }

    @Test
    public void testJavadocExamples_3() {
        assertTrue(CharSet.getInstance("^^a-c").contains('a'));
    }

    @Test
    public void testJavadocExamples_4() {
        assertFalse(CharSet.getInstance("^^a-c").contains('^'));
    }

    @Test
    public void testJavadocExamples_5() {
        assertTrue(CharSet.getInstance("^a-cd-f").contains('d'));
    }

    @Test
    public void testJavadocExamples_6() {
        assertTrue(CharSet.getInstance("a-c^").contains('^'));
    }

    @Test
    public void testJavadocExamples_7() {
        assertTrue(CharSet.getInstance("^", "a-c").contains('^'));
    }
}
