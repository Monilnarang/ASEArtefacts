package org.apache.commons.configuration2.tree;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class TestDefaultExpressionEngineSymbols_Purified {

    private static DefaultExpressionEngineSymbols.Builder builder() {
        return new DefaultExpressionEngineSymbols.Builder(DefaultExpressionEngineSymbols.DEFAULT_SYMBOLS);
    }

    private static void expEqual(final Object o1, final Object o2) {
        assertEquals(o1, o2);
        assertEquals(o2, o1);
        assertEquals(o1.hashCode(), o2.hashCode());
    }

    private static void expNE(final Object o1, final Object o2) {
        assertNotEquals(o1, o2);
        if (o2 != null) {
            assertNotEquals(o2, o1);
        }
    }

    @Test
    public void testDefaultSymbols_1() {
        assertEquals(".", DefaultExpressionEngineSymbols.DEFAULT_SYMBOLS.getPropertyDelimiter());
    }

    @Test
    public void testDefaultSymbols_2() {
        assertEquals("..", DefaultExpressionEngineSymbols.DEFAULT_SYMBOLS.getEscapedDelimiter());
    }

    @Test
    public void testDefaultSymbols_3() {
        assertEquals("(", DefaultExpressionEngineSymbols.DEFAULT_SYMBOLS.getIndexStart());
    }

    @Test
    public void testDefaultSymbols_4() {
        assertEquals(")", DefaultExpressionEngineSymbols.DEFAULT_SYMBOLS.getIndexEnd());
    }

    @Test
    public void testDefaultSymbols_5() {
        assertEquals("[@", DefaultExpressionEngineSymbols.DEFAULT_SYMBOLS.getAttributeStart());
    }

    @Test
    public void testDefaultSymbols_6() {
        assertEquals("]", DefaultExpressionEngineSymbols.DEFAULT_SYMBOLS.getAttributeEnd());
    }
}
