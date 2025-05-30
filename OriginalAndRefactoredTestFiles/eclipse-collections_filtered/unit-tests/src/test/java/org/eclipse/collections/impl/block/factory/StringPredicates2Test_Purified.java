package org.eclipse.collections.impl.block.factory;

import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StringPredicates2Test_Purified {

    @Test
    public void startsWith_1() {
        assertFalse(StringPredicates2.startsWith().accept(null, "Hello"));
    }

    @Test
    public void startsWith_2() {
        assertTrue(StringPredicates2.startsWith().accept("HelloWorld", "Hello"));
    }

    @Test
    public void startsWith_3() {
        assertFalse(StringPredicates2.startsWith().accept("HelloWorld", "World"));
    }

    @Test
    public void startsWith_4() {
        assertEquals("StringPredicates2.startsWith()", StringPredicates2.startsWith().toString());
    }

    @Test
    public void notStartsWith_1() {
        assertTrue(StringPredicates2.notStartsWith().accept(null, "Hello"));
    }

    @Test
    public void notStartsWith_2() {
        assertFalse(StringPredicates2.notStartsWith().accept("HelloWorld", "Hello"));
    }

    @Test
    public void notStartsWith_3() {
        assertTrue(StringPredicates2.notStartsWith().accept("HelloWorld", "World"));
    }

    @Test
    public void notStartsWith_4() {
        assertEquals("StringPredicates2.notStartsWith()", StringPredicates2.notStartsWith().toString());
    }

    @Test
    public void endsWith_1() {
        assertFalse(StringPredicates2.endsWith().accept(null, "Hello"));
    }

    @Test
    public void endsWith_2() {
        assertFalse(StringPredicates2.endsWith().accept("HelloWorld", "Hello"));
    }

    @Test
    public void endsWith_3() {
        assertTrue(StringPredicates2.endsWith().accept("HelloWorld", "World"));
    }

    @Test
    public void endsWith_4() {
        assertEquals("StringPredicates2.endsWith()", StringPredicates2.endsWith().toString());
    }

    @Test
    public void notEndsWith_1() {
        assertTrue(StringPredicates2.notEndsWith().accept(null, "Hello"));
    }

    @Test
    public void notEndsWith_2() {
        assertTrue(StringPredicates2.notEndsWith().accept("HelloWorld", "Hello"));
    }

    @Test
    public void notEndsWith_3() {
        assertFalse(StringPredicates2.notEndsWith().accept("HelloWorld", "World"));
    }

    @Test
    public void notEndsWith_4() {
        assertEquals("StringPredicates2.notEndsWith()", StringPredicates2.notEndsWith().toString());
    }

    @Test
    public void equalsIgnoreCase_1() {
        assertFalse(StringPredicates2.equalsIgnoreCase().accept(null, "HELLO"));
    }

    @Test
    public void equalsIgnoreCase_2() {
        assertTrue(StringPredicates2.equalsIgnoreCase().accept("hello", "HELLO"));
    }

    @Test
    public void equalsIgnoreCase_3() {
        assertTrue(StringPredicates2.equalsIgnoreCase().accept("WORLD", "world"));
    }

    @Test
    public void equalsIgnoreCase_4() {
        assertFalse(StringPredicates2.equalsIgnoreCase().accept("World", "Hello"));
    }

    @Test
    public void equalsIgnoreCase_5() {
        assertEquals("StringPredicates2.equalsIgnoreCase()", StringPredicates2.equalsIgnoreCase().toString());
    }

    @Test
    public void notEqualsIgnoreCase_1() {
        assertTrue(StringPredicates2.notEqualsIgnoreCase().accept(null, "HELLO"));
    }

    @Test
    public void notEqualsIgnoreCase_2() {
        assertFalse(StringPredicates2.notEqualsIgnoreCase().accept("hello", "HELLO"));
    }

    @Test
    public void notEqualsIgnoreCase_3() {
        assertFalse(StringPredicates2.notEqualsIgnoreCase().accept("WORLD", "world"));
    }

    @Test
    public void notEqualsIgnoreCase_4() {
        assertTrue(StringPredicates2.notEqualsIgnoreCase().accept("World", "Hello"));
    }

    @Test
    public void notEqualsIgnoreCase_5() {
        assertEquals("StringPredicates2.notEqualsIgnoreCase()", StringPredicates2.notEqualsIgnoreCase().toString());
    }

    @Test
    public void containsString_1() {
        assertTrue(StringPredicates2.contains().accept("WorldHelloWorld", "Hello"));
    }

    @Test
    public void containsString_2() {
        assertFalse(StringPredicates2.contains().accept("WorldHelloWorld", "Goodbye"));
    }

    @Test
    public void containsString_3() {
        assertEquals("StringPredicates2.contains()", StringPredicates2.contains().toString());
    }

    @Test
    public void matches_1() {
        assertTrue(StringPredicates2.matches().accept("aaaaabbbbb", "a*b*"));
    }

    @Test
    public void matches_2() {
        assertFalse(StringPredicates2.matches().accept("ba", "a*b"));
    }

    @Test
    public void matches_3() {
        assertEquals("StringPredicates2.matches()", StringPredicates2.matches().toString());
    }
}
